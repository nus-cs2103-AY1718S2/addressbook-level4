package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.BirthdaysCommand;

import seedu.address.logic.parser.exceptions.ParseException;

//@@author AzuraAiR
/**
 * Parses input arguments and creates a new BirthdaysCommand object
 */
public class BirthdaysCommandParser implements Parser<BirthdaysCommand> {


    /**
     * Parses the given {@code String} of arguments in the context of the BirthdaysCommand
     * and returns an BirthdaysCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public BirthdaysCommand parse(String args) throws ParseException {
        args = args.trim();
        String[] trimmedArgs = args.split("\\s+");

        if (trimmedArgs.length == 1) {   // Only birthdays alone is called
            if (trimmedArgs[0].equalsIgnoreCase(BirthdaysCommand.ADDITIONAL_COMMAND_PARAMETER)) {  // Check if valid
                return new BirthdaysCommand(true);
            } else if (trimmedArgs[0].equalsIgnoreCase("")) {
                return new BirthdaysCommand(false);
            }
        } else {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, BirthdaysCommand.MESSAGE_USAGE));
        }

        return new BirthdaysCommand(false);
    }

}
