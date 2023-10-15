--liquibase formatted sql
--changeset Dreilt:0013-1
INSERT INTO `event` (`uuid`, `name`, `event_type`, `date_time`, `language`, `admission`, `city`, `location`, `address`, `organizer_id`, `description`)
VALUES
    (UUID(), 'Java Dev Talks #1', 'MEETING', '2023-01-03T18:00', 'polski', 'FREE', 'Rzeszów', 'WSIiZ', 'Sucharskiego 2, 35-225 Rzeszów', 4, 'Spotkanie rzeszowskiej grupy pasjonatów języka Java.'),
    (UUID(), 'Java Dev Talks #2', 'MEETING', '2023-02-07T18:00', 'polski', 'FREE', 'Rzeszów', 'WSIiZ', 'Sucharskiego 2, 35-225 Rzeszów', 4, 'Spotkanie rzeszowskiej grupy pasjonatów języka Java.'),
    (UUID(), 'Java Dev Talks #3', 'MEETING', '2023-03-07T18:00', 'polski', 'FREE', 'Rzeszów', 'WSIiZ', 'Sucharskiego 2, 35-225 Rzeszów', 4, 'Spotkanie rzeszowskiej grupy pasjonatów języka Java.'),
    (UUID(), 'Java Dev Talks #4', 'MEETING', '2023-04-04T18:00', 'polski', 'FREE', 'Rzeszów', 'WSIiZ', 'Sucharskiego 2, 35-225 Rzeszów', 4, 'Spotkanie rzeszowskiej grupy pasjonatów języka Java.'),
    (UUID(), 'Java Dev Talks #5', 'MEETING', '2023-05-02T18:00', 'polski', 'FREE', 'Rzeszów', 'WSIiZ', 'Sucharskiego 2, 35-225 Rzeszów', 4, 'Spotkanie rzeszowskiej grupy pasjonatów języka Java.'),
    (UUID(), 'Java Dev Talks #6', 'MEETING', '2023-06-06T18:00', 'polski', 'FREE', 'Rzeszów', 'WSIiZ', 'Sucharskiego 2, 35-225 Rzeszów', 4, 'Spotkanie rzeszowskiej grupy pasjonatów języka Java.'),
    (UUID(), 'Java Dev Talks #7', 'MEETING', '2023-07-04T18:00', 'polski', 'FREE', 'Rzeszów', 'WSIiZ', 'Sucharskiego 2, 35-225 Rzeszów', 4, 'Spotkanie rzeszowskiej grupy pasjonatów języka Java.'),
    (UUID(), 'Java Dev Talks #8', 'MEETING', '2023-08-01T18:00', 'polski', 'FREE', 'Rzeszów', 'WSIiZ', 'Sucharskiego 2, 35-225 Rzeszów', 4, 'Spotkanie rzeszowskiej grupy pasjonatów języka Java.'),
    (UUID(), 'Java Dev Talks #9', 'MEETING', '2023-09-05T18:00', 'polski', 'FREE', 'Rzeszów', 'WSIiZ', 'Sucharskiego 2, 35-225 Rzeszów', 4, 'Spotkanie rzeszowskiej grupy pasjonatów języka Java.'),
    (UUID(), 'Java Dev Talks #10', 'MEETING', '2023-10-03T18:00', 'polski', 'FREE', 'Rzeszów', 'WSIiZ', 'Sucharskiego 2, 35-225 Rzeszów', 4, 'Spotkanie rzeszowskiej grupy pasjonatów języka Java.'),
    (UUID(), 'Java Dev Talks #11', 'MEETING', '2023-11-07T18:00', 'polski', 'FREE', 'Rzeszów', 'WSIiZ', 'Sucharskiego 2, 35-225 Rzeszów', 4, 'Spotkanie rzeszowskiej grupy pasjonatów języka Java.'),
    (UUID(), 'Java Dev Talks #12', 'MEETING', '2023-12-05T18:00', 'polski', 'FREE', 'Rzeszów', 'WSIiZ', 'Sucharskiego 2, 35-225 Rzeszów', 4, 'Spotkanie rzeszowskiej grupy pasjonatów języka Java.');