package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.coin.Coin;
import seedu.address.model.tag.Tag;

/**
 * An UI component that displays information of a {@code Coin}.
 */
public class CoinCard extends UiPart<Region> {

    private static final String FXML = "CoinListCard.fxml";
    private static final String[] TAG_STYLE_CLASSES = {
        "red", "blue", "yellow", "grey", "burlywood", "plum"
    };

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on CoinBook level 4</a>
     */

    public final Coin coin;

    @FXML
    private HBox cardPane;
    @FXML
    private Label code;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;
    @FXML
    private Label amount;
    @FXML
    private Label price;

    public CoinCard(Coin coin, int displayedIndex) {
        super(FXML);
        this.coin = coin;
        id.setText(displayedIndex + ". ");

        code.setText(coin.getCode().fullName);
        amount.setText(coin.getCurrentAmountHeld().getValue().toString());
        price.setText(coin.getPrice().toString());
        coin.getTags().forEach(tag -> newTag(tag));
    }

    /**
     * Create the tag label and add it to the display list.
     * @param tag
     */
    public void newTag(Tag tag) {
        Label label = new Label(tag.tagName);
        label.getStyleClass().add(getLabelColor(tag));
        tags.getChildren().add(label);
    }

    /**
     * Retrieve a color for the specified tag's label.
     * @param tag
     * @return Label style as a CSS class name string.
     */
    private String getLabelColor(Tag tag) {
        int choice = Math.abs(tag.tagName.hashCode());
        return TAG_STYLE_CLASSES[choice % TAG_STYLE_CLASSES.length];
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CoinCard)) {
            return false;
        }

        // state check
        CoinCard card = (CoinCard) other;
        return id.getText().equals(card.id.getText())
                && coin.equals(card.coin);
    }
}
