package com.example.LaptopKG.service;

import com.example.LaptopKG.dto.laptop.RequestLaptopDTO;
import com.example.LaptopKG.dto.laptop.ResponseLaptopDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface LaptopService {
    List<ResponseLaptopDTO> getAllLaptops();
    Page<ResponseLaptopDTO> getAllLaptops(Pageable pageable);
    ResponseLaptopDTO getLaptopById(Long id);
    List<ResponseLaptopDTO> getAllDeletedLaptops();
    List<ResponseLaptopDTO> getAllWithSearchByQuery(String query);
    ResponseLaptopDTO createLaptop(RequestLaptopDTO requestLaptopDTO);
    ResponseLaptopDTO updateLaptop(Long id, RequestLaptopDTO updateLaptopDto);
    ResponseLaptopDTO restoreLaptopById(Long id);
    ResponseEntity<String> deleteLaptopById(Long id);
    List<ResponseLaptopDTO> getRecommendedLaptops(Long laptopId);
}
