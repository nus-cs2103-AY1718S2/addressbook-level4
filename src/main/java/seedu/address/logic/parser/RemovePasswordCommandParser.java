package seedu.address.logic.parser;

import seedu.address.logic.commands.RemovePasswordCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author yeggasd
/**
 * Parses input arguments and creates a new AddCommand object
 */
public class RemovePasswordCommandParser implements Parser<RemovePasswordCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemovePasswordCommand parse(String arguments) {
        return new RemovePasswordCommand();
    }
}
