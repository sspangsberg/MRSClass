USE master;
GO
IF EXISTS(SELECT * FROM sys.databases WHERE name = 'MRS_2022')
BEGIN 
  DROP DATABASE MRS_2022;
END

CREATE DATABASE MRS_2022;
GO
USE MRS_2022;
GO
CREATE TABLE Movie (
    Id INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
    Title VARCHAR(255) NOT NULL,
    Year INT NOT NULL,
);
CREATE TABLE [User] (
    Id INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
    Name VARCHAR(255) NOT NULL
);
CREATE TABLE Rating (
    MovieId INT NOT NULL FOREIGN KEY REFERENCES Movie(Id),
    UserId INT NOT NULL FOREIGN KEY REFERENCES [User](Id),
    Score INT NOT NULL,
    CONSTRAINT PK_Rating PRIMARY KEY (MovieId,UserId)
);


-- Insert data
INSERT INTO Movie VALUES ('Notting Hill', 1997);
INSERT INTO Movie VALUES ('Terminator 2', 1991);
INSERT INTO Movie VALUES ('Tenet', 2020);

INSERT INTO [User] VALUES ('Preben');
INSERT INTO [User] VALUES ('Ib');

INSERT INTO Rating VALUES (1,1,8);
INSERT INTO Rating VALUES (2,1,1);
INSERT INTO Rating VALUES (2,2,6);
INSERT INTO Rating VALUES (3,2,9);

-- select all ratings from all user

SELECT m.Title, u.Id AS UserId, u.Name, r.Score 
FROM Movie m, Rating r, [User] u
WHERE r.MovieId = m.Id
AND r.UserId = u.Id
ORDER BY m.Title



