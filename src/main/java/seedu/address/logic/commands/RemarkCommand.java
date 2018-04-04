//@@author nhs-work
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
