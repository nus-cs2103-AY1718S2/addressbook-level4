package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.ROLE_STUDENT;
import static seedu.address.logic.commands.CommandTestUtil.STATUS_UNMATCHED;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LEVEL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LEVEL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.pair.Pair;
import seedu.address.model.pair.exceptions.DuplicatePairException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com").withPhone("85355255")
            .withPrice("50").withSubject("math").withStatus("Not Matched").withLevel("lower Sec")
            .withRole("Tutor").withRemark("Hardworking but slow learner.").withRate("3.0", "1").build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25").withEmail("johnd@example.com").withPhone("98765432")
            .withPrice("50").withSubject("math").withStatus("Matched").withLevel("lower Sec")
            .withRole("Student").withRemark("Not self motivated.").withRate("2.1", "2").build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withAddress("wall street")
            .withPrice("20").withSubject("chemistry").withStatus("Not Matched").withLevel("upper Sec")
            .withRole("Tutor").withRemark("Patient and clear in explanation.").withRate("4.2", "3").build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withAddress("10th street")
            .withPrice("20").withSubject("chemistry").withStatus("Matched").withLevel("upper Sec")
            .withRole("Student").withRemark("Fast learner.").withRate("4.0", "2").build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").withAddress("michegan ave")
            .withPrice("99").withSubject("english").withStatus("Matched").withLevel("upper Sec")
            .withRole("Tutor").withRemark("Inspirational tutor.").withRate("4.5", "4").build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").withAddress("little tokyo")
            .withPrice("99").withSubject("english").withStatus("Matched").withLevel("upper Sec")
            .withRole("Student").withRemark("Impatient and poor in explanation.")
            .withRate("1.1", "1").build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com").withAddress("4th street")
            .withPrice("320").withSubject("chinese").withStatus("not Matched").withLevel("lower Sec")
            .withRole("Tutor").withRemark("Friendly and approachable.").withRate("4.3", "1").build();
    public static final Person HENRY = new PersonBuilder().withName("Henry Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withAddress("little india").withSubject("chinese")
            .withLevel("upper Sec").withPrice("1234").withStatus("not Matched")
            .withRole("Tutor").withRemark("Very passionate tutor.").withRate("4.3", "1").build();
    public static final Person IRENE = new PersonBuilder().withName("Irene Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withAddress("chicago ave").withSubject("english")
            .withLevel("upper Sec").withPrice("4321").withStatus("not Matched")
            .withRole("Student").withRemark("Constantly postponing lessons.").withRate("2.1", "3").build();
    public static final Person JENNY = new PersonBuilder().withName("Jenny Yim").withPhone("8482131")
            .withEmail("hans@example.com").withAddress("chicago ave").withSubject("english")
            .withLevel("lower Sec").withPrice("4321").withStatus("not Matched")
            .withRole("Tutor").withRemark("Constantly postponing lessons.").withRate("2.0", "1").build();
    public static final Person KEITH = new PersonBuilder().withName("Keith Chang").withPhone("8482131")
            .withEmail("hans@example.com").withAddress("chicago ave").withSubject("english")
            .withLevel("lower Sec").withPrice("120").withStatus("not Matched")
            .withRole("Student").withRemark("Constantly postponing lessons.").withRate("2.4", "1").build();
    public static final Person LISA = new PersonBuilder().withName("Lisa Ong").withPhone("8482131")
            .withEmail("hans@example.com").withAddress("chicago ave").withSubject("english")
            .withLevel("lower Sec").withPrice("120").withStatus("not Matched")
            .withRole("Student").withRemark("Hardworking.").withRate("4.3", "1").build();
    public static final Person MARY = new PersonBuilder().withName("Mary Lou").withPhone("8482131")
            .withEmail("hans@example.com").withAddress("chicago ave").withSubject("english")
            .withLevel("lower Sec").withPrice("100").withStatus("not Matched")
            .withRole("Tutor").withRemark("Hardworking.").withRate("4.3", "1").build();
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withAddress("little india").withSubject("chinese")
            .withLevel("upper Sec").withPrice("1234").withStatus("not Matched")
            .withRole("Tutor").withTags("Friend").withRemark("Very passionate tutor.")
            .withRate("4.3", "1").build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withAddress("chicago ave").withSubject("english")
            .withLevel("upper Sec").withPrice("4321").withStatus("not Matched")
            .withRole("Tutor").withTags("Friend").withRemark("Constantly postponing lessons.")
            .withRate("2.0", "2").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND)
            .withPrice(VALID_PRICE_AMY).withSubject(VALID_SUBJECT_AMY).withLevel(VALID_LEVEL_AMY)
            .withStatus(STATUS_UNMATCHED).withRole(ROLE_STUDENT)
            .build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_FRIEND)
            .withPrice(VALID_PRICE_BOB).withLevel(VALID_LEVEL_BOB).withSubject(VALID_SUBJECT_BOB)
            .withStatus(STATUS_UNMATCHED).withRole(VALID_ROLE_BOB)
            .build();

    //Persons with some missing fields
    public static final Person JAMES = new PersonBuilder().withName("James Bond").withPhone("")
            .withEmail("").withAddress("jurong west").withSubject("math")
            .withLevel("upper Sec").withPrice("2234").withStatus("not Matched")
            .withRole("Tutor").withTags("Friend").withRemark("").build();
    public static final Person KEN = new PersonBuilder().withName("Ken Wong").withPhone("12344321")
            .withEmail("ken@example.com").withAddress("").withSubject("")
            .withLevel("upper sec").withPrice("2234").withStatus("not Matched")
            .withRole("Tutor").withTags("Friend").withRemark("").build();
    public static final Person LENNY = new PersonBuilder().withName("Lenny Face").withPhone("43211234")
            .withEmail("lennyfaces@example.com").withAddress("lim chu kang").withSubject("english")
            .withLevel("").withPrice("").withStatus("not Matched")
            .withRole("Tutor").withTags("Friend").withRemark("").build();
    public static final Person MISTER = new PersonBuilder().withName("Mister Rogers").withPhone("44122331")
            .withEmail("mrogers@example.com").withAddress("Mountbatten").withSubject("english")
            .withLevel("lower sec").withPrice("2123").withStatus("")
            .withRole("").withTags("Friend").withRemark("").build();

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
        for (Pair pair : TypicalPairs.getTypicalPairs()) {
            try {
                ab.addPair(pair);
            } catch (DuplicatePairException e) {
                throw new AssertionError("not possible");
            }
        }
        return ab;
    }

    /**
     * Returns an {@code AddressBook} with all the persons with unentered attributes.
     */
    public static AddressBook getMissingAttributesAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getMissingAttributesPersons()) {
            try {
                ab.addPerson(person);
            } catch (DuplicatePersonException e) {
                throw new AssertionError("not possible");
            }
        }
        return ab;
    }

    public static List<Person> getMissingAttributesPersons() {
        return new ArrayList<>(Arrays.asList(JAMES, KEN, LENNY, MISTER));
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE,
                HENRY, IRENE, JENNY, KEITH, LISA, MARY));
    }


}
