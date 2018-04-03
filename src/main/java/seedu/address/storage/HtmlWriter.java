package seedu.address.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import seedu.address.model.person.Person;
import seedu.address.model.person.customer.Customer;

/**
 * Writes Person Data to a HTML file
 */
public class HtmlWriter {
    public static final String OPENING_LINE = "<!DOCTYPE html><html><head>\n"
            + "<title>LoanSharkManager</title></head>\n"
            + "<body style=\"background-color:#383838;\"\n>"
            + "<font face=\"Segoe UI Semibold\" size=\"5\" color=\"white\">\n";

    private final String name;
    private final String amountOwed;
    private final String dueDate;
    private final String runnerAssigned;

    public HtmlWriter() {
        this.name = null;
        this.amountOwed = null;
        this.dueDate = null;
        this.runnerAssigned = null;
    }

    public HtmlWriter(Customer customer) {
        this.name = "wala";
        this.amountOwed = "wala";
        this.dueDate = "walala";
        this.runnerAssigned = "walalalala";
    }

    /**
     * Writes person's data to a HTML file and returns the file location
     * @return
     */
    public String writePerson() {
        String filepath = System.getProperty("user.dir") + File.separator + "PersonPage.html";
        String absoluteFilepath;
        File file = new File(filepath);
        try {
            PrintWriter printWriter = new PrintWriter(file);
            printWriter.print(OPENING_LINE);
            printWriter.println("<p>Name: Malaku Kozinov</p>");
            printWriter.println("<p>Amount Owed: $845000</p>");
            printWriter.println("<p>Due Date: 12/10/2020</p>");
            printWriter.println("<p>Status: VIP</p>");
            printWriter.println("<p>Runner Assigned: Johnnius Johnsohn</p>");
            printWriter.println("</body></html>");
            printWriter.close();
        } catch (FileNotFoundException e) {
            return "";
        }
        absoluteFilepath = file.getAbsolutePath();
        absoluteFilepath = absoluteFilepath.replaceAll("\"", "/");
        return absoluteFilepath;
    }
}
