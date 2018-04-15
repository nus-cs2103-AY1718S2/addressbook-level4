package seedu.address.model.util;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Balance;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alice Pauline"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                    new Balance("0.00"), getTagSet("friends")),
            new Person(new Name("Benson Meier"), new Phone("99272758"), new Email("berniceyu@example.com"),
                    new Balance("0.00"), getTagSet("colleagues", "friends")),
            new Person(new Name("Carl Kurz"), new Phone("93210283"), new Email("charlotte@example.com"),
                    new Balance("0.00"), getTagSet("neighbours")),
            new Person(new Name("Daniel Meier"), new Phone("91031282"), new Email("lidavid@example.com"),
                    new Balance("0.00"), getTagSet("family")),
            new Person(new Name("Elle Meyer"), new Phone("92492021"), new Email("irfan@example.com"),
                    new Balance("0.00"), getTagSet("classmates")),
            new Person(new Name("Jack Kunz"), new Phone("92624417"), new Email("royb@example.com"),
                    new Balance("0.00"), getTagSet("colleagues"))
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

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static UniquePersonList getPayeesSet(String... strings) throws DuplicatePersonException {
        UniquePersonList payees = new UniquePersonList();
        for (String s : strings) {
            for (Person person: getSamplePersons()) {
                if (person.getName().fullName == s) {
                    payees.add(person);
                }
            }
        }

        return payees;
    }

}
