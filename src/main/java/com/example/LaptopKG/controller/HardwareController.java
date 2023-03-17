package com.example.LaptopKG.controller;


import com.example.LaptopKG.dto.hardware.CreateHardwareDto;
import com.example.LaptopKG.service.HardwareService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hardware")
public class HardwareController {

    private final HardwareService hardwareService;

    @PostMapping("/create")
    public ResponseEntity<?> createHardware(@RequestBody @Valid CreateHardwareDto createHardwareDto){
        return ResponseEntity.ok(hardwareService.createHardware(createHardwareDto));
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllHardware(){
        return ResponseEntity.ok(hardwareService.getAllHardware());
    }

}
