package seedu.organizer.ui.calendar;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.fxmisc.easybind.EasyBind;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import seedu.organizer.model.task.Task;
import seedu.organizer.ui.UiPart;

//@@author guekling
/**
 * Supports the display of the month view of the calendar.
 */
public class MonthView extends UiPart<Region> {

    private static final String FXML = "MonthView.fxml";

    private static final int MAX_NUM_OF_DAYS = 35;
    private static final int NO_REMAINDER = 0;
    private static final int SUNDAY = 7;
    private static final int MAX_COLUMN = 6;
    private static final int MAX_ROW = 4;

    private int dateCount;
    private String[] datesToBePrinted;
    private ObservableList<Task> taskList;

    @FXML
    private Text calendarTitle;

    @FXML
    private GridPane taskCalendar;

    public MonthView(ObservableList<Task> taskList) {
        super(FXML);

        this.taskList = taskList;
    }

    /**
     * Sets the title of the calendar according to a specific month and year.
     *
     * @param month Full month name.
     * @param year Year represented as a 4-digit integer.
     */
    protected void setMonthCalendarTitle(int year, String month) {
        calendarTitle.setText(month + " " + year);
    }

    /**
     * Sets the dates and entries of a month-view calendar according to the specific month and year.
     *
     * @param year Year represented as a 4-digit integer.
     * @param month Month represented by numbers from 1 to 12.
     */
    protected void setMonthCalendarDatesAndEntries(int year, int month) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        int lengthOfMonth = startDate.lengthOfMonth();
        int startDay = getMonthStartDay(startDate);

        setMonthCalendarEntries(year, month, startDay);

        datesToBePrinted = new String[36];
        storeMonthDatesToBePrinted(lengthOfMonth);

        setFiveWeeksMonthCalendar(startDay);

