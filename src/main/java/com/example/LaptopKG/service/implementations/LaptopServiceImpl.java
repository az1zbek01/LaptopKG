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

    public List<ResponseLaptopDTO> getAllLaptops() {
        return toResponseLaptopDTO(findAllActiveLaptops());
    }

    public Page<ResponseLaptopDTO> getAllLaptops(Pageable pageable) {
        Page<Laptop> laptops1 = laptopRepository.findAllByStatus(Status.ACTIVE, pageable);
        long totalSize = laptops1.getTotalElements();
        List<ResponseLaptopDTO> laptopDTOS = toResponseLaptopDTO(laptops1.toList());
        return new PageImpl<>(laptopDTOS, pageable, totalSize);
    }

    public ResponseLaptopDTO getLaptopById(Long id) {
        Laptop laptop = findLaptopById(id);
        return toResponseLaptopDTO(laptop);
    }

    public List<ResponseLaptopDTO> getAllDeletedLaptops() {
        return toResponseLaptopDTO(laptopRepository.findAll()
                .stream()
                .filter(laptop -> laptop.getStatus() == Status.DELETED)
                .collect(Collectors.toList())
        );
    }

    public List<ResponseLaptopDTO> getAllWithSearchByQuery(String query) {
        if (query != null) {
            return toResponseLaptopDTO(laptopRepository.findAllByNameContainsIgnoreCaseOrDescriptionContainsIgnoreCase(query, query));
        }

        return toResponseLaptopDTO(laptopRepository.findAll());
    }

    public ResponseLaptopDTO createLaptop(RequestLaptopDTO requestLaptopDTO) {
        Set<Hardware> hardwareSet = constructHarwareSet(requestLaptopDTO);

        Laptop laptop = convertToLaptop(requestLaptopDTO, hardwareSet);
        laptopRepository.save(laptop);

        List<User> users = userRepository.findAll()
                .stream()
                .filter(user -> user.getStatus() == Status.ACTIVE)
                .toList();

        sendNotificationsToAllUsers(users, laptop.getBrand().getName());

        return toResponseLaptopDTO(laptop);
    }

    public ResponseLaptopDTO updateLaptop(Long id, RequestLaptopDTO updateLaptopDto) {
        if (!laptopRepository.existsById(id)) {
            throw new LaptopNotFoundException("Ноутбук с айди " + id + " не найден");
        }

        Set<Hardware> hardwareSet = constructHarwareSet(updateLaptopDto);
        Laptop laptopWithImage = laptopRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Ноутбук с айди " + id + " не найден")
        );
        Laptop laptop = convertToLaptop(updateLaptopDto, hardwareSet);
        laptop.setId(id);
        laptop.setAverageScore(laptopWithImage.getAverageScore());
        laptop.setImageUrl(laptopWithImage.getImageUrl());
        laptopRepository.save(laptop);

        return toResponseLaptopDTO(laptop);
    }

    public ResponseLaptopDTO restoreLaptopById(Long id) {
        Laptop laptop = laptopRepository.findById(id)
                .filter(l -> l.getStatus() == Status.DELETED)
                .orElseThrow(
                        () -> new AlreadyExistException("Ноутбук с айди " + id + " уже активен")
                );


        laptop.setStatus(Status.ACTIVE);
        laptopRepository.save(laptop);

        return toResponseLaptopDTO(laptop);
    }

    public ResponseEntity<String> deleteLaptopById(Long id) {
        Laptop laptop = findLaptopById(id);

        laptop.setStatus(Status.DELETED);
        laptopRepository.save(laptop);

        return ResponseEntity.ok("Ноутбук успешно удален");
    }

    @Override
    public List<ResponseLaptopDTO> getRecommendedLaptops(Long laptopId) {
        Laptop laptop = findLaptopById(laptopId);

        List<Laptop> laptops = findAllActiveLaptops()
                .stream()
                .filter(l -> l!= laptop)
                .filter(l -> l.getBrand().equals(laptop.getBrand()))
                .toList();

        return toResponseLaptopDTO(laptops);
    }

    private List<Laptop> findAllActiveLaptops() {
        return laptopRepository.findAll()
                .stream()
                .filter(laptop -> laptop.getStatus() == Status.ACTIVE)
                .collect(Collectors.toList());
    }

    private Laptop findLaptopById(Long id) {
        return laptopRepository.findById(id)
                .filter(l -> l.getStatus() == (Status.ACTIVE))
                .orElseThrow(
                        () -> new LaptopNotFoundException("Ноутбук с id " + id + " не найден")
                );
    }

    private void sendNotificationsToAllUsers(List<User> users, String brand) {
        for (User user : users) {
            Notification notification = new Notification();
            notification.setUser(user);
            notification.setHeader("Добавлен новый ноутбук!");
            notification.setMessage("В наш магазин был добавлен ноутбук бренда " + brand +
                    "! Можете посмотреть более подробную информацию в списке ноутбуков.");
            notification.setStatus(Status.ACTIVE);
            notificationRepository.save(notification);
        }
    }

    private Set<Hardware> constructHarwareSet(RequestLaptopDTO requestLaptopDTO) {
        Set<Hardware> hardwareSet = new HashSet<>();
        for (long hardId : requestLaptopDTO.getHardwareIds()) {
            hardwareSet.add(hardwareRepository.findById(hardId)
                    .orElseThrow(() -> new NotFoundException("Железо с айди " + hardId + " не было найдено"))
            );
        }
        return hardwareSet;
    }

    private Laptop convertToLaptop(RequestLaptopDTO requestLaptopDTO, Set<Hardware> hardwareSet) {
        return Laptop.builder()
                .hardwareList(new ArrayList<>(hardwareSet))
                .description(requestLaptopDTO.getDescription())
                .price(requestLaptopDTO.getPrice())
                .amount(requestLaptopDTO.getAmount())
                .brand(brandRepository.findById(requestLaptopDTO.getBrandId())
                        .orElseThrow(
                                () -> new NotFoundException("Бренд с айди " + requestLaptopDTO.getBrandId() + " не найден")
                        )
                )
                .name(requestLaptopDTO.getName())
                .category(Category.of(requestLaptopDTO.getCategory()))
                .guarantee(Guarantee.of(requestLaptopDTO.getGuarantee()))
                .status(Status.ACTIVE)
                .build();
    }
}
