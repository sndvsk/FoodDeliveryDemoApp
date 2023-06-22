-- Truncate every table to delete data from previous runs
truncate table base_fee_rules restart identity cascade;
truncate table delivery_fee restart identity cascade;
truncate table extra_fee_rules_temperature restart identity cascade;
truncate table extra_fee_rules_weather_phenomenon restart identity cascade;
truncate table extra_fee_rules_windspeed restart identity cascade;
truncate table weather_data restart identity cascade;
truncate table users restart identity cascade;
truncate table tokens restart identity cascade;
truncate table users_admins restart identity cascade;
truncate table users_owners restart identity cascade;
truncate table restaurants restart identity cascade;
truncate table addresses restart identity cascade;
truncate table menus restart identity cascade;
truncate table items restart identity cascade;
truncate table users_customers restart identity cascade;
truncate table orders restart identity cascade;
truncate table order_items restart identity cascade;

-- Insert data into the RegionalBaseFeeRule table
INSERT INTO base_fee_rules(city, wmo_code, vehicle_type, regional_base_fee)
VALUES ('tallinn', 26038, 'car', 4.0), ('tallinn', 26038, 'scooter', 3.5), ('tallinn', 26038, 'bike', 3.0),
       ('tartu', 26242, 'car', 3.5), ('tartu', 26242, 'scooter', 3.0), ('tartu', 26242, 'bike', 2.5),
       ('pärnu', 41803, 'car', 3.0), ('pärnu', 41803, 'scooter', 2.5), ('pärnu', 41803, 'bike', 2.0);

-- Insert data into the ExtraFeeAirTemperatureRule table

INSERT INTO extra_fee_rules_temperature(start_temperature_range, end_temperature_range, temperature_fee)
VALUES (-10.1, -20.0, 1.0), (0.0, -10.0, 0.5), (0.1, 60.0, 0.0);

-- Insert data into the ExtraFeeWindSpeedRule table
INSERT INTO extra_fee_rules_windspeed(start_windspeed_range, end_windspeed_range, windspeed_fee)
VALUES (20.1, 26.0, -1.0), (10.0, 20.0, 0.5), (0.0, 9.9, 0.0);

-- Insert data into the ExtraFeeWeatherPhenomenonRule table
INSERT INTO extra_fee_rules_weather_phenomenon(weather_phenomenon_name, weather_phenomenon_fee)
VALUES ('Clear', 0.0),
       ('Few clouds', 0.0),
       ('Variable clouds', 0.0),
       ('Cloudy with clear spells', 0.0),
       ('Overcast', 0.0),
       ('Light snow shower', 1.0),
       ('Moderate snow shower', 1.0),
       ('Heavy snow shower', 1.0),
       ('Light shower', 0.5),
       ('Moderate shower', 0.5),
       ('Heavy shower', 0.5),
       ('Light rain', 0.5),
       ('Moderate rain', 0.5),
       ('Heavy rain', 0.5),
       ('Glaze', -1.0),
       ('Light sleet', 1.0),
       ('Moderate sleet', 1.0),
       ('Light snowfall', 1.0),
       ('Moderate snowfall', 1.0),
       ('Heavy snowfall', 1.0),
       ('Blowing snow', 1.0),
       ('Drifting snow', 1.0),
       ('Hail', -1.0),
       ('Mist', 0.0),
       ('Fog', 0.0),
       ('Thunder', -1.0),
       ('Thunderstorm', -1.0);

-- Insert data into the DeliveryFee table
-- Please replace 'current_timestamp' with the precise timestamp if needed.
/*INSERT INTO delivery_fee(city, vehicle_type, delivery_fee, timestamp)
VALUES ('tallinn', 'car', 4.0, current_timestamp),
       ('tartu', 'scooter', 3.0, current_timestamp),
       ('pärnu', 'bike', 2.0, current_timestamp);*/

-- Insert data into the WeatherData table
-- Please replace 'current_timestamp' with the precise timestamp if needed.
/*INSERT INTO weather_data(station_name, wmo_code, air_temperature, wind_speed, weather_phenomenon, timestamp)
VALUES ('tallinn', 26038, 10.0, 5.0, 'Clear', current_timestamp),
       ('tartu', 26242, 1.0, 3.3, 'Light rain', current_timestamp),
       ('pärnu', 41803, 22.3, 0.5, 'Clear', current_timestamp);*/
