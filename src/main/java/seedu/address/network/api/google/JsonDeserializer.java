package seedu.address.network.api.google;

import java.io.IOException;
import java.util.concurrent.CompletionException;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.BookShelf;
import seedu.address.model.ReadOnlyBookShelf;
import seedu.address.model.book.Book;

/**
 * Provides utilities to deserialize JSON responses.
 */
public class JsonDeserializer {

    private static final Logger logger = LogsCenter.getLogger(JsonDeserializer.class);

    private final ObjectMapper mapper;

    JsonDeserializer() {
        mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Book.class, new BookDeserializer());
        module.addDeserializer(BookShelf.class, new BookShelfDeserializer());
        mapper.registerModule(module);
    }

    /**
     * Converts the JSON string from Google Books API into a book.
     */
    public Book convertJsonStringToBook(String json) {
        try {
            return mapper.readValue(json, Book.class);
        } catch (IOException e) {
            logger.warning("Failed to convert JSON to book.");
            throw new CompletionException(e);
        }
    }

    /**
     * Converts the JSON string from Google Books API into a book shelf.
     */
    public ReadOnlyBookShelf convertJsonStringToBookShelf(String json) {
        try {
            return mapper.readValue(json, BookShelf.class);
        } catch (IOException e) {
            logger.warning("Failed to convert JSON to book shelf.");
            throw new CompletionException(e);
        }
    }
}
