--liquibase formatted sql
--changeset dabrowskiw:25
CREATE TABLE product_company (
    id INT AUTO_INCREMENT PRIMARY KEY,
    company_id INT NOT NULL,
    product_id INT NOT NULL,
    description TEXT,
    FOREIGN KEY (company_id) REFERENCES company(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE CASCADE,
    UNIQUE (company_id, product_id)
);