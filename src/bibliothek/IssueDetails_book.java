/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bibliothek;

import java.io.Serializable;



/**
 *
 * @author AKASH
 */
class IssueDetails_book implements Serializable{
    private int issueMember;
    private String issueDate;
    private String returnDate;

    public IssueDetails_book(int issueMember, String issueDate) {
        this.issueMember = issueMember;
        this.issueDate = issueDate;
    }

    public int getIssueMember() {
        return issueMember;
    }

    public void setIssueMember(int issueMember) {
        this.issueMember = issueMember;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }
    
}
