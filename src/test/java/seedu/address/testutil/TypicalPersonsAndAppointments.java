package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.exceptions.DuplicateAppointmentException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersonsAndAppointments {

    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com").withPhone("85355255")
            .withDateAdded("01/01/2018").withTags("friends").build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25").withDateAdded("02/02/2018").withEmail("johnd@example.com")
            .withPhone("98765432").withTags("owesMoney", "friends").build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withAddress("wall street").withDateAdded("03/03/2018").build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withAddress("10th street").withDateAdded("04/04/2018").build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").withAddress("michegan ave").withDateAdded("05/05/2018").build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").withAddress("little tokyo").withDateAdded("06/06/2018").build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com").withAddress("4th street").withDateAdded("07/07/2018").build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withAddress("little india").build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withAddress("chicago ave").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    //@@author jlks96
    public static final Appointment ALICE_APPT = new AppointmentBuilder().withPersonName("Alice Pauline")
            .withDate("01/01/2018").withStartTime("10:30").withEndTime("11:30")
            .withLocation("123, Jurong West Ave 6, #08-111").build();
    public static final Appointment BENSON_APPT = new AppointmentBuilder().withPersonName("Benson Meier")
            .withDate("02/02/2018").withStartTime("10:30").withEndTime("11:30")
            .withLocation("311, Clementi Ave 2, #02-25").build();
    public static final Appointment CARL_APPT = new AppointmentBuilder().withPersonName("Carl Kurz")
            .withDate("03/03/2018").withStartTime("10:30").withEndTime("11:30").withLocation("wall street").build();
    public static final Appointment DANIEL_APPT = new AppointmentBuilder().withPersonName("Daniel Meier")
            .withDate("04/04/2018").withStartTime("10:30").withEndTime("11:30").withLocation("10th street").build();
    public static final Appointment ELLE_APPT = new AppointmentBuilder().withPersonName("Elle Meyer")
            .withDate("05/05/2018").withStartTime("10:30").withEndTime("11:30").withLocation("michegan ave").build();
    public static final Appointment FIONA_APPT = new AppointmentBuilder().withPersonName("Fiona Kunz")
            .withDate("06/06/2018").withStartTime("10:30").withEndTime("11:30").withLocation("little tokyo").build();
    public static final Appointment GEORGE_APPT = new AppointmentBuilder().withPersonName("George Best")
            .withDate("07/07/2018").withStartTime("10:30").withEndTime("11:30").withLocation("4th street").build();
    public static final Appointment HALEM_APPT = new AppointmentBuilder().withPersonName("Halem Brooke")
            .withDate("01/01/2018").withStartTime("10:30").withEndTime("11:30")
            .withLocation("Diamond Park").build();
    //@@author

    private TypicalPersonsAndAppointments() {} // prevents instantiation

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
        for (Appointment appointment : getTypicalAppointments()) {
            try {
                ab.addAppointment(appointment);
            } catch (DuplicateAppointmentException e) {
                throw new AssertionError("not possible");
            }
        }
        return ab;
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }

    //@@author jlks96
    public static List<Appointment> getTypicalAppointments() {
        return new ArrayList<>(
                Arrays.asList(ALICE_APPT, BENSON_APPT, CARL_APPT, DANIEL_APPT, ELLE_APPT, FIONA_APPT, GEORGE_APPT));
    }
    //@@author
}
