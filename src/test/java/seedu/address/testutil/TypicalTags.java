package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * A utility class containing a list of {@code Tag} objects to be used in tests.
 */
public class TypicalTags {
    public static final Tag TAG_HUSBAND = new Tag("husband");
    public static final Tag TAG_FRIEND = new Tag("friends");
    public static final Tag TAG_OWES_MONEY = new Tag("owesMoney");
    public static final Set<Tag> TAG_SET_OWES_MONEY_FRIEND = new HashSet<>(Arrays.asList(TAG_OWES_MONEY, TAG_FRIEND));
    public static final Set<Tag> TAG_SET_FRIEND = new HashSet<>(Arrays.asList(TAG_FRIEND));
    public static final Set<Tag> TAG_SET_HUSBAND = new HashSet<>(Arrays.asList(TAG_HUSBAND));
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*";
    public static final String VALID_TAG_DESC_FRIEND = " " + PREFIX_TAG + "friends";
    public static final String VALID_TAG_DESC_OWES_MONEY = " " + PREFIX_TAG + "owesMoney";

}
