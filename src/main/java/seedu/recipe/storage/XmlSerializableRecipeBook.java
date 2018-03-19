package seedu.recipe.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.recipe.commons.exceptions.IllegalValueException;
import seedu.recipe.model.ReadOnlyRecipeBook;
import seedu.recipe.model.RecipeBook;

/**
 * An Immutable RecipeBook that is serializable to XML format
 */
@XmlRootElement(name = "recipebook")
public class XmlSerializableRecipeBook {

    @XmlElement
    private List<XmlAdaptedRecipe> recipes;
    @XmlElement
    private List<XmlAdaptedTag> tags;

    /**
     * Creates an empty XmlSerializableRecipeBook.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableRecipeBook() {
        recipes = new ArrayList<>();
        tags = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableRecipeBook(ReadOnlyRecipeBook src) {
        this();
        recipes.addAll(src.getRecipeList().stream().map(XmlAdaptedRecipe::new).collect(Collectors.toList()));
        tags.addAll(src.getTagList().stream().map(XmlAdaptedTag::new).collect(Collectors.toList()));
    }

    /**
     * Converts this recipebook into the model's {@code RecipeBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedRecipe} or {@code XmlAdaptedTag}.
     */
    public RecipeBook toModelType() throws IllegalValueException {
        RecipeBook recipeBook = new RecipeBook();
        for (XmlAdaptedTag t : tags) {
            recipeBook.addTag(t.toModelType());
        }
        for (XmlAdaptedRecipe p : recipes) {
            recipeBook.addRecipe(p.toModelType());
        }
        return recipeBook;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableRecipeBook)) {
            return false;
        }

        XmlSerializableRecipeBook otherAb = (XmlSerializableRecipeBook) other;
        return recipes.equals(otherAb.recipes) && tags.equals(otherAb.tags);
    }
}
