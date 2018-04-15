package seedu.flashy.model.card.exceptions;

/**
 * Signals that the operation is unable to find the specified card.
 */
public class CardNotFoundException extends Exception {
    public CardNotFoundException() {
        super("Cannot find card.");
    }
}
