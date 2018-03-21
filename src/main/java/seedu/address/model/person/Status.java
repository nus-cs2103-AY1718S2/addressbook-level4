package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.HashMap;

import javafx.scene.paint.Color;

/**
 * Represents a candidate's status of his/her application.
 * Guarantees: Status value is one of the seven predefined values.
 */
public class Status {

    public static final String MESSAGE_STATUS_CONSTRAINTS =
            "Person status should only be specified by one of the predefined status indices.";
    public static final String STATUS_NEW = "New";
    public static final String STATUS_INTERVIEW_FIRST_ROUND = "1st round";
    public static final String STATUS_INTERVIEW_SECOND_ROUND = "2nd round";
    public static final String STATUS_REJECTED = "Rejected";
    public static final String STATUS_WAITLIST = "Waitlist";
    public static final String STATUS_OFFERED = "Offered";
    public static final String STATUS_ACCEPTED = "Accepted";
    public static final String STATUS_WITHDRAWN = "Withdrawn";

    public static final int STATUS_TYPE_CONUT = 8;

    public static final int INDEX_STATUS_NEW = 1;
    public static final int INDEX_STATUS_INTERVIEW_FIRST_ROUND = 2;
    public static final int INDEX_STATUS_INTERVIEW_SECOND_ROUND = 3;
    public static final int INDEX_STATUS_REJECTED = 4;
    public static final int INDEX_STATUS_WAITLIST = 5;
    public static final int INDEX_STATUS_OFFERED = 6;
    public static final int INDEX_STATUS_ACCEPTED = 7;
    public static final int INDEX_STATUS_WITHDRAWN = 8;

    public static final Color COLOR_NEW = Color.GREY;
    public static final Color COLOR_INTERVIEW_FIRST_ROUND = Color.YELLOW;
    public static final Color COLOR_INTERVIEW_SECOND_ROUND = Color.ORANGE;
    public static final Color COLOR_REJECTED = Color.RED;
    public static final Color COLOR_WAITLIST = Color.BROWN;
    public static final Color COLOR_OFFERED = Color.CYAN;
    public static final Color COLOR_ACCEPTED = Color.GREEN;
    public static final Color COLOR_WITHDRAWN = Color.PURPLE;

    private static final HashMap<Integer, String> STATUS_MAP = new HashMap<Integer, String>() {{
            put(INDEX_STATUS_NEW, STATUS_NEW);
            put(INDEX_STATUS_INTERVIEW_FIRST_ROUND, STATUS_INTERVIEW_FIRST_ROUND);
            put(INDEX_STATUS_INTERVIEW_SECOND_ROUND, STATUS_INTERVIEW_SECOND_ROUND);
            put(INDEX_STATUS_REJECTED, STATUS_REJECTED);
            put(INDEX_STATUS_WAITLIST, STATUS_WAITLIST);
            put(INDEX_STATUS_OFFERED, STATUS_OFFERED);
            put(INDEX_STATUS_ACCEPTED, STATUS_ACCEPTED);
            put(INDEX_STATUS_WITHDRAWN, STATUS_WITHDRAWN); }};

    private static final HashMap<String, Color> COLOR_MAP = new HashMap<String, Color>() {{
            put(STATUS_NEW, COLOR_NEW);
            put(STATUS_INTERVIEW_FIRST_ROUND, COLOR_INTERVIEW_FIRST_ROUND);
            put(STATUS_INTERVIEW_SECOND_ROUND, COLOR_INTERVIEW_SECOND_ROUND);
            put(STATUS_REJECTED, COLOR_REJECTED);
            put(STATUS_WAITLIST, COLOR_WAITLIST);
            put(STATUS_OFFERED, COLOR_OFFERED);
            put(STATUS_ACCEPTED, COLOR_ACCEPTED);
            put(STATUS_WITHDRAWN, COLOR_WITHDRAWN); }};

    public final String value;
    public final Color color;

    /**
     * Constructs a {@code Status}.
     *
     * @param statusIndex A valid status index.
     */
    public Status(int statusIndex) {
        checkArgument(isValidStatus(statusIndex), MESSAGE_STATUS_CONSTRAINTS);
        this.value = getStatus(statusIndex);
        this.color = getColor(value);
    }

    public Status(String status) {
        requireNonNull(status);
        checkArgument(isValidXmlStatus(status), MESSAGE_STATUS_CONSTRAINTS);
        this.value = status;
        this.color = getColor(value);
    }

    public Status() {
        this.value = getStatus(INDEX_STATUS_NEW);
        this.color = getColor(value);
    }

    /**
     * Returns true if a given string is a valid person major.
     */
    public static boolean isValidStatus (int test) {
        return STATUS_MAP.containsKey(test);
    }

    public static boolean isValidXmlStatus(String test) {
        return COLOR_MAP.containsKey(test);
    }

    private static String getStatus(int statusIndex) {
        return STATUS_MAP.get(statusIndex);
    }

    private static Color getColor(String status) {
        return COLOR_MAP.get(status);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Status // instanceof handles nulls
                && this.value.equals(((Status) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
