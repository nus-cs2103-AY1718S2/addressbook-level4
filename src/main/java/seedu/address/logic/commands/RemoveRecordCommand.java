//@@author nhs-work
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
