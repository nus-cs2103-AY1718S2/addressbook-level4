package seedu.recipe.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.recipe.commons.events.model.RecipeBookChangedEvent;
import seedu.recipe.commons.events.storage.DataSavingExceptionEvent;
import seedu.recipe.commons.exceptions.DataConversionException;
import seedu.recipe.model.ReadOnlyRecipeBook;
import seedu.recipe.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends RecipeBookStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    String getRecipeBookFilePath();

    @Override
    Optional<ReadOnlyRecipeBook> readRecipeBook() throws DataConversionException, IOException;

    @Override
    void saveRecipeBook(ReadOnlyRecipeBook recipeBook) throws IOException;

    /**
     * Saves the current version of the Recipe Book to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleRecipeBookChangedEvent(RecipeBookChangedEvent abce);
}
