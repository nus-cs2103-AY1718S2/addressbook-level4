package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.movie.Movie;

//@@author qwlai
/**
 * An UI component that displays information of a {@code Movie}.
 */
public class MovieCard extends UiPart<Region> {

    private static final String FXML = "MovieListCard.fxml";
    private static final String[] TAG_COLORS = {"red", "blue", "orange", "green", "yellow", "grey", "white", "black",
        "pink", "brown"};

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/movieplanner-level4/issues/336">The issue on MoviePlanner level 4</a>
     */

    public final Movie movie;

    @FXML
    private HBox cardPane;
    @FXML
    private Label movieName;
    @FXML
    private Label id;
    @FXML
    private Label duration;
    @FXML
    private Label rating;
    @FXML
    private Label startDate;
    @FXML
    private FlowPane tags;

    public MovieCard(Movie movie, int displayedIndex) {
        super(FXML);
        this.movie = movie;
        id.setText(displayedIndex + ". ");
        movieName.setText(movie.getName().toString());
        duration.setText((movie.getDuration().toString()));
        rating.setText((movie.getRating().toString()));
        startDate.setText((movie.getStartDate().toString()));
        initializeTags(movie);
    }

    /**
     * Returns color for {@code tagName} label
     */
    private String getTagColor(String tagName) {
        return TAG_COLORS[Math.abs(tagName.hashCode()) % TAG_COLORS.length];
    }

    /**
     * Create tag labels for {@code movie}
     */
    private void initializeTags(Movie movie) {
        movie.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.getStyleClass().add(getTagColor(tag.tagName));
            tags.getChildren().add(tagLabel);
        });
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MovieCard)) {
            return false;
        }

        // state check
        MovieCard card = (MovieCard) other;
        return id.getText().equals(card.id.getText())
                && movie.equals(card.movie);
    }
}
