package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;

import static seedu.address.logic.commands.CommandTestUtil.*;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("85355255").withGender("F").withAge("23").withLatitude("1.406916")
            .withLongitude("103.769663").withTags("friends").build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withPhone("98765432").withGender("F").withAge("23").withLatitude("1.406916")
            .withLongitude("103.769663").withTags("friends").build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withAddress("wall street").withGender("M").withAge("23").withLatitude("1.406916")
            .withLongitude("103.769663").build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withAddress("10th street").withGender("M").withAge("23").withLatitude("1.406916")
            .withLongitude("103.769663").build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").withAddress("michegan ave").withGender("M").withAge("23").withLatitude("1.406916")
            .withLongitude("103.769663").build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").withAddress("little tokyo").withGender("F").withAge("23").withLatitude("1.406916")
            .withLongitude("103.769663").build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com").withAddress("4th street").withGender("M").withAge("23").withLatitude("1.406916")
            .withLongitude("103.769663").build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withAddress("little india").withGender("M").withAge("23").withLatitude("1.406916")
            .withLongitude("103.769663").build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withAddress("chicago ave").withGender("F").withAge("23").withLatitude("1.406916")
            .withLongitude("103.769663").build();

    //Persons with same uneditable attributes ie Gender, Age, Latitude and Longitude
    public static final Person JACOB = new PersonBuilder().withName("Jacob Becky").withPhone("9482224")
            .withEmail("werner@example.com").withAddress("michegan ave").withGender("M").withAge("23").withLatitude("1.406916")
            .withLongitude("103.769663").build();
    public static final Person KALE = new PersonBuilder().withName("Kale Cook").withPhone("9482427")
            .withEmail("lydia@example.com").withAddress("little tokyo").withGender("M").withAge("23").withLatitude("1.406916")
            .withLongitude("103.769663").build();
    public static final Person LEE = new PersonBuilder().withName("Lee Sim").withPhone("9482442")
            .withEmail("anna@example.com").withAddress("4th street").withGender("M").withAge("23").withLatitude("1.406916")
            .withLongitude("103.769663").build();


    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

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
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE, JACOB, KALE, LEE));
    }
}
