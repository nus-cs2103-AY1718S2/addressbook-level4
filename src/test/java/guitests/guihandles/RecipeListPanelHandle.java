package guitests.guihandles;

import java.util.List;
import java.util.Optional;

import javafx.scene.control.ListView;
import seedu.recipe.model.recipe.Recipe;
import seedu.recipe.ui.RecipeCard;

/**
 * Provides a handle for {@code RecipeListPanel} containing the list of {@code RecipeCard}.
 */
public class RecipeListPanelHandle extends NodeHandle<ListView<RecipeCard>> {
    public static final String RECIPE_LIST_VIEW_ID = "#recipeListView";

    private Optional<RecipeCard> lastRememberedSelectedRecipeCard;

    public RecipeListPanelHandle(ListView<RecipeCard> recipeListPanelNode) {
        super(recipeListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code RecipeCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     */
    public RecipeCardHandle getHandleToSelectedCard() {
        List<RecipeCard> recipeList = getRootNode().getSelectionModel().getSelectedItems();

        if (recipeList.size() != 1) {
            throw new AssertionError("Recipe list size expected 1.");
        }

        return new RecipeCardHandle(recipeList.get(0).getRoot());
    }

    /**
     * Returns the index of the selected card.
     */
    public int getSelectedCardIndex() {
        return getRootNode().getSelectionModel().getSelectedIndex();
    }

    /**
     * Returns true if a card is currently selected.
     */
    public boolean isAnyCardSelected() {
        List<RecipeCard> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display and select the recipe.
     */
    public void navigateToCard(Recipe recipe) {
        List<RecipeCard> cards = getRootNode().getItems();
        Optional<RecipeCard> matchingCard = cards.stream().filter(card -> card.recipe.equals(recipe)).findFirst();

        if (!matchingCard.isPresent()) {
            throw new IllegalArgumentException("Recipe does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(matchingCard.get());
            getRootNode().getSelectionModel().select(matchingCard.get());
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Returns the recipe card handle of a recipe associated with the {@code index} in the list.
     */
    public RecipeCardHandle getRecipeCardHandle(int index) {
        return getRecipeCardHandle(getRootNode().getItems().get(index).recipe);
    }

    /**
     * Returns the {@code RecipeCardHandle} of the specified {@code recipe} in the list.
     */
    public RecipeCardHandle getRecipeCardHandle(Recipe recipe) {
        Optional<RecipeCardHandle> handle = getRootNode().getItems().stream()
                .filter(card -> card.recipe.equals(recipe))
                .map(card -> new RecipeCardHandle(card.getRoot()))
                .findFirst();
        return handle.orElseThrow(() -> new IllegalArgumentException("Recipe does not exist."));
    }

    /**
     * Selects the {@code RecipeCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Remembers the selected {@code RecipeCard} in the list.
     */
    public void rememberSelectedRecipeCard() {
        List<RecipeCard> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedRecipeCard = Optional.empty();
        } else {
            lastRememberedSelectedRecipeCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code RecipeCard} is different from the value remembered by the most recent
     * {@code rememberSelectedRecipeCard()} call.
     */
    public boolean isSelectedRecipeCardChanged() {
        List<RecipeCard> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedRecipeCard.isPresent();
        } else {
            return !lastRememberedSelectedRecipeCard.isPresent()
                    || !lastRememberedSelectedRecipeCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
