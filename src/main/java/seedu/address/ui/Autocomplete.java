package seedu.address.ui;

import java.util.HashSet;
import java.util.Set;
//import java.util.regex.Pattern;

import javafx.scene.control.TextField;
import seedu.address.logic.Logic;
import seedu.address.logic.parser.Prefix;

//@@author aquarinte
/**
 * Handles autocompletion of command input such as command word, options, prefixes
 * and also some user input parameters: Nric, pet patient name and tag.
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
     * Find suggestions for current user-input in commandTextField.
     */
    public Set<String> getSuggestions(Logic logic, TextField commandTextField) {
        suggestions = new HashSet<>();
        String userInput = commandTextField.getText().trim();
        String[] words = userInput.split(" ");
        //for (String s : words) {
        //System.out.println("Word: " + s);
        //}
        //The string to auto-complete:
        String target = words[words.length - 1];

        if (words.length == 1) {
            suggestCommandWords(logic, words[0]);
        } else {

            if (words[words.length - 2].equals("-o") && target.startsWith("nr/")) {
                for (String nric : logic.getAllNric()) {
                    if (target.equals("nr/")) {
                        suggestions.add(nric);
                    } else {
                        String[] splitByPrefix = target.split("/");
                        if (nric.startsWith(splitByPrefix[1])) {
                            suggestions.add(nric);
                        }
                    }
                }
            }

            if (words[words.length - 2].equals("-p") && target.startsWith("n/")) {
                for (String petName : logic.getAllPetPatientNames()) {
                    if (target.equals("n/")) {
                        suggestions.add(petName);
                    } else {
                        String[] splitByPrefix = target.split("/");
                        if (petName.startsWith(splitByPrefix[1])) {
                            suggestions.add(petName);
                        }
                    }

                }
            }

            if (target.startsWith("t/")) {
                for (String tag : logic.getAllTagNames()) {
                    if (target.equals("t/")) {
                        suggestions.add(tag);
                    } else {
                        String[] splitByPrefix = target.split("/");
                        if (tag.startsWith(splitByPrefix[1]) && !tag.equals(splitByPrefix[1])) {
                            suggestions.add(tag);
                        }
                    }
                }
            }

            suggestOptions(logic, target);
            suggestPrefixes(logic, target);
        }
        return suggestions;
    }

    /**
     * Find suggestions for prefixes.
     */
    private void suggestPrefixes(Logic logic, String target) {
        for (Prefix p : logic.getAllPrefixes()) {
            if (p.toString().startsWith(target) && !p.toString().equals(target)) {
                suggestions.add(p.toString());
            }
        }
    }

    /**
     * Find suggestions for options.
     */
    private void suggestOptions(Logic logic, String target) {
        for (String option : logic.getAllOptions()) {
            if (option.startsWith(target) && !option.equals(target)) {
                suggestions.add(option);
            }
        }
    }

    /**
     * Find suggestions for command word.
     */
    private void suggestCommandWords(Logic logic, String word) {
        for (String command : logic.getAllCommandWords()) {
            if (command.startsWith(word) && !command.equals(word)) {
                suggestions.add(command);
            }
        }
    }
}
