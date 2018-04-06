package seedu.progresschecker.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.progresschecker.model.Model.PREDICATE_SHOW_ALL_EXERCISES;

import java.util.List;
import java.util.Objects;

import seedu.progresschecker.commons.core.Messages;
import seedu.progresschecker.logic.commands.exceptions.CommandException;
import seedu.progresschecker.model.exercise.Exercise;
import seedu.progresschecker.model.exercise.ModelAnswer;
import seedu.progresschecker.model.exercise.Question;
import seedu.progresschecker.model.exercise.QuestionIndex;
import seedu.progresschecker.model.exercise.QuestionType;
import seedu.progresschecker.model.exercise.StudentAnswer;
import seedu.progresschecker.model.exercise.exceptions.ExerciseNotFoundException;

//@@author iNekox3
/**
 * Edits details of student answer of an exercise in the ProgressChecker.
 */
public class AnswerCommand extends UndoableCommand {
    public static final int MIN_WEEK_NUMBER = 2;
    public static final int MAX_WEEK_NUMBER = 13;

    public static final String COMMAND_WORD = "answer";
    public static final String COMMAND_ALIAS = "ans";
    public static final String COMMAND_FORMAT = COMMAND_WORD + " QUESTION-INDEX" + " ANSWER";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Answer an exercise "
            + "identified by the index number shown. "
            + "Existing answer will be overwritten by the input value.\n"
            + "Parameters: INDEX (must be in the format of WEEK.SECTION.QUESTION number "
            + "where WEEK range from " + MIN_WEEK_NUMBER + " to " + MAX_WEEK_NUMBER + ") "
            + "ANSWER\n"
            + "Example: " + COMMAND_WORD + " 2.1.1 "
            + "Procedural languages work at simple data structures and functions level.";

    public static final String MESSAGE_EDIT_EXERCISE_SUCCESS = "Answered Exercise: %1$s";

    private final QuestionIndex questionIndex;
    private final StudentAnswer studentAnswer;

    private Exercise exerciseToEdit;
    private Exercise editedExercise;

    /**
     * @param questionIndex of the question in the filtered exercise list to edit
     * @param studentAnswer answer to edit the exercise with
     */
    public AnswerCommand(QuestionIndex questionIndex, StudentAnswer studentAnswer) {
        requireNonNull(questionIndex);

        this.questionIndex = questionIndex;
        this.studentAnswer = studentAnswer;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.updateExercise(exerciseToEdit, editedExercise);
        } catch (ExerciseNotFoundException enfe) {
            throw new AssertionError("The target exercise cannot be missing");
        }
        model.updateFilteredExerciseList(PREDICATE_SHOW_ALL_EXERCISES);
        return new CommandResult(String.format(MESSAGE_EDIT_EXERCISE_SUCCESS, questionIndex));
    }

    //TODO: store mapping of questionIndex to exercise's index in exerciseList in a separate data structure
    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Exercise> exerciseList = model.getFilteredExerciseList();
        boolean isFound = false;

        if (!questionIndex.isValidIndex(questionIndex.toString())) {
            throw new CommandException(String.format(
                    Messages.MESSAGE_INVALID_EXERCISE_INDEX, questionIndex.toString()));
        }

        for (Exercise e : exerciseList) {
            if (e.getQuestionIndex().toString().equals(questionIndex.toString())) {
                exerciseToEdit = exerciseList.get(exerciseList.indexOf(e));
                editedExercise = createEditedExercise(exerciseToEdit, studentAnswer);
                isFound = true;
                break;
            }
        }

        if (!isFound) {
            throw new CommandException(String.format(
                    Messages.MESSAGE_INVALID_EXERCISE_INDEX, questionIndex.toString()));
        }
    }

    /**
     * Creates and returns a {@code Exercise} with the details of {@code exerciseToEdit}
     * edited with {@code editExerciseDescriptor}.
     */
    private static Exercise createEditedExercise(Exercise exerciseToEdit, StudentAnswer studentAnswer) {
        assert exerciseToEdit != null;

        QuestionIndex questionIndex = exerciseToEdit.getQuestionIndex();
        QuestionType questionType = exerciseToEdit.getQuestionType();
        Question question = exerciseToEdit.getQuestion();
        StudentAnswer updatedStudentAnswer = studentAnswer;
        ModelAnswer modelAnswer = exerciseToEdit.getModelAnswer();

        return new Exercise(questionIndex, questionType, question,
                updatedStudentAnswer, modelAnswer);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AnswerCommand)) {
            return false;
        }

        // state check
        AnswerCommand e = (AnswerCommand) other;
        return questionIndex.equals(e.questionIndex)
                && studentAnswer.equals(e.studentAnswer)
                && Objects.equals(exerciseToEdit, e.exerciseToEdit);
    }

}
