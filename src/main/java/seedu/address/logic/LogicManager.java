package seedu.address.logic;

import java.util.List;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.autocomplete.AutoCompleteManager;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.association.ClientOwnPet;
import seedu.address.model.client.Client;
import seedu.address.model.person.Person;
import seedu.address.model.pet.Pet;
import seedu.address.model.vettechnician.VetTechnician;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final CommandHistory history;
    private final AddressBookParser addressBookParser;
    private final UndoRedoStack undoRedoStack;
    private final AutoCompleteManager autoCompleteManager;
    private int currList = 0;

    public LogicManager(Model model) {
        this.model = model;
        history = new CommandHistory();
        addressBookParser = new AddressBookParser();
        undoRedoStack = new UndoRedoStack();
        autoCompleteManager = new AutoCompleteManager();
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
    public ObservableList<Client> getFilteredClientList() {
        return model.getFilteredClientList();
    }

    @Override
    public ObservableList<VetTechnician> getFilteredVetTechnicianList() {
        return model.getFilteredVetTechnicianList();
    }

    @Override
    public ObservableList<Pet> getFilteredPetList() {
        return model.getFilteredPetList();
    }

    @Override
    public ObservableList<ClientOwnPet> getClientPetAssociationList() {
        return model.getFilteredClientPetAssociationList();
    }

    @Override
    public ObservableList<Appointment> getFilteredAppointmentList() {
        return model.getFilteredAppointmentList();
    }

    @Override
    public ListElementPointer getHistorySnapshot() {
        return new ListElementPointer(history.getHistory());
    }

    @Override
    public List<String> getAutoCompleteCommands(String commandPrefix) {
        return autoCompleteManager.getAutoCompleteCommands(commandPrefix);
    }

    @Override
    public String getAutoCompleteNextParameter(String inputText) {
        return autoCompleteManager.getAutoCompleteNextMissingParameter(inputText);
    }

    public void setCurrentList(int currList) {
        this.currList = currList;
        model.setCurrentList(currList);
    }

    @Override
    public int getCurrentList() {
        return this.currList;
    }

    @Override
    public Client getClientDetails() {
        return model.getClientDetails();
    }

    @Override
    public ObservableList<Pet> getClientPetList() {
        return model.getClientPetList();
    }

    @Override
    public ObservableList<Appointment> getClientApptList() {
        return model.getClientApptList();
    }
}
