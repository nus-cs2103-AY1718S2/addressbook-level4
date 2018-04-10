// @@author kush1509
package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysJob;

import org.junit.Test;

import guitests.guihandles.JobCardHandle;
import seedu.address.model.job.Job;
import seedu.address.testutil.JobBuilder;

public class JobCardTest extends GuiUnitTest {
    @Test
    public void display() {

        // sample job
        Job job = new JobBuilder().build();
        JobCard jobCard = new JobCard(job, 1);
        uiPartRule.setUiPart(jobCard);
        assertCardDisplay(jobCard, job, 1);
    }

    @Test
    public void equals() {
        Job job = new JobBuilder().build();
        JobCard jobCard = new JobCard(job, 0);

        // same job, same index -> returns true
        JobCard copy = new JobCard(job, 0);
        assertTrue(jobCard.equals(copy));

        // same object -> returns true
        assertTrue(jobCard.equals(jobCard));

        // null -> returns false
        assertFalse(jobCard.equals(null));

        // different types -> returns false
        assertFalse(jobCard.equals(0));

        // different job, same index -> returns false
        Job differentJob = new JobBuilder().withPosition("differentPosition").build();
        assertFalse(jobCard.equals(new JobCard(differentJob, 0)));

        // same job, different index -> returns false
        assertFalse(jobCard.equals(new JobCard(job, 1)));
    }

    /**
     * Asserts that {@code jobCard} displays the details of {@code expectedJob} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(JobCard jobCard, Job expectedJob, int expectedId) {
        guiRobot.pauseForHuman();

        JobCardHandle jobCardHandle = new JobCardHandle(jobCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", jobCardHandle.getId());

        // verify job details are displayed correctly
        assertCardDisplaysJob(expectedJob, jobCardHandle);
    }
}
