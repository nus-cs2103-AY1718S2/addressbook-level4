# ZacZequn
###### \java\seedu\address\commons\events\model\MenuChangedEvent.java
``` java
package seedu.address.commons.events.model;

import java.util.HashMap;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyMenu;
import seedu.address.model.dish.Dish;

/** Indicates the Menu in the model has changed*/
public class MenuChangedEvent extends BaseEvent {

    public final ReadOnlyMenu data;

    public MenuChangedEvent(ReadOnlyMenu data) {
        this.data = data;
    }

    @Override
    public String toString() {
        HashMap<String, Dish> theMenu = data.getDishes();
        return theMenu.toString();
    }
}
```
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
        public void setOrder(Order order) {
            this.order = order;
        }

        public Optional<Order> getOrder() {
            return Optional.ofNullable(order);
        }
```
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
        public void setHalal(Halal halal) {
            this.halal = halal;
        }

        public Optional<Halal> getHalal() {
            return Optional.ofNullable(halal);
        }

        public void setVegetarian(Vegetarian vegetarian) {
            this.vegetarian = vegetarian;
        }

        public Optional<Vegetarian> getVegetarian() {
            return Optional.ofNullable(vegetarian);
        }
```
###### \java\seedu\address\logic\parser\AddCommandParser.java
``` java
            Optional<Halal> halalOptional = ParserUtil.parseHalal(argMultimap.getValue(PREFIX_HALAL));
            Halal halal = halalOptional.isPresent() ? halalOptional.get() : new Halal(null);
            Optional<Vegetarian> vegetarianOptional =
                    ParserUtil.parseVegetarian(argMultimap.getValue(PREFIX_VEGETARIAN));
            Vegetarian vegetarian = vegetarianOptional.isPresent() ? vegetarianOptional.get() : new Vegetarian(null);
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code String order} into a {@code Order}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code order} is invalid.
     */
    public static Order parseOrder(String order) throws IllegalValueException {
        requireNonNull(order);
        String trimmedOrder = order.trim();
        if (!Order.isValidOrder(trimmedOrder)) {
            throw new IllegalValueException(Order.MESSAGE_ORDER_CONSTRAINTS);
        }
        return new Order(trimmedOrder);
    }

    /**
     * Parses a {@code Optional<String> order} into an {@code Optional<Order>} if {@code order} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Order> parseOrder(Optional<String> order) throws IllegalValueException {
        requireNonNull(order);
        return order.isPresent() ? Optional.of(parseOrder(order.get())) : Optional.empty();
    }
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code String halal} into an {@code Halal}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code halal} is invalid.
     */
    public static Halal parseHalal(String halal) throws IllegalValueException {
        requireNonNull(halal);
        String trimmedHalal = halal.trim();
        if (!Halal.isValidHalal(trimmedHalal)) {
            throw new IllegalValueException(Halal.MESSAGE_HALAL_CONSTRAINTS);
        }
        return new Halal(trimmedHalal);
    }

    /**
     * Parses a {@code Optional<String> halal} into an {@code Optional<Halal>} if {@code halal} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Halal> parseHalal(Optional<String> halal) throws IllegalValueException {
        requireNonNull(halal);
        return halal.isPresent() ? Optional.of(parseHalal(halal.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String vegetarian} into an {@code Vegetarian}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code vegetarian} is invalid.
     */
    public static Vegetarian parseVegetarian(String vegetarian) throws IllegalValueException {
        requireNonNull(vegetarian);
        String trimmedVegetarian = vegetarian.trim();
        if (!Vegetarian.isValidVegetarian(trimmedVegetarian)) {
            throw new IllegalValueException(Vegetarian.MESSAGE_VEGETARIAN_CONSTRAINTS);
        }
        return new Vegetarian(trimmedVegetarian);
    }

    /**
     * Parses a {@code Optional<String> vegetarian} into an {@code Optional<Vegetarian>}
     * if {@code vegetarian} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Vegetarian> parseVegetarian(Optional<String> vegetarian) throws IllegalValueException {
        requireNonNull(vegetarian);
        return vegetarian.isPresent() ? Optional.of(parseVegetarian(vegetarian.get())) : Optional.empty();
    }
```
###### \java\seedu\address\MainApp.java
``` java
        try {
            menuOptional = storage.readMenu();
            if (!menuOptional.isPresent()) {
                logger.info("Data file for menu not found. Will be starting with an empty file");
            }
            initialMenu = menuOptional.orElseGet(SampleMenuDataUtil::getSampleMenu);
        } catch (DataConversionException e) {
            logger.warning("Data file for menu in wrong format. Menu will be starting with an empty file");
            initialMenu = new Menu();
        }  catch (IOException e) {
            logger.warning("Problem while reading from the menu file. Menu will be starting with an empty file");
            initialMenu = new Menu();
        }
```
###### \java\seedu\address\model\dish\Dish.java
``` java
package seedu.address.model.dish;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;



/**
 * Represents a Dish in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Dish {

    private final Name name;
    private final Price price;


    /**
     * If we know the name and price.
     */
    public Dish(Name name, Price price) {
        requireAllNonNull(name, price);
        this.name = name;
        this.price = price;
    }

    /**
     * If we only know the name. Only used for testing purpose
     */
    public Dish(Name name) {
        requireAllNonNull(name);
        this.name = name;
        this.price = new Price("3");
    }

    public Name getName() {
        return name;
    }

    public Price getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Dish)) {
            return false;
        }

        Dish otherDish = (Dish) other;
        return otherDish.getName().equals(this.getName())
                && otherDish.getPrice().equals(this.getPrice());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, price);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Price: ")
                .append(getPrice());
        return builder.toString();
    }

}
```
###### \java\seedu\address\model\dish\exceptions\DishNotFoundException.java
``` java
package seedu.address.model.dish.exceptions;

/**
 * Signals that the operation is unable to find the specified dish.
 */
public class DishNotFoundException extends Exception {
    public DishNotFoundException(String message) {
        super(message);
    }
}
```
###### \java\seedu\address\model\dish\Name.java
``` java
package seedu.address.model.dish;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Dish's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Name {

    public static final String MESSAGE_NAME_CONSTRAINTS =
            "Dish names should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String NAME_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String fullName;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public Name(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_NAME_CONSTRAINTS);
        this.fullName = name;
    }

    /**
     * Returns true if a given string is a valid dish name.
     */
    public static boolean isValidName(String test) {
        return test.matches(NAME_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Name // instanceof handles nulls
                && this.fullName.equals(((Name) other).fullName)); // state check
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}
```
###### \java\seedu\address\model\dish\Price.java
``` java
package seedu.address.model.dish;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's price number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPrice(String)}
 */
public class Price {


    public static final String MESSAGE_PRICE_CONSTRAINTS =
            "Price numbers can only contain numbers";
    public static final String PRICE_VALIDATION_REGEX = "\\d{1,}";
    public final String value;

    /**
     * Constructs a {@code Price}.
     *
     * @param price A valid price number.
     */
    public Price(String price) {
        requireNonNull(price);
        checkArgument(isValidPrice(price), MESSAGE_PRICE_CONSTRAINTS);
        this.value = price;
    }

    /**
     * Returns true if a given string is a valid person price number.
     */
    public static boolean isValidPrice(String test) {
        return test.matches(PRICE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Price // instanceof handles nulls
                && this.value.equals(((Price) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    /**
     *  Returns price in integer form to be able used by {@code compareTo} in Task
     */
    public int toInt() {
        return Integer.parseInt(value);
    }
}
```
###### \java\seedu\address\model\Menu.java
``` java
package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;

import seedu.address.model.dish.Dish;




/**
 * Wraps all data at the menu level
 */
public class Menu implements ReadOnlyMenu {

    private HashMap<String, Dish> dishes;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        dishes = new HashMap<>();
    }

    public Menu() {}

    /**
     * Creates an Menu using the Dishes {@code toBeCopied}
     */
    public Menu(ReadOnlyMenu toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    public void setMenu(HashMap<String, Dish> theDishes) {

        dishes = theDishes;
    }

    public void setMenu(Menu theMenu) {
        dishes = theMenu.getDishes();
    }

    /**
     * Resets the existing data of this {@code Menu} with {@code newData}.
     */
    public void resetData(ReadOnlyMenu newData) {
        requireNonNull(newData);
        setMenu(newData.getDishes());
    }

    /**
     * Returns the dish in Menu if available.
     * Otherwise return null.
     */
    public Dish get(String dish) {
        return dishes.get(dish);
    }

    /**
     * Adds a dish to the menu.
     */
    public void addDish(Dish dish) {
        dishes.put(dish.getName().toString(), dish);
    }


    /**
     * Returns a copy of the data in Menu.
     * Modifications on this copy will not affect the original Menu data
     */
    @Override
    public HashMap<String, Dish> getDishes() {
        return new HashMap<>(dishes);
    }

    @Override
    public String toString() {
        String menu = "";
        for (String name : dishes.keySet()) {
            menu += (name + " ");
        }
        menu = menu.trim();
        return menu;
    }
}
```
###### \java\seedu\address\model\ModelManager.java
``` java
    /** Raises an event to indicate customer stats has changed */
    private void indicateMenuChanged() {
        raise(new MenuChangedEvent(menu));
    }

```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void checkOrder(Person target)  throws DishNotFoundException {
        if (menu.get(target.getOrder().toString()) == null) {
            throw new DishNotFoundException("Dish not available");
        }
    }
