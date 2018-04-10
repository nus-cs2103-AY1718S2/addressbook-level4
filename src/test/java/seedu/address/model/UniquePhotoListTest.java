package seedu.address.model;
//@@author crizyli
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.photo.Photo;
import seedu.address.model.photo.UniquePhotoList;
import seedu.address.testutil.Assert;

public class UniquePhotoListTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Set<Photo> photos;

    private Photo photo1 = new Photo("test1.jpg");
    private Photo photo2 = new Photo("test2.jpg");

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniquePhotoList uniquePhotoList = new UniquePhotoList();
        thrown.expect(UnsupportedOperationException.class);
        uniquePhotoList.asObservableList().remove(0);
    }

    @Before
    public void setUp() {
        photos = new HashSet<Photo>();
        photos.add(photo1);
        photos.add(photo2);
    }

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new UniquePhotoList(null));
    }

    @Test
    public void construct_success() {
        UniquePhotoList uniquePhotoList = new UniquePhotoList(photos);
        assertEquals(photos, uniquePhotoList.toSet());
    }

    @Test
    public void setAndAddPhoto_success() throws UniquePhotoList.DuplicatePhotoException {
        UniquePhotoList uniquePhotoList = new UniquePhotoList();
        uniquePhotoList.setPhotos(photos);
        assertEquals(photos, uniquePhotoList.toSet());
        Photo photo3 = new Photo("test3.jpg");
        uniquePhotoList.add(photo3);
        Set<Photo> newPhotos = new HashSet<Photo>();
        newPhotos.add(photo3);
        newPhotos.add(photo2);
        newPhotos.add(photo1);
        UniquePhotoList uniquePhotoList1 = new UniquePhotoList(newPhotos);
        assertTrue(uniquePhotoList.equalsOrderInsensitive(uniquePhotoList1));
    }

    @Test
    public void addDuplicate_throwsDuplicatePhotoException() {
        UniquePhotoList uniquePhotoList = new UniquePhotoList();
        uniquePhotoList.setPhotos(photos);
        Assert.assertThrows(UniquePhotoList.DuplicatePhotoException.class, () -> uniquePhotoList.add(photo1));
    }

    @Test
    public void equals() {
        UniquePhotoList uniquePhotoList = new UniquePhotoList();
        uniquePhotoList.setPhotos(photos);

        Set<Photo> newPhotos = new HashSet<Photo>();
        newPhotos.add(photo2);
        newPhotos.add(photo1);
        UniquePhotoList uniquePhotoList1 = new UniquePhotoList(newPhotos);

        // same object -> returns true
        assertTrue(uniquePhotoList.equals(uniquePhotoList));

        // different types -> returns false
        assertFalse(uniquePhotoList.equals(1));

        // null -> returns false
        assertFalse(uniquePhotoList.equals(null));

        // same value -> returns true
        assertTrue(uniquePhotoList.equals(uniquePhotoList1));

        assertEquals(uniquePhotoList.asObservableList().hashCode(), uniquePhotoList.hashCode());
    }
}
