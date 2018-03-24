package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMMUNICATION_SKILLS_SCORE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMMUNICATION_SKILLS_SCORE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPECTED_GRADUATION_YEAR_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPECTED_GRADUATION_YEAR_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPERIENCE_SCORE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPERIENCE_SCORE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MAJOR_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MAJOR_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROBLEM_SOLVING_SKILLS_SCORE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROBLEM_SOLVING_SKILLS_SCORE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RESUME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RESUME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TECHNICAL_SKILLS_SCORE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TECHNICAL_SKILLS_SCORE_BOB;

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
            .withPhone("85355255").withExpectedGraduationYear("2020")
            .withMajor("Computer Science")
            .withRating("2.7", "3.9",
                    "2.5", "2.5")
            .withResume(formPathFromFileName("alice.pdf"))
            .withTags("friends").build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25").withResume(formPathFromFileName(null))
            .withEmail("johnd@example.com").withPhone("98765432").withExpectedGraduationYear("2021")
            .withMajor("Computer Engineering")
            .withRating("4", "4.5",
                    "3", "3.5")
            .withStatus(2).withTags("owesMoney", "friends").build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withAddress("wall street")
            .withExpectedGraduationYear("2019")
            .withMajor("Computer Science")
            .withRating("-1", "-1",
                    "-1", "-1").build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withAddress("10th street")
            .withExpectedGraduationYear("2020")
            .withMajor("Information Security")
            .withRating("-1", "-1",
                    "-1", "-1")
            .withResume(formPathFromFileName("daniel.pdf")).build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").withAddress("michegan ave")
            .withExpectedGraduationYear("2018")
            .withMajor("Business Analytics")
            .withRating("-1", "-1",
                    "-1", "-1").withStatus(7).build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").withAddress("little tokyo")
            .withExpectedGraduationYear("2019")
            .withMajor("Computer Science")
            .withRating("-1", "-1",
                    "-1", "-1").withStatus(1).build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com").withAddress("4th street")
            .withExpectedGraduationYear("2022")
            .withMajor("Information Systems")
            .withRating("-1", "-1",
                    "-1", "-1")
            .withResume(formPathFromFileName("george.pdf")).withStatus(6).build();
    public static final Person ALICE_WITHOUT_TAG = new PersonBuilder(ALICE).withTags().build();
    public static final Person BENSON_WITH_FRIENDS_TAG_REMOVED = new PersonBuilder(BENSON)
            .withTags("owesMoney").build();
    public static final Person CARL_WITHOUT_TAG = new PersonBuilder(CARL).withTags().build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withAddress("little india")
            .withExpectedGraduationYear("2019")
            .withMajor("Computer Science")
            .withRating("-1", "-1",
                    "-1", "-1")
            .withResume(formPathFromFileName("hoon.pdf")).build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withAddress("chicago ave")
            .withExpectedGraduationYear("2018")
            .withMajor("Computer Science")
            .withRating("-1", "-1",
                    "-1", "-1").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
            .withExpectedGraduationYear(VALID_EXPECTED_GRADUATION_YEAR_AMY)
            .withMajor(VALID_MAJOR_AMY)
            .withRating(VALID_TECHNICAL_SKILLS_SCORE_AMY, VALID_COMMUNICATION_SKILLS_SCORE_AMY,
                    VALID_PROBLEM_SOLVING_SKILLS_SCORE_AMY, VALID_EXPERIENCE_SCORE_AMY)
            .withResume(VALID_RESUME_AMY)
            .withTags(VALID_TAG_FRIEND).build();

    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withResume(VALID_RESUME_BOB)
            .withExpectedGraduationYear(VALID_EXPECTED_GRADUATION_YEAR_BOB)
            .withMajor(VALID_MAJOR_BOB)
            .withRating(VALID_TECHNICAL_SKILLS_SCORE_BOB, VALID_COMMUNICATION_SKILLS_SCORE_BOB,
                    VALID_PROBLEM_SOLVING_SKILLS_SCORE_BOB, VALID_EXPERIENCE_SCORE_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER
    public static final String KEYWORD_MATCHING_2019 = "2019"; //A keyword that matches 2020

    private static final String RESUME_PATH = "src/test/resources/resume/";

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
    public static void main(String[] args) {
        System.out.println(ALICE);
    }
}
