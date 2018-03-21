package seedu.recipe.ui;

import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.recipe.commons.core.LogsCenter;
import seedu.recipe.commons.events.ui.JumpToListRequestEvent;
import seedu.recipe.commons.events.ui.ShareRecipeEvent;
import seedu.recipe.commons.events.ui.RecipePanelSelectionChangedEvent;
import seedu.recipe.model.recipe.Recipe;

/**
 * Panel containing the list of recipes.
 */
public class RecipeListPanel extends UiPart<Region> {
    private static final String FXML = "RecipeListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(RecipeListPanel.class);

    @FXML
    private ListView<RecipeCard> recipeListView;

    public RecipeListPanel(ObservableList<Recipe> recipeList) {
        super(FXML);
        setConnections(recipeList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Recipe> recipeList) {
        ObservableList<RecipeCard> mappedList = EasyBind.map(
                recipeList, (recipe) -> new RecipeCard(recipe, recipeList.indexOf(recipe) + 1));
        recipeListView.setItems(mappedList);
        recipeListView.setCellFactory(listView -> new RecipeListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        recipeListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in recipe list panel changed to : '" + newValue + "'");
                        raise(new RecipePanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code RecipeCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            recipeListView.scrollTo(index);
            recipeListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code RecipeCard}.
     */
    class RecipeListViewCell extends ListCell<RecipeCard> {

        @Override
        protected void updateItem(RecipeCard recipe, boolean empty) {
            super.updateItem(recipe, empty);

            if (empty || recipe == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(recipe.getRoot());
            }
        }
    }

}
