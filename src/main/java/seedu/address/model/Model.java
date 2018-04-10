package seedu.address.model;

import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.exceptions.AppointmentNotFoundException;
import seedu.address.model.appointment.exceptions.ClashingAppointmentException;
import seedu.address.model.appointment.exceptions.DuplicateAppointmentException;
import seedu.address.model.email.Template;
import seedu.address.model.email.exceptions.DuplicateTemplateException;
import seedu.address.model.email.exceptions.TemplateNotFoundException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Appointment> PREDICATE_SHOW_ALL_APPOINTMENTS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Template> PREDICATE_SHOW_ALL_TEMPLATES = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyAddressBook newData);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /** Deletes the given person. */
    void deletePerson(Person target) throws PersonNotFoundException;

    /** Deletes the given list of persons. */
    void deletePersons(List<Person> targets) throws PersonNotFoundException;

    /** Adds the given person */
    void addPerson(Person person) throws DuplicatePersonException;

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     *
     * @throws DuplicatePersonException if updating the person's details causes the person to be equivalent to
     *      another existing person in the list.
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     */
    void updatePerson(Person target, Person editedPerson)
            throws DuplicatePersonException, PersonNotFoundException;

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    //@@author ng95junwei

    /**
     * Updates the filter of the filtered template list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredTemplateList(Predicate<Template> predicate);

    /** Selects template based on search string */
    Template selectTemplate(String search) throws TemplateNotFoundException;

    /** Adds the given template */
    void addTemplate(Template template) throws DuplicateTemplateException;

    /** Returns an unmodifiable view of all templates */
    ObservableList<Template> getAllTemplates();

    //@@author

    //@@author jlks96
    /** Deletes the given appointment. */
    void deleteAppointment(Appointment appointment) throws AppointmentNotFoundException;

    /** Adds the given appointment */
    void addAppointment(Appointment appointment) throws DuplicateAppointmentException, ClashingAppointmentException;

    /** Returns an unmodifiable view of the filtered appointment list */
    ObservableList<Appointment> getFilteredAppointmentList();

    /**
     * Updates the filter of the filtered appointment list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredAppointmentList(Predicate<Appointment> predicate);
    //@@author

}
