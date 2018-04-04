package seedu.organizer.ui;

import static org.junit.Assert.assertEquals;
import static seedu.organizer.testutil.TypicalExecutedCommands.getTypicalExecutedCommands;
import static seedu.organizer.testutil.TypicalTasks.getTypicalTasks;

import java.time.YearMonth;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.CalendarPanelHandle;
import guitests.guihandles.MonthViewHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import seedu.organizer.model.task.Task;

//@@author guekling
public class CalendarPanelTest extends GuiUnitTest {
    private static final ObservableList<Task> TYPICAL_TASKS = FXCollections.observableList(getTypicalTasks());
    private static final ObservableList<String> TYPICAL_EXECUTED_COMMANDS = FXCollections.observableList
        (getTypicalExecutedCommands());

    private static final int SUNDAY = 7;
    private static final int FIRST_ROW = 0;
    private static final int MAX_NUM_OF_DAYS = 35;
    private static final double DAYS_IN_WEEK = 7.0;

    private CalendarPanel calendarPanel;
    private CalendarPanelHandle calendarPanelHandle;
    private MonthViewHandle monthViewHandle;
    private YearMonth currentYearMonth;

    @Before
    public void setUp() {
        calendarPanel = new CalendarPanel(TYPICAL_TASKS, TYPICAL_EXECUTED_COMMANDS);
        uiPartRule.setUiPart(calendarPanel);

        calendarPanelHandle = new CalendarPanelHandle(calendarPanel.getRoot());
        monthViewHandle = new MonthViewHandle(calendarPanel.getMonthView().getRoot());

        currentYearMonth = YearMonth.now();
    }

    @Test
    public void display() {
        // verify that calendar title is displayed correctly
        monthViewHandle.getCalendarTitleText();
        String expectedTitle = currentYearMonth.getMonth().toString() + " " + currentYearMonth.getYear();
        assertEquals(expectedTitle, monthViewHandle.getCalendarTitleText());

        // verify that the first date of the month is displayed in the correct row and column
        Node startDateNode = monthViewHandle.getPrintedDateNode(1);
        int startDateRow = monthViewHandle.getRowIndex(startDateNode);
        int startDateColumn = monthViewHandle.getColumnIndex(startDateNode);
        int expectedStartDateColumn = getExpectedDateColumn(currentYearMonth, 1);

        assertEquals(FIRST_ROW, startDateRow);
        assertEquals(expectedStartDateColumn, startDateColumn);

        // verify that the last date of the month is displayed in the correct row and column
        int lastDate = currentYearMonth.lengthOfMonth();
        Node lastDateNode = monthViewHandle.getPrintedDateNode(lastDate);
        int lastDateRow = monthViewHandle.getRowIndex(lastDateNode);
        int lastDateColumn = monthViewHandle.getColumnIndex(lastDateNode);
        int expectedLastDateColumn = getExpectedDateColumn(currentYearMonth, lastDate);
        int expectedLastDateRow = getExpectedDateRow(currentYearMonth, lastDate);

        assertEquals(expectedLastDateColumn, lastDateColumn);
        assertEquals(expectedLastDateRow, lastDateRow);
    }

    /**
     * Retrieves the expected column index of a {@code date}.
     */
    private int getExpectedDateColumn(YearMonth yearMonth, int date) {
        int expectedDateColumn = yearMonth.atDay(date).getDayOfWeek().getValue();

        if (expectedDateColumn == SUNDAY) {
            expectedDateColumn = 0;
        }

        return expectedDateColumn;
    }

    /**
     * Retrieves the expected row index of a {@code date}.
     */
    private int getExpectedDateRow(YearMonth yearMonth, int date) {
        int startDay = yearMonth.atDay(1).getDayOfWeek().getValue();

        if (startDay == SUNDAY) {
            startDay = 0;
        }

        int totalDays = startDay + date;

        if (totalDays <= MAX_NUM_OF_DAYS) {
            return (int) (date / DAYS_IN_WEEK);
        } else {
            return FIRST_ROW;
        }
    }
}
