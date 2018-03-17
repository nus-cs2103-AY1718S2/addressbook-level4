package seedu.address.ui.testutil;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.ResultDisplayHandle;
import guitests.guihandles.StudentCardHandle;
import guitests.guihandles.StudentListPanelHandle;
import seedu.address.model.student.Student;

/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {
    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(StudentCardHandle expectedCard, StudentCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getAddress(), actualCard.getAddress());
        assertEquals(expectedCard.getEmail(), actualCard.getEmail());
        assertEquals(expectedCard.getName(), actualCard.getName());
        assertEquals(expectedCard.getPhone(), actualCard.getPhone());
        assertEquals(expectedCard.getTags(), actualCard.getTags());

        expectedCard.getTags().forEach(tag ->
            assertEquals(expectedCard.getTagsColour(tag), actualCard.getTagsColour(tag)));
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedStudent}.
     */
    public static void assertCardDisplaysStudent(Student expectedStudent, StudentCardHandle actualCard) {
        assertEquals(expectedStudent.getName().fullName, actualCard.getName());
        assertEquals(expectedStudent.getPhone().value, actualCard.getPhone());
        assertEquals(expectedStudent.getEmail().value, actualCard.getEmail());
        assertEquals(expectedStudent.getAddress().value, actualCard.getAddress());
        assertEquals(expectedStudent.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                actualCard.getTags());

        assertTagEquals(expectedStudent, actualCard);

    }

    /**
     * Checks if the tag colour matches the correct tag colour for {@code tagName}
     * @param expectedStudent
     * @param actualCard
     */
    private static void assertTagEquals(Student expectedStudent, StudentCardHandle actualCard) {
        List<String> expectedTags = expectedStudent.getTags().stream()
                .map(tag -> tag.tagName).collect(Collectors.toList());
        assertEquals(expectedTags, actualCard.getTags());
        expectedTags.forEach(tag ->
                assertEquals(Arrays.asList("label", getTagColourFor(tag)),
                        actualCard.getTagsColour(tag)));
    }

    private static String getTagColourFor(String tagName) {
        switch(tagName) {

        case "friends":
            return "cyan";

        case "colleagues":
        case "neighbours":
            return "blue";

        case "family":
        case "friend":
            return "yellow";

        case "classmates":
        case "owesMoney":
            return "teal";

        case "husband":
            return "olive";

        default :
            fail(tagName + " does not have a color assigned.");
            return "";
        }
    }

    /**
     * Asserts that the list in {@code studentListPanelHandle} displays the details of {@code students} correctly and
     * in the correct order.
     */
    public static void assertListMatching(StudentListPanelHandle studentListPanelHandle, Student... students) {
        for (int i = 0; i < students.length;
             i++) {
            assertCardDisplaysStudent(students[i], studentListPanelHandle.getStudentCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code studentListPanelHandle} displays the details of {@code students} correctly and
     * in the correct order.
     */
    public static void assertListMatching(StudentListPanelHandle studentListPanelHandle, List<Student> students) {
        assertListMatching(studentListPanelHandle, students.toArray(new Student[0]));
    }

    /**
     * Asserts the size of the list in {@code studentListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(StudentListPanelHandle studentListPanelHandle, int size) {
        int numberOfPeople = studentListPanelHandle.getListSize();
        assertEquals(size, numberOfPeople);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }
}
