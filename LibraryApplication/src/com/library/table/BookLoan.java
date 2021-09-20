package com.library.table;

public class BookLoan {

    private int bookId;
    private String title;
    private int cardNo;
    private String borrowerName;
    private int branchId;
    private String branchName;
    private String dateOut;
    private String dueDate;

    public BookLoan() {    }

    public BookLoan(int bookId, String title, int cardNo, String borrowerName, int branchId, String branchName) {
        this.bookId = bookId;
        this.title = title;
        this.cardNo = cardNo;
        this.borrowerName = borrowerName;
        this.branchId = branchId;
        this.branchName = branchName;
    }

    public int getBookId() { return bookId; }
    public void setBookId(int bookId) { this.bookId = bookId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getCardNo() { return cardNo; }
    public void setCardNo(int cardNo) { this.cardNo = cardNo; }

    public String getBorrowerName() { return borrowerName; }
    public void setBorrowerName(String borrowerName) { this.borrowerName = borrowerName; }

    public int getBranchId() { return branchId; }
    public void setBranchId(int branchId) { this.branchId = branchId; }

    public String getBranchName() { return branchName; }
    public void setBranchName(String branchName) { this.branchName = branchName; }

    public String getDateOut() { return dateOut; }
    public void setDateOut(String dateOut) { this.dateOut = dateOut; }

    public String getDueDate() { return dueDate; }
    public void setDueDate(String dueDate) { this.dueDate = dueDate; }

//    @Override
//    public String toString() {
//        return String.format("BookLoan{ Book: %s || Borrower: %s (cardNo: %d) || Branch: %s} ", title, borrowerName, cardNo, branchName);
//    }


    @Override
    public String toString() {
        return "BookLoan{" +
                "bookId=" + bookId +
                ", title='" + title + '\'' +
                ", cardNo=" + cardNo +
                ", borrowerName='" + borrowerName + '\'' +
                ", branchId=" + branchId +
                ", branchName='" + branchName + '\'' +
                ", dateOut='" + dateOut + '\'' +
                ", dueDate='" + dueDate + '\'' +
                '}';
    }
}
