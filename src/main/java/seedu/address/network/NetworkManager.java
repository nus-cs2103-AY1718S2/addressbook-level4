package seedu.address.network;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.network.ApiBookDetailsRequestEvent;
import seedu.address.commons.events.network.ApiBookDetailsResultEvent;
import seedu.address.commons.events.network.ApiSearchRequestEvent;
import seedu.address.commons.events.network.ApiSearchResultEvent;
import seedu.address.commons.events.network.ResultOutcome;
import seedu.address.commons.util.StringUtil;
import seedu.address.network.api.google.GoogleBooksApi;

/**
 * Provides networking functionality (making web API calls).
 *
 * No web API methods are directly exposed on this class. To make a web API call,
 * raise the corresponding *RequestEvent. To receive the results of the call,
 * handle the corresponding *ResultEvent.
 */
public class NetworkManager extends ComponentManager implements Network {

    private static final Logger logger = LogsCenter.getLogger(NetworkManager.class);

    private final HttpClient httpClient;
    private final GoogleBooksApi googleBooksApi;

    public NetworkManager() {
        super();
        httpClient = new HttpClient();
        googleBooksApi = new GoogleBooksApi(httpClient);
    }

    protected NetworkManager(HttpClient httpClient, GoogleBooksApi googleBooksApi) {
        super();
        requireAllNonNull(httpClient, googleBooksApi);
        this.httpClient = httpClient;
        this.googleBooksApi = googleBooksApi;
    }

    @Override
    public void stop() {
        httpClient.close();
    }

    @Subscribe
    protected void handleGoogleApiSearchRequestEvent(ApiSearchRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        googleBooksApi.searchBooks(event.searchParameters)
                .thenApply(bookShelf -> {
                    raise(new ApiSearchResultEvent(ResultOutcome.SUCCESS, bookShelf));
                    return bookShelf;
                })
                .exceptionally(e -> {
                    logger.warning("Search request failed: " + StringUtil.getDetails(e));
                    raise(new ApiSearchResultEvent(ResultOutcome.FAILURE, null));
                    return null;
                });
    }

    @Subscribe
    protected void handleGoogleApiBookDetailsRequestEvent(ApiBookDetailsRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        googleBooksApi.getBookDetails(event.bookId)
                .thenApply(book -> {
                    raise(new ApiBookDetailsResultEvent(ResultOutcome.SUCCESS, book));
                    return book;
                })
                .exceptionally(e -> {
                    logger.warning("Book detail request failed: " + StringUtil.getDetails(e));
                    raise(new ApiBookDetailsResultEvent(ResultOutcome.FAILURE, null));
                    return null;
                });
    }
}
