package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TASK_CATEGORY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TASK_DESCRIPTION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TASK_DUE_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TASK_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TASK_PRIORITY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TASK_STATUS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.TASK_CATEGORY_DESC_PERSONAL;
import static seedu.address.logic.commands.CommandTestUtil.TASK_CATEGORY_DESC_WORK;
import static seedu.address.logic.commands.CommandTestUtil.TASK_DESCRIPTION_DESC_TASKFIRST;
import static seedu.address.logic.commands.CommandTestUtil.TASK_DESCRIPTION_DESC_TASKSECOND;
import static seedu.address.logic.commands.CommandTestUtil.TASK_DUE_DATE_DESC_TASKFIRST;
import static seedu.address.logic.commands.CommandTestUtil.TASK_DUE_DATE_DESC_TASKSECOND;
import static seedu.address.logic.commands.CommandTestUtil.TASK_NAME_DESC_TASKFIRST;
import static seedu.address.logic.commands.CommandTestUtil.TASK_NAME_DESC_TASKSECOND;
import static seedu.address.logic.commands.CommandTestUtil.TASK_PRIORITY_DESC_TASKFIRST;
import static seedu.address.logic.commands.CommandTestUtil.TASK_PRIORITY_DESC_TASKSECOND;
import static seedu.address.logic.commands.CommandTestUtil.TASK_STATUS_DESC_TASKFIRST;
import static seedu.address.logic.commands.CommandTestUtil.TASK_STATUS_DESC_TASKSECOND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_CATEGORY_PERSONAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_CATEGORY_WORK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_DESCRIPTION_TASKFIRST;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_DESCRIPTION_TASKSECOND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_DUE_DATE_TASKFIRST;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_DUE_DATE_TASKSECOND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_NAME_TASKFIRST;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_NAME_TASKSECOND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_PRIORITY_TASKFIRST;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_PRIORITY_TASKSECOND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_STATUS_TASKFIRST;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_STATUS_TASKSECOND;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.TaskAddCommand;
import seedu.address.model.category.TaskCategory;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskDescription;
import seedu.address.model.task.TaskDueDate;
import seedu.address.model.task.TaskName;
import seedu.address.model.task.TaskPriority;
import seedu.address.model.task.TaskStatus;
import seedu.address.testutil.TaskBuilder;

//@@author CYX28
public class TaskAddCommandParserTest {

