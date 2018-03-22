package seedu.address.network;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    private GoogleBooksApi mockGoogleBooksApi;

    @Before
    public void setUp() {
        mockGoogleBooksApi = mock(GoogleBooksApi.class);
        networkManager = new NetworkManager(mock(HttpClient.class), mockGoogleBooksApi);
        eventsCollectorRule.eventsCollector.reset();
    }

    @Test
    public void handleGoogleApiSearchRequestEvent_emptyResult() {
        /*
         * The stubbed methods return a CompletableFuture that is already completed, so the chained
         * calls within NetworkManager will be executed synchronously. We are therefore guaranteed
         * that the *ResultEvent will have already been raised when the methods we are testing returns.
         */
        when(mockGoogleBooksApi.searchBooks(PARAM_EMPTY_RESULT))
                .thenReturn(CompletableFuture.completedFuture(new BookShelf()));

        networkManager.handleGoogleApiSearchRequestEvent(new ApiSearchRequestEvent(PARAM_EMPTY_RESULT));
        ApiSearchResultEvent event =
                (ApiSearchResultEvent) eventsCollectorRule.eventsCollector.getMostRecent(ApiSearchResultEvent.class);

        verify(mockGoogleBooksApi).searchBooks(PARAM_EMPTY_RESULT);
        assertEquals(ResultOutcome.SUCCESS, event.outcome);
        assertEquals(0, event.bookShelf.getBookList().size());
    }

    @Test
    public void handleGoogleApiSearchRequestEvent_success() {
        when(mockGoogleBooksApi.searchBooks(PARAM_SUCCESS))
                .thenReturn(CompletableFuture.completedFuture(TypicalBooks.getTypicalBookShelf()));

        networkManager.handleGoogleApiSearchRequestEvent(new ApiSearchRequestEvent(PARAM_SUCCESS));
        ApiSearchResultEvent event =
                (ApiSearchResultEvent) eventsCollectorRule.eventsCollector.getMostRecent(ApiSearchResultEvent.class);

        verify(mockGoogleBooksApi).searchBooks(PARAM_SUCCESS);
        assertEquals(ResultOutcome.SUCCESS, event.outcome);
        assertEquals(5, event.bookShelf.getBookList().size());
    }

    @Test
    public void handleGoogleApiSearchRequestEvent_failure() {
        when(mockGoogleBooksApi.searchBooks(PARAM_FAILURE))
                .thenReturn(getFailedFuture());

        networkManager.handleGoogleApiSearchRequestEvent(new ApiSearchRequestEvent(PARAM_FAILURE));
        ApiSearchResultEvent event =
                (ApiSearchResultEvent) eventsCollectorRule.eventsCollector.getMostRecent(ApiSearchResultEvent.class);

        verify(mockGoogleBooksApi).searchBooks(PARAM_FAILURE);
        assertEquals(ResultOutcome.FAILURE, event.outcome);
        assertEquals(null, event.bookShelf);
    }

    @Test
    public void handleGoogleApiBookDetailsRequestEvent_success() {
        when(mockGoogleBooksApi.getBookDetails(PARAM_SUCCESS))
                .thenReturn(CompletableFuture.completedFuture(TypicalBooks.ARTEMIS));

        networkManager.handleGoogleApiBookDetailsRequestEvent(new ApiBookDetailsRequestEvent(PARAM_SUCCESS));
        ApiBookDetailsResultEvent event = (ApiBookDetailsResultEvent)
                eventsCollectorRule.eventsCollector.getMostRecent(ApiBookDetailsResultEvent.class);

        verify(mockGoogleBooksApi).getBookDetails(PARAM_SUCCESS);
        assertEquals(ResultOutcome.SUCCESS, event.outcome);
        assertEquals(TypicalBooks.ARTEMIS, event.book);
    }

    @Test
    public void handleGoogleApiBookDetailsRequestEvent_failure() {
        when(mockGoogleBooksApi.getBookDetails(PARAM_FAILURE))
                .thenReturn(getFailedFuture());

        networkManager.handleGoogleApiBookDetailsRequestEvent(new ApiBookDetailsRequestEvent(PARAM_FAILURE));
        ApiBookDetailsResultEvent event = (ApiBookDetailsResultEvent)
                eventsCollectorRule.eventsCollector.getMostRecent(ApiBookDetailsResultEvent.class);

        verify(mockGoogleBooksApi).getBookDetails(PARAM_FAILURE);
        assertEquals(ResultOutcome.FAILURE, event.outcome);
        assertEquals(null, event.book);
    }

    /**
     * Returns a {@link CompletableFuture} that has already completed exceptionally.
     */
    private static <T> CompletableFuture<T> getFailedFuture() {
        return CompletableFuture.completedFuture(null).thenApply(obj -> {
            throw new CompletionException(new IOException());
        });
    }

}
