package com.fujitsu.fooddelivery.jaxb;

import jakarta.xml.bind.annotation.XmlElement;

public class Station {
    private String name;
    private Integer wmoCode;
    private Double airTemperature;
    private Double windSpeed;
    private String phenomenon;

    @XmlElement(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(name = "wmocode")
    public Integer getWmoCode() {
        return wmoCode;
    }

    public void setWmoCode(Integer wmoCode) {
        this.wmoCode = wmoCode;
    }

    @XmlElement(name = "airtemperature")
    public Double getAirTemperature() {
        return airTemperature;
    }

    public void setAirTemperature(Double airTemperature) {
        this.airTemperature = airTemperature;
    }

    @XmlElement(name = "windspeed")
    public Double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Double windSpeed) {
        this.windSpeed = windSpeed;
    }

    @XmlElement(name = "phenomenon")
    public String getPhenomenon() {
        return phenomenon;
    }

    public void setPhenomenon(String phenomenon) {
        this.phenomenon = phenomenon;
    }
}

