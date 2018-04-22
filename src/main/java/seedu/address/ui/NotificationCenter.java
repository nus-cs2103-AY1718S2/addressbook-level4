//@@author IzHoBX
package seedu.address.ui;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.index.Index;

/**
 * Encapsulates all the information and functionalities required for Notification Center.
 */
public class NotificationCenter {
    private static final int NOTIFICATION_CENTER_WIDTH = NotificationCard.NOTIFICATION_CARD_WIDTH
            + NotificationCard.NOTIFICATION_CARD_X_OFFSET * 3;
    private static final int NOTIFICATION_CARD_HEIGHT_IN_CENTER = NotificationCard.NOTIFICATION_CARD_HEIGHT;
    private static final int NOTIFICATION_CARD_WIDTH_IN_CENTER = NotificationCard.NOTIFICATION_CARD_WIDTH;
    protected LinkedList<javafx.scene.layout.Region> notificationCards;
    protected LinkedList<NotificationCard> notificationCardCopy;
    protected HashMap<String, LinkedList<javafx.scene.layout.Region>> idToCard;

    @FXML
    private VBox notificationCardsBox;

    @FXML
    private ScrollPane notificationCenterPlaceHolder;

    public NotificationCenter() {

    }

    public NotificationCenter(VBox notificationCardsBox,
                              javafx.scene.control.ScrollPane notificationCenterPlaceHolder) {
        notificationCards = new LinkedList<>();
        idToCard = new HashMap<>();
        notificationCards.add(null);
        //for 1 based index
        notificationCardCopy = new LinkedList<>();
        notificationCardCopy.add(null);
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
        return notificationCards.size();
    }

    /**
     * Adds a notification to the Notification center
     */
    public void add(NotificationCard newNotificationCard) {
        NotificationCard forCenter = newNotificationCard.getCopyForCenter();
        javafx.scene.layout.Region notificationCard = forCenter.getRoot();
        LinkedList<javafx.scene.layout.Region> cards;
        if (idToCard.get(forCenter.getId()) == null) {
            cards = new LinkedList<>();
        } else {
            cards = idToCard.get(forCenter.getId());
        }
        cards.add(notificationCard);
        idToCard.put(forCenter.getId(), cards);
        notificationCards.add(notificationCard);
        notificationCardCopy.add(forCenter);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                notificationCardsBox.getChildren().add(notificationCard);
            }
        });
    }

    public ScrollPane getNotificationCenter() {
        return notificationCenterPlaceHolder;
    }

    /**
     * Deletes all notification records associated with the given eventId
     */
    public void deleteNotification(String id) throws NullPointerException {
        if (!idToCard.containsKey(id)) {
            return;
        }
        for (javafx.scene.layout.Region nc: idToCard.get(id)) {
            notificationCardsBox.getChildren().remove(nc);
            notificationCards.remove(nc);
        }
        idToCard.remove(id);
        Iterator<NotificationCard> iterator = notificationCardCopy.iterator();
        iterator.next();
        //to bypass the null at index 0
        int toDecrement = 0;
        while (iterator.hasNext()) {
            NotificationCard curr = iterator.next();
            if (curr.getId().equals(id)) {
                iterator.remove();
                toDecrement++;
            } else if (toDecrement > 0) {
                curr.decreaseIndex(toDecrement);
            }
        }
    }

    public String getIdByIndex(Index index) {
        return notificationCardCopy.get(index.getOneBased()).getId();
    }

    public String getOwnerIdByIndex(Index index) {
        return notificationCardCopy.get(index.getOneBased()).getOwnerId();
    }


    public NotificationCard getNotificationCard(Index targetIndex) {
        return notificationCardCopy.get(targetIndex.getOneBased());
    }

    /**
    * Delete the notification card at the given index
    */
    public NotificationCard deleteNotificationByIndex(Index targetIndex) {
        notificationCardsBox.getChildren().remove(targetIndex.getZeroBased());
        idToCard.remove(notificationCards.get(targetIndex.getOneBased()).getId());
        notificationCards.remove(targetIndex.getOneBased());
        Iterator<NotificationCard> iterator = notificationCardCopy.iterator();
        for (int i = 0; i < notificationCardCopy.size(); i++) {
            if (i <= targetIndex.getOneBased()) {
                iterator.next();
            } else {
                NotificationCard curr = iterator.next();
                curr.decreaseIndex(1);
            }
        }
        return notificationCardCopy.remove(targetIndex.getOneBased());
    }

    /**
     * Removes all notification cards associated with the given ownerId
     */
    public void removeNotificationForPerson(int targetId) {
        System.out.println("TargetId: targetId");
        int totalRemoved = 0;
        LinkedList<NotificationCard> toDelete = new LinkedList<>();
        for (NotificationCard nc: notificationCardCopy) {
            if (nc != null && nc.getOwnerId().equals(targetId + "")) {
                toDelete.add(nc);
                notificationCards.remove(nc.getRoot());
                notificationCardsBox.getChildren().remove(nc.getRoot());
                totalRemoved++;
            } else if (totalRemoved > 0) {
                nc.decreaseIndex(totalRemoved);
            }
        }
        for (NotificationCard nc: toDelete) {
            notificationCardCopy.remove(nc);
        }
    }
}
