package seedu.address.model.subject;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's subjects in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidSubject(String)}
 */
public class Subject {

    public static final String MESSAGE_SUBJECT_CONSTRAINTS = "Subjects should be alphanumeric";
    public static final String SUBJECT_VALIDATION_REGEX = "\\p{Alnum}+";

    public final String subject;

    /**
     * Constructs a {@code Subject}.
     *
     * Returns true if a given string is a valid subject.
     */
    public Subject(String subject) {
        requireNonNull(subject);
        checkArgument(isValidSubject(subject), MESSAGE_SUBJECT_CONSTRAINTS);
        this.subject = subject;
    }

    /**
     * @param test
     * @return true if a given string is a valid subject
     */
    public static boolean isValidSubject(String test) {
        return test.matches(SUBJECT_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Subject // instanceof handles nulls
                && this.subject.equals(((Subject) other).subject)); // state check
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return subject;
    }


}
