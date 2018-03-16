package seedu.organizer.logic.parser;

import static seedu.organizer.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.organizer.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.organizer.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.organizer.logic.commands.FindMultipleFieldsCommand;
import seedu.organizer.model.task.MultipleFieldsContainsKeywordsPredicate;

//@@author guekling
public class FindMultipleFieldsCommandParserTest {

    private FindMultipleFieldsCommandParser parser = new FindMultipleFieldsCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindMultipleFieldsCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindMultipleFieldsCommand() {
        // no leading and trailing whitespaces
        FindMultipleFieldsCommand expectedFindMultipleFieldsCommand =
            new FindMultipleFieldsCommand(new MultipleFieldsContainsKeywordsPredicate(Arrays.asList("CS2102",
            "script")));
        assertParseSuccess(parser, "CS2102 script", expectedFindMultipleFieldsCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n CS2102 \n \t script  \t", expectedFindMultipleFieldsCommand);
    }
}
