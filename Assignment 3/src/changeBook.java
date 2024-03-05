import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class changeBook {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    Calendar calendar = Calendar.getInstance();
    readAndWrite write = new readAndWrite();
    public void borrowBook(ArrayList<bookType> bookTypeArrayList, ArrayList<personType> personTypeArrayList, String[] row, String output) throws error, ParseException, IOException {
        for (bookType bookType : bookTypeArrayList) {
            if (row[1].equals(String.valueOf(bookType.getBookID())) ) {
                if (bookType.getBorrowStatus().equals("in") && bookType.getReadStatus().equals("ready")){
                    for (personType personType : personTypeArrayList) {
                        if (row[2].equals(String.valueOf(personType.getPersonID()))) {
                            if (personType instanceof student) {
                                if (bookType instanceof handWrittenBook) {
                                    throw new error("You cannot borrow this book!");
                                } else {
                                    Date borrowdate = format.parse(row[3]);
                                    bookType.setBorrowdate(borrowdate);
                                    calendar.setTime(borrowdate);
                                    calendar.add(Calendar.DATE,7);
                                    bookType.setDeadlineDate(borrowdate);
                                    bookType.setDeadlineDate(calendar.getTime());
                                    personType.setBorrowedBook(bookType.getBookID());
                                    bookType.setBorrowStatus("out");
                                    write.writeOutput(output,"The book ["+bookType.getBookID()+"] was borrowed by member ["+personType.getPersonID()+"] at "+returnFormattedDate(borrowdate));
                                }
                            }else {
                                if (bookType instanceof handWrittenBook) {
                                    throw new error("You cannot borrow this book!");
                                } else {
                                    Date borrowdate = format.parse(row[3]);
                                    bookType.setBorrowdate(borrowdate);
                                    calendar.setTime(borrowdate);
                                    calendar.add(Calendar.DATE,14);
                                    bookType.setDeadlineDate(borrowdate);
                                    bookType.setDeadlineDate(calendar.getTime());
                                    personType.setBorrowedBook(bookType.getBookID());
                                    bookType.setBorrowStatus("out");
                                    write.writeOutput(output,"The book ["+bookType.getBookID()+"] was borrowed by member ["+personType.getPersonID()+"] at "+returnFormattedDate(borrowdate));
                                }
                            }
                        }

                    }
                }else {
                    throw new error("You cannot borrow this book!");
                }

            }
        }

    }
    public void readInLibrary(ArrayList<bookType> bookTypeArrayList, ArrayList<personType> personTypeArrayList, String[] row, String output) throws ParseException, error, IOException {
        for (bookType book : bookTypeArrayList){
            if (row[1].equals(String.valueOf(book.getBookID()))){
                if (book.getBorrowStatus().equals("in") && book.getReadStatus().equals("ready")){
                    for (personType person : personTypeArrayList){
                        if (row[2].equals(String.valueOf(person.getPersonID()))){
                            if (person instanceof student){
                                if (book instanceof printedBook){
                                    Date borrowdate = format.parse(row[3]);
                                    book.setBorrowdate(borrowdate);
                                    book.setDeadlineDate(borrowdate);
                                    person.setBorrowedBook(book.getBookID());
                                    book.setReadStatus("notReady");
                                    write.writeOutput(output,"The book ["+book.getBookID()+"] was read in library by member ["+person.getPersonID()+"] at "+returnFormattedDate(book.getBorrowdate()));
                                }else {
                                    write.writeOutput(output,"Students can not read handwritten books!");
                                }
                            }else {
                                Date borrowdate = format.parse(row[3]);
                                book.setBorrowdate(borrowdate);
                                book.setDeadlineDate(borrowdate);
                                person.setBorrowedBook(book.getBookID());
                                book.setReadStatus("notReady");
                                write.writeOutput(output,"The book ["+book.getBookID()+"] was read in library by member ["+person.getPersonID()+"] at "+returnFormattedDate(book.getBorrowdate()));

                            }
                        }
                    }
                }else {
                    write.writeOutput(output,"You can not read this book!");
                }
            }

        }

    }
    public void returnBook(ArrayList<bookType> bookTypeArrayList, ArrayList<personType> personTypeArrayList, String[] row, String output) throws ParseException, IOException {
        for (bookType book : bookTypeArrayList){
            if (row[1].equals(String.valueOf(book.getBookID()))){
                if (book.getBorrowStatus().equals("out") || book.getReadStatus().equals("notReady")){
                    for (personType person : personTypeArrayList){
                        if (row[2].equals(String.valueOf(person.getPersonID()))){
                                Date returnDate = format.parse(row[3]);
                                long difference = returnDate.getTime()-book.getDeadlineDate().getTime();
                                int fee = (int) (difference/(1000*60*60*24));
                                book.setBorrowdate(null);

                                if (fee > 0 && book.getReadStatus().equals("notReady")){
                                    write.writeOutput(output,"The book ["+book.getBookID()+"] was returned by member ["+person.getPersonID()+"] at "+returnFormattedDate(returnDate)+" Fee: 0");
                                } else if (fee > 0) {
                                    write.writeOutput(output,"The book ["+book.getBookID()+"] was returned by member ["+person.getPersonID()+"] at "+returnFormattedDate(returnDate)+" Fee: "+fee);
                                }else {
                                    write.writeOutput(output,"The book ["+book.getBookID()+"] was returned by member ["+person.getPersonID()+"] at "+returnFormattedDate(returnDate)+" Fee: 0");
                                }
                                book.setReadStatus("ready");
                                book.setBorrowStatus("in");
                                book.setIsExtend("off");
                                person.deleteBorrowedBook(book.getBookID());




                        }
                    }
                }else {
                    write.writeOutput(output,"You cannot return this book!");
                }
            }
        }

    }
    public void extendBook(ArrayList<bookType> bookTypeArrayList, ArrayList<personType> personTypeArrayList, String[] row, String output) throws ParseException, IOException {
        for (bookType book : bookTypeArrayList){
            if (row[1].equals(String.valueOf(book.getBookID()))){
                if (book.getIsExtend().equals("off")){
                    for (personType person : personTypeArrayList){
                        if (row[2].equals(String.valueOf(person.getPersonID()))){
                            Date date = format.parse(row[3]);
                            if (book.getDeadlineDate().compareTo(date) >= 0){
                                write.writeOutput(output,"The deadline of book ["+book.getBookID()+"] was extended by member ["+person.getPersonID()+"] at "+returnFormattedDate(date));
                                calendar.setTime(book.getDeadlineDate());
                                calendar.add(Calendar.DATE,7);
                                book.setDeadlineDate(calendar.getTime());
                                write.writeOutput(output,"New deadline of book ["+book.getBookID()+"] is "+returnFormattedDate(book.getDeadlineDate()));
                                book.setIsExtend("on");
                            }
                        }
                    }
                }else {
                    write.writeOutput(output,"You cannot extend the deadline!");
                }

            }
        }
    }
    public void gettingHistory(ArrayList<bookType> bookTypeArrayList,ArrayList<personType> personTypeArrayList,String output) throws ParseException, IOException {
        write.writeOutput(output,"History of library:\n");
        write.writeOutput(output,"Number of students: "+personTypeArrayList.stream().filter(person -> person instanceof student).count());
        for (personType person : personTypeArrayList){
            if (person instanceof student){
                write.writeOutput(output,person.toString());
            }
        }
        write.writeOutput(output,"\nNumber of academics: "+personTypeArrayList.stream().filter(person -> person instanceof academic).count());
        for (personType person : personTypeArrayList){
            if (person instanceof academic){
                write.writeOutput(output,person.toString());

            }
        }write.writeOutput(output,"\nNumber of printed books: "+bookTypeArrayList.stream().filter(book -> book instanceof printedBook).count());
        for (bookType book : bookTypeArrayList){
            if (book instanceof printedBook){
                write.writeOutput(output,book.toString());
            }
        }write.writeOutput(output,"\nNumber of handwritten books: "+bookTypeArrayList.stream().filter(book -> book instanceof handWrittenBook).count());
        for (bookType book : bookTypeArrayList){
            if (book instanceof handWrittenBook){
                write.writeOutput(output,book.toString());
            }
        }write.writeOutput(output,"\nNumber of borrowed books: "+bookTypeArrayList.stream().filter(book -> book.getBorrowStatus().equals("out")).count());
        for (bookType book : bookTypeArrayList){
            if (book.getBorrowStatus().equals("out")){
                for (personType person : personTypeArrayList){
                    if (person.getBorrowedBook().contains(book.getBookID())){
                        write.writeOutput(output,"The book ["+book.getBookID()+"] was borrowed by member ["+person.getPersonID()+"] at "+returnFormattedDate(book.getBorrowdate()));
                    }
                }
            }
        }write.writeOutput(output,"\nNumber of books read in library: "+bookTypeArrayList.stream().filter(book -> book.getReadStatus().equals("notReady")).count());
        for (bookType book : bookTypeArrayList){
            if (book.getReadStatus().equals("notReady")){
                for (personType person : personTypeArrayList){
                    if (person.getBorrowedBook().contains(book.getBookID())){
                        write.writeOutput(output,"The book ["+book.getBookID()+"] was read in library by member ["+person.getPersonID()+"] at "+returnFormattedDate(book.getBorrowdate()));
                    }
                }
            }
        }
    }



        public String returnFormattedDate(Date time) throws ParseException {
        if (time == null){
            return null;
        }else {
            SimpleDateFormat inputFormat
                    = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
            TimeZone timeZone = TimeZone.getTimeZone("TRT");
            inputFormat.setTimeZone(timeZone);
            Date date = inputFormat.parse(String.valueOf(time));

            return outputFormat.format(date);
        }

    }

}
