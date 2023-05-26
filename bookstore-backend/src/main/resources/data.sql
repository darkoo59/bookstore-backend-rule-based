INSERT INTO book (id, title, author, publisher, number_of_pages, genre, price, date_of_adding_to_bookstore, publish_date) VALUES
(1, 'Na Drini cuprija', 'Ivo Andric', 'Prosveta', 358, 7, 1399.99, '2022-11-20', '2022-12-23'),
(2, 'Dervis i smrt', 'Mesa Selimovic', 'Prosveta', 416, 7, 1299.99, '2023-04-23', '2022-08-09'),
(3, 'Koreni', 'Dobrica Cosic', 'Narodna knjiga', 391, 7, 1199.99, '2022-12-28', '2023-01-20'),
(4, 'Seobe', 'Milos Crnjanski', 'Prosveta', 384, 7, 1399.99, '2023-05-20', '2022-11-11'),
(5, 'Tvrdjava', 'Mesa Selimovic', 'Prosveta', 307, 7, 1099.99, '2023-05-21', '2022-10-26'),
(6, 'Zlocin i kazna', 'Fjodor Dostojevski', 'Dereta', 684, 7, 1599.99, '2023-05-21', '2022-05-14'),
(7, 'Ana Karenjina', 'Lav Tolstoj', 'Laguna', 810, 7, 1799.99, '2023-05-21', '2022-06-30'),
(8, 'Mrtve duse', 'Nikolaj Gogolj', 'Laguna', 400, 7, 999.99, '2023-05-21', '2022-02-23'),
(9, 'Besnilo', 'Borislav Pekic', 'Nolit', 440, 7, 1299.99, '2023-05-21', '2023-02-06'),
(10, 'Hajduk Stanko', 'Janko Veselinovic', 'Stubovi kulture', 279, 7, 899.99, '2023-05-21', '2022-09-02'),
(11, 'Angels and Demons', 'Dan Brown', 'Corgi Books', 620, 0, 1999.99, '2022-11-21', '2022-01-01'),
(12, 'The Shining', 'Stephen King', 'Doubleday', 447, 1, 1599.99, '2022-11-21', '2023-01-11'),
(13, 'Pride and Prejudice', 'Jane Austen', 'Wordsworth Classics', 304, 2, 799.99, '2022-11-21', '2022-04-22'),
(14, 'The Silence of the Lambs', 'Thomas Harris', 'Arrow Books', 430, 3, 1499.99, '2022-11-21', '2022-09-03'),
(15, 'Enders Game', 'Orson Scott Card', 'Tor Books', 352, 4, 1399.99, '2023-03-09', '2023-02-06'),
(16, 'The Fellowship of the Ring', 'J.R.R. Tolkien', 'HarperCollins Publishers', 479, 5, 1799.99, '2023-03-09', '2021-12-27'),
(17, 'The Elements of Style', 'William Strunk Jr.', 'Pearson', 105, 6, 3099.99, '2023-03-09', '2022-10-09');

INSERT INTO address (id,city,country,number,street) VALUES
    (1, 'Novi Sad', 'Serbia', '14', 'Bulevar Despota Stefana');

INSERT INTO user_ (id,email,firstname,gender,information,lastname,national_id,occupation,password,phone,address_id) VALUES
(1, 'darkoo59@gmail.com', 'Darko', 0, 'No more info', 'Selakovic', '1234567891011', 'Student', '$2a$10$qHMhAgTHBacCqhKyw6l4VOGT0gm9holiviRqSYiBdTJ8SFH.ZHG9q', '06123454654', 1);

INSERT INTO role (id,name) VALUES
(1, 'USER');

INSERT INTO person_roles(person_id, roles_id) VALUES
(1, 1);