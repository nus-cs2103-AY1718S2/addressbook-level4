//@@author ewaldhew
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.ListNotifsCommand.MESSAGE_SUCCESS;

import org.junit.Rule;
import org.junit.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.events.ui.ShowNotifManRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.CommandTestUtil.ModelStub;
import seedu.address.testutil.TypicalRules;
import seedu.address.ui.testutil.EventsCollectorRule;

public class ListNotifsCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_listnotifs_success() {
        ListNotifsCommand testCommand = new ListNotifsCommand();
        testCommand.setData(new ModelStubAcceptingGetRuleList(), new CommandHistory(), new UndoRedoStack());
        CommandResult result = testCommand.execute();
        assertEquals(MESSAGE_SUCCESS, result.feedbackToUser);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ShowNotifManRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }

    private class ModelStubAcceptingGetRuleList extends ModelStub {
        @Override
        public ObservableList<seedu.address.model.rule.Rule> getRuleList() {
            return TypicalRules.getTypicalRuleBook().getRuleList();
        }
    }
}
