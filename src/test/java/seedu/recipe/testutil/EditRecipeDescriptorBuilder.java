package seedu.recipe.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.recipe.logic.commands.EditCommand.EditRecipeDescriptor;
import seedu.recipe.model.recipe.Instruction;
import seedu.recipe.model.recipe.Ingredient;
import seedu.recipe.model.recipe.Name;
import seedu.recipe.model.recipe.Recipe;
import seedu.recipe.model.recipe.Phone;
import seedu.recipe.model.tag.Tag;

/**
 * A utility class to help with building EditRecipeDescriptor objects.
 */
public class EditRecipeDescriptorBuilder {

    private EditRecipeDescriptor descriptor;

    public EditRecipeDescriptorBuilder() {
        descriptor = new EditRecipeDescriptor();
    }

    public EditRecipeDescriptorBuilder(EditRecipeDescriptor descriptor) {
        this.descriptor = new EditRecipeDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditRecipeDescriptor} with fields containing {@code recipe}'s details
     */
    public EditRecipeDescriptorBuilder(Recipe recipe) {
        descriptor = new EditRecipeDescriptor();
        descriptor.setName(recipe.getName());
        descriptor.setPhone(recipe.getPhone());
        descriptor.setIngredient(recipe.getIngredient());
        descriptor.setInstruction(recipe.getInstruction());
        descriptor.setTags(recipe.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditRecipeDescriptor} that we are building.
     */
    public EditRecipeDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditRecipeDescriptor} that we are building.
     */
    public EditRecipeDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Ingredient} of the {@code EditRecipeDescriptor} that we are building.
     */
    public EditRecipeDescriptorBuilder withIngredient(String ingredient) {
        descriptor.setIngredient(new Ingredient(ingredient));
        return this;
    }

    /**
     * Sets the {@code Instruction} of the {@code EditRecipeDescriptor} that we are building.
     */
    public EditRecipeDescriptorBuilder withInstruction(String instruction) {
        descriptor.setInstruction(new Instruction(instruction));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditRecipeDescriptor}
     * that we are building.
     */
    public EditRecipeDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditRecipeDescriptor build() {
        return descriptor;
    }
}
