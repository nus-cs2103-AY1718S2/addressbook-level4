package seedu.address.model.person;

//Represents the release date of the prisoner in date time format

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class Release_Date {

    public static final String MESSAGE_RELEASE_DATE_CONSTRAINTS = "The date format should follow this format: yyyy-MM-dd";
//    credit of the regex goes to - Ofir Luzon - on Stack Overflow
    public static final String RELEASE_DATE_VALIDATION_REGEX = "^(?:(?:31(-)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(-)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(-)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(-)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$";
    public final String value;


//    Constructs valid release date with valid parameter
    public Release_Date(String release_date){
        requireNonNull(release_date);
        checkArgument(isValidReleaseDate(release_date), MESSAGE_RELEASE_DATE_CONSTRAINTS);
        this.value = release_date;
    }

//    Returns true if release date is valid
    public static boolean isValidReleaseDate(String test){ return test.matches(RELEASE_DATE_VALIDATION_REGEX); }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Release_Date // instanceof handles nulls
                && this.value.equals(((Release_Date) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
