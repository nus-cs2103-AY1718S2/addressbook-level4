package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GRADE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GRADE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCATION_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCATION_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Client;
import seedu.address.model.person.exceptions.DuplicatePersonException;

//@@author olimhc
/**
 * A utility class containing a list of {@code Clients} objects to be used in tests.
 */
public class SortedClients {
    //Students
    public static final Client ALICE = new ClientBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("85355255")
            .withTags("friends").withLocation("north").withGrade("k1").withSubject("math").withCategory("s").build();
    public static final Client BENSON = new ClientBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withTags("owesMoney", "friends").withLocation("north").withGrade("s2").withSubject("physics")
            .withCategory("s").build();
    public static final Client CARL = new ClientBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withAddress("wall street").withLocation("south").withGrade("j1")
            .withSubject("physics").withCategory("s").build();
    public static final Client DANIEL = new ClientBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withAddress("10th street").withLocation("east").withGrade("primary6")
            .withSubject("english").withCategory("s").build();
    public static final Client ELLE = new ClientBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").withAddress("michegan ave").withLocation("west").withGrade("p3")
            .withSubject("math").withCategory("s").build();
    public static final Client FIONA = new ClientBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").withAddress("little tokyo").withLocation("north").withGrade("secondary1")
            .withSubject("physics").withCategory("s").build();
    public static final Client GEORGE = new ClientBuilder().withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com").withAddress("4th street").withLocation("west").withGrade("j2")
            .withSubject("chemistry").withCategory("s").build();

    //Tutors
    public static final Client ANDREW = new ClientBuilder().withName("ANDREW LIM").withPhone("5212533")
            .withEmail("andrew@example.com").withAddress("Andrew street").withLocation("east").withGrade("primary2")
            .withSubject("english").withCategory("t").build();
    public static final Client EDISON = new ClientBuilder().withName("EDISON").withPhone("2313224")
            .withEmail("EDISON@example.com").withAddress("EDISON ave").withLocation("west").withGrade("j2")
            .withSubject("math").withCategory("t").build();
    public static final Client FLOWER = new ClientBuilder().withName("Flower").withPhone("2182427")
            .withEmail("flowerislife@example.com").withAddress("little flower").withLocation("central").withGrade("k1")
            .withSubject("physics").withCategory("t").build();
    public static final Client GERRARD = new ClientBuilder().withName("GERRARD").withPhone("8321242")
            .withEmail("liverpool@example.com").withAddress("Anfield").withLocation("west").withGrade("u4")
            .withSubject("chemistry").withCategory("t").build();

    // Manually added
    public static final Client HOON = new ClientBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withAddress("little india").withLocation("north").withGrade("s1")
            .withSubject("chemistry").withCategory("s").build();
    public static final Client IDA = new ClientBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withAddress("chicago ave").withLocation("north").withGrade("k2")
            .withSubject("math").withCategory("s").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Client AMY = new ClientBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND)
            .withLocation(VALID_LOCATION_AMY).withGrade(VALID_GRADE_AMY).withSubject(VALID_SUBJECT_AMY)
            .withCategory(VALID_CATEGORY_AMY).build();
    public static final Client BOB = new ClientBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .withLocation(VALID_LOCATION_BOB).withGrade(VALID_GRADE_BOB).withSubject(VALID_SUBJECT_BOB)
            .withCategory(VALID_CATEGORY_BOB).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private SortedClients() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical clients sorted by name.
     */
    public static AddressBook getSortedByNameAddressBook() {
        AddressBook ab = new AddressBook();
        for (Client client : getSortedByNameClients()) {
            try {
                ab.addClient(client);
            } catch (DuplicatePersonException e) {
                throw new AssertionError("not possible");
            }
        }
        return ab;
    }

    public static List<Client> getSortedByNameClients() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE,
                ANDREW, EDISON, FLOWER, GERRARD));
    }

    /**
     * Returns an {@code AddressBook} with all the typical clients sorted by grade.
     */
    public static AddressBook getSortedByGradeAddressBook() {
        AddressBook ab = new AddressBook();
        for (Client client : getSortedByGradeClients()) {
            try {
                ab.addClient(client);
            } catch (DuplicatePersonException e) {
                throw new AssertionError("not possible");
            }
        }
        return ab;
    }

    public static List<Client> getSortedByGradeClients() {
        return new ArrayList<>(Arrays.asList(ALICE, ELLE, DANIEL, FIONA, BENSON, CARL, GEORGE,
                FLOWER, ANDREW, EDISON, GERRARD));
    }

    /**
     * Returns an {@code AddressBook} with all the typical clients sorted by subject
     */
    public static AddressBook getSortedBySubjectAddressBook() {
        AddressBook ab = new AddressBook();
        for (Client client : getSortedBySubjectClients()) {
            try {
                ab.addClient(client);
            } catch (DuplicatePersonException e) {
                throw new AssertionError("not possible");
            }
        }
        return ab;
    }

    public static List<Client> getSortedBySubjectClients() {
        return new ArrayList<>(Arrays.asList(GEORGE, DANIEL, ALICE, ELLE, BENSON, CARL, FIONA,
                GERRARD, ANDREW, EDISON, FLOWER));
    }

    /**
     * Returns an {@code AddressBook} with all the typical clients sorted by location
     */
    public static AddressBook getSortedByLocationAddressBook() {
        AddressBook ab = new AddressBook();
        for (Client client : getSortedByLocationClients()) {
            try {
                ab.addClient(client);
            } catch (DuplicatePersonException e) {
                throw new AssertionError("not possible");
            }
        }
        return ab;
    }

    public static List<Client> getSortedByLocationClients() {
        return new ArrayList<>(Arrays.asList(DANIEL, BENSON, ALICE, FIONA, CARL, ELLE, GEORGE,
                FLOWER, ANDREW, GERRARD, EDISON));
    }
}
