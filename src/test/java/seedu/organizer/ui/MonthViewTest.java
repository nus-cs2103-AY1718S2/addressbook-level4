package seedu.organizer.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.organizer.testutil.TypicalExecutedCommands.PREVIOUS_MONTH_COMMAND_ALIAS;
import static seedu.organizer.testutil.TypicalExecutedCommands.PREVIOUS_MONTH_COMMAND_WORD;
import static seedu.organizer.testutil.TypicalExecutedCommands.getTypicalExecutedCommands;
import static seedu.organizer.testutil.TypicalTasks.PREPAREBREAKFAST;
import static seedu.organizer.testutil.TypicalTasks.getTypicalTasks;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.MonthViewHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import seedu.organizer.model.task.Task;
import seedu.organizer.testutil.TaskBuilder;
import seedu.organizer.ui.calendar.EntryCard;
import seedu.organizer.ui.calendar.MonthView;

//@@author guekling
public class MonthViewTest extends GuiUnitTest {
    private static final ObservableList<Task> TYPICAL_TASKS = FXCollections.observableList(getTypicalTasks());
    private static final ObservableList<String> TYPICAL_EXECUTED_COMMANDS = FXCollections.observableList
        (getTypicalExecutedCommands());

    private static final YearMonth MAY_2018 = YearMonth.of(2018, 5);
    private static final YearMonth DEC_2018 = YearMonth.of(2018, 12);

    private MonthView monthView;
    private MonthViewHandle monthViewHandle;

    @Before
    public void setUp() {
        monthView = new MonthView(TYPICAL_TASKS, TYPICAL_EXECUTED_COMMANDS);
        uiPartRule.setUiPart(monthView);

        monthViewHandle = new MonthViewHandle(monthView.getRoot());
    }

    @Test
    public void display_fiveWeeksCalendar() {
        monthView.getMonthView(MAY_2018);
        guiRobot.pause();

        // verify that calendar title is displayed correctly
        monthViewHandle.getCalendarTitleText();
        String expectedTitle = "MAY 2018";
        assertEquals(expectedTitle, monthViewHandle.getCalendarTitleText());

        // verify that the first date of the month is displayed in the correct row and column
        Node startDateNode = monthViewHandle.getPrintedDateNode(1);
        int startDateRow = monthViewHandle.getRowIndex(startDateNode);
        int startDateColumn = monthViewHandle.getColumnIndex(startDateNode);

        assertEquals(0, startDateRow);
        assertEquals(2, startDateColumn);

        // verify that the last date of the month is displayed in the correct row and column
        Node lastDateNode = monthViewHandle.getPrintedDateNode(31);
        int lastDateRow = monthViewHandle.getRowIndex(lastDateNode);
        int lastDateColumn = monthViewHandle.getColumnIndex(lastDateNode);

        assertEquals(4, lastDateColumn);
        assertEquals(4, lastDateRow);
    }

    @Test
    public void display_sixWeeksCalendar() {
        monthView.getMonthView(DEC_2018);
        guiRobot.pause();

        // verify that calendar title is displayed correctly
        monthViewHandle.getCalendarTitleText();
        String expectedTitle = "DECEMBER 2018";
        assertEquals(expectedTitle, monthViewHandle.getCalendarTitleText());

        // verify that the last date of the month is displayed in the correct row and column
        Node lastDateNode = monthViewHandle.getPrintedDateNode(31);
        int lastDateRow = monthViewHandle.getRowIndex(lastDateNode);
        int lastDateColumn = monthViewHandle.getColumnIndex(lastDateNode);

        assertEquals(1, lastDateColumn);
        assertEquals(0, lastDateRow);
    }