```
###### \java\seedu\address\model\person\Halal.java
``` java
package seedu.address.model.person;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's preference on food  in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidHalal(String)}
 */
public class Halal {
    public static final String MESSAGE_HALAL_CONSTRAINTS =
            "Person's preference should only be Halal or Non-halal";
    public final String value;

    /**
     * Constructs a {@code Halal}.
     *
     * @param halal A valid halal.
     */
    public Halal(String halal) {
        if (isNull(halal)) {
            this.value = "Non-halal";
        } else {
            checkArgument(isValidHalal(halal), MESSAGE_HALAL_CONSTRAINTS);
            if (halal.equalsIgnoreCase("Halal")) {
                this.value = "Halal";
            } else {
                this.value = "Non-halal";
            }
        }
    }

    /**
     * Returns true if a given string is a valid halal preference.
     */
    public static boolean isValidHalal(String test) {
        requireNonNull(test);
        if (test.equalsIgnoreCase("Halal") || (test.equalsIgnoreCase("Non-halal"))) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Halal // instanceof handles nulls
                && this.value.equals(((Halal) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
```
###### \java\seedu\address\model\person\Order.java
``` java
package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;


/**
 * Represents a Person's order in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidOrder(String)}
 */
public class Order {

    public static final String MESSAGE_ORDER_CONSTRAINTS =
            "Invalid order";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String ORDER_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String fullOrder;

    /**
     * Constructs a {@code Order}.
     *
     * @param order A valid order.
     */
    public Order(String order) {
        requireNonNull(order);
        checkArgument(isValidOrder(order), MESSAGE_ORDER_CONSTRAINTS);
        this.fullOrder = order.toString();
    }

    /**
     * Returns true if a given string is a valid person order.
     */
    public static boolean isValidOrder(String test) {
        return test.matches(ORDER_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return fullOrder.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Order // instanceof handles nulls
                && this.fullOrder.equals(((Order) other).fullOrder)); // state check
    }

    @Override
    public int hashCode() {
        return fullOrder.hashCode();
    }

}
```
###### \java\seedu\address\model\person\Person.java
``` java
    public Order getOrder() {
        return order;
    }
```
###### \java\seedu\address\model\person\Person.java
``` java
    public Halal getHalal() {
        return halal;
    }

    public Vegetarian getVegetarian() {
        return vegetarian;
    }
```
###### \java\seedu\address\model\person\Vegetarian.java
``` java
package seedu.address.model.person;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's preference on food  in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidVegetarian(String)}
 */
public class Vegetarian {
    public static final String MESSAGE_VEGETARIAN_CONSTRAINTS =
            "Person's preference should only be Vegetarian or Non-vegetarian";
    public final String value;

    /**
     * Constructs a {@code Vegetarian}.
     *
     * @param vegetarian A valid vegetarian.
     */
    public Vegetarian(String vegetarian) {
        if (isNull(vegetarian)) {
            this.value = "Non-vegetarian";
        } else {
            checkArgument(isValidVegetarian(vegetarian), MESSAGE_VEGETARIAN_CONSTRAINTS);
            if (vegetarian.equalsIgnoreCase("Vegetarian")) {
                this.value = "Vegetarian";
            } else {
                this.value = "Non-vegetarian";
            }
        }
    }

    /**
     * Returns true if a given string is a valid halal preference.
     */
    public static boolean isValidVegetarian(String test) {
        requireNonNull(test);
        if (test.equalsIgnoreCase("Vegetarian") || (test.equalsIgnoreCase("Non-vegetarian"))) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Vegetarian // instanceof handles nulls
                && this.value.equals(((Vegetarian) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
```
###### \java\seedu\address\model\ReadOnlyMenu.java
``` java
package seedu.address.model;

import java.util.HashMap;

import seedu.address.model.dish.Dish;


/**
 * Unmodifiable view of customers statistics
 */
public interface ReadOnlyMenu {

    /**
     * Returns a copy of the data in Menu.
     * Modifications on this copy will not affect the original Menu data
     */
    HashMap<String, Dish> getDishes();
}
```
###### \java\seedu\address\model\UserPrefs.java
``` java
    public String getMenuFilePath() {
        return menuFilePath;
    }

```
###### \java\seedu\address\model\util\SampleMenuDataUtil.java
``` java
package seedu.address.model.util;

import seedu.address.model.Menu;
import seedu.address.model.ReadOnlyMenu;
import seedu.address.model.dish.Dish;
import seedu.address.model.dish.Name;
import seedu.address.model.dish.Price;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleMenuDataUtil {
    public static Dish[] getSampleDishes() {
        return new Dish[] {
            new Dish(new Name("Chicken Rice"), new Price("3")),
            new Dish(new Name("Curry Chicken"), new Price("4")),
            new Dish(new Name("Chicken Chop"), new Price("5")),
            new Dish(new Name("Ban Mian"), new Price("4")),
            new Dish(new Name("Ice Milo"), new Price("2")),
            new Dish(new Name("Coffee"), new Price("2"))
        };
    }

    public static ReadOnlyMenu getSampleMenu() {
        Menu sampleMenu = new Menu();
        for (Dish sampleDish : getSampleDishes()) {
            sampleMenu.addDish(sampleDish);
        }
        return sampleMenu;
    }

}
```
###### \java\seedu\address\storage\MenuStorage.java
``` java
package seedu.address.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyMenu;

/**
 * Represents a storage for menu.
 */
public interface MenuStorage {

    /**
     * Returns the file path of the data file.
     */
    String getMenuFilePath();

    /**
     * Returns Menu data as a {@link ReadOnlyMenu}.
     * Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyMenu> readMenu() throws DataConversionException, IOException;

    /**
     * @see #getMenuFilePath()
     */
    Optional<ReadOnlyMenu> readMenu(String filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyMenu} to the storage.
     * @param menu cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveMenu(ReadOnlyMenu menu) throws IOException;

    /**
     * @see #saveMenu(ReadOnlyMenu)
     */
    void saveMenu(ReadOnlyMenu menu, String filePath) throws IOException;

}
```
###### \java\seedu\address\storage\StorageManager.java
``` java
    // ================ Menu methods ==============================

    @Override
    public String getMenuFilePath() {
        return menuStorage.getMenuFilePath();
    }

    @Override
    public Optional<ReadOnlyMenu> readMenu() throws DataConversionException, IOException {
        return readMenu(menuStorage.getMenuFilePath());
    }

    @Override
    public Optional<ReadOnlyMenu> readMenu(String filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return menuStorage.readMenu(filePath);
    }

    @Override
    public void saveMenu(ReadOnlyMenu menu) throws IOException {
        saveMenu(menu, menuStorage.getMenuFilePath());
    }

    @Override
    public void saveMenu(ReadOnlyMenu menu, String filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        menuStorage.saveMenu(menu, filePath);
    }

}
```
###### \java\seedu\address\storage\XmlAdaptedPerson.java
``` java
        if (this.order == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Order.class.getSimpleName()));
        }
        if (!Order.isValidOrder(this.order)) {
            throw new IllegalValueException(Order.MESSAGE_ORDER_CONSTRAINTS);
        }
        final Order order = new Order(this.order);
```
###### \java\seedu\address\storage\XmlAdaptedPerson.java
``` java
        if (this.halal == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Halal.class.getSimpleName()));
        }
        if (!Halal.isValidHalal(this.halal)) {
            throw new IllegalValueException(Halal.MESSAGE_HALAL_CONSTRAINTS);
        }
        final Halal halal = new Halal(this.halal);

        if (this.vegetarian == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Vegetarian.class.getSimpleName()));
        }
        if (!Vegetarian.isValidVegetarian(this.vegetarian)) {
            throw new IllegalValueException(Vegetarian.MESSAGE_VEGETARIAN_CONSTRAINTS);
        }
        final Vegetarian vegetarian = new Vegetarian(this.vegetarian);
```
###### \java\seedu\address\storage\XmlFileStorage.java
``` java
    /**
     * Saves the given menu data to the specified file.
     */
    public static void saveMenuDataToFile(File file, XmlSerializableMenu menu)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, menu);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage());
        }
    }

    /**
     * Returns menu stats in the file or an empty address book
     */
    public static XmlSerializableMenu loadMenuDataFromSaveFile(File file) throws DataConversionException,
            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableMenu.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }
