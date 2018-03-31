package seedu.address.commons.event;

import org.junit.Test;

import seedu.address.commons.events.logic.ZoomOutEvent;

//@@author jlks96
public class ZoomOutEventTest {
    @Test
    public void toString_comparedWithClassName_success() {
        ZoomOutEvent event = new ZoomOutEvent();
        assert(event.toString().equals("ZoomOutEvent"));
    }
}
