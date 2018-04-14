//@@author IzHoBX
package seedu.address.storage;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.notification.NotificationTime;

/**
 * Parses timetable endtime to separated fields of time.
 */
public class NotificationTimeParserUtil {
    private static final int YEAR_BEGIN_INDEX = 0;
    private static final int MONTH_BEGIN_INDEX = 5;
    private static final int DAY_BEGIN_INDEX = 8;
    private static final int HOUR_BEGIN_INDEX = 11;
    private static final int MINUTE_BEGIN_INDEX = 14;
    private static final int SECOND_BEGIN_INDEX = 17;

    private static final int YEAR_END_INDEX = 4;
    private static final int MONTH_END_INDEX = 7;
    private static final int DAY_END_INDEX = 10;
    private static final int HOUR_END_INDEX = 13;
    private static final int MINUTE_END_INDEX = 16;
    private static final int SECOND_END_INDEX = 19;

    //the menu of Month in Calendar is zero based
    private static final int MONTH_INDEX_OFFSET = -1;
    private static final int TIMEZONE_HOUR_OFFSET = 0;

    /**
     * Parses the input time string into time fields.
     *
     * @params input that a string containing time information in the format:
     * Default format: {"dateTime":"YYYY-MM-DDTHH:MM:SS.000+08:00timeZone:"Asia/Singapore"}
     * Acceptable format: YYYY-MM-DDTHH:MM:SS
     *
     * @return NotificationTime containing the parsed time fields.
     */
    public static NotificationTime parseTime(String input) {
        int firstIntegerOffset = findFirstIntegerOffset(input);
        assert(firstIntegerOffset != -1 && SECOND_END_INDEX + firstIntegerOffset <= input.length());
        try {
            NotificationTime tet = new NotificationTime(Integer.parseInt(input.substring(YEAR_BEGIN_INDEX
                            + firstIntegerOffset,
                    YEAR_END_INDEX + firstIntegerOffset)),
                    Integer.parseInt(input.substring(MONTH_BEGIN_INDEX + firstIntegerOffset, MONTH_END_INDEX
                            + firstIntegerOffset)) + MONTH_INDEX_OFFSET,
                    Integer.parseInt(input.substring(DAY_BEGIN_INDEX + firstIntegerOffset,
                            DAY_END_INDEX + firstIntegerOffset)),
                    Integer.parseInt(input.substring(HOUR_BEGIN_INDEX + firstIntegerOffset,
                            HOUR_END_INDEX + firstIntegerOffset)) + TIMEZONE_HOUR_OFFSET,
                    Integer.parseInt(input.substring(MINUTE_BEGIN_INDEX + firstIntegerOffset,
                            MINUTE_END_INDEX + firstIntegerOffset)),
                    Integer.parseInt(input.substring(SECOND_BEGIN_INDEX + firstIntegerOffset,
                            SECOND_END_INDEX + firstIntegerOffset)));
            return tet;
        } catch (NumberFormatException e) {
            LogsCenter.getLogger(NotificationTimeParserUtil.class).info("Time format in notification mismatch");
            assert(false);
        }
        //should not be returned
        return null;
    }

    /**
     * Finds the position of the first integer character
     */
    private static int findFirstIntegerOffset(String input) {
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) - '0' >= 0 && input.charAt(i) - '0' <= 9) {
                return i;
            }
        }
        return -1;
    }
}
