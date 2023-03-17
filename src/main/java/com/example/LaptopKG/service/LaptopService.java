package com.example.LaptopKG.service;

import com.example.LaptopKG.dto.laptop.CreateLaptopDto;
import com.example.LaptopKG.dto.laptop.GetLaptopDto;
import com.example.LaptopKG.dto.laptop.UpdateLaptopDto;
import com.example.LaptopKG.exception.LaptopNotFoundException;
import com.example.LaptopKG.model.Laptop;
import com.example.LaptopKG.model.enums.Status;
import com.example.LaptopKG.repository.LaptopRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LaptopService {

    private final LaptopRepository laptopRepository;

    public List<GetLaptopDto> getLaptops() {
        return laptopRepository.findAll().stream().filter(l -> l.getStatus().equals(Status.ACTIVE)).map(laptop ->
            new GetLaptopDto().laptopToDto(laptop)
        ).collect(Collectors.toList());

    }

    public GetLaptopDto getLaptopById(Long id) {
        Laptop laptop = laptopRepository.findById(id)
                .filter(l -> l.getStatus().equals(Status.ACTIVE))
                .orElseThrow( () ->
                new LaptopNotFoundException("Ноутбук с id " + id + " не найден"));
        return new GetLaptopDto().laptopToDto(laptop);
    }

    public String createLaptop(CreateLaptopDto createLaptopDto) {
        laptopRepository.save(createLaptopDto.toLaptop());
        return "Laptop is successfully created";
    }

    public String updateLaptop(Long id, UpdateLaptopDto updateLaptopDto) {
        Laptop laptop = updateLaptopDto.toLaptop();
        laptop.setId(id);
        laptopRepository.save(laptop);

        return "Laptop is successfully updated";
    }

    public String deleteLaptop(Long id) {
        Laptop laptop = laptopRepository.findById(id).filter(l -> l.getStatus().equals(Status.ACTIVE)).orElseThrow(
                () -> new LaptopNotFoundException("Ноутбук с id " + id + " не найден"));
        laptop.setStatus(Status.DELETED);
        laptopRepository.save(laptop);

        return "Laptop is successfully deleted";
    }
}
