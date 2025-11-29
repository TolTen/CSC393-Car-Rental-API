package com.ozyegin.csc393.car_rental_project;

import com.ozyegin.csc393.car_rental_project.domain.Location;
import com.ozyegin.csc393.car_rental_project.dto.LocationDTO;
import com.ozyegin.csc393.car_rental_project.repository.LocationRepository;
import com.ozyegin.csc393.car_rental_project.service.LocationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class LocationServiceTest {

    @Autowired
    private LocationService locationService;

    @Autowired
    private LocationRepository locationRepository;

    @Test
    void shouldCreateLocation() {
        LocationDTO dto = new LocationDTO();
        dto.setCode("TEST_LOC");
        dto.setName("Test Location");

        LocationDTO result = locationService.createLocation(dto);

        assertNotNull(result.getId());
        assertEquals("TEST_LOC", result.getCode());
        assertTrue(locationRepository.findByCode("TEST_LOC").isPresent());
    }

    @Test
    void shouldUpdateLocation() {

        Location loc = new Location();
        loc.setCode("OLD_CODE");
        loc.setName("Old Name");
        locationRepository.save(loc);

        LocationDTO updateDto = new LocationDTO();
        updateDto.setCode("NEW_CODE");
        updateDto.setName("New Name");

        LocationDTO result = locationService.updateLocation(loc.getId(), updateDto);

        assertEquals("NEW_CODE", result.getCode());
        assertEquals("New Name", result.getName());
    }

    @Test
    void shouldDeleteLocation() {
        Location loc = new Location();
        loc.setCode("DEL_LOC");
        locationRepository.save(loc);

        locationService.deleteLocationById(loc.getId());

        assertFalse(locationRepository.findById(loc.getId()).isPresent());
    }
}