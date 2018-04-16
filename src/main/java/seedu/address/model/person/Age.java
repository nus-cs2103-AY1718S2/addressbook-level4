package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

//@@author jstarw
/**
 * Represents a Person's age in the address book.
 * Represents a Person's value in the address book
 * Guarantees: immutable; is valid as declare in {@link #isValidAge(Integer)}}
 */
public class Age {
    public static final String AGE_CONSTRAINTS =
            "Persons age must be above 0 years old and under 150.";

    public final Integer value;

    /**
     * @param age a valid value
     */
    public Age(Integer age) {
        requireNonNull(age);
        checkArgument(isValidAge(age), AGE_CONSTRAINTS);
        this.value = age;
    }

    /**
     * checks if the age is valid
     * @param age
     * @return
     */
    public static boolean isValidAge(Integer age) {
        return age >= 0 && age < 150;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Age
                && this.value == ((Age) other).value);
    }

}
