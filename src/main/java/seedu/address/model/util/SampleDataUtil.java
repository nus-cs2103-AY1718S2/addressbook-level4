package seedu.address.model.util;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.AddressBook;
import seedu.address.model.CalendarManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyCalendarManager;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.tag.Group;
import seedu.address.model.tag.Preference;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getGroupSet("friends"), getPreferenceSet("videoGames")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getGroupSet("colleagues", "friends"), getPreferenceSet("cosmetics", "shoes")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getGroupSet("neighbours"), getPreferenceSet("skirts")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getGroupSet("family"), getPreferenceSet("shoes")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getGroupSet("classmates"), getPreferenceSet("videoGames", "computers")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getGroupSet("colleagues"), getPreferenceSet("photobooks", "notebooks"))
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
     * Returns a group set containing the list of strings given.
     */
    public static Set<Group> getGroupSet(String... strings) {
        HashSet<Group> groupTags = new HashSet<>();
        for (String s : strings) {
            groupTags.add(new Group(s));
        }

        return groupTags;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Preference> getPreferenceSet(String... strings) {
        HashSet<Preference> prefTags = new HashSet<>();
        for (String s : strings) {
            prefTags.add(new Preference(s));
        }

        return prefTags;
    }

    public static ReadOnlyCalendarManager getSampleCalendarManager() {
        CalendarManager sampleCm = new CalendarManager();
        return sampleCm;
    }
}
