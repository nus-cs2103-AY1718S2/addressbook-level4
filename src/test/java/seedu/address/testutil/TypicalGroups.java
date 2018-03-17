package seedu.address.testutil;

import seedu.address.model.tag.Group;

/**
 * A utility class containing a list of {@code Group} Objects to be used in tests.
 */
public class TypicalGroups {
    public static final Group FRIENDS = new Group("friends");
    public static final Group COLLEAGUES = new Group("colleagues");
    public static final Group BUDDIES = new Group("buddies");
    public static final Group FAMILY = new Group("family");

    private TypicalGroups() {}

}
