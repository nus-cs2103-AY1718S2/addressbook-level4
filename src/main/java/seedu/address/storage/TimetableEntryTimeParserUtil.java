package seedu.address.storage;

import seedu.address.model.timetableentry.TimetableEntryTime;

/**
 * Parses timetable endtime to separated fields of time.
 */
public class TimetableEntryTimeParserUtil {
    private static final int YEAR_BEGIN_INDEX = 0 + 13;
    private static final int MONTH_BEGIN_INDEX = 5 + 13;
    private static final int DAY_BEGIN_INDEX = 8 + 13;
    private static final int HOUR_BEGIN_INDEX = 11 + 13;
    private static final int MINUTE_BEGIN_INDEX = 14 + 13;
    private static final int SECOND_BEGIN_INDEX = 17 + 13;

    private static final int YEAR_END_INDEX = 4 + 13;
    private static final int MONTH_END_INDEX = 7 + 13;
    private static final int DAY_END_INDEX = 10 + 13;
    private static final int HOUR_END_INDEX = 13 + 13;
    private static final int MINUTE_END_INDEX = 16 + 13;
    private static final int SECOND_END_INDEX = 19 + 13;

    //the menu of Month in Calendar is zero based
    private static final int MONTH_INDEX_OFFSET = -1;
    private static final int TIMEZONE_HOUR_OFFSET = 0;

    /**
     * Parses the input time string into time fields.
     *
     * @params input that a string containing time information in the format
     * {"dateTime":"YYYY-MM-DDTHH:MM:SS.000+08:00",
     * "timeZone":"Asia/Singapore"}<
     *
     * @return TimetableEntryTime containing the parsed time fields.
     */
    public static TimetableEntryTime parseTime(String input) {
        TimetableEntryTime tet = new TimetableEntryTime(Integer.parseInt(input.substring(YEAR_BEGIN_INDEX,
                YEAR_END_INDEX)),
                Integer.parseInt(input.substring(MONTH_BEGIN_INDEX, MONTH_END_INDEX)) + MONTH_INDEX_OFFSET,
                Integer.parseInt(input.substring(DAY_BEGIN_INDEX, DAY_END_INDEX)),
                Integer.parseInt(input.substring(HOUR_BEGIN_INDEX, HOUR_END_INDEX)) + TIMEZONE_HOUR_OFFSET,
                Integer.parseInt(input.substring(MINUTE_BEGIN_INDEX, MINUTE_END_INDEX)),
                Integer.parseInt(input.substring(SECOND_BEGIN_INDEX, SECOND_END_INDEX)));
        System.out.println("ParsedTime: " + tet.getYear() + " " + tet.getMonth() + " " + tet.getDate() + " "
                + tet.getHour() + " " + tet.getMinute() + " " + tet.getSeconds());
        return tet;
    }
}
