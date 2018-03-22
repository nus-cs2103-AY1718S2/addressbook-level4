package seedu.address.logic.parser;

import seedu.address.logic.commands.LoginCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;


public class LoginCommandParser implements Parser<LoginCommand>{

    public LoginCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        String[] nameKeywords = trimmedArgs.split("\\s+");
        if (nameKeywords.length != 2) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoginCommand.MESSAGE_USAGE));
        }

        String username = nameKeywords[0];
        String password = nameKeywords[1];

        return new LoginCommand(username, password);
    }
}
