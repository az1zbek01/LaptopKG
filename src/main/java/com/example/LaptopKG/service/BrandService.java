package com.example.LaptopKG.service;


import com.example.LaptopKG.dto.brand.CreateAndUpdateBrandDto;
import com.example.LaptopKG.dto.brand.GetBrandDto;
import com.example.LaptopKG.model.Brand;
import com.example.LaptopKG.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BrandService {

    private final BrandRepository brandRepository;

    private final ModelMapper mapper;


    public String createBrand(CreateAndUpdateBrandDto createAndUpdateBrandDto) {

        Brand brand = mapper.map(createAndUpdateBrandDto, Brand.class);
        System.out.println(brand.getBrand());
        brandRepository.save(brand);

        return "Brand successfully created";
    }

    public List<GetBrandDto> getAll() {
        return brandRepository.findAll()
                .stream()
                .map(brand -> mapper.map(brand, GetBrandDto.class))
                .collect(Collectors.toList());
    }

    public GetBrandDto getById(Long id) {
        return brandRepository.findById(id)
                .map(brand -> mapper.map(brand, GetBrandDto.class))
                .orElseThrow();
    }

    public GetBrandDto updateBrand(Long id, CreateAndUpdateBrandDto updateBrandDto) {
        Brand brand = brandRepository.findById(id).orElseThrow();
        brand.setBrand(updateBrandDto.getBrand());
        brandRepository.save(brand);
        return mapper.map(brand, GetBrandDto.class);
    }
}
