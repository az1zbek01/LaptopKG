package com.example.LaptopKG.service;


import com.example.LaptopKG.dto.brand.CreateBrandDto;
import com.example.LaptopKG.dto.brand.GetBrandDto;
import com.example.LaptopKG.dto.brand.UpdateBrandDto;
import com.example.LaptopKG.dto.hardware.GetHardwareDto;
import com.example.LaptopKG.model.Brand;
import com.example.LaptopKG.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BrandService {

    private final BrandRepository brandRepository;

    public String createBrand(CreateBrandDto createBrandDto) {
        brandRepository.save(createBrandDto.toBrand());

        return "Brand successfully created";
    }

    public List<GetBrandDto> getAll() {
        return brandRepository.findAll()
                .stream()
                .map(brand -> new GetBrandDto().brandToDto(brand))
                .collect(Collectors.toList());
    }

    public GetBrandDto getById(Long id) {
        return brandRepository.findById(id)
                .map(brand -> new GetBrandDto().brandToDto(brand))
                .orElseThrow();
    }

    public GetBrandDto updateBrand(Long id, UpdateBrandDto updateBrandDto) {
        return null;
    }
}
