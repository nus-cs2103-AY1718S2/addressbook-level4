//@@author Kyholmes-test
package seedu.address.logic.commands;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalPatients.getTypicalAddressBook;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.appointment.DateTime;

public class AddAppointmentCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_nullObject_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddAppointmentCommand(null, null);
    }

    @Test
    public void equals() throws IllegalValueException {
        Index firstIndex = ParserUtil.parseIndex("1");
        Index secondIndex = ParserUtil.parseIndex("2");

        DateTime firstDateTime = ParserUtil.parseDateTime("1/1/2018 1100");
        DateTime secondDateTime = ParserUtil.parseDateTime("2/1/2018 1100");

        AddAppointmentCommand addAppointmentFirstIndexFirstDateTimeCommand =
                new AddAppointmentCommand(firstIndex, firstDateTime);

        AddAppointmentCommand addAppointmentFirstIndexSecondDateTimeCommand =
                new AddAppointmentCommand(firstIndex, secondDateTime);

        AddAppointmentCommand addAppointmentSecondIndexFirstDateTimeCommand =
                new AddAppointmentCommand(secondIndex, firstDateTime);

        AddAppointmentCommand addAppointmentSecondIndexSecondDateTimeCommand =
                new AddAppointmentCommand(secondIndex, secondDateTime);

        //same object -> return true
        assertTrue(addAppointmentFirstIndexFirstDateTimeCommand
                .equals(addAppointmentFirstIndexFirstDateTimeCommand));

        //same values -> returns true
        AddAppointmentCommand addAppointmentFirstIndexFirstDateTimeCommandCopy =
                new AddAppointmentCommand(firstIndex, firstDateTime);
        assertTrue(addAppointmentFirstIndexFirstDateTimeCommand
                .equals(addAppointmentFirstIndexFirstDateTimeCommandCopy));

        //different types -> returns false
        assertFalse(addAppointmentFirstIndexFirstDateTimeCommand.equals(1));

        //null -> returns false
        assertFalse(addAppointmentFirstIndexFirstDateTimeCommand.equals(null));

        //different pattern -> returns false
        assertFalse(addAppointmentFirstIndexFirstDateTimeCommand
                .equals(addAppointmentFirstIndexSecondDateTimeCommand));
        assertFalse(addAppointmentFirstIndexFirstDateTimeCommand
                .equals(addAppointmentSecondIndexFirstDateTimeCommand));
        assertFalse(addAppointmentFirstIndexFirstDateTimeCommand
                .equals(addAppointmentSecondIndexSecondDateTimeCommand));
    }
}
