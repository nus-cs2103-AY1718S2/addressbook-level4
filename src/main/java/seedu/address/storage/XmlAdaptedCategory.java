package seedu.address.storage;

import javax.xml.bind.annotation.XmlValue;

import seedu.address.model.book.Category;

/**
 * JAXB-friendly adapted version of the Category.
 */
public class XmlAdaptedCategory {

    @XmlValue
    private String category;

    /**
     * Constructs an XmlAdaptedCategory.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedCategory() {}

    /**
     * Constructs a {@code XmlAdaptedCategory} with the given {@code category}.
     */
    public XmlAdaptedCategory(String category) {
        this.category = category;
    }

    /**
     * Converts a given Category into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created.
     */
    public XmlAdaptedCategory(Category source) {
        category = source.category;
    }

    /**
     * Converts this jaxb-friendly adapted category object into the model's Category object.
     */
    public Category toModelType() {
        return new Category(category);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedCategory)) {
            return false;
        }

        return category.equals(((XmlAdaptedCategory) other).category);
    }
}
