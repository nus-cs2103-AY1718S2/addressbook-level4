//@@author ewaldhew
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.CommandTestUtil.ModelStub;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyRuleBook;
import seedu.address.model.RuleBook;
import seedu.address.model.rule.NotificationRule;
import seedu.address.model.rule.Rule;
import seedu.address.model.rule.exceptions.DuplicateRuleException;
import seedu.address.testutil.NotificationRuleBuilder;

public class NotifyCommandTest {

    @org.junit.Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullCoin_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new NotifyCommand(null);
    }

    @Test
    public void execute_coinAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingRuleAdded modelStub = new ModelStubAcceptingRuleAdded();
        NotificationRule validRule = new NotificationRuleBuilder().build();

        CommandResult commandResult = getNotifyCommandForCoin(validRule, modelStub).execute();

        Assert.assertEquals(String.format(NotifyCommand.MESSAGE_SUCCESS, validRule), commandResult.feedbackToUser);
        Assert.assertEquals(Arrays.asList(validRule), modelStub.rulesAdded);
    }

    @Test
    public void execute_duplicateCoin_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicateRuleException();
        NotificationRule validRule = new NotificationRuleBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(NotifyCommand.MESSAGE_DUPLICATE_RULE);

        getNotifyCommandForCoin(validRule, modelStub).execute();
    }

    @Test
    public void equals() {
        NotificationRule alice = new NotificationRuleBuilder().withValue("c/ALICE").build();
        NotificationRule bob = new NotificationRuleBuilder().withValue("c/BOB").build();
        NotifyCommand addAliceCommand = new NotifyCommand(alice);
        NotifyCommand addBobCommand = new NotifyCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        NotifyCommand addAliceCommandCopy = new NotifyCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different coin -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * Generates a new NotifyCommand with the details of the given coin.
     */
    private NotifyCommand getNotifyCommandForCoin(NotificationRule rule, Model model) {
        NotifyCommand command = new NotifyCommand(rule);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * A Model stub that always throw a DuplicateRuleException when trying to add a rule.
     */
    private class ModelStubThrowingDuplicateRuleException extends ModelStub {
        @Override
        public void addRule(Rule rule) throws DuplicateRuleException {
            throw new DuplicateRuleException();
        }

        @Override
        public ReadOnlyRuleBook getRuleBook() {
            return new RuleBook();
        }
    }

    /**
     * A Model stub that always accept the rule being added.
     */
    private class ModelStubAcceptingRuleAdded extends ModelStub {
        final ArrayList<Rule> rulesAdded = new ArrayList<>();

        @Override
        public void addRule(Rule rule) throws DuplicateRuleException {
            requireNonNull(rule);
            rulesAdded.add(rule);
        }

        @Override
        public ReadOnlyRuleBook getRuleBook() {
            return new RuleBook();
        }
    }

}
