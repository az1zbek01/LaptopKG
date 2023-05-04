package com.example.LaptopKG.controller;


import com.example.LaptopKG.dto.hardware.RequestHardwareDTO;
import com.example.LaptopKG.service.HardwareService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hardware")
@CrossOrigin(origins = "*")
@Tag(
        name = "Контроллер для работы с железом",
        description = "В этом контроллеры есть возможности добавления и получения железа"
)
public class HardwareController {

    private final HardwareService hardwareService;

    @PostMapping()
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(
            summary = "Добавление железа"
    )
    public ResponseEntity<?> createHardware(@RequestBody @Valid RequestHardwareDTO requestHardwareDTO){
        return ResponseEntity.ok(hardwareService.createHardware(requestHardwareDTO));
    }

    @GetMapping
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(
            summary = "Получение всего железа"
    )
    public ResponseEntity<?> getAllHardware(){
        return ResponseEntity.ok(hardwareService.getAllHardware());
    }

}
