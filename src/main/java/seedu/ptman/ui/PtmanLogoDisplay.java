package seedu.ptman.ui;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;

//@@author hzxcaryn
/**
 * Displays the PTMan logo at the left side of the command box.
 */
public class PtmanLogoDisplay extends UiPart<Region> {

    private static final String imagePath = "/icons/ptman_logo_icon.png";
    private static final String FXML = "PtmanLogoDisplay.fxml";

    @FXML
    private ImageView ptmanLogoView;

    public PtmanLogoDisplay() {
        super(FXML);

        Image ptmanLogo = new Image(imagePath);
        ptmanLogoView.setImage(ptmanLogo);
        setLogoSize();
    }

    /**
     * Scale the logo image to the desired size
     */
    private void setLogoSize() {
        ptmanLogoView.setFitWidth(35);
        ptmanLogoView.setPreserveRatio(true);
    }
}
