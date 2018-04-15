//@@author ongkuanyang
package seedu.address.model.appointment;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;

/**
 * Represents an appointment in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Appointment {

    private final AppointmentName name;
    private final AppointmentTime time;

    private final UniquePersonList persons;

    /**
     * Every field must be present and not null.
     */
    public Appointment(AppointmentName name, AppointmentTime time, UniquePersonList persons) {
        requireAllNonNull(name, time);
        this.name = name;
        this.time = time;
        this.persons = persons;
    }


    public AppointmentName getName() {
        return name;
    }

    public AppointmentTime getTime() {
        return time;
    }

    /**
     * Returns an immutable persons list, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public ObservableList<Person> getPersons() {
        return persons.asObservableList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Appointment)) {
            return false;
        }

        Appointment otherAppointment = (Appointment) other;
        return otherAppointment.getName().equals(this.getName())
                && otherAppointment.getTime().equals(this.getTime());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, time);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Time: ")
                .append(getTime())
                .append(" Persons: ");
        getPersons().forEach(builder::append);
        return builder.toString();
    }

}
//@@author
