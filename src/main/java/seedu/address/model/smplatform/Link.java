package seedu.address.model.smplatform;

import static java.util.Objects.requireNonNull;

//@@author Nethergale
/**
 * Represents a SocialMediaPlatform's link.
 * Guarantees: immutable; is always valid
 */
public class Link {
    public static final String MESSAGE_LINK_CONSTRAINTS = "Only one link is allowed for each social media platform.";
    public static final String MESSAGE_INVALID_LINK = "Links should be valid Facebook or Twitter profile links.";
    public static final String FACEBOOK_LINK_TYPE = "facebook";
    public static final String TWITTER_LINK_TYPE = "twitter";
    public static final String UNKNOWN_LINK_TYPE = "unknown";

    private static final String FACEBOOK_LINK_SIGNATURE = "facebook.com";
    private static final String TWITTER_LINK_SIGNATURE = "twitter.com";
    private static final String LONGEST_VALID_LINK_PREFIX = "https://www.";

    public final String value;

    public Link(String link) {
        requireNonNull(link);
        this.value = link;
    }

    /**
     * Returns the social media platform type of the link.
     */
    public static String getLinkType(String link) {
        if (link.contains(TWITTER_LINK_SIGNATURE)
                && !(link.indexOf(TWITTER_LINK_SIGNATURE) > LONGEST_VALID_LINK_PREFIX.length())) {
            return TWITTER_LINK_TYPE;
        } else if (link.contains(FACEBOOK_LINK_SIGNATURE)
                && !(link.indexOf(FACEBOOK_LINK_SIGNATURE) > LONGEST_VALID_LINK_PREFIX.length())) {
            return FACEBOOK_LINK_TYPE;
        } else {
            return UNKNOWN_LINK_TYPE;
        }
    }

    /**
     * Returns true if a given string is a valid link.
     */
    public static boolean isValidLink(String test) {
        if (test.contains(TWITTER_LINK_SIGNATURE)
                && !(test.indexOf(TWITTER_LINK_SIGNATURE) > LONGEST_VALID_LINK_PREFIX.length())) {
            return test.matches(Twitter.LINK_VALIDATION_REGEX);
        } else if (test.contains(FACEBOOK_LINK_SIGNATURE)
                && !(test.indexOf(FACEBOOK_LINK_SIGNATURE) > LONGEST_VALID_LINK_PREFIX.length())) {
            return test.matches(Facebook.LINK_VALIDATION_REGEX);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Link // instanceof handles nulls
                && this.value.equals(((Link) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
