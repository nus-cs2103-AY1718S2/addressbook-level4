package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

//@@author shookshire
/**
 * Represents if a Client is a student or tutor in TuitionCor.
 * Guarantees: immutable; is valid as declared in {@link #isValidCategory(String)}
 */
public class Category {

    public static final String MESSAGE_CATEGORY_CONSTRAINTS =
            "Client Category can only be s or t, representing student or tutor respectively";

    /*
     * Must be either s or t
     */
    public static final String ADDRESS_VALIDATION_REGEX = "[st]";

    public final String value;

    /**
     * Constructs an {@code Category}.
     *
     * @param category A valid category.
     */
    public Category(String category) {
        requireNonNull(category);
        checkArgument(isValidCategory(category), MESSAGE_CATEGORY_CONSTRAINTS);
        this.value = category;
    }

    /**
     * Returns true if a given string is a valid client category.
     */
    public static boolean isValidCategory(String test) {
        return test.matches(ADDRESS_VALIDATION_REGEX);
    }

    public boolean isStudent() {
        return value.equals("s");
    }

    public boolean isTutor() {
        return value.equals("t");
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Category // instanceof handles nulls
                && this.value.equals(((Category) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
