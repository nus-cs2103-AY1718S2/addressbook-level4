package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

import seedu.address.logic.commands.JumpCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author qwlai
/**
 * Parses input arguments and creates a new FindCommand object
 */
public class JumpCommandParser implements Parser<JumpCommand> {

    public static final String DATE_FORMAT = "dd/MM/uuuu";

    /**
     * Parses the given {@code String} of arguments in the context of the JumpCommand
     * and returns an JumpCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public JumpCommand parse(String args) throws ParseException {
        requireNonNull(args);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_FORMAT).withResolverStyle(ResolverStyle.STRICT);
        String trimmedDate = args.trim();
        try {
            LocalDate dateProvided = LocalDate.parse(trimmedDate, dtf);
            return new JumpCommand(dateProvided);
        } catch (DateTimeParseException e) {
            throw new ParseException (
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, JumpCommand.MESSAGE_USAGE));
        }
    }
}
