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

//@@author AzuraAiR
/**
 * A system test class for the help window, which contains interaction with other UI components.
 */
public class BirthdaysCommandSystemTest extends AddressBookSystemTest {
    private static final String ERROR_MESSAGE = "ATTENTION!!!! : On some computers, this test may fail when run on "
            + "non-headless mode as FxRobot#clickOn(Node, MouseButton...) clicks on the wrong location. We suspect "
            + "that this is a bug with TestFX library that we are using. If this test fails, you have to run your "
            + "tests on headless mode. See UsingGradle.adoc on how to do so.";

    // Stub from Typical Persons
    private static final String expectedResultStub = "1/1/1995 Alice Pauline\n"
            + "2/1/1989 Benson Meier\n"
            + "3/1/1991 Carl Kurz\n"
            + "6/1/1994 Fiona Kunz\n"
            + "4/2/1991 Daniel Meier\n"
            + "5/3/1991 Elle Meyer\n"
            + "7/10/1995 George Best\n";

    private final GuiRobot guiRobot = new GuiRobot();

    @Test
    public void openBirthdayList() {
        //use command box
        executeCommand(BirthdaysCommand.COMMAND_WORD);
        guiRobot.pauseForHuman();
        assertEquals(expectedResultStub, getBirthdayList().getText());
    }

    @Test
    public void assertBirthdayListWithOnePersonToday() {
        // Simulation of commands to create only one person whose birthday is today
        deleteAllPersons();
        executeCommand("   " + AddCommand.COMMAND_WORD + "  " + NAME_DESC_AMY + "  " + PHONE_DESC_AMY + " "
                + EMAIL_DESC_AMY + "   " + ADDRESS_DESC_AMY + "   b/"
                + buildBirthday(true) + " " + TIMETABLE_DESC_AMY + TAG_DESC_FRIEND + " ");

        // Create expected result


        // use command
        executeCommand(BirthdaysCommand.COMMAND_WORD + " " + BirthdaysCommand.ADDITIONAL_COMMAND_PARAMETER);
        guiRobot.waitForEvent(() -> guiRobot.isWindowShown("Birthdays Today"));

        BirthdayNotificationHandle alertDialog = new BirthdayNotificationHandle(guiRobot
                .getStage("Birthdays Today"));

        assertEquals(buildExpectedBirthday(), alertDialog.getContentText());
    }

    @Test
    public void assertBirthdayListWithZeroPersonToday() {
        // Simulation of commands to create only one person whose birthday is today
        deleteAllPersons();
        executeCommand("   " + AddCommand.COMMAND_WORD + "  " + NAME_DESC_AMY + "  " + PHONE_DESC_AMY + " "
                + EMAIL_DESC_AMY + "   " + ADDRESS_DESC_AMY + "   b/"
                + buildBirthday(false) + " " + TIMETABLE_DESC_AMY + " " + TAG_DESC_FRIEND + " ");

        // use command
        executeCommand(BirthdaysCommand.COMMAND_WORD + " " + BirthdaysCommand.ADDITIONAL_COMMAND_PARAMETER);
        guiRobot.waitForEvent(() -> guiRobot.isWindowShown("Birthdays Today"));

        BirthdayNotificationHandle alertDialog = new BirthdayNotificationHandle(guiRobot
                .getStage("Birthdays Today"));

        assertEquals("", alertDialog.getContentText());
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

}
