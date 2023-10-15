--liquibase formatted sql
--changeset Dreilt:0003-1
INSERT INTO `app_user` (`uuid`, `first_name`, `last_name`, `email`, `password`, `enabled`, `account_non_locked`)
VALUES
    -- admin@example.com / qwerty
    (UUID(), 'Admin', 'Admin', 'admin@example.com', '{bcrypt}$2a$10$nHSdx9AeVyj/KC9jqp9a8uiYy9Jr4lY/ILwMQJdshw98HBPn3mQhe', 1, 1),
    -- jankowalski@example.com / qwerty
    (UUID(), 'Jan', 'Kowalski', 'jankowalski@example.com', '{bcrypt}$2a$10$nHSdx9AeVyj/KC9jqp9a8uiYy9Jr4lY/ILwMQJdshw98HBPn3mQhe', 1, 1),
    -- patrykkowalski@example.com / qwerty
    (UUID(), 'Patryk', 'Kowalski', 'patrykkowalski@example.com', '{bcrypt}$2a$10$nHSdx9AeVyj/KC9jqp9a8uiYy9Jr4lY/ILwMQJdshw98HBPn3mQhe', 1, 1),
    -- jannowak@example.com / qwerty
    (UUID(), 'Jan', 'Nowak', 'jannowak@example.com', '{bcrypt}$2a$10$nHSdx9AeVyj/KC9jqp9a8uiYy9Jr4lY/ILwMQJdshw98HBPn3mQhe', 1, 1),
    -- patryknowak@example.com / qwerty
    (UUID(), 'Patryk', 'Nowak', 'patryknowak@example.com', '{bcrypt}$2a$10$nHSdx9AeVyj/KC9jqp9a8uiYy9Jr4lY/ILwMQJdshw98HBPn3mQhe', 1, 1),
    -- piotrwysocki@example.com / qwerty
    (UUID(), 'Piotr', 'Wysocki', 'piotrwysocki@example.com', '{bcrypt}$2a$10$nHSdx9AeVyj/KC9jqp9a8uiYy9Jr4lY/ILwMQJdshw98HBPn3mQhe', 1, 1),
    -- dawidpolak@example.com / qwerty
    (UUID(), 'Dawid', 'Polak', 'dawidpolak@example.com', '{bcrypt}$2a$10$nHSdx9AeVyj/KC9jqp9a8uiYy9Jr4lY/ILwMQJdshw98HBPn3mQhe', 1, 1),
    -- zuzannakowalska@example.com / qwerty
    (UUID(), 'Zuzanna', 'Kowalska', 'zuzannakowalska@example.com', '{bcrypt}$2a$10$nHSdx9AeVyj/KC9jqp9a8uiYy9Jr4lY/ILwMQJdshw98HBPn3mQhe', 1, 1),
    -- piotrmichalik@example.com / qwerty
    (UUID(), 'Piotr', 'Michalik', 'piotrmichalik@example.com', '{bcrypt}$2a$10$nHSdx9AeVyj/KC9jqp9a8uiYy9Jr4lY/ILwMQJdshw98HBPn3mQhe', 1, 1),
    -- dawiddabrowski@example.com / qwerty
    (UUID(), 'Dawid', 'Dąbrowski', 'dawiddabrowski@example.com', '{bcrypt}$2a$10$nHSdx9AeVyj/KC9jqp9a8uiYy9Jr4lY/ILwMQJdshw98HBPn3mQhe', 1, 1),
    -- danieldabrowski@example.com / qwerty
    (UUID(), 'Daniel', 'Dąbrowski', 'danieldabrowski@example.com', '{bcrypt}$2a$10$nHSdx9AeVyj/KC9jqp9a8uiYy9Jr4lY/ILwMQJdshw98HBPn3mQhe', 1, 1),
    -- marianowak@example.com / qwerty
    (UUID(), 'Maria', 'Nowak', 'marianowak@example.com', '{bcrypt}$2a$10$nHSdx9AeVyj/KC9jqp9a8uiYy9Jr4lY/ILwMQJdshw98HBPn3mQhe', 1, 1);