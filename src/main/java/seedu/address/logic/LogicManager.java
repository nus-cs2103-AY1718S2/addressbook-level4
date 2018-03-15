package seedu.address.logic;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.network.ApiBookDetailsResultEvent;
import seedu.address.commons.events.network.ApiSearchResultEvent;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.commons.events.ui.SwitchToBookListRequestEvent;
import seedu.address.commons.events.ui.SwitchToSearchResultsRequestEvent;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.SearchCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.BookShelfParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ActiveListType;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyBookShelf;
import seedu.address.model.book.Book;
import seedu.address.model.book.exceptions.DuplicateBookException;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final CommandHistory history;
    private final BookShelfParser bookShelfParser;
    private final UndoStack undoStack;

    public LogicManager(Model model) {
        this.model = model;
        history = new CommandHistory();
        bookShelfParser = new BookShelfParser();
        undoStack = new UndoStack();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        try {
            Command command = bookShelfParser.parseCommand(commandText);
            command.setData(model, history, undoStack);
            CommandResult result = command.execute();
            undoStack.push(command);
            return result;
        } finally {
            history.add(commandText);
        }
    }

    @Override
    public ObservableList<Book> getFilteredBookList() {
        return model.getFilteredBookList();
    }

    @Override
    public ObservableList<Book> getSearchResultsList() {
        return model.getSearchResultsList();
    }

    @Override
    public ListElementPointer getHistorySnapshot() {
        return new ListElementPointer(history.getHistory());
    }

    @Subscribe
    private void handleApiSearchResultEvent(ApiSearchResultEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        switch (event.outcome) {
        case FAILURE:
            raise(new NewResultAvailableEvent(SearchCommand.MESSAGE_SEARCH_FAIL));
            break;
        case SUCCESS:
            // Updating the search results on the model will update its observable list (and hence the UI)
            // so this must be done on the JavaFX thread.
            Platform.runLater(() -> {
                ReadOnlyBookShelf searchResults = event.bookShelf;
                model.updateSearchResults(searchResults);
                raise(new SwitchToSearchResultsRequestEvent());
                raise(new NewResultAvailableEvent(
                        String.format(SearchCommand.MESSAGE_SEARCH_SUCCESS, searchResults.getBookList().size())));
            });
            break;
        default:
            logger.warning("Unexpected ApiSearchResultEvent outcome.");
            break;
        }
    }

    @Subscribe
    private void handleApiBookDetailsResultEvent(ApiBookDetailsResultEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        switch (event.outcome) {
        case FAILURE:
            raise(new NewResultAvailableEvent(AddCommand.MESSAGE_ADD_FAIL));
            break;
        case SUCCESS:
            Platform.runLater(() -> {
                Book toAdd = event.book;
                try {
                    model.addBook(toAdd);
                    raise(new NewResultAvailableEvent(
                            String.format(AddCommand.MESSAGE_SUCCESS, toAdd)));
                } catch (DuplicateBookException e) {
                    raise(new NewResultAvailableEvent(AddCommand.MESSAGE_DUPLICATE_BOOK));
                }
            });
            break;
        default:
            logger.warning("Unexpected ApiBookDetailsEvent outcome.");
            break;
        }
    }

    @Subscribe
    private void handleShowBookListRequestEvent(SwitchToBookListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        model.setActiveListType(ActiveListType.BOOK_SHELF);
    }

    @Subscribe
    private void handleShowSearchResultsRequestEvent(SwitchToSearchResultsRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        model.setActiveListType(ActiveListType.SEARCH_RESULTS);
    }
}
