package guitests.guihandles;

import java.util.List;
import java.util.Optional;

import javafx.scene.control.ListView;
import seedu.address.model.cinema.Cinema;
import seedu.address.ui.CinemaCard;

/**
 * Provides a handle for {@code CinemaListPanel} containing the list of {@code CinemaCard}.
 */
public class CinemaListPanelHandle extends NodeHandle<ListView<CinemaCard>> {
    public static final String CINEMA_LIST_VIEW_ID = "#cinemaListView";

    private Optional<CinemaCard> lastRememberedSelectedCinemaCard;

    public CinemaListPanelHandle(ListView<CinemaCard> cinemaListPanelNode) {
        super(cinemaListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code CinemaCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     */
    public CinemaCardHandle getHandleToSelectedCard() {
        List<CinemaCard> cinemaList = getRootNode().getSelectionModel().getSelectedItems();

        if (cinemaList.size() != 1) {
            throw new AssertionError("Cinema list size expected 1.");
        }

        return new CinemaCardHandle(cinemaList.get(0).getRoot());
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
        List<CinemaCard> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display and select the cinema.
     */
    public void navigateToCard(Cinema cinema) {
        List<CinemaCard> cards = getRootNode().getItems();
        Optional<CinemaCard> matchingCard = cards.stream().filter(card -> card.cinema.equals(cinema)).findFirst();

        if (!matchingCard.isPresent()) {
            throw new IllegalArgumentException("Cinema does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(matchingCard.get());
            getRootNode().getSelectionModel().select(matchingCard.get());
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Returns the cinema card handle of a cinema associated with the {@code index} in the list.
     */
    public CinemaCardHandle getCinemaCardHandle(int index) {
        return getCinemaCardHandle(getRootNode().getItems().get(index).cinema);
    }

    /**
     * Returns the {@code CinemaCardHandle} of the specified {@code cinema} in the list.
     */
    public CinemaCardHandle getCinemaCardHandle(Cinema cinema) {
        Optional<CinemaCardHandle> handle = getRootNode().getItems().stream()
                .filter(card -> card.cinema.equals(cinema))
                .map(card -> new CinemaCardHandle(card.getRoot()))
                .findFirst();
        return handle.orElseThrow(() -> new IllegalArgumentException("Cinema does not exist."));
    }

    /**
     * Selects the {@code CinemaCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Remembers the selected {@code CinemaCard} in the list.
     */
    public void rememberSelectedCinemaCard() {
        List<CinemaCard> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedCinemaCard = Optional.empty();
        } else {
            lastRememberedSelectedCinemaCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code CinemaCard} is different from the value remembered by the most recent
     * {@code rememberSelectedCinemaCard()} call.
     */
    public boolean isSelectedCinemaCardChanged() {
        List<CinemaCard> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedCinemaCard.isPresent();
        } else {
            return !lastRememberedSelectedCinemaCard.isPresent()
                    || !lastRememberedSelectedCinemaCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
