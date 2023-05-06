package com.example.LaptopKG.service.implementations;

import com.example.LaptopKG.dto.laptop.RequestLaptopDTO;
import com.example.LaptopKG.dto.laptop.ResponseLaptopDTO;
import com.example.LaptopKG.exception.AlreadyExistException;
import com.example.LaptopKG.exception.LaptopNotFoundException;
import com.example.LaptopKG.exception.NotFoundException;
import com.example.LaptopKG.model.*;
import com.example.LaptopKG.model.enums.Category;
import com.example.LaptopKG.model.enums.Guarantee;
import com.example.LaptopKG.model.enums.Status;
import com.example.LaptopKG.repository.*;
import com.example.LaptopKG.service.LaptopService;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.example.LaptopKG.dto.laptop.ResponseLaptopDTO.toResponseLaptopDTO;

@Service
@AllArgsConstructor
public class LaptopServiceImpl implements LaptopService {
    private final LaptopRepository laptopRepository;
    private final BrandRepository brandRepository;
    private final HardwareRepository hardwareRepository;
    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;
    private final EntityManager entityManager;

    public List<ResponseLaptopDTO> getAllLaptops() {
        return toResponseLaptopDTO(
            laptopRepository.findAll()
                .stream()
                .filter(laptop -> laptop.getStatus() == Status.ACTIVE)
                .collect(Collectors.toList())
        );
    }

    public Page<ResponseLaptopDTO> getAllLaptops(Pageable pageable) {
        List<ResponseLaptopDTO> laptops = toResponseLaptopDTO(
            laptopRepository.findAll()
                .stream()
                .filter(laptop -> laptop.getStatus() == Status.ACTIVE)
                .collect(Collectors.toList())
        );

        return new PageImpl<>(laptops, pageable, laptops.size());
    }

    public ResponseLaptopDTO getLaptopById(Long id) {
        Laptop laptop = laptopRepository.findById(id)
                .filter(l -> l.getStatus() == (Status.ACTIVE))
                .orElseThrow(
                        () -> new LaptopNotFoundException("Ноутбук с id " + id + " не найден")
                );

        return toResponseLaptopDTO(laptop);
    }

    public List<ResponseLaptopDTO> getAllDeletedLaptops() {
        return toResponseLaptopDTO(laptopRepository.findAll()
                .stream()
                .filter(brand -> brand.getStatus() == Status.DELETED)
                .collect(Collectors.toList())
        );
    }

    public List<ResponseLaptopDTO> getAllWithSearchByQuery(String query){
        if(query != null)
            return toResponseLaptopDTO(laptopRepository.findAllByNameContainsIgnoreCaseOrDescriptionContainsIgnoreCase(query, query));

        return toResponseLaptopDTO(laptopRepository.findAll());
    }

    public ResponseLaptopDTO createLaptop(RequestLaptopDTO requestLaptopDTO) {
        Set<Hardware> hardwareSet = new HashSet<>();
        for (long id: requestLaptopDTO.getHardwareIds()) {
            hardwareSet.add(hardwareRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Железо с айди " + id + " не было найдено"))
            );
        }
        Laptop laptop = Laptop.builder()
                .hardwareList(new ArrayList<>(hardwareSet))
                .description(requestLaptopDTO.getDescription())
                .price(requestLaptopDTO.getPrice())
                .amount(requestLaptopDTO.getAmount())
                .brand(brandRepository.findById(requestLaptopDTO.getBrandId())
                        .orElseThrow(
                            () -> new NotFoundException("Brand with id " + requestLaptopDTO.getBrandId() + " wasn't found")
                        )
                )
                .name(requestLaptopDTO.getName())
                .category(Category.of(requestLaptopDTO.getCategory()))
                .guarantee(Guarantee.of(requestLaptopDTO.getGuarantee()))
                .status(Status.ACTIVE)
                .build();

        laptopRepository.save(laptop);

        List<User> users = userRepository.findAll()
                .stream()
                .filter(user -> user.getStatus() == Status.ACTIVE)
                .toList();

        for(User user: users){
            Notification notification = new Notification();
            notification.setUser(user);
            notification.setHeader("Добавлен новый ноутбук!");
            notification.setMessage("В наш магазин был добавлен ноутбук бренда " + laptop.getBrand().getName() +
                    "! Можете посмотреть более подробную информацию в списке ноутбуков.");
            notification.setStatus(Status.ACTIVE);
            notificationRepository.save(notification);
        }

        return toResponseLaptopDTO(laptop);
    }

    public ResponseLaptopDTO updateLaptop(Long id, RequestLaptopDTO updateLaptopDto) {

        if(!laptopRepository.existsById(id)){
            throw new LaptopNotFoundException("Laptop with id " + id + " wasn't found");
        }

        Set<Hardware> hardwareSet = new HashSet<>();
        for (long hardId: updateLaptopDto.getHardwareIds()) {
            hardwareSet.add(hardwareRepository.findById(hardId)
                    .orElseThrow(() -> new NotFoundException("Железо с айди " + hardId + " не было найдено"))
            );
        }

        Laptop laptop = Laptop.builder()
                .hardwareList(new ArrayList<>(hardwareSet))
                .description(updateLaptopDto.getDescription())
                .price(updateLaptopDto.getPrice())
                .amount(updateLaptopDto.getAmount())
                .brand(brandRepository.findById(updateLaptopDto.getBrandId())
                        .orElseThrow(
                                () -> new NotFoundException("Brand with id " + updateLaptopDto.getBrandId() + " wasn't found")
                        )
                )
                .name(updateLaptopDto.getName())
                .category(Category.of(updateLaptopDto.getCategory()))
                .guarantee(Guarantee.of(updateLaptopDto.getGuarantee()))
                .build();

        laptop.setId(id);
        laptopRepository.save(laptop);

        return toResponseLaptopDTO(laptop);
    }

    public ResponseLaptopDTO restoreLaptopById(Long id){
        Laptop laptop = laptopRepository.findById(id)
                .filter(b -> b.getStatus() == Status.DELETED)
                .orElseThrow(
                        () -> new AlreadyExistException("Laptop with id " + id + " already active")
                );

        laptop.setStatus(Status.ACTIVE);
        laptopRepository.save(laptop);

        return toResponseLaptopDTO(laptop);
    }

    public ResponseEntity<String> deleteLaptopById(Long id) {
        Laptop laptop = laptopRepository.findById(id).filter(l -> l.getStatus() == Status.ACTIVE)
                .orElseThrow(
                    () -> new LaptopNotFoundException("Ноутбук с id " + id + " не найден")
                );
        laptop.setStatus(Status.DELETED);
        laptopRepository.save(laptop);

        return ResponseEntity.ok("Laptop was successfully deleted");
    }
}
