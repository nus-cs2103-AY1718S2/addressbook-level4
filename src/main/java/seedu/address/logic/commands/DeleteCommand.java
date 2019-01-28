package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.exceptions.AppointmentDependencyNotEmptyException;
import seedu.address.model.appointment.exceptions.AppointmentNotFoundException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.petpatient.PetPatient;
import seedu.address.model.petpatient.exceptions.PetDependencyNotEmptyException;
import seedu.address.model.petpatient.exceptions.PetPatientNotFoundException;

//@@author wynonaK
/**
 * Deletes a person, pet patient or appointment from the address book.
 */
public class DeleteCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "delete";
    public static final String COMMAND_ALIAS = "d";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + " -[f]o/-[f]p/-a"
            + ": Deletes the contact/pet/appointment identified by the index number used in the latest listing.\n"
            + "Additional -[f] options indicates forcefully deleting object all of its related dependencies.\n"
            + "Parameters: INDEX (must be a positive integer, must not be invalid)\n"
            + "Example: " + COMMAND_WORD + " -o 1";

    public static final String MESSAGE_USAGE_OWNER = COMMAND_WORD
            + " -o"
            + ": Deletes the contact identified by the index number used in the latest contact listing.\n"
            + "Parameters: INDEX (must be a positive integer, must not be invalid)\n"
            + "Example: " + COMMAND_WORD + " -o 1";

    public static final String MESSAGE_USAGE_PET_PATIENT = COMMAND_WORD
            + " -p"
            + ": Deletes the pet patient identified by the index number used in the latest pet patient listing.\n"
            + "Parameters: INDEX (must be a positive integer, must not be invalid)\n"
            + "Example: " + COMMAND_WORD + " -p 1";

    public static final String MESSAGE_USAGE_APPOINTMENT = COMMAND_WORD
            + " -a"
            + ": Deletes the appointment identified by the index number used in the latest appointment listing.\n"
            + "Parameters: INDEX (must be a positive integer, must not be invalid)\n"
            + "Example: " + COMMAND_WORD + " -a 1";

    public static final String MESSAGE_USAGE_FORCE_OWNER = COMMAND_WORD
            + " -fo"
            + ": Forcefully deletes a contact and all related dependencies "
            + "identified by the index number used in the latest contact listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " -fo 1";

    public static final String MESSAGE_USAGE_FORCE_PET_PATIENT = COMMAND_WORD
            + " -fp"
            + ": Forcefully deletes a pet patient and all related dependencies "
            + "identified by the index number used in the latest pet patient listing.\n"
            + "Parameters: INDEX (must be a positive integer, must not be invalid)\n"
            + "Example: " + COMMAND_WORD + " -fp 1";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Contact: %1$s";
    public static final String MESSAGE_DELETE_PET_PATIENT_SUCCESS = "Deleted Pet Patient: %1$s";
    public static final String MESSAGE_DELETE_APPOINTMENT_SUCCESS = "Deleted Appointment: %1$s";

    private final Index targetIndex;
    private final int type;

    private Person personToDelete;
    private PetPatient petPatientToDelete;
    private Appointment appointmentToDelete;

    public DeleteCommand(int type, Index targetIndex) {
        this.type = type;
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            switch (type) {
            case 1: return deletePerson();
            case 2: return deletePetPatient();
            case 3: return deleteAppointment();
            case 4: return deleteForcePerson();
            case 5: return deleteForcePetPatient();
            default:
                throw new CommandException(MESSAGE_USAGE);
            }
        } catch (PetDependencyNotEmptyException e) {
            throw new CommandException(Messages.MESSAGE_DEPENDENCIES_EXIST);
        } catch (AppointmentDependencyNotEmptyException e) {
            throw new CommandException(Messages.MESSAGE_DEPENDENCIES_EXIST);
        }
    }
    /**
     * Deletes {@code personToDelete} from the address book.
     */
    private CommandResult deletePerson() throws PetDependencyNotEmptyException {
        try {
            requireNonNull(personToDelete);
            model.deletePerson(personToDelete);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target contact cannot be missing");
        }

        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, personToDelete));
    }

    private void getPersonToDelete() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
        }

        personToDelete = lastShownList.get(targetIndex.getZeroBased());
    }

    /**
     * Deletes the pet patient {@code petPatientToDelete} from the address book.
     */
    private CommandResult deletePetPatient() throws AppointmentDependencyNotEmptyException {
        try {
            requireNonNull(petPatientToDelete);
            model.deletePetPatient(petPatientToDelete);
        } catch (PetPatientNotFoundException ppnfe) {
            throw new AssertionError("The target pet patient cannot be missing");
        }

        return new CommandResult(String.format(MESSAGE_DELETE_PET_PATIENT_SUCCESS, petPatientToDelete));
    }

    private void getPetPatientToDelete() throws CommandException {
        List<PetPatient> lastShownList = model.getFilteredPetPatientList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
        }

        petPatientToDelete = lastShownList.get(targetIndex.getZeroBased());
    }
    /**
     * Deletes the appointment {@code appointmentToDelete} from the address book.
     */
    private CommandResult deleteAppointment() {
        try {
            requireNonNull(appointmentToDelete);
            model.deleteAppointment(appointmentToDelete);
        } catch (AppointmentNotFoundException anfe) {
            throw new AssertionError("The target appointment cannot be missing");
        }

        return new CommandResult(String.format(MESSAGE_DELETE_APPOINTMENT_SUCCESS, appointmentToDelete));
    }

    private void getAppointmentToDelete() throws CommandException {
        List<Appointment> lastShownList = model.getFilteredAppointmentList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
        }

        appointmentToDelete = lastShownList.get(targetIndex.getZeroBased());
    }

    /**
     * Forcefully deletes {@code personToDelete} from the address book.
     * All related dependencies (pet patients, appointments) will be deleted as well.
     */
    private CommandResult deleteForcePerson() {
        String deleteDependenciesList = "";

        try {
            requireNonNull(personToDelete);
            List<PetPatient> petPatientsDeleted = model.deletePetPatientDependencies(personToDelete);
            List<Appointment> appointmentsDeleted = new ArrayList<>();
            for (PetPatient pp : petPatientsDeleted) {
                appointmentsDeleted.addAll(model.deleteAppointmentDependencies(pp));
                deleteDependenciesList += "\n" + (String.format(MESSAGE_DELETE_PET_PATIENT_SUCCESS, pp));
            }
            for (Appointment appointment : appointmentsDeleted) {
                deleteDependenciesList += "\n" + (String.format(MESSAGE_DELETE_APPOINTMENT_SUCCESS, appointment));
            }
            model.deletePerson(personToDelete);
        } catch (PersonNotFoundException e) {
            throw new AssertionError("The target contact cannot be missing");
        } catch (PetDependencyNotEmptyException e) {
            throw new AssertionError("Pet dependencies still exist!");
        }

        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, personToDelete)
                + deleteDependenciesList);
    }

    /**
     * Forcefully deletes {@code petPatientToDelete} from the address book.
     * All related dependencies (appointments) will be deleted as well.
     */
    private CommandResult deleteForcePetPatient() {
        String deleteDependenciesList = "";

        try {
            requireNonNull(petPatientToDelete);
            List<Appointment> appointmentDependenciesDeleted = model.deleteAppointmentDependencies(petPatientToDelete);
            for (Appointment appointment : appointmentDependenciesDeleted) {
                deleteDependenciesList += "\n" + (String.format(MESSAGE_DELETE_APPOINTMENT_SUCCESS, appointment));
            }
            model.deletePetPatient(petPatientToDelete);
        } catch (PetPatientNotFoundException ppnfe) {
            throw new AssertionError("The target pet patient cannot be missing");
        }  catch (AppointmentDependencyNotEmptyException e) {
            throw new AssertionError("Appointment dependencies still exist!");
        }

        return new CommandResult(String.format(MESSAGE_DELETE_PET_PATIENT_SUCCESS, petPatientToDelete)
                + deleteDependenciesList);
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        try {
            switch (type) {
            case 1: getPersonToDelete();
                break;
            case 2: getPetPatientToDelete();
                break;
            case 3: getAppointmentToDelete();
                break;
            case 4: getPersonToDelete();
                break;
            case 5: getPetPatientToDelete();
                break;
            default:
                throw new CommandException(MESSAGE_USAGE);
            }
        } catch (CommandException e) {
            throw new CommandException(Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && this.targetIndex.equals(((DeleteCommand) other).targetIndex) // state check
                && Objects.equals(this.personToDelete, ((DeleteCommand) other).personToDelete));
    }
}
