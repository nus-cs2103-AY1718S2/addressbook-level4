# YuanQQLer
###### \java\seedu\address\commons\events\model\DeskBoardChangedEvent.java
``` java
/** Indicates the DeskBoard in the model has changed*/
public class DeskBoardChangedEvent extends BaseEvent {

    public final ReadOnlyDeskBoard data;

    public DeskBoardChangedEvent(ReadOnlyDeskBoard data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of activities " + data.getActivityList().size() + ", number of tags " + data.getTagList().size();
    }
}
```
###### \java\seedu\address\commons\events\ui\ActivityPanelSelectionChangedEvent.java
``` java
/**
 * Represents a selection change in the Activity List Panel
 */
public class ActivityPanelSelectionChangedEvent extends BaseEvent {


    private final ActivityCard newSelection;

    public ActivityPanelSelectionChangedEvent(ActivityCard newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ActivityCard getNewSelection() {
        return newSelection;
    }
}
```
###### \java\seedu\address\model\activity\Activity.java
``` java
/**
 * Represents a Activity in the desk board.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public abstract class Activity {

    private final Name name;
    private final DateTime dateTime;
    private final Remark remark;

    private final UniqueTagList tags;
    private final boolean isCompleted;

    /**
     * Every field must be present and not null.
     */
    public Activity(Name name, DateTime dateTime, Remark remark, Set<Tag> tags) {
        requireAllNonNull(name, dateTime, tags);
        this.name = name;
        this.dateTime = dateTime;
        this.remark = remark;
        // protect internal tags from changes in the arg list
        this.tags = new UniqueTagList(tags);
        this.isCompleted = false;
    }

    public Activity(Name name, DateTime dateTime, Remark remark, Set<Tag> tags, boolean isCompleted) {
        requireAllNonNull(name, dateTime, tags);
        this.name = name;
        this.dateTime = dateTime;
        this.remark = remark;
        // protect internal tags from changes in the arg list
        this.tags = new UniqueTagList(tags);
        this.isCompleted = isCompleted;
    }

    public Name getName() {
        return name;
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public Remark getRemark() {
        return remark;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags.toSet());
    }

    public abstract String getActivityType();

    public abstract Activity copy(Set<Tag> tags);

    public boolean isCompleted() {
        return isCompleted;
    }

    public abstract Activity getCompletedCopy();
}
```
###### \java\seedu\address\model\activity\Event.java
``` java
/**
 * Represents a Task in the desk board.
 * The field contains 3 field, name, start date/time, end date/time and
 *      (Optional)location (Optional)remark.
 * The following example would illustrate one example
 * ******** Example ******************************* *
 * NAME : CS2103 Exam
 * START DATE/TIME: 21/03/2018 23:59
 * END DATE/TIME:
 * LOCATION: TBC
 * REMARK: Submit through a pull request in git hub.
 * ************************************************ *
 */
public class Event extends Activity {

    private static final String  ACTIVITY_TYPE = "EVENT";

    private final DateTime endDateTime;
    private final Location location;

    /**
     * Every field must be present and not null.
     */
    public Event(
            Name name, DateTime startDateTime, DateTime endDateTime, Location location, Remark remark, Set<Tag> tags) {
        super(name, startDateTime, remark, tags);
        requireAllNonNull(endDateTime);
        this.endDateTime = endDateTime;
        this.location = location;
    }

    public Event(
            Name name, DateTime startDateTime, DateTime endDateTime, Location location, Remark remark, Set<Tag> tags,
            boolean isComplete) {
        super(name, startDateTime, remark, tags, isComplete);
        requireAllNonNull(endDateTime);
        this.endDateTime = endDateTime;
        this.location = location;
    }

    @Override
    public Name getName() {
        return super.getName();
    }

    public DateTime getStartDateTime() {
        return super.getDateTime();
    }

    public DateTime getEndDateTime() {
        return this.endDateTime;
    }

    public Location getLocation() {
        return this.location;
    }
    @Override
    public Remark getRemark() {
        return super.getRemark();
    }

    @Override
    public String getActivityType() {
        return ACTIVITY_TYPE;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    @Override
    public Set<Tag> getTags() {
        return super.getTags();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Event)) {
            return false;
        }

        Event otherEvent = (Event) other;
        return otherEvent.getName().equals(this.getName())
                && otherEvent.getStartDateTime().equals(this.getStartDateTime())
                && otherEvent.getEndDateTime().equals(this.getEndDateTime())
                && (location == null ? otherEvent.location == null : location.equals(otherEvent.getLocation()))
                && (getRemark() == null ? otherEvent.getRemark() == null : getRemark().equals(otherEvent.getRemark()))
                && this.isCompleted() == otherEvent.isCompleted();
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Event Name: ")
                .append(getName())
                .append(" Start Date/Time: ")
                .append(getDateTime())
                .append(" End Date/Time")
                .append(getEndDateTime())
                .append(" Location: ")
                .append(getLocation() == null ? "" : getLocation())
                .append(" Remark: ")
                .append(getRemark() == null ? "" : getRemark())
                .append(" Tags: ")
                .append(isCompleted() ? "Uncompleted" : "Completed");
        getTags().forEach(builder::append);
        return builder.toString();
    }

    @Override
    public Activity copy(Set<Tag> tags) {
        if (tags == null) {
            return new Event(getName(), getStartDateTime(), getEndDateTime(), getLocation(), getRemark(), getTags(),
                    isCompleted());
        }
        return new Event(getName(), getStartDateTime(), getEndDateTime(), getLocation(), getRemark(), tags,
                isCompleted());
    }

    @Override
    public Activity getCompletedCopy() {
        return new Event(
                getName(), getStartDateTime(), getEndDateTime(), getLocation(), getRemark(), getTags(), true);
    }
}

```
###### \java\seedu\address\model\activity\EventOnlyPredicate.java
``` java
/**
 * This class gives a predicate that returns only the event in a list.
 */
public class EventOnlyPredicate implements Predicate<Activity> {

    public EventOnlyPredicate() {
        ;
    }

    @Override
    public boolean test(Activity activity) {
        return activity.getActivityType().equals("EVENT");
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventOnlyPredicate); // instanceof handles nulls
    }

}
```
###### \java\seedu\address\model\activity\exceptions\ActivityNotFoundException.java
``` java
/**
 * Signals that the operation is unable to find the specified activity.
 */
public class ActivityNotFoundException extends Exception {}
```
###### \java\seedu\address\model\activity\exceptions\DuplicateActivityException.java
``` java
/**
 * Signals that the operation will result in duplicate Activity objects.
 */
public class DuplicateActivityException extends DuplicateDataException {
    public DuplicateActivityException() {
        super("Operation would result in duplicate activities");
    }
}
```
###### \java\seedu\address\model\activity\Location.java
``` java
/**
 * This class is to store location info in an event
 */
public class Location {
    public static final String MESSAGE_LOCATION_CONSTRAINTS =
            "Activity location should not be blank, or start with white space";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String LOCATION_VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs a {@code Name}.
     *
     * @param location A valid location.
     */
    public Location(String location) {
        requireNonNull(location);
        checkArgument(isValidLocation(location), MESSAGE_LOCATION_CONSTRAINTS);
        this.value = location;
    }

    /**
     * Returns true if a given string is a valid location.
     */
    public static boolean isValidLocation(String test) {
        return test.matches(LOCATION_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Location // instanceof handles nulls
                && this.value.equals(((Location) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### \java\seedu\address\model\activity\Remark.java
``` java
/**
 * Represents an Activity's remark in the desk board.
 * Guarantees: immutable; is valid as declared in {@link #isValidRemark(String)}
 */
