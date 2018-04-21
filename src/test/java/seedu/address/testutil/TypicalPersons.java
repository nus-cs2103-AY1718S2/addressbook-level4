package seedu.address.testutil;

import static java.util.Objects.isNull;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMMENT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMMENT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMMUNICATION_SKILLS_SCORE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMMUNICATION_SKILLS_SCORE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPECTED_GRADUATION_YEAR_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPECTED_GRADUATION_YEAR_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPERIENCE_SCORE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPERIENCE_SCORE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GRADE_POINT_AVERAGE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GRADE_POINT_AVERAGE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_JOB_APPLIED_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MAJOR_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MAJOR_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROBLEM_SOLVING_SKILLS_SCORE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROBLEM_SOLVING_SKILLS_SCORE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROFILE_IMAGE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROFILE_IMAGE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RESUME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RESUME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TECHNICAL_SKILLS_SCORE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TECHNICAL_SKILLS_SCORE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_UNIVERSITY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_UNIVERSITY_BOB;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("85355255").withUniversity("NUS").withExpectedGraduationYear("2020")
            .withMajor("Computer Science")
            .withGradePointAverage("4.84")
            .withJobApplied("Software Engineer")
            .withRating("2.7", "3.9",
                    "2.5", "2.5")
            .withResumeLazy(formPathFromFileName("alice.pdf"))
            .withProfileImageLazy(formImagePathFromFileName("elon.jpg"))
            .withComment("Alice!")
            .withInterviewDate(LocalDateTime.of(2018, Month.APRIL, 2, 14, 0, 0))
            .withTags("friends").build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25").withResumeLazy(formPathFromFileName(null))
            .withEmail("johnd@example.com").withPhone("98765432").withUniversity("NTU")
            .withExpectedGraduationYear("2021")
            .withMajor("Computer Engineering")
            .withGradePointAverage("4.73")
            .withJobApplied("Software Engineer")
            .withRating("4", "4.5",
                    "3", "3.5")
            .withProfileImageLazy(formImagePathFromFileName("gates.jpg"))
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
            .withProfileImageLazy(null).withComment(null).build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withAddress("10th street").withUniversity("SUTD")
            .withExpectedGraduationYear("2020")
            .withMajor("Information Security")
            .withGradePointAverage("4.24")
            .withJobApplied("Web Security Researcher")
            .withRating("-1", "-1",
                    "-1", "-1")
            .withProfileImageLazy(formImagePathFromFileName("jobs.jpg"))
            .withComment("Daniel!")
            .withResumeLazy(formPathFromFileName("daniel.pdf")).build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").withAddress("michegan ave").withUniversity("NUS")
            .withExpectedGraduationYear("2018")
            .withMajor("Business Analytics")
            .withGradePointAverage("4.33")
            .withJobApplied("Big Data Analyst")
            .withRating("-1", "-1",
                    "-1", "-1")
            .withProfileImageLazy(formImagePathFromFileName("larry.jpg"))
            .withComment("Elle!").withStatus(7).build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").withAddress("little tokyo").withUniversity("NUS")
            .withExpectedGraduationYear("2019")
            .withMajor("Computer Science")
            .withGradePointAverage("4.75")
            .withJobApplied("Software Engineer")
            .withRating("-1", "-1",
                    "-1", "-1")
            .withProfileImageLazy(formImagePathFromFileName("mark.jpg"))
            .withComment("Fiona!").withStatus(1).build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com").withAddress("4th street").withUniversity("NYU")
            .withExpectedGraduationYear("2022")
            .withMajor("Information Systems")
            .withGradePointAverage("3.88")
            .withJobApplied("Software Engineer")
            .withRating("-1", "-1",
                    "-1", "-1")
            .withResumeLazy(formPathFromFileName("george.pdf")).withStatus(6)
            .withProfileImageLazy(null).withComment(null).build();
    public static final Person ALICE_WITHOUT_TAG = new PersonBuilder(ALICE).withTags().build();
    public static final Person BENSON_WITH_FRIENDS_TAG_REMOVED = new PersonBuilder(BENSON)
            .withTags("owesMoney").build();
    public static final Person CARL_WITHOUT_TAG = new PersonBuilder(CARL).withTags().build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withAddress("little india").withUniversity("NUS")
            .withExpectedGraduationYear("2019")
            .withMajor("Computer Science")
            .withGradePointAverage("3.75")
            .withRating("-1", "-1",
                    "-1", "-1")
            .withResumeLazy(formPathFromFileName("hoon.pdf"))
            .withProfileImageLazy(null).withComment(null).build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withAddress("chicago ave").withUniversity("NUS")
            .withExpectedGraduationYear("2018")
            .withMajor("Computer Science")
            .withGradePointAverage("3.11")
            .withRating("-1", "-1",
                    "-1", "-1")
            .withProfileImageLazy(null).withComment(null).build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withUniversity(VALID_UNIVERSITY_AMY)
            .withExpectedGraduationYear(VALID_EXPECTED_GRADUATION_YEAR_AMY)
            .withMajor(VALID_MAJOR_AMY)
            .withGradePointAverage(VALID_GRADE_POINT_AVERAGE_AMY)
            .withRating(VALID_TECHNICAL_SKILLS_SCORE_AMY, VALID_COMMUNICATION_SKILLS_SCORE_AMY,
                    VALID_PROBLEM_SOLVING_SKILLS_SCORE_AMY, VALID_EXPERIENCE_SCORE_AMY)
            .withResumeLazy(VALID_RESUME_AMY)
            .withProfileImageLazy(VALID_PROFILE_IMAGE_AMY).withComment(VALID_COMMENT_AMY)
            .withTags(VALID_TAG_FRIEND).build();

    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withUniversity(VALID_UNIVERSITY_BOB)
            .withResumeLazy(VALID_RESUME_BOB)
            .withExpectedGraduationYear(VALID_EXPECTED_GRADUATION_YEAR_BOB)
            .withMajor(VALID_MAJOR_BOB)
            .withGradePointAverage(VALID_GRADE_POINT_AVERAGE_BOB)
            .withJobApplied(VALID_JOB_APPLIED_BOB)
            .withRating(VALID_TECHNICAL_SKILLS_SCORE_BOB, VALID_COMMUNICATION_SKILLS_SCORE_BOB,
                    VALID_PROBLEM_SOLVING_SKILLS_SCORE_BOB, VALID_EXPERIENCE_SCORE_BOB)
            .withProfileImageLazy(VALID_PROFILE_IMAGE_BOB).withComment(VALID_COMMENT_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER
    public static final String KEYWORD_MATCHING_2019 = "2019"; //A keyword that matches 2020

    private static final String RESUME_PATH = "src/test/resources/resume/";
    private static final String IMAGE_PATH = "src/test/resources/photos/";

    private TypicalPersons() {} // prevents instantiation

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
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
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

    public static void main(String[] args) {
        System.out.println(ALICE);
    }
}
