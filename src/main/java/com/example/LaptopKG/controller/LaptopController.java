package com.example.LaptopKG.controller;


import com.example.LaptopKG.dto.laptop.CreateLaptopDto;
import com.example.LaptopKG.dto.laptop.GetLaptopDto;
import com.example.LaptopKG.dto.laptop.UpdateLaptopDto;
import com.example.LaptopKG.dto.review.GetReviewDto;
import com.example.LaptopKG.service.LaptopService;
import com.example.LaptopKG.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/laptops")
@AllArgsConstructor
@Tag(
    name = "Контроллер для работы с ноутбуками",
    description = "В этом контроллеры есть возможности добавления, получения, обновления и удаления ноутбуков"
)
public class LaptopController {
    private final LaptopService laptopService;
    private final ReviewService reviewService;

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
    public GetLaptopDto getById(@PathVariable Long id){
        return laptopService.getLaptopById(id);
    }

    @GetMapping("/{id}/reviews")
    @Operation(
            summary = "Получение всех отзывов на ноутбук"
    )
    public List<GetReviewDto> getReviewsByLaptopId(@PathVariable long id){
        return reviewService.getReviewsByLaptopId(id);
    }

    @PostMapping("/create")
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(
            summary = "Добавление ноутбука"
    )
    public GetLaptopDto createLaptop(@RequestBody CreateLaptopDto createLaptopDto){
        return laptopService.createLaptop(createLaptopDto);
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(
            summary = "Обновление ноутбука"
    )
    public ResponseEntity<String> updateLaptop(@PathVariable Long id,
                                          @RequestBody UpdateLaptopDto updateLaptopDto){
        return laptopService.updateLaptop(id, updateLaptopDto);
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(
            summary = "Удаление ноутбука"
    )
    public ResponseEntity<String> deleteLaptop(@PathVariable Long id){
        return laptopService.deleteLaptop(id);
    }

}
