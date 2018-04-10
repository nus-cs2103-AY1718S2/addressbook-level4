package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.stream.Stream;

import seedu.address.logic.commands.DeleteTemplateCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//author ng95junwei
/**
 * Parses input arguments and creates a new DeleteTemplateCommand object
 */
public class DeleteTemplateCommandParser implements Parser<DeleteTemplateCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteAppointmentCommand
     * and returns a DeleteAppointmentCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteTemplateCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        String[] nameKeywords = trimmedArgs.split("\\s+");
        String purpose = nameKeywords[0].trim();
        if (nameKeywords.length != 1 || purpose.equals("")) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTemplateCommand.MESSAGE_USAGE));
        }

        return new DeleteTemplateCommand(purpose);

    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
