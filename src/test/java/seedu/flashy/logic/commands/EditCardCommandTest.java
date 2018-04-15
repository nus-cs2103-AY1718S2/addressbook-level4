package seedu.flashy.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.flashy.logic.commands.CommandTestUtil.CS2101_CARD;
import static seedu.flashy.logic.commands.CommandTestUtil.CS2103T_CARD;
import static seedu.flashy.logic.commands.CommandTestUtil.VALID_NAME_COMSCI;
import static seedu.flashy.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.flashy.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.flashy.logic.commands.CommandTestUtil.assertEqualCardId;
import static seedu.flashy.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.flashy.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.flashy.model.cardtag.CardTag.MESSAGE_CARD_NO_TAG;
import static seedu.flashy.model.tag.Tag.MESSAGE_TAG_NOT_FOUND;
import static seedu.flashy.testutil.TypicalCardBank.getTypicalCardBank;
import static seedu.flashy.testutil.TypicalCardBank.getTypicalCardBankFillBlanksCards;
import static seedu.flashy.testutil.TypicalCardBank.getTypicalCardBankMcqCards;
import static seedu.flashy.testutil.TypicalIndexes.INDEX_FIRST_CARD;
import static seedu.flashy.testutil.TypicalIndexes.INDEX_SECOND_CARD;
import static seedu.flashy.testutil.TypicalTags.COMSCI_TAG;
import static seedu.flashy.testutil.TypicalTags.ENGLISH_TAG;
import static seedu.flashy.testutil.TypicalTags.MATHEMATICS_TAG;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.Test;

import seedu.flashy.commons.core.Messages;
import seedu.flashy.commons.core.index.Index;
import seedu.flashy.logic.CommandHistory;
import seedu.flashy.logic.UndoRedoStack;
import seedu.flashy.logic.commands.EditCardCommand.EditCardDescriptor;
import seedu.flashy.model.CardBank;
import seedu.flashy.model.Model;
import seedu.flashy.model.ModelManager;
import seedu.flashy.model.UserPrefs;
import seedu.flashy.model.card.Card;
import seedu.flashy.model.card.FillBlanksCard;
import seedu.flashy.model.card.McqCard;
import seedu.flashy.model.tag.Name;
import seedu.flashy.model.tag.Tag;
import seedu.flashy.testutil.CardBuilder;
import seedu.flashy.testutil.EditCardDescriptorBuilder;
import seedu.flashy.testutil.FillBlanksCardBuilder;
import seedu.flashy.testutil.McqCardBuilder;

/**ed
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and
 * unit tests for EditCardCommand.
 */
public class EditCardCommandTest {

    private Model model = new ModelManager(getTypicalCardBank(), new UserPrefs());
    private Model modelWithMcqCards = new ModelManager(getTypicalCardBankMcqCards(), new UserPrefs());
    private Model modelWithFillBlanksCards = new ModelManager(getTypicalCardBankFillBlanksCards(), new UserPrefs());

