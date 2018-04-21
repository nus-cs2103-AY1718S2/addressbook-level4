package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.COMMENT_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.COMMENT_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EXPECTED_GRADUATION_YEAR_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EXPECTED_GRADUATION_YEAR_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.GRADE_POINT_AVERAGE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.GRADE_POINT_AVERAGE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EXPECTED_GRADUATION_YEAR_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_GRADE_POINT_AVERAGE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_JOB_APPLIED_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MAJOR_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PROFILE_IMAGE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_RESUME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_UNIVERSITY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.JOB_APPLIED_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.JOB_APPLIED_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.MAJOR_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.MAJOR_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.PROFILE_IMAGE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PROFILE_IMAGE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.RESUME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.RESUME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.UNIVERSITY_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.UNIVERSITY_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMMENT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMMENT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPECTED_GRADUATION_YEAR_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPECTED_GRADUATION_YEAR_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GRADE_POINT_AVERAGE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GRADE_POINT_AVERAGE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_JOB_APPLIED_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_JOB_APPLIED_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MAJOR_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MAJOR_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROFILE_IMAGE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROFILE_IMAGE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RESUME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RESUME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_UNIVERSITY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_UNIVERSITY_BOB;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.CommandTestUtil;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.ExpectedGraduationYear;
import seedu.address.model.person.GradePointAverage;
import seedu.address.model.person.JobApplied;
import seedu.address.model.person.Major;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ProfileImage;
import seedu.address.model.person.Resume;
import seedu.address.model.person.University;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder().withName(CommandTestUtil.VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withUniversity(VALID_UNIVERSITY_BOB)
                .withExpectedGraduationYear(VALID_EXPECTED_GRADUATION_YEAR_BOB).withMajor(VALID_MAJOR_BOB)
                .withGradePointAverage(VALID_GRADE_POINT_AVERAGE_BOB)
                .withJobApplied(VALID_JOB_APPLIED_BOB).withResume(VALID_RESUME_BOB)
                .withProfileImage(VALID_PROFILE_IMAGE_BOB).withComment(VALID_COMMENT_BOB)
                .withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + UNIVERSITY_DESC_BOB + EXPECTED_GRADUATION_YEAR_DESC_BOB + MAJOR_DESC_BOB
                + GRADE_POINT_AVERAGE_DESC_BOB + JOB_APPLIED_DESC_BOB + RESUME_DESC_BOB
                + PROFILE_IMAGE_DESC_BOB + COMMENT_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedPerson));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AMY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + UNIVERSITY_DESC_BOB + EXPECTED_GRADUATION_YEAR_DESC_BOB + MAJOR_DESC_BOB
                + GRADE_POINT_AVERAGE_DESC_BOB + JOB_APPLIED_DESC_BOB + RESUME_DESC_BOB
                + PROFILE_IMAGE_DESC_BOB + COMMENT_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedPerson));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + UNIVERSITY_DESC_BOB + EXPECTED_GRADUATION_YEAR_DESC_BOB + MAJOR_DESC_BOB
                + GRADE_POINT_AVERAGE_DESC_BOB + JOB_APPLIED_DESC_BOB + RESUME_DESC_BOB
                + PROFILE_IMAGE_DESC_BOB + COMMENT_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedPerson));

        // multiple emails - last email accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_AMY + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + UNIVERSITY_DESC_BOB + EXPECTED_GRADUATION_YEAR_DESC_BOB + MAJOR_DESC_BOB
                + GRADE_POINT_AVERAGE_DESC_BOB + JOB_APPLIED_DESC_BOB + RESUME_DESC_BOB
                + PROFILE_IMAGE_DESC_BOB + COMMENT_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedPerson));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_AMY
                + ADDRESS_DESC_BOB + UNIVERSITY_DESC_BOB + EXPECTED_GRADUATION_YEAR_DESC_BOB + MAJOR_DESC_BOB
                + GRADE_POINT_AVERAGE_DESC_BOB + JOB_APPLIED_DESC_BOB + RESUME_DESC_BOB
                + PROFILE_IMAGE_DESC_BOB + COMMENT_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedPerson));

        // multiple universities - last university accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + UNIVERSITY_DESC_AMY + UNIVERSITY_DESC_BOB + EXPECTED_GRADUATION_YEAR_DESC_BOB + MAJOR_DESC_BOB
                + GRADE_POINT_AVERAGE_DESC_BOB + JOB_APPLIED_DESC_BOB + RESUME_DESC_BOB
                + PROFILE_IMAGE_DESC_BOB + COMMENT_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedPerson));

        // multiple expectedGraduationYear - last expectedGraduationYear accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + UNIVERSITY_DESC_BOB + EXPECTED_GRADUATION_YEAR_DESC_AMY + EXPECTED_GRADUATION_YEAR_DESC_BOB
                + MAJOR_DESC_BOB + GRADE_POINT_AVERAGE_DESC_BOB + JOB_APPLIED_DESC_BOB + RESUME_DESC_BOB
                + PROFILE_IMAGE_DESC_BOB + COMMENT_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedPerson));

        // multiple major - last major accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + UNIVERSITY_DESC_BOB + EXPECTED_GRADUATION_YEAR_DESC_BOB
                + MAJOR_DESC_AMY + MAJOR_DESC_BOB + GRADE_POINT_AVERAGE_DESC_BOB
                + JOB_APPLIED_DESC_BOB + RESUME_DESC_BOB
                + PROFILE_IMAGE_DESC_BOB + COMMENT_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedPerson));

        // multiple gradePointAverage - last gradePointAverage accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + UNIVERSITY_DESC_BOB + EXPECTED_GRADUATION_YEAR_DESC_BOB
                + MAJOR_DESC_BOB + GRADE_POINT_AVERAGE_DESC_AMY + GRADE_POINT_AVERAGE_DESC_BOB
                + JOB_APPLIED_DESC_BOB + RESUME_DESC_BOB
                + PROFILE_IMAGE_DESC_BOB + COMMENT_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedPerson));

        // multiple job applied - last job applied accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + UNIVERSITY_DESC_BOB + EXPECTED_GRADUATION_YEAR_DESC_BOB + MAJOR_DESC_BOB
                + GRADE_POINT_AVERAGE_DESC_BOB + JOB_APPLIED_DESC_AMY + JOB_APPLIED_DESC_BOB + RESUME_DESC_BOB
                + PROFILE_IMAGE_DESC_BOB + COMMENT_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedPerson));

        // multiple resume - last resume accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + UNIVERSITY_DESC_BOB + EXPECTED_GRADUATION_YEAR_DESC_BOB + MAJOR_DESC_BOB
                + GRADE_POINT_AVERAGE_DESC_BOB + JOB_APPLIED_DESC_BOB + RESUME_DESC_AMY + RESUME_DESC_BOB
                + PROFILE_IMAGE_DESC_BOB + COMMENT_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedPerson));

        // multiple profile image - last profile image accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + UNIVERSITY_DESC_BOB + EXPECTED_GRADUATION_YEAR_DESC_BOB + MAJOR_DESC_BOB
                + GRADE_POINT_AVERAGE_DESC_BOB + JOB_APPLIED_DESC_BOB + RESUME_DESC_BOB
                + PROFILE_IMAGE_DESC_AMY + PROFILE_IMAGE_DESC_BOB + COMMENT_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedPerson));

        // multiple comment - last comment accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + UNIVERSITY_DESC_BOB + EXPECTED_GRADUATION_YEAR_DESC_BOB + MAJOR_DESC_BOB
                + GRADE_POINT_AVERAGE_DESC_BOB + JOB_APPLIED_DESC_BOB + RESUME_DESC_BOB
                + PROFILE_IMAGE_DESC_BOB + COMMENT_DESC_AMY + COMMENT_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedPerson));

        // multiple tags - all accepted
        Person expectedPersonMultipleTags = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withUniversity(VALID_UNIVERSITY_BOB)
                .withExpectedGraduationYear(VALID_EXPECTED_GRADUATION_YEAR_BOB)
                .withMajor(VALID_MAJOR_BOB)
                .withGradePointAverage(VALID_GRADE_POINT_AVERAGE_BOB)
                .withJobApplied(VALID_JOB_APPLIED_BOB)
                .withProfileImage(VALID_PROFILE_IMAGE_BOB)
                .withComment(VALID_COMMENT_BOB)
                .withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND).build();

        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + UNIVERSITY_DESC_BOB + EXPECTED_GRADUATION_YEAR_DESC_BOB + MAJOR_DESC_BOB
                + GRADE_POINT_AVERAGE_DESC_BOB + JOB_APPLIED_DESC_BOB + PROFILE_IMAGE_DESC_BOB + COMMENT_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                new AddCommand(expectedPersonMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // No resume
        Person expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withUniversity(VALID_UNIVERSITY_AMY)
                .withExpectedGraduationYear(VALID_EXPECTED_GRADUATION_YEAR_AMY)
                .withMajor(VALID_MAJOR_AMY)
                .withGradePointAverage(VALID_GRADE_POINT_AVERAGE_AMY)
                .withJobApplied(VALID_JOB_APPLIED_AMY).withStatus(1)
                .withProfileImage(VALID_PROFILE_IMAGE_AMY)
                .withComment(VALID_COMMENT_AMY)
                .withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND).build();

        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + UNIVERSITY_DESC_AMY + EXPECTED_GRADUATION_YEAR_DESC_AMY + MAJOR_DESC_AMY
                + GRADE_POINT_AVERAGE_DESC_AMY + JOB_APPLIED_DESC_AMY + PROFILE_IMAGE_DESC_AMY
                + COMMENT_DESC_AMY + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                new AddCommand(expectedPerson));

        // No profile image
        expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withUniversity(VALID_UNIVERSITY_AMY)
                .withExpectedGraduationYear(VALID_EXPECTED_GRADUATION_YEAR_AMY)
                .withMajor(VALID_MAJOR_AMY)
                .withGradePointAverage(VALID_GRADE_POINT_AVERAGE_AMY)
                .withJobApplied(VALID_JOB_APPLIED_AMY).withStatus(1)
                .withProfileImage(null).withComment(VALID_COMMENT_AMY)
                .withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND).build();

        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + UNIVERSITY_DESC_AMY + EXPECTED_GRADUATION_YEAR_DESC_AMY + MAJOR_DESC_AMY
                + GRADE_POINT_AVERAGE_DESC_AMY
                + JOB_APPLIED_DESC_AMY + COMMENT_DESC_AMY
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                new AddCommand(expectedPerson));

        // No comment
        expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withExpectedGraduationYear(VALID_EXPECTED_GRADUATION_YEAR_AMY)
                .withMajor(VALID_MAJOR_AMY)
                .withGradePointAverage(VALID_GRADE_POINT_AVERAGE_AMY)
                .withJobApplied(VALID_JOB_APPLIED_AMY).withStatus(1)
                .withProfileImage(VALID_PROFILE_IMAGE_AMY).withComment(null)
                .withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND).build();

        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + UNIVERSITY_DESC_AMY + EXPECTED_GRADUATION_YEAR_DESC_AMY + MAJOR_DESC_AMY
                + GRADE_POINT_AVERAGE_DESC_AMY + JOB_APPLIED_DESC_AMY + PROFILE_IMAGE_DESC_AMY
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                new AddCommand(expectedPerson));

        // Zero tags
        expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withUniversity(VALID_UNIVERSITY_AMY)
                .withExpectedGraduationYear(VALID_EXPECTED_GRADUATION_YEAR_AMY)
                .withMajor(VALID_MAJOR_AMY)
                .withGradePointAverage(VALID_GRADE_POINT_AVERAGE_AMY)
                .withJobApplied(VALID_JOB_APPLIED_AMY)
                .withResume(VALID_RESUME_AMY)
                .withProfileImage(VALID_PROFILE_IMAGE_AMY)
                .withComment(VALID_COMMENT_AMY)
                .withTags().build();

        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + UNIVERSITY_DESC_AMY + EXPECTED_GRADUATION_YEAR_DESC_AMY + MAJOR_DESC_AMY
                + GRADE_POINT_AVERAGE_DESC_AMY + JOB_APPLIED_DESC_AMY + RESUME_DESC_AMY
                + PROFILE_IMAGE_DESC_AMY + COMMENT_DESC_AMY,
                new AddCommand(expectedPerson));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + UNIVERSITY_DESC_BOB + EXPECTED_GRADUATION_YEAR_DESC_BOB + MAJOR_DESC_BOB
                + GRADE_POINT_AVERAGE_DESC_BOB + JOB_APPLIED_DESC_BOB,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_PHONE_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + UNIVERSITY_DESC_BOB + EXPECTED_GRADUATION_YEAR_DESC_BOB + MAJOR_DESC_BOB
                + GRADE_POINT_AVERAGE_DESC_BOB + JOB_APPLIED_DESC_BOB,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + VALID_EMAIL_BOB + ADDRESS_DESC_BOB
                + UNIVERSITY_DESC_BOB + EXPECTED_GRADUATION_YEAR_DESC_BOB + MAJOR_DESC_BOB
                + GRADE_POINT_AVERAGE_DESC_BOB + JOB_APPLIED_DESC_BOB,
                expectedMessage);

        // missing address prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + VALID_ADDRESS_BOB
                + UNIVERSITY_DESC_BOB + EXPECTED_GRADUATION_YEAR_DESC_BOB + MAJOR_DESC_BOB
                + GRADE_POINT_AVERAGE_DESC_BOB + JOB_APPLIED_DESC_BOB,
                expectedMessage);

        // missing university prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + VALID_UNIVERSITY_BOB + EXPECTED_GRADUATION_YEAR_DESC_BOB + MAJOR_DESC_BOB
                + GRADE_POINT_AVERAGE_DESC_BOB + JOB_APPLIED_DESC_BOB,
                expectedMessage);

        //missing expectedGraduationYear prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + UNIVERSITY_DESC_BOB + VALID_EXPECTED_GRADUATION_YEAR_BOB + MAJOR_DESC_BOB
                + GRADE_POINT_AVERAGE_DESC_BOB + JOB_APPLIED_DESC_BOB,
                expectedMessage);

        //missing major prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + UNIVERSITY_DESC_BOB + EXPECTED_GRADUATION_YEAR_DESC_BOB + VALID_MAJOR_BOB
                + GRADE_POINT_AVERAGE_DESC_BOB + JOB_APPLIED_DESC_BOB,
                expectedMessage);

        //missing gradePointAverage prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + UNIVERSITY_DESC_BOB + EXPECTED_GRADUATION_YEAR_DESC_BOB + MAJOR_DESC_BOB
                + VALID_GRADE_POINT_AVERAGE_BOB + JOB_APPLIED_DESC_BOB,
                expectedMessage);

        //missing job applied prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + UNIVERSITY_DESC_BOB + EXPECTED_GRADUATION_YEAR_DESC_BOB + MAJOR_DESC_BOB
                + GRADE_POINT_AVERAGE_DESC_BOB
                + VALID_JOB_APPLIED_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB + VALID_ADDRESS_BOB
                + VALID_UNIVERSITY_BOB + VALID_EXPECTED_GRADUATION_YEAR_BOB + VALID_MAJOR_BOB
                + VALID_GRADE_POINT_AVERAGE_BOB + VALID_JOB_APPLIED_BOB + VALID_RESUME_AMY,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + UNIVERSITY_DESC_BOB + EXPECTED_GRADUATION_YEAR_DESC_BOB + MAJOR_DESC_BOB
                + GRADE_POINT_AVERAGE_DESC_BOB
                + JOB_APPLIED_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Name.MESSAGE_NAME_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + UNIVERSITY_DESC_BOB + EXPECTED_GRADUATION_YEAR_DESC_BOB + MAJOR_DESC_BOB
                + GRADE_POINT_AVERAGE_DESC_BOB
                + JOB_APPLIED_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Phone.MESSAGE_PHONE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC + ADDRESS_DESC_BOB
                + UNIVERSITY_DESC_BOB + EXPECTED_GRADUATION_YEAR_DESC_BOB + MAJOR_DESC_BOB
                + GRADE_POINT_AVERAGE_DESC_BOB
                + JOB_APPLIED_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Email.MESSAGE_EMAIL_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC
                + UNIVERSITY_DESC_BOB + EXPECTED_GRADUATION_YEAR_DESC_BOB + MAJOR_DESC_BOB
                + GRADE_POINT_AVERAGE_DESC_BOB
                + JOB_APPLIED_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Address.MESSAGE_ADDRESS_CONSTRAINTS);

        // invalid university
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + INVALID_UNIVERSITY_DESC + EXPECTED_GRADUATION_YEAR_DESC_BOB + MAJOR_DESC_BOB
                + GRADE_POINT_AVERAGE_DESC_BOB
                + JOB_APPLIED_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                University.MESSAGE_UNIVERSITY_CONSTRAINTS);

        //invalid expectedGraduationYear
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + UNIVERSITY_DESC_BOB + INVALID_EXPECTED_GRADUATION_YEAR_DESC + MAJOR_DESC_BOB
                + GRADE_POINT_AVERAGE_DESC_BOB
                + JOB_APPLIED_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                ExpectedGraduationYear.MESSAGE_EXPECTED_GRADUATION_YEAR_CONSTRAINTS);

        //invalid major
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + UNIVERSITY_DESC_BOB + EXPECTED_GRADUATION_YEAR_DESC_BOB + INVALID_MAJOR_DESC
                + GRADE_POINT_AVERAGE_DESC_BOB
                + JOB_APPLIED_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Major.MESSAGE_MAJOR_CONSTRAINTS);

        //invalid gradePointAverage
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + UNIVERSITY_DESC_BOB + EXPECTED_GRADUATION_YEAR_DESC_BOB + MAJOR_DESC_BOB
                + INVALID_GRADE_POINT_AVERAGE_DESC
                + JOB_APPLIED_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                GradePointAverage.MESSAGE_GRADE_POINT_AVERAGE_CONSTRAINTS);

        //invalid job applied
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + UNIVERSITY_DESC_BOB + EXPECTED_GRADUATION_YEAR_DESC_BOB + MAJOR_DESC_BOB
                + GRADE_POINT_AVERAGE_DESC_BOB
                + INVALID_JOB_APPLIED_DESC
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                JobApplied.MESSAGE_JOB_APPLIED_CONSTRAINTS);

        // invalid resume
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + UNIVERSITY_DESC_BOB + EXPECTED_GRADUATION_YEAR_DESC_BOB + MAJOR_DESC_BOB
                + GRADE_POINT_AVERAGE_DESC_BOB
                + JOB_APPLIED_DESC_BOB
                + INVALID_RESUME_DESC + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Resume.MESSAGE_RESUME_CONSTRAINTS);

        // invalid profile image
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + UNIVERSITY_DESC_BOB + EXPECTED_GRADUATION_YEAR_DESC_BOB + MAJOR_DESC_BOB
                + GRADE_POINT_AVERAGE_DESC_BOB
                + JOB_APPLIED_DESC_BOB + INVALID_PROFILE_IMAGE_DESC + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                ProfileImage.MESSAGE_IMAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + UNIVERSITY_DESC_BOB + EXPECTED_GRADUATION_YEAR_DESC_BOB + MAJOR_DESC_BOB
                + GRADE_POINT_AVERAGE_DESC_BOB
                + JOB_APPLIED_DESC_BOB
                + INVALID_TAG_DESC + VALID_TAG_FRIEND,
                Tag.MESSAGE_TAG_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC
                + UNIVERSITY_DESC_BOB + EXPECTED_GRADUATION_YEAR_DESC_BOB + MAJOR_DESC_BOB
                + GRADE_POINT_AVERAGE_DESC_BOB
                + JOB_APPLIED_DESC_BOB,
                Name.MESSAGE_NAME_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + UNIVERSITY_DESC_BOB + EXPECTED_GRADUATION_YEAR_DESC_BOB + MAJOR_DESC_BOB
                + GRADE_POINT_AVERAGE_DESC_BOB
                + JOB_APPLIED_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
