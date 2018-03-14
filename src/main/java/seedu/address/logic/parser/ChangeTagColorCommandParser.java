package seedu.address.logic.parser;

import seedu.address.logic.commands.ChangeTagColorCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class ChangeTagColorCommandParser implements Parser<ChangeTagColorCommand>{

    @Override
    public ChangeTagColorCommand parse(String userInput) throws ParseException {
        String[] args = userInput.split(" ");
        return new ChangeTagColorCommand(args[1].toLowerCase().trim(), args[2].toLowerCase().trim());
    }
}
