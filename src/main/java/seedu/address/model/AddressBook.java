package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.address.model.student.Student;
import seedu.address.model.student.UniqueStudentList;
import seedu.address.model.student.exceptions.DuplicateStudentException;
import seedu.address.model.student.exceptions.StudentNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueStudentList students;
    private final UniqueTagList tags;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        students = new UniqueStudentList();
        tags = new UniqueTagList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Students and Tags in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    public void setStudents(List<Student> students) throws DuplicateStudentException {
        this.students.setStudents(students);
    }

    public void setTags(Set<Tag> tags) {
        this.tags.setTags(tags);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);
        setTags(new HashSet<>(newData.getTagList()));
        List<Student> syncedStudentList = newData.getStudentList().stream()
                .map(this::syncWithMasterTagList)
                .collect(Collectors.toList());

        try {
            setStudents(syncedStudentList);
        } catch (DuplicateStudentException e) {
            throw new AssertionError("AddressBooks should not have duplicate students");
        }
    }

    //// student-level operations

    /**
     * Adds a student to the address book.
     * Also checks the new student's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the student to point to those in {@link #tags}.
     *
     * @throws DuplicateStudentException if an equivalent student already exists.
     */
    public void addStudent(Student p) throws DuplicateStudentException {
        Student student = syncWithMasterTagList(p);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any student
        // in the student list.
        students.add(student);
    }

    /**
     * Replaces the given student {@code target} in the list with {@code editedStudent}.
     * {@code AddressBook}'s tag list will be updated with the tags of {@code editedStudent}.
     *
     * @throws DuplicateStudentException if updating the student's details causes the student to be equivalent to
     *      another existing student in the list.
     * @throws StudentNotFoundException if {@code target} could not be found in the list.
     *
     * @see #syncWithMasterTagList(Student)
     */
    public void updateStudent(Student target, Student editedStudent)
            throws DuplicateStudentException, StudentNotFoundException {
        requireNonNull(editedStudent);

        Student syncedEditedStudent = syncWithMasterTagList(editedStudent);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any student
        // in the student list.
        students.setStudent(target, syncedEditedStudent);
        removeUnusedTags();
    }

    /**
     * Removes all {@code Tag}s that are not used by any {@code Student} in this {@code AddressBook}.
     */
    private void removeUnusedTags() {
        Set<Tag> tagsInStudents = students.asObservableList().stream()
                .map(Student::getTags)
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
        tags.setTags(tagsInStudents);
    }

    /**
     *  Updates the master tag list to include tags in {@code student} that are not in the list.
     *  @return a copy of this {@code student} such that every tag in this student points to a Tag object in the master
     *  list.
     */
    private Student syncWithMasterTagList(Student student) {
        final UniqueTagList studentTags = new UniqueTagList(student.getTags());
        tags.mergeFrom(studentTags);

        // Create map with values = tag object references in the master list
        // used for checking student tag references
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        tags.forEach(tag -> masterTagObjects.put(tag, tag));

        // Rebuild the list of student tags to point to the relevant tags in the master tag list.
        final Set<Tag> correctTagReferences = new HashSet<>();
        studentTags.forEach(tag -> correctTagReferences.add(masterTagObjects.get(tag)));
        return new Student(
                student.getName(), student.getPhone(), student.getEmail(), student.getAddress(),
                student.getProgrammingLanguage(), correctTagReferences, student.getFavourite(), student.getDashboard());
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * @throws StudentNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */
    public boolean removeStudent(Student key) throws StudentNotFoundException {
        if (students.remove(key)) {
            return true;
        } else {
            throw new StudentNotFoundException();
        }
    }

    /**
     * Checks for the existence of {@code key} in this {@code AddressBook}.
     * @throws StudentNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */
    public boolean checkForStudentInAdressBook(Student key) throws StudentNotFoundException {
        if (students.contains(key)) {
            return true;
        } else {
            throw new StudentNotFoundException();
        }
    }

    //// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

    /**
     * Removes {@code tag} from {@code student} in this {@code AddressBook}.
     * @throws StudentNotFoundException if the {@code student} is not in this {@code AddressBook}.
     */
    private void removeTagFromStudent(Tag tag, Student student) throws StudentNotFoundException {
        Set<Tag> newTags = new HashSet<>(student.getTags());

        if (!newTags.remove(tag)) {
            return;
        }

        Student newStudent =
                new Student(student.getName(), student.getPhone(), student.getEmail(), student.getAddress(),
                        student.getProgrammingLanguage(), newTags);

        try {
            updateStudent(student, newStudent);
        } catch (DuplicateStudentException dpe) {
            throw new AssertionError("Modifying a student's tags only should not result in a duplicate. "
                    + "See Student#equals(Object).");
        }
    }

    /**
     * Removes {@code tag} from all students in this {@code AddressBook}.
     */
    public void removeTag(Tag tag) {
        try {
            for (Student student : students) {
                removeTagFromStudent(tag, student);
            }
        } catch (StudentNotFoundException pnfe) {
            throw new AssertionError("Impossible: original student is obtained from the address book.");
        }
    }

    //// util methods

    @Override
    public String toString() {
        return students.asObservableList().size() + " students, " + tags.asObservableList().size() +  " tags";
        // TODO: refine later
    }

    @Override
    public ObservableList<Student> getStudentList() {
        return students.asObservableList();
    }

    @Override
    public ObservableList<Tag> getTagList() {
        return tags.asObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && this.students.equals(((AddressBook) other).students)
                && this.tags.equalsOrderInsensitive(((AddressBook) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(students, tags);
    }
}
