package seedu.address.ui;

import java.util.Optional;
import java.util.logging.Logger;

import javafx.scene.control.TextInputDialog;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.logic.ReviewInputEvent;

/**
 * Pop-up dialog to prompt user to enter the review.
 */
public class ReviewDialog {

    private TextInputDialog dialog;
    private Logger logger = LogsCenter.getLogger(this.getClass());

    public ReviewDialog() {
        dialog = new TextInputDialog();
        dialog.setTitle("Review Input Dialog");
        dialog.setHeaderText("Review has no length limit and accept any character");
        dialog.setContentText("Input your review:");
    }

    /**
     * Show the pop-up dialog when called
     */
    public void show() {
        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            logger.info(result.get());
            EventsCenter.getInstance().post(new ReviewInputEvent(result.get()));

            //System.out.println("Your name: " + result.get());
        }

        // The Java 8 way to get the response value (with lambda expression).
        //result.ifPresent(name -> System.out.println("Your name: " + name));
    }
}
