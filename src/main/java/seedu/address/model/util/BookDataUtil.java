package seedu.address.model.util;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.book.Author;
import seedu.address.model.book.Category;

/**
 * Contains utility methods for manipulating book data.
 */
public class BookDataUtil {

    /**
     * Returns an author set containing the list of strings given.
     */
    public static Set<Author> getAuthorSet(String... strings) {
        HashSet<Author> authors = new HashSet<>();
        for (String s : strings) {
            authors.add(new Author(s));
        }

        return authors;
    }

    /**
     * Returns a category set containing the list of strings given.
     */
    public static Set<Category> getCategorySet(String... strings) {
        HashSet<Category> categories = new HashSet<>();
        for (String s : strings) {
            categories.add(new Category(s));
        }

        return categories;
    }

}
