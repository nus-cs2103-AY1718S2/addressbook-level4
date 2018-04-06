package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;


/**
 * A utility class containing a list of {@code Tag} objects to be used in tests.
 */
/** @@author Codee */
public class TypicalTags {

    public static final Tag GOOD_ATTITUDE = new Tag("goodAttitude", "teal");
    public static final Tag FRIEND = new Tag("friends", "teal");

    private TypicalTags() {} //prevents instantiation


    /**
     * Returns an {@code AddressBook} with all the typical teams.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Tag tag : getTypicalTags()) {
            try {
                ab.addTag(tag);
            } catch (UniqueTagList.DuplicateTagException e) {
                throw new AssertionError("not possible");
            }
        }
        return ab;
    }

    public static List<Tag> getTypicalTags() {
        return new ArrayList<>(Arrays.asList(GOOD_ATTITUDE, FRIEND));
    }

}
