package com.example.LaptopKG.controller;


import com.example.LaptopKG.dto.laptop.CreateLaptopDto;
import com.example.LaptopKG.dto.laptop.GetLaptopDto;
import com.example.LaptopKG.dto.laptop.UpdateLaptopDto;
import com.example.LaptopKG.service.LaptopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


//TODO: Create getall getById //create update delete
@RestController
@RequestMapping("/api/laptops")
@RequiredArgsConstructor
public class LaptopController {

    private final LaptopService laptopService;

    @GetMapping
    public List<GetLaptopDto> getAll(){

        return laptopService.getLaptops();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetLaptopDto> getById(@PathVariable Long id){
        return ResponseEntity.ok(laptopService.getLaptopById(id));
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> createLaptop(@RequestBody CreateLaptopDto createLaptopDto){
        return ResponseEntity.ok(laptopService.createLaptop(createLaptopDto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateLaptop(@PathVariable Long id, @RequestBody UpdateLaptopDto updateLaptopDto){
        return ResponseEntity.ok(laptopService.updateLaptop(id, updateLaptopDto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteLaptop(@PathVariable Long id){
        return ResponseEntity.ok(laptopService.deleteLaptop(id));
    }

}
