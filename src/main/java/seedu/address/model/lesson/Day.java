package seedu.address.model.lesson;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.DayOfWeek;
import java.util.HashMap;

/**
 * Represents a Student's day in a lesson in the Schedule.
 * Guarantees: immutable; is valid as declared in {@link #isValidDay String)}
 */
public class Day implements Comparable<Day> {

    private static final String STRING_MON = "mon";
    private static final String STRING_TUE = "tue";
    private static final String STRING_WED = "wed";
    private static final String STRING_THU = "thu";
    private static final String STRING_FRI = "fri";
    private static final String STRING_SAT = "sat";
    private static final String STRING_SUN = "sun";

    private static final Integer INTEGER_MON = 1;
    private static final Integer INTEGER_TUE = 2;
    private static final Integer INTEGER_WED = 3;
    private static final Integer INTEGER_THU = 4;
    private static final Integer INTEGER_FRI = 5;
    private static final Integer INTEGER_SAT = 6;
    private static final Integer INTEGER_SUN = 7;

    private static final String FULL_STRING_MON = "Monday";
    private static final String FULL_STRING_TUE = "Tuesday";
    private static final String FULL_STRING_WED = "Wednesday";
    private static final String FULL_STRING_THU = "Thursday";
    private static final String FULL_STRING_FRI = "Friday";
    private static final String FULL_STRING_SAT = "Saturday";
    private static final String FULL_STRING_SUN = "Sunday";

    public static final String MESSAGE_DAY_CONSTRAINTS = "Day should be of the format: first 3 letters of Day"
            + "(not case sensitive) i,e.\n"
            + STRING_MON + ", " + STRING_TUE + ", " + STRING_WED + ", " + STRING_THU + ", "
            + STRING_FRI + ", " + STRING_SAT + ", " + STRING_SUN + "\";\n\n";

    private static final HashMap<String, Integer> dayToIntMap = new HashMap<>();
    private static final HashMap<String, String> dayToFullDayMap = new HashMap<>();
    private static final HashMap<String, DayOfWeek> dayToDayOfWeekEnumMap = new HashMap<>();
    private static final String DAY_REGEX = "^(mon|tue|wed|thu|fri|sat|sun)";

    public final String value;

    /**
     * Builds the {@code dayToIntMap} for the integer value of the day in week
     * Week starts on Monday, with value of 1, end on Sunday, with value of 7
     *
     * Builds the {@code dayToFullDayMap} for short form to long form names of days,
     *
     * Builds the {@code dayToDayOfWeekEnumMap} for use with Google Calendar upload
     */
    static {
        dayToIntMap.put(STRING_MON, INTEGER_MON);
        dayToIntMap.put(STRING_TUE, INTEGER_TUE);
        dayToIntMap.put(STRING_WED, INTEGER_WED);
        dayToIntMap.put(STRING_THU, INTEGER_THU);
        dayToIntMap.put(STRING_FRI, INTEGER_FRI);
        dayToIntMap.put(STRING_SAT, INTEGER_SAT);
        dayToIntMap.put(STRING_SUN, INTEGER_SUN);

        dayToFullDayMap.put(STRING_MON, FULL_STRING_MON);
        dayToFullDayMap.put(STRING_TUE, FULL_STRING_TUE);
        dayToFullDayMap.put(STRING_WED, FULL_STRING_WED);
        dayToFullDayMap.put(STRING_THU, FULL_STRING_THU);
        dayToFullDayMap.put(STRING_FRI, FULL_STRING_FRI);
        dayToFullDayMap.put(STRING_SAT, FULL_STRING_SAT);
        dayToFullDayMap.put(STRING_SUN, FULL_STRING_SUN);

        dayToDayOfWeekEnumMap.put(STRING_MON, DayOfWeek.MONDAY);
        dayToDayOfWeekEnumMap.put(STRING_TUE, DayOfWeek.TUESDAY);
        dayToDayOfWeekEnumMap.put(STRING_WED, DayOfWeek.WEDNESDAY);
        dayToDayOfWeekEnumMap.put(STRING_THU, DayOfWeek.THURSDAY);
        dayToDayOfWeekEnumMap.put(STRING_FRI, DayOfWeek.FRIDAY);
        dayToDayOfWeekEnumMap.put(STRING_SAT, DayOfWeek.SATURDAY);
        dayToDayOfWeekEnumMap.put(STRING_SUN, DayOfWeek.SUNDAY);
    }

    /**
     * Constructs an {@code Day}.
     *
     * @param day A valid day string.
     */
    public Day(String day) {
        requireNonNull(day);
        checkArgument(isValidDay(day), MESSAGE_DAY_CONSTRAINTS);
        this.value = day;
    }

    /**
     * Returns if a given string is a valid student day.
     */
    public static boolean isValidDay(String test) {
        return test.matches(DAY_REGEX);
    }

    public static int dayToIntValue(String day) {
        return dayToIntMap.get(day);
    }

    public static DayOfWeek dayToDayOfWeekEnum(Day day) {
        return dayToDayOfWeekEnumMap.get(day.toString());
    }

    public int intValue() {
        return dayToIntMap.get(this.value);
    }
    public String fullDayName() {
        return dayToFullDayMap.get(this.value);
    }


    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Day // instanceof handles nulls
                && this.value.equals(((Day) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public int compareTo(Day other) {
        return this.intValue() - other.intValue();
    }
}
