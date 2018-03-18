package seedu.recipe.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;

import seedu.recipe.commons.exceptions.IllegalValueException;
import seedu.recipe.model.recipe.Ingredient;
import seedu.recipe.model.recipe.Instruction;
import seedu.recipe.model.recipe.Name;
import seedu.recipe.model.recipe.PreparationTime;
import seedu.recipe.model.recipe.Recipe;
import seedu.recipe.model.tag.Tag;

/**
 * JAXB-friendly version of the Recipe.
 */
public class XmlAdaptedRecipe {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Recipe's %s field is missing!";

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String preparationTime;
    @XmlElement(required = true)
    private String ingredient;
    @XmlElement(required = true)
    private String instruction;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedRecipe.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedRecipe() {}

    /**
     * Constructs an {@code XmlAdaptedRecipe} with the given recipe details.
     */
    public XmlAdaptedRecipe(String name, String preparationTime, String ingredient, String instruction, List<XmlAdaptedTag> tagged) {
        this.name = name;
        this.preparationTime = preparationTime;
        this.ingredient = ingredient;
        this.instruction = instruction;
        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
        }
    }

    /**
     * Converts a given Recipe into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedRecipe
     */
    public XmlAdaptedRecipe(Recipe source) {
        name = source.getName().fullName;
        preparationTime = source.getPreparationTime().value;
        ingredient = source.getIngredient().value;
        instruction = source.getInstruction().value;
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
    }

    /**
     * Converts this jaxb-friendly adapted recipe object into the model's Recipe object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted recipe
     */
    public Recipe toModelType() throws IllegalValueException {
        final List<Tag> recipeTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            recipeTags.add(tag.toModelType());
        }

        if (this.name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(this.name)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        final Name name = new Name(this.name);

        if (this.preparationTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, PreparationTime.class.getSimpleName()));
        }
        if (!PreparationTime.isValidPreparationTime(this.preparationTime)) {
            throw new IllegalValueException(PreparationTime.MESSAGE_PREPARATION_TIME_CONSTRAINTS);
        }
        final PreparationTime preparationTime = new PreparationTime(this.preparationTime);

        if (this.ingredient == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Ingredient.class.getSimpleName()));
        }
        if (!Ingredient.isValidIngredient(this.ingredient)) {
            throw new IllegalValueException(Ingredient.MESSAGE_INGREDIENT_CONSTRAINTS);
        }
        final Ingredient ingredient = new Ingredient(this.ingredient);

        if (this.instruction == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Instruction.class.getSimpleName()));
        }
        if (!Instruction.isValidInstuction(this.instruction)) {
            throw new IllegalValueException(Instruction.MESSAGE_INSTRUCTION_CONSTRAINTS);
        }
        final Instruction instruction = new Instruction(this.instruction);

        final Set<Tag> tags = new HashSet<>(recipeTags);
        return new Recipe(name, preparationTime, ingredient, instruction, tags);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedRecipe)) {
            return false;
        }

        XmlAdaptedRecipe otherRecipe = (XmlAdaptedRecipe) other;
        return Objects.equals(name, otherRecipe.name)
                && Objects.equals(preparationTime, otherRecipe.preparationTime)
                && Objects.equals(ingredient, otherRecipe.ingredient)
                && Objects.equals(instruction, otherRecipe.instruction)
                && tagged.equals(otherRecipe.tagged);
    }
}
