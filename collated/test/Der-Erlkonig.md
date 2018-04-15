# Der-Erlkonig
###### \java\seedu\address\storage\HtmlWriterTest.java
``` java
public class HtmlWriterTest {
    private Customer customer;
    private Runner runner;

    private HtmlWriter htmlWriter;
    private HtmlWriter htmlWriterCustomer;
    private HtmlWriter htmlWriterRunner;

    @Before
    public void setUp() throws Exception {
        customer = new PersonBuilder().buildCustomer();
        runner = new PersonBuilder().buildRunner();
        htmlWriter = new HtmlWriter();
        htmlWriterCustomer = new HtmlWriter(customer);
        htmlWriterRunner = new HtmlWriter(runner);
    }

    @Test
    public void checkOpeningLine() {
        String testOpeningLine = "<!DOCTYPE html><html><head>\n"
                + "<body style=\"background-color:#CEDBFB;\"\n>"
                + "<font face=\"Segoe UI\" size=\"5\" color=\"black\">"
                + "<table><tr><th align=\"left\" colspan=\"2\">";
        assertEquals(testOpeningLine, htmlWriter.OPENING_LINE);
    }

    @Test
    public void checkCustomerFields() {
        assertEquals(htmlWriterCustomer.getName(), "Alice Pauline");
        assertEquals(htmlWriterCustomer.getPhone(), "85355255");
        assertEquals(htmlWriterCustomer.getEmail(), "alice@gmail.com");
        assertEquals(htmlWriterCustomer.getAddress(), "123, Jurong West Ave 6, #08-111");
        assertEquals(htmlWriterCustomer.getAmountBorrowed(), "0.00");
        assertEquals(htmlWriterCustomer.getInterestRate(), "0.0");
        assertEquals(htmlWriterCustomer.getAmountCurrentlyOwed(), "0.00");
        assertEquals(htmlWriterCustomer.getOweStartDate(), "Thu, 1 Jan 1970");
        assertEquals(htmlWriterCustomer.getOweDueDate(), "Thu, 1 Jan 1970");
        assertEquals(htmlWriterCustomer.getRunnerAssigned(), "Not Assigned");
        assertNull(htmlWriterCustomer.getCustomerList());
    }

    @Test
    public void checkRunnerFields() {
        assertEquals(htmlWriterRunner.getName(), "Alice Pauline");
        assertEquals(htmlWriterRunner.getPhone(), "85355255");
        assertEquals(htmlWriterRunner.getEmail(), "alice@gmail.com");
        assertEquals(htmlWriterRunner.getAddress(), "123, Jurong West Ave 6, #08-111");
        assertEquals(htmlWriterRunner.getAmountBorrowed(), "");
        assertEquals(htmlWriterRunner.getInterestRate(), "");
        assertEquals(htmlWriterRunner.getAmountCurrentlyOwed(), "");
        assertEquals(htmlWriterRunner.getOweStartDate(), "");
        assertEquals(htmlWriterRunner.getOweDueDate(), "");
        assertEquals(htmlWriterRunner.getRunnerAssigned(), "");
        assertNotNull(htmlWriterRunner.getCustomerList());
    }
}
```
