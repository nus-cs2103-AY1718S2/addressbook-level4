package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import seedu.address.model.person.Person;
import seedu.address.model.timetableentry.TimetableEntry;

/**
 * An UI component that displays information of a {@code TimetableEntry}.
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

    public final TimetableEntry timetableEntry;

    @FXML
    private Label title;
    @FXML
    private Label id;
    @FXML
    private Label ownerName;
    @FXML
    private Label endTime;

    public NotificationCard(TimetableEntry timetableEntry, int displayedIndex, String ownerName) {
        super(FXML);
        this.timetableEntry = timetableEntry;
        id.setText(displayedIndex + ". ");
        title.setText(timetableEntry.getTitle());
        this.ownerName.setText(ownerName);
        endTime.setText(timetableEntry.getEndDateDisplay());
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
        return id.getText().equals(card.id.getText())
                && timetableEntry.equals(card.timetableEntry);
    }
}
