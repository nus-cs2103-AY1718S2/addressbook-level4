package seedu.address.model.util;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.EventBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyTaskBook;
import seedu.address.model.TaskBook;
import seedu.address.model.category.TaskCategory;
import seedu.address.model.event.Datetime;
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
            new Person(new Name("Eunice Goh"), new Phone("93334555"), new Email("eunice_goh@example.com"),
                new Address("Blk 403 Ang Mo Kio Street 20, #10-13"),
                getTagSet("classmate")),
            new Person(new Name("Francis Lim"), new Phone("91232345"), new Email("francis.lim@example.com"),
                new Address("Blk 133 Bishan Avenue 4, #12-23"),
                getTagSet("classmate")),
            new Person(new Name("George Lee"), new Phone("98769867"), new Email("georgelee@example.com"),
                new Address("Blk 100 Marine Parade Street 12, #13-33"),
                getTagSet("uncle")),
            new Person(new Name("Harold Sim"), new Phone("98723345"), new Email("harold_shy@example.com"),
                new Address("Blk 55 Toa Payoh Street 7, #05-52"),
                getTagSet("classmate")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("classmates")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("colleagues"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        try {
            AddressBook sampleAb = new AddressBook();
            for (Person samplePerson : getSamplePersons()) {
                sampleAb.addPerson(samplePerson);
            }
            return sampleAb;
        } catch (DuplicatePersonException e) {
            throw new AssertionError("sample data cannot contain duplicate persons", e);
        }
    }

    //@@author x3tsunayh

    public static Event[] getSampleEvents() throws IllegalValueException {
        return new Event[]{
            new Event("Company Charity Event", "Annual fund-raising event",
                "VivoCity", new Datetime("2017-10-31 0930")),
            new Event("Launch Marketing Campaign", "New products to be released",
                "-", new Datetime("2018-03-14 1000")),
            new Event("Class Reunion", "Catchup session with old classmates",
                "Tampines Hub", new Datetime("2018-04-15 1745")),
            new Event("CS2103 Demo Presentation", "Flight to London",
                "COM1-B103", new Datetime("2018-04-19 1500")),
            new Event("Marketing Department Meeting", "Monthly Meeting",
                "Company Conference Room 2", new Datetime("2018-04-20 1315")),
            new Event("Flight to London", "-",
                "Changi Airport", new Datetime("2018-05-12 2250")),
            new Event("Company Tour", "Checking out company operations in London",
                "ABC Company (London)", new Datetime("2018-05-14 0930")),
            new Event("Overseas Meeting", "Discussion w/ London representatives",
                "ABC Conference Hall 1", new Datetime("2018-05-18 1300")),
            new Event("Flight back to Singapore", "-",
                "Heathrow Airport", new Datetime("2018-05-21 1420")),
            new Event("Musical Concert", "Friend's performance",
                "NUS UCC", new Datetime("2018-05-23 1900"))
        };
    }

    public static ReadOnlyEventBook getSampleEventBook() {
        try {
            EventBook sampleEb = new EventBook();
            for (Event sampleEvent : getSampleEvents()) {
                sampleEb.addEvent(sampleEvent);
            }
            return sampleEb;
        } catch (CommandException e) {
            throw new AssertionError("sample data cannot contain duplicate events ", e);
        } catch (IllegalValueException e) {
            throw new AssertionError("Invalid input given!");
        }
    }

    //@@author CYX28
    public static Task[] getSampleTasks() {
        return new Task[] {
            new Task(new TaskName("Programming Project"), new TaskPriority("low"),
                new TaskDescription("(1) Analyse requirements (2) Write programs (3) Testing (4) Documentation "
                    + "(5) Submit to boss"), new TaskDueDate("2018-04-10"), new TaskStatus("undone"),
                getTaskCategorySet("personal", "interest")),
            new Task(new TaskName("Project meeting with the group and department"), new TaskPriority("high"),
                new TaskDescription("Finalise on project features"),
                new TaskDueDate("2018-04-22"), new TaskStatus("undone"),
                getTaskCategorySet("meeting", "ahighprofileprojectthatcannotbedelayedanymore")),
            new Task(new TaskName("Follow up with boss"), new TaskPriority("medium"),
                new TaskDescription("Present proposal to boss regarding project concerns"),
                new TaskDueDate("2018-04-25"), new TaskStatus("undone"),
                getTaskCategorySet("business", "work", "meeting", "boss", "proposal")),
            new Task (new TaskName("Agenda for business meeting"), new TaskPriority("high"),
                new TaskDescription("Discuss proposal details"),
                new TaskDueDate("2018-04-27"), new TaskStatus("undone"),
                getTaskCategorySet("meeting")),
            new Task(new TaskName("Clarify with client"), new TaskPriority("medium"),
                new TaskDescription("Clarify client's expectation of the project"),
                new TaskDueDate("2018-04-28"), new TaskStatus("undone"),
                getTaskCategorySet("work", "meeting", "client")),
            new Task(new TaskName("Buy birthday gift"), new TaskPriority("low"),
                new TaskDescription("Buy a gift for cousin's birthday"),
                new TaskDueDate("2018-04-30"), new TaskStatus("undone"),
                getTaskCategorySet("personal")),
            new Task(new TaskName("Audit department records"), new TaskPriority("high"),
                new TaskDescription("Preparation for monthly report"),
                new TaskDueDate("2018-04-30"), new TaskStatus("undone"),
                getTaskCategorySet("work")),
            new Task(new TaskName("Submit department report"), new TaskPriority("high"),
                new TaskDescription("Monthly report from the department"),
                new TaskDueDate("2018-05-01"), new TaskStatus("undone"),
                getTaskCategorySet("work")),
            new Task(new TaskName("Organizing of department cohesion event"), new TaskPriority("medium"),
                new TaskDescription("Liaising with finance department"),
                new TaskDueDate("2018-05-01"), new TaskStatus("undone"),
                getTaskCategorySet("work")),
            new Task(new TaskName("Collect Taobao Package"), new TaskPriority("low"),
                new TaskDescription("Taobao delivery on this day"),
                new TaskDueDate("2018-05-03"), new TaskStatus("undone"),
                getTaskCategorySet("personal"))
        };
    }

    public static ReadOnlyTaskBook getSampleTaskBook() {
        try {
            TaskBook sampleTb = new TaskBook();
            for (Task sampleTask : getSampleTasks()) {
                sampleTb.addTask(sampleTask);
            }
            return sampleTb;
        } catch (DuplicateTaskException dte) {
            throw new AssertionError("sample data cannot contain duplicate tasks", dte);
        }
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

    //@@author
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
