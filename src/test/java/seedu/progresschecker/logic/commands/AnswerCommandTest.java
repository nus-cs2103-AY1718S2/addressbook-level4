package seedu.progresschecker.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.progresschecker.model.exercise.Exercise;
import seedu.progresschecker.model.exercise.StudentAnswer;
import seedu.progresschecker.testutil.ExerciseBuilder;

//@@author iNekox3
public class AnswerCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullExercise_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AnswerCommand(null, null);
    }

    @Test
    public void equals() {
        Exercise exercise1 = new ExerciseBuilder().withStudentAnswer("a b c").build();
        Exercise exercise2 = new ExerciseBuilder().withStudentAnswer("d e f").build();
        AnswerCommand answerExercise1Command = new AnswerCommand(exercise1.getQuestionIndex(),
                new StudentAnswer("a b c"));
        AnswerCommand answerExercise2Command = new AnswerCommand(exercise2.getQuestionIndex(),
                new StudentAnswer("d e f"));

        // same object -> returns true
        assertTrue(answerExercise1Command.equals(answerExercise1Command));

        // different types -> returns false
        assertFalse(answerExercise1Command.equals(1));

        // null -> returns false
        assertFalse(answerExercise1Command.equals(null));

        // different person -> returns false
        assertFalse(answerExercise1Command.equals(answerExercise2Command));
    }
}
