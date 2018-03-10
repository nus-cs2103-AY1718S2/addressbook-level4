package seedu.address.network.api.google;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.CompletionException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.util.FileUtil;
import seedu.address.model.ReadOnlyBookShelf;
import seedu.address.model.book.Book;

public class JsonDeserializerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private JsonDeserializer deserializer = new JsonDeserializer();

    @Test
    public void convertJsonStringToBook_validResponse_success() throws Exception {
        String json = FileUtil.readFromFile(BookDeserializerTest.VALID_RESPONSE_FILE);
        Book book = deserializer.convertJsonStringToBook(json);
        assertEquals("The Book Without a Title", book.getTitle().title);
        assertEquals("This is a valid description.", book.getDescription().description);
    }

    @Test
    public void convertJsonStringToBook_validResponseNoDesc_success() throws Exception {
        String json = FileUtil.readFromFile(BookDeserializerTest.VALID_RESPONSE_NO_DESC_FILE);
        Book book = deserializer.convertJsonStringToBook(json);
        assertEquals("The Book Without a Title", book.getTitle().title);
        assertEquals("", book.getDescription().description);
    }

    @Test
    public void convertJsonStringToBook_invalidResponseWrongType_throwsCompletionException() throws Exception {
        thrown.expect(CompletionException.class);
        String json = FileUtil.readFromFile(BookDeserializerTest.INVALID_RESPONSE_WRONG_TYPE_FILE);
        deserializer.convertJsonStringToBook(json);
    }

    @Test
    public void convertJsonStringToBook_errorResponse_throwsCompletionException() throws Exception {
        thrown.expect(CompletionException.class);
        String json = FileUtil.readFromFile(BookDeserializerTest.ERROR_RESPONSE_FILE);
        deserializer.convertJsonStringToBook(json);
    }

    @Test
    public void convertJsonStringToBookShelf_validResponse_success() throws Exception {
        String json = FileUtil.readFromFile(BookShelfDeserializerTest.VALID_RESPONSE_FILE);
        ReadOnlyBookShelf bookShelf = deserializer.convertJsonStringToBookShelf(json);
        Book book1 = bookShelf.getBookList().get(0);
        assertEquals("The Book Without a Title", book1.getTitle().title);
        assertEquals("This is a valid description.", book1.getDescription().description);
    }

    @Test
    public void convertJsonStringToBookShelf_validResponseNoId_success() throws Exception {
        String json = FileUtil.readFromFile(BookShelfDeserializerTest.VALID_RESPONSE_NO_ID_FILE);
        ReadOnlyBookShelf bookShelf = deserializer.convertJsonStringToBookShelf(json);
        Book book1 = bookShelf.getBookList().get(0);
        assertEquals("The Book Without a Title", book1.getTitle().title);
        assertEquals("", book1.getDescription().description);
        Book book2 = bookShelf.getBookList().get(1);
        assertEquals("The Book Without a Title 2", book2.getTitle().title);
        assertEquals("", book2.getDescription().description);
    }

    @Test
    public void convertJsonStringToBookShelf_invalidResponseWrongType_throwsCompletionException() throws Exception {
        thrown.expect(CompletionException.class);
        String json = FileUtil.readFromFile(BookShelfDeserializerTest.INVALID_RESPONSE_WRONG_TYPE_FILE);
        deserializer.convertJsonStringToBookShelf(json);
    }

    @Test
    public void convertJsonStringToBookShelf_errorResponse_throwsCompletionException() throws Exception {
        thrown.expect(CompletionException.class);
        String json = FileUtil.readFromFile(BookShelfDeserializerTest.ERROR_RESPONSE_FILE);
        deserializer.convertJsonStringToBookShelf(json);
    }

}
