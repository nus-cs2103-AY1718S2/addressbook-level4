package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * An UI component that displays information of a {@code Notification}.
 */
public class NotificationCard extends UiPart<Region> {

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

    private String ownerId;


    public NotificationCard(String title, String displayedIndex, String ownerName, String endTime, String ownerId) {
        super(FXML);
        this.index.setText(displayedIndex + ". ");
        this.title.setText(title);
        this.ownerName.setText(ownerName);
        this.endTime.setText(endTime);
        this.ownerId = ownerId;

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
}
