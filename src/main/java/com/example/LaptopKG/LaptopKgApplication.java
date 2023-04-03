package com.example.LaptopKG;

import com.example.LaptopKG.dto.hardware.CreateHardwareDto;
import com.example.LaptopKG.dto.hardware.GetHardwareDto;
import com.example.LaptopKG.model.Hardware;
import com.example.LaptopKG.model.enums.HardwareType;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LaptopKgApplication {

	public static void main(String[] args) {
		SpringApplication.run(LaptopKgApplication.class, args);
	}

}
