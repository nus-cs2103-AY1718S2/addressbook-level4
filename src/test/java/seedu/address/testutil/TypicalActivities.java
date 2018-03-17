package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.DeskBoard;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.exceptions.DuplicateActivityException;

/**
 * A utility class containing a list of {@code Activity} objects to be used in tests.
 */
public class TypicalActivities {

    public static final Activity ALICE = new ActivityBuilder().withName("Alice Pauline")
            .withRemark("123, Jurong West Ave 6, #08-111")
            .withDateTime("85355255")
            .withTags("friends").build();
    public static final Activity BENSON = new ActivityBuilder().withName("Benson Meier")
            .withRemark("311, Clementi Ave 2, #02-25")
            .withTags("owesMoney", "friends").build();
    public static final Activity CARL = new ActivityBuilder().withName("Carl Kurz").withDateTime("95352563")
            .withRemark("wall street").build();
    public static final Activity DANIEL = new ActivityBuilder().withName("Daniel Meier").withDateTime("87652533")
            .withRemark("10th street").build();
    public static final Activity ELLE = new ActivityBuilder().withName("Elle Meyer").withDateTime("9482224")
            .withRemark("michegan ave").build();
    public static final Activity FIONA = new ActivityBuilder().withName("Fiona Kunz").withDateTime("9482427")
            .withRemark("little tokyo").build();
    public static final Activity GEORGE = new ActivityBuilder().withName("George Best").withDateTime("9482442")
            .withRemark("4th street").build();

    // Manually added
    public static final Activity HOON = new ActivityBuilder().withName("Hoon Meier").withDateTime("8482424")
            .withRemark("little india").build();
    public static final Activity IDA = new ActivityBuilder().withName("Ida Mueller").withDateTime("8482131")
            .withRemark("chicago ave").build();

    // Manually added - Activity's details found in {@code CommandTestUtil}
    public static final Activity AMY = new ActivityBuilder().withName(VALID_NAME_AMY).withDateTime(VALID_PHONE_AMY)
            .withRemark(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Activity BOB = new ActivityBuilder().withName(VALID_NAME_BOB).withDateTime(VALID_PHONE_BOB)
            .withRemark(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalActivities() {} // prevents instantiation

    /**
     * Returns an {@code DeskBoard} with all the typical persons.
     */
    public static DeskBoard getTypicalDeskBoard() {
        DeskBoard ab = new DeskBoard();
        for (Activity activity : getTypicalActivitiess()) {
            try {
                ab.addActivity(activity);
            } catch (DuplicateActivityException e) {
                throw new AssertionError("not possible");
            }
        }
        return ab;
    }

    public static List<Activity> getTypicalActivitiess() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
