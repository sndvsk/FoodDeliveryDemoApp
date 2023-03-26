create table if not exists "extra_fee_rules_windspeed";

truncate table "extra_fee_rules_windspeed";

INSERT INTO "extra_fee_rules_windspeed" ("start_windspeed_range", "end_windspeed_range", "windspeed_fee")
VALUES
       (20.1, 26.0, -1.0),
       (10.0, 20.0, 0.5),
       (0.0, 9.9, 0.0);