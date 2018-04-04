//@@author nhs-work
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
