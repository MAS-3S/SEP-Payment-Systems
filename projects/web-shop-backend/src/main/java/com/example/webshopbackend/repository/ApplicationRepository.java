package com.example.webshopbackend.repository;

import com.example.webshopbackend.model.Application;
import com.example.webshopbackend.model.Conference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, String> {

    List<Application> findAll();

    @Query(value = "SELECT a.cityName FROM Application a GROUP BY a.cityName ORDER BY COUNT(a.cityName) DESC")
    List<String> findMostCommonCityNames();

    @Query(value = "SELECT a.timeHour FROM Application a GROUP BY a.timeHour ORDER BY COUNT(a.timeHour) DESC")
    List<Integer> findMostCommonTimeHours();
}
