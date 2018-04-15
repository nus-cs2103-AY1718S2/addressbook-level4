package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_SIXTH;
import static seedu.address.testutil.TypicalLessons.ALICE_WED_15_17;
import static seedu.address.testutil.TypicalLessons.FIONA_SAT_15_17;
import static seedu.address.testutil.TypicalLessons.getTypicalSchedule;
import static seedu.address.testutil.TypicalStudents.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.student.Student;

//@@author demitycho
/**
 * Contains integration tests (interaction with the Model) for {@code AddLessonCommand}.
 */
public class AddLessonCommandIntegrationTest {

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(
                getTypicalAddressBook(), new UserPrefs(), getTypicalSchedule());
    }

    @Test
    public void execute_newStudent_success() throws Exception {
        Lesson lesson = FIONA_SAT_15_17;

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), model.getSchedule());
        Student student = expectedModel.getAddressBook().getStudentList().get(INDEX_SIXTH.getZeroBased());
        expectedModel.addLesson(student, lesson.getDay(), lesson.getStartTime(), lesson.getEndTime());

        assertCommandSuccess(prepareCommand(INDEX_SIXTH, lesson), model,
                String.format(AddLessonCommand.MESSAGE_SUCCESS, student.getName()), expectedModel);
    }

    @Test
    public void execute_clashingLesson_throwsCommandException() {
        assertCommandFailure(prepareCommand(Index.fromOneBased(1),
                ALICE_WED_15_17), model, AddLessonCommand.MESSAGE_INVALID_TIME_SLOT);
    }

    /**
     * Generates a new {@code AddCommand} which upon execution, adds {@code student} into the {@code model}.
     */
    private AddLessonCommand prepareCommand(Index index, Lesson lesson) {
        AddLessonCommand command = new AddLessonCommand(
                index, lesson.getDay(), lesson.getStartTime(), lesson.getEndTime());
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
