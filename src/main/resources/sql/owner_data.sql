-- insert into users table
INSERT INTO users(username, firstname, lastname, password, email, telephone, role, created_at, updated_at)
VALUES
    ('owner1', 'John', 'Doe', '$2a$10$KEpnXVQlB/JV1Fv2u3mPMOyCJ.ymDHT9xOdPcxHskPEthCeD0Hksy', 'john.doe@example.com', '+1234567890', 'OWNER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('owner2', 'Jane', 'Smith', '$2a$10$41/49VY9zkoN3E0wQPgtvO8VlLGV6yzTGRIXVPA6VVONincOoWh8O', 'jane.smith@example.com', '+2345678901', 'OWNER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('owner3', 'Jim', 'Johnson', '$2a$10$B5AFdtlc4OLO8BVbjbUEv.6iykXOEblJtW1okOyVDSZYOGRpL8PxK', 'jim.johnson@example.com', '+3456789012', 'OWNER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('owner4', 'Jill', 'Jackson', '$2a$10$XXrYoItLMH6Wfdsje8owVObEA6YIKn51OWSQM64uXQTep/OOJ.JU2', 'jill.jackson@example.com', '+4567890123', 'OWNER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('owner5', 'Joe', 'Davis', '$2a$10$thlPHBBpS1WhS/idDOVd8uTNKuSdT1G5ZEatmIw1RUnFGU0zDh83y', 'joe.davis@example.com', '+5678901234', 'OWNER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- insert into users_owners table
INSERT INTO users_owners(user_id, approved)
VALUES
    ((SELECT id FROM users WHERE username = 'owner1'), true),
    ((SELECT id FROM users WHERE username = 'owner2'), true),
    ((SELECT id FROM users WHERE username = 'owner3'), true),
    ((SELECT id FROM users WHERE username = 'owner4'), true),
    ((SELECT id FROM users WHERE username = 'owner5'), true);
