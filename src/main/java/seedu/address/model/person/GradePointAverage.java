package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's GradePointAverage in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidGradePointAverage(String)}
 */
public class GradePointAverage {
    public static final String MESSAGE_GRADE_POINT_AVERAGE_CONSTRAINTS =
            "Grade point average can only floating point numbers, and should be between 0.00 and 5.00 "
                    + "(in 2 decimal point).";
    public static final double GPA_LOWER_BOUND = 0.0;
    public static final double GPA_UPPER_BOUND = 5.0;
    private static final String EXPECTED_GRADE_POINT_AVERAGE_REGEX = "\\d+([.]\\d{2})?";
    public final String value;

    /**
     * Constructs a {@code gradePointAverage}.
     *
     * @param gradePointAverage A valid gradePointAverage.
     */
    public GradePointAverage(String gradePointAverage) {
        requireNonNull(gradePointAverage);
        checkArgument(isValidGradePointAverage(gradePointAverage),
                MESSAGE_GRADE_POINT_AVERAGE_CONSTRAINTS);
        this.value = gradePointAverage;
    }

    /**
     * Returns true if a given string is a valid gradePointAverage.
     */
    public static boolean isValidGradePointAverage(String test) {
        return test.matches(EXPECTED_GRADE_POINT_AVERAGE_REGEX) && isInValidRange(test);
    }

    /**
     *
     * @param test A grade point average matching regex
     * @return whether the grade point average is in valid range
     */
    private static boolean isInValidRange(String test) {
        double gradePointAverage = Double.parseDouble(test);
        return gradePointAverage >= GPA_LOWER_BOUND && gradePointAverage <= GPA_UPPER_BOUND;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GradePointAverage // instanceof handles nulls
                && this.value.equals(((GradePointAverage) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
