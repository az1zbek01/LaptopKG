package com.example.LaptopKG.controller;


import com.example.LaptopKG.dto.laptop.CreateLaptopDto;
import com.example.LaptopKG.dto.laptop.GetLaptopDto;
import com.example.LaptopKG.dto.laptop.UpdateLaptopDto;
import com.example.LaptopKG.service.LaptopService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


//TODO: Create getall getById //create update delete
@RestController
@RequestMapping("/api/laptops")
@AllArgsConstructor
@Tag(
    name = "Контроллер для работы с ноутбуками",
    description = "В этом контроллеры есть возможности добавления, получения, обновления и удаления ноутбуков"
)
public class LaptopController {

    private final LaptopService laptopService;

    @GetMapping
    @Operation(
            summary = "Получение всех ноутбуков"
    )
    public List<GetLaptopDto> getAll(){
        return laptopService.getLaptops();
    }

    @GetMapping("/byPages")
    @Operation(
            summary = "Получение всех ноутбуков с пагинацией"
    )
    public Page<GetLaptopDto> getAllWithPagination(@PageableDefault Pageable pageable){
        return laptopService.getLaptops(pageable);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Получение ноутбука по айди"
    )
    public ResponseEntity<GetLaptopDto> getById(@PathVariable Long id){
        return ResponseEntity.ok(laptopService.getLaptopById(id));
    }

    @PostMapping("/create")
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(
            summary = "Добавление ноутбука"
    )
    public ResponseEntity<?> createLaptop(@RequestBody CreateLaptopDto createLaptopDto){
        return ResponseEntity.ok(laptopService.createLaptop(createLaptopDto));
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(
            summary = "Обновление ноутбука"
    )
    public ResponseEntity<?> updateLaptop(@PathVariable Long id, @RequestBody UpdateLaptopDto updateLaptopDto){
        return ResponseEntity.ok(laptopService.updateLaptop(id, updateLaptopDto));
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(
            summary = "Удаление ноутбука"
    )
    public ResponseEntity<?> deleteLaptop(@PathVariable Long id){
        return ResponseEntity.ok(laptopService.deleteLaptop(id));
    }

}
