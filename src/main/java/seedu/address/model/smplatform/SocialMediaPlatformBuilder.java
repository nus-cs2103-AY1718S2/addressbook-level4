package seedu.address.model.smplatform;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author Nethergale
public class SocialMediaPlatformBuilder {
    private static final String MESSAGE_UNRECOGNISED_LINK_TYPE = "New social media platform cannot be constructed. "
            + "Link type cannot be recognised.";

    /**
     * Constructs the specific social media platform object by using the {@code type} and setting the {@code link}
     * as a parameter.
     *
     * @return the created social media platform object
     * @throws IllegalValueException if type is not recognised
     */
    public static SocialMediaPlatform build(String type, Link link) throws IllegalValueException {
        if (type.equals(Link.FACEBOOK_LINK_TYPE)) {
            return new Facebook(link);
        } else if (type.equals(Link.TWITTER_LINK_TYPE)) {
            return new Twitter(link);
        } else {
            throw new IllegalValueException(MESSAGE_UNRECOGNISED_LINK_TYPE);
        }
    }
}