public class Remark {

    public static final String MESSAGE_REMARK_CONSTRAINTS =
            "Activity remark can take any values, and it should not be blank.";

    public static final String REMARK_VALIDATION_REGEX = ".+";

    public final String value;

    /**
     * Constructs an {@code Remark}.
     *
     * @param remark A valid remark.
     */
    public Remark(String remark) {
        requireNonNull(remark);
        checkArgument(isValidRemark(remark), MESSAGE_REMARK_CONSTRAINTS);
        this.value = remark;
    }

    /**
     * Returns true if a given string is a valid activity remark.
     */
    public static boolean isValidRemark(String test) {
        return test.matches(REMARK_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Remark // instanceof handles nulls
                && this.value.equals(((Remark) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### \java\seedu\address\model\activity\Task.java
``` java
/**
 * Represents a Task in the desk board.
 * The field contains 3 field, name, due date and (Optional)remark.
 * The following example would illustrate one example
 * ******** Example ******************************* *
 * NAME : CS2103 Project
 * DUE DATE/TIME: 21/03/2018 23:59
 * REMARK: Submit through a pull request in git hub.
 * ************************************************ *
 */
public class Task extends Activity {

    private static final String ACTIVITY_TYPE = "TASK";

    /**
     * Every field must be present and not null.
     */
    public Task(Name name, DateTime dueDateTime, Remark remark, Set<Tag> tags) {
        super(name, dueDateTime, remark, tags);


    }

    public Task(Name name, DateTime dueDateTime, Remark remark, Set<Tag> tags, boolean isComplete) {
        super(name, dueDateTime, remark, tags, isComplete);


    }
    @Override
    public Name getName() {
        return super.getName();
    }

    public DateTime getDueDateTime() {
        return super.getDateTime();
    }

    @Override
    public Remark getRemark() {
        return super.getRemark();
    }

    @Override
    public String getActivityType() {
        return ACTIVITY_TYPE;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    @Override
    public Set<Tag> getTags() {
        return super.getTags();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Task)) {
            return false;
        }

        Task otherTask = (Task) other;
        return otherTask.getName().equals(this.getName())
                && otherTask.getDueDateTime().equals(this.getDueDateTime())
                && (getRemark() == null ? otherTask.getRemark() == null : getRemark().equals(otherTask.getRemark()))
                && this.isCompleted() == otherTask.isCompleted();
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Task Name: ")
                .append(getName())
                .append(" Due Date/Time: ")
                .append(getDateTime())
                .append(" Remark: ")
                .append(getRemark() == null ? "" : getRemark())
                .append(" Tags: ")
                .append(isCompleted() ? "Uncompleted" : "Completed");
        getTags().forEach(builder::append);
        return builder.toString();
    }

    @Override
    public Activity copy(Set<Tag> tags) {
        if (tags == null) {
            return new Task(getName(), getDueDateTime(), getRemark(), getTags(), isCompleted());
        }
        return new Task(getName(), getDueDateTime(), getRemark(), tags, isCompleted());
    }

    @Override
    public Activity getCompletedCopy() {
        return new Task(getName(), getDueDateTime(), getRemark(), getTags(), true);
    }
}
```
###### \java\seedu\address\model\activity\TaskOnlyPredicate.java
``` java
/**
 * This class gives a predicate that returns only the tasks in a list.
 */
public class TaskOnlyPredicate implements Predicate<Activity> {
    public TaskOnlyPredicate() {
        ;
    }

    @Override
    public boolean test(Activity activity) {
        return activity.getActivityType().equals("TASK");
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskOnlyPredicate); // instanceof handles nulls
    }

}
```
###### \java\seedu\address\model\DeskBoard.java
``` java
/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class DeskBoard implements ReadOnlyDeskBoard {

    private final UniqueActivityList activities;
    private final UniqueTagList tags;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        activities = new UniqueActivityList();
        tags = new UniqueTagList();
    }

    public DeskBoard() {}

    /**
     * Creates an DeskBoard using the Activities and Tags in the {@code toBeCopied}
     */
    public DeskBoard(ReadOnlyDeskBoard toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    public void setActivities(List<Activity> activities) throws DuplicateActivityException {
        this.activities.setActivity(activities);
    }

    public void setTags(Set<Tag> tags) {
        this.tags.setTags(tags);
    }

    /**
     * Resets the existing data of this {@code DeskBoard} with {@code newData}.
     */
    public void resetData(ReadOnlyDeskBoard newData) {
        requireNonNull(newData);
        setTags(new HashSet<>(newData.getTagList()));
        List<Activity> syncedActivityList = newData.getActivityList().stream()
                .map(this::syncWithMasterTagList)
                .collect(Collectors.toList());

        try {
            setActivities(syncedActivityList);
        } catch (DuplicateActivityException e) {
            throw new AssertionError("DeskBoard should not have duplicate activities");
        }
    }

    //// activity-level operations

    /**
     * Adds an activity to the desk board.
     * Also checks the new activity's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the activity to point to those in {@link #tags}.
     *
     * @throws DuplicateActivityException if an equivalent activity already exists.
     */
    public void addActivity(Activity p) throws DuplicateActivityException {
        Activity activity = syncWithMasterTagList(p);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any activity
        // in the activity list.
        activities.add(activity);
    }

    /**
     * Replaces the given activity {@code target} in the list with {@code editedActivity}.
     * {@code DeskBoard}'s tag list will be updated with the tags of {@code editedActivity}.
     *
     * @throws DuplicateActivityException if updating the activity's details causes the activity to be equivalent to
     *      another existing activity in the list.
     * @throws ActivityNotFoundException if {@code target} could not be found in the list.
     *
     * @see #syncWithMasterTagList(Activity)
     */
    public void updateActivity(Activity target, Activity editedActivity)
            throws DuplicateActivityException, ActivityNotFoundException {
        requireNonNull(editedActivity);

        Activity syncedEditedActivity = syncWithMasterTagList(editedActivity);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any activity
        // in the activity list.
        activities.setActivity(target, syncedEditedActivity);
    }

    /**
     *  Updates the master tag list to include tags in {@code activity} that are not in the list.
     *  @return a copy of this {@code activity} such that every tag in this activity points to
     *      a Tag object in the master list.
     */
    private Activity syncWithMasterTagList(Activity activity) {
        final UniqueTagList activityTags = new UniqueTagList(activity.getTags());
        tags.mergeFrom(activityTags);

        // Create map with values = tag object references in the master list
        // used for checking activity tag references
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        tags.forEach(tag -> masterTagObjects.put(tag, tag));

        // Rebuild the list of activity tags to point to the relevant tags in the master tag list.
        final Set<Tag> correctTagReferences = new HashSet<>();
        activityTags.forEach(tag -> correctTagReferences.add(masterTagObjects.get(tag)));
        return activity.copy(correctTagReferences);
    }

    /**
     * Removes {@code key} from this {@code DeskBoard}.
     * @throws ActivityNotFoundException if the {@code key} is not in this {@code DeskBoard}.
     */
    public boolean removeActivity(Activity key) throws ActivityNotFoundException {
        if (activities.remove(key)) {
            return true;
        } else {
            throw new ActivityNotFoundException();
        }
    }

    //// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

    //// util methods

    @Override
    public String toString() {
        return activities.internalListAsObservable().size() + " activities, "
                + tags.internalListAsObservable().size() +  " tags";
        // TODO: refine later
    }

    @Override
    public ObservableList<Activity> getActivityList() {
        return activities.internalListAsObservable();
    }


    @Override
    public ObservableList<Tag> getTagList() {
        return tags.internalListAsObservable();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeskBoard // instanceof handles nulls
                && this.activities.equals(((DeskBoard) other).activities)
                && this.tags.equalsOrderInsensitive(((DeskBoard) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(activities, tags);
    }
}
```
###### \java\seedu\address\model\Model.java
``` java
/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Activity> PREDICATE_SHOW_ALL_ACTIVITY = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyDeskBoard newData);

    /** Returns the DeskBoard */
    ReadOnlyDeskBoard getDeskBoard();

    /** Deletes the given activity. */
    void deleteActivity(Activity target) throws ActivityNotFoundException;

    /** Adds the given activity */
    void addActivity(Activity activity) throws DuplicateActivityException;

    /**
     * Replaces the given activity {@code target} with {@code editedActivity}.
     *
     * @throws DuplicateActivityException if updating the activity's details causes the activity to be equivalent to
     *      another existing activity in the list.
     * @throws ActivityNotFoundException if {@code target} could not be found in the list.
     */
    void updateActivity(Activity target, Activity editedActivity)
            throws DuplicateActivityException, ActivityNotFoundException;

    /** Returns an unmodifiable view of the filtered activity list */
    ObservableList<Activity> getFilteredActivityList();

```
###### \java\seedu\address\model\ModelManager.java
``` java
/**
 * Represents the in-memory model of the desk board data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final DeskBoard deskBoard;
    private final FilteredList<Activity> filteredActivities;
    /**
     * Initializes a ModelManager with the given deskBoard and userPrefs.
     */
    public ModelManager(ReadOnlyDeskBoard deskBoard, UserPrefs userPrefs) {
        super();
        requireAllNonNull(deskBoard, userPrefs);

        logger.fine("Initializing with desk board: " + deskBoard + " and user prefs " + userPrefs);

        this.deskBoard = new DeskBoard(deskBoard);
        filteredActivities = new FilteredList<>(this.deskBoard.getActivityList());
    }

    public ModelManager() {
        this(new DeskBoard(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyDeskBoard newData) {
        deskBoard.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public ReadOnlyDeskBoard getDeskBoard() {
        return deskBoard;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new DeskBoardChangedEvent(deskBoard));
    }

    @Override
    public synchronized void deleteActivity(Activity target) throws ActivityNotFoundException {
        deskBoard.removeActivity(target);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addActivity(Activity activity) throws DuplicateActivityException {
        deskBoard.addActivity(activity);
        updateFilteredActivityList(PREDICATE_SHOW_ALL_ACTIVITY);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void updateActivity(Activity target, Activity editedActivity)
            throws DuplicateActivityException, ActivityNotFoundException {
        requireAllNonNull(target, editedActivity);

        deskBoard.updateActivity(target, editedActivity);
        indicateAddressBookChanged();
    }

    //=========== Filtered Activity List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Activity} backed by the internal list of
     * {@code deskBoard}
     */
    @Override
    public ObservableList<Activity> getFilteredActivityList() {
        return FXCollections.unmodifiableObservableList(filteredActivities);
    }

```
###### \java\seedu\address\model\ReadOnlyDeskBoard.java
``` java
/**
 * Unmodifiable view of an desk board
 */
public interface ReadOnlyDeskBoard {

    /**
     * Returns an unmodifiable view of the activities list.
     * This list will not contain any duplicate activities..
     */
    ObservableList<Activity> getActivityList();

    /**
     * Returns an unmodifiable view of the tags list.
     * This list will not contain any duplicate tags.
     */
    ObservableList<Tag> getTagList();

}
```
