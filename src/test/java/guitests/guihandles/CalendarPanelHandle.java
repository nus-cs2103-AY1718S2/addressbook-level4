package guitests.guihandles;

import javafx.scene.Node;

//@@author Robert-Peng
/**
 *
 */
public class CalendarPanelHandle extends NodeHandle<Node> {

    public static final String CALENDARPANEL_ID = "#calendarPlaceholder";

    protected CalendarPanelHandle(Node calendarPanelNode) {
        super(calendarPanelNode);
    }
}
