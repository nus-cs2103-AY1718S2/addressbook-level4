package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.book.Author;
import seedu.address.model.book.Book;
import seedu.address.model.book.Category;
import seedu.address.model.book.Description;
import seedu.address.model.book.Title;

/**
 * JAXB-friendly version of the Book.
 */
public class XmlAdaptedBook {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    @XmlElement(required = true)
    private String title;
    @XmlElement(required = true)
    private String description;

    @XmlElement
    private List<XmlAdaptedAuthor> authors = new ArrayList<>();
    @XmlElement
    private List<XmlAdaptedCategory> categories = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedBook.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedBook() {}

    /**
     * Constructs an {@code XmlAdaptedBook} with the given book details.
     */
    public XmlAdaptedBook(String title, String description, List<XmlAdaptedAuthor> authors,
                          List<XmlAdaptedCategory> categories) {
        this.title = title;
        this.description = description;
        if (authors != null) {
            this.authors = new ArrayList<>(authors);
        }
        if (categories != null) {
            this.categories = new ArrayList<>(categories);
        }
    }

    /**
     * Converts a given Book into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedBook.
     */
    public XmlAdaptedBook(Book source) {
        title = source.getTitle().title;
        description = source.getDescription().description;
        authors = new ArrayList<>();
        for (Author author : source.getAuthors()) {
            authors.add(new XmlAdaptedAuthor(author));
        }
        categories = new ArrayList<>();
        for (Category category : source.getCategories()) {
            categories.add(new XmlAdaptedCategory(category));
        }
    }

    /**
     * Converts this jaxb-friendly adapted book object into the model's Book object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted book.
     */
    public Book toModelType() throws IllegalValueException {
        final List<Author> bookAuthors = new ArrayList<>();
        for (XmlAdaptedAuthor author : authors) {
            bookAuthors.add(author.toModelType());
        }
        final List<Category> bookCategories = new ArrayList<>();
        for (XmlAdaptedCategory category : categories) {
            bookCategories.add(category.toModelType());
        }

        if (this.title == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Title.class.getSimpleName()));
        }
        final Title title = new Title(this.title);

        if (this.description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Description.class.getSimpleName()));
        }
        final Description description = new Description(this.description);

        return new Book(new HashSet<>(bookAuthors), title, new HashSet<>(bookCategories), description);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedBook)) {
            return false;
        }

        XmlAdaptedBook otherBook = (XmlAdaptedBook) other;
        return Objects.equals(title, otherBook.title)
                && Objects.equals(description, otherBook.description)
                && authors.equals(otherBook.authors)
                && categories.equals(otherBook.categories);
    }
}
