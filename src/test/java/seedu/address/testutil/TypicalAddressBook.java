package seedu.address.testutil;

import seedu.address.model.AddressBook;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.exceptions.DuplicateAppointmentException;
import seedu.address.model.appointment.exceptions.DuplicateDateTimeException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicateNricException;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.petpatient.PetPatient;
import seedu.address.model.petpatient.exceptions.DuplicatePetPatientException;

/**
 * A utility class that contains an {@code AddressBook} object to be used in tests.
 */
public class TypicalAddressBook {
    /**
     * Returns an {@code AddressBook} with all the typical persons, typical pet patients and typical appointments.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : TypicalPersons.getTypicalPersons()) {
            try {
                ab.addPerson(person);
            } catch (DuplicatePersonException e) {
                throw new AssertionError("not possible.");
            } catch (DuplicateNricException e) {
                throw new AssertionError("Cannot have duplicate NRIC.");
            }
        }

        for (PetPatient petPatient : TypicalPetPatients.getTypicalPetPatients()) {
            try {
                ab.addPetPatient(petPatient);
            } catch (DuplicatePetPatientException e) {
                throw new AssertionError("Duplicate pet patient.");
            }
        }

        for (Appointment appt : TypicalAppointments.getTypicalAppointments()) {
            try {
                ab.addAppointment(appt);
            } catch (DuplicateAppointmentException e) {
                throw new AssertionError("Duplicate appointment.");
            } catch (DuplicateDateTimeException e) {
                throw new AssertionError("Duplicate date time.");
            }
        }
        return ab;
    }
}
