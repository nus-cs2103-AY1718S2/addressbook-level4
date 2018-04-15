package seedu.address.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.lesson.LessonList;

//@@author demitycho
public class LessonListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        LessonList uniqueStudentList = new LessonList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueStudentList.asObservableList().remove(0);
    }
}
