package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MESSAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PURPOSE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddTemplateCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.email.Template;

//@@author ng95junwei
/**
 * Parses input arguments and creates a new AddAppointmentCommand object
 */
public class AddTemplateCommandParser implements Parser<AddTemplateCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddAppointmentCommand
     * and returns an AddAppointmentCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddTemplateCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                args, PREFIX_PURPOSE, PREFIX_SUBJECT, PREFIX_MESSAGE);

        if (!arePrefixesPresent(
                argMultimap, PREFIX_PURPOSE, PREFIX_SUBJECT, PREFIX_MESSAGE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTemplateCommand.MESSAGE_USAGE));
        }

        try {
            String purpose = (argMultimap.getValue(PREFIX_PURPOSE)).get().trim();
            String subject = (argMultimap.getValue(PREFIX_SUBJECT)).get().trim();
            String message = (argMultimap.getValue(PREFIX_MESSAGE)).get().trim();

            Template template = new Template(purpose, subject, message);

            return new AddTemplateCommand(template);
        } catch (java.lang.IllegalArgumentException ive) {
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
