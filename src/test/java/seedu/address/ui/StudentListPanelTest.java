package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_STUDENT;
import static seedu.address.testutil.TypicalStudents.getTypicalStudents;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysStudent;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardEquals;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.StudentCardHandle;
import guitests.guihandles.StudentListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.model.student.Student;

public class StudentListPanelTest extends GuiUnitTest {
    private static final ObservableList<Student> TYPICAL_STUDENTS =
            FXCollections.observableList(getTypicalStudents());

    private static final JumpToListRequestEvent JUMP_TO_SECOND_EVENT = new JumpToListRequestEvent(INDEX_SECOND_STUDENT);

    private StudentListPanelHandle studentListPanelHandle;

    @Before
    public void setUp() {
        StudentListPanel studentListPanel = new StudentListPanel(TYPICAL_STUDENTS);
        uiPartRule.setUiPart(studentListPanel);

        studentListPanelHandle = new StudentListPanelHandle(getChildNode(studentListPanel.getRoot(),
                StudentListPanelHandle.STUDENT_LIST_VIEW_ID));
    }

    @Test
    public void display() {
        for (int i = 0; i < TYPICAL_STUDENTS.size(); i++) {
            studentListPanelHandle.navigateToCard(TYPICAL_STUDENTS.get(i));
            Student expectedStudent = TYPICAL_STUDENTS.get(i);
            StudentCardHandle actualCard = studentListPanelHandle.getStudentCardHandle(i);

            assertCardDisplaysStudent(expectedStudent, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void handleJumpToListRequestEvent() {
        postNow(JUMP_TO_SECOND_EVENT);
        guiRobot.pauseForHuman();

        StudentCardHandle expectedCard = studentListPanelHandle.getStudentCardHandle(INDEX_SECOND_STUDENT
                .getZeroBased());
        StudentCardHandle selectedCard = studentListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedCard, selectedCard);
    }
}
