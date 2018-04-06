package seedu.address.ui;

import java.util.List;
import java.util.stream.Collectors;

import javafx.scene.control.TextField;
import seedu.address.logic.Logic;

//@@author aquarinte
/**
 * Handles case-insensitive autocompletion of command input such as command word, options, prefixes
 * and also some user input parameters: Nric, pet patient name and tag.
 */
public class Autocomplete {

    private static Autocomplete instance;
    private Logic logic;
    private String targetWord;

    public static Autocomplete getInstance() {
        if (instance == null) {
            instance = new Autocomplete();
        }
        return instance;
    }

    /**
     * Find suggestions for current user-input in commandTextField.
     */
    public List<String> getSuggestions(Logic logic, TextField commandTextField) {
        this.logic = logic;
        String userInput = commandTextField.getText().trim();
        String[] words = userInput.split(" ");
        targetWord = words[words.length - 1].toLowerCase();

        if (words.length == 1) {
            return suggestCommandWords();
        } else {
            if (addReferenceOwnerNric(words) || editPetPatientOwnerNric(words) || findByPersonNric(words)) {
                return suggestNrics();
            }

            if (words[words.length - 2].equals("-p") && targetWord.startsWith("n/")) {
                return suggestPetPatientNames();
            }

            if (targetWord.startsWith("t/")) {
                return suggestTagNames();
            }

            if (targetWord.startsWith("-")) {
                return suggestOptions();
            }

            return suggestPrefixes();
        }
    }

    /**
     * Checks if command input is the "add" command, and whether it has the form "-o nr/" at the end.
     */
    private boolean addReferenceOwnerNric(String[] words) {
        if (words[0].equals("add") && words[words.length - 2].equals("-o") && targetWord.startsWith("nr/")) {
            return true;
        }
        return false;
    }

    /**
     * Checks if command input is the "edit -p" command, with the last word starting with "nr/".
     */
    private boolean editPetPatientOwnerNric(String[] words) {
        if (words[0].equals("edit") && words[1].equals("-p") && targetWord.startsWith("nr/")) {
            return true;
        }
        return false;
    }

    /**
     * Checks if command input is the "edit -p" command, with the last word starting with "nr/".
     */
    private boolean findByPersonNric(String[] words) {
        if (words[0].equals("find") && words[1].equals("-o") && targetWord.startsWith("nr/")) {
            return true;
        }
        return false;
    }

    /**
     * Returns a sorted list of suggestions for tags.
     */
    private List<String> suggestTagNames() {
        if (targetWord.equals("t/")) {
            List<String> suggestions = logic.getAllTagNames().stream()
                    .sorted()
                    .collect(Collectors.toList());
            return suggestions;
        } else {
            String[] splitByPrefix = targetWord.split("/");
            String targetTag = splitByPrefix[1];
            List<String> suggestions = logic.getAllTagNames().stream()
                    .filter(t -> t.toLowerCase().startsWith(targetTag) && !t.toLowerCase().equals(targetTag))
                    .sorted()
                    .collect(Collectors.toList());
            return suggestions;
        }
    }

    /**
     * Returns a sorted list of suggestions for pet patient names.
     */
    private List<String> suggestPetPatientNames() {
        if (targetWord.equals("n/")) {
            List<String> suggestions = logic.getAllPetPatientNames().stream()
                    .sorted()
                    .collect(Collectors.toList());
            return suggestions;
        } else {
            String[] splitByPrefix = targetWord.split("/");
            String targetPetName = splitByPrefix[1];
            List<String> suggestions = logic.getAllPetPatientNames().stream()
                    .filter(pn -> pn.startsWith(targetPetName) && !pn.equals(targetPetName))
                    .sorted()
                    .collect(Collectors.toList());
            return suggestions;
        }
    }

    /**
     * Returns a sorted list of suggestions for Nric.
     */
    private List<String> suggestNrics() {
        if (targetWord.equals("nr/")) {
            List<String> suggestions = logic.getAllNric().stream()
                    .sorted()
                    .collect(Collectors.toList());
            return suggestions;

        } else {
            String[] splitByPrefix = targetWord.split("/");
            String targetNric = splitByPrefix[1].toUpperCase();
            List<String> suggestions = logic.getAllNric().stream()
                    .filter(n -> n.startsWith(targetNric))
                    .sorted()
                    .collect(Collectors.toList());
            return suggestions;
        }
    }

    /**
     * Returns a sorted list of suggestions for prefixes.
     */
    private List<String> suggestPrefixes() {
        List<String> suggestions = logic.getAllPrefixes().stream()
                .filter(p -> p.startsWith(targetWord) && !p.equals(targetWord))
                .sorted()
                .collect(Collectors.toList());
        return suggestions;
    }

    /**
     * Returns a sorted list of suggestions for options.
     */
    private List<String> suggestOptions() {
        List<String> suggestions = logic.getAllOptions().stream()
                .filter(o -> o.startsWith(targetWord) && !o.equals(targetWord))
                .sorted()
                .collect(Collectors.toList());
        return suggestions;
    }

    /**
     * Returns a sorted list of suggestions for command words.
     */
    private List<String> suggestCommandWords() {
        List<String> suggestions = logic.getAllCommandWords().stream()
                .filter(c -> c.startsWith(targetWord) && !c.equals(targetWord))
                .sorted()
                .collect(Collectors.toList());
        return suggestions;
    }
}
