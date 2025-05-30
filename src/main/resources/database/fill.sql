INSERT INTO genres (id, name) VALUES
                                 (1, 'Fiction'),
                                 (2, 'Non-Fiction'),
                                 (3, 'Science Fiction'),
                                 (4, 'Fantasy'),
                                 (5, 'History');


INSERT INTO authors (id, name) VALUES
                                                   (1, 'George Orwell'),
                                                   (2, 'J.K. Rowling'),
                                                   (3, 'Isaac Asimov'),
                                                   (4, 'Yuval Harari');

INSERT INTO books (id, title, description, author_id, genre_id, year, page_amount) VALUES
                                                                                       (1, '1984', 'Dystopian novel set in totalitarian society.', 1, 1, 1949, 200),
                                                                                       (2, 'Harry Potter and the Sorcerer`s Stone', 'First book in the Harry Potter series.', 2, 4, 1997, 150),
                                                                                       (3, 'Foundation', 'A science fiction epic.', 3, 3, 1951, 200),
                                                                                       (4, 'Sapiens', 'A brief history of humankind.', 4, 2, 2011, 300);
