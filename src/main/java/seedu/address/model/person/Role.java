package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Arrays;
import java.util.HashSet;

//@@author alexawangzi
/**
 * Represents a Person's role in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidRole(String)}
 */
public class Role {

    public static final String[] ROLES_VALUES = new String[] { "student", "s", "tutor", "t", ""};
    public static final HashSet<String> SET_ALL_ROLES = new HashSet<>(Arrays.asList(ROLES_VALUES));

    public static final String MESSAGE_ROLE_CONSTRAINTS = "Role should be one of: \n"
            + SET_ALL_ROLES.toString()
            + "\n";

    public final String value;

    /**
     * Constructs an {@code Role}.
     *
     * @param role A valid role description.
     */
    public Role(String role) {
        requireNonNull(role);
        role = validateRole(role);
        this.value = formatRole(role);
    }


    /**
     * Format the input into proper case
     * @param role
     * @return
     */
    private String formatRole(String role) {
        ProperCaseConverter pc = new ProperCaseConverter();
        return pc.convertToProperCase(role);
    }


    /**
     * check validity of the status string supplied
     * @param role
     * @return string representing a valid role
     */
    private String validateRole(String role) {
        role.toLowerCase();
        checkArgument(isValidRole(role), MESSAGE_ROLE_CONSTRAINTS);
        role = convertToFullRole(role);
        return role;
    }

    /**
     * Convert a shortcut to full role name
     */
    public String convertToFullRole(String original) {
        String cur = original.toLowerCase();
        if (cur.equals("s")) {
            cur = "student";
        } else if (cur.equals("t")) {
            cur = "tutor";
        }
        return cur;
    }


    /**
     * Returns if a given string is a valid role description.
     */
    public static boolean isValidRole(String test) {
        test = test.toLowerCase();
        return SET_ALL_ROLES.contains(test);
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
