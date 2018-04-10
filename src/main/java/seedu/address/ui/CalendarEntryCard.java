package seedu.address.ui;
//@@author SuxianAlicia
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.event.CalendarEntry;

/**
 * An UI component that displays information of a {@code CalendarEntry}.
 */
public class CalendarEntryCard extends UiPart<Region> {

    private static final String FXML = "CalendarEntryCard.fxml";

    public final CalendarEntry calendarEntry;

    @FXML
    private HBox cardPane;

    @FXML
    private Label entryTitle;

    @FXML
    private Label id;

    @FXML
    private Label startDate;

    @FXML
    private Label endDate;

    @FXML
    private Label timeDuration;


    public CalendarEntryCard(CalendarEntry calendarEntry, int displayedIndex) {
        super(FXML);
        this.calendarEntry = calendarEntry;
        id.setText(displayedIndex + ". ");
        entryTitle.setText(calendarEntry.getEntryTitle().toString());
        startDate.setText("From: " + calendarEntry.getStartDate().toString());
        endDate.setText("To: " + calendarEntry.getEndDate().toString());
        timeDuration.setText("Between " + calendarEntry.getStartTime().toString()
                + " and " + calendarEntry.getEndTime().toString());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CalendarEntryCard)) {
            return false;
        }

        // state check
        CalendarEntryCard card = (CalendarEntryCard) other;
        return id.getText().equals(card.id.getText())
                && calendarEntry.equals(card.calendarEntry);
    }
}
