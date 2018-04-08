package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddRemarkCommand;
import seedu.address.logic.commands.CcaCommand.EditPersonDescriptor;
import seedu.address.logic.commands.CcaCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Cca;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CCA;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;

//@@author chuakunhong

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class CcaCommandParser implements Parser<CcaCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CcaCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_CCA);

        if (!argMultimap.arePrefixesPresent(PREFIX_CCA)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CcaCommand.MESSAGE_USAGE));
        }
        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CcaCommand.MESSAGE_USAGE));
        }

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        if (ParserUtil.parseCca(argMultimap.getValue(PREFIX_CCA)).get().toString().isEmpty()) {
            throw new ParseException((String.format(MESSAGE_INVALID_COMMAND_FORMAT, CcaCommand.MESSAGE_USAGE)));
        } else {
            ParserUtil.parseCca(argMultimap.getValue(PREFIX_CCA)).ifPresent(editPersonDescriptor::setCca);
        }
        return new CcaCommand(index, editPersonDescriptor);
    }
    //@@author
}
