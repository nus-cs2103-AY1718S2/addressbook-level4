package guitests.guihandles;

import java.util.List;
import java.util.Optional;

import javafx.scene.control.ListView;
import seedu.address.model.movie.Movie;
import seedu.address.ui.MovieCard;

/**
 * Provides a handle for {@code MovieListPanel} containing the list of {@code MovieCard}.
 */
public class MovieListPanelHandle extends NodeHandle<ListView<MovieCard>> {
    public static final String MOVIE_LIST_VIEW_ID = "#movieListView";

    private Optional<MovieCard> lastRememberedSelectedMovieCard;

    public MovieListPanelHandle(ListView<MovieCard> movieListPanelNode) {
        super(movieListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code MovieCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     */
    public MovieCardHandle getHandleToSelectedCard() {
        List<MovieCard> movieList = getRootNode().getSelectionModel().getSelectedItems();

        if (movieList.size() != 1) {
            throw new AssertionError("Movie list size expected 1.");
        }

        return new MovieCardHandle(movieList.get(0).getRoot());
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
        List<MovieCard> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display and select the movie.
     */
    public void navigateToCard(Movie movie) {
        List<MovieCard> cards = getRootNode().getItems();
        Optional<MovieCard> matchingCard = cards.stream().filter(card -> card.movie.equals(movie)).findFirst();

        if (!matchingCard.isPresent()) {
            throw new IllegalArgumentException("Movie does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(matchingCard.get());
            getRootNode().getSelectionModel().select(matchingCard.get());
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Returns the movie card handle of a movie associated with the {@code index} in the list.
     */
    public MovieCardHandle getMovieCardHandle(int index) {
        return getMovieCardHandle(getRootNode().getItems().get(index).movie);
    }

    /**
     * Returns the {@code MovieCardHandle} of the specified {@code movie} in the list.
     */
    public MovieCardHandle getMovieCardHandle(Movie movie) {
        Optional<MovieCardHandle> handle = getRootNode().getItems().stream()
                .filter(card -> card.movie.equals(movie))
                .map(card -> new MovieCardHandle(card.getRoot()))
                .findFirst();
        return handle.orElseThrow(() -> new IllegalArgumentException("Movie does not exist."));
    }

    /**
     * Selects the {@code MovieCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Remembers the selected {@code MovieCard} in the list.
     */
    public void rememberSelectedMovieCard() {
        List<MovieCard> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedMovieCard = Optional.empty();
        } else {
            lastRememberedSelectedMovieCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code MovieCard} is different from the value remembered by the most recent
     * {@code rememberSelectedMovieCard()} call.
     */
    public boolean isSelectedMovieCardChanged() {
        List<MovieCard> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedMovieCard.isPresent();
        } else {
            return !lastRememberedSelectedMovieCard.isPresent()
                    || !lastRememberedSelectedMovieCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
