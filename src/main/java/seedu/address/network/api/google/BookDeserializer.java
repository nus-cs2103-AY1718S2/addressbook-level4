package seedu.address.network.api.google;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import seedu.address.model.book.Book;
import seedu.address.model.book.Category;
import seedu.address.model.book.Description;
import seedu.address.model.book.Gid;
import seedu.address.model.book.Isbn;
import seedu.address.model.book.PublicationDate;
import seedu.address.model.book.Publisher;
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

        return new Book(new Gid(root.id), getIsbnFromIndustryIdentifiers(volumeInfo.industryIdentifiers),
                BookDataUtil.getAuthorSet(volumeInfo.authors), new Title(volumeInfo.title),
                getCategorySet(volumeInfo.categories), getDescription(volumeInfo.description),
                new Publisher(volumeInfo.publisher), new PublicationDate(volumeInfo.publishedDate));
    }

    private Isbn getIsbnFromIndustryIdentifiers(JsonIndustryIdentifiers[] iiArray) {
        for (JsonIndustryIdentifiers ii: iiArray) {
            if ("ISBN_13".equals(ii.type)) {
                return new Isbn(ii.identifier);
            }
        }
        return new Isbn("");
    }

    private Set<Category> getCategorySet(String[] categories) {
        Set<Category> categorySet = new HashSet<>();
        for (String category: categories) {
            String[] splitCats = category.split("/");
            for (String token: splitCats) {
                categorySet.add(new Category(token.trim()));
            }
        }
        return categorySet;
    }

    private Description getDescription(String description) {
        return new Description(description.replaceAll("<br>", "\n"));
    }

    /** Temporary data holder used for deserialization. */
    private static class JsonRoot {
        private int error = 0;
        private String id = "";
        private JsonVolumeInfo volumeInfo = new JsonVolumeInfo();

        // This should fail if the API returns an error, due to incompatible types.
        public void setError(int error) {
            this.error = error;
        }

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
        private JsonIndustryIdentifiers[] industryIdentifiers = new JsonIndustryIdentifiers[0];
        private String[] categories = new String[0];

        public void setIndustryIdentifiers(JsonIndustryIdentifiers[] industryIdentifiers) {
            this.industryIdentifiers = industryIdentifiers;
        }

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

    /** Temporary data holder used for deserialization. */
    private static class JsonIndustryIdentifiers {
        private String type;
        private String identifier;

        public void setIdentifier(String identifier) {
            this.identifier = identifier;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

}
