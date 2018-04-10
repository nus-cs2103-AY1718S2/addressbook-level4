package seedu.address.model.event;
//@@author SuxianAlicia
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents Title of a {@code CalendarEntry} in Event list of Address Book.
 * Guarantees: immutable; is valid as declared in {@link #isValidEntryTitle(String)}
 */
public class EntryTitle {
    public static final String MESSAGE_ENTRY_TITLE_CONSTRAINTS =
            "Event title should only contain alphanumeric characters and spaces"
                    + "and it should not be blank";

    public static final String ENTRY_TITLE_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    private final String entryTitle;

    /**
     * Constructs {@code EntryTitle}.
     *
     * @param entryTitle Valid event title.
     */
    public EntryTitle(String entryTitle) {
        requireNonNull(entryTitle);
        checkArgument(isValidEntryTitle(entryTitle), MESSAGE_ENTRY_TITLE_CONSTRAINTS);
        this.entryTitle = entryTitle;
    }

    /**
     * Returns true if a given string is a valid event title.
     */
    public static boolean isValidEntryTitle(String test) {
        return test.matches(ENTRY_TITLE_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return entryTitle;
    }

    /**
     * entryTitle matching is non case-sensitive
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EntryTitle // instanceof handles nulls
                && this.entryTitle.equalsIgnoreCase(((EntryTitle) other).entryTitle)); // state check
    }

    @Override
    public int hashCode() {
        return entryTitle.hashCode();
    }
}
