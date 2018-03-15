package seedu.address.testutil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.InterviewDate;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;

import static seedu.address.logic.commands.CommandTestUtil.*;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    private static final LocalDateTime sampleInterviewDate =
            LocalDateTime.ofEpochSecond(1540814400, 0, InterviewDate.LOCAL_ZONE_OFFSET);

    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("85355255").withExpectedGraduationYear("2020").withResume(formPathFromFileName("alice.pdf"))
            .withTags("friends").build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25").withResume(formPathFromFileName(null))
            .withEmail("johnd@example.com").withPhone("98765432").withExpectedGraduationYear("2021")
            .withTags("owesMoney", "friends").build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withAddress("wall street")
            .withExpectedGraduationYear("2019").build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withAddress("10th street")
            .withExpectedGraduationYear("2020").withResume(formPathFromFileName("daniel.pdf"))
            .build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").withAddress("michegan ave")
            .withExpectedGraduationYear("2018").build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").withAddress("little tokyo")
            .withExpectedGraduationYear("2019").build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com").withAddress("4th street").withResume(formPathFromFileName("george.pdf"))
            .withExpectedGraduationYear("2022").build();
    public static final Person ALICE_WITHOUT_TAG = new PersonBuilder(ALICE).withTags().build();
    public static final Person BENSON_WITH_FRIENDS_TAG_REMOVED = new PersonBuilder(BENSON)
            .withTags("owesMoney").build();
    public static final Person CARL_WITHOUT_TAG = new PersonBuilder(CARL).withTags().build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withAddress("little india").withResume(formPathFromFileName("hoon.pdf"))
            .withExpectedGraduationYear("2019").build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withAddress("chicago ave")
            .withExpectedGraduationYear("2018").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withResume(VALID_RESUME_AMY)
            .withExpectedGraduationYear(VALID_EXPECTED_GRADUATION_YEAR_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withResume(VALID_RESUME_BOB)
            .withExpectedGraduationYear(VALID_EXPECTED_GRADUATION_YEAR_BOB)
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
