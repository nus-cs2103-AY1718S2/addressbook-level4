package seedu.address.logic.commands;

import static seedu.address.testutil.TypicalPatients.getTypicalAddressBook;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.patient.Remark;

public class RemarkCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddCommand(null);
    }

    @Test
    public void execute_displaysIndexAndRemark() throws Exception {
        thrown.expect(CommandException.class);
        thrown.expectMessage("Index: 1 Remark: Test");

        RemarkCommand command = new RemarkCommand(Index.fromOneBased(1), new Remark("Test"));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        command.execute();
    }

}
