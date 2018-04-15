package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DeleteTransactionCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
//@author phmignot
/**
 * Parses input arguments and creates a new DeleteTransactionCommand object
 */
public class DeleteTransactionCommandParser implements Parser<DeleteTransactionCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteTransactionCommand
     * and returns an DeleteTransactionCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteTransactionCommand parse(String args, Model model) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteTransactionCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTransactionCommand.MESSAGE_USAGE));
        }
    }

}

