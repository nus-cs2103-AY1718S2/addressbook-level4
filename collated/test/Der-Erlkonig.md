# Der-Erlkonig
###### \java\seedu\address\storage\HtmlWriterTest.java
``` java
public class HtmlWriterTest {
    private HtmlWriter htmlWriter;

    @Before
    public void setUp() throws Exception {
        htmlWriter = new HtmlWriter();
    }

    @Test
    public void checkOpeningLine() {
        String testOpeningLine = "<!DOCTYPE html><html><head>\n"
                + "<body style=\"background-color:#383838;\"\n>"
                + "<font face=\"Segoe UI\" size=\"5\" color=\"white\">"
                + "<table><tr><th align=\"left\" colspan=\"2\">";
        assertEquals(testOpeningLine, htmlWriter.OPENING_LINE);
    }
}
```
