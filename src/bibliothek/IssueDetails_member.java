/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bibliothek;



/**
 *
 * @author AKASH
 */
class IssueDetails_member {
    private String issuedBook;
    private String issueDate;
    private String returnDate;

    public IssueDetails_member(String issuedBook, String issueDate) {
        this.issuedBook = issuedBook;
        this.issueDate = issueDate;
    }
    
    
    public String getIssuedBook() {
        return issuedBook;
    }

    public void setIssuedBook(String issuedBook) {
        this.issuedBook = issuedBook;
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
