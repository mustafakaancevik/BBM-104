import java.io.IOException;
import java.text.ParseException;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {
         readAndWrite readAndWrite = new readAndWrite();
         readAndWrite.readInput(args[0],args[1]);
    }
}