package seedu.organizer.model.subtask;

//@@author agus
import static java.util.Collections.reverse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.organizer.model.util.SampleDataUtil;
import seedu.organizer.testutil.Assert;

public class UniqueSubtaskListTest {
    @Test
    public void constructor_null_nullpointerexception() {
        Assert.assertThrows(NullPointerException.class, () -> new UniqueSubtaskList(null));
    }

    @Test
    public void constructor_validInput_preserveOrder() {
        List<Subtask> subtasks = SampleDataUtil.getSubtaskList("a", "b");
        UniqueSubtaskList subtaskList = new UniqueSubtaskList(subtasks);
        assertEquals(subtaskList.toList(), subtasks);
        reverse(subtasks);
        assertNotEquals(subtaskList.toList(), subtasks);
    }

    @Test
    public void setSubtasks_validInput_canchange() {
        List<Subtask> subtasks = SampleDataUtil.getSubtaskList("a", "b");
        List<Subtask> otherSubtasks = SampleDataUtil.getSubtaskList("c", "d");
        UniqueSubtaskList subtaskList = new UniqueSubtaskList(subtasks);
        subtaskList.setSubtasks(otherSubtasks);
        assertNotEquals(subtaskList.toList(), subtasks);
        assertEquals(subtaskList.toList(), otherSubtasks);
    }

    @Test
    public void contains_validInput_chackforcontains() {
        List<Subtask> subtasks = SampleDataUtil.getSubtaskList("a", "b");
        List<Subtask> cloned = new ArrayList<>(subtasks);
        List<Subtask> otherSubtasks = SampleDataUtil.getSubtaskList("c", "d");
        UniqueSubtaskList subtaskList = new UniqueSubtaskList(subtasks);

        for (Subtask subtask: cloned) {
            assertTrue(subtaskList.contains(subtask));
        }

        for (Subtask subtask: otherSubtasks) {
            assertFalse(subtaskList.contains(subtask));
        }
    }

    @Test
    public void add_allInput() throws Exception {
        List<Subtask> subtasks = SampleDataUtil.getSubtaskList("a", "b");
        List<Subtask> otherSubtasks = SampleDataUtil.getSubtaskList("c", "d");
        UniqueSubtaskList subtaskList = new UniqueSubtaskList(subtasks);

        Assert.assertThrows(NullPointerException.class, () -> subtaskList.add(null));
        Assert.assertThrows(UniqueSubtaskList.DuplicateSubtaskException.class, () -> subtaskList.add(subtasks.get(0)));
        subtaskList.add(otherSubtasks.get(0));
        subtasks.add(otherSubtasks.get(0));
        assertEquals(subtaskList.toList(), subtasks);
    }

    @Test
    public void iterator() {
        List<Subtask> subtasks = SampleDataUtil.getSubtaskList("a", "b");
        List<Subtask> otherSubtasks = SampleDataUtil.getSubtaskList();
        UniqueSubtaskList subtaskList = new UniqueSubtaskList(subtasks);

        for (Subtask subtask: subtaskList) {
            otherSubtasks.add(subtask);
        }

        assertEquals(subtasks, otherSubtasks);
    }

    @Test
    public void asObservableList() {
        List<Subtask> subtasks = SampleDataUtil.getSubtaskList("a", "b");
        List<Subtask> otherSubtasks = SampleDataUtil.getSubtaskList();
        UniqueSubtaskList subtaskList = new UniqueSubtaskList(subtasks);

        for (Subtask subtask: subtaskList.asObservableList()) {
            otherSubtasks.add(subtask);
        }

        assertEquals(subtasks, otherSubtasks);
    }

    @Test
    public void equals() {
        List<Subtask> subtasks = SampleDataUtil.getSubtaskList("a", "b");
        List<Subtask> otherSubtasks = SampleDataUtil.getSubtaskList("b", "a");
        UniqueSubtaskList subtaskList = new UniqueSubtaskList(subtasks);
        UniqueSubtaskList otherSubtaskList = new UniqueSubtaskList(otherSubtasks);

        assertNotEquals(subtaskList, otherSubtaskList);
        assertEquals(subtaskList, subtaskList);
    }

    @Test
    public void equalsOrderInsensitive_true() {
        List<Subtask> subtasks = SampleDataUtil.getSubtaskList("a", "b");
        List<Subtask> otherSubtasks = SampleDataUtil.getSubtaskList("b", "a");
        UniqueSubtaskList subtaskList = new UniqueSubtaskList(subtasks);
        UniqueSubtaskList otherSubtaskList = new UniqueSubtaskList(otherSubtasks);

        assertTrue(subtaskList.equalsOrderInsensitive(otherSubtaskList));
    }

    @Test
    public void equalsOrderInsensitive_false() {
        List<Subtask> subtasks = SampleDataUtil.getSubtaskList("a", "b");
        List<Subtask> otherSubtasks = SampleDataUtil.getSubtaskList("c", "a");
        UniqueSubtaskList subtaskList = new UniqueSubtaskList(subtasks);
        UniqueSubtaskList otherSubtaskList = new UniqueSubtaskList(otherSubtasks);

        assertFalse(subtaskList.equalsOrderInsensitive(otherSubtaskList));
    }

    @Test
    public void hash_same() {
        List<Subtask> subtasks = SampleDataUtil.getSubtaskList("a", "b");
        List<Subtask> otherSubtasks = SampleDataUtil.getSubtaskList("a", "c");
        UniqueSubtaskList subtaskList = new UniqueSubtaskList(subtasks);
        UniqueSubtaskList otherSubtaskList = new UniqueSubtaskList(subtasks);
        UniqueSubtaskList diffSubtaskList = new UniqueSubtaskList(otherSubtasks);

        assertEquals(subtaskList.hashCode(), otherSubtaskList.hashCode());
        assertEquals(subtaskList.hashCode(), subtaskList.hashCode());
        assertNotEquals(subtaskList.hashCode(), diffSubtaskList.hashCode());
    }
}
