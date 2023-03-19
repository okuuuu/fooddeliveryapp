package com.fujitsu.fooddelivery.jaxb;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
//import java.time.LocalDateTime;
//import java.time.ZoneOffset;
import java.util.List;

@XmlRootElement(name = "observations")
public class Observations {
    private List<Station> stations;
    private long timestamp;

    @XmlElement(name = "station")
    public List<Station> getStations() {
        return stations;
    }

    public void setStations(List<Station> stations) {
        this.stations = stations;
    }

    @XmlAttribute(name = "timestamp")
    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

//    public LocalDateTime getTimestampAsLocalDateTime() {
//        return LocalDateTime.ofEpochSecond(timestamp, 0, ZoneOffset.UTC);
//    }
}
