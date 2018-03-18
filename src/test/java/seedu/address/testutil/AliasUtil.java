package seedu.address.testutil;

import seedu.address.logic.commands.AliasCommand;
import seedu.address.model.alias.Alias;

/**
 * A utility class for Alias.
 */
public class AliasUtil {

    /**
     * Returns an alias command string for adding the {@code alias}.
     */
    public static String getAliasCommand(Alias alias) {
        return AliasCommand.COMMAND_WORD + " " + getAliasDetails(alias);
    }

    /**
     * Returns the part of command string for the given {@code alias}'s details.
     */
    public static String getAliasDetails(Alias alias) {
        StringBuilder sb = new StringBuilder();
        sb.append(alias.getCommand() + " ");
        sb.append(alias.getAlias());
        return sb.toString();
    }
}