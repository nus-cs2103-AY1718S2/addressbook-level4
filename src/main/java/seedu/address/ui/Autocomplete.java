package seedu.address.ui;

import static seedu.address.logic.parser.CliSyntax.OPTION_APPOINTMENT;
import static seedu.address.logic.parser.CliSyntax.OPTION_OWNER;
import static seedu.address.logic.parser.CliSyntax.OPTION_PETPATIENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BLOODTYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BREED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COLOUR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SPECIES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.google.common.eventbus.Subscribe;

import javafx.scene.control.TextField;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.FindCommand;
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
    private String trimmedCommandInput;
    private String[] trimmedCommandInputArray;
    private String commandWord;
    private String option;
    private String targetWord;
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
     * Returns a list of suggestions for autocomplete based on user input (up to current caret position).
     *
     * @param commandTextField Command box that holds user input.
     */
    public List<String> getSuggestions(TextField commandTextField) {
        int cursorPosition = commandTextField.getCaretPosition();
        trimmedCommandInput = StringUtil.leftTrim(commandTextField.getText(0, cursorPosition));

        // split string, but retain all whitespaces in array "trimmedCommandInputArray"
        trimmedCommandInputArray = trimmedCommandInput.split("((?<= )|(?= ))", -1);

        commandWord = trimmedCommandInputArray[0];
        targetWord = trimmedCommandInputArray[trimmedCommandInputArray.length - 1].toLowerCase();
        setOption();

        if (trimmedCommandInputArray.length <= 2) {
            return getCommandWordSuggestions();
        }

        if (!targetWord.equals("") && hasOptionsAndPrefixes()) {
            if (hasAddCommandReferNric() || hasEditCommandReferNric() || hasFindCommandReferNric()) {
                return getNricSuggestions();
            }

            if (hasReferenceToExistingPetPatientNames()) {
                return getPetPatientNameSuggestions();
            }

            if (targetWord.startsWith(PREFIX_SPECIES.toString())) {
                return getPetPatientSpeciesSuggestions();
            }

            if (targetWord.startsWith(PREFIX_BREED.toString())) {
                return getPetPatientBreedSuggestions();
            }

            if (targetWord.startsWith(PREFIX_COLOUR.toString())) {
                return getPetPatientColourSuggestions();
            }

            if (targetWord.startsWith(PREFIX_BLOODTYPE.toString())) {
                return getPetPatientBloodTypeSuggestions();
            }

            if (targetWord.startsWith(PREFIX_TAG.toString())) {
                return getTagSuggestions();
            }

            if (targetWord.startsWith("-")) {
                return getOptionSuggestions();
            }

            return getPrefixSuggestions();

        } else {

            if (hasOptionsAndPrefixes()) {
                return getPrefixSuggestions();
            }
        }

        return new ArrayList<String>();
    }

    /**
     * Returns false if the command is one that does not require any options or prefixes in its syntax.
     */
    private boolean hasOptionsAndPrefixes() {
        if (logic.getCommandWordsWithOptionPrefix().contains(commandWord)) {
            return true;
        }

        return false;
    }

    /**
     * Checks if command input {@code trimmedCommandInputArray} contains the "add" command with reference to existing
     * persons' Nric, and determine if autocomplete for persons' Nric is necessary.
     *
     * Returns false if the command input is to add a new person.
     */
    private boolean hasAddCommandReferNric() {
        // adding a new owner will not have autocomplete for Nric
        if (commandWord.equals(AddCommand.COMMAND_WORD)
                && trimmedCommandInputArray[2].equals(OPTION_OWNER)) {
            return false;
        }

        if (commandWord.equals(AddCommand.COMMAND_WORD)
                && trimmedCommandInputArray[trimmedCommandInputArray.length - 3].equals(OPTION_OWNER)
                && targetWord.startsWith(PREFIX_NRIC.toString())) {
            return true;
        }
        return false;
    }

    /**
     * Checks if command input {@code trimmedCommandInputArray} contains the "edit" command with reference to existing
     * persons' Nric, and determine if autocomplete for persons' Nric is necessary.
     *
     * Returns true if editing the owner's nric of a pet patient.
     */
    private boolean hasEditCommandReferNric() {
        if (commandWord.equals(EditCommand.COMMAND_WORD)
                && option.equals(OPTION_PETPATIENT)
                && targetWord.startsWith(PREFIX_NRIC.toString())) {
            return true;
        }
        return false;
    }

    /**
     * Checks if command input {@code trimmedCommandInputArray} contains the "find" command with reference to existing
     * persons' Nric, and determine if autocomplete for persons' Nric is necessary.
     *
     * Returns true if finding a person by nric.
     */
    private boolean hasFindCommandReferNric() {
        if (commandWord.equals(FindCommand.COMMAND_WORD)
                && option.equals(OPTION_OWNER) && targetWord.startsWith(PREFIX_NRIC.toString())) {
            return true;
        }
        return false;
    }

    /**
     *Returns true if command input {@code trimmedCommandInput} is the syntax for adding a new appointment.
     */
    private boolean hasReferenceToExistingPetPatientNames() {
        final Pattern addNewAppointment = Pattern.compile(AddCommand.COMMAND_WORD + " -(a)+(?<apptInfo>.*)"
                + "-(o)+(?<ownerNric>.*)" + "-(p)+(?<petName>.*)");
        final Matcher matcherForNewAppt = addNewAppointment.matcher(trimmedCommandInput);

        if (matcherForNewAppt.matches()) {
            return true;
        }

        return false;
    }

    /**
     * Sets {@code option} based on the last option found in {@code trimmedCommandInput}.
     */
    private void setOption() {
        option = "nil";

        int o = trimmedCommandInput.lastIndexOf(OPTION_OWNER);
        int p = trimmedCommandInput.lastIndexOf(OPTION_PETPATIENT);
        int a = trimmedCommandInput.lastIndexOf(OPTION_APPOINTMENT);

        int index = (a > p) ? a : p;
        index = (index > o) ? index : o;

        if (index > -1 && (trimmedCommandInput.length() >= index + 2)) {
            option = trimmedCommandInput.substring(index, index + 2); // (inclusive, exclusive)
        }
    }

    /**
     * Returns a string that contains the parameter part of {@code targetWord}.
     */
    private String getParameter() {
        String[] splitByPrefix = targetWord.split("/");
        String parameter = splitByPrefix[1];
        return parameter;
    }

    /**
     * Returns a sorted list of suggestions for tags.
     * List size conforms to max size {@code MAX_SUGGESTION_COUNT}.
     */
    private List<String> getTagSuggestions() {
        setTagListBasedOnOption();
        if (targetWord.equals(PREFIX_TAG.toString())) {
            List<String> suggestions = tagSet
                    .stream()
                    .sorted(String::compareToIgnoreCase)
                    .limit(MAX_SUGGESTION_COUNT)
                    .collect(Collectors.toList());
            return suggestions;

        } else {
            String targetTag = getParameter();
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

        case OPTION_OWNER:
            tagSet = logic.getAllPersonTags();
            break;

        case OPTION_PETPATIENT:
            tagSet = logic.getAllPetPatientTags();
            break;

        case OPTION_APPOINTMENT:
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
        if (targetWord.equals(PREFIX_NAME.toString())) {
            List<String> suggestions = logic.getAllPetPatientNames()
                    .stream()
                    .sorted(String::compareToIgnoreCase)
                    .limit(MAX_SUGGESTION_COUNT)
                    .collect(Collectors.toList());
            return suggestions;

        } else {
            String targetPetName = getParameter();
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
        if (targetWord.equals(PREFIX_SPECIES.toString())) {
            List<String> suggestions = logic.getAllPetPatientSpecies()
                    .stream()
                    .sorted(String::compareToIgnoreCase)
                    .limit(MAX_SUGGESTION_COUNT)
                    .collect(Collectors.toList());
            return suggestions;

        } else {
            String targetSpecies = getParameter();
            List<String> suggestions = logic.getAllPetPatientSpecies()
                    .stream()
                    .filter(s -> s.toLowerCase().startsWith(targetSpecies) && !s.toLowerCase().equals(targetSpecies))
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
        if (targetWord.equals(PREFIX_BREED.toString())) {
            List<String> suggestions = logic.getAllPetPatientBreeds()
                    .stream()
                    .sorted(String::compareToIgnoreCase)
                    .limit(MAX_SUGGESTION_COUNT)
                    .collect(Collectors.toList());
            return suggestions;

        } else {
            String targetBreed = getParameter();
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
        if (targetWord.equals(PREFIX_COLOUR.toString())) {
            List<String> suggestions = logic.getAllPetPatientColours()
                    .stream()
                    .sorted(String::compareToIgnoreCase)
                    .limit(MAX_SUGGESTION_COUNT)
                    .collect(Collectors.toList());
            return suggestions;

        } else {
            String targetPetColour = getParameter();
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
        if (targetWord.equals(PREFIX_BLOODTYPE.toString())) {
            List<String> suggestions = logic.getAllPetPatientBloodTypes()
                    .stream()
                    .sorted(String::compareToIgnoreCase)
                    .limit(MAX_SUGGESTION_COUNT)
                    .collect(Collectors.toList());
            return suggestions;

        } else {
            String targetPetBloodType = getParameter();
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
        if (targetWord.equals(PREFIX_NRIC.toString())) {
            List<String> suggestions = logic.getAllNric()
                    .stream()
                    .sorted()
                    .limit(MAX_SUGGESTION_COUNT)
                    .collect(Collectors.toList());
            return suggestions;

        } else {
            String targetNric = getParameter().toUpperCase();
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
                .filter(p -> p.startsWith(targetWord) && !(StringUtil.removeDescription(p).equals(targetWord)))
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
                .filter(o -> o.startsWith(targetWord) && !(StringUtil.removeDescription(o).equals(targetWord)))
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
        logger.info(LogsCenter.getEventHandlingLogMessage(a, "Local data has changed,"
                + " update autocomplete data"));
    }

}
