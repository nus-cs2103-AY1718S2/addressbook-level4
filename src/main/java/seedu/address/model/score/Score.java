package seedu.address.model.score;

import static java.util.Objects.requireNonNull;

//@@author TeyXinHui
/**
 * Represents a Person's L1R5 score in the address book.
 * Guarantees: immutable;
 */
public class Score {

    public final String score;

    /**
     * Constructs a {@code Score}.
     *
     * @param score A valid score.
     */
    public Score(String score) {
        requireNonNull(score);
        this.score = score;
    }


    @Override
    public String toString() {
        return score + " ";
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Score // instanceof handles nulls
                && (this.score == (((Score) other).score))); // state check
    }

    @Override
    public int hashCode() {
        return score.hashCode();
    }
}
//@@author
