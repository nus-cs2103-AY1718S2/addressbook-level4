package seedu.organizer.model.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.organizer.model.Organizer;
import seedu.organizer.model.ReadOnlyOrganizer;
import seedu.organizer.model.subtask.Subtask;
import seedu.organizer.model.tag.Tag;
import seedu.organizer.model.task.Deadline;
import seedu.organizer.model.task.Description;
import seedu.organizer.model.task.Name;
import seedu.organizer.model.task.Priority;
import seedu.organizer.model.task.Status;
import seedu.organizer.model.task.Task;
import seedu.organizer.model.task.exceptions.DuplicateTaskException;

/**
 * Contains utility methods for populating {@code Organizer} with sample data.
 */
public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        return new Task[]{
            new Task(new Name("Learn PHP"), new Priority("5"), new Deadline("2018-04-30"),
                    new Description("Find a PHP tutorial and learn from it"),
                    getTagSet("colleagues")),
            new Task(new Name("Learn Java"), new Priority("4"), new Deadline("2018-07-07"),
                    new Description("Learn OOP"),
                    getTagSet("classmates")),
            new Task(new Name("Interview preparation"), new Priority("3"), new Deadline("2018-04-03"),
                    new Description("Prepare for MOH Interview"),
                    getTagSet("family")),
            new Task(new Name("Revision"), new Priority("2"), new Deadline("2018-03-27"),
                    new Description("Revise Topic 3 for CS2103T"),
                    getTagSet("neighbours")),
            new Task(new Name("Study"), new Priority("1"), new Deadline("2018-03-29"),
                    new Description("Study for CS2103T Exam"),
                    getTagSet("colleagues", "friends")),
            new Task(new Name("Exam"), new Priority("0"), new Deadline("2018-05-11"),
                        new Description("CS2103T Exam"),
                        getTagSet("friends"))
        };
    }

    public static ReadOnlyOrganizer getSampleOrganizer() {
        try {
            Organizer sampleAb = new Organizer();
            for (Task sampleTask : getSampleTasks()) {
                sampleAb.addTask(sampleTask);
            }
            return sampleAb;
        } catch (DuplicateTaskException e) {
            throw new AssertionError("sample data cannot contain duplicate tasks", e);
        }
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        HashSet<Tag> tags = new HashSet<>();
        for (String s : strings) {
            tags.add(new Tag(s));
        }

        return tags;
    }

    /**
     * Returns a subtask set containing the list of strings given.
     */
    public static List<Subtask> getSubtaskList(String... strings) {
        List<Subtask> subtasks = new ArrayList<>();
        for (String s : strings) {
            subtasks.add(new Subtask(new Name(s), new Status(false)));
        }

        return subtasks;
    }
}
