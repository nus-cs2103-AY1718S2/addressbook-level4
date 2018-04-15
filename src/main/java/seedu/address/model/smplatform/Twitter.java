package seedu.address.model.smplatform;

/**
 * Represents a twitter object.
 */
public class Twitter extends SocialMediaPlatform {

    public static final String PLATFORM_KEYWORD = "twitter";

    public static final String PLATFORM_ALIAS = "tw";

    //@@author Nethergale-reused
    //Reused from https://stackoverflow.com/questions/6024848/regex-to-validate-a-twitter-url with minor modifications
    public static final String LINK_VALIDATION_REGEX = "(?:https?:\\/\\/)?(?:www\\.|m\\.)?twitter\\.com\\/"
            + "[^/ \\\\](?:(?:\\w)*#!\\/)?(?:pages\\/)?(?:[\\w\\-]*\\/)*([\\w\\-]*)/?";

    //@@author Nethergale
    public Twitter(Link link) {
        this.link = link;
    }
}
