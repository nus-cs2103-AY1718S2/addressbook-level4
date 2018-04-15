package seedu.address.model.util;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.ActivityLevel;
import seedu.address.model.person.Address;
import seedu.address.model.person.Age;
import seedu.address.model.person.Email;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Height;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Weight;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"), new Height("169.0"), new Weight("55.5"),
                    new Gender("M"), new Age("22"), new ActivityLevel("1.2"), getTagSet("friends")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new Height("169.0"), new Weight("55.5"),
                    new Gender("F"), new Age("22"), new ActivityLevel("1.2"), getTagSet("colleagues", "friends")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new Height("169.0"), new Weight("85.5"),
                    new Gender("F"), new Age("22"), new ActivityLevel("1.2"), getTagSet("neighbours")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new Height("169.0"), new Weight("45.5"),
                    new Gender("M"), new Age("22"), new ActivityLevel("1.2"), getTagSet("family")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"), new Height("169.0"), new Weight("55.5"),
                    new Gender("M"), new Age("22"), new ActivityLevel("1.2"), getTagSet("classmates")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"), new Height("169.0"), new Weight("99.5"),
                    new Gender("M"), new Age("22"), new ActivityLevel("1.2"), getTagSet("colleagues")),
            new Person(new Name("Elliot Kingsly"), new Phone("96524617"), new Email("elliotk@example.com"),
                new Address("26 College Ave East, #11-31"), new Height("179.0"), new Weight("45.5"),
                    new Gender("M"), new Age("22"), new ActivityLevel("1.2"), getTagSet("acquaintance")),
            new Person(new Name("Fabian Journey"), new Phone("92802347"), new Email("fabianj@example.com"),
                new Address("30 College Ave East, #21-160"), new Height("169.0"), new Weight("95.5"),
                    new Gender("M"), new Age("22"), new ActivityLevel("1.2"), getTagSet("colleagues")),
            new Person(new Name("George Lincoln"), new Phone("93023853"), new Email("georgel@example.com"),
                new Address("26 Colleg Ave East, #10-150"), new Height("169.0"), new Weight("45.5"),
                    new Gender("M"), new Age("22"), new ActivityLevel("1.2"), getTagSet("colleagues")),
            new Person(new Name("Holdor Stopdor"), new Phone("91203312"), new Email("holdors@example.com"),
                new Address("26 College Ave East, #05-170A"), new Height("169.0"), new Weight("55.5"),
                    new Gender("M"), new Age("22"), new ActivityLevel("1.2"), getTagSet("cousin")),
            new Person(new Name("Julienne Vegette"), new Phone("90012593"), new Email("cutvege@example.com"),
                new Address("26 College Ave West, #20-180"), new Height("169.0"), new Weight("85.5"),
                    new Gender("F"), new Age("22"), new ActivityLevel("1.375"), getTagSet("cousin")),
            new Person(new Name("Kingler Watercrab"), new Phone("90901238"), new Email("krabby@example.com"),
                new Address("26 Seafoam Islands, #01-10"), new Height("179.0"), new Weight("65.5"),
                    new Gender("M"), new Age("22"), new ActivityLevel("1.2"), getTagSet("colleagues")),
            new Person(new Name("Ligate Tor"), new Phone("91920123"), new Email("croc@example.com"),
                new Address("17 Kerning Swamps"), new Height("169.0"), new Weight("45.5"),
                    new Gender("M"), new Age("22"), new ActivityLevel("1.2"), getTagSet("crocodile")),
            new Person(new Name("Maggie Karp"), new Phone("92112391"), new Email("uselessfish@example.com"),
                new Address("26 Hoenn Waters, #01-01"), new Height("179.0"), new Weight("75.5"),
                    new Gender("F"), new Age("22"), new ActivityLevel("1.2"), getTagSet("colleagues")),
            new Person(new Name("Never Gionna"), new Phone("90231238"), new Email("letyouup@example.com"),
                new Address("13 Rick Roll, #01-02"), new Height("169.0"), new Weight("85.5"),
                    new Gender("M"), new Age("22"), new ActivityLevel("1.2"), getTagSet("mememaker")),
            new Person(new Name("Olive Pickles"), new Phone("91032031"), new Email("vege@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"), new Height("169.0"), new Weight("45.5"),
                    new Gender("F"), new Age("22"), new ActivityLevel("1.2"), getTagSet("colleagues")),
            new Person(new Name("Pian Urse"), new Phone("82013381"), new Email("bigfish@example.com"),
                new Address("26 Aquarium Depths, #01-02"), new Height("149.0"), new Weight("105.5"),
                    new Gender("M"), new Age("72"), new ActivityLevel("1.0"), getTagSet("boss")),
            new Person(new Name("Queen Trojan"), new Phone("90012491"), new Email("neighneigh@example.com"),
                new Address("10 Ludibrium Tower, #03-01"), new Height("169.0"), new Weight("105.5"),
                    new Gender("M"), new Age("22"), new ActivityLevel("1.2"), getTagSet("horse")),
            new Person(new Name("Sparta Kook"), new Phone("91102941"), new Email("commander@example.com"),
                new Address("271 Euljiro-3-Ga"), new Height("172.0"), new Weight("82.5"),
                    new Gender("M"), new Age("22"), new ActivityLevel("1.2"), getTagSet("korean")),
            new Person(new Name("Tropi Us"), new Phone("91239910"), new Email("grassbird@example.com"),
                new Address("21 Grasslands Ave, #01-20"), new Height("189.0"), new Weight("65.5"),
                    new Gender("M"), new Age("22"), new ActivityLevel("1.2"), getTagSet("grasstype"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        try {
            AddressBook sampleAb = new AddressBook();
            for (Person samplePerson : getSamplePersons()) {
                sampleAb.addPerson(samplePerson);
            }
            return sampleAb;
        } catch (DuplicatePersonException e) {
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

}
