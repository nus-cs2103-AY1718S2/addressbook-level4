package seedu.address.storage;

import static org.junit.Assert.*;
import static seedu.address.storage.XmlAdaptedJob.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalJobs.MARKETING_INTERN;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.job.Location;
import seedu.address.model.job.NumberOfPositions;
import seedu.address.model.job.Position;
import seedu.address.model.job.Team;
import seedu.address.testutil.Assert;

public class XmlAdaptedJobTest {
    
    private static final String INVALID_POSITION = "Associ@te";
    private static final String INVALID_TEAM = "Engineering#";
    private static final String INVALID_LOCATION = " ";
    private static final String INVALID_NUMBER_OF_POSITIONS = "a12";
    private static final String INVALID_TAG = "C and Java";
    
    private static final String VALID_POSITION = MARKETING_INTERN.getPosition().toString();
    private static final String VALID_TEAM = MARKETING_INTERN.getTeam().toString();
    private static final String VALID_LOCATION = MARKETING_INTERN.getLocation().toString();
    private static final String VALID_NUMBER_OF_POSITIONS = MARKETING_INTERN.getNumberOfPositions().toString();
    private static final List<XmlAdaptedTag> VALID_TAGS = MARKETING_INTERN.getTags().stream()
            .map(XmlAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validJobDetails_returnsJob() throws Exception {
        XmlAdaptedJob job = new XmlAdaptedJob(MARKETING_INTERN);
        assertEquals(MARKETING_INTERN, job.toModelType());
    }

    @Test
    public void toModelType_invalidPosition_throwsIllegalValueException() {
        XmlAdaptedJob job =
                new XmlAdaptedJob(INVALID_POSITION, VALID_TEAM, VALID_LOCATION, VALID_NUMBER_OF_POSITIONS, VALID_TAGS);
        String expectedMessage = Position.MESSAGE_POSITION_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, job::toModelType);
    }

    @Test
    public void toModelType_nullPosition_throwsIllegalValueException() {
        XmlAdaptedJob job =
                new XmlAdaptedJob(null, VALID_TEAM, VALID_LOCATION, VALID_NUMBER_OF_POSITIONS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Position.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, job::toModelType);
    }

    @Test
    public void toModelType_invalidTeam_throwsIllegalValueException() {
        XmlAdaptedJob job =
                new XmlAdaptedJob(VALID_POSITION, INVALID_TEAM, VALID_LOCATION, VALID_NUMBER_OF_POSITIONS, VALID_TAGS);
        String expectedMessage = Team.MESSAGE_TEAM_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, job::toModelType);
    }

    @Test
    public void toModelType_nullTeam_throwsIllegalValueException() {
        XmlAdaptedJob job =
                new XmlAdaptedJob(VALID_POSITION, null, VALID_LOCATION, VALID_NUMBER_OF_POSITIONS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Team.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, job::toModelType);
    }

    @Test
    public void toModelType_invalidLocation_throwsIllegalValueException() {
        XmlAdaptedJob job =
                new XmlAdaptedJob(VALID_POSITION, VALID_TEAM, INVALID_LOCATION, VALID_NUMBER_OF_POSITIONS, VALID_TAGS);
        String expectedMessage = Location.MESSAGE_LOCATION_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, job::toModelType);
    }

    @Test
    public void toModelType_nullLocation_throwsIllegalValueException() {
        XmlAdaptedJob job =
                new XmlAdaptedJob(VALID_POSITION, VALID_TEAM, null, VALID_NUMBER_OF_POSITIONS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Location.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, job::toModelType);
    }

    @Test
    public void toModelType_invalidNumberOfPositions_throwsIllegalValueException() {
        XmlAdaptedJob job =
                new XmlAdaptedJob(VALID_POSITION, VALID_TEAM, VALID_LOCATION, INVALID_NUMBER_OF_POSITIONS, VALID_TAGS);
        String expectedMessage = NumberOfPositions.MESSAGE_NUMBER_OF_POSITIONS_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, job::toModelType);
    }

    @Test
    public void toModelType_nullNumberOfPositions_throwsIllegalValueException() {
        XmlAdaptedJob job =
                new XmlAdaptedJob(VALID_POSITION, VALID_TEAM, VALID_LOCATION, null, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, NumberOfPositions.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, job::toModelType);
    }

    @Test
    public void toModelType_emptyTags_throwsIllegalValueException() {
        List<XmlAdaptedTag> emptyTags = new ArrayList<>();
        XmlAdaptedJob job =
                new XmlAdaptedJob(VALID_POSITION, VALID_TEAM, VALID_LOCATION, VALID_NUMBER_OF_POSITIONS, emptyTags);
        Assert.assertThrows(IllegalValueException.class, job::toModelType);
    }
    
    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<XmlAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new XmlAdaptedTag(INVALID_TAG));
        XmlAdaptedJob job =
                new XmlAdaptedJob(VALID_POSITION, VALID_TEAM, VALID_LOCATION, VALID_NUMBER_OF_POSITIONS, invalidTags);
        Assert.assertThrows(IllegalValueException.class, job::toModelType);
    }
    
}