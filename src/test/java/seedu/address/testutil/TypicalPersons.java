package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

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
            .withNric("S8535525Z")
            .withTags("friends")
            .withSubjects("English A1").build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withNric("S9123456X")
            .withTags("owesMoney", "friends")
            .withSubjects("Physics A2").build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz").withNric("S9535256J").build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier").withNric("S8765253I").build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer").withNric("S9482224A").build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz").withNric("S9482427J").build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best").withNric("S9482442U").build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier").withNric("S8482424Z").build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller").withNric("S8482131K").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY).withNric(VALID_NRIC_AMY)
            .withTags(VALID_TAG_FRIEND).build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB).withNric(VALID_NRIC_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

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
}
