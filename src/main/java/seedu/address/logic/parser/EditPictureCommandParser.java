package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PICTURE_PATH;

import java.net.MalformedURLException;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditPictureCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.student.MiscellaneousInfo.ProfilePicturePath;

public class EditPictureCommandParser implements Parser<EditPictureCommand> {

    @Override
    public EditPictureCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(userInput, PREFIX_INDEX, PREFIX_PICTURE_PATH);

        if (!arePrefixesPresent(argMultimap,PREFIX_INDEX, PREFIX_PICTURE_PATH) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditPictureCommand.MESSAGE_USAGE));
        }
        Index index;
        ProfilePicturePath profilePicturePath;

        try {
            index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_INDEX).get());
        } catch (IllegalValueException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditPictureCommand.MESSAGE_USAGE));
        }

        try {
            profilePicturePath = ParserUtil.parsePictureUrl(argMultimap.getValue(PREFIX_PICTURE_PATH)).get();
        } catch (MalformedURLException|IllegalValueException e) {
            throw new ParseException(e.getMessage(), e);
        }

        return new EditPictureCommand(index, profilePicturePath);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}

