package seedu.address.model.person.timetable;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.timetable.TimetableParserUtil.parseUrl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents a Person's timetable in the address book
 */
public class Timetable {

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
        if (url.equals("")) {    // Create new empty timetable if url is empty
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
