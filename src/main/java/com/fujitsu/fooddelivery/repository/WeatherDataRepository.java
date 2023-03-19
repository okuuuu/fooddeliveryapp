package com.fujitsu.fooddelivery.repository;

import com.fujitsu.fooddelivery.entity.WeatherData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

// A repository interface extending JpaRepository, used for
// interacting with the application's database to store and retrieve weather data.
@Repository
public interface WeatherDataRepository extends JpaRepository<WeatherData, Long> {
    @Query("SELECT wd FROM WeatherData wd WHERE wd.name = ?1 ORDER BY wd.timestamp DESC")
    List<WeatherData> findLatestWeatherDataByCity(String city);
}
