package seedu.address.model.Insurance;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Insurance plan in reInsurance.
 * Guarantees: immutable;
 */
public class Insurance {

    public static final String MESSAGE_INSURANCE_CONSTRAINTS =
        "Insurance should only contain alphanumeric characters";

    private static final String SPECIAL_CHARACTERS = "\\[\\]{|}";
    private static final String INSURANCE_NAME = "[\\p{Alnum} ]*";
    private static final String COMMISSION_FORMAT = "^[\\w" + SPECIAL_CHARACTERS + "]+";

    /*
     * The first character of the address must not be a whitespace,
     */
    public static final String INSURANCE_VALIDATION_REGEX =  INSURANCE_NAME + COMMISSION_FORMAT;


    public final String insuranceName;

    /**
     * Constructs a {@code Name}.
     *
     * @param insurance A valid insurance.
     */
    public Insurance(String insurance) {
        checkArgument(isValidInsurance(insurance), MESSAGE_INSURANCE_CONSTRAINTS);
        this.insuranceName = insurance;
    }

    /**
     * Returns true if a given string is a valid insurance.
     */
    public static boolean isValidInsurance(String test) {
        if (test == null) {
            return true;
        }
        return test.matches(INSURANCE_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return insuranceName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Insurance // instanceof handles nulls
            && this.insuranceName.equals(((Insurance) other).insuranceName)); // state check
    }

    @Override
    public int hashCode() {
        return insuranceName.hashCode();
    }

}
