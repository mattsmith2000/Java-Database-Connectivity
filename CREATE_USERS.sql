USE travel;
DROP TABLE IF EXISTS users;
CREATE TABLE users(username VARCHAR(12) PRIMARY KEY, password VARCHAR(30), name VARCHAR(25), access VARCHAR(7));
INSERT INTO users VALUES('pef468', 'pass123', 'Pattric Fern', 'Admin');
INSERT INTO users VALUES('jer164', 'bestpass123', 'Jason Richards', 'Editor');
INSERT INTO users VALUES('jpc462', 'dpdh', 'Jerry Cantrell', 'General');