import java.util.ArrayList;

public class student extends personType{
    private ArrayList<Integer> borrowedBook = new ArrayList<Integer>();
    public student(int personID,String type) {
        super(personID,type);

    }
    @Override
    public void setBorrowedBook(int borrowedBook) throws error {
        int maxBook = 2;
        if (this.borrowedBook.size() >= maxBook){
            throw new error("You have exceeded the borrowing limit!");
        }else {
            this.borrowedBook.add(borrowedBook);

        }
    }
    public void deleteBorrowedBook(int bookId){
        getBorrowedBook().remove(new Integer(bookId));
    }
    @Override
    public ArrayList<Integer> getBorrowedBook() {
        return borrowedBook;
    }



}
