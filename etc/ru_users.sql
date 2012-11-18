CREATE TABLE ru_users
(
  ID int identity primary key,
  userName varchar(128) not null unique,
  firstName varchar(128),
  lastName varchar(128),
  email varchar(128),
  password varchar(128),
  gender int,
  created date
  check Gender >= 0 and Gender <=2
)

