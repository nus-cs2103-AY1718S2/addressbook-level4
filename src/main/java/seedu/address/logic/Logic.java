package seedu.address.logic;

import java.util.Set;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Person;
import seedu.address.model.petpatient.PetPatient;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<Person> getFilteredPersonList();

    /** Returns an unmodifiable view of the filtered list of petPatients */
    ObservableList<PetPatient> getFilteredPetPatientList();

    /** Returns an unmodifiable view of the filtered list of Appointments */
    ObservableList<Appointment> getFilteredAppointmentList();

    /** Returns the list of input entered by the user, encapsulated in a {@code ListElementPointer} object */
    ListElementPointer getHistorySnapshot();

    /** Processes and sets attributes for pet patients objects. */
    void setAttributesForPetPatientObjects();

    /** Processes and sets attributes for person objects. */
    void setAttributesForPersonObjects();

    /** Processes and sets attributes for appointment. */
    void setAttributesForAppointmentObjects();

    /** Returns a set of all command words. */
    Set<String> getAllCommandWords();

    /** Returns a set of all prefixes. */
    Set<String> getAllPrefixes();

    /** Returns a set of all options used in command syntax. */
    Set<String> getAllOptions();

    /** Returns a set of all Nric found in model. */
    Set<String> getAllNric();

    /** Returns a set of all pet patient names found in model. */
    Set<String> getAllPetPatientNames();

    /** Returns a set of all pet patient species found in model. */
    Set<String> getAllPetPatientSpecies();

    /** Returns a set of all pet patient breeds found in model. */
    Set<String> getAllPetPatientBreeds();

    /** Returns a set of all pet patient colours found in model. */
    Set<String> getAllPetPatientColours();

    /** Returns a set of all pet patient blood types found in model. */
    Set<String> getAllPetPatientBloodTypes();

    /** Returns a set of all persons' tags found in model. */
    Set<String> getAllPersonTags();

    /** Returns a set of all pet patients' tags found in model. */
    Set<String> getAllPetPatientTags();

    /** Returns a set of all appointments' tags found in model. */
    Set<String> getAllAppointmentTags();

    /** Returns a set of all tags (persons', pet patients' & appointments') found in model. */
    Set<String> getAllTagsInModel();

}
