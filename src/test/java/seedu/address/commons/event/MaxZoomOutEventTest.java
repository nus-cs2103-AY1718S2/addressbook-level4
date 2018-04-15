package seedu.address.commons.event;

import org.junit.Test;

import seedu.address.commons.events.ui.MaxZoomOutEvent;

//@@author jlks96
public class MaxZoomOutEventTest {
    @Test
    public void toString_comparedWithClassName_success() {
        MaxZoomOutEvent event = new MaxZoomOutEvent();
        assert(event.toString().equals("MaxZoomOutEvent"));
    }
}
