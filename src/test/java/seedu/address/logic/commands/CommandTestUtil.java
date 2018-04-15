package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ACTIVITYLEVEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_START_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_START_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HEIGHT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEIGHT;

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
    public static final String VALID_HEIGHT_AMY = "158.0";
    public static final String VALID_HEIGHT_BOB = "169.0";
    public static final String VALID_WEIGHT_AMY = "55.5";
    public static final String VALID_WEIGHT_BOB = "65.5";
    public static final String VALID_GENDER_AMY = "f";
    public static final String VALID_GENDER_BOB = "m";
    public static final String VALID_AGE_AMY = "22";
    public static final String VALID_AGE_BOB = "23";
    public static final String VALID_ACTIVITYLEVEL_AMY = "1.2";
    public static final String VALID_ACTIVITYLEVEL_BOB = "1.9";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";

    public static final String VALID_EVENT_NAME = "Weights Training";
    public static final String VALID_START_DATE = "2018-05-20";
    public static final String VALID_START_TIME = "15:30";
    public static final String VALID_END_DATE = "2018-05-20";
    public static final String VALID_END_TIME = "16:30";
    public static final String EVENT_NAME_DESC = " " + PREFIX_EVENT_NAME + VALID_EVENT_NAME;
    public static final String EVENT_START_DATE_DESC = " " + PREFIX_EVENT_START_DATE + VALID_START_DATE;
    public static final String EVENT_START_TIME_DESC = " " + PREFIX_EVENT_START_TIME + VALID_START_TIME;
    public static final String EVENT_END_DATE_DESC = " " + PREFIX_EVENT_END_DATE + VALID_END_DATE;
    public static final String EVENT_END_TIME_DESC = " " + PREFIX_EVENT_END_TIME + VALID_END_TIME;

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String HEIGHT_DESC_AMY = " " + PREFIX_HEIGHT + VALID_HEIGHT_AMY;
    public static final String HEIGHT_DESC_BOB = " " + PREFIX_HEIGHT + VALID_HEIGHT_BOB;
    public static final String WEIGHT_DESC_AMY = " " + PREFIX_WEIGHT + VALID_WEIGHT_AMY;
    public static final String WEIGHT_DESC_BOB = " " + PREFIX_WEIGHT + VALID_WEIGHT_BOB;
    public static final String GENDER_DESC_AMY = " " + PREFIX_GENDER + VALID_GENDER_AMY;
    public static final String GENDER_DESC_BOB = " " + PREFIX_GENDER + VALID_GENDER_BOB;
    public static final String AGE_DESC_AMY = " " + PREFIX_AGE + VALID_AGE_AMY;
    public static final String AGE_DESC_BOB = " " + PREFIX_AGE + VALID_AGE_BOB;
    public static final String ACTIVITYLEVEL_DESC_AMY = " " + PREFIX_ACTIVITYLEVEL + VALID_ACTIVITYLEVEL_AMY;
    public static final String ACTIVITYLEVEL_DESC_BOB = " " + PREFIX_ACTIVITYLEVEL + VALID_ACTIVITYLEVEL_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_HEIGHT_DESC = " " + PREFIX_HEIGHT; // empty string not allowed for height
    public static final String INVALID_WEIGHT_DESC = " " + PREFIX_WEIGHT; // empty string not allowed for weight
    public static final String INVALID_GENDER_DESC = " " + PREFIX_GENDER + "Y"; // 'Y' not allowed for gender
    public static final String INVALID_AGE_DESC = " " + PREFIX_AGE + "Y"; // 'Y' not allowed for age
    public static final String INVALID_ACTIVITYLEVEL_DESC = " " + PREFIX_ACTIVITYLEVEL + "Y";
    // 'Y' not allowed for activity level
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String INVALID_EVENT_NAME = "Weights&Training";
    public static final String INVALID_START_DATE = "18-05-20";
    public static final String INVALID_START_TIME = "15:300";
    public static final String INVALID_END_DATE = "18-05-20";
    public static final String INVALID_END_TIME = "160:30";
    public static final String INVALID_EVENT_NAME_DESC = " " + PREFIX_EVENT_NAME
            + "Weight&Training"; // '&' not allowed in event names
    public static final String INVALID_START_DATE_DESC = " " + PREFIX_EVENT_START_DATE
            + "18-05-20"; // Start Date should be in YYYY-MM-DD format
    public static final String INVALID_START_TIME_DESC = " " + PREFIX_EVENT_START_TIME
            + "3:300"; // Start Time should be in HH:MM format
    public static final String INVALID_END_DATE_DESC = " " + PREFIX_EVENT_END_DATE
            + "18-05-20"; // End Date should be in YYYY-MM-DD format
    public static final String INVALID_END_TIME_DESC = " " + PREFIX_EVENT_END_TIME
            + "4:300"; // End Time should be in HH:MM format

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withHeight(VALID_HEIGHT_AMY).withWeight(VALID_WEIGHT_AMY).withGender(VALID_GENDER_AMY)
                .withAge(VALID_AGE_AMY)
                .withActivityLevel(VALID_ACTIVITYLEVEL_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withHeight(VALID_HEIGHT_BOB).withWeight(VALID_WEIGHT_BOB).withGender(VALID_GENDER_BOB)
                .withAge(VALID_AGE_BOB)
                .withActivityLevel(VALID_ACTIVITYLEVEL_BOB)
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
