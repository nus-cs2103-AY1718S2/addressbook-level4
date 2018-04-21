package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.AddCommand.MESSAGE_DUPLICATE_PERSON;
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
import static seedu.address.logic.commands.CommandTestUtil.PROFILE_IMAGE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PROFILE_IMAGE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.RESUME_DESC_AMY;
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
import static seedu.address.logic.commands.CommandTestUtil.VALID_MAJOR_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MAJOR_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROFILE_IMAGE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RESUME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_UNIVERSITY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_UNIVERSITY_BOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RESUME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.HOON;
import static seedu.address.testutil.TypicalPersons.IDA;
import static seedu.address.testutil.TypicalPersons.KEYWORD_MATCHING_MEIER;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
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
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;

public class AddCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void add() throws Exception {
        Model model = getModel();

        /* ------------------------ Perform add operations on the shown unfiltered list ----------------------------- */

        /* Case: add a person without tags to a non-empty address book, command with leading spaces and trailing spaces
         * -> added
         */
        Person toAdd = new PersonBuilder(AMY).withRating("-1", "-1", "-1", "-1").build();
        String command = "   " + AddCommand.COMMAND_WORD + "  " + NAME_DESC_AMY + "  " + PHONE_DESC_AMY + " "
                + EMAIL_DESC_AMY + "   " + ADDRESS_DESC_AMY + "   " + UNIVERSITY_DESC_AMY + " "
                + EXPECTED_GRADUATION_YEAR_DESC_AMY + " "
                + MAJOR_DESC_AMY + " " + GRADE_POINT_AVERAGE_DESC_AMY + " "
                + JOB_APPLIED_DESC_AMY + " " + RESUME_DESC_AMY + " " + PROFILE_IMAGE_DESC_AMY + " "
                + COMMENT_DESC_AMY + " " + TAG_DESC_FRIEND + " ";
        assertCommandSuccess(command, toAdd);

