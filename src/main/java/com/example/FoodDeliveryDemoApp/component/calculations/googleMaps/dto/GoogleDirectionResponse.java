package com.example.FoodDeliveryDemoApp.component.calculations.googleMaps.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoogleDirectionResponse {

    public List<GeocodedWaypoint> geocoded_waypoints;
    public List<Route> routes;
    public String status;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Polyline {
        public String points;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GeocodedWaypoint {
        public String geocoder_status;
        public String place_id;
        public List<String> types;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Coordinates {
        public Double lat;
        public Double lng;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Bounds {
        public Coordinates northeast;
        public Coordinates southwest;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ValueText {
        public String text;
        public Integer value;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Step {
        public ValueText distance;
        public ValueText duration;
        public Coordinates end_location;
        public String html_instructions;
        public String maneuver;
        public Polyline polyline;
        public Coordinates start_location;
        public String travel_mode;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Leg {
        public ValueText distance;
        public ValueText duration;
        public ValueText duration_in_traffic;
        public String end_address;
        public Coordinates end_location;
        public String start_address;
        public Coordinates start_location;
        public List<Step> steps;
        public List<Object> traffic_speed_entry;
        public List<Object> via_waypoint;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Route {
        public Bounds bounds;
        public String copyrights;
        public List<Leg> legs;
        public Polyline overview_polyline;
        public String summary;
        public List<Object> warnings;
        public List<Object> waypoint_order;
    }

}
