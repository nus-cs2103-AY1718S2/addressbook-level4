# emer7
###### \java\guitests\guihandles\DetailPanelHandle.java
``` java
    public static final String BROWSER_PANEL_ID = "#detailPanel";
    private static final String NAME_FIELD_ID = "#name";
    private static final String ADDRESS_FIELD_ID = "#address";

    private final Label nameLabel;
    private final Label addressLabel;

    private Label oldNameLabel;
    private Label oldAddressLabel;
```
###### \java\guitests\guihandles\DetailPanelHandle.java
``` java
        this.nameLabel = getChildNode(NAME_FIELD_ID);
        this.addressLabel = getChildNode(ADDRESS_FIELD_ID);
```
###### \java\guitests\guihandles\DetailPanelHandle.java
``` java
    public String getName() {
        return nameLabel.getText();
    }

    public String getAddress() {
        return addressLabel.getText();
    }

    /**
     * Remember the current Person details
     */
    public void rememberPersonDetail() {
        oldNameLabel = nameLabel;
        oldAddressLabel = addressLabel;
    }

    public boolean isDetailChanged() {
        return !(oldNameLabel.getText().equals(getName())
                && oldAddressLabel.getText().equals(getAddress()));
    }

    public boolean isFieldsEmpty() {
        return nameLabel.getText().equals("")
                && addressLabel.getText().equals("");
    }
```
###### \java\guitests\guihandles\ReviewDialogHandle.java
``` java
package guitests.guihandles;

import static guitests.guihandles.ReviewInputHandle.REVIEW_INPUT_FIELD_ID;
import static guitests.guihandles.ReviewerInputHandle.REVIEWER_INPUT_FIELD_ID;
import static seedu.address.ui.ReviewDialog.REVIEW_DIALOG_PANE_FIELD_ID;

import guitests.GuiRobot;
import javafx.scene.control.DialogPane;
import javafx.stage.Stage;

/**
 * A handle to the {@code ReviewDialog} of the application.
 */
public class ReviewDialogHandle extends StageHandle {

    public static final String REVIEW_DIALOG_WINDOW_TITLE = "Review Dialog";

    private final DialogPane dialogPane;

    public ReviewDialogHandle(Stage reviewDialogStage) {
        super(reviewDialogStage);

        this.dialogPane = getChildNode("#" + REVIEW_DIALOG_PANE_FIELD_ID);
    }

    /**
     * Returns true if a review dialog is currently present in the application.
     */
    public static boolean isWindowPresent() {
        return new GuiRobot().isWindowShown(REVIEW_DIALOG_WINDOW_TITLE);
    }

    public void setReviewerInputTextField(String value) {
        ReviewerInputHandle reviewerInput = new ReviewerInputHandle(getChildNode("#" + REVIEWER_INPUT_FIELD_ID));
        reviewerInput.run(value);
    }

    public void setReviewInputTextField(String value) {
        ReviewInputHandle reviewInput = new ReviewInputHandle(getChildNode("#" + REVIEW_INPUT_FIELD_ID));
        reviewInput.run(value);
    }
}
```
###### \java\guitests\guihandles\ReviewerInputHandle.java
``` java

package guitests.guihandles;

import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

/**
 * A handle to the {@code ReviewDialog#reviewer} in the GUI.
 */
public class ReviewerInputHandle extends NodeHandle<TextField> {

    public static final String REVIEWER_INPUT_FIELD_ID = "reviewerInputTextField";

    public ReviewerInputHandle(TextField reviewerInputNode) {
        super(reviewerInputNode);
    }

    /**
     * Returns the text in the reviewer input box.
     */
    public String getInput() {
        return getRootNode().getText();
    }

    /**
     * Enters the given value in the text field and presses enter.
     */
    public void run(String value) {
        click();
        guiRobot.interact(() -> getRootNode().setText(value));
        guiRobot.pauseForHuman();

        guiRobot.type(KeyCode.ENTER);
    }
}
```
###### \java\guitests\guihandles\ReviewInputHandle.java
``` java

package guitests.guihandles;

import javafx.scene.control.TextArea;

/**
 * A handle to the {@code ReviewDialog#review} in the GUI.
 */
public class ReviewInputHandle extends NodeHandle<TextArea> {

    public static final String REVIEW_INPUT_FIELD_ID = "reviewInputTextField";

    public ReviewInputHandle(TextArea reviewInputNode) {
        super(reviewInputNode);
    }

    /**
     * Returns the text in the review input box.
     */
    public String getInput() {
        return getRootNode().getText();
    }

    /**
     * Enters the given value in the text area and presses enter.
     */
    public void run(String command) {
        click();
        guiRobot.interact(() -> getRootNode().setText(command));
        guiRobot.pauseForHuman();
    }
}
```
###### \java\seedu\address\logic\commands\FindCommandTest.java
``` java
        List<String> firstNamePredicateKeyphraseList = Collections.singletonList("first");
        List<String> secondNamePredicateKeyphraseList = Arrays.asList("first", "second");
        List<String> firstTagPredicateKeyphraseList = Collections.singletonList("first");
        List<String> secondTagPredicateKeyphraseList = Arrays.asList("first", "second");
        List<String> firstRatingPredicateKeyphraseList = Collections.singletonList("first");
        List<String> secondRatingPredicateKeyphraseList = Collections.singletonList("second");

        FieldContainKeyphrasesPredicate firstPredicate =
                new FieldContainKeyphrasesPredicate(
                        firstNamePredicateKeyphraseList,
                        firstTagPredicateKeyphraseList,
                        firstRatingPredicateKeyphraseList);
        FieldContainKeyphrasesPredicate secondPredicate =
                new FieldContainKeyphrasesPredicate(
                        secondNamePredicateKeyphraseList,
                        secondTagPredicateKeyphraseList,
                        secondRatingPredicateKeyphraseList);
```
###### \java\seedu\address\logic\commands\FindCommandTest.java
``` java
        FindCommand command =
                new FindCommand(
                        new FieldContainKeyphrasesPredicate(
                                Arrays.asList(nameInput.split("\\s+")),
                                Arrays.asList(tagInput.split("\\s+")),
                                Arrays.asList(ratingInput.split("\\s+"))));
```
###### \java\seedu\address\logic\commands\ReviewCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.address.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.HashSet;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.review.Review;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;

