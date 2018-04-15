//@@author RyanAngJY
package seedu.recipe.model.util;

import static seedu.recipe.ui.UiPart.FXML_FILE_FOLDER;

import java.net.URL;

import seedu.recipe.MainApp;
import seedu.recipe.model.recipe.Recipe;

/**
 * HTML formatter for Recipe class.
 */
public class HtmlFormatter {

    public static String getHtmlFormat (Recipe recipe) {

        URL recipeCss = MainApp.class.getResource(FXML_FILE_FOLDER + "Recipe.css");
        URL bootstrapCss = MainApp.class.getResource(FXML_FILE_FOLDER + "bootstrap.css");

        String name = recipe.getName().toString();
        String cookingTime = recipe.getCookingTime().toString();
        String preparationTime = recipe.getPreparationTime().toString();
        String calories = recipe.getCalories().toString();
        String servings = recipe.getServings().toString();
        String image = recipe.getImage().getUsablePath();
        String ingredient = recipe.getIngredient().toString();
        String instruction = recipe.getInstruction().toString();

        return "<html>"
                + "<head>"
                + "<link rel='stylesheet' type='text/css' href='" + bootstrapCss.toExternalForm() + "' />"
                + "<link rel='stylesheet' type='text/css' href='" + recipeCss.toExternalForm() + "' />"
                + "</head>"
                + "<body>"
                + "<div class='row'>"
                + "<h1 class='name'>" + name + "</h1>"
                + "<div class='col-sm-6'>"
                + "<div class='col-sm-3'>"
                + "<h5>Cooking Time:</h5>"
                + "<p>" + cookingTime + "</p>"
                + "</div>"
                + "<div class='col-sm-3'>"
                + "<h5>Preparation Time:</h5>"
                + "<p>" + preparationTime + "</p>"
                + "</div>"
                + "<div class='col-sm-3'>"
                + "<h5>Calories:</h5>"
                + "<p>" + calories + "</p>"
                + "</div>"
                + "<div class='col-sm-3'>"
                + "<h5>Servings:</h5>"
                + "<p>" + servings + "</p>"
                + "</div>"
                + "</div>"
                + "<div class='col-sm-6'>"
                + "<img src='" + image + "' />"
                + "</div>"
                + "<div class='col-sm-12'>"
                + "<div class='col-sm-12'>"
                + "<h5>Ingredients:</h5>"
                + "<p>" + ingredient + "</p>"
                + "</div>"
                + "<div class='col-sm-12'>"
                + "<h5>Instructions:</h5>"
                + "<p>" + instruction + "</p>"
                + "</div>"
                + "</div>"
                + "</div>"
                + "</body>"
                + "</html>";
    }
}
//@@author
