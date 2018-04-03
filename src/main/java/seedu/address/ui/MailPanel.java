package seedu.address.ui;

import java.io.IOException;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

import org.fxmisc.easybind.EasyBind;




/**
 * Mail panel
 */

public class MailPanel extends UiPart<Region> {
    private static final String FXML = "EmailPanel.fxml";

    @FXML
    private ListView<EmailCard> emailListView;

    public MailPanel() throws MessagingException, IOException {
        super(FXML);
        setConnections();
        registerAsAnEventHandler(this);
    }

    private void setConnections() {
        //gets the list of messages
        System.out.println("In set Connections");
        ObservableList<EmailCard> emailList = FXCollections.observableArrayList();
        //try {
        Message[] messages = messageList();
            /*emailList = FXCollections.observableArrayList();
            for (int i = 0; i < messages.length; i++){
                emailList.add(new EmailCard(messages[i], i));
                System.out.println(messages[i].getFrom());
            }
        } catch (MessagingException e) {
            System.out.println("Messaging exception @ mail panel");
        }*/
        /*ObservableList<EmailCard> mappedList = EasyBind.map(
                emailList, (msg) ->
                        new EmailCard(message, emailList.indexOf(msg) + 1));*/

        emailListView.setItems(emailList);
        emailListView.setCellFactory(listView -> new EmailListViewCell());

        /*ObservableList<AppointmentCard> mappedList = EasyBind.map(
                appointmentList, (appointment) ->
                        new EmailCard(appointment, appointmentList.indexOf(appointment) + 1));
        appointmentListView.setItems(mappedList);
        appointmentListView.setCellFactory(listView -> new AppointmentListViewCell());*/
        //setEventHandlerForSelectionChangeEvent();
    }

    /**
     * Returns a list of messages in the inbox
     * @return
     */
    public Message[] messageList() {
        String USERNAME = "sell.it.sg@gmail.com";
        String PASSWORD = "gloriacs2103";
        String HOST = "smtp.gmail.com";
        System.out.println("In message List");
        //empty Properties
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", HOST);
        props.put("mail.smtp.port", "465");
        //get session
        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        };
        System.out.println("Authenticate Worked");
        Session session = Session.getDefaultInstance(props, auth);
        System.out.println("Got my session");
        //get the store to view messages
        try {
            Store store = session.getStore("smtp");
            System.out.println("store works");
            store.connect(HOST, USERNAME, PASSWORD);
            System.out.println("store connected");
            Folder folder = store.getFolder("INBOX");
            folder.open(Folder.READ_ONLY);
            //gets & returns messages
            System.out.println("right before messages");
            Message[] messages = folder.getMessages();
            System.out.println("right after messages");
            System.out.println(messages[0].getFrom());
            folder.close();
            store.close();
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
