package guitests.guihandles;
//@@author SuxianAlicia
import java.time.LocalDate;

import com.calendarfx.view.CalendarView;
import com.calendarfx.view.page.DayPage;
import com.calendarfx.view.page.MonthPage;
import com.calendarfx.view.page.WeekPage;

import javafx.scene.layout.StackPane;

/**
 * A handler for {@code CalendarPanel} of the Ui.
 */
public class CalendarPanelHandle extends NodeHandle<StackPane> {

    public static final String CALENDAR_PANEL_ID = "#calendarPanelHolder";

    private static final String DAY_VIEW = "Day";
    private static final String MONTH_VIEW = "Month";
    private static final String WEEK_VIEW = "Week";

    private CalendarView calendarView;

    public CalendarPanelHandle(StackPane rootNode) {
        super(rootNode);

        if (getRootNode().getChildren().get(0) instanceof CalendarView) {
            calendarView = (CalendarView) getRootNode().getChildren().get(0);
        } else {
            throw new AssertionError("#calendarPanelholder should have child node of CalendarView object.");
        }
    }

    /**
     * Returns the current view of {@code calendarView}.
     */
    public String getCurrentView() {
        if (calendarView.getSelectedPage() instanceof MonthPage) {
            return MONTH_VIEW;
        } else if (calendarView.getSelectedPage() instanceof WeekPage) {
            return WEEK_VIEW;
        } else if (calendarView.getSelectedPage() instanceof DayPage) {
            return DAY_VIEW;
        }
        return null;
    }

    /**
     * Returns the current date displayed by {@code calendarView}.
     */
    public LocalDate getCurrentDate() {
        return calendarView.getDate();
    }

    /**
     * Returns today's date as stored in {@code calendarView}.
     */
    public LocalDate getTodayDate() {
        return calendarView.getToday();
    }
}
