package seedu.address.commons.events.ui;

import java.time.YearMonth;

import seedu.address.commons.events.BaseEvent;

//@@author trafalgarandre
/**
 * An event requesting to change view of Calendar to MonthPage.
 */
public class ShowMonthRequestEvent extends BaseEvent {
    public final YearMonth targetYearMonth;

    public ShowMonthRequestEvent(YearMonth yearMonth) {
        this.targetYearMonth = yearMonth;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
