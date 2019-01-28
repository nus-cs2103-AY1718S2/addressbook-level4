package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PARAMETER_FORMAT;
import static seedu.address.logic.commands.AddCommand.MESSAGE_INVALID_NRIC;
import static seedu.address.logic.commands.AddCommand.MESSAGE_INVALID_PET_PATIENT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.BLOODTYPE_DESC_NERO;
import static seedu.address.logic.commands.CommandTestUtil.BREED_DESC_NERO;
import static seedu.address.logic.commands.CommandTestUtil.COLOUR_DESC_NERO;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_THREE;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NRIC_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_NERO;
import static seedu.address.logic.commands.CommandTestUtil.NRIC_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NRIC_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NRIC_DESC_CHARLIE;
import static seedu.address.logic.commands.CommandTestUtil.NRIC_DESC_DION;
import static seedu.address.logic.commands.CommandTestUtil.NRIC_DESC_ELIAS;
import static seedu.address.logic.commands.CommandTestUtil.NRIC_DESC_FION;
import static seedu.address.logic.commands.CommandTestUtil.OPTION_APPOINTMENT;
import static seedu.address.logic.commands.CommandTestUtil.OPTION_OWNER;
import static seedu.address.logic.commands.CommandTestUtil.OPTION_PET;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.REMARK_DESC_THREE;
import static seedu.address.logic.commands.CommandTestUtil.SPECIES_DESC_NERO;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_VACCINATION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BLOODTYPE_NERO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BREED_NERO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COLOUR_NERO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_NERO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_CHARLIE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_DION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_ELIAS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_FION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SPECIES_NERO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.TypicalAppointments.BOB_APP;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.HOON;
import static seedu.address.testutil.TypicalPersons.IDA;
import static seedu.address.testutil.TypicalPersons.KEYWORD_MATCHING_MEIER;
import static seedu.address.testutil.TypicalPetPatients.KARUPIN;
import static seedu.address.testutil.TypicalPetPatients.NERO;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.exceptions.ConcurrentAppointmentException;
import seedu.address.model.appointment.exceptions.DuplicateAppointmentException;
import seedu.address.model.appointment.exceptions.DuplicateDateTimeException;
import seedu.address.model.appointment.exceptions.PastAppointmentException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.exceptions.DuplicateNricException;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.petpatient.PetPatient;
import seedu.address.model.petpatient.PetPatientName;
import seedu.address.model.petpatient.exceptions.DuplicatePetPatientException;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.AppointmentUtil;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;
import seedu.address.testutil.PetPatientBuilder;
import seedu.address.testutil.PetPatientUtil;

public class AddCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void add() throws Exception {
        Model model = getModel();

        /* ------------------------ Perform add operations on the shown unfiltered list ----------------------------- */

        /* Case: add a person without tags to a non-empty address book, command with leading spaces and trailing spaces
         * -> added
         */
        Person toAdd = AMY;
        String command = "   " + AddCommand.COMMAND_WORD + "  " + OPTION_OWNER + "  " + NAME_DESC_AMY + "  "
                + PHONE_DESC_AMY + " " + EMAIL_DESC_AMY + "   " + ADDRESS_DESC_AMY + "   " + NRIC_DESC_AMY + " "
                + TAG_DESC_FRIEND + " ";
        assertCommandSuccess(command, toAdd);

