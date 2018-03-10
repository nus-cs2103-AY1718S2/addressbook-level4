package seedu.address.network;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

import org.asynchttpclient.AsyncHttpClient;

import seedu.address.commons.core.LogsCenter;

/**
 * A wrapper around the AsyncHttpClient class from async-http-client.
 */
public class HttpClient {

    private static final Logger logger = LogsCenter.getLogger(HttpClient.class);

    private final AsyncHttpClient asyncHttpClient;

    public HttpClient(AsyncHttpClient asyncHttpClient) {
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
                .thenApply(HttpResponse::new);
    }

    /**
     * Stops and closes the underlying AsyncHttpClient.
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
