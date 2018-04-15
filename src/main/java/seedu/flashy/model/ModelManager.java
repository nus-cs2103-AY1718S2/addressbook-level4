package seedu.flashy.model;

import static java.util.Objects.requireNonNull;
import static seedu.flashy.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import seedu.flashy.commons.core.ComponentManager;
import seedu.flashy.commons.core.EventsCenter;
import seedu.flashy.commons.core.LogsCenter;
import seedu.flashy.commons.events.model.CardBankChangedEvent;
import seedu.flashy.commons.events.ui.CardListPanelSelectionChangedEvent;
import seedu.flashy.commons.events.ui.EmptyCardBackEvent;
import seedu.flashy.commons.events.ui.TagListPanelSelectionChangedEvent;
import seedu.flashy.model.card.Card;
import seedu.flashy.model.card.exceptions.CardNotFoundException;
import seedu.flashy.model.card.exceptions.DuplicateCardException;
import seedu.flashy.model.card.exceptions.NoCardSelectedException;
import seedu.flashy.model.cardtag.CardTag;
import seedu.flashy.model.cardtag.DuplicateEdgeException;
import seedu.flashy.model.cardtag.EdgeNotFoundException;
import seedu.flashy.model.tag.AddTagResult;
import seedu.flashy.model.tag.Tag;
import seedu.flashy.model.tag.exceptions.DuplicateTagException;
import seedu.flashy.model.tag.exceptions.TagNotFoundException;

