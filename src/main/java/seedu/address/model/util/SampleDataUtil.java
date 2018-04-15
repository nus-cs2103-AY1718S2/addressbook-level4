package seedu.address.model.util;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.pair.PairHash;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Level;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Price;
import seedu.address.model.person.Rate;
import seedu.address.model.person.Remark;
import seedu.address.model.person.Role;
import seedu.address.model.person.Status;
import seedu.address.model.person.Subject;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                new Price("100"),
                new Subject("math"),
                new Level("Upper Sec"),
                new Status("Not Matched"),
                new Role("Student"),
                getTagSet("100", "Math", "Upper Sec", "Not Matched", "Student"),
                new Remark("Hardworking student."),
                Rate.initializeRate(),
                PairHash.getDefaultPairHashSet()),

            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                new Price("50"),
                new Subject("English"),
                new Level("Upper Sec"),
                new Status("Not Matched"),
                new Role("Student"),
                getTagSet("50", "English", "Upper Sec", "Not Matched", "Student"),
                new Remark("Very active, requires more attention."),
                Rate.initializeRate(),
                PairHash.getDefaultPairHashSet()),

            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                new Price("150"),
                new Subject("chinese"),
                new Level("Upper Sec"),
                new Status("Not Matched"),
                new Role("Student"),
                getTagSet("150", "Chinese", "Upper Sec", "Not Matched", "Student"),
                new Remark("Hardworking but very weak in Chinese."),
                Rate.initializeRate(),
                PairHash.getDefaultPairHashSet()),

            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                new Price("70"),
                new Subject("chinese"),
                new Level("Upper Sec"),
                new Status("Not Matched"),
                new Role("Tutor"),
                getTagSet("70", "Chinese", "Upper Sec", "Not Matched", "Tutor"),
                new Remark("Friendly and approachable."),
                Rate.initializeRate(),
                PairHash.getDefaultPairHashSet()),

            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                new Price("20"),
                new Subject("English"),
                new Level("Lower Sec"),
                new Status("Not Matched"),
                new Role("Tutor"),
                getTagSet("20", "English", "Lower Sec", "Not Matched", "Tutor"),
                new Remark("Bad tutor, very impatient."),
                Rate.initializeRate(),
                PairHash.getDefaultPairHashSet()),

            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                new Price("40"),
                new Subject("English"),
                new Level("Upper Sec"),
                new Status("Not Matched"),
                new Role("Tutor"),
                getTagSet("40", "English", "Upper Sec", "Not Matched", "Tutor"),
                new Remark("Generally friendly, but not detailed in teaching."),
                Rate.initializeRate(),
                PairHash.getDefaultPairHashSet()),
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
        for (int i = 0; i < strings.length; i++) {
            switch (i) {
            case 0:
                tags.add(new Tag(strings[i], Tag.AllTagTypes.PRICE));
                break;
            case 1:
                tags.add(new Tag(strings[i], Tag.AllTagTypes.SUBJECT));
                break;
            case 2:
                tags.add(new Tag(strings[i], Tag.AllTagTypes.LEVEL));
                break;
            case 3:
                tags.add(new Tag(strings[i], Tag.AllTagTypes.STATUS));
                break;
            case 4:
                tags.add(new Tag(strings[i], Tag.AllTagTypes.ROLE));
                break;
            default:
                break;
            }
        }

        return tags;
    }

}
