package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.DeskBoardChangedEvent;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.EventOnlyPredicate;
import seedu.address.model.activity.TaskOnlyPredicate;
import seedu.address.model.activity.exceptions.ActivityNotFoundException;
import seedu.address.model.activity.exceptions.DuplicateActivityException;

//@@author YuanQLLer
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
        indicateDeskBoardChanged();
    }

    @Override
    public ReadOnlyDeskBoard getDeskBoard() {
        return deskBoard;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateDeskBoardChanged() {
        raise(new DeskBoardChangedEvent(deskBoard));
    }

    @Override
    public synchronized void deleteActivity(Activity target) throws ActivityNotFoundException {
        deskBoard.removeActivity(target);
        indicateDeskBoardChanged();
    }

    //@@author Kyomian
    @Override
    public synchronized void clearActivities(String activityTypeToClear) {
        deskBoard.clearActivities(activityTypeToClear);
        indicateDeskBoardChanged();
    }

    //@@author
    @Override
    public synchronized void addActivity(Activity activity) throws DuplicateActivityException {
        deskBoard.addActivity(activity);
        updateFilteredActivityList(PREDICATE_SHOW_ALL_ACTIVITY);
        indicateDeskBoardChanged();
    }

    //@@author karenfrilya97
    /** Adds all activities in the given desk board, except for those already found in the existing desk board*/
    public synchronized void addActivities(ReadOnlyDeskBoard otherDeskBoard) {
        deskBoard.addActivities(otherDeskBoard.getActivityList());
        updateFilteredActivityList(PREDICATE_SHOW_ALL_ACTIVITY);
        indicateDeskBoardChanged();
    }

    //@@author YuanQLLer
    @Override
    public synchronized void updateActivity(Activity target, Activity editedActivity)
            throws DuplicateActivityException, ActivityNotFoundException {
        requireAllNonNull(target, editedActivity);

        deskBoard.updateActivity(target, editedActivity);
        indicateDeskBoardChanged();
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

    //@@author jasmoon
    /**
     * Returns an unmodifiable view of the list of {@code Task} backed by the task list of
     * {@code deskBoard}
     */
    @Override
    public ObservableList<Activity> getFilteredTaskList()   {
        FilteredList<Activity> taskList =  new FilteredList<>(filteredActivities, new TaskOnlyPredicate());
        ObservableList<Activity> result = FXCollections.unmodifiableObservableList(taskList);
        return result;
    }

    //@@author YuanQLLer
    /**
     * Returns an unmodifiable view of the list of {@code Event} backed by the event list of
     * {@code deskBoard}
     */
    @Override
    public ObservableList<Activity> getFilteredEventList() {
        FilteredList<Activity> eventList =  new FilteredList<>(filteredActivities, new EventOnlyPredicate());
        ObservableList<Activity> result = FXCollections.unmodifiableObservableList(eventList);
        return result;
    }

    @Override
    public void updateFilteredActivityList(Predicate<Activity> predicate) {
        requireNonNull(predicate);
        filteredActivities.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return deskBoard.equals(other.deskBoard)
                && filteredActivities.equals(other.filteredActivities);
    }

}
