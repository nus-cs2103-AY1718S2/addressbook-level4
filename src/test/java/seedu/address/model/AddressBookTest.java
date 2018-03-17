package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_UNUSED;
import static seedu.address.testutil.TypicalStudents.ALICE;
import static seedu.address.testutil.TypicalStudents.AMY;
import static seedu.address.testutil.TypicalStudents.BOB;
import static seedu.address.testutil.TypicalStudents.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.student.Student;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.StudentBuilder;

public class AddressBookTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AddressBook addressBook = new AddressBook();
    private final AddressBook addressBookWithBobAndAmy = new AddressBookBuilder().withStudent(BOB)
            .withStudent(AMY).build();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getStudentList());
        assertEquals(Collections.emptyList(), addressBook.getTagList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        addressBook.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicateStudents_throwsAssertionError() {
        // Repeat ALICE twice
        List<Student> newStudents = Arrays.asList(ALICE, ALICE);
        List<Tag> newTags = new ArrayList<>(ALICE.getTags());
        AddressBookStub newData = new AddressBookStub(newStudents, newTags);

        thrown.expect(AssertionError.class);
        addressBook.resetData(newData);
    }

    @Test
    public void getStudentList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getStudentList().remove(0);
    }

    @Test
    public void getTagList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getTagList().remove(0);
    }

    @Test
    public void updateStudent_detailsChanged_studentsAndTagsListUpdated() throws Exception {
        AddressBook addressBookUpdatedToAmy = new AddressBookBuilder().withStudent(BOB).build();
        addressBookUpdatedToAmy.updateStudent(BOB, AMY);

        AddressBook expectedAddressBook = new AddressBookBuilder().withStudent(AMY).build();

        assertEquals(expectedAddressBook, addressBookUpdatedToAmy);
    }

    @Test
    public void removeTag_nonExistentTag_addressBookUnchanged() throws Exception {
        addressBookWithBobAndAmy.removeTag(new Tag(VALID_TAG_UNUSED));

        AddressBook expectedAddressBook = new AddressBookBuilder().withStudent(BOB).withStudent(AMY).build();

        assertEquals(expectedAddressBook, addressBookWithBobAndAmy);
    }

    @Test
    public void removeTag_tagUsedByMultipleStudents_tagRemoved() throws Exception {
        addressBookWithBobAndAmy.removeTag(new Tag(VALID_TAG_FRIEND));

        Student amyWithoutFriendTag = new StudentBuilder(AMY).withTags().build();
        Student bobWithoutFriendTag = new StudentBuilder(BOB).withTags(VALID_TAG_HUSBAND).build();
        AddressBook expectedAddressBook = new AddressBookBuilder().withStudent(bobWithoutFriendTag)
                .withStudent(amyWithoutFriendTag).build();

        assertEquals(expectedAddressBook, addressBookWithBobAndAmy);
    }

    /**
     * A stub ReadOnlyAddressBook whose students and tags lists can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Student> students = FXCollections.observableArrayList();
        private final ObservableList<Tag> tags = FXCollections.observableArrayList();

        AddressBookStub(Collection<Student> students, Collection<? extends Tag> tags) {
            this.students.setAll(students);
            this.tags.setAll(tags);
        }

        @Override
        public ObservableList<Student> getStudentList() {
            return students;
        }

        @Override
        public ObservableList<Tag> getTagList() {
            return tags;
        }
    }

}
