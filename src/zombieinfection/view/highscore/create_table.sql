DROP TABLE IF EXISTS Highscores CASCADE;

CREATE TABLE Highscores(
	name TEXT,
	score INT NOT NULL CHECK (score > 0),
	PRIMARY KEY (name)
);