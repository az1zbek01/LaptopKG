package com.example.LaptopKG.service;

import com.example.LaptopKG.dto.brand.RequestBrandDTO;
import com.example.LaptopKG.dto.brand.ResponseBrandDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BrandService {
    List<ResponseBrandDTO> getAll();
    List<ResponseBrandDTO> getAllDeletedBrands();
    ResponseBrandDTO getById(Long id);
    ResponseBrandDTO createBrand(RequestBrandDTO requestBrandDTO);
    ResponseBrandDTO updateBrand(Long id, RequestBrandDTO updateBrandDto);
    ResponseBrandDTO restoreBrandById(Long id);
    ResponseEntity<String> deleteBrand(Long id);

}
