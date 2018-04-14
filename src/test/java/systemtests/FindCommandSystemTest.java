package systemtests;

import static org.junit.Assert.assertFalse;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.commons.core.Messages.MESSAGE_PET_PATIENTS_LISTED_OVERVIEW;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.BLOODTYPE_DESC_NERO;
import static seedu.address.logic.commands.CommandTestUtil.BREED_DESC_NERO;
import static seedu.address.logic.commands.CommandTestUtil.COLOUR_DESC_NERO;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_NERO;
import static seedu.address.logic.commands.CommandTestUtil.NRIC_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.OPTION_OWNER;
import static seedu.address.logic.commands.CommandTestUtil.OPTION_PET;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.SPECIES_DESC_NERO;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FIV;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.KEYWORD_MATCHING_MEIER;
import static seedu.address.testutil.TypicalPersons.NRIC_KEYWORD_MATCHING_MEIER;
import static seedu.address.testutil.TypicalPersons.OWES_MONEY_TAG;
import static seedu.address.testutil.TypicalPetPatients.NERO;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;

public class FindCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void findPerson() {
        /* Case: find multiple persons in address book, command with leading spaces and trailing spaces
         * -> 2 persons found
         */
        String command = "   " + FindCommand.COMMAND_WORD + " -o n/" + KEYWORD_MATCHING_MEIER + "   ";
        Model expectedModel = getModel();
        ModelHelper.setFilteredPersonList(expectedModel, BENSON, DANIEL); // first names of Benson & Daniel are "Meier"
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous find command where person list is displaying the persons we are finding
         * -> 2 persons found
         */
        command = FindCommand.COMMAND_WORD + " -o n/" + KEYWORD_MATCHING_MEIER;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person where person list is not displaying the person we are finding -> 1 person found */
        command = FindCommand.COMMAND_WORD + " -o n/Carl";
        ModelHelper.setFilteredPersonList(expectedModel, CARL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in address book, 2 keywords -> 2 persons found */
        command = FindCommand.COMMAND_WORD + " -o n/Benson Daniel";
        ModelHelper.setFilteredPersonList(expectedModel, BENSON, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in address book, 2 keywords in reversed order -> 2 persons found */
        command = FindCommand.COMMAND_WORD + " -o n/Daniel Benson";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in address book, 2 keywords with 1 repeat -> 2 persons found */
        command = FindCommand.COMMAND_WORD + " -o n/Daniel Benson Daniel";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in address book, 2 matching keywords and 1 non-matching keyword
         * -> 2 persons found
         */
        command = FindCommand.COMMAND_WORD + " -o n/Daniel Benson NonMatchingKeyWord";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: undo previous find command -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo previous find command -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: find same persons in address book after deleting 1 of them -> 1 person found */
        executeCommand(DeleteCommand.COMMAND_WORD + " -fo 1");
        assertFalse(getModel().getAddressBook().getPersonList().contains(BENSON));
        command = FindCommand.COMMAND_WORD + " -o n/" + KEYWORD_MATCHING_MEIER;
        expectedModel = getModel();
        ModelHelper.setFilteredPersonList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person in address book, keyword is same as name but of different case -> 1 person found */
        command = FindCommand.COMMAND_WORD + " -o n/MeIeR";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person in address book, keyword is substring of name -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " -o n/Mei";
        ModelHelper.setFilteredPersonList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person in address book, name is substring of keyword -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " -o n/Meiers";
        ModelHelper.setFilteredPersonList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person not in address book -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " -o n/Mark";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find phone number of person in address book -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " -o n/" + DANIEL.getPhone().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find address of person in address book -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " -o n/" + DANIEL.getAddress().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find tags of person in address book -> 0 persons found */
        List<Tag> tags = new ArrayList<>(DANIEL.getTags());
        command = FindCommand.COMMAND_WORD + " -o n/" + tags.get(0).tagName;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find while a person is selected -> selected card deselected */
        showAllPersons();
        selectPerson(Index.fromOneBased(1));
        assertFalse(getPersonListPanel().getHandleToSelectedCard().getName().equals(DANIEL.getName().fullName));
        command = FindCommand.COMMAND_WORD + " -o n/Daniel";
        ModelHelper.setFilteredPersonList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardDeselected();

        /* Case: find person in empty address book -> 0 persons found */
        deleteAllPersons();
        command = FindCommand.COMMAND_WORD + " -o n/" + KEYWORD_MATCHING_MEIER;
        expectedModel = getModel();
        ModelHelper.setFilteredPersonList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: mixed case command word -> rejected */
        command = "FiNd -o n/Meier";
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);
    }

