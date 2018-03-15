package seedu.address.ui;

import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.JumpToSearchResultsIndexRequestEvent;
import seedu.address.commons.events.ui.SearchResultsSelectionChangedEvent;
import seedu.address.model.book.Book;

/**
 * Panel containing the list of search results.
 */
public class SearchResultsPanel extends UiPart<Region> {
    private static final String FXML = "SearchResultsPanel.fxml";
    private static final String INFO_TEXT = "Showing %s search results.";
    private final Logger logger = LogsCenter.getLogger(SearchResultsPanel.class);

    @FXML
    private Label infoLabel;
    @FXML
    private ListView<BookCard> searchResultsListView;

    public SearchResultsPanel(ObservableList<Book> searchResults) {
        super(FXML);
        setConnections(searchResults);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Book> searchResults) {
        ObservableList<BookCard> mappedList = EasyBind.map(
                searchResults, (book) -> new BookCard(book, searchResults.indexOf(book) + 1));
        searchResultsListView.setItems(mappedList);
        searchResultsListView.setCellFactory(listView -> new BookListViewCell());
        setEventHandlerForSelectionChangeEvent();
        setEventHandlerForListChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        searchResultsListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in search results panel changed to : '" + newValue + "'");
                        raise(new SearchResultsSelectionChangedEvent(newValue));
                    }
                });
    }

    private void setEventHandlerForListChangeEvent() {
        searchResultsListView.getItems().addListener((ListChangeListener<BookCard>) c -> {
            long newSize = c.getList().size();
            infoLabel.setText(String.format(INFO_TEXT, newSize));
        });
    }

    /** Scrolls to the top of the search results list. */
    protected void scrollToTop() {
        searchResultsListView.scrollTo(0);
    }

    /**
     * Scrolls to the {@code BookCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            searchResultsListView.scrollTo(index);
            searchResultsListView.getSelectionModel().clearAndSelect(index);
        });
    }

    protected void clearSelectionAndScrollToTop() {
        searchResultsListView.getSelectionModel().clearSelection();
        scrollToTop();
    }

    @Subscribe
    private void handleJumpToResultsListRequestEvent(JumpToSearchResultsIndexRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code BookCard}.
     */
    class BookListViewCell extends ListCell<BookCard> {

        @Override
        protected void updateItem(BookCard book, boolean empty) {
            super.updateItem(book, empty);

            if (empty || book == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(book.getRoot());
            }
        }
    }

}
