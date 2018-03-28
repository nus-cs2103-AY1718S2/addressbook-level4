package seedu.recipe.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_CALORIES;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_COOKING_TIME;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_IMG;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_INGREDIENT;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_INSTRUCTION;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_PREPARATION_TIME;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_SERVINGS;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_URL;
import static seedu.recipe.model.Model.PREDICATE_SHOW_ALL_RECIPES;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.recipe.commons.core.Messages;
import seedu.recipe.commons.core.index.Index;
import seedu.recipe.commons.util.CollectionUtil;
import seedu.recipe.logic.commands.exceptions.CommandException;
import seedu.recipe.model.recipe.Calories;
import seedu.recipe.model.recipe.CookingTime;
import seedu.recipe.model.recipe.Image;
import seedu.recipe.model.recipe.Ingredient;
import seedu.recipe.model.recipe.Instruction;
import seedu.recipe.model.recipe.Name;
import seedu.recipe.model.recipe.PreparationTime;
import seedu.recipe.model.recipe.Recipe;
import seedu.recipe.model.recipe.Servings;
import seedu.recipe.model.recipe.Url;
import seedu.recipe.model.recipe.exceptions.DuplicateRecipeException;
import seedu.recipe.model.recipe.exceptions.RecipeNotFoundException;
import seedu.recipe.model.tag.Tag;

/**
 * Edits the details of an existing recipe in the recipe book.
 */
