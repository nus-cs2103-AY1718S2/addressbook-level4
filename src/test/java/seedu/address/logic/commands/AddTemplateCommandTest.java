package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import javax.jws.WebParam;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.email.Template;
import seedu.address.testutil.TemplateBuilder;

//@@author ng95junwei
public class AddTemplateCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullAppointment_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddTemplateCommand(null);
    }

    @Test
    public void execute_successful_addTemplate() throws Exception{
        Model modelStub = new ModelManager();
        Template validTemplate = new TemplateBuilder().build();
        int countBefore = modelStub.getFilteredTemplateList().size();
        CommandResult result = generateAddTemplateCommand(validTemplate, modelStub).execute();
        assertEquals(
                String.format(AddTemplateCommand.MESSAGE_SUCCESS, validTemplate), result.feedbackToUser
        );
        assertEquals(countBefore + 1, modelStub.getFilteredTemplateList().size());
    }

    @Test
    public void execute_duplicateTemplate_throwsCommandException() throws Exception{
        Model modelStub = new ModelManager();
        Template validTemplate = new TemplateBuilder().build();

        thrown.expect(CommandException.class);

        generateAddTemplateCommand(validTemplate, modelStub).execute();
        generateAddTemplateCommand(validTemplate, modelStub).execute();
    }

    private AddTemplateCommand generateAddTemplateCommand(Template template, Model model) {
        AddTemplateCommand command = new AddTemplateCommand(template);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
