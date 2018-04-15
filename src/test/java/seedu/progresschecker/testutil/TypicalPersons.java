package seedu.progresschecker.testutil;

import static seedu.progresschecker.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.progresschecker.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.progresschecker.logic.commands.CommandTestUtil.VALID_MAJOR_AMY;
import static seedu.progresschecker.logic.commands.CommandTestUtil.VALID_MAJOR_BOB;
import static seedu.progresschecker.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.progresschecker.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.progresschecker.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.progresschecker.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.progresschecker.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.progresschecker.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.progresschecker.logic.commands.CommandTestUtil.VALID_USERNAME_AMY;
import static seedu.progresschecker.logic.commands.CommandTestUtil.VALID_USERNAME_BOB;
import static seedu.progresschecker.logic.commands.CommandTestUtil.VALID_YEAR_AMY;
import static seedu.progresschecker.logic.commands.CommandTestUtil.VALID_YEAR_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.progresschecker.model.ProgressChecker;
import seedu.progresschecker.model.person.Person;
import seedu.progresschecker.model.person.exceptions.DuplicatePersonException;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withMajor("Computer Science").withYear("2").withEmail("alice@example.com")
            .withPhone("85355255").withYear("2").withUsername("AliceGithub")
            .withTags("friends").build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withMajor("Computer Science").withYear("2").withUsername("BensonGithub")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withTags("owesMoney", "friends").build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz")
            .withPhone("95352563").withUsername("CarlGithub")
            .withEmail("heinz@example.com").withMajor("Information Security").withYear("2").build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier")
            .withPhone("87652533").withUsername("DanielGithub")
            .withEmail("cornelia@example.com").withMajor("Computer Engineering").withYear("2").build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer")
            .withPhone("9482224").withUsername("ElleGithub")
            .withEmail("werner@example.com").withMajor("Computer Science").withYear("2").build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz")
            .withPhone("9482427").withUsername("FionaGithub")
            .withEmail("lydia@example.com").withMajor("Computer Engineering").withYear("2").build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best")
            .withPhone("9482442").withUsername("GeorgeGithub")
            .withEmail("anna@example.com").withMajor("Computer Science").withYear("2").build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier")
            .withPhone("8482424").withUsername("HoonGithub")
            .withEmail("stefan@example.com").withMajor("Computer Science").withYear("2").build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller")
            .withPhone("8482131").withUsername("IdaGithub")
            .withEmail("hans@example.com").withMajor("Computer Engineering").withYear("2").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY)
            .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withUsername(VALID_USERNAME_AMY)
            .withMajor(VALID_MAJOR_AMY).withYear(VALID_YEAR_AMY)
            .withTags(VALID_TAG_FRIEND).build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB)
            .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withUsername(VALID_USERNAME_BOB)
            .withMajor(VALID_MAJOR_BOB).withYear(VALID_YEAR_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code ProgressChecker} with all the typical persons.
     */
    public static ProgressChecker getTypicalProgressChecker() {
        ProgressChecker ab = new ProgressChecker();
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
}
