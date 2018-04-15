package seedu.address.model.util;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Category;
import seedu.address.model.person.Client;
import seedu.address.model.person.Email;
import seedu.address.model.person.Grade;
import seedu.address.model.person.Location;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Subject;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Client[] getSampleStudents() {
        return new Client[] {
            new Client(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("FemaleTutorsOnly"), new Location("North"), new Grade("p2"), new Subject("math"),
                    new Category("s")),
            new Client(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("Urgent", "WillingToPay"), new Location("Central"), new Grade("s1"),
                    new Subject("Physics"), new Category("s")),
            new Client(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("Urgent"), new Location("North"), new Grade("p2"),
                    new Subject("Physics Chinese"), new Category("s")),
            new Client(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("ExtraCareRequired"), new Location("Central"), new Grade("s1"),
                    new Subject("Chemistry Science"), new Category("s")),
            new Client(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("Smart"), new Location("North"), new Grade("p2"), new Subject("Math"),
                    new Category("s")),
            new Client(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("Urgent"), new Location("Central"), new Grade("s1"), new Subject("Physics"),
                    new Category("s")),
        };
    }

    public static Client[] getSampleTutors() {
        return new Client[]{
            new Client(new Name("Johnny"), new Phone("87438873"), new Email("Johnnyenglish@example.com"),
                    new Address("Blk 30 London Street, #06-40"),
                    getTagSet("FemaleTutorsOnly"), new Location("North"), new Grade("p2"), new Subject("math"),
                    new Category("s")),
            new Client(new Name("James"), new Phone("3213283"), new Email("james@example.com"),
                    new Address("Blk 111 James Avenue"),
                    getTagSet("Friendly"), new Location("North South"), new Grade("s1 s2 s3 s4"),
                    new Subject("Math Science English SocialStudies Geography"), new Category("t")),
            new Client(new Name("George"), new Phone("66316282"), new Email("george@example.com"),
                    new Address("Blk 436 George Town Street 26, #16-43"),
                    getTagSet("GoodReputation"), new Location("Central"), new Grade("k1 k2"),
                    new Subject("Chinese"), new Category("t")),
            new Client(new Name("Jennifer"), new Phone("66632521"), new Email("jeniferrrrrrrr@example.com"),
                    new Address("Blk 47 Janifer Street 20, #13-35"),
                    getTagSet("Scholar"), new Location("South"), new Grade("p1 p2 p3"),
                    new Subject("Math"), new Category("t")),
            new Client(new Name("Nancy"), new Phone("666454417"), new Email("nancy@example.com"),
                    new Address("Blk 999 Queenstown 85, #01-31"),
                    getTagSet("Scholar"), new Location("Central"), new Grade("u1 u2"),
                    new Subject("Physics"), new Category("t"))
        };
    }

    public static Client[] getSampleClosedTutors() {
        return new Client[]{
            new Client(new Name("Cindy"), new Phone("81234123"), new Email("Cindyee@example.com"),
                  new Address("Blk 111 Cindy Avenue"),
                  getTagSet("Inactive"), new Location("North South"), new Grade("s1 s2 s3 s4"),
                  new Subject("Math Science English"), new Category("t")),
            new Client(new Name("Galvin"), new Phone("94443283"), new Email("Galvin@example.com"),
                    new Address("Blk 1 Galvin Avenue"),
                    getTagSet("Assigned"), new Location("South"), new Grade("u1"),
                    new Subject("Geography"), new Category("t"))
        };
    }

    public static Client[] getSampleClosedStudents() {
        return new Client[]{
            new Client(new Name("Tom"), new Phone("8134123"), new Email("Tom@example.com"),
                    new Address("Blk 121 Jerry Avenue"),
                    getTagSet("Assigned"), new Location("South"), new Grade("s1"),
                    new Subject("English"), new Category("s")),
            new Client(new Name("Jerry"), new Phone("94449983"), new Email("Jerry@example.com"),
                    new Address("Blk 1 Tom Avenue"),
                    getTagSet("Assigned"), new Location("East"), new Grade("k1"),
                    new Subject("English"), new Category("s"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        try {
            AddressBook sampleAb = new AddressBook();
            for (Client sampleStudent : getSampleStudents()) {
                sampleAb.addClient(sampleStudent);
            }
            for (Client sampleTutor : getSampleTutors()) {
                sampleAb.addClient(sampleTutor);
            }
            for (Client sampleClosedStudent : getSampleClosedStudents()) {
                sampleAb.addClosedClient(sampleClosedStudent);
            }
            for (Client sampleClosedTutor : getSampleClosedTutors()) {
                sampleAb.addClosedTutor(sampleClosedTutor);
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
