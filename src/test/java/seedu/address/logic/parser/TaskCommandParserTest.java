package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.logictestutil.TaskTestConstants.DATE_TIME_DESC_CS2010_QUIZ;
import static seedu.address.logic.logictestutil.TaskTestConstants.DATE_TIME_DESC_MA2108_HOMEWORK;
import static seedu.address.logic.logictestutil.TaskTestConstants.INVALID_TASK_DATE_TIME_DESC;
import static seedu.address.logic.logictestutil.TaskTestConstants.INVALID_TASK_NAME_DESC;
import static seedu.address.logic.logictestutil.TaskTestConstants.INVALID_TASK_REMARK_DESC;
import static seedu.address.logic.logictestutil.TaskTestConstants.INVALID_TASK_TAG_DESC;
import static seedu.address.logic.logictestutil.TaskTestConstants.NAME_DESC_CS2010_QUIZ;
import static seedu.address.logic.logictestutil.TaskTestConstants.NAME_DESC_MA2108_HOMEWORK;
import static seedu.address.logic.logictestutil.TaskTestConstants.REMARK_DESC_CS2010_QUIZ;
import static seedu.address.logic.logictestutil.TaskTestConstants.REMARK_DESC_MA2108_HOMEWORK;
import static seedu.address.logic.logictestutil.TaskTestConstants.TAG_DESC_CS2010;
import static seedu.address.logic.logictestutil.TaskTestConstants.TAG_DESC_URGENT;
import static seedu.address.logic.logictestutil.TaskTestConstants.VALID_DATE_TIME_CS2010_QUIZ;
import static seedu.address.logic.logictestutil.TaskTestConstants.VALID_NAME_CS2010_QUIZ;
import static seedu.address.logic.logictestutil.TaskTestConstants.VALID_REMARK_CS2010_QUIZ;
import static seedu.address.logic.logictestutil.TaskTestConstants.VALID_TAG_CS2010;
import static seedu.address.logic.logictestutil.TaskTestConstants.VALID_TAG_URGENT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.TaskCommand;
import seedu.address.model.activity.DateTime;
import seedu.address.model.activity.Name;
import seedu.address.model.activity.Remark;
import seedu.address.model.activity.Task;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.TaskBuilder;

//@@author Kyomian
public class TaskCommandParserTest {
    private TaskCommandParser parser = new TaskCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Task expectedTask = new TaskBuilder().withName(VALID_NAME_CS2010_QUIZ).withDateTime(VALID_DATE_TIME_CS2010_QUIZ)
                .withRemark(VALID_REMARK_CS2010_QUIZ).withTags(VALID_TAG_CS2010).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_CS2010_QUIZ + DATE_TIME_DESC_CS2010_QUIZ
                + REMARK_DESC_CS2010_QUIZ + TAG_DESC_CS2010, new TaskCommand(expectedTask));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_MA2108_HOMEWORK + NAME_DESC_CS2010_QUIZ
                + DATE_TIME_DESC_CS2010_QUIZ + REMARK_DESC_CS2010_QUIZ + TAG_DESC_CS2010,
                new TaskCommand(expectedTask));

        // multiple date times - last date time accepted
        assertParseSuccess(parser, NAME_DESC_CS2010_QUIZ + DATE_TIME_DESC_MA2108_HOMEWORK
                + DATE_TIME_DESC_CS2010_QUIZ + REMARK_DESC_CS2010_QUIZ
                + TAG_DESC_CS2010, new TaskCommand(expectedTask));

        // multiple remarks - last remark accepted
        assertParseSuccess(parser, NAME_DESC_CS2010_QUIZ + DATE_TIME_DESC_CS2010_QUIZ
                + REMARK_DESC_MA2108_HOMEWORK + REMARK_DESC_CS2010_QUIZ + TAG_DESC_CS2010,
                new TaskCommand(expectedTask));

        // multiple tags - all accepted
        Task expectedTaskMultipleTags = new TaskBuilder().withName(VALID_NAME_CS2010_QUIZ)
                .withDateTime(VALID_DATE_TIME_CS2010_QUIZ)
                .withRemark(VALID_REMARK_CS2010_QUIZ)
                .withTags(VALID_TAG_CS2010, VALID_TAG_URGENT).build();
        assertParseSuccess(parser, NAME_DESC_CS2010_QUIZ + DATE_TIME_DESC_CS2010_QUIZ
                + REMARK_DESC_CS2010_QUIZ + TAG_DESC_URGENT + TAG_DESC_CS2010,
                new TaskCommand(expectedTaskMultipleTags));
    }


    @Test
    public void parse_optionalRemarkMissing_success() {
        Task expectedTask = new TaskBuilder().withName(VALID_NAME_CS2010_QUIZ).withDateTime(VALID_DATE_TIME_CS2010_QUIZ)
                .withRemark().withTags(VALID_TAG_CS2010).build();
        assertParseSuccess(parser, NAME_DESC_CS2010_QUIZ + DATE_TIME_DESC_CS2010_QUIZ + TAG_DESC_CS2010,
                new TaskCommand(expectedTask));
    }

    @Test
    public void parse_optionalTagsMissing_success() {
        Task expectedTask = new TaskBuilder().withName(VALID_NAME_CS2010_QUIZ).withDateTime(VALID_DATE_TIME_CS2010_QUIZ)
                .withRemark(VALID_REMARK_CS2010_QUIZ).withTags().build();
        assertParseSuccess(parser, NAME_DESC_CS2010_QUIZ + DATE_TIME_DESC_CS2010_QUIZ
                        + REMARK_DESC_CS2010_QUIZ, new TaskCommand(expectedTask));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, TaskCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_CS2010_QUIZ + DATE_TIME_DESC_CS2010_QUIZ
                        + REMARK_DESC_CS2010_QUIZ, expectedMessage);

        // missing date time prefix
        assertParseFailure(parser, NAME_DESC_CS2010_QUIZ + VALID_DATE_TIME_CS2010_QUIZ
                        + REMARK_DESC_CS2010_QUIZ, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_TASK_NAME_DESC + DATE_TIME_DESC_CS2010_QUIZ
                + REMARK_DESC_CS2010_QUIZ + TAG_DESC_CS2010, Name.MESSAGE_NAME_CONSTRAINTS);

        // invalid date time
        assertParseFailure(parser, NAME_DESC_CS2010_QUIZ + INVALID_TASK_DATE_TIME_DESC
                + REMARK_DESC_CS2010_QUIZ + TAG_DESC_CS2010, DateTime.MESSAGE_DATETIME_CONSTRAINTS);

        // invalid remark
        assertParseFailure(parser, NAME_DESC_CS2010_QUIZ + DATE_TIME_DESC_CS2010_QUIZ
                + INVALID_TASK_REMARK_DESC + TAG_DESC_CS2010, Remark.MESSAGE_REMARK_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_CS2010_QUIZ + DATE_TIME_DESC_CS2010_QUIZ
                + REMARK_DESC_CS2010_QUIZ + INVALID_TASK_TAG_DESC, Tag.MESSAGE_TAG_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_TASK_NAME_DESC + INVALID_TASK_DATE_TIME_DESC
                        + REMARK_DESC_CS2010_QUIZ,
                Name.MESSAGE_NAME_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_CS2010_QUIZ + DATE_TIME_DESC_CS2010_QUIZ
                        + REMARK_DESC_CS2010_QUIZ + TAG_DESC_CS2010,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, TaskCommand.MESSAGE_USAGE));
    }
}
