package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.TabPane;

public class DetailsPanelHandle extends NodeHandle<TabPane>{

    public DetailsPanelHandle(TabPane detailsPanel) {
        super(detailsPanel);
    }
    
    public int getCurrentTab() {
        return getRootNode().getSelectionModel().getSelectedIndex();
    };
}