package seedu.recipe.logic.parser;

import static seedu.recipe.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.recipe.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.recipe.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.recipe.logic.commands.TagCommand;
import seedu.recipe.model.tag.TagContainsKeywordsPredicate;

public class TagCommandParserTest {

    private TagCommandParser parser = new TagCommandParser();

    @Test
    public void parseEmptyArgThrowsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseValidArgsReturnsTagCommand() {
        // no leading and trailing whitespaces
        TagCommand expectedTagCommand =
                new TagCommand(new TagContainsKeywordsPredicate(Arrays.asList("friends", "owesMoney")),
                                    new String[] {"friends", "owesMoney"});
        assertParseSuccess(parser, "friends owesMoney", expectedTagCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n friends \n \t owesMoney  \t", expectedTagCommand);
    }
}
