//@@author jas5469
package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.group.Group;
import seedu.address.model.group.UniqueGroupList;
import seedu.address.model.group.exceptions.DuplicateGroupException;

/**
 * A utility class containing a list of {@code Group} objects to be used in tests.
 */
public class TypicalGroups {

    public static final Group GROUP_A = new GroupBuilder().withInformation("Group A").build();
    public static final Group GROUP_B = new GroupBuilder().withInformation("Group B").build();
    public static final Group GROUP_C = new GroupBuilder().withInformation("Group C").build();
    public static final Group GROUP_D = new GroupBuilder().withInformation("Group D").build();
    public static final Group GROUP_E = new GroupBuilder().withInformation("Group E").build();
    public static final Group GROUP_F = new GroupBuilder().withPerson("Group F",
            TypicalPersons.ALICE).build();
    public static final Group GROUP_G = new GroupBuilder().withPerson("Group G",
            TypicalPersons.BENSON).build();
    public static final Group GROUP_H = new GroupBuilder().withPerson("Group H", TypicalPersons.ALICE,
            TypicalPersons.BENSON).build();
    public static final Group GROUP_I = new GroupBuilder().withInformation("Group F").build();

    public static List<Group> getTypicalGroups() {
        return new ArrayList<>(Arrays.asList(GROUP_A, GROUP_B, GROUP_F, GROUP_H));
    }

    public static UniqueGroupList getTypicalGroupsList() {
        UniqueGroupList groupList = new UniqueGroupList();
        try {
            groupList.add(new Group(GROUP_A.getInformation(), GROUP_A.getPersonList()));
            groupList.add(new Group(GROUP_B.getInformation(), GROUP_B.getPersonList()));
            groupList.add(new Group(GROUP_F.getInformation(), GROUP_F.getPersonList()));
            groupList.add(new Group(GROUP_H.getInformation(), GROUP_H.getPersonList()));
        } catch (DuplicateGroupException e) {
            throw new IllegalArgumentException("group is expected to be unique.");
        }
        return groupList;
    }
}
