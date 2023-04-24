package com.example.LaptopKG.service;

import com.example.LaptopKG.dto.hardware.GetHardwareDto;
import com.example.LaptopKG.dto.laptop.CreateLaptopDto;
import com.example.LaptopKG.dto.laptop.GetLaptopDto;
import com.example.LaptopKG.dto.laptop.UpdateLaptopDto;
import com.example.LaptopKG.exception.LaptopNotFoundException;
import com.example.LaptopKG.model.Hardware;
import com.example.LaptopKG.model.Laptop;
import com.example.LaptopKG.model.enums.Category;
import com.example.LaptopKG.model.enums.Guarantee;
import com.example.LaptopKG.model.enums.HardwareType;
import com.example.LaptopKG.model.enums.Status;
import com.example.LaptopKG.repository.BrandRepository;
import com.example.LaptopKG.repository.HardwareRepository;
import com.example.LaptopKG.repository.LaptopRepository;
import lombok.RequiredArgsConstructor;

import org.modelmapper.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.LaptopKG.dto.laptop.GetLaptopDto.toGetLaptopDto;

@Service
@RequiredArgsConstructor
public class LaptopService {

    private final LaptopRepository laptopRepository;
    private final ModelMapper mapper;
    private final BrandRepository brandRepository;
    private final HardwareRepository hardwareRepository;

    public List<GetLaptopDto> getLaptops() {
        return toGetLaptopDto(laptopRepository.findAll());
    }

    public Page<GetLaptopDto> getLaptops(Pageable pageable) {
        List<GetLaptopDto> laptops = toGetLaptopDto(laptopRepository.findAll());
        return new PageImpl<>(laptops, pageable, laptops.size());
    }

    public GetLaptopDto getLaptopById(Long id) {
        Laptop laptop = laptopRepository.findById(id)
                .filter(l -> l.getStatus().equals(Status.ACTIVE))
                .orElseThrow( () ->
                new LaptopNotFoundException("Ноутбук с id " + id + " не найден"));

        List<GetHardwareDto> hardwareList = laptop.getHardwareList().stream()
                .map(hardware -> mapper.map(hardware, GetHardwareDto.class)).toList();;

        GetLaptopDto getLaptopDto = GetLaptopDto.builder()
                .id(laptop.getId())
                .amount(laptop.getAmount())
                .brand(laptop.getBrand().getBrand())
                .category(laptop.getCategory().getCategory())
                .description(laptop.getDescription())
                .guarantee(laptop.getGuarantee().getGuarantee())
                .discount(laptop.getDiscount())
                .price(laptop.getPrice())
                .build();


        return getLaptopDto;
    }

    public String createLaptop(CreateLaptopDto createLaptopDto) {


//        Converter<CreateLaptopDto, Laptop> createLaptopDtoLaptopConverter = new Converter<CreateLaptopDto, Laptop>() {
//            @Override
//            public Laptop convert(MappingContext<CreateLaptopDto, Laptop> ctx) {
//                CreateLaptopDto dto = ctx.getSource();
//
//
//                List<Hardware> hardwareList = new ArrayList<>();
//                for (int i = 0; i < dto.getModelIds().size(); i++) {
//                    hardwareList.add(hardwareRepository.findById(dto.getModelIds().get(i)).orElse(null));
//                }
//
//                return Laptop.builder()
//                .model(hardwareList)
//                .description(dto.getDescription())
//                .price(dto.getPrice())
//                .amount(dto.getAmount())
//                .brand(brandRepository.findById(dto.getBrandId()).orElse(null))
//                .discount(dto.getDiscount())
//                .category(Category.valueOf(dto.getCategory()))
//                .status(Status.ACTIVE)
//                .build();
//            }
//        };
//
//        mapper.typeMap(CreateLaptopDto.class, Laptop.class)
//                .addMappings(mapper -> mapper.using(createLaptopDtoLaptopConverter)
//                        .map(CreateLaptopDto::getModelIds, Laptop::setModel)
//                        );
//        Laptop laptop = mapper.map(createLaptopDto, Laptop.class);

        // todo: probably repository has better function to find list of hardware by their ids
//        List<Hardware> hardwareList = new ArrayList<>();
//        List<HardwareType> hardwareTypes = new ArrayList<>(Arrays.stream(HardwareType.values()).toList());
//        for (int i = 0; i < createLaptopDto.getModelIds().size(); i++) {
//            Hardware hardware = hardwareRepository.findById(createLaptopDto.getModelIds().get(i)).orElse(null);
//
//            if(hardware == null){
//                continue;
//            }
//            if (hardwareTypes.contains(hardware.getHardwareType())) {
//                hardwareList.add(hardware);
//                hardwareTypes.remove(hardware.getHardwareType());
//            }else {
//                throw new IllegalArgumentException();
//            }
//        }

        Laptop laptop = Laptop.builder()
                .description(createLaptopDto.getDescription())
                .price(createLaptopDto.getPrice())
                .amount(createLaptopDto.getAmount())
                .brand(brandRepository.findById(createLaptopDto.getBrandId()).orElse(null))
                .discount(createLaptopDto.getDiscount())
                .category(Category.of(createLaptopDto.getCategory()))
                .guarantee(Guarantee.of(createLaptopDto.getGuarantee()))
                .status(Status.ACTIVE)
                .build();

        laptopRepository.save(laptop);
        return "Laptop is successfully created";
    }

    public String updateLaptop(Long id, UpdateLaptopDto updateLaptopDto) {

        // todo: optimize using mapper
        List<Hardware> hardwareList = new ArrayList<>();
        List<HardwareType> hardwareTypes = new ArrayList<>(Arrays.stream(HardwareType.values()).toList());
        for (int i = 0; i < updateLaptopDto.getModelIds().size(); i++) {
            Hardware hardware = hardwareRepository.findById(updateLaptopDto.getModelIds().get(i)).orElse(null);

            if(hardware == null){
                continue;
            }
            if (hardwareTypes.contains(hardware.getHardwareType())) {
                hardwareList.add(hardware);
                hardwareTypes.remove(hardware.getHardwareType());
            }else {
                throw new IllegalArgumentException();
            }
        }

        Laptop laptop = Laptop.builder()
                .hardwareList(hardwareList)
                .description(updateLaptopDto.getDescription())
                .price(updateLaptopDto.getPrice())
                .amount(updateLaptopDto.getAmount())
                .brand(brandRepository.findById(updateLaptopDto.getBrandId()).orElse(null))
                .discount(updateLaptopDto.getDiscount())
                .category(Category.of(updateLaptopDto.getCategory()))
                .guarantee(Guarantee.of(updateLaptopDto.getGuarantee()))
                .status(Status.of(updateLaptopDto.getStatus()))
                .build();
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
