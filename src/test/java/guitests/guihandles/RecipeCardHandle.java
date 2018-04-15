package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;

/**
 * Provides a handle to a recipe card in the recipe list panel.
 */
public class RecipeCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";
    private static final String SERVINGS_FIELD_ID = "#servings";
    private static final String PREPARATION_TIME_FIELD_ID = "#preparationTime";
    private static final String INGREDIENT_FIELD_ID = "#ingredient";
    private static final String CALORIES_FIELD_ID = "#calories";
    private static final String URL_FIELD_ID = "#url";
    private static final String IMAGE_FIELD_ID = "#imageView";
    private static final String TAGS_FIELD_ID = "#tags";

    private final Label idLabel;
    private final Label nameLabel;
    private final Label servingsLabel;
    private final Label preparationTimeLabel;
    private final Label ingredientLabel;
    private final Label caloriesLabel;
    private final Label urlLabel;
    private final List<Label> tagLabels;
    private final ImageView imageView;

    public RecipeCardHandle(Node cardNode) {
        super(cardNode);

        this.idLabel = getChildNode(ID_FIELD_ID);
        this.nameLabel = getChildNode(NAME_FIELD_ID);
        this.servingsLabel = getChildNode(SERVINGS_FIELD_ID);
        this.preparationTimeLabel = getChildNode(PREPARATION_TIME_FIELD_ID);
        this.caloriesLabel = getChildNode(CALORIES_FIELD_ID);
        this.urlLabel = getChildNode(URL_FIELD_ID);
        this.ingredientLabel = getChildNode(INGREDIENT_FIELD_ID);
        this.imageView = getChildNode(IMAGE_FIELD_ID);

        Region tagsContainer = getChildNode(TAGS_FIELD_ID);
        this.tagLabels = tagsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getName() {
        return nameLabel.getText();
    }

    public String getServings() {
        return servingsLabel.getText();
    }

    public String getPreparationTime() {
        return preparationTimeLabel.getText();
    }

    public String getIngredient() {
        return ingredientLabel.getText();
    }

    public String getCalories() {
        return caloriesLabel.getText();
    }

    public String getUrl() {
        return urlLabel.getText();
    }

    public List<String> getTags() {
        return tagLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }
}
