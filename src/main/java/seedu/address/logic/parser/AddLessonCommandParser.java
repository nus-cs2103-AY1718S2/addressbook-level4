package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import static java.util.Objects.requireNonNull;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddLessonCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.lesson.Day;
import seedu.address.model.lesson.Time;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;

/**
 * Parses input arguments and creates a new AddCommandCommand object
 */
public class AddLessonCommandParser implements Parser<AddLessonCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddLessonCommand
     * and returns an AddLessonCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddLessonCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DAY, PREFIX_START_TIME, PREFIX_END_TIME);

        Index index;
        Day day;
        Time startTime;
        Time endTime;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddLessonCommand.MESSAGE_USAGE));
        }

        try {
            day = ParserUtil.parseDay(argMultimap.getValue(PREFIX_DAY)).get();
            startTime = ParserUtil.parseTime(argMultimap.getValue(PREFIX_START_TIME)).get();
            endTime = ParserUtil.parseTime(argMultimap.getValue(PREFIX_END_TIME)).get();

        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
        //day = new Day("mon");
        return new AddLessonCommand(index, day, startTime, endTime);
    }

}
