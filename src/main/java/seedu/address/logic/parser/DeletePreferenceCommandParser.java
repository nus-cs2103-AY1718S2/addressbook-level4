package seedu.address.logic.parser;
//@@author SuxianAlicia
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DeletePreferenceCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Preference;

/**
 * Parses given arguments and creates a new DeletePreferenceCommand object.
 */
public class DeletePreferenceCommandParser implements Parser<DeletePreferenceCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeletePreferenceCommand
     * and returns an DeletePreferenceCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeletePreferenceCommand parse(String userInput) throws ParseException {
        try {
            Preference preference = ParserUtil.parsePreference(userInput);
            return new DeletePreferenceCommand(preference);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeletePreferenceCommand.MESSAGE_USAGE));
        }
    }
}
