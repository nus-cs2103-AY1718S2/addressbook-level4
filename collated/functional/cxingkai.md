# cxingkai
###### \java\seedu\address\logic\commands\LoginCommand.java
``` java
package seedu.address.logic.commands;

import javafx.stage.Stage;
import seedu.address.logic.login.LoginManager;
import seedu.address.logic.login.LoginWindow;

/**
 * Allows user to login to the system
 */
public class LoginCommand extends Command {
    public static final String COMMAND_WORD = "login";
    public static final String COMMAND_ALIAS = "lg";

    public static final String MESSAGE_ALREADY_LOGGED_IN = "Already logged in";
    public static final String MESSAGE_LOGIN_CANCEL = "Login cancelled.";
    public static final String MESSAGE_LOGIN_SUCCESS = "Successfully logged in as ";

    public static final String MESSAGE_NOT_LOGGED_IN = "Not logged in!\n%1$s";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Allows user to login to the system "
            + "given the correct username and password.\n"
            + "Example: " + COMMAND_WORD;

    public static final String TEST_USERNAME = "alice";
    public static final String TEST_PASSWORD = "password123";
    public static final String FAKE_PASSWORD = "fake_password";

    @Override
    public CommandResult execute() {
        LoginWindow loginWindow = new LoginWindow();
        Stage stage = new Stage();
        loginWindow.start(stage);

        return new CommandResult(processResultString());
    }

    @Override
    public boolean equals (Object other) {
        return other == this // short circuit if same object
                || (other instanceof LoginCommand); // instanceof handles nulls
    }

    /**
     * Creates output String for user feedback
     */
    public String processResultString() {
        String userRole = null;
        if (LoginManager.getUserState() == LoginManager.DOCTOR_LOGIN) {
            userRole = "Doctor";
        } else if (LoginManager.getUserState() == LoginManager.MEDICAL_STAFF_LOGIN) {
            userRole = "Staff";
        } else {
            return MESSAGE_LOGIN_CANCEL;
        }

        return MESSAGE_LOGIN_SUCCESS + LoginManager.getUserName() + " (" + userRole + ")";
    }
}
```
###### \java\seedu\address\logic\commands\LogoutCommand.java
``` java
package seedu.address.logic.commands;

import seedu.address.logic.login.LoginManager;

/**
 * Logs user out of the system
 */
public class LogoutCommand extends Command {
    public static final String COMMAND_WORD = "logout";
    public static final String SUCCESS_MESSAGE = "Successfully logged out of IMDB.";

    @Override
    public CommandResult execute() {
        LoginManager.logout();
        return new CommandResult(SUCCESS_MESSAGE);
    }

    @Override
    public boolean equals (Object other) {
        return other == this // short circuit if same object
                || (other instanceof LogoutCommand); // instanceof handles nulls
    }
}
```
###### \java\seedu\address\logic\commands\PrintCommand.java
``` java
package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.PrintFormatter;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.patient.Patient;

/**
 * Selects a patient identified using it's last displayed index from the address book.
 * Formats and prints that patient's medical records.
 */
public class PrintCommand extends Command {
    public static final String COMMAND_WORD = "print";
    public static final String COMMAND_ALIAS = "p";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Formats and prints out a patient's "
            + "medical records.\n"
            + "Example: " + COMMAND_WORD + " INDEX";

    private static PrintFormatter printFormatter;

    private final Index targetIndex;

    private Patient patient;

    public PrintCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<Patient> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        this.patient = lastShownList.get(targetIndex.getZeroBased());
        this.printFormatter = new PrintFormatter(this.patient);

        return new CommandResult("Printed records of patient " + patient.getName().toString());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PrintCommand // instanceof handles nulls
                && this.targetIndex.equals(((PrintCommand) other).targetIndex)); // state check
    }
}
```
###### \java\seedu\address\logic\login\LoginController.java
``` java
package seedu.address.logic.login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Controller for LoginLayout. Handles button press.
 */
public class LoginController {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Text messageText;

    /**
     * Authenticates input username and password when login button is clicked
     */
    @FXML
    protected void handleButtonAction(ActionEvent event) {
        String username = this.usernameField.getText();
        String password = this.passwordField.getText();
        if (username.equals("") || password.equals("")) {
            messageText.setText("Please fill in both fields.");
        } else {
            if (LoginManager.authenticate(username, password)) {
                // success
                Stage stage = (Stage) usernameField.getScene().getWindow();
                stage.close();
            } else {
                messageText.setText("Username and password do not match!");
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
        }
    }

    /**
     * Authenticates input username and password when enter button is pressed
     */
    @FXML
    protected void handleEnterKey(KeyEvent event) {
        String username = this.usernameField.getText();
        String password = this.passwordField.getText();
        if (username.equals("") || password.equals("")) {
            messageText.setText("Please fill in both fields.");
        } else {
            if (LoginManager.authenticate(username, password)) {
                // success
                Stage stage = (Stage) usernameField.getScene().getWindow();
                stage.close();
            } else {
                messageText.setText("Username and password do not match!");
            }
        }
    }
}
```
###### \java\seedu\address\logic\login\LoginManager.java
``` java
package seedu.address.logic.login;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import seedu.address.MainApp;

/**
 * LoginManager class to store login state and handle login attempts
 */
public final class LoginManager {
    public static final int NO_USER_STATE = 0;
    public static final int DOCTOR_LOGIN = 1;
    public static final int MEDICAL_STAFF_LOGIN = 2;
    public static final String NO_USER_STRING = null;

    private static LoginState currLoginState = new LoginState(NO_USER_STATE, NO_USER_STRING);
    private static String passwordPath = "/data/passwords.csv";

    private LoginManager() {
        currLoginState = new LoginState(NO_USER_STATE, NO_USER_STRING);
    }

    public static int getUserState() {
        return currLoginState.getState();
    }

    public static String getUserName() {
        return currLoginState.getUser();
    }

    /**
     * Utility function for tests
     */
    public static void logout() {
        currLoginState.updateState(NO_USER_STATE, NO_USER_STRING);
    }

    /**
     * Check if username and password match and are in the passwords list.
     * Login state will be updated.
     */
    public static boolean authenticate (String username, String password) {
        boolean match = false;
        int loginStateIndex = 0;

        try {
            InputStreamReader isr = new InputStreamReader(MainApp.class.getResourceAsStream(passwordPath));
            BufferedReader br = new BufferedReader(isr);

            String line;
            while ((line = br.readLine()) != null) {
                String[] lineArray = line.split(",");
                String fileUsername = lineArray[0];
                String filePassword = lineArray[1];

                if (username.equals(fileUsername) && password.equals(filePassword)) {
                    int role = Integer.parseInt(lineArray[2]);
                    loginStateIndex = role;
                    match = true;
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        if (match) {
            currLoginState.updateState(loginStateIndex, username);
            return true;
        }

        return false;
    }
}
```
###### \java\seedu\address\logic\login\LoginState.java
``` java
package seedu.address.logic.login;

/**
 * Stores login state
 */
public class LoginState {

    private int state;
    private String user;

    LoginState(int state, String user) {
        this.state = state;
        this.user = user;
    }

    /**
     * Sets the user and user role
     */
    public void updateState(int newState, String user) {
        this.state = newState;
        this.user = user;
    }

    public int getState() {
        return state;
    }

    public String getUser() {
        return user;
    }
}
```
###### \java\seedu\address\logic\login\LoginWindow.java
``` java
package seedu.address.logic.login;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import seedu.address.MainApp;

/**
 * Class for initializing login popup GUI
 */
public class LoginWindow extends Application {

    private Stage primaryStage;
    private AnchorPane rootLayout;

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Opens a new window and starts up the login form
     */
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Login Window");

        // Prevent the window resizing
        this.primaryStage.setResizable(false);

        // initRootLayout();

        showLoginForm();
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/view/RootLayout.fxml"));
            rootLayout = loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializes the login form
     */
    public void showLoginForm() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/view/LoginLayout.fxml"));
            rootLayout = loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Returns the main stage.
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

}
```
###### \java\seedu\address\logic\parser\LoginCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.LoginCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new LoginCommand object
 */
