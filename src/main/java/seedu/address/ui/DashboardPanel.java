package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;

public class DashboardPanel extends UiPart<Region> {

    private static final String FXML = "DashboardPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private Label studentName;

    public DashboardPanel() {
        super(FXML);
        studentName.setText("John doe");
    }
}
