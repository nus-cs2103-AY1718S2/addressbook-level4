// @@author anh2111
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates that report should be recalculated
 */
public class RefreshReportPanelEvent extends BaseEvent {

    public RefreshReportPanelEvent() {
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
// @@author
