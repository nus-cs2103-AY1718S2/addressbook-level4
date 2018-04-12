package seedu.address.model.util;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.review.Review;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("John Doe"), new Phone("87438807"), new Email("johnd@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("sales", "associate"), "null"),
            new Person(new Name("Jane Doe"), new Phone("99272758"), new Email("janed@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("developer", "iOS"), "null"),
            new Person(new Name("Bill Mark"), new Phone("93210283"), new Email("billm@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("marketing", "new"), "null"),
            new Person(new Name("Anne Sue"), new Phone("91031282"), new Email("annes@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("developer", "android"), "primary"),
            new Person(new Name("Tan Ling"), new Phone("92492021"), new Email("tanlg@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("marketing", "senior"), "null"),
            new Person(new Name("Tan Seng"), new Phone("92624417"), new Email("tansg@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("marketing", "junior"), "null")
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
            throw new AssertionError("sample data cannot contain duplicate employees", e);
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

    //@@author emer7
    /**
     * Returns a review set containing the list of strings given.
     */
    public static Set<Review> getReviewSet(String... strings) {
        HashSet<Review> reviews = new HashSet<>();
        for (String s : strings) {
            reviews.add(new Review(s));
        }

        return reviews;
    }
    //@@author

}
