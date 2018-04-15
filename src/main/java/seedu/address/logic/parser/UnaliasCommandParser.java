package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.UnaliasCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author jingyinno
/**
 * Parses input arguments and creates a new UnaliasCommand object
 */
public class UnaliasCommandParser implements Parser<UnaliasCommand> {
    private static final String SPLIT_TOKEN = "\\s+";
    private static final int CORRECT_ARGS_LENGTH = 1;
    
    /**
     * Parses the given {@code String} of arguments in the context of the UnaliasCommand
     * and returns an UnaliasCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UnaliasCommand parse(String args) throws ParseException {
        String unalias = validateNumberOfArgs(args);
        try {
            String toBeRemoved = ParserUtil.parseUnalias(unalias);
            return new UnaliasCommand(toBeRemoved);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Returns a not empty String of unalias.
     */
    private String validateNumberOfArgs(String args) throws ParseException {
        String unalias = args.trim();
        String[] splitArgs = unalias.split(SPLIT_TOKEN);
        if (unalias.isEmpty() || splitArgs.length != CORRECT_ARGS_LENGTH) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnaliasCommand.MESSAGE_USAGE));
        }
        return unalias;
    }
}
