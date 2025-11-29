package com.ozyegin.csc393.car_rental_project;

import com.ozyegin.csc393.car_rental_project.domain.Member;
import com.ozyegin.csc393.car_rental_project.dto.MemberDTO;
import com.ozyegin.csc393.car_rental_project.repository.MemberRepository;
import com.ozyegin.csc393.car_rental_project.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void shouldCreateMember() {
        MemberDTO dto = new MemberDTO();
        dto.setName("Tolga TolTen");
        dto.setEmail("tolga@test.com");
        dto.setDrivingLicenseNumber("EHLIYET_123");
        dto.setPhone("5554443322");
        dto.setAddress("Istanbul");

        MemberDTO result = memberService.createMember(dto);

        assertNotNull(result.getId());
        assertEquals("EHLIYET_123", result.getDrivingLicenseNumber());
    }

    @Test
    void shouldFindMemberByDrivingLicense() {
        Member member = new Member();
        member.setName("Ayse");
        member.setDrivingLicenseNumber("FIND_THIS");
        memberRepository.save(member);

        MemberDTO result = memberService.findByDrivingLicenseNumber("FIND_THIS");

        assertNotNull(result);
        assertEquals("Ayse", result.getName());
    }

    @Test
    void shouldUpdateMember() {
        Member member = new Member();
        member.setName("Mehmet");
        member.setDrivingLicenseNumber("UPDATE_ME");
        memberRepository.save(member);

        MemberDTO updateDto = new MemberDTO();
        updateDto.setName("Mehmet Can");
        updateDto.setDrivingLicenseNumber("UPDATE_ME");
        updateDto.setEmail("new@test.com");

        MemberDTO result = memberService.updateMember(member.getId(), updateDto);

        assertEquals("Mehmet Can", result.getName());
        assertEquals("new@test.com", result.getEmail());
    }

    @Test
    void shouldGetMemberById() {
        MemberDTO dto = new MemberDTO();
        dto.setName("Ali");
        dto.setAddress("Istanbul");
        dto.setEmail("ali2@test.com");
        dto.setPhone("5551111111");
        dto.setDrivingLicenseNumber("LICENSE_999");

        MemberDTO created = memberService.createMember(dto);

        MemberDTO fromService = memberService.getMemberById(created.getId());

        assertEquals(created.getId(), fromService.getId());
        assertEquals("Ali", fromService.getName());
        assertEquals("LICENSE_999", fromService.getDrivingLicenseNumber());
    }

    @Test
    void shouldListAllMembersAndDelete() {
        int before = memberService.getAllMembers().size();

        MemberDTO dto = new MemberDTO();
        dto.setName("Veli");
        dto.setAddress("Ankara");
        dto.setEmail("veli@test.com");
        dto.setPhone("5552222222");
        dto.setDrivingLicenseNumber("LICENSE_DEL");

        MemberDTO created = memberService.createMember(dto);

        List<MemberDTO> all = memberService.getAllMembers();
        assertTrue(all.size() >= before + 1);

        memberService.deleteMemberById(created.getId());

        assertThrows(IllegalArgumentException.class,
                () -> memberService.getMemberById(created.getId()));
    }
}
