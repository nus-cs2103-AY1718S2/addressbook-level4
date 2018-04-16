package seedu.address.model.util;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Age;
import seedu.address.model.person.Email;
import seedu.address.model.person.Expenditure;
import seedu.address.model.person.Income;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[]{
            new Person(
                new Name("Alex Yeoh"),
                new Phone("87438807"),
                new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("friends"),
                //@@author SoilChang
                new Income(2000.00),
                new Expenditure(2000.0),
                new Expenditure(2000.0),
                //@@author
                new Age(20),
                Optional.empty()
            ),
            new Person(
                new Name("Bernice Yu"),
                new Phone("99272758"),
                new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("colleagues", "friends"),
                //@@author SoilChang
                new Income(2200.00),
                new Expenditure(2000.0),
                new Expenditure(2000.0),
                //@@author
                new Age(20),
                Optional.empty()
            ),

            //@@author SoilChang
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("neighbours"), new Income(34550.00), new Expenditure(2000.0),
                new Expenditure(2000.0), new Age(20), Optional.empty()),
            //@@author

            //@@author SoilChang
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("family"), new Income(202323.00), new Expenditure(2000.0),
                new Expenditure(2000.0), new Age(20), Optional.empty()),
            //@@author

            //@@author SoilChang
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("classmates"), new Income(5000.00), new Expenditure(2000.0),
                new Expenditure(2000.0), new Age(20), Optional.empty()),
            //@@author SoilChang

            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("colleagues"), new Income(7000.00), new Expenditure(2000.0),
                new Expenditure(2000.0), new Age(20), Optional.empty()),

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
