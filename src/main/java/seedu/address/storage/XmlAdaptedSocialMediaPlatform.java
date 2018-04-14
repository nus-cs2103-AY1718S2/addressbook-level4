package seedu.address.storage;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.smplatform.Link;
import seedu.address.model.smplatform.SocialMediaPlatform;
import seedu.address.model.smplatform.SocialMediaPlatformFactory;

//@@author Nethergale
/**
 * JAXB-friendly adapted version of the SocialMediaPlatform.
 */
public class XmlAdaptedSocialMediaPlatform {

    @XmlElement
    private String type;
    @XmlElement
    private String link;

    /**
     * Constructs an XmlAdaptedSocialMediaPlatform.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedSocialMediaPlatform() {}

    /**
     * Constructs a {@code XmlAdaptedSocialMediaPlatform} with the given {@code type} and {@code link}.
     */
    public XmlAdaptedSocialMediaPlatform(String type, String link) {
        this.type = type;
        this.link = link;
    }

    /**
     * Converts a given String and Link into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedSocialMediaPlatform
     */
    public XmlAdaptedSocialMediaPlatform(String type, Link source) {
        this.type = type;
        link = source.value;
    }

    /**
     * Converts this jaxb-friendly adapted social media platform object into the model's social media platform object.
     *
     * @throws IllegalValueException if link is not valid
     */
    public SocialMediaPlatform toModelType() throws IllegalValueException {
        if (!Link.isValidLink(link)) {
            throw new IllegalValueException(Link.MESSAGE_INVALID_LINK);
        }

        return SocialMediaPlatformFactory.getSocialMediaPlatform(type, new Link(link));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedSocialMediaPlatform)) {
            return false;
        }

        return type.equals(((XmlAdaptedSocialMediaPlatform) other).type)
                && link.equals(((XmlAdaptedSocialMediaPlatform) other).link);
    }
}
