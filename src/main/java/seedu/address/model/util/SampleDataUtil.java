package seedu.address.model.util;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.timetable.Timetable;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("The True Leader"), new Phone("13371337"), new Email("OverpoweringAura@example.com"),
                new Address("123 EZLeader Street 29, #13-37"),
                    new Birthday("01011995"), new Timetable("http://modsn.us/RY8zU"), getTagSet("supremeLeader")),
            new Person(new Name("Johnny"), new Phone("87438807"), new Email("Johnny@example.com"),
                    new Address("123 Johnny Street 29, #14-32"),
                    new Birthday("01011995"), new Timetable("http://modsn.us/RY8zU"), getTagSet("friends")),
            new Person(new Name("Alan"), new Phone("31313131"), new Email("Alen@example.com"),
                    new Address("123 Ale Street 29, #04-20"),
                    new Birthday("01011995"), new Timetable("http://modsn.us/RY8zU"), getTagSet("friends")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    new Birthday("01011993"), new Timetable("http://modsn.us/7CXrQ"),
                    getTagSet("colleagues", "friends")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    new Birthday("13121988"), new Timetable("http://modsn.us/8jluS"), getTagSet("neighbours")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    new Birthday("02021993"), new Timetable("http://modsn.us/phiZF"), getTagSet("family")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                    new Birthday("26031996"), new Timetable("http://modsn.us/phiZF"), getTagSet("classmates")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                    new Birthday("26032010"), new Timetable("http://modsn.us/phiZF"), getTagSet("colleagues")),
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                    new Address("Blk 30 Geylang Street 29, #06-40"),
                    new Birthday("01011995"), new Timetable("http://modsn.us/RY8zU"), getTagSet("friends")),
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
