package seedu.recipe.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.recipe.model.recipe.Instruction;
import seedu.recipe.model.recipe.Ingredient;
import seedu.recipe.model.recipe.Name;
import seedu.recipe.model.recipe.Recipe;
import seedu.recipe.model.recipe.Phone;
import seedu.recipe.model.tag.Tag;
import seedu.recipe.model.util.SampleDataUtil;

/**
 * A utility class to help with building Recipe objects.
 */
public class RecipeBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_INGREDIENT = "alice@gmail.com";
    public static final String DEFAULT_INSTRUCTION = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_TAGS = "friends";

    private Name name;
    private Phone phone;
    private Ingredient ingredient;
    private Instruction instruction;
    private Set<Tag> tags;

    public RecipeBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        ingredient = new Ingredient(DEFAULT_INGREDIENT);
        instruction = new Instruction(DEFAULT_INSTRUCTION);
        tags = SampleDataUtil.getTagSet(DEFAULT_TAGS);
    }

    /**
     * Initializes the RecipeBuilder with the data of {@code recipeToCopy}.
     */
    public RecipeBuilder(Recipe recipeToCopy) {
        name = recipeToCopy.getName();
        phone = recipeToCopy.getPhone();
        ingredient = recipeToCopy.getIngredient();
        instruction = recipeToCopy.getInstruction();
        tags = new HashSet<>(recipeToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Recipe} that we are building.
     */
    public RecipeBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Recipe} that we are building.
     */
    public RecipeBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Instruction} of the {@code Recipe} that we are building.
     */
    public RecipeBuilder withInstruction(String instruction) {
        this.instruction = new Instruction(instruction);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Recipe} that we are building.
     */
    public RecipeBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Ingredient} of the {@code Recipe} that we are building.
     */
    public RecipeBuilder withIngredient(String ingredient) {
        this.ingredient = new Ingredient(ingredient);
        return this;
    }

    public Recipe build() {
        return new Recipe(name, phone, ingredient, instruction, tags);
    }

}
