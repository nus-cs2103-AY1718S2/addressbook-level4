//@@author kokonguyen191
package seedu.recipe.ui.parser;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * A WebParser for recipes.wikia.com
 */
public class WikiaParser extends WebParser {

    public static final String DOMAIN = "recipes.wikia.com";
    private static final String LF = "\n";

    protected Document document;
    protected Element contentText;

    /**
     * Constructs from a Jsoup Document.
     */
    public WikiaParser(Document document) {
        requireNonNull(document);
        this.document = document;
        getMainBody();
    }

    /**
     * Constructs from a HTML string and a URL.
     */
    public WikiaParser(String html, String url) {
        requireNonNull(html);
        this.document = Jsoup.parse(html, url);
        getMainBody();
    }

    /**
     * Assigns {@code contentText} to the Element that contains the article body.
     */
    protected void getMainBody() {
        contentText = this.document.getElementById("mw-content-text");
    }

    @Override
    public String getName() {
        return document.selectFirst(".page-header__title").text();
    }

    @Override
    public String getIngredient() {
        Elements elements = contentText.select("h2,ul");
        Iterator<Element> eleIte = elements.iterator();
        while (eleIte.hasNext() && !eleIte.next().text().startsWith("Ingredient")) {
            // Do nothing and just go to the line that starts with "Ingredient"
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

    @Override
    public String getInstruction() {
        Elements elementsWithInstruction = contentText.select("ol li");
        List<String> instructionList = elementsWithInstruction.eachText();
        return String.join("\n", instructionList);
    }

    @Override
    public String getImageUrl() {
        Element image = contentText.selectFirst(".image img");
        if (image == null) {
            return "";
        } else {
            return image.attr("src");
        }
    }

    @Override
    public String getUrl() {
        return document.selectFirst("[rel=\"canonical\"]").attr("href");
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof WikiaParser // instanceof handles nulls
                && document.html().equals(((WikiaParser) other).document.html()));
    }
}
