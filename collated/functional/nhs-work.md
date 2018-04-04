# nhs-work
###### \build\resources\main\view\RecordLayout.fxml
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
###### \out\production\resources\view\RecordLayout.fxml
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
###### \src\main\java\seedu\address\logic\commands\RecordCommand.java
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
    public Patient getToEdit() {
        return patientToEdit;
    }
    public Patient getEdited() {
        return editedPatient;
    }
}
```
###### \src\main\java\seedu\address\logic\commands\RemarkCommand.java
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
        //throw new CommandException("Index: " + index.getOneBased() + " Remark: " + remark);
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
    public Patient getToEdit() {
        return patientToEdit;
    }
    public Patient getEdited() {
        return editedPatient;
    }
}
```
###### \src\main\java\seedu\address\logic\commands\RemoveRecordCommand.java
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
        if (temp.size() == 0) {
            temp.add(new Record());
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
    public Patient getToEdit() {
        return patientToEdit;
    }
    public Patient getEdited() {
        return editedPatient;
    }
}
```
###### \src\main\java\seedu\address\logic\parser\RecordCommandParser.java
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
###### \src\main\java\seedu\address\logic\parser\RemarkCommandParser.java
``` java
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;

import java.util.stream.Stream;

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

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
```
###### \src\main\java\seedu\address\logic\parser\RemoveRecordCommandParser.java
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
###### \src\main\java\seedu\address\logic\record\RecordController.java
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
        if (date.equals("") || symptom.equals("") || illness.equals("") || treatment.equals("")) {
            messageText.setText("Please fill in all fields.");
        } else {
            if (RecordManager.authenticate(date, symptom, illness, treatment)) {
                messageText.setText("Success! Please close this window.");
                closeButtonAction();
            } else {
                messageText.setText("Invalid entries!");
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
        if (date.equals("") || symptom.equals("") || illness.equals("") || treatment.equals("")) {
            messageText.setText("Please fill in all fields.");
        } else {
            if (RecordManager.authenticate(date, symptom, illness, treatment)) {
                messageText.setText("Success! Please close this window.");
                closeButtonAction();
            } else {
                messageText.setText("Invalid entries!");
            }
        }
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
###### \src\main\java\seedu\address\logic\record\RecordWindow.java
``` java
package seedu.address.logic.record;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import seedu.address.MainApp;
import seedu.address.model.patient.Record;

/**
 * Class for initializing login popup GUI
 */
public class RecordWindow extends Application {

    @FXML
    private TextField dateField;

    @FXML
    private TextField symptomField;

    @FXML
    private TextField illnessField;

