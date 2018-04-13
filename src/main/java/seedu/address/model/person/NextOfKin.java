package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.model.person.Name.MESSAGE_NAME_CONSTRAINTS;
import static seedu.address.model.person.Name.NAME_VALIDATION_REGEX;

/**
 * Represents a Person's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */

//@@author chuakunhong
public class NextOfKin {

    public final String fullName;
    public final String phone;
    public final String email;
    public final String remark;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public NextOfKin(String name, String phone, String email, String remark) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_NAME_CONSTRAINTS);
        this.fullName = name;

        requireNonNull(email);
        this.email = email;

        requireNonNull(phone);
        this.phone = phone;

        requireNonNull(remark);
        this.remark = remark;
    }

    /**
     * Returns true if a given string is a valid person name.
     */
    public static boolean isValidName(String test) {
        return test.matches(NAME_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return (fullName + " Phone: " + phone + " Email:" + email + " Remark:" + remark);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NextOfKin // instanceof handles nulls
                && this.fullName.equals(((NextOfKin) other).fullName)); // state check
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }
//@@author
}
