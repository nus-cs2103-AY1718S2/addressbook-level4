package seedu.address.model.alias;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

//@@author jingyinno
/**
 * Represents an Alias in the address book.
 * Guarantees: immutable; alias and command are valid as declared in {@link #isValidAliasParameter(String)}
 */
public class Alias {

    public static final String MESSAGE_ALIAS_CONSTRAINTS = "Alias arguments should be alphanumeric";
    public static final String ALIAS_VALIDATION_REGEX = "\\p{Alnum}+";

    private final String command;
    private final String aliasName;

    /**
     * Constructs an {@code Alias}.
     *
     * @param command A valid command word
     * @param aliasName A valid alias name.
     */
    public Alias(String command, String aliasName) {
        requireNonNull(aliasName);
        checkArgument(isValidAliasParameter(aliasName), MESSAGE_ALIAS_CONSTRAINTS);
        checkArgument(isValidAliasParameter(command), MESSAGE_ALIAS_CONSTRAINTS);
        this.aliasName = aliasName;
        this.command = command;
    }

    /**
     *
     * @return the command word
     */
    public String getCommand() {
        return command;
    }

    /**
     *
     * @return the alias name
     */
    public String getAlias() {
        return aliasName;
    }

    /**
     * Returns true if a given string is a valid Alias parameter.
     */
    public static boolean isValidAliasParameter(String test) {
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
