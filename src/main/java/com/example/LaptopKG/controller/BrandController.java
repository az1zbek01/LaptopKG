package com.example.LaptopKG.controller;


import com.example.LaptopKG.dto.brand.CreateAndUpdateBrandDto;
import com.example.LaptopKG.dto.brand.GetBrandDto;
import com.example.LaptopKG.service.BrandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brands")
@RequiredArgsConstructor
@Tag(
    name = "Контроллер для работы с брендами",
    description = "В этом контроллеры есть возможности добавления, получения и обновления брендов"
)
public class BrandController {
    private final BrandService brandService;

    @GetMapping()
    @Operation(
            summary = "Получение всех брендов"
    )
    public List<GetBrandDto> getAllBrands(){
        return brandService.getAll();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Получение бренда по айди"
    )
    public GetBrandDto getBrandById(@PathVariable Long id){
        return brandService.getById(id);
    }

    @GetMapping("/deleted")
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(
            summary = "Получение всех удаленных брендов"
    )
    public List<GetBrandDto> getAllDeletedBrands(){
        return brandService.getAllDeletedBrands();
    }

    @PostMapping("/create")
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(
            summary = "Добавление бренда"
    )
    public GetBrandDto createBrand(@RequestBody CreateAndUpdateBrandDto createBrandDto){
        return brandService.createBrand(createBrandDto);
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(
            summary = "Обновление бренда по айди"
    )
    public GetBrandDto updateBrand(@PathVariable Long id, @RequestBody CreateAndUpdateBrandDto updateBrandDto){
        return brandService.updateBrand(id, updateBrandDto);
    }

    @PutMapping("/restore/{id}")
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(
            summary = "Восстановление бренда по айди"
    )
    public GetBrandDto restoreBrandById(@PathVariable Long id) {
        return brandService.restoreBrandById(id);
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(
            summary = "Удаление бренда"
    )
    public ResponseEntity<String> deleteBrand(@PathVariable Long id){
        return brandService.deleteBrand(id);
    }
}
