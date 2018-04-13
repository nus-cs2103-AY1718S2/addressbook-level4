package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.model.person.Phone.isValidPhone;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditPersonDescriptor;
import seedu.address.logic.commands.NextOfKinCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditCommand object
 */
//@@author chuakunhong
public class NextOfKinCommandParser implements Parser<NextOfKinCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public NextOfKinCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_REMARK);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, NextOfKinCommand.MESSAGE_USAGE));
        }

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        if (!(argMultimap.arePrefixesPresent(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_REMARK))) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, NextOfKinCommand.MESSAGE_USAGE));
        }
        try {
            ParserUtil.parseNextOfKin(argMultimap.getValue(PREFIX_NAME),
                    argMultimap.getValue(PREFIX_PHONE),
                    argMultimap.getValue(PREFIX_EMAIL),
                    argMultimap.getValue(PREFIX_REMARK)).ifPresent(editPersonDescriptor::setNextOfKin);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        if (argMultimap.getValue(PREFIX_NAME).get().isEmpty() && argMultimap.getValue(PREFIX_PHONE).get().isEmpty()
                && argMultimap.getValue(PREFIX_NAME).get().isEmpty()) {
            throw new ParseException(NextOfKinCommand.MESSAGE_NOT_EDITED);
        }

        if (isValidPhone(argMultimap.getValue(PREFIX_PHONE).get())){

        }

        return new NextOfKinCommand(index, editPersonDescriptor);
    }
//@@author
}