```
###### \java\seedu\address\storage\XmlMenuStorage.java
``` java
package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.ReadOnlyMenu;

/**
 * A class to access Menu data stored as an xml file on the hard disk.
 */
public class XmlMenuStorage implements MenuStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlMenuStorage.class);

    private String filePath;

    public XmlMenuStorage(String filePath) {
        this.filePath = filePath;
    }

    public String getMenuFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyMenu> readMenu() throws DataConversionException, IOException {
        return readMenu(filePath);
    }

    /**
     * Similar to {@link #readMenu()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyMenu> readMenu(String filePath) throws DataConversionException,
            FileNotFoundException {
        requireNonNull(filePath);

        File menuFile = new File(filePath);

        if (!menuFile.exists()) {
            logger.info("Menu file "  + menuFile + " not found");
            return Optional.empty();
        }

        XmlSerializableMenu xmlMenu = XmlFileStorage.loadMenuDataFromSaveFile(new File(filePath));
        try {
            return Optional.of(xmlMenu.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + menuFile + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveMenu(ReadOnlyMenu menu) throws IOException {
        saveMenu(menu, filePath);
    }

    /**
     * Similar to {@link #saveMenu(ReadOnlyMenu)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveMenu(ReadOnlyMenu menu, String filePath) throws IOException {
        requireNonNull(menu);
        requireNonNull(filePath);

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveMenuDataToFile(file, new XmlSerializableMenu(menu));
    }

}
```
###### \java\seedu\address\storage\XmlSerializableMenu.java
``` java
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
```
###### \java\seedu\address\ui\PersonCard.java
``` java
    /**
     * Returns the color style for {@code tagName}'s label.
     */
    private String getTagColorStyleFor(String tagName) {
        return TAG_COLOR_STYLES[Math.abs(tagName.hashCode()) % TAG_COLOR_STYLES.length];
    }

    /**
     * Creates the tag labels for {@code person}.
     */
    private void initTags(Person person) {
        person.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.getStyleClass().add(getTagColorStyleFor(tag.tagName));
            tags.getChildren().add(tagLabel);
        });
    }
```
###### \resources\view\DarkTheme.css
``` css
#tags .teal {
    -fx-text-fill: white;
    -fx-background-color: #3e7b91;
}

#tags .red {
    -fx-text-fill: black;
    -fx-background-color: red;
}

#tags .yellow {
    -fx-text-fill: yellow;
    -fx-background-color: black;
}

#tags .blue {
    -fx-text-fill: blue;
    -fx-background-color: white;
}

#tags .orange {
    -fx-text-fill: black;
    -fx-background-color: orange;
}

#tags .brown {
    -fx-text-fill: while;
    -fx-background-color: brown;
}

#tags .green {
    -fx-text-fill: black;
    -fx-background-color: green;
}

#tags .pink {
    -fx-text-fill: black;
    -fx-background-color: pink;
}

#tags .black {
    -fx-text-fill: white;
    -fx-background-color: black;
}

#tags .grey {
    -fx-text-fill: black;
    -fx-background-color: grey;
}
```
