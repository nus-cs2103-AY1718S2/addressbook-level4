//@@author IzHoBX
package seedu.address.ui.testutil;

import java.util.LinkedList;

/**
 * A stub that mimics the behavior of Notification Card by removing the JavaFX components.
 */
public class NotificationCardStub {

    protected boolean isFirstStage;
    private String title;
    private String index;
    private String ownerName;
    private String endTime;
    private LinkedList<String> content = new LinkedList<>();

    private String ownerId;
    private boolean isForCenter;
    private String id;

    public NotificationCardStub(String title, String displayedIndex, String ownerName, String endTime, String ownerId,
                            boolean isFirstStage, String id) {
        this.index = displayedIndex + ". ";
        this.title = title;
        this.ownerName = ownerName;
        this.endTime = endTime;
        this.ownerId = ownerId;
        this.id = id;

        this.isFirstStage = isFirstStage;
        isForCenter = false;
        setStyle();
    }

    public NotificationCardStub(String title, String displayedIndex, String ownerName, String endTime, String ownerId,
                            boolean isFirstStage, boolean isForCenter, String id) {
        this.index = displayedIndex + ". ";
        this.title = title;
        this.ownerName = ownerName;
        this.endTime = endTime;
        this.ownerId = ownerId;
        this.id = id;

        this.isFirstStage = isFirstStage;
        isForCenter = isForCenter;
        setStyle();
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NotificationCardStub)) {
            return false;
        }

        // state check
        NotificationCardStub card = (NotificationCardStub) other;
        return index.equals(card.getIndex())
                && title.equals(card.title)
                && ownerName.equals(card.ownerName)
                && endTime.equals(card.endTime);
    }

    public String getOwnerId() {
        return ownerId;
    }

    public String getTitle() {
        return title;
    }

    public String getIndex() {
        return index;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setStyle() {

    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Title: " + title + " Owner: " + ownerName;
    }

    /**
     * Decreases the index displayed on notification card.
     */
    public void decreaseIndex(int i) {
        String currIndex = this.index;
        int j;
        for (j = 0; j < currIndex.length(); j++) {
            if (currIndex.charAt(j) == '.') {
                break;
            }
        }
        int currIndexNumeric = Integer.parseInt(currIndex.substring(0, j));
        this.index = (currIndexNumeric - i) + ". ";
    }
}
