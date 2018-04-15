# ewaldhew
###### \java\guitests\guihandles\NotificationsWindowHandle.java
``` java
package guitests.guihandles;

import guitests.GuiRobot;
import javafx.stage.Stage;

/**
 * A handle to the {@code NotificationsWindow} of the application.
 */
public class NotificationsWindowHandle extends StageHandle {

    public static final String NOTIFICATIONS_WINDOW_TITLE = "Notifications";

    private static final String NOTIFICATIONS_WINDOW_PANEL_ID = "#ruleListPanelPlaceholder";

    public NotificationsWindowHandle(Stage stage) {
        super(stage);
    }

    /**
     * Returns true if the window is currently present in the application.
     */
    public static boolean isWindowPresent() {
        return new GuiRobot().isWindowShown(NOTIFICATIONS_WINDOW_TITLE);
    }

}
```
###### \java\seedu\address\logic\commands\ListNotifsCommandTest.java
``` java
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
```
###### \java\seedu\address\logic\commands\NotifyCommandTest.java
``` java
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
```
###### \java\seedu\address\logic\commands\SpawnNotificationCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalCoins.ALIS;
import static seedu.address.testutil.TypicalCoins.BOS;
import static seedu.address.testutil.TypicalCoins.getTypicalCoinBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.coin.Coin;


public class SpawnNotificationCommandTest {

    @Test
    public void equals() throws Exception {
        SpawnNotificationCommand firstCommand = prepareCommand(ALIS);
        SpawnNotificationCommand secondCommand = prepareCommand(BOS);

        // same object -> returns true
        assertTrue(firstCommand.equals(firstCommand));

        // same values -> returns true
        SpawnNotificationCommand firstCommandCopy = prepareCommand(ALIS);
        assertTrue(firstCommand.equals(firstCommandCopy));

        // different types -> returns false
        assertFalse(firstCommand.equals(1));

        // null -> returns false
        assertFalse(firstCommand.equals(null));

        // different coin -> returns false
        assertFalse(firstCommand.equals(secondCommand));
    }

