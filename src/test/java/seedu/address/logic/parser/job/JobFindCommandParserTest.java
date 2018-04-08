// @@author kush1509
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.job.JobFindCommand;
import seedu.address.logic.parser.job.JobFindCommandParser;
import seedu.address.model.job.PositionContainsKeywordsPredicate;
import seedu.address.model.skill.JobSkillContainsKeywordsPredicate;

public class JobFindCommandParserTest {

    private JobFindCommandParser parser = new JobFindCommandParser();
    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, JobFindCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, JobFindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsJobFindCommand() {
        // no leading and trailing whitespaces
        JobFindCommand expectedFindPositionCommand =
                new JobFindCommand(new PositionContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, " p/Alice Bob", expectedFindPositionCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n p/Alice \n \t Bob  \t", expectedFindPositionCommand);

        // no leading and trailing whitespaces
        JobFindCommand expectedFindSkillCommand =
                new JobFindCommand(new JobSkillContainsKeywordsPredicate(Arrays.asList("developer", "accountant")));
        assertParseSuccess(parser, " s/developer accountant", expectedFindSkillCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n s/developer \n \t accountant  \t", expectedFindSkillCommand);
    }

    @Test
    public void parse_invalidArg_throwsParseException() {
        assertParseFailure(parser, " p/", String.format(MESSAGE_INVALID_COMMAND_FORMAT, JobFindCommand.MESSAGE_USAGE));

        assertParseFailure(parser, " s/", String.format(MESSAGE_INVALID_COMMAND_FORMAT, JobFindCommand.MESSAGE_USAGE));

        assertParseFailure(parser, " p/ s/", String.format(MESSAGE_INVALID_COMMAND_FORMAT, JobFindCommand.MESSAGE_USAGE));

        assertParseFailure(parser, " s/ p/", String.format(MESSAGE_INVALID_COMMAND_FORMAT, JobFindCommand.MESSAGE_USAGE));
    }

}
