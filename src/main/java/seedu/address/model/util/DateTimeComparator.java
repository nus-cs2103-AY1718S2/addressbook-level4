package seedu.address.model.util;

import seedu.address.model.activity.Activity;
import seedu.address.model.activity.DateTime;

import java.util.Comparator;

//@@author karenfrilya97
public class DateTimeComparator implements Comparator<Activity> {

    public DateTimeComparator () {
    }

    public int compare(Activity o1, Activity o2) {
        DateTime dt1 = o1.getDateTime();
        DateTime dt2 = o2.getDateTime();
        return dt1.getLocalDateTime().compareTo(dt2.getLocalDateTime());
    }
}
