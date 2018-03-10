package seedu.address.network;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Response;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.ReadOnlyBookShelf;
import seedu.address.model.book.Book;
import seedu.address.network.json.JsonDeserializer;

/**
 * Provides access to the Google Books API.
 */
public class GoogleBooksApi {

    private static final String URL_SEARCH_BOOKS =
            "https://www.googleapis.com/books/v1/volumes?maxResults=30&printType=books&q=%s";
    private static final String URL_BOOK_DETAILS = "https://www.googleapis.com/books/v1/volumes/%s";

    private final AsyncHttpClient httpClient;

    public GoogleBooksApi(AsyncHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * Searches for books on Google Books that matches a set of parameters.
     *
     * @param parameters search parameters.
     * @return a CompletableFuture that resolves to a ReadOnlyBookShelf.
     */
    public CompletableFuture<ReadOnlyBookShelf> searchBooks(String parameters) {
        String requestUrl = URL_SEARCH_BOOKS.replace("%s", StringUtil.urlEncode(parameters));
        return executeGetAndApply(requestUrl, JsonDeserializer::convertJsonResponseToBookShelf);
    }

    /**
     * Retrieves the details of a single book on Google Books.
     *
     * @param bookId the ID of the book on Google Books.
     * @return a CompletableFuture that resolves to a Book.
     */
    public CompletableFuture<Book> getBookDetails(String bookId) {
        String requestUrl = URL_BOOK_DETAILS.replace("%s", StringUtil.urlEncode(bookId));
        return executeGetAndApply(requestUrl, JsonDeserializer::convertJsonResponseToBook);
    }

    /**
     * Asynchronously executes a HTTP GET request to the specified url and
     * applies the specified function to transform the resulting response.
     */
    private <T> CompletableFuture<T> executeGetAndApply(String url, Function<Response, ? extends T> fn) {
        return httpClient
                .prepareGet(url)
                .execute()
                .toCompletableFuture()
                .thenApply(fn);
    }
}
