package seedu.address.model.smplatform;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author Nethergale
/**
 * Acts as a social media platform creator.
 * Determines the different types of social media platform objects to be created by using the link and its type.
 */
public final class SocialMediaPlatformBuilder {
    public static final String MESSAGE_BUILD_ERROR = "Social media platform cannot be constructed. "
            + "Link type is unrecognised or mismatched with link.";

    /**
     * Don't let anyone instantiate this class.
     */
    private SocialMediaPlatformBuilder() {}

    /**
     * Constructs the specific social media platform object by using the {@code type} and setting the {@code link}
     * as a parameter.
     *
     * @return the created social media platform object
     * @throws IllegalValueException if type is not recognised
     */
    public static SocialMediaPlatform build(String type, Link link) throws IllegalValueException {
        if (type.equals(Link.FACEBOOK_LINK_TYPE) && type.equals(Link.getLinkType(link.value))) {
            return new Facebook(link);
        } else if (type.equals(Link.TWITTER_LINK_TYPE) && type.equals(Link.getLinkType(link.value))) {
            return new Twitter(link);
        } else {
            throw new IllegalValueException(MESSAGE_BUILD_ERROR);
        }
    }
}
