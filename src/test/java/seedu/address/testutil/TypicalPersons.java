package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_JERSEY_NUMBER_17;
import static seedu.address.logic.commands.CommandTestUtil.VALID_JERSEY_NUMBER_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POSITION_MIDFIELD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POSITION_STRIKER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RATING_0;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RATING_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
            .withPhone("85355255").withTags("friends").withRating("0")
            .withPosition("1").withJerseyNumber("1").build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withTags("owesMoney", "friends").withRating("1")
            .withPosition("2").withJerseyNumber("2").build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withAddress("wall street").withRating("2")
            .withPosition("3").withJerseyNumber("3").build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withAddress("10th street").withRating("3")
            .withPosition("4").withJerseyNumber("4").build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").withAddress("michegan ave").withRating("4")
            .withPosition("1").withJerseyNumber("5").build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").withAddress("little tokyo").withRating("5")
            .withPosition("2").withJerseyNumber("6").build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com").withAddress("4th street").withRemark("Could be famous").withRating("0")
            .withPosition("3").withJerseyNumber("7").build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withAddress("little india").build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withAddress("chicago ave").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withRating(VALID_RATING_0)
            .withPosition(VALID_POSITION_STRIKER).withJerseyNumber(VALID_JERSEY_NUMBER_2)
            .withTags(VALID_TAG_FRIEND).build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withRating(VALID_RATING_1)
            .withPosition(VALID_POSITION_MIDFIELD).withJerseyNumber(VALID_JERSEY_NUMBER_17)
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
    //@@author lohtianwei
    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }

    public static List<Person> getTypicalPersonsSortedByAddress() {
        return new ArrayList<>(Arrays.asList(DANIEL, ALICE, BENSON, GEORGE, FIONA, ELLE, CARL));
    }

    public static List<Person> getTypicalPersonsSortedByEmail() {
        return new ArrayList<>(Arrays.asList(ALICE, GEORGE, DANIEL, CARL, BENSON, FIONA, ELLE));
    }

    public static List<Person> getTypicalPersonsSortedByPhone() {
        return new ArrayList<>(Arrays.asList(ALICE, DANIEL, ELLE, FIONA, GEORGE, CARL, BENSON));
    }

    public static AddressBook getSortedAddressBook(String field, String order) {
        AddressBook ab = new AddressBook();
        List<Person> personList;

        switch(field) {
        case "name":
            personList = getTypicalPersons();
            break;
        case "phone":
            personList = getTypicalPersonsSortedByPhone();
            break;
        case "email":
            personList = getTypicalPersonsSortedByEmail();
            break;
        case "address":
            personList = getTypicalPersonsSortedByAddress();
            break;
        default:
            personList = getTypicalPersons();
        }

        if (order.equals("desc")) {
            Collections.reverse(personList);
        }

        for (Person person : personList) {
            try {
                ab.addPerson(person);
            } catch (DuplicatePersonException e) {
                assert false : "not possible";
            }
        }

        return ab;
    }

    public static AddressBook getEmptyAddressBook() {
        AddressBook ab = new AddressBook();
        return ab;
    }
}