public class LoginCommandParser implements Parser<LoginCommand> {
    public static final String SPACE_REGEX = "\\s+";

    /**
     * Parses the given {@code String} of arguments in the context of the LoginCommand
     * and returns an LoginCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public LoginCommand parse(String args) throws ParseException {
        if (!args.trim().equals("")) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoginCommand.MESSAGE_USAGE));
        }

        return new LoginCommand();
    }
}
```
###### \java\seedu\address\logic\parser\PrintCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.PrintCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new PrintCommand object
 */
public class PrintCommandParser implements Parser<PrintCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the PrintCommand
     * and returns an PrintCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public PrintCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new PrintCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, PrintCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\logic\PrintFormatter.java
``` java
package seedu.address.logic;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;

import seedu.address.model.patient.Patient;
import seedu.address.model.patient.Record;
import seedu.address.model.patient.RecordList;


/**
 * Prints the records of given patient to pdf format
 */
public class PrintFormatter {
    private static Document document;
    private static String filePath;
    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 20,
            Font.BOLD);
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
            Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.BOLD);
    private static Font smallNormal = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.NORMAL);

    public PrintFormatter(Patient patient) {
        initDocument(patient);
        document.open();
        addHeader();
        writePatientInfo(patient);
        writeRecords(patient);
        document.close();
        openPdf();
    }

    /**
     * Creates a new pdf document
     */
    private void initDocument(Patient patient) {
        filePath = patient.getName().toString() + " records.pdf";
        document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
        } catch (FileNotFoundException | DocumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds title of pdf document
     */
    private void addHeader() {
        Paragraph header = new Paragraph();
        header.setAlignment(Element.ALIGN_CENTER);
        header.add(new Chunk("Medical Records", catFont));
        header.add(Chunk.NEWLINE);
        header.add(Chunk.NEWLINE);

        try {
            document.add(header);
        } catch (DocumentException de) {
            de.printStackTrace();
        }
    }

    /**
     * Writes information of given patient at the top of the pdf document
     */
    private void writePatientInfo(Patient patient) {
        Paragraph paragraph = new Paragraph();
        paragraph.setAlignment(Element.ALIGN_LEFT);
        paragraph.add(new Chunk("Name: ", smallBold));
        paragraph.add(new Chunk(patient.getName().toString(), smallNormal));
        paragraph.add(Chunk.NEWLINE);

        paragraph.add(new Chunk("Blood Type: ", smallBold));
        paragraph.add(new Chunk(patient.getBloodType().toString(), smallNormal));
        paragraph.add(Chunk.NEWLINE);

        paragraph.add(new Chunk("Remarks: ", smallBold));
        paragraph.add(new Chunk(patient.getRemark().toString(), smallNormal));
        paragraph.add(Chunk.NEWLINE);

        DottedLineSeparator separator = new DottedLineSeparator();
        paragraph.add(new Chunk(separator));

        try {
            document.add(paragraph);
        } catch (DocumentException de) {
            de.printStackTrace();
        }
    }

    /**
     * Writes out records of given patient on the pdf document
     */
    private void writeRecords(Patient patient) {
        Paragraph paragraph = new Paragraph();
        paragraph.setAlignment(Element.ALIGN_LEFT);

        RecordList recordList = patient.getRecordList();
        int recordListSize = recordList.getNumberOfRecords();

        for (int recordIndex = 0; recordIndex < recordListSize; recordIndex++) {
            paragraph.add(Chunk.NEWLINE);

            Record record = recordList.getRecord(recordIndex);
            Chunk recordNumberChunk = new Chunk("Record #" + (recordIndex + 1));
            recordNumberChunk.setUnderline(0.1f, -2f);
            recordNumberChunk.setFont(subFont);
            paragraph.add(recordNumberChunk);
            paragraph.add(Chunk.NEWLINE);

            paragraph.add(new Chunk("Date recorded: ", smallBold));
            paragraph.add(new Chunk(record.getDate(), smallNormal));
            paragraph.add(Chunk.NEWLINE);

            paragraph.add(new Chunk("Symptoms: ", smallBold));
            paragraph.add(new Chunk(record.getSymptom(), smallNormal));
            paragraph.add(Chunk.NEWLINE);

            paragraph.add(new Chunk("Illness: ", smallBold));
            paragraph.add(new Chunk(record.getIllness(), smallNormal));
            paragraph.add(Chunk.NEWLINE);

            paragraph.add(new Chunk("Treatment: ", smallBold));
            paragraph.add(new Chunk(record.getTreatment(), smallNormal));
            paragraph.add(Chunk.NEWLINE);

            DottedLineSeparator separator = new DottedLineSeparator();
            paragraph.add(new Chunk(separator));
            paragraph.add(Chunk.NEWLINE);
        }

        try {
            document.add(paragraph);
        } catch (DocumentException de) {
            de.printStackTrace();
        }
    }

    /**
     * Opens Pdf that was created
     */
    private void openPdf() {
        if (Desktop.isDesktopSupported()) {
            try {
                File myFile = new File(filePath);
                Desktop.getDesktop().open(myFile);
            } catch (IOException ex) {
                // no application registered for PDFs
                ex.printStackTrace();
            }
        }
    }
}
```
###### \resources\view\LoginLayout.fxml
``` fxml
<AnchorPane maxHeight="-Infinity" maxWidth="-Infinity" minHeight="-Infinity" minWidth="-Infinity" prefHeight="200.0" prefWidth="300.0" xmlns="http://javafx.com/javafx/8.0.141" xmlns:fx="http://javafx.com/fxml/1" fx:controller="seedu.address.logic.login.LoginController">
   <children>
      <GridPane layoutX="16.0" layoutY="36.0" prefHeight="90.0" prefWidth="268.0">
        <columnConstraints>
          <ColumnConstraints hgrow="SOMETIMES" maxWidth="133.3333740234375" minWidth="10.0" prefWidth="89.3333740234375" />
          <ColumnConstraints hgrow="SOMETIMES" maxWidth="191.6666259765625" minWidth="10.0" prefWidth="185.6666259765625" />
        </columnConstraints>
        <rowConstraints>
          <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
          <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
        </rowConstraints>
         <children>
            <Text strokeType="OUTSIDE" strokeWidth="0.0" text="Username" />
            <Text strokeType="OUTSIDE" strokeWidth="0.0" text="Password" GridPane.rowIndex="1" />
            <TextField fx:id="usernameField" onKeyPressed="#handleKeyAction" prefHeight="25.0" prefWidth="175.0" GridPane.columnIndex="1" />
            <PasswordField fx:id="passwordField" onKeyPressed="#handleKeyAction" GridPane.columnIndex="1" GridPane.rowIndex="1" />
         </children>
      </GridPane>
      <Button layoutX="238.0" layoutY="140.0" mnemonicParsing="false" onAction="#handleButtonAction" text="Login" textAlignment="CENTER" />
      <Text fx:id="messageText" fill="#cc1919" layoutX="16.0" layoutY="157.0" strokeType="OUTSIDE" strokeWidth="0.0" />
   </children>
</AnchorPane>
```
