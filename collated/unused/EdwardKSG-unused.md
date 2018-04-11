# EdwardKSG-unused
###### \remotePostgresConnection.java
``` java
//unused because relational database is not allowed in this project
/**
 * Tests connection to remote Postgres database server (Amazon AWS).
 */
public class remotePostgresConnectionTest {

    public static void main(String[] args) {

        //information for connecting to the remote postgresql server
        String url = "jdbc:postgresql://rds-postgresql-addressbook.cnpjakv2naou.ap-southeast-1.rds.amazonaws.com:5434/addressbook";
        String user = "anminkang";
        String password = "addressbook";

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (Connection con = DriverManager.getConnection(url, user, password);
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT VERSION()")) {

            if (rs.next()) {
                System.out.println(rs.getString(1));
            }

        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(remotePostgresConnectionTest.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
}
```
###### \schema.sql
``` sql
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
```
