package seedu.address.model.coin;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Coin's currency code in the book.
 * Guarantees: immutable; is valid as declared in {@link #isValidCode(String)}
 */
public class Code {


    public static final String MESSAGE_CODE_CONSTRAINTS =
            "Currency codes can only contain letters";
    public static final String CODE_VALIDATION_REGEX = "[a-zA-Z]{3,}";
    public final String value;

    /**
     * Constructs a {@code Code}.
     *
     * @param code A valid currency code.
     */
    public Code(String code) {
        requireNonNull(code);
        checkArgument(isValidCode(code), MESSAGE_CODE_CONSTRAINTS);
        this.value = code;
    }

    /**
     * Returns true if a given string is a valid coin phone number.
     */
    public static boolean isValidCode(String test) {
        return test.matches(CODE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Code // instanceof handles nulls
                && this.value.equals(((Code) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
