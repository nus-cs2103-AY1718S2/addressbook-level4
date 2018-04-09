package seedu.address.model.smplatform;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

//@@author Nethergale
public class LinkTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Link(null));
    }

    @Test
    public void isValidLink() {
        // null link
        Assert.assertThrows(NullPointerException.class, () -> Link.isValidLink(null));

        // invalid links
        assertFalse(Link.isValidLink("")); // empty string
        assertFalse(Link.isValidLink(" ")); // spaces only
        assertFalse(Link.isValidLink("www.google.com/")); // unknown link
        assertFalse(Link.isValidLink("www.facebook.com/")); // facebook link without any path specified
        assertFalse(Link.isValidLink("www.facebook.com////")); // facebook link with slashes only
        assertFalse(Link.isValidLink("www.facebook.com/ /")); // facebook link with space as profile username
        assertFalse(Link.isValidLink("www.twitter.com/")); // twitter link without any path specified
        assertFalse(Link.isValidLink("www.twitter.com////")); // twitter link with slashes only
        assertFalse(Link.isValidLink("www.twitter.com/ /")); // twitter link with space as username handle

        // valid links
        assertTrue(Link.isValidLink("https://www.facebook.com/abc")); // facebook page with protocol and subdomain
        assertTrue(Link.isValidLink("http://facebook.com/abc")); // facebook page with protocol only
        assertTrue(Link.isValidLink("www.facebook.com/teo.yong")); // facebook page with subdomain only
        assertTrue(Link.isValidLink("facebook.com/abc")); // facebook page with profile username only
        assertTrue(Link.isValidLink("facebook.com/profile.php?id=100008354955053")); // facebook page with ID
        assertTrue(Link.isValidLink("https://www.twitter.com/abc")); // twitter page with protocol and subdomain
        assertTrue(Link.isValidLink("http://twitter.com/__ChrisLee")); // twitter page with protocol only
        assertTrue(Link.isValidLink("www.twitter.com/yosp")); // twitter page with subdomain only
        assertTrue(Link.isValidLink("twitter.com/abc")); // twitter page with username handle only
    }
}
