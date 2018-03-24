package guitests.guihandles;

import com.calendarfx.view.CalendarView;

import javafx.scene.Node;

//@@author jlks96
/**
 * Provides a handle for {@code CalendarPanel}.
 */
public class CalendarPanelHandle extends NodeHandle<Node> {
    public static final String CALENDAR_ID = "#calendarView";
    private final CalendarView calendarView;

    public CalendarPanelHandle(Node calendarPanelNode) {
        super(calendarPanelNode);
        calendarView = getChildNode(CALENDAR_ID);
    }


}
