package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INTERVIEW;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOBTITLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LINK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SKILLS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;

import seedu.address.model.interview.Interview;
import seedu.address.model.interview.InterviewMatchInterviewee;
import seedu.address.model.interview.exceptions.InterviewNotFoundException;
import seedu.address.model.job.Job;
import seedu.address.model.job.JobMatchesKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonContainsKeywordsPredicate;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.EditJobDescriptorBuilder;
import seedu.address.testutil.EditPersonDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_REMARK_AMY = "Has a pet";
    public static final String VALID_REMARK_BOB = "Comes from NUS";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
    public static final String VALID_TAG_NONEXISTENT = "nonexistent";
    public static final String VALID_LINK_AMY = "https://www.google.com.sg/";
    public static final String VALID_LINK_BOB = "https://www.google.com.sg/";
    public static final String VALID_SKILL_AMY = "CSS";
    public static final String VALID_SKILL_BOB = "CSS";

    public static final String VALID_JOBTITLE_FE = "Frontend Engineer";
    public static final String VALID_JOBTITLE_BE = "Backend Engineer";
    public static final String VALID_LOCATION_FE = "Bayfront";
    public static final String VALID_LOCATION_BE = "Kent Ridge";
    public static final String VALID_SKILL_FE = "HTML, CSS, JavaScript";
    public static final String VALID_SKILL_BE = "JavaScript, Python, Java";
    public static final String VALID_TAG_FE = "FreshGrad";
    public static final String VALID_TAG_BE = "Intern";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;
    public static final String LINK_DESC_AMY = " " + PREFIX_LINK + VALID_LINK_AMY;
    public static final String LINK_DESC_BOB = " " + PREFIX_LINK + VALID_LINK_BOB;
    public static final String SKILL_DESC_AMY = " " + PREFIX_SKILLS + VALID_SKILL_AMY;
    public static final String SKILL_DESC_BOB = " " + PREFIX_SKILLS + VALID_SKILL_BOB;

    public static final String JOBTITLE_DESC_FE = " " + PREFIX_JOBTITLE + VALID_JOBTITLE_FE;
    public static final String JOBTITLE_DESC_BE = " " + PREFIX_JOBTITLE + VALID_JOBTITLE_BE;
    public static final String LOCATION_DESC_FE = " " + PREFIX_LOCATION + VALID_LOCATION_FE;
    public static final String LOCATION_DESC_BE = " " + PREFIX_LOCATION + VALID_LOCATION_BE;
    public static final String SKILL_DESC_FE = " " + PREFIX_SKILLS + VALID_SKILL_FE;
    public static final String SKILL_DESC_BE = " " + PREFIX_SKILLS + VALID_SKILL_BE;
    public static final String TAG_DESC_FE = " " + PREFIX_TAG + VALID_TAG_FE;
    public static final String TAG_DESC_BE = " " + PREFIX_TAG + VALID_TAG_BE;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags
    public static final String INVALID_LINK = " " + PREFIX_LINK + "abc.com"; // 'should start with https'

    // TODO: Create correct invalid descriptors
    public static final String INVALID_JOBTITLE_DESC = " " + PREFIX_JOBTITLE + "SoftwareEngineer%";
    // '%' is not allowed in job title
    public static final String INVALID_LOCATION_DESC = " " + PREFIX_LOCATION + " "; // Location shouls not be empty
    public static final String INVALID_SKILL_DESC =  " " + PREFIX_SKILLS + ""; // Alphanumeric skills are expected
    public static final String VALID_INTERVIEW_TITLE_SE = "SE INTERVIEW";
    public static final String VALID_INTERVIEWEE_SE = "David";
    public static final String VALID_INTERVIEW_LOCATION_SE = "NUS";
    public static final String VALID_DATE_SE = "01.01.2018";

    public static final String INTERVIEW_TITLE_DESC_SE = " " + PREFIX_INTERVIEW + VALID_INTERVIEW_TITLE_SE;
    public static final String INTERVIEWEE_DESC_SE = " " + PREFIX_NAME + VALID_INTERVIEWEE_SE;
    public static final String DATE_DESC_SE = " " + PREFIX_DATE + VALID_DATE_SE;
    public static final String INTERVIEW_LOCATION_DESC_SE = " " + PREFIX_LOCATION + VALID_INTERVIEW_LOCATION_SE;

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

    public static final EditJobCommand.EditJobDescriptor DESC_FE;
    public static final EditJobCommand.EditJobDescriptor DESC_BE;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        DESC_FE = new EditJobDescriptorBuilder().withJobTitle(VALID_JOBTITLE_FE).withLocation(VALID_LOCATION_FE)
                .withSkills(VALID_SKILL_FE).withTags(VALID_TAG_FE).build();
        DESC_BE = new EditJobDescriptorBuilder().withJobTitle(VALID_JOBTITLE_BE).withLocation(VALID_LOCATION_BE)
                .withSkills(VALID_SKILL_BE).withTags(VALID_TAG_BE).build();
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
        model.updateFilteredPersonList(new PersonContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPersonList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the job at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showJobAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredJobList().size());

        Job job = model.getFilteredJobList().get(targetIndex.getZeroBased());
        final String[] splitName = job.getJobTitle().fullTitle.split("\\s+");
        model.updateFilteredJobList(new JobMatchesKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredJobList().size());
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
     * Updates {@code model}'s filtered list to show only the interview at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showInterviewAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredInterviewList().size());

        Interview interview = model.getFilteredInterviewList().get(targetIndex.getZeroBased());
        final String splitName = interview.getInterviewee().fullName;
        model.updateFilteredInterviewList(new InterviewMatchInterviewee(splitName));

        assertEquals(1, model.getFilteredInterviewList().size());
    }

    /**
     * Deletes the first person in {@code model}'s filtered list from {@code model}'s address book.
     */
    public static void deleteFirstInterview (Model model) {
        Interview firstInterview = model.getFilteredInterviewList().get(0);
        try {
            model.deleteInterview(firstInterview);
        } catch (InterviewNotFoundException pnfe) {
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
