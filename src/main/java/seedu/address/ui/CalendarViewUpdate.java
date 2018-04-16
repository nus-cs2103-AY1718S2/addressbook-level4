package seedu.address.ui;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;

import seedu.address.model.Model;
import seedu.address.model.event.ReadOnlyEvent;

//@@author x3tsunayh

/**
 * Methods to update Calendar View
 */
public class CalendarViewUpdate {

    /**
     * Updates view state of Calendar UI
     * for methods like add, delete, edit, etc.
     *
     * @param calendarView
     */
    public static void updateViewState(CalendarView calendarView) {
        calendarView.setCurrentYearMonth(YearMonth.now());
        calendarView.populateCalendar(calendarView.getCurrentYearMonth(), null);
    }

    /**
     * Updates view state of Calendar UI
     * for the Find Event Command.
     *
     * @param calendarView
     * @param model
     */
    public static void updateFindState(CalendarView calendarView, Model model) {
        List<ReadOnlyEvent> events = model.getFilteredEventList();
        if (events.size() != 0) {
            String findYearMonth = events.get(0).getDatetime().value.substring(0, 7);
            // If every event in the filtered list is on the same day, Calendar View jumps to that day.
            boolean changeSelectedYearMonth = events.stream()
                    .allMatch(e -> e.getDatetime().value.substring(0, 7).equals(findYearMonth));

            if (changeSelectedYearMonth) {
                calendarView.setCurrentYearMonth(YearMonth.parse(findYearMonth,
                        DateTimeFormatter.ofPattern("yyyy-MM")));
                calendarView.populateCalendar(calendarView.getCurrentYearMonth(), null);
            }
        }
    }
}
