package seedu.progresschecker.model.exercise;

import static java.util.Objects.requireNonNull;

//@@author iNekox3
/**
 * Represents an Exercise's model answer in the ProgressChecker.
 */
public class ModelAnswer {

    public final String value;

    /**
     * Constructs a {@code ModelAnswer}.
     *
     * @param answer An answer of any word and character.
     */
    public ModelAnswer(String answer) {
        requireNonNull(answer);
        this.value = answer;
    }

    @Override
    public String toString() {
        return value;
    }
}
