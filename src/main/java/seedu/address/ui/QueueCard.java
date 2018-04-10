//@@author Kyholmes
package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.patient.Patient;

/**
 * An UI component that displays information of a {@code queue}.
 */
public class QueueCard extends UiPart<Region> {

    private static final String FXML = "QueueCard.fxml";

    public final Patient patient;

    @FXML
    private Label index;

    @FXML
    private Label name;

    public QueueCard(Patient patient, int displayedIndex) {
        super(FXML);
        this.patient = patient;
        index.setText(displayedIndex + ". ");
        name.setText(patient.getName().fullName);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof QueueCard)) {
            return false;
        }

        QueueCard card = (QueueCard) other;
        return index.getText().equals(card.index.getText())
                && patient.equals(card.patient);
    }
}
