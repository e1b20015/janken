CREATE TABLE users (
    id IDENTITY PRIMARY KEY,
    name VARCHAR NOT NULL
);
CREATE TABLE matches (
    id IDENTITY PRIMARY KEY,
    user1 INT,
    user2 INT,
    user1Hand VARCHAR NOT NULL,
    user2Hand VARCHAR NOT NULL
);