    //@@author wynonaK
    @Test
    public void findNric() {
        /* Case: find multiple persons by nric in address book, command with leading spaces and trailing spaces
         * -> 2 persons found
         */
        String command = "   " + FindCommand.COMMAND_WORD + " -o nr/" + NRIC_KEYWORD_MATCHING_MEIER + "   ";
        Model expectedModel = getModel();
        ModelHelper.setFilteredPersonList(expectedModel, BENSON, DANIEL); // first names of Benson & Daniel are "Meier"
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous find command where person list is displaying the persons we are finding
         * -> 2 persons found
         */
        command = FindCommand.COMMAND_WORD + " -o nr/" + NRIC_KEYWORD_MATCHING_MEIER;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find nric where person list is not displaying the person we are finding -> 1 person found */
        command = FindCommand.COMMAND_WORD + " -o nr/F2345678U";
        ModelHelper.setFilteredPersonList(expectedModel, CARL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple nric in address book, 2 keywords -> 2 persons found */
        command = FindCommand.COMMAND_WORD + " -o nr/S0123456Q T0123456L";
        ModelHelper.setFilteredPersonList(expectedModel, BENSON, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple nric in address book, 2 keywords in reversed order -> 2 persons found */
        command = FindCommand.COMMAND_WORD + " -o nr/T0123456L S0123456Q";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple nric in address book, 2 keywords with 1 repeat -> 2 persons found */
        command = FindCommand.COMMAND_WORD + " -o nr/T0123456L S0123456Q T0123456L";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple nric in address book, 2 matching keywords and 1 non-matching keyword
         * -> 2 persons found
         */
        command = FindCommand.COMMAND_WORD + " -o nr/S0123456Q T0123456L S9012389E";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find same persons in address book after deleting 1 of them -> 1 person found */
        executeCommand(DeleteCommand.COMMAND_WORD + " -fo 1");
        assertFalse(getModel().getAddressBook().getPersonList().contains(BENSON));
        command = FindCommand.COMMAND_WORD + " -o nr/" + NRIC_KEYWORD_MATCHING_MEIER;
        expectedModel = getModel();
        ModelHelper.setFilteredPersonList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find nric not in address book -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " -o nr/T0014852E";
        ModelHelper.setFilteredPersonList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();
    }

    @Test
    public void findPersonTag() {
        /* Case: find persons with owemoney tag in address book, command with leading spaces and trailing spaces
         * -> 1 person found
         */
        String command = "   " + FindCommand.COMMAND_WORD + " -o t/" + OWES_MONEY_TAG + "   ";
        Model expectedModel = getModel();
        ModelHelper.setFilteredPersonList(expectedModel, BENSON);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous find command where person list is displaying the persons we are finding
         * -> 1 persons found
         */

        command = FindCommand.COMMAND_WORD + " -o t/" + OWES_MONEY_TAG;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person where person list changes*/
        command = FindCommand.COMMAND_WORD + " -o t/friends";
        ModelHelper.setFilteredPersonList(expectedModel, ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in address book, 2 keywords -> 7 persons found */
        command = FindCommand.COMMAND_WORD + " -o t/friends owesMoney";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in address book, 2 keywords in reversed order -> 7 persons found */
        command = FindCommand.COMMAND_WORD + " -o t/owesMoney friends";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in address book, 2 keywords with 1 repeat -> 7 persons found */
        command = FindCommand.COMMAND_WORD + " -o t/owesMoney friends owesMoney";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in address book, 2 matching keywords and 1 non-matching keyword
         * -> 2 persons found
         */
        command = FindCommand.COMMAND_WORD + " -o t/owesMoney friends NonMatchingKeyWord";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person in address book, keyword is same as name but of different case -> 1 person found */
        command = FindCommand.COMMAND_WORD + " -o t/OwEsMoNey fRiEnDs";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: undo previous find command -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo previous find command -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: find person in address book, keyword is substring of tag -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " -o t/OWE";
        ModelHelper.setFilteredPersonList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person in address book, tag is substring of keyword -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " -o t/owesmoneys";
        ModelHelper.setFilteredPersonList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person not in address book -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " -o t/Chicken";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find phone number of person in address book -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " -o t/" + DANIEL.getPhone().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find address of person in address book -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " -o t/" + DANIEL.getAddress().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person in empty address book -> 0 persons found */
        deleteAllPersons();
        command = FindCommand.COMMAND_WORD + " -o t/friends";
        expectedModel = getModel();
        ModelHelper.setFilteredPersonList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: mixed case command word -> rejected */
        command = "FiNd -o t/friends";
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void findPet() {
        String command = AddCommand.COMMAND_WORD + " " + OPTION_OWNER + TAG_DESC_FRIEND
                + PHONE_DESC_BOB + ADDRESS_DESC_BOB
                + NAME_DESC_BOB + NRIC_DESC_BOB + TAG_DESC_HUSBAND + EMAIL_DESC_BOB;
        executeCommand(command);

        command = AddCommand.COMMAND_WORD + "  " + OPTION_PET + "  " + NAME_DESC_NERO
                + "  " +  SPECIES_DESC_NERO + "  " + BREED_DESC_NERO + "  " +  COLOUR_DESC_NERO + "  "
                + BLOODTYPE_DESC_NERO + "  " + TAG_DESC_FIV + " " + OPTION_OWNER + "  " + NRIC_DESC_BOB + "  ";
        executeCommand(command);

        /* Case: find pet name with  tag in address book, command with leading spaces and trailing spaces
         * -> 1 person found, 1 pet found
         */
        command = "   " + FindCommand.COMMAND_WORD + " -p " + NAME_DESC_NERO + "   ";
        Model expectedModel = getModel();
        ModelHelper.setFilteredPersonList(expectedModel, BOB);
        ModelHelper.setFilteredPetPatientList(expectedModel, NERO);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous find command where person list is displaying the persons we are finding
         * -> 1 persons found, 1 pet found
         */
        command = FindCommand.COMMAND_WORD + " -p " + NAME_DESC_NERO;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find name not in address book -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " -p n/NEerrreo";
        ModelHelper.setFilteredPersonList(expectedModel);
        ModelHelper.setFilteredPetPatientList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find pet name with  tag in address book, command with leading spaces and trailing spaces
         * -> 1 person found, 1 pet found
         */
        command = "   " + FindCommand.COMMAND_WORD + " -p " + SPECIES_DESC_NERO + "   ";
        ModelHelper.setFilteredPersonList(expectedModel, BOB);
        ModelHelper.setFilteredPetPatientList(expectedModel, NERO);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous find command where person list is displaying the persons we are finding
         * -> 1 persons found, 1 pet found
         */
        command = FindCommand.COMMAND_WORD + " -p " + SPECIES_DESC_NERO;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find species not in address book -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " -p s/Doggy";
        ModelHelper.setFilteredPersonList(expectedModel);
        ModelHelper.setFilteredPetPatientList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find pet name with  tag in address book, command with leading spaces and trailing spaces
         * -> 1 person found, 1 pet found
         */
        command = "   " + FindCommand.COMMAND_WORD + " -p " + BREED_DESC_NERO + "   ";
        ModelHelper.setFilteredPersonList(expectedModel, BOB);
        ModelHelper.setFilteredPetPatientList(expectedModel, NERO);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous find command where person list is displaying the persons we are finding
         * -> 1 persons found, 1 pet found
         */
        command = FindCommand.COMMAND_WORD + " -p " + BREED_DESC_NERO;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find breed not in address book -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " -p b/breedx";
        ModelHelper.setFilteredPersonList(expectedModel);
        ModelHelper.setFilteredPetPatientList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find pet name with  tag in address book, command with leading spaces and trailing spaces
         * -> 1 person found, 1 pet found
         */
        command = "   " + FindCommand.COMMAND_WORD + " -p " + COLOUR_DESC_NERO + "   ";
        ModelHelper.setFilteredPersonList(expectedModel, BOB);
        ModelHelper.setFilteredPetPatientList(expectedModel, NERO);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous find command where person list is displaying the persons we are finding
         * -> 1 persons found, 1 pet found
         */
        command = FindCommand.COMMAND_WORD + " -p " + COLOUR_DESC_NERO;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find colour not in address book -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " -p c/Purple";
        ModelHelper.setFilteredPersonList(expectedModel);
        ModelHelper.setFilteredPetPatientList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find pet name with  tag in address book, command with leading spaces and trailing spaces
         * -> 1 person found, 1 pet found
         */
        command = "   " + FindCommand.COMMAND_WORD + " -p " + BLOODTYPE_DESC_NERO + "   ";
        ModelHelper.setFilteredPersonList(expectedModel, BOB);
        ModelHelper.setFilteredPetPatientList(expectedModel, NERO);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous find command where person list is displaying the persons we are finding
         * -> 1 persons found, 1 pet found
         */
        command = FindCommand.COMMAND_WORD + " -p " + BLOODTYPE_DESC_NERO;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find blood type not in address book -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " -p bt/O";
        ModelHelper.setFilteredPersonList(expectedModel);
        ModelHelper.setFilteredPetPatientList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find pet name with  tag in address book, command with leading spaces and trailing spaces
         * -> 1 person found, 1 pet found
         */
        command = "   " + FindCommand.COMMAND_WORD + " -p " + TAG_DESC_FIV + "   ";
        ModelHelper.setFilteredPersonList(expectedModel, BOB);
        ModelHelper.setFilteredPetPatientList(expectedModel, NERO);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous find command where person list is displaying the persons we are finding
         * -> 1 persons found, 1 pet found
         */
        command = FindCommand.COMMAND_WORD + " -p " + TAG_DESC_FIV;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find tag not in address book -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " -p t/owner";
        ModelHelper.setFilteredPersonList(expectedModel);
        ModelHelper.setFilteredPetPatientList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();
    }



    //@@author
    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_PERSONS_LISTED_OVERVIEW} with the number of people in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        String expectedResultMessage = String.format(
                MESSAGE_PERSONS_LISTED_OVERVIEW, expectedModel.getFilteredPersonList().size())
                + "\n"
                + String.format(MESSAGE_PET_PATIENTS_LISTED_OVERVIEW, expectedModel.getFilteredPetPatientList().size());

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
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
