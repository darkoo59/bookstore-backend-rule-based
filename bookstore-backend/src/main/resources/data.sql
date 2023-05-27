INSERT INTO book (id, title, author, publisher, number_of_pages, genre, price) VALUES
(1, 'Na Drini cuprija', 'Ivo Andric', 'Prosveta', 358, 7, 1400.00),
(2, 'Dervis i smrt', 'Mesa Selimovic', 'Prosveta', 416, 7, 1300.00),
(3, 'Koreni', 'Dobrica Cosic', 'Narodna knjiga', 391, 7, 1200.00),
(4, 'Seobe', 'Milos Crnjanski', 'Prosveta', 384, 7, 1400.00),
(5, 'Tvrdjava', 'Mesa Selimovic', 'Prosveta', 307, 7, 1100.00),
(6, 'Zlocin i kazna', 'Fjodor Dostojevski', 'Dereta', 684, 7, 1600.00),
(7, 'Ana Karenjina', 'Lav Tolstoj', 'Laguna', 810, 7, 1800.00),
(8, 'Mrtve duse', 'Nikolaj Gogolj', 'Laguna', 400, 7, 1000.00),
(9, 'Besnilo', 'Borislav Pekic', 'Nolit', 440, 7, 1300.00),
(10, 'Hajduk Stanko', 'Janko Veselinovic', 'Stubovi kulture', 279, 7, 900.00),
(11, 'Angels and Demons', 'Dan Brown', 'Corgi Books', 620, 0, 2000.00),
(12, 'The Shining', 'Stephen King', 'Doubleday', 447, 1, 3600.00),
(13, 'Pride and Prejudice', 'Jane Austen', 'Wordsworth Classics', 304, 2, 800.00),
(14, 'The Silence of the Lambs', 'Thomas Harris', 'Arrow Books', 430, 3, 1500.00),
(15, 'Enders Game', 'Orson Scott Card', 'Tor Books', 352, 4, 1400.00),
(16, 'The Fellowship of the Ring', 'J.R.R. Tolkien', 'HarperCollins Publishers', 479, 5, 1800.00),
(17, 'The Elements of Style', 'William Strunk Jr.', 'Pearson', 105, 6, 3100.00);

INSERT INTO address (id,city,country,number,street) VALUES
    (1, 'Novi Sad', 'Serbia', '14', 'Bulevar Despota Stefana');

INSERT INTO user_ (id,email,firstname,gender,information,lastname,national_id,occupation,password,phone,address_id) VALUES
(1, 'darkoo59@gmail.com', 'Darko', 0, 'No more info', 'Selakovic', '1234567891011', 'Student', '$2a$10$qHMhAgTHBacCqhKyw6l4VOGT0gm9holiviRqSYiBdTJ8SFH.ZHG9q', '06123454654', 1);

INSERT INTO role (id,name) VALUES
(1, 'USER');

INSERT INTO person_roles(person_id, roles_id) VALUES
(1, 1);