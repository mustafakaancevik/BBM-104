import java.io.IOException;
import java.util.ArrayList;

public class addCommand {
    readAndWrite write = new readAndWrite();
    public void addMember(ArrayList<personType> personTypeArrayList,String[] row,String output) throws IOException {
        if (row[1].equals("S")){
            personTypeArrayList.add(new student(personTypeArrayList.size()+1,"S"));
            write.writeOutput(output,"Created new member: Student [id: "+personTypeArrayList.size()+"]");
        } else if (row[1].equals("A")) {
            personTypeArrayList.add(new academic(personTypeArrayList.size()+1,"A"));
            write.writeOutput(output,"Created new member: Academic [id: "+personTypeArrayList.size()+"]");

        }


    }
    public void addBook(ArrayList<bookType> bookTypeArrayList, String[] row,String output) throws IOException {
        if (row[1].equals("P")){
            bookTypeArrayList.add(new printedBook(bookTypeArrayList.size()+1,"P"));
            write.writeOutput(output,"Created new book: Printed [id: "+bookTypeArrayList.size()+"]");
        } else if (row[1].equals("H")) {
            bookTypeArrayList.add(new handWrittenBook(bookTypeArrayList.size()+1,"H"));
            write.writeOutput(output,"Created new book: Handwritten [id: "+bookTypeArrayList.size()+"]");

        }

    }
}
