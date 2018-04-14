package seedu.address.testutil;
//@@author SuxianAlicia

import static seedu.address.testutil.TypicalLocalDates.LEAP_YEAR_DATE;
import static seedu.address.ui.util.CalendarFxUtil.DAY_VIEW;
import static seedu.address.ui.util.CalendarFxUtil.MONTH_VIEW;
import static seedu.address.ui.util.CalendarFxUtil.REQUEST_BACK;
import static seedu.address.ui.util.CalendarFxUtil.REQUEST_NEXT;
import static seedu.address.ui.util.CalendarFxUtil.REQUEST_TODAY;
import static seedu.address.ui.util.CalendarFxUtil.WEEK_VIEW;

import seedu.address.commons.events.ui.ChangeCalendarDateRequestEvent;
import seedu.address.commons.events.ui.ChangeCalendarPageRequestEvent;
import seedu.address.commons.events.ui.ChangeCalendarViewRequestEvent;

/**
 * A utility class containing a list of {@code BaseEvent} objects to be used in tests.
 */
public class TypicalBaseEvents {

    public static final ChangeCalendarViewRequestEvent CHANGE_TO_DAY_EVENT =
            new ChangeCalendarViewRequestEvent(DAY_VIEW);
    public static final ChangeCalendarViewRequestEvent CHANGE_TO_MONTH_EVENT =
            new ChangeCalendarViewRequestEvent(MONTH_VIEW);
    public static final ChangeCalendarViewRequestEvent CHANGE_TO_WEEK_EVENT =
            new ChangeCalendarViewRequestEvent(WEEK_VIEW);

    public static final ChangeCalendarPageRequestEvent CHANGE_TO_NEXT_PAGE_EVENT =
            new ChangeCalendarPageRequestEvent(REQUEST_NEXT);
    public static final ChangeCalendarPageRequestEvent CHANGE_TO_PREVIOUS_PAGE_EVENT =
            new ChangeCalendarPageRequestEvent(REQUEST_BACK);
    public static final ChangeCalendarPageRequestEvent CHANGE_TO_TODAY_EVENT =
            new ChangeCalendarPageRequestEvent(REQUEST_TODAY);

    public static final ChangeCalendarDateRequestEvent CHANGE_DATE_EVENT =
            new ChangeCalendarDateRequestEvent(LEAP_YEAR_DATE);
}
