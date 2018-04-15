// @@author anh2111
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OPTION;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 *  ArgumentWithOption class encapsulates an argument in the format: [OPTION] ARGS, and handles the parsing, extracting
 *  option from the argument.
 */
public class ArgumentWithOption {

    private static final Pattern ARGUMENT_FORMAT =
            Pattern.compile("(?<commandOption>" + PREFIX_OPTION.getPrefix() + "\\S+)?(?<arguments>.*)");
    private String rawArgs;
    private final String option;
    private String args;

    public ArgumentWithOption(String rawArgs) throws ParseException {
        this.rawArgs = rawArgs.trim();

        final Matcher matcher = ARGUMENT_FORMAT.matcher(this.rawArgs);
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }
        this.args = matcher.group("arguments");

        String rawOption = matcher.group("commandOption");
        this.option = (rawOption != null) ?  rawOption.substring(PREFIX_OPTION.getPrefix().length()) : "";
    }

    public boolean isOption(String toCheck) {
        return toCheck.equals(option);
    }

    public String getArgs() {
        return args;
    }
}
// @@author
