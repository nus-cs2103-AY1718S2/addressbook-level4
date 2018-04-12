package seedu.address.storage;

import java.util.HashMap;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Menu;
import seedu.address.model.ReadOnlyMenu;
import seedu.address.model.dish.Dish;

/**
 * An Immutable Menu that is serializable to XML format
 */
@XmlRootElement(name = "menu")
public class XmlSerializableMenu {

    @XmlElement
    private HashMap<String, Dish> dishes;

    /**
     * Creates an empty XmlSerializableMenu.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableMenu() {
        dishes = new HashMap<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableMenu(ReadOnlyMenu src) {
        this();
        dishes = src.getDishes();
    }

    /**
     * Converts this menu into the model's {@code Menu} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedPerson} or {@code XmlAdaptedTag}.
     */
    public Menu toModelType() throws IllegalValueException {
        Menu menu = new Menu();
        menu.setMenu(dishes);
        return menu;
    }

}
