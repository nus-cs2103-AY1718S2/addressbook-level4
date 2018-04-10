package seedu.address.logic.parser;
//@@author SuxianAlicia
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENTRY_TITLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditEntryCommand;
import seedu.address.logic.commands.EditEntryCommand.EditEntryDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditEntryCommand object.
 */
public class EditEntryCommandParser implements Parser<EditEntryCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditEntryCommand
     * and returns an EditEntryCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    @Override
    public EditEntryCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(userInput, PREFIX_ENTRY_TITLE, PREFIX_START_DATE, PREFIX_END_DATE,
                        PREFIX_START_TIME, PREFIX_END_TIME);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditEntryCommand.MESSAGE_USAGE));
        }

        EditEntryDescriptor editEntryDescriptor = new EditEntryDescriptor();

        try {
            ParserUtil.parseEventTitle(argMultimap.getValue(PREFIX_ENTRY_TITLE))
                    .ifPresent(editEntryDescriptor::setEntryTitle);
            ParserUtil.parseStartDate(argMultimap.getValue(PREFIX_START_DATE))
                    .ifPresent(editEntryDescriptor::setStartDate);
            ParserUtil.parseEndDate(argMultimap.getValue(PREFIX_END_DATE))
                    .ifPresent(editEntryDescriptor::setEndDate);
            ParserUtil.parseStartTime(argMultimap.getValue(PREFIX_START_TIME))
                    .ifPresent(editEntryDescriptor::setStartTime);
            ParserUtil.parseEndTime(argMultimap.getValue(PREFIX_END_TIME))
                    .ifPresent(editEntryDescriptor::setEndTime);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        if (!editEntryDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditEntryCommand.MESSAGE_NOT_EDITED);
        }

        return new EditEntryCommand(index, editEntryDescriptor);
    }

}
