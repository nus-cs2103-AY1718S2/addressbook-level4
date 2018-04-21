package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.storage.XmlAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.ExpectedGraduationYear;
import seedu.address.model.person.GradePointAverage;
import seedu.address.model.person.InterviewDate;
import seedu.address.model.person.JobApplied;
import seedu.address.model.person.Major;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Rating;
import seedu.address.model.person.Status;
import seedu.address.model.person.University;
import seedu.address.testutil.Assert;

public class XmlAdaptedPersonTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_UNIVERSITY = "N{}S";
    private static final String INVALID_EXPECTED_GRADUATION_YEAR = "2o1o";
    private static final String INVALID_MAJOR = "C[]mputer Science";
    private static final String INVALID_GRADE_POINT_AVERAGE = "4.X";
    private static final String INVALID_JOB_APPLIED = " ";
    private static final String INVALID_TECHNICAL_SKILLS_SCORE = "10";
    private static final String INVALID_COMMUNICATION_SKILLS_SCORE = "-1.5";
    private static final String INVALID_PROBLEM_SOLVING_SKILLS_SCORE = "0";
    private static final String INVALID_EXPERIENCE_SCORE = "5.5";
    private static final String INVALID_INTERVIEW_DATE = "Tomorrow";
    private static final String INVALID_STATUS = "dead";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final String VALID_UNIVERSITY = BENSON.getUniversity().toString();
    private static final String VALID_EXPECTED_GRADUATION_YEAR = BENSON.getExpectedGraduationYear().toString();
    private static final String VALID_MAJOR = BENSON.getMajor().toString();
    private static final String VALID_GRADE_POINT_AVERAGE = BENSON.getGradePointAverage().toString();
    private static final String VALID_JOB_APPLIED = BENSON.getJobApplied().toString();
    private static final String VALID_TECHNICAL_SKILLS_SCORE = Double.toString(
            BENSON.getRating().getTechnicalSkillsScore());
    private static final String VALID_COMMUNICATION_SKILLS_SCORE = Double.toString(
            BENSON.getRating().getCommunicationSkillsScore());
    private static final String VALID_PROBLEM_SOLVING_SKILLS_SCORE = Double.toString(
            BENSON.getRating().getProblemSolvingSkillsScore());
    private static final String VALID_EXPERIENCE_SCORE = Double.toString(
            BENSON.getRating().getExperienceScore());
    private static final String VALID_RESUME = BENSON.getResume().toString();
    private static final String VALID_PROFILE_IMAGE = BENSON.getProfileImage().toString();
    private static final String VALID_COMMENT = BENSON.getComment().toString();
    private static final String VALID_INTERVIEW_DATE = "1540814400";
    private static final String VALID_STATUS = BENSON.getStatus().toString();
    private static final List<XmlAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(XmlAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        XmlAdaptedPerson person = new XmlAdaptedPerson(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_UNIVERSITY,
                    VALID_EXPECTED_GRADUATION_YEAR, VALID_MAJOR,
                    VALID_GRADE_POINT_AVERAGE, VALID_JOB_APPLIED,
                    VALID_TECHNICAL_SKILLS_SCORE,
                    VALID_COMMUNICATION_SKILLS_SCORE, VALID_PROBLEM_SOLVING_SKILLS_SCORE,
                    VALID_EXPERIENCE_SCORE, VALID_RESUME, VALID_PROFILE_IMAGE, VALID_COMMENT,
                    VALID_INTERVIEW_DATE, VALID_STATUS, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedPerson person = new XmlAdaptedPerson(null, VALID_PHONE, VALID_EMAIL,
            VALID_ADDRESS, VALID_UNIVERSITY, VALID_EXPECTED_GRADUATION_YEAR, VALID_MAJOR,
            VALID_GRADE_POINT_AVERAGE, VALID_JOB_APPLIED,
            VALID_TECHNICAL_SKILLS_SCORE,
            VALID_COMMUNICATION_SKILLS_SCORE, VALID_PROBLEM_SOLVING_SKILLS_SCORE,
            VALID_EXPERIENCE_SCORE, VALID_RESUME, VALID_PROFILE_IMAGE, VALID_COMMENT,
            VALID_INTERVIEW_DATE, VALID_STATUS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        XmlAdaptedPerson person =
            new XmlAdaptedPerson(VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_UNIVERSITY,
                VALID_EXPECTED_GRADUATION_YEAR, VALID_MAJOR,
                VALID_GRADE_POINT_AVERAGE, VALID_JOB_APPLIED,
                VALID_TECHNICAL_SKILLS_SCORE,
                VALID_COMMUNICATION_SKILLS_SCORE, VALID_PROBLEM_SOLVING_SKILLS_SCORE,
                VALID_EXPERIENCE_SCORE, VALID_RESUME, VALID_PROFILE_IMAGE, VALID_COMMENT,
                VALID_INTERVIEW_DATE, VALID_STATUS, VALID_TAGS);
        String expectedMessage = Phone.MESSAGE_PHONE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        XmlAdaptedPerson person = new XmlAdaptedPerson(VALID_NAME, null, VALID_EMAIL,
            VALID_ADDRESS, VALID_UNIVERSITY, VALID_EXPECTED_GRADUATION_YEAR, VALID_MAJOR,
            VALID_GRADE_POINT_AVERAGE,
            VALID_JOB_APPLIED,
            VALID_TECHNICAL_SKILLS_SCORE,
            VALID_COMMUNICATION_SKILLS_SCORE, VALID_PROBLEM_SOLVING_SKILLS_SCORE,
            VALID_EXPERIENCE_SCORE, VALID_RESUME, VALID_PROFILE_IMAGE, VALID_COMMENT,
            VALID_INTERVIEW_DATE, VALID_STATUS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        XmlAdaptedPerson person =
            new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS, VALID_UNIVERSITY,
                VALID_EXPECTED_GRADUATION_YEAR, VALID_MAJOR,
                VALID_GRADE_POINT_AVERAGE,
                VALID_JOB_APPLIED,
                VALID_TECHNICAL_SKILLS_SCORE,
                VALID_COMMUNICATION_SKILLS_SCORE, VALID_PROBLEM_SOLVING_SKILLS_SCORE,
                VALID_EXPERIENCE_SCORE, VALID_RESUME, VALID_PROFILE_IMAGE, VALID_COMMENT,
                VALID_INTERVIEW_DATE, VALID_STATUS, VALID_TAGS);
        String expectedMessage = Email.MESSAGE_EMAIL_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        XmlAdaptedPerson person = new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, null,
            VALID_ADDRESS, VALID_UNIVERSITY, VALID_EXPECTED_GRADUATION_YEAR, VALID_MAJOR,
            VALID_GRADE_POINT_AVERAGE, VALID_JOB_APPLIED,
            VALID_TECHNICAL_SKILLS_SCORE,
            VALID_COMMUNICATION_SKILLS_SCORE, VALID_PROBLEM_SOLVING_SKILLS_SCORE,
            VALID_EXPERIENCE_SCORE, VALID_RESUME, VALID_PROFILE_IMAGE, VALID_COMMENT,
            VALID_INTERVIEW_DATE, VALID_STATUS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        XmlAdaptedPerson person =
            new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS,
                VALID_UNIVERSITY, VALID_EXPECTED_GRADUATION_YEAR, VALID_MAJOR,
                VALID_GRADE_POINT_AVERAGE, VALID_JOB_APPLIED,
                VALID_TECHNICAL_SKILLS_SCORE,
                VALID_COMMUNICATION_SKILLS_SCORE, VALID_PROBLEM_SOLVING_SKILLS_SCORE,
                VALID_EXPERIENCE_SCORE, VALID_RESUME, VALID_PROFILE_IMAGE, VALID_COMMENT,
                VALID_INTERVIEW_DATE, VALID_STATUS, VALID_TAGS);
        String expectedMessage = Address.MESSAGE_ADDRESS_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        XmlAdaptedPerson person = new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL,
            null, VALID_UNIVERSITY, VALID_EXPECTED_GRADUATION_YEAR, VALID_MAJOR,
            VALID_GRADE_POINT_AVERAGE, VALID_JOB_APPLIED,
            VALID_TECHNICAL_SKILLS_SCORE,
            VALID_COMMUNICATION_SKILLS_SCORE, VALID_PROBLEM_SOLVING_SKILLS_SCORE,
            VALID_EXPERIENCE_SCORE, VALID_RESUME, VALID_PROFILE_IMAGE, VALID_COMMENT,
            VALID_INTERVIEW_DATE, VALID_STATUS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    //@@author kexiaowen
    @Test
    public void toModelType_invalidUniversity_throwsIllegalValueException() {
        XmlAdaptedPerson person = new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                INVALID_UNIVERSITY, VALID_EXPECTED_GRADUATION_YEAR, VALID_MAJOR,
                VALID_GRADE_POINT_AVERAGE, VALID_JOB_APPLIED, VALID_TECHNICAL_SKILLS_SCORE,
                VALID_COMMUNICATION_SKILLS_SCORE, VALID_PROBLEM_SOLVING_SKILLS_SCORE, VALID_EXPERIENCE_SCORE,
                VALID_RESUME, VALID_PROFILE_IMAGE, VALID_COMMENT, VALID_INTERVIEW_DATE, VALID_STATUS,
                VALID_TAGS);
        String expectedMessage = University.MESSAGE_UNIVERSITY_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullUniversity_throwsIllegalValueException() {
        XmlAdaptedPerson person = new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                null, VALID_EXPECTED_GRADUATION_YEAR, VALID_MAJOR,
                VALID_GRADE_POINT_AVERAGE, VALID_JOB_APPLIED, VALID_TECHNICAL_SKILLS_SCORE,
                VALID_COMMUNICATION_SKILLS_SCORE, VALID_PROBLEM_SOLVING_SKILLS_SCORE, VALID_EXPERIENCE_SCORE,
                VALID_RESUME, VALID_PROFILE_IMAGE, VALID_COMMENT, VALID_INTERVIEW_DATE, VALID_STATUS,
                VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT,
                University.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    //@@author
    @Test
    public void toModelType_invalidExpectedGraduationYear_throwsIllegalValueException() {
        XmlAdaptedPerson person =
            new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_UNIVERSITY, INVALID_EXPECTED_GRADUATION_YEAR, VALID_MAJOR, VALID_GRADE_POINT_AVERAGE,
                VALID_JOB_APPLIED,
                VALID_TECHNICAL_SKILLS_SCORE,
                VALID_COMMUNICATION_SKILLS_SCORE, VALID_PROBLEM_SOLVING_SKILLS_SCORE,
                VALID_EXPERIENCE_SCORE, VALID_RESUME, VALID_PROFILE_IMAGE, VALID_COMMENT,
                VALID_INTERVIEW_DATE, VALID_STATUS, VALID_TAGS);
        String expectedMessage = ExpectedGraduationYear.MESSAGE_EXPECTED_GRADUATION_YEAR_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullExpectedGraduationYear_throwsIllegalValueException() {
        XmlAdaptedPerson person = new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_UNIVERSITY, null, VALID_MAJOR, VALID_GRADE_POINT_AVERAGE,
                VALID_JOB_APPLIED, VALID_TECHNICAL_SKILLS_SCORE,
                VALID_COMMUNICATION_SKILLS_SCORE, VALID_PROBLEM_SOLVING_SKILLS_SCORE,
                VALID_EXPERIENCE_SCORE, VALID_RESUME, VALID_PROFILE_IMAGE, VALID_COMMENT,
                VALID_INTERVIEW_DATE, VALID_STATUS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT,
                ExpectedGraduationYear.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    //@@author tanhengyeow
    @Test
    public void toModelType_invalidMajor_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_UNIVERSITY,
                        VALID_EXPECTED_GRADUATION_YEAR, INVALID_MAJOR, VALID_GRADE_POINT_AVERAGE,
                        VALID_JOB_APPLIED, VALID_TECHNICAL_SKILLS_SCORE,
                        VALID_COMMUNICATION_SKILLS_SCORE, VALID_PROBLEM_SOLVING_SKILLS_SCORE,
                        VALID_EXPERIENCE_SCORE, VALID_RESUME, VALID_PROFILE_IMAGE, VALID_COMMENT,
                        VALID_INTERVIEW_DATE, VALID_STATUS, VALID_TAGS);
        String expectedMessage = Major.MESSAGE_MAJOR_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullMajor_throwsIllegalValueException() {
        XmlAdaptedPerson person = new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_UNIVERSITY, VALID_EXPECTED_GRADUATION_YEAR, null,
                VALID_GRADE_POINT_AVERAGE, VALID_JOB_APPLIED, VALID_TECHNICAL_SKILLS_SCORE,
                VALID_COMMUNICATION_SKILLS_SCORE, VALID_PROBLEM_SOLVING_SKILLS_SCORE,
                VALID_EXPERIENCE_SCORE, VALID_RESUME, VALID_PROFILE_IMAGE, VALID_COMMENT,
                VALID_INTERVIEW_DATE, VALID_STATUS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT,
                Major.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidGradePointAverage_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_UNIVERSITY,
                        VALID_EXPECTED_GRADUATION_YEAR, VALID_MAJOR,
                        INVALID_GRADE_POINT_AVERAGE, VALID_JOB_APPLIED, VALID_TECHNICAL_SKILLS_SCORE,
                        VALID_COMMUNICATION_SKILLS_SCORE, VALID_PROBLEM_SOLVING_SKILLS_SCORE,
                        VALID_EXPERIENCE_SCORE, VALID_RESUME, VALID_PROFILE_IMAGE, VALID_COMMENT,
                        VALID_INTERVIEW_DATE, VALID_STATUS, VALID_TAGS);
        String expectedMessage = GradePointAverage.MESSAGE_GRADE_POINT_AVERAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    //@@author kexiaowen
    @Test
    public void toModelType_invalidJobApplied_throwsIllegalValueException() {
        XmlAdaptedPerson person = new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_UNIVERSITY, VALID_EXPECTED_GRADUATION_YEAR, VALID_MAJOR, VALID_GRADE_POINT_AVERAGE,
                INVALID_JOB_APPLIED, VALID_TECHNICAL_SKILLS_SCORE, VALID_COMMUNICATION_SKILLS_SCORE,
                VALID_PROBLEM_SOLVING_SKILLS_SCORE, VALID_EXPERIENCE_SCORE, VALID_RESUME,
                VALID_PROFILE_IMAGE, VALID_COMMENT,
                VALID_INTERVIEW_DATE, VALID_STATUS, VALID_TAGS);
        String expectedMessage = JobApplied.MESSAGE_JOB_APPLIED_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullJobApplied_throwsIllegalValueException() {
        XmlAdaptedPerson person = new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_UNIVERSITY, VALID_EXPECTED_GRADUATION_YEAR, VALID_MAJOR, VALID_GRADE_POINT_AVERAGE,
                null, VALID_TECHNICAL_SKILLS_SCORE, VALID_COMMUNICATION_SKILLS_SCORE,
                VALID_PROBLEM_SOLVING_SKILLS_SCORE, VALID_EXPERIENCE_SCORE, VALID_RESUME,
                VALID_PROFILE_IMAGE, VALID_COMMENT,
                VALID_INTERVIEW_DATE, VALID_STATUS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT,
                JobApplied.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    //@@author tanhengyeow
    @Test
    public void toModelType_nullGradePointAverage_throwsIllegalValueException() {
        XmlAdaptedPerson person = new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_UNIVERSITY, VALID_EXPECTED_GRADUATION_YEAR, VALID_MAJOR,
                null, VALID_JOB_APPLIED, VALID_TECHNICAL_SKILLS_SCORE,
                VALID_COMMUNICATION_SKILLS_SCORE, VALID_PROBLEM_SOLVING_SKILLS_SCORE,
                VALID_EXPERIENCE_SCORE, VALID_RESUME, VALID_PROFILE_IMAGE, VALID_COMMENT,
                VALID_INTERVIEW_DATE, VALID_STATUS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT,
                GradePointAverage.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    //@@author kexiaowen
    @Test
    public void toModelType_invalidRating_throwsIllegalValueException() {
        XmlAdaptedPerson person = new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_UNIVERSITY, VALID_EXPECTED_GRADUATION_YEAR, VALID_MAJOR, VALID_GRADE_POINT_AVERAGE,
                VALID_JOB_APPLIED, INVALID_TECHNICAL_SKILLS_SCORE, INVALID_COMMUNICATION_SKILLS_SCORE,
                INVALID_PROBLEM_SOLVING_SKILLS_SCORE, INVALID_EXPERIENCE_SCORE,
                VALID_RESUME, VALID_PROFILE_IMAGE, VALID_COMMENT,
                VALID_INTERVIEW_DATE, VALID_STATUS, VALID_TAGS);
        String expectedMessage = Rating.MESSAGE_RATING_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    //@@author Ang-YC
    @Test
    public void toModelType_invalidInterviewDate_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_UNIVERSITY,
                        VALID_EXPECTED_GRADUATION_YEAR, VALID_MAJOR,
                        VALID_GRADE_POINT_AVERAGE, VALID_JOB_APPLIED, VALID_TECHNICAL_SKILLS_SCORE,
                        VALID_COMMUNICATION_SKILLS_SCORE, VALID_PROBLEM_SOLVING_SKILLS_SCORE,
                        VALID_EXPERIENCE_SCORE, VALID_RESUME, VALID_PROFILE_IMAGE, VALID_COMMENT,
                        INVALID_INTERVIEW_DATE, VALID_STATUS, VALID_TAGS);
        String expectedMessage = InterviewDate.MESSAGE_INTERVIEW_DATE_XML_ERROR;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }
    //@@author

    @Test
    public void toModelType_nullStatus_throwsIllegalValueException() {
        XmlAdaptedPerson person = new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_UNIVERSITY, VALID_EXPECTED_GRADUATION_YEAR, VALID_MAJOR,
                VALID_GRADE_POINT_AVERAGE, VALID_JOB_APPLIED, VALID_TECHNICAL_SKILLS_SCORE,
                VALID_COMMUNICATION_SKILLS_SCORE, VALID_PROBLEM_SOLVING_SKILLS_SCORE,
                VALID_EXPERIENCE_SCORE, VALID_RESUME, VALID_PROFILE_IMAGE, VALID_COMMENT,
                VALID_INTERVIEW_DATE, null, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT,
                Status.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidStatus_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_UNIVERSITY,
                        VALID_EXPECTED_GRADUATION_YEAR, VALID_MAJOR, VALID_GRADE_POINT_AVERAGE,
                        VALID_JOB_APPLIED, VALID_TECHNICAL_SKILLS_SCORE,
                        VALID_COMMUNICATION_SKILLS_SCORE, VALID_PROBLEM_SOLVING_SKILLS_SCORE,
                        VALID_EXPERIENCE_SCORE, VALID_RESUME, VALID_PROFILE_IMAGE, VALID_COMMENT,
                        VALID_INTERVIEW_DATE, INVALID_STATUS, VALID_TAGS);
        String expectedMessage = Status.MESSAGE_STATUS_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<XmlAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new XmlAdaptedTag(INVALID_TAG));
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_UNIVERSITY,
                        VALID_EXPECTED_GRADUATION_YEAR, VALID_MAJOR,
                        VALID_GRADE_POINT_AVERAGE, VALID_JOB_APPLIED, VALID_TECHNICAL_SKILLS_SCORE,
                        VALID_COMMUNICATION_SKILLS_SCORE, VALID_PROBLEM_SOLVING_SKILLS_SCORE,
                        VALID_EXPERIENCE_SCORE, VALID_RESUME, VALID_PROFILE_IMAGE, VALID_COMMENT,
                        VALID_INTERVIEW_DATE, VALID_STATUS, invalidTags);
        Assert.assertThrows(IllegalValueException.class, person::toModelType);
    }

    //@@author Ang-YC
    @Test
    public void equals_sameObject_returnsTrue() {
        XmlAdaptedPerson person = new XmlAdaptedPerson(BENSON);
        assertTrue(person.equals(person));
    }

    @Test
    public void equals_differentValues_returnsFalse() {
        XmlAdaptedPerson alice = new XmlAdaptedPerson(ALICE);
        XmlAdaptedPerson benson = new XmlAdaptedPerson(BENSON);
        assertFalse(alice.equals(benson));
    }
    //@@author
}
