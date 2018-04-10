package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersonsAndAppointments.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.email.Template;
import seedu.address.testutil.TemplateBuilder;

//@@author ng95junwei

public class DeleteTemplateCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Template toAdd = new TemplateBuilder().build();

    @Test
    public void execute_existingTemplate_success() throws Exception {
        model.addTemplate(toAdd);
        String userInput = toAdd.getPurpose();
        String expectedMessage = String.format(DeleteTemplateCommand.MESSAGE_DELETE_TEMPLATE_SUCCESS,
                toAdd.getPurpose());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        DeleteTemplateCommand command = generateDeleteCommand(userInput, model);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);

    }

    @Test
    public void execute_nonexistingTemplate_failure() throws Exception {
        String userInput = "Test";
        String expectedMessage = Messages.MESSAGE_TEMPLATE_NOT_FOUND;

        DeleteTemplateCommand command = generateDeleteCommand(userInput, model);

        assertCommandFailure(command, model, expectedMessage);
    }

    @Test
    public void equals() {
        DeleteTemplateCommand command1 = new DeleteTemplateCommand("test");
        DeleteTemplateCommand command2 = new DeleteTemplateCommand("test");
        DeleteTemplateCommand command3 = new DeleteTemplateCommand("different");

        //same object - true
        assertTrue(command1.equals(command1));

        //same value - true
        assertTrue(command1.equals(command2));

        //different type - false
        assertFalse(command1.equals(1));

        //different values - false
        assertFalse(command1.equals(command3));

        //null - false
        assertFalse(command1.equals(null));
    }

    /**
     * helper function to return a command with data already set
     * @param userInput
     * @param model
     * @return
     */
    public DeleteTemplateCommand generateDeleteCommand(String userInput, Model model) {
        DeleteTemplateCommand command = new DeleteTemplateCommand(userInput);
        command.setData(model, new CommandHistory(), new UndoRedoStack());

        return command;
    }

}
