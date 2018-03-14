package seedu.address.network;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.events.network.ApiBookDetailsRequestEvent;
import seedu.address.commons.events.network.ApiBookDetailsResultEvent;
import seedu.address.commons.events.network.ApiSearchRequestEvent;
import seedu.address.commons.events.network.ApiSearchResultEvent;
import seedu.address.commons.events.network.ResultOutcome;
import seedu.address.model.BookShelf;
import seedu.address.model.ReadOnlyBookShelf;
import seedu.address.model.book.Book;
import seedu.address.network.api.google.GoogleBooksApi;
import seedu.address.testutil.TypicalBooks;
import seedu.address.ui.testutil.EventsCollectorRule;

public class NetworkManagerTest {
    private static final String PARAM_EMPTY_RESULT = "1";
    private static final String PARAM_SUCCESS = "12345";
    private static final String PARAM_FAILURE = "failure";

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private NetworkManager networkManager;

    @Before
    public void setUp() {
        networkManager = new NetworkManager(new HttpClient(null), new GoogleBooksApiStub());
        eventsCollectorRule.eventsCollector.reset();
    }

    @Test
    public void handleGoogleApiSearchRequestEvent_emptyResult() {
        networkManager.handleGoogleApiSearchRequestEvent(new ApiSearchRequestEvent(PARAM_EMPTY_RESULT));
        ApiSearchResultEvent event =
                (ApiSearchResultEvent) eventsCollectorRule.eventsCollector.getMostRecent(ApiSearchResultEvent.class);
        assertEquals(ResultOutcome.SUCCESS, event.outcome);
        assertEquals(0, event.bookShelf.getBookList().size());
    }

    @Test
    public void handleGoogleApiSearchRequestEvent_success() {
        networkManager.handleGoogleApiSearchRequestEvent(new ApiSearchRequestEvent(PARAM_SUCCESS));
        ApiSearchResultEvent event =
                (ApiSearchResultEvent) eventsCollectorRule.eventsCollector.getMostRecent(ApiSearchResultEvent.class);
        assertEquals(ResultOutcome.SUCCESS, event.outcome);
        assertEquals(5, event.bookShelf.getBookList().size());
    }

    @Test
    public void handleGoogleApiSearchRequestEvent_failure() {
        networkManager.handleGoogleApiSearchRequestEvent(new ApiSearchRequestEvent(PARAM_FAILURE));
        ApiSearchResultEvent event =
                (ApiSearchResultEvent) eventsCollectorRule.eventsCollector.getMostRecent(ApiSearchResultEvent.class);
        assertEquals(ResultOutcome.FAILURE, event.outcome);
        assertEquals(null, event.bookShelf);
    }

    @Test
    public void handleGoogleApiBookDetailsRequestEvent_success() {
        networkManager.handleGoogleApiBookDetailsRequestEvent(new ApiBookDetailsRequestEvent(PARAM_SUCCESS));
        ApiBookDetailsResultEvent event = (ApiBookDetailsResultEvent)
                eventsCollectorRule.eventsCollector.getMostRecent(ApiBookDetailsResultEvent.class);
        assertEquals(ResultOutcome.SUCCESS, event.outcome);
        assertEquals(TypicalBooks.ARTEMIS, event.book);
    }

    @Test
    public void handleGoogleApiBookDetailsRequestEvent_failure() {
        networkManager.handleGoogleApiBookDetailsRequestEvent(new ApiBookDetailsRequestEvent(PARAM_FAILURE));
        ApiBookDetailsResultEvent event = (ApiBookDetailsResultEvent)
                eventsCollectorRule.eventsCollector.getMostRecent(ApiBookDetailsResultEvent.class);
        assertEquals(ResultOutcome.FAILURE, event.outcome);
        assertEquals(null, event.book);
    }

    /**
     * A stub GoogleBooksApi that returns preset results when given certain parameters.
     * The stubbed methods return a {@link CompletableFuture} that is already completed, so the chained
     * calls within {@link NetworkManager} will be executed synchronously. We are therefore guaranteed
     * that the {code *ResultEvent} will have already been raised when the methods we are testing returns.
     * */
    private static class GoogleBooksApiStub extends GoogleBooksApi {

        GoogleBooksApiStub() {
            super(null);
        }

        @Override
        public CompletableFuture<ReadOnlyBookShelf> searchBooks(String parameters) {
            if (parameters.equals(PARAM_EMPTY_RESULT)) {
                return CompletableFuture.completedFuture(new BookShelf());
            } else if (parameters.equals(PARAM_SUCCESS)) {
                return CompletableFuture.completedFuture(TypicalBooks.getTypicalBookShelf());
            }
            return CompletableFuture.completedFuture(null).thenApply((bookShelf) -> {
                throw new CompletionException(new IOException());
            });
        }

        @Override
        public CompletableFuture<Book> getBookDetails(String bookId) {
            if (bookId.equals(PARAM_SUCCESS)) {
                return CompletableFuture.completedFuture(TypicalBooks.ARTEMIS);
            }
            return CompletableFuture.completedFuture(null).thenApply((book) -> {
                throw new CompletionException(new IOException());
            });
        }
    }

}
