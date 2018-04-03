//@@author nhatquang3112
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTENT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditToDoCommand;
import seedu.address.logic.commands.EditToDoCommand.EditToDoDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditToDoCommand object
 */
public class EditToDoCommandParser implements Parser<EditToDoCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditToDoCommand
     * and returns an EditToDoCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditToDoCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_CONTENT);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditToDoCommand.MESSAGE_USAGE));
        }

        EditToDoDescriptor editToDoDescriptor = new EditToDoDescriptor();
        try {
            ParserUtil.parseContent(argMultimap.getValue(PREFIX_CONTENT)).ifPresent(editToDoDescriptor::setContent);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        if (!editToDoDescriptor.isContentFieldEdited()) {
            throw new ParseException(EditToDoCommand.MESSAGE_NOT_EDITED_TODO);
        }

        return new EditToDoCommand(index, editToDoDescriptor);
    }
}
