package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.layout.StackPane;

//@@author guekling
/**
 * A handler for the {@code CalendarPanel} of the UI.
 */
public class CalendarPanelHandle extends NodeHandle<Node> {

    public static final String CALENDAR_ID = "#calendarPane";

    private final StackPane calendarPane;

    public CalendarPanelHandle(Node calendarPanelNode) {
        super(calendarPanelNode);

        this.calendarPane = getChildNode(CALENDAR_ID);
    }
}
