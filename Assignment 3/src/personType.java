import java.util.ArrayList;
import java.util.Date;

public abstract class personType {
    private int personID;
    private ArrayList<Integer> borrowedBook;
    private Date borrowDate;
    private String type;

    public personType(int personID,String type){
        this.personID = personID;
        this.type = type;

    }

    public abstract void setBorrowedBook(int borrowedBook) throws error;

    public ArrayList<Integer> getBorrowedBook() {
        return borrowedBook;
    }


    public Date getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public int getPersonID() {
        return personID;
    }

    public void setPersonID(int personID) {
        this.personID = personID;
    }

    public String getType() {
        return type;
    }
    public abstract void deleteBorrowedBook(int bookId);

    public void setType(String type) {
        this.type = type;
    }
    @Override
    public String toString() {
        if (type.equals("S")){
            return "Student [id: "+personID+"]";
        }else {
            return "Academic [id: "+personID+"]";
        }
    }
}
