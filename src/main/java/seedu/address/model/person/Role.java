//@@author zacci
package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's role in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidRole(String)}
 */
public class Role {


    public static final String MESSAGE_ROLE_CONSTRAINTS =
            "Role can only take on the values 'g' or 'p', which represents Guard or Prisoner respectively";
    public static final Role PRISONER = new Role("p");
    public static final Role GUARD = new Role("g");
    public final String value;

    /**
     * Constructs a {@code Role}.
     *
     * @param role 'g' or 'p' to represent Guard or Prisoner respectively.
     */
    public Role(String role) {
        requireNonNull(role);
        checkArgument(isValidRole(role), MESSAGE_ROLE_CONSTRAINTS);
        this.value = role;
    }

    /**
     * Returns true if a given string is a valid person phone number.
     */
    public static boolean isValidRole(String test) {
        return test.equals("g") || test.equals("p");
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Role // instanceof handles nulls
                && this.value.equals(((Role) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
