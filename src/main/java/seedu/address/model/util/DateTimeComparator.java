package seedu.address.model.util;

import java.util.Comparator;

import seedu.address.model.activity.Activity;
import seedu.address.model.activity.DateTime;

//@@author karenfrilya97
/**
 * Comparator class that compares Activity based on dateTime attributes.
 * For Event objects, this class only compares based on the startDateTime,
 * and does not take into account the endDateTime.
 */
public class DateTimeComparator implements Comparator<Activity> {

    public DateTimeComparator () {
    }

    /**
     * Compares two activities
     * @param o1 and
     * @param o2,
     * @return a negative integer, zero, or a positive integer
     * if the first activity's dateTime is earlier than, equal to or later than
     * the second activity's dateTime respectively.
     */
    public int compare(Activity o1, Activity o2) {
        DateTime dt1 = o1.getDateTime();
        DateTime dt2 = o2.getDateTime();
        return dt1.getLocalDateTime().compareTo(dt2.getLocalDateTime());
    }
}
