package seedu.address.model.person.timetable;

import seedu.address.logic.parser.exceptions.ParseException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.timetable.TimetableParserUtil.parseUrl;

public class Timetable {

    private static final String NUSMODS_SHORT = "modsn.us";
    private static final String URL_HOST_REGEX = "\\/\\/.*?\\/";
    public static final String MESSAGE_URL_CONSTRAINTS =
            "Timetable URL should only be NUSMods shortened URLs";
    public static final String MESSAGE_INVALID_URL =
            "The given NUSMods URL is invalid";

    public final String value;

    private TimetableData data;

    public Timetable(String url) {
        requireNonNull(url);
        String trimmedUrl = url.trim();
        checkArgument(isValidUrl(trimmedUrl), MESSAGE_URL_CONSTRAINTS);

        this.value = trimmedUrl;
        try {
            this.data = parseUrl(trimmedUrl);
        } catch (ParseException pe){
            this.data = new TimetableData(); // Create new empty timetable if url fails
        }
    }

    /**
     * Returns if a url is a valid NUSMods url
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
