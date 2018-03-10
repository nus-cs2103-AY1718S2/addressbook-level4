package seedu.address.network.api.google;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import seedu.address.model.book.Book;
import seedu.address.model.book.Description;
import seedu.address.model.book.Title;
import seedu.address.model.util.BookDataUtil;

/**
 * Custom Jackson deserializer for deserializing JSON to book.
 */
public class BookDeserializer extends StdDeserializer<Book> {

    BookDeserializer() {
        this(null);
    }

    BookDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Book deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonRoot root = jp.readValueAs(JsonRoot.class);
        JsonVolumeInfo volumeInfo = root.volumeInfo;

        return new Book(BookDataUtil.getAuthorSet(volumeInfo.authors), new Title(volumeInfo.title),
                BookDataUtil.getCategorySet(volumeInfo.categories), new Description(volumeInfo.description));
    }

    /** Temporary data holder used for deserialization. */
    private static class JsonRoot {
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
