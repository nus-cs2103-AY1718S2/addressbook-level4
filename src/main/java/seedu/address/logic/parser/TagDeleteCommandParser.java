package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.TagDeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

//@@author TeyXinHui
/**
 * Parses input arguments and creates a new FindCommand object
 */
public class TagDeleteCommandParser implements Parser<TagDeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the TagDeleteCommand
     * and returns an TagDeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public TagDeleteCommand parse(String args) throws ParseException {
        try {
            Tag tag = ParserUtil.parseTag(args.trim());
            return new TagDeleteCommand(tag);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagDeleteCommand.MESSAGE_USAGE));
        }
    }

}
//@@author
