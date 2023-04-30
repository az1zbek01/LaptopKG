package com.example.LaptopKG.service;


import com.example.LaptopKG.dto.brand.RequestBrandDTO;
import com.example.LaptopKG.dto.brand.ResponseBrandDTO;
import com.example.LaptopKG.exception.AlreadyExistException;
import com.example.LaptopKG.exception.NotFoundException;
import com.example.LaptopKG.model.Brand;
import com.example.LaptopKG.model.enums.Status;
import com.example.LaptopKG.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BrandService {
    private final BrandRepository brandRepository;
    private final ModelMapper mapper;

    // Getting all brands
    public List<ResponseBrandDTO> getAll() {
        // Find all active brands, mapping them from Entity to DTO and return them
        return brandRepository.findAll()
                .stream()
                .filter(brand -> brand.getStatus() == Status.ACTIVE)
                .map(brand -> mapper.map(brand, ResponseBrandDTO.class))
                .collect(Collectors.toList());
    }

    // Getting all deleted brands
    public List<ResponseBrandDTO> getAllDeletedBrands() {
        // Find all deleted brands, mapping them from Entity to DTO and return them
        return brandRepository.findAll()
                .stream()
                .filter(brand -> brand.getStatus() == Status.DELETED)
                .map(brand -> mapper.map(brand, ResponseBrandDTO.class))
                .collect(Collectors.toList());
    }

    // Getting brand by id
    public ResponseBrandDTO getById(Long id) {
        // Map from entity to dto and return brand
        return brandRepository.findById(id)
                // Check if brand is not deleted
                .filter(brand -> brand.getStatus() == Status.ACTIVE)
                .map(brand -> mapper.map(brand, ResponseBrandDTO.class))
                // throw exception if brand doesn't exist
                .orElseThrow(() -> new NotFoundException("Бренд с айди " + id + " не найден"));
    }

    // Brand creating
    public ResponseBrandDTO createBrand(RequestBrandDTO requestBrandDTO) {
        // Check if brand exists by name
        if(brandRepository.existsByName(requestBrandDTO.getName())){
            throw new AlreadyExistException("Brand with name " +
                    requestBrandDTO.getName() + " already exists");
        }
        // We are mapping from DTO to Entity
        Brand brand = mapper.map(requestBrandDTO, Brand.class);

        // Make brand active and save it
        brand.setStatus(Status.ACTIVE);
        brandRepository.save(brand);

        // Mapping from Entity to DTO and return it
        return mapper.map(brand, ResponseBrandDTO.class);
    }

    // Updating brand
    public ResponseBrandDTO updateBrand(Long id, RequestBrandDTO updateBrandDto) {
        // Get brand from DB or throw exception if it doesn't exist
        Brand brand = brandRepository.findById(id)
                // Check if brand is not deleted
                .filter(b -> b.getStatus() == Status.ACTIVE)
                .orElseThrow(() -> new NotFoundException("Бренд с айди " + id + " не найден"));

        // Update brand
        brand.setName(updateBrandDto.getName());

        // Save updated brand
        brandRepository.save(brand);

        // Map from entity to dto and return it
        return mapper.map(brand, ResponseBrandDTO.class);
    }

    // Restore deleted brand
    public ResponseBrandDTO restoreBrandById(long id){
        // Find brand by id or throw exception if already active or doesn't exist in DB
        Brand brand = brandRepository.findById(id)
                .filter(b -> b.getStatus() == Status.DELETED)
                .orElseThrow(
                        () -> new AlreadyExistException("Brand with id " + id + " already active")
                );

        // Make brand active and save it
        brand.setStatus(Status.ACTIVE);
        brandRepository.save(brand);

        // Return restored brand
        return mapper.map(brand, ResponseBrandDTO.class);
    }

    // Brand deleting
    public ResponseEntity<String> deleteBrand(Long id){
        // Get brand from DB or throw exception if it doesn't exist
        Brand brand = brandRepository.findById(id)
                // Check if brand is not deleted
                .filter(b -> b.getStatus() == Status.ACTIVE)
                .orElseThrow(() -> new NotFoundException("Бренд с айди " + id + " не найден"));

        // Mark brand as deleted and save it
        brand.setStatus(Status.DELETED);
        brandRepository.save(brand);

        // Return status 200 and message
        return ResponseEntity.ok("Бренд успешно удален");
    }
}
