package seedu.address.model.util;

import java.util.HashSet;
import java.util.Set;

import com.google.common.base.Strings;

import seedu.address.model.Imdb;
import seedu.address.model.ReadOnlyImdb;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.patient.Address;
import seedu.address.model.patient.BloodType;
import seedu.address.model.patient.DateOfBirth;
import seedu.address.model.patient.Email;
import seedu.address.model.patient.Name;
import seedu.address.model.patient.Nric;
import seedu.address.model.patient.Patient;
import seedu.address.model.patient.Phone;
import seedu.address.model.patient.RecordList;
import seedu.address.model.patient.Remark;
import seedu.address.model.patient.exceptions.DuplicatePatientException;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code Imdb} with sample data.
 */
public class SampleDataUtil {
    public static Patient[] getSamplePersons() {
        return new Patient[] {
            new Patient(new Name("Alex Yeoh"), new Nric("S1234567A"), new Phone("87438807"),
                    new Email("alexyeoh@example.com"),
                    new Address("Blk 30 Geylang Street 29, #06-40"), new DateOfBirth("11/11/1991"),
                    new BloodType("A+"), new Remark(""), new RecordList(), getTagSet("G6PD"),
                    getAppointmentSet("21/2/2018 1000", "19/3/2018 1530", "25/4/2018 0930")),
            new Patient(new Name("Bernice Yu"), new Nric("S7654321B"), new Phone("99272758"),
                    new Email("berniceyu@example.com"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new DateOfBirth("10/10/1990"),
                    new BloodType("B-"), new Remark(""), new RecordList(), getTagSet("aspirin", "asthma"),
                    getAppointmentSet("2/2/2018 1030", "13/2/2018 1830")),
            new Patient(new Name("Charlotte Oliveiro"), new Nric("S5671234C"), new Phone("93210283"),
                    new Email("charlotte@example.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new DateOfBirth("09/09/1989"),
                    new BloodType("A+"), new Remark(""), new RecordList(), getTagSet("peanuts"),
                    getAppointmentSet("21/3/2018 1100", "2/4/2018 1430")),
            new Patient(new Name("David Li"), new Nric("S3456712D"), new Phone("91031282"),
                    new Email("lidavid@example.com"),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new DateOfBirth("08/08/1988"),
                    new BloodType("B+"), new Remark(""), new RecordList(), getTagSet("seafood"),
                    getAppointmentSet("21/4/2018 1130")),
            new Patient(new Name("Irfan Ibrahim"), new Nric("S5673412E"), new Phone("92492021"),
                    new Email("irfan@example.com"),
                    new Address("Blk 47 Tampines Street 20, #17-35"), new DateOfBirth("07/07/1987"),
                    new BloodType("O-"), new Remark(""), new RecordList(),
                    getTagSet("ibuprofen"), getAppointmentSet("")),
            new Patient(new Name("Roy Balakrishnan"), new Nric("S1234512F"), new Phone("92624417"),
                    new Email("royb@example.com"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"), new DateOfBirth("06/06/1986"),
                    new BloodType("O+"), new Remark(""), new RecordList(), getTagSet("paracetomol"),
                    getAppointmentSet("26/3/2018 1600"))
        };
    }

    public static ReadOnlyImdb getSampleAddressBook() {
        try {
            Imdb sampleAb = new Imdb();
            for (Patient samplePatient : getSamplePersons()) {
                sampleAb.addPerson(samplePatient);
            }
            return sampleAb;
        } catch (DuplicatePatientException e) {
            throw new AssertionError("sample data cannot contain duplicate persons", e);
        }
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        HashSet<Tag> tags = new HashSet<>();
        for (String s : strings) {
            tags.add(new Tag(s));
        }

        return tags;
    }

    public static Set<Appointment> getAppointmentSet(String ... strings) {
        HashSet<Appointment> appointments = new HashSet<>();
        for (String s : strings) {
            if (!Strings.isNullOrEmpty(s)) {
                appointments.add(new Appointment(s));
            }
        }

        return appointments;
    }
}
