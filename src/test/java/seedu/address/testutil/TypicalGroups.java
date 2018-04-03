package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.group.Group;

/**
 * A utility class containing a list of {@code Group} objects to be used in tests.
 */
public class TypicalGroups {

    public static final Group GROUP_A = new GroupBuilder().withInformation("Group A").build();
    public static final Group GROUP_B = new GroupBuilder().withInformation("Group B").build();
    public static final Group GROUP_C = new GroupBuilder().withInformation("Group C").build();
    public static final Group GROUP_D = new GroupBuilder().withInformation("Group A").build();
    public static final Group GROUP_E = new GroupBuilder().withInformation("Group E").build();


    public static List<Group> getTypicalGroups() {
        return new ArrayList<>(Arrays.asList(GROUP_A, GROUP_B, GROUP_C));
    }
}
