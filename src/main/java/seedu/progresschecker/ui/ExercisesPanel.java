package seedu.progresschecker.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import seedu.progresschecker.commons.core.LogsCenter;
import seedu.progresschecker.commons.events.ui.PageLoadChangedEvent;

//@@author iNekox3
/**
 * The Exercises Panel of the App.
 */
public class ExercisesPanel extends UiPart<Region> {

    private static final String FXML = "ExercisesPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private final StringProperty displayed = new SimpleStringProperty("");

    @FXML
    private TextArea exercisesPanel;

    public ExercisesPanel() {
        super(FXML);
        exercisesPanel.textProperty().bind(displayed);
        registerAsAnEventHandler(this);
    }

    // TODO: Replace with loading all exercises from locally stored file
    /**
     * Display the set of exercises in the given week specified by {@code index}.
     */
    void displayExercises(int index) {
        final String header = "Week " + index + "'s Exercises\n\n";;
        final String message;

        switch (index) {
        case 2:
            message = "2.1.1 Compare Software Engineering with Civil Engineering in terms of how work products\n"
                    + "in CE (i.e. buildings) differ from those of SE (i.e. software).\n\n"
                    + "2.1.2 Comment on this statement: Building software is cheaper and easier than building\n"
                    + "bridges (all we need is a PC!).";
            break;
        case 3:
            message = "3.1.1 Explain what is refactoring and why it is not the same as rewriting, bug fixing,\n"
                    + "or adding features.";
            break;
        case 4:
            message = "4.5.1 Which are benefits of exceptions?\n"
                    + "a.Exceptions allow us to separate normal code from error handling code.\n"
                    + "b. Exceptions can prevent problems that happen in the environment.\n"
                    + "c. Exceptions allow us to handle in one location an error raised in another location.\n";
            break;
        case 5:
            message = "5.6.1 Which one of these is recommended not to use in UML diagrams because it adds more\n"
                    + "confusion than clarity?\na. Composition symbol\n b. Aggregation symbol";
            break;
        case 6:
        case 7:
        case 8:
        case 9:
        case 10:
        case 11:
        case 12:
        case 13:
            message = "Exercises not yet retrieved.";
            break;
        default:
            message = "Week not found.";
        }
        Platform.runLater(() -> displayed.setValue(header + message));
    }

    @Subscribe
    private void handlePageLoadChangedEvent(PageLoadChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        displayExercises(event.getPageIndex());
    }
}
