package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PICTURE_URL;

import java.net.MalformedURLException;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditPictureCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.student.MiscellaneousInfo.ProfilePictureUrl;

public class EditPictureCommandParser implements Parser<EditPictureCommand> {

    @Override
    public EditPictureCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(userInput, PREFIX_PICTURE_URL);

        Index index;
        ProfilePictureUrl profilePictureUrl;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditPictureCommand.MESSAGE_USAGE));
        }

        try {
            profilePictureUrl = ParserUtil.parsePictureUrl(argMultimap.getValue(PREFIX_PICTURE_URL)).get();
        } catch (MalformedURLException e) {
            throw new ParseException(e.getMessage(), e);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        return new EditPictureCommand(index, profilePictureUrl);
    }
}
