package seedu.address.model.todo;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.todo.exceptions.DuplicateToDoException;
import seedu.address.model.todo.exceptions.ToDoNotFoundException;

/**
 * A list of to-dos that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see ToDo#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueToDoList implements Iterable<ToDo> {

    private final ObservableList<ToDo> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent to-do as the given argument.
     */
    public boolean contains(ToDo toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a to-do to the list.
     *
     * @throws DuplicateToDoException if the to-do to add is a duplicate of an existing to-do in the list.
     */
    public void add(ToDo toAdd) throws DuplicateToDoException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateToDoException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the to-do {@code target} in the list with {@code editedToDo}.
     *
     * @throws DuplicateToDoException if the replacement is equivalent to another existing to-do in the list.
     * @throws ToDoNotFoundException if {@code target} could not be found in the list.
     */
    public void setToDo(ToDo target, ToDo editedToDo)
            throws DuplicateToDoException, ToDoNotFoundException {
        requireNonNull(editedToDo);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ToDoNotFoundException();
        }

        if (!target.equals(editedToDo) && internalList.contains(editedToDo)) {
            throw new DuplicateToDoException();
        }

        internalList.set(index, editedToDo);
    }

    /**
     * Removes the equivalent to-do from the list.
     *
     * @throws ToDoNotFoundException if no such to-do could be found in the list.
     */
    public boolean remove(ToDo toRemove) throws ToDoNotFoundException {
        requireNonNull(toRemove);
        final boolean toDoFoundAndDeleted = internalList.remove(toRemove);
        if (!toDoFoundAndDeleted) {
            throw new ToDoNotFoundException();
        }
        return toDoFoundAndDeleted;
    }

    public void setToDos(UniqueToDoList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setToDos(List<ToDo> todos) throws DuplicateToDoException {
        requireAllNonNull(todos);
        final UniqueToDoList replacement = new UniqueToDoList();
        for (final ToDo todo : todos) {
            replacement.add(todo);
        }
        setToDos(replacement);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<ToDo> asObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<ToDo> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueToDoList // instanceof handles nulls
                && this.internalList.equals(((UniqueToDoList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
