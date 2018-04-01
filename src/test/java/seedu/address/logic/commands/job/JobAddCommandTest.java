// @@author kush1509
package seedu.address.logic.commands.job;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalJobs.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.job.Job;
import seedu.address.testutil.JobBuilder;

public class JobAddCommandTest {

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newJob_success() throws Exception {
        
        Job validJob = new JobBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addJob(validJob);

        assertCommandSuccess(prepareCommand(validJob, model), model,
                String.format(JobAddCommand.MESSAGE_SUCCESS, validJob), expectedModel);
    }

    @Test
    public void execute_duplicateJob_throwsCommandException() {
        Job jobInList = model.getAddressBook().getJobList().get(0);
        assertCommandFailure(prepareCommand(jobInList, model), model, JobAddCommand.MESSAGE_DUPLICATE_JOB);
    }

    /**
     * Generates a new {@code JobAddCommand} which upon execution, adds {@code job} into the {@code model}.
     */
    private JobAddCommand prepareCommand(Job job, Model model) {
        JobAddCommand command = new JobAddCommand(job);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }


}