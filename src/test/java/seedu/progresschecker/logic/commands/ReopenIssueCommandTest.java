package seedu.progresschecker.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.progresschecker.testutil.TypicalIndexes.INDEX_ISSUE;
import static seedu.progresschecker.testutil.TypicalPersons.getTypicalProgressChecker;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.progresschecker.commons.core.index.Index;
import seedu.progresschecker.logic.CommandHistory;
import seedu.progresschecker.logic.UndoRedoStack;
import seedu.progresschecker.logic.commands.exceptions.CommandException;
import seedu.progresschecker.model.Model;
import seedu.progresschecker.model.ModelManager;
import seedu.progresschecker.model.UserPrefs;
import seedu.progresschecker.model.credentials.GitDetails;
import seedu.progresschecker.testutil.GitDetailsBuilder;

public class ReopenIssueCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model;

    @Before
    public void setUp() throws Exception {
        model = new ModelManager(getTypicalProgressChecker(), new UserPrefs());
        GitDetails validDetails = new GitDetailsBuilder().build();
        model.loginGithub(validDetails);
    }

    @Test
    public void execute_closeIssue_success() throws Exception {

        CommandResult commandResult = prepareCommand(INDEX_ISSUE, model).execute();

        /**
         * The model cannot be tested because if the model is tested,
         * There is just one model instead of two : an expected model and a model
         * The reason for the same is because if createIssue command is executed twice, there will be 2 issues online
         * Thus, the success message is comapred with the feedback to the user
         * success message is only posted after an issue is created on git
         */
        assertEquals (String.format(ReopenIssueCommand.MESSAGE_SUCCESS,
                INDEX_ISSUE.getOneBased()), commandResult.feedbackToUser);
    }

    @Test
    public void execute_authenticationError_throwsCommandException() throws Exception {
        model.logoutGithub();

        thrown.expect(CommandException.class);
        thrown.expectMessage(ReopenIssueCommand.MESSAGE_AUTHENTICATION_FAILURE);

        prepareCommand(INDEX_ISSUE, model).execute();

    }

    /**
     * Generates a new {@code ReopenIssueCommand} which upon execution, adds {@code issue} into the {@code model}.
     */
    private ReopenIssueCommand prepareCommand(Index index, Model model) {
        ReopenIssueCommand command = new ReopenIssueCommand(index);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}

