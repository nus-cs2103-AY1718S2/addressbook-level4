package systemtests;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPECTED_GRADUATION_YEAR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADE_POINT_AVERAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INTERVIEW_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RATING;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.KEYWORD_MATCHING_2019;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;

public class FilterCommandSystemTest extends AddressBookSystemTest {
    @Test
    public void filter() {
        //Expected Graduation Year filtering
        /* Case: filters multiple persons in address book, command with leading spaces and trailing spaces
         * -> 3 persons found
         */
        showAllPersons();
        String command = "   " + FilterCommand.COMMAND_WORD + " "
                + PREFIX_EXPECTED_GRADUATION_YEAR + KEYWORD_MATCHING_2019 + "   ";
        Model expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, CARL, FIONA); // their graduation year is before or equal 2019
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous filter command where person list is displaying the persons satisfying the filter
         * -> 2 persons found
         */
        showAllPersons();
        command = FilterCommand.COMMAND_WORD + " " + PREFIX_EXPECTED_GRADUATION_YEAR + KEYWORD_MATCHING_2019;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: filter person twice -> 5 persons found and 2 persons found*/
        showAllPersons();
        command = FilterCommand.COMMAND_WORD + " " + PREFIX_EXPECTED_GRADUATION_YEAR + "2017-2020";
        ModelHelper.setFilteredList(expectedModel, ALICE, CARL, DANIEL, ELLE, FIONA);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();
        command = FilterCommand.COMMAND_WORD + " " + PREFIX_EXPECTED_GRADUATION_YEAR + "2020-2022";
        ModelHelper.setFilteredList(expectedModel, ALICE, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: filter no person in address book, 2017 -> 0 persons found */
        showAllPersons();
        command = FilterCommand.COMMAND_WORD + " " + PREFIX_EXPECTED_GRADUATION_YEAR + "2017";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: filter multiple persons in address book, 2 keywords -> 3 persons found */
        showAllPersons();
        command = FilterCommand.COMMAND_WORD + " " + PREFIX_EXPECTED_GRADUATION_YEAR + "2018" + " "
                + PREFIX_EXPECTED_GRADUATION_YEAR + KEYWORD_MATCHING_2019;
        ModelHelper.setFilteredList(expectedModel, CARL, FIONA); //only last keyword effective
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        //Rating filtering
        /* Case: filters multiple persons in address book, command with leading spaces and trailing spaces
         * -> 2 persons found
         */
        showAllPersons();
        command = "   " + FilterCommand.COMMAND_WORD + " "
                + PREFIX_RATING + "   3.75" + "   ";
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, BENSON); // their graduation year is before or equal 2019
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous filter command where person list is displaying the persons satisfying the filter
         * -> 2 persons found
         */
        command = FilterCommand.COMMAND_WORD + " " + PREFIX_RATING + "3.75";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: filter person twice -> 5 persons found and 2 persons found*/
        showAllPersons();
        command = FilterCommand.COMMAND_WORD + " " + PREFIX_RATING + "2-5";
        ModelHelper.setFilteredList(expectedModel, ALICE, BENSON);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();
        command = FilterCommand.COMMAND_WORD + " " + PREFIX_RATING + "3-5";
        ModelHelper.setFilteredList(expectedModel, BENSON);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: filter no person in address book, 2017 -> 0 persons found */
        showAllPersons();
        command = FilterCommand.COMMAND_WORD + " " + PREFIX_RATING + "1.0";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: filter multiple persons in address book, 2 keywords -> 3 persons found */
        showAllPersons();
        command = FilterCommand.COMMAND_WORD + " " + PREFIX_RATING + "1.0" + " "
                + PREFIX_RATING + "3.75";
        ModelHelper.setFilteredList(expectedModel, BENSON); //only last keyword effective
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        //Mixed filtering - will update more after the larger test sample is generated
        showAllPersons();
        command = "   " + FilterCommand.COMMAND_WORD + " " + PREFIX_RATING + " 1-5" + " "
                + PREFIX_EXPECTED_GRADUATION_YEAR + "2021";
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, BENSON); // their graduation year is before or equal 2019
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        //Grade Point Average filtering
        /* Case: filters multiple persons in address book, command with leading spaces and trailing spaces
         * -> 2 persons found
         */
        showAllPersons();
        command = "   " + FilterCommand.COMMAND_WORD + " "
                + PREFIX_GRADE_POINT_AVERAGE + "   4.84" + "   ";
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, ALICE); // their graduation year is before or equal 2019
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous filter command where person list is displaying the persons satisfying the filter
         * -> 2 persons found
         */
        command = FilterCommand.COMMAND_WORD + " " + PREFIX_GRADE_POINT_AVERAGE + "4.84";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: filter person twice -> 5 persons found and 2 persons found*/
        showAllPersons();
        command = FilterCommand.COMMAND_WORD + " " + PREFIX_GRADE_POINT_AVERAGE + "4.70-4.90";
        ModelHelper.setFilteredList(expectedModel, ALICE, BENSON, FIONA);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();
        command = FilterCommand.COMMAND_WORD + " " + PREFIX_GRADE_POINT_AVERAGE + "3.00-4.80";
        ModelHelper.setFilteredList(expectedModel, BENSON, FIONA);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: filter no person in address book, 2017 -> 0 persons found */
        showAllPersons();
        command = FilterCommand.COMMAND_WORD + " " + PREFIX_GRADE_POINT_AVERAGE + "1.00";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: filter multiple persons in address book, 2 keywords -> 3 persons found */
        showAllPersons();
        command = FilterCommand.COMMAND_WORD + " " + PREFIX_GRADE_POINT_AVERAGE + "1.00" + " "
                + PREFIX_GRADE_POINT_AVERAGE + "4.84";
        ModelHelper.setFilteredList(expectedModel, ALICE); //only last keyword effective
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        //Grade Point Average filtering
        /* Case: filters multiple persons in address book, command with leading spaces and trailing spaces
         * -> 2 persons found
         */
        showAllPersons();
        command = "   " + FilterCommand.COMMAND_WORD + " "
                + PREFIX_INTERVIEW_DATE + "   20180402" + "   ";
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, ALICE); // their graduation year is before or equal 2019
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous filter command where person list is displaying the persons satisfying the filter
         * -> 2 persons found
         */
        command = FilterCommand.COMMAND_WORD + " " + PREFIX_INTERVIEW_DATE + "20180402";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: filter person twice -> 5 persons found and 2 persons found*/
        showAllPersons();
        command = FilterCommand.COMMAND_WORD + " " + PREFIX_INTERVIEW_DATE + "20180402-20180408";
        ModelHelper.setFilteredList(expectedModel, ALICE, BENSON);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();
        command = FilterCommand.COMMAND_WORD + " " + PREFIX_INTERVIEW_DATE + "20180331-20180404";
        ModelHelper.setFilteredList(expectedModel, ALICE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: filter no person in address book, 2017 -> 0 persons found */
        showAllPersons();
        command = FilterCommand.COMMAND_WORD + " " + PREFIX_INTERVIEW_DATE + "19990101";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: filter multiple persons in address book, 2 keywords -> 3 persons found */
        showAllPersons();
        command = FilterCommand.COMMAND_WORD + " " + PREFIX_INTERVIEW_DATE + "20180402" + " "
                + PREFIX_INTERVIEW_DATE + "20180406";
        ModelHelper.setFilteredList(expectedModel, BENSON); //only last keyword effective
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        // general cases
        //setup a filter
        showAllPersons();
        command = FilterCommand.COMMAND_WORD + " " + PREFIX_EXPECTED_GRADUATION_YEAR + "2018" + " "
                + PREFIX_EXPECTED_GRADUATION_YEAR + KEYWORD_MATCHING_2019;
        ModelHelper.setFilteredList(expectedModel, CARL, FIONA); //only last keyword effective
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: undo previous filter command -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo previous filter command -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: filter same persons in address book after deleting 1 of them -> 2 person found */
        executeCommand(DeleteCommand.COMMAND_WORD + " 1");
        assertFalse(getModel().getAddressBook().getPersonList().contains(CARL));
        showAllPersons();
        command = FilterCommand.COMMAND_WORD + " " + PREFIX_EXPECTED_GRADUATION_YEAR + "2017-2019";
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, ELLE, FIONA);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: filter while a person is selected -> selected card deselected */
        showAllPersons();
        selectPerson(Index.fromOneBased(4));
        assertTrue(getPersonListPanel().getHandleToSelectedCard().getName().equals(ELLE.getName().fullName));
        command = FilterCommand.COMMAND_WORD + " " + PREFIX_EXPECTED_GRADUATION_YEAR + "2017-2019";
        ModelHelper.setFilteredList(expectedModel, ELLE, FIONA);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardDeselected();


        /* Case: filter person in empty address book -> 0 persons found */
        deleteAllPersons();
        command = FilterCommand.COMMAND_WORD + " " + PREFIX_EXPECTED_GRADUATION_YEAR + KEYWORD_MATCHING_2019;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: mixed case command word -> rejected */
        command = "fiLteR y/2020";
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);
    }

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
                MESSAGE_PERSONS_LISTED_OVERVIEW, expectedModel.getFilteredPersonList().size());

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
