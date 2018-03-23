package seedu.address.model.smplatform;

public abstract class SocialMediaPlatform {
    protected Link link;

    public abstract void setLink(Link link);

    public Link getLink() {
        return link;
    }
}
