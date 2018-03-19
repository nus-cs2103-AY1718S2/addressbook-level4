package seedu.recipe.ui;

import static org.junit.Assert.assertEquals;
import static seedu.recipe.testutil.EventsUtil.postNow;
import static seedu.recipe.testutil.TypicalIndexes.INDEX_SECOND_RECIPE;
import static seedu.recipe.testutil.TypicalRecipes.getTypicalRecipes;
import static seedu.recipe.ui.testutil.GuiTestAssert.assertCardDisplaysRecipe;
import static seedu.recipe.ui.testutil.GuiTestAssert.assertCardEquals;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.RecipeCardHandle;
import guitests.guihandles.RecipeListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.recipe.commons.events.ui.JumpToListRequestEvent;
import seedu.recipe.model.recipe.Recipe;

public class RecipeListPanelTest extends GuiUnitTest {
    private static final ObservableList<Recipe> TYPICAL_RECIPES =
            FXCollections.observableList(getTypicalRecipes());

    private static final JumpToListRequestEvent JUMP_TO_SECOND_EVENT = new JumpToListRequestEvent(INDEX_SECOND_RECIPE);

    private RecipeListPanelHandle recipeListPanelHandle;

    @Before
    public void setUp() {
        RecipeListPanel recipeListPanel = new RecipeListPanel(TYPICAL_RECIPES);
        uiPartRule.setUiPart(recipeListPanel);

        recipeListPanelHandle = new RecipeListPanelHandle(getChildNode(recipeListPanel.getRoot(),
                RecipeListPanelHandle.RECIPE_LIST_VIEW_ID));
    }

    @Test
    public void display() {
        for (int i = 0; i < TYPICAL_RECIPES.size(); i++) {
            recipeListPanelHandle.navigateToCard(TYPICAL_RECIPES.get(i));
            Recipe expectedRecipe = TYPICAL_RECIPES.get(i);
            RecipeCardHandle actualCard = recipeListPanelHandle.getRecipeCardHandle(i);

            assertCardDisplaysRecipe(expectedRecipe, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void handleJumpToListRequestEvent() {
        postNow(JUMP_TO_SECOND_EVENT);
        guiRobot.pauseForHuman();

        RecipeCardHandle expectedCard = recipeListPanelHandle.getRecipeCardHandle(INDEX_SECOND_RECIPE.getZeroBased());
        RecipeCardHandle selectedCard = recipeListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedCard, selectedCard);
    }
}
