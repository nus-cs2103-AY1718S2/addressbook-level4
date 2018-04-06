package seedu.address.logic.parser;

//@@author lohtianwei
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RATING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.TogglePrivacyCommand;
import seedu.address.logic.commands.TogglePrivacyCommand.EditPersonPrivacy;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new TogglePrivacyCommand object
 */
public class TogglePrivacyCommandParser implements Parser<TogglePrivacyCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the TogglePrivacyCommand
     * and returns an TogglePrivacyCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public TogglePrivacyCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args,
                        PREFIX_REMARK, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_RATING);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TogglePrivacyCommand.MESSAGE_USAGE));
        }

        EditPersonPrivacy epp = new EditPersonPrivacy();
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            epp.setPrivatePhone(false);
        }

        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            epp.setPrivateAddress(false);
        }

        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            epp.setPrivateEmail(false);
        }

        if (argMultimap.getValue(PREFIX_REMARK).isPresent()) {
            epp.setPrivateRemark(false);
        }

        if (argMultimap.getValue(PREFIX_RATING).isPresent()) {
            epp.setPrivateRating(false);
        }
        return new TogglePrivacyCommand(index, epp);
    }
}
