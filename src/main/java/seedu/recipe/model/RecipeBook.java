package seedu.recipe.model;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.recipe.model.recipe.Recipe;
import seedu.recipe.model.recipe.UniqueRecipeList;
import seedu.recipe.model.recipe.exceptions.DuplicateRecipeException;
import seedu.recipe.model.recipe.exceptions.RecipeNotFoundException;
import seedu.recipe.model.tag.Tag;
import seedu.recipe.model.tag.UniqueTagList;

/**
 * Wraps all data at the recipe-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class RecipeBook implements ReadOnlyRecipeBook {

    private final UniqueRecipeList recipes;
    private final UniqueTagList tags;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        recipes = new UniqueRecipeList();
        tags = new UniqueTagList();
    }

    public RecipeBook() {}

    /**
     * Creates an RecipeBook using the Recipes and Tags in the {@code toBeCopied}
     */
    public RecipeBook(ReadOnlyRecipeBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    public void setRecipes(List<Recipe> recipes) throws DuplicateRecipeException {
        this.recipes.setRecipes(recipes);
    }

    public void setTags(Set<Tag> tags) {
        this.tags.setTags(tags);
    }

    /**
     * Resets the existing data of this {@code RecipeBook} with {@code newData}.
     */
    public void resetData(ReadOnlyRecipeBook newData) {
        requireNonNull(newData);
        setTags(new HashSet<>(newData.getTagList()));
        List<Recipe> syncedRecipeList = newData.getRecipeList().stream()
                .map(this::syncWithMasterTagList)
                .collect(Collectors.toList());

        try {
            setRecipes(syncedRecipeList);
        } catch (DuplicateRecipeException e) {
            throw new AssertionError("RecipeBooks should not have duplicate recipes");
        }
    }

    //// recipe-level operations

    /**
     * Adds a recipe to the recipe book.
     * Also checks the new recipe's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the recipe to point to those in {@link #tags}.
     *
     * @throws DuplicateRecipeException if an equivalent recipe already exists.
     */
    public void addRecipe(Recipe p) throws DuplicateRecipeException {
        Recipe recipe = syncWithMasterTagList(p);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any recipe
        // in the recipe list.
        recipes.add(recipe);
    }

    /**
     * Replaces the given recipe {@code target} in the list with {@code editedRecipe}.
     * {@code RecipeBook}'s tag list will be updated with the tags of {@code editedRecipe}.
     *
     * @throws DuplicateRecipeException if updating the recipe's details causes the recipe to be equivalent to
     *      another existing recipe in the list.
     * @throws RecipeNotFoundException if {@code target} could not be found in the list.
     *
     * @see #syncWithMasterTagList(Recipe)
     */
    public void updateRecipe(Recipe target, Recipe editedRecipe)
            throws DuplicateRecipeException, RecipeNotFoundException {
        requireNonNull(editedRecipe);

        Recipe syncedEditedRecipe = syncWithMasterTagList(editedRecipe);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any recipe
        // in the recipe list.
        recipes.setRecipe(target, syncedEditedRecipe);
    }

    /**
     *  Updates the master tag list to include tags in {@code recipe} that are not in the list.
     *  @return a copy of this {@code recipe} such that every tag in this recipe points to a Tag object in the master
     *  list.
     */
    private Recipe syncWithMasterTagList(Recipe recipe) {
        final UniqueTagList recipeTags = new UniqueTagList(recipe.getTags());
        tags.mergeFrom(recipeTags);

        // Create map with values = tag object references in the master list
        // used for checking recipe tag references
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        tags.forEach(tag -> masterTagObjects.put(tag, tag));

        // Rebuild the list of recipe tags to point to the relevant tags in the master tag list.
        final Set<Tag> correctTagReferences = new HashSet<>();
        recipeTags.forEach(tag -> correctTagReferences.add(masterTagObjects.get(tag)));
        return new Recipe(recipe.getName(), recipe.getPreparationTime(), recipe.getIngredient(),
                recipe.getInstruction(), recipe.getUrl(), correctTagReferences);
    }

    /**
     * Removes {@code key} from this {@code RecipeBook}.
     * @throws RecipeNotFoundException if the {@code key} is not in this {@code RecipeBook}.
     */
    public boolean removeRecipe(Recipe key) throws RecipeNotFoundException {
        if (recipes.remove(key)) {
            return true;
        } else {
            throw new RecipeNotFoundException();
        }
    }

    //// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

    //// util methods

    @Override
    public String toString() {
        return recipes.asObservableList().size() + " recipes, " + tags.asObservableList().size() +  " tags";
        // TODO: refine later
    }

    @Override
    public ObservableList<Recipe> getRecipeList() {
        return recipes.asObservableList();
    }

    @Override
    public ObservableList<Tag> getTagList() {
        return tags.asObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RecipeBook // instanceof handles nulls
                && this.recipes.equals(((RecipeBook) other).recipes)
                && this.tags.equalsOrderInsensitive(((RecipeBook) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(recipes, tags);
    }
}
