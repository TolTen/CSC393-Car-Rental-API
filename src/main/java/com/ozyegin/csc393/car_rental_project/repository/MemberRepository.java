package com.ozyegin.csc393.car_rental_project.repository;

import com.ozyegin.csc393.car_rental_project.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByDrivingLicenseNumber(String drivingLicenseNumber);
}