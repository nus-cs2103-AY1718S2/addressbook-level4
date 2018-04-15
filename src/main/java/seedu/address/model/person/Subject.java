package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Arrays;
import java.util.HashSet;

//@@author alexawangzi
/**
 * Represents a Person's subject in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidSubject(String)}
 */
public class Subject {

    public static final String[] SUBJECT_VALUES =
            new String[]{"english", "eng", "chinese", "chi", "math", "physics", "phy", "chemistry", "chem", ""};
    public static final HashSet<String> SET_ALL_SUBJECT = new HashSet<>(Arrays.asList(SUBJECT_VALUES));

    public static final String MESSAGE_SUBJECT_CONSTRAINTS = "Subject should be one of: \n"
            + SET_ALL_SUBJECT.toString()
            + "\n";

    public final String value;

    /**
     * Constructs an {@code Subject}.
     *
     * @param subject A valid subject description.
     */
    public Subject(String subject) {
        requireNonNull(subject);
        subject = validateSubject(subject);
        this.value = formatsubject(subject);
    }

    private String formatsubject(String subject) {
        ProperCaseConverter pc = new ProperCaseConverter();
        return pc.convertToProperCase(subject);
    }


    /**
     * check validity of the subject string supplied
     * @param subject
     * @return string representing a valid subject
     */
    private String validateSubject(String subject) {

        subject.toLowerCase();
        checkArgument(isValidSubject(subject), MESSAGE_SUBJECT_CONSTRAINTS);
        subject = convertToFullSubject(subject);
        return subject;
    }

    /**
     * Convert a shortcut to full subject name
     */
    public String convertToFullSubject(String original) {
        String cur = original.toLowerCase();
        if (cur.equals("eng")) {
            cur = "english";
        } else if (cur.equals("chi")) {
            cur = "chinese";
        } else if (cur.equals("phy")) {
            cur = "physics";
        } else if (cur.equals("chem")) {
            cur = "chemistry";
        }
        return cur;
    }


    /**
     * Returns if a given string is a valid subject description.
     */
    public static boolean isValidSubject(String test) {
        test = test.toLowerCase();
        return SET_ALL_SUBJECT.contains(test);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Subject // instanceof handles nulls
                && this.value.equals(((Subject) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
