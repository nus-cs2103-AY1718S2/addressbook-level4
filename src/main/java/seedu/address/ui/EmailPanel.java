package seedu.address.ui;

import java.io.IOException;
import java.util.logging.Logger;

import javax.mail.MessagingException;

import com.google.common.eventbus.Subscribe;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.scene.web.HTMLEditor;
import javafx.stage.StageStyle;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.logic.GmailClient;
import seedu.address.model.GmailMessage;
import seedu.address.model.person.Person;

/**
 * Shows the email drafting tab
 */
public class EmailPanel extends UiPart<Region> {

    private static final String FXML = "EmailPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(EmailPanel.class);
    private String recipientEmail;

    @FXML
    private TextField toTxtField;

    @FXML
    private TextField subjectTxtField;

    @FXML
    private HTMLEditor bodyTxtField;

    @FXML
    private Button sendBtn;

    public EmailPanel() {
        super(FXML);
        registerAsAnEventHandler(this);
    }

    /**
     *Handles the send button click
     */
    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        String bodyText = bodyTxtField.getHtmlText();
        String subjectText = subjectTxtField.getText();

        executeSendEmail(subjectText, bodyText);
    }

    /**
     *Executes the sending of email.
     * @param subject subject of the email
     * @param bodyText body text of the email
     * @throws IOException
     */
    private void executeSendEmail(String subject, String bodyText) throws IOException {

        GmailMessage gmailMessage = new GmailMessage(recipientEmail, subject, bodyText);

        try {
            GmailClient client = GmailClient.getInstance();
            client.sendEmail(gmailMessage.getEmailContent());

            showAlertDialogAndWait("Success", "Email has been sent successfully!");

            clearAllFields();

        } catch (MessagingException e) {
            e.printStackTrace();
            showAlertDialogAndWait("Error", "Email was not sent. Please try again.");
        }

    }

    /**
     *Fill up the email draft with details of the recipient
     * @param person person that was selected in the list
     */
    private void fillEmailDraft(Person person) {
        recipientEmail = person.getEmail().value;
        toTxtField.setText(recipientEmail);
        bodyTxtField.setHtmlText("<font face=\"Segoe UI\">Dear " + person.getName().fullName + ",</font>");
        subjectTxtField.requestFocus();
    }

    /**
     *Shows the alert dialog after email is sent successfully
     * @param content the text which will be display to the user
     */
    private void showAlertDialogAndWait(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("contactHeRo");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.initStyle(StageStyle.UTILITY);
        alert.getDialogPane().getStylesheets().add("view/DarkTheme.css");
        alert.showAndWait();
    }

    /**
     *Clear the textfield and htmleditor textbox
     */
    private void clearAllFields() {
        toTxtField.clear();
        subjectTxtField.clear();
        bodyTxtField.setHtmlText("");
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        fillEmailDraft(event.getNewSelection().person);
    }
}
