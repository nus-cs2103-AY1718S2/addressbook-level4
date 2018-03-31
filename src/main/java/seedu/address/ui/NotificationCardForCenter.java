package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * An UI component that displays information of a {@code Notification}.
 */
public class NotificationCardForCenter extends NotificationCard {


    public static final int NOTIFICATION_CARD_X_OFFSET = 15;
    public static final int NOTIFICATION_CARD_Y_OFFSET = 15;
    public static final int NOTIFICATION_CARD_WIDTH = 300 + NOTIFICATION_CARD_X_OFFSET;
    public static final int NOTIFICATION_CARD_HEIGHT = 100 + NOTIFICATION_CARD_Y_OFFSET;
    private static final String FXML = "NotificationCardForCenter.fxml";
    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    @FXML
    private Label title;
    @FXML
    private Label index;
    @FXML
    private Label ownerName;
    @FXML
    private Label endTime;
    @FXML
    private VBox xOffset;
    @FXML
    private VBox yOffset;
    @FXML
    private GridPane contents;

    private String ownerId;


    public NotificationCardForCenter(String title, String displayedIndex, String ownerName, String endTime, String ownerId) {
        super(title, displayedIndex, ownerName, endTime, ownerId, FXML);
    }
}
