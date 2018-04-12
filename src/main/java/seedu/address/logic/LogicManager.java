package seedu.address.logic;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Person;
import seedu.address.model.petpatient.PetPatient;
import seedu.address.model.tag.Tag;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final CommandHistory history;
    private final AddressBookParser addressBookParser;
    private final UndoRedoStack undoRedoStack;

    private CliSyntax cliSyntax;

    // person details
    private Set<String> nricInModel;
    private Set<String> phoneNumbersInModel;
    private Set<Tag> personTagsInModel;

    // pet patient details
    private Set<String> petPatientNamesInModel;
    private Set<String> speciesInModel;
    private Set<String> breedsInModel;
    private Set<String> coloursInModel;
    private Set<String> bloodTypesInModel;
    private Set<Tag> petPatientTagsInModel;

    // appointment details
    private Set<Tag> appointmentTagsInModel;

    public LogicManager(Model model) {
        this.model = model;
        history = new CommandHistory();
        addressBookParser = new AddressBookParser();
        undoRedoStack = new UndoRedoStack();
        cliSyntax = CliSyntax.getInstance();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        try {
            Command command = addressBookParser.parseCommand(commandText);
            command.setData(model, history, undoRedoStack);
            CommandResult result = command.execute();
            undoRedoStack.push(command);
            return result;
        } finally {
            history.add(commandText);
        }
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    @Override
    public ObservableList<PetPatient> getFilteredPetPatientList() {
        return model.getFilteredPetPatientList();
    }

    @Override
    public ObservableList<Appointment> getFilteredAppointmentList() {
        return model.getFilteredAppointmentList(); }

    @Override
    public ListElementPointer getHistorySnapshot() {
        return new ListElementPointer(history.getHistory());
    }

    //@@author aquarinte
    @Override
    public Set<String> getAllCommandWords() {
        return cliSyntax.getCommandWords();
    }

    @Override
    public Set<String> getAllPrefixes() {
        return cliSyntax.getPrefixes();
    }

    @Override
    public Set<String> getAllOptions() {
        return cliSyntax.getOptions();
    }

    @Override
    public Set<String> getAllNric() {
        return nricInModel;
    }

    @Override
    public Set<String> getAllPersonTags() {
        Set<String> personTags = personTagsInModel.stream()
                .map(pt -> pt.tagName)
                .collect(Collectors.toSet());
        return personTags;
    }

    @Override
    public Set<String> getAllPetPatientNames() {
        return petPatientNamesInModel;
    }

    @Override
    public Set<String> getAllPetPatientSpecies() {
        return speciesInModel;
    }

    @Override
    public Set<String> getAllPetPatientBreeds() {
        return breedsInModel;
    }

    @Override
    public Set<String> getAllPetPatientColours() {
        return coloursInModel;
    }

    @Override
    public Set<String> getAllPetPatientBloodTypes() {
        return bloodTypesInModel;
    }

    @Override
    public Set<String> getAllPetPatientTags() {
        Set<String> petPatientTags = petPatientTagsInModel.stream()
                .map(ppt -> ppt.tagName)
                .collect(Collectors.toSet());
        return petPatientTags;
    }

    @Override
    public Set<String> getAllAppointmentTags() {
        Set<String> appointmentTags = appointmentTagsInModel.stream()
                .map(a -> a.tagName)
                .collect(Collectors.toSet());
        return appointmentTags;
    }

    @Override
    public void setAttributesForPersonObjects() {
        nricInModel = new HashSet<>();
        phoneNumbersInModel = new HashSet<>();
        personTagsInModel = new HashSet<>();

        for (Person p : model.getAddressBook().getPersonList()) {
            nricInModel.add(p.getNric().toString());
            phoneNumbersInModel.add(p.getPhone().toString());
            personTagsInModel.addAll(p.getTags());
        }
    }

    @Override
    public void setAttributesForPetPatientObjects() {
        petPatientNamesInModel = new HashSet<>();
        speciesInModel = new HashSet<>();
        breedsInModel = new HashSet<>();
        coloursInModel = new HashSet<>();
        bloodTypesInModel = new HashSet<>();
        petPatientTagsInModel = new HashSet<>();

        for (PetPatient p : model.getAddressBook().getPetPatientList()) {
            petPatientNamesInModel.add(p.getName().toString());
            speciesInModel.add(p.getSpecies().toString());
            breedsInModel.add(p.getBreed().toString());
            coloursInModel.add(p.getColour().toString());
            bloodTypesInModel.add(p.getBloodType().toString());
            petPatientTagsInModel.addAll(p.getTags());
        }
    }

    @Override
    public void setAttributesForAppointmentObjects() {
        appointmentTagsInModel = new HashSet<>();
        for (Appointment a : model.getAddressBook().getAppointmentList()) {
            appointmentTagsInModel.addAll(a.getTag());
        }
    }

    @Override
    public Set<String> getAllTagsInModel() {
        Set<String> tagsInModel = new HashSet<>();
        for (Tag t : model.getTagList()) {
            tagsInModel.add(t.tagName);
        }
        return tagsInModel;
    }
}
