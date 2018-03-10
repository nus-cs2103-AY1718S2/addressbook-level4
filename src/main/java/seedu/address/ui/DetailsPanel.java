package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.logic.commands.DeleteCommand;

public class DetailsPanel extends UiPart<Region> {

    private static final String FXML = "detailsPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ProfileDisplay.class);
    
    @FXML
    private Tab profile;
    
    @FXML
    private Tab linkedIn;

    private ProfileDisplay profileDisplay;
    public BrowserPanel browserPanel;

    public DetailsPanel() {
        super(FXML);
        profileDisplay = new ProfileDisplay();
        profile.setContent(profileDisplay.getRoot());
        browserPanel = new BrowserPanel();
        linkedIn.setContent(browserPanel.getRoot());
        registerAsAnEventHandler(this);
    }

}
