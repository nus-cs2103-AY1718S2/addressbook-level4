package systemtests;

import static seedu.progresschecker.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.progresschecker.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.progresschecker.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.progresschecker.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.progresschecker.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
//import static seedu.progresschecker.logic.commands.CommandTestUtil.INVALID_MAJOR_DESC;
import static seedu.progresschecker.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.progresschecker.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.progresschecker.logic.commands.CommandTestUtil.MAJOR_DESC_AMY;
import static seedu.progresschecker.logic.commands.CommandTestUtil.MAJOR_DESC_BOB;
import static seedu.progresschecker.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.progresschecker.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.progresschecker.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.progresschecker.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.progresschecker.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.progresschecker.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.progresschecker.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.progresschecker.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.progresschecker.logic.commands.CommandTestUtil.VALID_MAJOR_AMY;
import static seedu.progresschecker.logic.commands.CommandTestUtil.VALID_MAJOR_BOB;
import static seedu.progresschecker.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.progresschecker.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.progresschecker.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.progresschecker.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.progresschecker.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.progresschecker.logic.commands.CommandTestUtil.VALID_YEAR_AMY;
//import static seedu.progresschecker.logic.commands.CommandTestUtil.VALID_YEAR_BOB;
import static seedu.progresschecker.logic.commands.CommandTestUtil.YEAR_DESC_AMY;
import static seedu.progresschecker.logic.commands.CommandTestUtil.YEAR_DESC_BOB;
import static seedu.progresschecker.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.progresschecker.testutil.TypicalPersons.ALICE;
import static seedu.progresschecker.testutil.TypicalPersons.AMY;
import static seedu.progresschecker.testutil.TypicalPersons.BOB;
import static seedu.progresschecker.testutil.TypicalPersons.CARL;
import static seedu.progresschecker.testutil.TypicalPersons.HOON;
import static seedu.progresschecker.testutil.TypicalPersons.IDA;
import static seedu.progresschecker.testutil.TypicalPersons.KEYWORD_MATCHING_MEIER;

import org.junit.Test;

import seedu.progresschecker.commons.core.Messages;
import seedu.progresschecker.commons.core.index.Index;
import seedu.progresschecker.logic.commands.AddCommand;
import seedu.progresschecker.logic.commands.RedoCommand;
import seedu.progresschecker.logic.commands.UndoCommand;
import seedu.progresschecker.model.Model;
import seedu.progresschecker.model.person.Email;
//import seedu.progresschecker.model.person.Major;
import seedu.progresschecker.model.person.Name;
import seedu.progresschecker.model.person.Person;
import seedu.progresschecker.model.person.Phone;
import seedu.progresschecker.model.person.exceptions.DuplicatePersonException;
//import seedu.progresschecker.model.person.Year;
import seedu.progresschecker.model.tag.Tag;
import seedu.progresschecker.testutil.PersonBuilder;
import seedu.progresschecker.testutil.PersonUtil;

public class AddCommandSystemTest extends ProgressCheckerSystemTest {

