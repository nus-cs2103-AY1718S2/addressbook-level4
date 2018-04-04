# karenfrilya97
###### \java\seedu\address\model\activity\UniqueActivityList.java
``` java
    private static Comparator<Activity> dateTimeComparator = new Comparator<Activity>() {
        public int compare (Activity o1, Activity o2) {
            DateTime dt1 = o1.getDateTime();
            DateTime dt2 = o2.getDateTime();
            return dt1.getLocalDateTime().compareTo(dt2.getLocalDateTime());
        }
    };

    private final ObservableList<Activity> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent activity as the given argument.
     */
    public boolean contains(Activity toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }


    /**
     * Adds a activity to the list.
     * If activity is a task or an event, is added to its respective list.
     *
     * @throws DuplicateActivityException if the activity to add is a duplicate of an existing activity in the list.
     */
    public void add(Activity toAdd) throws DuplicateActivityException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateActivityException();
        }
        internalList.add(toAdd);
```
###### \java\seedu\address\model\activity\UniqueActivityList.java
``` java
        Collections.sort(internalList, dateTimeComparator);
    }

    /**
     * Replaces the activity {@code target} in the list with {@code editedActivity}.
     * If activity is a task or an event, edited in its respective list.
     *
     * @throws DuplicateActivityException if the replacement is equivalent to another existing activity in the list.
     * @throws ActivityNotFoundException if {@code target} could not be found in the list.
     */
    public void setActivity(Activity target, Activity editedActivity)
            throws DuplicateActivityException, ActivityNotFoundException {
        requireNonNull(editedActivity);

        int index = internalList.indexOf(target);
        int taskIndex;
        int eventIndex;

        if (index == -1) {
            throw new ActivityNotFoundException();
        }

        if (!target.equals(editedActivity) && internalList.contains(editedActivity)) {
            throw new DuplicateActivityException();
        }

        internalList.set(index, editedActivity);

    }

    public void setActivity(UniqueActivityList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setActivity(List<Activity> activities) throws DuplicateActivityException {
        requireAllNonNull(activities);
        final UniqueActivityList replacement = new UniqueActivityList();
        for (final Activity activity : activities) {
            replacement.add(activity);
        }
        setActivity(replacement);
    }

    /**
     * Removes the equivalent activity from the list and its respective task or event list.
     *
     * @throws ActivityNotFoundException if no such activity could be found in the list.
     */
    public boolean remove(Activity toRemove) throws ActivityNotFoundException {
        requireNonNull(toRemove);
        final boolean activityFoundAndDeleted = internalList.remove(toRemove);
        if (!activityFoundAndDeleted) {
            throw new ActivityNotFoundException();
```
###### \java\seedu\address\storage\XmlAdaptedActivity.java
``` java
/**
 * JAXB-friendly version of the Activity.
 */
public abstract class XmlAdaptedActivity {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "%s's %s field is missing!";

    @XmlElement(required = true)
    protected String name;
    @XmlElement(required = true)
    protected String dateTime;
    @XmlElement(required = true)
    protected String remark;
    @XmlElement
    protected boolean iscompleted;
    @XmlElement
    protected List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedActivity.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedActivity() {}

    /**
     * Constructs an {@code XmlAdaptedActivity} with the given activity details.
     */
    public XmlAdaptedActivity(String name, String dateTime, String remark, List<XmlAdaptedTag> tagged) {
        this.name = name;
        this.dateTime = dateTime;
        this.remark = remark;
        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
        }
        iscompleted = false;
    }

    /**
     * Converts a given Activity into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedActivity
     */
    public XmlAdaptedActivity(Activity source) {
        name = source.getName().fullName;
        dateTime = source.getDateTime().toString();
        remark = source.getRemark().value;
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
        iscompleted = source.isCompleted();
    }

    /**
     * Converts this jaxb-friendly adapted activity object into the model's Activity object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted activity
     */
    public abstract Activity toModelType() throws IllegalValueException;

    public abstract String getActivityType();

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedActivity)) {
            return false;
        }

        XmlAdaptedActivity otherActivity = (XmlAdaptedActivity) other;
        return Objects.equals(name, otherActivity.name)
                && Objects.equals(dateTime, otherActivity.dateTime)
                && Objects.equals(remark, otherActivity.remark)
                && tagged.equals(otherActivity.tagged)
                && this.iscompleted == otherActivity.iscompleted;
    }
}
```
###### \java\seedu\address\storage\XmlAdaptedEvent.java
``` java
/**
 * JAXB-friendly version of the Event.
 */
public class XmlAdaptedEvent extends XmlAdaptedActivity {

    private static final String ACTIVITY_TYPE = "Event";

    @XmlElement(required = true)
    private String endDateTime;
    @XmlElement(required = true)
    private String location;

    /**
     * Constructs an XmlAdaptedEvent.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedEvent() {}

    /**
     * Constructs an {@code XmlAdaptedEvent} with the given event details.
     */
    public XmlAdaptedEvent(String name, String startDateTime, String endDateTime,
                           String location, String remark, List<XmlAdaptedTag> tagged) {
        super(name, startDateTime, remark, tagged);
        this.endDateTime = endDateTime;
        this.location = location;
    }

    /**
     * Converts a given Event into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedEvent
     */
    public XmlAdaptedEvent(Event source) {
        super((Activity) source);
        endDateTime = source.getEndDateTime().toString();
        location = source.getLocation().toString();
    }

    /**
     * Converts this jaxb-friendly adapted Event object into the model's Event object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted ectivity
     */
    @Override
    public Event toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }

        if (this.name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    getActivityType(), "name"));
        }
        if (!Name.isValidName(this.name)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        final Name name = new Name(this.name);

        if (this.dateTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    getActivityType(), "start date/time"));
        }
        if (!DateTime.isValidDateTime(this.dateTime)) {
            throw new IllegalValueException(DateTime.MESSAGE_DATETIME_CONSTRAINTS);
        }
        final DateTime startDateTime = new DateTime(this.dateTime);

        if (this.endDateTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    getActivityType(), "end date/time"));
        }
        if (!DateTime.isValidDateTime(this.endDateTime)) {
            throw new IllegalValueException(DateTime.MESSAGE_DATETIME_CONSTRAINTS);
        }
        final DateTime endDateTime = new DateTime(this.endDateTime);

        if (!Location.isValidLocation(this.location)) {
            throw new IllegalValueException(Location.MESSAGE_LOCATION_CONSTRAINTS);
        }
        final Location location = new Location(this.location);

        if (!Remark.isValidRemark(this.remark)) {
            throw new IllegalValueException(Remark.MESSAGE_REMARK_CONSTRAINTS);
        }
        final Remark remark = new Remark(this.remark);

        final Set<Tag> tags = new HashSet<>(personTags);
        return new Event(name, startDateTime, endDateTime, location, remark, tags, this.iscompleted);
    }

    @Override
    public String getActivityType() {
        return ACTIVITY_TYPE;
    }

    @Override
    public boolean equals(Object other) {
        if (!super.equals(other)) {
            return false;
        }

        if (!(other instanceof XmlAdaptedEvent)) {
            return false;
        }

        XmlAdaptedEvent otherEvent = (XmlAdaptedEvent) other;
        return Objects.equals(endDateTime, otherEvent.endDateTime)
                && Objects.equals(location, otherEvent.location);
    }

}
```
###### \java\seedu\address\storage\XmlAdaptedTask.java
``` java
/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask extends XmlAdaptedActivity {

    private static final String ACTIVITY_TYPE = "Task";

    /**
     * Constructs an XmlAdaptedTask.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedTask() {}

    /**
     * Constructs an {@code XmlAdaptedTask} with the given task details.
     */
    public XmlAdaptedTask(String name, String dueDateTime, String remark, List<XmlAdaptedTag> tagged) {
        super(name, dueDateTime, remark, tagged);
    }

    /**
     * Converts a given Task into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedTask
     */
    public XmlAdaptedTask(Task source) {
        super(source);
    }

    /**
     * Converts this jaxb-friendly adapted Task object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task
     */
    public Task toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }

        if (this.name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    getActivityType(), "name"));
        }
        if (!Name.isValidName(this.name)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        final Name name = new Name(this.name);

        if (this.dateTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    getActivityType(), "due date/time"));
        }
        if (!DateTime.isValidDateTime(this.dateTime)) {
            throw new IllegalValueException(DateTime.MESSAGE_DATETIME_CONSTRAINTS);
        }
        final DateTime dateTime = new DateTime(this.dateTime);

        if (!Remark.isValidRemark(this.remark)) {
            throw new IllegalValueException(Remark.MESSAGE_REMARK_CONSTRAINTS);
        }
        final Remark remark = new Remark(this.remark);

        final Set<Tag> tags = new HashSet<>(personTags);

        return new Task(name, dateTime, remark, tags, this.iscompleted);
    }

    @Override
    public String getActivityType() {
        return ACTIVITY_TYPE;
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other) && other instanceof XmlAdaptedTask;
    }

}
```
###### \java\seedu\address\storage\XmlSerializableDeskBoard.java
``` java
/**
 * An Immutable DeskBoard that is serializable to XML format
 */
@XmlRootElement(name = "deskboard")
public class XmlSerializableDeskBoard {

    @XmlElement
    private List<XmlAdaptedTask> tasks;
    @XmlElement
    private List<XmlAdaptedEvent> events;
    @XmlElement
    private List<XmlAdaptedTag> tags;

    /**
     * Creates an empty XmlSerializableDeskBoard.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableDeskBoard() {
        tasks = new ArrayList<>();
        events = new ArrayList<>();
        tags = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableDeskBoard(ReadOnlyDeskBoard src) {
        this();
        for (Activity activity : src.getActivityList()) {
            if (activity instanceof Task) {
                tasks.add(new XmlAdaptedTask((Task) activity));
            } else if (activity instanceof Event) {
                events.add(new XmlAdaptedEvent((Event) activity));
            }
        }
        tags.addAll(src.getTagList().stream().map(XmlAdaptedTag::new).collect(Collectors.toList()));
    }

    /**
     * Converts this addressbook into the model's {@code DeskBoard} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedActivity} or {@code XmlAdaptedTag}.
     */
    public DeskBoard toModelType() throws IllegalValueException {
        DeskBoard deskBoard = new DeskBoard();
        for (XmlAdaptedTag t : tags) {
            deskBoard.addTag(t.toModelType());
        }
        for (XmlAdaptedActivity a : tasks) {
            deskBoard.addActivity(((XmlAdaptedTask) a).toModelType());
        }
        for (XmlAdaptedActivity e : events) {
            deskBoard.addActivity(((XmlAdaptedEvent) e).toModelType());
        }
        return deskBoard;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableDeskBoard)) {
            return false;
        }

        XmlSerializableDeskBoard otherDb = (XmlSerializableDeskBoard) other;
        return tasks.equals(otherDb.tasks) && events.equals(otherDb.events) && tags.equals(otherDb.tags);
    }
}
```
