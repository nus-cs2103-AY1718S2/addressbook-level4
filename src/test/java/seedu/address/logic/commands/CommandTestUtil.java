package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMMUNICATION_SKILLS_SCORE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPECTED_GRADUATION_YEAR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPERIENCE_SCORE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADE_POINT_AVERAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IMAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOB_APPLIED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAJOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROBLEM_SOLVING_SKILLS_SCORE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RESUME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TECHNICAL_SKILLS_SCORE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_UNIVERSITY;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.EditPersonDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {
    public static final String RESUME_PATH = "src/test/resources/resume/";
    public static final String IMAGE_PATH = "src/test/resources/photos/";

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_UNIVERSITY_AMY = "NUS";
    public static final String VALID_UNIVERSITY_BOB = "NTU";
    public static final String VALID_EXPECTED_GRADUATION_YEAR_AMY = "2018";
    public static final String VALID_EXPECTED_GRADUATION_YEAR_BOB = "2020";
    public static final String VALID_GRADE_POINT_AVERAGE_AMY = "4.75";
    public static final String VALID_GRADE_POINT_AVERAGE_BOB = "4.93";
    public static final String VALID_MAJOR_AMY = "Computer Science";
    public static final String VALID_MAJOR_BOB = "Information Security";
    public static final String VALID_JOB_APPLIED_AMY = "Software Engineer";
    public static final String VALID_JOB_APPLIED_BOB = "Web Security Analyst";
    public static final String VALID_TECHNICAL_SKILLS_SCORE_AMY = "4";
    public static final String VALID_TECHNICAL_SKILLS_SCORE_BOB = "4.5";
    public static final String VALID_COMMUNICATION_SKILLS_SCORE_AMY = "3";
    public static final String VALID_COMMUNICATION_SKILLS_SCORE_BOB = "4";
    public static final String VALID_PROBLEM_SOLVING_SKILLS_SCORE_AMY = "2";
    public static final String VALID_PROBLEM_SOLVING_SKILLS_SCORE_BOB = "4";
    public static final String VALID_EXPERIENCE_SCORE_AMY = "3";
    public static final String VALID_EXPERIENCE_SCORE_BOB = "4.5";
    public static final String VALID_RESUME_AMY = RESUME_PATH + "amy.pdf";
    public static final String VALID_RESUME_BOB = null;
    public static final String VALID_PROFILE_IMAGE_AMY = IMAGE_PATH + "jobs.jpg";
    public static final String VALID_PROFILE_IMAGE_BOB = IMAGE_PATH + "gates.jpg";
    public static final String VALID_COMMENT_AMY = "Good interaction skill";
    public static final String VALID_COMMENT_BOB = "Great team player";

    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String UNIVERSITY_DESC_AMY = " " + PREFIX_UNIVERSITY + VALID_UNIVERSITY_AMY;
    public static final String UNIVERSITY_DESC_BOB = " " + PREFIX_UNIVERSITY + VALID_UNIVERSITY_BOB;
    public static final String EXPECTED_GRADUATION_YEAR_DESC_AMY = " " + PREFIX_EXPECTED_GRADUATION_YEAR
            + VALID_EXPECTED_GRADUATION_YEAR_AMY;
    public static final String EXPECTED_GRADUATION_YEAR_DESC_BOB = " " + PREFIX_EXPECTED_GRADUATION_YEAR
            + VALID_EXPECTED_GRADUATION_YEAR_BOB;
    public static final String GRADE_POINT_AVERAGE_DESC_AMY = " " + PREFIX_GRADE_POINT_AVERAGE
            + VALID_GRADE_POINT_AVERAGE_AMY;
    public static final String GRADE_POINT_AVERAGE_DESC_BOB = " " + PREFIX_GRADE_POINT_AVERAGE
            + VALID_GRADE_POINT_AVERAGE_BOB;
    public static final String MAJOR_DESC_AMY = " " + PREFIX_MAJOR + VALID_MAJOR_AMY;
    public static final String MAJOR_DESC_BOB = " " + PREFIX_MAJOR + VALID_MAJOR_BOB;
    public static final String JOB_APPLIED_DESC_AMY = " " + PREFIX_JOB_APPLIED + VALID_JOB_APPLIED_AMY;
    public static final String JOB_APPLIED_DESC_BOB = " " + PREFIX_JOB_APPLIED + VALID_JOB_APPLIED_BOB;
    public static final String RESUME_DESC_AMY = " " + PREFIX_RESUME + VALID_RESUME_AMY;
    public static final String RESUME_DESC_BOB = "";
    public static final String PROFILE_IMAGE_DESC_AMY = " " + PREFIX_IMAGE + VALID_PROFILE_IMAGE_AMY;
    public static final String PROFILE_IMAGE_DESC_BOB = " " + PREFIX_IMAGE + VALID_PROFILE_IMAGE_BOB;
    public static final String COMMENT_DESC_AMY = " " + PREFIX_COMMENT + VALID_COMMENT_AMY;
    public static final String COMMENT_DESC_BOB = " " + PREFIX_COMMENT + VALID_COMMENT_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_UNIVERSITY_DESC =
            " " + PREFIX_UNIVERSITY + "NU!S"; // '!' not allowed for addresses
    public static final String INVALID_EXPECTED_GRADUATION_YEAR_DESC = " " + PREFIX_EXPECTED_GRADUATION_YEAR
            + "2o20";
    public static final String INVALID_GRADE_POINT_AVERAGE_DESC = " " + PREFIX_GRADE_POINT_AVERAGE
            + "5.1"; // not in range
    public static final String INVALID_MAJOR_DESC = " " + PREFIX_MAJOR + "&Computer"; // '&' not allowed in major
    public static final String INVALID_JOB_APPLIED_DESC =
            " " + PREFIX_JOB_APPLIED + " "; // empty string not allowed in job applied
    public static final String INVALID_RATING_DESC = " " + PREFIX_TECHNICAL_SKILLS_SCORE + "-10"
            + " " + PREFIX_COMMUNICATION_SKILLS_SCORE + "0"
            + " " + PREFIX_PROBLEM_SOLVING_SKILLS_SCORE + "7.5"
            + " " + PREFIX_EXPERIENCE_SCORE + "0.5"; // scores should be between 1 and 5 or equal to default value -1
    public static final String INVALID_RESUME_DESC = " " + PREFIX_RESUME + "fileDoesNot.exist";
    public static final String INVALID_PROFILE_IMAGE_DESC = " " + PREFIX_IMAGE + "fileNotFound.jpg";
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withUniversity(VALID_UNIVERSITY_AMY)
                .withJobApplied(VALID_JOB_APPLIED_AMY)
                .withResume(VALID_RESUME_AMY).withProfileImage(VALID_PROFILE_IMAGE_AMY)
                .withComment(VALID_COMMENT_AMY).withTags(VALID_TAG_FRIEND).build();

        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withUniversity(VALID_UNIVERSITY_BOB)
                .withJobApplied(VALID_JOB_APPLIED_BOB)
                .withResume(VALID_RESUME_BOB).withProfileImage(VALID_PROFILE_IMAGE_BOB)
                .withComment(VALID_COMMENT_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        try {
            CommandResult result = command.execute();
            assertEquals(expectedMessage, result.feedbackToUser);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book and the filtered person list in the {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Person> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPersonList());

        try {
            command.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedAddressBook, actualModel.getAddressBook());
            assertEquals(expectedFilteredList, actualModel.getFilteredPersonList());
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPersonList().size());

        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPersonList().size());
    }

    /**
     * Deletes the first person in {@code model}'s filtered list from {@code model}'s address book.
     */
    public static void deleteFirstPerson(Model model) {
        Person firstPerson = model.getFilteredPersonList().get(0);
        try {
            model.deletePerson(firstPerson);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("Person in filtered list must exist in model.", pnfe);
        }
    }

    /**
     * Returns an {@code UndoCommand} with the given {@code model} and {@code undoRedoStack} set.
     */
    public static UndoCommand prepareUndoCommand(Model model, UndoRedoStack undoRedoStack) {
        UndoCommand undoCommand = new UndoCommand();
        undoCommand.setData(model, new CommandHistory(), undoRedoStack);
        return undoCommand;
    }

    /**
     * Returns a {@code RedoCommand} with the given {@code model} and {@code undoRedoStack} set.
     */
    public static RedoCommand prepareRedoCommand(Model model, UndoRedoStack undoRedoStack) {
        RedoCommand redoCommand = new RedoCommand();
        redoCommand.setData(model, new CommandHistory(), undoRedoStack);
        return redoCommand;
    }
}
