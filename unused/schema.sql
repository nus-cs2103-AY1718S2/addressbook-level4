/*@@author EdwardKSG-unused*/
/* unused because relational database is not allowed in this project*/
CREATE TABLE user (
  /*NUS net id*/
  netid VARCHAR(8) PRIMARY KEY ,
  name VARCHAR(100) NOT NULL ,
  phone NUMERIC NOT NULL UNIQUE ,

  /*alternative email besides the default email which is auto-generated based on NETID*/
  email VARCHAR(50),

  /*year of study*/
  year NUMERIC,

  /*major course*/
  course VARCHAR(30),
  gender BOOLEAN,

  /*accumulated points earned by a user, as an evidence of the person's learning progress*/
  progress NUMERIC,
  grpid VARCHAR(6) REFERENCES group(grpid) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE question (
  index VARCHAR(10) PRIMARY KEY ,

  /*the question itself*/
  content VARCHAR(200) NOT NULL
);

CREATE TABLE answer_question (
  index VARCHAR(10) REFERENCES question(index) ON DELETE CASCADE ON UPDATE CASCADE ,

  /*answer filled by a user*/
  answer VARCHAR(1000),
  userid VARCHAR(8) REFERENCES user(netid) ON DELETE CASCADE ON UPDATE CASCADE ,
  PRIMARY KEY (index, userid)
);

/*project group of CS2103 and CS2103T*/
CREATE TABLE group (
  grpid VARCHAR(6) PRIMARY KEY ,
  member1 VARCHAR(8) REFERENCES user(netid) ON DELETE CASCADE ON UPDATE CASCADE ,
  member2 VARCHAR(8) REFERENCES user(netid) ON DELETE CASCADE ON UPDATE CASCADE ,
  member3 VARCHAR(8) REFERENCES user(netid) ON DELETE CASCADE ON UPDATE CASCADE ,
  member4 VARCHAR(8) REFERENCES user(netid) ON DELETE CASCADE ON UPDATE CASCADE ,

  /*in case we have extra students to squeeze in one project group*/
  member5 VARCHAR(8) REFERENCES user(netid) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE announcement (
  userid VARCHAR(8) REFERENCES user(netid) ON DELETE CASCADE ON UPDATE CASCADE ,

  /*time of creation*/
  time TIME,
  PRIMARY KEY (userid, time)
)
