package seedu.address.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;

/**
 * SuggestionPopUp component shows suggestions when user type in command
 */
public class SuggestionPopUp extends ContextMenu {

    private static int MAX_SUGGESTIONS = 4;

    private List<CustomMenuItem> suggestions = new ArrayList<>();

    public void findSuggestions(String inputText, List<String> textList) {
        Collections.sort(textList);

        for (String suggestion : textList) {
            if (suggestion.startsWith(inputText)) {
                CustomMenuItem item = new CustomMenuItem(new Label(suggestion), false);
                suggestions.add(item);
            }

            if (suggestions.size() == MAX_SUGGESTIONS) {
                break;
            }
        }
    }

    public List<CustomMenuItem> getSuggestions() {
        return suggestions;
    }
}
