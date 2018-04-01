package seedu.progresschecker.model.exercise;

import static java.util.Objects.requireNonNull;
import static seedu.progresschecker.commons.util.AppUtil.checkArgument;

//@@author iNekox3
/**
 * Represents an Exercise's question index in the ProgressChecker.
 */
public class QuestionIndex {

    public static final String MESSAGE_INDEX_CONSTRAINTS =
            "Indices can only contain numbers, and should be in the format of"
            + "SECTION NUMBER.PART NUMBER.QUESTION NUMBER";
    public static final String INDEX_VALIDATION_REGEX = "([2-9]|1[0-3])\\.([0-9]|[0-9]{2})\\.([0-9]|[0-9]{2})";
    public final String value;

    /**
     * Constructs a {@code QuestionIndex}.
     *
     * @param index A valid index number.
     */
    public QuestionIndex(String index) {
        requireNonNull(index);
        checkArgument(isValidIndex(index), MESSAGE_INDEX_CONSTRAINTS);
        this.value = index;
    }

    /**
     * Returns true if a given string is a valid index number.
     */
    public static boolean isValidIndex(String test) {
        return test.matches(INDEX_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }
}
