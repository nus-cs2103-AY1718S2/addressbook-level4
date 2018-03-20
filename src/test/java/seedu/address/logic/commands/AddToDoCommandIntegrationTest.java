package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.todo.ToDo;
import seedu.address.testutil.ToDoBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddToDoCommand}.
 */
public class AddToDoCommandIntegrationTest {

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newToDo_success() throws Exception {
        ToDo validToDo = new ToDoBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addToDo(validToDo);

        assertCommandSuccess(prepareCommand(validToDo, model), model,
                String.format(AddToDoCommand.MESSAGE_SUCCESS, validToDo), expectedModel);
    }

    @Test
    public void execute_duplicateToDo_throwsCommandException() {
        ToDo toDoInList = model.getAddressBook().getToDoList().get(0);
        assertCommandFailure(prepareCommand(toDoInList, model), model, AddToDoCommand.MESSAGE_DUPLICATE_TODO);
    }

    /**
     * Generates a new {@code AddToDoCommand} which upon execution, adds {@code todo} into the {@code model}.
     */
    private AddToDoCommand prepareCommand(ToDo todo, Model model) {
        AddToDoCommand command = new AddToDoCommand(todo);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
