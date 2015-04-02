/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bibliothek;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sayan
 */
public class Librarian {

    private static final String url = "jdbc:mysql://10.136.158.28:3306/library";
    private static final String user = "root";
    private static final String password = "qwerty";


    // TODO :  Modify records
    public void create_record(type memberType, int ID, String name, String phoneNo, String address) {
        //mbr = new Member(memberType, ID, name, phoneNo, address);
        int bookLimit = 0;
        int duration = 0;
        if (memberType == type.underGraduate) {
            bookLimit = 2;
            duration = 1;
        } else if (memberType == type.postGraduate) {
            bookLimit = 4;
            duration = 1;
        } else if (memberType == type.researchScholars) {
            bookLimit = 6;
            duration = 3;
        } else if (memberType == type.faculty) {
            bookLimit = 10;
            duration = 6;
        }
        try {
            Statement stmt = null;

            Connection con = DriverManager.getConnection(url, user, password);
            System.out.println("Success");
            stmt = con.createStatement();
            String add = "INSERT into members VALUES ("
                    + memberType + ", "
                    + ID + ", '"
                    + name + "', '"
                    + phoneNo + "', '"
                    + address + "', "
                    + 0.0 + ", "
                    + bookLimit + ", "
                    + duration + ", "
                    + null + ")";
            stmt.executeUpdate(add);
            System.out.println("Member Added!!");
        } catch (SQLException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void delete_record(int ID) {
        try {
            Statement stmt = null;

            Connection con = DriverManager.getConnection(url, user, password);
            System.out.println("Success");
            stmt = con.createStatement();
            String add = "DELETE from members WHERE ID = " + ID;
            stmt.executeUpdate(add);
            System.out.println("Member Deleted!!");
        } catch (SQLException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void create_book(String ISBN, String name, String publisher, int yearOfPurchase, int rackNo, double price, int copies) {
        ArrayList<SubBook> copyDetails = new ArrayList<>();
        for (int i = 0; i < copies; i++) {
            SubBook newCopy = new SubBook(i + 1, false);
            copyDetails.add(newCopy);
        }
        try {
            //book = new Book(ISBN, name, publisher, yearOfPurchase, rackNo, price);
            Statement stmt = null;

            Connection con = DriverManager.getConnection(url, user, password);
            System.out.println("Success");
            stmt = con.createStatement();
            String add = "INSERT into books VALUES ('"
                    + ISBN + "', '"
                    + name + "', '"
                    + publisher + "', "
                    + yearOfPurchase + ", "
                    + rackNo + ", "
                    + copies + ", "
                    + (copies + 1) + ", "
                    + price + ", "
                    + false + ", '"
                    + copyDetails + "', "
                    + null + ")";
            stmt.executeUpdate(add);
            System.out.println("Book Added!!");

        } catch (SQLException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void delete_book(String ISBN) {
        try {
            Statement stmt = null;

            Connection con = DriverManager.getConnection(url, user, password);
            System.out.println("Success");
            stmt = con.createStatement();
            String add = "DELETE from books WHERE ISBN = '" + ISBN + "'";
            stmt.executeUpdate(add);
            System.out.println("Book Deleted!!");
        } catch (SQLException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void create_subBook(String ISBN, int copies) {
        try {
            Statement stmt = null;

            Connection con = DriverManager.getConnection(url, user, password);
            System.out.println("Success");
            stmt = con.createStatement();
            String add = "SELECT * FROM books WHERE ISBN = '" + ISBN + "'";
            ResultSet rs = stmt.executeQuery(add);
            rs.next();
            ArrayList<SubBook> copyDetails = (ArrayList<SubBook>) rs.getBlob("copyDetails");
            int countID = rs.getInt("countID");
            for (int i = 0; i < copies; i++) {
                SubBook newCopy = new SubBook(countID + i, false);
                copyDetails.add(newCopy);
            }
            countID = countID + copies;
            rs.close();
            add = "UPDATE books SET countID = " + countID + ", copyDetails = '" + copyDetails + "' WHERE ISBN = '" + ISBN + "'";
            stmt.executeUpdate(add);
            System.out.println("SUBBook Created!!");
        } catch (SQLException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void delete_subBook(String ISBN, int ID) {
        try {
            Statement stmt = null;
            Connection con = DriverManager.getConnection(url, user, password);
            System.out.println("Success");
            stmt = con.createStatement();
            String add = "SELECT * FROM books WHERE ISBN = '" + ISBN + "'";
            ResultSet rs = stmt.executeQuery(add);
            rs.next();
            ArrayList<SubBook> copyDetails = (ArrayList<SubBook>) rs.getBlob("copyDetails");
            Iterator itr = copyDetails.iterator();
            while (itr.hasNext()) {
                SubBook sb = (SubBook) itr.next();
                if (sb.getID() == ID) {
                    itr.remove();
                    break;
                }
            }

            rs.close();
            add = "UPDATE books SET copyDetails = '" + copyDetails + "' WHERE ISBN = '" + ISBN + "'";
            stmt.executeUpdate(add);
            System.out.println("SUBBook Deleted!!");
        } catch (SQLException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
