package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.BLOODTYPE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.BLOODTYPE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.DOB_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DOB_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_BLOODTYPE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DOB_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NRIC_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NRIC_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NRIC_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BLOODTYPE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DOB_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.TypicalPatients.ALICE;
import static seedu.address.testutil.TypicalPatients.AMY;
import static seedu.address.testutil.TypicalPatients.BOB;
import static seedu.address.testutil.TypicalPatients.CARL;
import static seedu.address.testutil.TypicalPatients.HOON;
import static seedu.address.testutil.TypicalPatients.IDA;
import static seedu.address.testutil.TypicalPatients.KEYWORD_MATCHING_MEIER;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.login.LoginManager;
import seedu.address.model.Model;
import seedu.address.model.patient.Address;
import seedu.address.model.patient.BloodType;
import seedu.address.model.patient.DateOfBirth;
import seedu.address.model.patient.Email;
import seedu.address.model.patient.Name;
import seedu.address.model.patient.Nric;
import seedu.address.model.patient.Patient;
import seedu.address.model.patient.Phone;
import seedu.address.model.patient.exceptions.DuplicatePatientException;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PatientBuilder;
import seedu.address.testutil.PatientUtil;

public class AddCommandSystemTest extends ImdbSystemTest {

    @Test
    public void add() throws Exception {
        Model model = getModel();
        // LoginManager.logout();
        LoginManager.authenticate("alice", "password123");

        /* ------------------------ Perform add operations on the shown unfiltered list ----------------------------- */

        /* Case: add a patient without tags to a non-empty address book, command with leading spaces and trailing spaces
         * -> added
         */
        Patient toAdd = AMY;
        String command = "   " + AddCommand.COMMAND_WORD + "  " + NAME_DESC_AMY + "  " + NRIC_DESC_AMY + "  "
                + PHONE_DESC_AMY + " "
                + EMAIL_DESC_AMY + "   " + ADDRESS_DESC_AMY + "   " + DOB_DESC_AMY + "   "
                + BLOODTYPE_DESC_AMY + "   " + TAG_DESC_FRIEND + " ";
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

        /* Case: add a patient with all fields same as another patient in the address book except name -> added */
        toAdd = new PatientBuilder().withName(VALID_NAME_BOB).withNric(VALID_NRIC_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_AMY).withDob(VALID_DOB_AMY)
                .withBloodType(VALID_BLOODTYPE_AMY).withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_BOB + NRIC_DESC_AMY
                + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + DOB_DESC_AMY + BLOODTYPE_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: add a patient with all fields same as another patient in the address book except phone -> added */
        toAdd = new PatientBuilder().withName(VALID_NAME_AMY).withNric(VALID_NRIC_AMY)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_AMY).withDob(VALID_DOB_AMY).withBloodType(VALID_BLOODTYPE_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + NRIC_DESC_AMY + PHONE_DESC_BOB
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + DOB_DESC_AMY + BLOODTYPE_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: add a patient with all fields same as another patient in the address book except email -> added */
        toAdd = new PatientBuilder().withName(VALID_NAME_AMY).withNric(VALID_NRIC_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_AMY).withDob(VALID_DOB_AMY).withBloodType(VALID_BLOODTYPE_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + NRIC_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_BOB + ADDRESS_DESC_AMY
                + DOB_DESC_AMY + BLOODTYPE_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: add a patient with all fields same as another patient in the address book except address -> added */
        toAdd = new PatientBuilder().withName(VALID_NAME_AMY).withNric(VALID_NRIC_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_BOB).withDob(VALID_DOB_AMY).withBloodType(VALID_BLOODTYPE_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + NRIC_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + ADDRESS_DESC_BOB
                + DOB_DESC_AMY + BLOODTYPE_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);


        /* Case: add to empty address book -> added */
        deleteAllPersons();
        assertCommandSuccess(ALICE);

        /* Case: add a patient with tags, command with parameters in random order -> added */
        toAdd = BOB;
        command = AddCommand.COMMAND_WORD + TAG_DESC_FRIEND + PHONE_DESC_BOB + ADDRESS_DESC_BOB + NAME_DESC_BOB
                + TAG_DESC_HUSBAND + EMAIL_DESC_BOB + NRIC_DESC_BOB + DOB_DESC_BOB + BLOODTYPE_DESC_BOB;
        assertCommandSuccess(command, toAdd);

        /* Case: add a patient, missing tags -> added */
        assertCommandSuccess(HOON);

        /* -------------------------- Perform add operation on the shown filtered list ------------------------------ */

        /* Case: filters the patient list before adding -> added */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        assertCommandSuccess(IDA);

        /* ------------------------ Perform add operation while a patient card is selected -------------------------- */

        /* Case: selects first card in the patient list, add a patient -> added, card selection remains unchanged */
        selectPerson(Index.fromOneBased(1));
        assertCommandSuccess(CARL);

        /* ----------------------------------- Perform invalid add operations --------------------------------------- */

        /* Case: add a duplicate patient -> rejected */
        command = PatientUtil.getAddCommand(HOON);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_PERSON);

        /* Case: add a duplicate patient except with different tags -> rejected */
        // "friends" is an existing tag used in the default model, see TypicalPatients#ALICE
        // This test will fail if a new tag that is not in the model is used, see the bug documented in
        // Imdb#addPerson(Patient)
        command = PatientUtil.getAddCommand(HOON) + " " + PREFIX_TAG.getPrefix() + "friends";
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_PERSON);

