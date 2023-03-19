package com.fujitsu.fooddelivery.service;

import com.fujitsu.fooddelivery.entity.WeatherData;
import com.fujitsu.fooddelivery.jaxb.Observations;
import com.fujitsu.fooddelivery.jaxb.Station;
import com.fujitsu.fooddelivery.repository.WeatherDataRepository;
import jakarta.annotation.PostConstruct;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.StringReader;
import java.util.*;

@Service
public class WeatherDataService {
    @Autowired
    private final WeatherDataRepository weatherDataRepository;

    @Autowired
    public WeatherDataService(WeatherDataRepository weatherDataRepository) {
        this.weatherDataRepository = weatherDataRepository;
    }

    @PostConstruct
    @Scheduled(cron = "${weather.data.import.cron:0 15 * * * *}")
    public void importWeatherData() {
        System.out.println("Importing weather data...");
        List<WeatherData> weatherDataList = fetchWeatherData();

        weatherDataRepository.saveAll(weatherDataList);
    }

    private List<WeatherData> fetchWeatherData() {
        String url = "https://www.ilmateenistus.ee/ilma_andmed/xml/observations.php";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        return parseWeatherDataXml(response.getBody()); // Split XML using buffered reader!
    }

    private List<WeatherData> parseWeatherDataXml(String xml) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Observations.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            StringReader reader = new StringReader(xml);
            Observations observations = (Observations) unmarshaller.unmarshal(reader);

            List<String> targetStations = Arrays.asList("Tallinn-Harku", "Tartu-Tõravere", "Pärnu");
            List<Station> filteredStations = observations.getStations().stream()
                    .filter(station -> targetStations.contains(station.getName()))
                    .toList();

            // Create a list of WeatherData objects with the parsed information
            List<WeatherData> weatherDataList = new ArrayList<>();
            for (Station station : filteredStations) {
                WeatherData weatherData = new WeatherData();
                weatherData.setName(station.getName());
                weatherData.setWmoCode(station.getWmoCode());
                weatherData.setAirTemperature(station.getAirTemperature());
                weatherData.setWindSpeed(station.getWindSpeed());
                weatherData.setPhenomenon(station.getPhenomenon());
                weatherData.setTimestamp(observations.getTimestamp());
                weatherDataList.add(weatherData);
            }

            return weatherDataList;
        } catch (JAXBException e) {
            throw new RuntimeException("Error parsing weather data XML", e);
        }
    }
    public WeatherData getLatestWeatherData(String stationName) {
        List<WeatherData> weatherDataList = weatherDataRepository.findLatestWeatherDataByCity(stationName);

        if (!weatherDataList.isEmpty()) {
            return weatherDataList.get(0);
        } else {
            return null;
        }
    }
    public boolean isWeatherDataAvailable() {
        return weatherDataRepository.count() > 0;
    }
}
