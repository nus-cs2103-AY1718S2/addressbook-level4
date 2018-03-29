package seedu.address.logic.record;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import seedu.address.model.patient.Record;

/**
 * Controller for RecordLayout. Handles button press.
 */
public class RecordController {
    @FXML
    private TextField dateField;

    @FXML
    private TextField symptomField;

    @FXML
    private TextField illnessField;

    @FXML
    private TextField treatmentField;

    @FXML
    private Text messageText;


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
            } else {
                messageText.setText("Username and password do not match!");
            }
        }
    }

    public void initData(Record record) {
        dateField.setText(record.getDate());
        symptomField.setText(record.getSymptom());
        illnessField.setText(record.getIllness());
        treatmentField.setText(record.getTreatment());
    }
}