    @FXML
    private TextField treatmentField;

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
###### \src\main\java\seedu\address\model\patient\Record.java
``` java
package seedu.address.model.patient;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
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

    public static final String MESSAGE_RECORD_CONSTRAINTS =
            "Patient record can take any values, but each field must be populated";

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

        //to nest following lines into try once the various classes are set up
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
                .append(getDate())
                .append(" Symptoms: ")
                .append(getSymptom())
                .append(" Illness: ")
                .append(getIllness())
                .append(" Treatment: ")
                .append(getTreatment());
        return builder.toString();
    }

    /**
     * The is an outdated method that was utilised when there was only 1 record per patient.
     * It returns the string that is equivalent to the command that created this class.
     */
    public String toCommandString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("1 ") //as the command will not be executed, we will be placing a dummy index
                .append(PREFIX_DATE)
                .append(getDate())
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
###### \src\main\java\seedu\address\model\patient\RecordList.java
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

    public static final String MESSAGE_RECORDLIST_CONSTRAINTS =
            "Patient record list can take any values, but each field must be populated";

    private ArrayList<Record> recordList;
    private int numRecord;

    public RecordList() {
        this.recordList = new ArrayList<Record>();
        recordList.add(new Record());
        this.numRecord = 1;
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
            recordList.add(new Record());
            this.numRecord = 1;
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
    } //currently not very defensive

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
            builder.append("Index: ")
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
###### \src\main\java\seedu\address\model\patient\Remark.java
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
###### \src\main\java\seedu\address\model\patient\TextField.java
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
            "Patient text field should only contain alphanumeric characters and spaces, and it should not be blank";

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
###### \src\main\resources\view\RecordLayout.fxml
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
###### \src\test\java\seedu\address\logic\commands\RecordCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.address.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPatients.getTypicalAddressBook;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.record.RecordManager;
import seedu.address.model.Imdb;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.patient.Patient;
import seedu.address.model.patient.Record;
import seedu.address.testutil.PatientBuilder;

public class RecordCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new RecordCommand(null, null);
    }

    @Test
    public void execute_addRecordUnfilteredList_success() throws Exception {
        Patient toEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Patient editedPatient = new PatientBuilder(toEdit).withRecordList("01/04/2018 s/ i/ t/").build();
        RecordCommand recordCommand = prepareCommand(INDEX_FIRST_PERSON, 0,
                new Record("01/04/2018", "", "", ""));

        String expectedMessage = String.format(RecordCommand.MESSAGE_EDIT_RECORD_SUCCESS, editedPatient);

        Model expectedModel = new ModelManager(new Imdb(model.getImdb()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPatient);

        assertCommandSuccess(recordCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteRecordUnfilteredList_success() throws Exception {
        Patient toEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Patient editedPatient = new PatientBuilder(toEdit).withRecordList("01/04/2018 s/ i/ t/").build();
        RecordCommand recordCommand = prepareCommand(INDEX_FIRST_PERSON, 0, new Record("01/04/2018", "", "", ""));

        String expectedMessage = String.format(RecordCommand.MESSAGE_EDIT_RECORD_SUCCESS, editedPatient);

        Model expectedModel = new ModelManager(new Imdb(model.getImdb()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPatient);

        assertCommandSuccess(recordCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() throws Exception {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Patient patientInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Patient editedPatient = new PatientBuilder(patientInFilteredList)
                .withRecordList("01/04/2018 s/test i/test t/test").build();
        RecordCommand recordCommand = prepareCommand(INDEX_FIRST_PERSON, 0,
                new Record("01/04/2018", "test", "test", "test"));

        String expectedMessage = String.format(RecordCommand.MESSAGE_EDIT_RECORD_SUCCESS, editedPatient);

        Model expectedModel = new ModelManager(new Imdb(model.getImdb()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPatient);

        assertCommandSuccess(recordCommand, model, expectedMessage, expectedModel);
        RecordCommand undoCommand = prepareCommand(INDEX_FIRST_PERSON, 0,
                new Record(new SimpleDateFormat("dd/MM/yyyy").format(new Date()),
                "", "", ""));
        undoCommand.execute();
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        RecordCommand recordCommand = prepareCommand(outOfBoundIndex, 0);

        assertCommandFailure(recordCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit records of filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getImdb().getPersonList().size());

        RecordCommand recordCommand = prepareCommand(outOfBoundIndex, 0);

        assertCommandFailure(recordCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Patient patientToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        RecordCommand recordCommand = prepareCommand(INDEX_FIRST_PERSON, 0,
                new Record("01/04/2018", "test", "test", "test"));
        RecordCommand toUndoCommand = prepareCommand(INDEX_FIRST_PERSON, 0,
                new Record(new SimpleDateFormat("dd/MM/yyyy").format(new Date()),
                "", "", ""));
        Model expectedModel = new ModelManager(new Imdb(model.getImdb()), new UserPrefs());

        // edit -> first patient edited
        recordCommand.execute();
        Patient editedPatient = recordCommand.getEdited();
        undoRedoStack.push(recordCommand);

        // undo -> reverts addressbook back to previous state and filtered patient list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first patient edited again
        expectedModel.updatePerson(patientToEdit, editedPatient);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        toUndoCommand.execute();
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        RecordCommand recordCommand = prepareCommand(outOfBoundIndex, 0);

        // execution failed -> recordCommand not pushed into undoRedoStack
        assertCommandFailure(recordCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Edits a {@code Patient} from a filtered list.
     * 2. Undo the edit.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously edited patient in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the edit. This ensures {@code RedoCommand} edits the patient object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_samePersonEdited() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        RecordCommand recordCommand = prepareCommand(INDEX_FIRST_PERSON, 0, new Record("01/04/2018", "b", "b", "b"));
        RecordCommand toUndoCommand = prepareCommand(INDEX_SECOND_PERSON, 0,
                new Record(new SimpleDateFormat("dd/MM/yyyy").format(new Date()),
                "", "", ""));
        Model expectedModel = new ModelManager(new Imdb(model.getImdb()), new UserPrefs());

        showPersonAtIndex(model, INDEX_SECOND_PERSON);
        Patient patientToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        // record -> edits the records of the second patient in unfiltered patient list
        // / first patient in filtered patient list
        recordCommand.execute();
        Patient editedPatient = recordCommand.getEdited();
        undoRedoStack.push(recordCommand);

        // undo -> reverts addressbook back to previous state and filtered patient list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        expectedModel.updatePerson(patientToEdit, editedPatient);
        assertNotEquals(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()), patientToEdit);
        // redo -> edits same second patient in unfiltered patient list
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
        toUndoCommand.execute();
    }

    @Test
    public void equals() throws Exception {
        final RecordCommand standardCommand = prepareCommand(INDEX_FIRST_PERSON, 1);

        // same values -> returns true
        RecordCommand commandWithSameValues = prepareCommand(INDEX_FIRST_PERSON, 1);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different patient index -> returns false
        assertFalse(standardCommand.equals(new RecordCommand(INDEX_SECOND_PERSON, Index.fromOneBased(1))));

        // different record index -> returns false
        assertFalse(standardCommand.equals(new RecordCommand(INDEX_FIRST_PERSON, Index.fromOneBased(5))));
    }

    /**
     * Returns an {@code RecordCommand} with parameters {@code index} and {@code descriptor}
     */
    private RecordCommand prepareCommand(Index patientIndex, int recordIndex) {
        RecordCommand recordCommand = new RecordCommand(patientIndex, Index.fromZeroBased(recordIndex));
        recordCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return recordCommand;
    }

    /**
     * Returns an {@code RecordCommand} with parameters {@code index} and {@code descriptor}
     */
    private RecordCommand prepareCommand(Index patientIndex, int recordIndex, Record record) {
        RecordCommand recordCommand = new RecordCommand(patientIndex, Index.fromZeroBased(recordIndex), true);
        RecordManager.authenticate(record.getDate(), record.getSymptom(), record.getIllness(), record.getTreatment());
        recordCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return recordCommand;
    }

}
```
###### \src\test\java\seedu\address\logic\commands\RemarkCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.address.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPatients.getTypicalAddressBook;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Imdb;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.patient.Patient;
import seedu.address.model.patient.Remark;
import seedu.address.testutil.PatientBuilder;

public class RemarkCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_nullIndexAndRemark_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new RemarkCommand(null, null);
    }

    @Test
    public void execute_addRemarkUnfilteredList_success() throws Exception {
        Patient toEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Patient editedPatient = new PatientBuilder(toEdit).withRemark("test").build();
        RemarkCommand remarkCommand = prepareCommand(INDEX_FIRST_PERSON, new Remark("test"));

        String expectedMessage = String.format(RemarkCommand.MESSAGE_ADD_SUCCESS, editedPatient);

        Model expectedModel = new ModelManager(new Imdb(model.getImdb()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPatient);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteRemarkUnfilteredList_success() throws Exception {
        Patient toEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Patient editedPatient = new PatientBuilder(toEdit).withRemark("").build();
        RemarkCommand remarkCommand = prepareCommand(INDEX_FIRST_PERSON, new Remark(""));

        String expectedMessage = String.format(RemarkCommand.MESSAGE_REMOVE_SUCCESS, editedPatient);

        Model expectedModel = new ModelManager(new Imdb(model.getImdb()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPatient);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() throws Exception {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Patient patientInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Patient editedPatient = new PatientBuilder(patientInFilteredList).withRemark("test").build();
        RemarkCommand remarkCommand = prepareCommand(INDEX_FIRST_PERSON, new Remark("test"));

        String expectedMessage = String.format(RemarkCommand.MESSAGE_ADD_SUCCESS, editedPatient);

        Model expectedModel = new ModelManager(new Imdb(model.getImdb()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPatient);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        RemarkCommand remarkCommand = prepareCommand(outOfBoundIndex, new Remark("test"));

        assertCommandFailure(remarkCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit remarks of filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getImdb().getPersonList().size());

        RemarkCommand remarkCommand = prepareCommand(outOfBoundIndex, new Remark("test"));

        assertCommandFailure(remarkCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Patient patientToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        RemarkCommand remarkCommand = prepareCommand(INDEX_FIRST_PERSON, new Remark("test"));
        Model expectedModel = new ModelManager(new Imdb(model.getImdb()), new UserPrefs());

        // edit -> first patient edited
        remarkCommand.execute();
        Patient editedPatient = remarkCommand.getEdited();
        undoRedoStack.push(remarkCommand);

        // undo -> reverts addressbook back to previous state and filtered patient list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first patient edited again
        expectedModel.updatePerson(patientToEdit, editedPatient);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        RemarkCommand remarkCommand = prepareCommand(outOfBoundIndex, new Remark("test"));

        // execution failed -> remarkCommand not pushed into undoRedoStack
        assertCommandFailure(remarkCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Edits a {@code Patient} from a filtered list.
     * 2. Undo the edit.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously edited patient in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the edit. This ensures {@code RedoCommand} edits the patient object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_samePersonEdited() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        RemarkCommand remarkCommand = prepareCommand(INDEX_FIRST_PERSON, new Remark("test"));
        Model expectedModel = new ModelManager(new Imdb(model.getImdb()), new UserPrefs());

        showPersonAtIndex(model, INDEX_SECOND_PERSON);
        Patient patientToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        // remark -> edits the remarks of the second patient in unfiltered patient list
        // / first patient in filtered patient list
        remarkCommand.execute();
        Patient editedPatient = remarkCommand.getEdited();
        undoRedoStack.push(remarkCommand);

        // undo -> reverts addressbook back to previous state and filtered patient list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        expectedModel.updatePerson(patientToEdit, editedPatient);
        assertNotEquals(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()), patientToEdit);
        // redo -> edits same second patient in unfiltered patient list
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() throws Exception {
        final RemarkCommand standardCommand = prepareCommand(INDEX_FIRST_PERSON, new Remark(VALID_REMARK_AMY));

        // same values -> returns true
        RemarkCommand commandWithSameValues = prepareCommand(INDEX_FIRST_PERSON, new Remark(VALID_REMARK_AMY));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new RemarkCommand(INDEX_SECOND_PERSON, new Remark(VALID_REMARK_AMY))));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new RemarkCommand(INDEX_FIRST_PERSON, new Remark(VALID_REMARK_BOB))));
    }

    /**
     * Returns an {@code RemarkCommand} with parameters {@code index} and {@code descriptor}
     */
    private RemarkCommand prepareCommand(Index index, Remark remark) {
        RemarkCommand remarkCommand = new RemarkCommand(index, remark);
        remarkCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return remarkCommand;
    }

}
```
###### \src\test\java\seedu\address\logic\commands\RemoveRecordCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.address.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPatients.getTypicalAddressBook;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Imdb;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.patient.Patient;
import seedu.address.testutil.PatientBuilder;

public class RemoveRecordCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new RemoveRecordCommand(null, null);
    }

    @Test
    public void execute_deleteRecordUnfilteredList_success() throws Exception {
        Patient toEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Patient editedPatient = new PatientBuilder(toEdit).withRecordList("01/04/2018 s/test i/test t/test").build();

        String expectedMessage = String.format(RemoveRecordCommand.MESSAGE_REMOVE_RECORD_SUCCESS, toEdit);

        Model updatedModel = new ModelManager(new Imdb(model.getImdb()), new UserPrefs());
        updatedModel.updatePerson(model.getFilteredPersonList().get(0), editedPatient);

        RemoveRecordCommand removeRecordCommand = prepareCommand(INDEX_FIRST_PERSON, 0, updatedModel);

        assertCommandSuccess(removeRecordCommand, updatedModel, expectedMessage, model);
    }

    @Test
    public void execute_filteredList_success() throws Exception {

        Patient patientInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Patient editedPatient = new PatientBuilder(patientInFilteredList)
                .withRecordList("01/04/2018 s/test i/test t/test").build();

        String expectedMessage = String
                .format(RemoveRecordCommand.MESSAGE_REMOVE_RECORD_SUCCESS, patientInFilteredList);

        Model updatedModel = new ModelManager(new Imdb(model.getImdb()), new UserPrefs());
        updatedModel.updatePerson(model.getFilteredPersonList().get(0), editedPatient);

        showPersonAtIndex(updatedModel, INDEX_FIRST_PERSON);

        RemoveRecordCommand removeRecordCommand = prepareCommand(INDEX_FIRST_PERSON, 0, updatedModel);

        assertCommandSuccess(removeRecordCommand, updatedModel, expectedMessage, model);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        RemoveRecordCommand removeRecordCommand = prepareCommand(outOfBoundIndex, 0, model);

        assertCommandFailure(removeRecordCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit records of filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getImdb().getPersonList().size());

        RemoveRecordCommand removeRecordCommand = prepareCommand(outOfBoundIndex, 0, model);

        assertCommandFailure(removeRecordCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();

        Patient oldPatient = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Patient currentPatient = new PatientBuilder(oldPatient)
                .withRecordList("01/04/2018 s/test i/test t/test").build();

        Model currentModel = new ModelManager(new Imdb(model.getImdb()), new UserPrefs());
        currentModel.updatePerson(model.getFilteredPersonList().get(0), currentPatient);

        Model updatedModel = new ModelManager(new Imdb(model.getImdb()), new UserPrefs());
        updatedModel.updatePerson(model.getFilteredPersonList().get(0), currentPatient);

        RemoveRecordCommand removeRecordCommand = prepareCommand(INDEX_FIRST_PERSON, 0, currentModel);

        UndoCommand undoCommand = prepareUndoCommand(currentModel, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(currentModel, undoRedoStack);


        // edit -> first patient edited
        removeRecordCommand.execute();
        undoRedoStack.push(removeRecordCommand);

        // undo -> reverts addressbook back to previous state and filtered patient list to show all persons
        assertCommandSuccess(undoCommand, currentModel, UndoCommand.MESSAGE_SUCCESS, updatedModel);

        // redo -> same first patient edited again
        assertCommandSuccess(redoCommand, currentModel, RedoCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        RemoveRecordCommand removeRecordCommand = prepareCommand(outOfBoundIndex, 0, model);

        // execution failed -> removeRecordCommand not pushed into undoRedoStack
        assertCommandFailure(removeRecordCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Edits a {@code Patient} from a filtered list.
     * 2. Undo the edit.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously edited patient in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the edit. This ensures {@code RedoCommand} edits the patient object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_samePersonEdited() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();

        Patient oldPatient = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        Patient currentPatient = new PatientBuilder(oldPatient)
                .withRecordList("01/04/2018 s/test i/test t/test").build();

        Model currentModel = new ModelManager(new Imdb(model.getImdb()), new UserPrefs());
        currentModel.updatePerson(model.getFilteredPersonList().get(1), currentPatient);

        Model updatedModel = new ModelManager(new Imdb(model.getImdb()), new UserPrefs());
        updatedModel.updatePerson(model.getFilteredPersonList().get(1), currentPatient);

        UndoCommand undoCommand = prepareUndoCommand(currentModel, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(currentModel, undoRedoStack);

        RemoveRecordCommand removeRecordCommand = prepareCommand(INDEX_FIRST_PERSON, 0, currentModel);

        showPersonAtIndex(currentModel, INDEX_SECOND_PERSON);
        Patient patientToEdit = currentModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        // record -> edits the records of the second patient in unfiltered patient list
        // / first patient in filtered patient list
        removeRecordCommand.execute();
        undoRedoStack.push(removeRecordCommand);

        // undo -> reverts addressbook back to previous state and filtered patient list to show all persons
        assertCommandSuccess(undoCommand, currentModel, UndoCommand.MESSAGE_SUCCESS, updatedModel);

        assertNotEquals(currentModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()), patientToEdit);
        // redo -> edits same second patient in unfiltered patient list
        assertCommandSuccess(redoCommand, currentModel, RedoCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void equals() throws Exception {
        final RemoveRecordCommand standardCommand = prepareCommand(INDEX_FIRST_PERSON, 1, model);

        // same values -> returns true
        RemoveRecordCommand commandWithSameValues = prepareCommand(INDEX_FIRST_PERSON, 1, model);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different patient index -> returns false
        assertFalse(standardCommand.equals(new RemoveRecordCommand(INDEX_SECOND_PERSON, Index.fromOneBased(1))));

        // different record index -> returns false
        assertFalse(standardCommand.equals(new RemoveRecordCommand(INDEX_FIRST_PERSON, Index.fromOneBased(5))));
    }


    /**
     * Returns an {@code RecordCommand} with parameters {@code index} and {@code descriptor}
     */
    private RemoveRecordCommand prepareCommand(Index patientIndex, int recordIndex, Model model) {
        RemoveRecordCommand removeRecordCommand = new RemoveRecordCommand(
                patientIndex, Index.fromZeroBased(recordIndex));
        removeRecordCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return removeRecordCommand;
    }

}
```
###### \src\test\java\seedu\address\logic\parser\RecordCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RecordCommand;

public class RecordCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, RecordCommand.MESSAGE_USAGE);

    private RecordCommandParser parser = new RecordCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no patient index specified
        assertParseFailure(parser, PREFIX_INDEX + "1 ", MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + PREFIX_INDEX + "1 ", MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + PREFIX_INDEX + "1 ", MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 abc/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        final StringBuilder builder = new StringBuilder();
        builder.append(targetIndex.getOneBased())
                .append(" " + PREFIX_INDEX + "1 ");

        RecordCommand expectedCommand = new RecordCommand(targetIndex, Index.fromZeroBased(0));

        assertParseSuccess(parser, builder.toString(), expectedCommand);
    }
}
```
###### \src\test\java\seedu\address\logic\parser\RemarkCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.model.patient.Remark;

public class RemarkCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE);

    private RemarkCommandParser parser = new RemarkCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + " r/Is friendly";

        RemarkCommand expectedCommand = new RemarkCommand(targetIndex, new Remark("Is friendly"));

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
```
###### \src\test\java\seedu\address\logic\parser\RemoveRecordCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RemoveRecordCommand;

