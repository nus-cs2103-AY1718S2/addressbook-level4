package seedu.address.model.util;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Comment;
import seedu.address.model.person.Email;
import seedu.address.model.person.ExpectedGraduationYear;
import seedu.address.model.person.GradePointAverage;
import seedu.address.model.person.InterviewDate;
import seedu.address.model.person.JobApplied;
import seedu.address.model.person.Major;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ProfileImage;
import seedu.address.model.person.Rating;
import seedu.address.model.person.Resume;
import seedu.address.model.person.Status;
import seedu.address.model.person.University;
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
                new University("NUS"),
                new ExpectedGraduationYear("2018"),
                new Major("Computer Science"),
                new GradePointAverage("4.84"),
                new JobApplied("Software Engineer"),
                new Rating(4.3, 4.8,
                            4.0, 4.1),
                new Resume(null),
                new ProfileImage(null),
                new Comment("Alex is great"), new InterviewDate(1540814400L),
                new Status(), getTagSet("friendly")),

            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                new University("NTU"),
                new ExpectedGraduationYear("2019"),
                new Major("Computer Science"),
                new GradePointAverage("4.73"),
                new JobApplied("Data Analyst"),
                new Rating(-1, -1,
                        -1, -1),
                new Resume(null), new ProfileImage(null),
                new Comment(null), new InterviewDate(),
                new Status(1), getTagSet("smart", "friendly")),

            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                new University("SMU"),
                new ExpectedGraduationYear("2020"),
                new Major("Computer Science"), new GradePointAverage("4.92"),
                new JobApplied("Software Tester"),
                new Rating(4.5, 3,
                        4.5, 2.5),
                new Resume(null),
                new ProfileImage(null),
                new Comment(null), new InterviewDate(),
                new Status(5), getTagSet("referredbyBoss")),

            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                new University("SUTD"),
                new ExpectedGraduationYear("2020"),
                new Major("Computer Science"),
                new GradePointAverage("4.24"),
                new JobApplied("Network Administrator"),
                new Rating(-1, -1,
                        -1, -1),
                new Resume(null), new ProfileImage(null),
                new Comment(null), new InterviewDate(),
                new Status(3), getTagSet("referredbyBoss")),

            new Person(new Name("Edward Lee"), new Phone("85593322"), new Email("ed@lee.com"),
                new Address("King Edward VII hall"),
                new University("NUS"),
                new ExpectedGraduationYear("2021"),
                new Major("Computer and Electrical Engineering"), new GradePointAverage("4.28"),
                new JobApplied("System Engineer"),
                new Rating(3.32, 5, 4.53, 3.23),
                new Resume(null), new ProfileImage(null),
                new Comment(null), new InterviewDate(),
                new Status(4), getTagSet("serious")),

            new Person(new Name("Fiona Lina"), new Phone("95585859"), new Email("fionalina@example.com"),
                new Address("Sentosa Island"),
                new University("NTU"),
                new ExpectedGraduationYear("2018"),
                new Major("Computer Science"),
                new GradePointAverage("4.42"),
                new JobApplied("Software Engineer"),
                new Rating(4.0, 4.8,
                            4.0, 4.0),
                new Resume(null),
                new ProfileImage(null),
                new Comment("Fiona is pretty."), new InterviewDate(1540824400L),
                new Status(), getTagSet("friendly")),

            new Person(new Name("George Yeo"), new Phone("24499944"), new Email("gyeo@example.com"),
                new Address("Kent Ridge Hall"),
                new University("NTU"),
                new ExpectedGraduationYear("2019"),
                new Major("Business Analytics"),
                new GradePointAverage("3.43"),
                new JobApplied("Data Analyst"),
                new Rating(-1, -1,
                        -1, -1),
                new Resume(null), new ProfileImage(null),
                new Comment(null), new InterviewDate(),
                new Status(1), getTagSet("smart", "friendly")),

            new Person(new Name("How Rae Yeo"), new Phone("35953842"), new Email("how@are.you"),
                new Address("13 Methodist Street"),
                new University("SMU"),
                new ExpectedGraduationYear("2023"),
                new Major("Computer Science and Applied Math"), new GradePointAverage("4.92"),
                new JobApplied("Software Tester"),
                new Rating(4.5, 3,
                        4.5, 2.5),
                new Resume(null),
                new ProfileImage(null),
                new Comment(null), new InterviewDate(),
                new Status(5), getTagSet("referredbyBoss")),

            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                new University("NUS"),
                new ExpectedGraduationYear("2021"),
                new Major("Computer Science"), new GradePointAverage("4.33"),
                new JobApplied("Database Administrator"),
                new Rating(3, 5, 3.5, 3),
                new Resume(null), new ProfileImage(null),
                new Comment(null), new InterviewDate(),
                new Status(4), getTagSet("serious")),

            new Person(new Name("Julius Hassange"), new Phone("9103454"), new Email("jh@example.com"),
                new Address("300 Lead Street"),
                new University("SUTD"),
                new ExpectedGraduationYear("2020"),
                new Major("Information System"),
                new GradePointAverage("4.28"),
                new JobApplied("Network Administrator"),
                new Rating(-1, -1,
                        -1, -1),
                new Resume(null), new ProfileImage(null),
                new Comment(null), new InterviewDate(),
                new Status(3), getTagSet("referredbyBoss")),

            new Person(new Name("Koh Kok Koon"), new Phone("85593323"), new Email("kkk@example.com"),
                new Address("King Kong Lane"),
                new University("SMU"),
                new ExpectedGraduationYear("2021"),
                new Major("Electrical Engineering"), new GradePointAverage("4.83"),
                new JobApplied("System Engineer"),
                new Rating(3.32, 3.3, 4.53, 3),
                new Resume(null), new ProfileImage(null),
                new Comment(null), new InterviewDate(),
                new Status(4), getTagSet("serious")),

            new Person(new Name("Lee Lil Lol"), new Phone("87438870"), new Email("lol@lel.com"),
                new Address("101 First Avenue"),
                new University("NUS"),
                new ExpectedGraduationYear("2017"),
                new Major("Computer Security"),
                new GradePointAverage("4.41"),
                new JobApplied("Software Engineer"),
                new Rating(3.4, 4.82,
                            4.0, 4.13),
                new Resume(null),
                new ProfileImage(null),
                new Comment("LOL"), new InterviewDate(1540914400L),
                new Status(), getTagSet("friendly")),

            new Person(new Name("Mohammed Lee"), new Phone("99275885"), new Email("mlee@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #08-18"),
                new University("NTU"),
                new ExpectedGraduationYear("2019"),
                new Major("Computer Science"),
                new GradePointAverage("4.39"),
                new JobApplied("Data Analyst"),
                new Rating(-1, -1,
                        -1, -1),
                new Resume(null), new ProfileImage(null),
                new Comment(null), new InterviewDate(),
                new Status(1), getTagSet("smart", "friendly")),

            new Person(new Name("Neo Mui Leo"), new Phone("93330283"), new Email("nml@example.com"),
                new Address("Blk 11 Ho Ching Road, 74, #11-04"),
                new University("SMU"),
                new ExpectedGraduationYear("2020"),
                new Major("Computer Science"), new GradePointAverage("4.12"),
                new JobApplied("Software Tester"),
                new Rating(4.5, 3.3,
                        4.5, 2.5),
                new Resume(null),
                new ProfileImage(null),
                new Comment(null), new InterviewDate(),
                new Status(5), getTagSet("referredbyBoss")),

            new Person(new Name("Olivia Quek"), new Phone("86031282"), new Email("olivia@example.com"),
                new Address("Blk 437 Serangoon Gardens Street 26, #16-43"),
                new University("SUTD"),
                new ExpectedGraduationYear("2020"),
                new Major("Computer Science"),
                new GradePointAverage("3.83"),
                new JobApplied("Network Administrator"),
                new Rating(-1, -1,
                        -1, -1),
                new Resume(null), new ProfileImage(null),
                new Comment(null), new InterviewDate(),
                new Status(3), getTagSet("referredbyBoss")),

            new Person(new Name("Pek Yi Ling"), new Phone("85542322"), new Email("pekyl@gmail.com"),
                new Address("RVRC"),
                new University("NUS"),
                new ExpectedGraduationYear("2021"),
                new Major("Computer and Electrical Engineering"), new GradePointAverage("4.38"),
                new JobApplied("System Engineer"),
                new Rating(4.32, 5, 4.53, 2.23),
                new Resume(null), new ProfileImage(null),
                new Comment(null), new InterviewDate(),
                new Status(4), getTagSet("serious")),

            new Person(new Name("Qoo Io"), new Phone("93285859"), new Email("qoo10@example.com"),
                new Address("Central Business Distrinct"),
                new University("NTU"),
                new ExpectedGraduationYear("2018"),
                new Major("Computer Science"),
                new GradePointAverage("3.92"),
                new JobApplied("Software Engineer"),
                new Rating(3.8, 4.7,
                            4.0, 4.0),
                new Resume(null),
                new ProfileImage(null),
                new Comment("Qoo Io is rich."), new InterviewDate(1540844400L),
                new Status(), getTagSet("friendly")),

            new Person(new Name("Rar Teh Cat"), new Phone("24499235"), new Email("rtc@example.com"),
                new Address("Sheares Hall"),
                new University("NTU"),
                new ExpectedGraduationYear("2019"),
                new Major("Business Analytics"),
                new GradePointAverage("2.43"),
                new JobApplied("Data Analyst"),
                new Rating(-1, -1,
                        -1, -1),
                new Resume(null), new ProfileImage(null),
                new Comment(null), new InterviewDate(),
                new Status(1), getTagSet("smart", "friendly")),

            new Person(new Name("Stuart"), new Phone("35933842"), new Email("stuart@are.you"),
                new Address("14 Methodist Street"),
                new University("SMU"),
                new ExpectedGraduationYear("2023"),
                new Major("Computer Science and Economics"), new GradePointAverage("4.99"),
                new JobApplied("Software Tester"),
                new Rating(4.5, 3.5,
                        4.5, 4.5),
                new Resume(null),
                new ProfileImage(null),
                new Comment(null), new InterviewDate(),
                new Status(5), getTagSet("referredbyBoss")),
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
