package guitests.guihandles;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.view.CalendarView;

import javafx.scene.layout.StackPane;
import seedu.address.ui.util.CalendarFxUtil;

/**
 * A handler for {@code CentrePanel} of the Ui.
 */
//@@author SuxianAlicia
public class CalendarPanelHandle extends NodeHandle<StackPane> {

    public static final String CALENDAR_PANEL_ID = "#calendarPanelholder";

    private Calendar calendar;
    private CalendarSource calendarSource;
    private CalendarView calendarView;

    protected CalendarPanelHandle(StackPane rootNode) {
        super(rootNode);
        calendarView = CalendarFxUtil.returnModifiedCalendarView();
        calendarSource = new CalendarSource();
        calendar = new Calendar();
        calendar.setReadOnly(true);
        calendarSource.getCalendars().addAll(calendar);
        calendarView.getCalendarSources().setAll(calendarSource);
        getRootNode().getChildren().add(calendarView);
    }

}
