package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.AddLessonCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.lesson.Time;
import seedu.address.model.student.Name;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class AddLessonCommandParser implements Parser<AddLessonCommand> {
    static final int NAME_INDEX = 0;
    static final int START_TIME_INDEX = 1;
    static final int END_TIME_INDEX = 2;

    /**
     * Parses the given {@code String} of arguments in the context of the AddLessonCommand
     * and returns an AddLessonCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddLessonCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddLessonCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new AddLessonCommand(new Name(nameKeywords[NAME_INDEX]),
                new Time(nameKeywords[START_TIME_INDEX]), new Time(nameKeywords[END_TIME_INDEX]));
    }

}
