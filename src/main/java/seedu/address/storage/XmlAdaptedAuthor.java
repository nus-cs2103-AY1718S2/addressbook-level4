package seedu.address.storage;

import javax.xml.bind.annotation.XmlValue;

import seedu.address.model.book.Author;

/**
 * JAXB-friendly adapted version of the Author.
 */
public class XmlAdaptedAuthor {

    @XmlValue
    private String authorName;

    /**
     * Constructs an XmlAdaptedAuthor.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedAuthor() {}

    /**
     * Constructs a {@code XmlAdaptedAuthor} with the given {@code authorName}.
     */
    public XmlAdaptedAuthor(String authorName) {
        this.authorName = authorName;
    }

    /**
     * Converts a given Author into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created.
     */
    public XmlAdaptedAuthor(Author source) {
        authorName = source.fullName;
    }

    /**
     * Converts this jaxb-friendly adapted author object into the model's Author object.
     */
    public Author toModelType() {
        return new Author(authorName);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedAuthor)) {
            return false;
        }

        return authorName.equals(((XmlAdaptedAuthor) other).authorName);
    }
}
