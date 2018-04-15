package seedu.address.storage;

import static seedu.address.storage.XmlAdaptedJob.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalJobs.FRONTEND;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.job.JobTitle;
import seedu.address.model.job.Location;
import seedu.address.model.skill.Skill;
import seedu.address.testutil.Assert;

//@@author ChengSashankh
public class XmlAdaptedJobTest {
    private static final String INVALID_JOBTITLE = "B!@@#";
    private static final String INVALID_LOCATION = " ";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_SKILL = "";

    private static final String VALID_JOBTITLE = FRONTEND.getJobTitle().toString();
    private static final String VALID_LOCATION = FRONTEND.getLocation().toString();
    private static final String VALID_SKILL = FRONTEND.getSkills().toString();
    private static final List<XmlAdaptedTag> VALID_TAGS = FRONTEND.getTags().stream()
            .map(XmlAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_invalidJobTitle_throwsIllegalValueException() {
        XmlAdaptedJob job =
                new XmlAdaptedJob(INVALID_JOBTITLE, VALID_LOCATION, VALID_SKILL, VALID_TAGS);
        String expectedMessage = JobTitle.MESSAGE_TITLE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, job::toModelType);
    }

    @Test
    public void toModelType_nullJobTitle_throwsIllegalValueException() {
        XmlAdaptedJob job = new XmlAdaptedJob(null, VALID_LOCATION, VALID_SKILL, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, JobTitle.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, job::toModelType);
    }

    @Test
    public void toModelType_invalidLocation_throwsIllegalValueException() {
        XmlAdaptedJob job =
                new XmlAdaptedJob(VALID_JOBTITLE, INVALID_LOCATION, VALID_SKILL, VALID_TAGS);
        String expectedMessage = Location.MESSAGE_LOCATION_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, job::toModelType);
    }

    @Test
    public void toModelType_nullLocation_throwsIllegalValueException() {
        XmlAdaptedJob job = new XmlAdaptedJob(VALID_JOBTITLE, null, VALID_SKILL, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Location.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, job::toModelType);
    }

    @Test
    public void toModelType_invalidSkills_throwsIllegalValueException() {
        XmlAdaptedJob job =
                new XmlAdaptedJob(VALID_JOBTITLE, VALID_LOCATION, INVALID_SKILL, VALID_TAGS);
        String expectedMessage = Skill.MESSAGE_SKILL_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, job::toModelType);
    }

    @Test
    public void toModelType_nullSkills_throwsIllegalValueException() {
        XmlAdaptedJob job = new XmlAdaptedJob(VALID_JOBTITLE, VALID_LOCATION, null, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Skill.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, job::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<XmlAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new XmlAdaptedTag(INVALID_TAG));
        XmlAdaptedJob job =
                new XmlAdaptedJob(VALID_JOBTITLE, VALID_LOCATION, VALID_SKILL, invalidTags);
        Assert.assertThrows(IllegalValueException.class, job::toModelType);
    }

}
//@@author
