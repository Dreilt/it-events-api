--liquibase formatted sql
--changeset Dreilt:0007-1
UPDATE `app_user`
SET date_of_birth='1995-10-06', city = 'Serwerownia', bio = 'Cześć! Jestem administratorem tego serwisu.'
WHERE email = 'admin@example.com';
--changeset Dreilt:0007-2
UPDATE `app_user`
SET date_of_birth='1995-10-06', city = 'Rzeszów', bio = 'Cześć! Nazywam się Jan Kowalski i jestem z Rzeszowa. Obecnie jestem na trzecim roku informatyki na Politechnice Rzeszowskiej.'
WHERE email = 'jankowalski@example.com';
--changeset Dreilt:0007-3
UPDATE `app_user`
SET date_of_birth='1996-02-02', city = 'Kraków', bio = 'Cześć! Nazywam się Patryk Kowalski i jestem z Krakowa. Obecnie jestem na trzecim roku informatyki na Politechnice Krakowskiej.'
WHERE email = 'patrykkowalski@example.com';
--changeset Dreilt:0007-4
UPDATE `app_user`
SET date_of_birth='1997-03-04', city = 'Rzeszów', bio = 'Cześć! Nazywam się Jan Nowak i jestem z Rzeszowa. Obecnie jestem na trzecim roku informatyki w Wyższej Szkole Informatyki i Zarządzania w Rzeszowie.'
WHERE email = 'jannowak@example.com';
--changeset Dreilt:0007-5
UPDATE `app_user`
SET date_of_birth='2000-01-18', city = 'Kraków', bio = 'Cześć! Nazywam się Patryk Nowak i jestem z Krakowa. Obecnie jestem na trzecim roku informatyki na Uniwersytecie Jagiellońskim.'
WHERE email = 'patryknowak@example.com';
--changeset Dreilt:0007-6
UPDATE `app_user`
SET date_of_birth='1996-02-02', city = 'Rzeszów', bio = 'Cześć! Nazywam się Piotr Wysocki i jestem z Rzeszowa. Obecnie jestem na czwartym roku informatyki na Politechnice Rzeszowskiej.'
WHERE email = 'piotrwysocki@example.com';
--changeset Dreilt:0007-7
UPDATE `app_user`
SET date_of_birth='2003-11-11', city = 'Kraków', bio = 'Cześć! Nazywam się Dawid Polak i jestem z Krakowa. Obecnie jestem na czwartym roku informatyki na Politechnice Krakowskiej.'
WHERE email = 'dawidpolak@example.com';
--changeset Dreilt:0007-8
UPDATE `app_user`
SET date_of_birth='2002-10-06', city = 'Rzeszów', bio = 'Cześć! Nazywam się Zuzanna Kowalska i jestem z Rzeszowa. Obecnie jestem na trzecim roku informatyki w Wyższej Szkole Informatyki i Zarządzania w Rzeszowie.'
WHERE email = 'zuzannakowalska@example.com';
--changeset Dreilt:0007-9
UPDATE `app_user`
SET date_of_birth='1999-06-06', city = 'Kraków', bio = 'Cześć! Nazywam się Piotr Michalik i jestem z Krakowa. Obecnie jestem na czwartym roku informatyki na Uniwersytecie Jagiellońskim.'
WHERE email = 'piotrmichalik@example.com';
--changeset Dreilt:0007-10
UPDATE `app_user`
SET date_of_birth='2003-10-06', city = 'Rzeszów', bio = 'Cześć! Nazywam się Dawid Dąbrowski i jestem z Rzeszowa. Obecnie jestem na piątym roku informatyki na Politechnice Rzeszowskiej.'
WHERE email = 'dawiddabrowski@example.com';
--changeset Dreilt:0007-11
UPDATE `app_user`
SET date_of_birth='2000-05-03', city = 'Kraków', bio = 'Cześć! Nazywam się Daniel Dąbrowski i jestem z Krakowa. Obecnie jestem na piątym roku informatyki na Politechnice Krakowskiej.'
WHERE email = 'danieldabrowski@example.com';
--changeset Dreilt:0007-12
UPDATE `app_user`
SET date_of_birth='2000-07-05', city = 'Rzeszów', bio = 'Cześć! Nazywam się Maria Nowak i jestem z Rzeszowa. Obecnie jestem na trzecim roku informatyki w Wyższej Szkole Informatyki i Zarządzania w Rzeszowie.'
WHERE email = 'marianowak@example.com';