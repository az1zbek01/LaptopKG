package com.example.LaptopKG.service;

import java.util.List;

public interface EnumService {
    List<?> getListByType(String type);
    List<String> getCategories();
    List<String> getDeliveryTypes();
    List<Integer> getGuaranties();
    List<String> getHardwareTypes();
    List<String> getOrderStatuses();
    List<String> getPaymentTypes();
    List<String> getStatuses();
}
