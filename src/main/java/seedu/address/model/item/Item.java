//@@author chenchongsong
package seedu.address.model.item;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Item in the TravelBanker.
 * Guarantees: immutable; name is valid as declared in {@link #isValidItemName(String)}
 */
public class Item {

    public static final String MESSAGE_ITEMNAME_CONSTRAINTS = "ItemNames should be alphanumeric with whitespaces or _";
    public static final String MESSAGE_ITEMVALUE_CONSTRAINTS = "Item Values should be a floating point number!";
    public static final String ITEM_NAME_VALIDATION_REGEX = "[\\p{Alnum}_\\s]+";
    public static final String ITEM_VALUE_VALIDATION_REGEX = "-?\\d+(\\.\\d+)?(E-?\\d+)?";

    private final String itemName;
    private final String itemValue;

    /**
     * Constructs a {@code Item}.
     *
     * @param itemName A valid item name.
     */
    public Item(String itemName, String itemValue) {
        requireNonNull(itemName);
        requireNonNull(itemValue);
        checkArgument(isValidItemName(itemName), MESSAGE_ITEMNAME_CONSTRAINTS);
        checkArgument(isValidItemValue(itemValue), MESSAGE_ITEMVALUE_CONSTRAINTS);
        this.itemName = itemName;
        this.itemValue = itemValue;
    }

    /**
     * Returns true if a given string is a valid item name.
     */
    public static boolean isValidItemName(String test) {
        return test.matches(ITEM_NAME_VALIDATION_REGEX);
    }

    /**
     * Returns true if a given string is a valid item value.
     */
    public static boolean isValidItemValue(String test) {
        return test.matches(ITEM_VALUE_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Item // instanceof handles nulls
                && this.itemName.equals(((Item) other).itemName)
                && this.itemValue.equals(((Item) other).itemValue)); // state check
    }

    @Override
    public int hashCode() {
        return itemName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return "ItemName  [ " + itemName + " ]    ||    ItemValue [ " + itemValue + " ]";
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemValue() {
        return itemValue;
    }

}
