package seedu.recipe.model.recipe;

import static seedu.recipe.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import seedu.recipe.model.tag.Tag;
import seedu.recipe.model.tag.UniqueTagList;

/**
 * Represents a Recipe in the recipe book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Recipe {

    private final Name name;
    private final PreparationTime preparationTime;
    private final Ingredient ingredient;
    private final Instruction instruction;
    private final Url url;
    private final UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Recipe(Name name, PreparationTime preparationTime, Ingredient ingredient, Instruction instruction,
                  Url url, Set<Tag> tags) {
        requireAllNonNull(name, preparationTime, ingredient, instruction, url, tags);
        this.name = name;
        this.preparationTime = preparationTime;
        this.ingredient = ingredient;
        this.instruction = instruction;
        this.url = url;
        // protect internal tags from changes in the arg list
        this.tags = new UniqueTagList(tags);
    }

    public Name getName() {
        return name;
    }

    public PreparationTime getPreparationTime() {
        return preparationTime;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public Instruction getInstruction() {
        return instruction;
    }

    public Url getUrl() {
        return url;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags.toSet());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Recipe)) {
            return false;
        }

        Recipe otherRecipe = (Recipe) other;
        return otherRecipe.getName().equals(this.getName())
                && otherRecipe.getPreparationTime().equals(this.getPreparationTime())
                && otherRecipe.getIngredient().equals(this.getIngredient())
                && otherRecipe.getInstruction().equals(this.getInstruction())
                && otherRecipe.getUrl().equals(this.getUrl());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, preparationTime, ingredient, instruction, url, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" PreparationTime: ")
                .append(getPreparationTime())
                .append(" Ingredient: ")
                .append(getIngredient())
                .append(" Instruction: ")
                .append(getInstruction())
                .append(" Url: ")
                .append(getUrl())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
