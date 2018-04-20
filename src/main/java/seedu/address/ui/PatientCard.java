package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.patient.Patient;
import seedu.address.model.tag.Tag;

/**
 * An UI component that displays information of a {@code Patient}.
 */
public class PatientCard extends UiPart<Region> {

    private static final String FXML = "PatientListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on Imdb level 4</a>
     */

    public final Patient patient;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private FlowPane tags;

    public PatientCard(Patient patient, int displayedIndex) {
        super(FXML);
        this.patient = patient;
        id.setText(displayedIndex + ". ");
        name.setText(patient.getName().fullName);

        initTagLabels(patient);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PatientCard)) {
            return false;
        }

        // state check
        PatientCard card = (PatientCard) other;
        return id.getText().equals(card.id.getText())
                && patient.equals(card.patient);
    }

    /**
     * create tag labels for patient, change tag color based on the hex string
     * @param patient
     */
    private void initTagLabels(Patient patient) {
        //Solution below adopted from https://assylias.wordpress.com/2013/12/08/383/ and
        //https://www.javaworld.com/article/2074537/core-java/tostring--
        //hexadecimal-representation-of-identity-hash-codes.html
        for (Tag tag : patient.getTags()) {
            Label newLabel = new Label(tag.tagName);
            newLabel.setStyle("-fx-background-color: #" + convertHashCodeToHexString(tag.tagName));
            tags.getChildren().add(newLabel);
        }
    }

    private static String convertHashCodeToHexString(String tagName) {
        return Integer.toHexString(tagName.hashCode());
    }
}
