package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;

import seedu.address.model.AddressBook;
import seedu.address.model.calendar.AppointmentEntry;
import seedu.address.model.calendar.exceptions.DuplicateAppointmentException;
//@@author yuxiangSg
/**
 * A utility class containing a list of {@code AppointmentEntry} objects to be used in tests.
 */
public class TypicalAppointmentEntires {

    public static final AppointmentEntry MEET_JOHN = new AppointmentBuilder().withTitle("meet john").build();

    public static final AppointmentEntry MEET_JAMES = new AppointmentBuilder().withTitle("meet james").build();

    public static final AppointmentEntry MEET_JOSH = new AppointmentBuilder().withTitle("meet josh").build();

    public static final String KEYWORD_MATCHING_YX = "yx"; // A keyword that matches YX

    private TypicalAppointmentEntires(){

    } // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAppointmentAddressBook() {
        AddressBook ab = new AddressBook();
        for (AppointmentEntry entry : getTypicalAppointmentEntries()) {
            try {
                ab.addAppointment(entry);
            } catch (DuplicateAppointmentException e) {
                throw new AssertionError("not possible");
            }
        }
        return ab;
    }

    public static ArrayList<AppointmentEntry> getTypicalAppointmentEntries() {
        return new ArrayList<>(Arrays.asList(MEET_JAMES, MEET_JOHN, MEET_JOSH));
    }




}
