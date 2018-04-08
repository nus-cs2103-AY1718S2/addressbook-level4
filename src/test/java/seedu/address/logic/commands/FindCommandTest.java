package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.commons.core.Messages.MESSAGE_PET_PATIENTS_LISTED_OVERVIEW;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import org.junit.Test;

import seedu.address.commons.util.StringUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.petpatient.PetPatient;

//@@author wynonaK
/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        Predicate<Person> firstPredicate = forPerson -> Collections.singletonList("first")
                .stream().anyMatch(keyword -> StringUtil.containsWordIgnoreCase(forPerson.getName().fullName, keyword));
        Predicate<Person> secondPredicate = forPerson -> Collections.singletonList("second")
                .stream().anyMatch(keyword -> StringUtil.containsWordIgnoreCase(forPerson.getName().fullName, keyword));

        FindCommand findFirstCommand = new FindCommand(firstPredicate);
        FindCommand findSecondCommand = new FindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroNameKeywords_noPersonFound() throws CommandException {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0)
                + "\n"
                + String.format(MESSAGE_PET_PATIENTS_LISTED_OVERVIEW, 0);
        FindCommand command = preparePersonNameCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_zeroNricKeywords_noPersonFound() throws CommandException {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0)
                + "\n"
                + String.format(MESSAGE_PET_PATIENTS_LISTED_OVERVIEW, 0);
        FindCommand command = preparePersonNricCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_zeroTagKeywords_noPersonFound() throws CommandException {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0)
                + "\n"
                + String.format(MESSAGE_PET_PATIENTS_LISTED_OVERVIEW, 0);
        FindCommand command = preparePersonTagCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_zeroNameKeywords_noPetPatientFound() throws CommandException {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0)
                + "\n"
                + String.format(MESSAGE_PET_PATIENTS_LISTED_OVERVIEW, 0);
        FindCommand command = preparePetNameCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_zeroSpeciesKeywords_noPetPatientFound() throws CommandException {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0)
                + "\n"
                + String.format(MESSAGE_PET_PATIENTS_LISTED_OVERVIEW, 0);
        FindCommand command = preparePetSpeciesCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_zeroBreedKeywords_noPetPatientFound() throws CommandException {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0)
                + "\n"
                + String.format(MESSAGE_PET_PATIENTS_LISTED_OVERVIEW, 0);
        FindCommand command = preparePetBreedCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_zeroColorKeywords_noPetPatientFound() throws CommandException {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0)
                + "\n"
                + String.format(MESSAGE_PET_PATIENTS_LISTED_OVERVIEW, 0);
        FindCommand command = preparePetColorCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_zeroBloodTypeKeywords_noPetPatientFound() throws CommandException {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0)
                + "\n"
                + String.format(MESSAGE_PET_PATIENTS_LISTED_OVERVIEW, 0);
        FindCommand command = preparePetBloodTypeCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_zeroTagKeywords_noPetPatientFound() throws CommandException {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0)
                + "\n"
                + String.format(MESSAGE_PET_PATIENTS_LISTED_OVERVIEW, 0);
        FindCommand command = preparePetTagCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_nameKeyword_personsFound() throws CommandException {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1)
                + "\n"
                + String.format(MESSAGE_PET_PATIENTS_LISTED_OVERVIEW, 0);
        FindCommand command = preparePersonNameCommand("Kurz");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL));
    }

    @Test
    public void execute_multipleNameKeywords_multiplePersonsFound() throws CommandException {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3)
                + "\n"
                + String.format(MESSAGE_PET_PATIENTS_LISTED_OVERVIEW, 0);
        FindCommand command = preparePersonNameCommand("Kurz Elle Kunz");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, ELLE, FIONA));
    }

    @Test
    public void execute_nricKeyword_personFound() throws CommandException {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1)
                + "\n"
                + String.format(MESSAGE_PET_PATIENTS_LISTED_OVERVIEW, 0);
        FindCommand command = preparePersonNricCommand("F2345678U");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL));
    }

    @Test
    public void execute_multipleNricKeywords_multiplePersonsFound() throws CommandException {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3)
                + "\n"
                + String.format(MESSAGE_PET_PATIENTS_LISTED_OVERVIEW, 0);
        FindCommand command = preparePersonNricCommand("F2345678U T0120956W S0156956W");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, ELLE, FIONA));
    }

    @Test
    public void execute_tagKeyword_personFound() throws CommandException {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1)
                + "\n"
                + String.format(MESSAGE_PET_PATIENTS_LISTED_OVERVIEW, 2);
        FindCommand command = preparePersonTagCommand("owesMoney");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(BENSON));
    }

    @Test
    public void execute_multipleTagKeyword_multiplePersonsFound() throws CommandException {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 7)
                + "\n"
                + String.format(MESSAGE_PET_PATIENTS_LISTED_OVERVIEW, 3);
        FindCommand command = preparePersonTagCommand("friends owesMoney");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }

    @Test
    public void execute_petNameKeyword_personFoundForPet() throws CommandException {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1)
                + "\n"
                + String.format(MESSAGE_PET_PATIENTS_LISTED_OVERVIEW, 1);
        FindCommand command = preparePetNameCommand("Joker");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(BENSON));
    }

    @Test
    public void execute_multipleNameKeyword_multiplePersonsFoundForPet() throws CommandException {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2)
                + "\n"
                + String.format(MESSAGE_PET_PATIENTS_LISTED_OVERVIEW, 2);
        FindCommand command = preparePetNameCommand("Jewel Joker");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(ALICE, BENSON));
    }

    @Test
    public void execute_petSpeciesKeyword_personFoundForPetDog() throws CommandException {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1)
                + "\n"
                + String.format(MESSAGE_PET_PATIENTS_LISTED_OVERVIEW, 1);
        FindCommand command = preparePetSpeciesCommand("Dog");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(BENSON));
    }

    @Test
    public void execute_petSpeciesKeyword_multiplePersonFoundForPetCat() throws CommandException {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2)
                + "\n"
                + String.format(MESSAGE_PET_PATIENTS_LISTED_OVERVIEW, 2);
        FindCommand command = preparePetSpeciesCommand("Cat");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(ALICE, BENSON));
    }

    @Test
    public void execute_multiplePetSpeciesKeyword_multiplePersonFoundForPet() throws CommandException {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2)
                + "\n"
                + String.format(MESSAGE_PET_PATIENTS_LISTED_OVERVIEW, 3);
        FindCommand command = preparePetSpeciesCommand("Dog Cat");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(ALICE, BENSON));
    }

    @Test
    public void execute_breedKeyword_personFoundForPet() throws CommandException {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1)
                + "\n"
                + String.format(MESSAGE_PET_PATIENTS_LISTED_OVERVIEW, 1);
        FindCommand command = preparePetBreedCommand("Golden Retriever");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(BENSON));
    }

    @Test
    public void execute_multipleBreedKeyword_multiplePersonFoundForPet() throws CommandException {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2)
                + "\n"
                + String.format(MESSAGE_PET_PATIENTS_LISTED_OVERVIEW, 3);
        FindCommand command = preparePetBreedCommand("Persian Ragdoll Golden Retriever Domestic Shorthair");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(ALICE, BENSON));
    }

    @Test
    public void execute_colorKeyword_personFoundForPet() throws CommandException {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1)
                + "\n"
                + String.format(MESSAGE_PET_PATIENTS_LISTED_OVERVIEW, 1);
        FindCommand command = preparePetColorCommand("Brown");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(BENSON));
    }

    @Test
    public void execute_multiplePetColorKeyword_multiplePersonFoundForPet() throws CommandException {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2)
                + "\n"
                + String.format(MESSAGE_PET_PATIENTS_LISTED_OVERVIEW, 3);
        FindCommand command = preparePetColorCommand("Calico Brown Golden");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(ALICE, BENSON));
    }

    @Test
    public void execute_bloodTypeKeyword_personFoundForPet() throws CommandException {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1)
                + "\n"
                + String.format(MESSAGE_PET_PATIENTS_LISTED_OVERVIEW, 1);
        FindCommand command = preparePetBloodTypeCommand("AB");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(ALICE));
    }

    @Test
    public void execute_multipleBloodTypeKeyword_multiplePersonFoundForPet() throws CommandException {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2)
                + "\n"
                + String.format(MESSAGE_PET_PATIENTS_LISTED_OVERVIEW, 3);
        FindCommand command = preparePetBloodTypeCommand("AB A DEA 4+");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(ALICE, BENSON));
    }

    @Test
    public void execute_tagKeyword_personFoundForPet() throws CommandException {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1)
                + "\n"
                + String.format(MESSAGE_PET_PATIENTS_LISTED_OVERVIEW, 1);
        FindCommand command = preparePetTagCommand("3legged");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(BENSON));
    }

    @Test
    public void execute_multipleTagKeyword_multiplePersonsFoundForPets() throws CommandException {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2)
                + "\n"
                + String.format(MESSAGE_PET_PATIENTS_LISTED_OVERVIEW, 2);
        FindCommand command = preparePetTagCommand("Depression 3Legged");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(ALICE, BENSON));
    }

    /**
     * Parses {@code userInput} into a {@code FindCommand}.
     */
    private FindCommand preparePersonNameCommand(String userInput) {
        Predicate<Person> predicate = forPerson -> Arrays.asList(userInput.split("\\s+"))
                .stream().anyMatch(keyword -> StringUtil.containsWordIgnoreCase(forPerson.getName().fullName, keyword));
        FindCommand command =
                new FindCommand(predicate);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Parses {@code userInput} into a {@code FindCommand}.
     */
    private FindCommand preparePersonNricCommand(String userInput) {
        Predicate<Person> predicate = forPerson -> Arrays.asList(userInput.split("\\s+"))
                .stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(forPerson.getNric().toString(), keyword));
        FindCommand command =
                new FindCommand(predicate);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Parses {@code userInput} into a {@code FindCommand}.
     */
    private FindCommand preparePersonTagCommand(String userInput) {
        Predicate<Person> predicate = forPerson -> Arrays.asList(userInput.split("\\s+"))
                .stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(forPerson.getTagString(), keyword));
        FindCommand command =
                new FindCommand(predicate);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Parses {@code userInput} into a {@code FindCommand}.
     */
    private FindCommand preparePetNameCommand(String userInput) {
        Predicate<PetPatient> predicate = forPetPatient -> Arrays.asList(userInput.split("\\s+"))
                .stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(forPetPatient.getName().fullName, keyword));
        FindCommand command =
                new FindCommand(predicate, 2);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Parses {@code userInput} into a {@code FindCommand}.
     */
    private FindCommand preparePetSpeciesCommand(String userInput) {
        Predicate<PetPatient> predicate = forPetPatient -> Arrays.asList(userInput.split("\\s+"))
                .stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(forPetPatient.getSpecies().species, keyword));
        FindCommand command =
                new FindCommand(predicate, 2);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Parses {@code userInput} into a {@code FindCommand}.
     */
    private FindCommand preparePetBreedCommand(String userInput) {
        Predicate<PetPatient> predicate = forPetPatient -> Arrays.asList(userInput.split("\\s+"))
                .stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(forPetPatient.getBreed().breed, keyword));
        FindCommand command =
                new FindCommand(predicate, 2);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Parses {@code userInput} into a {@code FindCommand}.
     */
    private FindCommand preparePetColorCommand(String userInput) {
        Predicate<PetPatient> predicate = forPetPatient -> Arrays.asList(userInput.split("\\s+"))
                .stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(forPetPatient.getColour().colour, keyword));
        FindCommand command =
                new FindCommand(predicate, 2);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Parses {@code userInput} into a {@code FindCommand}.
     */
    private FindCommand preparePetBloodTypeCommand(String userInput) {
        Predicate<PetPatient> predicate = forPetPatient -> Arrays.asList(userInput.split("\\s+"))
                .stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(forPetPatient.getBloodType().bloodType, keyword));
        FindCommand command =
                new FindCommand(predicate, 2);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Parses {@code userInput} into a {@code FindCommand}.
     */
    private FindCommand preparePetTagCommand(String userInput) {
        Predicate<PetPatient> predicate = forPetPatient -> Arrays.asList(userInput.split("\\s+"))
                .stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(forPetPatient.getTagString(), keyword));
        FindCommand command =
                new FindCommand(predicate, 2);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<Person>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(FindCommand command, String expectedMessage, List<Person> expectedList)
            throws CommandException {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }
}
