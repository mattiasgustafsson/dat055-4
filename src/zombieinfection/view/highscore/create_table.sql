DROP TABLE IF EXISTS Highscores CASCADE;

CREATE TABLE Highscores(
	name TEXT,
	score INT NOT NULL CHECK (score > 0),
	PRIMARY KEY (name)
);


CREATE USER postgres WITH login;

GRANT SELECT, INSERT, UPDATE ON Highscores TO postgres; 

INSERT INTO highscores VALUES('Batman',188); 
INSERT INTO highscores VALUES('Superman',234); 
INSERT INTO highscores VALUES('Wonder Woman',78); 