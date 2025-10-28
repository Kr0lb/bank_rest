INSERT INTO roles (id, name)
VALUES ('fbc6b3f9-6d32-4a2b-b5c1-fd327eaf6b75', 'ADMIN'),
       ('e38b1586-6c26-4028-bb7a-29e9cb93e4d8', 'USER');
INSERT INTO users (id, firstname, lastname, middle_name, phone_number, email, password, enabled)
VALUES ('b0c8d024-dc61-46d2-b735-23deabc9b0ad', 'Ivan', 'Ivanov', 'Ivanovich', '1234567890', 'ivanov@example.com',
        '$2a$12$RXciqVlOhBGcxppCc36KMuB.ouzedO.0j8bsojNnmcAUO5y7wjMwi', TRUE),
       ('a1d17e85-1f9a-40be-b4c7-717b577a2f76', 'Petr', 'Petrov', 'Petrovich', '2345678901', 'petrov@example.com',
        '$2a$12$RXciqVlOhBGcxppCc36KMuB.ouzedO.0j8bsojNnmcAUO5y7wjMwi', TRUE),
       ('cfb69a8f-32d2-4b3d-8fe3-3479a2e314ad', 'Svetlana', 'Semenova', 'Igorevna', '3456789012',
        'semenova@example.com', '$2a$12$RXciqVlOhBGcxppCc36KMuB.ouzedO.0j8bsojNnmcAUO5y7wjMwi', TRUE);
INSERT INTO cards (id, number, validity_period, status, balance, user_id)
VALUES ('b52d9c75-30be-43c8-87c0-d36f28a63cc9', '1111222233334444', '2025-12-31', 1, 1000.50,
        'b0c8d024-dc61-46d2-b735-23deabc9b0ad'),
       ('f0b7da89-7a85-4f1d-b204-83a9b8bc760e', '5555666677778888', '2024-06-30', 0, 500.00,
        'b0c8d024-dc61-46d2-b735-23deabc9b0ad'),
       ('ae6a83d1-0c91-47e7-a8a7-5d9db65836ad', '9999000011112222', '2026-05-15', 1, 2500.75,
        'b0c8d024-dc61-46d2-b735-23deabc9b0ad');
INSERT INTO cards (id, number, validity_period, status, balance, user_id)
VALUES ('bb45b383-df85-4b64-bf23-280e779599ae', '1234123412341234', '2024-11-30', 1, 750.25,
        'a1d17e85-1f9a-40be-b4c7-717b577a2f76'),
       ('55770b26-d3c2-4a63-bb1e-4ea674303c78', '8765876587658765', '2025-08-20', 1, 1250.00,
        'a1d17e85-1f9a-40be-b4c7-717b577a2f76'),
       ('e51562be-39b3-4419-830b-1f62f86cfaaf', '2222333344445555', '2026-09-01', 0, 0.00,
        'a1d17e85-1f9a-40be-b4c7-717b577a2f76'),
       ('345fa7f1-c017-48a0-a66e-787a99db9f12', '1111333355557777', '2023-12-25', 1, 3200.50,
        'a1d17e85-1f9a-40be-b4c7-717b577a2f76');
INSERT INTO cards (id, number, validity_period, status, balance, user_id)
VALUES ('88b9b003-cd0d-4ef7-b7f7-d93db89c66ab', '1111444466668888', '2026-07-01', 1, 1500.00,
        'cfb69a8f-32d2-4b3d-8fe3-3479a2e314ad'),
       ('2a9ed59e-6b79-408a-876f-d63b15b90519', '3333444455556666', '2024-10-15', 0, 0.00,
        'cfb69a8f-32d2-4b3d-8fe3-3479a2e314ad'),
       ('6c85ecf1-90d2-4dfb-ae2f-e2f325dbd4fc', '5555777788889999', '2025-01-01', 1, 2700.60,
        'cfb69a8f-32d2-4b3d-8fe3-3479a2e314ad');
INSERT INTO user_roles (role_id, user_id)
VALUES ('fbc6b3f9-6d32-4a2b-b5c1-fd327eaf6b75', 'b0c8d024-dc61-46d2-b735-23deabc9b0ad');
INSERT INTO user_roles (role_id, user_id)
VALUES ('e38b1586-6c26-4028-bb7a-29e9cb93e4d8', 'b0c8d024-dc61-46d2-b735-23deabc9b0ad');
INSERT INTO user_roles (role_id, user_id)
VALUES ('e38b1586-6c26-4028-bb7a-29e9cb93e4d8', 'a1d17e85-1f9a-40be-b4c7-717b577a2f76');
INSERT INTO user_roles (role_id, user_id)
VALUES ('e38b1586-6c26-4028-bb7a-29e9cb93e4d8', 'cfb69a8f-32d2-4b3d-8fe3-3479a2e314ad');
