package guitests.guihandles;

import javafx.scene.Node;

/**
 * A handler for {@code CentrePanel} of the Ui.
 */
public class CalendarPanelHandle extends NodeHandle<Node> {

    public static final String LABEL_YEAR_ID = "#year";
    public static final String LABEL_MONTH_ID = "#month";
    public static final String GRID_PANE_ID = "#daysOfMonth";

    protected CalendarPanelHandle(Node rootNode) {
        super(rootNode);
    }

}
