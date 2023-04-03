package com.example.LaptopKG.controller;


import com.example.LaptopKG.service.EnumService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/types")
public class EnumController {

    private final EnumService enumService;

    @GetMapping("/{type}")
    public ResponseEntity<?> getCategories(@PathVariable String type){
        return ResponseEntity.ok(enumService.getListByType(type));
    }


}
