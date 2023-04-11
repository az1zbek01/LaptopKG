package com.example.LaptopKG.service;

import com.example.LaptopKG.dto.hardware.CreateHardwareDto;
import com.example.LaptopKG.dto.hardware.GetHardwareDto;
import com.example.LaptopKG.model.Hardware;
import com.example.LaptopKG.model.enums.HardwareType;
import com.example.LaptopKG.repository.HardwareRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HardwareService {

    private final HardwareRepository hardwareRepository;
    private final ModelMapper mapper;

    public GetHardwareDto createHardware(CreateHardwareDto createHardwareDto) {

        Hardware hardware = mapper.map(createHardwareDto, Hardware.class);

        hardwareRepository.save(hardware);
        return mapper.map(hardware, GetHardwareDto.class);
    }

    public List<GetHardwareDto> getAllHardware() {


        return hardwareRepository.findAll().stream().map(
                hardware ->
                mapper.map(hardware, GetHardwareDto.class)


        ).collect(Collectors.toList());
    }
}