public class RemoveRecordCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveRecordCommand.MESSAGE_USAGE);

    private RemoveRecordCommandParser parser = new RemoveRecordCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no patient index specified
        assertParseFailure(parser, PREFIX_INDEX + "1 ", MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + PREFIX_INDEX + "1 ", MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + PREFIX_INDEX + "1 ", MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 abc/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        final StringBuilder builder = new StringBuilder();
        builder.append(targetIndex.getOneBased())
                .append(" " + PREFIX_INDEX + "1 ");

        RemoveRecordCommand expectedCommand = new RemoveRecordCommand(targetIndex, Index.fromZeroBased(0));

        assertParseSuccess(parser, builder.toString(), expectedCommand);
    }
}
```
###### \src\test\java\seedu\address\model\patient\RecordListTest.java
``` java
package seedu.address.model.patient;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.testutil.Assert;

public class RecordListTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Record(null, null, null, null));
    }

    @Test
    public void isValidRecordList() throws ParseException {
        // null address
        Assert.assertThrows(NullPointerException.class, () -> RecordList.isValidRecordList(null));

        ArrayList<Record> temp = new ArrayList<Record>();

        // valid recordLists
        temp.add(new Record("01/04/2018", "", "", ""));
        assertTrue(RecordList.isValidRecordList(new RecordList(temp))); // empty string
        temp.remove(0);
        temp.add(new Record("01/04/2018", " ", " ", " "));
        assertTrue(RecordList.isValidRecordList(new RecordList(temp))); // spaces only
        temp.remove(0);
        temp.add(new Record("01/04/2018", "b", "c", "d"));
        assertTrue(RecordList.isValidRecordList(new RecordList(temp))); // one character
        try {
            assertTrue(RecordList.isValidRecordList(new RecordList("01/04/2018 s/a i/b t/c"))); // one character
        } catch (ParseException pe) {
            throw pe;
        }

        // invalid recordList
        temp.remove(0);
        Assert.assertThrows(IllegalArgumentException.class, () -> new RecordList("9th March 2017 s/ i/ t/"));
    }
}
```
###### \src\test\java\seedu\address\model\patient\RecordTest.java
``` java
package seedu.address.model.patient;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class RecordTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Record(null, null, null, null));
    }

    @Test
    public void isValidRecord() {
        // null record
        Assert.assertThrows(NullPointerException.class, () -> Record.isValidRecord(null));

        // invalid records
        Assert.assertThrows(IllegalArgumentException.class, () -> Record.isValidRecord(new Record("", "", "", "")));
        Assert.assertThrows(IllegalArgumentException.class, () -> Record.isValidRecord(new Record(" ", " ", " ", " ")));
        Assert.assertThrows(IllegalArgumentException.class, () -> Record.isValidRecord(
                new Record("5th March 2016", " ", " ", " ")));

        // valid records
        assertTrue(Record.isValidRecord(new Record("01/04/2018", "High temperature", "Fever", "Antibiotics")));
        assertTrue(Record.isValidRecord(new Record("99/99/9999", "b", "c", "d"))); // one character
    }
}
```
###### \src\test\java\seedu\address\model\patient\RemarkTest.java
``` java
package seedu.address.model.patient;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class RemarkTest {

    @Test
    public void isValidRemark() {
        // valid remarks
        assertTrue(Remark.isValidRemark("test"));
        assertTrue(Remark.isValidRemark("Shows up weekly for medication")); // long remark
        assertTrue(Remark.isValidRemark("a")); // one character
    }

    @Test
    public void equals() {
        Remark remark = new Remark("test");

        // same object -> returns true
        assertTrue(remark.equals(remark));

        // same value -> returns true
        Remark remark2 = new Remark("test");
        assertTrue(remark.equals(remark2));

        // different value -> returns false
        Remark remark3 = new Remark("not test");
        assertFalse(remark.equals(remark3));

        // different objects -> returns false
        assertFalse(remark.equals(362));

        // null -> returns false
        assertFalse(remark.equals(null));
    }
}
```
