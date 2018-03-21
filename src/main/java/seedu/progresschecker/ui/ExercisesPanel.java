package seedu.progresschecker.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import seedu.progresschecker.commons.core.LogsCenter;
import seedu.progresschecker.commons.events.ui.PageLoadChangedEvent;

/**
 * The Exercises Panel of the App.
 */
public class ExercisesPanel extends UiPart<Region> {

    private static final String FXML = "ExercisesPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private final StringProperty displayed = new SimpleStringProperty("{ content for exercises to be inserted }");

    @FXML
    private TextArea exercisesPanel;

    public ExercisesPanel() {
        super(FXML);
        exercisesPanel.textProperty().bind(displayed);
        registerAsAnEventHandler(this);
    }

    @Subscribe
    private void handlePageLoadChangedEvent(PageLoadChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
    }
}
