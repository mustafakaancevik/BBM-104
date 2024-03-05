import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Scanner;

public class readAndWrite {
    public void readInput(String input,String output) throws IOException {
        Scanner scanner = null;

        try {
            File inputFile = new File(input);
            scanner = new Scanner(inputFile);
            if (inputFile.exists()) {
                String content = new String(Files.readAllBytes(inputFile.toPath()), StandardCharsets.UTF_8);
                if (content.trim().isEmpty()) {
                    writeOutput(output, "The file is empty!");
                    System.exit(0);
                }
            }
        }catch (IOException e){
            writeOutput(output,"There is no txt file!");
            System.exit(0);

        }
        ArrayList<personType> personTypeArrayList = new ArrayList<>();
        ArrayList<bookType> bookTypeArrayList = new ArrayList<>();
        addCommand addCommand = new addCommand();
        changeBook changeBook = new changeBook();
        while (scanner.hasNextLine()) {
            try {
                String line = scanner.nextLine();
                if (!line.isEmpty()){
                    String[] row = line.split("\t");

                    switch (row[0]){
                        case "addBook":
                            addCommand.addBook(bookTypeArrayList,row,output);
                            break;
                        case "addMember":
                            addCommand.addMember(personTypeArrayList,row,output);
                            break;
                        case "borrowBook":
                            changeBook.borrowBook(bookTypeArrayList,personTypeArrayList,row,output);
                            break;
                        case "readInLibrary":
                            changeBook.readInLibrary(bookTypeArrayList,personTypeArrayList,row,output);
                            break;
                        case "returnBook":
                            changeBook.returnBook(bookTypeArrayList,personTypeArrayList,row,output);
                            break;
                        case "extendBook":
                            changeBook.extendBook(bookTypeArrayList,personTypeArrayList,row,output);
                            break;
                        case "getTheHistory":
                            changeBook.gettingHistory(bookTypeArrayList,personTypeArrayList,output);
                            break;
                    }

                }
            }catch (Exception e){
                writeOutput(output,e.getMessage());
        }

        }


    }
    public void writeOutput(String output,String value) throws IOException {
        BufferedWriter writer;
        writer = new BufferedWriter(new FileWriter(output, true));
        writer.write(value+"\n");
        writer.close();
    }
}
