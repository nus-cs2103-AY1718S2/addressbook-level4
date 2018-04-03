package seedu.address.ui;

import java.io.IOException;
import javax.mail.Message;
import javax.mail.MessagingException;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

/**
 * the card for the email panel
 */
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

    public EmailCard(Message message, int index) {
        super(FXML);
        try {
            email.setText(message.getFrom().toString());
            subject.setText(message.getSubject());
            preview.setText(message.getContent().toString().substring(0, 20));
        } catch ( MessagingException e ) {
            System.out.println("Messaging Exception");
        } catch ( IOException e ) {
            System.out.println("IOException");
        }
    }

}

