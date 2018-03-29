package seedu.address.ui;

import java.util.HashSet;
import java.util.Set;

import javafx.scene.control.TextField;
import seedu.address.logic.Logic;
import seedu.address.logic.parser.Prefix;

//@@author aquarinte
/**
 * Handles autocompletion of command input such as command word, options and prefixes.
 */
public class Autocomplete {

    private static Autocomplete instance;
    private Set<String> suggestions;

    public static Autocomplete getInstance() {
        if (instance == null) {
            instance = new Autocomplete();
        }
        return instance;
    }

    /**
     * Find suggestions for current user-input in comandTextField.
     */
    public Set<String> getSuggestion(Logic logic, TextField commandTextField) {
        String userInput = commandTextField.getText().trim();
        String[] words = userInput.split(" ");
        suggestions = new HashSet<>();

        if (words.length == 1) {
            // suggest command words
            for (String command : logic.getAllCommandWords()) {
                if (command.startsWith(words[0])) {
                    suggestions.add(command);
                }
            }
        } else {

            if (words[words.length - 2].equals("-o") && words[words.length - 1].equals("nr/")) {
                for (String nric : logic.getAllNric()) {
                    suggestions.add(nric);
                }
            }
            for (String option : logic.getAllOptions()) {
                if (option.startsWith(words[words.length - 1])) {
                    suggestions.add(option);
                }
            }

            for (Prefix p : logic.getAllPrefixes()) {
                if (p.toString().startsWith(words[words.length - 1])) {
                    suggestions.add(p.toString());
                }
            }
        }
        return suggestions;

    }
}
