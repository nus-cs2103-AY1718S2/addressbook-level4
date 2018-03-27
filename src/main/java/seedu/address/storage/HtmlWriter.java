package seedu.address.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class HtmlWriter {
    public HtmlWriter() {}

    public String writePerson() {
        String filepath = System.getProperty("user.dir") + "PersonPage.html";
        File file = new File(filepath);
        try {
            PrintWriter printWriter = new PrintWriter(file);
            printWriter.print("good lord");
            printWriter.close();
        } catch (FileNotFoundException e) {

        }
        return filepath;
    }
}
