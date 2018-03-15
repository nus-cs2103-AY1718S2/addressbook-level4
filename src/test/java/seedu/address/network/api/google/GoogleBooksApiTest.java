package seedu.address.network.api.google;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.util.FileUtil;
import seedu.address.model.ReadOnlyBookShelf;
import seedu.address.model.book.Book;
import seedu.address.network.HttpClient;
import seedu.address.network.HttpResponse;

public class GoogleBooksApiTest {

    private static final String URL_SEARCH_BOOKS_OK = String.format(GoogleBooksApi.URL_SEARCH_BOOKS, "123");
    private static final String URL_BOOK_DETAILS_OK = String.format(GoogleBooksApi.URL_BOOK_DETAILS, "123");
    private static final String URL_SEARCH_BOOKS_FAIL = String.format(GoogleBooksApi.URL_SEARCH_BOOKS, "");
    private static final String URL_BOOK_DETAILS_FAIL = String.format(GoogleBooksApi.URL_BOOK_DETAILS, "");
    private static final String URL_SEARCH_BOOKS_BAD_RESPONSE = String.format(GoogleBooksApi.URL_SEARCH_BOOKS, "html");
    private static final String URL_BOOK_DETAILS_BAD_RESPONSE = String.format(GoogleBooksApi.URL_BOOK_DETAILS, "html");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private GoogleBooksApi googleBooksApi;

    @Before
    public void setUp() {
        googleBooksApi = new GoogleBooksApi(new HttpClientStub());
    }

    @Test
    public void searchBooks_validParam_success() {
        ReadOnlyBookShelf bookShelf = googleBooksApi.searchBooks("123").join();
        Book book1 = bookShelf.getBookList().get(0);
        assertEquals("The Book Without a Title", book1.getTitle().title);
        assertEquals("This is a valid description.", book1.getDescription().description);
    }

    @Test
    public void searchBooks_invalidParam_throwsCompletionException() {
        thrown.expect(CompletionException.class);
        googleBooksApi.searchBooks("").join();
    }

    @Test
    public void searchBooks_badResponseType_throwsCompletionException() {
        thrown.expect(CompletionException.class);
        googleBooksApi.searchBooks("html").join();
    }

    @Test
    public void getBookDetails_validId_success() {
        Book book = googleBooksApi.getBookDetails("123").join();
        assertEquals("The Book Without a Title", book.getTitle().title);
        assertEquals("This is a valid description.", book.getDescription().description);
    }

    @Test
    public void getBookDetails_invalidId_throwsCompletionException() {
        thrown.expect(CompletionException.class);
        googleBooksApi.getBookDetails("").join();
    }

    @Test
    public void getBookDetails_badResponseType_throwsCompletionException() {
        thrown.expect(CompletionException.class);
        googleBooksApi.getBookDetails("html").join();
    }

    /** A stub HttpClient that returns preset responses when given certain urls, and null for other urls. */
    private static class HttpClientStub extends HttpClient {

        public HttpClientStub() {
            super(null);
        }

        @Override
        public CompletableFuture<HttpResponse> makeGetRequest(String url) {
            try {
                if (url.equals(URL_SEARCH_BOOKS_OK)) {
                    return CompletableFuture.completedFuture(makeResponse(200,
                            FileUtil.readFromFile(BookShelfDeserializerTest.VALID_RESPONSE_FILE)));
                } else if (url.equals(URL_BOOK_DETAILS_OK)) {
                    return CompletableFuture.completedFuture(makeResponse(200,
                            FileUtil.readFromFile(BookDeserializerTest.VALID_RESPONSE_FILE)));
                } else if (url.equals(URL_SEARCH_BOOKS_FAIL) || url.equals(URL_BOOK_DETAILS_FAIL)) {
                    return CompletableFuture.completedFuture(makeResponse(503, "{ \"error\": { \"code\": 503 } }"));
                } else if (url.equals(URL_SEARCH_BOOKS_BAD_RESPONSE) || url.equals(URL_BOOK_DETAILS_BAD_RESPONSE)) {
                    return CompletableFuture.completedFuture(
                            new HttpResponse(503, "text/html;", "{ \"error\": { \"code\": 503 } }"));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        private HttpResponse makeResponse(int code, String body) {
            return new HttpResponse(code, "application/json;", body);
        }
    }
}
