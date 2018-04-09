package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALLERGIES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEXTOFKINNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEXTOFKINPHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARKS;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditMiscCommand;
import seedu.address.logic.commands.EditMiscCommand.EditMiscDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditMiscCommand object
 */
public class EditMiscCommandParser implements Parser<EditMiscCommand> {

    @Override
    public EditMiscCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ALLERGIES, PREFIX_NEXTOFKINNAME, PREFIX_NEXTOFKINPHONE,
                        PREFIX_REMARKS);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditMiscCommand.MESSAGE_USAGE));
        }

        EditMiscDescriptor editMiscDescriptor = new EditMiscDescriptor();
        try {
            ParserUtil.parseAllergies(argMultimap.getValue(PREFIX_ALLERGIES))
                    .ifPresent(editMiscDescriptor::setAllergies);
            ParserUtil.parseNextOfKinName(argMultimap.getValue(PREFIX_NEXTOFKINNAME))
                    .ifPresent(editMiscDescriptor::setNextOfKinName);
            ParserUtil.parseNextOfKinPhone(argMultimap.getValue(PREFIX_NEXTOFKINPHONE))
                    .ifPresent(editMiscDescriptor::setNextOfKinPhone);
            ParserUtil.parseRemarks(argMultimap.getValue(PREFIX_REMARKS)).ifPresent(editMiscDescriptor::setRemarks);

        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        if (!editMiscDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditMiscCommand.MESSAGE_NOT_EDITED);
        }

        return new EditMiscCommand(index, editMiscDescriptor);
    }
}
