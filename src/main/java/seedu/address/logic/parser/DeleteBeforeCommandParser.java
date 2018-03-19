package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.text.SimpleDateFormat;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DeleteBeforeCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.DateAdded;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new DeleteBeforeCommand object
 */
public class DeleteBeforeCommandParser implements Parser<DeleteBeforeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteBeforeCommand
     * and returns an DeleteBeforeCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteBeforeCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DATE, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_DATE, PREFIX_TAG)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteBeforeCommand.MESSAGE_USAGE));
        }

        try {
            DateAdded inputDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE)).get();
            Set<Tag> inputTags = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
            dateFormatter.parse(inputDate.toString()); //check if can parse inputDate, requires review
            return new DeleteBeforeCommand(inputDate, inputTags);

        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        } catch (java.text.ParseException e) { //cannot parse dateAdded into a Date object
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteBeforeCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
