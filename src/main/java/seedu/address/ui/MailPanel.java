package seedu.address.ui;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;

import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPStore;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;


/**
 * Mail panel
 */
//@@author glorialaw
public class MailPanel extends UiPart<VBox> {
    private static final String FXML = "EmailPanel.fxml";

    @FXML
    private Label loading;
    @FXML
    private ListView<EmailCard> emailListView;

    public MailPanel() throws MessagingException, IOException {
        super(FXML);

        Task<Void> fetchEmails = new Task<Void>() {
            @Override
            protected Void call() {
                setConnections();
                return null;
            }

            @Override
            protected void succeeded() {
                MailPanel.this.getRoot().getChildren().remove(0);
            }

        };

        new Thread(fetchEmails).start();

        registerAsAnEventHandler(this);
    }

    private void setConnections() {
        loading.setText("Loading Emails...");
        //gets the list of messages
        System.out.println("In set Connections");
        ObservableList<EmailCard> emailList = FXCollections.observableArrayList();
        //try {
        Message[] messages = messageList();
        for (int i = 0; i < messages.length; i++) {
            emailList.add(new EmailCard(messages[i], i));
        }
        emailListView.setItems(emailList);
        emailListView.setCellFactory(listView -> new EmailListViewCell());
    }

    /**
     * Returns a list of messages in the inbox
     * @return list of messages
     */
    public Message[] messageList() {
        String username = "sell.it.sg@gmail.com";
        String password = "gloriacs2103";
        String host = "imap.gmail.com";
        Properties props = new Properties();
        props.put("mail.store.protocol", "imaps");
        props.put("mail.imaps.host", host);
        props.put("mail.imaps.port", "993");
        props.put("mail.imaps.ssl.trust", host);
        props.put("mail.imaps.timeout", "10000");

        Session session = Session.getInstance(props);
        IMAPStore store = null;
        Folder inbox = null;

        try {
            store = (IMAPStore) session.getStore("imaps");
            store.connect(username, password);
            inbox = (IMAPFolder) store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);
            //gets & returns messages
            Message[] messages = inbox.getMessages();
            return messages;
        } catch (NoSuchProviderException e) {
            System.out.println("NoSuchProviderException");
        } catch (MessagingException e) {
            System.out.println("Caught MessagingException @MailPanel");
        }
        return null;
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code EmailCard}.
     */
    class EmailListViewCell extends ListCell<EmailCard> {

        @Override
        protected void updateItem(EmailCard email, boolean empty) {
            super.updateItem(email, empty);

            if (empty || email == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(email.getRoot());
            }
        }
    }

}
