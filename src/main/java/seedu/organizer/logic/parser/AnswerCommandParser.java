package seedu.organizer.logic.parser;

import static seedu.organizer.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.organizer.logic.parser.CliSyntax.PREFIX_ANSWER;
import static seedu.organizer.logic.parser.CliSyntax.PREFIX_USERNAME;

import java.util.stream.Stream;

import seedu.organizer.commons.exceptions.IllegalValueException;
import seedu.organizer.logic.commands.AnswerCommand;
import seedu.organizer.logic.parser.exceptions.ParseException;

//@@author dominickenn
/**
 * Parses input arguments and creates a new AnswerCommand object
 */
public class AnswerCommandParser implements Parser<AnswerCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AnswerCommand
     * and returns a AnswerCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AnswerCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_USERNAME, PREFIX_ANSWER);

        if (!arePrefixesPresent(argMultimap, PREFIX_USERNAME, PREFIX_ANSWER)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AnswerCommand.MESSAGE_USAGE));
        }

        try {
            String username = ParserUtil.parseUsername(argMultimap.getValue(PREFIX_USERNAME)).get();
            String answer = ParserUtil.parseAnswer(argMultimap.getValue(PREFIX_ANSWER)).get();
            return new AnswerCommand(username, answer);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
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