        /* Case: missing name -> rejected */
        command = AddCommand.COMMAND_WORD + NRIC_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + DOB_DESC_AMY + BLOODTYPE_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing nric -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + DOB_DESC_AMY + BLOODTYPE_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing phone -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + NRIC_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + DOB_DESC_AMY + BLOODTYPE_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing email -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + NRIC_DESC_AMY + PHONE_DESC_AMY
                + ADDRESS_DESC_AMY + DOB_DESC_AMY + BLOODTYPE_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing address -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + NRIC_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + DOB_DESC_AMY + BLOODTYPE_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing dob -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + NRIC_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + BLOODTYPE_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing blood type -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + NRIC_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + DOB_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        command = "adds " + PatientUtil.getPersonDetails(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid name -> rejected */
        command = AddCommand.COMMAND_WORD + INVALID_NAME_DESC + NRIC_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + DOB_DESC_AMY + BLOODTYPE_DESC_AMY;
        assertCommandFailure(command, Name.MESSAGE_NAME_CONSTRAINTS);

        /* Case: invalid nric -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + INVALID_NRIC_DESC + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + DOB_DESC_AMY + BLOODTYPE_DESC_AMY;
        assertCommandFailure(command, Nric.MESSAGE_NRIC_CONSTRAINTS);

        /* Case: invalid phone -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + NRIC_DESC_AMY + INVALID_PHONE_DESC + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + DOB_DESC_AMY + BLOODTYPE_DESC_AMY;
        assertCommandFailure(command, Phone.MESSAGE_PHONE_CONSTRAINTS);

        /* Case: invalid email -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + NRIC_DESC_AMY + PHONE_DESC_AMY + INVALID_EMAIL_DESC
                + ADDRESS_DESC_AMY + DOB_DESC_AMY + BLOODTYPE_DESC_AMY;
        assertCommandFailure(command, Email.MESSAGE_EMAIL_CONSTRAINTS);

        /* Case: invalid address -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + NRIC_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + INVALID_ADDRESS_DESC + DOB_DESC_AMY + BLOODTYPE_DESC_AMY;
        assertCommandFailure(command, Address.MESSAGE_ADDRESS_CONSTRAINTS);

        /* Case: invalid dob -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + NRIC_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + INVALID_DOB_DESC + BLOODTYPE_DESC_AMY;
        assertCommandFailure(command, DateOfBirth.MESSAGE_DOB_CONSTRAINTS);

        /* Case: invalid blood type -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + NRIC_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + DOB_DESC_AMY + INVALID_BLOODTYPE_DESC;
        assertCommandFailure(command, BloodType.MESSAGE_BLOODTYPE_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + NRIC_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + DOB_DESC_AMY + BLOODTYPE_DESC_AMY
                + INVALID_TAG_DESC;
        assertCommandFailure(command, Tag.MESSAGE_TAG_CONSTRAINTS);
    }

    /**
     * Executes the {@code AddCommand} that adds {@code toAdd} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code AddCommand} with the details of
     * {@code toAdd}.<br>
     * 4. {@code Model}, {@code Storage} and {@code PatientListPanel} equal to the corresponding components in
     * the current model added with {@code toAdd}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code ImdbSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see ImdbSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(Patient toAdd) {
        assertCommandSuccess(PatientUtil.getAddCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(Patient)}. Executes {@code command}
     * instead.
     * @see AddCommandSystemTest#assertCommandSuccess(Patient)
     */
    private void assertCommandSuccess(String command, Patient toAdd) {
        Model expectedModel = getModel();
        try {
            expectedModel.addPerson(toAdd);
        } catch (DuplicatePatientException dpe) {
            throw new IllegalArgumentException("toAdd already exists in the model.");
        }
        String expectedResultMessage = String.format(AddCommand.MESSAGE_SUCCESS, toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Patient)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Model}, {@code Storage} and {@code PatientListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see AddCommandSystemTest#assertCommandSuccess(String, Patient)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxAndResultDisplayShowsDefaultStyle();
        assertStatusBarChangedExceptSaveLocation();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Model}, {@code Storage} and {@code PatientListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code ImdbSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see ImdbSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxAndResultDisplayShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
