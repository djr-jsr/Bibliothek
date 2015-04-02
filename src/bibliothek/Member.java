/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bibliothek;

import java.util.ArrayList;

enum type{underGraduate,postGraduate,researchScholars,faculty};
/**
 *
 * @author AKASH
 */
class Member {
    private type memberType;
    private int ID;
    private String name;
    private String phoneNo;
    private String address;
    private double fine;
    private int bookLimit;
    private int duration;
    ArrayList<IssueDetails_member> booksIssued;


    public Member(type memberType, int ID, String name, String phoneNo, String address) {
        this.memberType = memberType;
        this.ID = ID;
        this.name = name;
        this.phoneNo = phoneNo;
        this.address = address;
        fine=0.0;
        if(memberType==type.underGraduate){
            bookLimit=2;
            duration=1;
        }
        else if(memberType==type.postGraduate){
            bookLimit=4;
            duration=1;
        }
        else if(memberType==type.researchScholars){
            bookLimit=6;
            duration=3;
        }
        else if(memberType==type.faculty){
            bookLimit=10;
            duration=6;
        }
    }

    public type getMemberType() {
        return memberType;
    }

    public void setMemberType(type memberType) {
        this.memberType = memberType;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getFine() {
        return fine;
    }

    public void setFine(double fine) {
        this.fine = fine;
    }

    public int getBookLimit() {
        return bookLimit;
    }

    public void setBookLimit(int bookLimit) {
        this.bookLimit = bookLimit;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
    
    public void issue(String ISBN)
    {
        
    }
    
    public void returned(String ISBN)
    {
        
    }
    
    public void reserve(String ISBN)
    {
        
    }
}
