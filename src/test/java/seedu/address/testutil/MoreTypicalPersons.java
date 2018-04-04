package seedu.address.testutil;

import static java.util.Objects.isNull;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
/**
 * A utility class containing a longer list of {@code Person} objects to be used in tests.
 */
public class MoreTypicalPersons {
    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("85355255").withUniversity("NUS").withExpectedGraduationYear("2020")
            .withMajor("Computer Science")
            .withGradePointAverage("4.84")
            .withJobApplied("Software Engineer")
            .withRating("2.7", "3.9",
                    "2.5", "2.5")
            .withResume(formPathFromFileName("alice.pdf"))
            .withProfileImage(formImagePathFromFileName("elon.jpg"))
            .withComment("Alice!")
            .withInterviewDate(LocalDateTime.of(2018, Month.APRIL, 2, 14, 0, 0))
            .withTags("friends").build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25").withResume(formPathFromFileName(null))
            .withEmail("johnd@example.com").withPhone("98765432").withUniversity("NTU")
            .withExpectedGraduationYear("2021")
            .withMajor("Computer Engineering")
            .withGradePointAverage("4.73")
            .withJobApplied("Software Engineer")
            .withRating("4", "4.5",
                    "3", "3.5")
            .withProfileImage(formImagePathFromFileName("gates.jpg"))
            .withComment("Benson!")
            .withInterviewDate(LocalDateTime.of(2018, Month.APRIL, 6, 16, 0, 0))
            .withStatus(2).withTags("owesMoney", "friends").build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withAddress("wall street").withUniversity("SMU")
            .withExpectedGraduationYear("2019")
            .withMajor("Computer Science")
            .withGradePointAverage("4.92")
            .withJobApplied("Front-end Developer")
            .withRating("-1", "-1",
                    "-1", "-1")
            .withProfileImage(null).withComment(null).build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withAddress("10th street").withUniversity("SUTD")
            .withExpectedGraduationYear("2020")
            .withMajor("Information Security")
            .withGradePointAverage("4.24")
            .withJobApplied("Web Security Researcher")
            .withRating("-1", "-1",
                    "-1", "-1")
            .withProfileImage(formImagePathFromFileName("jobs.jpg"))
            .withComment("Daniel!")
            .withResume(formPathFromFileName("daniel.pdf")).build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").withAddress("michegan ave").withUniversity("NUS")
            .withExpectedGraduationYear("2018")
            .withMajor("Business Analytics")
            .withGradePointAverage("4.33")
            .withJobApplied("Big Data Analyst")
            .withRating("-1", "-1",
                    "-1", "-1")
            .withProfileImage(formImagePathFromFileName("larry.jpg"))
            .withComment("Elle!").withStatus(7).build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").withAddress("little tokyo").withUniversity("NUS")
            .withExpectedGraduationYear("2019")
            .withMajor("Computer Science")
            .withGradePointAverage("4.75")
            .withJobApplied("Software Engineer")
            .withRating("-1", "-1",
                    "-1", "-1")
            .withProfileImage(formImagePathFromFileName("mark.jpg"))
            .withComment("Fiona!").withStatus(1).build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com").withAddress("4th street").withUniversity("NYU")
            .withExpectedGraduationYear("2022")
            .withMajor("Information Systems")
            .withGradePointAverage("3.88")
            .withJobApplied("Software Engineer")
            .withRating("-1", "-1",
                    "-1", "-1")
            .withResume(formPathFromFileName("george.pdf")).withStatus(6)
            .withProfileImage(null).withComment(null).build();
    public static final Person HOWARD = new PersonBuilder().withName("HOWARD LEE")
            .withAddress("124, Jurong East Ave 8, #09-111").withEmail("hlee@example.com")
            .withPhone("86355255").withUniversity("NUS").withExpectedGraduationYear("2020")
            .withMajor("Computer Science")
            .withGradePointAverage("4.04")
            .withJobApplied("Software Tester")
            .withRating("2.79", "3.99",
                    "2.59", "2.59")
            .withResume(null)
            .withProfileImage(null)
            .withComment("Alice!")
            .withInterviewDate(LocalDateTime.of(2018, Month.APRIL, 2, 10, 0, 0))
            .withTags("friends").build();
    public static final Person IOTA = new PersonBuilder().withName("Iota Small")
            .withAddress("311, Tiny Drive 2, #02-25").withResume(null)
            .withEmail("io@example.com").withPhone("98765431").withUniversity("NTU")
            .withExpectedGraduationYear("2020")
            .withMajor("Computer Engineering")
            .withGradePointAverage("4.30")
            .withJobApplied("Software Engineer")
            .withRating("4", "4.5",
                    "3", "3.5")
            .withProfileImage(null)
            .withComment("Small!")
            .withInterviewDate(LocalDateTime.of(2018, Month.APRIL, 7, 16, 0, 0))
            .withStatus(2).withTags("owesMoney", "friends").build();
    public static final Person JASMINE = new PersonBuilder().withName("Jasmine Julie").withPhone("95352542")
            .withEmail("jas@mean.com").withAddress("Jasmine Farm").withUniversity("SMU")
            .withExpectedGraduationYear("2019")
            .withMajor("Computer Science")
            .withGradePointAverage("4.29")
            .withJobApplied("Front-end Developer")
            .withRating("-1", "-1",
                    "-1", "-1")
            .withProfileImage(null).withComment(null).build();
    public static final Person KAHOOT = new PersonBuilder().withName("KAHOOT").withPhone("87652534")
            .withEmail("itsme@kahoot.com").withAddress("11th street").withUniversity("SUTD")
            .withExpectedGraduationYear("2020")
            .withMajor("Information Security")
            .withGradePointAverage("4.49")
            .withJobApplied("Web Security Researcher")
            .withRating("-1", "-1",
                    "-1", "-1")
            .withProfileImage(null)
            .withComment("Kahoot!")
            .withResume(null).build();
    public static final Person LOUIS = new PersonBuilder().withName("Louis Lee").withPhone("94822241")
            .withEmail("lsquared@example.com").withAddress("lol ave").withUniversity("NUS")
            .withExpectedGraduationYear("2018")
            .withMajor("Business Analytics")
            .withGradePointAverage("3.33")
            .withJobApplied("Big Data Analyst")
            .withRating("-1", "-1",
                    "-1", "-1")
            .withProfileImage(null)
            .withComment("LOL!").withStatus(7).build();
    public static final Person MILLS = new PersonBuilder().withName("Mill Mills").withPhone("8482427")
            .withEmail("m2@example.com").withAddress("tiny tokyo").withUniversity("NUS")
            .withExpectedGraduationYear("2019")
            .withMajor("Computer Science")
            .withGradePointAverage("4.57")
            .withJobApplied("Software Engineer")
            .withRating("-1", "-1",
                    "-1", "-1")
            .withProfileImage(null)
            .withComment("M1+!").withStatus(1).build();
    public static final Person NULL = new PersonBuilder().withName("Null Neil").withPhone("00000000")
            .withEmail("noemail@null.com").withAddress("Nowhere").withUniversity("NYU")
            .withExpectedGraduationYear("2022")
            .withMajor("Information Systems")
            .withGradePointAverage("3.88")
            .withJobApplied("Software Engineer")
            .withRating("-1", "-1",
                    "-1", "-1")
            .withResume(null).withStatus(6)
            .withProfileImage(null).withComment(null).build();
    public static final Person OLIVER = new PersonBuilder().withName("Oliver Ford")
            .withAddress("223, Tampines Ave 6, #08-111").withEmail("oliv@example.com")
            .withPhone("85332125").withUniversity("NUS").withExpectedGraduationYear("2020")
            .withMajor("Computer Science")
            .withGradePointAverage("3.89")
            .withJobApplied("Software Engineer")
            .withRating("2.72", "3.91",
                    "2.53", "2.5")
            .withResume(null)
            .withProfileImage(null)
            .withComment("Oliver!")
            .withInterviewDate(LocalDateTime.of(2018, Month.APRIL, 10, 14, 0, 0))
            .withTags("friends").build();
    public static final Person PERL = new PersonBuilder().withName("INN PERL")
            .withAddress("3, Clementi Ave 3, #03-35").withResume(null)
            .withEmail("perl@example.com").withPhone("82282282").withUniversity("NTU")
            .withExpectedGraduationYear("2021")
            .withMajor("Computer Engineering")
            .withGradePointAverage("4.70")
            .withJobApplied("Software Engineer")
            .withRating("4", "3.5",
                    "3", "3.5")
            .withProfileImage(null)
            .withComment("Perl!")
            .withInterviewDate(LocalDateTime.of(2018, Month.APRIL, 11, 16, 0, 0))
            .withStatus(2).withTags("owesMoney", "friends").build();
    public static final Person ROLLE = new PersonBuilder().withName("ROLLE THEO").withPhone("23112233")
            .withEmail("rolle@theo.rem").withAddress("Dead Avenue").withUniversity("SMU")
            .withExpectedGraduationYear("2019")
            .withMajor("Mathematics")
            .withGradePointAverage("4.22")
            .withJobApplied("Front-end Developer")
            .withRating("-1", "-1",
                    "-1", "-1")
            .withProfileImage(null).withComment(null).build();
    public static final Person SALLY = new PersonBuilder().withName("Sally Silly").withPhone("84654534")
            .withEmail("sa@ll.com").withAddress("Silk Road").withUniversity("SUTD")
            .withExpectedGraduationYear("2020")
            .withMajor("Information Security")
            .withGradePointAverage("3.24")
            .withJobApplied("Web Security Researcher")
            .withRating("-1", "-1",
                    "-1", "-1")
            .withProfileImage(null)
            .withComment("Silly!")
            .withResume(null).build();
    public static final Person TALL = new PersonBuilder().withName("Tall Wang").withPhone("90844444")
            .withEmail("reallytall@example.com").withAddress("Short Street").withUniversity("NUS")
            .withExpectedGraduationYear("2018")
            .withMajor("Business Analytics")
            .withGradePointAverage("3.33")
            .withJobApplied("Big Data Analyst")
            .withRating("-1", "-1",
                    "-1", "-1")
            .withProfileImage(null)
            .withComment("Really tall!").withStatus(7).build();
    public static final Person ULYSS = new PersonBuilder().withName("Ulyss Grant").withPhone("948223243")
            .withEmail("ulyss@example.com").withAddress("Hero Lane").withUniversity("NUS")
            .withExpectedGraduationYear("2019")
            .withMajor("Computer Science")
            .withGradePointAverage("4.75")
            .withJobApplied("Software Engineer")
            .withRating("-1", "-1",
                    "-1", "-1")
            .withProfileImage(null)
            .withComment("War!").withStatus(1).build();
    public static final Person VICTOR = new PersonBuilder().withName("Victor Tan").withPhone("948223142")
            .withEmail("victor@tan.com").withAddress("S17").withUniversity("NYU")
            .withExpectedGraduationYear("2022")
            .withMajor("Information Systems")
            .withGradePointAverage("3.08")
            .withJobApplied("Software Engineer")
            .withRating("-1", "-1",
                    "-1", "-1")
            .withResume(null).withStatus(6)
            .withProfileImage(null).withComment(null).build();
    public static final Person WAN = new PersonBuilder().withName("Wan Win Won")
            .withAddress("888, Rich Lane, #09-111").withEmail("www@example.com")
            .withPhone("86520555").withUniversity("NUS").withExpectedGraduationYear("2020")
            .withMajor("Computer Science")
            .withGradePointAverage("4.00")
            .withJobApplied("Software Tester")
            .withRating("2.79", "4.99",
                    "2.59", "2.59")
            .withResume(null)
            .withProfileImage(null)
            .withComment("WWW!")
            .withInterviewDate(LocalDateTime.of(2018, Month.APRIL, 14, 10, 0, 0))
            .withTags("friends").build();
    public static final Person XENON = new PersonBuilder().withName("Xenon Low")
            .withAddress("111, Company Drive 2, #02-25").withResume(null)
            .withEmail("xenon@example.com").withPhone("98425431").withUniversity("NTU")
            .withExpectedGraduationYear("2020")
            .withMajor("Computer Engineering")
            .withGradePointAverage("4.30")
            .withJobApplied("Software Engineer")
            .withRating("4", "4.5",
                    "3", "3.5")
            .withProfileImage(null)
            .withComment("X!")
            .withInterviewDate(LocalDateTime.of(2018, Month.APRIL, 7, 11, 0, 0))
            .withStatus(2).withTags("owesMoney", "friends").build();
    public static final Person YAP = new PersonBuilder().withName("Eugene Yap").withPhone("9535000")
            .withEmail("eug@ene.com").withAddress("Eugenia Farm").withUniversity("SMU")
            .withExpectedGraduationYear("2019")
            .withMajor("Computer Science")
            .withGradePointAverage("4.29")
            .withJobApplied("Front-end Developer")
            .withRating("-1", "-1",
                    "-1", "-1")
            .withProfileImage(null).withComment(null).build();
    public static final Person ZEAL = new PersonBuilder().withName("Zillion Bill").withPhone("87232534")
            .withEmail("zeal@real.com").withAddress("Zeal street").withUniversity("SUTD")
            .withExpectedGraduationYear("2020")
            .withMajor("Information Security")
            .withGradePointAverage("4.99")
            .withJobApplied("Web Security Researcher")
            .withRating("-1", "-1",
                    "-1", "-1")
            .withProfileImage(null)
            .withComment("Zeal is Real!")
            .withResume(null).build();
    private static final String RESUME_PATH = "src/test/resources/resume/";
    private static final String IMAGE_PATH = "src/test/resources/photos/";

    private MoreTypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersons()) {
            try {
                ab.addPerson(person);
            } catch (DuplicatePersonException e) {
                throw new AssertionError("not possible");
            }
        }
        return ab;
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE, HOWARD, IOTA, JASMINE,
                KAHOOT, LOUIS, MILLS, NULL, OLIVER, PERL, ROLLE, SALLY, TALL, ULYSS, VICTOR, WAN, XENON, YAP, ZEAL));
    }

    /**
     * Forms the resume path from the resume file name
     */
    private static String formPathFromFileName(String fileName) {
        if (fileName == null) {
            return null;
        } else {
            return RESUME_PATH + fileName;
        }
    }

    //@@author Ang-YC
    /**
     * Forms the image path from image file name
     */
    private static String formImagePathFromFileName(String fileName) {
        if (isNull(fileName)) {
            return null;
        }
        return IMAGE_PATH + fileName;
    }
    //@@author

}
