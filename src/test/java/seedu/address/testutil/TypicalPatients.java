package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BLOODTYPE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BLOODTYPE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DOB_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DOB_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Imdb;
import seedu.address.model.patient.Patient;
import seedu.address.model.patient.exceptions.DuplicatePatientException;

/**
 * A utility class containing a list of {@code Patient} objects to be used in tests.
 */
public class TypicalPatients {
    public static final Patient ALICE;
    public static final Patient BENSON;
    public static final Patient CARL;
    public static final Patient DANIEL;
    public static final Patient ELLE;
    public static final Patient FIONA;
    public static final Patient GEORGE;

    public static final Patient HOON;
    public static final Patient IDA;

    public static final Patient AMY;
    public static final Patient BOB;

    static {
        Patient temp = null;
        try {
            temp = new PatientBuilder().withName("Alice Pauline")
                    .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
                    .withPhone("85355255")
                    .withTags("friends")
                    .withNric("S1234567A")
                    .withBloodType("A")
                    .withDob("11/11/1991")
                    .build();
        } catch (ParseException pe) {
            try {
                throw pe;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        ALICE = temp;
        temp = null;
        try {
            temp = new PatientBuilder().withName("Benson Meier")
                    .withAddress("311, Clementi Ave 2, #02-25")
                    .withEmail("johnd@example.com").withPhone("98765432")
                    .withTags("owesMoney", "friends")
                    .withNric("S7654321B")
                    .withBloodType("B-")
                    .withDob("10/10/1990").build();
        } catch (ParseException pe) {
            try {
                throw pe;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        BENSON = temp;
        temp = null;
        try {
            temp = new PatientBuilder().withName("Carl Kurz").withPhone("95352563")
                    .withEmail("heinz@example.com").withAddress("wall street")
                    .withNric("S5671234C")
                    .withBloodType("A+")
                    .withDob("09/09/1989").build();
        } catch (ParseException pe) {
            try {
                throw pe;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        CARL = temp;
        temp = null;
        try {
            temp = new PatientBuilder().withName("Daniel Meier").withPhone("87652533")
                    .withEmail("cornelia@example.com").withAddress("10th street")
                    .withNric("S3456712D")
                    .withBloodType("B+")
                    .withDob("08/08/1988").build();
        } catch (ParseException pe) {
            try {
                throw pe;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        DANIEL = temp;
        temp = null;
        try {
            temp = new PatientBuilder().withName("Elle Meyer").withPhone("9482224")
                    .withEmail("werner@example.com").withAddress("michegan ave")
                    .withNric("S5673412E")
                    .withBloodType("O")
                    .withDob("07/07/1987").build();
        } catch (ParseException pe) {
            try {
                throw pe;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        ELLE = temp;
        temp = null;
        try {
            temp = new PatientBuilder().withName("Fiona Kunz").withPhone("9482427")
                    .withEmail("lydia@example.com").withAddress("little tokyo")
                    .withNric("S1234512F")
                    .withBloodType("O+")
                    .withDob("06/06/1986")
                    .withAppointments("28/3/2018 1230")
                    .build();
        } catch (ParseException pe) {
            try {
                throw pe;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        FIONA = temp;
        temp = null;
        try {
            temp = new PatientBuilder().withName("George Best").withPhone("9482442")
                    .withEmail("anna@example.com").withAddress("4th street")
                    .withRemark("Comes for checkup every Tuesday").withNric("S1234567A")
                    .withBloodType("B+")
                    .withDob("11/11/1991")
                    .withAppointments("28/3/2018 1230").build();
        } catch (ParseException pe) {
            try {
                throw pe;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        GEORGE = temp;

        // Manually added
        temp = null;
        try {
            temp = new PatientBuilder().withName("Hoon Meier").withPhone("8482424")
                    .withEmail("stefan@example.com").withAddress("little india").withNric("S1234567A")
                    .withBloodType("B+")
                    .withDob("10/10/1991").build();
        } catch (ParseException pe) {
            try {
                throw pe;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        HOON = temp;
        temp = null;
        try {
            temp = new PatientBuilder().withName("Ida Mueller").withPhone("8482131")
                    .withEmail("hans@example.com").withAddress("chicago ave").withNric("S1234567A")
                    .withBloodType("B+")
                    .withDob("10/10/1991").build();
        } catch (ParseException pe) {
            try {
                throw pe;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        IDA = temp;

        // Manually added - Patient's details found in {@code CommandTestUtil}
        temp = null;
        try {
            temp = new PatientBuilder().withName(VALID_NAME_AMY).withNric(VALID_NRIC_AMY)
                    .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
                    .withAddress(VALID_ADDRESS_AMY).withDob(VALID_DOB_AMY)
                    .withBloodType(VALID_BLOODTYPE_AMY).withTags(VALID_TAG_FRIEND).build();
        } catch (ParseException pe) {
            try {
                throw pe;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        AMY = temp;
        temp = null;
        try {
            temp = new PatientBuilder().withName(VALID_NAME_BOB).withNric(VALID_NRIC_BOB)
                    .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                    .withAddress(VALID_ADDRESS_BOB).withDob(VALID_DOB_BOB)
                    .withBloodType(VALID_BLOODTYPE_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();

        } catch (ParseException pe) {
            try {
                throw pe;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        BOB = temp;
        //should not reach this stage
    }

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPatients() {} // prevents instantiation

    /**
     * Returns an {@code Imdb} with all the typical persons.
     */
    public static Imdb getTypicalAddressBook() {
        Imdb ab = new Imdb();
        for (Patient patient : getTypicalPersons()) {
            try {
                ab.addPerson(patient);
            } catch (DuplicatePatientException e) {
                throw new AssertionError("not possible");
            }
        }
        return ab;
    }

    public static List<Patient> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
