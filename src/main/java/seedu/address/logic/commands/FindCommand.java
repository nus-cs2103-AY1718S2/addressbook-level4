package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.petpatient.PetPatient;

//@@author wynonaK
/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";
    public static final String COMMAND_ALIAS = "f";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all fields that matches any of"
            + " the specified option, prefixes & keywords (case-sensitive)"
            + " and displays them as a list with index numbers.\n"
            + "Parameters: OPTION PREFIX/KEYWORD [MORE_PREFIX/MORE_KEYWORDS]...\n"
            + "Accepted Options: -o (CONTACT-RELATED), -p (PET-PATIENT-RELATED)\n"
            + "Accepted Prefixes for Contacts: n/NAME, nr/NRIC, t/TAG\n"
            + "Accepted Prefixes for Pet Patient: n/NAME, s/SPECIES, b/BREED, c/COLOUR, bt/BLOODTYPE, t/TAG\n"
            + "Example: " + COMMAND_WORD + " -o n/alice bob charlie";

    private HashMap<String, String[]> hashMap;
    private int type = 0;

    public FindCommand(HashMap<String, String[]> hashMap) {
        this.hashMap = hashMap;
        if (hashMap.containsKey("ownerName")
                || hashMap.containsKey("ownerNric")
                || hashMap.containsKey("ownerTag")) {
            type = 1;
        } else if (hashMap.containsKey("petName")
                || hashMap.containsKey("petSpecies")
                || hashMap.containsKey("petBreed")
                || hashMap.containsKey("petColour")
                || hashMap.containsKey("petBloodType")
                || hashMap.containsKey("petTag")) {
            type = 2;
        }
    }

    @Override
    public CommandResult execute() throws CommandException {
        switch (type) {
        case 1:
            return findOwner();
        case 2:
            return findPetPatient();
        default:
            throw new CommandException(MESSAGE_USAGE);
        }
    }

    /**
     * Finds owners with given {@code predicate} in this {@code addressbook}.
     */
    private CommandResult findOwner() {
        Predicate<Person> finalPredicate = null;

        if (hashMap.containsKey("ownerName")) {
            String[] nameKeywords = hashMap.get("ownerName");
            Predicate<Person> namePredicate =  person -> Arrays.stream(nameKeywords)
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword));
            finalPredicate = namePredicate;
        }

        if (hashMap.containsKey("ownerNric")) {
            String[] nricKeywords = hashMap.get("ownerNric");
            Predicate<Person>  nricPredicate = person -> Arrays.stream(nricKeywords)
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getNric().toString(), keyword));
            if (finalPredicate == null) {
                finalPredicate = nricPredicate;
            } else {
                finalPredicate = finalPredicate.and(nricPredicate);
            }
        }

        if (hashMap.containsKey("ownerTag")) {
            String[] tagKeywords = hashMap.get("ownerTag");
            Predicate<Person> tagPredicate = person -> Arrays.stream(tagKeywords)
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getTagString(), keyword));
            if (finalPredicate == null) {
                finalPredicate = tagPredicate;
            } else {
                finalPredicate = finalPredicate.and(tagPredicate);
            }
        }

        model.updateFilteredPersonList(finalPredicate);
        updatePetListForOwner();
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size())
                + "\n"
                + getMessageForPetPatientListShownSummary(model.getFilteredPetPatientList().size()));
    }

    /**
     * Finds owners with given {@code predicate} in this {@code addressbook}.
     */
    private CommandResult findPetPatient() {
        Predicate<PetPatient> finalPredicate = null;

        if (hashMap.containsKey("petName")) {
            String[] nameKeywords = hashMap.get("petName");
            Predicate<PetPatient> namePredicate =  petPatient -> Arrays.stream(nameKeywords)
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(petPatient.getName().fullName, keyword));
            finalPredicate = namePredicate;
        }

        if (hashMap.containsKey("petSpecies")) {
            String[] speciesKeywords = hashMap.get("petSpecies");
            Predicate<PetPatient> speciesPredicate =  petPatient -> Arrays.stream(speciesKeywords)
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(petPatient.getSpecies().species, keyword));
            if (finalPredicate == null) {
                finalPredicate = speciesPredicate;
            } else {
                finalPredicate = finalPredicate.and(speciesPredicate);
            }
        }

        if (hashMap.containsKey("petBreed")) {
            String[] breedKeywords = hashMap.get("petBreed");
            Predicate<PetPatient> breedPredicate =  petPatient -> Arrays.stream(breedKeywords)
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(petPatient.getBreed().breed, keyword));
            if (finalPredicate == null) {
                finalPredicate = breedPredicate;
            } else {
                finalPredicate = finalPredicate.and(breedPredicate);
            }
        }

        if (hashMap.containsKey("petColour")) {
            String[] colourKeywords = hashMap.get("petColour");
            Predicate<PetPatient> colourPredicate =  petPatient -> Arrays.stream(colourKeywords)
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(petPatient.getColour().colour, keyword));
            if (finalPredicate == null) {
                finalPredicate = colourPredicate;
            } else {
                finalPredicate = finalPredicate.and(colourPredicate);
            }
        }

        if (hashMap.containsKey("petBloodType")) {
            String[] bloodTypeKeywords = hashMap.get("petBloodType");
            Predicate<PetPatient> bloodTypePredicate =  petPatient -> Arrays.stream(bloodTypeKeywords)
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(
                            petPatient.getBloodType().bloodType, keyword));
            if (finalPredicate == null) {
                finalPredicate = bloodTypePredicate;
            } else {
                finalPredicate = finalPredicate.and(bloodTypePredicate);
            }
        }

        if (hashMap.containsKey("petTag")) {
            String[] tagKeywords = hashMap.get("petTag");
            Predicate<PetPatient> tagPredicate = petPatient -> Arrays.stream(tagKeywords)
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(petPatient.getTagString(), keyword));
            if (finalPredicate == null) {
                finalPredicate = tagPredicate;
            } else {
                finalPredicate = finalPredicate.and(tagPredicate);
            }
        }

        model.updateFilteredPetPatientList(finalPredicate);
        updateOwnerListForPets();
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size())
                + "\n"
                + getMessageForPetPatientListShownSummary(model.getFilteredPetPatientList().size()));
    }

    /**
     * Updates the filtered pet list with the changed owners in this {@code addressbook}.
     */
    private void updatePetListForOwner() {
        List<String> nricKeywordsForPets = new ArrayList<>();
        for (Person person : model.getFilteredPersonList()) {
            nricKeywordsForPets.add(person.getNric().toString());
        }
        Predicate<PetPatient> petPatientNricPredicate =  petPatient -> nricKeywordsForPets.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(petPatient.getOwner().toString(), keyword));
        model.updateFilteredPetPatientList(petPatientNricPredicate);
    }

    /**
     * Updates the filtered person list with the changed pets in this {@code addressbook}.
     */
    private void updateOwnerListForPets() {
        List<String> nricKeywordsForOwner = new ArrayList<>();
        for (PetPatient petPatient : model.getFilteredPetPatientList()) {
            if (!nricKeywordsForOwner.contains(petPatient.getOwner().toString())) {
                nricKeywordsForOwner.add(petPatient.getOwner().toString());
            }
        }
        Predicate<Person> ownerNricPredicate =  person -> nricKeywordsForOwner.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getNric().toString(), keyword));
        model.updateFilteredPersonList(ownerNricPredicate);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && this.hashMap.equals(((FindCommand) other).hashMap)); // state check
    }
}
