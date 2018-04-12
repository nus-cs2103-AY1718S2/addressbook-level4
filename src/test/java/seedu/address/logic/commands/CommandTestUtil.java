package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.logic.parser.TokenType.PREFIX_CODE;
import static seedu.address.logic.parser.TokenType.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.CoinBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyCoinBook;
import seedu.address.model.ReadOnlyRuleBook;
import seedu.address.model.coin.Coin;
import seedu.address.model.coin.NameContainsKeywordsPredicate;
import seedu.address.model.coin.Price;
import seedu.address.model.coin.exceptions.CoinNotFoundException;
import seedu.address.model.coin.exceptions.DuplicateCoinException;
import seedu.address.model.rule.Rule;
import seedu.address.model.rule.exceptions.DuplicateRuleException;
import seedu.address.model.rule.exceptions.RuleNotFoundException;
import seedu.address.testutil.EditCoinDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMB = "AMB";
    public static final String VALID_NAME_BOS = "BOS";
    public static final String VALID_NAME_JOBS = "JOBS";
    public static final String VALID_TAG_HOT = "hot";
    public static final String VALID_TAG_FAV = "fav";

    public static final String NAME_DESC_AMB = " " + PREFIX_CODE + VALID_NAME_AMB;
    public static final String NAME_DESC_BOS = " " + PREFIX_CODE + VALID_NAME_BOS;
    public static final String NAME_DESC_JOBS = " " + PREFIX_CODE + VALID_NAME_JOBS;
    public static final String TAG_DESC_FAV = " " + PREFIX_TAG + VALID_TAG_FAV;
    public static final String TAG_DESC_HOT = " " + PREFIX_TAG + VALID_TAG_HOT;

    public static final String INVALID_NAME_DESC = " " + PREFIX_CODE + "BTC&"; // '&' not allowed in names
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hot*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final TagCommand.EditCoinDescriptor DESC_AMY;
    public static final TagCommand.EditCoinDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditCoinDescriptorBuilder().withName(VALID_NAME_AMB)
                .withTags(VALID_TAG_FAV).build();
        DESC_BOB = new EditCoinDescriptorBuilder().withName(VALID_NAME_BOS)
                .withTags(VALID_TAG_HOT, VALID_TAG_FAV).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        try {
            CommandResult result = command.execute();
            assertEquals(expectedMessage, result.feedbackToUser);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book and the filtered coin list in the {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        CoinBook expectedCoinBook = new CoinBook(actualModel.getCoinBook());
        List<Coin> expectedFilteredList = new ArrayList<>(actualModel.getFilteredCoinList());

        try {
            command.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedCoinBook, actualModel.getCoinBook());
            assertEquals(expectedFilteredList, actualModel.getFilteredCoinList());
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the coin at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showCoinAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredCoinList().size());

        Coin coin = model.getFilteredCoinList().get(targetIndex.getZeroBased());
        final String[] splitName = coin.getCode().fullName.split("\\s+");
        model.updateFilteredCoinList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredCoinList().size());
    }

    /**
     * Deletes the first coin in {@code model}'s filtered list from {@code model}'s address book.
     */
    public static void deleteFirstCoin(Model model) {
        Coin firstCoin = model.getFilteredCoinList().get(0);
        try {
            model.deleteCoin(firstCoin);
        } catch (CoinNotFoundException pnfe) {
            throw new AssertionError("Coin in filtered list must exist in model.", pnfe);
        }
    }

    /**
     * Returns an {@code UndoCommand} with the given {@code model} and {@code undoRedoStack} set.
     */
    public static UndoCommand prepareUndoCommand(Model model, UndoRedoStack undoRedoStack) {
        UndoCommand undoCommand = new UndoCommand();
        undoCommand.setData(model, new CommandHistory(), undoRedoStack);
        return undoCommand;
    }

    /**
     * Returns a {@code RedoCommand} with the given {@code model} and {@code undoRedoStack} set.
     */
    public static RedoCommand prepareRedoCommand(Model model, UndoRedoStack undoRedoStack) {
        RedoCommand redoCommand = new RedoCommand();
        redoCommand.setData(model, new CommandHistory(), undoRedoStack);
        return redoCommand;
    }


    /**
     * A default model stub that have all of the methods failing.
     */
    public static class ModelStub implements Model {
        @Override
        public void addCoin(Coin coin) throws DuplicateCoinException {
            fail("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyCoinBook newData) {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyCoinBook getCoinBook() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void deleteCoin(Coin target) throws CoinNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updateCoin(Coin target, Coin editedCoin)
                throws DuplicateCoinException {
            fail("This method should not be called.");
        }

        //@@author laichengyu
        @Override
        public void syncAll(HashMap<String, Price> newPriceMetrics)
                throws DuplicateCoinException, CoinNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public List<String> getCodeList() {
            fail("This method should not be called.");
            return null;
        }
        //@@author

        @Override
        public ObservableList<Coin> getFilteredCoinList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredCoinList(Predicate<Coin> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void deleteRule(Rule target) throws RuleNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void addRule(Rule rule) throws DuplicateRuleException {
            fail("This method should not be called.");
        }

        @Override
        public void updateRule(Rule target, Rule editedRule) throws DuplicateRuleException, RuleNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyRuleBook getRuleBook() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public ObservableList<Rule> getRuleList() {
            fail("This method should not be called.");
            return null;
        }
    }

}
