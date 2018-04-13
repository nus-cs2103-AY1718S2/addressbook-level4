package seedu.address.commons.util;

import java.time.LocalDate;

/**
 * A container for calendar related utility functions
 */
public class CalendarUtil {
    private static final int JAN = 1;
    private static final int JUN = 6;
    private static final int JUL = 7;
    private static final int DEC = 12;

    private static LocalDate today = LocalDate.now();

    public static int getCurrentSemester() {
        int currMonth = today.getMonthValue();
        if (currMonth >= JAN && currMonth <= JUN) {
            return 2;
        } else if (currMonth >= JUL && currMonth <= DEC) {
            return 1;
        }
        return 0;
    }

    public static int getAcadYear() {
        if (getCurrentSemester() == 1) {
            return today.getYear();
        } else {
            return today.getYear() - 1;
        }
    }
}
