package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysJob;

import org.junit.Test;

import guitests.guihandles.JobCardHandle;
import seedu.address.model.job.Job;
import seedu.address.testutil.JobBuilder;

//@@author ChengSashankh
public class JobCardTest extends GuiUnitTest {
    @Test
    public void display() {
        // no tags
        Job jobWithNoTags = new JobBuilder().withTags(new String[0]).build();
        JobCard jobCard = new JobCard(jobWithNoTags, 1);
        uiPartRule.setUiPart(jobCard);
        assertCardDisplay(jobCard, jobWithNoTags, 1);

        // with tags
        Job jobWithTags = new JobBuilder().build();
        jobCard = new JobCard(jobWithTags, 2);
        uiPartRule.setUiPart(jobCard);
        assertCardDisplay(jobCard, jobWithTags, 2);
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
        Job differentJob = new JobBuilder().withJobTitle("differentName").build();
        assertFalse(jobCard.equals(new JobCard(differentJob, 0)));

        // same person, different index -> returns false
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

        // verify person details are displayed correctly
        assertCardDisplaysJob(expectedJob, jobCardHandle);
    }

}
//@@author
