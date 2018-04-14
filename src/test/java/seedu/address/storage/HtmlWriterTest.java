package seedu.address.storage;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

//@@author Der-Erlkonig
public class HtmlWriterTest {
    private HtmlWriter htmlWriter;

    @Before
    public void setUp() throws Exception {
        htmlWriter = new HtmlWriter();
    }

    @Test
    public void checkOpeningLine() {
        String testOpeningLine = "<!DOCTYPE html><html><head>\n"
                + "<body style=\"background-color:#CEDBFB;\"\n>"
                + "<font face=\"Segoe UI\" size=\"5\" color=\"black\">"
                + "<table><tr><th align=\"left\" colspan=\"2\">";
        assertEquals(testOpeningLine, htmlWriter.OPENING_LINE);
    }
}
