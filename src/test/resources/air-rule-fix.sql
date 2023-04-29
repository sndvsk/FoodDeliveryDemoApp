CREATE TABLE IF NOT EXISTS extra_fee_rules_temperature (
                                                           start_temperature_range NUMERIC,
                                                           end_temperature_range NUMERIC,
                                                           temperature_fee NUMERIC
);

TRUNCATE TABLE extra_fee_rules_temperature;

INSERT INTO extra_fee_rules_temperature (start_temperature_range, end_temperature_range, temperature_fee)
VALUES
(-10.1, -20.0, 1.0),
(0.0, -10.0, 0.5),
(0.1, 60.0, 0.0);