    /**
     * Returns a {@code SpawnNotificationCommand} with the parameter {@code coin}.
     */
    private SpawnNotificationCommand prepareCommand(Coin coin) {
        SpawnNotificationCommand command = new SpawnNotificationCommand(coin.toString());
        command.setData(new ModelManager(getTypicalCoinBook(), new UserPrefs()),
                new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
```
###### \java\seedu\address\logic\parser\ConditionGeneratorTest.java
``` java
    @Test
    public void cond_generatesPriceRiseCondition() throws Exception {
        ConditionGenerator conditionGenerator = initGenerator(PREFIX_PRICE_RISE_TOKEN, LESS_TOKEN, DECIMAL_TOKEN);
        Predicate<Coin> condition = conditionGenerator.cond();
        Coin passCoin = new CoinBuilder().withPrice(ZERO_PRICE).build();
        Coin failCoin = new CoinBuilder().withPrice(ZERO_PRICE).build();

        passCoin = new Coin(passCoin, lesserPrice);
        failCoin = new Coin(failCoin, greaterPrice);

        assertTrue(condition.test(passCoin));
        assertFalse(condition.test(failCoin));
    }

    @Test
    public void cond_generatesWorthRiseCondition() throws Exception {
        ConditionGenerator conditionGenerator = initGenerator(PREFIX_WORTH_RISE_TOKEN, GREATER_TOKEN, DECIMAL_TOKEN);
        Predicate<Coin> condition = conditionGenerator.cond();
        Coin passCoin = new CoinBuilder().build();
        Coin failCoin = new CoinBuilder().build();

        passCoin.addTotalAmountBought(greaterAmount);
        failCoin.addTotalAmountBought(lesserAmount);

        assertTrue(condition.test(passCoin));
        assertFalse(condition.test(failCoin));
    }

    @Test
    public void cond_generatesPriceFallCondition() throws Exception {
        ConditionGenerator conditionGenerator = initGenerator(PREFIX_PRICE_FALL_TOKEN, GREATER_TOKEN, DECIMAL_TOKEN);
        Predicate<Coin> condition = conditionGenerator.cond();
        Coin passCoin = new CoinBuilder().withPrice(greaterPrice).build();
        Coin failCoin = new CoinBuilder().withPrice(lesserPrice).build();

        passCoin = new Coin(passCoin, ZERO_PRICE);
        failCoin = new Coin(failCoin, ZERO_PRICE);

        assertTrue(condition.test(passCoin));
        assertFalse(condition.test(failCoin));
    }

    @Test
    public void cond_generatesWorthFallCondition() throws Exception {
        ConditionGenerator conditionGenerator = initGenerator(PREFIX_WORTH_FALL_TOKEN, LESS_TOKEN, DECIMAL_TOKEN);
        Predicate<Coin> condition = conditionGenerator.cond();
        Coin passCoin = new CoinBuilder().withAmountBought(lesserAmount).build();
        Coin failCoin = new CoinBuilder().withAmountBought(greaterAmount).build();

        passCoin = new Coin(passCoin, ZERO_PRICE);
        failCoin = new Coin(failCoin, ZERO_PRICE);

        assertTrue(condition.test(passCoin));
        assertFalse(condition.test(failCoin));
    }
```
###### \java\seedu\address\logic\parser\NotifyCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

public class NotifyCommandParserTest {
    private static final String VALID_INPUT = "c/TEST";

    private NotifyCommandParser parser = new NotifyCommandParser();

    @Test
    public void parse_allFieldsPresent_success() throws Exception {
        assertParseSuccess(parser, VALID_INPUT, parser.parse(VALID_INPUT));
    }
}
```
###### \java\seedu\address\logic\RuleCheckerTest.java
``` java
package seedu.address.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalCoins.getTypicalCoinBook;
import static seedu.address.testutil.TypicalRules.getTypicalRuleBook;
import static seedu.address.testutil.TypicalTargets.INDEX_FIRST_COIN;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.model.CoinChangedEvent;
import seedu.address.commons.events.model.RuleBookChangedEvent;
import seedu.address.commons.events.ui.ShowNotificationRequestEvent;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.RuleBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.coin.Amount;
import seedu.address.model.coin.Coin;
import seedu.address.testutil.TestUtil;
import seedu.address.ui.testutil.EventsCollectorRule;

public class RuleCheckerTest {

    private static final RuleBook emptyRuleBook = new RuleBook();
    private static final RuleBook testRuleBook = getTypicalRuleBook();
    private static final Model model = new ModelManager(getTypicalCoinBook(), new UserPrefs());


    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private RuleChecker ruleChecker;
    private CoinChangedEvent event;

    @Before
    public void setUp() {
        Coin firstCoin = TestUtil.getCoin(model, INDEX_FIRST_COIN);
        Coin editedCoin = new Coin(firstCoin);
        editedCoin.addTotalAmountBought(new Amount("10"));
        event = new CoinChangedEvent(INDEX_FIRST_COIN, firstCoin, editedCoin);
    }

    @Test
    public void handleCoinChangedEvent_emptyRules_doesNotPost() {
        ruleChecker = new RuleChecker(emptyRuleBook);

        EventsCenter.getInstance().post(event);
        assertFalse(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ShowNotificationRequestEvent);
    }

    @Test
    public void updateRuleBook_validRuleTypeMatches_postsShowNotifEvent() {
        ruleChecker = new RuleChecker(emptyRuleBook);

        EventsCenter.getInstance().post(new RuleBookChangedEvent(testRuleBook));

        EventsCenter.getInstance().post(event);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ShowNotificationRequestEvent);

        ShowNotificationRequestEvent snre =
                (ShowNotificationRequestEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(snre.codeString, event.data.getCode().toString());
        assertEquals(snre.targetIndex, INDEX_FIRST_COIN);
    }
}
```
###### \java\seedu\address\ui\ChartsPanelTest.java
``` java
package seedu.address.ui;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalCoins.ALIS;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.ChartsPanelHandle;
import seedu.address.commons.events.ui.CoinPanelSelectionChangedEvent;
import seedu.address.model.coin.Amount;
import seedu.address.model.coin.Coin;
import seedu.address.testutil.CoinBuilder;

public class ChartsPanelTest extends GuiUnitTest {
    private CoinPanelSelectionChangedEvent selectionChangedEventValidCoinStub;
    private CoinPanelSelectionChangedEvent selectionChangedEventInvalidCoinStub;

    private ChartsPanel chartsPanel;
    private ChartsPanelHandle chartsPanelHandle;

    @Before
    public void setUp() {
        final ArrayList<String> testDataX = new ArrayList<>(Arrays.asList(
                "1452592800", "1452596400", "1452600000", "1452603600", "1452607200",
                "1452610800", "1452614400", "1452618000", "1452621600"));

        final ArrayList<Amount> testDataY = new ArrayList<>(Arrays.asList(
                new Amount("0.002591"), new Amount("0.002580"), new Amount("0.002617"),
                new Amount("0.002563"), new Amount("0.002597"), new Amount("0.002576"),
                new Amount("0.002555"), new Amount("0.002719"), new Amount("0.002575")));

        Coin validCodeCoin = ALIS;
        Coin invalidCodeCoin = new CoinBuilder().withName("invalid").build();

        validCodeCoin.getPrice().setHistorical(testDataY, testDataX);

        selectionChangedEventValidCoinStub = new CoinPanelSelectionChangedEvent(new CoinCard(validCodeCoin, 0));
        selectionChangedEventInvalidCoinStub = new CoinPanelSelectionChangedEvent(new CoinCard(invalidCodeCoin, 1));

        guiRobot.interact(() -> chartsPanel = new ChartsPanel());
        uiPartRule.setUiPart(chartsPanel);

        chartsPanelHandle = new ChartsPanelHandle(chartsPanel.getRoot());
    }

    @Test
    public void display() throws Exception {
        postNow(selectionChangedEventValidCoinStub);
        assertFalse(chartsPanelHandle.isEmpty());

        postNow(selectionChangedEventInvalidCoinStub);
        assertTrue(chartsPanelHandle.isEmpty());
    }
}
```
###### \java\seedu\address\ui\NotificationsWindowTest.java
``` java
package seedu.address.ui;

import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalRules.getTypicalRuleBook;

import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;

import guitests.guihandles.NotificationsWindowHandle;
import javafx.application.Platform;
import javafx.stage.Stage;

public class NotificationsWindowTest extends GuiUnitTest {

    private NotificationsWindow notificationsWindow;
    private NotificationsWindowHandle notificationsWindowHandle;

    @Before
    public void setUp() throws Exception {
        guiRobot.interact(() -> notificationsWindow =
                new NotificationsWindow(new Stage(), getTypicalRuleBook().getRuleList()));
        Stage windowStage = FxToolkit.setupStage((stage) -> stage.setScene(notificationsWindow.getRoot().getScene()));
        FxToolkit.showStage();
        notificationsWindowHandle = new NotificationsWindowHandle(windowStage);
    }

    @Test
    public void display() {
        Platform.runLater(() -> {
            notificationsWindow.show();
            assertTrue(NotificationsWindowHandle.isWindowPresent());
        });
    }

}
```
