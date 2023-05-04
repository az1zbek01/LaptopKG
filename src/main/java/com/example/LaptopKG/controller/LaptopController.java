package com.example.LaptopKG.controller;


import com.example.LaptopKG.dto.laptop.RequestLaptopDTO;
import com.example.LaptopKG.dto.laptop.ResponseLaptopDTO;
import com.example.LaptopKG.dto.review.ResponseReviewDTO;
import com.example.LaptopKG.service.implementations.LaptopServiceImpl;
import com.example.LaptopKG.service.implementations.ReviewServiceImpl;
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
@CrossOrigin(origins = "*")
@Tag(
    name = "Контроллер для работы с ноутбуками",
    description = "В этом контроллеры есть возможности добавления, получения, обновления и удаления ноутбуков"
)
public class LaptopController {
    private final LaptopServiceImpl laptopServiceImpl;
    private final ReviewServiceImpl reviewServiceImpl;

    @GetMapping
    @Operation(
            summary = "Получение всех ноутбуков"
    )
    public List<ResponseLaptopDTO> getAllLaptops(){
        return laptopServiceImpl.getAllLaptops();
    }

    @GetMapping("/byPages")
    @Operation(
            summary = "Получение всех ноутбуков с пагинацией"
    )
    public Page<ResponseLaptopDTO> getAllWithPagination(@PageableDefault Pageable pageable){
        return laptopServiceImpl.getAllLaptops(pageable);
    }

    @GetMapping("/search")
    @Operation(
            summary = "Поиск ноутбуков по названию и описанию"
    )
    public List<ResponseLaptopDTO> getAllWithSearchByQuery(@RequestParam(required = false) String query){
        return laptopServiceImpl.getAllWithSearchByQuery(query);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Получение ноутбука по айди"
    )
    public ResponseLaptopDTO getById(@PathVariable Long id){
        return laptopServiceImpl.getLaptopById(id);
    }

    @GetMapping("/{id}/reviews")
    @Operation(
            summary = "Получение всех отзывов на ноутбук"
    )
    public List<ResponseReviewDTO> getReviewsByLaptopId(@PathVariable Long id){
        return reviewServiceImpl.getReviewsByLaptopId(id);
    }

    @GetMapping("/deleted")
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(
            summary = "Получение всех удаленных ноутбуков"
    )
    public List<ResponseLaptopDTO> getAllDeletedLaptops(){
        return laptopServiceImpl.getAllDeletedLaptops();
    }

    @PostMapping("/create")
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(
            summary = "Добавление ноутбука"
    )
    public ResponseLaptopDTO createLaptop(@RequestBody RequestLaptopDTO requestLaptopDTO){
        return laptopServiceImpl.createLaptop(requestLaptopDTO);
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(
            summary = "Обновление ноутбука по айди"
    )
    public ResponseLaptopDTO updateLaptop(@PathVariable Long id,
                                          @RequestBody RequestLaptopDTO updateLaptopDto){
        return laptopServiceImpl.updateLaptop(id, updateLaptopDto);
    }

    @PutMapping("/restore/{id}")
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(
            summary = "Восстановление ноутбука по айди"
    )
    public ResponseLaptopDTO restoreLaptopById(@PathVariable Long id){
        return laptopServiceImpl.restoreLaptopById(id);
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(
            summary = "Удаление ноутбука"
    )
    public ResponseEntity<String> deleteLaptopById(@PathVariable Long id){
        return laptopServiceImpl.deleteLaptopById(id);
    }

}
