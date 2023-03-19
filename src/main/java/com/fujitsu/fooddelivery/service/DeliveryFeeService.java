package com.fujitsu.fooddelivery.service;


import com.fujitsu.fooddelivery.entity.WeatherData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static com.fujitsu.fooddelivery.service.VehicleType.*;

@Service
public class DeliveryFeeService {

    private final WeatherDataService weatherDataService;

    @Autowired
    public DeliveryFeeService(WeatherDataService weatherDataService) {
        this.weatherDataService = weatherDataService;
    }

    public BigDecimal calculateDeliveryFee(String city, String vehicle) {
        Map<String, String> cityToStation = getCityToStationMapping();
        String stationName = cityToStation.get(city.toLowerCase());
        if (stationName == null) {
            throw new IllegalArgumentException("Invalid city: " + city);
        }

        Map<String, VehicleType> vehicleToVehicleType = getVehicleToVehicleType();
        VehicleType vehicleType = vehicleToVehicleType.get(vehicle.toLowerCase());
        if (vehicleType == null) {
            throw new IllegalArgumentException("Invalid vehicle: " + vehicle);
        }

        WeatherData latestWeatherData = weatherDataService.getLatestWeatherData(stationName);

        if (latestWeatherData == null) {
            throw new RuntimeException("No weather data found for the specified city.");
        }

        BigDecimal regionalBaseFee = getRBF(city, vehicleType);
        BigDecimal airTemperatureExtraFee = getATEF(latestWeatherData.getAirTemperature(), vehicleType);
        BigDecimal windSpeedExtraFee = getWSEF(latestWeatherData.getWindSpeed(), vehicleType);
        BigDecimal weatherPhenomenonExtraFee = getWPEF(latestWeatherData.getPhenomenon(), vehicleType);

        return regionalBaseFee.add(airTemperatureExtraFee).add(windSpeedExtraFee).add(weatherPhenomenonExtraFee);

    }

    private BigDecimal getRBF(String city, VehicleType vehicleType) {
        switch (city.toLowerCase()) {
            case "tallinn" -> {
                return switch (vehicleType) {
                    case CAR -> BigDecimal.valueOf(4);
                    case SCOOTER -> BigDecimal.valueOf(3.5);
                    case BIKE -> BigDecimal.valueOf(3);
                };
            }
            case "tartu" -> {
                return switch (vehicleType) {
                    case CAR -> BigDecimal.valueOf(3.5);
                    case SCOOTER -> BigDecimal.valueOf(3);
                    case BIKE -> BigDecimal.valueOf(2.5);
                };
            }
            case "pärnu" -> {
                return switch (vehicleType) {
                    case CAR -> BigDecimal.valueOf(3);
                    case SCOOTER -> BigDecimal.valueOf(2.5);
                    case BIKE -> BigDecimal.valueOf(2);
                };
            }
        }
        throw new RuntimeException("Invalid city or vehicle type.");
    }

    private BigDecimal getATEF(Double airTemperature, VehicleType vehicleType) {
        if (vehicleType == VehicleType.SCOOTER || vehicleType == VehicleType.BIKE) {
            if (airTemperature.compareTo((double) -10) < 0) {
                return BigDecimal.valueOf(1);
            } else if (airTemperature.compareTo((double) 0) < 0) {
                return BigDecimal.valueOf(0.5);
            }
        }
        return BigDecimal.ZERO;
    }

    private BigDecimal getWSEF(Double windSpeed, VehicleType vehicleType) {
        if (vehicleType == VehicleType.BIKE) {
            if (windSpeed.compareTo(20.0) > 0) {
                throw new RuntimeException("Usage of selected vehicle type is forbidden");
            } else if (windSpeed.compareTo(10.0) >= 0) {
                return BigDecimal.valueOf(0.5);
            }
        }
        return BigDecimal.ZERO;
    }

    private BigDecimal getWPEF(String phenomenon, VehicleType vehicleType) {
        if (vehicleType == CAR) {
            return BigDecimal.ZERO;
        }

        String lowerCasePhenomenon = phenomenon.toLowerCase();

        if (lowerCasePhenomenon.contains("snow") || lowerCasePhenomenon.contains("sleet")) {
            return BigDecimal.valueOf(1.0);
        } else if (lowerCasePhenomenon.contains("rain") || (lowerCasePhenomenon.contains("shower") && !lowerCasePhenomenon.contains("snow"))) {
            return BigDecimal.valueOf(0.5);
        } else if (lowerCasePhenomenon.contains("glaze") || lowerCasePhenomenon.contains("hail") || lowerCasePhenomenon.contains("thunder")) {
            throw new IllegalStateException("Usage of selected vehicle type is forbidden");
        } else {
            return BigDecimal.ZERO;
        }
    }
    private Map<String, String> getCityToStationMapping() {
        Map<String, String> cityToStation = new HashMap<>();
        cityToStation.put("tallinn", "Tallinn-Harku");
        cityToStation.put("tartu", "Tartu-Tõravere");
        cityToStation.put("pärnu", "Pärnu");
        return cityToStation;
    }
    private Map<String, VehicleType> getVehicleToVehicleType() {
        Map<String, VehicleType> vehicleToVehicleType = new HashMap<>();
        vehicleToVehicleType.put("car", CAR);
        vehicleToVehicleType.put("scooter", SCOOTER);
        vehicleToVehicleType.put("bike", BIKE);
        return vehicleToVehicleType;
    }
}