    @Test
    public void showEntries_fiveWeeksCalendar() {
        monthView.getMonthView(MAY_2018);
        guiRobot.pause();

        // one entry
        Task toAddTask = new TaskBuilder().withName("ES2660").withDeadline("2018-05-01").build();
        addTaskToTaskList(toAddTask);
        guiRobot.pause();

        ListView<EntryCard> entriesListView = (ListView) monthViewHandle.getListViewEntriesNode(0, 2);
        EntryCard actualEntryCard = entriesListView.getItems().get(0);
        EntryCard expectedEntryCard = new EntryCard(toAddTask);
        assertEquals(expectedEntryCard, actualEntryCard);

        // multiple entries on different dates
        toAddTask = new TaskBuilder().withName("CS2101").withDeadline("2018-05-18").build();
        addTaskToTaskList(toAddTask);
        guiRobot.pause();

        entriesListView = (ListView) monthViewHandle.getListViewEntriesNode(2, 5);
        actualEntryCard = entriesListView.getItems().get(0);
        expectedEntryCard = new EntryCard(toAddTask);
        assertEquals(expectedEntryCard, actualEntryCard);

        // entry on a Sunday
        toAddTask = new TaskBuilder().withName("GEQ1000").withDeadline("2018-05-20").build();
        addTaskToTaskList(toAddTask);
        guiRobot.pause();

        entriesListView = (ListView) monthViewHandle.getListViewEntriesNode(3, 0);
        actualEntryCard = entriesListView.getItems().get(0);
        expectedEntryCard = new EntryCard(toAddTask);
        assertEquals(expectedEntryCard, actualEntryCard);

        // entries on the same date
        toAddTask = new TaskBuilder().withName("MA1101R").withDeadline("2018-05-18").build();
        addTaskToTaskList(toAddTask);
        guiRobot.pause();

        entriesListView = (ListView) monthViewHandle.getListViewEntriesNode(2, 5);
        actualEntryCard = entriesListView.getItems().get(1);
        expectedEntryCard = new EntryCard(toAddTask);
        assertEquals(expectedEntryCard, actualEntryCard);
    }

    @Test
    public void showEntries_sixWeeksCalendar() {
        monthView.getMonthView(DEC_2018);
        guiRobot.pause();

        Task toAddTask = new TaskBuilder().withName("CS2103T").withDeadline("2018-12-31").build();
        addTaskToTaskList(toAddTask);
        guiRobot.pause();

        ListView<EntryCard> entriesListView = (ListView) monthViewHandle.getListViewEntriesNode(0, 1);
        EntryCard actualEntryCard = entriesListView.getItems().get(0);
        EntryCard expectedEntryCard = new EntryCard(toAddTask);
        assertEquals(expectedEntryCard, actualEntryCard);
    }

    @Test
    public void goToPreviousMonth_commandsSuccessful() {
        monthView.getMonthView(MAY_2018);

        // using command word to go to previous month
        addCommandToExecutedCommandsList(PREVIOUS_MONTH_COMMAND_WORD);

        MonthView expectedMonthView = new MonthView(TYPICAL_TASKS, TYPICAL_EXECUTED_COMMANDS);
        expectedMonthView.getMonthView(YearMonth.of(2018, 4));
        guiRobot.pause();
        monthView.equals(expectedMonthView);

        // using command alias to go to previous month

        addCommandToExecutedCommandsList(PREVIOUS_MONTH_COMMAND_ALIAS);

        expectedMonthView.getMonthView(YearMonth.of(2018, 3));
        guiRobot.pause();
        monthView.equals(expectedMonthView);
    }

