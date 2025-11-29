package com.ozyegin.csc393.car_rental_project.service;

import com.ozyegin.csc393.car_rental_project.domain.Extra;
import com.ozyegin.csc393.car_rental_project.dto.ExtraDTO;
import com.ozyegin.csc393.car_rental_project.repository.ExtraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
//@RequiredArgsConstructor
public class ExtraService {

    private final ExtraRepository extraRepository;

    public ExtraService(ExtraRepository extraRepository) {
        this.extraRepository = extraRepository;
    }

    public ExtraDTO createExtra(ExtraDTO dto) {
        Extra extra = mapToEntity(dto);
        Extra saved = extraRepository.save(extra);
        return mapToDTO(saved);
    }

    public List<ExtraDTO> getAllExtras() {
        return extraRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public ExtraDTO getExtraById(Long id) {
        Extra extra = extraRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Extra not found with id: " + id));
        return mapToDTO(extra);
    }

    @Transactional
    public ExtraDTO updateExtra(Long id, ExtraDTO dto) {
        Extra existing = extraRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Extra not found"));

        existing.setName(dto.getName());
        existing.setPrice(dto.getPrice());

        Extra saved = extraRepository.save(existing);
        return mapToDTO(saved);
    }

    public void deleteExtraById(Long id) {
        extraRepository.deleteById(id);
    }


    private ExtraDTO mapToDTO(Extra entity) {
        ExtraDTO dto = new ExtraDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setPrice(entity.getPrice());
        return dto;
    }

    private Extra mapToEntity(ExtraDTO dto) {
        Extra entity = new Extra();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setPrice(dto.getPrice());
        return entity;
    }
}