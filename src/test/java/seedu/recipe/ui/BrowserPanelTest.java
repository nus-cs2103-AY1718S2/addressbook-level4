package seedu.recipe.ui;

import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.recipe.testutil.EventsUtil.postNow;
import static seedu.recipe.testutil.TypicalRecipes.ALICE;
import static seedu.recipe.ui.BrowserPanel.DEFAULT_PAGE;
import static seedu.recipe.ui.UiPart.FXML_FILE_FOLDER;

import java.net.URL;
import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.BrowserPanelHandle;
import seedu.recipe.MainApp;
import seedu.recipe.commons.core.index.Index;
import seedu.recipe.commons.events.ui.RecipePanelSelectionChangedEvent;
import seedu.recipe.commons.events.ui.ShareRecipeEvent;
import seedu.recipe.model.recipe.Recipe;
import seedu.recipe.testutil.RecipeBuilder;
import seedu.recipe.ui.util.FacebookHandler;

public class BrowserPanelTest extends GuiUnitTest {
    private RecipePanelSelectionChangedEvent selectionChangedEventStub;
    private ShareRecipeEvent shareRecipeEvent;

    private BrowserPanel browserPanel;
    private BrowserPanelHandle browserPanelHandle;

    private Recipe recipeStub = new RecipeBuilder().build();
    private Recipe recipeStubWithoutUrl = new RecipeBuilder().build();

    @Before
    public void setUp() {
        selectionChangedEventStub = new RecipePanelSelectionChangedEvent(new RecipeCard(ALICE, 0));
        shareRecipeEvent = new ShareRecipeEvent(Index.fromZeroBased(0), recipeStub);

        guiRobot.interact(() -> browserPanel = new BrowserPanel());
        uiPartRule.setUiPart(browserPanel);

        browserPanelHandle = new BrowserPanelHandle(browserPanel.getRoot());
    }

    @Test
    public void display() throws Exception {
        // default web page
        URL expectedDefaultPageUrl = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        assertEquals(expectedDefaultPageUrl, browserPanelHandle.getLoadedUrl());

        // associated web page of a recipe
        postNow(selectionChangedEventStub);
        URL expectedRecipeUrl = new URL(ALICE.getUrl().toString());

        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedRecipeUrl, browserPanelHandle.getLoadedUrl());

        //@@author RyanAngJY
        postNow(shareRecipeEvent);
        String expectedUrlRegex = "https://www.facebook.com.*";
        assertTrue(Pattern.matches(expectedUrlRegex, browserPanelHandle.getLoadedUrl().toString()));

        shareRecipeEvent = new ShareRecipeEvent(Index.fromZeroBased(1), recipeStubWithoutUrl);
        postNow(shareRecipeEvent);
        assertTrue(Pattern.matches(expectedUrlRegex, browserPanelHandle.getLoadedUrl().toString()));
        //@@author

    }
}
