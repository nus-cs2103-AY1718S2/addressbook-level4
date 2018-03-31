package seedu.organizer.logic.commands;

import static seedu.organizer.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.organizer.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.organizer.testutil.TypicalTasks.getTypicalOrganizer;

import org.junit.Before;
import org.junit.Test;

import seedu.organizer.logic.CommandHistory;
import seedu.organizer.logic.UndoRedoStack;
import seedu.organizer.model.Model;
import seedu.organizer.model.ModelManager;
import seedu.organizer.model.UserPrefs;
import seedu.organizer.model.user.User;

//@@author dominickenn
/**
 * Contains integration tests (interaction with the Model) for {@code SignUpCommand}.
 */
public class SignUpCommandIntegrationTest {

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalOrganizer(), new UserPrefs());
    }

    @Test
    public void execute_newUser_success() throws Exception {
        User validUser = new User("david", "david123");

        Model expectedModel = new ModelManager(model.getOrganizer(), new UserPrefs());
        expectedModel.addUser(validUser);

        assertCommandSuccess(prepareCommand(validUser, model), model,
                String.format(SignUpCommand.MESSAGE_SUCCESS, validUser), expectedModel);
    }

    @Test
    public void execute_duplicateUser_throwsCommandException() {
        User userInList = model.getOrganizer().getUserList().get(0);
        assertCommandFailure(prepareCommand(userInList, model), model, SignUpCommand.MESSAGE_DUPLICATE_USER);
    }

    /**
     * Generates a new {@code SignUpCommand} which upon execution, adds {@code user} into the {@code model}.
     */
    private SignUpCommand prepareCommand(User user, Model model) {
        SignUpCommand command = new SignUpCommand(user);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}

