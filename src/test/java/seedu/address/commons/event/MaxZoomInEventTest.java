package seedu.address.commons.event;

import org.junit.Test;

import seedu.address.commons.events.ui.MaxZoomInEvent;

//@@author jlks96
public class MaxZoomInEventTest {
    @Test
    public void toString_comparedWithClassName_success() {
        MaxZoomInEvent event = new MaxZoomInEvent();
        assert(event.toString().equals("MaxZoomInEvent"));
    }
}
