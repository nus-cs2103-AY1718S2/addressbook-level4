package guitests.guihandles;

import java.util.List;
import java.util.Optional;

import javafx.scene.control.ListView;
import seedu.address.model.coin.Coin;
import seedu.address.ui.CoinCard;

/**
 * Provides a handle for {@code CoinListPanel} containing the list of {@code CoinCard}.
 */
public class CoinListPanelHandle extends NodeHandle<ListView<CoinCard>> {
    public static final String COIN_LIST_VIEW_ID = "#coinListView";

    private Optional<CoinCard> lastRememberedSelectedCoinCard;

    public CoinListPanelHandle(ListView<CoinCard> coinListPanelNode) {
        super(coinListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code CoinCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     */
    public CoinCardHandle getHandleToSelectedCard() {
        List<CoinCard> coinList = getRootNode().getSelectionModel().getSelectedItems();

        if (coinList.size() != 1) {
            throw new AssertionError("Coin list size expected 1.");
        }

        return new CoinCardHandle(coinList.get(0).getRoot());
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
        List<CoinCard> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display and select the coin.
     */
    public void navigateToCard(Coin coin) {
        List<CoinCard> cards = getRootNode().getItems();
        Optional<CoinCard> matchingCard = cards.stream().filter(card -> card.coin.equals(coin)).findFirst();

        if (!matchingCard.isPresent()) {
            throw new IllegalArgumentException("Coin does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(matchingCard.get());
            getRootNode().getSelectionModel().select(matchingCard.get());
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Returns the coin card handle of a coin associated with the {@code index} in the list.
     */
    public CoinCardHandle getCoinCardHandle(int index) {
        return getCoinCardHandle(getRootNode().getItems().get(index).coin);
    }

    /**
     * Returns the {@code CoinCardHandle} of the specified {@code coin} in the list.
     */
    public CoinCardHandle getCoinCardHandle(Coin coin) {
        Optional<CoinCardHandle> handle = getRootNode().getItems().stream()
                .filter(card -> card.coin.equals(coin))
                .map(card -> new CoinCardHandle(card.getRoot()))
                .findFirst();
        return handle.orElseThrow(() -> new IllegalArgumentException("Coin does not exist."));
    }

    /**
     * Selects the {@code CoinCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Remembers the selected {@code CoinCard} in the list.
     */
    public void rememberSelectedCoinCard() {
        List<CoinCard> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedCoinCard = Optional.empty();
        } else {
            lastRememberedSelectedCoinCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code CoinCard} is different from the value remembered by the most recent
     * {@code rememberSelectedCoinCard()} call.
     */
    public boolean isSelectedCoinCardChanged() {
        List<CoinCard> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedCoinCard.isPresent();
        } else {
            return !lastRememberedSelectedCoinCard.isPresent()
                    || !lastRememberedSelectedCoinCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
