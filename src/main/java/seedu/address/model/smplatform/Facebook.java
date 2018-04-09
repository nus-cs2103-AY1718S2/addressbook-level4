package seedu.address.model.smplatform;

//@@author Nethergale
/**
 * Represents a facebook object.
 */
public class Facebook extends SocialMediaPlatform {
    //Code adapted from https://stackoverflow.com/questions/5205652/facebook-profile-url-regular-expression
    public static final String LINK_VALIDATION_REGEX = "(?:https?:\\/\\/)?(?:www\\.|m\\.)?facebook\\.com\\/"
            + "(?:profile.php\\?id=(?=\\d.*))?"
            + "[^/ \\\\](?:(?:\\w)*#!\\/)?(?:pages\\/)?(?:[\\w\\-]*\\/)*([\\w\\-\\.]*)/?";

    public Facebook(Link link) {
        this.link = link;
    }
}
