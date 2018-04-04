//@@author kokonguyen191
package seedu.recipe.ui.util;

import static java.util.Objects.requireNonNull;
import static seedu.recipe.logic.commands.AddCommand.COMMAND_WORD;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_IMG;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_INGREDIENT;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_INSTRUCTION;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.Iterator;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * A WebParser for recipes.wikia.com
 */
public class WikiaParser implements WebParser {

    public static final String DOMAIN = "recipes.wikia.com";
    private static final String LF = "\n";

    private Document document;
    private Element contentText;

    /**
     * Constructs from a Ssoup Document.
     */
    public WikiaParser(Document document) {
        requireNonNull(document);
        this.document = document;
        contentText = this.document.getElementById("mw-content-text");
    }

    /**
     * Constructs from a HTML string.
     */
    public WikiaParser(String html) {
        requireNonNull(html);
        this.document = Jsoup.parse(html);
        contentText = this.document.getElementById("mw-content-text");
    }

    @Override
    public String parseRecipe() {
        String name = getName();
        String ingredient = getIngredient();
        String instruction = getInstruction();
        String imageUrl = getImageUrl();

        if (!name.equals("")) {
            StringBuilder commandBuilder = new StringBuilder();
            commandBuilder.append(COMMAND_WORD);
            commandBuilder.append(LF);
            commandBuilder.append(PREFIX_NAME);
            commandBuilder.append(name);
            if (!ingredient.equals("")) {
                commandBuilder.append(LF);
                commandBuilder.append(PREFIX_INGREDIENT);
                commandBuilder.append(ingredient);
            }
            if (!instruction.equals("")) {
                commandBuilder.append(LF);
                commandBuilder.append(PREFIX_INSTRUCTION);
                commandBuilder.append(instruction);
            }
            if (!imageUrl.equals("")) {
                commandBuilder.append(LF);
                commandBuilder.append(PREFIX_IMG);
                commandBuilder.append(imageUrl);
            }
            return commandBuilder.toString();
        }
        return null;
    }

    public String getName() {
        return document.selectFirst(".page-header__title").text();
    }

    public String getIngredient() {
        Elements elements = contentText.select("h2,ul");
        Iterator<Element> eleIte = elements.iterator();
        while (eleIte.hasNext() && !eleIte.next().text().startsWith("Ingredient")) {
            // Do nothing
        }
        Elements elementsWithIngredientWithLink = new Elements();
        Elements elementsWithIngredient = new Elements();
        while (eleIte.hasNext()) {
            Element nextElement = eleIte.next();
            if (nextElement.tagName().contains("h2")) {
                break;
            }
            elementsWithIngredient.addAll(nextElement.select("li"));
            elementsWithIngredientWithLink.addAll(nextElement.select("a"));
        }
        List<String> ingredientList;
        if (elementsWithIngredientWithLink.isEmpty()) {
            ingredientList = elementsWithIngredient.eachText();
        } else {
            ingredientList = elementsWithIngredientWithLink.eachText();
        }
        return String.join(", ", ingredientList);
    }

    public String getInstruction() {
        Elements elementsWithInstruction = contentText.select("ol li");
        List<String> instructionList = elementsWithInstruction.eachText();
        return String.join("\n", instructionList);
    }

    public String getImageUrl() {
        Element image = contentText.selectFirst(".image img");
        if (image == null) {
            return "";
        } else {
            return image.attr("src");
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof WikiaParser // instanceof handles nulls
                && document.equals(((WikiaParser) other).document));
    }
}
