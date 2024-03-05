import java.util.ArrayList;

public class academic extends personType{
    private ArrayList<Integer> borrowedBook = new ArrayList<Integer>();
    public academic(int personID,String type) {
        super(personID,type);
    }

    @Override
    public void setBorrowedBook(int borrowedBook) throws error {
        int maxBook = 4;

        if (getBorrowedBook().size() >= maxBook){
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
