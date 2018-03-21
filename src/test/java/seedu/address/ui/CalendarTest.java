package seedu.address.ui;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CalendarTest extends GuiUnitTest {

    @Test
    public void equals() {
        //Current time
        Calendar calendar = new Calendar();
        assertEquals(calendar, new Calendar());
    }
}
