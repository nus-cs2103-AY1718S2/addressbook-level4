//@@author nhs-work
package seedu.address.logic.record;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import seedu.address.model.patient.Record;

/**
 * Controller for RecordLayout. Handles button press.
 */
public class RecordController {
    @FXML
    private TextArea dateField;

    @FXML
    private TextArea symptomField;

    @FXML
    private TextArea illnessField;

    @FXML
    private TextArea treatmentField;

    @FXML
    private Text messageText;

    @FXML
    private javafx.scene.control.Button saveButton;

    /**
     * Takes in input to the various record fields when confirm button is clicked.
     */
    @FXML
    protected void handleButtonAction(ActionEvent event) {
        String date = this.dateField.getText();
        String symptom = this.symptomField.getText();
        String illness = this.illnessField.getText();
        String treatment = this.treatmentField.getText();
        if (date.equals("") || symptom.equals("") || illness.equals("") || treatment.equals("")) {
            messageText.setText("Please fill in all fields.");
        } else {
            if (RecordManager.authenticate(date, symptom, illness, treatment)) {
                messageText.setText("Success! Please close this window.");
                closeButtonAction();
            } else {
                messageText.setText("Invalid entries!");
            }
        }
    }

    /**
     * Takes in input to the various record fields when enter button is clicked in any field.
     */
    @FXML
    protected void handleKeyAction(KeyEvent event) {
        KeyCode code = event.getCode();
        if (code == KeyCode.ENTER) {
            handleEnterKey(event);
        } else if (code == KeyCode.TAB && !event.isShiftDown()
                && !event.isControlDown()) {
            handleTabKey(event);
        }
    }

    /**
     * Takes in user input to every field, verifies if it is a valid entry
     * and calls for the method to close the stage.
     */
    @FXML
    protected void handleEnterKey(KeyEvent event) {
        String date = this.dateField.getText();
        String symptom = this.symptomField.getText();
        String illness = this.illnessField.getText();
        String treatment = this.treatmentField.getText();
        if (date.equals("") || symptom.equals("") || illness.equals("") || treatment.equals("")) {
            messageText.setText("Please fill in all fields.");
        } else {
            if (RecordManager.authenticate(date, symptom, illness, treatment)) {
                messageText.setText("Success! Please close this window.");
                closeButtonAction();
            } else {
                messageText.setText("Invalid entries!");
            }
        }
        event.consume();
    }

    /**
     * Overwrites default tab key to allow for moving to next text field.
     */
    @FXML
    protected void handleTabKey(KeyEvent event) {
        event.consume();
        Node node = (Node) event.getSource();
        KeyEvent newEvent =
                new KeyEvent(event.getSource(),
                event.getTarget(), event.getEventType(),
                event.getCharacter(), event.getText(),
                event.getCode(), event.isShiftDown(),
                true, event.isAltDown(),
                event.isMetaDown());

        node.fireEvent(newEvent);
    }

    /**
     * Handles closing the stage.
     */
    @FXML
    private void closeButtonAction() {
        // get a handle to the stage
        Stage stage = (Stage) saveButton.getScene().getWindow();
        // close the stage
        stage.close();
    }

    /**
     * Initialises the text fields with data from the patient's {@code record}.
     */
    public void initData(Record record) {
        dateField.setWrapText(true);
        symptomField.setWrapText(true);
        illnessField.setWrapText(true);
        treatmentField.setWrapText(true);
        dateField.setText(record.getDate());
        symptomField.setText(record.getSymptom());
        illnessField.setText(record.getIllness());
        treatmentField.setText(record.getTreatment());
    }
}
