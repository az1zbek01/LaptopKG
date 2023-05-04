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

    // Get all active laptops
    public List<ResponseLaptopDTO> getAllLaptops() {
        // Return laptops mapping from entity to dto
        return toResponseLaptopDTO(
            // find all laptops from db
            laptopRepository.findAll()
                .stream()
                // filter laptops by status
                .filter(laptop -> laptop.getStatus() == Status.ACTIVE)
                .collect(Collectors.toList())
        );
    }

    // Get all active laptops with pagination
    public Page<ResponseLaptopDTO> getAllLaptops(Pageable pageable) {
        // Find all laptops from DB, map from entity to dto
        List<ResponseLaptopDTO> laptops = toResponseLaptopDTO(
            // find all laptops from db
            laptopRepository.findAll()
                .stream()
                // filter laptops by status
                .filter(laptop -> laptop.getStatus() == Status.ACTIVE)
                .collect(Collectors.toList())
        );

        // Return laptops as page with pagination
        return new PageImpl<>(laptops, pageable, laptops.size());
    }

    // Get laptop by id
    public ResponseLaptopDTO getLaptopById(Long id) {
        // Find laptop in DB by id
        Laptop laptop = laptopRepository.findById(id)
                // check if laptop is active
                .filter(l -> l.getStatus() == (Status.ACTIVE))
                // throw exception if laptop doesn't exist
                .orElseThrow(
                        () -> new LaptopNotFoundException("Ноутбук с id " + id + " не найден")
                );

        return toResponseLaptopDTO(laptop);
    }

    // Getting all deleted laptops
    public List<ResponseLaptopDTO> getAllDeletedLaptops() {
        // Find all deleted laptops, mapping them from Entity to DTO and return them
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

    // Laptop creation
    public ResponseLaptopDTO createLaptop(RequestLaptopDTO requestLaptopDTO) {
        // Get set of hardware by ids
        Set<Hardware> hardwareSet = new HashSet<>();
        for (long id: requestLaptopDTO.getHardwareIds()) {
            hardwareSet.add(hardwareRepository.findById(id)
                    // throw exception if hardware doesn't exist
                    .orElseThrow(() -> new NotFoundException("Железо с айди " + id + " не было найдено"))
            );
        }
        // Map from dto to entity
        Laptop laptop = Laptop.builder()
                .hardwareList(new ArrayList<>(hardwareSet))
                .description(requestLaptopDTO.getDescription())
                .price(requestLaptopDTO.getPrice())
                .amount(requestLaptopDTO.getAmount())
                .brand(brandRepository.findById(requestLaptopDTO.getBrandId())
                        // throw exception if brand doesn't exist
                        .orElseThrow(
                            () -> new NotFoundException("Brand with id " + requestLaptopDTO.getBrandId() + " wasn't found")
                        )
                )
                .name(requestLaptopDTO.getName())
                .category(Category.of(requestLaptopDTO.getCategory()))
                .guarantee(Guarantee.of(requestLaptopDTO.getGuarantee()))
                .status(Status.ACTIVE)
                .build();

        // Save new laptop
        laptopRepository.save(laptop);

        // Find all active users
        List<User> users = userRepository.findAll()
                .stream()
                .filter(user -> user.getStatus() == Status.ACTIVE)
                .toList();

        // Send notifications about new laptop to all users
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

    // Laptop updating
    public ResponseLaptopDTO updateLaptop(Long id, RequestLaptopDTO updateLaptopDto) {

        // Check if laptop exists by id
        if(!laptopRepository.existsById(id)){
            throw new LaptopNotFoundException("Laptop with id " + id + " wasn't found");
        }

        // Get list of hardware by ids
        Set<Hardware> hardwareSet = new HashSet<>();
        for (long hardId: updateLaptopDto.getHardwareIds()) {
            hardwareSet.add(hardwareRepository.findById(hardId)
                    // throw exception if hardware doesn't exist
                    .orElseThrow(() -> new NotFoundException("Железо с айди " + hardId + " не было найдено"))
            );
        }

        // Map from dto to entity
        Laptop laptop = Laptop.builder()
                .hardwareList(new ArrayList<>(hardwareSet))
                .description(updateLaptopDto.getDescription())
                .price(updateLaptopDto.getPrice())
                .amount(updateLaptopDto.getAmount())
                .brand(brandRepository.findById(updateLaptopDto.getBrandId())
                        // throw exception if brand doesn't exist
                        .orElseThrow(
                                () -> new NotFoundException("Brand with id " + updateLaptopDto.getBrandId() + " wasn't found")
                        )
                )
                .name(updateLaptopDto.getName())
                .category(Category.of(updateLaptopDto.getCategory()))
                .guarantee(Guarantee.of(updateLaptopDto.getGuarantee()))
                .build();

        // Save updated laptop and return laptop mapping it to dto
        laptop.setId(id);
        laptopRepository.save(laptop);

        return toResponseLaptopDTO(laptop);
    }

    // Restore deleted laptop
    public ResponseLaptopDTO restoreLaptopById(Long id){
        // Find brand by id or throw exception if already active or doesn't exist in DB
        Laptop laptop = laptopRepository.findById(id)
                .filter(b -> b.getStatus() == Status.DELETED)
                .orElseThrow(
                        () -> new AlreadyExistException("Laptop with id " + id + " already active")
                );

        // Make laptop active and save it
        laptop.setStatus(Status.ACTIVE);
        laptopRepository.save(laptop);

        // Return restored laptop
        return toResponseLaptopDTO(laptop);
    }

    // Laptop deleting
    public ResponseEntity<String> deleteLaptopById(Long id) {
        // Find laptop by id and check if it is active
        Laptop laptop = laptopRepository.findById(id).filter(l -> l.getStatus() == Status.ACTIVE)
                // throw exception if laptop wasn't found or is not active
                .orElseThrow(
                    () -> new LaptopNotFoundException("Ноутбук с id " + id + " не найден")
                );
        // Mark laptop as deleted and save it
        laptop.setStatus(Status.DELETED);
        laptopRepository.save(laptop);

        // Return status 200 and message
        return ResponseEntity.ok("Laptop was successfully deleted");
    }
}
