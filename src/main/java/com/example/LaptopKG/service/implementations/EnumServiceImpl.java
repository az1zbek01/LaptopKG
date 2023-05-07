package com.example.LaptopKG.service.implementations;


import com.example.LaptopKG.exception.NotFoundException;
import com.example.LaptopKG.model.enums.*;
import com.example.LaptopKG.service.EnumService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnumServiceImpl implements EnumService {

    public List<?> getListByType(String type) {

        switch (type) {
            case "category" -> {
                return getCategories();
            }
            case "delivery-type" -> {
                return getDeliveryTypes();
            }
            case "guarantee" -> {
                return getGuaranties();
            }
            case "hardware-type" -> {
                return getHardwareTypes();
            }
            case "order-status" -> {
                return getOrderStatuses();
            }
            case "payment-type" -> {
                return getPaymentTypes();
            }
            case "status" -> {
                return getStatuses();
            }
            default -> throw new NotFoundException("Такой тип не найден");
        }
    }


    public List<String> getCategories() {
        return Arrays.stream(Category.values()).map(Category::getCategory).collect(Collectors.toList());
    }

    public List<String> getDeliveryTypes() {
        return Arrays.stream(DeliveryType.values()).map(DeliveryType::getDeliveryType).collect(Collectors.toList());
    }

    public List<Integer> getGuaranties() {
        return Arrays.stream(Guarantee.values()).map(Guarantee::getGuarantee).collect(Collectors.toList());
    }

    public List<String> getHardwareTypes() {
        return Arrays.stream(HardwareType.values()).map(HardwareType::getHardwareType).collect(Collectors.toList());
    }

    public List<String> getOrderStatuses() {
        return Arrays.stream(OrderStatus.values()).map(OrderStatus::getOrderStatus).collect(Collectors.toList());
    }

    public List<String> getPaymentTypes() {
        return Arrays.stream(PaymentType.values()).map(PaymentType::getPaymentType).collect(Collectors.toList());
    }

    public List<String> getStatuses() {
        return Arrays.stream(Status.values()).map(Status::getStatus).collect(Collectors.toList());
    }


}
