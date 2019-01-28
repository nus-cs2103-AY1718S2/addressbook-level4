package seedu.address.model.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.Remark;
import seedu.address.model.appointment.exceptions.ConcurrentAppointmentException;
import seedu.address.model.appointment.exceptions.DuplicateAppointmentException;
import seedu.address.model.appointment.exceptions.DuplicateDateTimeException;
import seedu.address.model.appointment.exceptions.PastAppointmentException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
//import seedu.address.model.person.NRIC;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.exceptions.DuplicateNricException;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.petpatient.BloodType;
import seedu.address.model.petpatient.Breed;
import seedu.address.model.petpatient.Colour;
import seedu.address.model.petpatient.PetPatient;
import seedu.address.model.petpatient.PetPatientName;
import seedu.address.model.petpatient.Species;
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
            new Person(new Name("Alexia Tan"), new Phone("67321372"), new Email("alexia@example.com"),
                new Address("260 Orchard Road, The Heeren ,04-30/31 238855, Singapore"), new Nric("S1199380Z"),
                getTagSet("owner")),
            new Person(new Name("Bernard Yeong"), new Phone("65457582"), new Email("bernardyeong@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new Nric("S8267808E"),
                getTagSet("owner")),
            new Person(new Name("John Cena"), new Phone("93282203"), new Email("johncena@example.com"),
                new Address("5 Airport Cargo Road #452A Core H 4th Storey, 819462, Singapore"), new Nric("S6654649G"),
                getTagSet("owner")),
            new Person(new Name("Rick Sanchez"), new Phone("62653105"), new Email("ricksanchez@example.com"),
                new Address("15 Kian Teck Road 628770, Singapore"), new Nric("S5985945E"),
                getTagSet("owner")),
            new Person(new Name("Lee Tze Ting"), new Phone("63392060"), new Email("tzeting@example.com"),
                new Address("73 Bras Basah, 07-01 470765, Singapore"), new Nric("S1209036F"),
                getTagSet("volunteer")),
            new Person(new Name("Lee Yan Hwa"), new Phone("68845060"), new Email("yanhwa@example.com"),
                new Address("69 Mohamed Sultan Raod, 239015, Singapore"), new Nric("S3643153I"),
                getTagSet("volunteer")),
            new Person(new Name("Yuuri Katsuki"), new Phone("63353388"), new Email("yuuriviktor@example.com"),
                new Address("180 Clemenceau Avenue #06-01 Haw Par Centre, 239922, Singapore"), new Nric("S4176809F"),
                getTagSet("supplier")),
            new Person(new Name("Lu Li Ming"), new Phone("62255154"), new Email("liming@example.com"),
                new Address("69 Choa Chu Kang Loop #02-12, 689672, Singapore"), new Nric("S2557566J"),
                getTagSet("owner", "spca")),
            new Person(new Name("Eileen Yeo"), new Phone("67797976"), new Email("eileen@example.com"),
                new Address("Block 51 Ayer Rajah Crescent 02-15/16 Singapore 139948, Singapore"), new Nric("S9408343E"),
                getTagSet("volunteer", "owner")),
            new Person(new Name("Liew Chin Chuan"), new Phone("63921480"), new Email("chinchuan@example.com"),
                new Address("71 Sultan Gate, 198496, Singapore"), new Nric("S2330718I"),
                getTagSet("owner", "volunteer")),
            new Person(new Name("Samson Yeow"), new Phone("63488686"), new Email("samson@example.com"),
                new Address("86 East Coast Road, 428788, Singapore"), new Nric("S7165937B"),
                getTagSet("owner", "spca")),
            new Person(new Name("Codee Ong"), new Phone("63488686"), new Email("codeeo@example.com"),
                new Address("35 Changi North Crescent, 499641, Singapore"), new Nric("S1317219F"),
                getTagSet("owner")),
            new Person(new Name("Fuji Syuusuke"), new Phone("90245123"), new Email("fujis@example.com"),
                new Address("Blk 106 Bukit Purmei Street 10, #20-20"), new Nric("S9015638A"),
                getTagSet("supplier", "owner")),
            new Person(new Name("Tezuka Kunimitsu"), new Phone("92247377"), new Email("teuzkak@example.com"),
                new Address("Blk 106 Bukit Purmei Street 10, #20-20"), new Nric("S2012044D"),
                getTagSet("supplier", "owner"))
        };
    }

    public static PetPatient[] getSamplePetPatients() {
        return new PetPatient[] {
            new PetPatient(new PetPatientName("Ane"), new Species("Cat"), new Breed("Siamese"),
                new Colour("brown"), new BloodType("A"), new Nric("S0123456B"),
                getTagSet("hostile")),
            new PetPatient(new PetPatientName("Bei"), new Species("Cat"), new Breed("British Shorthair"),
                new Colour("grey"), new BloodType("B"), new Nric("T0123456C"),
                getTagSet("depression")),
            new PetPatient(new PetPatientName("Nei"), new Species("Cat"), new Breed("Maine Coon"),
                new Colour("black"), new BloodType("AB"), new Nric("T0123456C"),
                getTagSet("aggressive")),
            new PetPatient(new PetPatientName("Chae"), new Species("Cat"), new Breed("Russian Blue"),
                new Colour("grey"), new BloodType("A"), new Nric("G0123456A"),
                getTagSet("fiv")),
            new PetPatient(new PetPatientName("Don"), new Species("Dog"), new Breed("German Shepherd"),
                new Colour("brown"), new BloodType("DEA 4+"), new Nric("F0123456B"),
                getTagSet("aggressive")),
            new PetPatient(new PetPatientName("Este"), new Species("Dog"), new Breed("Golden Retriever"),
                new Colour("golden"), new BloodType("DEA 6+"), new Nric("S0163456E"),
                getTagSet("microchipped")),
            new PetPatient(new PetPatientName("Famm"), new Species("Dog"), new Breed("Pug"),
                new Colour("golden"), new BloodType("DEA 1.1-"), new Nric("F0123056T"),
                getTagSet("3legged")),
            new PetPatient(new PetPatientName("Plan"), new Species("Dog"), new Breed("Siberian Husky"),
                new Colour("white"), new BloodType("DEA 1.1+"), new Nric("F0123056T"),
                getTagSet("hostile", "newborn")),
            new PetPatient(new PetPatientName("Blu"), new Species("Cat"), new Breed("Burmese"),
                new Colour("brown"), new BloodType("A"), new Nric("S1199380Z"),
                getTagSet("hostile", "fiv")),
            new PetPatient(new PetPatientName("Red"), new Species("Cat"), new Breed("Cornish Rex"),
                new Colour("white"), new BloodType("B"), new Nric("S8267808E"),
                getTagSet("fiv")),
            new PetPatient(new PetPatientName("Fluffy"), new Species("Cat"), new Breed("Birman"),
                new Colour("white"), new BloodType("AB"), new Nric("S6654649G"),
                getTagSet("aggressive")),
            new PetPatient(new PetPatientName("Scooby"), new Species("Cat"), new Breed("Ocicat"),
                new Colour("white"), new BloodType("A"), new Nric("S5985945E"),
                getTagSet("hostile", "newborn")),
            new PetPatient(new PetPatientName("Snowball"), new Species("Dog"), new Breed("Rottweiler"),
                new Colour("brown and black"), new BloodType("DEA 4+"), new Nric("S2557566J"),
                getTagSet("aggressive", "microchipped")),
            new PetPatient(new PetPatientName("Wabbit"), new Species("Dog"), new Breed("Beagle"),
                new Colour("brown and white"), new BloodType("DEA 6+"), new Nric("S2557566J"),
                getTagSet("microchipped")),
            new PetPatient(new PetPatientName("Oreo"), new Species("Dog"), new Breed("Dalmation"),
                new Colour("black and white"), new BloodType("DEA 1.1+"), new Nric("S9408343E"),
                getTagSet("3legged")),
            new PetPatient(new PetPatientName("Milkshake"), new Species("Bird"), new Breed("Black Throated Sparrow"),
                new Colour("black and white"), new BloodType("NIL"), new Nric("S2330718I"),
                getTagSet("newborn", "missing")),
            new PetPatient(new PetPatientName("Ginger"), new Species("Bird"), new Breed("Amazon Parrot"),
                new Colour("green"), new BloodType("NIL"), new Nric("S2330718I"),
                getTagSet("hostile")),
            new PetPatient(new PetPatientName("Juniper"), new Species("Chinchilla"), new Breed("Lanigera Chinchilla"),
                new Colour("grey"), new BloodType("NIL"), new Nric("S2330718I"),
                getTagSet("newborn")),
            new PetPatient(new PetPatientName("Baron"), new Species("Chinchilla"), new Breed("Brevicaudata Chinchilla"),
                new Colour("black"), new BloodType("NIL"), new Nric("S7165937B"),
                getTagSet("microchipped", "allergy")),
            new PetPatient(new PetPatientName("Sting"), new Species("Guinea Pig"), new Breed("Abyssinian Guinea Pig"),
                new Colour("white"), new BloodType("B RH-"), new Nric("S7165937B"),
                getTagSet("newborn", "hostile")),
            new PetPatient(new PetPatientName("Riddle"), new Species("Guinea Pig"), new Breed("Skinny Pig"),
                new Colour("black and white"), new BloodType("A RH+"), new Nric("S7165937B"),
                getTagSet("aggressive", "allergy")),
            new PetPatient(new PetPatientName("Tiki"), new Species("Guinea Pig"), new Breed("Teddy Guinea Pig"),
                new Colour("golden"), new BloodType("AB RH+"), new Nric("S0163456E"),
                getTagSet("drooling", "newborn")),
            new PetPatient(new PetPatientName("Hero"), new Species("Dog"), new Breed("German Shepherd"),
                new Colour("black"), new BloodType("DEA 1.1+"), new Nric("S2012044D"),
                getTagSet("newborn")),
            new PetPatient(new PetPatientName("Thorn"), new Species("Cat"), new Breed("Chinchilla Persian"),
                new Colour("white"), new BloodType("AB"), new Nric("S9015638A"),
                getTagSet("newborn")),
            new PetPatient(new PetPatientName("Alpha"), new Species("Dog"), new Breed("Alaskan Malamute"),
                new Colour("black and white"), new BloodType("DEA 4+"), new Nric("S1317219F"),
                getTagSet("aggressive", "newborn")),
            new PetPatient(new PetPatientName("Beta"), new Species("Dog"), new Breed("Alaskan Malamute"),
                new Colour("brown and white"), new BloodType("DEA 6+"), new Nric("S1317219F"),
                getTagSet("microchipped", "hostile")),
            new PetPatient(new PetPatientName("Gamma"), new Species("Dog"), new Breed("Alaskan Malamute"),
                new Colour("red and white"), new BloodType("DEA 1.1-"), new Nric("S1317219F"),
                getTagSet("microchipped")),
            new PetPatient(new PetPatientName("Delta"), new Species("Dog"), new Breed("Alaskan Malamute"),
                new Colour("brown and white"), new BloodType("DEA 1.1+"), new Nric("S1317219F"),
                getTagSet("microchipped")),
            new PetPatient(new PetPatientName("Epsilon"), new Species("Dog"), new Breed("Alaskan Malamute"),
                new Colour("seal and white"), new BloodType("DEA 4+"), new Nric("S1317219F"),
                getTagSet("microchipped", "aggressive")),
            new PetPatient(new PetPatientName("Zeta"), new Species("Dog"), new Breed("Alaskan Malamute"),
                new Colour("sable and white"), new BloodType("DEA 4-"), new Nric("F0123056T"),
                getTagSet("microchipped", "senior")),
            new PetPatient(new PetPatientName("Eta"), new Species("Dog"), new Breed("Alaskan Malamute"),
                new Colour("brown and white"), new BloodType("DEA 6+"), new Nric("S1317219F"),
                getTagSet("microchipped")),
            new PetPatient(new PetPatientName("Theta"), new Species("Dog"), new Breed("Alaskan Malamute"),
                new Colour("gray and white"), new BloodType("DEA 1.1+"), new Nric("S1317219F"),
                getTagSet("microchipped", "senior")),
            new PetPatient(new PetPatientName("Iota"), new Species("Dog"), new Breed("Alaskan Malamute"),
                new Colour("black and white"), new BloodType("DEA 1.1+"), new Nric("S1317219F"),
                getTagSet("microchipped", "arthritis"))
        };
    }


    public static Appointment[] getSampleAppointments() {
        return new Appointment[] {
            new Appointment(new Nric("S0123456B"), new PetPatientName("Ane"), new Remark("nil"),
                    getLocalDateTime("2018-10-01 10:30"), getTagSet("checkup")),
            new Appointment(new Nric("T0123456C"), new PetPatientName("Bei"), new Remark("nil"),
                    getLocalDateTime("2018-10-02 10:30"), getTagSet("presurgery")),
            new Appointment(new Nric("F0123056T"), new PetPatientName("Famm"), new Remark("Home visit"),
                    getLocalDateTime("2018-10-03 10:30"), getTagSet("vaccination")),
            new Appointment(new Nric("F0123056T"), new PetPatientName("Plan"), new Remark("Home visit"),
                    getLocalDateTime("2018-10-03 11:00"), getTagSet("vaccination")),
            new Appointment(new Nric("T0123456C"), new PetPatientName("Bei"), new Remark("nil"),
                    getLocalDateTime("2018-10-06 10:30"), getTagSet("surgery")),
            new Appointment(new Nric("G0123456A"), new PetPatientName("Chae"), new Remark("nil"),
                    getLocalDateTime("2018-10-07 09:30"), getTagSet("checkup")),
            new Appointment(new Nric("F0123456B"), new PetPatientName("Don"), new Remark("nil"),
                    getLocalDateTime("2018-10-07 15:30"), getTagSet("microchipping")),
            new Appointment(new Nric("T0123456C"), new PetPatientName("Bei"), new Remark("nil"),
                    getLocalDateTime("2018-10-09 15:30"), getTagSet("postsurgery")),
            new Appointment(new Nric("T0123456C"), new PetPatientName("Nei"), new Remark("nil"),
                    getLocalDateTime("2018-10-09 16:00"), getTagSet("checkup")),
            new Appointment(new Nric("S1199380Z"), new PetPatientName("Blu"), new Remark("nil"),
                    getLocalDateTime("2018-06-01 10:30"), getTagSet("vaccination")),
            new Appointment(new Nric("S8267808E"), new PetPatientName("Red"), new Remark("Home visit"),
                    getLocalDateTime("2018-06-01 11:30"), getTagSet("checkup")),
            new Appointment(new Nric("S6654649G"), new PetPatientName("Fluffy"), new Remark("nil"),
                    getLocalDateTime("2018-06-02 10:30"), getTagSet("vaccination")),
            new Appointment(new Nric("S9408343E"), new PetPatientName("Oreo"), new Remark("nil"),
                    getLocalDateTime("2018-06-02 11:00"), getTagSet("microchipping")),
            new Appointment(new Nric("S2557566J"), new PetPatientName("Wabbit"), new Remark("nil"),
                    getLocalDateTime("2018-06-03 10:30"), getTagSet("sterilisation")),
            new Appointment(new Nric("S2330718I"), new PetPatientName("Ginger"), new Remark("nil"),
                    getLocalDateTime("2018-06-03 09:30"), getTagSet("checkup")),
            new Appointment(new Nric("F0123456B"), new PetPatientName("Juniper"), new Remark("Might require stay"),
                    getLocalDateTime("2018-06-04 15:30"), getTagSet("sterilisation")),
            new Appointment(new Nric("S7165937B"), new PetPatientName("Baron"), new Remark("nil"),
                    getLocalDateTime("2018-06-04 16:30"), getTagSet("checkup")),
            new Appointment(new Nric("S7165937B"), new PetPatientName("Sting"), new Remark("Home visit"),
                    getLocalDateTime("2018-06-05 16:00"), getTagSet("vaccination")),
            new Appointment(new Nric("S7165937B"), new PetPatientName("Riddle"), new Remark("Might require stay"),
                    getLocalDateTime("2018-06-06 15:30"), getTagSet("sterilisation")),
            new Appointment(new Nric("S0163456E"), new PetPatientName("Tiki"), new Remark("nil"),
                    getLocalDateTime("2018-06-07 16:30"), getTagSet("checkup")),
            new Appointment(new Nric("S2012044D"), new PetPatientName("Hero"), new Remark("Might require stay"),
                    getLocalDateTime("2018-06-08 16:00"), getTagSet("sterilisation")),
            new Appointment(new Nric("S9015638A"), new PetPatientName("Thorn"), new Remark("Might require stay"),
                    getLocalDateTime("2018-06-08 18:00"), getTagSet("sterilisation")),
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
        } catch (ConcurrentAppointmentException cae) {
            throw new AssertionError("AddressBook should not add appointments to on-going appointment slots");
        } catch (PastAppointmentException pae) {
            throw new AssertionError("AddressBook should not add appointments with past DateTime");
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
