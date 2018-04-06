package seedu.address.model.photo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.DuplicateDataException;
import seedu.address.commons.util.CollectionUtil;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

public class UniquePhotoList implements Iterable<Photo> {

    private final ObservableList<Photo> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty PhotoList.
     */
    public UniquePhotoList() {}

    /**
     * Creates a UniquePhotoList using given photos.
     * Enforces no nulls.
     */
    public UniquePhotoList(Set<Photo> photos) {
        requireAllNonNull(photos);
        internalList.addAll(photos);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns all photos in this list as a Set.
     * This set is mutable and change-insulated against the internal list.
     */
    public Set<Photo> toSet() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return new HashSet<>(internalList);
    }

    /**
     * Replaces the Photos in this list with those in the argument photo list.
     */
    public void setPhotos(Set<Photo> photos) {
        requireAllNonNull(photos);
        internalList.setAll(photos);
        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Ensures every photo in the argument list exists in this object.
     */
    public void mergeFrom(UniquePhotoList from) {
        final Set<Photo> alreadyInside = this.toSet();
        from.internalList.stream()
                .filter(photo -> !alreadyInside.contains(photo))
                .forEach(internalList::add);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns true if the list contains an equivalent Photo as the given argument.
     */
    public boolean contains(Photo toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a Photo to the list.
     *
     * @throws UniquePhotoList.DuplicatePhotoException if the Tag to add is a duplicate of an existing Tag in the list.
     */
    public void add(Photo toAdd) throws UniquePhotoList.DuplicatePhotoException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new UniquePhotoList.DuplicatePhotoException();
        }
        internalList.add(toAdd);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    @Override
    public Iterator<Photo> iterator() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.iterator();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Photo> asObservableList() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public boolean equals(Object other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        return other == this // short circuit if same object
                || (other instanceof UniquePhotoList // instanceof handles nulls
                && this.internalList.equals(((UniquePhotoList) other).internalList));
    }

    /**
     * Returns true if the element in this list is equal to the elements in {@code other}.
     * The elements do not have to be in the same order.
     */
    public boolean equalsOrderInsensitive(UniquePhotoList other) {
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
    public static class DuplicatePhotoException extends DuplicateDataException {
        protected DuplicatePhotoException() {
            super("Operation would result in duplicate photos");
        }
    }

}
