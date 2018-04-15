package seedu.progresschecker.model.exercise;

import static java.util.Objects.requireNonNull;
import static seedu.progresschecker.commons.util.AppUtil.checkArgument;

//@@author iNekox3
/**
 * Represents an Exercise's question type in the ProgressChecker.
 */
public class QuestionType {

    public static final String MESSAGE_TYPE_CONSTRAINTS =
            "Type can only be 'text' or 'choice'";
    public static final String TYPE_VALIDATION_REGEX = "text|choice";
    public final String value;

    /**
     * Constructs a {@code QuestionType}.
     *
     * @param type A valid type.
     */
    public QuestionType(String type) {
        requireNonNull(type);
        checkArgument(isValidType(type), MESSAGE_TYPE_CONSTRAINTS);
        this.value = type;
    }

    /**
     * Returns true if a given string is a valid type.
     */
    public static boolean isValidType(String test) {
        return test.matches(TYPE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }
}
