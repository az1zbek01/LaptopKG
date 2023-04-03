package com.example.LaptopKG.controller;


import com.example.LaptopKG.dto.hardware.CreateHardwareDto;
import com.example.LaptopKG.dto.hardware.HardwareNameDto;
import com.example.LaptopKG.model.enums.HardwareType;
import com.example.LaptopKG.service.HardwareService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hardware")
public class HardwareController {

    private final HardwareService hardwareService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> createHardware(@RequestBody @Valid CreateHardwareDto createHardwareDto){
        return ResponseEntity.ok(hardwareService.createHardware(createHardwareDto));
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getAllHardware(){
        return ResponseEntity.ok(hardwareService.getAllHardware());
    }


    //fixME delete this shit not fix
    @PostMapping("/createWithName")
    public ResponseEntity<?> createWithNameTest(@RequestBody @Valid HardwareNameDto hardwareNameDto){
        System.out.println(HardwareType.DIAGONAL.getHardwareType());
        CreateHardwareDto hardwareDto = CreateHardwareDto.builder()
                .name(hardwareNameDto.getName())
                .hardwareType(HardwareType.DIAGONAL.getHardwareType())
                .build();
        return ResponseEntity.ok(hardwareService.createHardware(hardwareDto));
    }

}