    @Test
    public void goToPreviousMonth_titleDatesAndEntriesPrintedSuccessfully() {
        monthView.getMonthView(MAY_2018);

        Task toAddTaskOne = new TaskBuilder().withName("GER1000").withDeadline("2018-04-12").build();
        addTaskToTaskList(toAddTaskOne);

        Task toAddTaskTwo = new TaskBuilder().withName("CS2010").withDeadline("2018-04-25").build();
        addTaskToTaskList(toAddTaskTwo);

        addCommandToExecutedCommandsList(PREVIOUS_MONTH_COMMAND_WORD);
        guiRobot.pause();

        // verify that calendar title is displayed correctly
        monthViewHandle.getCalendarTitleText();
        String expectedTitle = "APRIL 2018";
        assertEquals(expectedTitle, monthViewHandle.getCalendarTitleText());

        // verify that grid lines are visible after clearCalendar() is called
        assertEquals(true, monthViewHandle.isGridLinesVisible());

        // verify that the first date of the month is displayed in the correct row and column
        Node startDateNode = monthViewHandle.getPrintedDateNode(1);
        int startDateRow = monthViewHandle.getRowIndex(startDateNode);
        int startDateColumn = monthViewHandle.getColumnIndex(startDateNode);

        assertEquals(0, startDateRow);
        assertEquals(0, startDateColumn);

        // verify that the last date of the month is displayed in the correct row and column
        Node lastDateNode = monthViewHandle.getPrintedDateNode(30);
        int lastDateRow = monthViewHandle.getRowIndex(lastDateNode);
        int lastDateColumn = monthViewHandle.getColumnIndex(lastDateNode);

        assertEquals(1, lastDateColumn);
        assertEquals(4, lastDateRow);

        // verify that entries are displayed
        ListView<EntryCard> entriesListView = (ListView) monthViewHandle.getListViewEntriesNode(1, 4);
        EntryCard actualEntryCard = entriesListView.getItems().get(0);
        EntryCard expectedEntryCard = new EntryCard(toAddTaskOne);
        assertEquals(expectedEntryCard, actualEntryCard);

        entriesListView = (ListView) monthViewHandle.getListViewEntriesNode(3, 3);
        actualEntryCard = entriesListView.getItems().get(0);
        expectedEntryCard = new EntryCard(toAddTaskTwo);
        assertEquals(expectedEntryCard, actualEntryCard);
    }

    @Test
    public void equals() {
        monthView.getMonthView(MAY_2018);

        // same object -> returns true
        assertTrue(monthView.equals(monthView));

        // same month view -> returns true
        MonthView newMonthView = new MonthView(TYPICAL_TASKS, TYPICAL_EXECUTED_COMMANDS);
        newMonthView.getMonthView(YearMonth.of(2018, 5));
        guiRobot.pause();
        assertTrue(monthView.equals(newMonthView));

        // null -> returns false
        assertFalse(monthView.equals(null));

        // different types -> returns false
        assertFalse(monthView.equals(0));

        // different month view -> returns false
        newMonthView.getMonthView(YearMonth.of(2018, 3));
        guiRobot.pause();
        assertFalse(monthView.equals(newMonthView));

        // different entries -> returns false
        ObservableList<Task> newTaskList = FXCollections.observableList(new ArrayList<>(Arrays.asList(
            PREPAREBREAKFAST)));
        MonthView differentMonthView = new MonthView(newTaskList, TYPICAL_EXECUTED_COMMANDS);
        differentMonthView.getMonthView(MAY_2018);
        guiRobot.pause();
        assertFalse(monthView.equals(differentMonthView));

        // same title but different dates -> returns false
        MonthView otherMonthView = new MonthView(TYPICAL_TASKS, TYPICAL_EXECUTED_COMMANDS);
        otherMonthView.getMonthView(DEC_2018);
        guiRobot.pause();
        newMonthView.setMonthCalendarTitle(2018, "MAY");
        assertFalse(monthView.dateIsEqual(otherMonthView));

    }

    /**
     * Adds a new {@code command} to the {@code TYPICAL_EXECUTED_COMMANDS} observable list.
     */
    private void addCommandToExecutedCommandsList(String command) {
        TYPICAL_EXECUTED_COMMANDS.add(command);
    }

    /**
     * Adds a new {@code task} to the {@code TYPICAL_TASKS} observable list.
     */
    private void addTaskToTaskList(Task task) {
        TYPICAL_TASKS.add(task);
    }
}
