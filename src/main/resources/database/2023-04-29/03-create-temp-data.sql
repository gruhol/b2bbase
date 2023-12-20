--liquibase formatted sql
--changeset dabrowskiw:12
INSERT INTO company (name, slug, type, legal_form, nip, regon, krs, email, phone, www_site, www_store, edi_cooperation, api_cooperation, product_file_cooperation, active)
VALUES
('Apple', 'apple', 'WHOLESALER', 'JDG', '1135518002', '175396539', NULL, 'kontakt@apple.pl', '660012896', 'https://www.apple.pl', 'https://www.apple.pl/store', TRUE, TRUE, TRUE, TRUE),
('Microsoft', 'microsoft', 'WHOLESALER', 'ZOO', '3557416561', '230014047', NULL, 'kontakt@microsoft.pl', '762012896', 'https://www.microsfot.pl', 'https://www.microsoft.pl/store', TRUE, TRUE, FALSE, TRUE),
('Orlen', 'orlen', 'WHOLESALER', 'SA', '8262564877', '177758604', NULL, 'kontakt@orlen.pl', '660012111', 'https://www.orlen.pl', 'https://www.orlen.pl/store', FALSE, FALSE, FALSE, TRUE),
('ThinkData', 'thinkdata', 'WHOLESALER', 'JDG', '4172962230', '414499476', NULL, 'kontakt@thinkdata.pl', '662078402', 'https://www.thinkdata.pl', 'https://www.thinkdata.pl/store', TRUE, TRUE, TRUE, TRUE),
('Internorm', 'Internorm', 'WHOLESALER', 'JDG', '5696317349', '972142820', NULL, 'kontakt@internorm.pl', '660012858', 'https://www.internorm.pl', 'https://www.internorm.pl/store', FALSE, TRUE, TRUE, TRUE),
('company1', 'company1', 'WHOLESALER', 'JDG', '5696317347', '972122820', NULL, 'kontakt@company1.pl', '660012858', 'https://www.internorm.pl', 'https://www.internorm.pl/store', FALSE, TRUE, TRUE, FALSE),
('company2', 'company2', 'WHOLESALER', 'JDG', '5646317347', '972122830', NULL, 'kontakt@company2.pl', '660012858', 'https://www.internorm.pl', 'https://www.internorm.pl/store', FALSE, TRUE, TRUE, TRUE),
('company3', 'company3', 'WHOLESALER', 'JDG', '5656317347', '972122840', NULL, 'kontakt@company3.pl', '660012858', 'https://www.internorm.pl', 'https://www.internorm.pl/store', FALSE, TRUE, TRUE, TRUE),
('company4', 'company4', 'WHOLESALER', 'JDG', '5666317347', '972122850', NULL, 'kontakt@company4.pl', '660012858', 'https://www.internorm.pl', 'https://www.internorm.pl/store', FALSE, TRUE, TRUE, TRUE),
('company5', 'company5', 'WHOLESALER', 'JDG', '5686317347', '972122860', NULL, 'kontakt@company5.pl', '660012858', 'https://www.internorm.pl', 'https://www.internorm.pl/store', FALSE, TRUE, TRUE, TRUE),
('company6', 'company6', 'WHOLESALER', 'JDG', '5616317347', '972122870', NULL, 'kontakt@company6.pl', '660012858', 'https://www.internorm.pl', 'https://www.internorm.pl/store', FALSE, TRUE, TRUE, TRUE);

INSERT INTO branch (name, headquarter, slug, post_code, city, street, house_number, office_number, email, phone, company_id)
VALUES
('Apple Warszawa', TRUE, 'apple-warszawa', '00-001', 'Warszawa', 'Marszałkowska', '12', '2', 'applewarszawa@apple.pl', '551234567', 1),
('Apple Kraków', FALSE, 'apple-krakow', '30-001', 'Kraków', 'Warszawska', '10', '2', 'applekrakow@apple.pl', '551234367', 1),
('Microsoft Warszawa', TRUE, 'microsoft-warszawa', '00-001', 'Warszawa', 'Marszałkowska', '15', '5', 'microsoft-waw@microsoft.pl', '551234456', 2),
('Orlen Warszawa', TRUE, 'orlen-warszawa', '00-002', 'Warszawa', 'Cicha', '125', '25', 'orlen-waw@orlen.pl', '551233456', 3),
('ThinkData', TRUE, 'orlen-warszawa', '04-002', 'Warszawa', 'Lukowska', '1', '25', 'warszawa@thinkdata.pl', '500233456', 4),
('Internorm', TRUE, 'internorm-warszawa', '04-002', 'Warszawa', 'Poprawna', '1', '25', 'warszawa@internorm.pl', '577233456', 5);