package seedu.address.logic.commands;

//@@author lohtianwei
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class KeyCommandTest {

    private Model model;

    @Before
    public void start() {
        model = new ModelManager();
    }

    @Test
    public void checkKey() throws Exception {
        //checks that default lock state is false
        assertFalse(model.getLockState());

        //checks that key can lock MTM
        model.lockAddressBookModel();
        assertTrue(model.getLockState());

        //checks that key can unlock MTM
        model.unlockAddressBookModel();
        assertFalse(model.getLockState());

        //checks that toggling works
        model.lockAddressBookModel();
        model.unlockAddressBookModel();
        assertFalse(model.getLockState());

        model.unlockAddressBookModel();
        model.lockAddressBookModel();
        assertTrue(model.getLockState());
    }
}
