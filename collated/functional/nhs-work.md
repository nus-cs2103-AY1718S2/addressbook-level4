# nhs-work
###### \java\seedu\address\logic\commands\RecordCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.List;

import javafx.stage.Stage;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.record.RecordManager;
import seedu.address.logic.record.RecordWindow;
import seedu.address.model.patient.Patient;
import seedu.address.model.patient.Record;
import seedu.address.model.patient.RecordList;
import seedu.address.model.patient.exceptions.DuplicatePatientException;
import seedu.address.model.patient.exceptions.PatientNotFoundException;

/**
 * Adds a patient to the address book.
 */
public class RecordCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "record";
    public static final String COMMAND_ALIAS = "rec";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the medical record of a patient in the address book. "
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_INDEX + "INDEX] "
            + "\n"
            + "Example: " + COMMAND_WORD + " "
            + "1 "
            + PREFIX_INDEX + "1 ";

    public static final String MESSAGE_EDIT_RECORD_SUCCESS = "Medical record updated: %1$s";
    public static final String MESSAGE_ADD_RECORD_SUCCESS = "New medical record added: %1$s";
    public static final String MESSAGE_CLOSE_RECORD_SUCCESS = "Medical record has been closed with no changes.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This patient already exists in the address book.";

    private final Index patientIndex;
    private final Index recordIndex;

    private Patient patientToEdit;
    private Patient editedPatient;

    private boolean isTest;

    /**
     * Creates a RecordCommand to edit the records of the specified {@code Patient}
     */
    public RecordCommand(Index patientIndex, Index recordIndex) {
        requireNonNull(patientIndex);
        this.patientIndex = patientIndex;
        this.recordIndex = recordIndex;
        this.isTest = false;
    }

    /**
     * Creates a RecordCommand to edit the records of the specified {@code Patient}
     * This constructor is utilised when testing.
     */
    public RecordCommand(Index patientIndex, Index recordIndex, boolean isTest) {
        requireNonNull(patientIndex);
        this.patientIndex = patientIndex;
        this.recordIndex = recordIndex;
        this.isTest = isTest;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.updatePerson(patientToEdit, editedPatient);
        } catch (DuplicatePatientException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PatientNotFoundException pnfe) {
            throw new AssertionError("The target patient cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return generateCommandResult();
    }

    /**
     * Creates and returns a {@code CommandResult} with the details of {@code RecordCommand}
     * edited with {@code record}.
     */
    private CommandResult generateCommandResult() {
        if (editedPatient == patientToEdit) {
            return new CommandResult(String.format(MESSAGE_CLOSE_RECORD_SUCCESS));
        } else if (patientToEdit.getRecordList().getNumberOfRecords()
                < editedPatient.getRecordList().getNumberOfRecords()) {
            return new CommandResult(String.format(MESSAGE_ADD_RECORD_SUCCESS, editedPatient));
        } else {
            return new CommandResult(String.format(MESSAGE_EDIT_RECORD_SUCCESS, editedPatient));
        }
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Patient> lastShownList = model.getFilteredPersonList();

        if (patientIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        patientToEdit = lastShownList.get(patientIndex.getZeroBased());

        //creating medical record window here and obtaining user input
        if (!isTest) { //only execute if it is not a test
            RecordWindow recordWindow = new RecordWindow();
            Stage stage = new Stage();
            recordWindow.start(stage, patientToEdit.getRecord(recordIndex.getZeroBased()));
        }

        Record editedRecord = RecordManager.getRecord();

        if (editedRecord == null) {
            editedPatient = patientToEdit;
        } else {
            editedPatient = createEditedPatient(patientToEdit, recordIndex.getZeroBased(), editedRecord);
        }
    }

    /**
     * Creates and returns a {@code Patient} with the details of {@code patientToEdit}
     * edited with {@code record}.
     */
    private static Patient createEditedPatient(Patient patientToEdit, int recordIndex, Record record) {
        assert patientToEdit != null;

        ArrayList<Record> temp = new ArrayList<Record>();
        for (int i = 0; i < patientToEdit.getRecordList().getNumberOfRecords(); i++) {
            String date = patientToEdit.getRecordList().getRecordList().get(i).getDate();
            String symptom = patientToEdit.getRecordList().getRecordList().get(i).getSymptom();
            String illness = patientToEdit.getRecordList().getRecordList().get(i).getIllness();
            String treatment = patientToEdit.getRecordList().getRecordList().get(i).getTreatment();
            temp.add(new Record(date, symptom, illness, treatment));
        }
        RecordList recordlist = new RecordList(temp);
        recordlist.edit(recordIndex, record);

        return new Patient(patientToEdit.getName(), patientToEdit.getNric(), patientToEdit.getPhone(),
                patientToEdit.getEmail(), patientToEdit.getAddress(), patientToEdit.getDob(),
                patientToEdit.getBloodType(), patientToEdit.getRemark(), recordlist,
                patientToEdit.getTags(), patientToEdit.getAppointments());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RecordCommand)) {
            return false;
        }

        // state check
        RecordCommand e = (RecordCommand) other;

        return getPatientIndex().equals(e.getPatientIndex())
                && getRecordIndex().equals(e.getRecordIndex());
    }

    public Index getPatientIndex() {
        return patientIndex;
    }
    public Index getRecordIndex() {
        return recordIndex;
    }
    public Patient getEdited() {
        return editedPatient;
    }
}
```
###### \java\seedu\address\logic\commands\RemarkCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.patient.Patient;
import seedu.address.model.patient.Remark;
import seedu.address.model.patient.exceptions.DuplicatePatientException;
import seedu.address.model.patient.exceptions.PatientNotFoundException;


/**
 * Adds a patient to the address book.
 */
public class RemarkCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "remark";
    public static final String COMMAND_ALIAS = "rk";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the remark for a patient specified in the INDEX. "
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_REMARK + "REMARK] "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_REMARK + "Monthly blood test";

    public static final String MESSAGE_ADD_SUCCESS = "Added remark to: %1$s";
    public static final String MESSAGE_REMOVE_SUCCESS = "Removed remark from: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This patient already exists in the address book.";

    private Patient patientToEdit;
    private Patient editedPatient;
    private final Index index;
    private final Remark remark;

    /**
     * Creates an AddCommand to add the specified {@code Patient}
     */
    public RemarkCommand(Index index, Remark remark) {
        requireNonNull(index);
        requireNonNull(remark);
        this.index = index;
        this.remark = remark;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(patientToEdit);
        requireNonNull(editedPatient);

        try {
            model.updatePerson(patientToEdit, editedPatient);
        } catch (DuplicatePatientException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PatientNotFoundException pnfe) {
            throw new AssertionError("The target patient cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(generateSuccessMessage(editedPatient));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Patient> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        patientToEdit = lastShownList.get(index.getZeroBased());
        editedPatient = createEditedPerson(patientToEdit, remark);
    }

    /**
     * Creates and returns a {@code Patient} with the details of {@code patientToEdit}
     * edited with the new remark.
     */
    private static Patient createEditedPerson(Patient patientToEdit, Remark remark) {
        assert patientToEdit != null;

        Remark updatedRemark = remark;

        return new Patient(patientToEdit.getName(), patientToEdit.getNric(), patientToEdit.getPhone(),
                patientToEdit.getEmail(), patientToEdit.getAddress(), patientToEdit.getDob(),
                patientToEdit.getBloodType(), updatedRemark, patientToEdit.getRecordList(),
                patientToEdit.getTags(), patientToEdit.getAppointments());
    }

    /**
     * Generates the success message depending on whether a remark was modified or deleted from {@code patientToEdit}
     */
    private String generateSuccessMessage(Patient editedPatient) {
        String message = MESSAGE_ADD_SUCCESS;
        if (remark.value.isEmpty()) {
            message = MESSAGE_REMOVE_SUCCESS;
        }
        return String.format(message, editedPatient);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RemarkCommand)) {
            return false;
        }

        // state check
        RemarkCommand e = (RemarkCommand) other;

        return getIndex().equals(e.getIndex())
                && getRemark().equals(e.getRemark());
    }

    public Index getIndex() {
        return index;
    }
    public Remark getRemark() {
        return remark;
    }
    public Patient getEdited() {
        return editedPatient;
    }
}
```
###### \java\seedu\address\logic\commands\RemoveRecordCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.patient.Patient;
import seedu.address.model.patient.Record;
import seedu.address.model.patient.RecordList;
import seedu.address.model.patient.exceptions.DuplicatePatientException;
import seedu.address.model.patient.exceptions.PatientNotFoundException;

