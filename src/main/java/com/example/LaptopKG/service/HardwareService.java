package com.example.LaptopKG.service;

import com.example.LaptopKG.dto.hardware.RequestHardwareDTO;
import com.example.LaptopKG.dto.hardware.ResponseHardwareDTO;

import java.util.List;

public interface HardwareService {
    ResponseHardwareDTO createHardware(RequestHardwareDTO requestHardwareDTO);
    List<ResponseHardwareDTO> getAllHardware();
}
