package seedu.flashy.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.flashy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.flashy.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.flashy.commons.core.index.Index;
import seedu.flashy.commons.exceptions.IllegalValueException;
import seedu.flashy.logic.commands.EditCommand;
import seedu.flashy.logic.commands.EditCommand.EditTagDescriptor;
import seedu.flashy.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME);
        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        EditCommand.EditTagDescriptor editTagDescriptor = new EditTagDescriptor();
        try {
            ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME)).ifPresent(editTagDescriptor::setName);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        if (!editTagDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editTagDescriptor);
    }
}
