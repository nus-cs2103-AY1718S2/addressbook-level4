package seedu.organizer.model.util;

import java.util.HashSet;
import java.util.Set;

import seedu.organizer.model.Organizer;
import seedu.organizer.model.ReadOnlyOrganizer;
import seedu.organizer.model.tag.Tag;
import seedu.organizer.model.task.Address;
import seedu.organizer.model.task.Deadline;
import seedu.organizer.model.task.Name;
import seedu.organizer.model.task.Priority;
import seedu.organizer.model.task.Task;
import seedu.organizer.model.task.exceptions.DuplicateTaskException;

/**
 * Contains utility methods for populating {@code Organizer} with sample data.
 */
public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        return new Task[]{
            new Task(new Name("Exam"), new Priority("9"), new Deadline("2018-05-11"),
                        new Address("Blk 30 Geylang Street 29, #06-40"),
                        getTagSet("friends")),
            new Task(new Name("Study"), new Priority("8"), new Deadline("2018-03-29"),
                        new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                        getTagSet("colleagues", "friends")),
            new Task(new Name("Revision"), new Priority("7"), new Deadline("2018-03-27"),
                        new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                        getTagSet("neighbours")),
            new Task(new Name("Interview preparation"), new Priority("5"), new Deadline("2018-04-03"),
                        new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                        getTagSet("family")),
            new Task(new Name("Learn Java"), new Priority("2"), new Deadline("2018-07-07"),
                        new Address("Blk 47 Tampines Street 20, #17-35"),
                        getTagSet("classmates")),
            new Task(new Name("Learn PHP"), new Priority("0"), new Deadline("2018-04-30"),
                        new Address("Blk 45 Aljunied Street 85, #11-31"),
                        getTagSet("colleagues"))
        };
    }

    public static ReadOnlyOrganizer getSampleAddressBook() {
        try {
            Organizer sampleAb = new Organizer();
            for (Task sampleTask : getSampleTasks()) {
                sampleAb.addTask(sampleTask);
            }
            return sampleAb;
        } catch (DuplicateTaskException e) {
            throw new AssertionError("sample data cannot contain duplicate persons", e);
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

}