    //@@author shawnclq
    @Test
    public void execute_frontBackSpecifiedUnfilteredList_success() throws Exception {
        Card editedCard = new CardBuilder().build();
        Card targetCard = model.getFilteredCardList().get(INDEX_FIRST_CARD.getZeroBased());
        EditCardDescriptor descriptor = new EditCardDescriptorBuilder(editedCard).build();
        EditCardCommand editCommand = prepareCommand(INDEX_FIRST_CARD, descriptor);

        String expectedMessage = String.format(EditCardCommand.MESSAGE_EDIT_CARD_SUCCESS, editedCard);

        Model expectedModel = new ModelManager(new CardBank(model.getCardBank()), new UserPrefs());
        expectedModel.updateCard(model.getFilteredCardList().get(0), editedCard);
        editedCard = model.getFilteredCardList().get(INDEX_FIRST_CARD.getZeroBased());

        // To check whether card ID has changed
        assertEqualCardId(targetCard, editedCard);
        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_mismatchedCardsToFillBlanksCard_failure() throws Exception {
        FillBlanksCard editedCard = new FillBlanksCardBuilder().build();
        Card targetCard = model.getFilteredCardList().get(INDEX_FIRST_CARD.getZeroBased());
        EditCardDescriptor descriptor = new EditCardDescriptorBuilder(editedCard).build();
        EditCardCommand editCommand = prepareCommand(INDEX_FIRST_CARD, descriptor);

        String expectedMessage = String.format(EditCardCommand.MESSAGE_MISMATCHED_CARDS,
                targetCard.getType(), editedCard.getType());

        assertCommandFailure(editCommand, model, expectedMessage);
    }

    @Test
    public void execute_mismatchedCardsToMcqCard_failure() throws Exception {
        McqCard editedCard = new McqCardBuilder().build();
        Card targetCard = model.getFilteredCardList().get(INDEX_FIRST_CARD.getZeroBased());
        EditCardDescriptor descriptor = new EditCardDescriptorBuilder(editedCard)
                .withOptions(editedCard.getOptions()).build();
        EditCardCommand editCommand = prepareCommand(INDEX_FIRST_CARD, descriptor);

        String expectedMessage = String.format(EditCardCommand.MESSAGE_MISMATCHED_CARDS,
                targetCard.getType(), editedCard.getType());

        assertCommandFailure(editCommand, model, expectedMessage);
    }

    @Test
    public void execute_mismatchedFillBlanksCardsToMcqCard_failure() throws Exception {
        McqCard editedCard = new McqCardBuilder().build();
        FillBlanksCard targetCard = (FillBlanksCard) modelWithFillBlanksCards.getFilteredCardList()
                .get(INDEX_FIRST_CARD.getZeroBased());
        EditCardDescriptor descriptor = new EditCardDescriptorBuilder(editedCard)
                .withOptions(editedCard.getOptions()).build();
        EditCardCommand editCommand = prepareCommand(INDEX_FIRST_CARD, descriptor);
        editCommand.setData(modelWithFillBlanksCards, new CommandHistory(), new UndoRedoStack());


        String expectedMessage = String.format(EditCardCommand.MESSAGE_MISMATCHED_CARDS,
                targetCard.getType(), editedCard.getType());

        assertCommandFailure(editCommand, modelWithFillBlanksCards, expectedMessage);
    }

    @Test
    public void execute_mismatchedFillBlanksCardsToCard_failure() throws Exception {
        Card editedCard = new CardBuilder().build();
        FillBlanksCard targetCard = (FillBlanksCard) modelWithFillBlanksCards.getFilteredCardList()
                .get(INDEX_FIRST_CARD.getZeroBased());
        EditCardDescriptor descriptor = new EditCardDescriptorBuilder(editedCard).build();
        EditCardCommand editCommand = prepareCommand(INDEX_FIRST_CARD, descriptor);
        editCommand.setData(modelWithFillBlanksCards, new CommandHistory(), new UndoRedoStack());

        String expectedMessage = String.format(EditCardCommand.MESSAGE_MISMATCHED_CARDS,
                targetCard.getType(), editedCard.getType());

        assertCommandFailure(editCommand, modelWithFillBlanksCards, expectedMessage);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() throws Exception {
        Index indexLastCard = Index.fromOneBased(model.getFilteredCardList().size());
        Card lastCard = model.getFilteredCardList().get(indexLastCard.getZeroBased());

        CardBuilder cardInList = new CardBuilder(lastCard);
        Card editedCard = cardInList.withFront(VALID_NAME_COMSCI).withId(lastCard.getId().toString())
                .build();

        EditCardCommand.EditCardDescriptor descriptor = new EditCardDescriptorBuilder().withFront(VALID_NAME_COMSCI)
                .build();
        EditCardCommand editCommand = prepareCommand(indexLastCard, descriptor);

        String expectedMessage = String.format(EditCardCommand.MESSAGE_EDIT_CARD_SUCCESS, editedCard);

        Model expectedModel = new ModelManager(new CardBank(model.getCardBank()), new UserPrefs());
        expectedModel.updateCard(lastCard, editedCard);

        // To check whether card ID has changed
        assertEqualCardId(lastCard, editedCard);
        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }
    //@@author

    //@@author jethrokuan
    @Test
    public void execute_someTagsAdded_success() throws Exception {
        Index indexLastCard = Index.fromOneBased(model.getFilteredCardList().size());
        Card lastCard = model.getFilteredCardList().get(indexLastCard.getZeroBased());

        Model expectedModel = new ModelManager(new CardBank(model.getCardBank()), new UserPrefs());

        expectedModel.addTags(lastCard, new HashSet<>(Arrays.asList(MATHEMATICS_TAG, COMSCI_TAG)));

        EditCardCommand.EditCardDescriptor descriptor = new EditCardDescriptorBuilder()
                .withTagsToAdd(new HashSet<>(Arrays.asList(MATHEMATICS_TAG, COMSCI_TAG)))
                .build();

        String expectedMessage = String.format(EditCardCommand.MESSAGE_EDIT_CARD_SUCCESS, lastCard);
        EditCardCommand editCommand = prepareCommand(indexLastCard, descriptor);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someTagsRemoved_success() throws Exception {
        Index indexLastCard = Index.fromOneBased(model.getFilteredCardList().size());
        Card lastCard = model.getFilteredCardList().get(indexLastCard.getZeroBased());
        Tag tag = model.getTags(lastCard).get(0);

        EditCardCommand.EditCardDescriptor descriptor = new EditCardDescriptorBuilder()
                .withTagsToRemove(new HashSet<>(Arrays.asList(tag)))
                .build();

        String expectedMessage = String.format(EditCardCommand.MESSAGE_EDIT_CARD_SUCCESS, lastCard);
        EditCardCommand editCommand = prepareCommand(indexLastCard, descriptor);

        Model expectedModel = new ModelManager(new CardBank(model.getCardBank()), new UserPrefs());
        expectedModel.removeTags(lastCard, new HashSet<>(Arrays.asList(tag)));

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_editCardNewTagCreated_success() throws Exception {
        Index indexLastCard = Index.fromOneBased(model.getFilteredCardList().size());
        Card lastCard = model.getFilteredCardList().get(indexLastCard.getZeroBased());

        Tag newTag = new Tag(new Name("Machine Learning"));
        EditCardCommand.EditCardDescriptor descriptor = new EditCardDescriptorBuilder()
                .withTagsToAdd(new HashSet<>(Arrays.asList(newTag)))
                .build();

        String expectedMessage = String.format(EditCardCommand.MESSAGE_EDIT_CARD_SUCCESS, lastCard);
        EditCardCommand editCardCommand = prepareCommand(indexLastCard, descriptor);

        Model expectedModel = new ModelManager(new CardBank(model.getCardBank()), new UserPrefs());
        expectedModel.addTags(lastCard, new HashSet<>(Arrays.asList(newTag)));

        assertCommandSuccess(editCardCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_removeCardNoTag_failure() throws Exception {
        Index indexLastCard = Index.fromOneBased(model.getFilteredCardList().size());

        EditCardCommand.EditCardDescriptor descriptor = new EditCardDescriptorBuilder()
                .withTagsToRemove(new HashSet<>(Arrays.asList(ENGLISH_TAG)))
                .build();

        String expectedMessage = String.format(MESSAGE_TAG_NOT_FOUND, ENGLISH_TAG.getName());
        EditCardCommand editCommand = prepareCommand(indexLastCard, descriptor);

        assertCommandFailure(editCommand, model, expectedMessage);
    }

    @Test
    public void execute_removeCardNoEdge_failure() throws Exception {
        Index indexLastCard = Index.fromOneBased(model.getFilteredCardList().size());

        EditCardCommand.EditCardDescriptor descriptor = new EditCardDescriptorBuilder()
                .withTagsToRemove(new HashSet<>(Arrays.asList(MATHEMATICS_TAG)))
                .build();

        String expectedMessage = String.format(MESSAGE_CARD_NO_TAG, MATHEMATICS_TAG.getName());
        EditCardCommand editCommand = prepareCommand(indexLastCard, descriptor);

        assertCommandFailure(editCommand, model, expectedMessage);
    }
    //@@author

    //@@author shawnclq
    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        Card targetCard = model.getFilteredCardList().get(INDEX_FIRST_CARD.getZeroBased());
        EditCardCommand editCommand = prepareCommand(INDEX_FIRST_CARD, new EditCardCommand.EditCardDescriptor());
        Card editedCard = model.getFilteredCardList().get(INDEX_FIRST_CARD.getZeroBased());

        String expectedMessage = String.format(EditCardCommand.MESSAGE_EDIT_CARD_SUCCESS, editedCard);

        Model expectedModel = new ModelManager(new CardBank(model.getCardBank()), new UserPrefs());

        // To check whether card ID has changed
        assertEqualCardId(targetCard, editedCard);
        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() throws Exception {
        Card cardInFilteredList = model.getFilteredCardList().get(INDEX_FIRST_CARD.getZeroBased());
        Card editedCard = new CardBuilder(cardInFilteredList).withFront(VALID_NAME_COMSCI).build();
        EditCardCommand editCommand = prepareCommand(INDEX_FIRST_CARD,
                new EditCardDescriptorBuilder().withFront(VALID_NAME_COMSCI).build());

        String expectedMessage = String.format(EditCardCommand.MESSAGE_EDIT_CARD_SUCCESS, editedCard);

        Model expectedModel = new ModelManager(new CardBank(model.getCardBank()), new UserPrefs());
        expectedModel.updateCard(model.getFilteredCardList().get(0), editedCard);

        // To check whether card ID has changed
        assertEqualCardId(cardInFilteredList, editedCard);
        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateCardUnfilteredList_failure() {
        Card firstCard = model.getFilteredCardList().get(INDEX_FIRST_CARD.getZeroBased());
        EditCardDescriptor descriptor = new EditCardDescriptorBuilder(firstCard).build();
        EditCardCommand editCommand = prepareCommand(INDEX_SECOND_CARD, descriptor);

        assertCommandFailure(editCommand, model, EditCardCommand.MESSAGE_DUPLICATE_CARD);
    }

    @Test
    public void execute_duplicateCardFilteredList_failure() {
        // edit card in filtered list into a duplicate in flashy book
        Card cardInList = model.getCardBank().getCardList().get(INDEX_SECOND_CARD.getZeroBased());
        EditCardCommand editCommand = prepareCommand(INDEX_FIRST_CARD,
                new EditCardDescriptorBuilder(cardInList).build());

        assertCommandFailure(editCommand, model, EditCardCommand.MESSAGE_DUPLICATE_CARD);
    }

    @Test
    public void execute_invalidCardIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCardList().size() + 1);
        EditCardCommand.EditCardDescriptor descriptor = new EditCardDescriptorBuilder()
                .withFront(VALID_NAME_COMSCI).build();
        EditCardCommand editCommand = prepareCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_CARD_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of flashy book
     */
    @Test
    public void execute_invalidCardIndexFilteredList_failure() {
        model.filterCardsByTag(new Tag(new Name("Name")));
        Index outOfBoundIndex = INDEX_SECOND_CARD;
        // ensures that outOfBoundIndex is still in bounds of flashy book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getCardBank().getCardList().size());

        EditCardCommand editCommand = prepareCommand(outOfBoundIndex,
                new EditCardDescriptorBuilder().withFront(VALID_NAME_COMSCI).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_CARD_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Card editedCard = new CardBuilder().build();
        Card cardToEdit = model.getFilteredCardList().get(INDEX_FIRST_CARD.getZeroBased());
        EditCardCommand.EditCardDescriptor descriptor = new EditCardDescriptorBuilder(editedCard)
                .withUuid(cardToEdit.getId()).build();
        EditCardCommand editCommand = prepareCommand(INDEX_FIRST_CARD, descriptor);
        Model expectedModel = new ModelManager(new CardBank(model.getCardBank()), new UserPrefs());
        Card newCard = model.getFilteredCardList().get(INDEX_FIRST_CARD.getZeroBased());
        // To check whether card ID has changed
        assertEqualCardId(cardToEdit, newCard);

        // edit -> first card edited
        editCommand.execute();
        undoRedoStack.push(editCommand);

        // undo -> reverts cardbank back to previous state and filtered card list to show all cards
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first card edited again
        expectedModel.updateCard(cardToEdit, editedCard);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCardList().size() + 1);
        EditCardDescriptor descriptor = new EditCardDescriptorBuilder().withFront(VALID_NAME_COMSCI).build();
        EditCardCommand editCommand = prepareCommand(outOfBoundIndex, descriptor);

        // execution failed -> editCommand not pushed into undoRedoStack
        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_CARD_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Edits a {@code Card} from a filtered list.
     * 2. Undo the edit.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously edited card in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the edit. This ensures {@code RedoCommand} edits the card object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameCardEdited() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Card editedCard = new CardBuilder().withFront("Jethro Kuan").build();
        EditCardDescriptor descriptor = new EditCardDescriptorBuilder(editedCard).build();
        Card targetCard = model.getFilteredCardList().get(INDEX_FIRST_CARD.getZeroBased());
        EditCardCommand editCommand = prepareCommand(INDEX_FIRST_CARD, descriptor);
        Card newCard = model.getFilteredCardList().get(INDEX_FIRST_CARD.getZeroBased());
        Model expectedModel = new ModelManager(new CardBank(model.getCardBank()), new UserPrefs());
        // To check whether card ID has changed
        assertEqualCardId(targetCard, newCard);

        Tag tag = model.getFilteredTagList().get(0);
        model.filterCardsByTag(tag);

        Card cardToEdit = model.getFilteredCardList().get(INDEX_FIRST_CARD.getZeroBased());
        // edit -> edits second card in unfiltered card list / first card in filtered card list
        editCommand.execute();
        undoRedoStack.push(editCommand);

        // undo -> reverts cardbank back to previous state and filtered card list to show all cards
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        expectedModel.updateCard(cardToEdit, editedCard);
        assertNotEquals(model.getFilteredCardList().get(INDEX_FIRST_CARD.getZeroBased()), cardToEdit);
        // redo -> edits same second card in unfiltered card list
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() throws Exception {
        final EditCardCommand standardCommand = prepareCommand(INDEX_FIRST_CARD, CS2101_CARD);

        // same values -> returns true
        EditCardCommand.EditCardDescriptor copyDescriptor = new EditCardCommand.EditCardDescriptor(CS2101_CARD);
        EditCardCommand commandWithSameValues = prepareCommand(INDEX_FIRST_CARD, copyDescriptor);
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
        assertFalse(standardCommand.equals(new EditCardCommand(INDEX_SECOND_CARD, CS2101_CARD)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCardCommand(INDEX_FIRST_CARD, CS2103T_CARD)));
    }

    /**
     * Returns an {@code EditCardCommand} with parameters {@code index} and {@code descriptor}
     */
    private EditCardCommand prepareCommand(Index index, EditCardDescriptor descriptor) {
        EditCardCommand editCommand = new EditCardCommand(index, descriptor);
        editCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return editCommand;
    }

}
