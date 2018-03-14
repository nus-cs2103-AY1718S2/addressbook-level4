package seedu.address.model.alias;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Alias in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidAliasName(String)}
 */
public class Alias {

    public static final String MESSAGE_ALIAS_CONSTRAINTS = "ALias names should be alphanumeric";
    public static final String ALIAS_VALIDATION_REGEX = "\\p{Alnum}+";

    public final String command;
    public final String aliasName;

    /**
     * Constructs a {@code Alias}.
     *
     * @param aliasName A valid alias name.
     */
    public Alias(String command, String aliasName) {
        requireNonNull(aliasName);
        checkArgument(isValidAliasName(aliasName), MESSAGE_ALIAS_CONSTRAINTS);
        this.aliasName = aliasName;
        this.command = command;
    }

    /**
     * Returns true if a given string is a valid alias name.
     */
    public static boolean isValidAliasName(String test) {
        return test.matches(ALIAS_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Alias // instanceof handles nulls
                && this.aliasName.equals(((Alias) other).aliasName)
                && this.command.equals(((Alias) other).command)); // state check
    }

    @Override
    public int hashCode() {
        return aliasName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + aliasName + ']';
    }

}