    @Test
    public void add() throws Exception {
        Model model = getModel();

        /* ------------------------ Perform add operations on the shown unfiltered list ----------------------------- */

        /* Case: add a person without tags to a non-empty ProgressChecker, command with leading spaces and trailing
         * spaces
         * -> added
         */
        Person toAdd = AMY;
        String command = "   " + AddCommand.COMMAND_WORD + "  " + NAME_DESC_AMY + "  " + PHONE_DESC_AMY + " "
                + EMAIL_DESC_AMY + "   " + MAJOR_DESC_AMY + "   " + YEAR_DESC_AMY + "   " + TAG_DESC_FRIEND + " ";
        assertCommandSuccess(command, toAdd);

        /* Case: undo adding Amy to the list -> Amy deleted */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding Amy to the list -> Amy added again */
        command = RedoCommand.COMMAND_WORD;
        model.addPerson(toAdd);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: add a person with all fields same as another person in the ProgressChecker except name -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
                .withMajor(VALID_MAJOR_AMY).withYear(VALID_YEAR_AMY).withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + MAJOR_DESC_AMY + YEAR_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: add a person with all fields same as another person in the ProgressChecker except phone -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_AMY)
                .withMajor(VALID_MAJOR_AMY).withYear(VALID_YEAR_AMY).withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_BOB + EMAIL_DESC_AMY + MAJOR_DESC_AMY
                + YEAR_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: add a person with all fields same as another person in the ProgressChecker except email -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_BOB)
                .withMajor(VALID_MAJOR_AMY).withYear(VALID_YEAR_AMY).withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_BOB + MAJOR_DESC_AMY
                + YEAR_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: add a person with all fields same as another person in the ProgressChecker except major -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
                .withMajor(VALID_MAJOR_BOB).withYear(VALID_YEAR_AMY).withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + MAJOR_DESC_BOB
                + YEAR_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: add to empty ProgressChecker -> added */
        deleteAllPersons();
        assertCommandSuccess(ALICE);

        /* Case: add a person with tags, command with parameters in random order -> added */
        toAdd = BOB;
        command = AddCommand.COMMAND_WORD + TAG_DESC_FRIEND + PHONE_DESC_BOB + MAJOR_DESC_BOB + YEAR_DESC_BOB
                + NAME_DESC_BOB + TAG_DESC_HUSBAND + EMAIL_DESC_BOB;
        assertCommandSuccess(command, toAdd);

        /* Case: add a person, missing tags -> added */
        assertCommandSuccess(HOON);

        /* -------------------------- Perform add operation on the shown filtered list ------------------------------ */

        /* Case: filters the person list before adding -> added */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        assertCommandSuccess(IDA);

        /* ------------------------ Perform add operation while a person card is selected --------------------------- */

        /* Case: selects first card in the person list, add a person -> added, card selection remains unchanged */
        selectPerson(Index.fromOneBased(1));
        assertCommandSuccess(CARL);

        /* ----------------------------------- Perform invalid add operations --------------------------------------- */

        /* Case: add a duplicate person -> rejected */
        command = PersonUtil.getAddCommand(HOON);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_PERSON);

        /* Case: add a duplicate person except with different tags -> rejected */
        // "friends" is an existing tag used in the default model, see TypicalPersons#ALICE
        // This test will fail if a new tag that is not in the model is used, see the bug documented in
        // ProgressChecker#addPerson(Person)
        command = PersonUtil.getAddCommand(HOON) + " " + PREFIX_TAG.getPrefix() + "friends";
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_PERSON);

        /* Case: missing name -> rejected */
        command = AddCommand.COMMAND_WORD + PHONE_DESC_AMY + EMAIL_DESC_AMY + MAJOR_DESC_AMY + YEAR_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing phone -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + EMAIL_DESC_AMY + MAJOR_DESC_AMY + YEAR_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing email -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + MAJOR_DESC_AMY + YEAR_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing major -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + YEAR_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing year -> rejected */
        /*command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + MAJOR_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));*/

        /* Case: invalid keyword -> rejected */
        command = "adds " + PersonUtil.getPersonDetails(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid name -> rejected */
        command = AddCommand.COMMAND_WORD + INVALID_NAME_DESC + PHONE_DESC_AMY + EMAIL_DESC_AMY + MAJOR_DESC_AMY
                + YEAR_DESC_AMY;
        assertCommandFailure(command, Name.MESSAGE_NAME_CONSTRAINTS);

        /* Case: invalid phone -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + INVALID_PHONE_DESC + EMAIL_DESC_AMY + MAJOR_DESC_AMY
                + YEAR_DESC_AMY;
        assertCommandFailure(command, Phone.MESSAGE_PHONE_CONSTRAINTS);

        /* Case: invalid email -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + INVALID_EMAIL_DESC + MAJOR_DESC_AMY
                + YEAR_DESC_AMY;
        assertCommandFailure(command, Email.MESSAGE_EMAIL_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + MAJOR_DESC_AMY
                + YEAR_DESC_AMY
                + INVALID_TAG_DESC;
        assertCommandFailure(command, Tag.MESSAGE_TAG_CONSTRAINTS);
    }

    /**
     * Executes the {@code AddCommand} that adds {@code toAdd} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code AddCommand} with the details of
     * {@code toAdd}.<br>
     * 4. {@code Model}, {@code Storage} and {@code PersonListPanel} equal to the corresponding components in
     * the current model added with {@code toAdd}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code ProgressCheckerSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see ProgressCheckerSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(Person toAdd) {
        assertCommandSuccess(PersonUtil.getAddCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(Person)}. Executes {@code command}
     * instead.
     * @see AddCommandSystemTest#assertCommandSuccess(Person)
     */
    private void assertCommandSuccess(String command, Person toAdd) {
        Model expectedModel = getModel();
        try {
            expectedModel.addPerson(toAdd);
        } catch (DuplicatePersonException dpe) {
            throw new IllegalArgumentException("toAdd already exists in the model.");
        }
        String expectedResultMessage = String.format(AddCommand.MESSAGE_SUCCESS, toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Person)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Model}, {@code Storage} and {@code PersonListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see AddCommandSystemTest#assertCommandSuccess(String, Person)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Model}, {@code Storage} and {@code PersonListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code ProgressCheckerSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see ProgressCheckerSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
