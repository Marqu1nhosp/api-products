INSERT INTO users (id, username, password, role) VALUES (100, 'ana@gmail.com', '$2a$12$BSWj2bLClN7wJniwoyQugOXd/mwaQnFhQ0RI5DKFn7yM24m3j6Eca', 'ROLE_ADMIN');
INSERT INTO users (id, username, password, role) VALUES (101, 'marcos@gmail.com', '$2a$12$BSWj2bLClN7wJniwoyQugOXd/mwaQnFhQ0RI5DKFn7yM24m3j6Eca', 'ROLE_ADMIN');
INSERT INTO users (id, username, password, role) VALUES (102, 'bia@gmail.com', '$2a$12$BSWj2bLClN7wJniwoyQugOXd/mwaQnFhQ0RI5DKFn7yM24m3j6Eca', 'ROLE_CLIENT');

INSERT INTO categories(id, name_category) VALUES(100, 'teste categoria');
INSERT INTO categories(id, name_category) VALUES(101, 'teste categoria 2');
INSERT INTO categories(id, name_category) VALUES(102, 'teste categoria 3');

INSERT INTO products(id, name_product, description, price, category_id) VALUES(100, 'teste produto', 'descrição do produto', 50.00, 100);
INSERT INTO products(id, name_product, description, price, category_id) VALUES(101, 'teste produto 2', 'descrição do produto 2', 100.50, 100);
INSERT INTO products(id, name_product, description, price, category_id) VALUES(102, 'teste produto 3', 'descrição do produto 3', 100.00, 100);