public class ReviewCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() throws Exception {
        Person reviewedPerson = new PersonBuilder().withEmail("alice@example.com").withRating("1")
                .withReviews("supervisor@example.com\nLazy")
                .build();
        EditCommand.EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(reviewedPerson)
                .withReviews("sales@example.com\nLazy").build();

        ReviewCommand reviewCommand = prepareCommand(INDEX_FIRST_PERSON, descriptor);

        HashSet<Review> newReviews = new HashSet<>();
        newReviews.add(new Review("supervisor@example.com\nLazy"));
        newReviews.add(new Review("sales@example.com\nLazy"));
        reviewedPerson.setReviews(newReviews);

        String expectedMessage = String.format(ReviewCommand.MESSAGE_REVIEW_PERSON_SUCCESS, reviewedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), reviewedPerson);

        assertCommandSuccess(reviewCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() throws Exception {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person reviewedPerson = new PersonBuilder(personInFilteredList)
                .withReviews("supervisor@example.com\nLazy")
                .build();
        ReviewCommand reviewCommand = prepareCommand(INDEX_FIRST_PERSON,
                new EditPersonDescriptorBuilder().withReviews("sales@example.com\nLazy").build());

        HashSet<Review> newReviews = new HashSet<>();
        newReviews.add(new Review("supervisor@example.com\nLazy"));
        newReviews.add(new Review("sales@example.com\nLazy"));
        reviewedPerson.setReviews(newReviews);

        String expectedMessage = String.format(ReviewCommand.MESSAGE_REVIEW_PERSON_SUCCESS,
                reviewedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), reviewedPerson);

        assertCommandSuccess(reviewCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        EditCommand.EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .build();
        ReviewCommand reviewCommand = prepareCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(reviewCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Review filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        ReviewCommand reviewCommand = prepareCommand(outOfBoundIndex,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(reviewCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Person reviewedPerson = new PersonBuilder()
                .withEmail("alice@example.com")
                .withRating("5")
                .withReviews("supervisor@example.com\nLazy")
                .build();
        Person personToReview = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditCommand.EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(reviewedPerson)
                .withReviews("sales@example.com\nLazy")
                .build();
        ReviewCommand reviewCommand = prepareCommand(INDEX_FIRST_PERSON, descriptor);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        // review -> first person reviewed
        reviewCommand.execute();
        undoRedoStack.push(reviewCommand);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first person reviewed again
        expectedModel.updatePerson(personToReview, reviewedPerson);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        EditCommand.EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .build();
        ReviewCommand reviewCommand = prepareCommand(outOfBoundIndex, descriptor);

        // execution failed -> reviewCommand not pushed into undoRedoStack
        assertCommandFailure(reviewCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Reviews a {@code Person} from a filtered list.
     * 2. Undo the review.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously reviewed person in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the review. This ensures {@code RedoCommand} reviews the person object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_samePersonReviewed() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Person reviewedPerson = new PersonBuilder()
                .withName("Benson Meier")
                .withPhone("98765432")
                .withEmail("johnd@example.com")
                .withAddress("311, Clementi Ave 2, #02-25")
                .withTags("owesMoney", "friends")
                .withReviews("supervisor@example.com\nLazy")
                .build();
        EditCommand.EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(reviewedPerson)
                .withReviews("sales@example.com\nLazy")
                .build();
        ReviewCommand reviewCommand = prepareCommand(INDEX_FIRST_PERSON, descriptor);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        showPersonAtIndex(model, INDEX_SECOND_PERSON);
        Person personToReview = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        // review -> reviews second person in unfiltered person list / first person in filtered person list
        reviewCommand.execute();
        undoRedoStack.push(reviewCommand);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        expectedModel.updatePerson(personToReview, reviewedPerson);
        assertNotEquals(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()), personToReview);
        // redo -> reviews same second person in unfiltered person list
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() throws Exception {
        final ReviewCommand standardCommand = prepareCommand(INDEX_FIRST_PERSON, DESC_AMY);

        // same values -> returns true
        EditCommand.EditPersonDescriptor copyDescriptor = new EditCommand.EditPersonDescriptor(DESC_AMY);
        ReviewCommand commandWithSameValues = prepareCommand(INDEX_FIRST_PERSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // one command preprocessed when previously equal -> returns false
        commandWithSameValues.preprocessUndoableCommand();
        assertFalse(standardCommand.equals(commandWithSameValues));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new ReviewCommand(INDEX_SECOND_PERSON, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new ReviewCommand(INDEX_FIRST_PERSON, DESC_BOB)));
    }

    /**
     * Returns an {@code ReviewCommand} with parameters {@code index} and {@code descriptor}
     */
    private ReviewCommand prepareCommand(Index index, EditCommand.EditPersonDescriptor descriptor) {
        ReviewCommand reviewCommand = new ReviewCommand(index, descriptor);
        reviewCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return reviewCommand;
    }
}
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
                        + " t/"
                        + tagKeyphrases.stream().collect(Collectors.joining(" t/"))
                        + " r/"
                        + ratingKeyphrases.stream().collect(Collectors.joining(" r/")));
        assertEquals(new FindCommand(
                new FieldContainKeyphrasesPredicate(
                        nameKeyphrases, tagKeyphrases, ratingKeyphrases)),
                        command);
```
###### \java\seedu\address\logic\parser\ReviewCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

import seedu.address.logic.commands.ReviewCommand;

public class ReviewCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReviewCommand.MESSAGE_USAGE);

    private ReviewCommandParser parser = new ReviewCommandParser();

    @Test
    public void parse_indexInvalid_failure() {
        assertParseFailure(parser, "a", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_indexZeroOrLess_failure() {
        assertParseFailure(parser, "0", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "-1", MESSAGE_INVALID_FORMAT);
    }
}
```
###### \java\seedu\address\model\person\FieldContainKeyphrasesPredicateTest.java
``` java
package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class FieldContainKeyphrasesPredicateTest {

    @Test
    public void equals() {
        List<String> firstNamePredicateKeyphraseList = Collections.singletonList("first");
        List<String> secondNamePredicateKeyphraseList = Arrays.asList("first", "second");
        List<String> firstTagPredicateKeyphraseList = Collections.singletonList("first");
        List<String> secondTagPredicateKeyphraseList = Arrays.asList("first", "second");
        List<String> firstRatingPredicateKeyphraseList = Collections.singletonList("first");
        List<String> secondRatingPredicateKeyphraseList = Collections.singletonList("second");

        FieldContainKeyphrasesPredicate firstPredicate =
                new FieldContainKeyphrasesPredicate(
                        firstNamePredicateKeyphraseList,
                        firstTagPredicateKeyphraseList,
                        firstRatingPredicateKeyphraseList);
        FieldContainKeyphrasesPredicate secondPredicate =
                new FieldContainKeyphrasesPredicate(
                        secondNamePredicateKeyphraseList,
                        secondTagPredicateKeyphraseList,
                        secondRatingPredicateKeyphraseList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        FieldContainKeyphrasesPredicate firstPredicateCopy = new FieldContainKeyphrasesPredicate(
                firstNamePredicateKeyphraseList, firstTagPredicateKeyphraseList, firstRatingPredicateKeyphraseList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_containKeyphrases_returnsTrue() {
        // All zero keyphrase
        FieldContainKeyphrasesPredicate predicate = new FieldContainKeyphrasesPredicate(
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList());
        assertTrue(predicate.test(new PersonBuilder()
                .withName("Alice Bob")
                .withTags("Friends", "Family")
                .withRating("3")
                .build()));

        // All one keyphrase
        predicate = new FieldContainKeyphrasesPredicate(
                Collections.singletonList("Alice"),
                Collections.singletonList("Friends"),
                Collections.singletonList("3"));
        assertTrue(predicate.test(new PersonBuilder()
                .withName("Alice Bob")
                .withTags("Friends", "Family")
                .withRating("3")
                .build()));

        // Name one keyphrase
        predicate = new FieldContainKeyphrasesPredicate(
                Collections.singletonList("Alice Bob"),
                Collections.emptyList(),
                Collections.emptyList());
        assertTrue(predicate.test(new PersonBuilder()
                .withName("Alice Bob")
                .withTags("Friends", "Family")
                .withRating("3")
                .build()));

        // Tag one keyphrase
        predicate = new FieldContainKeyphrasesPredicate(
                Collections.emptyList(),
                Collections.singletonList("Friends Family"),
                Collections.emptyList());
        assertTrue(predicate.test(new PersonBuilder()
                .withName("Alice Bob")
                .withTags("Friends", "Family")
                .withRating("3")
                .build()));

        // Rating one keyphrase
        predicate = new FieldContainKeyphrasesPredicate(
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.singletonList("3"));
        assertTrue(predicate.test(new PersonBuilder()
                .withName("Alice Bob")
                .withTags("Friends", "Family")
                .withRating("3")
                .build()));

        // Both name and tag multiple keyphrases, but only one matches
        predicate = new FieldContainKeyphrasesPredicate(
                Arrays.asList("Alice", "Carol"),
                Arrays.asList("Friends", "Enemy"),
                Collections.emptyList());
        assertTrue(predicate.test(new PersonBuilder()
                .withName("Alice Bob")
                .withTags("Friends", "Family")
                .withRating("3")
                .build()));

        // Both name and tag mixed-case keyphrase
        predicate = new FieldContainKeyphrasesPredicate(
                Collections.singletonList("aLIce"),
                Collections.singletonList("fRIeNDs"),
                Collections.singletonList("3"));
        assertTrue(predicate.test(new PersonBuilder()
                .withName("Alice Bob")
                .withTags("Friends", "Family")
                .withRating("3")
                .build()));
    }

    @Test
    public void test_notContainKeyphrases_returnsFalse() {
        // Non-matching keyphrase
        FieldContainKeyphrasesPredicate predicate = new FieldContainKeyphrasesPredicate(
                Collections.singletonList("Carol"),
                Collections.singletonList("Enemy"),
                Collections.singletonList("3"));
        assertFalse(predicate.test(new PersonBuilder()
                .withName("Alice Bob")
                .withTags("Friends")
                .withRating("3")
                .build()));

        // Keyphrase match phone, email, address, and tags, but does not match name
        predicate = new FieldContainKeyphrasesPredicate(
                Arrays.asList("12345 alice@email.com Main Street"),
                Collections.singletonList("Friends"),
                Collections.singletonList("3"));
        assertFalse(predicate.test(new PersonBuilder()
                .withName("Alice")
                .withPhone("12345")
                .withEmail("alice@email.com")
                .withAddress("Main Street")
                .withTags("Friends")
                .withRating("3")
                .build()));

        // Keyphrase match name, phone, email, and address, but does not match tags
        predicate = new FieldContainKeyphrasesPredicate(
                Collections.singletonList("Alice"),
                Arrays.asList("12345 alice@email.com Main Street"),
                Collections.singletonList("3"));
        assertFalse(predicate.test(new PersonBuilder()
                .withName("Alice")
                .withPhone("12345")
                .withEmail("alice@email.com")
                .withAddress("Main Street")
                .withTags("Friends")
                .withRating("3")
                .build()));

        // Keyphrase match name, phone, email, address, and tags, but does not match rating
        predicate = new FieldContainKeyphrasesPredicate(
                Collections.singletonList("Alice"),
                Collections.singletonList("Friends"),
                Arrays.asList("12345 alice@email.com Main Street"));
        assertFalse(predicate.test(new PersonBuilder()
                .withName("Alice")
                .withPhone("12345")
                .withEmail("alice@email.com")
                .withAddress("Main Street")
                .withTags("Friends")
                .withRating("3")
                .build()));
    }
}
```
###### \java\seedu\address\model\person\RatingContainsKeyphrasesPredicateTest.java
``` java
package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class RatingContainsKeyphrasesPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeyphraseList = Collections.singletonList("first");
        List<String> secondPredicateKeyphraseList = Arrays.asList("first", "second");

        RatingContainsKeyphrasesPredicate firstPredicate = new RatingContainsKeyphrasesPredicate(
                firstPredicateKeyphraseList);
        RatingContainsKeyphrasesPredicate secondPredicate = new RatingContainsKeyphrasesPredicate(
                secondPredicateKeyphraseList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        RatingContainsKeyphrasesPredicate firstPredicateCopy = new RatingContainsKeyphrasesPredicate(
                firstPredicateKeyphraseList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_ratingContainsKeyphrases_returnsTrue() {
        // Zero keyphrase
        RatingContainsKeyphrasesPredicate predicate = new RatingContainsKeyphrasesPredicate(Collections.emptyList());
        assertTrue(predicate.test(new PersonBuilder().withRating("2").build()));

        // One keyphrase
        predicate = new RatingContainsKeyphrasesPredicate(Collections.singletonList("3"));
        assertTrue(predicate.test(new PersonBuilder().withRating("3").build()));

        // Multiple keyphrases
        predicate = new RatingContainsKeyphrasesPredicate(Arrays.asList("3", "4"));
        assertTrue(predicate.test(new PersonBuilder().withRating("4").build()));
    }

    @Test
    public void test_ratingDoesNotContainKeyphrases_returnsFalse() {
        // Non-matching keyphrase
        RatingContainsKeyphrasesPredicate predicate = new RatingContainsKeyphrasesPredicate(
                Collections.singletonList("2"));
        assertFalse(predicate.test(new PersonBuilder().withRating("1").build()));

        // Keyphrase match name, phone, email, address, and tags, but does not match rating
        predicate = new RatingContainsKeyphrasesPredicate(
                Arrays.asList("Alice 12345 alice@email.com Main Street Friends"));
        assertFalse(predicate.test(new PersonBuilder()
                .withName("Alice")
                .withPhone("12345")
                .withEmail("alice@email.com")
                .withAddress("Main Street")
                .withTags("Friends")
                .withRating("3")
                .build()));
    }
}
```
###### \java\seedu\address\model\person\TagContainsKeyphrasesPredicateTest.java
``` java
package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class TagContainsKeyphrasesPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeyphraseList = Collections.singletonList("first");
        List<String> secondPredicateKeyphraseList = Arrays.asList("first", "second");

        TagContainsKeyphrasesPredicate firstPredicate = new TagContainsKeyphrasesPredicate(
                firstPredicateKeyphraseList);
        TagContainsKeyphrasesPredicate secondPredicate = new TagContainsKeyphrasesPredicate(
                secondPredicateKeyphraseList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TagContainsKeyphrasesPredicate firstPredicateCopy = new TagContainsKeyphrasesPredicate(
                firstPredicateKeyphraseList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_tagContainsKeyphrases_returnsTrue() {
        // Zero keyphrase
        TagContainsKeyphrasesPredicate predicate = new TagContainsKeyphrasesPredicate(Collections.emptyList());
        assertTrue(predicate.test(new PersonBuilder().withTags("Friends").build()));

        // One keyphrase
        predicate = new TagContainsKeyphrasesPredicate(Collections.singletonList("Friends"));
        assertTrue(predicate.test(new PersonBuilder().withTags("Friends", "Family").build()));
        predicate = new TagContainsKeyphrasesPredicate(Collections.singletonList("Friends Family"));
        assertTrue(predicate.test(new PersonBuilder().withTags("Friends", "Family").build()));

        // Multiple keyphrases
        predicate = new TagContainsKeyphrasesPredicate(Arrays.asList("Friends", "Colleagues"));
        assertTrue(predicate.test(new PersonBuilder().withTags("Friends").build()));

        // Mixed-case keyphrase
        predicate = new TagContainsKeyphrasesPredicate(Arrays.asList("fRiends"));
        assertTrue(predicate.test(new PersonBuilder().withTags("Friends").build()));
    }

    @Test
    public void test_tagDoesNotContainKeyphrases_returnsFalse() {
        // Non-matching keyphrase
        TagContainsKeyphrasesPredicate predicate = new TagContainsKeyphrasesPredicate(
                Collections.singletonList("Friends"));
        assertFalse(predicate.test(new PersonBuilder().withTags("Enemy", "Nemesis").build()));

        // Keyphrase match name, phone, email, and address, but does not match tag
        predicate = new TagContainsKeyphrasesPredicate(
                Arrays.asList("Alice 12345 alice@email.com Main Street 3"));
        assertFalse(predicate.test(new PersonBuilder()
                .withName("Alice")
                .withPhone("12345")
                .withEmail("alice@email.com")
                .withAddress("Main Street")
                .withTags("Friends")
                .withRating("3")
                .build()));
    }
}
```
###### \java\seedu\address\testutil\EditPersonDescriptorBuilder.java
``` java
    /**
     * Parses the {@code reviews} into a {@code Set<Review>} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public EditPersonDescriptorBuilder withReviews(String... reviews) {
        Set<Review> reviewSet = Stream.of(reviews).map(Review::new).collect(Collectors.toSet());
        descriptor.setReviews(reviewSet);
        return this;
    }
```
###### \java\seedu\address\testutil\PersonBuilder.java
``` java
    /**
     * Sets the {@code Review} of the {@code Person} that we are building.
     */
    public PersonBuilder withReviews(String ... reviews) {
        this.reviews = SampleDataUtil.getReviewSet(reviews);
        return this;
    }

    /**
     * Build a person with the determined details
     * @return person to be built
     */
    public Person build() {
        Person toReturn = new Person(name, phone, email, address, tags, calendarId);
        toReturn.setRating(rating);
        toReturn.setReviews(reviews);
        return toReturn;
```
###### \java\seedu\address\ui\DetailPanelTest.java
``` java
        detailPanelHandle = new DetailPanelHandle(detailPanel.getRoot());
        assertPanelDisplay(new PersonCardHandle(selectionChangedPersonCardStub.getRoot()), detailPanelHandle);
    }

    /**
     * Asserts that {@code personCard} displays the details of {@code expectedPerson} correctly and matches
     * {@code expectedId}.
     */
    private void assertPanelDisplay(PersonCardHandle expectedPersonCard, DetailPanelHandle detailPanelHandle) {
        guiRobot.pauseForHuman();
        assertPanelDisplaysPerson(expectedPersonCard, detailPanelHandle);
    }
```
###### \java\seedu\address\ui\testutil\GuiTestAssert.java
``` java
    /**
     * Asserts that {@code actualPanel} displays the details of {@code expectedPerson}.
     */
    public static void assertPanelDisplaysPerson(PersonCardHandle expectedPersonCard, DetailPanelHandle actualPanel) {
        assertEquals(expectedPersonCard.getName(), actualPanel.getName());
    }
```
###### \java\systemtests\AddressBookSystemTest.java
``` java
    /**
     * Asserts that detailPanel is empty and the previously selected card is now deselected.
     * @see DetailPanelHandle#isFieldsEmpty()
     * @see PersonListPanelHandle#isAnyCardSelected()
     */
    protected void assertSelectedCardDeselectedDetailEmpty() {
        assertTrue(getDetailPanel().isFieldsEmpty());
        assertFalse(getPersonListPanel().isAnyCardSelected());
    }

```
###### \java\systemtests\EditCommandSystemTest.java
``` java
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
```
###### \java\systemtests\ReviewCommandSystemTest.java
``` java
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
```
###### \java\systemtests\SampleDataTest.java
``` java
        Person[] expectedList = new Person[0];
        UnlockCommand testUnlockCommand = new UnlockCommand();
        testUnlockCommand.setTestMode(true);
        testUnlockCommand.setData(getModel(), new CommandHistory(), new UndoRedoStack());
        testUnlockCommand.execute();
        showAllPersons();
```
