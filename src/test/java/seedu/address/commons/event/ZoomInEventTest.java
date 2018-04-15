package seedu.address.commons.event;

import org.junit.Test;

import seedu.address.commons.events.logic.ZoomInEvent;

//@@author jlks96
public class ZoomInEventTest {
    @Test
    public void toString_comparedWithClassName_success() {
        ZoomInEvent event = new ZoomInEvent();
        assert(event.toString().equals("ZoomInEvent"));
    }
}
