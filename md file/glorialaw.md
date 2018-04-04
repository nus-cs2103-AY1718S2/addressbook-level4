# glorialaw
###### \out\production\resources\view\EmailCard.fxml
``` fxml
<HBox id="cardPane" fx:id="cardPane" xmlns="http://javafx.com/javafx/9.0.1" xmlns:fx="http://javafx.com/fxml/1">
    <GridPane prefHeight="104.0" prefWidth="343.0" HBox.hgrow="ALWAYS">
        <columnConstraints>
            <ColumnConstraints hgrow="SOMETIMES" minWidth="10" prefWidth="150" />
        </columnConstraints>
        <VBox alignment="CENTER_LEFT" minHeight="103.0" prefHeight="104.0" prefWidth="229.0" GridPane.columnIndex="0">
            <padding>
                <Insets bottom="5" left="15" right="5" top="5" />
            </padding>
            <HBox alignment="CENTER_LEFT" prefHeight="20.0" prefWidth="323.0" spacing="5">
                <Label fx:id="name" styleClass="cell_big_label" text="\$first">
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
###### \out\production\resources\view\EmailPanel.fxml
``` fxml
<?xml version="1.0" encoding="UTF-8"?>

<?import javafx.scene.control.ListView?>
<?import javafx.scene.layout.VBox?>

<VBox xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1">
    <ListView fx:id="emailListView" VBox.vgrow="ALWAYS" />
</VBox>
```
###### \src\main\java\seedu\address\model\person\CustTimeZone.java
``` java
/**
 * Represents a person's timezone in the address book.
 */

public class CustTimeZone {

    public static final String MESSAGE_TIMEZONE_CONSTRAINTS =
            "Time zones should be have the standard time zone abbreviations, and should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String TIMEZONE_VALIDATION_REGEX = "[^\\s].*";

    //will convert from string to TimeZone when calendar is implemented
    public final String timeZone;

    public CustTimeZone(String timeZone) {
        requireNonNull(timeZone);
        checkArgument(isValidTimeZone(timeZone), MESSAGE_TIMEZONE_CONSTRAINTS);
        this.timeZone = timeZone;
    }

    /**
     * Returns true if a given string is a valid time zone.
     */
    public static boolean isValidTimeZone(String test) {
        return test.matches(TIMEZONE_VALIDATION_REGEX);
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
###### \src\main\java\seedu\address\ui\EmailCard.java
``` java
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

```
###### \src\main\java\seedu\address\ui\MailPanel.java
``` java
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
```
###### \src\main\java\seedu\address\ui\MailServer.java
``` java
public class MailServer {
    private static String USERNAME = "sell.it.sg";
    private static String PASSWORD = "gloriacs2103";
    private static String HOST = "smtp.gmail.com";

    public MailServer( String[] recipient, String subject, String msg ){
        String sender = USERNAME;
        String pwd = PASSWORD;

        sendEmail( sender, pwd, recipient, subject, msg );
    }

    /**
     * Puts together the email message and sends it
     * @param sender - email the program is using
     * @param pwd - password of the email account
     * @param recipients - the addresses the message is sent to
     * @param subject - user chooses subject
     * @param msg - contents the user types
     */
    private static void sendEmail(String sender, String pwd, String[] recipients, String subject, String msg) {
        Properties props = System.getProperties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", HOST);
        props.put("mail.smtp.user", sender);
        props.put("mail.smtp.password", pwd);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(sender, pwd);
            }
        };
        Session session = Session.getDefaultInstance(props, auth);



        //create the message
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(sender));
            for( String recipient : recipients ) {
                message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            }
            message.setSubject(subject);
            message.setText(msg);
            //send the message
            Transport.send(message);

        } catch ( MessagingException e ) {
            System.out.println("Messaging Exception Detected");
        }

    }


}
```
