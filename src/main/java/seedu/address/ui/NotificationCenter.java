//@@author IzHoBX
package seedu.address.ui;

import java.util.LinkedList;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

/**
 * Encapsulates all the information and functionalities required for Notification Center.
 */
public class NotificationCenter {
    private static final int NOTIFICATION_CENTER_WIDTH = NotificationCard.NOTIFICATION_CARD_WIDTH
            + NotificationCard.NOTIFICATION_CARD_X_OFFSET * 3;
    private static final int NOTIFICATION_CARD_HEIGHT_IN_CENTER = NotificationCard.NOTIFICATION_CARD_HEIGHT;
    private static final int NOTIFICATION_CARD_WIDTH_IN_CENTER = NotificationCard.NOTIFICATION_CARD_WIDTH;
    private LinkedList<javafx.scene.layout.Region> notificationCards;

    @FXML
    private VBox notificationCardsBox;

    @FXML
    private ScrollPane notificationCenterPlaceHolder;

    public NotificationCenter(VBox notificationCardsBox,
                              javafx.scene.control.ScrollPane notificationCenterPlaceHolder) {
        notificationCards = new LinkedList<>();
        notificationCards.add(null);
        //for 1 based index
        this.notificationCardsBox = notificationCardsBox;
        this.notificationCenterPlaceHolder = notificationCenterPlaceHolder;
        setWidth();
        hideNotificationCenter();
        setPadding();
    }

    private void hideNotificationCenter() {
        notificationCenterPlaceHolder.setTranslateX(NOTIFICATION_CENTER_WIDTH);
    }

    private void setWidth() {
        notificationCenterPlaceHolder.setMaxWidth(NOTIFICATION_CENTER_WIDTH);
        notificationCardsBox.setMaxWidth(NOTIFICATION_CENTER_WIDTH);
    }

    private void setPadding() {
        notificationCardsBox.setPadding(new Insets(NotificationCard.NOTIFICATION_CARD_Y_OFFSET, 0, 0,
                NotificationCard.NOTIFICATION_CARD_X_OFFSET));
    }

    public int getWidth() {
        return NOTIFICATION_CENTER_WIDTH;
    }

    public LinkedList<javafx.scene.layout.Region> getNotificationCards() {
        return notificationCards;
    }

    public int getTotalUndismmissedNotificationCards() {
        return notificationCards.size() - 1;
    }

    /**
     * Adds a notification to the Notification center
     */
    public void add(NotificationCard newNotificationCard) {
        NotificationCard forCenter = newNotificationCard.getCopyForCenter();
        notificationCards.add(forCenter.getRoot());
        notificationCardsBox.getChildren().add(forCenter.getRoot());
    }

    public ScrollPane getNotificationCenter() {
        return notificationCenterPlaceHolder;
    }
}
