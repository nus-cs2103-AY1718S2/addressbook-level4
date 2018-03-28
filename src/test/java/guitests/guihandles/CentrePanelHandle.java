package guitests.guihandles;

import javafx.scene.Node;

/**
 * Provides a handle for {@code CentrePanel}.
 */
public class CentrePanelHandle extends NodeHandle<Node> {
    public static final String CENTRE_PANEL_ID = "#centrePlaceholder";

    public final BrowserPanelHandle browserPanel;
    //private final CalendarPanelHandle calendarPanel;

    protected CentrePanelHandle(Node rootNode) {
        super(rootNode);

        browserPanel = new BrowserPanelHandle(getChildNode(BrowserPanelHandle.BROWSER_ID));
    }

    public BrowserPanelHandle getBrowserPanelHandle() {
        return browserPanel;
    }
}
