package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class NextOfKin {

    public static final String MESSAGE_NAME_CONSTRAINTS =
            "Person names should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String NAME_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public static final String MESSAGE_PHONE_CONSTRAINTS =
            "Phone numbers can only contain numbers, and should be at least 3 digits long";

    //public static final String PHONE_VALIDATION_REGEX = "\\d{3,}";

    private static  final String SPECIAL_CHARACTERS = "!#$%&'*+/=?`{|}~^.-";
    public static final String MESSAGE_EMAIL_CONSTRAINTS = "Person emails should be of the format local-part@domain "
            + "and adhere to the following constraints:\n"
            + "1. The local-part should only contain alphanumeric characters and these special characters, excluding "
            + "the parentheses, (" + SPECIAL_CHARACTERS + ") .\n"
            + "2. This is followed by a '@' and then a domain name. "
            + "The domain name must:\n"
            + "    - be at least 2 characters long\n"
            + "    - start and end with alphanumeric characters\n"
            + "    - consist of alphanumeric characters, a period or a hyphen for the characters in between, if any.";
    // alphanumeric and special characters
    private static final String LOCAL_PART_REGEX = "^[\\w" + SPECIAL_CHARACTERS + "]+";
    private static final String DOMAIN_FIRST_CHARACTER_REGEX = "[^\\W_]"; // alphanumeric characters except underscore
    private static final String DOMAIN_MIDDLE_REGEX = "[a-zA-Z0-9.-]*"; // alphanumeric, period and hyphen
    private static final String DOMAIN_LAST_CHARACTER_REGEX = "[^\\W_]$";
    //public static final String EMAIL_VALIDATION_REGEX = LOCAL_PART_REGEX + "@"
    //+ DOMAIN_FIRST_CHARACTER_REGEX + DOMAIN_MIDDLE_REGEX + DOMAIN_LAST_CHARACTER_REGEX;


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
        //checkArgument(isValidEmail(email), MESSAGE_EMAIL_CONSTRAINTS);
        this.email = email;

        requireNonNull(phone);
        //checkArgument(isValidPhone(phone), MESSAGE_PHONE_CONSTRAINTS);
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

    /**
     * Returns true if a given string is a valid person phone number.
     */
    /*
    public static boolean isValidPhone(String test) {
        return test.matches(PHONE_VALIDATION_REGEX);
    }

    public static boolean isValidEmail(String test) {
        return test.matches(EMAIL_VALIDATION_REGEX);
    }*/


    @Override
    public String toString() {
        return (fullName + " Phone: " + phone + " Email:" + email + " Remark:" + remark);
    }

    /**
     * Returns a string of Next of Kin's name, email and phone
     */
    public String printNok() {
        final StringBuilder builder = new StringBuilder();
        builder.append(fullName)
                .append(" ")
                .append(email)
                .append(" ")
                .append(phone);
        return builder.toString();
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

}
