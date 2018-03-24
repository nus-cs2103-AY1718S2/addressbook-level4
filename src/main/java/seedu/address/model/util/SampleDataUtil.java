package seedu.address.model.util;

import static java.util.Objects.isNull;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.ExpectedGraduationYear;
import seedu.address.model.person.InterviewDate;
import seedu.address.model.person.Major;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Rating;
import seedu.address.model.person.Resume;
import seedu.address.model.person.Status;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    private static final String resumePath = "src/main/resources/resume/";

    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"), new ExpectedGraduationYear("2018"),
                new Major("Computer Science"),
                new Rating(4.3, 4.8,
                            4.0, 4.1),
                new Resume(formPathFromFileName("alex.pdf")), new InterviewDate(1540814400L),
                new Status(), getTagSet("friends")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new ExpectedGraduationYear("2019"),
                    new Major("Computer Science"),
                new Rating(-1, -1,
                        -1, -1),
                new Resume(null), new InterviewDate(), new Status(1),
                getTagSet("colleagues", "friends")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new ExpectedGraduationYear("2020"),
                    new Major("Computer Science"),
                new Rating(4.5, 3,
                        4.5, 2.5),
                new Resume(formPathFromFileName("char.pdf")), new InterviewDate(), new Status(5),
                getTagSet("neighbours")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new ExpectedGraduationYear("2020"),
                    new Major("Computer Science"),
                new Rating(-1, -1,
                        -1, -1),
                new Resume(null), new InterviewDate(), new Status(3),
                getTagSet("family")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"), new ExpectedGraduationYear("2021"),
                    new Major("Computer Science"),
                new Rating(3, 5, 3.5, 3),
                new Resume(null), new InterviewDate(), new Status(4),
                getTagSet("classmates")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"), new ExpectedGraduationYear("2019"),
                    new Major("Computer Science"),
                new Rating(-1, -1,
                        -1, -1),
                new Resume(null), new InterviewDate(), new Status(2),
                getTagSet("colleagues"))
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
     * Forms the resume path from resume file name
     */
    private static String formPathFromFileName(String fileName) {
        if (isNull(fileName)) {
            return null;
        }
        return resumePath + fileName;
    }

}