/**
 * Adds a patient to the address book.
 */
public class RemoveRecordCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "remover";
    public static final String COMMAND_ALIAS = "rr";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes the medical record of a patient in the address book. "
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_INDEX + "INDEX] "
            + "\n"
            + "Example: " + COMMAND_WORD + " "
            + "1 "
            + PREFIX_INDEX + "1 ";

    public static final String MESSAGE_REMOVE_RECORD_SUCCESS = "Medical record removed: %1$s";
    public static final String MESSAGE_REMOVE_RECORD_FAILURE = "The index specified does not exist.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This patient already exists in the address book.";

    private final Index patientIndex;
    private final Index recordIndex;

    private Patient patientToEdit;
    private Patient editedPatient;

    /**
     * Creates a RecordCommand to edit the records of the specified {@code Patient}
     */
    public RemoveRecordCommand(Index patientIndex, Index recordIndex) {
        requireNonNull(patientIndex);
        this.patientIndex = patientIndex;
        this.recordIndex = recordIndex;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.updatePerson(patientToEdit, editedPatient);
        } catch (DuplicatePatientException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PatientNotFoundException pnfe) {
            throw new AssertionError("The target patient cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_REMOVE_RECORD_SUCCESS, editedPatient));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Patient> lastShownList = model.getFilteredPersonList();

        if (patientIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        patientToEdit = lastShownList.get(patientIndex.getZeroBased());

        editedPatient = createEditedPatient(patientToEdit, recordIndex.getZeroBased());
    }

    /**
     * Creates and returns a {@code Patient} with the details of {@code patientToEdit}
     * edited with {@code record}.
     */
    private static Patient createEditedPatient(Patient patientToEdit, int recordIndex) throws CommandException {
        assert patientToEdit != null;

        ArrayList<Record> temp = new ArrayList<Record>();
        for (int i = 0; i < patientToEdit.getRecordList().getNumberOfRecords(); i++) {
            String date = patientToEdit.getRecordList().getRecordList().get(i).getDate();
            String symptom = patientToEdit.getRecordList().getRecordList().get(i).getSymptom();
            String illness = patientToEdit.getRecordList().getRecordList().get(i).getIllness();
            String treatment = patientToEdit.getRecordList().getRecordList().get(i).getTreatment();
            temp.add(new Record(date, symptom, illness, treatment));
        }
        try {
            temp.remove(recordIndex);
        } catch (IndexOutOfBoundsException ie) {
            throw new CommandException(MESSAGE_REMOVE_RECORD_FAILURE);
        }

        RecordList recordlist = new RecordList(temp);

        return new Patient(patientToEdit.getName(), patientToEdit.getNric(), patientToEdit.getPhone(),
                patientToEdit.getEmail(), patientToEdit.getAddress(), patientToEdit.getDob(),
                patientToEdit.getBloodType(), patientToEdit.getRemark(), recordlist,
                patientToEdit.getTags(), patientToEdit.getAppointments());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RemoveRecordCommand)) {
            return false;
        }

        // state check
        RemoveRecordCommand e = (RemoveRecordCommand) other;

        return getPatientIndex().equals(e.getPatientIndex())
                && getRecordIndex().equals(e.getRecordIndex());
    }

    public Index getPatientIndex() {
        return patientIndex;
    }
    public Index getRecordIndex() {
        return recordIndex;
    }
    public Patient getEdited() {
        return editedPatient;
    }
}
```
###### \java\seedu\address\logic\parser\RecordCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.RecordCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new RecordCommand object
 */
public class RecordCommandParser implements Parser<RecordCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RecordCommand
     * and returns an RecordCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RecordCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_INDEX);

        Index patientIndex;
        Index recordIndex;

        try {
            patientIndex = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RecordCommand.MESSAGE_USAGE));
        }

        if (!arePrefixesPresent(argMultimap, PREFIX_INDEX)
                || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RecordCommand.MESSAGE_USAGE));
        }

        try {
            recordIndex = ParserUtil.parseIndex((argMultimap.getValue(PREFIX_INDEX)).get());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RecordCommand.MESSAGE_USAGE));
        }

        return new RecordCommand(patientIndex, recordIndex);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
```
###### \java\seedu\address\logic\parser\RemarkCommandParser.java
``` java
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.patient.Remark;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class RemarkCommandParser implements Parser<RemarkCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemarkCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_REMARK);

        System.out.println(argMultimap.getPreamble());

        if (!argMultimap.getValue(PREFIX_REMARK).isPresent()
                || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE));
        }
        try {
            Index index = ParserUtil.parseIndex(argMultimap.getPreamble());
            String remark = ParserUtil.parseRemark(argMultimap.getValue(PREFIX_REMARK)).get();
            return new RemarkCommand(index, new Remark(remark));
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE));
        }
    }

}
```
###### \java\seedu\address\logic\parser\RemoveRecordCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.RemoveRecordCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new RecordCommand object
 */
