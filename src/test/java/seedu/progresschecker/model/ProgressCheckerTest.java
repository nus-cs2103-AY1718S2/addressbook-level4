package seedu.progresschecker.model;

import static org.junit.Assert.assertEquals;
import static seedu.progresschecker.testutil.TypicalPersons.ALICE;
import static seedu.progresschecker.testutil.TypicalPersons.getTypicalProgressChecker;

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
import seedu.progresschecker.model.person.Person;
import seedu.progresschecker.model.tag.Tag;

public class ProgressCheckerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final ProgressChecker progressChecker = new ProgressChecker();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), progressChecker.getPersonList());
        assertEquals(Collections.emptyList(), progressChecker.getTagList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        progressChecker.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyProgressChecker_replacesData() {
        ProgressChecker newData = getTypicalProgressChecker();
        progressChecker.resetData(newData);
        assertEquals(newData, progressChecker);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsAssertionError() {
        // Repeat ALICE twice
        List<Person> newPersons = Arrays.asList(ALICE, ALICE);
        List<Tag> newTags = new ArrayList<>(ALICE.getTags());
        ProgressCheckerStub newData = new ProgressCheckerStub(newPersons, newTags);

        thrown.expect(AssertionError.class);
        progressChecker.resetData(newData);
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        progressChecker.getPersonList().remove(0);
    }

    @Test
    public void getTagList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        progressChecker.getTagList().remove(0);
    }

    /**
     * A stub ReadOnlyProgressChecker whose persons and tags lists can violate interface constraints.
     */
    private static class ProgressCheckerStub implements ReadOnlyProgressChecker {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();
        private final ObservableList<Tag> tags = FXCollections.observableArrayList();

        ProgressCheckerStub(Collection<Person> persons, Collection<? extends Tag> tags) {
            this.persons.setAll(persons);
            this.tags.setAll(tags);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }

        @Override
        public ObservableList<Tag> getTagList() {
            return tags;
        }
    }

}
