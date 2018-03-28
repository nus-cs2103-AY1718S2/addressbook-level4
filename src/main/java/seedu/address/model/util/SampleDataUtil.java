package seedu.address.model.util;

import java.util.HashSet;
import java.util.Set;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.EventBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.category.TaskCategory;
import seedu.address.model.event.Event;
import seedu.address.model.event.ReadOnlyEventBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskDescription;
import seedu.address.model.task.TaskDueDate;
import seedu.address.model.task.TaskName;
import seedu.address.model.task.TaskPriority;
import seedu.address.model.task.TaskStatus;
import seedu.address.model.task.exceptions.DuplicateTaskException;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("friends")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("colleagues", "friends")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("neighbours")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("family")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("classmates")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("colleagues"))
        };
    }

    public static Task[] getSampleTasks() {
        return new Task[] {
            new Task(new TaskName("Task XYZ"), new TaskPriority("Medium"),
                new TaskDescription("Task description XYZ"),
                new TaskDueDate("2018-04-28"), new TaskStatus("Undone"),
                getTaskCategorySet("Work")),
            new Task(new TaskName("Essay research"), new TaskPriority("Low"),
                new TaskDescription("Google for interesting points on essay topic"),
                new TaskDueDate("2018-05-29"), new TaskStatus("Undone"),
                getTaskCategorySet("Personal")),
            new Task(new TaskName("Formulate meeting agenda"), new TaskPriority("High"),
                new TaskDescription("(1) Analyse the project requirements (2) Record meeting agenda"),
                new TaskDueDate("2018-04-20"), new TaskStatus("Undone"),
                getTaskCategorySet("Meeting"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        try {
            AddressBook sampleAb = new AddressBook();
            for (Person samplePerson : getSamplePersons()) {
                sampleAb.addPerson(samplePerson);
            }
            for (Task sampleTask : getSampleTasks()) {
                sampleAb.addTask(sampleTask);
            }
            return sampleAb;
        } catch (DuplicatePersonException e) {
            throw new AssertionError("sample data cannot contain duplicate persons", e);
        } catch (DuplicateTaskException e) {
            throw new AssertionError("sample data cannot contain duplicate tasks", e);
        }
    }

    public static ReadOnlyEventBook getSampleEventBook() {
        try {
            EventBook sampleEb = new EventBook();
            for (Event sampleEvent : getSampleEvents()) {
                sampleEb.addEvent(sampleEvent);
            }
            return sampleEb;
        } catch (CommandException e) {
            throw new AssertionError("sample data cannot contain duplicate events", e);
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
     * Returns a task category set containing the list of strings given.
     */
    public static Set<TaskCategory> getTaskCategorySet(String... strings) {
        HashSet<TaskCategory> taskCategories = new HashSet<>();
        for (String s : strings) {
            taskCategories.add(new TaskCategory(s));
        }

        return taskCategories;
    }

    public static Event[] getSampleEvents() {
        return new Event[]{
            new Event(new String("Clique Gathering"), new String("Night out with friends"),
                new String("Tampines Hub"), new String("13-10-2017 1700")),
            new Event(new String("School Hall Concert"), new String("Friend performing"),
                new String("UCC"), new String("26-10-2017 1800")),
            new Event(new String("Halloween Party"), new String("Halloween Event"),
                new String("Andy's House"), new String("31-10-2017 2000"))
        };
    }
}
