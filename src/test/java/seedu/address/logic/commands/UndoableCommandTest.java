package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static seedu.address.logic.commands.CommandTestUtil.deleteFirstStudent;
import static seedu.address.logic.commands.CommandTestUtil.showStudentAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;
import static seedu.address.testutil.TypicalStudents.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.student.Student;
import seedu.address.model.student.exceptions.StudentNotFoundException;

public class UndoableCommandTest {
    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final DummyCommand dummyCommand = new DummyCommand(model);

    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void executeUndo() throws Exception {
        dummyCommand.execute();
        deleteFirstStudent(expectedModel);
        assertEquals(expectedModel, model);

        showStudentAtIndex(model, INDEX_FIRST_STUDENT);

        // undo() should cause the model's filtered list to show all students
        dummyCommand.undo();
        expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        assertEquals(expectedModel, model);
    }

    @Test
    public void redo() {
        showStudentAtIndex(model, INDEX_FIRST_STUDENT);

        // redo() should cause the model's filtered list to show all students
        dummyCommand.redo();
        deleteFirstStudent(expectedModel);
        assertEquals(expectedModel, model);
    }

    /**
     * Deletes the first student in the model's filtered list.
     */
    class DummyCommand extends UndoableCommand {
        DummyCommand(Model model) {
            this.model = model;
        }

        @Override
        public CommandResult executeUndoableCommand() throws CommandException {
            Student studentToDelete = model.getFilteredStudentList().get(0);
            try {
                model.deleteStudent(studentToDelete);
            } catch (StudentNotFoundException pnfe) {
                fail("Impossible: studentToDelete was retrieved from model.");
            }
            return new CommandResult("");
        }
    }
}
