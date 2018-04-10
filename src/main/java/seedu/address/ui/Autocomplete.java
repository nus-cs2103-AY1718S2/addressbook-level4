package seedu.address.ui;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.google.common.eventbus.Subscribe;

import javafx.scene.control.TextField;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.logic.Logic;

//@@author aquarinte
/**
 * Handles case-insensitive autocompletion of command line syntax
 * and also some user input parameters: Nric, pet patient name, species, tags etc.
 */
public class Autocomplete {

    private static final Logger logger = LogsCenter.getLogger(Autocomplete.class);
    private static Autocomplete instance;
    private Logic logic;
    private String targetWord;
    private String[] words;

    public static Autocomplete getInstance() {
        if (instance == null) {
            instance = new Autocomplete();
            EventsCenter.getInstance().registerHandler(instance);
        }
        return instance;
    }

    /**
     * Initalizes or updates data required for autocomplete.
     */
    public void init(Logic logic) {
        this.logic = logic;
        logic.processPersonsData();
        logic.processPetPatientsData();
        logic.processAppointmentsData();
    }

    /**
     * Finds suggestions for current user-input in commandTextField.
     */
    public List<String> getSuggestions(TextField commandTextField) {
        int cursorPosition = commandTextField.getCaretPosition();
        words = commandTextField.getText(0, cursorPosition).split("((?<= )|(?= ))", -1);
        targetWord = words[words.length - 1].toLowerCase();

        if (words.length <= 2) {
            return getCommandWordSuggestions();
        }

        if (!targetWord.equals("")) {
            if (hasAddCommandReferNric() || hasEditCommandReferNric() || hasFindCommandReferNric()) {
                return getNricSuggestions();
            }

            if (words[words.length - 3].equals("-p") && targetWord.startsWith("n/")) {
                return getPetPatientNameSuggestions();
            }

            if (targetWord.startsWith("s/")) {
                return getPetPatientSpeciesSuggestions();
            }

            if (targetWord.startsWith("b/")) {
                return getPetPatientBreedSuggestions();
            }

            if (targetWord.startsWith("c/")) {
                return getPetPatientColourSuggestions();
            }

            if (targetWord.startsWith("bt/")) {
                return getPetPatientBlootTypeSuggestions();
            }

            if (targetWord.startsWith("t/")) {
                return getTagSuggestions();
            }

            if (targetWord.startsWith("-")) {
                return getOptionSuggestions();
            }

            return getPrefixSuggestions();

        } else {
            return getPrefixSuggestions();
        }
    }

    /**
     * Checks if command input is the "add" command, and whether it has the form "-o nr/" at the end.
     */
    private boolean hasAddCommandReferNric() {
        if (words[0].equals("add") && words[words.length - 3].equals("-o") && targetWord.startsWith("nr/")) {
            return true;
        }
        return false;
    }

    /**
     * Checks if command input is the "edit -p" command, with the last word starting with "nr/".
     */
    private boolean hasEditCommandReferNric() {
        if (words[0].equals("edit") && words[2].equals("-p") && targetWord.startsWith("nr/")) {
            return true;
        }
        return false;
    }

    /**
     * Checks if command input is the "find -o" command, with the last word starting with "nr/".
     */
    private boolean hasFindCommandReferNric() {
        if (words[0].equals("find") && words[2].equals("-o") && targetWord.startsWith("nr/")) {
            return true;
        }
        return false;
    }

    /**
     * Checks if user input is the find command.
     */
    private boolean isFindCommand() {
        if (words[0].equals("find")) {
            return true;
        } else if (words[1].equals("find")) {
            return true;
        }
        return false;
    }

    /**
     * Returns a sorted list of suggestions for tags.
     */
    private List<String> getTagSuggestions() {
        if (targetWord.equals("t/")) {
            List<String> suggestions = logic.getAllTagsInModel()
                    .stream()
                    .sorted()
                    .collect(Collectors.toList());
            return suggestions;
        } else {
            String[] splitByPrefix = targetWord.split("/");
            String targetTag = splitByPrefix[1];
            List<String> suggestions = logic.getAllTagsInModel()
                    .stream()
                    .filter(t -> t.toLowerCase().startsWith(targetTag) && !t.toLowerCase().equals(targetTag))
                    .sorted()
                    .collect(Collectors.toList());
            return suggestions;
        }
    }

    /**
     * Returns a sorted list of suggestions for pet patient names.
     */
    private List<String> getPetPatientNameSuggestions() {
        if (targetWord.equals("n/")) {
            List<String> suggestions = logic.getAllPetPatientNames()
                    .stream()
                    .sorted()
                    .collect(Collectors.toList());
            return suggestions;
        } else {
            String[] splitByPrefix = targetWord.split("/");
            String targetPetName = splitByPrefix[1];
            List<String> suggestions = logic.getAllPetPatientNames()
                    .stream()
                    .filter(pn -> pn.toLowerCase().startsWith(targetPetName) && !pn.toLowerCase().equals(targetPetName))
                    .sorted()
                    .collect(Collectors.toList());
            return suggestions;
        }
    }

