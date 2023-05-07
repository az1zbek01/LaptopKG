package com.example.LaptopKG.service.implementations;


import com.example.LaptopKG.dto.brand.RequestBrandDTO;
import com.example.LaptopKG.dto.brand.ResponseBrandDTO;
import com.example.LaptopKG.exception.AlreadyExistException;
import com.example.LaptopKG.exception.NotFoundException;
import com.example.LaptopKG.model.Brand;
import com.example.LaptopKG.model.enums.Status;
import com.example.LaptopKG.repository.BrandRepository;
import com.example.LaptopKG.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {
    private final BrandRepository brandRepository;
    private final ModelMapper mapper;

    public List<ResponseBrandDTO> getAll() {
        return brandRepository.findAll()
                .stream()
                .filter(brand -> brand.getStatus() == Status.ACTIVE)
                .map(brand -> mapper.map(brand, ResponseBrandDTO.class))
                .collect(Collectors.toList());
    }

    public List<ResponseBrandDTO> getAllDeletedBrands() {
        return brandRepository.findAll()
                .stream()
                .filter(brand -> brand.getStatus() == Status.DELETED)
                .map(brand -> mapper.map(brand, ResponseBrandDTO.class))
                .collect(Collectors.toList());
    }

    public ResponseBrandDTO getById(Long id) {
        return brandRepository.findById(id)
                .filter(brand -> brand.getStatus() == Status.ACTIVE)
                .map(brand -> mapper.map(brand, ResponseBrandDTO.class))
                .orElseThrow(() -> new NotFoundException("Бренд с айди " + id + " не найден"));
    }

    public ResponseBrandDTO createBrand(RequestBrandDTO requestBrandDTO) {
        if(brandRepository.existsByName(requestBrandDTO.getName())){
            throw new AlreadyExistException("Бренд с названием " +
                    requestBrandDTO.getName() + " уже существует");
        }
        Brand brand = mapper.map(requestBrandDTO, Brand.class);

        brand.setStatus(Status.ACTIVE);
        brandRepository.save(brand);

        return mapper.map(brand, ResponseBrandDTO.class);
    }

    public ResponseBrandDTO updateBrand(Long id, RequestBrandDTO updateBrandDto) {
        Brand brand = findBrandById(id);

        brand.setName(updateBrandDto.getName());
        brandRepository.save(brand);

        return mapper.map(brand, ResponseBrandDTO.class);
    }

    public ResponseBrandDTO restoreBrandById(Long id){
        Brand brand = brandRepository.findById(id)
                .filter(b -> b.getStatus() == Status.DELETED)
                .orElseThrow(
                        () -> new AlreadyExistException("Бренд с айди " + id + " уже существует")
                );

        brand.setStatus(Status.ACTIVE);
        brandRepository.save(brand);

        return mapper.map(brand, ResponseBrandDTO.class);
    }

    public ResponseEntity<String> deleteBrand(Long id){
        Brand brand = findBrandById(id);

        brand.setStatus(Status.DELETED);
        brandRepository.save(brand);

        return ResponseEntity.ok("Бренд успешно удален");
    }

    private Brand findBrandById(Long id){
        return brandRepository.findById(id)
                .filter(b -> b.getStatus() == Status.ACTIVE)
                .orElseThrow(() -> new NotFoundException("Бренд с айди " + id + " не найден"));
    }
}
