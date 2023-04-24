package com.example.LaptopKG.controller;


import com.example.LaptopKG.service.EnumService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/types")
@Tag(
        name = "Контроллер для работы с enums",
        description = "В этом контроллеры есть возможности получения всех enum'ов"
)
public class EnumController {
    private final EnumService enumService;

    @GetMapping("/{type}")
    @Operation(
            summary = "Получение enum'ов по типу"
    )
    public ResponseEntity<?> getByType(@PathVariable
                                       @Parameter(description = "(category, delivery-type, guarantee, " +
                                               "hardware-type, order-status, payment-type, status)")
                                           String type){
        return ResponseEntity.ok(enumService.getListByType(type));
    }


}