    /**
     * Returns a sorted list of suggestions for pet patient species.
     */
    private List<String> getPetPatientSpeciesSuggestions() {
        if (targetWord.equals("s/")) {
            List<String> suggestions = logic.getAllPetPatientSpecies()
                    .stream()
                    .sorted()
                    .collect(Collectors.toList());
            return suggestions;
        } else {
            String[] splitByPrefix = targetWord.split("/");
            String targetSpecie = splitByPrefix[1];
            List<String> suggestions = logic.getAllPetPatientSpecies()
                    .stream()
                    .filter(s -> s.toLowerCase().startsWith(targetSpecie) && !s.toLowerCase().equals(targetSpecie))
                    .sorted()
                    .collect(Collectors.toList());
            return suggestions;
        }
    }

    /**
     * Returns a sorted list of suggestions for pet patient breeds.
     */
    private List<String> getPetPatientBreedSuggestions() {
        if (targetWord.equals("b/")) {
            List<String> suggestions = logic.getAllPetPatientBreeds()
                    .stream()
                    .sorted()
                    .collect(Collectors.toList());
            return suggestions;
        } else {
            String[] splitByPrefix = targetWord.split("/");
            String targetBreed = splitByPrefix[1];
            List<String> suggestions = logic.getAllPetPatientBreeds()
                    .stream()
                    .filter(b -> b.toLowerCase().startsWith(targetBreed) && !b.toLowerCase().equals(targetBreed))
                    .sorted()
                    .collect(Collectors.toList());
            return suggestions;
        }
    }

    /**
     * Returns a sorted list of suggestions for pet patient colours.
     */
    private List<String> getPetPatientColourSuggestions() {
        if (targetWord.equals("c/")) {
            List<String> suggestions = logic.getAllPetPatientColours()
                    .stream()
                    .sorted()
                    .collect(Collectors.toList());
            return suggestions;
        } else {
            String[] splitByPrefix = targetWord.split("/");
            String targetPetColour = splitByPrefix[1];
            List<String> suggestions = logic.getAllPetPatientColours()
                    .stream()
                    .filter(c -> c.toLowerCase().startsWith(targetPetColour)
                            && !c.toLowerCase().equals(targetPetColour))
                    .sorted()
                    .collect(Collectors.toList());
            return suggestions;
        }
    }

    /**
     * Returns a sorted list of suggestions for pet patient blood types.
     */
    private List<String> getPetPatientBlootTypeSuggestions() {
        if (targetWord.equals("bt/")) {
            List<String> suggestions = logic.getAllPetPatientBloodTypes()
                    .stream()
                    .sorted()
                    .collect(Collectors.toList());
            return suggestions;
        } else {
            String[] splitByPrefix = targetWord.split("/");
            String targetPetBloodType = splitByPrefix[1];
            List<String> suggestions = logic.getAllPetPatientBloodTypes()
                    .stream()
                    .filter(bt -> bt.toLowerCase().startsWith(targetPetBloodType)
                            && !bt.toLowerCase().equals(targetPetBloodType))
                    .sorted()
                    .collect(Collectors.toList());
            return suggestions;
        }
    }

    /**
     * Returns a sorted list of suggestions for Nric.
     */
    private List<String> getNricSuggestions() {
        if (targetWord.equals("nr/")) {
            List<String> suggestions = logic.getAllNric()
                    .stream()
                    .sorted()
                    .collect(Collectors.toList());
            return suggestions;

        } else {
            String[] splitByPrefix = targetWord.split("/");
            String targetNric = splitByPrefix[1].toUpperCase();
            List<String> suggestions = logic.getAllNric()
                    .stream()
                    .filter(n -> n.startsWith(targetNric) && !n.equals(targetNric))
                    .sorted()
                    .collect(Collectors.toList());
            return suggestions;
        }
    }

    /**
     * Returns a sorted list of suggestions for prefixes.
     */
    private List<String> getPrefixSuggestions() {
        List<String> suggestions = logic.getAllPrefixes()
                .stream()
                .filter(p -> p.startsWith(targetWord) && !p.equals(targetWord))
                .sorted()
                .collect(Collectors.toList());
        return suggestions;
    }

    /**
     * Returns a sorted list of suggestions for options.
     */
    private List<String> getOptionSuggestions() {
        List<String> suggestions = logic.getAllOptions()
                .stream()
                .filter(o -> o.startsWith(targetWord) && !o.equals(targetWord))
                .sorted()
                .collect(Collectors.toList());
        return suggestions;
    }

    /**
     * Returns a sorted list of suggestions for command words.
     */
    private List<String> getCommandWordSuggestions() {
        List<String> suggestions = logic.getAllCommandWords()
                .stream()
                .filter(c -> c.startsWith(targetWord) && !c.equals(targetWord))
                .sorted()
                .collect(Collectors.toList());
        return suggestions;
    }

    @Subscribe
    public void handleAddressBookChangedEvent(AddressBookChangedEvent a) {
        init(this.logic);
        logger.info(LogsCenter.getEventHandlingLogMessage(a, "Local data changed,"
                + " update autocomplete data"));

    }

}
