package com.example.LaptopKG.controller;

import com.example.LaptopKG.dto.order.RequestOrderDTO;
import com.example.LaptopKG.dto.order.ResponseOrderDTO;
import com.example.LaptopKG.model.User;
import com.example.LaptopKG.model.enums.OrderStatus;
import com.example.LaptopKG.service.implementations.OrderServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@AllArgsConstructor
@CrossOrigin(origins = "*")
@Tag(
        name = "Контроллер для работы с заказами",
        description = "В этом контроллере есть возможности добавления, получения, обновления и удаления заказов"
)
public class OrderController {
    private final OrderServiceImpl orderServiceImpl;

    @GetMapping()
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(
            summary = "Получение всех заказов"
    )
    public List<ResponseOrderDTO> getAllOrders(){
        return orderServiceImpl.getAllOrders();
    }

    @GetMapping("/deleted")
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(
            summary = "Получение всех удаленных заказов"
    )
    public List<ResponseOrderDTO> getAllDeletedOrders(){
        return orderServiceImpl.getAllDeletedOrders();
    }

    @GetMapping("/myOrders")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Получение всех заказов авторизованного пользователя"
    )
    public List<ResponseOrderDTO> getAllOrdersOfUser(@AuthenticationPrincipal User user){
        return orderServiceImpl.getAllOrdersOfUser(user);
    }

    @PostMapping()
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Добавление заказа"
    )
    public ResponseOrderDTO addOrder(@RequestBody RequestOrderDTO requestOrderDTO,
                                     @AuthenticationPrincipal User user){
        return orderServiceImpl.addOrder(requestOrderDTO, user);
    }

    @PutMapping("/changeStatus/{orderId}")
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(
            summary = "Изменить статус заказа"
    )
    public ResponseEntity<String> changeOrderStatus(@PathVariable("orderId") Long id,
                                                    @RequestParam OrderStatus orderStatus,
                                                    @RequestParam(required = false) String message){
        return orderServiceImpl.changeOrderStatus(id, orderStatus, message);
    }

    @PutMapping("/cancelOrder/{orderId}")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Отменить заказ"
    )
    public ResponseEntity<String> cancelOrder(@PathVariable("orderId") Long id,
                                              @AuthenticationPrincipal User user){
        return orderServiceImpl.cancelOrder(id, user);
    }

    @DeleteMapping("/{orderId}")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Удалить заказ"
    )
    public ResponseEntity<String> deleteOrder(@PathVariable("orderId") Long id,
                                              @AuthenticationPrincipal User user){
        return orderServiceImpl.deleteOrder(id, user);
    }
}
