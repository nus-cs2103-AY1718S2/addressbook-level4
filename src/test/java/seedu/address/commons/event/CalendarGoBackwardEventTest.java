package seedu.address.commons.event;

import org.junit.Test;

import seedu.address.commons.events.logic.CalendarGoBackwardEvent;

//@@author jlks96
public class CalendarGoBackwardEventTest {
    @Test
    public void toString_comparedWithClassName_success() {
        CalendarGoBackwardEvent event = new CalendarGoBackwardEvent();
        assert(event.toString().equals("CalendarGoBackwardEvent"));
    }
}
