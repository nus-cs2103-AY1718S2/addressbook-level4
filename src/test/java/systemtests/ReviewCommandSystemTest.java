//@@author emer7
package systemtests;

import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REVIEW_LAZY;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.KEYPHRASE_MATCHING_MEIER;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import guitests.GuiRobot;
import guitests.guihandles.ReviewDialogHandle;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.ReviewCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.UnlockCommand;
import seedu.address.model.Model;
import seedu.address.model.person.Email;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.review.Review;
import seedu.address.testutil.PersonBuilder;

public class ReviewCommandSystemTest extends AddressBookSystemTest {

    private static final String ERROR_MESSAGE = "ATTENTION!!!! : On some computers, this test may fail when run on "
            + "non-headless mode as FxRobot#clickOn(Node, MouseButton...) clicks on the wrong location. We suspect "
            + "that this is a bug with TestFX library that we are using. If this test fails, you have to run your "
            + "tests on headless mode. See UsingGradle.adoc on how to do so.";

    private final GuiRobot guiRobot = new GuiRobot();

    @Test
    public void openReviewDialog() {
        UnlockCommand testUnlockCommand = new UnlockCommand();
        testUnlockCommand.setTestMode(true);
        testUnlockCommand.setData(getModel(), new CommandHistory(), new UndoRedoStack());
        testUnlockCommand.execute();
        showAllPersons();

        Index index = INDEX_FIRST_PERSON;
        String command = "     " + ReviewCommand.COMMAND_WORD + "      " + index.getOneBased() + "       ";
        executeCommand(command);
        assertReviewDialogOpen();
    }

    @Test
    public void review() throws Exception {
        Model model = getModel();
        UnlockCommand testUnlockCommand = new UnlockCommand();
        testUnlockCommand.setTestMode(true);
        testUnlockCommand.setData(getModel(), new CommandHistory(), new UndoRedoStack());
        testUnlockCommand.execute();
        showAllPersons();

        /* Case: review once with different reviewer -> new review*/
        Index index = INDEX_FIRST_PERSON;
        String command = "     " + ReviewCommand.COMMAND_WORD + " " + index.getOneBased();
        String reviewer = "test@example.com";
        String review = "hardworker";
        Person editedPerson = new PersonBuilder(ALICE).build();
        Set<Review> newReview = new HashSet<>();
        newReview.add(editedPerson.getReviews().iterator().next());
        newReview.add(new Review(reviewer + "\n" + review));
        editedPerson.setReviews(newReview);
        assertCommandSuccess(command, index, editedPerson, reviewer, review);

        /* Case: review once with same reviewer -> replace old review*/
        reviewer = "test@example.com";
        review = "lazy";
        editedPerson = new PersonBuilder(ALICE).build();
        newReview = new HashSet<>();
        newReview.add(editedPerson.getReviews().iterator().next());
        newReview.add(new Review(reviewer + "\n" + review));
        editedPerson.setReviews(newReview);
        assertCommandSuccess(command, index, editedPerson, reviewer, review);

        /* Case: review once with empty review -> null review*/
        reviewer = "test@example.com";
        review = "";
        editedPerson = new PersonBuilder(ALICE).build();
        newReview = new HashSet<>();
        newReview.add(editedPerson.getReviews().iterator().next());
        newReview.add(new Review(reviewer + "\n" + "-"));
        editedPerson.setReviews(newReview);
        assertCommandSuccess(command, index, editedPerson, reviewer, review);

        /* Case: undo reviewing the last person in the list -> last person restored */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage, "undo");

        /* Case: redo reviewing the last person in the list -> last person reviewed again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        model.updatePerson(
                getModel().getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()), editedPerson);
        assertCommandSuccess(command, model, expectedResultMessage, "redo");

        /* ------------------ Performing review operation while a filtered list is being shown ---------------------- */

        /* Case: filtered person list, review index within bounds of address book and person list -> edited */
        showPersonsWithName(KEYPHRASE_MATCHING_MEIER);
        index = INDEX_FIRST_PERSON;
        assertTrue(index.getZeroBased() < getModel().getFilteredPersonList().size());
        command = ReviewCommand.COMMAND_WORD + " " + index.getOneBased();
        reviewer = "test@example.com";
        review = "hardwork";
        editedPerson = getModel().getFilteredPersonList().get(index.getZeroBased());
        newReview = new HashSet<>();
        newReview.add(editedPerson.getReviews().iterator().next());
        newReview.add(new Review(reviewer + "\n" + review));
        editedPerson.setReviews(newReview);
        assertCommandSuccess(command, index, editedPerson, reviewer, review);

        /* -------------------- Performing review operation while a person card is selected ------------------------- */

        /* Case: selects first card in the person list, review a person -> reviewed, card selection remains unchanged
         * but detail panel changes
         */
        showAllPersons();
        index = INDEX_FIRST_PERSON;
        selectPerson(index);
        command = ReviewCommand.COMMAND_WORD + " " + index.getOneBased();
        reviewer = "test@example.com";
        review = "lazy";
        editedPerson = new PersonBuilder(ALICE).build();
        newReview = new HashSet<>();
        newReview.add(editedPerson.getReviews().iterator().next());
        newReview.add(new Review(reviewer + "\n" + review));
        editedPerson.setReviews(newReview);
        // this can be misleading: card selection actually remains unchanged but the
        // detail panel is updated to reflect the new person's name
        assertCommandSuccess(command, index, editedPerson, index, reviewer, review);

