package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.StatusCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Status;
/**
 * Parses input arguments and creates a new StatusCommand object
 */
public class StatusCommandParser implements Parser<StatusCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the StatusCommand
     * and returns an StatusCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public StatusCommand parse(String args) throws ParseException {
        try {
            // Parse the arguments
            String[] arguments = args.trim().split("\\s+", 2);
            if (arguments.length != 2) {
                throw new IllegalValueException("Invalid command, expected 2 arguments");
            }
            // Parse the index
            Index index = ParserUtil.parseIndex(arguments[0]);

            // Parse the status
            int statusIndex = Integer.valueOf(arguments[1]);
            Status status = null;
            if (Status.isValidStatus(statusIndex)) {
                status = new Status(statusIndex);
            } else {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatusCommand.MESSAGE_USAGE));
            }

            return new StatusCommand(index, status);

        } catch (NumberFormatException nfe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatusCommand.MESSAGE_USAGE));
        } catch (ParseException pe) {
            throw pe;

        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatusCommand.MESSAGE_USAGE));
        }
    }
}
