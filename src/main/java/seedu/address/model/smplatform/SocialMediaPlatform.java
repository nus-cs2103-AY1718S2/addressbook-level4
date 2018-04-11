package seedu.address.model.smplatform;

//@@author Nethergale
/**
 * Represents a social media platform, which can take many forms.
 */
public abstract class SocialMediaPlatform {

    public static final String MESSAGE_USERNAME_CONSTRAINTS =
            "Social media  usernames should only contain alphanumeric characters, " +
                    "underscore,  and spaces, and it should not be blank";

    public static final String USERNAME_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum}_ ]*";

    public static final String MESSAGE_PLATFORM_CONSTRAINTS =
            "Platforms available are : \n"
                    + "1) " + Facebook.PLATFORM_KEYWORD + " (alias: " + Facebook.PLATFORM_ALIAS + ")\n"
                    + "2) " + Twitter.PLATFORM_KEYWORD + " (alias: " + Twitter.PLATFORM_ALIAS + ")\n";

    protected Link link;

    public Link getLink() {
        return link;
    }

    /**
     * Returns true if a given string is a valid social platform name.
     */
    public static boolean isValidPlatform(String test) {
        if (test.equals(Facebook.PLATFORM_KEYWORD)
                || test.equals(Facebook.PLATFORM_ALIAS)
                || test.equals(Twitter.PLATFORM_KEYWORD)
                || test.equals(Twitter.PLATFORM_ALIAS)) {
            return true;
        }
        return false;
    }
}
