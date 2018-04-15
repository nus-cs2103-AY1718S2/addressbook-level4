package guitests.guihandles;

import java.util.List;
import java.util.Optional;

import javafx.scene.control.ListView;
import seedu.address.model.pair.Pair;
import seedu.address.ui.PairCard;

/**
 * Provides a handle for {@code PairListPanel} containing the list of {@code PairCard}.
 */
public class PairListPanelHandle extends NodeHandle<ListView<PairCard>> {
    public static final String PERSON_LIST_VIEW_ID = "#pairListView";

    private Optional<PairCard> lastRememberedSelectedPairCard;

    public PairListPanelHandle(ListView<PairCard> pairListPanelNode) {
        super(pairListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code PairCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     */
    public PairCardHandle getHandleToSelectedCard() {
        List<PairCard> pairList = getRootNode().getSelectionModel().getSelectedItems();

        if (pairList.size() != 1) {
            throw new AssertionError("Pair list size expected 1.");
        }

        return new PairCardHandle(pairList.get(0).getRoot());
    }

    /**
     * Returns the index of the selected card.
     */
    public int getSelectedCardIndex() {
        return getRootNode().getSelectionModel().getSelectedIndex();
    }

    /**
     * Returns true if a card is currently selected.
     */
    public boolean isAnyCardSelected() {
        List<PairCard> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display and select the pair.
     */
    public void navigateToCard(Pair pair) {
        List<PairCard> cards = getRootNode().getItems();
        Optional<PairCard> matchingCard = cards.stream().filter(card -> card.pair.equals(pair)).findFirst();

        if (!matchingCard.isPresent()) {
            throw new IllegalArgumentException("Pair does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(matchingCard.get());
            getRootNode().getSelectionModel().select(matchingCard.get());
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Returns the pair card handle of a pair associated with the {@code index} in the list.
     */
    public PairCardHandle getPairCardHandle(int index) {
        return getPairCardHandle(getRootNode().getItems().get(index).pair);
    }

    /**
     * Returns the {@code PairCardHandle} of the specified {@code pair} in the list.
     */
    public PairCardHandle getPairCardHandle(Pair pair) {
        Optional<PairCardHandle> handle = getRootNode().getItems().stream()
                .filter(card -> card.pair.equals(pair))
                .map(card -> new PairCardHandle(card.getRoot()))
                .findFirst();
        return handle.orElseThrow(() -> new IllegalArgumentException("Pair does not exist."));
    }

    /**
     * Selects the {@code PairCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Remembers the selected {@code PairCard} in the list.
     */
    public void rememberSelectedPairCard() {
        List<PairCard> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedPairCard = Optional.empty();
        } else {
            lastRememberedSelectedPairCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code PairCard} is different from the value remembered by the most recent
     * {@code rememberSelectedPairCard()} call.
     */
    public boolean isSelectedPairCardChanged() {
        List<PairCard> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedPairCard.isPresent();
        } else {
            return !lastRememberedSelectedPairCard.isPresent()
                    || !lastRememberedSelectedPairCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
