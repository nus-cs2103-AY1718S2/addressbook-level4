package seedu.progresschecker.logic.commands;

import static seedu.progresschecker.logic.commands.CommandTestUtil.VALID_GITHUB_USERNAME_ONE;
import static seedu.progresschecker.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.progresschecker.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.progresschecker.testutil.TypicalPersons.getTypicalProgressChecker;

import org.junit.Before;
import org.junit.Test;

import seedu.progresschecker.logic.CommandHistory;
import seedu.progresschecker.logic.UndoRedoStack;
import seedu.progresschecker.model.Model;
import seedu.progresschecker.model.ModelManager;
import seedu.progresschecker.model.UserPrefs;
import seedu.progresschecker.model.credentials.GitDetails;
import seedu.progresschecker.testutil.GitDetailsBuilder;

public class GitloginCommandTest {
    private Model model;

    @Before
    public void setUp() throws Exception {
        model = new ModelManager(getTypicalProgressChecker(), new UserPrefs());
    }

    @Test
    public void execute_gitLogin_success() throws Exception {
        GitDetails validGitDetails = new GitDetailsBuilder().build();

        Model expectedModel = new ModelManager(model.getProgressChecker(), new UserPrefs());
        expectedModel.loginGithub(validGitDetails);

        assertCommandSuccess(prepareCommand(validGitDetails, model), model,
                GitLoginCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() throws Exception {
        GitDetails validGitDetails = new GitDetailsBuilder().build();
        model.loginGithub(validGitDetails);

        assertCommandFailure(prepareCommand(validGitDetails, model), model, "You have already logged in as "
                + VALID_GITHUB_USERNAME_ONE + ". Please logout first.");
    }

    /**
     * Generates a new {@code GitLoginCommand} which upon execution, adds {@code gitDetails} into the {@code model}.
     */
    private GitLoginCommand prepareCommand(GitDetails gitDetails, Model model) {
        GitLoginCommand command = new GitLoginCommand(gitDetails);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