public class EditCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the recipe identified "
            + "by the index number used in the last recipe listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_INGREDIENT + "INGREDIENT] "
            + "[" + PREFIX_INSTRUCTION + "INSTRUCTION] "
            + "[" + PREFIX_COOKING_TIME + "COOKING_TIME] "
            + "[" + PREFIX_PREPARATION_TIME + "PREPARATION_TIME] "
            + "[" + PREFIX_CALORIES + "PREFIX_CALORIES] "
            + "[" + PREFIX_SERVINGS + "PREFIX_SERVINGS] "
            + "[" + PREFIX_URL + "URL] "
            + "[" + PREFIX_IMG + "IMAGE_FILE_PATH] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PREPARATION_TIME + "5 mins "
            + PREFIX_INGREDIENT + "chicken, rice";

    public static final String MESSAGE_EDIT_RECIPE_SUCCESS = "Edited Recipe: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_RECIPE = "This recipe already exists in the recipe book.";

    private final Index index;
    private final EditRecipeDescriptor editRecipeDescriptor;

    private Recipe recipeToEdit;
    private Recipe editedRecipe;

    /**
     * @param index of the recipe in the filtered recipe list to edit
     * @param editRecipeDescriptor details to edit the recipe with
     */
    public EditCommand(Index index, EditRecipeDescriptor editRecipeDescriptor) {
        requireNonNull(index);
        requireNonNull(editRecipeDescriptor);

        this.index = index;
        this.editRecipeDescriptor = new EditRecipeDescriptor(editRecipeDescriptor);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.updateRecipe(recipeToEdit, editedRecipe);
        } catch (DuplicateRecipeException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_RECIPE);
        } catch (RecipeNotFoundException pnfe) {
            throw new AssertionError("The target recipe cannot be missing");
        }
        model.updateFilteredRecipeList(PREDICATE_SHOW_ALL_RECIPES);
        return new CommandResult(String.format(MESSAGE_EDIT_RECIPE_SUCCESS, editedRecipe));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Recipe> lastShownList = model.getFilteredRecipeList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX);
        }

        recipeToEdit = lastShownList.get(index.getZeroBased());
        editedRecipe = createEditedRecipe(recipeToEdit, editRecipeDescriptor);
    }

    /**
     * Creates and returns a {@code Recipe} with the details of {@code recipeToEdit}
     * edited with {@code editRecipeDescriptor}.
     */
    private static Recipe createEditedRecipe(Recipe recipeToEdit, EditRecipeDescriptor editRecipeDescriptor) {
        assert recipeToEdit != null;

        Name updatedName = editRecipeDescriptor.getName().orElse(recipeToEdit.getName());
        Ingredient updatedIngredient = editRecipeDescriptor.getIngredient().orElse(recipeToEdit.getIngredient());
        Instruction updatedInstruction = editRecipeDescriptor.getInstruction().orElse(recipeToEdit.getInstruction());
        CookingTime updatedCookingTime =
                editRecipeDescriptor.getCookingTime().orElse(recipeToEdit.getCookingTime());
        PreparationTime updatedPreparationTime =
                editRecipeDescriptor.getPreparationTime().orElse(recipeToEdit.getPreparationTime());
        Calories updatedCalories =
                editRecipeDescriptor.getCalories().orElse(recipeToEdit.getCalories());
        Servings updatedServings =
                editRecipeDescriptor.getServings().orElse(recipeToEdit.getServings());
        Url updatedUrl = editRecipeDescriptor.getUrl().orElse(recipeToEdit.getUrl());
        Image updatedImage = editRecipeDescriptor.getImage().orElse(recipeToEdit.getImage());
        Set<Tag> updatedTags = editRecipeDescriptor.getTags().orElse(recipeToEdit.getTags());

        return new Recipe(updatedName, updatedIngredient, updatedInstruction, updatedCookingTime,
                updatedPreparationTime, updatedCalories, updatedServings, updatedUrl, updatedImage, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return index.equals(e.index)
                && editRecipeDescriptor.equals(e.editRecipeDescriptor)
                && Objects.equals(recipeToEdit, e.recipeToEdit);
    }

    /**
     * Stores the details to edit the recipe with. Each non-empty field value will replace the
     * corresponding field value of the recipe.
     */
    public static class EditRecipeDescriptor {
        private Name name;
        private Ingredient ingredient;
        private Instruction instruction;
        private CookingTime cookingTime;
        private PreparationTime preparationTime;
        private Calories calories;
        private Servings servings;
        private Url url;
        private Image image;
        private Set<Tag> tags;

        public EditRecipeDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditRecipeDescriptor(EditRecipeDescriptor toCopy) {
            setName(toCopy.name);
            setIngredient(toCopy.ingredient);
            setInstruction(toCopy.instruction);
            setCookingTime(toCopy.cookingTime);
            setPreparationTime(toCopy.preparationTime);
            setCalories(toCopy.calories);
            setServings(toCopy.servings);
            setUrl(toCopy.url);
            setImage(toCopy.image);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(this.name, this.ingredient, this.instruction, this.cookingTime,
                    this.preparationTime, this.calories, this.servings, this.url, this.image, this.tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setIngredient(Ingredient ingredient) {
            this.ingredient = ingredient;
        }

        public Optional<Ingredient> getIngredient() {
            return Optional.ofNullable(ingredient);
        }

        public void setInstruction(Instruction instruction) {
            this.instruction = instruction;
        }

        public Optional<Instruction> getInstruction() {
            return Optional.ofNullable(instruction);
        }

        public void setCookingTime(CookingTime cookingTime) {
            this.cookingTime = cookingTime;
        }

        public Optional<CookingTime> getCookingTime() {
            return Optional.ofNullable(cookingTime);
        }

        public void setPreparationTime(PreparationTime preparationTime) {
            this.preparationTime = preparationTime;
        }

        public Optional<PreparationTime> getPreparationTime() {
            return Optional.ofNullable(preparationTime);
        }

        public void setCalories(Calories calories) {
            this.calories = calories;
        }

        public Optional<Calories> getCalories() {
            return Optional.ofNullable(calories);
        }

        public void setServings(Servings servings) {
            this.servings = servings;
        }

        public Optional<Servings> getServings() {
            return Optional.ofNullable(servings);
        }

        public void setUrl(Url url) {
            this.url = url;
        }

        public Optional<Url> getUrl() {
            return Optional.ofNullable(url);
        }

        public void setImage(Image image) {
            this.image = image;
        }

        public Optional<Image> getImage() {
            return Optional.ofNullable(image);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditRecipeDescriptor)) {
                return false;
            }

            // state check
            EditRecipeDescriptor e = (EditRecipeDescriptor) other;

            return getName().equals(e.getName())
                    && getPreparationTime().equals(e.getPreparationTime())
                    && getIngredient().equals(e.getIngredient())
                    && getInstruction().equals(e.getInstruction())
                    && getUrl().equals(e.getUrl())
                    && getImage().equals(e.getImage())
                    && getTags().equals(e.getTags());
        }
    }
}
