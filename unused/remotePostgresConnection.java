package seedu.address.storage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

//@@author EdwardKSG-unused
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