        /* Case: undo adding Amy to the list -> Amy deleted */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding Amy to the list -> Amy added again */
        command = RedoCommand.COMMAND_WORD;
        model.addPerson(toAdd);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: add a person with all fields same as another person in the address book except name -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_AMY).withUniversity(VALID_UNIVERSITY_AMY)
                .withExpectedGraduationYear(VALID_EXPECTED_GRADUATION_YEAR_AMY)
                .withMajor(VALID_MAJOR_AMY).withGradePointAverage(VALID_GRADE_POINT_AVERAGE_AMY)
                .withJobApplied(VALID_JOB_APPLIED_AMY)
                .withResume(VALID_RESUME_AMY).withProfileImage(VALID_PROFILE_IMAGE_AMY).withComment(VALID_COMMENT_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + UNIVERSITY_DESC_AMY + EXPECTED_GRADUATION_YEAR_DESC_AMY + MAJOR_DESC_AMY
                + GRADE_POINT_AVERAGE_DESC_AMY + JOB_APPLIED_DESC_AMY + RESUME_DESC_AMY
                + PROFILE_IMAGE_DESC_AMY + COMMENT_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: add a person with all fields same as another person in the address book except phone -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_AMY).withUniversity(VALID_UNIVERSITY_AMY)
                .withExpectedGraduationYear(VALID_EXPECTED_GRADUATION_YEAR_AMY)
                .withMajor(VALID_MAJOR_AMY)
                .withGradePointAverage(VALID_GRADE_POINT_AVERAGE_AMY)
                .withJobApplied(VALID_JOB_APPLIED_AMY)
                .withResume(VALID_RESUME_AMY).withProfileImage(VALID_PROFILE_IMAGE_AMY).withComment(VALID_COMMENT_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_BOB + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + UNIVERSITY_DESC_AMY + EXPECTED_GRADUATION_YEAR_DESC_AMY + MAJOR_DESC_AMY
                + GRADE_POINT_AVERAGE_DESC_AMY + JOB_APPLIED_DESC_AMY + RESUME_DESC_AMY + PROFILE_IMAGE_DESC_AMY
                + COMMENT_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: add a person with all fields same as another person in the address book except email -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_AMY).withUniversity(VALID_UNIVERSITY_AMY)
                .withExpectedGraduationYear(VALID_EXPECTED_GRADUATION_YEAR_AMY)
                .withMajor(VALID_MAJOR_AMY)
                .withGradePointAverage(VALID_GRADE_POINT_AVERAGE_AMY).withJobApplied(VALID_JOB_APPLIED_AMY)
                .withResume(VALID_RESUME_AMY).withProfileImage(VALID_PROFILE_IMAGE_AMY).withComment(VALID_COMMENT_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_BOB + ADDRESS_DESC_AMY
                + UNIVERSITY_DESC_AMY + EXPECTED_GRADUATION_YEAR_DESC_AMY + MAJOR_DESC_AMY
                + GRADE_POINT_AVERAGE_DESC_AMY + JOB_APPLIED_DESC_AMY + RESUME_DESC_AMY
                + PROFILE_IMAGE_DESC_AMY + COMMENT_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: add a person with all fields same as another person in the address book except address -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_BOB).withUniversity(VALID_UNIVERSITY_AMY)
                .withExpectedGraduationYear(VALID_EXPECTED_GRADUATION_YEAR_AMY)
                .withMajor(VALID_MAJOR_AMY).withGradePointAverage(VALID_GRADE_POINT_AVERAGE_AMY)
                .withJobApplied(VALID_JOB_APPLIED_AMY)
                .withResume(VALID_RESUME_AMY).withProfileImage(VALID_PROFILE_IMAGE_AMY).withComment(VALID_COMMENT_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_BOB
                + UNIVERSITY_DESC_AMY + EXPECTED_GRADUATION_YEAR_DESC_AMY + MAJOR_DESC_AMY
                + GRADE_POINT_AVERAGE_DESC_AMY + JOB_APPLIED_DESC_AMY + RESUME_DESC_AMY
                + PROFILE_IMAGE_DESC_AMY + COMMENT_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: add a person with all fields same as another person in the address book except university -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_AMY).withUniversity(VALID_UNIVERSITY_BOB)
                .withExpectedGraduationYear(VALID_EXPECTED_GRADUATION_YEAR_AMY)
                .withMajor(VALID_MAJOR_AMY).withGradePointAverage(VALID_GRADE_POINT_AVERAGE_AMY)
                .withJobApplied(VALID_JOB_APPLIED_AMY)
                .withResume(VALID_RESUME_AMY).withProfileImage(VALID_PROFILE_IMAGE_AMY).withComment(VALID_COMMENT_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + UNIVERSITY_DESC_BOB + EXPECTED_GRADUATION_YEAR_DESC_AMY + MAJOR_DESC_AMY
                + GRADE_POINT_AVERAGE_DESC_AMY + JOB_APPLIED_DESC_AMY + RESUME_DESC_AMY
                + PROFILE_IMAGE_DESC_AMY + COMMENT_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: add a person with all fields same as another person in the address book
        except expected graduation year -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_AMY).withUniversity(VALID_UNIVERSITY_AMY)
                .withExpectedGraduationYear(VALID_EXPECTED_GRADUATION_YEAR_BOB)
                .withMajor(VALID_MAJOR_AMY).withGradePointAverage(VALID_GRADE_POINT_AVERAGE_AMY)
                .withJobApplied(VALID_JOB_APPLIED_AMY)
                .withResume(VALID_RESUME_AMY).withProfileImage(VALID_PROFILE_IMAGE_AMY).withComment(VALID_COMMENT_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + UNIVERSITY_DESC_AMY + EXPECTED_GRADUATION_YEAR_DESC_BOB + MAJOR_DESC_AMY
                + GRADE_POINT_AVERAGE_DESC_AMY + JOB_APPLIED_DESC_AMY + RESUME_DESC_AMY + PROFILE_IMAGE_DESC_AMY
                + COMMENT_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: add a person with all fields same as another person in the address book
        except major -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_AMY).withUniversity(VALID_UNIVERSITY_AMY)
                .withExpectedGraduationYear(VALID_EXPECTED_GRADUATION_YEAR_AMY)
                .withMajor(VALID_MAJOR_BOB).withGradePointAverage(VALID_GRADE_POINT_AVERAGE_AMY)
                .withJobApplied(VALID_JOB_APPLIED_AMY)
                .withResume(VALID_RESUME_AMY).withProfileImage(VALID_PROFILE_IMAGE_AMY).withComment(VALID_COMMENT_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + UNIVERSITY_DESC_AMY + EXPECTED_GRADUATION_YEAR_DESC_AMY + MAJOR_DESC_BOB
                + GRADE_POINT_AVERAGE_DESC_AMY + JOB_APPLIED_DESC_AMY + RESUME_DESC_AMY + PROFILE_IMAGE_DESC_AMY
                + COMMENT_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: add a person with all fields same as another person in the address book
        except gradePointAverage -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_AMY).withUniversity(VALID_UNIVERSITY_AMY)
                .withExpectedGraduationYear(VALID_EXPECTED_GRADUATION_YEAR_AMY)
                .withMajor(VALID_MAJOR_AMY)
                .withGradePointAverage(VALID_GRADE_POINT_AVERAGE_BOB)
                .withResume(VALID_RESUME_AMY).withProfileImage(VALID_PROFILE_IMAGE_AMY).withComment(VALID_COMMENT_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + UNIVERSITY_DESC_AMY + EXPECTED_GRADUATION_YEAR_DESC_AMY + MAJOR_DESC_AMY
                + GRADE_POINT_AVERAGE_DESC_BOB + JOB_APPLIED_DESC_AMY + RESUME_DESC_AMY
                + PROFILE_IMAGE_DESC_AMY + COMMENT_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: add a person with all fields same as another person in the address book except comment -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_AMY).withUniversity(VALID_UNIVERSITY_AMY)
                .withExpectedGraduationYear(VALID_EXPECTED_GRADUATION_YEAR_AMY)
                .withMajor(VALID_MAJOR_AMY)
                .withGradePointAverage(VALID_GRADE_POINT_AVERAGE_AMY)
                .withResume(VALID_RESUME_AMY).withProfileImage(VALID_PROFILE_IMAGE_AMY).withComment(VALID_COMMENT_BOB)
                .withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + UNIVERSITY_DESC_AMY + EXPECTED_GRADUATION_YEAR_DESC_AMY + MAJOR_DESC_AMY
                + GRADE_POINT_AVERAGE_DESC_AMY + JOB_APPLIED_DESC_AMY + RESUME_DESC_AMY
                + PROFILE_IMAGE_DESC_AMY + COMMENT_DESC_BOB + TAG_DESC_FRIEND;
        assertCommandFailure(command, MESSAGE_DUPLICATE_PERSON);

        /* Case: add to empty address book -> added */
        deleteAllPersons();
        assertCommandSuccess(new PersonBuilder(ALICE).withRating("-1", "-1", "-1", "-1").build());

        /* Case: add a person with resume, command with parameters in random order -> added */
        toAdd = new PersonBuilder(AMY).withRating("-1", "-1", "-1", "-1").build();;
        command = AddCommand.COMMAND_WORD + RESUME_DESC_AMY + PHONE_DESC_AMY + ADDRESS_DESC_AMY + NAME_DESC_AMY
                + TAG_DESC_FRIEND + EMAIL_DESC_AMY + PROFILE_IMAGE_DESC_AMY + COMMENT_DESC_AMY
                + EXPECTED_GRADUATION_YEAR_DESC_AMY + MAJOR_DESC_AMY + UNIVERSITY_DESC_AMY
                + GRADE_POINT_AVERAGE_DESC_AMY + JOB_APPLIED_DESC_AMY;
        assertCommandSuccess(command, toAdd);

        /* Case: add a person, missing resume -> added */
        assertCommandSuccess(new PersonBuilder(BOB).withRating("-1", "-1", "-1", "-1").build());
        deleteAllPersons();

        /* Case: add a person with tags, command with parameters in random order -> added */
        toAdd = new PersonBuilder(BOB).withRating("-1", "-1", "-1", "-1").build();
        command = AddCommand.COMMAND_WORD + TAG_DESC_FRIEND + PHONE_DESC_BOB + ADDRESS_DESC_BOB + NAME_DESC_BOB
                + TAG_DESC_HUSBAND + EMAIL_DESC_BOB + PROFILE_IMAGE_DESC_BOB + COMMENT_DESC_BOB
                + EXPECTED_GRADUATION_YEAR_DESC_BOB + UNIVERSITY_DESC_BOB
                + MAJOR_DESC_BOB + GRADE_POINT_AVERAGE_DESC_BOB + JOB_APPLIED_DESC_BOB;
        assertCommandSuccess(command, toAdd);

        /* Case: add a person, missing tags -> added */
        assertCommandSuccess(new PersonBuilder(HOON).withRating("-1", "-1", "-1", "-1").build());

        /* -------------------------- Perform add operation on the shown filtered list ------------------------------ */

        /* Case: filters the person list before adding -> added */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        assertCommandSuccess(new PersonBuilder(IDA).withRating("-1", "-1", "-1", "-1").build());

        /* ------------------------ Perform add operation while a person card is selected --------------------------- */

        /* Case: selects first card in the person list, add a person -> added, card selection remains unchanged */
        selectPerson(Index.fromOneBased(1));
        assertCommandSuccess(new PersonBuilder(CARL).withRating("-1", "-1", "-1", "-1").build());

        /* ----------------------------------- Perform invalid add operations --------------------------------------- */

        /* Case: add a duplicate person -> rejected */
        command = PersonUtil.getAddCommand(HOON);
        assertCommandFailure(command, MESSAGE_DUPLICATE_PERSON);

        /* Case: add a duplicate person except with different tags -> rejected */
        // "friends" is an existing tag used in the default model, see TypicalPersons#ALICE
        // This test will fail if a new tag that is not in the model is used, see the bug documented in
        // AddressBook#addPerson(Person)
        command = PersonUtil.getAddCommand(HOON) + " " + PREFIX_TAG.getPrefix() + "friends";
        assertCommandFailure(command, MESSAGE_DUPLICATE_PERSON);

        command = PersonUtil.getAddCommand(HOON) + " " + PREFIX_RESUME.getPrefix()
                + "src/test/resources/resume/amy.pdf";
        assertCommandFailure(command, MESSAGE_DUPLICATE_PERSON);

        /* Case: missing name -> rejected */
        command = AddCommand.COMMAND_WORD + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + UNIVERSITY_DESC_AMY
                + EXPECTED_GRADUATION_YEAR_DESC_AMY + MAJOR_DESC_AMY
                + GRADE_POINT_AVERAGE_DESC_AMY + JOB_APPLIED_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing phone -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + UNIVERSITY_DESC_AMY
                + EXPECTED_GRADUATION_YEAR_DESC_AMY + MAJOR_DESC_AMY
                + GRADE_POINT_AVERAGE_DESC_AMY + JOB_APPLIED_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing email -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + ADDRESS_DESC_AMY + UNIVERSITY_DESC_AMY
                + EXPECTED_GRADUATION_YEAR_DESC_AMY + MAJOR_DESC_AMY
                + GRADE_POINT_AVERAGE_DESC_AMY + JOB_APPLIED_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing address -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + UNIVERSITY_DESC_AMY
                + EXPECTED_GRADUATION_YEAR_DESC_AMY + MAJOR_DESC_AMY
                + GRADE_POINT_AVERAGE_DESC_AMY + JOB_APPLIED_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing university -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + EXPECTED_GRADUATION_YEAR_DESC_AMY + MAJOR_DESC_AMY
                + GRADE_POINT_AVERAGE_DESC_AMY + JOB_APPLIED_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing expected graduation year -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + ADDRESS_DESC_AMY + MAJOR_DESC_AMY + GRADE_POINT_AVERAGE_DESC_AMY + JOB_APPLIED_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing major -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + UNIVERSITY_DESC_AMY + EXPECTED_GRADUATION_YEAR_DESC_AMY + GRADE_POINT_AVERAGE_DESC_AMY
                + JOB_APPLIED_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing job applied -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + UNIVERSITY_DESC_AMY + EXPECTED_GRADUATION_YEAR_DESC_AMY + MAJOR_DESC_AMY
                + GRADE_POINT_AVERAGE_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing gradePointAverage -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + UNIVERSITY_DESC_AMY + EXPECTED_GRADUATION_YEAR_DESC_AMY + MAJOR_DESC_AMY + JOB_APPLIED_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        command = "adds " + PersonUtil.getPersonDetails(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid name -> rejected */
        command = AddCommand.COMMAND_WORD + INVALID_NAME_DESC + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + UNIVERSITY_DESC_AMY + EXPECTED_GRADUATION_YEAR_DESC_AMY + MAJOR_DESC_AMY
                + GRADE_POINT_AVERAGE_DESC_AMY + JOB_APPLIED_DESC_AMY;
        assertCommandFailure(command, Name.MESSAGE_NAME_CONSTRAINTS);

        /* Case: invalid phone -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + INVALID_PHONE_DESC + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + UNIVERSITY_DESC_AMY + EXPECTED_GRADUATION_YEAR_DESC_AMY + MAJOR_DESC_AMY
                + GRADE_POINT_AVERAGE_DESC_AMY + JOB_APPLIED_DESC_AMY;
        assertCommandFailure(command, Phone.MESSAGE_PHONE_CONSTRAINTS);

        /* Case: invalid email -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + INVALID_EMAIL_DESC + ADDRESS_DESC_AMY
                + UNIVERSITY_DESC_AMY + EXPECTED_GRADUATION_YEAR_DESC_AMY + MAJOR_DESC_AMY
                + GRADE_POINT_AVERAGE_DESC_AMY + JOB_APPLIED_DESC_AMY;
        assertCommandFailure(command, Email.MESSAGE_EMAIL_CONSTRAINTS);

        /* Case: invalid address -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + INVALID_ADDRESS_DESC
                + UNIVERSITY_DESC_AMY + EXPECTED_GRADUATION_YEAR_DESC_AMY + MAJOR_DESC_AMY
                + GRADE_POINT_AVERAGE_DESC_AMY + JOB_APPLIED_DESC_AMY;
        assertCommandFailure(command, Address.MESSAGE_ADDRESS_CONSTRAINTS);

        /* Case: invalid university -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + INVALID_UNIVERSITY_DESC + EXPECTED_GRADUATION_YEAR_DESC_AMY + MAJOR_DESC_AMY
                + GRADE_POINT_AVERAGE_DESC_AMY + JOB_APPLIED_DESC_AMY;
        assertCommandFailure(command, University.MESSAGE_UNIVERSITY_CONSTRAINTS);

        /* Case: invalid expected graduation year -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + UNIVERSITY_DESC_AMY + INVALID_EXPECTED_GRADUATION_YEAR_DESC + MAJOR_DESC_AMY
                + GRADE_POINT_AVERAGE_DESC_AMY + JOB_APPLIED_DESC_AMY;
        assertCommandFailure(command, ExpectedGraduationYear.MESSAGE_EXPECTED_GRADUATION_YEAR_CONSTRAINTS);

        /* Case: invalid major -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + UNIVERSITY_DESC_AMY + EXPECTED_GRADUATION_YEAR_DESC_AMY + INVALID_MAJOR_DESC
                + GRADE_POINT_AVERAGE_DESC_AMY + JOB_APPLIED_DESC_AMY;
        assertCommandFailure(command, Major.MESSAGE_MAJOR_CONSTRAINTS);

        /* Case: invalid gradePointAverage -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + UNIVERSITY_DESC_AMY + EXPECTED_GRADUATION_YEAR_DESC_AMY + MAJOR_DESC_AMY
                + INVALID_GRADE_POINT_AVERAGE_DESC + JOB_APPLIED_DESC_AMY;
        assertCommandFailure(command, GradePointAverage.MESSAGE_GRADE_POINT_AVERAGE_CONSTRAINTS);

        /* Case: invalid job applied -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + UNIVERSITY_DESC_AMY + EXPECTED_GRADUATION_YEAR_DESC_AMY + MAJOR_DESC_AMY
                + GRADE_POINT_AVERAGE_DESC_AMY + INVALID_JOB_APPLIED_DESC;
        assertCommandFailure(command, JobApplied.MESSAGE_JOB_APPLIED_CONSTRAINTS);

        /* Case: invalid resume -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + UNIVERSITY_DESC_AMY + EXPECTED_GRADUATION_YEAR_DESC_AMY + MAJOR_DESC_AMY
                + GRADE_POINT_AVERAGE_DESC_AMY + JOB_APPLIED_DESC_AMY + INVALID_RESUME_DESC;
        assertCommandFailure(command, Resume.MESSAGE_RESUME_CONSTRAINTS);

        /* Case: invalid profile image -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + UNIVERSITY_DESC_AMY + EXPECTED_GRADUATION_YEAR_DESC_AMY + MAJOR_DESC_AMY
                + GRADE_POINT_AVERAGE_DESC_AMY + JOB_APPLIED_DESC_AMY + INVALID_PROFILE_IMAGE_DESC;
        assertCommandFailure(command, ProfileImage.MESSAGE_IMAGE_CONSTRAINTS);


        /* Case: invalid tag -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + UNIVERSITY_DESC_AMY + EXPECTED_GRADUATION_YEAR_DESC_AMY + MAJOR_DESC_AMY
                + GRADE_POINT_AVERAGE_DESC_AMY + JOB_APPLIED_DESC_AMY + INVALID_TAG_DESC;
        assertCommandFailure(command, Tag.MESSAGE_TAG_CONSTRAINTS);

        /* Case: blank resume -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + UNIVERSITY_DESC_AMY + EXPECTED_GRADUATION_YEAR_DESC_AMY + MAJOR_DESC_AMY
                + GRADE_POINT_AVERAGE_DESC_AMY + JOB_APPLIED_DESC_AMY + " " + PREFIX_RESUME;
        assertCommandFailure(command, Resume.MESSAGE_RESUME_CONSTRAINTS);
    }

    /**
     * Executes the {@code AddCommand} that adds {@code toAdd} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code AddCommand} with the details of
     * {@code toAdd}.<br>
     * 4. {@code Model}, {@code Storage} and {@code PersonListPanel} equal to the corresponding components in
     * the current model added with {@code toAdd}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(Person toAdd) {
        assertCommandSuccess(PersonUtil.getAddCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(Person)}. Executes {@code command}
     * instead.
     * @see AddCommandSystemTest#assertCommandSuccess(Person)
     */
    private void assertCommandSuccess(String command, Person toAdd) {
        Model expectedModel = getModel();
        try {
            expectedModel.addPerson(toAdd);
        } catch (DuplicatePersonException dpe) {
            throw new IllegalArgumentException("toAdd already exists in the model.");
        }
        String expectedResultMessage = String.format(AddCommand.MESSAGE_SUCCESS, toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Person)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Model}, {@code Storage} and {@code PersonListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see AddCommandSystemTest#assertCommandSuccess(String, Person)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Model}, {@code Storage} and {@code PersonListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
