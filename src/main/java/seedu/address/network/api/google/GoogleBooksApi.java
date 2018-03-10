package seedu.address.network.api.google;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

import org.asynchttpclient.AsyncHttpClient;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.ReadOnlyBookShelf;
import seedu.address.model.book.Book;

/**
 * Provides access to the Google Books API.
 */
public class GoogleBooksApi {

    private static final String CONTENT_TYPE_JSON = "application/json";
    private static final String URL_SEARCH_BOOKS =
            "https://www.googleapis.com/books/v1/volumes?maxResults=30&printType=books&q=%s";
    private static final String URL_BOOK_DETAILS = "https://www.googleapis.com/books/v1/volumes/%s";

    private final AsyncHttpClient httpClient;
    private final JsonDeserializer deserializer;

    public GoogleBooksApi(AsyncHttpClient httpClient) {
        this.httpClient = httpClient;
        this.deserializer = new JsonDeserializer();
    }

    /**
     * Searches for books on Google Books that matches a set of parameters.
     *
     * @param parameters search parameters.
     * @return a CompletableFuture that resolves to a ReadOnlyBookShelf.
     */
    public CompletableFuture<ReadOnlyBookShelf> searchBooks(String parameters) {
        String requestUrl = URL_SEARCH_BOOKS.replace("%s", StringUtil.urlEncode(parameters));
        return executeGetAndApply(requestUrl, deserializer::convertJsonStringToBookShelf);
    }

    /**
     * Retrieves the details of a single book on Google Books.
     *
     * @param bookId the ID of the book on Google Books.
     * @return a CompletableFuture that resolves to a Book.
     */
    public CompletableFuture<Book> getBookDetails(String bookId) {
        String requestUrl = URL_BOOK_DETAILS.replace("%s", StringUtil.urlEncode(bookId));
        return executeGetAndApply(requestUrl, deserializer::convertJsonStringToBook);
    }

    /**
     * Asynchronously executes a HTTP GET request to the specified url and
     * applies the specified function to transform the resulting response body.
     */
    private <T> CompletableFuture<T> executeGetAndApply(String url, Function<String, ? extends T> fn) {
        return httpClient
                .prepareGet(url)
                .execute()
                .toCompletableFuture()
                .thenApply(response -> {
                    assert response.getContentType().startsWith(CONTENT_TYPE_JSON);
                    return response.getResponseBody();
                })
                .thenApply(fn);
    }
}
