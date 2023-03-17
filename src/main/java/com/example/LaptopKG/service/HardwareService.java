package com.example.LaptopKG.service;

import com.example.LaptopKG.dto.hardware.CreateHardwareDto;
import com.example.LaptopKG.dto.hardware.GetHardwareDto;
import com.example.LaptopKG.repository.HardwareRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HardwareService {

    private final HardwareRepository hardwareRepository;

    public String createHardware(CreateHardwareDto createHardwareDto) {
        hardwareRepository.save(createHardwareDto.toHardware());
        return "Hardware successfully created";
    }

    public List<GetHardwareDto> getAllHardware() {
        return hardwareRepository.findAll().stream().map(hardware ->
                new GetHardwareDto().hardwareToDto(hardware)).collect(Collectors.toList());
    }
}