        // If month has more than 5 weeks
        if (dateCount != lengthOfMonth) {
            setSixWeeksMonthCalendar(lengthOfMonth);
        }
    }

    /**
     * Sets the entries of a month-view calendar according to the specific month and year.
     *
     * @param year Year represented as a 4-digit integer.
     * @param month Month represented by numbers from 1 to 12.
     * @param startDay Integer value of the day of week of the start day  of the month. Values ranges from 1 - 7,
     *                 representing the different days of the week.
     */
    private void setMonthCalendarEntries(int year, int month, int startDay) {
        ObservableList<EntryCard> entryCardsList = getEntryCardsList(year, month);
        setMonthEntries(startDay, entryCardsList);

        addListenerToTaskList(year, month, startDay);
    }

    //============================= Populating the Month Calendar Dates ===========================================

    /**
     * Sets the dates of a five-weeks month-view calendar into the {@code taskCalendar}.
     *
     * @param startDay Integer value of the day of week of the start day  of the month. Values ranges from 1 - 7,
     *                 representing the different days of the week.
     */
    private void setFiveWeeksMonthCalendar(int startDay) {
        dateCount = 1;
        for (int row = 0; row <= MAX_ROW; row++) {
            if (row == 0) {
                for (int column = startDay; column <= MAX_COLUMN; column++) {
                    Text dateToPrint = new Text(datesToBePrinted[dateCount]);
                    addMonthDate(dateToPrint, column, row);
                    dateCount++;
                }
            } else {
                for (int column = 0; column <= MAX_COLUMN; column++) {
                    Text dateToPrint = new Text(datesToBePrinted[dateCount]);
                    addMonthDate(dateToPrint, column, row);
                    dateCount++;
                }
            }
        }
    }

    /**
     * Sets the dates of the sixth week in a six-weeks month-view calendar into the {@code taskCalendar}.
     *
     * @param lengthOfMonth Integer value of the number of days in a month.
     */
    private void setSixWeeksMonthCalendar(int lengthOfMonth) {
        int remainingDays = lengthOfMonth - dateCount;

        for (int column = 0; column <= remainingDays; column++) {
            Text dateToPrint = new Text(datesToBePrinted[dateCount]);
            addMonthDate(dateToPrint, column, 0);
            dateCount++;
        }
    }

    /**
     * Gets the day of week of the start date of a particular month and year.
     *
     * @param startDate A LocalDate variable that represents the date, viewed as year-month-day. The day will always
     *                  be set as 1.
     * @return Integer value of the day of week of the start day  of the month. Values ranges from 1 - 7,
     *         representing the different days of the week.
     */
    private int getMonthStartDay(LocalDate startDate) {
        int startDay = startDate.getDayOfWeek().getValue();

        // Sunday is the first column in the calendar
        if (startDay == SUNDAY) {
            startDay = 0;
        }

        return startDay;
    }

    /**
     * Adds a particular date to the correct {@code column} and {@code row} in the {@code taskCalendar}.
     *
     * @param dateToPrint The formatted date text to be printed on the {@code taskCalendar}.
     * @param column The column number in {@code taskCalendar}. Column number should range from 0 to 6.
     * @param row The row number in {@code taskCalendar}. Row number should range from 0 to 4.
     */
    private void addMonthDate(Text dateToPrint, int column, int row) {
        taskCalendar.add(dateToPrint, column, row);
        taskCalendar.setHalignment(dateToPrint, HPos.LEFT);
        taskCalendar.setValignment(dateToPrint, VPos.TOP);
    }

    /**
     * Stores the formatted date String to be printed on the {@code taskCalendar}.
     *
     * @param lengthOfMonth Integer value of the number of days in a month.
     */
    private void storeMonthDatesToBePrinted(int lengthOfMonth) {
        for (int date = 1; date <= 35; date++) {
            if (date <= lengthOfMonth) {
                datesToBePrinted[date] = "  " + String.valueOf(date);
            }
        }
    }

    //============================= Populating the Month Calendar Entries =========================================

    /**
     * Sets the entries, in the form of {@code EntryCard}, into the {@code taskCalendar}.
     *
     * @param startDay Integer value of the day of week of the start day of the month. Values ranges from 1 - 7,
     *                 representing the different days of the week.
     * @param entryCardsList An {@code ObservableList} of {@code EntryCard}.
     */
    private void setMonthEntries(int startDay, ObservableList<EntryCard> entryCardsList) {
        int numOfEntries = entryCardsList.size();
        for (int size = 0; size < numOfEntries; size++) {
            List<EntryCard> toAddList = new ArrayList<>();
            EntryCard currentEntry = entryCardsList.get(size);
            toAddList.add(currentEntry);
            ObservableList<EntryCard> toAddObservableList = FXCollections.observableList(toAddList);

            int deadline = currentEntry.getTask().getDeadline().date.getDayOfMonth();
            int countDate = deadline + startDay;

            size = checkSameDayEntries(entryCardsList, size, toAddList, deadline);

            int remainder = countDate % 7;
            int divisor = countDate / 7;

            addEntries(toAddObservableList, countDate, remainder, divisor);
        }
    }

    /**
     * Calculating the position of the entries to be added into the {@code taskCalendar}.
     *
     * @param toAddObservableList An {@code ObservableList} to be added to a particular {@code column} and {@code
     * row} in the {@code taskCalendar}.
     * @param countDate The addition of {@code deadline} and {@code startDay}.
     */
    private void addEntries(ObservableList<EntryCard> toAddObservableList, int countDate, int remainder, int divisor) {
        if (countDate <= MAX_NUM_OF_DAYS) {
            if (remainder == NO_REMAINDER) {
                int row = divisor - 1;
                int column = MAX_COLUMN;

                addEntryListView(toAddObservableList, row, column);
            } else {
                int row = divisor;
                int column = remainder - 1;

                addEntryListView(toAddObservableList, row, column);
            }
        } else {
            int row = 0;
            int column = remainder - 1;

            addEntryListView(toAddObservableList, row, column);
        }
    }

    /**
     * Adds a {@code ListView} that contains a list of {@code EntryCard} to a particular {@code column} and {@code
     * row} in the {@code taskCalendar}.
     *
     * @param toAddObservableList An {@code ObservableList} to be added to a particular {@code column} and {@code
     * row} in the {@code taskCalendar}.
     * @param column The column number in {@code taskCalendar}. Column number should range from 0 to 6.
     * @param row The row number in {@code taskCalendar}. Row number should range from 0 to 4.
     */
    private void addEntryListView(ObservableList<EntryCard> toAddObservableList, int row, int column) {
        ListView<EntryCard> entries = new ListView<>();
        entries.setItems(toAddObservableList);
        entries.setCellFactory(listView -> new EntryListViewCell());
        entries.setMaxHeight(60);

        taskCalendar.add(entries, column, row);
        taskCalendar.setValignment(entries, VPos.BOTTOM);
    }

    /**
     * Checks if the {@code entryCardsList} contains other {@code EntryCard} with the same {@code deadline} as the
     * previous {@code EntryCard}. If there exists one, the {@code EntryCard} will be added to the {@code toAddList}.
     *
     * @param entryCardsList An {@code ObservableList} of {@code EntryCard}.
     * @param size The variable used as the condition for the for loop in {@code setMonthEntries}.
     * @param toAddList A list of {@EntryCard} to be added to a particular {@code column} and {@code row} in the
     * {@code taskCalendar}.
     * @param deadline The deadline of the {@code Task} in the previous {@code EntryCard}.
     *
     * @return An increment in {@code size} if there exists an {@code EntryCard} with the same {@code deadline} as
     * the previous {@code EntryCard}
     */
    private int checkSameDayEntries(ObservableList<EntryCard> entryCardsList, int size, List<EntryCard> toAddList,
                                    int deadline) {
        int numOfEntries = entryCardsList.size();
        if (size != numOfEntries) {
            for (int nextSize = size + 1; nextSize < numOfEntries; nextSize++) {
                EntryCard nextEntry = entryCardsList.get(nextSize);
                int nextDeadline = nextEntry.getTask().getDeadline().date.getDayOfMonth();

                if (deadline == nextDeadline) {
                    toAddList.add(nextEntry);
                    size++;
                } else {
                    break;
                }
            }
        }

        return size;
    }

    /**
     * Maps each {@code Task} in the {@code SortedList} to an {@code EntryCard}.
     *
     * @param year Year represented as a 4-digit integer.
     * @param month Month represented by numbers from 1 to 12.
     * @return An {@code ObservableList} of {@code EntryCard}.
     */
    private ObservableList<EntryCard> getEntryCardsList(int year, int month) {
        FilteredList<Task> filteredList = getFilteredTaskList(year, month);
        SortedList<Task> taskSortedList = getSortedTaskList(filteredList);

        return EasyBind.map(taskSortedList, (task) -> new EntryCard(task));
    }

    /**
     * Sorts the {@code filteredList} according to the {@code deadlineComparator}.
     *
     * @param filteredList Filtered {@code taskList} that contains tasks whose deadlines are of a particular month
     *                     and year.
     * @return A sorted {@code filteredList} that contains tasks arranged according to their deadlines.
     */
    private SortedList<Task> getSortedTaskList(FilteredList<Task> filteredList) {
        return filteredList.sorted(deadlineComparator());
    }

    /**
     * Filters the {@code taskList} so that it contains tasks whose deadlines are of a particular month and year.
     *
     * @param year Year represented as a 4-digit integer.
     * @param month Month represented by numbers from 1 to 12.
     * @return A filtered {@code taskList} that contains tasks whose deadlines are of a particular month and year.
     */
    private FilteredList<Task> getFilteredTaskList(int year, int month) {
        FilteredList<Task> filteredList = new FilteredList<>(taskList, task -> true);

        filteredList.setPredicate(task -> {
            LocalDate date = task.getDeadline().date;

            if ((date.getMonthValue() == month) && (date.getYear() == year)) {
                return true;
            } else {
                return false;
            }
        });
        return filteredList;
    }

    /**
     * Updates the calendar entries when a change in {@code taskList} is detected.
     *
     * @param year Year represented as a 4-digit integer.
     * @param month Month represented by numbers from 1 to 12.
     * @param startDay Integer value of the day of week of the start day of the month. Values ranges from 1 - 7,
     *                 representing the different days of the week.
     */
    private void addListenerToTaskList(int year, int month, int startDay) {
        taskList.addListener(new ListChangeListener<Task>() {
            @Override
            public void onChanged(Change change) {

                while (change.next()) {
                    ObservableList<EntryCard> updatedEntryCardsList = getEntryCardsList(year, month);
                    setMonthEntries(startDay, updatedEntryCardsList);
                }
            }
        });
    }

    /**
     * @return A {@code Task} comparator based on deadline.
     */
    private static Comparator<Task> deadlineComparator() {
        return new Comparator<Task>() {
            @Override
            public int compare(Task task1, Task task2) {
                return (task1.getDeadline().date).compareTo(task2.getDeadline().date);
            }
        };
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code EntryCard}.
     */
    class EntryListViewCell extends ListCell<EntryCard> {

        @Override
        protected void updateItem(EntryCard entry, boolean empty) {
            super.updateItem(entry, empty);

            if (empty || entry == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(entry.getRoot());
            }
        }
    }
}
