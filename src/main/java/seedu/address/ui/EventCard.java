package seedu.address.ui;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.commons.util.DateUtil;
import seedu.address.model.event.ReadOnlyEvent;

//@@author x3tsunayh

/**
 * An UI component that displays information of a {@code Event}.
 */
public class EventCard extends UiPart<Region> {

    private static final String FXML = "EventListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final ReadOnlyEvent event;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label title;
    @FXML
    private Label description;
    @FXML
    private Label eventLocation;
    @FXML
    private Label datetime;
    @FXML
    private HBox eventLabelPane;
    @FXML
    private Label eventLabel;
    @FXML
    private ImageView eventImage;

    public EventCard(ReadOnlyEvent event, int displayedIndex) {
        super(FXML);
        id.setText(displayedIndex + ". ");
        this.event = event;
        bindListeners(event);
        setEventStatusStyle(event.getDatetime().value);
    }

    /**
     * Set the style for the event status label pane
     * @param maxWidth maximum width of the pane
     * @param colorCode color code of the border of the pane
     */
    private void setEventPaneStyle(int maxWidth, String colorCode) {
        eventLabelPane.setMaxWidth(maxWidth);
        eventLabelPane.setStyle("-fx-border-color: " + colorCode + ";");
    }

    /**
     * Set the text to be displayed at event date label
     * @param eventStatus text to be displayed
     */
    private void setEventLabelText(String eventStatus) {
        eventLabel.setText(eventStatus);
    }

    /**
     * Sets the event field style to alert users
     * (1) past events: grey color display
     * (2) happening today: red color display
     * (3) happening next 3 days: yellow color display
     * (4) beyond next 3 days: green color display
     */
    private void setEventStatusStyle(String eventDate) {
        int remainingDays = DateUtil.getDayCountBetweenTwoDates(DateUtil.getTodayDate(),
                DateUtil.getParsedDateTime(eventDate));

        if (remainingDays < 0) { // overdue tasks
            setEventPaneStyle(200, "#C5CDE2");
            setEventLabelText("  PAST EVENT");
        } else if (remainingDays == 0) {
            setEventPaneStyle(200, "#FF0000");
            setEventLabelText("  HAPPENING TODAY!");
        } else if (remainingDays < 3) {
            setEventPaneStyle(200, "#FFC000");
            setEventLabelText("  HAPPENING SOON");
        } else {
            setEventPaneStyle(200, "#00FF00");
            setEventLabelText("  NOT ANYTIME SOON");
        }
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Event} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyEvent event) {
        title.textProperty().bind(Bindings.convert(event.titleProperty()));
        description.textProperty().bind(Bindings.convert(event.descriptionProperty()));
        eventLocation.textProperty().bind(Bindings.convert(event.locationProperty()));
        datetime.textProperty().bind(Bindings.convert(event.datetimeProperty()));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EventCard)) {
            return false;
        }

        // state check
        EventCard card = (EventCard) other;
        return id.getText().equals(card.id.getText())
                && event.equals(card.event);
    }
}
