package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.TagOrderCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new TagOrderCommand object
 */
public class TagOrderCommandParser implements Parser<TagOrderCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the TagOrderCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public TagOrderCommand parse(String args) throws ParseException {
        requireNonNull(args);

        String[] splittedArgs = splitStrings(args.trim());

        Index index;
        String tagWord;

        if (splittedArgs.length > 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    TagOrderCommand.MESSAGE_ONE_TAG_ONLY));
        }

        try {
            index = ParserUtil.parseIndex(splittedArgs[0]);
            tagWord = splittedArgs[1];
            Tag tag = new Tag(tagWord);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagOrderCommand.MESSAGE_USAGE));
        } catch (ArrayIndexOutOfBoundsException aioobe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagOrderCommand.MESSAGE_USAGE));
        } catch (IllegalArgumentException iae) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagOrderCommand.MESSAGE_USAGE));
        }

        return new TagOrderCommand(index, tagWord);
    }

    private String[] splitStrings(String args) {
        return args.split("\\s+");
    }
}

