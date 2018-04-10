package seedu.address.model.skill;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.DuplicateDataException;
import seedu.address.commons.util.CollectionUtil;

/**
 * A list of skills that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
 *
 * @see Skill#equals(Object)
 */
public class UniqueSkillList implements Iterable<Skill> {

    private final ObservableList<Skill> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty SkillList.
     */
    public UniqueSkillList() {}

    /**
     * Creates a UniqueSkillList using given skills.
     * Enforces no nulls.
     */
    public UniqueSkillList(Set<Skill> skills) {
        requireAllNonNull(skills);
        internalList.addAll(skills);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns all skills in this list as a Set.
     * This set is mutable and change-insulated against the internal list.
     */
    public Set<Skill> toSet() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return new HashSet<>(internalList);
    }

    /**
     * Replaces the Skills in this list with those in the argument skill list.
     */
    public void setSkills(Set<Skill> skills) {
        requireAllNonNull(skills);
        internalList.setAll(skills);
        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Ensures every skill in the argument list exists in this object.
     */
    public void mergeFrom(UniqueSkillList from) {
        final Set<Skill> alreadyInside = this.toSet();
        from.internalList.stream()
                .filter(skill -> !alreadyInside.contains(skill))
                .forEach(internalList::add);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns true if the list contains an equivalent Skill as the given argument.
     */
    public boolean contains(Skill toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a Skill to the list.
     *
     * @throws DuplicateSkillException if the Skill to add is a duplicate of an existing Skill in the list.
     */
    public void add(Skill toAdd) throws DuplicateSkillException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateSkillException();
        }
        internalList.add(toAdd);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Removes a Skill from the list
     */
    public void remove(Skill toRemove) {
        requireAllNonNull(toRemove);
        if (contains(toRemove)) {
            internalList.remove(toRemove);
        }
    }

    @Override
    public Iterator<Skill> iterator() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.iterator();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Skill> asObservableList() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public boolean equals(Object other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        return other == this // short circuit if same object
                || (other instanceof UniqueSkillList // instanceof handles nulls
                        && this.internalList.equals(((UniqueSkillList) other).internalList));
    }

    /**
     * Returns true if the element in this list is equal to the elements in {@code other}.
     * The elements do not have to be in the same order.
     */
    public boolean equalsOrderInsensitive(UniqueSkillList other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        assert CollectionUtil.elementsAreUnique(other.internalList);
        return this == other || new HashSet<>(this.internalList).equals(new HashSet<>(other.internalList));
    }

    @Override
    public int hashCode() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.hashCode();
    }

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateSkillException extends DuplicateDataException {
        protected DuplicateSkillException() {
            super("Operation would result in duplicate skills");
        }
    }

}
