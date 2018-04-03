package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
//@@author mhq199657
/**
 * Represents a Person's expectedGraduationYear in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidExpectedGraduationYear(String)}
 */
public class ExpectedGraduationYear {
    public static final String MESSAGE_EXPECTED_GRADUATION_YEAR_CONSTRAINTS =
            "Expected graduation year can only contain numbers, and should be between 2017 to 2025.";
    private static final String EXPECTED_GRADUATION_YEAR_VALIDATION_REGEX = "\\d{4}";
    private static final int YEAR_LOWER_BOUND = 2017;
    private static final int YEAR_UPPER_BOUND = 2025;
    public final String value;
    /**
     * Constructs a {@code expectedGraduationYear}.
     *
     * @param expectedGraduationYear A valid expectedGraduationYear.
     */
    public ExpectedGraduationYear(String expectedGraduationYear) {
        requireNonNull(expectedGraduationYear);
        checkArgument(isValidExpectedGraduationYear(expectedGraduationYear),
                MESSAGE_EXPECTED_GRADUATION_YEAR_CONSTRAINTS);
        this.value = expectedGraduationYear;
    }

    /**
     * Returns true if a given string is a valid expectedGraduationYear.
     */
    public static boolean isValidExpectedGraduationYear(String test) {
        return test.matches(EXPECTED_GRADUATION_YEAR_VALIDATION_REGEX) && isInValidRange(test);
    }

    /**
     *
     * @param test An expected graduation year matching regex
     * @return whether the graduation year is in valid range
     */
    private static boolean isInValidRange(String test) {
        int year = Integer.parseInt(test);
        return year >= YEAR_LOWER_BOUND && year <= YEAR_UPPER_BOUND;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExpectedGraduationYear // instanceof handles nulls
                && this.value.equals(((ExpectedGraduationYear) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
