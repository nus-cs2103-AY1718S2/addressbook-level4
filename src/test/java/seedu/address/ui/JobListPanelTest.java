// @@author kush1509
package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalJobs.getTypicalJobs;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysJob;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardEquals;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.JobCardHandle;
import guitests.guihandles.JobListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.events.ui.JumpToJobListRequestEvent;
import seedu.address.model.job.Job;

public class JobListPanelTest extends GuiUnitTest {
    private static final ObservableList<Job> TYPICAL_JOBS =
            FXCollections.observableList(getTypicalJobs());

    private static final JumpToJobListRequestEvent JUMP_TO_SECOND_EVENT = new JumpToJobListRequestEvent(INDEX_SECOND);

    private JobListPanelHandle jobListPanelHandle;

    @Before
    public void setUp() {
        JobListPanel jobListPanel = new JobListPanel(TYPICAL_JOBS);
        uiPartRule.setUiPart(jobListPanel);

        jobListPanelHandle = new JobListPanelHandle(getChildNode(jobListPanel.getRoot(),
                JobListPanelHandle.JOB_LIST_VIEW_ID));
    }

    @Test
    public void display() {
        for (int i = 0; i < TYPICAL_JOBS.size(); i++) {
            jobListPanelHandle.navigateToCard(TYPICAL_JOBS.get(i));
            Job expectedJob = TYPICAL_JOBS.get(i);
            JobCardHandle actualCard = jobListPanelHandle.getJobCardHandle(i);

            assertCardDisplaysJob(expectedJob, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void handleJumpToListRequestEvent() {
        postNow(JUMP_TO_SECOND_EVENT);
        guiRobot.pauseForHuman();

        JobCardHandle expectedCard = jobListPanelHandle.getJobCardHandle(INDEX_SECOND.getZeroBased());
        JobCardHandle selectedCard = jobListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedCard, selectedCard);
    }

}
