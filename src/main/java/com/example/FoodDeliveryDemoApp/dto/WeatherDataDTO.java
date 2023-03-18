package com.example.FoodDeliveryDemoApp.dto;

import jakarta.xml.bind.annotation.*;

import java.time.Instant;
import java.util.List;

public class WeatherDataDTO {

    @SuppressWarnings({"unused", "CommentedOutCode"})
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Station {
        private String name;
        private String wmocode;
        private Double airtemperature;
        private Double windspeed;
        private String phenomenon;
        public Instant timestamp;

/*        private Double longitude;
        private Double latitude;
        private Double visibility;
        private Integer precipitations;
        private Double airpressure;
        private Integer relativehumidity;
        private Integer winddirection;
        private Double windspeedmax;
        private Double waterlevel;
        private Double waterlevel_eh2000;
        private Double watertemperature;
        private Double uvindex;*/

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getWmocode() {
            return wmocode;
        }

        public void setWmocode(String wmocode) {
            this.wmocode = wmocode;
        }

        public Double getAirtemperature() {
            return airtemperature;
        }

        public void setAirtemperature(Double airtemperature) {
            this.airtemperature = airtemperature;
        }

        public Double getWindspeed() {
            return windspeed;
        }

        public void setWindspeed(Double windspeed) {
            this.windspeed = windspeed;
        }

        public String getPhenomenon() {
            return phenomenon;
        }

        public void setPhenomenon(String phenomenon) {
            this.phenomenon = phenomenon;
        }

        public void setTimestamp(Instant timestamp) {
            this.timestamp = timestamp;
        }

        public Instant getTimestamp() {
            return timestamp;
        }
    }

    @SuppressWarnings("unused")
    @XmlRootElement(name = "observations")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Observations {

        @XmlAttribute(name = "timestamp")
        public Long timestamp;

        @XmlElement(name = "station")
        public List<Station> stations;

        public Long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(Long timestamp) {
            this.timestamp = timestamp;
        }

        public List<Station> getStations() {
            return stations;
        }

        public void setStations(List<Station> stations) {
            this.stations = stations;
        }
    }

}


