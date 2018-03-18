package seedu.recipe.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.recipe.model.recipe.Recipe;

/**
 * An UI component that displays information of a {@code Recipe}.
 */
public class RecipeCard extends UiPart<Region> {

    private static final String FXML = "RecipeListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on RecipeBook level 4</a>
     */

    public final Recipe recipe;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label instruction;
    @FXML
    private Label ingredient;
    @FXML
    private FlowPane tags;

    public RecipeCard(Recipe recipe, int displayedIndex) {
        super(FXML);
        this.recipe = recipe;
        id.setText(displayedIndex + ". ");
        name.setText(recipe.getName().fullName);
        phone.setText(recipe.getPhone().value);
        instruction.setText(recipe.getInstruction().value);
        ingredient.setText(recipe.getIngredient().value);
        initTags(recipe);
    }

    //@@author RyanAngJY
    /**
     * Returns the color style for {@code tagName}'s label.
     */
    private String getTagColorStyleFor(String tagName) {
        // the hash code of the tag name is used to generate a random color for each tag,
        // color remains consistent between different runs of the program since hash code does not change
        String hexadecimalHashCode = UiUtil.convertIntToHexadecimalString(tagName.hashCode());
        String hexadecimalColorCode = UiUtil.convertStringToValidColorCode(hexadecimalHashCode);
        return hexadecimalColorCode;
    }

    /**
     * Creates the tag labels for {@code recipe}.
     */
    private void initTags(Recipe recipe) {
        recipe.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            String labelBackgroundColor = getTagColorStyleFor(tag.tagName);
            UiUtil.setLabelColor(tagLabel, labelBackgroundColor);
            tags.getChildren().add(tagLabel);
        });
    }
    //@@author

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RecipeCard)) {
            return false;
        }

        // state check
        RecipeCard card = (RecipeCard) other;
        return id.getText().equals(card.id.getText())
                && recipe.equals(card.recipe);
    }
}
