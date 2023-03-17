package com.example.LaptopKG.controller;


import com.example.LaptopKG.dto.brand.CreateBrandDto;
import com.example.LaptopKG.dto.brand.UpdateBrandDto;
import com.example.LaptopKG.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/brand")
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;

    @GetMapping("/")
    public ResponseEntity<?> getAllBrands(){
        return ResponseEntity.ok(brandService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBrandById(@PathVariable Long id){
        return ResponseEntity.ok(brandService.getById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createBrand(@RequestBody CreateBrandDto createBrandDto){
        return ResponseEntity.ok(brandService.createBrand(createBrandDto));
    }

    @PostMapping("/{id}/update")
    public ResponseEntity<?> updateBrand(@PathVariable Long id, @RequestBody UpdateBrandDto updateBrandDto){
        return ResponseEntity.ok(brandService.updateBrand(id, updateBrandDto));
    }


}
