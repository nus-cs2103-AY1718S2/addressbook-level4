package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;

import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author jasmoon
/**
 * Parses input arguments and create a new ListCommand object.
 */
public class ListCommandParser implements Parser<ListCommand> {

    public final ArrayList<String> availableCommands;

    public ListCommandParser()  {
        availableCommands = new ArrayList<String>();
        availableCommands.add("task");
        availableCommands.add("event");
    }

    /**
     * Parses the given {@code String} of arguments in the context of the ListCommand
     * and returns an ListCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListCommand parse(String args) throws ParseException {
        String commandRequest = args.trim();

        if (availableCommands.contains(commandRequest) || commandRequest.length() == 0)   {
            return new ListCommand(commandRequest);
        } else  {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }
    }

}
