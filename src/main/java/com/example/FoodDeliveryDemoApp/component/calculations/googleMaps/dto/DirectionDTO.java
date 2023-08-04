package com.example.FoodDeliveryDemoApp.component.calculations.googleMaps.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DirectionDTO {

    private List<Route> routes;

    public static class Route {
        private List<Leg> legs;
    }

    public static class Leg {
        private Distance distance;
        private Duration duration;
        private String startAddress;
        private String endAddress;
        private List<Step> steps;
    }

    public static class Step {
        private Distance distance;
        private Duration duration;
        private Location startLocation;
        private Location endLocation;
        private String htmlInstructions;
    }

    public static class Distance {
        private String text;
        private int value;
    }

    public static class Duration {
        private String text;
        private int value;
    }

    public static class Location {
        private double lat;
        private double lng;
    }
}

