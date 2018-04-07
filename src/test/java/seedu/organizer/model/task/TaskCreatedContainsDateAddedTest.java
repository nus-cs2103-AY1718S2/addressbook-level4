package seedu.organizer.model.task;

import static junit.framework.TestCase.assertNotNull;

import static seedu.organizer.logic.commands.CommandTestUtil.VALID_DEADLINE_EXAM;
import static seedu.organizer.logic.commands.CommandTestUtil.VALID_DESCRIPTION_EXAM;
import static seedu.organizer.logic.commands.CommandTestUtil.VALID_NAME_EXAM;
import static seedu.organizer.logic.commands.CommandTestUtil.VALID_PRIORITY_EXAM;

import java.util.HashSet;

import org.junit.Test;

import seedu.organizer.model.tag.Tag;

//@@author dominickenn
/**\
 * Tests whether a DateAdded is automatically created upon Task creation
 */
public class TaskCreatedContainsDateAddedTest {

    @Test
    public void createTaskContainsDateAdded() {
        Task task = new Task(new Name(VALID_NAME_EXAM), new Priority(VALID_PRIORITY_EXAM),
                new Deadline(VALID_DEADLINE_EXAM), new Description(VALID_DESCRIPTION_EXAM),
                new HashSet<Tag>());
        assertNotNull(task.getDateAdded());
    }

}
