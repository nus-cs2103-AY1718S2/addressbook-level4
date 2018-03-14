package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ChangeTagColorCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

public class ChangeTagColorCommandParser implements Parser<ChangeTagColorCommand>{

    @Override
    public ChangeTagColorCommand parse(String userInput) throws ParseException {
        String[] args = userInput.split(" ");
        if (args.length != 3) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeTagColorCommand.MESSAGE_USAGE));
        }
        try {

            Tag tag = ParserUtil.parseTag(args[1]);
            String color = ParserUtil.parseColor((args[2]));
            return new ChangeTagColorCommand(tag.name, color);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }
}
