package systemtests;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;

import java.time.LocalDate;

import org.junit.Test;

import guitests.GuiRobot;
import guitests.guihandles.BirthdayNotificationHandle;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.BirthdaysCommand;

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
    public void assertTodayBirthdayListWithOnePerson() {
        LocalDate currentDate = LocalDate.now();
        int currentDay = currentDate.getDayOfMonth();
        int currentMonth = currentDate.getMonthValue();
        int currentYear = currentDate.getYear();
        StringBuilder string = new StringBuilder();

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

        // Simulation of commands to create only one person with the birthday
        deleteAllPersons();
        executeCommand("   " + AddCommand.COMMAND_WORD + "  " + NAME_DESC_AMY + "  " + PHONE_DESC_AMY + " "
                + EMAIL_DESC_AMY + "   " + ADDRESS_DESC_AMY + "   b/"
                + string.toString() + " " + TAG_DESC_FRIEND + " ");

        // Create expected result
        string = new StringBuilder();
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

        // use command
        executeCommand(BirthdaysCommand.COMMAND_WORD + " " + BirthdaysCommand.ADDITIONAL_COMMAND_PARAMETER);
        guiRobot.waitForEvent(() -> guiRobot.isWindowShown("Birthdays Today"));

        BirthdayNotificationHandle alertDialog = new BirthdayNotificationHandle(guiRobot
                .getStage("Birthdays Today"));

        assertEquals(string.toString(), alertDialog.getContentText());
    }

    @Test
    public void assertTodaysBirthdayListWithZeroPerson() {
        LocalDate currentDate = LocalDate.now();
        int currentDay = currentDate.getDayOfMonth() - 1;
        if (currentDate.getDayOfMonth() == 1) {
            currentDay = currentDate.getDayOfMonth() + 1;
        }
        int currentMonth = currentDate.getMonthValue();
        int currentYear = currentDate.getYear();
        StringBuilder string = new StringBuilder();

        // Creation of birthday to fit today + additional one day to differ birthdays
        if (currentDay <= 9) {
            string.append(0);
        }
        string.append(currentDay);
        if (currentMonth <= 9) {
            string.append(0);
        }
        string.append(currentMonth);
        string.append("1995");

        // Simulation of commands to create only one person with the birthday
        deleteAllPersons();
        executeCommand("   " + AddCommand.COMMAND_WORD + "  " + NAME_DESC_AMY + "  " + PHONE_DESC_AMY + " "
                + EMAIL_DESC_AMY + "   " + ADDRESS_DESC_AMY + "   b/"
                + string.toString() + " " + TAG_DESC_FRIEND + " ");

        // use command
        executeCommand(BirthdaysCommand.COMMAND_WORD + " " + BirthdaysCommand.ADDITIONAL_COMMAND_PARAMETER);
        guiRobot.waitForEvent(() -> guiRobot.isWindowShown("Birthdays Today"));

        BirthdayNotificationHandle alertDialog = new BirthdayNotificationHandle(guiRobot
                .getStage("Birthdays Today"));

        assertEquals("", alertDialog.getContentText());
    }

}
