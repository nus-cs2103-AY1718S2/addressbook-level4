package seedu.address.network;

import java.io.IOException;
import java.util.logging.Logger;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Dsl;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.network.GoogleApiSearchRequestEvent;
import seedu.address.commons.events.network.GoogleApiSearchResultEvent;
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

    private final AsyncHttpClient httpClient;
    private final GoogleBooksApi googleBooksApi;

    public NetworkManager() {
        super();
        httpClient = Dsl.asyncHttpClient(Dsl.config()
                .setConnectTimeout(CONNECTION_TIMEOUT_MILLIS)
                .setReadTimeout(READ_TIMEOUT_MILLIS)
                .setRequestTimeout(REQUEST_TIMEOUT_MILLIS));
        googleBooksApi = new GoogleBooksApi(httpClient);
    }

    @Override
    public void stop() {
        try {
            if (!httpClient.isClosed()) {
                httpClient.close();
            }
        } catch (IOException e) {
            logger.warning("Failed to shut down AsyncHttpClient.");
        }
    }

    @Subscribe
    private void handleGoogleApiSearchRequestEvent(GoogleApiSearchRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        googleBooksApi.searchBooks(event.searchParameters)
                .thenApply(bookShelf -> {
                    raise(new GoogleApiSearchResultEvent(ResultOutcome.SUCCESS, bookShelf));
                    return bookShelf;
                })
                .exceptionally(e -> {
                    logger.warning("Search request failed: " + StringUtil.getDetails(e));
                    raise(new GoogleApiSearchResultEvent(ResultOutcome.FAILURE, null));
                    return null;
                });
    }
}
