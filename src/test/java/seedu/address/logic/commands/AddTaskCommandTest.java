package seedu.address.logic.commands;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

//@@author Wu Di
public class AddTaskCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullTask_throwNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddTaskCommand(null);
    }

    @Test
    public void execute_taskAcceptedByModel_addTaskSuccessful() throws Exception {

    }

}
