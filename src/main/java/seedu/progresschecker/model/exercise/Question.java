package seedu.progresschecker.model.exercise;

import static java.util.Objects.requireNonNull;

//@@author iNekox3
/**
 * Represents an Exercise's question in the ProgressChecker.
 */
public class Question {

    public final String value;

    /**
     * Constructs a {@code Question}.
     *
     * @param question A question of any word and character.
     */
    public Question(String question) {
        requireNonNull(question);
        this.value = question;
    }

    @Override
    public String toString() {
        return value;
    }
}
