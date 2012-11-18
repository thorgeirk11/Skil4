
CREATE TABLE ru_users
(
  ID int identity primary key,
  userName varchar(128) not null unique,
  Name varchar(128),
  email varchar(128),
  pass varchar(128)
)
Create Table ru_followers
(
	ID int identity primary key,
	UserID int not null references ru_users(ID),
	UserWhoFollows int not null references ru_users(ID),
)
CREATE TABLE ru_boards
(
  ID int identity primary key,
  boardName varchar(128) not null,
  userName varchar(128) not null references ru_users(userName),
  category varchar(128),
  created datetime default GETDATE(),
  unique (boardName,userName)
)
CREATE TABLE ru_pins
(
  ID int identity primary key,
  link varchar(512),
  image varchar(512),
  description varchar(1024),
  created datetime default GETDATE(),
  boardID int references ru_boards(ID),
)
Create table ru_pin_likes
(
	ID int identity primary key,
	pinID int not null references ru_pins(ID),
	userID int not null references ru_users(ID),
	unique (pinID,userID)
)


select * from ru_users
select * from ru_boards
select * from ru_pins
select * from ru_followers
select * from ru_pin_likes

SELECT ru_users.* FROM ru_users 
inner join ru_followers on ru_followers.userID = ru_users.ID
WHERE ru_followers.UserWhoFollows = 2


insert into ru_users (userName,Name,Email,pass)
values  ('gudnyg11@ru.is','Gu�n�','gudnyg11@ru.is','asdasd')

insert into ru_followers (UserID,UserWhoFollows)
values (2,1)


insert into ru_boards (BoardName,userName,category)
values  ('Robots','thorgeirk11@ru.is','Technolgy')
insert into ru_boards (BoardName,userName,category)
values  ('Civil war','thorgeirk11@ru.is','History')
insert into ru_boards (BoardName,userName,category)
values  ('Recipes','thorgeirk11@ru.is','Food')
insert into ru_boards (BoardName,userName,category)
values  ('Harry Potter','thorgeirk11@ru.is','Books')



insert into ru_pins (link,[image],[description], boardID)
values  ('http://www.wikipedia.org/theCivilWar','http://www.soldierstudies.org/images/webquest/civil%20war%20soldiers.jpg','The American Civil War (1861�1865), in the United States often referred to as simply the Civil War and sometimes called the "War Between the States"',4)
insert into ru_pins (link,[image],[description], boardID)
values  ('http://www.MacCheese.com/','http://img4-2.myrecipes.timeinc.net/i/recipes/ct/04/09/baked-macaroni-ct-1585215-l.jpg','50% makkaronies and 50% cheese and cook it together',5)
insert into ru_pins (link,[image],[description], boardID)
values  ('http://www.imdb.com/title/tt0926084/','http://jwwartick.files.wordpress.com/2011/11/hp-dh2.jpeg','The first half of the seventh movie in the Harry Potter series',6)
insert into ru_pins (link,[image],[description], boardID)
values  ('http://www.imdb.com/title/tt0373889/','http://www.tribute.ca/harrypotter/images/HP5/harry_potter_and_the_order_of_the_phoenix_poster3.jpg', 'The fifth movie in the Harry Potter series',6)
insert into ru_pins (link,[image],[description], boardID)
values  ('http://www.tviund.com','http://tviund.com/wp-content/uploads/2010/09/Tv%C3%ADundL%C3%B3g%C3%B3400DPI-1.png', 'Nemendaf�lag t�lvunarfr��i � HR',3)



delete from ru_users
delete from ru_boards
delete from ru_pins
delete from ru_followers
delete from ru_pin_likes

drop table ru_users
drop table ru_boards
drop table ru_pins
drop table ru_followers
drop table ru_pin_likes