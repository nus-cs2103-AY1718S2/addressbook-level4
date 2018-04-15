//@@author philos22
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.ContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        List<String> nameList = new ArrayList<>();
        nameList.add("Alice");
        nameList.add("Bob");
        List<String> tagList = new ArrayList<>();
        tagList.add("Tag1");
        tagList.add("Tag2");
        FindCommand expectedFindCommand =
                new FindCommand(new ContainsKeywordsPredicate(nameList, tagList));

        assertParseSuccess(parser, "find n/Alice Bob t/Tag1 Tag2", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "find \n n/Alice \n \t Bob  \t t/Tag1 \n Tag2", expectedFindCommand);
    }

}
