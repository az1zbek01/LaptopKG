package com.example.LaptopKG.controller;


import com.example.LaptopKG.service.implementations.EnumServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/types")
@CrossOrigin(origins = "*")
@Tag(
        name = "Контроллер для работы с enums",
        description = "В этом контроллеры есть возможности получения всех enum'ов"
)
public class EnumController {
    private final EnumServiceImpl enumServiceImpl;

    @GetMapping("/{type}")
    @Operation(
            summary = "Получение enum'ов по типу"
    )
    public ResponseEntity<?> getByType(@PathVariable
                                       @Parameter(description = "(category, delivery-type, guarantee, " +
                                               "hardware-type, order-status, payment-type, status)")
                                           String type){
        return ResponseEntity.ok(enumServiceImpl.getListByType(type));
    }


}
