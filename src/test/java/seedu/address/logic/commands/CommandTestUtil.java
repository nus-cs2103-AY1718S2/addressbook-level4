package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CCA;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CCA_POSITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INJURIES_HISTORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.io.IOException;
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
    public static final String VALID_NRIC_AMY = "S1111111Z";
    public static final String VALID_NRIC_BOB = "S2222222Z";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
    public static final String VALID_TAG_REMOVE = "remove";
    public static final String VALID_SUBJECT_MATHEMATICS = "EMath A1";
    public static final String VALID_SUBJECT_ENGLISH = "English A1";
    public static final String VALID_SUBJECT_PHYSICS = "Phy A1";
    public static final String VALID_SUBJECT_MALAY = "Malay A1";
    public static final String VALID_SUBJECT_BIOLOGY = "Bio A1";
    public static final String VALID_SUBJECT_HISTORY = "Hist A1";
    public static final String VALID_REMARK = "Hardworking";
    public static final String VALID_INJURIES_HISTORY = "Right Ligament Tear";
    public static final String INVALID_REMARK = " ";
    public static final String VALID_PHONE = "98765433";
    public static final String VALID_EMAIL = "email@gmail.com";
    public static final String VALID_RELATIONSHIP_REMARK = "Father";
    public static final String VALID_CCA = "Basketball";
    public static final String VALID_CCA_POSITION = "Member";

    public static final String VALID_NOK_DESC = " " + PREFIX_NAME + VALID_NAME_BOB + " " + PREFIX_PHONE + VALID_PHONE
            + " " + PREFIX_EMAIL + VALID_EMAIL + " " + PREFIX_REMARK + VALID_RELATIONSHIP_REMARK;
    public static final String INVALID_NOK_NAME_DESC = " " + PREFIX_NAME + "$%^&&" + " " + PREFIX_PHONE
            + VALID_PHONE + " " + PREFIX_EMAIL + VALID_EMAIL + " " + PREFIX_REMARK + VALID_RELATIONSHIP_REMARK;
    public static final String INVALID_NOK_PHONE_DESC = " " + PREFIX_NAME + VALID_NAME_BOB + " " + PREFIX_PHONE
            + "adgeg2" + " " + PREFIX_EMAIL + VALID_EMAIL + " " + PREFIX_REMARK + VALID_RELATIONSHIP_REMARK;
    public static final String INVALID_NOK_EMAIL_DESC = " " + PREFIX_NAME + VALID_NAME_BOB + " " + PREFIX_PHONE
            + VALID_PHONE + " " + PREFIX_EMAIL + "ef2dfwe" + " " + PREFIX_REMARK + VALID_RELATIONSHIP_REMARK;
    public static final String INVALID_NOK_REMARK_DESC = " " + PREFIX_NAME + VALID_NAME_BOB + " " + PREFIX_PHONE
            + VALID_PHONE + " " + PREFIX_EMAIL + VALID_EMAIL + " " + PREFIX_REMARK + "funny";

    public static final String VALID_CCA_DESC = " " + PREFIX_CCA + VALID_CCA + " " + PREFIX_CCA_POSITION
            + VALID_CCA_POSITION;
    public static final String EMPTY_CCA_DESC = " " + PREFIX_CCA + "" + " " + PREFIX_CCA_POSITION + VALID_CCA_POSITION;
    public static final String EMPTY_CCA_POSITION_DESC = " " + PREFIX_CCA + VALID_CCA + " " + PREFIX_CCA_POSITION + "";
    public static final String NO_CCA_POSITION_STATED = " " + PREFIX_CCA + VALID_CCA;
    public static final String NO_CCA_STATED = " " + PREFIX_CCA_POSITION + VALID_CCA_POSITION;
    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String NRIC_DESC_AMY = " " + PREFIX_NRIC + VALID_NRIC_AMY;
    public static final String NRIC_DESC_BOB = " " + PREFIX_NRIC + VALID_NRIC_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;
    public static final String NOK_DESC_AMY = " " + PREFIX_NOK + VALID_NAME_AMY;
    public static final String NOK_DESC_BOB = " " + PREFIX_NOK + VALID_NAME_BOB;
    public static final String SUBJECT_DESC_AMY = " " + PREFIX_SUBJECT + VALID_SUBJECT_MATHEMATICS + " "
            + PREFIX_SUBJECT + VALID_SUBJECT_PHYSICS + " " + PREFIX_SUBJECT + VALID_SUBJECT_ENGLISH + " "
            + PREFIX_SUBJECT + VALID_SUBJECT_MALAY + " " + PREFIX_SUBJECT + VALID_SUBJECT_BIOLOGY + " "
            + PREFIX_SUBJECT + VALID_SUBJECT_HISTORY;
    public static final String SUBJECT_DESC_BOB = " " + PREFIX_SUBJECT + VALID_SUBJECT_MATHEMATICS + " "
            + PREFIX_SUBJECT + VALID_SUBJECT_PHYSICS + " " + PREFIX_SUBJECT + VALID_SUBJECT_ENGLISH + " "
            + PREFIX_SUBJECT + VALID_SUBJECT_MALAY + " " + PREFIX_SUBJECT + VALID_SUBJECT_BIOLOGY + " "
            + PREFIX_SUBJECT + VALID_SUBJECT_HISTORY;
    public static final String REMARK_DESC_AMY = " " + PREFIX_REMARK + VALID_REMARK;
    public static final String REMARK_DESC_BOB = " " + PREFIX_REMARK + VALID_REMARK;
    public static final String INJURIES_HISTORY_DESC = " " + PREFIX_INJURIES_HISTORY + VALID_INJURIES_HISTORY;
    public static final String INVALID_INJURIES_HISTORY_DESC = " " + PREFIX_INJURIES_HISTORY + INVALID_REMARK;
    public static final String INVALID_REMARK_DESC = " " + PREFIX_REMARK + INVALID_REMARK;
    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_NRIC_DESC = " " + PREFIX_NRIC + "911a"; // 'a' not allowed in phones
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags
    public static final String INVALID_SUBJECT_NAME_DESC = " " + PREFIX_SUBJECT + "sasf A1";
    public static final String INVALID_SUBJECT_GRADE_DESC = " " + PREFIX_SUBJECT + "English hihi";

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditPersonDescriptor DESC_AMY;
    public static final EditPersonDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY).withNric(VALID_NRIC_AMY)
                .withTags(VALID_TAG_FRIEND).withSubjects(VALID_SUBJECT_MATHEMATICS, VALID_SUBJECT_PHYSICS,
                        VALID_SUBJECT_ENGLISH, VALID_SUBJECT_MALAY, VALID_SUBJECT_BIOLOGY, VALID_SUBJECT_HISTORY)
                .withRemark(REMARK_DESC_AMY).withInjuriesHistory(INJURIES_HISTORY_DESC).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).withNric(VALID_NRIC_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).withSubjects(VALID_SUBJECT_MATHEMATICS,
                        VALID_SUBJECT_PHYSICS, VALID_SUBJECT_ENGLISH, VALID_SUBJECT_MALAY, VALID_SUBJECT_BIOLOGY,
                        VALID_SUBJECT_HISTORY).withRemark(REMARK_DESC_BOB).withInjuriesHistory(VALID_INJURIES_HISTORY)
                        .build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) throws IOException {
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
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage)
            throws IOException {
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
