package seedu.address.commons.event;

import org.junit.Test;

import seedu.address.commons.events.logic.CalendarGoForwardEvent;

//@@author jlks96
public class CalendarGoForwardEventTest {
    @Test
    public void toString_comparedWithClassName_success() {
        CalendarGoForwardEvent event = new CalendarGoForwardEvent();
        assert(event.toString().equals("CalendarGoForwardEvent"));
    }
}
