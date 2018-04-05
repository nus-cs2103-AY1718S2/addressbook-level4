package seedu.progresschecker.model.photo;

import static java.util.Objects.requireNonNull;
import static seedu.progresschecker.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.progresschecker.commons.util.CollectionUtil;
import seedu.progresschecker.model.photo.exceptions.DuplicatePhotoException;
import seedu.progresschecker.model.photo.exceptions.PhotoNotFoundException;

//@@author Livian1107
/**
 * A list of photo paths that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see PhotoPath#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniquePhotoList implements Iterable<PhotoPath> {

    private final ObservableList<PhotoPath> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent photo path as the given argument.
     */
    public boolean contains(PhotoPath toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a photo path to the list.
     *
     * @throws DuplicatePhotoException if the photo path to add is a duplicate of an existing photo path in the list.
     */
    public void add(PhotoPath toAdd) throws DuplicatePhotoException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePhotoException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the photo path {@code target} in the list with {@code editedPhoto}.
     *
     * @throws DuplicatePhotoException if the replacement is equivalent to another existing photo path in the list.
     * @throws PhotoNotFoundException if {@code target} could not be found in the list.
     */
    public void setPhoto(PhotoPath target, PhotoPath editedPhoto)
            throws DuplicatePhotoException, PhotoNotFoundException {
        requireNonNull(editedPhoto);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PhotoNotFoundException();
        }

        if (!target.equals(editedPhoto) && internalList.contains(editedPhoto)) {
            throw new DuplicatePhotoException();
        }

        internalList.set(index, editedPhoto);
    }

    /**
     * Removes the equivalent photo path from the list.
     *
     * @throws PhotoNotFoundException if no such person could be found in the list.
     */
    public boolean remove(PhotoPath toRemove) throws PhotoNotFoundException {
        requireNonNull(toRemove);
        final boolean photoFoundAndDeleted = internalList.remove(toRemove);
        if (!photoFoundAndDeleted) {
            throw new PhotoNotFoundException();
        }
        return photoFoundAndDeleted;
    }

    public void setPhotos(UniquePhotoList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setPhotos(List<PhotoPath> photos) throws DuplicatePhotoException {
        requireAllNonNull(photos);
        final UniquePhotoList replacement = new UniquePhotoList();
        for (final PhotoPath photo : photos) {
            replacement.add(photo);
        }
        setPhotos(replacement);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<PhotoPath> asObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<PhotoPath> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniquePhotoList // instanceof handles nulls
                && this.internalList.equals(((UniquePhotoList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
