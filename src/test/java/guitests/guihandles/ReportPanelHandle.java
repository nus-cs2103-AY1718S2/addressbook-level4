// @@author anh2111
package guitests.guihandles;

import javafx.scene.Node;

/**
 * A handler for the {@code BrowserPanel} of the UI.
 */
public class ReportPanelHandle extends NodeHandle<Node> {

    public static final String REPORT_ID = "#report";
    private boolean isReportPanelOpened = false;

    public ReportPanelHandle(Node reportPanelNode) {
        super(reportPanelNode);
    }

    public boolean isOpened() {
        return isReportPanelOpened;
    }
}
// @@author
