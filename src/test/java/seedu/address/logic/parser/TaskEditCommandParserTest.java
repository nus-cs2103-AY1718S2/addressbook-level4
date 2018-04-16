package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TASK_CATEGORY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TASK_DESCRIPTION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TASK_DUE_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TASK_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TASK_PRIORITY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TASK_STATUS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.TASK_CATEGORY_DESC_PERSONAL;
import static seedu.address.logic.commands.CommandTestUtil.TASK_CATEGORY_DESC_WORK;
import static seedu.address.logic.commands.CommandTestUtil.TASK_DESCRIPTION_DESC_TASKFIRST;
import static seedu.address.logic.commands.CommandTestUtil.TASK_DESCRIPTION_DESC_TASKSECOND;
import static seedu.address.logic.commands.CommandTestUtil.TASK_DUE_DATE_DESC_TASKFIRST;
import static seedu.address.logic.commands.CommandTestUtil.TASK_DUE_DATE_DESC_TASKSECOND;
import static seedu.address.logic.commands.CommandTestUtil.TASK_NAME_DESC_TASKFIRST;
import static seedu.address.logic.commands.CommandTestUtil.TASK_PRIORITY_DESC_TASKFIRST;
import static seedu.address.logic.commands.CommandTestUtil.TASK_PRIORITY_DESC_TASKSECOND;
import static seedu.address.logic.commands.CommandTestUtil.TASK_STATUS_DESC_TASKFIRST;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_CATEGORY_PERSONAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_CATEGORY_WORK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_DESCRIPTION_TASKFIRST;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_DESCRIPTION_TASKSECOND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_DUE_DATE_TASKFIRST;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_DUE_DATE_TASKSECOND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_NAME_TASKFIRST;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_PRIORITY_TASKFIRST;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_PRIORITY_TASKSECOND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_STATUS_TASKFIRST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_CATEGORY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TASK;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.TaskEditCommand;
import seedu.address.logic.commands.TaskEditCommand.EditTaskDescriptor;
import seedu.address.model.category.TaskCategory;
import seedu.address.model.task.TaskDescription;
import seedu.address.model.task.TaskDueDate;
import seedu.address.model.task.TaskName;
import seedu.address.model.task.TaskPriority;
import seedu.address.model.task.TaskStatus;
import seedu.address.testutil.EditTaskDescriptorBuilder;

//@@author CYX28
public class TaskEditCommandParserTest {

