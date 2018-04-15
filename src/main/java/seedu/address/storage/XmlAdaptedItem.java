//@@author chenchongsong
package seedu.address.storage;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.item.Item;

/**
 * JAXB-friendly adapted version of the Item.
 */
public class XmlAdaptedItem {

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String value;

    /**
     * Constructs an XmlAdaptedItem.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedItem() {}

    /**
     * Constructs a {@code XmlAdaptedItem} with the given {@code itemName}.
     */
    public XmlAdaptedItem(String itemName, String itemValue) {
        this.name = itemName;
        this.value = itemValue;
    }

    /**
     * Converts a given item into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedItem(Item source) {
        name = source.getItemName();
        value = source.getItemValue();
    }

    /**
     * Converts this jaxb-friendly adapted item object into the model's Item object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Item toModelType() throws IllegalValueException {
        if (!Item.isValidItemName(name)) {
            throw new IllegalValueException(Item.MESSAGE_ITEMNAME_CONSTRAINTS);
        }
        if (!Item.isValidItemValue(value)) {
            throw new IllegalValueException(Item.MESSAGE_ITEMVALUE_CONSTRAINTS);
        }
        return new Item(name, value);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof XmlAdaptedItem)) {
            return false;
        }
        return name.equals(((XmlAdaptedItem) other).name);
    }

}
