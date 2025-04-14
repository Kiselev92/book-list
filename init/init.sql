   CREATE TABLE books_list(
       id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
       vendorcode VARCHAR(100) NOT NULL DEFAULT 'Неизвестен',
       title VARCHAR (100) NOT NULL,
       year INT NOT NULL,
       brand VARCHAR (100),
       stock INT,
       price INT NOT NULL
);
