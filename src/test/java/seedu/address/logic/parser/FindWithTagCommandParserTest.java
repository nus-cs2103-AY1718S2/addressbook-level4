package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.FindWithTagCommand;
import seedu.address.model.person.TagContainsKeywordsPredicate;

//@@author KevinChuangCH
public class FindWithTagCommandParserTest {

    private FindWithTagCommandParser parser = new FindWithTagCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindWithTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindWithTagCommand() {
        // no leading and trailing whitespaces
        FindWithTagCommand expectedFindWithTagCommand =
                new FindWithTagCommand(new TagContainsKeywordsPredicate(Arrays.asList("neighbour", "owesMoney")));
        assertParseSuccess(parser, "neighbour owesMoney", expectedFindWithTagCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n neighbour \n \t owesMoney  \t", expectedFindWithTagCommand);
    }

}
