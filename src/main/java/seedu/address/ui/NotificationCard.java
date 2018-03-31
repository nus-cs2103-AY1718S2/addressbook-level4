package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.notification.Notification;

/**
 * An UI component that displays information of a {@code Notification}.
 */
public class NotificationCard extends UiPart<Region> {


    public static final int NOTIFICATION_CARD_X_OFFSET = 15;
    public static final int NOTIFICATION_CARD_Y_OFFSET = 15;
    public static final int NOTIFICATION_CARD_WIDTH = 300 + NOTIFICATION_CARD_X_OFFSET;
    public static final int NOTIFICATION_CARD_HEIGHT = 100 + NOTIFICATION_CARD_Y_OFFSET;
    private static final String FXML = "NotificationCard.fxml";
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
    private GridPane content;

    private String ownerId;
    private boolean isFirstStage;
    private boolean isForCenter;


    public NotificationCard(String title, String displayedIndex, String ownerName, String endTime, String ownerId,
                            boolean isFirstStage) {
        super(FXML);
        this.index.setText(displayedIndex + ". ");
        this.title.setText(title);
        this.ownerName.setText(ownerName);
        this.endTime.setText(endTime);
        this.ownerId = ownerId;

        xOffset.setMaxWidth(NOTIFICATION_CARD_X_OFFSET);
        yOffset.setMaxWidth(NOTIFICATION_CARD_Y_OFFSET);
        this.isFirstStage = isFirstStage;
        isForCenter = false;
        setStyle();
    }

    public NotificationCard(String title, String displayedIndex, String ownerName, String endTime, String ownerId,
                            boolean isFirstStage, boolean isForCenter) {
        super(FXML);
        this.index.setText(displayedIndex + ". ");
        this.title.setText(title);
        this.ownerName.setText(ownerName);
        this.endTime.setText(endTime);
        this.ownerId = ownerId;

        xOffset.setMaxWidth(NOTIFICATION_CARD_X_OFFSET);
        yOffset.setMaxWidth(NOTIFICATION_CARD_Y_OFFSET);
        this.isFirstStage = isFirstStage;
        this.isForCenter = isForCenter;
        setStyle();
    }


    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NotificationCard)) {
            return false;
        }

        // state check
        NotificationCard card = (NotificationCard) other;
        return index.getText().equals(card.index.getText())
                && title.equals(((NotificationCard) other).title)
                && ownerName.equals(((NotificationCard) other).ownerName)
                && endTime.equals(((NotificationCard) other).endTime);
    }

    public String getOwnerId() {
        return ownerId;
    }

    public String getTitle() {
        return title.getText();
    }

    public String getIndex() {
        return index.getText();
    }

    public String getOwnerName() {
        return ownerName.getText();
    }

    public String getEndTime() {
        return endTime.getText();
    }

    public void setStyle() {
        if (!isForCenter) {
            if (isFirstStage) {
                content.getStyleClass().add("notification-card-first-stage");
            } else {
                content.getStyleClass().add("notification-card-second-stage");
            }
        } else {
            if (isFirstStage) {
                content.getStyleClass().add("notification-card-notification-center-first-stage");
            } else {
                content.getStyleClass().add("notification-card-notification-center-second-stage");
            }
        }
    }

    public NotificationCard getCopyForCenter() {
        return new NotificationCard(this.getTitle(), this.getIndex(), this.getOwnerName(), this.getEndTime(), this
                .getOwnerId(), isFirstStage, true);
    }
}
