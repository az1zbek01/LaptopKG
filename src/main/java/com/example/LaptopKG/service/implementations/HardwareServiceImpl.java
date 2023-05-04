package com.example.LaptopKG.service.implementations;

import com.example.LaptopKG.dto.hardware.RequestHardwareDTO;
import com.example.LaptopKG.dto.hardware.ResponseHardwareDTO;
import com.example.LaptopKG.model.Hardware;
import com.example.LaptopKG.repository.HardwareRepository;
import com.example.LaptopKG.service.HardwareService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HardwareServiceImpl implements HardwareService {
    private final HardwareRepository hardwareRepository;
    private final ModelMapper mapper;

    public ResponseHardwareDTO createHardware(RequestHardwareDTO requestHardwareDTO) {

        Hardware hardware = mapper.map(requestHardwareDTO, Hardware.class);

        hardwareRepository.save(hardware);
        return mapper.map(hardware, ResponseHardwareDTO.class);
    }

    public List<ResponseHardwareDTO> getAllHardware() {


        return hardwareRepository.findAll().stream().map(
                hardware ->
                mapper.map(hardware, ResponseHardwareDTO.class)


        ).collect(Collectors.toList());
    }
}
