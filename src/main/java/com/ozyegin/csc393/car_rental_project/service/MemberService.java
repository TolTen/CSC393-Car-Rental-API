package com.ozyegin.csc393.car_rental_project.service;

import com.ozyegin.csc393.car_rental_project.domain.Member;
import com.ozyegin.csc393.car_rental_project.dto.MemberDTO;
import com.ozyegin.csc393.car_rental_project.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
//@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public MemberDTO createMember(MemberDTO dto) {
        Member member = mapToEntity(dto);
        Member saved = memberRepository.save(member);
        return mapToDTO(saved);
    }

    public List<MemberDTO> getAllMembers() {
        return memberRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public MemberDTO getMemberById(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Member not found with id: " + id));
        return mapToDTO(member);
    }

    public MemberDTO findByDrivingLicenseNumber(String drivingLicenseNumber) {
        Member member = memberRepository.findByDrivingLicenseNumber(drivingLicenseNumber).orElseThrow(() -> new IllegalArgumentException("Member not found with license: " + drivingLicenseNumber));
        return mapToDTO(member);
    }

    @Transactional
    public MemberDTO updateMember(Long id, MemberDTO dto) {
        Member existing = memberRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Member not found"));

        existing.setName(dto.getName());
        existing.setAddress(dto.getAddress());
        existing.setEmail(dto.getEmail());
        existing.setPhone(dto.getPhone());
        existing.setDrivingLicenseNumber(dto.getDrivingLicenseNumber());

        Member saved = memberRepository.save(existing);
        return mapToDTO(saved);
    }

    public void deleteMemberById(Long id) {
        memberRepository.deleteById(id);
    }


    private MemberDTO mapToDTO(Member entity) {
        MemberDTO dto = new MemberDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setAddress(entity.getAddress());
        dto.setEmail(entity.getEmail());
        dto.setPhone(entity.getPhone());
        dto.setDrivingLicenseNumber(entity.getDrivingLicenseNumber());
        return dto;
    }

    private Member mapToEntity(MemberDTO dto) {
        Member entity = new Member();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setAddress(dto.getAddress());
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());
        entity.setDrivingLicenseNumber(dto.getDrivingLicenseNumber());
        return entity;
    }
}