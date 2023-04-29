--liquibase formatted sql
--changeset dabrowskiw:8
INSERT INTO company (name, slug, type, legal_form, nip, regon, krs, email, phone, www_site, www_store, edi_cooperation, api_cooperation, product_file_cooperation)
VALUES
('Apple', 'apple', 'WHOLESALER', 'JDG', '1135518002', '175396539', NULL, 'kontakt@apple.pl', '660012896', 'https://www.apple.pl', 'https://www.apple.pl/store', TRUE, TRUE, TRUE),
('Microsoft', 'microsoft', 'WHOLESALER', 'ZOO', '3557416561', '230014047', NULL, 'kontakt@microsoft.pl', '762012896', 'https://www.microsfot.pl', 'https://www.microsoft.pl/store', TRUE, TRUE, FALSE),
('Orlen', 'orlen', 'WHOLESALER', 'SA', '8262564877', '177758604', NULL, 'kontakt@orlen.pl', '660012111', 'https://www.orlen.pl', 'https://www.orlen.pl/store', FALSE, FALSE, FALSE),
('ThinkData', 'thinkdata', 'WHOLESALER', 'JDG', '4172962230', '414499476', NULL, 'kontakt@thinkdata.pl', '662078402', 'https://www.thinkdata.pl', 'https://www.thinkdata.pl/store', TRUE, TRUE, TRUE),
('Internorm', 'Internorm', 'WHOLESALER', 'JDG', '5696317349', '972142820', NULL, 'kontakt@internorm.pl', '660012858', 'https://www.internorm.pl', 'https://www.internorm.pl/store', FALSE, TRUE, TRUE);