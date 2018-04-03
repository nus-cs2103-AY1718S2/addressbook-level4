package seedu.address.model.person.timetable;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.timetable.TimetableParserUtil.parseUrl;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.timetable.Lesson;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents a Person's timetable in the address book
 */
public class Timetable {

    public static final String DUMMY_LINK_ONE = "http://modsn.us/aaaaa";
    public static final String DUMMY_LINK_TWO = "http://modsn.us/bbbbb";
    public static final String NUSMODS_SHORT = "modsn.us";
    public static final String URL_HOST_REGEX = "\\/\\/.*?\\/";
    public static final String MESSAGE_URL_CONSTRAINTS =
            "Timetable URL should only be NUSMods shortened URLs";
    public static final String MESSAGE_INVALID_URL =
            "The given NUSMods URL is invalid";

    public final String value;

    private TimetableData data;

    public Timetable(String url) {
        requireNonNull(url);
        this.value = url;
        // Create new empty timetable if url is empty or a dummy link
        if (url.equals("") || url.equals(DUMMY_LINK_ONE) || url.equals(DUMMY_LINK_TWO)) {
            this.data = new TimetableData();
            return;
        }

        checkArgument(isValidUrl(url), MESSAGE_URL_CONSTRAINTS);

        try {
            this.data = parseUrl(url);
        } catch (ParseException pe) {
            this.data = new TimetableData(); // Create new empty timetable if url fails
        }
    }

    /**
     * Checks if string is a valid shortened NUSMods url
     * @param test
     * @return
     */
    public static boolean isValidUrl(String test) {
        Matcher matcher = Pattern.compile(URL_HOST_REGEX).matcher(test);
        if (!matcher.find()) {
            return false;
        }

        String hostName = matcher.group()
                .substring(2, matcher.group().length() - 1);
        return hostName.equals(NUSMODS_SHORT);
    }

    /**
     * Returns the lesson at the specified slot, null if empty
     * @param week the week the lesson is found at
     * @param day the day the lesson is found at
     * @param timeSlot the timeslot the lesson is found at
     * @return Lesson found at that slot, null if slot is empty
     * @throws IllegalValueException when week,day,timeslot are invalid values
     */
    public Lesson getLessonFromSlot(String week, String day, int timeSlot) throws IllegalValueException {
        return data.getLessonFromSlot(week, day, timeSlot);
    }

    /**
     * Adds a lesson to the timetable
     * @param lessonToAdd lesson to be added
     * @throws IllegalValueException when week,day,timeslot are invalid values
     */
    public void addLessonToSlot(Lesson lessonToAdd) throws IllegalValueException {
        data.addLessonToSlot(lessonToAdd);
    }

    /**
     * Returns the timetable
     * @return Timetable as an Array
     */
    public ArrayList<ArrayList<ArrayList<String>>> getTimetable() {
        return data.getTimeTable();
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Timetable // instanceof handles nulls
                && this.value.equals(((Timetable) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
