package com.ozyegin.csc393.car_rental_project.service;

import com.ozyegin.csc393.car_rental_project.domain.Location;
import com.ozyegin.csc393.car_rental_project.dto.LocationDTO;
import com.ozyegin.csc393.car_rental_project.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
//@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public LocationDTO createLocation(LocationDTO dto) {
        Location location = mapToEntity(dto);
        Location saved = locationRepository.save(location);
        return mapToDTO(saved);
    }

    public List<LocationDTO> getAllLocations() {
        return locationRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public LocationDTO getLocationById(Long id) {
        Location location = locationRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Location not found with id: " + id));
        return mapToDTO(location);
    }

    @Transactional
    public LocationDTO updateLocation(Long id, LocationDTO dto) {
        Location existing = locationRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Location not found"));

        existing.setCode(dto.getCode());
        existing.setName(dto.getName());

        Location saved = locationRepository.save(existing);
        return mapToDTO(saved);
    }

    public void deleteLocationById(Long id) {
        locationRepository.deleteById(id);
    }


    private LocationDTO mapToDTO(Location entity) {
        LocationDTO dto = new LocationDTO();
        dto.setId(entity.getId());
        dto.setCode(entity.getCode());
        dto.setName(entity.getName());
        return dto;
    }

    private Location mapToEntity(LocationDTO dto) {
        Location entity = new Location();
        entity.setId(dto.getId());
        entity.setCode(dto.getCode());
        entity.setName(dto.getName());
        return entity;
    }
}