package seedu.address.network.api.google;

import java.io.IOException;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.BookShelf;
import seedu.address.model.book.Book;
import seedu.address.model.book.Description;
import seedu.address.model.book.Title;
import seedu.address.model.book.exceptions.DuplicateBookException;
import seedu.address.model.util.BookDataUtil;

/**
 * Custom Jackson deserializer for deserializing JSON to book shelf.
 */
public class BookShelfDeserializer extends StdDeserializer<BookShelf> {

    private static final Logger logger = LogsCenter.getLogger(BookShelfDeserializer.class);

    BookShelfDeserializer() {
        this(null);
    }

    BookShelfDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public BookShelf deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonRoot root = jp.readValueAs(JsonRoot.class);
        BookShelf bookShelf = new BookShelf();

        for (JsonVolume volume : root.items) {
            convertAndAddBook(bookShelf, volume);
        }
        return bookShelf;
    }

    /** Converts a JsonVolume into a Book, and add it into the book shelf. */
    private void convertAndAddBook(BookShelf bookShelf, JsonVolume volume) {
        JsonVolumeInfo volumeInfo = volume.volumeInfo;
        Book book = new Book(BookDataUtil.getAuthorSet(volumeInfo.authors), new Title(volumeInfo.title),
                BookDataUtil.getCategorySet(volumeInfo.categories), new Description(volumeInfo.description));
        try {
            bookShelf.addBook(book);
        } catch (DuplicateBookException e) {
            logger.warning("Found duplicate book when deserializing: " + book);
        }
    }

    /** Temporary data holder used for deserialization. */
    private static class JsonRoot {
        private int error = 0;
        private int totalItems = 0;
        private JsonVolume[] items = new JsonVolume[0];

        // This should fail if the API returns an error, due to incompatible types.
        public void setError(int error) {
            this.error = error;
        }

        public void setTotalItems(int totalItems) {
            this.totalItems = totalItems;
        }

        public void setItems(JsonVolume[] items) {
            this.items = items;
        }
    }

    /** Temporary data holder used for deserialization. */
    private static class JsonVolume {
        private String id = "";
        private JsonVolumeInfo volumeInfo = new JsonVolumeInfo();

        public void setId(String id) {
            this.id = id;
        }

        public void setVolumeInfo(JsonVolumeInfo volumeInfo) {
            this.volumeInfo = volumeInfo;
        }
    }

    /** Temporary data holder used for deserialization. */
    private static class JsonVolumeInfo {
        private String title = "";
        private String[] authors = new String[0];
        private String publisher = "";
        private String publishedDate = "";
        private String description = "";
        private String[] categories = new String[0];

        public void setTitle(String title) {
            this.title = title;
        }

        public void setAuthors(String[] authors) {
            this.authors = authors;
        }

        public void setPublisher(String publisher) {
            this.publisher = publisher;
        }

        public void setPublishedDate(String publishedDate) {
            this.publishedDate = publishedDate;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setCategories(String[] categories) {
            this.categories = categories;
        }
    }

}
