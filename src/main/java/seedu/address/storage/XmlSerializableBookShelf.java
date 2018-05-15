package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.BookShelf;
import seedu.address.model.ReadOnlyBookShelf;

/**
 * An Immutable BookShelf that is serializable to XML format
 */
@XmlRootElement(name = "bookshelf")
public class XmlSerializableBookShelf {

    @XmlElement
    private List<XmlAdaptedBook> books;

    /**
     * Creates an empty XmlSerializableBookShelf.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableBookShelf() {
        books = new ArrayList<>();
    }

    /**
     * Converts a given BookShelf into this class for JAXB use.
     */
    public XmlSerializableBookShelf(ReadOnlyBookShelf src) {
        this();
        books.addAll(src.getBookList().stream().map(XmlAdaptedBook::new).collect(Collectors.toList()));
    }

    /**
     * Converts this bookshelf into the model's {@code BookShelf} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedBook}.
     */
    public BookShelf toModelType() throws IllegalValueException {
        BookShelf bookShelf = new BookShelf();
        for (XmlAdaptedBook b : books) {
            bookShelf.addBook(b.toModelType());
        }
        return bookShelf;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableBookShelf)) {
            return false;
        }

        XmlSerializableBookShelf otherBookShelf = (XmlSerializableBookShelf) other;
        return books.equals(otherBookShelf.books);
    }
}