        /* Case: undo adding Amy to the list -> Amy deleted */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding Amy to the list -> Amy added again */
        command = RedoCommand.COMMAND_WORD;
        model.addPerson(toAdd);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: add a person with all fields same as another person in the address book except name -> rejected */
        command = AddCommand.COMMAND_WORD + " " + OPTION_OWNER + NAME_DESC_BOB + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + NRIC_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_NRIC);

        /* Case: add a person with all fields same as another person in the address book except name and nric
        -> accepted */
        toAdd = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_AMY).withNric(VALID_NRIC_BOB).withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_WORD + " " + OPTION_OWNER + NAME_DESC_BOB + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + NRIC_DESC_BOB + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: add a person with all fields same as another person in the address book except phone and nric
        -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_AMY).withNric(VALID_NRIC_CHARLIE).withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_WORD + " " + OPTION_OWNER + NAME_DESC_AMY + PHONE_DESC_BOB + EMAIL_DESC_AMY
            + ADDRESS_DESC_AMY + NRIC_DESC_CHARLIE + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: add a person with all fields same as another person in the address book except email and nric
        -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_AMY).withNric(VALID_NRIC_DION).withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_WORD + " " + OPTION_OWNER + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_BOB
            + ADDRESS_DESC_AMY + NRIC_DESC_DION + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: add a person with all fields same as another person in the address book except address and nric
        -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_BOB).withNric(VALID_NRIC_ELIAS).withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_WORD + " " + OPTION_OWNER + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
            + ADDRESS_DESC_BOB + NRIC_DESC_ELIAS + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: add a person with all fields same as another person in the address book except NRIC -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
            .withAddress(VALID_ADDRESS_AMY).withNric(VALID_NRIC_FION).withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_WORD + " " + OPTION_OWNER + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
            + ADDRESS_DESC_AMY + NRIC_DESC_FION + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: add to empty address book -> added */
        deleteAllPersons();
        assertCommandSuccess(ALICE);

        /* Case: add a person with tags, command with parameters in random order -> added */
        toAdd = BOB;
        command = AddCommand.COMMAND_WORD + " " + OPTION_OWNER + TAG_DESC_FRIEND + PHONE_DESC_BOB + ADDRESS_DESC_BOB
            + NAME_DESC_BOB + NRIC_DESC_BOB + TAG_DESC_HUSBAND + EMAIL_DESC_BOB;
        assertCommandSuccess(command, toAdd);

        /* Case: add a person, missing tags -> added */
        assertCommandSuccess(HOON);


        //@@author aquarinte
        /* Case: add a pet patient without tags to a non-empty address book, command with leading spaces and
         * trailing spaces -> added
         */
        PetPatient toAddPet = NERO;
        Nric bobNric = BOB.getNric();
        command = "   " + AddCommand.COMMAND_WORD + "  " + OPTION_PET + "  " + NAME_DESC_NERO
                + "  " +  SPECIES_DESC_NERO + "  " + BREED_DESC_NERO + "  " +  COLOUR_DESC_NERO + "  "
                + BLOODTYPE_DESC_NERO + "  " + OPTION_OWNER + "  " + NRIC_DESC_BOB + "  ";
        assertCommandSuccess(command, toAddPet, bobNric);

        /* Case: add a pet patient, missing tags -> added */
        assertCommandSuccess(KARUPIN, KARUPIN.getOwner());

        /* Case: add a pet patient with all fields same as another pet patient in the address book except name
        -> added */
        toAddPet = new PetPatientBuilder().withName("Joseph").withSpecies(VALID_SPECIES_NERO)
                .withBreed(VALID_BREED_NERO).withColour(VALID_COLOUR_NERO).withBloodType(VALID_BLOODTYPE_NERO)
                .withTags().withOwnerNric(bobNric.toString()).build();
        command = AddCommand.COMMAND_WORD + OPTION_PET + " n/Joseph" + SPECIES_DESC_NERO + BREED_DESC_NERO
                + COLOUR_DESC_NERO + BLOODTYPE_DESC_NERO + OPTION_OWNER + NRIC_DESC_BOB;
        assertCommandSuccess(command, toAddPet, bobNric);

        /* Case: add a pet patient with all fields same as another pet patient in the address book except species
        -> added */
        toAddPet = new PetPatientBuilder().withName(VALID_NAME_NERO).withSpecies("Dog").withBreed(VALID_BREED_NERO)
                .withColour(VALID_COLOUR_NERO).withBloodType(VALID_BLOODTYPE_NERO).withTags()
                .withOwnerNric(bobNric.toString()).build();
        command = AddCommand.COMMAND_WORD + OPTION_PET + NAME_DESC_NERO + " s/Dog" + BREED_DESC_NERO
                + COLOUR_DESC_NERO + BLOODTYPE_DESC_NERO + OPTION_OWNER + NRIC_DESC_BOB;
        assertCommandSuccess(command, toAddPet, bobNric);

        /* Case: add a pet patient with all fields same as another pet patient in the address book except breed
        -> added */
        toAddPet = new PetPatientBuilder().withName(VALID_NAME_NERO).withSpecies(VALID_SPECIES_NERO)
                .withBreed("Maltese").withColour(VALID_COLOUR_NERO).withBloodType(VALID_BLOODTYPE_NERO).withTags()
                .withOwnerNric(bobNric.toString()).build();
        command = AddCommand.COMMAND_WORD + OPTION_PET + NAME_DESC_NERO + SPECIES_DESC_NERO + " b/Maltese"
                + COLOUR_DESC_NERO + BLOODTYPE_DESC_NERO + OPTION_OWNER + NRIC_DESC_BOB;
        assertCommandSuccess(command, toAddPet, bobNric);

        /* Case: add a pet patient with all fields same as another pet patient in the address book except colour
        -> added */
        toAddPet = new PetPatientBuilder().withName(VALID_NAME_NERO).withSpecies(VALID_SPECIES_NERO)
                .withBreed(VALID_BREED_NERO).withColour("silver").withBloodType(VALID_BLOODTYPE_NERO).withTags()
                .withOwnerNric(bobNric.toString()).build();
        command = AddCommand.COMMAND_WORD + OPTION_PET + NAME_DESC_NERO + SPECIES_DESC_NERO + BREED_DESC_NERO
                + " c/silver" + BLOODTYPE_DESC_NERO + OPTION_OWNER + NRIC_DESC_BOB;
        assertCommandSuccess(command, toAddPet, bobNric);

        /* Case: add a pet patient with all fields same as another pet patient in the address book except blood type
        -> added */
        toAddPet = new PetPatientBuilder().withName(VALID_NAME_NERO).withSpecies(VALID_SPECIES_NERO)
                .withBreed(VALID_BREED_NERO).withColour(VALID_COLOUR_NERO).withBloodType("A").withTags()
                .withOwnerNric(bobNric.toString()).build();
        command = AddCommand.COMMAND_WORD + OPTION_PET + NAME_DESC_NERO + SPECIES_DESC_NERO + BREED_DESC_NERO
                + COLOUR_DESC_NERO + " bt/A" + OPTION_OWNER + NRIC_DESC_BOB;
        assertCommandSuccess(command, toAddPet, bobNric);

        /* Case: add an appointment to a  non-empty address book, command with leading spaces and trailing spaces
        -> added */
        Appointment toAddAppt = BOB_APP;
        command = "   " + AddCommand.COMMAND_WORD + "  " + OPTION_APPOINTMENT + "  " + DATE_DESC_THREE
                + "  " +  REMARK_DESC_THREE + "  " + TAG_DESC_VACCINATION + "  " + OPTION_OWNER + "  "
                + NRIC_DESC_BOB + OPTION_PET + "  " + NAME_DESC_NERO + "  ";
        assertCommandSuccess(command, toAddAppt, bobNric, BOB_APP.getPetPatientName());
        //@author

        /* -------------------------- Perform add operation on the shown filtered list ------------------------------ */

        /* Case: filters the person list before adding -> added */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        assertCommandSuccess(IDA);

        /* ------------------------ Perform add operation while a person card is selected --------------------------- */

        /* Case: selects first card in the person list, add a person -> added, card selection remains unchanged */
        selectPerson(Index.fromOneBased(1));
        assertCommandSuccess(CARL);

        /* ----------------------------------- Perform invalid add operations --------------------------------------- */

        /* Case: add a duplicate person -> rejected */
        command = PersonUtil.getAddCommand(HOON);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_PERSON);

        /* Case: add a duplicate person except with different tags -> rejected */
        // "friends" is an existing tag used in the default model, see TypicalPersons#ALICE
        // This test will fail if a new tag that is not in the model is used, see the bug documented in
        // AddressBook#addPerson(Person)
        command = PersonUtil.getAddCommand(HOON) + " " + PREFIX_TAG.getPrefix() + "friends";
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_PERSON);

        /* Case: missing name -> rejected */
        command = AddCommand.COMMAND_WORD + " " + OPTION_OWNER + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + NRIC_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_PARAMETER_FORMAT, AddCommand.MESSAGE_ERROR_PERSON));

        /* Case: missing phone -> rejected */
        command = AddCommand.COMMAND_WORD + " " + OPTION_OWNER + NAME_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + NRIC_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_PARAMETER_FORMAT, AddCommand.MESSAGE_ERROR_PERSON));

        /* Case: missing email -> rejected */
        command = AddCommand.COMMAND_WORD + " " + OPTION_OWNER + NAME_DESC_AMY + PHONE_DESC_AMY + ADDRESS_DESC_AMY
                + NRIC_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_PARAMETER_FORMAT, AddCommand.MESSAGE_ERROR_PERSON));

        /* Case: missing address -> rejected */
        command = AddCommand.COMMAND_WORD + " " + OPTION_OWNER + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + NRIC_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_PARAMETER_FORMAT, AddCommand.MESSAGE_ERROR_PERSON));

        //@@author Robert-Peng
        /* Case: missing nric -> rejected */
        command = AddCommand.COMMAND_WORD + " " + OPTION_OWNER + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_PARAMETER_FORMAT, AddCommand.MESSAGE_ERROR_PERSON));
        //@@author

        /* Case: invalid keyword -> rejected */
        command = "adds " + PersonUtil.getPersonDetails(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid name -> rejected */
        command = AddCommand.COMMAND_WORD + " " + OPTION_OWNER + INVALID_NAME_DESC + PHONE_DESC_AMY
            + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + NRIC_DESC_AMY;
        assertCommandFailure(command, Name.MESSAGE_NAME_CONSTRAINTS);

        /* Case: invalid phone -> rejected */
        command = AddCommand.COMMAND_WORD + " " + OPTION_OWNER + NAME_DESC_AMY + INVALID_PHONE_DESC
            + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + NRIC_DESC_AMY;
        assertCommandFailure(command, Phone.MESSAGE_PHONE_CONSTRAINTS);

        /* Case: invalid email -> rejected */
        command = AddCommand.COMMAND_WORD + " " + OPTION_OWNER + NAME_DESC_AMY + PHONE_DESC_AMY
            + INVALID_EMAIL_DESC + ADDRESS_DESC_AMY + NRIC_DESC_AMY;
        assertCommandFailure(command, Email.MESSAGE_EMAIL_CONSTRAINTS);

        /* Case: invalid address -> rejected */
        command = AddCommand.COMMAND_WORD + " " + OPTION_OWNER + NAME_DESC_AMY + PHONE_DESC_AMY
            + EMAIL_DESC_AMY + INVALID_ADDRESS_DESC + NRIC_DESC_AMY;
        assertCommandFailure(command, Address.MESSAGE_ADDRESS_CONSTRAINTS);

        //@@author Robert-Peng
        /* Case: invalid nric -> rejected */
        command = AddCommand.COMMAND_WORD + " " + OPTION_OWNER + NAME_DESC_AMY + PHONE_DESC_AMY
            + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + INVALID_NRIC_DESC;
        assertCommandFailure(command, Nric.MESSAGE_NRIC_CONSTRAINTS);
        //@@author

        /* Case: invalid tag -> rejected */
        command = AddCommand.COMMAND_WORD + " " + OPTION_OWNER + NAME_DESC_AMY + PHONE_DESC_AMY
            + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + NRIC_DESC_AMY + INVALID_TAG_DESC;
        assertCommandFailure(command, Tag.MESSAGE_TAG_CONSTRAINTS);

        //@@author aquarinte
        /* Case: add a duplicate pet patient -> rejected */
        command = AddCommand.COMMAND_WORD + " " + OPTION_PET + NAME_DESC_NERO + SPECIES_DESC_NERO + BREED_DESC_NERO
                + COLOUR_DESC_NERO + BLOODTYPE_DESC_NERO + OPTION_OWNER + NRIC_DESC_BOB;
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_PET_PATIENT);

        /* Case: missing pet patient name -> rejected */
        command = AddCommand.COMMAND_WORD + " " + OPTION_PET + SPECIES_DESC_NERO + BREED_DESC_NERO
                + COLOUR_DESC_NERO + BLOODTYPE_DESC_NERO + OPTION_OWNER + NRIC_DESC_BOB;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_PARAMETER_FORMAT,
                AddCommand.MESSAGE_ERROR_PETPATIENT));

        /* Case: missing pet patient species -> rejected */
        command = AddCommand.COMMAND_WORD + " " + OPTION_PET + NAME_DESC_NERO + BREED_DESC_NERO
                + COLOUR_DESC_NERO + BLOODTYPE_DESC_NERO + OPTION_OWNER + NRIC_DESC_BOB;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_PARAMETER_FORMAT,
                AddCommand.MESSAGE_ERROR_PETPATIENT));

        /* Case: missing pet patient breed -> rejected */
        command = AddCommand.COMMAND_WORD + " " + OPTION_PET + NAME_DESC_NERO + SPECIES_DESC_NERO
                + COLOUR_DESC_NERO + BLOODTYPE_DESC_NERO + OPTION_OWNER + NRIC_DESC_BOB;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_PARAMETER_FORMAT,
                AddCommand.MESSAGE_ERROR_PETPATIENT));

        /* Case: missing pet patient colour -> rejected */
        command = AddCommand.COMMAND_WORD + " " + OPTION_PET + NAME_DESC_NERO + SPECIES_DESC_NERO
                + BREED_DESC_NERO + BLOODTYPE_DESC_NERO + OPTION_OWNER + NRIC_DESC_BOB;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_PARAMETER_FORMAT,
                AddCommand.MESSAGE_ERROR_PETPATIENT));

        /* Case: missing pet patient blood type -> rejected */
        command = AddCommand.COMMAND_WORD + " " + OPTION_PET + NAME_DESC_NERO + SPECIES_DESC_NERO
                + BREED_DESC_NERO + COLOUR_DESC_NERO + OPTION_OWNER + NRIC_DESC_BOB;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_PARAMETER_FORMAT,
                AddCommand.MESSAGE_ERROR_PETPATIENT));

        /* Case: missing option and owner's nric -> rejected */
        command = AddCommand.COMMAND_WORD + " " + OPTION_PET + NAME_DESC_NERO + SPECIES_DESC_NERO
                + BREED_DESC_NERO + COLOUR_DESC_NERO + BLOODTYPE_DESC_NERO;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing owner's nric -> rejected */
        command = AddCommand.COMMAND_WORD + " " + OPTION_PET + NAME_DESC_NERO + SPECIES_DESC_NERO
                + BREED_DESC_NERO + COLOUR_DESC_NERO + BLOODTYPE_DESC_NERO + OPTION_OWNER;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_PARAMETER_FORMAT,
                AddCommand.MESSAGE_MISSING_NRIC_PREFIX));

        /* Case: missing appointment date -> rejected */
        command = AddCommand.COMMAND_WORD + OPTION_APPOINTMENT
                + REMARK_DESC_THREE + TAG_DESC_VACCINATION + OPTION_OWNER + NRIC_DESC_BOB + OPTION_PET
                + NAME_DESC_NERO;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_PARAMETER_FORMAT,
                AddCommand.MESSAGE_ERROR_APPOINTMENT));

        /* Case: missing appointment remark -> rejected */
        command = AddCommand.COMMAND_WORD + OPTION_APPOINTMENT + DATE_DESC_THREE
                + TAG_DESC_VACCINATION + OPTION_OWNER + NRIC_DESC_BOB + OPTION_PET
                + NAME_DESC_NERO;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_PARAMETER_FORMAT,
                AddCommand.MESSAGE_ERROR_APPOINTMENT));

        /* Case: missing appointment tag -> rejected */
        command = AddCommand.COMMAND_WORD + OPTION_APPOINTMENT + DATE_DESC_THREE
                + REMARK_DESC_THREE + OPTION_OWNER + NRIC_DESC_BOB + OPTION_PET
                + NAME_DESC_NERO;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_PARAMETER_FORMAT,
                AddCommand.MESSAGE_ERROR_APPOINTMENT));

        /* Case: missing appointment's owner nric -> rejected */
        command = AddCommand.COMMAND_WORD + OPTION_APPOINTMENT + DATE_DESC_THREE
                + REMARK_DESC_THREE + TAG_DESC_VACCINATION + OPTION_OWNER + OPTION_PET
                + NAME_DESC_NERO;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_PARAMETER_FORMAT,
                AddCommand.MESSAGE_MISSING_NRIC_PREFIX));

        /* Case: missing appointment's pet patient name -> rejected */
        command = AddCommand.COMMAND_WORD + OPTION_APPOINTMENT + DATE_DESC_THREE
                + REMARK_DESC_THREE + TAG_DESC_VACCINATION + OPTION_OWNER + NRIC_DESC_BOB + OPTION_PET;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_PARAMETER_FORMAT,
                AddCommand.MESSAGE_MISSING_PET_PATIENT_NAME_PREFIX));


    }

    /**
     * Executes the {@code AddCommand} that adds {@code toAdd} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code AddCommand} with the details of
     * {@code toAdd}.<br>
     * 4. {@code Model}, {@code Storage}, {@code PersonListPanel} and {@code PetPatientListPanel} equal to the
     * corresponding components in the current model added with {@code toAdd}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(Appointment toAdd, Nric ownerNric, PetPatientName petPatientName)
            throws CommandException {
        assertCommandSuccess(AppointmentUtil.getAddCommand(toAdd, ownerNric, petPatientName), toAdd, ownerNric,
                petPatientName);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(PetPatient, Nric)}. Executes {@code command}
     * instead.
     * @see AddCommandSystemTest#assertCommandSuccess(PetPatient, Nric)
     */
    private void assertCommandSuccess(String command, Appointment toAdd, Nric ownerNric, PetPatientName petName)
            throws CommandException {
        Model expectedModel = getModel();
        Person owner = getModel().getPersonWithNric(ownerNric);
        PetPatient pet = getModel().getPetPatientWithNricAndName(ownerNric, petName);

        if (owner == null) {
            throw new CommandException(MESSAGE_INVALID_NRIC);
        }

        if (pet == null) {
            throw new CommandException(MESSAGE_INVALID_PET_PATIENT);
        }

        try {
            expectedModel.addAppointment(toAdd);
        } catch (DuplicateAppointmentException dae) {
            throw new IllegalArgumentException("toAdd already exists in the model.");
        } catch (DuplicateDateTimeException e) {
            throw new IllegalArgumentException("this date time already exists in the model.");
        } catch (ConcurrentAppointmentException c) {
            throw new IllegalArgumentException("there is another appointment which is concurrent");
        } catch (PastAppointmentException p) {
            throw new IllegalArgumentException("this date has already past.");
        }
        String expectedResultMessage = String.format(AddCommand.MESSAGE_SUCCESS_APPOINTMENT, toAdd, owner, pet);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Executes the {@code AddCommand} that adds {@code toAdd} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code AddCommand} with the details of
     * {@code toAdd}.<br>
     * 4. {@code Model}, {@code Storage}, {@code PersonListPanel} and {@code PetPatientListPanel} equal to the
     * corresponding components in the current model added with {@code toAdd}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(PetPatient toAdd, Nric ownerNric) throws CommandException {
        assertCommandSuccess(PetPatientUtil.getAddCommand(toAdd, ownerNric), toAdd, ownerNric);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(PetPatient, Nric)}. Executes {@code command}
     * instead.
     * @see AddCommandSystemTest#assertCommandSuccess(PetPatient, Nric)
     */
    private void assertCommandSuccess(String command, PetPatient toAdd, Nric ownerNric) throws CommandException {
        Model expectedModel = getModel();
        Person owner = getModel().getPersonWithNric(ownerNric);
        if (owner == null) {
            throw new CommandException(MESSAGE_INVALID_NRIC);
        }

        try {
            expectedModel.addPetPatient(toAdd);
        } catch (DuplicatePetPatientException dpe) {
            throw new IllegalArgumentException("toAdd already exists in the model.");
        }
        String expectedResultMessage = String.format(AddCommand.MESSAGE_SUCCESS_PETPATIENT, toAdd, owner);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    //@@author
    /**
     * Executes the {@code AddCommand} that adds {@code toAdd} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code AddCommand} with the details of
     * {@code toAdd}.<br>
     * 4. {@code Model}, {@code Storage} and {@code PersonListPanel} equal to the corresponding components in
     * the current model added with {@code toAdd}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(Person toAdd) {
        assertCommandSuccess(PersonUtil.getAddCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(Person)}. Executes {@code command}
     * instead.
     * @see AddCommandSystemTest#assertCommandSuccess(Person)
     */
    private void assertCommandSuccess(String command, Person toAdd) {
        Model expectedModel = getModel();
        try {
            expectedModel.addPerson(toAdd);
        } catch (DuplicatePersonException dpe) {
            throw new IllegalArgumentException("toAdd already exists in the model.");
        } catch (DuplicateNricException e) {
            throw new IllegalArgumentException("toAdd's NRIC already exists in the model.");
        }
        String expectedResultMessage = String.format(AddCommand.MESSAGE_SUCCESS_PERSON, toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Person)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Model}, {@code Storage} and {@code PersonListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see AddCommandSystemTest#assertCommandSuccess(String, Person)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Model}, {@code Storage} and {@code PersonListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
