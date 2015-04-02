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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sayan
 */
public class LibrarySystem {

    private static final String url = "jdbc:mysql://10.136.158.28:3306/library";
    private static final String user = "root";
    private static final String password = "qwerty";

    //private static ArrayList<Book> bookList;
    //private static ArrayList<Member> memberList;

    public void statistics() {

    }

    public void print() {

    }

    public void display() {

    }

    public void penalty() {

    }

    public void issue(String ISBN, int ID) {
        try {
            int flag = 0;
            Statement stmt = null;
            Connection con = DriverManager.getConnection(url, user, password);
            System.out.println("Success");
            stmt = con.createStatement();
            String add = "SELECT * FROM books WHERE ISBN = '" + ISBN + "'";
            ResultSet rs = stmt.executeQuery(add);
            rs.next();
            boolean isReserved = rs.getBoolean("isReserved");
            int onShelf = rs.getInt("onShelf");
            ArrayList<SubBook> copyDetails = (ArrayList<SubBook>) rs.getBlob("copyDetails");
            ArrayList<Integer> reserveList = (ArrayList<Integer>) rs.getBlob("reserveList");

            String mem = "SELECT * FROM members WHERE ID = " + ID;
            ResultSet ms = stmt.executeQuery(mem);
            ms.next();
            double fine = ms.getDouble("fine");
            int bookLimit = ms.getInt("bookLimit");
            ArrayList<IssueDetails_member> booksIssued = (ArrayList<IssueDetails_member>) ms.getBlob("booksIssued");

            //if fine is not paid or max books issued
            if (fine > 0 || booksIssued.size() >= bookLimit) {
                return;
            }

            //for non-reserved books
            if (isReserved == false && onShelf > 0) {
                Iterator itr = copyDetails.iterator();
                while (itr.hasNext()) {
                    SubBook sb = (SubBook) itr.next();
                    if (!sb.isIsIssued()) {
                        onShelf--;
                        itr.remove();
                        sb.setIsIssued(true);
                        ArrayList<IssueDetails_book> issuedMembers = sb.getIssuedMembers();
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        String date = sdf.format(Calendar.getInstance().getTime());
                        IssueDetails_book temp = new IssueDetails_book(ID, date);
                        issuedMembers.add(temp);
                        sb.setIssuedMembers(issuedMembers);

                        IssueDetails_member memtemp = new IssueDetails_member(ISBN, date);
                        booksIssued.add(memtemp);
                        copyDetails.add(sb);
                        break;
                    }
                }
            }

            //if book is reserved
            boolean member_exists = false;
            if (isReserved == true && onShelf > 0) {
                Iterator iter = reserveList.iterator();
                while(iter.hasNext())
                {
                    int x = (int) iter.next();
                    if(x == ID)
                    {
                        member_exists = true;
                        iter.remove();
                        break;
                    }
                }
                 
                if(!member_exists)
                    return;
                
                Iterator itr = copyDetails.iterator();
                while (itr.hasNext()) {
                    SubBook sb = (SubBook) itr.next();
                    if (!sb.isIsIssued()) {
                        onShelf--;
                        itr.remove();
                        sb.setIsIssued(true);
                        ArrayList<IssueDetails_book> issuedMembers = sb.getIssuedMembers();
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        String date = sdf.format(Calendar.getInstance().getTime());
                        IssueDetails_book temp = new IssueDetails_book(ID, date);
                        issuedMembers.add(temp);
                        sb.setIssuedMembers(issuedMembers);
                        if(reserveList.isEmpty())
                            isReserved = false;

                        IssueDetails_member memtemp = new IssueDetails_member(ISBN, date);
                        booksIssued.add(memtemp);
                        copyDetails.add(sb);
                        break;
                    }
                }
            }
            rs.close();
            ms.close();
            add = "UPDATE books SET reserveList = '" + reserveList 
                    + "', isReserved = " + isReserved
                    + ", copyDetails = '" + copyDetails
                    + "', onShelf = " + onShelf
                    + " WHERE ISBN = '" + ISBN + "'";
            stmt.executeUpdate(add);
            
            mem = "UPDATE members SET booksIssued = '" + booksIssued + "' WHERE ID = " + ID;
            stmt.executeUpdate(mem);
            System.out.println("Book Issued!!");
        } catch (SQLException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void returned(String ISBN, int subID, int ID) {

    }

    public void reserve(String ISBN, int ID) {
        try {
            int flag = 0;
            Statement stmt = null;
            Connection con = DriverManager.getConnection(url, user, password);
            System.out.println("Success");
            stmt = con.createStatement();
            String add = "SELECT * FROM books WHERE ISBN = '" + ISBN + "'";
            ResultSet rs = stmt.executeQuery(add);
            rs.next();
            boolean isReserved = rs.getBoolean("isReserved");
            isReserved = true;
            ArrayList<Integer> reserveList = (ArrayList<Integer>) rs.getBlob("reserveList");
            Iterator itr = reserveList.iterator();
            while (itr.hasNext()) {
                int sb = (int) itr.next();
                if (sb == ID) {
                    flag = 1;
                    break;
                }
            }
            if (flag == 0) {
                reserveList.add(ID);
            }
            rs.close();
            add = "UPDATE books SET reserveList = '" + reserveList 
                    + "', isReserved = " + isReserved 
                    + " WHERE ISBN = '" + ISBN + "'";
            stmt.executeUpdate(add);
            System.out.println("Book Reserved!!");
        } catch (SQLException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
