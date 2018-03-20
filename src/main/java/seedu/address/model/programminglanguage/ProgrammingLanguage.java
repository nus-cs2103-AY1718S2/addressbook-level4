package seedu.address.model.programminglanguage;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Student's programming language in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidProgrammingLanguage(String)}
 */
public class ProgrammingLanguage {

    public static final String MESSAGE_PROGRAMMING_LANGUAGE_CONSTRAINTS = "Programming language should be a visibile "
            + "character";
    public static final String PROGRAMMING_LANGUAGE_VALIDATION_REGEX = "\\p{Graph}+";

    public final String programmingLanguage;

    /**
     * Constructs a {@code programminglanguage}.
     *
     * Returns true if a given string is a valid programmingLanguage.
     */
    public ProgrammingLanguage(String programmingLanguage) {
        requireNonNull(programmingLanguage);
        checkArgument(isValidProgrammingLanguage(programmingLanguage), MESSAGE_PROGRAMMING_LANGUAGE_CONSTRAINTS);
        this.programmingLanguage = programmingLanguage;
    }

    /**
     * @param test
     * @return true if a given string is a valid programmingLanguage
     */
    public static boolean isValidProgrammingLanguage(String test) {
        return test.matches(PROGRAMMING_LANGUAGE_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ProgrammingLanguage // instanceof handles nulls
                && this.programmingLanguage.equals(((ProgrammingLanguage) other).programmingLanguage)); // state check
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return programmingLanguage;
    }


}
