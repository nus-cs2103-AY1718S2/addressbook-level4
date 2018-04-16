package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_TITLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILEPATH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_DUE_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_STATUS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskNameContainsKeywordsPredicate;
import seedu.address.model.task.exceptions.TaskNotFoundException;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.EditTaskDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
    public static final String VALID_LOCAL_IMAGE_JPG = "src/test/resources/PictureTest/dezhanglee.jpg";
    public static final String VALID_LOCAL_IMAGE_5MB = "src/test/resources/PictureTest/5mbTestJpg.jpg";
    public static final String VALID_LOCAL_IMAGE_PNG = "src/main/resources/images/help_icon.png";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;
    public static final String PICTURE_DESC_LOCAL_IMAGE = " " + PREFIX_FILEPATH + VALID_LOCAL_IMAGE_JPG;
    public static final String PICTURE_DESC_LOCAL_IMAGE_5MB = " " + PREFIX_FILEPATH + VALID_LOCAL_IMAGE_5MB;

    public static final String INVALID_LOCAL_FILE_NONIMAGE =
            "src/test/resources/PictureTest/nonImageFile.txt";
    public static final String INVALID_LOCAL_FILE_NONIMAGE_WITH_IMAGE_FILETYPE =
            "src/test/resources/PictureTest/nonImageFileWithJpgPrefix.jpg";
    public static final String VALID_LOCAL_IMAGE_BIGGER_THAN_5MB =
            "src/test/resources/PictureTest/5.6mbTestJpg.jpg";

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String INVALID_PIC_DESC_NONIMAGE = " " + PREFIX_FILEPATH + INVALID_LOCAL_FILE_NONIMAGE;
    public static final String INVALID_PIC_DESC_NONIMAGE_WITH_IMAGE_FILETYPE = " " + PREFIX_FILEPATH
            + INVALID_LOCAL_FILE_NONIMAGE_WITH_IMAGE_FILETYPE;
    public static final String INVALID_PIC_DESC_IMAGE_GREATER_THAN_5MB = " " + PREFIX_FILEPATH
            + VALID_LOCAL_IMAGE_BIGGER_THAN_5MB;

    //@@author x3tsunayh
    public static final String VALID_XML_FILEPATH = "validXmlFile.xml";
    public static final String VALID_CSV_FILEPATH = "validCsvFile.csv";
    public static final String INVALID_XML_FILEPATH = "invalidXmlFile.xmll";
    public static final String INVALID_CSV_FILEPATH = "invalidCsvFile.csvv";
    public static final String EXISTING_XML_FILEPATH = "existingXmlFile.xml";
    public static final String EXISTING_CSV_FILEPATH = "existingCsvFile.csv";
    public static final String VALID_TITLE_CNY = "CNY Celebration 2018";
    public static final String VALID_TITLE_CHRISTMAS = "Christmas Celebration 2018";
    public static final String VALID_DESCRIPTION_CNY = "CNY Celebration at FOS";
    public static final String VALID_DESCRIPTION_CHRISTMAS = "Christmas Party at SOC";
    public static final String VALID_LOCATION_CNY = "NUS S16 Level 3";
    public static final String VALID_LOCATION_CHRISTMAS = "NUS COM1";
    public static final String VALID_DATETIME_CNY = "2018-02-15 1000";
    public static final String VALID_DATETIME_CHRISTMAS = "2018-12-24 1830";

    public static final String TITLE_DESC_CNY = " " + PREFIX_EVENT_TITLE + VALID_TITLE_CNY;
    public static final String TITLE_DESC_CHRISTMAS = " " + PREFIX_EVENT_TITLE + VALID_TITLE_CHRISTMAS;
    public static final String DESCRIPTION_DESC_CNY = " " + PREFIX_EVENT_DESCRIPTION + VALID_DESCRIPTION_CNY;
    public static final String DESCRIPTION_DESC_CHRISTMAS = " "
            + PREFIX_EVENT_DESCRIPTION + VALID_DESCRIPTION_CHRISTMAS;
    public static final String LOCATION_DESC_CNY = " " + PREFIX_EVENT_LOCATION + VALID_LOCATION_CNY;
    public static final String LOCATION_DESC_CHRISTMAS = " " + PREFIX_EVENT_LOCATION + VALID_LOCATION_CHRISTMAS;
    public static final String DATETIME_DESC_CNY = " " + PREFIX_EVENT_DATETIME + VALID_DATETIME_CNY;
    public static final String DATETIME_DESC_CHRISTMAS = " " + PREFIX_EVENT_DATETIME + VALID_DATETIME_CHRISTMAS;

    public static final String VALID_DATETIME_01 = "2018-12-01 2359";
    public static final String VALID_DATETIME_29 = "2018-12-29 2359";
    public static final String VALID_DATETIME_30 = "2018-12-30 2359";
    public static final String VALID_DATETIME_31 = "2018-12-31 2359";
    public static final String INVALID_DATETIME_00 = "2018-12-00 2359"; //There is no 00 in the date
    public static final String INVALID_DATETIME_32 = "2018-12-32 2359"; //There is no 32 in the date

    public static final String VALID_DATETIME_DESC_01 = " "
            + PREFIX_EVENT_DATETIME + VALID_DATETIME_01;
    public static final String VALID_DATETIME_DESC_29 = " "
            + PREFIX_EVENT_DATETIME + VALID_DATETIME_29;
    public static final String VALID_DATETIME_DESC_30 = " "
            + PREFIX_EVENT_DATETIME + VALID_DATETIME_30;
    public static final String VALID_DATETIME_DESC_31 = " "
            + PREFIX_EVENT_DATETIME + VALID_DATETIME_31;
    public static final String INVALID_DATETIME_DESC_00 = " "
            + PREFIX_EVENT_DATETIME + INVALID_DATETIME_00;
    public static final String INVALID_DATETIME_DESC_32 = " "
            + PREFIX_EVENT_DATETIME + INVALID_DATETIME_32;

    //@@author CYX28
    public static final String VALID_TASK_NAME_TASKFIRST = "First task";
    public static final String VALID_TASK_NAME_TASKSECOND = "Second task";
    public static final String VALID_TASK_PRIORITY_TASKFIRST = "high";
    public static final String VALID_TASK_PRIORITY_TASKSECOND = "low";
    public static final String VALID_TASK_DESCRIPTION_TASKFIRST = "Create a new app";
    public static final String VALID_TASK_DESCRIPTION_TASKSECOND = "Discuss outing details";
    public static final String VALID_TASK_DUE_DATE_TASKFIRST = "2018-04-02";
    public static final String VALID_TASK_DUE_DATE_TASKSECOND = "2018-05-10";
    public static final String VALID_TASK_STATUS_TASKFIRST = "undone";
    public static final String VALID_TASK_STATUS_TASKSECOND = "done";
    public static final String VALID_TASK_CATEGORY_PERSONAL = "personal";
    public static final String VALID_TASK_CATEGORY_WORK = "work";
    public static final String VALID_TASK_CATEGORY_MEETING = "meeting";

    public static final String TASK_NAME_DESC_TASKFIRST =
            " " + PREFIX_TASK_NAME + VALID_TASK_NAME_TASKFIRST;
    public static final String TASK_NAME_DESC_TASKSECOND =
            " " + PREFIX_TASK_NAME + VALID_TASK_NAME_TASKSECOND;
    public static final String TASK_PRIORITY_DESC_TASKFIRST =
            " " + PREFIX_TASK_PRIORITY + VALID_TASK_PRIORITY_TASKFIRST;
    public static final String TASK_PRIORITY_DESC_TASKSECOND =
            " " + PREFIX_TASK_PRIORITY + VALID_TASK_PRIORITY_TASKSECOND;
    public static final String TASK_DESCRIPTION_DESC_TASKFIRST =
            " " + PREFIX_TASK_DESCRIPTION + VALID_TASK_DESCRIPTION_TASKFIRST;
    public static final String TASK_DESCRIPTION_DESC_TASKSECOND =
            " " + PREFIX_TASK_DESCRIPTION + VALID_TASK_DESCRIPTION_TASKSECOND;
    public static final String TASK_DUE_DATE_DESC_TASKFIRST =
            " " + PREFIX_TASK_DUE_DATE + VALID_TASK_DUE_DATE_TASKFIRST;
    public static final String TASK_DUE_DATE_DESC_TASKSECOND =
            " " + PREFIX_TASK_DUE_DATE + VALID_TASK_DUE_DATE_TASKSECOND;
    public static final String TASK_STATUS_DESC_TASKFIRST =
            " " + PREFIX_TASK_STATUS + VALID_TASK_STATUS_TASKFIRST;
    public static final String TASK_STATUS_DESC_TASKSECOND =
            " " + PREFIX_TASK_STATUS + VALID_TASK_STATUS_TASKSECOND;
    public static final String TASK_CATEGORY_DESC_PERSONAL =
            " " + PREFIX_TASK_CATEGORY + VALID_TASK_CATEGORY_PERSONAL;
    public static final String TASK_CATEGORY_DESC_WORK =
            " " + PREFIX_TASK_CATEGORY + VALID_TASK_CATEGORY_WORK;

    public static final String INVALID_TASK_NAME_DESC = " "
            + PREFIX_TASK_NAME + "Task*"; // '*' not allowed in taskNames
    public static final String INVALID_TASK_PRIORITY_DESC = " "
            + PREFIX_TASK_PRIORITY + "random"; // only 'high', 'medium', 'low' allowed in taskPriority
    public static final String INVALID_TASK_DESCRIPTION_DESC = " "
            + PREFIX_TASK_DESCRIPTION + ""; // empty string not allowed in taskDescription
    public static final String INVALID_TASK_DUE_DATE_DESC = " "
            + PREFIX_TASK_DUE_DATE + "2018 05 10"; // date without formatter not allowed in taskDueDate
    public static final String INVALID_TASK_STATUS_DESC = " "
            + PREFIX_TASK_STATUS + "random"; // only 'done', 'undone' allowed in taskStatus
    public static final String INVALID_TASK_CATEGORY_DESC = " "
            + PREFIX_TASK_CATEGORY + "meeting & work"; // '&' not allowed in taskCategory

    //@@author
    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

    public static final TaskEditCommand.EditTaskDescriptor DESC_TASKFIRST;
    public static final TaskEditCommand.EditTaskDescriptor DESC_TASKSECOND;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();

        DESC_TASKFIRST = new EditTaskDescriptorBuilder().withTaskName(VALID_TASK_NAME_TASKFIRST)
                .withTaskPriority(VALID_TASK_PRIORITY_TASKFIRST).withTaskDescription(VALID_TASK_DESCRIPTION_TASKFIRST)
                .withTaskDueDate(VALID_TASK_DUE_DATE_TASKFIRST).withTaskStatus(VALID_TASK_STATUS_TASKFIRST)
                .withTaskCategories(VALID_TASK_CATEGORY_PERSONAL).build();

        DESC_TASKSECOND = new EditTaskDescriptorBuilder().withTaskName(VALID_TASK_NAME_TASKSECOND)
                .withTaskPriority(VALID_TASK_PRIORITY_TASKSECOND).withTaskDescription(VALID_TASK_DESCRIPTION_TASKSECOND)
                .withTaskDueDate(VALID_TASK_DUE_DATE_TASKSECOND).withTaskStatus(VALID_TASK_STATUS_TASKSECOND)
                .withTaskCategories(VALID_TASK_CATEGORY_WORK).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        try {
            CommandResult result = command.execute();
            assertEquals(expectedMessage, result.feedbackToUser);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book and the filtered person list in the {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Person> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPersonList());
        List<Task> expectedFilteredTaskList = new ArrayList<>(actualModel.getFilteredTaskList());

        try {
            command.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedAddressBook, actualModel.getAddressBook());
            assertEquals(expectedFilteredList, actualModel.getFilteredPersonList());
            assertEquals(expectedFilteredTaskList, actualModel.getFilteredTaskList());
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPersonList().size());

        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPersonList().size());
    }

    /**
     * Deletes the first person in {@code model}'s filtered list from {@code model}'s address book.
     */
    public static void deleteFirstPerson(Model model) {
        Person firstPerson = model.getFilteredPersonList().get(0);
        try {
            model.deletePerson(firstPerson);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("Person in filtered list must exist in model.", pnfe);
        }
    }

    //@@author CYX28
    /**
     * Updates {@code model}'s filtered list to show only the task at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showTaskAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredTaskList().size());

        Task task = model.getFilteredTaskList().get(targetIndex.getZeroBased());
        final String[] splitTaskName = task.getTaskName().value.split("\\s+");
        model.updateFilteredTaskList(new TaskNameContainsKeywordsPredicate(Arrays.asList(splitTaskName[0])));

        assertEquals(1, model.getFilteredTaskList().size());
    }

    /**
     * Deletes the first task in {@code model}'s filtered list from {@code model}'s address book.
     */
    public static void deleteFirstTask(Model model) {
        Task firstTask = model.getFilteredTaskList().get(0);
        try {
            model.deleteTask(firstTask);
        } catch (TaskNotFoundException tnfe) {
            throw new AssertionError("Task in filtered list must exist in model.", tnfe);
        }
    }

    //@@author
    /**
     * Returns an {@code UndoCommand} with the given {@code model} and {@code undoRedoStack} set.
     */
    public static UndoCommand prepareUndoCommand(Model model, UndoRedoStack undoRedoStack) {
        UndoCommand undoCommand = new UndoCommand();
        undoCommand.setData(model, new CommandHistory(), undoRedoStack);
        return undoCommand;
    }

    /**
     * Returns a {@code RedoCommand} with the given {@code model} and {@code undoRedoStack} set.
     */
    public static RedoCommand prepareRedoCommand(Model model, UndoRedoStack undoRedoStack) {
        RedoCommand redoCommand = new RedoCommand();
        redoCommand.setData(model, new CommandHistory(), undoRedoStack);
        return redoCommand;
    }
}
