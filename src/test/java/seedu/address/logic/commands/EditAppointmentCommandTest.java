package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAppointmentEntires.getTypicalAppointmentAddressBook;

import java.time.LocalDateTime;

import org.junit.Test;

import com.calendarfx.model.Interval;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.calendar.AppointmentEntry;
import seedu.address.testutil.AppointmentBuilder;
//@@author yuxiangSg
public class EditAppointmentCommandTest {
    private Model model = new ModelManager(getTypicalAppointmentAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecified_success() throws Exception {
        String validSearchText = CommandTestUtil.VALID_TITLE_JOHN;
        String newTitle = "meet YX";
        LocalDateTime newStartDateTime = LocalDateTime.of(2018, 04, 04, 13, 00);
        LocalDateTime newEndDateTime = LocalDateTime.of(2018, 04, 04, 14, 00);
        AppointmentEntry updatedEntry = new AppointmentEntry(newTitle, new Interval(newStartDateTime, newEndDateTime));

        EditAppointmentCommand.EditAppointmentDescriptor descriptor =
                new EditAppointmentCommand.EditAppointmentDescriptor();
        descriptor.setGivenTitle(newTitle);
        descriptor.setStartDateTime(newStartDateTime);
        descriptor.setEndDateTime(newEndDateTime);

        EditAppointmentCommand editAppointmentCommand = prepareCommand(validSearchText, descriptor);

        String expectedMessage = String.format(EditAppointmentCommand.MESSAGE_SUCCESS, updatedEntry);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.editAppointment(validSearchText, updatedEntry);

        assertCommandSuccess(editAppointmentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecified_success() throws Exception {
        String validSearchText = CommandTestUtil.VALID_TITLE_JOHN;
        String newTitle = "meet YX";
        AppointmentEntry updatedEntry = new AppointmentBuilder().withTitle(newTitle).build();

        EditAppointmentCommand.EditAppointmentDescriptor descriptor =
                new EditAppointmentCommand.EditAppointmentDescriptor();
        descriptor.setGivenTitle(newTitle);

        EditAppointmentCommand editAppointmentCommand = prepareCommand(validSearchText, descriptor);

        String expectedMessage = String.format(EditAppointmentCommand.MESSAGE_SUCCESS, updatedEntry);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.editAppointment(validSearchText, updatedEntry);

        assertCommandSuccess(editAppointmentCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Returns an {@code EditAppointmentCommand} with parameters {@code SearchText} and {@code descriptor}
     */
    private EditAppointmentCommand prepareCommand(String searchText,
                                                  EditAppointmentCommand.EditAppointmentDescriptor descriptor) {
        EditAppointmentCommand editAppointmentCommand = new EditAppointmentCommand(searchText, descriptor);
        editAppointmentCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return editAppointmentCommand;
    }
}
