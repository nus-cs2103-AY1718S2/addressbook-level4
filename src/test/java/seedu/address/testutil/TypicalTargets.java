package seedu.address.testutil;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandTarget;

/**
 * A utility class containing a list of {@code Index} objects to be used in tests.
 */
public class TypicalTargets {
    public static final Index INDEX_FIRST_COIN = Index.fromOneBased(1);
    public static final Index INDEX_SECOND_COIN = Index.fromOneBased(2);
    public static final Index INDEX_THIRD_COIN = Index.fromOneBased(3);

    public static final CommandTarget TARGET_FIRST_COIN = new CommandTarget(INDEX_FIRST_COIN);
    public static final CommandTarget TARGET_SECOND_COIN = new CommandTarget(INDEX_SECOND_COIN);
    public static final CommandTarget TARGET_THIRD_COIN = new CommandTarget(INDEX_THIRD_COIN);
}
