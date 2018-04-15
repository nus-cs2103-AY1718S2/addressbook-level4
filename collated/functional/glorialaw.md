# glorialaw
###### /java/seedu/address/model/person/CustTimeZone.java
``` java
public class CustTimeZone {

    public static final String MESSAGE_TIMEZONE_CONSTRAINTS =
            "Time zones should be have the standard time zone abbreviations, and should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    //will convert from string to TimeZone when calendar is implemented
    public final String timeZone;

    public CustTimeZone(String timeZone) {
        requireNonNull(timeZone);
        checkArgument(isValidTimeZone(timeZone), MESSAGE_TIMEZONE_CONSTRAINTS);
        this.timeZone = timeZone;
    }

    /**
     * Returns true if a given string is a valid time zone. Some short abbreviations are not supported such as SGT.
     * Corrected with the if statement.
     */
    public static boolean isValidTimeZone(String test) {
        if (test.toUpperCase().equals("SGT")) {
            test = "Asia/Singapore";
        }
        return Arrays.asList(TimeZone.getAvailableIDs()).contains(test);
    }

    @Override
    public String toString() {
        return timeZone; }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof CustTimeZone
                && this.timeZone.equals(((CustTimeZone) other).timeZone));
    }

    @Override
    public int hashCode() {
        return timeZone.hashCode(); }

}
```
###### /java/seedu/address/ui/MailServer.java
``` java
public class MailServer {
    private static String username = "sg.salesperson@gmail.com";
    private static String password = "gloriacs2103";
    private static String host = "smtp.gmail.com";

    public MailServer(String[] recipient, String subject, String msg) throws MessagingException {
        sendEmail(recipient, subject, msg);
    }

    /**
     * starts a new smtp session
     */
    public static Session startSession() {
        Properties props = System.getProperties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", username);
        props.put("mail.smtp.password", password);
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        };
        Session session = Session.getInstance(props, auth);
        return session;
    }

    /**
     * Puts together the email message and sends it
     *
     * @param recipients - the addresses the message is sent to
     * @param subject    - user chooses subject
     * @param msg        - contents the user types
     */
    private static void sendEmail(String[] recipients, String subject, String msg) throws MessagingException {
        Session session = startSession();
        //create the message
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        for (String recipient : recipients) {
            message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
        }
        message.setSubject(subject);
        message.setText(msg);
        //send the message
        Transport.send(message);
    }
}
```
###### /java/seedu/address/ui/ComposeEmailWindow.java
``` java
/**
 * makes the compose email window
 */
public class ComposeEmailWindow {
    private static final String composeWindow = "/view/emailcompose.fxml";
    private static final String successWindow = "/view/successwindow.fxml";
    private Stage puWindow = new Stage();
    private String content;

    @FXML
    private AnchorPane successPopup;

    @FXML
    private AnchorPane emailComposePopup;

    @FXML
    private TextField recipients;

    @FXML
    private TextField subject;

    @FXML
    private TextArea message;

    @FXML
    private Button sendButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Button closeButton;

    @FXML
    private Label results;

    /**
     * Creates a new Email compose window
     */
    public ComposeEmailWindow(String type, String email, String sub, String content)
            throws IOException, SyntaxException {
        //get URL
        FXMLLoader fxmlLoader = loadScene(composeWindow);
        Parent root = (Parent) fxmlLoader.load();
        recipients.setText(email);
        subject.setText(type + sub);
        if (content != "") {
            addPreviousEmail(type, email, sub, content);
        }
        puWindow.initModality(Modality.APPLICATION_MODAL);
        puWindow.initStyle(StageStyle.UNDECORATED);
        puWindow.setTitle("Compose Email");
        //puWindow.getScene().setRoot(root);
        //this.main.setRoot(root);
        puWindow.setScene(new Scene(root));
        puWindow.show();
    }

    /**
     * Shows the message that user is trying to reply to or forward
     * @param type indicates whether or not it is a forwarded email or reply
     * @param email is the person that sent the previous email
     * @param subject the subject of the previous email
     * @param content the contents of the previous email
     */
    private void addPreviousEmail(String type, String email, String subject, String content) {
        String previousMessage = "\n\n\n";
        if (type == "RE: ") {
            previousMessage = previousMessage + "-----Original Message-----\n";
        } else {
            previousMessage = previousMessage + "Begin Forwarded Message: \n\n";
        }
        previousMessage = previousMessage + "From: " + email + "\n"
                + "To: sg.salesperson@gmail.com\n"
                + "Subject: " + subject + "\n\n"
                + content + "\n\n\n";
        message.setText(previousMessage);
    }
    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    public void sendEmail() throws IOException {
        FXMLLoader fxmlLoader = loadScene(successWindow);
        Parent confirm = (Parent) fxmlLoader.load();
        try {
            new MailServer(parseRecipients(), subject.getText(), message.getText());
            results.setText("Email Sent Successfully");
            results.setTextFill(Color.web("#4cc486"));
        } catch (MessagingException e) {
            results.setText("Could not send email due to error.");
            results.setTextFill(Color.web("Red"));
            e.getStackTrace();
        }
        puWindow.getScene().setRoot(confirm);
        puWindow.show();
    }

    private String[] parseRecipients() {
        System.out.println(recipients.getText());
        return recipients.getText().split(",");
    }

    /**
     * Closes window with ESC key
     */
    @FXML
    private void handleKeyPress(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
        case ESCAPE:
            puWindow.close();
            break;
        default:
            // let JavaFx handle the keypress
        }
    }

    @FXML
    private void cancelWindow() {
        puWindow.close();
    }

    /**
     *loads the scene
     */
    private FXMLLoader loadScene (String fxml) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL fxmlFileUrl = MainApp.class.getResource(fxml);
        fxmlLoader.setLocation(fxmlFileUrl);
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(null);
        return fxmlLoader;
    }
}
```
###### /java/seedu/address/ui/EmailCard.java
``` java
public class EmailCard extends UiPart<Region> {
    private static final String FXML = "EmailCard.fxml";

    @FXML
    private HBox emailCardPane;

    @FXML
    private Label email;

    @FXML
    private Label subject;

    @FXML
    private Label preview;

    private Message msg;

    public EmailCard(Message message) {
        super(FXML);
        String errorMsg = "Please ensure that you are connected to the internet.";
        if (message == null) {
            email.setText("unknown@unknown.com");
            subject.setText("Error: Unable to retrieve message");
            preview.setText(errorMsg);
            this.msg = null;
        } else if (message != null) {
            try {
                email.setText(message.getFrom()[0].toString());
                subject.setText(message.getSubject());
                this.msg = message;
                //check if it is multipart
                if (message.getContent() instanceof String) {
                    String content = (String) message.getContent();
                    preview.setText(((String) message.getContent()).substring(0, min(20, content.length())));
                } else if (message.isMimeType("multipart/*")) {
                    Multipart multipart = (Multipart) message.getContent();
                    if (multipart.getCount() > 0) {
                        String msg = multipart.getBodyPart(0).getContent().toString();
                        preview.setText(msg.substring(0, min(20, msg.length())));
                    }
                } else if (message.isMimeType("message/rfc822")) {
                    Part part = (Part) message.getContent();
                    try {
                        if (part.getContent() instanceof String) {
                            String content = (String) part.getContent();
                            preview.setText(((String) part.getContent()).substring(0, min(20, content.length())));
                        } else if (part.isMimeType("multipart/*")) {
                            Multipart multipart = (Multipart) part.getContent();
                            if (multipart.getCount() > 0) {
                                String msg = multipart.getBodyPart(0).getContent().toString();
                                preview.setText(msg.substring(0, min(20, msg.length())));
                            }

                        } else {
                            preview.setText("This message is not in a supported format.");
                        }
                    } catch (MessagingException e) {
                        System.out.println("Messaging Exception");
                    } catch (IOException e) {
                        System.out.println("IOException");
                    }
                } else {
                    preview.setText("This message is not in a supported format.");
                }
            } catch (MessagingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * opens popup window prefilled with email when email from inbox is clicked
     * @throws IOException
     */
    @FXML
    private void openEmail() throws IOException {
        try {
            OpenEmailWindow cew = new OpenEmailWindow(this.email.getText(),
                    this.subject.getText(), this.msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

```
###### /java/seedu/address/ui/MailPanel.java
``` java
public class MailPanel extends UiPart<VBox> {
    private static final long freq = 30;
    private static final String FXML = "EmailPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(MailPanel.class);
    private final ObservableList<EmailCard> emailList = FXCollections.observableArrayList();
    private int messageCount = 0;
    private final IMAPFolder inbox = getInbox();

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
                MailPanel.this.getRoot().getChildren().remove(loading);
            }

        };

        new Thread(fetchEmails).start();
        ScheduledExecutorService runChecks = Executors.newScheduledThreadPool(1);
        Runnable rc = new Runnable() {
            @Override
            public void run() {
                Platform.setImplicitExit(false);
                refreshMessages();
            }
        };
        runChecks.scheduleAtFixedRate(rc, freq, freq, TimeUnit.SECONDS);
        registerAsAnEventHandler(this);
    }

    private void setConnections() {
        loading.setText("Loading Emails...");
        //gets the list of messages
        int length = 0;
        Message[] messages = messageList();
        if (messages != null) {
            length = messages.length;
            messageCount = length;
            for (int i = 0; i < length; i++) {
                emailList.add(new EmailCard(messages[i]));
            }
        } else if (messages == null) {
            emailList.add(new EmailCard(null));
        }
        FXCollections.reverse(emailList);
        emailListView.setItems(emailList);
        emailListView.setCellFactory(listView -> new EmailListViewCell());

    }

    /**
     * Returns a list of messages in the inbox
     * @return list of messages
     */
    public Message[] messageList() {
        if (inbox != null) {
            try {
                inbox.open(Folder.READ_ONLY);
                //gets & returns messages
                Message[] messages = inbox.getMessages();
                inbox.addMessageCountListener(new MessageCountAdapter() {
                    public void messagesAdded(MessageCountEvent mce) {
                        //System.out.println("Listener finally worked");
                        Platform.runLater(() -> {
                            Message[] newMessages = mce.getMessages();
                            FXCollections.reverse(emailList);
                            for (Message message : newMessages) {
                                emailList.add(new EmailCard(message));
                            }
                            FXCollections.reverse(emailList);
                        });
                    }
                });
                return messages;
            } catch (NoSuchProviderException e) {
                return null;
            } catch (MessagingException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    /**
     * Refreshes the inbox so that the contents can be updated
     * @return
     */
    @FXML
    private void refreshMessages() {
        //System.out.println("Inside refresh messages");
        try {
            if (!inbox.isOpen()) {
                inbox.open(Folder.READ_ONLY);
                inbox.idle();
            }
        } catch (MessagingException me) {
            me.getCause();
        }

    }

    private IMAPFolder getInbox() {
        String username = "sg.salesperson@gmail.com";
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
            return (IMAPFolder) inbox;
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
            return null;
        } catch (MessagingException e) {
            System.out.println(e.getCause());
            return null;
        }
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
```
###### /java/seedu/address/ui/OpenEmailWindow.java
``` java
/**
 * Generates the full email that was received
 */
public class OpenEmailWindow {
    private static final String openWindow = "/view/emailOpen.fxml";
    private Stage puWindow = new Stage();

    @FXML
    private AnchorPane emailComposePopup;

    @FXML
    private Label fromContent;

    @FXML
    private Label subjectContent;

    @FXML
    private TextArea msgContent;

    @FXML
    private Button newEmailButton;

    @FXML
    private Button replyButton;

    @FXML
    private Button forwardButton;

    @FXML
    private Button closeButton;

    /**
     * Open the email message window
     */
    public OpenEmailWindow(String email, String subject, Message msg) throws IOException, SyntaxException {
        String errorMsg = "Please ensure that you are connected to the internet.";
        //get URL
        FXMLLoader fxmlLoader = loadScene(openWindow);
        Parent root = (Parent) fxmlLoader.load();
        fromContent.setText(email);
        subjectContent.setText(subject);
        if (msg != null) {
            setContent(msg);
        } else {
            msgContent.setText(errorMsg);
        }
        puWindow.initModality(Modality.APPLICATION_MODAL);
        puWindow.initStyle(StageStyle.UNDECORATED);
        puWindow.setTitle("Compose Email");
        puWindow.setScene(new Scene(root));
        puWindow.show();
    }

    /**
     * Parses the message content into a string.
     * @param message is the email message packet
     */
    private void setContent (Message message) {
        String content = "";
        try {
            if (message.getContent() instanceof String) {
                content = (String) message.getContent();
                msgContent.setText(content);
            } else if (message.isMimeType("multipart/*")) {
                Multipart multipart = (Multipart) message.getContent();
                if (multipart.getCount() > 0) {
                    int i;
                    for (i = 0; i < multipart.getCount(); i++) {
                        content += multipart.getBodyPart(i).getContent().toString();
                    }
                    msgContent.setText(content);
                }
            } else {
                msgContent.setText("This message is in an unsupported format.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    /**
     * Closes window with ESC key
     */
    @FXML
    private void handleKeyPress(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
        case ESCAPE:
            puWindow.close();
            break;
        default:
            // let JavaFx handle the keypress
        }
    }

    /**
     * opens a new email window
     */
    @FXML
    private void openComposeWindow() {
        try {
            ComposeEmailWindow cew = new ComposeEmailWindow("", "", "", "");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * opens a new reply email window
     */
    @FXML
    private void openReplyWindow() {
        try {
            ComposeEmailWindow cew = new ComposeEmailWindow("RE: ", this.fromContent.getText(),
                    this.subjectContent.getText(), this.msgContent.getText());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * opens a new forward email window
     */
    @FXML
    private void openForwardWindow() {
        try {
            ComposeEmailWindow cew = new ComposeEmailWindow("FWD: ", "",
                     this.subjectContent.getText(), this.msgContent.getText());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void closeWindow () {
        puWindow.close();
    }

    /**
     *loads the scene
     */
    private FXMLLoader loadScene (String fxml) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL fxmlFileUrl = MainApp.class.getResource(fxml);
        fxmlLoader.setLocation(fxmlFileUrl);
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(null);
        return fxmlLoader;
    }

}
```
###### /java/seedu/address/ui/PersonCard.java
``` java
    /**
     * opens email sender when email is pushed
     * @throws IOException
     */
    @FXML
    private void writeEmail() throws IOException {
        try {
            ComposeEmailWindow cew = new ComposeEmailWindow("", this.email.getText(), "", "");
        } catch (IOException e) {
            System.out.println("IOException");
        }
    }

```
###### /resources/view/EmailPanel.fxml
``` fxml

<?import javafx.scene.control.ListView?>
<?import javafx.scene.layout.VBox?>
<?import javafx.scene.control.Label?>

<VBox xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1">
    <Label fx:id="loading" />
    <ListView fx:id="emailListView" VBox.vgrow="ALWAYS" />
</VBox>
```
###### /resources/view/emailOpen.fxml
``` fxml

<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Button?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.control.TextArea?>
<?import javafx.scene.control.ToolBar?>
<?import javafx.scene.layout.AnchorPane?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.RowConstraints?>
<?import javafx.scene.text.Text?>

<AnchorPane fx:id="emailComposePopup" onKeyPressed="#handleKeyPress" prefHeight="400.0" prefWidth="600.0" xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1">
    <children>
        <GridPane layoutX="-7.0" layoutY="41.0" prefHeight="379.0" prefWidth="600.0">
            <columnConstraints>
                <ColumnConstraints hgrow="SOMETIMES" maxWidth="499.3333206176758" minWidth="10.0" prefWidth="64.33333714803061" />
                <ColumnConstraints hgrow="SOMETIMES" maxWidth="568.6666831970215" minWidth="10.0" prefWidth="462.6666870117187" />
            </columnConstraints>
            <rowConstraints>
                <RowConstraints maxHeight="127.99999237060547" minHeight="9.333323001861572" prefHeight="30.333338419596345" vgrow="SOMETIMES" />
                <RowConstraints maxHeight="257.6666769981384" minHeight="3.3333435058594034" prefHeight="30.66665649414061" vgrow="SOMETIMES" />
                <RowConstraints maxHeight="335.6666564941406" minHeight="10.0" prefHeight="292.00001017252606" vgrow="SOMETIMES" />
            </rowConstraints>
            <children>
                <Label prefHeight="17.0" prefWidth="41.0" text="From:" />
                <Label text="Subject:" GridPane.rowIndex="1" />
                <Label text="Message:" GridPane.rowIndex="2" />
            <Label fx:id="fromContent" prefHeight="17.0" prefWidth="492.0" text="Label" wrapText="true" GridPane.columnIndex="1" />
            <Label fx:id="subjectContent" prefHeight="17.0" prefWidth="507.0" text="Label" wrapText="true" GridPane.columnIndex="1" GridPane.rowIndex="1" />
            <TextArea fx:id="msgContent" blendMode="DARKEN" editable="false" prefHeight="200.0" prefWidth="200.0" wrapText="true" GridPane.columnIndex="1" GridPane.rowIndex="2" />
            </children>
            <padding>
                <Insets bottom="10.0" left="10.0" right="10.0" top="10.0" />
            </padding>
        </GridPane>
        <Text strokeType="OUTSIDE" strokeWidth="0.0" text="Text" />
      <ToolBar layoutY="4.0" prefHeight="47.0" prefWidth="600.0" style="-fx-background-color: white;">
        <items>
            <Button fx:id="newEmailButton" mnemonicParsing="false" onMouseClicked="#openComposeWindow" text="New Email" />
            <Button fx:id="replyButton" mnemonicParsing="false" onMouseClicked="#openReplyWindow" text="Reply" />
            <Button fx:id="forwardButton" mnemonicParsing="false" onMouseClicked="#openForwardWindow" text="Forward" />
            <Button fx:id="closeButton" mnemonicParsing="false" onMouseClicked="#closeWindow" text="Close" />
        </items>
         <padding>
            <Insets left="20.0" right="20.0" />
         </padding>
      </ToolBar>
    </children>
</AnchorPane>
```
###### /resources/view/emailcompose.fxml
``` fxml

<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Button?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.control.TextArea?>
<?import javafx.scene.control.TextField?>
<?import javafx.scene.layout.AnchorPane?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.RowConstraints?>
<?import javafx.scene.layout.VBox?>
<?import javafx.scene.text.Text?>

<AnchorPane fx:id="emailComposePopup" onKeyPressed="#handleKeyPress" prefHeight="400.0" prefWidth="600.0" xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1">
   <children>
      <GridPane layoutX="-7.0" prefHeight="420.0" prefWidth="600.0">
        <columnConstraints>
          <ColumnConstraints hgrow="SOMETIMES" maxWidth="499.3333206176758" minWidth="10.0" prefWidth="64.33333714803061" />
            <ColumnConstraints hgrow="SOMETIMES" maxWidth="568.6666831970215" minWidth="10.0" prefWidth="462.6666870117187" />
            <ColumnConstraints hgrow="SOMETIMES" maxWidth="568.6666831970215" minWidth="10.0" prefWidth="57.99997965494788" />
        </columnConstraints>
        <rowConstraints>
          <RowConstraints maxHeight="127.99999237060547" minHeight="9.333323001861572" prefHeight="9.333323001861572" vgrow="SOMETIMES" />
          <RowConstraints maxHeight="257.6666769981384" minHeight="3.3333435058594034" prefHeight="28.00001525878909" vgrow="SOMETIMES" />
          <RowConstraints maxHeight="335.6666564941406" minHeight="10.0" prefHeight="310.99998474121094" vgrow="SOMETIMES" />
        </rowConstraints>
         <children>
            <TextField id="recipients" fx:id="recipients" prefHeight="25.0" prefWidth="473.0" promptText="separate recipients with ," GridPane.columnIndex="1">
               <padding>
                  <Insets bottom="5.0" left="5.0" right="5.0" top="5.0" />
               </padding>
            </TextField>
            <Label text="To:" />
            <Label text="Subject:" GridPane.rowIndex="1" />
            <TextField id="subject" fx:id="subject" promptText="enter subject..." GridPane.columnIndex="1" GridPane.rowIndex="1" />
            <TextArea id="message" fx:id="message" prefHeight="200.0" prefWidth="200.0" promptText="your message here..." GridPane.columnIndex="1" GridPane.rowIndex="2" />
            <Label text="Message:" GridPane.rowIndex="2" />
            <VBox alignment="CENTER" prefHeight="232.0" prefWidth="59.0" GridPane.columnIndex="2" GridPane.halignment="CENTER" GridPane.rowIndex="2" GridPane.valignment="CENTER">
               <children>
                  <Button fx:id="sendButton" alignment="CENTER" contentDisplay="CENTER" defaultButton="true" mnemonicParsing="false" onAction="#sendEmail" prefHeight="25.0" prefWidth="51.0" text="Send" />
                  <Button fx:id="cancelButton" mnemonicParsing="false" onMouseClicked="#cancelWindow" text="Cancel" />
               </children>
               <GridPane.margin>
                  <Insets />
               </GridPane.margin>
            </VBox>
         </children>
         <padding>
            <Insets bottom="10.0" left="10.0" right="10.0" top="10.0" />
         </padding>
      </GridPane>
      <Text strokeType="OUTSIDE" strokeWidth="0.0" text="Text" />
   </children>
</AnchorPane>
```
###### /resources/view/successwindow.fxml
``` fxml

<?import javafx.scene.control.Button?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.layout.AnchorPane?>
<?import javafx.scene.layout.Pane?>

<AnchorPane fx:id="successPopup" onKeyPressed="#handleKeyPress" prefHeight="400.0" prefWidth="600.0" style="-fx-background-color: white;" xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1">
   <children>
      <Pane layoutX="200.0" layoutY="89.0" prefHeight="200.0" prefWidth="200.0">
         <children>
            <Label fx:id="results" layoutX="-7.0" layoutY="-7.0" prefHeight="215.0" prefWidth="217.0" text="Label" wrapText="true" />
         </children>
      </Pane>
      <Button fx:id="closeButton" layoutX="537.0" layoutY="14.0" mnemonicParsing="false" onAction="#cancelWindow" text="return" />
   </children>
</AnchorPane>
```
###### /resources/view/EmailCard.fxml
``` fxml

<HBox id="cardPane" fx:id="cardPane" onMouseClicked="#openEmail" xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1">
    <GridPane HBox.hgrow="ALWAYS">
        <columnConstraints>
            <ColumnConstraints hgrow="SOMETIMES" minWidth="10" prefWidth="150" />
        </columnConstraints>
        <VBox alignment="CENTER_LEFT" minHeight="103.0" prefHeight="104.0" prefWidth="229.0" GridPane.columnIndex="0">
            <padding>
                <Insets bottom="5" left="15" right="5" top="5" />
            </padding>
            <HBox alignment="CENTER_LEFT" prefHeight="20.0" prefWidth="323.0" spacing="5">
                <Label fx:id="email" styleClass="cell_big_label" text="\\$sender">
               <font>
                  <Font name="System Bold" size="14.0" />
               </font></Label>
            </HBox>
            <Label fx:id="subject" prefHeight="17.0" prefWidth="324.0" styleClass="cell_small_label" text="\$subject">
            <font>
               <Font name="System Bold" size="12.0" />
            </font></Label>
         <Label fx:id="preview" prefHeight="51.0" prefWidth="321.0" text="\$preview" wrapText="true" />
        </VBox>
      <rowConstraints>
         <RowConstraints />
      </rowConstraints>
    </GridPane>
</HBox>
```
