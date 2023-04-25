package com.example.LaptopKG.service;


import com.example.LaptopKG.dto.brand.CreateAndUpdateBrandDto;
import com.example.LaptopKG.dto.brand.GetBrandDto;
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

    // Brand creating
    public GetBrandDto createBrand(CreateAndUpdateBrandDto createAndUpdateBrandDto) {
        // We are mapping from DTO to Entity
        Brand brand = mapper.map(createAndUpdateBrandDto, Brand.class);

        // Make brand active and save it
        brand.setStatus(Status.ACTIVE);
        brandRepository.save(brand);

        // Mapping from Entity to DTO and return it
        return mapper.map(brand, GetBrandDto.class);
    }

    // Getting all brands
    public List<GetBrandDto> getAll() {
        // Find all brands, mapping them from Entity to DTO and return them
        return brandRepository.findAll()
                .stream()
                .map(brand -> mapper.map(brand, GetBrandDto.class))
                .collect(Collectors.toList());
    }

    // Getting brand by id
    public GetBrandDto getById(Long id) {
        // Map from entity to dto and return brand
        return brandRepository.findById(id)
                .map(brand -> mapper.map(brand, GetBrandDto.class))
                // throw exception if brand doesn't exist
                .orElseThrow(() -> new NotFoundException("Бренд с айди " + id + " не найден"));
    }

    // Updating brand
    public GetBrandDto updateBrand(Long id, CreateAndUpdateBrandDto updateBrandDto) {
        // Get brand from DB or throw exception if it doesn't exist
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Бренд с айди " + id + " не найден"));

        // Update brand
        brand.setBrand(updateBrandDto.getBrand());

        // Save updated brand
        brandRepository.save(brand);

        // Map from entity to dto and return it
        return mapper.map(brand, GetBrandDto.class);
    }

    // Brand deleting
    public ResponseEntity<String> deleteBrand(Long id){
        // Get brand from DB or throw exception if it doesn't exist
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Бренд с айди " + id + " не найден"));

        // Mark brand as deleted and save it
        brand.setStatus(Status.DELETED);
        brandRepository.save(brand);

        // Return status 200 and message
        return ResponseEntity.ok("Бренд успешно удален");
    }
}
