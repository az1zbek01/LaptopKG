package com.example.LaptopKG.controller;


import com.example.LaptopKG.dto.brand.CreateAndUpdateBrandDto;
import com.example.LaptopKG.service.BrandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/brands")
@RequiredArgsConstructor
@Tag(
    name = "Контроллер для работы с брендами",
    description = "В этом контроллеры есть возможности добавления, получения и обновления брендов"
)
public class BrandController {

    private final BrandService brandService;

    @GetMapping("")
    @Operation(
            summary = "Получение всех брендов"
    )
    public ResponseEntity<?> getAllBrands(){
        return ResponseEntity.ok(brandService.getAll());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Получение бренда по айди"
    )
    public ResponseEntity<?> getBrandById(@PathVariable Long id){
        return ResponseEntity.ok(brandService.getById(id));
    }

    @PostMapping("/create")
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(
            summary = "Добавление бренда"
    )
    public ResponseEntity<?> createBrand(@RequestBody CreateAndUpdateBrandDto createBrandDto){
        return ResponseEntity.ok(brandService.createBrand(createBrandDto));
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(
            summary = "Обновление бренда"
    )
    public ResponseEntity<?> updateBrand(@PathVariable Long id, @RequestBody CreateAndUpdateBrandDto updateBrandDto){
        return ResponseEntity.ok(brandService.updateBrand(id, updateBrandDto));
    }
}
