package seedu.address.model.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.Remark;
import seedu.address.model.appointment.exceptions.DuplicateAppointmentException;
import seedu.address.model.appointment.exceptions.DuplicateDateTimeException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
//import seedu.address.model.person.NRIC;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.exceptions.DuplicateNricException;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.petpatient.PetPatient;
import seedu.address.model.petpatient.PetPatientName;
import seedu.address.model.petpatient.exceptions.DuplicatePetPatientException;
import seedu.address.model.tag.Tag;

//@@author wynonaK
/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"), new Nric("S0123456B"),
                getTagSet("owner")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new Nric("T0123456C"),
                getTagSet("owner")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new Nric("G0123456A"),
                getTagSet("owner")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new Nric("F0123456B"),
                getTagSet("owner")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"), new Nric("S0163456E"),
                getTagSet("owner")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"), new Nric("F0123056T"),
                getTagSet("owner")),
            new Person(new Name("Fuji Syuusuke"), new Phone("90245123"), new Email("fujis@example.com"),
                new Address("Blk 106 Bukit Purmei Street 10, #20-20"), new Nric("S9015638A"),
                getTagSet("supplier"))
        };
    }

    public static PetPatient[] getSamplePetPatients() {
        return new PetPatient[] {
            new PetPatient(new PetPatientName("Ane"), "Cat", "Siamese",
                    "Brown", "A", new Nric("S0123456B"), getTagSet("Hostile")),
            new PetPatient(new PetPatientName("Bei"), "Cat", "British Shorthair",
                    "Grey", "B", new Nric("T0123456C"), getTagSet("Overfriendly")),
            new PetPatient(new PetPatientName("Nei"), "Cat", "Maine Coon",
                    "Black", "AB", new Nric("T0123456C"), getTagSet("Aggressive")),
            new PetPatient(new PetPatientName("Chae"), "Cat", "Russian Blue",
                    "Grey", "A", new Nric("G0123456A"), getTagSet("Naive")),
            new PetPatient(new PetPatientName("Don"), "Dog", "German Shepherd",
                    "Brown", "DEA 4", new Nric("F0123456B"), getTagSet("Aggressive")),
            new PetPatient(new PetPatientName("Este"), "Dog", "Golden Retriever",
                    "Golden", "DEA 6", new Nric("S0163456E"), getTagSet("Overfriendly")),
            new PetPatient(new PetPatientName("Famm"), "Dog", "Pug",
                    "Golden", "DEA 1.1-", new Nric("F0123056T"), getTagSet("3legged")),
            new PetPatient(new PetPatientName("Plan"), "Dog", "Siberian Husky",
                    "White", "DEA 1.1+", new Nric("F0123056T"), getTagSet("Hostile")),
        };
    }

    public static Appointment[] getSampleAppointments() {
        return new Appointment[] {
            new Appointment(new Nric("S0123456B"), new PetPatientName("Ane"), new Remark("nil"),
                    getLocalDateTime("2018-10-01 10:30"), getTagSet("Checkup")),
            new Appointment(new Nric("T0123456C"), new PetPatientName("Bei"), new Remark("nil"),
                    getLocalDateTime("2018-10-02 10:30"), getTagSet("Presurgery")),
            new Appointment(new Nric("F0123056T"), new PetPatientName("Famm"), new Remark("Home visit"),
                    getLocalDateTime("2018-10-03 10:30"), getTagSet("Vaccination")),
            new Appointment(new Nric("F0123056T"), new PetPatientName("Plan"), new Remark("Home visit"),
                    getLocalDateTime("2018-10-03 11:00"), getTagSet("Vaccination")),
            new Appointment(new Nric("T0123456C"), new PetPatientName("Bei"), new Remark("nil"),
                    getLocalDateTime("2018-10-06 10:30"), getTagSet("Surgery")),
            new Appointment(new Nric("G0123456A"), new PetPatientName("Chae"), new Remark("nil"),
                    getLocalDateTime("2018-10-07 09:30"), getTagSet("Checkup")),
            new Appointment(new Nric("F0123456B"), new PetPatientName("Don"), new Remark("nil"),
                    getLocalDateTime("2018-10-07 15:30"), getTagSet("Microchipping")),
            new Appointment(new Nric("T0123456C"), new PetPatientName("Bei"), new Remark("nil"),
                    getLocalDateTime("2018-10-09 15:30"), getTagSet("Postsurgery")),
            new Appointment(new Nric("T0123456C"), new PetPatientName("Nei"), new Remark("nil"),
                    getLocalDateTime("2018-10-09 16:00"), getTagSet("Checkup")),
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        try {
            AddressBook sampleAb = new AddressBook();
            for (Person samplePerson : getSamplePersons()) {
                sampleAb.addPerson(samplePerson);
            }
            for (PetPatient petPatient : getSamplePetPatients()) {
                sampleAb.addPetPatient(petPatient);
            }
            for (Appointment appointment : getSampleAppointments()) {
                sampleAb.addAppointment(appointment);
            }
            return sampleAb;
        } catch (DuplicatePersonException e) {
            throw new AssertionError("sample data cannot contain duplicate persons", e);
        } catch (DuplicateNricException e) {
            throw new AssertionError("sample data cannot contain duplicate NRIC values", e);
        } catch (DuplicatePetPatientException e) {
            throw new AssertionError("sample data cannot contain duplicate pet patients", e);
        } catch (DuplicateDateTimeException e) {
            throw new AssertionError("sample data cannot contain double booked appointments", e);
        } catch (DuplicateAppointmentException e) {
            throw new AssertionError("sample data cannot contain duplicate appointments", e);
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

    /**
     * Returns a LocalDateTime object of the given string.
     */
    private static LocalDateTime getLocalDateTime(String string) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        return LocalDateTime.parse(string, formatter);
    }

}
