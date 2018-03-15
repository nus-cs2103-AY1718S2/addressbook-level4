package seedu.address.network;

import java.util.logging.Logger;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Dsl;

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
 * Provides networking functionality (making API calls).
 *
 * No API methods are directly exposed on this class. To make an API call,
 * raise the corresponding *RequestEvent. To receive the results of the call,
 * handle the corresponding *ResultEvent.
 */
public class NetworkManager extends ComponentManager implements Network {

    private static final Logger logger = LogsCenter.getLogger(NetworkManager.class);
    private static final int CONNECTION_TIMEOUT_MILLIS = 1000 * 5; // 5 seconds
    private static final int READ_TIMEOUT_MILLIS = 1000 * 5; // 5 seconds
    private static final int REQUEST_TIMEOUT_MILLIS = 1000 * 5; // 5 seconds

    private final HttpClient httpClient;
    private final GoogleBooksApi googleBooksApi;

    public NetworkManager() {
        super();
        AsyncHttpClient asyncHttpClient = Dsl.asyncHttpClient(Dsl.config()
                .setConnectTimeout(CONNECTION_TIMEOUT_MILLIS)
                .setReadTimeout(READ_TIMEOUT_MILLIS)
                .setRequestTimeout(REQUEST_TIMEOUT_MILLIS));
        httpClient = new HttpClient(asyncHttpClient);
        googleBooksApi = new GoogleBooksApi(httpClient);
    }

    protected NetworkManager(HttpClient httpClient, GoogleBooksApi googleBooksApi) {
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
