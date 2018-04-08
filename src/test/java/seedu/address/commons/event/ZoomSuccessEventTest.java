package seedu.address.commons.event;

import org.junit.Test;

import seedu.address.commons.events.ui.ZoomSuccessEvent;

//@@author jlks96
public class ZoomSuccessEventTest {
    @Test
    public void toString_comparedWithClassName_success() {
        ZoomSuccessEvent event = new ZoomSuccessEvent();
        assert(event.toString().equals("ZoomSuccessEvent"));
    }
}
