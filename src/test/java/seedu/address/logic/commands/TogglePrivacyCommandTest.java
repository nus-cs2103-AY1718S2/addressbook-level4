package seedu.address.logic.commands;

//@@author lohtianwei
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static seedu.address.logic.commands.TogglePrivacyCommand.EditPersonPrivacy;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditPersonPrivacyBuilder;
import seedu.address.testutil.PersonBuilder;

public class TogglePrivacyCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void editPersonPrivacyTest() {
        EditPersonPrivacy epp = new EditPersonPrivacy();

        assertFalse(epp.anyNonNullField());

        EditPersonPrivacy eppBuilder = new EditPersonPrivacyBuilder().setAddressPrivate("false")
                .setEmailPrivate("false").setPhonePrivate("true").setRatingPrivate("false")
                .setRemarkPrivate("true").build();

        epp.setPrivateAddress(false);
        epp.setPrivateEmail(false);
        epp.setPrivatePhone(true);
        epp.setPrivateRating(false);
        epp.setPrivateRemark(true);

        assertEquals(eppBuilder.getPrivateAddress(), epp.getPrivateAddress());
        assertEquals(eppBuilder.getPrivateEmail(), epp.getPrivateEmail());
        assertEquals(eppBuilder.getPrivatePhone(), epp.getPrivatePhone());
        assertEquals(eppBuilder.getPrivateRating(), epp.getPrivateRating());
        assertEquals(eppBuilder.getPrivateRemark(), epp.getPrivateRemark());
    }

    @Test
    public void oneFieldToggledSuccess() throws Exception {
        Index indexLast = Index.fromOneBased(model.getFilteredPersonList().size());
        Person lastPerson = model.getFilteredPersonList().get(indexLast.getZeroBased());

        Person listedPerson = new PersonBuilder().withName(lastPerson.getName().toString())
                .withEmail(lastPerson.getEmail().toString()).withRating(lastPerson.getRating().toString())
                .withPhone(lastPerson.getPhone().toString()).build();

        listedPerson.getPhone().setPrivate(true);

        EditPersonPrivacy epp = new EditPersonPrivacyBuilder(listedPerson).setPhonePrivate("true").build();
        TogglePrivacyCommand togglePrivacyCommand = new TogglePrivacyCommand(indexLast, epp);
        togglePrivacyCommand.setData(model, new CommandHistory(), new UndoRedoStack());

        String expected = String.format(TogglePrivacyCommand.MESSAGE_SUCCESS, listedPerson);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(lastPerson, listedPerson);
    }
}
