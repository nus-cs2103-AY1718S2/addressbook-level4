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
import seedu.address.model.book.Gid;
import seedu.address.model.book.Isbn;
import seedu.address.model.book.PublicationDate;
import seedu.address.model.book.Publisher;
import seedu.address.model.book.Rating;
import seedu.address.model.book.Title;

/**
 * JAXB-friendly version of the Book.
 */
public class XmlAdaptedBook {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    @XmlElement(required = true)
    private String gid;
    @XmlElement(required = true)
    private String isbn;
    @XmlElement(required = true)
    private String title;
    @XmlElement(required = true)
    private String description;
    @XmlElement(required = true)
    private Integer rating;
    @XmlElement(required = true)
    private String publisher;
    @XmlElement(required = true)
    private String publicationDate;

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
    public XmlAdaptedBook(String gid, String isbn, String title, String description,
                           List<XmlAdaptedAuthor> authors, List<XmlAdaptedCategory> categories,
                           String publisher, String publicationDate) {
        this.title = title;
        this.description = description;
        this.rating = -1;
        if (authors != null) {
            this.authors = new ArrayList<>(authors);
        }
        if (categories != null) {
            this.categories = new ArrayList<>(categories);
        }
        this.gid = gid;
        this.isbn = isbn;
        this.publicationDate = publicationDate;
        this.publisher = publisher;
    }

    public XmlAdaptedBook(String gid, String isbn, String title, String description, Integer rate,
                          List<XmlAdaptedAuthor> authors, List<XmlAdaptedCategory> categories,
                          String publisher, String publicationDate) {
        this.title = title;
        this.description = description;
        this.rating = -1;
        if (authors != null) {
            this.authors = new ArrayList<>(authors);
        }
        if (categories != null) {
            this.categories = new ArrayList<>(categories);
        }
        this.rating = rate;
        this.gid = gid;
        this.isbn = isbn;
        this.publicationDate = publicationDate;
        this.publisher = publisher;
    }

    /**
     * Converts a given Book into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedBook.
     */
    public XmlAdaptedBook(Book source) {
        gid = source.getGid().gid;
        isbn = source.getIsbn().isbn;
        title = source.getTitle().title;
        description = source.getDescription().description;
        rating = source.getRating().rating;
        authors = new ArrayList<>();
        for (Author author : source.getAuthors()) {
            authors.add(new XmlAdaptedAuthor(author));
        }
        categories = new ArrayList<>();
        for (Category category : source.getCategories()) {
            categories.add(new XmlAdaptedCategory(category));
        }
        publisher = source.getPublisher().publisher;
        publicationDate = source.getPublicationDate().date;
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
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Title.class.getSimpleName()));
        }
        final Title title = new Title(this.title);

        if (this.description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Description.class.getSimpleName()));
        }
        final Description description = new Description(this.description);

        if (this.rating == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Rating.class.getSimpleName()));
        }
        final Rating rating = new Rating(this.rating);

        if (this.gid == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Gid.class.getSimpleName()));
        }
        final Gid gid = new Gid(this.gid);

        if (this.isbn == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Isbn.class.getSimpleName()));
        }
        final Isbn isbn = new Isbn(this.isbn);

        if (this.publisher == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Publisher.class.getSimpleName()));
        }
        final Publisher publisher = new Publisher(this.publisher);

        if (this.publicationDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    PublicationDate.class.getSimpleName()));
        }
        final PublicationDate publicationDate = new PublicationDate(this.publicationDate);

        return new Book(gid, isbn, new HashSet<>(bookAuthors), title,
                new HashSet<>(bookCategories), description, rating, publisher, publicationDate);
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
                && Objects.equals(rating, otherBook.rating)
                && authors.equals(otherBook.authors)
                && categories.equals(otherBook.categories)
                && Objects.equals(gid, otherBook.gid)
                && Objects.equals(isbn, otherBook.isbn)
                && Objects.equals(publisher, otherBook.publisher)
                && Objects.equals(publicationDate, otherBook.publicationDate);
    }
}
