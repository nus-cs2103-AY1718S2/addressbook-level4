package seedu.progresschecker.ui;

import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.progresschecker.commons.core.LogsCenter;
import seedu.progresschecker.model.exercise.Exercise;

//@@author iNekox3
/**
 * Panel containing the list of exercises.
 */
public class ExerciseListPanel extends UiPart<Region> {
    private static final String FXML = "ExerciseListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ExerciseListPanel.class);

    @FXML
    private ListView<ExerciseCard> exerciseListView;

    public ExerciseListPanel(ObservableList<Exercise> exerciseList) {
        super(FXML);
        setConnections(exerciseList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Exercise> exerciseList) {
        ObservableList<ExerciseCard> mappedList = EasyBind.map(
                exerciseList, (exercise) -> new ExerciseCard(exercise));
        exerciseListView.setItems(mappedList);
        exerciseListView.setCellFactory(listView -> new ExerciseListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code ExerciseCard}.
     */
    class ExerciseListViewCell extends ListCell<ExerciseCard> {

        @Override
        protected void updateItem(ExerciseCard exercise, boolean empty) {
            super.updateItem(exercise, empty);

            if (empty || exercise == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(exercise.getRoot());
            }
        }
    }

}
