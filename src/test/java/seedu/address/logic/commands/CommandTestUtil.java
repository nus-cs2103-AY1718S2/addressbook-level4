package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEVEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.pair.NameContainsKeywordsPredicatePair;
import seedu.address.model.pair.Pair;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.PersonMatchedCannotDeleteException;
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
    public static final String VALID_TAG_HUSBAND = "Husband";
    public static final String VALID_TAG_FRIEND = "Friend";
    public static final String VALID_TAG_UNUSED = "Unused";
    public static final String SUBJECT_ENGLISH = "english";
    public static final String SUBJECT_CHINESE = "chinese";
    public static final String SUBJECT_CHEMISTRY = "chemistry";
    public static final String SUBJECT_PHYSICS = "physics";
    public static final String VALID_SUBJECT_AMY = "math";
    public static final String VALID_SUBJECT_BOB = "english";
    public static final String VALID_LEVEL_AMY = "lower Sec";
    public static final String VALID_LEVEL_BOB = "upper Sec";
    public static final String VALID_STATUS_BOB = "not matched";
    public static final String VALID_PRICE_AMY = "98";
    public static final String VALID_PRICE_BOB = "113";
    public static final String VALID_ROLE_BOB = "tutor";
    public static final String LEVEL_LOWER_SEC = "lower Sec";
    public static final String LEVEL_UPPER_SEC = "upper Sec";
    public static final String LEVEL_LOWER_PRI = "lower Pri";
    public static final String LEVEL_UPPER_PRI = "upper Pri";
    public static final String ROLE_TUTOR = "tutor";
    public static final String ROLE_STUDENT = "student";
    public static final String STATUS_UNMATCHED = "not matched";
    public static final String STATUS_MATCHED = "matched";
    public static final String REMARK_AMY = "Hardworking student.";
    public static final String REMARK_BOB = "Patient tutor.";
    public static final String VALID_RATE_AMY = "4.0";
    public static final String VALID_RATE_BOB = "4.2";
    public static final String RATECOUNT_AMY = "4";
    public static final String RATECOUNT_BOB = "1";

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

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_PRICE_DESC = " " + PREFIX_PRICE + "-1"; //negative number
    public static final String INVALID_SUBJECT_DESC = " " + PREFIX_SUBJECT + "fake news"; //not a listed subject
    public static final String INVALID_LEVEL_DESC = " " + PREFIX_LEVEL + "kindergarden"; //not a listed level
    public static final String INVALID_ROLE_DESC = " " + PREFIX_ROLE + "teacher"; // 'teacher' not allowed in tags
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags
    public static final String INVALID_STATUS_DESC = " " + PREFIX_STATUS + "veryverymatched"; //not a listed status
    public static final String INVALID_RATE_EXCEEDRANGE = "6.0";
    public static final String INVALID_RATE_NEGATIVE = "-0.1";

    public static final String SUBJECT_DESC_ENGLISH = " " + PREFIX_SUBJECT + "english";
    public static final String SUBJECT_DESC_CHINESE = " " + PREFIX_SUBJECT + "chinese";
    public static final String LEVEL_DESC_LOWER_SEC = " " + PREFIX_LEVEL + "lower Sec";
    public static final String LEVEL_DESC_UPPER_SEC = " " + PREFIX_LEVEL + "upper Sec";
    public static final String SUBJECT_DESC_AMY = " " + PREFIX_SUBJECT + VALID_SUBJECT_AMY;
    public static final String SUBJECT_DESC_BOB = " " + PREFIX_SUBJECT + VALID_SUBJECT_BOB;
    public static final String LEVEL_DESC_AMY = " " + PREFIX_LEVEL + VALID_LEVEL_AMY;
    public static final String LEVEL_DESC_BOB = " " + PREFIX_LEVEL + VALID_LEVEL_BOB;
    public static final String PRICE_DESC_AMY = " " + PREFIX_PRICE + VALID_PRICE_AMY;
    public static final String PRICE_DESC_BOB = " " + PREFIX_PRICE + VALID_PRICE_BOB;
    public static final String ROLE_DESC_AMY = " " + PREFIX_ROLE + ROLE_STUDENT;
    public static final String ROLE_DESC_BOB = " " + PREFIX_ROLE + VALID_ROLE_BOB;
    public static final String ROLE_DESC_STUDENT = " " + PREFIX_ROLE + "student";
    public static final String ROLE_DESC_TUTOR = " " + PREFIX_ROLE + "tutor";
    public static final String STATUS_DESC_UNMATCHED = " " + PREFIX_STATUS + "not matched";

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final String CASE_INSENSITIVE_NAME_BOB = " " + PREFIX_NAME + "bOb cHoO";
    public static final String CASE_INSENSITIVE_EMAIL_BOB = " " + PREFIX_EMAIL + "bOb@eXaMpLe.CoM";
    public static final String CASE_INSENSITIVE_ADDRESS_BOB  = " " + PREFIX_ADDRESS + "bLOck 123, BoBBy StReEt 3";
    public static final String CASE_INSENSITIVE_SUBJECT_BOB = " " + PREFIX_SUBJECT + "EngLIsH";
    public static final String CASE_INSENSITIVE_LEVEL_BOB = " " + PREFIX_LEVEL + "uPPeR seC";
    public static final String CASE_INSENSITIVE_STATUS_BOB = " " + PREFIX_STATUS + "nOt MAtCHEd";
    public static final String CASE_INSENSITIVE_ROLE_BOB = " " + PREFIX_ROLE + "TutOR";

    public static final String SUBJECT_SHORTCUT_CHINESE = " " + PREFIX_SUBJECT + "chi";
    public static final String SUBJECT_SHORTCUT_PHYSICS = " " + PREFIX_SUBJECT + "phy";
    public static final String SUBJECT_SHORTCUT_CHEMISTRY = " " + PREFIX_SUBJECT + "chem";
    public static final String SUBJECT_SHORTCUT_ENGLISH = " " + PREFIX_SUBJECT + "eng";
    public static final String LEVEL_SHORTCUT_UPPER_SEC = " " + PREFIX_LEVEL + "us";
    public static final String LEVEL_SHORTCUT_LOWER_SEC = " " + PREFIX_LEVEL + "ls";
    public static final String LEVEL_SHORTCUT_UPPER_PRI = " " + PREFIX_LEVEL + "up";
    public static final String LEVEL_SHORTCUT_LOWER_PRI = " " + PREFIX_LEVEL + "lp";
    public static final String STATUS_SHORTCUT_UNMATCHED = " " + PREFIX_STATUS + "nm";
    public static final String STATUS_SHORTCUT_MATCHED = " " + PREFIX_STATUS + "m";
    public static final String ROLE_SHORTCUT_TUTOR = " " + PREFIX_ROLE + "t";
    public static final String ROLE_SHORTCUT_STUDENT = " " + PREFIX_ROLE + "s";

    public static final String CASE_INSENSITIVE_SUBJECT_SHORTCUT_ENGLISH = " " + PREFIX_SUBJECT + "eNg";
    public static final String CASE_INSENSITIVE_LEVEL_SHORTCUT_UPPER_SEC = " " + PREFIX_LEVEL + "Us";
    public static final String CASE_INSENSITIVE_STATUS_SHORTCUT_UNMATCHED = " " + PREFIX_STATUS + "nM";
    public static final String CASE_INSENSITIVE_ROLE_SHORTCUT_TUTOR = " " + PREFIX_ROLE + "T";

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

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
     * Updates {@code model}'s filtered list to show only the pair at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showPairAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPairList().size());

        Pair pair = model.getFilteredPairList().get(targetIndex.getZeroBased());
        final String[] splitName = pair.getPairName().split("\\s+");
        model.updateFilteredPairList(new NameContainsKeywordsPredicatePair(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPairList().size());
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
        } catch (PersonMatchedCannotDeleteException e) {
            e.printStackTrace();
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
