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
            printWriter.println("<!DOCTYPE html><html><head><title>LoanSharkManager</title></head><body style=\"background-color:#383838;\">");
            printWriter.println("<font face=\"Segoe UI Semibold\" size=\"20\" color=\"white\">");
            printWriter.println("<p>Name:</p>");
            printWriter.println("<p>Amount Owed:</p>");
            printWriter.println("<p>Due Date:</p>");
            printWriter.println("</body></html>");
            printWriter.close();
        } catch (FileNotFoundException e) {

        }
        absoluteFilepath = file.getAbsolutePath();
        absoluteFilepath = absoluteFilepath.replaceAll("\"", "/");
        return absoluteFilepath;
    }
}
