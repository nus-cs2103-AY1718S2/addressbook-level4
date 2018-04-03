package seedu.address.model.policy;

/**
 * Represents a Date's Month.
 */
public enum Month {
    JANUARY, FEBRUARY, MARCH, APRIL, MAY, JUNE, JULY, AUGUST, SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER;

    /**
     * Returns the value associated with each month (from 1 to 12)
     * Returns 0 in case of incorrect parameter
     */
    public static int toInt(Month month) {
        Month[] months = Month.values();
        for (int i = 0; i < months.length; i++) {
            if (months[i] == month) {
                return i + 1;
            }
        }
        return 0;
    }
}
