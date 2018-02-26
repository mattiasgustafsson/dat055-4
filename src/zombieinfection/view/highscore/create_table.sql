DROP TABLE IF EXISTS Highscores CASCADE;

CREATE TABLE Highscores(
    name TEXT,
    score INT NOT NULL CHECK (score > 0),
    PRIMARY KEY (name)
);


CREATE USER zombie WITH login; //kolla med server
ALTER USER zombie WITH password '<Hn$dY3._BG2M7#N'; 

GRANT SELECT, INSERT, UPDATE ON Highscores TO zombie; 

INSERT INTO highscores VALUES('Batman',188); 
INSERT INTO highscores VALUES('Superman',234); 
INSERT INTO highscores VALUES('Wonder Woman',78); 