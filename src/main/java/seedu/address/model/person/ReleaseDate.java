//@@author philos22-unused
package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the release date of the prisoner in date time format
 */
public class ReleaseDate {

    public static final String MESSAGE_RELEASE_DATE_CONSTRAINTS = "The date format: yyyy-MM-dd";
    //    credit of the regex goes to - Ofir Luzon - on Stack Overflow
    public static final String RELEASE_DATE_VALIDATION_REGEX = "^(?:(?:31(-)(?:0?[13578]|1[02]))\\1|"
            +
            "(?:(?:29|30)(-)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(-)0?2\\3"
            +
            "(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))"
            +
            "$|^(?:0?[1-9]|1\\d|2[0-8])(-)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$";
    public final String value;


    //    Constructs valid release date with valid parameter
    public ReleaseDate(String releaseDate) {
        requireNonNull(releaseDate);
        checkArgument(isValidReleaseDate(releaseDate), MESSAGE_RELEASE_DATE_CONSTRAINTS);
        this.value = releaseDate;
    }

    //    Returns true if release date is valid
    public static boolean isValidReleaseDate(String test) {
        return test.matches(RELEASE_DATE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReleaseDate // instanceof handles nulls
                && this.value.equals(((ReleaseDate) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
