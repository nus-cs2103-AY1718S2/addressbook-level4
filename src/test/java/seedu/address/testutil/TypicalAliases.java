package seedu.address.testutil;

import seedu.address.model.AddressBook;
import seedu.address.model.alias.Alias;
import seedu.address.model.alias.exceptions.DuplicateAliasException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * A utility class containing a list of {@code Alias} objects to be used in tests.
 */
public class TypicalAliases {

    public static final Alias ADD = new AliasBuilder().withCommand("add").withAlias("add1").build();
    public static final Alias ALIAS  = new AliasBuilder().withCommand("alias").withAlias("alias1").build();
    public static final Alias CLEAR = new AliasBuilder().withCommand("clear").withAlias("clear1").build();
    public static final Alias DELETE = new AliasBuilder().withCommand("delete").withAlias("delete1").build();
    public static final Alias EDIT = new AliasBuilder().withCommand("edit").withAlias("edit1").build();
    public static final Alias EXIT = new AliasBuilder().withCommand("exit").withAlias("exit1").build();
    public static final Alias FIND = new AliasBuilder().withCommand("find").withAlias("find1").build();
    public static final Alias HELP = new AliasBuilder().withCommand("help").withAlias("help1").build();
    public static final Alias HISTORY = new AliasBuilder().withCommand("history").withAlias("history1").build();
    public static final Alias IMPORT = new AliasBuilder().withCommand("import").withAlias("import1").build();
    public static final Alias LIST = new AliasBuilder().withCommand("list").withAlias("list1").build();
    public static final Alias REDO = new AliasBuilder().withCommand("redo").withAlias("redo1").build();
    public static final Alias UNDO = new AliasBuilder().withCommand("undo").withAlias("undo1").build();


    private TypicalAliases() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Alias alias : getTypicalAliases()) {
            try {
                ab.addAlias(alias);
            } catch (DuplicateAliasException e) {
                throw new AssertionError("not possible");
            }
        }
        return ab;
    }

    public static List<Alias> getTypicalAliases() {
        return new ArrayList<>(Arrays.asList(ADD, ALIAS, CLEAR, DELETE, EDIT, EXIT, HELP, HISTORY, LIST, REDO, UNDO));
    }
}
