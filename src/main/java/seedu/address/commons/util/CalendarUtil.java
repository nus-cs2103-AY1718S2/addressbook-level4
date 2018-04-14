package seedu.address.commons.util;

import java.time.LocalDate;

//@@author Isaaaca
/**
 * A container for calendar related utility functions
 */
public class CalendarUtil {
    private static final int JAN = 1;
    private static final int JUN = 6;

    private static LocalDate today = LocalDate.now();

    public static int getCurrentSemester() {
        return getSem(today);
    }

    public static int getSem(LocalDate localDate) {
        int currMonth = localDate.getMonthValue();
        if (currMonth >= JAN && currMonth <= JUN) {
            return 2;
        } else {
            return 1;
        }
    }

    public static int getCurrAcadYear() {
        return  getAcadYear(today);
    }

    public static int getAcadYear(LocalDate localDate) {
        if (getSem(localDate) == 1) {
            return localDate.getYear();
        } else {
            return localDate.getYear() - 1;
        }
    }
}
