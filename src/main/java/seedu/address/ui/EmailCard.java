package seedu.address.ui;

import java.io.IOException;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

/**
 * the card for the email panel
 */
//@@author glorialaw
public class EmailCard extends UiPart<Region> {
    private static final String FXML = "EmailCard.fxml";

    @FXML
    private HBox cardPane;

    @FXML
    private Label email;

    @FXML
    private Label subject;

    @FXML
    private Label preview;

    private Message msg;

    public EmailCard(Message message) {
        super(FXML);
        String ERROR_MESSAGE = "Please ensure that you are connected to the internet.";
        if (message == null) {
            email.setText("unknown@unknown.com");
            subject.setText("Error: Unable to retrieve message");
            preview.setText(ERROR_MESSAGE);
            this.msg = null;
        } else if (message != null) {
            try {
                email.setText(message.getFrom()[0].toString());
                subject.setText(message.getSubject());
                this.msg = message;
                //check if it is multipart
                if (message.getContent() instanceof String) {
                    preview.setText(((String) message.getContent()).substring(0, 20));
                } else {
                    Multipart multipart = (Multipart) message.getContent();
                    if (multipart.getCount() > 0) {
                        String msg = multipart.getBodyPart(0).getContent().toString();
                        preview.setText(msg);
                    }
                }
            } catch (MessagingException e) {
                System.out.println("Messaging Exception");
            } catch (IOException e) {
                System.out.println("IOException");
            }
        }
    }

    /**
     * opens popup window prefilled with email when email from inbox is clicked
     * @throws IOException
     */
    @FXML
    private void openEmail() throws IOException {
        System.out.println("Attempting to open email");
        try {
            OpenEmailWindow cew = new OpenEmailWindow(this.email.getText(),
                    this.subject.getText(), this.msg);
        } catch (IOException e) {
            System.out.println("IOException");
        }
    }
}

