package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.cinema.Cinema;

/**
 * An UI component that displays information of a {@code Cinema}.
 */
public class CinemaCard extends UiPart<Region> {

    private static final String FXML = "CinemaListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/movieplanner-level4/issues/336">The issue on MoviePlanner level 4</a>
     */

    public final Cinema cinema;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;

    public CinemaCard(Cinema cinema, int displayedIndex) {
        super(FXML);
        this.cinema = cinema;
        id.setText(displayedIndex + ". ");
        name.setText(cinema.getName().fullName);
        phone.setText(cinema.getPhone().value);
        address.setText(cinema.getAddress().value);
        email.setText(cinema.getEmail().value);
    }


    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CinemaCard)) {
            return false;
        }

        // state check
        CinemaCard card = (CinemaCard) other;
        return id.getText().equals(card.id.getText())
                && cinema.equals(card.cinema);
    }
}
