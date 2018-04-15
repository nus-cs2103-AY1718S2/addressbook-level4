//@@author deeheenguyen
package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.XmlAdaptedInterview.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalInterviews.SE_INTERVIEW;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.interview.Date;
import seedu.address.model.interview.InterviewLocation;
import seedu.address.model.interview.InterviewTitle;
import seedu.address.model.person.Name;
import seedu.address.testutil.Assert;

public class XmlAdaptedInterviewTest {
    private static final String INVALID_INTERTVIEW_TITLE = "R@chel";
    private static final String INVALID_DATE = "+651234";
    private static final String INVALID_INTERVIEW_LOCATION = " ";
    private static final String INVALID_INTERVIEWEE = "@@__";

    private static final String VALID_INTERVIEW_TITLE = SE_INTERVIEW.getInterviewTitle().toString();
    private static final String VALID_DATE = SE_INTERVIEW.getDate().toString();
    private static final String VALID_INTERVIEWEE = SE_INTERVIEW.getInterviewee().toString();
    private static final String VALID_INTERVIEW_LOCATION = SE_INTERVIEW.getInterviewLocation().toString();

    @Test
    public void toModelType_validInterviewDetails_returnsInterview() throws Exception {
        XmlAdaptedInterview interview = new XmlAdaptedInterview(SE_INTERVIEW);
        assertEquals(SE_INTERVIEW, interview.toModelType());
    }

    @Test
    public void toModelType_invalidInterviewTitle_throwsIllegalValueException() {
        XmlAdaptedInterview interview =
                new XmlAdaptedInterview(INVALID_INTERTVIEW_TITLE, VALID_INTERVIEWEE, VALID_DATE,
                        VALID_INTERVIEW_LOCATION);
        String expectedMessage = InterviewTitle.MESSAGE_TITLE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, interview::toModelType);
    }

    @Test
    public void toModelType_nullInterviewTitle_throwsIllegalValueException() {
        XmlAdaptedInterview interview = new XmlAdaptedInterview(null, VALID_INTERVIEWEE,
                VALID_DATE, VALID_INTERVIEW_LOCATION);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, InterviewTitle.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, interview::toModelType);
    }

    @Test
    public void toModelType_invalidInterviewee_throwsIllegalValueException() {
        XmlAdaptedInterview interview =
                new XmlAdaptedInterview(VALID_INTERVIEW_TITLE, INVALID_INTERVIEWEE, VALID_DATE,
                        VALID_INTERVIEW_LOCATION);
        String expectedMessage = Name.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, interview::toModelType);
    }

    @Test
    public void toModelType_nullInterviewee_throwsIllegalValueException() {
        XmlAdaptedInterview interview = new XmlAdaptedInterview(VALID_INTERVIEW_TITLE, null, VALID_DATE,
                VALID_INTERVIEW_LOCATION);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, interview::toModelType);
    }

    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        XmlAdaptedInterview interview =
                new XmlAdaptedInterview(VALID_INTERVIEW_TITLE, VALID_INTERVIEWEE, INVALID_DATE,
                        VALID_INTERVIEW_LOCATION);
        String expectedMessage = Date.MESSAGE_DATE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, interview::toModelType);
    }

    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        XmlAdaptedInterview interview = new XmlAdaptedInterview(VALID_INTERVIEW_TITLE, VALID_INTERVIEWEE,
                null, VALID_INTERVIEW_LOCATION);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, interview::toModelType);
    }

    @Test
    public void toModelType_invalidInterviewLocation_throwsIllegalValueException() {
        XmlAdaptedInterview interview =
                new XmlAdaptedInterview(VALID_INTERVIEW_TITLE, VALID_INTERVIEWEE, VALID_DATE,
                        INVALID_INTERVIEW_LOCATION);
        String expectedMessage = InterviewLocation.MESSAGE_LOCATION_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, interview::toModelType);
    }

    @Test
    public void toModelType_nullInterviewLocation_throwsIllegalValueException() {
        XmlAdaptedInterview interview = new XmlAdaptedInterview(VALID_INTERVIEW_TITLE, VALID_INTERVIEWEE, VALID_DATE,
                null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, InterviewLocation.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, interview::toModelType);
    }

}
