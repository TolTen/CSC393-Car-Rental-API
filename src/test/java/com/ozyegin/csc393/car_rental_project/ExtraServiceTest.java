package com.ozyegin.csc393.car_rental_project;

import com.ozyegin.csc393.car_rental_project.domain.Extra;
import com.ozyegin.csc393.car_rental_project.dto.ExtraDTO;
import com.ozyegin.csc393.car_rental_project.repository.ExtraRepository;
import com.ozyegin.csc393.car_rental_project.service.ExtraService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class ExtraServiceTest {

    @Autowired
    private ExtraService extraService;

    @Autowired
    private ExtraRepository extraRepository;

    @Test
    void shouldCreateExtra() {
        ExtraDTO dto = new ExtraDTO();
        dto.setName("Snow Tires");
        dto.setPrice(BigDecimal.valueOf(500));

        ExtraDTO result = extraService.createExtra(dto);

        assertNotNull(result.getId());
        assertEquals("Snow Tires", result.getName());
    }

    @Test
    void shouldUpdateExtra() {
        Extra extra = new Extra();
        extra.setName("Wifi");
        extra.setPrice(BigDecimal.valueOf(100));
        extraRepository.save(extra);

        ExtraDTO updateDto = new ExtraDTO();
        updateDto.setName("Wifi Fast");
        updateDto.setPrice(BigDecimal.valueOf(200));

        ExtraDTO result = extraService.updateExtra(extra.getId(), updateDto);

        assertEquals("Wifi Fast", result.getName());
        assertEquals(BigDecimal.valueOf(200), result.getPrice());
    }

    @Test
    void shouldGetExtraById() {
        ExtraDTO dto = new ExtraDTO();
        dto.setName("Navigation");
        dto.setPrice(BigDecimal.valueOf(250));

        ExtraDTO created = extraService.createExtra(dto);

        ExtraDTO fromService = extraService.getExtraById(created.getId());

        assertEquals(created.getId(), fromService.getId());
        assertEquals("Navigation", fromService.getName());
        assertEquals(BigDecimal.valueOf(250), fromService.getPrice());
    }

    @Test
    void shouldListAllExtrasAndDelete() {
        int before = extraService.getAllExtras().size();

        ExtraDTO dto = new ExtraDTO();
        dto.setName("Baby Seat");
        dto.setPrice(BigDecimal.valueOf(300));
        ExtraDTO created = extraService.createExtra(dto);

        List<ExtraDTO> all = extraService.getAllExtras();
        assertTrue(all.size() >= before + 1);

        extraService.deleteExtraById(created.getId());

        assertThrows(IllegalArgumentException.class, () -> extraService.getExtraById(created.getId()));
    }
}
