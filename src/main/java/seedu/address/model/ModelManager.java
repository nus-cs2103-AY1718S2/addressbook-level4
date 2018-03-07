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
import seedu.address.commons.events.model.OrganizerChangedEvent;
import seedu.address.model.task.Task;
import seedu.address.model.task.exceptions.DuplicateTaskException;
import seedu.address.model.task.exceptions.TaskNotFoundException;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final Organizer organizer;
    private final FilteredList<Task> filteredTasks;

    /**
     * Initializes a ModelManager with the given organizer and userPrefs.
     */
    public ModelManager(ReadOnlyOrganizer addressBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.organizer = new Organizer(addressBook);
        filteredTasks = new FilteredList<>(this.organizer.getPersonList());
    }

    public ModelManager() {
        this(new Organizer(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyOrganizer newData) {
        organizer.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public ReadOnlyOrganizer getOrganizer() {
        return organizer;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new OrganizerChangedEvent(organizer));
    }

    @Override
    public synchronized void deletePerson(Task target) throws TaskNotFoundException {
        organizer.removePerson(target);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addPerson(Task task) throws DuplicateTaskException {
        organizer.addPerson(task);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }

    @Override
    public void updatePerson(Task target, Task editedTask)
            throws DuplicateTaskException, TaskNotFoundException {
        requireAllNonNull(target, editedTask);

        organizer.updatePerson(target, editedTask);
        indicateAddressBookChanged();
    }

    //=========== Filtered Task List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Task} backed by the internal list of
     * {@code organizer}
     */
    @Override
    public ObservableList<Task> getFilteredPersonList() {
        return FXCollections.unmodifiableObservableList(filteredTasks);
    }

    @Override
    public void updateFilteredPersonList(Predicate<Task> predicate) {
        requireNonNull(predicate);
        filteredTasks.setPredicate(predicate);
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
        return organizer.equals(other.organizer)
                && filteredTasks.equals(other.filteredTasks);
    }

}
