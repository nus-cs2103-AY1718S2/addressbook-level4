package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DETAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIMETABLE_LINK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;

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
import seedu.address.testutil.EditPersonDescriptorBuilder;

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
    public static final String VALID_TIMETABLE_LINK_AMY = "http://modsn.us/hwWlG";
    public static final String VALID_TIMETABLE_LINK_BOB = "http://modsn.us/d8zfG";
    public static final String VALID_DETAIL_AMY = "Likes boy";
    public static final String VALID_DETAIL_BOB = "Likes girl";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friends";
    public static final String VALID_TAG_COLOR_RED = "red";
    public static final String VALID_TAG_UNUSED = "UNUSED"; //for testing only, do not use to build person

    public static final String VALID_EVENT_NAME_F1 = "F1 Race";
    public static final String VALID_EVENT_NAME_NDP = "National Day Parade";
    public static final String VALID_EVENT_VENUE_F1 = "Marina Bay Street Circuit";
    public static final String VALID_EVENT_VENUE_NDP = "Promenade";
    public static final String VALID_EVENT_DATE_F1 = "19/07/2018";
    public static final String VALID_EVENT_DATE_NDP = "09/08/2018";
    public static final String VALID_EVENT_START_TIME_F1 = "1000";
    public static final String VALID_EVENT_START_TIME_NDP = "1700";
    public static final String VALID_EVENT_END_TIME_F1 = "1300";
    public static final String VALID_EVENT_END_TIME_NDP = "1900";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String TIMETABLE_LINK_DESC_AMY = " " + PREFIX_TIMETABLE_LINK + VALID_TIMETABLE_LINK_AMY;
    public static final String TIMETABLE_LINK_DESC_BOB = " " + PREFIX_TIMETABLE_LINK + VALID_TIMETABLE_LINK_BOB;
    public static final String DETAIL_DESC_AMY = " " + PREFIX_DETAIL + VALID_DETAIL_AMY;
    public static final String DETAIL_DESC_BOB = " " + PREFIX_DETAIL + VALID_DETAIL_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;

    public static final String EVENT_NAME_DESC_F1 = " " + PREFIX_NAME + VALID_EVENT_NAME_F1;
    public static final String EVENT_NAME_DESC_NDP = " " + PREFIX_NAME + VALID_EVENT_NAME_NDP;
    public static final String EVENT_VENUE_DESC_F1 = " " + PREFIX_VENUE + VALID_EVENT_VENUE_F1;
    public static final String EVENT_VENUE_DESC_NDP = " " + PREFIX_VENUE + VALID_EVENT_VENUE_NDP;
    public static final String EVENT_DATE_DESC_F1 = " " + PREFIX_DATE + VALID_EVENT_DATE_F1;
    public static final String EVENT_DATE_DESC_NDP = " " + PREFIX_DATE + VALID_EVENT_DATE_NDP;
    public static final String EVENT_START_TIME_DESC_F1 = " " + PREFIX_START_TIME + VALID_EVENT_START_TIME_F1;
    public static final String EVENT_START_TIME_DESC_NDP = " " + PREFIX_START_TIME + VALID_EVENT_START_TIME_NDP;
    public static final String EVENT_END_TIME_DESC_F1 = " " + PREFIX_END_TIME + VALID_EVENT_END_TIME_F1;
    public static final String EVENT_END_TIME_DESC_NDP = " " + PREFIX_END_TIME + VALID_EVENT_END_TIME_NDP;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TIMETABLE_LINK_DESC = " " + PREFIX_TIMETABLE_LINK
            + "https:modn.us/aoubo"; //URL head not correct
    public static final String INVALID_DETAIL_DESC = " " + PREFIX_DETAIL
            + "Likes &"; //'&' not allowed in detail
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags
    public static final String INVALID_TAG_COLOR = " rainbow"; // doesn't support

    public static final String INVALID_EVENT_NAME_DESC = " " + PREFIX_NAME + "Something&"; // '&' not allowed in names
    public static final String INVALID_EVENT_VENUE_DESC = " " + PREFIX_VENUE + "where!?!"; // 'a' not allowed in phones
    public static final String INVALID_EVENT_DATE_DESC = " " + PREFIX_DATE + "2018-03-28"; // not DD/MM/YYYY format
    public static final String INVALID_EVENT_START_TIME_DESC = " " + PREFIX_START_TIME + "2369"; // wrong minute
    public static final String INVALID_EVENT_END_TIME_DESC = " " + PREFIX_END_TIME + "23:59";    // not HHmm format

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

    public static final String CONTENT_E = "ToDo E";
    public static final String CONTENT_B = "ToDo B";

    public static final String VALID_CONTENT = "Something to do";
    public static final String INVALID_CONTENT = "Something to do&"; // '&' not allowed in contents

    public static final String VALID_STATUS_DONE = "done";
    public static final String VALID_STATUS_UNDONE = "undone";
    public static final String INVALID_STATUS = "invalid status";

    public static final String CONTENT_DESC = " " + PREFIX_CONTENT + VALID_CONTENT;
    public static final String INVALID_CONTENT_DESC = " " + PREFIX_CONTENT + INVALID_CONTENT;

    public static final String INFORMATION_A = "Group A";
    public static final String VALID_INFORMATION = "GROUP A";
    public static final String INVALID_INFORMATION = "Group! A"; //'!' not supported in information

    public static final String VALID_DESC_GROUP = " " + PREFIX_GROUP + VALID_INFORMATION + " "
            + PREFIX_NAME + VALID_NAME_BOB;
    public static final String INVALID_DESC_NO_GROUP = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String INVALID_DESC_NO_NAME = " " + PREFIX_GROUP + "Group A";

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
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

        try {
            command.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedAddressBook, actualModel.getAddressBook());
            assertEquals(expectedFilteredList, actualModel.getFilteredPersonList());
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
