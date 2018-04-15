package seedu.ptman.model.outlet;

import static java.util.Objects.requireNonNull;
import static seedu.ptman.commons.util.AppUtil.checkArgument;

//@@author SunBangjie
/**
 * Represents an outlet's contact number in PTMan.
 * Guarantees: immutable; is valid as declared in {@link #isValidOutletContact(String)}
 */
public class OutletContact {

    public static final String MESSAGE_OUTLET_CONTACT_CONSTRAINTS =
            "Outlet contact numbers can only contain numbers, and should be at least 3 digits long";
    public static final String OUTLET_CONTACT_VALIDATION_REGEX = "\\d{3,}";
    public final String value;

    /**
     * Constructs a {@code OutletContact}.
     *
     * @param phone A valid phone number.
     */
    public OutletContact(String phone) {
        requireNonNull(phone);
        checkArgument(isValidOutletContact(phone), MESSAGE_OUTLET_CONTACT_CONSTRAINTS);
        this.value = phone;
    }

    /**
     * Returns true if a given string is a valid employee phone number.
     */
    public static boolean isValidOutletContact(String test) {
        return test.matches(OUTLET_CONTACT_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof OutletContact // instanceof handles nulls
                && this.value.equals(((OutletContact) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
