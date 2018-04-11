package seedu.address.ui;

import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.google.common.eventbus.Subscribe;

import javafx.scene.control.TextField;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.logic.Logic;
import seedu.address.logic.parser.CliSyntax;

//@@author aquarinte
/**
 * Handles case-insensitive autocompletion of command line syntax,
 * and also some user input parameters: Nric, pet patient name, species, tags etc.
 */
public class Autocomplete {

    private static final Logger logger = LogsCenter.getLogger(Autocomplete.class);
    private static final int MAX_SUGGESTION_COUNT = CliSyntax.MAX_SYNTAX_SIZE;
    private static Autocomplete instance;
    private Logic logic;
    private String targetWord;
    private String[] words;
    private String option;
    private Set<String> tagSet;

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
        logic.setAttributesForPersonObjects();
        logic.setAttributesForPetPatientObjects();
        logic.setAttributesForAppointmentObjects();
    }

    /**
     * Returns a list of suggestions for autocomplete based on current user input.
     *
     * @param commandTextField Command box that holds user input.
     */
    public List<String> getSuggestions(TextField commandTextField) {
        int cursorPosition = commandTextField.getCaretPosition();
        // split string, but retain all whitespaces in array "words"
        words = commandTextField.getText(0, cursorPosition).split("((?<= )|(?= ))", -1);
        targetWord = words[words.length - 1].toLowerCase();
        setOption();

        if (words.length <= 2) {
            return getCommandWordSuggestions();
        }

        if (!targetWord.equals("")) {
            if (hasAddCommandReferNric() || hasEditCommandReferNric() || hasFindCommandReferNric()) {
                return getNricSuggestions();
            }

            if (hasReferenceToExistingPetPatientNames()) {
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
                return getPetPatientBloodTypeSuggestions();
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
     * Checks if command input {@code words} contains the "add" command with reference to existing persons' Nric,
     * to determine if autocomplete for persons' Nric is necessary.
     *
     * Returns false if the command input is to add a new person.
     */
    private boolean hasAddCommandReferNric() {
        // returns false if adding a new person.
        if (words[0].equals("add") && words[2].equals("-o")) {
            return false;
        }

        if (words[0].equals("add") && words[words.length - 3].equals("-o") && targetWord.startsWith("nr/")) {
            return true;
        }
        return false;
    }

    /**
     * Checks if command input {@code words} contains the "edit" command with reference to existing persons' Nric,
     * to determine if autocomplete for persons' Nric is necessary.
     */
    private boolean hasEditCommandReferNric() {
        if (words[0].equals("edit") && option.equals("-p") && targetWord.startsWith("nr/")) {
            return true;
        }
        return false;
    }

    /**
     * Checks if command input {@code words} contains the "find" command with reference to existing persons' Nric,
     * to determine if autocomplete for persons' Nric is necessary.
     */
    private boolean hasFindCommandReferNric() {
        if (words[0].equals("find") && option.equals("-o") && targetWord.startsWith("nr/")) {
            return true;
        }
        return false;
    }

    /**
     * Checks if command input {@code words} has reference to existing pet patients' names to determine
     * if autocomplete for pet patient names is necessary.
     *
     * Returns false if command input is adding a new pet patient.
     * Returns false if command input is adding a new owner, new pet patient, new appointment.
     * Returns false if command input is editing a pet patient's name.
     */
    private boolean hasReferenceToExistingPetPatientNames() {
        // add a new pet patient command format
        if (words[0].equals("add") && words[2].equals("-p") && targetWord.startsWith("n/")) {
            return false;
        }

        // adding a new owner, new pet patient, new appointment command format
        if (words[0].equals("add") && words[2].equals("-o") && words[words.length - 3].equals("-p")
                && targetWord.startsWith("n/")) {
            return false;
        }

        if (words[0].equals("edit") && option.equals("-p") && targetWord.startsWith("n/")) {
            return false;
        }

        if (words[words.length - 3].equals("-p") && targetWord.startsWith("n/")) {
            return true;
        }

        return false;
    }

    /**
     * Sets {@code option} based on the last option found in {@code words}.
     */
    private void setOption() {
        option = "nil";
        String commandBoxInput = String.join("", words);
        int index = commandBoxInput.lastIndexOf("-");

        if (index > -1 && (commandBoxInput.length() >= index + 2)) {
            option = commandBoxInput.substring(index, index + 2); // (inclusive, exclusive)
        }
    }

    /**
     * Returns a sorted list of suggestions for tags.
     * List size conforms to max size {@code MAX_SUGGESTION_COUNT}.
     */
    private List<String> getTagSuggestions() {
        setTagListBasedOnOption();
        if (targetWord.equals("t/")) {
            List<String> suggestions = tagSet
                    .stream()
                    .sorted(String::compareToIgnoreCase)
                    .limit(MAX_SUGGESTION_COUNT)
                    .collect(Collectors.toList());
            return suggestions;
        } else {
            String[] splitByPrefix = targetWord.split("/");
            String targetTag = splitByPrefix[1];
            List<String> suggestions = tagSet
                    .stream()
                    .filter(t -> t.toLowerCase().startsWith(targetTag) && !t.toLowerCase().equals(targetTag))
                    .sorted(String::compareToIgnoreCase)
                    .limit(MAX_SUGGESTION_COUNT)
                    .collect(Collectors.toList());
            return suggestions;
        }
    }

    /**
     * Sets {@code tagList} based on {@code option}.
     * -o option will set elements of {@code tagList} to be persons' tags.
     * -p option will set elements of {@code tagList} to be pet patients' tags.
     * -a option will set elements of {@code tagList} to be appointments' tags.
     */
    private void setTagListBasedOnOption() {
        switch(option) {
        case "-o":
            tagSet = logic.getAllPersonTags();
            break;
        case "-p":
            tagSet = logic.getAllPetPatientTags();
            break;
        case "-a":
            tagSet = logic.getAllAppointmentTags();
            break;
        default:
            tagSet = logic.getAllTagsInModel();
        }
    }

    /**
     * Returns a sorted list of suggestions for pet patient names.
     * List size conforms to max size {@code MAX_SUGGESTION_COUNT}.
     */
    private List<String> getPetPatientNameSuggestions() {
        if (targetWord.equals("n/")) {
            List<String> suggestions = logic.getAllPetPatientNames()
                    .stream()
                    .sorted(String::compareToIgnoreCase)
                    .limit(MAX_SUGGESTION_COUNT)
                    .collect(Collectors.toList());
            return suggestions;
        } else {
            String[] splitByPrefix = targetWord.split("/");
            String targetPetName = splitByPrefix[1];
            List<String> suggestions = logic.getAllPetPatientNames()
                    .stream()
                    .filter(pn -> pn.toLowerCase().startsWith(targetPetName) && !pn.toLowerCase().equals(targetPetName))
                    .sorted(String::compareToIgnoreCase)
                    .limit(MAX_SUGGESTION_COUNT)
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
                    .sorted(String::compareToIgnoreCase)
                    .limit(MAX_SUGGESTION_COUNT)
                    .collect(Collectors.toList());
            return suggestions;
        } else {
            String[] splitByPrefix = targetWord.split("/");
            String targetSpecie = splitByPrefix[1];
            List<String> suggestions = logic.getAllPetPatientSpecies()
                    .stream()
                    .filter(s -> s.toLowerCase().startsWith(targetSpecie) && !s.toLowerCase().equals(targetSpecie))
                    .sorted(String::compareToIgnoreCase)
                    .limit(MAX_SUGGESTION_COUNT)
                    .collect(Collectors.toList());
            return suggestions;
        }
    }

    /**
     * Returns a sorted list of suggestions for pet patient breeds.
     * List size conforms to max size {@code MAX_SUGGESTION_COUNT}.
     */
    private List<String> getPetPatientBreedSuggestions() {
        if (targetWord.equals("b/")) {
            List<String> suggestions = logic.getAllPetPatientBreeds()
                    .stream()
                    .sorted(String::compareToIgnoreCase)
                    .limit(MAX_SUGGESTION_COUNT)
                    .collect(Collectors.toList());
            return suggestions;
        } else {
            String[] splitByPrefix = targetWord.split("/");
            String targetBreed = splitByPrefix[1];
            List<String> suggestions = logic.getAllPetPatientBreeds()
                    .stream()
                    .filter(b -> b.toLowerCase().startsWith(targetBreed) && !b.toLowerCase().equals(targetBreed))
                    .sorted(String::compareToIgnoreCase)
                    .limit(MAX_SUGGESTION_COUNT)
                    .collect(Collectors.toList());
            return suggestions;
        }
    }

    /**
     * Returns a sorted list of suggestions for pet patient colours.
     * List size conforms to max size {@code MAX_SUGGESTION_COUNT}.
     */
    private List<String> getPetPatientColourSuggestions() {
        if (targetWord.equals("c/")) {
            List<String> suggestions = logic.getAllPetPatientColours()
                    .stream()
                    .sorted(String::compareToIgnoreCase)
                    .limit(MAX_SUGGESTION_COUNT)
                    .collect(Collectors.toList());
            return suggestions;
        } else {
            String[] splitByPrefix = targetWord.split("/");
            String targetPetColour = splitByPrefix[1];
            List<String> suggestions = logic.getAllPetPatientColours()
                    .stream()
                    .filter(c -> c.toLowerCase().startsWith(targetPetColour)
                            && !c.toLowerCase().equals(targetPetColour))
                    .sorted(String::compareToIgnoreCase)
                    .limit(MAX_SUGGESTION_COUNT)
                    .collect(Collectors.toList());
            return suggestions;
        }
    }

    /**
     * Returns a sorted list of suggestions for pet patient blood types.
     * List size conforms to max size {@code MAX_SUGGESTION_COUNT}.
     */
    private List<String> getPetPatientBloodTypeSuggestions() {
        if (targetWord.equals("bt/")) {
            List<String> suggestions = logic.getAllPetPatientBloodTypes()
                    .stream()
                    .sorted(String::compareToIgnoreCase)
                    .limit(MAX_SUGGESTION_COUNT)
                    .collect(Collectors.toList());
            return suggestions;
        } else {
            String[] splitByPrefix = targetWord.split("/");
            String targetPetBloodType = splitByPrefix[1];
            List<String> suggestions = logic.getAllPetPatientBloodTypes()
                    .stream()
                    .filter(bt -> bt.toLowerCase().startsWith(targetPetBloodType)
                            && !bt.toLowerCase().equals(targetPetBloodType))
                    .sorted(String::compareToIgnoreCase)
                    .limit(MAX_SUGGESTION_COUNT)
                    .collect(Collectors.toList());
            return suggestions;
        }
    }

    /**
     * Returns a sorted list of suggestions for Nric.
     * List size conforms to max size {@code MAX_SUGGESTION_COUNT}.
     */
    private List<String> getNricSuggestions() {
        if (targetWord.equals("nr/")) {
            List<String> suggestions = logic.getAllNric()
                    .stream()
                    .sorted()
                    .limit(MAX_SUGGESTION_COUNT)
                    .collect(Collectors.toList());
            return suggestions;

        } else {
            String[] splitByPrefix = targetWord.split("/");
            String targetNric = splitByPrefix[1].toUpperCase();
            List<String> suggestions = logic.getAllNric()
                    .stream()
                    .filter(n -> n.startsWith(targetNric) && !n.equals(targetNric))
                    .sorted()
                    .limit(MAX_SUGGESTION_COUNT)
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
