package seedu.address.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class HtmlWriter {
    public HtmlWriter() {}

    public String writePerson() {
        String filepath = System.getProperty("user.dir") + File.separator + "PersonPage.html";
        File file = new File(filepath);
        try {
            PrintWriter printWriter = new PrintWriter(file);
            printWriter.print("<!DOCTYPE html><html>,<head><title>HELPLAH</title></head><body style=\"background-color:blue;\"></body></html>");
            printWriter.close();
        } catch (FileNotFoundException e) {

        }
        filepath = file.getAbsolutePath();
        filepath = filepath.replaceAll("\"", "/");
        return filepath;
    }
}
