--liquibase formatted sql
--changeset dabrowskiw:27
CREATE TABLE product_image (
    id INT AUTO_INCREMENT PRIMARY KEY,
    url VARCHAR(255) NOT NULL,
    product_id INT NULL,
    company_product_id INT NULL,
    FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE CASCADE,
    FOREIGN KEY (company_product_id) REFERENCES product_company(id) ON DELETE CASCADE
);