//@@author Kyholmes
package seedu.address.ui;

import javax.swing.plaf.synth.Region;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
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
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof QueueCard)) {
            return false;
        }

        // state check
        QueueCard card = (QueueCard) other;
        return index.getText().equals(card.index.getText())
                && patient.equals(card.patient);
    }
}
