//@@author ewaldhew
package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.rule.Rule;

/**
 * Controller for the notification manager
 */
public class NotificationsWindow extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(BrowserWindow.class);
    private static final Image WINDOW_ICON = new Image("/images/address_book_32.png");
    private static final String WINDOW_TITLE = "Notifications";
    private static final String FXML = "NotificationsWindow.fxml";

    private RuleListPanel ruleListPanel;

    @FXML
    private StackPane ruleListPanelPlaceholder;

    public NotificationsWindow(Stage stage, ObservableList<Rule> data) {
        super(FXML, stage);

        // Configure the UI
        setTitle();
        setWindowDefaultSize();

        registerAsAnEventHandler(this);

        ruleListPanel = new RuleListPanel(data);
        ruleListPanelPlaceholder.getChildren().add(ruleListPanel.getRoot());
    }

    private void setTitle() {
        this.getRoot().setTitle(WINDOW_TITLE);
    }

    /**
     * Sets the default size.
     */
    private void setWindowDefaultSize() {
        this.getRoot().setHeight(300);
        this.getRoot().setWidth(500);
    }

    /**
     * Shows the notification window.
     * @throws IllegalStateException
     * <ul>
     *     <li>
     *         if this method is called on a thread other than the JavaFX Application Thread.
     *     </li>
     *     <li>
     *         if this method is called during animation or layout processing.
     *     </li>
     *     <li>
     *         if this method is called on the primary stage.
     *     </li>
     *     <li>
     *         if {@code dialogStage} is already showing.
     *     </li>
     * </ul>
     */
    public void show() {
        logger.fine("Showing notification manager.");
        getRoot().show();
        setWindowDefaultSize();
    }

}