/**
 * Represents the in-memory model of the flashy book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final CardBank cardBank;
    private final FilteredList<Tag> filteredTags;
    private final ObservableList<Card> filteredCards;
    private Card selectedCard;
    private Tag selectedTag;
    private LocalDateTime beforeThisDate;

    /**
     * Initializes a ModelManager with the given cardBank and userPrefs.
     */
    public ModelManager(ReadOnlyCardBank cardBank, UserPrefs userPrefs) {
        super();
        requireAllNonNull(cardBank, userPrefs);

        logger.fine("Initializing with flashy book: " + cardBank + " and user prefs " + userPrefs);

        this.cardBank = new CardBank(cardBank);
        filteredTags = new FilteredList<>(this.cardBank.getTagList());

        // To prevent direct referencing, which would cause setAll() to affect cardBank's list
        filteredCards = FXCollections.observableArrayList();
        filteredCards.setAll(this.cardBank.getCardList());

        selectedTag = null;
        selectedCard = null;
        beforeThisDate = null;
    }

    public ModelManager() {
        this(new CardBank(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyCardBank newData) {
        cardBank.resetData(newData);
        indicateCardBankChanged();
    }

    @Override
    public ReadOnlyCardBank getCardBank() {
        return cardBank;
    }

    /**
     * Raises an event to indicate the model has changed
     */
    private void indicateCardBankChanged() {
        raise(new CardBankChangedEvent(cardBank));
        updateFilteredCardList();
    }

    //@@author jethrokuan
    @Override
    public synchronized void deleteTag(Tag target) throws TagNotFoundException {
        CardTag cardTag = this.cardBank.getCardTag();
        List<Card> cards = cardTag.getCards(target, this.cardBank.getCardList());
        for (Card card : cards) {
            try {
                cardTag.removeEdge(card, target);
            } catch (EdgeNotFoundException e) {
                throw new IllegalStateException("Not possible to reach here.");
            }
        }

        cardBank.removeTag(target);
        indicateCardBankChanged();
    }

    @Override
    public synchronized AddTagResult addTag(Tag tag) {
        AddTagResult tagResult = cardBank.addTag(tag);
        updateFilteredTagList(PREDICATE_SHOW_ALL_TAGS);
        indicateCardBankChanged();
        return tagResult;
    }

    @Override
    public void updateTag(Tag target, Tag editedTag)
        throws DuplicateTagException, TagNotFoundException {
        requireAllNonNull(target, editedTag);

        cardBank.updateTag(target, editedTag);
        indicateCardBankChanged();
    }
    //@@author

    //=========== Filtered Tag List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Tag} backed by the internal list of
     * {@code cardBank}
     */
    @Override
    public ObservableList<Tag> getFilteredTagList() {
        return FXCollections.unmodifiableObservableList(filteredTags);
    }

    @Override
    public void updateFilteredTagList(Predicate<Tag> predicate) {
        requireNonNull(predicate);
        filteredTags.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return cardBank.equals(other.cardBank)
            && filteredTags.equals(other.filteredTags);
    }

    //@@author jethrokuan
    @Override
    public synchronized void addCard(Card card) throws DuplicateCardException {
        cardBank.addCard(card);
        indicateCardBankChanged();
    }

    @Override
    public synchronized void deleteCard(Card card) throws CardNotFoundException {
        CardTag cardTag = this.getCardBank().getCardTag();
        List<Tag> tags = cardTag.getTags(card, this.getCardBank().getTagList());

        // We need to clone tags because removing tags while iterating over it results in strange behaviour.
        List<Tag> tempTags = new ArrayList<>();
        for (Tag tag : tags) {
            tempTags.add(tag);
        }

        int size = tempTags.size();
        for (Tag tag : tempTags) {
            try {
                cardTag.removeEdge(card, tag);
                if (!cardTag.hasCards(tag)) {
                    cardBank.removeTag(tag);
                }
            } catch (EdgeNotFoundException e) {
                throw new IllegalStateException("Not possible to reach here.");
            } catch (TagNotFoundException e) {
                throw new IllegalStateException("Not possible to reach here.");
            }
        }
        cardBank.deleteCard(card);
        indicateCardBankChanged();
    }

    //@@author

    //@@author pukipuki
    @Override
    public Card getSelectedCard() {
        return selectedCard;
    }

    @Override
    public void setSelectedCard(Card selectedCard) {
        this.selectedCard = selectedCard;
    }

    @Override
    public void answerSelectedCard(int confidenceLevel) throws NoCardSelectedException {
        if (selectedCard == null) {
            logger.warning("no card selected, unable to apply to null.");
            throw new NoCardSelectedException();
        }
        boolean isTooEasy = selectedCard.getSchedule().feedbackHandlerRouter(confidenceLevel);
        logger.fine("sending answer feedback to scheduler, confidenceLevel: " + confidenceLevel);
        if (isTooEasy) {
            filteredCards.remove(selectedCard);
            emptyAndUnselectCard();
        } else {
            showDueCards(beforeThisDate);
        }
    }
    //@@author

    @Override
    public void updateCard(Card target, Card editedCard)
        throws DuplicateCardException, CardNotFoundException {
        requireAllNonNull(target, editedCard);

        cardBank.updateCard(target, editedCard);
        indicateCardBankChanged();
    }

    //@@author yong-jie
    @Override
    public void showAllCards() {
        filteredCards.setAll(this.cardBank.getCardList());
    }

    /**
     * Synchronises the card list with that of the card bank.
     */
    private void updateFilteredCardList() {
        if (selectedTag == null) {
            showAllCards();
            return;
        }
        filterCardsByTag(selectedTag);
    }

    @Override
    public void filterCardsByTag(Tag tag) {
        filteredCards.setAll(cardBank
            .getCardTag()
            .getCards(tag, cardBank.getCardList()));
    }

    //@@author
    @Override
    public ObservableList<Card> getFilteredCardList() {
        return FXCollections.unmodifiableObservableList(filteredCards);
    }

    //@@author pukipuki
    @Override
    public void setNextReview(LocalDateTime date) throws NoCardSelectedException {
        if (selectedCard == null) {
            logger.warning("no card selected, unable to apply to null.");
            throw new NoCardSelectedException();
        }
        selectedCard.getSchedule().setNextReview(date);
        logger.fine("Setting next review date of card to: " + date.toString());
        showDueCards(beforeThisDate);
    }

    @Override
    public void showDueCards(LocalDateTime date) {
        if (date == null) {
            beforeThisDate = LocalDate.now().atStartOfDay();
        } else {
            beforeThisDate = date;
        }
        filteredCards.setAll(this.cardBank.getReviewList(beforeThisDate, filteredCards));
        logger.fine("Showing cards due before: " + beforeThisDate.toString());
        emptyAndUnselectCard();
    }

    @Override
    public void emptyAndUnselectCard() {
        logger.fine("Clearing back of card.");
        EventsCenter.getInstance().post(new EmptyCardBackEvent());
        this.selectedCard = null;
    }
    //@@author

    //@@author jethrokuan
    @Override
    public List<Tag> getTags(Card card) {
        return this.getCardBank()
            .getCardTag()
            .getTags(card, this.getCardBank().getTagList());
    }

    // NOTE: tag passed might not have the correct ids, so it is important to fetch them first.
    @Override
    public void removeTags(Card card, Set<Tag> tags) throws EdgeNotFoundException, TagNotFoundException {
        CardTag cardTag = this.getCardBank().getCardTag();
        for (Tag tag : tags) {
            int index = this.cardBank.getTagList().indexOf(tag);
            if (index == -1) {
                throw new TagNotFoundException(tag);
            }
            Tag existingTag = this.cardBank.getTagList().get(index);
            cardTag.removeEdge(card, existingTag);
        }
        indicateCardBankChanged(); // Force UI update.
    }

    // NOTE: tag passed might not have the correct ids, so it is important to fetch them first.
    @Override
    public void addTags(Card card, Set<Tag> tags) throws DuplicateEdgeException {
        CardTag cardTag = this.getCardBank().getCardTag();
        for (Tag tag : tags) {
            Tag newOrExistingTag = addTag(tag).getTag();
            cardTag.addEdge(card, newOrExistingTag);
        }
        indicateCardBankChanged(); // Force UI update.
    }

    @Override
    public void showUntaggedCards() {
        Predicate<Card> predCardsNoTags = card -> getTags(card).isEmpty();
        filteredCards.setAll(this.getCardBank().getCardList().filtered(predCardsNoTags));
    }
    //@@author

    //@@author yong-jie
    @Subscribe
    private void handleTagListPanelSelectionEvent(TagListPanelSelectionChangedEvent event) {
        selectedTag = event.getNewSelection().tag;
        filterCardsByTag(selectedTag);
    }

    //@@author pukipuki
    @Subscribe
    private void handleCardListPanelSelectionEvent(CardListPanelSelectionChangedEvent event) {
        this.selectedCard = event.getNewSelection().card;
    }
    //@@author
}
