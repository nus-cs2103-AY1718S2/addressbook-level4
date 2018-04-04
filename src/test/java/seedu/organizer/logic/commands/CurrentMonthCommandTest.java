package seedu.organizer.logic.commands;

import static seedu.organizer.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.organizer.testutil.TypicalExecutedCommands.CURRENT_MONTH_COMMAND_ALIAS;
import static seedu.organizer.testutil.TypicalExecutedCommands.CURRENT_MONTH_COMMAND_WORD;
import static seedu.organizer.testutil.TypicalExecutedCommands.getTypicalExecutedCommands;
import static seedu.organizer.testutil.TypicalTasks.getTypicalTasks;

import java.time.YearMonth;

import org.junit.Before;
import org.junit.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.organizer.model.task.Task;
import seedu.organizer.ui.GuiUnitTest;
import seedu.organizer.ui.calendar.MonthView;

//@@author guekling
/**
 * Contains integration tests (interaction with the UI) and unit tests for CurrentMonthCommand.
 */
public class CurrentMonthCommandTest extends GuiUnitTest {
    private static final ObservableList<Task> TYPICAL_TASKS = FXCollections.observableList(getTypicalTasks());
    private static final ObservableList<String> TO_BE_UPDATED_TYPICAL_EXECUTED_COMMANDS = FXCollections.observableList
            (getTypicalExecutedCommands());
    private static final ObservableList<String> TYPICAL_EXECUTED_COMMANDS = FXCollections.observableList
            (getTypicalExecutedCommands());

    private static final YearMonth currentYearMonth = YearMonth.now();

    private MonthView monthView;
    private MonthView expectedMonthView;
    private CurrentMonthCommand currentMonthCommand;

    @Before
    public void setUp() {
        monthView = new MonthView(TYPICAL_TASKS, TO_BE_UPDATED_TYPICAL_EXECUTED_COMMANDS);
        expectedMonthView = new MonthView(TYPICAL_TASKS, TYPICAL_EXECUTED_COMMANDS);

        currentMonthCommand = new CurrentMonthCommand();
    }

    @Test
    public void execute_commandWord() {
        addCommandToExecutedCommandsList(CURRENT_MONTH_COMMAND_WORD);
        com.sun.javafx.application.PlatformImpl.startup(()->{}); // initialising JavaFX toolkit explicitly
        expectedMonthView.getMonthView(currentYearMonth);
        guiRobot.pause();
        assertCommandSuccess(currentMonthCommand, monthView, currentMonthCommand.MESSAGE_SUCCESS, expectedMonthView);
    }

    @Test
    public void execute_commandAlias() {
        addCommandToExecutedCommandsList(CURRENT_MONTH_COMMAND_ALIAS);
        com.sun.javafx.application.PlatformImpl.startup(()->{}); // initialising JavaFX toolkit explicitly
        expectedMonthView.getMonthView(currentYearMonth);
        guiRobot.pause();
        assertCommandSuccess(currentMonthCommand, monthView, currentMonthCommand.MESSAGE_SUCCESS, expectedMonthView);
    }

    /**
     * Adds a new {@code command} to the {@code TO_BE_UPDATED_TYPICAL_EXECUTED_COMMANDS} observable list.
     */
    private void addCommandToExecutedCommandsList(String command) {
        TO_BE_UPDATED_TYPICAL_EXECUTED_COMMANDS.add(command);
    }
}
