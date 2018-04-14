package seedu.progresschecker.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.progresschecker.logic.parser.CliSyntax.PREFIX_ASSIGNEES;
import static seedu.progresschecker.logic.parser.CliSyntax.PREFIX_BODY;
import static seedu.progresschecker.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.progresschecker.logic.parser.CliSyntax.PREFIX_GIT_PASSCODE;
import static seedu.progresschecker.logic.parser.CliSyntax.PREFIX_GIT_REPO;
import static seedu.progresschecker.logic.parser.CliSyntax.PREFIX_GIT_USERNAME;
import static seedu.progresschecker.logic.parser.CliSyntax.PREFIX_LABEL;
import static seedu.progresschecker.logic.parser.CliSyntax.PREFIX_MAJOR;
import static seedu.progresschecker.logic.parser.CliSyntax.PREFIX_MILESTONE;
import static seedu.progresschecker.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.progresschecker.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.progresschecker.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.progresschecker.logic.parser.CliSyntax.PREFIX_TITLE;
import static seedu.progresschecker.logic.parser.CliSyntax.PREFIX_USERNAME;
import static seedu.progresschecker.logic.parser.CliSyntax.PREFIX_YEAR;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.progresschecker.commons.core.index.Index;
import seedu.progresschecker.logic.CommandHistory;
import seedu.progresschecker.logic.UndoRedoStack;
import seedu.progresschecker.logic.commands.exceptions.CommandException;
import seedu.progresschecker.model.Model;
import seedu.progresschecker.model.ProgressChecker;
import seedu.progresschecker.model.person.NameContainsKeywordsPredicate;
import seedu.progresschecker.model.person.Person;
import seedu.progresschecker.model.person.exceptions.PersonNotFoundException;
import seedu.progresschecker.testutil.EditPersonDescriptorBuilder;

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
    public static final String VALID_USERNAME_AMY = "AmyBeeGithub";
    public static final String VALID_USERNAME_BOB = "BobChooGithub";
    public static final String VALID_MAJOR_AMY = "Computer Science";
    public static final String VALID_MAJOR_BOB = "Computer Engineering";
    public static final String VALID_YEAR_AMY = "2";
    public static final String VALID_YEAR_BOB = "3";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
    public static final String VALID_PATH_AMY = "/images/contact/amy.jpg";
    public static final String VALID_PATH_BOB = "/images/contact/bob.jpg";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String USERNAME_DESC_AMY = " " + PREFIX_USERNAME + VALID_USERNAME_AMY;
    public static final String USERNAME_DESC_BOB = " " + PREFIX_USERNAME + VALID_USERNAME_BOB;
    public static final String MAJOR_DESC_AMY = " " + PREFIX_MAJOR + VALID_MAJOR_AMY;
    public static final String MAJOR_DESC_BOB = " " + PREFIX_MAJOR + VALID_MAJOR_BOB;
    public static final String YEAR_DESC_AMY = " " + PREFIX_YEAR + VALID_YEAR_AMY;
    public static final String YEAR_DESC_BOB = " " + PREFIX_YEAR + VALID_YEAR_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_USERNAME_DESC = " "
            + PREFIX_USERNAME + "James&Github"; // '&' not allowed in names
    public static final String INVALID_MAJOR_DESC = " " + PREFIX_MAJOR; // empty string not allowed for majors
    public static final String INVALID_YEAR_DESC = " " + PREFIX_YEAR + "9"; // year of study cannot exceed 5
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags
    public static final String INVALID_PATH_DESC = "/images/ "; // prefix of path is not completed

    public static final String VALID_TITLE_ONE = "This is a github issue one";
    public static final String VALID_TITLE_TWO = "This is a github issue two";
    public static final String VALID_ASSIGNEE_BOB = "bob";
    public static final String VALID_ASSIGNEE_AMY = "amy";
    public static final String VALID_ASSIGNEE_ANMIN = "anminkang";
    public static final String VALID_BODY_ONE = "This is issue one's body";
    public static final String VALID_BODY_TWO = "This is issue two's body";
    public static final String VALID_MILESTONE_ONE = "v1.2";
    public static final String VALID_MILESTONE_TWO = "v1.3";
    public static final String VALID_LABEL_TASK = "type.task";
    public static final String VALID_LABEL_STORY = "type.story";

    public static final String TITLE_DESC_ONE = " " + PREFIX_TITLE + VALID_TITLE_ONE;
    public static final String TITLE_DESC_TWO = " " + PREFIX_TITLE + VALID_TITLE_TWO;
    public static final String BODY_DESC_ONE = " " + PREFIX_BODY + VALID_BODY_ONE;
    public static final String BODY_DESC_TWO = " " + PREFIX_BODY + VALID_BODY_TWO;
    public static final String ASSIGNEE_DESC_BOB = " " + PREFIX_ASSIGNEES + VALID_ASSIGNEE_BOB;
    public static final String ASSIGNEE_DESC_AMY = " " + PREFIX_ASSIGNEES + VALID_ASSIGNEE_AMY;
    public static final String ASSIGNEE_DESC_ANMIN = " " + PREFIX_ASSIGNEES + VALID_ASSIGNEE_ANMIN;
    public static final String MILESTONE_DESC_ONE = " " + PREFIX_MILESTONE + VALID_MILESTONE_ONE;
    public static final String MILESTONE_DESC_TWO = " " + PREFIX_MILESTONE + VALID_MILESTONE_TWO;
    public static final String LABEL_DEC_TASK = " " + PREFIX_LABEL + VALID_LABEL_TASK;
    public static final String LABEL_DEC_STORY = " " + PREFIX_LABEL + VALID_LABEL_STORY;

    public static final String INVALID_TITLE_DESC = " " + PREFIX_TITLE + "";
    public static final String INVALID_BODY_DESC = " " + PREFIX_BODY + "";
    public static final String INVALID_LABEL_DESC = " " + PREFIX_LABEL + "";
    public static final String INVALID_ASSIGNEE_DESC = " " + PREFIX_ASSIGNEES + "";

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final String VALID_GITHUB_REPO_ONE = "adityaa1998/addressbook-level4";
    public static final String VALID_GITHUB_REPO_TWO = "adityaa1998/samplerepo-pr-practice";
    public static final String VALID_GITHUB_USERNAME_ONE = "anminkang";
    public static final String VALID_GITHUB_USERNAME_TWO = "adityaa1998";
    public static final String VALID_GITHUB_PASSCODE_ONE = "Github1";
    public static final String VALID_GITHUB_PASSCODE_TWO = "Github2";

    public static final String GITHUB_DESC_REPO_ONE = " " + PREFIX_GIT_REPO + VALID_GITHUB_REPO_ONE;
    public static final String GITHUB_DESC_REPO_TWO = " " + PREFIX_GIT_REPO + VALID_GITHUB_REPO_TWO;
    public static final String GITHUB_DESC_USERNAME_ONE = " " + PREFIX_GIT_USERNAME + VALID_GITHUB_USERNAME_ONE;
    public static final String GITHUB_DESC_USERNAME_TWO = " " + PREFIX_GIT_USERNAME + VALID_GITHUB_USERNAME_TWO;
    public static final String GITHUB_DESC_PASSCODE_ONE = " " + PREFIX_GIT_PASSCODE + VALID_GITHUB_PASSCODE_ONE;
    public static final String GITHUB_DESC_PASSCODE_TWO = " " + PREFIX_GIT_PASSCODE + VALID_GITHUB_PASSCODE_TWO;

    public static final String INVALID_GITHUB_REPO_DESC = " " + PREFIX_GIT_REPO + "";
    public static final String INVALID_GITHUB_USERNAME_DESC = " " + PREFIX_GIT_USERNAME + "";
    public static final String INVALID_GITHUB_PASSCODE_DESC = " " + PREFIX_GIT_PASSCODE + "";

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
                .withUsername(VALID_USERNAME_AMY).withMajor(VALID_MAJOR_AMY)
                .withYear(VALID_YEAR_AMY).withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withUsername(VALID_USERNAME_BOB).withMajor(VALID_MAJOR_BOB)
                .withYear(VALID_YEAR_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
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
     * - the ProgressChecker and the filtered person list in the {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        ProgressChecker expectedProgressChecker = new ProgressChecker(actualModel.getProgressChecker());
        List<Person> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPersonList());

        try {
            command.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedProgressChecker, actualModel.getProgressChecker());
            assertEquals(expectedFilteredList, actualModel.getFilteredPersonList());
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s ProgressChecker.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPersonList().size());

        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPersonList().size());
    }

    /**
     * Deletes the first person in {@code model}'s filtered list from {@code model}'s ProgressChecker.
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
