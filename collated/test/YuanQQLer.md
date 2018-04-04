# YuanQQLer
###### \java\seedu\address\testutil\EventBuilder.java
``` java
/**
 * This the class to build event
 */
public class EventBuilder implements ActivityBuilder {

    public static final String DEFAULT_NAME = "CIP";
    public static final String DEFAULT_START_DATETIME = "04/04/2018 08:10";
    public static final String DEFAULT_END_DATETIME = "04/04/2018 10:00";
    public static final String DEFAULT_LOCATION = "123, Jurong West Ave 6";
    public static final String DEFAULT_REMARK = "nil";
    public static final String DEFAULT_TAGS = "optional";

    private Name name;
    private DateTime startDateTime;
    private DateTime endDateTime;
    private Location location;
    private Remark remark;
    private Set<Tag> tags;

    public EventBuilder() {
        name = new Name(DEFAULT_NAME);
        startDateTime = new DateTime(DEFAULT_START_DATETIME);
        endDateTime = new DateTime(DEFAULT_END_DATETIME);
        location = new Location(DEFAULT_LOCATION);
        remark = new Remark(DEFAULT_REMARK);
        tags = SampleDataUtil.getTagSet(DEFAULT_TAGS);
    }

    /**
     * Initializes the EventBuilder with the data of {@code activityToCopy}.
     */
    public EventBuilder(Event eventToCopy) {
        name = eventToCopy.getName();
        startDateTime = eventToCopy.getStartDateTime();
        endDateTime = eventToCopy.getEndDateTime();
        location = eventToCopy.getLocation();
        remark = eventToCopy.getRemark();
        tags = new HashSet<>(eventToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Activity} that we are building.
     */
    public EventBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Activity} that we are building.
     */
    public EventBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Remark} of the {@code Activity} that we are building.
     */
    public EventBuilder withRemark(String remark) {
        this.remark = new Remark(remark);
        return this;
    }

    /**
     * Sets the {@code DateTime} of the {@code Activity} that we are building.
     */
    public EventBuilder withStartDateTime(String dateTime) {
        this.startDateTime = new DateTime(dateTime);
        return this;
    }

    /**
     * Sets the {@code DateTime} of the {@code Activity} that we are building.
     */
    public EventBuilder withEndDateTime(String dateTime) {
        this.endDateTime = new DateTime(dateTime);
        return this;
    }

    /**
     * Sets the {@code Location} of the {@code Activity} that we are building.
     */
    public EventBuilder withLocation (String location) {
        this.location = new Location(location);
        return this;
    }

```
###### \java\seedu\address\testutil\TaskBuilder.java
``` java
/**
 * Build task for testing
 */
public class TaskBuilder implements ActivityBuilder {
    public static final String DEFAULT_NAME = "Assignment";
    public static final String DEFAULT_DATETIME = "04/04/2018 08:10";
    public static final String DEFAULT_REMARK = "Urgent";
    public static final String DEFAULT_TAGS = "Optional";

    private Name name;
    private DateTime dateTime;
    private Remark remark;
    private Set<Tag> tags;

    public TaskBuilder() {
        name = new Name(DEFAULT_NAME);
        dateTime = new DateTime(DEFAULT_DATETIME);
        remark = new Remark(DEFAULT_REMARK);
        tags = SampleDataUtil.getTagSet(DEFAULT_TAGS);
    }

    /**
     * Initializes the ActivityBuilder with the data of {@code taskToCopy}.
     */
    public TaskBuilder(Task taskToCopy) {
        name = taskToCopy.getName();
        dateTime = taskToCopy.getDateTime();
        remark = taskToCopy.getRemark();
        tags = new HashSet<>(taskToCopy.getTags());
    }

    /**
     * Initializes the ActivityBuilder with the data of {@code taskToCopy}.
     */
    public TaskBuilder(Activity activityToCopy) {
        name = activityToCopy.getName();
        dateTime = activityToCopy.getDateTime();
        remark = activityToCopy.getRemark();
        tags = new HashSet<>(activityToCopy.getTags());
    }
    /**
     * Sets the {@code Name} of the {@code Activity} that we are building.
     */
    public TaskBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Activity} that we are building.
     */
    public TaskBuilder withTags(String... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Remark} of the {@code Activity} that we are building.
     */
    public TaskBuilder withRemark(String remark) {
        this.remark = new Remark(remark);
        return this;
    }

    /**
     * Sets the {@code DateTime} of the {@code Activity} that we are building.
     */
    public TaskBuilder withDateTime(String dateTime) {
        this.dateTime = new DateTime(dateTime);
        return this;
    }

```
###### \java\seedu\address\testutil\TypicalActivities.java
``` java
/**
 * A utility class containing a list of {@code Activity} objects to be used in tests.
 */
public class TypicalActivities {

    public static final Task ASSIGNMENT1 = new TaskBuilder().withName("CS2101Assignment")
            .withRemark(" ")
            .withDateTime("04/03/2018 23:59")
            .withTags("CS2101").build();
    public static final Task ASSIGNMENT2 = new TaskBuilder().withName("CS2102Assignment")
            .withRemark(" ")
            .withDateTime("15/03/2018 23:59")
            .withTags("CS2102").build();
    public static final Task QUIZ = new TaskBuilder().withName("CS2101Quiz")
            .withDateTime("19/03/2018 23:59")
            .withRemark("IVLE Quiz").build();
    public static final Event CCA = new EventBuilder().withName("CCA")
            .withStartDateTime("01/04/2018 20:00")
            .withEndDateTime("01/04/2018 21:00")
            .withLocation("Campus")
            .withRemark("nil").build();
    public static final Event CIP = new EventBuilder().withName("CIP")
            .withStartDateTime("02/04/2018 08:00")
            .withEndDateTime("02/04/2018 12:00")
            .withLocation("michegan ave")
            .withRemark("nil")
            .withTags("CIP").build();
    public static final Event EXAM1 = new EventBuilder().withName("CS2101Exam")
            .withStartDateTime("28/04/2018 09:00")
            .withEndDateTime("28/04/2018 11:00")
            .withLocation("MPSH")
            .withRemark("nil")
            .withTags("CS2101").build();
    public static final Event IFG = new EventBuilder().withName("InterFacultyGame")
            .withStartDateTime("04/01/2018 20:00")
            .withEndDateTime("04/01/2018 22:00")
            .withLocation("MPSH 1")
            .withRemark("nil").build();

    // Manually added
    public static final Task ASSIGNMENT3 = new TaskBuilder().withName("CS2102Assignment")
            .withDateTime("01/04/2018 20:00")
            .withRemark("nil").build();
    public static final Event DEMO1 = new EventBuilder().withName("CS2102ProjectDemo")
            .withStartDateTime("04/04/2018 09:00")
            .withEndDateTime("04/04/2018 10:00")
            .withRemark("FinalDemo").build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalActivities() {} // prevents instantiation

    /**
     * Returns an {@code DeskBoard} with all the typical activities.
     */
    public static DeskBoard getTypicalDeskBoard() {
        DeskBoard deskBoard = new DeskBoard();
        for (Activity activity : getTypicalActivities()) {
            try {
                deskBoard.addActivity(activity);
            } catch (DuplicateActivityException e) {
                throw new AssertionError("Not possible");
            }
        }
        return deskBoard;
    }

    public static List<Activity> getTypicalActivities() {
        return new ArrayList<>(Arrays.asList(ASSIGNMENT1, ASSIGNMENT2, QUIZ, CCA, CIP, EXAM1, IFG));
    }

    public static List<Activity> getTypicalTask() {
        return new ArrayList<>(Arrays.asList(ASSIGNMENT1, ASSIGNMENT2, QUIZ));
    }
}
```
