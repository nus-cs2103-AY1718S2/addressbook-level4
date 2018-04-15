package seedu.address.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;

import seedu.address.model.person.Person;
import seedu.address.model.person.customer.Customer;
import seedu.address.model.person.runner.Runner;

//@@author Der-Erlkonig
/**
 * Writes Person Data to a HTML file
 */
public class HtmlWriter {
    public static final String OPENING_LINE = "<!DOCTYPE html><html><head>\n"
            + "<body style=\"background-color:#CEDBFB;\"\n>"
            + "<font face=\"Segoe UI\" size=\"5\" color=\"black\">"
            + "<table><tr><th align=\"left\" colspan=\"2\">";

    private final String name;
    private final String phone;
    private final String email;
    private final String address;
    private final String amountBorrowed;
    private final String interestRate;
    private final String amountCurrentlyOwed;
    private final String oweStartDate;
    private final String oweDueDate;
    private final String runnerAssigned;

    private final List<Person> customerList;

    private final SimpleDateFormat simpledate = new SimpleDateFormat("EEE, d MMM yyyy");

    public HtmlWriter() {
        this.name = null;
        this.phone = null;
        this.address = null;
        this.email = null;
        this.amountBorrowed = null;
        this.interestRate = null;
        this.amountCurrentlyOwed = null;
        this.oweStartDate = null;
        this.oweDueDate = null;
        this.runnerAssigned = null;
        this.customerList = null;
    }

    /**
     * Constructs HtmlWriter with Customer's details
     * @param customer
     */
    public HtmlWriter(Customer customer) {
        this.name = customer.getName().fullName;
        this.phone = customer.getPhone().value;
        this.address = customer.getAddress().value;
        this.email = customer.getEmail().value;
        this.amountBorrowed = String.format("%,.2f", customer.getMoneyBorrowed().value);
        this.interestRate = customer.getStandardInterest().toString();
        this.amountCurrentlyOwed = String.format("%,.2f", customer.getMoneyCurrentlyOwed());
        this.oweStartDate = simpledate.format(customer.getOweStartDate());
        this.oweDueDate = simpledate.format(customer.getOweDueDate());
        this.runnerAssigned = customer.getRunner().getName().fullName;
        this.customerList = null;
    }

    /**
     * Constructs HtmlWriter with Runner's Details
     * @param runner
     */
    public HtmlWriter(Runner runner) {
        this.name = runner.getName().fullName;
        this.phone = runner.getPhone().value;
        this.email = runner.getEmail().value;
        this.address = runner.getAddress().value;
        this.amountBorrowed = "";
        this.interestRate = "";
        this.amountCurrentlyOwed = "";
        this.oweStartDate = "";
        this.oweDueDate = "";
        this.runnerAssigned = "";
        this.customerList = runner.getCustomers();
    }

    /**
     * Writes Customer's data to a HTML file and returns the file location
     * @return
     */
    public String writeCustomer() {
        String filepath = System.getProperty("user.dir") + File.separator + "PersonPage.html";
        String absoluteFilepath;
        File file = new File(filepath);
        try {
            PrintWriter printWriter = new PrintWriter(file);
            printWriter.print(OPENING_LINE);
            printWriter.println(name + "</th></tr>");
            printWriter.println("<tr><td style=\"width: 240px;\">phone: </td><td>" + phone + "</td></tr>");
            printWriter.println("<tr><td>address: </td><td>" + address + "</td></tr>");
            printWriter.println("<tr><td>email: </td><td>" + email + "</td></tr>");
            printWriter.println("<tr><td>amount borrowed: </td><td>$" + amountBorrowed + "</td></tr>");
            printWriter.println("<tr><td>interest (weekly): </td><td>" + interestRate + "%</td></tr>");
            printWriter.println("<tr><td>amount owed: </td><td>$" + amountCurrentlyOwed + "</td></tr>");
            printWriter.println("<tr><td>start date: </td><td>" + oweStartDate + "</td></tr>");
            printWriter.println("<tr><td>due date: </td><td>" + oweDueDate + "</td></tr>");
            printWriter.println("<tr><td>runner assigned: </td><td>" + runnerAssigned + "</td></tr>");
            printWriter.println("</table></body></html>");
            printWriter.close();
        } catch (FileNotFoundException e) {
            return "";
        }
        absoluteFilepath = file.getAbsolutePath();
        absoluteFilepath = absoluteFilepath.replaceAll("\"", "/");
        return absoluteFilepath;
    }

    /**
     * Writes Runner's data to HTML file and returns the file location
     * @return
     */
    public String writeRunner() {
        String filepath = System.getProperty("user.dir") + File.separator + "PersonPage.html";
        String absoluteFilepath;
        File file = new File(filepath);
        int customerListSize = customerList.size();
        try {
            PrintWriter printWriter = new PrintWriter(file);
            printWriter.print(OPENING_LINE);
            printWriter.println(name + "</th></tr>");
            printWriter.println("<tr><td style=\"width: 120px;\">phone: </td><td>" + phone + "</td></tr>");
            printWriter.println("<tr><td>email: </td><td>" + email + "</td></tr>");
            printWriter.println("<tr><td>address: </td><td>" + address + "</td></tr>");
            printWriter.println("</table><br><br><table><tr><th align=\"left\">");
            printWriter.println("Customers Assigned [" + customerListSize + "]");
            printWriter.println("</th></tr>");
            for (Person eachCustomer: customerList) {
                printWriter.println("<tr><td>");
                printWriter.println("- " + eachCustomer.getName().fullName);
                printWriter.println("</td></tr>");
            }
            printWriter.println("</table></body></html>");
            printWriter.close();
        } catch (FileNotFoundException e) {
            return "";
        }
        absoluteFilepath = file.getAbsolutePath();
        absoluteFilepath = absoluteFilepath.replaceAll("\"", "/");
        return absoluteFilepath;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getAmountBorrowed() {
        return amountBorrowed;
    }

    public String getInterestRate() {
        return interestRate;
    }

    public String getAmountCurrentlyOwed() {
        return amountCurrentlyOwed;
    }

    public String getOweStartDate() {
        return oweStartDate;
    }

    public String getOweDueDate() {
        return oweDueDate;
    }

    public String getRunnerAssigned() {
        return runnerAssigned;
    }

    public List getCustomerList() {
        return customerList;
    }
}
