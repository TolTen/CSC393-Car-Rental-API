package com.ozyegin.csc393.car_rental_project;

import com.ozyegin.csc393.car_rental_project.domain.*;
import com.ozyegin.csc393.car_rental_project.enums.CarCategory;
import com.ozyegin.csc393.car_rental_project.enums.CarStatus;
import com.ozyegin.csc393.car_rental_project.enums.TransmissionType;
import com.ozyegin.csc393.car_rental_project.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataInitializer implements CommandLineRunner {

    private final LocationRepository locationRepository;
    private final CarRepository carRepository;
    private final MemberRepository memberRepository;
    private final ExtraRepository extraRepository;

    public DataInitializer(LocationRepository locationRepository,
                           CarRepository carRepository,
                           MemberRepository memberRepository,
                           ExtraRepository extraRepository) {
        this.locationRepository = locationRepository;
        this.carRepository = carRepository;
        this.memberRepository = memberRepository;
        this.extraRepository = extraRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (locationRepository.count() > 0) {
            return;
        }

        System.out.println("--- BAŞLANGIÇ VERİLERİ YÜKLENİYOR ---");

        Location loc1 = new Location();
        loc1.setCode("IST");
        loc1.setName("İstanbul Havalimanı");
        locationRepository.save(loc1);

        Location loc2 = new Location();
        loc2.setCode("SAW");
        loc2.setName("Sabiha Gökçen Havalimanı");
        locationRepository.save(loc2);

        Car car1 = new Car();
        car1.setBarcode("BAR123456");
        car1.setLicensePlate("34 AB 123");
        car1.setBrand("Toyota");
        car1.setModel("Corolla");
        car1.setSeatCount(5);
        car1.setMileage(15000);
        car1.setDailyPrice(BigDecimal.valueOf(1500));

        car1.setCategory(CarCategory.MIDSIZE);
        car1.setTransmission(TransmissionType.AUTOMATIC);
        car1.setStatus(CarStatus.AVAILABLE);
        car1.setCurrentLocation(loc1);
        carRepository.save(car1);

        Car car2 = new Car();
        car2.setBarcode("BAR987654");
        car2.setLicensePlate("35 YZ 999");
        car2.setBrand("BMW");
        car2.setModel("320i");
        car2.setSeatCount(5);
        car2.setMileage(5000);
        car2.setDailyPrice(BigDecimal.valueOf(3500));
        car2.setCategory(CarCategory.LUXURY);
        car2.setTransmission(TransmissionType.AUTOMATIC);
        car2.setStatus(CarStatus.AVAILABLE);
        car2.setCurrentLocation(loc2);
        carRepository.save(car2);


        Member member1 = new Member();
        member1.setName("Ahmet Yılmaz");
        member1.setEmail("ahmet@example.com");
        member1.setPhone("5551234567");
        member1.setAddress("Kadıköy, İstanbul");
        member1.setDrivingLicenseNumber("EHLIYET001");
        memberRepository.save(member1);


        Extra extra1 = new Extra();
        extra1.setName("Bebek Koltuğu");
        extra1.setPrice(BigDecimal.valueOf(200));
        extraRepository.save(extra1);

        Extra extra2 = new Extra();
        extra2.setName("GPS Navigasyon");
        extra2.setPrice(BigDecimal.valueOf(150));
        extraRepository.save(extra2);

        System.out.println("--- BAŞLANGIÇ VERİLERİ YÜKLENDİ ---");
    }
}