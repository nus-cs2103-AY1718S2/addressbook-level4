package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CCA;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CCA_POSITION;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DeleteCcaCommand;
import seedu.address.logic.commands.EditPersonDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
//@@author chuakunhong

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class DeleteCcaCommandParser implements Parser<DeleteCcaCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCcaCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_CCA, PREFIX_CCA_POSITION);

        if (!argMultimap.arePrefixesPresent(PREFIX_CCA, PREFIX_CCA_POSITION)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCcaCommand.MESSAGE_USAGE));
        }
        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCcaCommand.MESSAGE_USAGE));
        }

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        if (ParserUtil.parseCca(argMultimap.getValue(PREFIX_CCA), argMultimap.getValue(PREFIX_CCA_POSITION))
                .get().toString().isEmpty()) {
            throw new ParseException((String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                                                    DeleteCcaCommand.MESSAGE_USAGE)));
        } else {
            ParserUtil.parseCca(argMultimap.getValue(PREFIX_CCA), argMultimap.getValue(PREFIX_CCA_POSITION))
                    .ifPresent(editPersonDescriptor::setCca);
        }
        return new DeleteCcaCommand(index, editPersonDescriptor);
    }
    //@@author
}
