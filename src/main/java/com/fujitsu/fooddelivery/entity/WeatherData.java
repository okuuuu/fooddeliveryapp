package com.fujitsu.fooddelivery.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

// A Java class representing the weather data entity. It contains
// fields to store the weather data, such as the city name, timestamp, and weather phenomena.
@Entity
public class WeatherData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer wmoCode;
    private Double airTemperature;
    private Double windSpeed;
    private String phenomenon;
    private long timestamp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getWmoCode() {
        return wmoCode;
    }

    public void setWmoCode(Integer wmoCode) {
        this.wmoCode = wmoCode;
    }

    public Double getAirTemperature() {
        return airTemperature;
    }

    public void setAirTemperature(Double airTemperature) {
        this.airTemperature = airTemperature;
    }

    public Double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getPhenomenon() {
        return phenomenon;
    }

    public void setPhenomenon(String phenomenon) {
        this.phenomenon = phenomenon;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
