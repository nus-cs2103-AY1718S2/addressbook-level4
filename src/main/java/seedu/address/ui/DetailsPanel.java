package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.SwitchTabRequestEvent;

/**
 * The Details Panel of the App,
 * contains the contact details panel and the browser panel.
 */
public class DetailsPanel extends UiPart<Region> {

    private static final String FXML = "DetailsPanel.fxml";

    private ContactDetailsDisplay contactDetailsDisplay;
    private BrowserPanel browserPanel;

    private final Logger logger = LogsCenter.getLogger(ContactDetailsDisplay.class);

    @FXML
    private Tab profile;

    @FXML
    private Tab linkedIn;

    @FXML
    private TabPane tabPane;

    public DetailsPanel() {
        super(FXML);
        registerAsAnEventHandler(this);
    }

    @Subscribe
    private void handleSwitchTabRequestEvent(SwitchTabRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        tabPane.getSelectionModel().clearAndSelect(event.tabId);
    }

    public void releaseResources() {
        browserPanel.freeResources();
    }

    /**
     * Adds the BrowserView to the DetailsPanel
     */
    public void addBrowserPanel() {
        browserPanel = new BrowserPanel();
        linkedIn.setContent(browserPanel.getRoot());
    }

    /**
     * Adds the ContactDetailsPanel to the DetailsPanel
     */
    public void addContactDetailsDisplayPanel() {
        contactDetailsDisplay = new ContactDetailsDisplay();
        profile.setContent(contactDetailsDisplay.getRoot());
    }
}
