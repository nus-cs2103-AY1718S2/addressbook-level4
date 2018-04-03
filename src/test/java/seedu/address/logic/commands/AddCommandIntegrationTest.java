package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_CHARLIE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BLOODTYPE_NERO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BREED_NERO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COLOUR_NERO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_CHARLIE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_CHARLIE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_NERO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_CHARLIE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_DION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_CHARLIE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SPECIES_NERO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_CHECKUP;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.petpatient.PetPatient;
import seedu.address.model.petpatient.PetPatientName;
import seedu.address.testutil.AppointmentBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PetPatientBuilder;

//@@author aquarinte
/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private final String messageAddpetpatient = "New pet patient added: %1$s \nunder owner: %2$s";
    private final String messageAddappointment = "New appointment made: %1$s\nunder owner: %2$s\nfor pet patient: %3$s";
    private final String messageAddall = "New person added: %1$s\nNew pet patient added: %2$s\n"
            + "New appointment made: %3$s";

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() throws Exception {
        Person validPerson = new PersonBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addPerson(validPerson);

        assertCommandSuccess(prepareCommand(validPerson, model), model,
                String.format(AddCommand.MESSAGE_SUCCESS, validPerson), expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person personInList = model.getAddressBook().getPersonList().get(0);
        assertCommandFailure(prepareCommand(personInList, model), model, AddCommand.MESSAGE_DUPLICATE_PERSON);
    }

    //@@author aquarinte
    @Test
    public void execute_duplicateNric_throwsCommandException() {
        Nric nric = model.getAddressBook().getPersonList().get(0).getNric();
        Person duplicateNric = new PersonBuilder().withName("Red").withPhone("90002134").withEmail("red@gmail.com")
                .withAddress("24 Pallet Town").withNric(nric.toString()).build();
        assertCommandFailure(prepareCommand(duplicateNric, model), model, AddCommand.MESSAGE_DUPLICATE_NRIC);
    }

    @Test
    public void execute_newPetPatient_success() throws Exception {
        Person validPerson = model.getAddressBook().getPersonList().get(0);
        PetPatient validPetPatient = new PetPatientBuilder().withOwnerNric(validPerson.getNric().toString()).build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addPetPatient(validPetPatient);

        assertCommandSuccess(prepareCommand(validPetPatient, validPerson.getNric(), model), model,
                String.format(messageAddpetpatient, validPetPatient, validPerson), expectedModel);
    }

    @Test
    public void execute_duplicatePetPatient_throwsCommandException() {
        PetPatient duplicate = model.getAddressBook().getPetPatientList().get(0);
        assertCommandFailure(prepareCommand(duplicate, duplicate.getOwner(), model),
                model, AddCommand.MESSAGE_DUPLICATE_PET_PATIENT);
    }

    @Test
    public void execute_newPetPatientWithNricDoesNotExist_throwsCommandException() {
        // Nric does not exists in address book
        String nricDoesNotExist = VALID_NRIC_DION;
        PetPatient validPetPatient = new PetPatientBuilder().withOwnerNric(nricDoesNotExist).build();

        assertCommandFailure(prepareCommand(validPetPatient, validPetPatient.getOwner(), model),
                model, AddCommand.MESSAGE_INVALID_NRIC);
    }

    @Test
    public void execute_newAppointment_success() throws Exception {
        PetPatient existing = model.getAddressBook().getPetPatientList().get(0);
        Person owner = model.getPersonWithNric(existing.getOwner());
        Appointment appt = new AppointmentBuilder().withOwnerNric(owner.getNric().toString())
                .withPetPatientName(existing.getName().toString()).build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addAppointment(appt);

        assertCommandSuccess(prepareCommand(appt, existing.getOwner(), existing.getName(), model), model,
                String.format(messageAddappointment, appt, owner, existing), expectedModel);
    }

    @Test
    public void execute_newAppointmentWithNricDoesNotExists_throwsCommandException() {
        String nricDoesNotExist = VALID_NRIC_DION;
        String petPatientNameExists = model.getAddressBook().getPetPatientList().get(0).getName().toString();
        Appointment validAppt = new AppointmentBuilder().withOwnerNric(nricDoesNotExist)
                .withPetPatientName(petPatientNameExists).build();

        assertCommandFailure(prepareCommand(validAppt, validAppt.getOwnerNric(), validAppt.getPetPatientName(), model),
                model, AddCommand.MESSAGE_INVALID_NRIC);
    }

    @Test
    public void execute_newAppointmentWithPetPatientNameDoesNotExists_throwsCommandException() {
        String petNameDoesNotExist = VALID_NAME_NERO;
        String ownerNricExists = model.getAddressBook().getPersonList().get(0).getNric().toString();
        Appointment validAppt = new AppointmentBuilder().withOwnerNric(ownerNricExists)
                .withPetPatientName(petNameDoesNotExist).build();

        assertCommandFailure(prepareCommand(validAppt, validAppt.getOwnerNric(), validAppt.getPetPatientName(), model),
                model, AddCommand.MESSAGE_INVALID_PET_PATIENT);
    }

    @Test
    public void execute_duplicateAppointment_throwsCommandException() {
        Appointment duplicate = model.getAddressBook().getAppointmentList().get(0);
        assertCommandFailure(prepareCommand(duplicate, duplicate.getOwnerNric(), duplicate.getPetPatientName(), model),
                model, AddCommand.MESSAGE_DUPLICATE_APPOINTMENT);
    }

    @Test
    public void execute_duplicateDateTimeAppointment_throwsCommandException() {
        PetPatient existing = model.getAddressBook().getPetPatientList().get(0);
        Person owner = model.getPersonWithNric(existing.getOwner());
        String dupDateTime = model.getAddressBook().getAppointmentList().get(0).getFormattedLocalDateTime();

        Appointment appt = new AppointmentBuilder().withRemark(VALID_REMARK_ONE).withDateTime(dupDateTime)
                .withAppointmentTags(VALID_TAG_CHECKUP).withOwnerNric(owner.getNric().toString())
                .withPetPatientName(existing.getName().toString()).build();

        assertCommandFailure(prepareCommand(appt, owner.getNric(), existing.getName(), model),
                model, AddCommand.MESSAGE_DUPLICATE_DATETIME);
    }

    @Test
    public void execute_addAllNew_success() throws Exception {
        Person newPerson = new PersonBuilder().withName(VALID_NAME_CHARLIE).withPhone(VALID_PHONE_CHARLIE)
                .withEmail(VALID_EMAIL_CHARLIE).withAddress(VALID_ADDRESS_CHARLIE).withNric(VALID_NRIC_CHARLIE).build();

        PetPatient newPetPatient = new PetPatientBuilder().withName(VALID_NAME_NERO).withSpecies(VALID_SPECIES_NERO)
                .withBreed(VALID_BREED_NERO).withColour(VALID_COLOUR_NERO).withBloodType(VALID_BLOODTYPE_NERO)
                .withOwnerNric(newPerson.getNric().toString()).build();

        Appointment newAppt = new AppointmentBuilder().withDateTime(VALID_DATE_ONE).withRemark(VALID_REMARK_ONE)
                .withAppointmentTags(VALID_TAG_CHECKUP).withOwnerNric(newPerson.getNric().toString())
                .withPetPatientName(newPetPatient.getName().toString()).build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addPerson(newPerson);
        expectedModel.addPetPatient(newPetPatient);
        expectedModel.addAppointment(newAppt);

        assertCommandSuccess(prepareCommand(newPerson, newPetPatient, newAppt, model), model,
                String.format(messageAddall, newPerson, newPetPatient, newAppt), expectedModel);
    }

    /**
     * Generates a new {@code AddCommand} which upon execution, adds {@code person} into the {@code model}.
     */
    private AddCommand prepareCommand(Person person, Model model) {
        AddCommand command = new AddCommand(person);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Generates a new {@code AddCommand} which upon execution, adds {@code petpatient} into the {@code model}.
     */
    private AddCommand prepareCommand(PetPatient petPatient, Nric ownerNric, Model model) {
        AddCommand command = new AddCommand(petPatient, ownerNric);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Generates a new {@code AddCommand} which upon execution, adds {@code appointment} into the {@code model}.
     */
    private AddCommand prepareCommand(Appointment appt, Nric ownerNric, PetPatientName petPatientName, Model model) {
        AddCommand command = new AddCommand(appt, ownerNric, petPatientName);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Generates a new {@code AddCommand} which upon execution, adds {@code person}, {@code petpatient} and
     * {@code appointment} into the {@code model}.
     */
    private AddCommand prepareCommand(Person person, PetPatient petPatient, Appointment appt, Model model) {
        AddCommand command = new AddCommand(person, petPatient, appt);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
