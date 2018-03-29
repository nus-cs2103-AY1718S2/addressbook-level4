package seedu.address.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class HtmlWriter {
    public HtmlWriter() {}

    public String writePerson() {
        String filepath = System.getProperty("user.dir") + File.separator + "PersonPage.html";
        String absoluteFilepath;
        File file = new File(filepath);
        try {
            PrintWriter printWriter = new PrintWriter(file);
            printWriter.print("<!DOCTYPE html><html><head><title>LoanSharkManager</title></head><body style=\"background-color:#383838;\">");
            printWriter.print("<font face=\"Segoe UI Semibold\" size=\"20\" color=\"white\">");
            printWriter.print("<p>Name:</p>");
            printWriter.print("<p>Amount Owed:</p>");
            printWriter.print("<p>Due Date:</p>");
            printWriter.print("</body></html>");
            printWriter.close();
        } catch (FileNotFoundException e) {

        }
        absoluteFilepath = file.getAbsolutePath();
        absoluteFilepath = absoluteFilepath.replaceAll("\"", "/");
        return absoluteFilepath;
    }
}