public class RemoveRecordCommandParser implements Parser<RemoveRecordCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RemoveRecordCommand
     * and returns an RemoveRecordCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemoveRecordCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_INDEX);

        Index patientIndex;
        Index recordIndex;

        try {
            patientIndex = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveRecordCommand.MESSAGE_USAGE));
        }

        if (!arePrefixesPresent(argMultimap, PREFIX_INDEX)
                || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveRecordCommand.MESSAGE_USAGE));
        }

        try {
            recordIndex = ParserUtil.parseIndex((argMultimap.getValue(PREFIX_INDEX)).get());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveRecordCommand.MESSAGE_USAGE));
        }

        return new RemoveRecordCommand(patientIndex, recordIndex);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
```
###### \java\seedu\address\logic\record\RecordController.java
``` java
package seedu.address.logic.record;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import seedu.address.model.patient.DateOfBirth;
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
        closeWindow(date, symptom, illness, treatment);
    }

    /**
     * Handles the events required when a close window event occurs.
     */
    private void closeWindow(String date, String symptom, String illness, String treatment) {
        if (date.equals("") || symptom.equals("") || illness.equals("") || treatment.equals("")) {
            messageText.setText("Please fill in all fields.");
        } else {
            if (RecordManager.authenticate(date, symptom, illness, treatment)) {
                messageText.setText("Success! Please close this window.");
                closeButtonAction();
            } else {
                if (!DateOfBirth.isValidDob(date)) {
                    messageText.setText("Date should only contain digits and"
                            + "slashes, in the following format DD/MM/YYYY");
                } else {
                    messageText.setText("Text field should only contain"
                            + "visible characters and spaces, and it should not be blank");
                }
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
        closeWindow(date, symptom, illness, treatment);
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
```
###### \java\seedu\address\logic\record\RecordWindow.java
``` java
package seedu.address.logic.record;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import seedu.address.MainApp;
import seedu.address.model.patient.Record;

/**
 * Class for initializing login popup GUI
 */
public class RecordWindow extends Application {

    private Stage primaryStage;
    private AnchorPane rootLayout;
    private Record record;

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Opens a new window and starts up the medical record form.
     */
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Medical Record Window");

        // Prevent the window resizing
        this.primaryStage.setResizable(false);

        //initRootLayout();

        showRecordForm();
    }

    /**
     * Overloaded method of start.
     */
    public void start(Stage primaryStage, Record record) {
        this.record = record;
        start(primaryStage);
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
            primaryStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializes the medical record form
     */
    public void showRecordForm() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/view/RecordLayout.fxml"));
            rootLayout = loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            RecordController rc = loader.<RecordController>getController();
            rc.initData(record);
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
###### \java\seedu\address\model\patient\Record.java
``` java
package seedu.address.model.patient;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ILLNESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SYMPTOM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TREATMENT;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.stream.Stream;

import seedu.address.logic.commands.RecordCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents a Medical Record in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Record {

    private final DateOfBirth date;
    private final TextField symptom;
    private final TextField illness;
    private final TextField treatment;

    public Record() {
        this(new SimpleDateFormat("dd/MM/yyyy").format(new Date()), "", "", "");
    }

    /**
     * Every field must be present and not null.
     */
    public Record(String date, String symptom, String illness, String treatment) {
        requireAllNonNull(date, symptom, illness, treatment);
        this.date = new DateOfBirth(date);
        this.symptom = new TextField(symptom);
        this.illness = new TextField(illness);
        this.treatment = new TextField(treatment);
    }

    public Record(Record record) {
        this(record.getDate(), record.getSymptom(),
                record.getIllness(), record.getTreatment());
    }

    public Record(String string) throws ParseException {
        Record temp = this.parse(string);
        this.date = new DateOfBirth(temp.getDate());
        this.symptom = new TextField(temp.getSymptom());
        this.illness = new TextField(temp.getIllness());
        this.treatment = new TextField(temp.getTreatment());
    }

    /**
     *
     * @param args Takes in a string that represents a medical record.
     * @return Returns the medical record that is represented by the string.
     * @throws ParseException
     */
    private Record parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args,
                        PREFIX_SYMPTOM, PREFIX_ILLNESS, PREFIX_TREATMENT);

        if (!arePrefixesPresent(argMultimap,
                PREFIX_SYMPTOM, PREFIX_ILLNESS, PREFIX_TREATMENT)
                || (argMultimap.getPreamble() == null)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RecordCommand.MESSAGE_USAGE));
        }

        String date = argMultimap.getPreamble();
        String symptom = (argMultimap.getValue(PREFIX_SYMPTOM)).get();
        String illness = (argMultimap.getValue(PREFIX_ILLNESS)).get();
        String treatment = (argMultimap.getValue(PREFIX_TREATMENT)).get();

        return new Record(date, symptom, illness, treatment);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    public String getDate() {
        return date.toString();
    }

    public String getSymptom() {
        return symptom.toString();
    }

    public String getIllness() {
        return illness.toString();
    }

    public String getTreatment() {
        return treatment.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Record)) {
            return false;
        }

        Record otherPatient = (Record) other;
        return otherPatient.getDate().equals(this.getDate())
                && otherPatient.getSymptom().equals(this.getSymptom())
                && otherPatient.getIllness().equals(this.getIllness())
                && otherPatient.getTreatment().equals(this.getTreatment());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(date, symptom, illness, treatment);
    }

    /**
     * Returns true if all fields of record are non-null.
     */
    public static boolean isValidRecord(Record test) {
        requireAllNonNull(test, test.getDate(), test.getIllness(), test.getSymptom(), test.getTreatment());
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Date: ")
                .append(getDate());
        return builder.toString();
    }

    /**
     * Returns a string that is used to generate a RecordList that has this Record.
     */
    public String toCommandStringRecordList() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getDate())
                .append(" ")
                .append(PREFIX_SYMPTOM)
                .append(getSymptom())
                .append(" ")
                .append(PREFIX_ILLNESS)
                .append(getIllness())
                .append(" ")
                .append(PREFIX_TREATMENT)
                .append(getTreatment());
        return builder.toString();
    }

}
```
###### \java\seedu\address\model\patient\RecordList.java
``` java
package seedu.address.model.patient;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Objects;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents a list of Medical Records in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class RecordList {

    private ArrayList<Record> recordList;
    private int numRecord;

    public RecordList() {
        this.recordList = new ArrayList<Record>();
        this.numRecord = 0;
    }

    /**
     * Every field must be present and not null.
     */
    public RecordList(ArrayList<Record> recordList) {
        requireAllNonNull(recordList);
        this.recordList = recordList;
        this.numRecord = recordList.size();
    }

    public RecordList(String string) throws ParseException {
        if (string.isEmpty()) {
            this.recordList = new ArrayList<Record>();
            this.numRecord = 0;
        } else {
            this.recordList = new ArrayList<Record>();
            String[] lines = string.split("\\r?\\n");
            for (int i = 0; i < lines.length; i++) {
                recordList.add(new Record(lines[i]));
            }
            this.numRecord = lines.length;
        }
    }

    public int getNumberOfRecords() {
        return numRecord;
    }

    public ArrayList<Record> getRecordList() {
        return recordList;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof RecordList)) {
            return false;
        }

        RecordList otherPatient = (RecordList) other;
        return otherPatient.getNumberOfRecords() == (this.getNumberOfRecords())
                && otherPatient.getRecordList().equals(this.getRecordList());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(numRecord, recordList);
    }

    /**
     * Returns true if all fields of record are non-null.
     */
    public static boolean isValidRecordList(RecordList test) {
        requireAllNonNull(test, test.getNumberOfRecords(), test);
        for (int i = 0; i < test.getNumberOfRecords(); i++) {
            requireAllNonNull(test.getRecordList().get(i));
        }
        return true;
    }

    public Record getRecord(int recordIndex) {
        return recordList.get(recordIndex);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < numRecord; i++) {
            builder.append("Record number: ")
                    .append((i + 1) + " ")
                    .append(recordList.get(i).toString())
                    .append("\n");
        }
        return builder.toString();
    }

    /**
     * Returns the string of this class.
     */
    public String toCommandString() {
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < numRecord; i++) {
            builder.append(recordList.get(i).toCommandStringRecordList())
                    .append("\n");
        }
        return builder.toString();
    }

    /**
     * Edits the list of records based on the arguments provided.
     */
    public void edit(int recordIndex, Record record) {
        if (this.numRecord > recordIndex) {
            this.set(recordIndex, record);
        } else { //will always add new record as long as index > numRecords
            this.recordList.add(record);
            this.numRecord += 1;
        }
    }

    public void set(int recordIndex, Record record) {
        recordList.set(recordIndex, record);
    }
}
```
###### \java\seedu\address\model\patient\Remark.java
``` java
package seedu.address.model.patient;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Patient's remarks in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidRemark(String)}
 */
public class Remark {

    public static final String MESSAGE_REMARK_CONSTRAINTS =
            "Patient remark can take any values";


    public final String value;

    /**
     * Constructs an {@code Remark}.
     *
     * @param remark A valid remark.
     */
    public Remark(String remark) {
        requireNonNull(remark);
        checkArgument(isValidRemark(remark), MESSAGE_REMARK_CONSTRAINTS);
        this.value = remark;
    }

    /**
     * Returns true for all string values for remark.
     */
    public static boolean isValidRemark(String test) {
        requireNonNull(test);
        return true;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Remark // instanceof handles nulls
                && this.value.equals(((Remark) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### \java\seedu\address\model\patient\TextField.java
``` java
package seedu.address.model.patient;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Patient's medical record's text field
 * (symptom/illness/treatment) in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTextField(String)}
 */
public class TextField {

    public static final String MESSAGE_TEXTFIELD_CONSTRAINTS =
            "Patient text field should only contain visible characters and spaces, and it should not be blank";

    /*
     * TextField must not be null and it can only contain alphanumeric characters and spaces.
     */
    public static final String TEXTFIELD_VALIDATION_REGEX = "[\\p{Graph}\\p{Blank}]*";

    public final String textField;

    /**
     * Constructs a {@code TextField}.
     *
     * @param textField A valid TextField.
     */
    public TextField(String textField) {
        requireNonNull(textField);
        checkArgument(isValidTextField(textField), MESSAGE_TEXTFIELD_CONSTRAINTS);
        this.textField = textField;
    }

    /**
     * Returns true if a given string is a valid patient TextField.
     */
    public static boolean isValidTextField(String test) {
        return test.matches(TEXTFIELD_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return textField;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TextField // instanceof handles nulls
                && this.textField.equals(((TextField) other).textField)); // state check
    }

    @Override
    public int hashCode() {
        return textField.hashCode();
    }

}
```
###### \resources\view\RecordLayout.fxml
``` fxml

<!-- TODO: set a more appropriate initial size -->

<AnchorPane maxHeight="-Infinity" maxWidth="-Infinity" minHeight="-Infinity" minWidth="-Infinity" prefHeight="746.0" prefWidth="1061.0" xmlns="http://javafx.com/javafx/8.0.141" xmlns:fx="http://javafx.com/fxml/1" fx:controller="seedu.address.logic.record.RecordController">
   <children>
      <GridPane layoutX="16.0" layoutY="19.0" prefHeight="671.0" prefWidth="1010.0">
        <columnConstraints>
          <ColumnConstraints hgrow="SOMETIMES" maxWidth="133.3333740234375" minWidth="10.0" prefWidth="89.3333740234375" />
          <ColumnConstraints hgrow="SOMETIMES" maxWidth="1.7976931348623157E308" minWidth="10.0" prefWidth="185.6666259765625" />
        </columnConstraints>
        <rowConstraints>
          <RowConstraints maxHeight="120.0" minHeight="10.0" prefHeight="41.0" vgrow="SOMETIMES" />
          <RowConstraints maxHeight="210.0" minHeight="10.0" prefHeight="210.0" vgrow="SOMETIMES" />
            <RowConstraints maxHeight="210.0" minHeight="10.0" prefHeight="210.0" vgrow="SOMETIMES" />
            <RowConstraints maxHeight="210.0" minHeight="10.0" prefHeight="210.0" vgrow="SOMETIMES" />
        </rowConstraints>
         <children>
            <Text strokeType="OUTSIDE" strokeWidth="0.0" text="Date:" />
            <TextArea fx:id="dateField" onKeyPressed="#handleKeyAction" prefHeight="200.0" prefWidth="200.0" GridPane.columnIndex="1" />
            <Text strokeType="OUTSIDE" strokeWidth="0.0" text="Symptoms:" GridPane.rowIndex="1" />
            <TextArea fx:id="symptomField" onKeyPressed="#handleKeyAction" prefHeight="200.0" prefWidth="200.0" GridPane.columnIndex="1" GridPane.rowIndex="1" />
            <Text strokeType="OUTSIDE" strokeWidth="0.0" text="Illness:" GridPane.rowIndex="2" />
            <TextArea fx:id="illnessField" onKeyPressed="#handleKeyAction" prefHeight="200.0" prefWidth="200.0" GridPane.columnIndex="1" GridPane.rowIndex="2" />
            <Text strokeType="OUTSIDE" strokeWidth="0.0" text="Treatment:" GridPane.rowIndex="3" />
            <TextArea fx:id="treatmentField" onKeyPressed="#handleKeyAction" prefHeight="200.0" prefWidth="200.0" GridPane.columnIndex="1" GridPane.rowIndex="3" />
         </children>
      </GridPane>
      <Button fx:id="saveButton" layoutX="973.0" layoutY="701.0" mnemonicParsing="false" onAction="#handleButtonAction" text="Save" textAlignment="CENTER" />
      <Text fx:id="messageText" fill="#cc1919" layoutX="350.0" layoutY="722.0" strokeType="OUTSIDE" strokeWidth="0.0" wrappingWidth="361.0" />
   </children>
</AnchorPane>
```
