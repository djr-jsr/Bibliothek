/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bibliothek;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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

    private static final String url = "jdbc:mysql://10.109.52.217:3306/library";
    private static final String user = "root";
    private static final String password = "qwerty";

    // TODO :  Modify records
    public void create_record(type memberType, int ID, String name, String phoneNo, String address) {
        //mbr = new Member(memberType, ID, name, phoneNo, address);
        ArrayList<IssueDetails_member> issue_member = new ArrayList<>();
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
            //stmt = con.createStatement();
            System.out.println("Hello");
            String add = "INSERT into members VALUES ('"
                    + memberType.toString() + "', "
                    + ID + ", '"
                    + name + "', '"
                    + phoneNo + "', '"
                    + address + "', "
                    + 0.0 + ", "
                    + bookLimit + ", "
                    + duration + ", "
                    + "?" + ")";
            PreparedStatement pstmt = con.prepareStatement(add);
            pstmt.setObject(1, issue_member);
            pstmt.executeUpdate();
            //stmt.executeUpdate(add);
            System.out.println("Member Added!!");
        } catch (SQLException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void delete_record(int ID) throws IOException, ClassNotFoundException {
        try {
            Statement stmt = null;
            boolean flag = false;
            Connection con = DriverManager.getConnection(url, user, password);
            System.out.println("Success");

            stmt = con.createStatement();
            String add = "SELECT * FROM members WHERE ID = " + ID;
            ResultSet rs = stmt.executeQuery(add);
            rs.next();
            byte[] buf = rs.getBytes("booksIssued");
            ObjectInputStream o = new ObjectInputStream(new ByteArrayInputStream(buf));

            ArrayList<IssueDetails_member> bookIssued = (ArrayList<IssueDetails_member>) o.readObject();
            Iterator itr = bookIssued.iterator();
            while (itr.hasNext()) {
                IssueDetails_member mem = (IssueDetails_member) itr.next();
                if (mem.getReturnDate().equalsIgnoreCase("")) {
                    flag = true;
                }
            }
            if (!flag) {
                add = "DELETE from members WHERE ID = " + ID;
                stmt.executeUpdate(add);
                System.out.println("Member Deleted!!");
            }
            else{
                System.out.println("Member can't be deleted!!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void create_book(String ISBN, String name, String publisher, int yearOfPurchase, int rackNo, double price, int copies) {
        ArrayList<SubBook> copyDetails = new ArrayList<>();
        ArrayList<Integer> reserveList = new ArrayList<>();
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
                    + false + ", "
                    + "?" + ", "
                    + "?" + ")";
            PreparedStatement statement = con.prepareStatement(add);
            statement.setObject(1, copyDetails);
            statement.setObject(2, reserveList);
            statement.executeUpdate();
            //stmt.executeUpdate(add);
            System.out.println("Book Added!!");

        } catch (SQLException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void delete_book(String ISBN) throws IOException, ClassNotFoundException {
        try {
            Statement stmt = null;

            Connection con = DriverManager.getConnection(url, user, password);
            System.out.println("Success");
            stmt = con.createStatement();
            String add = "SELECT * FROM books WHERE ISBN = '" + ISBN + "'";
            ResultSet rs = stmt.executeQuery(add);
            rs.next();
            int onShelf = rs.getInt("onShelf");
            byte[] buf = rs.getBytes("copyDetails");
            ObjectInputStream o = new ObjectInputStream(new ByteArrayInputStream(buf));

            ArrayList<SubBook> copyDetails = (ArrayList<SubBook>) o.readObject();
            if (onShelf == copyDetails.size()) {
                add = "DELETE from books WHERE ISBN = '" + ISBN + "'";
                stmt.executeUpdate(add);
                System.out.println("Book Deleted!!");
            }

        } catch (SQLException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void create_subBook(String ISBN, int copies) throws IOException, ClassNotFoundException {
        try {
            Statement stmt = null;

            Connection con = DriverManager.getConnection(url, user, password);
            System.out.println("Success");
            stmt = con.createStatement();
            String add = "SELECT * FROM books WHERE ISBN = '" + ISBN + "'";
            ResultSet rs = stmt.executeQuery(add);
            rs.next();
            int onShelf = rs.getInt("onShelf");
            byte[] buf = rs.getBytes("copyDetails");
            ObjectInputStream o = new ObjectInputStream(new ByteArrayInputStream(buf));

            ArrayList<SubBook> copyDetails = (ArrayList<SubBook>) o.readObject();
            System.out.println(onShelf);

            int countID = rs.getInt("countID");
            for (int i = 0; i < copies; i++) {
                SubBook newCopy = new SubBook(countID + i, false);
                copyDetails.add(newCopy);
            }
            countID = countID + copies;
            onShelf = onShelf + copies;
            rs.close();
            add = "UPDATE books SET countID = " + countID
                    + ", copyDetails = " + "?"
                    + ", onShelf = " + onShelf
                    + " WHERE ISBN = '" + ISBN + "'";
            PreparedStatement statement = con.prepareStatement(add);
            statement.setObject(1, copyDetails);
            statement.executeUpdate();
            //stmt.executeUpdate(add);
            System.out.println("SUBBook Created!!");
        } catch (SQLException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void delete_subBook(String ISBN, int ID) throws IOException, ClassNotFoundException {
        try {
            boolean flag = false;
            Statement stmt = null;
            Connection con = DriverManager.getConnection(url, user, password);
            System.out.println("Success");
            stmt = con.createStatement();
            String add = "SELECT * FROM books WHERE ISBN = '" + ISBN + "'";
            ResultSet rs = stmt.executeQuery(add);
            rs.next();
            int onShelf = rs.getInt("onShelf");
            byte[] buf = rs.getBytes("copyDetails");
            ObjectInputStream o = new ObjectInputStream(new ByteArrayInputStream(buf));

            ArrayList<SubBook> copyDetails = (ArrayList<SubBook>) o.readObject();
            Iterator itr = copyDetails.iterator();
            while (itr.hasNext()) {
                SubBook sb = (SubBook) itr.next();
                if (sb.getID() == ID && sb.isIsIssued() == false) {
                    onShelf--;
                    flag = true;
                    itr.remove();
                    break;
                }
            }

            rs.close();
            add = "UPDATE books SET copyDetails = " + "?"
                    + ", onShelf = " + onShelf
                    + " WHERE ISBN = '" + ISBN + "'";
            PreparedStatement statement = con.prepareStatement(add);
            statement.setObject(1, copyDetails);
            statement.executeUpdate();
            //stmt.executeUpdate(add);
            if (flag == true) {
                System.out.println("SUBBook Deleted!!");
            } else {
                System.out.println("Book Not Deleted!!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
