package seedu.address.model.transaction;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
//@@author ongkc
/**
 * Represents a Transaction's description in the address book.
 */
public class Description {
    public static final String MESSAGE_DESCRIPTION_CONSTRAINTS =
            "Transaction description can take any value and should not be blank";
    /*
     * Description must not be only space or "" (a blank string).
     */
    public static final String DESCRIPTION_VALIDATION_REGEX = "^(\\s|\\S)*(\\S)+(\\s|\\S)*$";

    public final String value;

    /**
     * Constructs an {@code Description}.
     *
     * @param description A valid description.
     */
    public Description(String description) {
        requireNonNull(description);
        checkArgument(isValidDescription(description), MESSAGE_DESCRIPTION_CONSTRAINTS);
        this.value = description;
    }

    /**
     * Returns true if a given string is a valid person email.
     */
    public static boolean isValidDescription(String test) {
        return test.matches(DESCRIPTION_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}

