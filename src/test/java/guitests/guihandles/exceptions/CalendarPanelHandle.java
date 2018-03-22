package guitests.guihandles.exceptions;

import com.calendarfx.view.CalendarView;

import guitests.guihandles.NodeHandle;
import javafx.scene.Node;

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
