package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's company in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidCompany(String)}
 */
public class Company {

    public static final String MESSAGE_COMPANY_CONSTRAINTS =
            "Person's company should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the company must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String COMPANY_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String value;

    /**
     * Constructs an {@code Company}.
     *
     * @param company A valid company.
     */
    public Company(String company) {
        requireNonNull(company);
        checkArgument(isValidCompany(company), MESSAGE_COMPANY_CONSTRAINTS);
        this.value = company;
    }

    /**
     * Returns true if a given string is a valid person email.
     */
    public static boolean isValidCompany(String test) {
        return test.matches(COMPANY_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Company // instanceof handles nulls
                && this.value.equals(((Company) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
