/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bibliothek;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sayan
 */
public class Initialize {

    private static final String url = "jdbc:mysql://10.109.52.217:3306";
    private static final String user = "root";
    private static final String password = "qwerty";

    public void init() {
        try {
            Statement stmt = null;

            Connection con = DriverManager.getConnection(url, user, password);
            System.out.println("Success");
            stmt = con.createStatement();
            String query = "SELECT COUNT(*) FROM information_schema.tables WHERE table_schema = 'library'";
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            boolean exists = rs.getInt("COUNT(*)") > 0;
            if (!exists) {
                query = "CREATE DATABASE library";
                stmt.executeUpdate(query);
                System.out.println("Database Created Successfully!!");
                con = DriverManager.getConnection(url+"/library", user, password);
                System.out.println("Success");
                stmt = con.createStatement();

                String sql = "CREATE TABLE books "
                        + "(ISBN VARCHAR(20), "
                        + " name VARCHAR(255), "
                        + " publisher VARCHAR(255), "
                        + " yearOfPurchase INTEGER, "
                        + " rackNo INTEGER, "
                        + " onShelf INTEGER, "
                        + " countID INTEGER, "
                        + " price DOUBLE, "
                        + " isReserved BOOLEAN, "
                        + " copyDetails LONGBLOB, "
                        + " reserveList LONGBLOB)";

                stmt.executeUpdate(sql);
                System.out.println("Created table in given database...");
                
                sql = "CREATE TABLE members "
                        + "(memberType VARCHAR(20), "
                        + " ID INTEGER, "
                        + " name VARCHAR(255), "
                        + " phoneNo VARCHAR(20), "
                        + " address VARCHAR(255), "
                        + " fine DOUBLE, "
                        + " bookLimit INTEGER, "
                        + " duration INTEGER, "
                        + " booksIssued LONGBLOB)";
                
                stmt.executeUpdate(sql);
                System.out.println("Created table in given database...");
                
                sql = "CREATE TABLE countMembers (name VARCHAR(10), ID INTEGER)";
                stmt.executeUpdate(sql);
                sql = "INSERT into countMembers VALUES ('memberID', 0)";
                stmt.executeUpdate(sql);
                
                System.out.println("Created table in given database...");
            }
// Close connection, statement, and result set.
        } catch (SQLException ex) {
            Logger.getLogger(Initialize.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
