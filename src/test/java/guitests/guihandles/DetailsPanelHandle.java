package guitests.guihandles;

import javafx.scene.control.TabPane;

/**
 * A handle to the {@code DetailsPanelHandle} in the GUI.
 */
public class DetailsPanelHandle extends NodeHandle<TabPane> {

    public DetailsPanelHandle(TabPane detailsPanel) {
        super(detailsPanel);
    }

    public int getCurrentTab() {
        return getRootNode().getSelectionModel().getSelectedIndex();
    }
}
