package systemtests;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Person;
import seedu.address.model.petpatient.PetPatient;

/**
 * Contains helper methods to set up {@code Model} for testing.
 */
public class ModelHelper {
    private static final Predicate<Person> PREDICATE_MATCHING_NO_PERSONS = unused -> false;
    private static final Predicate<PetPatient> PREDICATE_MATCHING_NO_PETPATIENTS = unused -> false;
    private static final Predicate<Appointment> PREDICATE_MATCHING_NO_APPOINTMENTS = unused -> false;

    /**
     * Updates {@code model}'s filtered list to display only {@code toDisplay}.
     */
    public static void setFilteredPersonList(Model model, List<Person> toDisplay) {
        Optional<Predicate<Person>> predicate =
                toDisplay.stream().map(ModelHelper::getPredicateMatching).reduce(Predicate::or);
        model.updateFilteredPersonList(predicate.orElse(PREDICATE_MATCHING_NO_PERSONS));
    }

    /**
     * @see ModelHelper#setFilteredPersonList(Model, List)
     */
    public static void setFilteredPersonList(Model model, Person... toDisplay) {
        setFilteredPersonList(model, Arrays.asList(toDisplay));
    }

    /**
     * Returns a predicate that evaluates to true if this {@code Person} equals to {@code other}.
     */
    private static Predicate<Person> getPredicateMatching(Person other) {
        return person -> person.equals(other);
    }

    /**
     * Returns a predicate that evaluates to true if this {@code PetPatient} equals to {@code other}.
     */
    private static Predicate<PetPatient> getPredicateMatching(PetPatient other) {
        return petPatient -> petPatient.equals(other);
    }

    /**
     * Returns a predicate that evaluates to true if this {@code Appointment} equals to {@code other}.
     */
    private static Predicate<Appointment> getPredicateMatching(Appointment other) {
        return appointment -> appointment.equals(other);
    }

    /**
     * Updates {@code model}'s filtered list to display only {@code toDisplay}.
     */
    public static void setFilteredPetPatientList(Model model, List<PetPatient> toDisplay) {
        Optional<Predicate<PetPatient>> predicate =
                toDisplay.stream().map(ModelHelper::getPredicateMatching).reduce(Predicate::or);
        model.updateFilteredPetPatientList(predicate.orElse(PREDICATE_MATCHING_NO_PETPATIENTS));
    }

    /**
     * @see ModelHelper#setFilteredPetPatientList(Model, List)
     */
    public static void setFilteredPetPatientList(Model model, PetPatient... toDisplay) {
        setFilteredPetPatientList(model, Arrays.asList(toDisplay));
    }

    /**
     * Updates {@code model}'s filtered list to display only {@code toDisplay}.
     */
    public static void setFilteredAppointmentList(Model model, List<Appointment> toDisplay) {
        Optional<Predicate<Appointment>> predicate =
                toDisplay.stream().map(ModelHelper::getPredicateMatching).reduce(Predicate::or);
        model.updateFilteredAppointmentList(predicate.orElse(PREDICATE_MATCHING_NO_APPOINTMENTS));
    }

    /**
     * @see ModelHelper#setFilteredAppointmentList(Model, List)
     */
    public static void setFilteredAppointmentList(Model model, Appointment... toDisplay) {
        setFilteredAppointmentList(model, Arrays.asList(toDisplay));
    }
}
