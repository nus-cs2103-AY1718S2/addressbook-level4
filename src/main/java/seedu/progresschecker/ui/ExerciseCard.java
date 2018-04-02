package seedu.progresschecker.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.progresschecker.model.exercise.Exercise;

//@@author iNekox3
/**
 * An UI component that displays information of an {@code Exercise}.
 */
public class ExerciseCard extends UiPart<Region> {

    private static final String FXML = "ExerciseListCard.fxml";

    public final Exercise exercise;

    @FXML
    private Label questionIndex;
    @FXML
    private Label questionType;
    @FXML
    private Label question;
    @FXML
    private Label studentAnswer;
    @FXML
    private Label modelAnswer;

    public ExerciseCard(Exercise exercise) {
        super(FXML);
        this.exercise = exercise;
        questionIndex.setText(exercise.getQuestionIndex().value);
        question.setText(exercise.getQuestion().value);
        studentAnswer.setText(exercise.getStudentAnswer().value);
        modelAnswer.setText(exercise.getModelAnswer().value);
    }
}
