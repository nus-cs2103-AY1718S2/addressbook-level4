package systemtests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.ListAppointmentCommand;
import seedu.address.model.Model;

//@@author wynonaK
public class ListAppointmentCommandSystemTest extends AddressBookSystemTest {
    @Test
    public void listAppointment() {
        /* --------------------------------- Perform listappt command success --------------------------------------- */
        //year parameters given properly
        assertCommandSuccess(ListAppointmentCommand.COMMAND_WORD + " -y 2018 ",
                String.format(ListAppointmentCommand.MESSAGE_SUCCESS, "year"));

        //no year given, passes with today's year
        assertCommandSuccess(ListAppointmentCommand.COMMAND_WORD + " -y ",
                String.format(ListAppointmentCommand.MESSAGE_SUCCESS, "year"));

        //month parameters given properly
        assertCommandSuccess(ListAppointmentCommand.COMMAND_WORD + " -m 2018-12",
                String.format(ListAppointmentCommand.MESSAGE_SUCCESS, "month"));

        //month given but no year, passes with today's year
        assertCommandSuccess(ListAppointmentCommand.COMMAND_WORD + " -m 12",
                String.format(ListAppointmentCommand.MESSAGE_SUCCESS, "month"));

        //no month given, passes with today's month
        assertCommandSuccess(ListAppointmentCommand.COMMAND_WORD + " -m ",
                String.format(ListAppointmentCommand.MESSAGE_SUCCESS, "month"));

        //week parameters given properly
        assertCommandSuccess(ListAppointmentCommand.COMMAND_WORD + " -w 2018-12-31 ",
                String.format(ListAppointmentCommand.MESSAGE_SUCCESS, "week"));

        //no week given, passes with today's week
        assertCommandSuccess(ListAppointmentCommand.COMMAND_WORD + " -w ",
                String.format(ListAppointmentCommand.MESSAGE_SUCCESS, "week"));

        //date parameters given properly
        assertCommandSuccess(ListAppointmentCommand.COMMAND_WORD + " -d 2018-12-31 ",
                String.format(ListAppointmentCommand.MESSAGE_SUCCESS, "day"));

        //no date given, passes with today's date
        assertCommandSuccess(ListAppointmentCommand.COMMAND_WORD + " -d ",
                String.format(ListAppointmentCommand.MESSAGE_SUCCESS, "day"));

        /* --------------------------------- Perform listappt command failures -------------------------------------- */

        //null, fail
        assertCommandFailure(ListAppointmentCommand.COMMAND_WORD + " ",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ListAppointmentCommand.MESSAGE_USAGE));

        //writing wrong caps, fail
        assertCommandFailure("LiStApPt",
                String.format(Messages.MESSAGE_UNKNOWN_COMMAND));

        //unknown parameter, fail
        assertCommandFailure(ListAppointmentCommand.COMMAND_WORD + " -opaenuf ",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ListAppointmentCommand.MESSAGE_USAGE));

        //unknown year parameter, fail
        assertCommandFailure(ListAppointmentCommand.COMMAND_WORD + " -y naodnnn ",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ListAppointmentCommand.MESSAGE_USAGE));

        //extra year parameter, fail
        assertCommandFailure(ListAppointmentCommand.COMMAND_WORD + " -y 2018 3noisefn ",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ListAppointmentCommand.MESSAGE_USAGE));

        //unknown year-month parameter, fail
        assertCommandFailure(ListAppointmentCommand.COMMAND_WORD + " -m ajebfdliua ",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ListAppointmentCommand.MESSAGE_USAGE));

        //extra month parameter, fail
        assertCommandFailure(ListAppointmentCommand.COMMAND_WORD + " -m 12 3noisefn ",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ListAppointmentCommand.MESSAGE_USAGE));

        //invalid month parameter, fail
        assertCommandFailure(ListAppointmentCommand.COMMAND_WORD + " -m 60 ",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ListAppointmentCommand.MESSAGE_USAGE));

        //unknown week parameter, fail
        assertCommandFailure(ListAppointmentCommand.COMMAND_WORD + " -w opaenuf ",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ListAppointmentCommand.MESSAGE_USAGE));

        //extra week parameter, fail
        assertCommandFailure(ListAppointmentCommand.COMMAND_WORD + " -w 2018-12-31 3noisefn ",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ListAppointmentCommand.MESSAGE_USAGE));

        //unknown day parameter, fail
        assertCommandFailure(ListAppointmentCommand.COMMAND_WORD + " -d opuf ",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ListAppointmentCommand.MESSAGE_USAGE));

        //extra day parameter, fail
        assertCommandFailure(ListAppointmentCommand.COMMAND_WORD + " -d 2018-12-31 3noisefn ",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ListAppointmentCommand.MESSAGE_USAGE));
    }

    /**
     * Performs verification for command to calendarView
     */
    private void assertCommandSuccess(String command, String message) {
        executeCommand(command);
        assertEquals(getResultDisplay().getText() , message);
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Model}, {@code Storage} and {@code PersonListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
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