        /* -------------------------------- Performing invalid review operation ------------------------------------- */

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(ReviewCommand.COMMAND_WORD + " 0",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ReviewCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(ReviewCommand.COMMAND_WORD + " -1",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ReviewCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected but will still display the pop up dialog first */

        /* Case: missing index -> rejected */
        assertCommandFailure(ReviewCommand.COMMAND_WORD,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ReviewCommand.MESSAGE_USAGE));

        /* Case: invalid reviewer -> rejected */
        assertCommandFailure(ReviewCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased(),
                Email.MESSAGE_EMAIL_CONSTRAINTS, INVALID_EMAIL_DESC, VALID_REVIEW_LAZY);
    }

    /**
     * Asserts that the review dialog is open, and closes it after checking.
     */
    private void assertReviewDialogOpen() {
        assertTrue(ERROR_MESSAGE, ReviewDialogHandle.isWindowPresent());
        guiRobot.pauseForHuman();

        ReviewDialogHandle reviewDialog =
                new ReviewDialogHandle(guiRobot.getStage(ReviewDialogHandle.REVIEW_DIALOG_WINDOW_TITLE));
        reviewDialog.close();
        getMainWindowHandle().focus();
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Index, Person, Index)} except that
     * the detail panel and selected card remain unchanged.
     * @param toEdit the index of the current model's filtered list
     * @see ReviewCommandSystemTest#assertCommandSuccess(String, Index, Person, Index, String, String)
     */
    private void assertCommandSuccess(String command, Index toEdit, Person editedPerson,
                                      String reviewer, String review) {
        assertCommandSuccess(command, toEdit, editedPerson, toEdit, reviewer, review);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} and in addition,<br>
     * 1. Asserts that result display box displays the success message of executing {@code ReviewCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the person at index {@code toEdit} being
     * updated to values specified {@code editedPerson}.<br>
     * @param toEdit the index of the current model's filtered list.
     * @see ReviewCommandSystemTest#assertCommandSuccess(String, Model, String, Index, String, String)
     */
    private void assertCommandSuccess(String command, Index toEdit, Person editedPerson,
                                      Index expectedSelectedCardIndex,
                                      String reviewer, String review) {
        Model expectedModel = getModel();
        try {
            expectedModel.updatePerson(
                    expectedModel.getFilteredPersonList().get(toEdit.getZeroBased()), editedPerson);
            expectedModel.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        } catch (DuplicatePersonException | PersonNotFoundException e) {
            throw new IllegalArgumentException(
                    "editedPerson is a duplicate in expectedModel, or it isn't found in the model.");
        }

        assertCommandSuccess(command, expectedModel,
                String.format(ReviewCommand.MESSAGE_REVIEW_PERSON_SUCCESS, editedPerson), expectedSelectedCardIndex,
                reviewer, review);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to {@code expectedModel}.<br>
     * 4. Asserts that the detail panel and selected card update accordingly depending on the card.<br>
     * 5. Asserts that the status bar's sync status changes.<br>
     * 6. Asserts that the command box has the default style class.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see AddressBookSystemTest#assertSelectedCardChanged(Index)
     * @see AddressBookSystemTest#assertSelectedCardDeselectedDetailEmpty()
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
                                      String undoOrRedo) {
        executeCommand(command);
        expectedModel.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxAndResultDisplayShowsDefaultStyle();
        if (undoOrRedo.equals("redo")) {
            assertSelectedCardChanged(null);
        } else {
            assertSelectedCardDeselectedDetailEmpty();
        }
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to {@code expectedModel}.<br>
     * 4. Asserts that the detail panel and selected card update accordingly depending on the card at
     * {@code expectedSelectedCardIndex}.<br>
     * 5. Asserts that the status bar's sync status changes.<br>
     * 6. Asserts that the command box has the default style class.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see AddressBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
                                      Index expectedSelectedCardIndex, String reviewer, String review) {
        executeCommand(command);

        ReviewDialogHandle reviewDialog =
                new ReviewDialogHandle(guiRobot.getStage(ReviewDialogHandle.REVIEW_DIALOG_WINDOW_TITLE));
        reviewDialog.focus();
        reviewDialog.setReviewInputTextField(review);
        reviewDialog.setReviewerInputTextField(reviewer);

        expectedModel.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxAndResultDisplayShowsDefaultStyle();
        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to the current model.<br>
     * 4. Asserts that the detail panel, selected card and status bar remain unchanged.<br>
     * 5. Asserts that the command box has the error style.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage, String... review) {
        Model expectedModel = getModel();

        executeCommand(command);

        if (review.length > 0) {
            ReviewDialogHandle reviewDialog =
                    new ReviewDialogHandle(guiRobot.getStage(ReviewDialogHandle.REVIEW_DIALOG_WINDOW_TITLE));
            reviewDialog.focus();
            reviewDialog.setReviewInputTextField(review[1]);
            reviewDialog.setReviewerInputTextField(review[0]);
        }

        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxAndResultDisplayShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
