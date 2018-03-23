package seedu.address.model.smplatform;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Facebook extends SocialMediaPlatform {
    /**
     * code extract from https://stackoverflow.com/questions/5205652/facebook-profile-url-regular-expression
     */
    public static final String LINK_VALIDATION_REGEX = "(?:https?:\\/\\/)?(?:www\\.|m\\.)?facebook\\.com\\/.(?:(?:\\w)*#!\\/)?(?:pages\\/)?(?:[\\w\\-]*\\/)*([\\w\\-\\.]*)/?";

    @Override
    public void setLink(Link link) {
        //Pattern p = Pattern.compile(LINK_VALIDATION_REGEX);
        //Matcher m = p.matcher(link.value);
        //System.out.println("Link: " + link.value + "\nMatches: " + m.matches());

        this.link = link;
        if (!Pattern.compile(LINK_VALIDATION_REGEX).matcher(link.value).matches() && !link.value.isEmpty()) {
            System.out.println("This is not a valid FB link.");
        }
    }
}
