/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bibliothek;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author AKASH
 */
public class Book {
    private String ISBN;
    private String name;
    private String publisher;
    private int yearOfPurchase;
    private int rackNo;
    private int onShelf;
    private int countID;
    private double price;
    private boolean isReserved;
    ArrayList<SubBook> copyDetails;
    ArrayList<Integer> reserveList;
    public Book(String ISBN, String name, String publisher, int yearOfPurchase, int rackNo, double price) {
        this.ISBN = ISBN;
        this.name = name;
        this.publisher = publisher;
        this.yearOfPurchase = yearOfPurchase;
        this.rackNo = rackNo;
        this.price=price;
        onShelf=0;
        countID=0;
        isReserved=false;
        copyDetails=new ArrayList<>();
        reserveList=new ArrayList<>();
    }
    
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCountID() {
        return countID;
    }

    public void setCountID(int countID) {
        this.countID = countID;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getYearOfPurchase() {
        return yearOfPurchase;
    }

    public void setYearOfPurchase(int yearOfPurchase) {
        this.yearOfPurchase = yearOfPurchase;
    }

    public int getRackNo() {
        return rackNo;
    }

    public void setRackNo(int rackNo) {
        this.rackNo = rackNo;
    }

    public int getOnShelf() {
        return onShelf;
    }

    public void setOnShelf(int onShelf) {
        this.onShelf = onShelf;
    }

    public boolean isIsReserved() {
        return isReserved;
    }

    public void setIsReserved(boolean isReserved) {
        this.isReserved = isReserved;
    }

    public ArrayList<SubBook> getCopyDetails() {
        return copyDetails;
    }

    public void setCopyDetails(ArrayList<SubBook> copyDetails) {
        this.copyDetails = copyDetails;
    }

    public ArrayList<Integer> getReserveList() {
        return reserveList;
    }

    public void setReserveList(ArrayList<Integer> reserveList) {
        this.reserveList = reserveList;
    }
    
    public void add(){
        SubBook newCopy = new SubBook(countID,false);
        copyDetails.add(newCopy);
        countID++;
    }
    public void delete(int ID){
        int i;
        for(i=0;i<copyDetails.size();i++){
            if(copyDetails.get(i).getID()==ID){
                copyDetails.remove(i);
            }
        }
    }
}
