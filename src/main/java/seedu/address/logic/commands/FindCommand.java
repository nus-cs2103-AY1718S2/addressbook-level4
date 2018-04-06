package seedu.address.logic.commands;

import java.util.ArrayList;
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
            + "Example: " + COMMAND_WORD + "-o n/alice bob charlie";

    private Predicate<Person> personPredicate = null;
    private Predicate<PetPatient> petPatientPredicate = null;
    private int type = 0;

    public FindCommand(Predicate<Person> personPredicate) {
        this.personPredicate = personPredicate;
        type = 1;
    }

    public FindCommand(Predicate<PetPatient> petPatientPredicate, int petPatientIndicator) {
        this.petPatientPredicate = petPatientPredicate;
        type = petPatientIndicator;
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
        model.updateFilteredPersonList(personPredicate);
        updatePetListForOwner();
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size())
                + "\n"
                + getMessageForPetPatientListShownSummary(model.getFilteredPetPatientList().size()));
    }

    /**
     * Finds owners with given {@code predicate} in this {@code addressbook}.
     */
    private CommandResult findPetPatient() {
        model.updateFilteredPetPatientList(petPatientPredicate);
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
                && this.personPredicate.equals(((FindCommand) other).personPredicate)); // state check
    }
}
