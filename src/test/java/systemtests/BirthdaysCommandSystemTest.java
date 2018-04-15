package systemtests;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TIMETABLE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;

import java.time.LocalDate;

import org.junit.Test;

import guitests.GuiRobot;
import guitests.guihandles.BirthdayNotificationHandle;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.BirthdaysCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;

//@@author AzuraAiR
/**
 * A system test class for the birthdays list and todays notification,
 * which contains interaction with other UI components.
 */
public class BirthdaysCommandSystemTest extends AddressBookSystemTest {
    private static final String ERROR_MESSAGE = "ATTENTION!!!! : On some computers, this test may fail when run on "
            + "non-headless mode as FxRobot#clickOn(Node, MouseButton...) clicks on the wrong location. We suspect "
            + "that this is a bug with TestFX library that we are using. If this test fails, you have to run your "
            + "tests on headless mode. See UsingGradle.adoc on how to do so.";

    // Stub from Typical Persons
    private static final String expectedResult = "1/1/1995 Alice Pauline\n"
            + "2/1/1989 Benson Meier\n"
            + "3/1/1991 Carl Kurz\n"
            + "6/1/1994 Fiona Kunz\n"
            + "4/2/1991 Daniel Meier\n"
            + "5/3/1991 Elle Meyer\n"
            + "7/10/1995 George Best\n";

    private final GuiRobot guiRobot = new GuiRobot();

    @Test
    public void openBirthdayList() {
        /* Case: open birthday list -> success */
        executeCommand(BirthdaysCommand.COMMAND_WORD);
        guiRobot.pauseForHuman();
        assertEquals(expectedResult, getBirthdayList().getText());

        /* Case: undo previous command -> rejected */
        String command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo previous command -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: empty birthday list -> success */
        deleteAllPersonsAndAliases();
        executeCommand(BirthdaysCommand.COMMAND_WORD);
        guiRobot.pauseForHuman();
        assertEquals("", getBirthdayList().getText());
    }


    @Test
    public void assertBirthdayNotificationWithOnePersonToday() {
        // Simulation of commands to create only one person whose birthday is today
        deleteAllPersonsAndAliases();
        executeCommand("   " + AddCommand.COMMAND_WORD + "  " + NAME_DESC_AMY + "  " + PHONE_DESC_AMY + " "
                + EMAIL_DESC_AMY + "   " + ADDRESS_DESC_AMY + "   b/"
                + buildBirthday(true) + " " + TIMETABLE_DESC_AMY + TAG_DESC_FRIEND + " ");

        // use command
        executeCommand(BirthdaysCommand.COMMAND_WORD + " " + BirthdaysCommand.ADDITIONAL_COMMAND_PARAMETER);
        guiRobot.waitForEvent(() -> guiRobot.isWindowShown("Birthdays Today"));

        BirthdayNotificationHandle alertDialog = new BirthdayNotificationHandle(guiRobot
                .getStage("Birthdays Today"));

        assertEquals(buildExpectedBirthday(), alertDialog.getContentText());
    }

    @Test
    public void assertBirthdayNotificationWithZeroPersonToday() {
        // Simulation of commands to create only one person whose birthday is today
        deleteAllPersonsAndAliases();
        executeCommand("   " + AddCommand.COMMAND_WORD + "  " + NAME_DESC_AMY + "  " + PHONE_DESC_AMY + " "
                + EMAIL_DESC_AMY + "   " + ADDRESS_DESC_AMY + "   b/"
                + buildBirthday(false) + " " + TIMETABLE_DESC_AMY + " " + TAG_DESC_FRIEND + " ");

        // use command
        executeCommand(BirthdaysCommand.COMMAND_WORD + " " + BirthdaysCommand.ADDITIONAL_COMMAND_PARAMETER);
        guiRobot.waitForEvent(() -> guiRobot.isWindowShown("Birthdays Today"));

        BirthdayNotificationHandle alertDialog = new BirthdayNotificationHandle(guiRobot
                .getStage("Birthdays Today"));

        assertEquals(BirthdaysCommand.MESSAGE_NO_BIRTHDAY_TODAY, alertDialog.getContentText());
    }

    /**
     * Builds a birthday desc for the add command
     * @param isTodayABirthday if the person is having a person today, her birthday will be set to today
     *                         Otherwise, it will be set +/- 1 day
     * @return String for " b/" portion of AddCommand
     */
    private String buildBirthday(boolean isTodayABirthday) {
        LocalDate currentDate = LocalDate.now();
        int currentDay;
        int currentMonth  = currentDate.getMonthValue();
        StringBuilder string = new StringBuilder();

        if (isTodayABirthday) {
            currentDay = currentDate.getDayOfMonth();
        } else {
            if (currentDate.getDayOfMonth() == 1 || currentDate.getDayOfMonth() < 28) {
                currentDay = currentDate.getDayOfMonth() + 1;
            } else {
                currentDay = currentDate.getDayOfMonth() - 1;
            }
        }

        // Creation of birthday to fit today
        if (currentDay <= 9) {
            string.append(0);
        }
        string.append(currentDay);
        if (currentMonth <= 9) {
            string.append(0);
        }
        string.append(currentMonth);
        string.append("1995");

        return string.toString();
    }

    /**
     * Creates the stub for the testing of Birthdays
     * @return Amy with her current age
     */
    private String buildExpectedBirthday() {
        LocalDate currentDate = LocalDate.now();
        int currentYear = currentDate.getYear();
        StringBuilder string = new StringBuilder();

        int age = currentYear - 1995;
        string.append(VALID_NAME_AMY);
        string.append(" (");
        string.append(age);
        if (age != 1) {
            string.append(" years old)");
        } else if (age > 0) {
            string.append(" years old)");
        }
        string.append("\n");

        return string.toString();
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
