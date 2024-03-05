import java.util.Date;

public class bookType {
    private int bookID;
    private String borrowStatus;
    private String readStatus;
    private Date extendDate;
    private Date borrowdate;
    private Date deadlineDate;
    private String isExtend;
    private String type;


    public bookType(int bookID,String type){
        this.setBookID(bookID);
        this.borrowStatus = "in";
        this.readStatus = "ready";
        this.extendDate = null;
        this.isExtend = "off";
        this.type = type;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public String getBorrowStatus() {
        return borrowStatus;
    }

    public void setBorrowStatus(String borrowStatus) {
        this.borrowStatus = borrowStatus;
    }

    public Date getExtendDate() {
        return extendDate;
    }

    public void setExtendDate(Date extendDate) {
        this.extendDate = extendDate;
    }

    public Date getBorrowdate() {

        return borrowdate;
    }

    public void setBorrowdate(Date borrowdate) {
        this.borrowdate = borrowdate;
    }

    public Date getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(Date deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    public String getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(String readStatus) {
        this.readStatus = readStatus;
    }

    public String getIsExtend() {
        return isExtend;
    }

    public void setIsExtend(String isExtend) {
        this.isExtend = isExtend;
    }

    @Override
    public String toString() {
        if (type.equals("H")){
            return "Handwritten [id: "+bookID+"]";
        }else {
            return "Printed [id: "+bookID+"]";
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
