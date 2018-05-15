package seedu.address.network;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.logging.Logger;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Dsl;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.StringUtil;

//@@author takuyakanbr
/**
 * A wrapper around the {@link AsyncHttpClient} class from async-http-client.
 */
public class HttpClient {

    private static final Logger logger = LogsCenter.getLogger(HttpClient.class);
    private static final int CONNECTION_TIMEOUT_MILLIS = 1000 * 10; // 10 seconds
    private static final int READ_TIMEOUT_MILLIS = 1000 * 10; // 10 seconds
    private static final int REQUEST_TIMEOUT_MILLIS = 1000 * 10; // 10 seconds
    private static final long DELAY_ON_EXCEPTION = 300L;

    private final AsyncHttpClient asyncHttpClient;

    protected HttpClient() {
        this.asyncHttpClient = Dsl.asyncHttpClient(Dsl.config()
                .setConnectTimeout(CONNECTION_TIMEOUT_MILLIS)
                .setReadTimeout(READ_TIMEOUT_MILLIS)
                .setRequestTimeout(REQUEST_TIMEOUT_MILLIS));
    }

    protected HttpClient(AsyncHttpClient asyncHttpClient) {
        requireNonNull(asyncHttpClient);
        this.asyncHttpClient = asyncHttpClient;
    }

    /**
     * Asynchronously executes a HTTP GET request to the specified url.
     */
    public CompletableFuture<HttpResponse> makeGetRequest(String url) {
        return asyncHttpClient
                .prepareGet(url)
                .execute()
                .toCompletableFuture()
                .thenApply(HttpResponse::new)
                .handleAsync(HttpClient::waitOnException);
    }

    /**
     * Waits for a short period of time in the event of exception.
     * Prevents API methods from completing before necessary pre-processing is completed.
     */
    private static <T> T waitOnException(T result, Throwable error) {
        if (error != null) {
            try {
                Thread.sleep(DELAY_ON_EXCEPTION);
            } catch (InterruptedException ie) {
                logger.warning(StringUtil.getDetails(ie));
            }
            throw new CompletionException(error);
        }
        return result;
    }

    /**
     * Stops and closes the underlying {@link AsyncHttpClient}.
     */
    public void close() {
        try {
            if (!asyncHttpClient.isClosed()) {
                asyncHttpClient.close();
            }
        } catch (IOException e) {
            logger.warning("Failed to shut down AsyncHttpClient.");
        }
    }
}