    private static final String TASK_CATEGORY_EMPTY = " " + PREFIX_TASK_CATEGORY;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, TaskEditCommand.MESSAGE_USAGE);

    private TaskEditCommandParser parser = new TaskEditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_TASK_NAME_TASKFIRST, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "5", TaskEditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + TASK_NAME_DESC_TASKFIRST, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + TASK_NAME_DESC_TASKFIRST, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "5 random task", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "5 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1"
                + INVALID_TASK_NAME_DESC, TaskName.MESSAGE_TASK_NAME_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1"
                + INVALID_TASK_PRIORITY_DESC, TaskPriority.MESSAGE_TASK_PRIORITY_CONSTRAINTS); // invalid priority
        assertParseFailure(parser, "1"
                + INVALID_TASK_DESCRIPTION_DESC,
                TaskDescription.MESSAGE_TASK_DESCRIPTION_CONSTRAINTS); // invalid description
        assertParseFailure(parser, "1"
                + INVALID_TASK_DUE_DATE_DESC, TaskDueDate.MESSAGE_TASK_DUE_DATE_CONSTRAINTS); // invalid due date
        assertParseFailure(parser, "1"
                + INVALID_TASK_STATUS_DESC, TaskStatus.MESSAGE_TASK_STATUS_CONSTRAINTS); // invalid status
        assertParseFailure(parser, "1"
                + INVALID_TASK_CATEGORY_DESC, TaskCategory.MESSAGE_TASK_CATEGORY_CONSTRAINTS); // invalid category

        // invalid task priority followed by valid task description
        assertParseFailure(parser, "1" + INVALID_TASK_PRIORITY_DESC + TASK_DESCRIPTION_DESC_TASKFIRST,
                TaskPriority.MESSAGE_TASK_PRIORITY_CONSTRAINTS);

        // valid task priority followed by invalid task priority.
        // The test case for invalid task priority followed by valid task priority is tested at
        // {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + TASK_PRIORITY_DESC_TASKSECOND + INVALID_TASK_PRIORITY_DESC,
                TaskPriority.MESSAGE_TASK_PRIORITY_CONSTRAINTS);

        // while parsing {@code PREFIX_TASK_CATEGORY} alone will reset the task categories of the
        // {@code Task} being edited, parsing it together with a valid task category results in error
        assertParseFailure(parser, "1" + TASK_CATEGORY_DESC_PERSONAL + TASK_CATEGORY_DESC_WORK
                + TASK_CATEGORY_EMPTY, TaskCategory.MESSAGE_TASK_CATEGORY_CONSTRAINTS);
        assertParseFailure(parser, "1" + TASK_CATEGORY_DESC_PERSONAL + TASK_CATEGORY_EMPTY
                + TASK_CATEGORY_DESC_WORK, TaskCategory.MESSAGE_TASK_CATEGORY_CONSTRAINTS);
        assertParseFailure(parser, "1" + TASK_CATEGORY_EMPTY + TASK_CATEGORY_DESC_PERSONAL
                + TASK_CATEGORY_DESC_WORK, TaskCategory.MESSAGE_TASK_CATEGORY_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_TASK_NAME_DESC + INVALID_TASK_PRIORITY_DESC
                + INVALID_TASK_DESCRIPTION_DESC + VALID_TASK_DUE_DATE_TASKFIRST + VALID_TASK_STATUS_TASKFIRST,
                TaskName.MESSAGE_TASK_NAME_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_TASK;
        String userInput = targetIndex.getOneBased() + TASK_PRIORITY_DESC_TASKSECOND + TASK_CATEGORY_DESC_PERSONAL
                + TASK_DESCRIPTION_DESC_TASKFIRST + TASK_DUE_DATE_DESC_TASKFIRST + TASK_NAME_DESC_TASKFIRST
                + TASK_STATUS_DESC_TASKFIRST + TASK_CATEGORY_DESC_WORK;

        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withTaskName(VALID_TASK_NAME_TASKFIRST)
                .withTaskPriority(VALID_TASK_PRIORITY_TASKSECOND).withTaskDescription(VALID_TASK_DESCRIPTION_TASKFIRST)
                .withTaskDueDate(VALID_TASK_DUE_DATE_TASKFIRST).withTaskStatus(VALID_TASK_STATUS_TASKFIRST)
                .withTaskCategories(VALID_TASK_CATEGORY_PERSONAL, VALID_TASK_CATEGORY_WORK).build();
        TaskEditCommand expectedCommand = new TaskEditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_TASK;
        String userInput = targetIndex.getOneBased() + TASK_PRIORITY_DESC_TASKSECOND + TASK_STATUS_DESC_TASKFIRST;

        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withTaskPriority(VALID_TASK_PRIORITY_TASKSECOND)
                .withTaskStatus(VALID_TASK_STATUS_TASKFIRST).build();
        TaskEditCommand expectedCommand = new TaskEditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // task name
        Index targetIndex = INDEX_SECOND_TASK;
        String userInput = targetIndex.getOneBased() + TASK_NAME_DESC_TASKFIRST;
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withTaskName(VALID_TASK_NAME_TASKFIRST).build();
        TaskEditCommand expectedCommand = new TaskEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // task priority
        userInput = targetIndex.getOneBased() + TASK_PRIORITY_DESC_TASKFIRST;
        descriptor = new EditTaskDescriptorBuilder().withTaskPriority(VALID_TASK_PRIORITY_TASKFIRST).build();
        expectedCommand = new TaskEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // task description
        userInput = targetIndex.getOneBased() + TASK_DESCRIPTION_DESC_TASKFIRST;
        descriptor = new EditTaskDescriptorBuilder().withTaskDescription(VALID_TASK_DESCRIPTION_TASKFIRST).build();
        expectedCommand = new TaskEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // task due date
        userInput = targetIndex.getOneBased() + TASK_DUE_DATE_DESC_TASKFIRST;
        descriptor = new EditTaskDescriptorBuilder().withTaskDueDate(VALID_TASK_DUE_DATE_TASKFIRST).build();
        expectedCommand = new TaskEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // task status
        userInput = targetIndex.getOneBased() + TASK_STATUS_DESC_TASKFIRST;
        descriptor = new EditTaskDescriptorBuilder().withTaskStatus(VALID_TASK_STATUS_TASKFIRST).build();
        expectedCommand = new TaskEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // task categories
        userInput = targetIndex.getOneBased() + TASK_CATEGORY_DESC_WORK;
        descriptor = new EditTaskDescriptorBuilder().withTaskCategories(VALID_TASK_CATEGORY_WORK).build();
        expectedCommand = new TaskEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_TASK;
        String userInput = targetIndex.getOneBased() + TASK_PRIORITY_DESC_TASKFIRST + TASK_DESCRIPTION_DESC_TASKFIRST
                + TASK_DUE_DATE_DESC_TASKFIRST + TASK_STATUS_DESC_TASKFIRST + TASK_CATEGORY_DESC_WORK
                + TASK_PRIORITY_DESC_TASKFIRST + TASK_DESCRIPTION_DESC_TASKFIRST + TASK_DUE_DATE_DESC_TASKFIRST
                + TASK_STATUS_DESC_TASKFIRST + TASK_CATEGORY_DESC_WORK + TASK_CATEGORY_DESC_PERSONAL;

        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withTaskPriority(VALID_TASK_PRIORITY_TASKFIRST)
                .withTaskDescription(VALID_TASK_DESCRIPTION_TASKFIRST).withTaskDueDate(VALID_TASK_DUE_DATE_TASKFIRST)
                .withTaskStatus(VALID_TASK_STATUS_TASKFIRST).withTaskCategories(VALID_TASK_CATEGORY_PERSONAL,
                        VALID_TASK_CATEGORY_WORK).build();
        TaskEditCommand expectedCommand = new TaskEditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_TASK;
        String userInput = targetIndex.getOneBased() + INVALID_TASK_PRIORITY_DESC + TASK_PRIORITY_DESC_TASKSECOND;
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder()
                .withTaskPriority(VALID_TASK_PRIORITY_TASKSECOND).build();
        TaskEditCommand expectedCommand = new TaskEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + TASK_PRIORITY_DESC_TASKSECOND + INVALID_TASK_DESCRIPTION_DESC
                + TASK_DUE_DATE_DESC_TASKSECOND + TASK_DESCRIPTION_DESC_TASKSECOND;
        descriptor = new EditTaskDescriptorBuilder().withTaskPriority(VALID_TASK_PRIORITY_TASKSECOND)
                .withTaskDescription(VALID_TASK_DESCRIPTION_TASKSECOND)
                .withTaskDueDate(VALID_TASK_DUE_DATE_TASKSECOND).build();
        expectedCommand = new TaskEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTaskCategories_success() {
        Index targetIndex = INDEX_SECOND_TASK;
        String userInput = targetIndex.getOneBased() + TASK_CATEGORY_EMPTY;

        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withTaskCategories().build();
        TaskEditCommand expectedCommand = new TaskEditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }


}
