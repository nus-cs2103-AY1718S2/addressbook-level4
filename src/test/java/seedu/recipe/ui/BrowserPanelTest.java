package seedu.recipe.ui;

import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
import static org.junit.Assert.assertEquals;
import static seedu.recipe.testutil.EventsUtil.postNow;
import static seedu.recipe.testutil.TypicalRecipes.ALICE;
import static seedu.recipe.ui.BrowserPanel.DEFAULT_PAGE;
import static seedu.recipe.ui.UiPart.FXML_FILE_FOLDER;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.BrowserPanelHandle;
import seedu.recipe.MainApp;
import seedu.recipe.commons.core.index.Index;
import seedu.recipe.commons.events.ui.RecipePanelSelectionChangedEvent;
import seedu.recipe.commons.events.ui.ShareRecipeEvent;
import seedu.recipe.model.recipe.Recipe;
import seedu.recipe.model.recipe.Url;
import seedu.recipe.testutil.RecipeBuilder;
import seedu.recipe.ui.util.FacebookHandler;

public class BrowserPanelTest extends GuiUnitTest {
    private RecipePanelSelectionChangedEvent selectionChangedEventStub;
    private ShareRecipeEvent shareRecipeEvent;

    private BrowserPanel browserPanel;
    private BrowserPanelHandle browserPanelHandle;

    private Recipe recipeStub = new RecipeBuilder().build();
    private Recipe recipeStubWithoutUrl = new RecipeBuilder().withUrl(Url.NULL_URL_REFERENCE).build();

    @Before
    public void setUp() {
        selectionChangedEventStub = new RecipePanelSelectionChangedEvent(new RecipeCard(ALICE, 0));
        shareRecipeEvent = new ShareRecipeEvent(Index.fromZeroBased(1), recipeStub);

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
        // login page to share recipe
        postNow(shareRecipeEvent);
        expectedRecipeUrl = new URL(FacebookHandler.getPostDomain() + recipeStub.getUrl()
                + FacebookHandler.getRedirectEmbedded());
        assertEquals(expectedRecipeUrl, browserPanelHandle.getLoadedUrl());

        shareRecipeEvent = new ShareRecipeEvent(Index.fromZeroBased(1), recipeStubWithoutUrl);
        postNow(shareRecipeEvent);
        expectedRecipeUrl = new URL(FacebookHandler.REDIRECT_DOMAIN);
        assertEquals(expectedRecipeUrl, browserPanelHandle.getLoadedUrl());
        //@@author
    }
}