    private TaskAddCommandParser parser = new TaskAddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Task expectedTask = new TaskBuilder().withTaskName(VALID_TASK_NAME_TASKFIRST)
                .withTaskPriority(VALID_TASK_PRIORITY_TASKFIRST).withTaskDescription(VALID_TASK_DESCRIPTION_TASKFIRST)
                .withTaskDueDate(VALID_TASK_DUE_DATE_TASKFIRST).withTaskStatus(VALID_TASK_STATUS_TASKFIRST)
                .withTaskCategories(VALID_TASK_CATEGORY_PERSONAL).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + TASK_NAME_DESC_TASKFIRST + TASK_PRIORITY_DESC_TASKFIRST
                + TASK_DESCRIPTION_DESC_TASKFIRST + TASK_DUE_DATE_DESC_TASKFIRST + TASK_STATUS_DESC_TASKFIRST
                + TASK_CATEGORY_DESC_PERSONAL, new TaskAddCommand(expectedTask));

        // multiple taskNames - last taskName accepted
        assertParseSuccess(parser, TASK_NAME_DESC_TASKSECOND + TASK_NAME_DESC_TASKFIRST
                + TASK_PRIORITY_DESC_TASKFIRST + TASK_DESCRIPTION_DESC_TASKFIRST + TASK_DUE_DATE_DESC_TASKFIRST
                + TASK_STATUS_DESC_TASKFIRST + TASK_CATEGORY_DESC_PERSONAL, new TaskAddCommand(expectedTask));

        // multiple taskPriorities - last taskPriority accepted
        assertParseSuccess(parser, TASK_NAME_DESC_TASKFIRST + TASK_PRIORITY_DESC_TASKSECOND
                + TASK_PRIORITY_DESC_TASKFIRST + TASK_DESCRIPTION_DESC_TASKFIRST + TASK_DUE_DATE_DESC_TASKFIRST
                + TASK_STATUS_DESC_TASKFIRST + TASK_CATEGORY_DESC_PERSONAL, new TaskAddCommand(expectedTask));

        // multiple taskDescriptions - last taskDescription accepted
        assertParseSuccess(parser, TASK_NAME_DESC_TASKFIRST + TASK_PRIORITY_DESC_TASKFIRST
                + TASK_DESCRIPTION_DESC_TASKSECOND + TASK_DESCRIPTION_DESC_TASKFIRST + TASK_DUE_DATE_DESC_TASKFIRST
                + TASK_STATUS_DESC_TASKFIRST + TASK_CATEGORY_DESC_PERSONAL, new TaskAddCommand(expectedTask));

        // multiple taskDueDates - last taskDueDate accepted
        assertParseSuccess(parser, TASK_NAME_DESC_TASKFIRST + TASK_PRIORITY_DESC_TASKFIRST
                + TASK_DESCRIPTION_DESC_TASKFIRST + TASK_DUE_DATE_DESC_TASKSECOND + TASK_DUE_DATE_DESC_TASKFIRST
                + TASK_STATUS_DESC_TASKFIRST + TASK_CATEGORY_DESC_PERSONAL, new TaskAddCommand(expectedTask));

        // multiple taskStatuses - last taskStatus accepted
        assertParseSuccess(parser, TASK_NAME_DESC_TASKFIRST + TASK_PRIORITY_DESC_TASKFIRST
                + TASK_DESCRIPTION_DESC_TASKFIRST + TASK_DUE_DATE_DESC_TASKFIRST + TASK_STATUS_DESC_TASKSECOND
                + TASK_STATUS_DESC_TASKFIRST + TASK_CATEGORY_DESC_PERSONAL, new TaskAddCommand(expectedTask));

        // multiple taskCategories - all accepted
        Task expectedTaskMultipleCategories = new TaskBuilder().withTaskName(VALID_TASK_NAME_TASKFIRST)
                .withTaskPriority(VALID_TASK_PRIORITY_TASKFIRST).withTaskDescription(VALID_TASK_DESCRIPTION_TASKFIRST)
                .withTaskDueDate(VALID_TASK_DUE_DATE_TASKFIRST).withTaskStatus(VALID_TASK_STATUS_TASKFIRST)
                .withTaskCategories(VALID_TASK_CATEGORY_PERSONAL, VALID_TASK_CATEGORY_WORK).build();
        assertParseSuccess(parser, TASK_NAME_DESC_TASKFIRST + TASK_PRIORITY_DESC_TASKFIRST
                + TASK_DESCRIPTION_DESC_TASKFIRST + TASK_DUE_DATE_DESC_TASKFIRST + TASK_STATUS_DESC_TASKFIRST
                + TASK_CATEGORY_DESC_PERSONAL + TASK_CATEGORY_DESC_WORK,
                new TaskAddCommand(expectedTaskMultipleCategories));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero taskCategories
        Task expectedTask = new TaskBuilder().withTaskName(VALID_TASK_NAME_TASKSECOND)
                .withTaskPriority(VALID_TASK_PRIORITY_TASKSECOND).withTaskDescription(VALID_TASK_DESCRIPTION_TASKSECOND)
                .withTaskDueDate(VALID_TASK_DUE_DATE_TASKSECOND).withTaskStatus(VALID_TASK_STATUS_TASKSECOND)
                .withTaskCategories().build();
        assertParseSuccess(parser, TASK_NAME_DESC_TASKSECOND + TASK_PRIORITY_DESC_TASKSECOND
                + TASK_DESCRIPTION_DESC_TASKSECOND + TASK_DUE_DATE_DESC_TASKSECOND + TASK_STATUS_DESC_TASKSECOND,
                new TaskAddCommand(expectedTask));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, TaskAddCommand.MESSAGE_USAGE);

        // missing taskName prefix
        assertParseFailure(parser, VALID_TASK_NAME_TASKFIRST + TASK_PRIORITY_DESC_TASKFIRST
                + TASK_DESCRIPTION_DESC_TASKSECOND + TASK_DUE_DATE_DESC_TASKFIRST + TASK_STATUS_DESC_TASKSECOND,
                expectedMessage);

        // missing taskPriority prefix
        assertParseFailure(parser, TASK_NAME_DESC_TASKFIRST + VALID_TASK_PRIORITY_TASKFIRST
                + TASK_DESCRIPTION_DESC_TASKSECOND + TASK_DUE_DATE_DESC_TASKFIRST + TASK_STATUS_DESC_TASKSECOND,
                expectedMessage);

        // missing taskDescription prefix
        assertParseFailure(parser, TASK_NAME_DESC_TASKFIRST + TASK_PRIORITY_DESC_TASKFIRST
                + VALID_TASK_DESCRIPTION_TASKFIRST + TASK_DUE_DATE_DESC_TASKFIRST + TASK_STATUS_DESC_TASKSECOND,
                expectedMessage);

        // missing taskDueDate prefix
        assertParseFailure(parser, TASK_NAME_DESC_TASKFIRST + TASK_PRIORITY_DESC_TASKFIRST
                + TASK_DESCRIPTION_DESC_TASKSECOND + VALID_TASK_DUE_DATE_TASKFIRST + TASK_STATUS_DESC_TASKSECOND,
                expectedMessage);

        // missing taskStatus prefix
        assertParseFailure(parser, TASK_NAME_DESC_TASKFIRST + TASK_PRIORITY_DESC_TASKFIRST
                + TASK_DESCRIPTION_DESC_TASKSECOND + TASK_DUE_DATE_DESC_TASKFIRST + VALID_TASK_STATUS_TASKFIRST,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_TASK_NAME_TASKFIRST + VALID_TASK_PRIORITY_TASKFIRST
                + VALID_TASK_DESCRIPTION_TASKFIRST + VALID_TASK_DUE_DATE_TASKFIRST + VALID_TASK_STATUS_TASKFIRST,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid taskName
        assertParseFailure(parser, INVALID_TASK_NAME_DESC + TASK_PRIORITY_DESC_TASKFIRST
                + TASK_DESCRIPTION_DESC_TASKSECOND + TASK_DUE_DATE_DESC_TASKFIRST + TASK_STATUS_DESC_TASKFIRST
                + TASK_CATEGORY_DESC_PERSONAL + TASK_CATEGORY_DESC_WORK, TaskName.MESSAGE_TASK_NAME_CONSTRAINTS);

        // invalid taskPriority
        assertParseFailure(parser, TASK_NAME_DESC_TASKFIRST + INVALID_TASK_PRIORITY_DESC
                + TASK_DESCRIPTION_DESC_TASKSECOND + TASK_DUE_DATE_DESC_TASKFIRST + TASK_STATUS_DESC_TASKFIRST
                + TASK_CATEGORY_DESC_PERSONAL + TASK_CATEGORY_DESC_WORK,
                TaskPriority.MESSAGE_TASK_PRIORITY_CONSTRAINTS);

        // invalid taskDescription
        assertParseFailure(parser, TASK_NAME_DESC_TASKFIRST + TASK_PRIORITY_DESC_TASKFIRST
                + INVALID_TASK_DESCRIPTION_DESC + TASK_DUE_DATE_DESC_TASKFIRST + TASK_STATUS_DESC_TASKFIRST
                + TASK_CATEGORY_DESC_PERSONAL + TASK_CATEGORY_DESC_WORK,
                TaskDescription.MESSAGE_TASK_DESCRIPTION_CONSTRAINTS);

        // invalid taskDueDate
        assertParseFailure(parser, TASK_NAME_DESC_TASKFIRST + TASK_PRIORITY_DESC_TASKFIRST
                + TASK_DESCRIPTION_DESC_TASKSECOND + INVALID_TASK_DUE_DATE_DESC + TASK_STATUS_DESC_TASKFIRST
                + TASK_CATEGORY_DESC_PERSONAL + TASK_CATEGORY_DESC_WORK, TaskDueDate.MESSAGE_TASK_DUE_DATE_CONSTRAINTS);

        // invalid taskStatus
        assertParseFailure(parser, TASK_NAME_DESC_TASKFIRST + TASK_PRIORITY_DESC_TASKFIRST
                + TASK_DESCRIPTION_DESC_TASKSECOND + TASK_DUE_DATE_DESC_TASKFIRST + INVALID_TASK_STATUS_DESC
                + TASK_CATEGORY_DESC_PERSONAL + TASK_CATEGORY_DESC_WORK, TaskStatus.MESSAGE_TASK_STATUS_CONSTRAINTS);

        // invalid taskCategory
        assertParseFailure(parser, TASK_NAME_DESC_TASKFIRST + TASK_PRIORITY_DESC_TASKFIRST
                + TASK_DESCRIPTION_DESC_TASKSECOND + TASK_DUE_DATE_DESC_TASKFIRST + TASK_STATUS_DESC_TASKFIRST
                + INVALID_TASK_CATEGORY_DESC + VALID_TASK_CATEGORY_PERSONAL,
                TaskCategory.MESSAGE_TASK_CATEGORY_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_TASK_NAME_DESC + TASK_PRIORITY_DESC_TASKFIRST
                + TASK_DESCRIPTION_DESC_TASKFIRST + INVALID_TASK_DUE_DATE_DESC + TASK_STATUS_DESC_TASKFIRST,
                TaskName.MESSAGE_TASK_NAME_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + TASK_NAME_DESC_TASKFIRST + TASK_PRIORITY_DESC_TASKFIRST
                + TASK_DESCRIPTION_DESC_TASKFIRST + TASK_DUE_DATE_DESC_TASKFIRST + TASK_STATUS_DESC_TASKFIRST
                + TASK_CATEGORY_DESC_PERSONAL + TASK_CATEGORY_DESC_WORK,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, TaskAddCommand.MESSAGE_USAGE));
    }

}
