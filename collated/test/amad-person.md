# amad-person
###### /java/guitests/guihandles/OrderCardHandle.java
``` java
package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * Provides a handle to an order card in the order list panel.
 */
public class OrderCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String ORDER_INFORMATION_ID = "#orderInformation";
    private static final String PRICE_AND_QUANTITY_ID = "#priceAndQuantity";
    private static final String TOTAL_PRICE_ID = "#totalPrice";
    private static final String DELIVERY_DATE_ID = "#deliveryDate";

    private final Label idLabel;
    private final Label orderInformationLabel;
    private final Label priceAndQuantityLabel;
    private final Label totalPriceLabel;
    private final Label deliveryDateLabel;

    public OrderCardHandle(Node cardNode) {
        super(cardNode);

        this.idLabel = getChildNode(ID_FIELD_ID);
        this.orderInformationLabel = getChildNode(ORDER_INFORMATION_ID);
        this.priceAndQuantityLabel = getChildNode(PRICE_AND_QUANTITY_ID);
        this.totalPriceLabel = getChildNode(TOTAL_PRICE_ID);
        this.deliveryDateLabel = getChildNode(DELIVERY_DATE_ID);
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getOrderInformation() {
        return orderInformationLabel.getText();
    }

    public String getPriceAndQuantity() {
        return priceAndQuantityLabel.getText();
    }

    public String getTotalPrice() {
        return totalPriceLabel.getText();
    }

    public String getDeliveryDate() {
        return deliveryDateLabel.getText();
    }
}
```
###### /java/guitests/guihandles/OrderListPanelHandle.java
``` java
package guitests.guihandles;

import java.util.List;
import java.util.Optional;

import javafx.scene.control.ListView;
import seedu.address.model.order.Order;
import seedu.address.ui.OrderCard;

/**
 * Provides a handle for {@code OrderListPanel} containing the list of {@code OrderCard}.
 */
public class OrderListPanelHandle extends NodeHandle<ListView<OrderCard>> {
    public static final String ORDER_LIST_VIEW_ID = "#orderListView";

    public OrderListPanelHandle(ListView<OrderCard> orderListPanelNode) {
        super(orderListPanelNode);
    }

    /**
     * Navigates the listview to display and select the order.
     */
    public void navigateToCard(Order order) {
        List<OrderCard> orderCards = getRootNode().getItems();
        Optional<OrderCard> matchingCard = orderCards.stream()
                .filter(orderCard -> orderCard.order.equals(order)).findFirst();

        if (!matchingCard.isPresent()) {
            throw  new IllegalArgumentException("Order does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(matchingCard.get());
            getRootNode().getSelectionModel().select(matchingCard.get());
        });

        guiRobot.pauseForHuman();
    }

    /**
     * Returns the order card handle of an order associated with the {@code index} in the list.
     */
    public OrderCardHandle getOrderCardHandle(int index) {
        return getOrderCardHandle(getRootNode().getItems().get(index).order);
    }

    /**
     * Returns the order card handle of an order associated with the {@code order} in the list.
     */
    private OrderCardHandle getOrderCardHandle(Order order) {
        Optional<OrderCardHandle> handle = getRootNode().getItems().stream()
                .filter(orderCard -> orderCard.order.equals(order))
                .map(orderCard -> new OrderCardHandle(orderCard.getRoot()))
                .findFirst();
        return handle.orElseThrow(() -> new IllegalArgumentException("Order does not exist."));
    }
}
```
###### /java/seedu/address/logic/commands/AddOrderCommandTest.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalOrders.FACEWASH;
import static seedu.address.testutil.TypicalOrders.SHOES;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.calendarfx.model.Calendar;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.CalendarManager;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyCalendarManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.entry.CalendarEntry;
import seedu.address.model.entry.exceptions.CalendarEntryNotFoundException;
import seedu.address.model.entry.exceptions.DuplicateCalendarEntryException;
import seedu.address.model.order.Order;
import seedu.address.model.order.exceptions.DuplicateOrderException;
import seedu.address.model.order.exceptions.OrderNotFoundException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Group;
import seedu.address.model.tag.Preference;
import seedu.address.model.tag.exceptions.GroupNotFoundException;
import seedu.address.model.tag.exceptions.PreferenceNotFoundException;
import seedu.address.testutil.OrderBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand)
 * and unit tests for AddOrderCommand.
 */
public class AddOrderCommandTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new CalendarManager(), new UserPrefs());
    }

    @Test
    public void constructor_nullOrder_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddOrderCommand(null, null);
    }

    @Test
    public void execute_orderAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingOrderAdded modelStub = new ModelStubAcceptingOrderAdded();
        Order validOrder = new OrderBuilder().build();
        Person person = model.getAddressBook().getPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        CommandResult commandResult = getAddOrderCommandForOrder(INDEX_FIRST_PERSON, validOrder, modelStub).execute();

        assertEquals(String.format(AddOrderCommand.MESSAGE_ADD_ORDER_SUCCESS,
                person.getName(), validOrder), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validOrder), modelStub.ordersAdded);
    }

    @Test
    public void execute_duplicateOrder_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicateOrderException();

        Order validOrder = new OrderBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddOrderCommand.MESSAGE_ORDER_NOT_ADDED);

        getAddOrderCommandForOrder(INDEX_FIRST_PERSON, validOrder, modelStub).execute();
    }

    @Test
    public void equals() {
        Order firstOrder = SHOES;
        Order secondOrder = FACEWASH;

        AddOrderCommand firstAddOrderCommmand = new AddOrderCommand(INDEX_FIRST_PERSON, firstOrder);
        AddOrderCommand secondAddOrderCommand = new AddOrderCommand(INDEX_SECOND_PERSON, secondOrder);

        // same object -> returns true
        assertTrue(firstAddOrderCommmand.equals(firstAddOrderCommmand));

        // same values -> returns true
        AddOrderCommand firstAddOrderCommandCopy = new AddOrderCommand(INDEX_FIRST_PERSON, firstOrder);
        assertTrue(firstAddOrderCommmand.equals(firstAddOrderCommandCopy));

        // different types -> returns false
        assertFalse(firstAddOrderCommmand.equals(1));

        // null -> return false
        assertFalse(firstAddOrderCommandCopy.equals(null));

        // different person -> return false
        assertFalse(firstAddOrderCommmand.equals(secondAddOrderCommand));
    }

    /**
     * Generates a new AddOrderCommand with the details of the given order.
     */
    private AddOrderCommand getAddOrderCommandForOrder(Index index, Order order, Model model) {
        AddOrderCommand command = new AddOrderCommand(index, order);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * A default model stub that has all of the irrelevant methods failing.
     */
    private class ModelStub implements Model {
        private final FilteredList<Person> filteredPersons = new FilteredList<>(model.getFilteredPersonList());

        @Override
        public void addPerson(Person person) throws DuplicatePersonException {
            fail("This method should not be called.");
        }


        @Override
        public void resetData(ReadOnlyAddressBook newData, ReadOnlyCalendarManager newCalendarData) {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void deletePerson(Person target) throws PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updatePerson(Person target, Person editedPerson)
                throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public void updateOrder(Order target, Order editedOrder) throws DuplicateOrderException {
            fail("This method should not be called.");
        }

        @Override
        public void updateOrderStatus(Order target, String orderStatus)
                throws DuplicateOrderException, OrderNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return model.getFilteredPersonList();
        }

        @Override
        public ObservableList<Order> getFilteredOrderList() {
            return model.getFilteredOrderList();
        }

        @Override
        public ObservableList<CalendarEntry> getFilteredCalendarEntryList() {
            return model.getFilteredCalendarEntryList();
        }

        @Override
        public Calendar getCalendar() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public ReadOnlyCalendarManager getCalendarManager() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            filteredPersons.setPredicate(predicate);
        }

        @Override
        public void updateFilteredCalendarEventList(Predicate<CalendarEntry> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void updateFilteredOrderList(Predicate<Order> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void deleteGroup(Group targetGroup) throws GroupNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void deletePreference(Preference targetPreference) throws PreferenceNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void addOrderToOrderList(Order orderToAdd) throws DuplicateOrderException {
            fail("This method should not be called.");
        }

        @Override
        public void deleteOrder(Order targetOrder) throws OrderNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void addCalendarEntry(CalendarEntry toAdd)
                throws DuplicateCalendarEntryException {
            fail("This method should not be called.");
        }

        @Override
        public void deleteCalendarEntry(CalendarEntry entryToDelete) throws CalendarEntryNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updateCalendarEntry(CalendarEntry entryToEdit, CalendarEntry editedEntry)
                throws DuplicateCalendarEntryException, CalendarEntryNotFoundException {
            fail("This method should not be called.");

        }
    }

    /**
     * A Model stub that always throws a DuplicatePersonException when trying to add a person.
     */
    private class ModelStubThrowingDuplicateOrderException extends ModelStub {
        @Override
        public void addOrderToOrderList(Order order) throws DuplicateOrderException {
            throw new DuplicateOrderException();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }

        @Override
        public ReadOnlyCalendarManager getCalendarManager() {
            return new CalendarManager();
        }
    }

    /**
     * A Model stub that always accepts the order being added.
     */
    private class ModelStubAcceptingOrderAdded extends ModelStub {
        final ArrayList<Order> ordersAdded = new ArrayList<>();

        @Override
        public void addOrderToOrderList(Order order) throws DuplicateOrderException {
            requireNonNull(order);
            ordersAdded.add(order);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }

        @Override
        public ReadOnlyCalendarManager getCalendarManager() {
            return new CalendarManager();
        }
    }
}

```
###### /java/seedu/address/logic/commands/ChangeOrderStatusCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.ChangeOrderStatusCommand.MESSAGE_INVALID_ORDER_STATUS;
import static seedu.address.logic.commands.ChangeOrderStatusCommand.MESSAGE_ORDER_STATUS_CHANGED_SUCCESS;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.address.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.address.model.order.OrderStatus.ORDER_STATUS_DONE;
import static seedu.address.model.order.OrderStatus.ORDER_STATUS_ONGOING;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ORDER;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ORDER;
import static seedu.address.testutil.TypicalOrders.getTypicalAddressBookWithOrders;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.CalendarManager;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.order.Order;

public class ChangeOrderStatusCommandTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBookWithOrders(), new CalendarManager(), new UserPrefs());
    }

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new ChangeOrderStatusCommand(null, ORDER_STATUS_ONGOING);
    }

    @Test
    public void constructor_nullOrderStatus_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new ChangeOrderStatusCommand(INDEX_FIRST_ORDER, null);
    }

    @Test
    public void execute_orderStatusAccepted_statusChanged() throws Exception {
        Order orderToChangeStatus = model.getFilteredOrderList().get(INDEX_FIRST_ORDER.getZeroBased());
        ChangeOrderStatusCommand changeOrderStatusToDoneCommand = getChangeOrderStatusCommand(INDEX_FIRST_ORDER,
                ORDER_STATUS_DONE, model);

        String expectedMessage = String.format(MESSAGE_ORDER_STATUS_CHANGED_SUCCESS,
                INDEX_FIRST_ORDER.getOneBased(), ORDER_STATUS_DONE);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new CalendarManager(),
                new UserPrefs());

        expectedModel.updateOrderStatus(orderToChangeStatus, ORDER_STATUS_DONE);
        assertCommandSuccess(changeOrderStatusToDoneCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidOrderIndex_throwsCommandException() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredOrderList().size() + 1);
        ChangeOrderStatusCommand changeOrderStatusCommand = getChangeOrderStatusCommand(outOfBoundsIndex,
                ORDER_STATUS_DONE, model);

        String expectedMessage = Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX;

        assertCommandFailure(changeOrderStatusCommand, model, expectedMessage);
    }

    @Test
    public void execute_invalidOrderStatus_throwsCommandException() {
        String invalidOrderStatus = "fulfill3ed";
        ChangeOrderStatusCommand changeOrderStatusCommand = getChangeOrderStatusCommand(INDEX_FIRST_ORDER,
                invalidOrderStatus, model);

        String expectedMessage = String.format(MESSAGE_INVALID_ORDER_STATUS, invalidOrderStatus);

        assertCommandFailure(changeOrderStatusCommand, model, expectedMessage);
    }

    @Test
    public void executeUndoRedo_validOrderIndexAndOrderStatus_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);

        Order orderToChangeStatus = model.getFilteredOrderList().get(INDEX_FIRST_ORDER.getZeroBased());

        ChangeOrderStatusCommand changeOrderStatusCommand = getChangeOrderStatusCommand(INDEX_FIRST_ORDER,
                ORDER_STATUS_DONE, model);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new CalendarManager(), new UserPrefs());

        // execute changeOrderStatusCommand -> order status of order updated
        changeOrderStatusCommand.execute();
        undoRedoStack.push(changeOrderStatusCommand);

        // undo -> order status of order reverted back to original
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same order has its order status updated again
        expectedModel.updateOrderStatus(orderToChangeStatus, ORDER_STATUS_DONE);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidOrderIndex_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);

        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredOrderList().size() + 1);

        ChangeOrderStatusCommand changeOrderStatusCommand = getChangeOrderStatusCommand(outOfBoundsIndex,
                ORDER_STATUS_DONE, model);

        String expectedMessage = Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX;

        // execution failed -> changeOrderStatusCommand not pushed into undoRedoStack
        assertCommandFailure(changeOrderStatusCommand, model, expectedMessage);

        // no commands in undoRedoStack -> undo and redo both fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void executeUndoRedo_invalidOrderStatus_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);

        String invalidOrderStatus = "fulfill3d";

        ChangeOrderStatusCommand changeOrderStatusCommand = getChangeOrderStatusCommand(INDEX_FIRST_ORDER,
                invalidOrderStatus, model);

        String expectedMessage = String.format(MESSAGE_INVALID_ORDER_STATUS, invalidOrderStatus);

        // execution failed -> changeOrderStatusCommand not pushed into undoRedoStack
        assertCommandFailure(changeOrderStatusCommand, model, expectedMessage);

        // no commands in undoRedoStack -> undo and redo both fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        ChangeOrderStatusCommand firstCommand = getChangeOrderStatusCommand(INDEX_FIRST_ORDER,
                ORDER_STATUS_DONE, model);
        ChangeOrderStatusCommand firstCommandCopy = getChangeOrderStatusCommand(INDEX_FIRST_ORDER,
                ORDER_STATUS_DONE, model);

        assertEquals(firstCommand, firstCommandCopy);
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        ChangeOrderStatusCommand firstCommand = getChangeOrderStatusCommand(INDEX_FIRST_ORDER,
                ORDER_STATUS_DONE, model);

        assertTrue(firstCommand.equals(firstCommand));
    }

    @Test
    public void equals_differentValues_returnsFalse() {
        ChangeOrderStatusCommand firstCommand = getChangeOrderStatusCommand(INDEX_FIRST_ORDER,
                ORDER_STATUS_DONE, model);

        // null -> returns false
        assertNotEquals(null, firstCommand);

        // different types -> returns false
        assertNotEquals(1, firstCommand);

        // different index -> returns false
        assertNotEquals(new ChangeOrderStatusCommand(INDEX_SECOND_ORDER, ORDER_STATUS_DONE), firstCommand);

        // different order status -> returns false
        assertNotEquals(new ChangeOrderStatusCommand(INDEX_FIRST_ORDER, ORDER_STATUS_ONGOING), firstCommand);
    }

    /**
     * Generates a new ChangeOrderStatusCommand with the given index and new order status.
     */
    private ChangeOrderStatusCommand getChangeOrderStatusCommand(Index index, String orderStatus, Model model) {
        ChangeOrderStatusCommand command = new ChangeOrderStatusCommand(index, orderStatus);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
```
###### /java/seedu/address/logic/commands/ChangeThemeCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.ChangeThemeCommand.MESSAGE_INVALID_THEME;
import static seedu.address.logic.commands.ChangeThemeCommand.MESSAGE_THEME_CHANGED_SUCCESS;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_THEME;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.model.theme.Theme.DARK_THEME_KEYWORD;
import static seedu.address.model.theme.Theme.LIGHT_THEME_KEYWORD;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.CalendarManager;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ChangeThemeCommandTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    private UserPrefs userPrefs;
    private GuiSettings guiSettings;
    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new CalendarManager(), new UserPrefs());
        userPrefs = new UserPrefs();
        guiSettings = userPrefs.getGuiSettings();
    }

    @Test
    public void constructor_nullTheme_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new ChangeThemeCommand(null);
    }

    @Test
    public void execute_themeAccepted_guiSettingsChanged() {
        ChangeThemeCommand changeThemeToLightCommand = prepareCommand(LIGHT_THEME_KEYWORD);

        String expectedMessage = String.format(MESSAGE_THEME_CHANGED_SUCCESS, LIGHT_THEME_KEYWORD);

        UserPrefs expectedUserPrefs = new UserPrefs();
        expectedUserPrefs.setGuiSettings(
                guiSettings.getWindowHeight(),
                guiSettings.getWindowWidth(),
                guiSettings.getWindowCoordinates().x,
                guiSettings.getWindowCoordinates().y,
                LIGHT_THEME_KEYWORD
        );

        Model expectedModel = new ModelManager(getTypicalAddressBook(), model.getCalendarManager(), expectedUserPrefs);
        assertCommandSuccess(changeThemeToLightCommand, model, expectedMessage, expectedModel);

        ChangeThemeCommand changeThemeToDarkCommand = prepareCommand(DARK_THEME_KEYWORD);

        expectedMessage = String.format(MESSAGE_THEME_CHANGED_SUCCESS, DARK_THEME_KEYWORD);

        expectedUserPrefs = new UserPrefs();
        expectedUserPrefs.setGuiSettings(
                guiSettings.getWindowHeight(),
                guiSettings.getWindowWidth(),
                guiSettings.getWindowCoordinates().x,
                guiSettings.getWindowCoordinates().y,
                DARK_THEME_KEYWORD
        );

        expectedModel = new ModelManager(getTypicalAddressBook(), model.getCalendarManager(), expectedUserPrefs);
        assertCommandSuccess(changeThemeToDarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidTheme_throwsCommandException() {
        ChangeThemeCommand invalidChangeThemeCommand = prepareCommand(INVALID_THEME);

        String expectedMessage = String.format(MESSAGE_INVALID_THEME, INVALID_THEME);

        assertCommandFailure(invalidChangeThemeCommand, model, expectedMessage);
    }

    @Test
    public void equals() {
        ChangeThemeCommand darkThemeCommand = new ChangeThemeCommand(DARK_THEME_KEYWORD);
        ChangeThemeCommand lightThemeCommand = new ChangeThemeCommand(LIGHT_THEME_KEYWORD);

        // same object -> true
        assertTrue(darkThemeCommand.equals(darkThemeCommand));

        // same value -> true
        ChangeThemeCommand darkThemeCommandCopy = new ChangeThemeCommand(DARK_THEME_KEYWORD);
        assertTrue(darkThemeCommand.equals(darkThemeCommandCopy));

        // different value -> false
        assertFalse(darkThemeCommand.equals(lightThemeCommand));

        // different type -> false
        assertFalse(darkThemeCommand.equals(1));

        // null -> false
        assertFalse(darkThemeCommand.equals(null));

    }

    private ChangeThemeCommand prepareCommand(String theme) {
        ChangeThemeCommand changeThemeCommand = new ChangeThemeCommand(theme);
        changeThemeCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return changeThemeCommand;
    }
}
```
###### /java/seedu/address/logic/commands/DeleteOrderCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.address.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ORDER;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ORDER;
import static seedu.address.testutil.TypicalOrders.getTypicalAddressBookWithOrders;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.CalendarManager;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.order.Order;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteOrderCommand}.
 */
public class DeleteOrderCommandTest {

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBookWithOrders(), new CalendarManager(), new UserPrefs());
    }

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        Order orderToDelete = model.getFilteredOrderList().get(INDEX_FIRST_ORDER.getZeroBased());
        DeleteOrderCommand deleteOrderCommand = prepareCommand(INDEX_FIRST_ORDER);

        String expectedMessage = String.format(DeleteOrderCommand.MESSAGE_DELETE_ORDER_SUCCESS, orderToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), model.getCalendarManager(),
                new UserPrefs());
        expectedModel.deleteOrder(orderToDelete);

        assertCommandSuccess(deleteOrderCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredOrderList().size() + 1);
        DeleteOrderCommand deleteOrderCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deleteOrderCommand, model, Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Order orderToDelete = model.getFilteredOrderList().get(INDEX_FIRST_ORDER.getZeroBased());
        DeleteOrderCommand deleteOrderCommand = prepareCommand(INDEX_FIRST_ORDER);
        Model expectedModel = new ModelManager(model.getAddressBook(), model.getCalendarManager(), new UserPrefs());

        // delete -> first order deleted
        deleteOrderCommand.execute();
        undoRedoStack.push(deleteOrderCommand);

        // undo -> reverts address book back to previous state and order list to show all orders
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first order deleted again
        expectedModel.deleteOrder(orderToDelete);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredOrderList().size() + 1);
        DeleteOrderCommand deleteOrderCommand = prepareCommand(outOfBoundIndex);

        // execution failed -> deleteOrderCommand not pushed into undoRedoStack
        assertCommandFailure(deleteOrderCommand, model, Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals() throws Exception {
        DeleteOrderCommand deleteFirstOrderCommand = prepareCommand(INDEX_FIRST_ORDER);
        DeleteOrderCommand deleteSecondOrderCommand = prepareCommand(INDEX_SECOND_ORDER);

        // same object -> returns true
        assertTrue(deleteFirstOrderCommand.equals(deleteFirstOrderCommand));

        // same values -> returns true
        DeleteOrderCommand deleteFirstOrderCommandCopy = prepareCommand(INDEX_FIRST_ORDER);
        assertTrue(deleteFirstOrderCommand.equals(deleteFirstOrderCommandCopy));

        // one command preprocessed when previously equal -> returns false
        deleteFirstOrderCommandCopy.preprocessUndoableCommand();
        assertFalse(deleteFirstOrderCommand.equals(deleteFirstOrderCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstOrderCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstOrderCommand.equals(null));

        // different order -> returns false
        assertFalse(deleteFirstOrderCommand.equals(deleteSecondOrderCommand));
    }

    /**
     * Returns a {@code DeleteOrderCommand} with the parameter {@code index}.
     */
    private DeleteOrderCommand prepareCommand(Index index) {
        DeleteOrderCommand deleteOrderCommand = new DeleteOrderCommand(index);
        deleteOrderCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteOrderCommand;
    }
}
```
###### /java/seedu/address/logic/commands/EditOrderCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_COMICBOOK;
import static seedu.address.logic.commands.CommandTestUtil.DESC_COMPUTER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DELIVERY_DATE_BOOKS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ORDER_INFORMATION_COMPUTER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_CHOC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_BOOKS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_COMPUTER;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.address.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ORDER;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ORDER;
import static seedu.address.testutil.TypicalOrders.getTypicalAddressBookWithOrders;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.EditOrderCommand.EditOrderDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.CalendarManager;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.order.Order;
import seedu.address.testutil.EditOrderDescriptorBuilder;
import seedu.address.testutil.OrderBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests
 * for EditOrderCommand.
 */
public class EditOrderCommandTest {

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBookWithOrders(), new CalendarManager(), new UserPrefs());
    }

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() throws Exception {
        Index indexLastOrder = Index.fromOneBased(model.getFilteredOrderList().size());
        Order lastOrder = model.getFilteredOrderList().get(indexLastOrder.getZeroBased());

        OrderBuilder orderInList = new OrderBuilder(lastOrder);
        Order editedOrder = orderInList.withOrderInformation(VALID_ORDER_INFORMATION_COMPUTER)
                .withPrice(VALID_PRICE_CHOC).withQuantity(VALID_QUANTITY_BOOKS)
                .withDeliveryDate(VALID_DELIVERY_DATE_BOOKS).build();
        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder()
                .withOrderInformation(VALID_ORDER_INFORMATION_COMPUTER).withPrice(VALID_PRICE_CHOC)
                .withQuantity(VALID_QUANTITY_BOOKS).withDeliveryDate(VALID_DELIVERY_DATE_BOOKS)
                .build();

        EditOrderCommand editOrderCommand = prepareCommand(indexLastOrder, descriptor);

        String expectedMessage = String.format(EditOrderCommand.MESSAGE_EDIT_ORDER_SUCCESS, editedOrder);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), model.getCalendarManager(),
                new UserPrefs());
        expectedModel.updateOrder(lastOrder, editedOrder);

        assertCommandSuccess(editOrderCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() throws Exception {
        Index indexLastOrder = Index.fromOneBased(model.getFilteredOrderList().size());
        Order lastOrder = model.getFilteredOrderList().get(indexLastOrder.getZeroBased());

        OrderBuilder orderInList = new OrderBuilder(lastOrder);
        Order editedOrder = orderInList.withOrderInformation(VALID_ORDER_INFORMATION_COMPUTER)
                .withQuantity(VALID_QUANTITY_COMPUTER).build();

        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder()
                .withOrderInformation(VALID_ORDER_INFORMATION_COMPUTER)
                .withQuantity(VALID_QUANTITY_COMPUTER).build();

        EditOrderCommand editOrderCommand = prepareCommand(indexLastOrder, descriptor);

        String expectedMessage = String.format(EditOrderCommand.MESSAGE_EDIT_ORDER_SUCCESS, editedOrder);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new CalendarManager(),
                new UserPrefs());
        expectedModel.updateOrder(lastOrder, editedOrder);

        assertCommandSuccess(editOrderCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() throws Exception {
        EditOrderCommand editOrderCommand = prepareCommand(INDEX_FIRST_ORDER, new EditOrderDescriptor());
        Order editedOrder = model.getFilteredOrderList().get(INDEX_FIRST_ORDER.getZeroBased());

        String expectedMessage = String.format(EditOrderCommand.MESSAGE_EDIT_ORDER_SUCCESS, editedOrder);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), model.getCalendarManager(),
                new UserPrefs());
        expectedModel.updateOrder(model.getFilteredOrderList().get(0), editedOrder);

        assertCommandSuccess(editOrderCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateOrderUnfilteredList_failure() {
        Order firstOrder = model.getFilteredOrderList().get(INDEX_FIRST_ORDER.getZeroBased());
        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder(firstOrder).build();
        EditOrderCommand editOrderCommand = prepareCommand(INDEX_SECOND_ORDER, descriptor);

        assertCommandFailure(editOrderCommand, model, EditOrderCommand.MESSAGE_DUPLICATE_ORDER);
    }

    @Test
    public void execute_invalidOrderIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredOrderList().size() + 1);
        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder()
                .withOrderInformation(VALID_ORDER_INFORMATION_COMPUTER).build();
        EditOrderCommand editOrderCommand = prepareCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editOrderCommand, model, Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Order editedOrder = new OrderBuilder().build();
        Order orderToEdit = model.getFilteredOrderList().get(INDEX_FIRST_ORDER.getZeroBased());
        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder(editedOrder).build();
        EditOrderCommand editOrderCommand = prepareCommand(INDEX_FIRST_ORDER, descriptor);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), model.getCalendarManager(),
                new UserPrefs());

        // edit -> first order edited
        editOrderCommand.execute();
        undoRedoStack.push(editOrderCommand);

        // undo -> reverts address book back to previous state and filtered order list to show all orders
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first order edited again
        expectedModel.updateOrder(orderToEdit, editedOrder);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredOrderList().size() + 1);
        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder()
                .withOrderInformation(VALID_ORDER_INFORMATION_COMPUTER).build();
        EditOrderCommand editOrderCommand = prepareCommand(outOfBoundIndex, descriptor);

        // execution failed -> editOrderCommand not pushed into undoRedoStack
        assertCommandFailure(editOrderCommand, model, Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }


    @Test
    public void equals() throws Exception {
        final EditOrderCommand firstCommand = prepareCommand(INDEX_FIRST_ORDER, DESC_COMPUTER);

        // same values -> returns true
        EditOrderDescriptor copyDescriptor = new EditOrderDescriptor(DESC_COMPUTER);
        EditOrderCommand firstCommandCopy = prepareCommand(INDEX_FIRST_ORDER, copyDescriptor);
        assertTrue(firstCommand.equals(firstCommandCopy));

        // same object -> returns true
        assertTrue(firstCommand.equals(firstCommand));

        // one command preprocessed when previously equal -> returns false
        firstCommandCopy.preprocessUndoableCommand();
        assertFalse(firstCommand.equals(firstCommandCopy));

        // null -> returns false
        assertFalse(firstCommand.equals(null));

        // different types -> returns false
        assertFalse(firstCommand.equals(1));

        // different index -> returns false
        assertFalse(firstCommand.equals(new EditOrderCommand(INDEX_SECOND_ORDER, DESC_COMPUTER)));

        // different descriptor -> returns false
        assertFalse(firstCommand.equals(new EditOrderCommand(INDEX_FIRST_ORDER, DESC_COMICBOOK)));
    }

    /**
     * Returns an {@code EditOrderCommand} with parameters {@code index} and {@code descriptor}
     */
    private EditOrderCommand prepareCommand(Index index, EditOrderDescriptor descriptor) {
        EditOrderCommand editOrderCommand = new EditOrderCommand(index, descriptor);
        editOrderCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return editOrderCommand;
    }
}
```
###### /java/seedu/address/logic/parser/AddOrderCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DELIVERY_DATE_DESC_BOOKS;
import static seedu.address.logic.commands.CommandTestUtil.DELIVERY_DATE_DESC_CHOC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DELIVERY_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ORDER_INFORMATION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PRICE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_QUANTITY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.ORDER_INFORMATION_DESC_BOOKS;
import static seedu.address.logic.commands.CommandTestUtil.ORDER_INFORMATION_DESC_CHOC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.PRICE_DESC_BOOKS;
import static seedu.address.logic.commands.CommandTestUtil.PRICE_DESC_CHOC;
import static seedu.address.logic.commands.CommandTestUtil.QUANTITY_DESC_BOOKS;
import static seedu.address.logic.commands.CommandTestUtil.QUANTITY_DESC_CHOC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DELIVERY_DATE_CHOC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ORDER_INFORMATION_CHOC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ORDER_STATUS_CHOC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_CHOC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_CHOC;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.AddOrderCommand;
import seedu.address.model.order.DeliveryDate;
import seedu.address.model.order.Order;
import seedu.address.model.order.OrderInformation;
import seedu.address.model.order.Price;
import seedu.address.model.order.Quantity;
import seedu.address.testutil.OrderBuilder;

public class AddOrderCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddOrderCommand.MESSAGE_USAGE);

    private final AddOrderCommandParser parser = new AddOrderCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Order expectedOrder = new OrderBuilder()
                .withOrderInformation(VALID_ORDER_INFORMATION_CHOC)
                .withOrderStatus(VALID_ORDER_STATUS_CHOC)
                .withPrice(VALID_PRICE_CHOC)
                .withQuantity(VALID_QUANTITY_CHOC)
                .withDeliveryDate(VALID_DELIVERY_DATE_CHOC)
                .build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE
                        + INDEX_FIRST_PERSON.getOneBased() + ORDER_INFORMATION_DESC_CHOC
                        + PRICE_DESC_CHOC + QUANTITY_DESC_CHOC + DELIVERY_DATE_DESC_CHOC,
                new AddOrderCommand(INDEX_FIRST_PERSON, expectedOrder));

        // multiple order information strings - last order information string accepted
        assertParseSuccess(parser, INDEX_FIRST_PERSON.getOneBased()
                        + ORDER_INFORMATION_DESC_BOOKS + ORDER_INFORMATION_DESC_CHOC
                        + PRICE_DESC_CHOC + QUANTITY_DESC_CHOC + DELIVERY_DATE_DESC_CHOC,
                new AddOrderCommand(INDEX_FIRST_PERSON, expectedOrder));

        // multiple prices - last price accepted
        assertParseSuccess(parser, INDEX_FIRST_PERSON.getOneBased()
                        + ORDER_INFORMATION_DESC_CHOC
                        + PRICE_DESC_BOOKS + PRICE_DESC_CHOC
                        + QUANTITY_DESC_CHOC + DELIVERY_DATE_DESC_CHOC,
                new AddOrderCommand(INDEX_FIRST_PERSON, expectedOrder));

        // multiple quantities - last quantity accepted
        assertParseSuccess(parser, INDEX_FIRST_PERSON.getOneBased()
                        + ORDER_INFORMATION_DESC_CHOC
                        + PRICE_DESC_CHOC
                        + QUANTITY_DESC_BOOKS + QUANTITY_DESC_CHOC
                        + DELIVERY_DATE_DESC_CHOC,
                new AddOrderCommand(INDEX_FIRST_PERSON, expectedOrder));

        // multiple delivery dates - last delivery date accepted
        assertParseSuccess(parser, INDEX_FIRST_PERSON.getOneBased()
                        + ORDER_INFORMATION_DESC_CHOC
                        + PRICE_DESC_CHOC + QUANTITY_DESC_CHOC
                        + DELIVERY_DATE_DESC_BOOKS + DELIVERY_DATE_DESC_CHOC,
                new AddOrderCommand(INDEX_FIRST_PERSON, expectedOrder));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        // missing order information prefix
        assertParseFailure(parser, INDEX_FIRST_PERSON.getOneBased() + VALID_ORDER_INFORMATION_CHOC
                        + PRICE_DESC_CHOC + QUANTITY_DESC_CHOC
                        + DELIVERY_DATE_DESC_CHOC,
                MESSAGE_INVALID_FORMAT);

        // missing price prefix
        assertParseFailure(parser, INDEX_FIRST_PERSON.getOneBased() + ORDER_INFORMATION_DESC_CHOC
                        + VALID_PRICE_CHOC + QUANTITY_DESC_CHOC
                        + DELIVERY_DATE_DESC_CHOC,
                MESSAGE_INVALID_FORMAT);

        // missing quantity prefix
        assertParseFailure(parser, INDEX_FIRST_PERSON.getOneBased() + ORDER_INFORMATION_DESC_CHOC
                        + PRICE_DESC_CHOC + VALID_QUANTITY_CHOC
                        + DELIVERY_DATE_DESC_CHOC,
                MESSAGE_INVALID_FORMAT);

        // missing delivery date prefix
        assertParseFailure(parser, INDEX_FIRST_PERSON.getOneBased() + ORDER_INFORMATION_DESC_CHOC
                        + PRICE_DESC_CHOC + QUANTITY_DESC_CHOC
                        + VALID_DELIVERY_DATE_CHOC,
                MESSAGE_INVALID_FORMAT);

        // all prefixes missing
        assertParseFailure(parser, INDEX_FIRST_PERSON.getOneBased() + VALID_ORDER_INFORMATION_CHOC
                        + VALID_PRICE_CHOC + VALID_QUANTITY_CHOC
                        + VALID_DELIVERY_DATE_CHOC,
                MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid order information
        assertParseFailure(parser, INDEX_FIRST_PERSON.getOneBased() + INVALID_ORDER_INFORMATION_DESC
                        + PRICE_DESC_CHOC + QUANTITY_DESC_CHOC
                        + DELIVERY_DATE_DESC_CHOC,
                OrderInformation.MESSAGE_ORDER_INFORMATION_CONSTRAINTS);

        // invalid price
        assertParseFailure(parser, INDEX_FIRST_PERSON.getOneBased() + ORDER_INFORMATION_DESC_CHOC
                        + INVALID_PRICE_DESC + QUANTITY_DESC_CHOC
                        + DELIVERY_DATE_DESC_CHOC,
                Price.MESSAGE_PRICE_CONSTRAINTS);

        // invalid quantity
        assertParseFailure(parser, INDEX_FIRST_PERSON.getOneBased() + ORDER_INFORMATION_DESC_CHOC
                        + PRICE_DESC_CHOC + INVALID_QUANTITY_DESC
                        + DELIVERY_DATE_DESC_CHOC,
                Quantity.MESSAGE_QUANTITY_CONSTRAINTS);

        // invalid delivery date
        assertParseFailure(parser, INDEX_FIRST_PERSON.getOneBased() + ORDER_INFORMATION_DESC_CHOC
                        + PRICE_DESC_CHOC + QUANTITY_DESC_CHOC
                        + INVALID_DELIVERY_DATE_DESC,
                DeliveryDate.MESSAGE_DELIVERY_DATE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INDEX_FIRST_PERSON.getOneBased() + INVALID_ORDER_INFORMATION_DESC
                        + PRICE_DESC_CHOC + QUANTITY_DESC_CHOC
                        + INVALID_DELIVERY_DATE_DESC,
                OrderInformation.MESSAGE_ORDER_INFORMATION_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + INDEX_FIRST_PERSON.getOneBased()
                + ORDER_INFORMATION_DESC_CHOC + PRICE_DESC_CHOC
                + QUANTITY_DESC_CHOC + DELIVERY_DATE_DESC_CHOC, MESSAGE_INVALID_FORMAT);
    }
}
```
###### /java/seedu/address/logic/parser/AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_addAlias() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(AddCommand.COMMAND_ALIAS
                + " " + PersonUtil.getPersonDetails(person));
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_addOrder() throws Exception {
        Order order = new OrderBuilder().build();
        AddOrderCommand command = (AddOrderCommand) parser.parseCommand(OrderUtil
                .getAddOrderCommand(INDEX_FIRST_PERSON.getOneBased(), order));
        assertEquals(new AddOrderCommand(INDEX_FIRST_PERSON, order), command);
    }

    @Test
    public void parseCommand_addOrderAlias() throws Exception {
        Order order = new OrderBuilder().build();
        AddOrderCommand command = (AddOrderCommand) parser.parseCommand(AddOrderCommand.COMMAND_ALIAS
                + " " + INDEX_FIRST_PERSON.getOneBased() + " " + OrderUtil.getOrderDetails(order));
        assertEquals(new AddOrderCommand(INDEX_FIRST_PERSON, order), command);
    }
```
###### /java/seedu/address/logic/parser/AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_changeTheme() throws Exception {
        ChangeThemeCommand command = (ChangeThemeCommand) parser.parseCommand(
                ChangeThemeCommand.COMMAND_WORD + " " + LIGHT_THEME_KEYWORD);
        assertEquals(new ChangeThemeCommand(LIGHT_THEME_KEYWORD), command);
    }

    @Test
    public void parseCommand_changeThemeAlias() throws Exception {
        ChangeThemeCommand command = (ChangeThemeCommand) parser.parseCommand(
                ChangeThemeCommand.COMMAND_ALIAS + " " + LIGHT_THEME_KEYWORD);
        assertEquals(new ChangeThemeCommand(LIGHT_THEME_KEYWORD), command);
    }
```
###### /java/seedu/address/logic/parser/AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_clearAlias() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_ALIAS) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_ALIAS + " 3") instanceof ClearCommand);
    }
```
###### /java/seedu/address/logic/parser/AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_deleteAlias() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_ALIAS + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }
```
###### /java/seedu/address/logic/parser/AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_deleteOrder() throws Exception {
        DeleteOrderCommand command = (DeleteOrderCommand) parser.parseCommand(
                DeleteOrderCommand.COMMAND_WORD + " " + INDEX_FIRST_ORDER.getOneBased());
        assertEquals(new DeleteOrderCommand(INDEX_FIRST_ORDER), command);
    }

    @Test
    public void parseCommand_deleteOrderAlias() throws Exception {
        DeleteOrderCommand command = (DeleteOrderCommand) parser.parseCommand(
                DeleteOrderCommand.COMMAND_ALIAS + " " + INDEX_FIRST_ORDER.getOneBased());
        assertEquals(new DeleteOrderCommand(INDEX_FIRST_ORDER), command);
    }
```
###### /java/seedu/address/logic/parser/AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_editAlias() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_ALIAS + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getPersonDetails(person));
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_editOrder() throws Exception {
        Order order = new OrderBuilder().build();
        EditOrderCommand.EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder()
                .withOrderInformation(DEFAULT_ORDER_INFORMATION).withPrice(DEFAULT_PRICE)
                .withQuantity(DEFAULT_QUANTITY).withDeliveryDate(DEFAULT_DELIVERY_DATE).build();

        EditOrderCommand command = (EditOrderCommand) parser.parseCommand(EditOrderCommand.COMMAND_WORD + " "
                + INDEX_FIRST_ORDER.getOneBased() + " " + OrderUtil.getOrderDetails(order));

        assertEquals(new EditOrderCommand(INDEX_FIRST_ORDER, descriptor), command);
    }

    @Test
    public void parseCommand_editOrderAlias() throws Exception {
        Order order = new OrderBuilder().build();
        EditOrderCommand.EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder()
                .withOrderInformation(DEFAULT_ORDER_INFORMATION).withPrice(DEFAULT_PRICE)
                .withQuantity(DEFAULT_QUANTITY).withDeliveryDate(DEFAULT_DELIVERY_DATE).build();

        EditOrderCommand command = (EditOrderCommand) parser.parseCommand(EditOrderCommand.COMMAND_ALIAS + " "
                + INDEX_FIRST_ORDER.getOneBased() + " " + OrderUtil.getOrderDetails(order));

        assertEquals(new EditOrderCommand(INDEX_FIRST_ORDER, descriptor), command);
    }
```
###### /java/seedu/address/logic/parser/AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_exitAlias() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_ALIAS) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_ALIAS + " 3") instanceof ExitCommand);
    }
```
###### /java/seedu/address/logic/parser/AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_findAlias() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_ALIAS + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }
```
###### /java/seedu/address/logic/parser/AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_helpAlias() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_ALIAS) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_ALIAS + " 3") instanceof HelpCommand);
    }
```
###### /java/seedu/address/logic/parser/AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_historyAlias() throws Exception {
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_ALIAS) instanceof HistoryCommand);
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_ALIAS + " 3") instanceof HistoryCommand);

        try {
            parser.parseCommand("histories");
            fail("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
        }
    }
```
###### /java/seedu/address/logic/parser/AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_listAlias() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_ALIAS) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_ALIAS + " 3") instanceof ListCommand);
    }
```
###### /java/seedu/address/logic/parser/AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_selectAlias() throws Exception {
        SelectCommand command = (SelectCommand) parser.parseCommand(
                SelectCommand.COMMAND_ALIAS + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new SelectCommand(INDEX_FIRST_PERSON), command);
    }
```
###### /java/seedu/address/logic/parser/AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_redoCommandWordAlias_returnsRedoCommand() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_ALIAS) instanceof RedoCommand);
        assertTrue(parser.parseCommand("redo 1") instanceof RedoCommand);
    }
```
###### /java/seedu/address/logic/parser/AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_undoCommandWordAlias_returnsUndoCommand() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_ALIAS) instanceof UndoCommand);
        assertTrue(parser.parseCommand("undo 3") instanceof UndoCommand);
    }
```
###### /java/seedu/address/logic/parser/ChangeOrderStatusCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORDER_STATUS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.model.order.OrderStatus.ORDER_STATUS_DONE;
import static seedu.address.model.order.OrderStatus.ORDER_STATUS_ONGOING;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ORDER;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ORDER;

import org.junit.Test;

import seedu.address.logic.commands.ChangeOrderStatusCommand;

public class ChangeOrderStatusCommandParserTest {

    private final ChangeOrderStatusCommandParser parser = new ChangeOrderStatusCommandParser();

    @Test
    public void parse_validArgs_returnsChangeOrderStatusThemeCommand() {
        String userInput = "1 " + PREFIX_ORDER_STATUS.toString() + " " + ORDER_STATUS_DONE;
        assertParseSuccess(parser, userInput, new ChangeOrderStatusCommand(INDEX_FIRST_ORDER, ORDER_STATUS_DONE));

        userInput = "2 " + PREFIX_ORDER_STATUS.toString() + " " + ORDER_STATUS_ONGOING;
        assertParseSuccess(parser, userInput, new ChangeOrderStatusCommand(INDEX_SECOND_ORDER, ORDER_STATUS_ONGOING));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ChangeOrderStatusCommand.MESSAGE_USAGE));
    }
}
```
###### /java/seedu/address/logic/parser/ChangeThemeCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.model.theme.Theme.DARK_THEME_KEYWORD;
import static seedu.address.model.theme.Theme.LIGHT_THEME_KEYWORD;

import org.junit.Test;

import seedu.address.logic.commands.ChangeThemeCommand;

public class ChangeThemeCommandParserTest {

    private final ChangeThemeCommandParser parser = new ChangeThemeCommandParser();

    @Test
    public void parse_validArgs_returnsChangeThemeCommand() {
        assertParseSuccess(parser, "light", new ChangeThemeCommand(LIGHT_THEME_KEYWORD));

        assertParseSuccess(parser, "dark", new ChangeThemeCommand(DARK_THEME_KEYWORD));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ChangeThemeCommand.MESSAGE_USAGE));
    }
}
```
###### /java/seedu/address/logic/parser/DeleteOrderCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.DeleteOrderCommand;

public class DeleteOrderCommandParserTest {

    private final DeleteOrderCommandParser parser = new DeleteOrderCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "1", new DeleteOrderCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteOrderCommand.MESSAGE_USAGE));
    }
}
```
###### /java/seedu/address/logic/parser/EditOrderCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DELIVERY_DATE_DESC_CHOC;
import static seedu.address.logic.commands.CommandTestUtil.DELIVERY_DATE_DESC_COMPUTER;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DELIVERY_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ORDER_INFORMATION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PRICE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_QUANTITY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.ORDER_INFORMATION_DESC_BOOKS;
import static seedu.address.logic.commands.CommandTestUtil.ORDER_INFORMATION_DESC_CHOC;
import static seedu.address.logic.commands.CommandTestUtil.ORDER_INFORMATION_DESC_COMPUTER;
import static seedu.address.logic.commands.CommandTestUtil.PRICE_DESC_BOOKS;
import static seedu.address.logic.commands.CommandTestUtil.PRICE_DESC_CHOC;
import static seedu.address.logic.commands.CommandTestUtil.PRICE_DESC_COMPUTER;
import static seedu.address.logic.commands.CommandTestUtil.QUANTITY_DESC_BOOKS;
import static seedu.address.logic.commands.CommandTestUtil.QUANTITY_DESC_CHOC;
import static seedu.address.logic.commands.CommandTestUtil.QUANTITY_DESC_COMPUTER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DELIVERY_DATE_CHOC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DELIVERY_DATE_COMPUTER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ORDER_INFORMATION_CHOC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ORDER_INFORMATION_COMPUTER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_BOOKS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_CHOC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_COMPUTER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_BOOKS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_CHOC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_COMPUTER;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ORDER;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ORDER;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_ORDER;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditOrderCommand;
import seedu.address.logic.commands.EditOrderCommand.EditOrderDescriptor;
import seedu.address.model.order.DeliveryDate;
import seedu.address.model.order.OrderInformation;
import seedu.address.model.order.Price;
import seedu.address.model.order.Quantity;
import seedu.address.testutil.EditOrderDescriptorBuilder;

public class EditOrderCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditOrderCommand.MESSAGE_USAGE);

    private final EditOrderCommandParser parser = new EditOrderCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_ORDER_INFORMATION_COMPUTER, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditOrderCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + ORDER_INFORMATION_DESC_COMPUTER, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + ORDER_INFORMATION_DESC_COMPUTER, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 o/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_ORDER_INFORMATION_DESC,
                OrderInformation.MESSAGE_ORDER_INFORMATION_CONSTRAINTS); // invalid order information
        assertParseFailure(parser, "1" + INVALID_PRICE_DESC, Price.MESSAGE_PRICE_CONSTRAINTS); // invalid price
        assertParseFailure(parser, "1" + INVALID_QUANTITY_DESC,
                Quantity.MESSAGE_QUANTITY_CONSTRAINTS); // invalid quantity
        assertParseFailure(parser, "1" + INVALID_DELIVERY_DATE_DESC,
                DeliveryDate.MESSAGE_DELIVERY_DATE_CONSTRAINTS); // invalid address

        // invalid order information followed by valid price
        assertParseFailure(parser, "1" + INVALID_ORDER_INFORMATION_DESC + PRICE_DESC_COMPUTER,
                OrderInformation.MESSAGE_ORDER_INFORMATION_CONSTRAINTS);

        // valid quantity followed by invalid quantity. The test case for invalid quantity followed by valid quantity
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + QUANTITY_DESC_COMPUTER + INVALID_QUANTITY_DESC,
                Quantity.MESSAGE_QUANTITY_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_PRICE_DESC + INVALID_QUANTITY_DESC
                + VALID_ORDER_INFORMATION_COMPUTER, Price.MESSAGE_PRICE_CONSTRAINTS);
    }
```
###### /java/seedu/address/logic/parser/EditOrderCommandParserTest.java
``` java
    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_ORDER;
        String userInput = targetIndex.getOneBased() + PRICE_DESC_BOOKS + DELIVERY_DATE_DESC_COMPUTER;

        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder()
                .withPrice(VALID_PRICE_BOOKS)
                .withDeliveryDate(VALID_DELIVERY_DATE_COMPUTER).build();
        EditOrderCommand expectedCommand = new EditOrderCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        Index targetIndex = INDEX_THIRD_ORDER;

        // order information
        String userInput = targetIndex.getOneBased() + ORDER_INFORMATION_DESC_CHOC;
        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder()
                .withOrderInformation(VALID_ORDER_INFORMATION_CHOC).build();
        EditOrderCommand expectedCommand = new EditOrderCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // price
        userInput = targetIndex.getOneBased() + PRICE_DESC_CHOC;
        descriptor = new EditOrderDescriptorBuilder()
                .withPrice(VALID_PRICE_CHOC).build();
        expectedCommand = new EditOrderCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // quantity
        userInput = targetIndex.getOneBased() + QUANTITY_DESC_CHOC;
        descriptor = new EditOrderDescriptorBuilder()
                .withQuantity(VALID_QUANTITY_CHOC).build();
        expectedCommand = new EditOrderCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // delivery date
        userInput = targetIndex.getOneBased() + DELIVERY_DATE_DESC_CHOC;
        descriptor = new EditOrderDescriptorBuilder()
                .withDeliveryDate(VALID_DELIVERY_DATE_CHOC).build();
        expectedCommand = new EditOrderCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_SECOND_ORDER;
        String userInput = targetIndex.getOneBased() + ORDER_INFORMATION_DESC_CHOC + ORDER_INFORMATION_DESC_BOOKS
                + ORDER_INFORMATION_DESC_COMPUTER + PRICE_DESC_CHOC + PRICE_DESC_BOOKS + QUANTITY_DESC_COMPUTER
                + QUANTITY_DESC_CHOC + DELIVERY_DATE_DESC_COMPUTER;

        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder()
                .withOrderInformation(VALID_ORDER_INFORMATION_COMPUTER).withPrice(VALID_PRICE_BOOKS)
                .withQuantity(VALID_QUANTITY_CHOC).withDeliveryDate(VALID_DELIVERY_DATE_COMPUTER)
                .build();

        EditOrderCommand expectedCommand = new EditOrderCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_ORDER;
        String userInput = targetIndex.getOneBased() + INVALID_QUANTITY_DESC + QUANTITY_DESC_CHOC;
        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder().withQuantity(VALID_QUANTITY_CHOC).build();
        EditOrderCommand expectedCommand = new EditOrderCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + PRICE_DESC_BOOKS + INVALID_QUANTITY_DESC + DELIVERY_DATE_DESC_COMPUTER
                + QUANTITY_DESC_BOOKS;
        descriptor = new EditOrderDescriptorBuilder()
                .withPrice(VALID_PRICE_BOOKS).withQuantity(VALID_QUANTITY_BOOKS)
                .withDeliveryDate(VALID_DELIVERY_DATE_COMPUTER).build();
        expectedCommand = new EditOrderCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
```
###### /java/seedu/address/model/order/DeliveryDateTest.java
``` java
package seedu.address.model.order;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class DeliveryDateTest {

    @Test
    public void constructor_nullDeliveryDate_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new DeliveryDate(null));
    }

    @Test
    public void constructor_invalidDeliveryDate_throwsIllegalArgumentException() {
        String invalidDeliveryDate = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new DeliveryDate(invalidDeliveryDate));
    }

    @Test
    public void isValidDeliveryDate_nullOrderStatus_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> DeliveryDate.isValidDeliveryDate(null));
    }

    @Test
    public void isValidDeliveryDate_invalidDeliveryDate_returnsFalse() {
        assertFalse(DeliveryDate.isValidDeliveryDate("")); // empty string
        assertFalse(DeliveryDate.isValidDeliveryDate(" ")); // spaces only
        assertFalse(DeliveryDate.isValidDeliveryDate("wejo*21")); // invalid string
        assertFalse(DeliveryDate.isValidDeliveryDate("12/12/2012")); // invalid format
        assertFalse(DeliveryDate.isValidDeliveryDate("0-1-98")); // invalid date
        assertFalse(DeliveryDate.isValidDeliveryDate("50-12-1998")); // invalid day
        assertFalse(DeliveryDate.isValidDeliveryDate("10-15-2013")); // invalid month
        assertFalse(DeliveryDate.isValidDeliveryDate("09-08-10000")); // invalid year
        assertFalse(DeliveryDate.isValidDeliveryDate("29-02-2001")); // leap day doesn't exist
    }

    @Test
    public void isValidDeliveryDate_validDeliveryDate_returnsTrue() {
        assertTrue(DeliveryDate.isValidDeliveryDate("01-01-2001")); // valid date
        assertTrue(DeliveryDate.isValidDeliveryDate("29-02-2000")); // leap year
    }

    @Test
    public void equals_sameValue_returnsTrue() {
        DeliveryDate deliveryDate = new DeliveryDate("10-10-2020");
        DeliveryDate deliveryDateCopy = new DeliveryDate("10-10-2020");
        assertEquals(deliveryDate, deliveryDateCopy);
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        DeliveryDate deliveryDate = new DeliveryDate("10-10-2020");
        assertEquals(deliveryDate, deliveryDate);
    }

    @Test
    public void equals_nullObject_returnsFalse() {
        DeliveryDate deliveryDate = new DeliveryDate("10-10-2020");
        assertNotEquals(null, deliveryDate);
    }

    @Test
    public void equals_differentValue_returnsFalse() {
        DeliveryDate firstDeliveryDate = new DeliveryDate("10-10-2020");
        DeliveryDate secondDeliveryDate = new DeliveryDate("11-10-2020");
        assertNotEquals(firstDeliveryDate, secondDeliveryDate);
    }
}
```
###### /java/seedu/address/model/order/OrderInformationTest.java
``` java
package seedu.address.model.order;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class OrderInformationTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new OrderInformation(null));
    }

    @Test
    public void constructor_invalidOrderInformation_throwsIllegalArgumentException() {
        String invalidOrderInformation = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new OrderInformation(invalidOrderInformation));
    }

    @Test
    public void isValidOrderStatus_nullOrderInformation_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> OrderInformation.isValidOrderInformation(null));
    }

    @Test
    public void isValidOrderStatus_invalidOrderInformation_returnsFalse() {
        assertFalse(OrderInformation.isValidOrderInformation("")); // empty string
        assertFalse(OrderInformation.isValidOrderInformation(" ")); // spaces only
    }

    @Test
    public void isValidOrderInformation_validOrderInformation_returnsTrue() {
        assertTrue(OrderInformation.isValidOrderInformation("Books")); // single word
        assertTrue(OrderInformation.isValidOrderInformation("Confectionery Boxes")); // multiple words
        assertTrue(OrderInformation.isValidOrderInformation("NBA 2k18")); // alphanumeric
    }

    @Test
    public void equals_sameValue_returnsTrue() {
        OrderInformation orderInformation = new OrderInformation("Books");
        OrderInformation orderInformationCopy = new OrderInformation("Books");
        assertEquals(orderInformation, orderInformationCopy);
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        OrderInformation orderInformation = new OrderInformation("Books");
        assertEquals(orderInformation, orderInformation);
    }

    @Test
    public void equals_nullObject_returnsFalse() {
        OrderInformation orderInformation = new OrderInformation("Books");
        assertNotEquals(null, orderInformation);
    }

    @Test
    public void equals_differentValue_returnsFalse() {
        OrderInformation firstOrderInformation = new OrderInformation("Books");
        OrderInformation secondOrderInformation = new OrderInformation("Chocolates");
        assertNotEquals(firstOrderInformation, secondOrderInformation);
    }
}
```
###### /java/seedu/address/model/order/OrderStatusTest.java
``` java
package seedu.address.model.order;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class OrderStatusTest {

    @Test
    public void constructor_nullOrderStatus_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new OrderStatus(null));
    }

    @Test
    public void constructor_invalidOrderStatus_throwsIllegalArgumentException() {
        String invalidOrderStatus = "fulfill3d";
        Assert.assertThrows(IllegalArgumentException.class, () -> new OrderStatus(invalidOrderStatus));
    }

    @Test
    public void isValidOrderStatus_nullOrderStatus_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> OrderStatus.isValidOrderStatus(null));
    }

    @Test
    public void isValidOrderStatus_invalidOrderStatus_returnsFalse() {
        assertFalse(OrderStatus.isValidOrderStatus("0ngo!ng_order"));
        assertFalse(OrderStatus.isValidOrderStatus("orderd0ne"));
    }

    @Test
    public void isValidOrderStatus_validOrderStatus_returnsTrue() {
        assertTrue(OrderStatus.isValidOrderStatus("ongoing"));
        assertTrue(OrderStatus.isValidOrderStatus("done"));
    }

    @Test
    public void equals_sameValue_returnsTrue() {
        OrderStatus orderStatus = new OrderStatus("ongoing");
        OrderStatus orderStatusCopy = new OrderStatus("ongoing");
        assertEquals(orderStatus, orderStatusCopy);
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        OrderStatus orderStatus = new OrderStatus("ongoing");
        assertEquals(orderStatus, orderStatus);
    }

    @Test
    public void equals_nullObject_returnsFalse() {
        OrderStatus orderStatus = new OrderStatus("ongoing");
        assertNotEquals(null, orderStatus);
    }

    @Test
    public void equals_differentValue_returnsFalse() {
        OrderStatus firstOrderStatus = new OrderStatus("ongoing");
        OrderStatus secondOrderStatus = new OrderStatus("done");
        assertNotEquals(firstOrderStatus, secondOrderStatus);
    }
}
```
###### /java/seedu/address/model/order/OrderTest.java
``` java
package seedu.address.model.order;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class OrderTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Order(null, null, null, null, null));
    }
}
```
###### /java/seedu/address/model/order/PriceTest.java
``` java
package seedu.address.model.order;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class PriceTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Price(null));
    }

    @Test
    public void constructor_invalidPrice_throwsIllegalArgumentException() {
        String invalidPrice = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Price(invalidPrice));
    }

    @Test
    public void isValidPrice_nullPrice_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> Price.isValidPrice(null));
    }

    @Test
    public void isValidPrice_invalidPrice_returnsFalse() {
        assertFalse(Price.isValidPrice("")); // empty string
        assertFalse(Price.isValidPrice(" ")); // spaces only
        assertFalse(Price.isValidPrice("sj)")); // non numeric characters
        assertFalse(Price.isValidPrice("2.3.20")); // more than one decimal place
        assertFalse(Price.isValidPrice("10.1234")); // more than two digits after decimal point
        assertFalse(Price.isValidPrice("10,00,000")); // commas
        assertFalse(Price.isValidPrice("-1.0")); // negative
        assertFalse(Price.isValidPrice("+10.0")); // plus sign
        assertFalse(Price.isValidPrice("1000000.01")); // out of allowed range
    }

    @Test
    public void isValidPrice_validPrice_returnsTrue() {
        assertTrue(Price.isValidPrice("10.0")); // one digit after decimal point
        assertTrue(Price.isValidPrice("500.75")); // two digits after decimal point
        assertTrue(Price.isValidPrice("015.50")); // leading zero
    }

    @Test
    public void equals_sameValue_returnsTrue() {
        Price price = new Price("12.00");
        Price priceCopy = new Price("12.00");
        assertEquals(price, priceCopy);
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        Price price = new Price("12.00");
        assertEquals(price, price);
    }

    @Test
    public void equals_nullObject_returnsFalse() {
        Price price = new Price("12.00");
        assertNotEquals(null, price);
    }

    @Test
    public void equals_differentValue_returnsFalse() {
        Price firstPrice = new Price("12.00");
        Price secondPrice = new Price("500.00");
        assertNotEquals(firstPrice, secondPrice);
    }
}
```
###### /java/seedu/address/model/order/QuantityTest.java
``` java
package seedu.address.model.order;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class QuantityTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Quantity(null));
    }

    @Test
    public void constructor_invalidQuantity_throwsIllegalArgumentException() {
        String invalidQuantity = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Quantity(invalidQuantity));
    }

    @Test
    public void isValidQuantity_nullQuantity_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> Price.isValidPrice(null));
    }

    @Test
    public void isValidQuantity_invalidQuantity_returnsFalse() {
        assertFalse(Quantity.isValidQuantity("")); // empty string
        assertFalse(Quantity.isValidQuantity(" ")); // spaces only
        assertFalse(Quantity.isValidQuantity("sj)")); // non numeric characters
        assertFalse(Quantity.isValidQuantity("2.3")); // decimal
        assertFalse(Quantity.isValidQuantity("-1")); // negative integer
        assertFalse(Quantity.isValidQuantity("0")); // zero
        assertFalse(Quantity.isValidQuantity("+9")); // plus sign
        assertFalse(Quantity.isValidQuantity("100000000")); // out of allowed range
    }

    @Test
    public void isValidQuantity_validQuantity_returnsTrue() {
        assertTrue(Quantity.isValidQuantity("10")); // positive integer
        assertTrue(Quantity.isValidQuantity("0500")); // leading zero allowed
    }

    @Test
    public void equals_sameValue_returnsTrue() {
        Quantity quantity = new Quantity("10");
        Quantity quantityCopy = new Quantity("10");
        assertEquals(quantity, quantityCopy);
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        Quantity quantity = new Quantity("10");
        assertEquals(quantity, quantity);
    }

    @Test
    public void equals_nullObject_returnsFalse() {
        Quantity quantity = new Quantity("10");
        assertNotEquals(null, quantity);
    }

    @Test
    public void equals_differentValue_returnsFalse() {
        Quantity firstQuantity = new Quantity("10");
        Quantity secondQuantity = new Quantity("20");
        assertNotEquals(firstQuantity, secondQuantity);
    }
}
```
###### /java/seedu/address/model/theme/ThemeTest.java
``` java
package seedu.address.model.theme;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class ThemeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Theme(null));
    }

    @Test
    public void constructor_invalidTheme_throwsIllegalArgumentException() {
        String invalidTheme = "39dhks";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Theme(invalidTheme));
    }

    @Test
    public void equals() {
        Theme firstTheme = new Theme("dark");

        // same theme object -> true
        assertTrue(firstTheme.equals(firstTheme));

        // same theme value -> true
        Theme firstThemeCopy = new Theme("dark");
        assertTrue(firstTheme.equals(firstThemeCopy));

        // different type -> false
        assertFalse(firstTheme.equals(1));

        // null -> false
        assertFalse(firstTheme.equals(null));
    }

    @Test
    public void isValidTheme() {
        // null theme
        Assert.assertThrows(NullPointerException.class, () -> Theme.isValidTheme(null));

        // invalid theme
        assertFalse(Theme.isValidTheme("")); // empty string
        assertFalse(Theme.isValidTheme(" ")); // spaces only
        assertFalse(Theme.isValidTheme("djwe398")); // invalid keywords for theme

        // valid theme
        assertTrue(Theme.isValidTheme("dark")); // dark theme
        assertTrue(Theme.isValidTheme("light")); // light theme
    }

    @Test
    public void setTheme_switchBetweenThemes() {
        // change to 'dark' -> changed successfully
        String theme = "dark";
        Theme.setCurrentTheme(theme);
        assertEquals(Theme.getCurrentTheme(), theme);

        // change to 'light' -> changed successfully
        theme = "light";
        Theme.setCurrentTheme(theme);
        assertEquals(Theme.getCurrentTheme(), theme);

        // change to 'day' (doesn't exist) -> not changed from previous theme
        theme = "day";
        Theme.setCurrentTheme(theme);
        assertFalse(Theme.getCurrentTheme().equals(theme));
        assertEquals(Theme.getCurrentTheme(), "light");
    }
}
```
###### /java/seedu/address/model/UniqueGroupListTest.java
``` java
    @Test
    public void equals() throws UniqueGroupList.DuplicateGroupException {
        UniqueGroupList firstGroupList = new UniqueGroupList();
        firstGroupList.add(FRIENDS);
        UniqueGroupList secondGroupList = new UniqueGroupList();
        secondGroupList.add(COLLEAGUES);

        // same object -> true
        assertTrue(firstGroupList.equals(firstGroupList));

        // different type -> false
        assertFalse(firstGroupList.equals(1));

        // different objects, same type -> false
        assertFalse(firstGroupList.equals(secondGroupList));
    }
```
###### /java/seedu/address/model/UniqueGroupListTest.java
``` java
    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueGroupList uniqueGroupList = new UniqueGroupList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueGroupList.asObservableList().remove(0);
    }

    @Test
    public void asUniqueList_addDuplicateGroup_throwsDuplicateGroupException()
            throws UniqueGroupList.DuplicateGroupException {
        UniqueGroupList uniqueGroupList = new UniqueGroupList();
        thrown.expect(UniqueGroupList.DuplicateGroupException.class);
        uniqueGroupList.add(FRIENDS);
        uniqueGroupList.add(FRIENDS);
    }
```
###### /java/seedu/address/model/UniqueOrderListTest.java
``` java
package seedu.address.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalOrders.BOOKS;
import static seedu.address.testutil.TypicalOrders.CHOCOLATES;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.order.UniqueOrderList;
import seedu.address.model.order.exceptions.DuplicateOrderException;

public class UniqueOrderListTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void equals() throws DuplicateOrderException {
        UniqueOrderList firstOrderList = new UniqueOrderList();
        firstOrderList.add(BOOKS);
        UniqueOrderList secondOrderList = new UniqueOrderList();
        secondOrderList.add(CHOCOLATES);

        // same object -> true
        assertTrue(firstOrderList.equals(firstOrderList));

        // different type -> false
        assertFalse(firstOrderList.equals(1));

        // different objects, same type -> false
        assertFalse(firstOrderList.equals(secondOrderList));
    }

    @Test
    public void asOrderInsensitiveList_compareListsWithSameItemsInDiffOrder_assertEqual()
            throws DuplicateOrderException {
        UniqueOrderList firstOrderList = new UniqueOrderList();
        firstOrderList.add(BOOKS);
        firstOrderList.add(CHOCOLATES);
        UniqueOrderList secondOrderList = new UniqueOrderList();
        secondOrderList.add(CHOCOLATES);
        secondOrderList.add(BOOKS);

        assertTrue(firstOrderList.equalsOrderInsensitive(secondOrderList));
    }

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueOrderList uniqueOrderList = new UniqueOrderList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueOrderList.asObservableList().remove(0);
    }

    @Test
    public void asUniqueList_addDuplicateOrder_throwsDuplicateOrderException()
            throws DuplicateOrderException {
        UniqueOrderList uniqueOrderList = new UniqueOrderList();
        thrown.expect(DuplicateOrderException.class);
        uniqueOrderList.add(BOOKS);
        uniqueOrderList.add(BOOKS);
    }
}
```
###### /java/seedu/address/model/UniquePreferenceListTest.java
``` java
    @Test
    public void equals() throws UniquePreferenceList.DuplicatePreferenceException {
        UniquePreferenceList firstPrefList = new UniquePreferenceList();
        firstPrefList.add(VIDEO_GAMES);
        UniquePreferenceList secondPrefList = new UniquePreferenceList();
        secondPrefList.add(COMPUTERS);

        // same object -> true
        assertTrue(firstPrefList.equals(firstPrefList));

        // different type -> false
        assertFalse(firstPrefList.equals(1));

        // different objects, same type -> false
        assertFalse(firstPrefList.equals(secondPrefList));
    }
```
###### /java/seedu/address/model/UniquePreferenceListTest.java
``` java
    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniquePreferenceList uniquePreferenceList = new UniquePreferenceList();
        thrown.expect(UnsupportedOperationException.class);
        uniquePreferenceList.asObservableList().remove(0);
    }

    @Test
    public void asUniqueList_addDuplicatePref_throwsDuplicatePreferenceException()
            throws UniquePreferenceList.DuplicatePreferenceException {
        UniquePreferenceList uniquePrefList = new UniquePreferenceList();
        thrown.expect(UniquePreferenceList.DuplicatePreferenceException.class);
        uniquePrefList.add(SHOES);
        uniquePrefList.add(SHOES);
    }
```
###### /java/seedu/address/storage/XmlAdaptedOrderTest.java
``` java
package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.XmlAdaptedOrder.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalOrders.BOOKS;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.order.DeliveryDate;
import seedu.address.model.order.OrderInformation;
import seedu.address.model.order.OrderStatus;
import seedu.address.model.order.Price;
import seedu.address.model.order.Quantity;
import seedu.address.testutil.Assert;

public class XmlAdaptedOrderTest {
    private static final String INVALID_ORDER_INFORMATION = "Choc0l@t3s";
    private static final String INVALID_ORDER_STATUS = "fulfilled";
    private static final String INVALID_PRICE = "25.00.99";
    private static final String INVALID_QUANTITY = "-2";
    private static final String INVALID_DELIVERY_DATE = "50-12-2010";

    private static final String VALID_ORDER_INFORMATION = BOOKS.getOrderInformation().toString();
    private static final String VALID_ORDER_STATUS = BOOKS.getOrderStatus().getOrderStatusValue();
    private static final String VALID_PRICE = BOOKS.getPrice().toString();
    private static final String VALID_QUANTITY = BOOKS.getQuantity().toString();
    private static final String VALID_DELIVERY_DATE = BOOKS.getDeliveryDate().toString();

    @Test
    public void toModelType_validOrderDetails_returnsOrder() throws Exception {
        XmlAdaptedOrder order = new XmlAdaptedOrder(BOOKS);
        assertEquals(BOOKS, order.toModelType());
    }

    @Test
    public void toModelType_invalidOrderInformation_throwsIllegalValueException() {
        XmlAdaptedOrder order = new XmlAdaptedOrder(INVALID_ORDER_INFORMATION, VALID_ORDER_STATUS, VALID_PRICE,
                VALID_QUANTITY, VALID_DELIVERY_DATE);
        String expectedMessage = OrderInformation.MESSAGE_ORDER_INFORMATION_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_nullOrderInformation_throwsIllegalValueException() {
        XmlAdaptedOrder order = new XmlAdaptedOrder(null, VALID_ORDER_STATUS, VALID_PRICE,
                VALID_QUANTITY, VALID_DELIVERY_DATE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, OrderInformation.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_invalidOrderStatus_throwsIllegalValueException() {
        XmlAdaptedOrder order = new XmlAdaptedOrder(VALID_ORDER_INFORMATION, INVALID_ORDER_STATUS, VALID_PRICE,
                VALID_QUANTITY, VALID_DELIVERY_DATE);
        String expectedMessage = OrderStatus.MESSAGE_ORDER_STATUS_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_nullOrderStatus_throwsIllegalValueException() {
        XmlAdaptedOrder order = new XmlAdaptedOrder(VALID_ORDER_INFORMATION, null, VALID_PRICE,
                VALID_QUANTITY, VALID_DELIVERY_DATE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, OrderStatus.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_invalidPrice_throwsIllegalValueException() {
        XmlAdaptedOrder order = new XmlAdaptedOrder(VALID_ORDER_INFORMATION, VALID_ORDER_STATUS, INVALID_PRICE,
                VALID_QUANTITY, VALID_DELIVERY_DATE);
        String expectedMessage = Price.MESSAGE_PRICE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_nullPrice_throwsIllegalValueException() {
        XmlAdaptedOrder order = new XmlAdaptedOrder(VALID_ORDER_INFORMATION, VALID_ORDER_STATUS, null,
                VALID_QUANTITY, VALID_DELIVERY_DATE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Price.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_invalidQuantity_throwsIllegalValueException() {
        XmlAdaptedOrder order = new XmlAdaptedOrder(VALID_ORDER_INFORMATION, VALID_ORDER_STATUS, VALID_PRICE,
                INVALID_QUANTITY, VALID_DELIVERY_DATE);
        String expectedMessage = Quantity.MESSAGE_QUANTITY_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_nullQuantity_throwsIllegalValueException() {
        XmlAdaptedOrder order = new XmlAdaptedOrder(VALID_ORDER_INFORMATION, VALID_ORDER_STATUS, VALID_PRICE,
                null, VALID_DELIVERY_DATE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Quantity.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_invalidDeliveryDate_throwsIllegalValueException() {
        XmlAdaptedOrder order = new XmlAdaptedOrder(VALID_ORDER_INFORMATION, VALID_ORDER_STATUS, VALID_PRICE,
                VALID_QUANTITY, INVALID_DELIVERY_DATE);
        String expectedMessage = DeliveryDate.MESSAGE_DELIVERY_DATE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_nullDeliveryDate_throwsIllegalValueException() {
        XmlAdaptedOrder order = new XmlAdaptedOrder(VALID_ORDER_INFORMATION, VALID_ORDER_STATUS, VALID_PRICE,
                VALID_QUANTITY, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, DeliveryDate.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }
}
```
###### /java/seedu/address/storage/XmlSerializableAddressBookTest.java
``` java
    @Test
    public void toModelType_typicalOrdersFile_success() throws Exception {
        XmlSerializableAddressBook dataFromFile = XmlUtil.getDataFromFile(TYPICAL_ORDERS_FILE,
                XmlSerializableAddressBook.class);
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        AddressBook typicalOrdersAddressBook = TypicalOrders.getTypicalAddressBookWithOrders();
        assertEquals(addressBookFromFile, typicalOrdersAddressBook);
    }
```
###### /java/seedu/address/storage/XmlSerializableAddressBookTest.java
``` java
    @Test
    public void toModelType_invalidOrderFile_throwsIllegalValueException() throws Exception {
        XmlSerializableAddressBook dataFromFile = XmlUtil.getDataFromFile(INVALID_ORDER_FILE,
                XmlSerializableAddressBook.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }
```
###### /java/seedu/address/testutil/EditOrderDescriptorBuilder.java
``` java
package seedu.address.testutil;

import seedu.address.logic.commands.EditOrderCommand.EditOrderDescriptor;
import seedu.address.model.order.DeliveryDate;
import seedu.address.model.order.Order;
import seedu.address.model.order.OrderInformation;
import seedu.address.model.order.OrderStatus;
import seedu.address.model.order.Price;
import seedu.address.model.order.Quantity;

/**
 * A utility class to help with building EditOrderDescriptor objects.
 */
public class EditOrderDescriptorBuilder {

    private EditOrderDescriptor descriptor;

    public EditOrderDescriptorBuilder() {
        descriptor = new EditOrderDescriptor();
    }

    public EditOrderDescriptorBuilder(EditOrderDescriptor descriptor) {
        this.descriptor = new EditOrderDescriptor(descriptor);
    }

    public EditOrderDescriptorBuilder(Order order) {
        descriptor = new EditOrderDescriptor();
        descriptor.setOrderInformation(order.getOrderInformation());
        descriptor.setOrderStatus(order.getOrderStatus());
        descriptor.setPrice(order.getPrice());
        descriptor.setQuantity(order.getQuantity());
        descriptor.setDeliveryDate(order.getDeliveryDate());
    }

    /**
     * Sets the {@code OrderInformation} of the {@code EditOrderDescriptor} that we are building.
     */
    public EditOrderDescriptorBuilder withOrderInformation(String orderInformation) {
        descriptor.setOrderInformation(new OrderInformation(orderInformation));
        return this;
    }

    /**
     * Sets the {@code OrderStatus} of the {@code EditOrderDescriptor} that we are building.
     */
    public EditOrderDescriptorBuilder withOrderStatus(String orderStatus) {
        descriptor.setOrderStatus(new OrderStatus(orderStatus));
        return this;
    }

    /**
     * Sets the {@code Price} of the {@code EditOrderDescriptor} that we are building.
     */
    public EditOrderDescriptorBuilder withPrice(String price) {
        descriptor.setPrice(new Price(price));
        return this;
    }

    /**
     * Sets the {@code Quantity} of the {@code EditOrderDescriptor} that we are building.
     */
    public EditOrderDescriptorBuilder withQuantity(String quantity) {
        descriptor.setQuantity(new Quantity(quantity));
        return this;
    }

    /**
     * Sets the {@code DeliveryDate} of the {@code EditOrderDescriptor} that we are building.
     */
    public EditOrderDescriptorBuilder withDeliveryDate(String deliveryDate) {
        descriptor.setDeliveryDate(new DeliveryDate(deliveryDate));
        return this;
    }

    public EditOrderDescriptor build() {
        return descriptor;
    }
}
```
###### /java/seedu/address/testutil/OrderBuilder.java
``` java
package seedu.address.testutil;

import seedu.address.model.order.DeliveryDate;
import seedu.address.model.order.Order;
import seedu.address.model.order.OrderInformation;
import seedu.address.model.order.OrderStatus;
import seedu.address.model.order.Price;
import seedu.address.model.order.Quantity;

/**
 * A utility class to help with building Order objects.
 */
public class OrderBuilder {

    public static final String DEFAULT_ORDER_INFORMATION = "Books";
    public static final String DEFAULT_ORDER_STATUS = "ongoing";
    public static final String DEFAULT_PRICE = "15.00";
    public static final String DEFAULT_QUANTITY = "5";
    public static final String DEFAULT_DELIVERY_DATE = "10-05-2018";

    private OrderInformation orderInformation;
    private OrderStatus orderStatus;
    private Price price;
    private Quantity quantity;
    private DeliveryDate deliveryDate;

    public OrderBuilder() {
        orderInformation = new OrderInformation(DEFAULT_ORDER_INFORMATION);
        orderStatus = new OrderStatus(DEFAULT_ORDER_STATUS);
        price = new Price(DEFAULT_PRICE);
        quantity = new Quantity(DEFAULT_QUANTITY);
        deliveryDate = new DeliveryDate(DEFAULT_DELIVERY_DATE);
    }

    /**
     * Initializes the OrderBuilder with the data of {@code orderToCopy}.
     */
    public OrderBuilder(Order orderToCopy) {
        orderInformation = orderToCopy.getOrderInformation();
        orderStatus = orderToCopy.getOrderStatus();
        price = orderToCopy.getPrice();
        quantity = orderToCopy.getQuantity();
        deliveryDate = orderToCopy.getDeliveryDate();
    }

    /**
     * Sets the {@code OrderInformation} of the {@code Order} that we are building.
     */
    public OrderBuilder withOrderInformation(String orderInformation) {
        this.orderInformation = new OrderInformation(orderInformation);
        return this;
    }

    /**
     * Sets the {@code OrderStatus} of the {@code Order} that we are building.
     */
    public OrderBuilder withOrderStatus(String orderStatus) {
        this.orderStatus = new OrderStatus(orderStatus);
        return this;
    }

    /**
     * Sets the {@code Price} of the {@code Order} that we are building.
     */
    public OrderBuilder withPrice(String price) {
        this.price = new Price(price);
        return this;
    }

    /**
     * Sets the {@code Quantity} of the {@code Order} that we are building.
     */
    public OrderBuilder withQuantity(String quantity) {
        this.quantity = new Quantity(quantity);
        return this;
    }

    /**
     * Sets the {@code DeliveryDate} of the {@code Order} that we are building.
     */
    public OrderBuilder withDeliveryDate(String deliveryDate) {
        this.deliveryDate = new DeliveryDate(deliveryDate);
        return this;
    }

    public Order build() {
        return new Order(orderInformation, orderStatus, price, quantity, deliveryDate);
    }
}
```
###### /java/seedu/address/testutil/OrderUtil.java
``` java
package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DELIVERY_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORDER_INFORMATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;

import seedu.address.logic.commands.AddOrderCommand;
import seedu.address.model.order.Order;

/**
 * A utility class for Order.
 */
public class OrderUtil {

    /**
     * Returns an add order command string for adding the {@code order}.
     */
    public static String getAddOrderCommand(int index, Order order) {
        return AddOrderCommand.COMMAND_WORD + " " + index + " " + getOrderDetails(order);
    }

    /**
     * Returns the part of command string for the given {@code orders}'s details.
     */
    public static String getOrderDetails(Order order) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_ORDER_INFORMATION).append(order.getOrderInformation().toString()).append(" ");
        sb.append(PREFIX_PRICE).append(order.getPrice().toString()).append(" ");
        sb.append(PREFIX_QUANTITY).append(order.getQuantity().toString()).append(" ");
        sb.append(PREFIX_DELIVERY_DATE).append(order.getDeliveryDate().toString());
        return sb.toString();
    }
}
```
###### /java/seedu/address/testutil/TestUtil.java
``` java
    /**
     * Returns the middle index of the order in the {@code model}'s order list.
     */
    public static Index getMidOrderIndex(Model model) {
        return Index.fromOneBased(model.getAddressBook().getOrderList().size() / 2);
    }

    /**
     * Returns the last index of the order in the {@code model}'s order list.
     */
    public static Index getLastOrderIndex(Model model) {
        return Index.fromOneBased(model.getAddressBook().getOrderList().size());
    }
```
###### /java/seedu/address/testutil/TestUtil.java
``` java

    /**
     * Returns the person in the {@code model}'s person list at {@code index}.
     */
    public static Person getPerson(Model model, Index index) {
        return model.getAddressBook().getPersonList().get(index.getZeroBased());
    }

```
###### /java/seedu/address/testutil/TestUtil.java
``` java
    /**
     * Returns the order in the {@code model}'s order list at {@code index}.
     */
    public static Order getOrder(Model model, Index index) {
        return model.getAddressBook().getOrderList().get(index.getZeroBased());
    }
```
###### /java/seedu/address/testutil/TypicalOrders.java
``` java
package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_DELIVERY_DATE_BOOKS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DELIVERY_DATE_CHOC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DELIVERY_DATE_COMPUTER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ORDER_INFORMATION_BOOKS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ORDER_INFORMATION_CHOC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ORDER_INFORMATION_COMPUTER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ORDER_STATUS_BOOKS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ORDER_STATUS_CHOC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ORDER_STATUS_COMPUTER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_BOOKS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_CHOC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_COMPUTER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_BOOKS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_CHOC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_COMPUTER;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.order.Order;
import seedu.address.model.order.exceptions.DuplicateOrderException;
import seedu.address.model.person.exceptions.DuplicatePersonException;

/**
 * A utility class containing a list of {@code Order} objects to be used in tests.
 */
public class TypicalOrders {

    public static final Order SHOES = new OrderBuilder()
            .withOrderInformation("Shoes")
            .withOrderStatus("ongoing")
            .withPrice("129.99")
            .withQuantity("3")
            .withDeliveryDate("10-09-2018")
            .build();

    public static final Order FACEWASH = new OrderBuilder()
            .withOrderInformation("Face Wash")
            .withOrderStatus("ongoing")
            .withPrice("24.75")
            .withQuantity("1")
            .withDeliveryDate("05-11-2018")
            .build();

    public static final Order BOOKS = new OrderBuilder()
            .withOrderInformation(VALID_ORDER_INFORMATION_BOOKS)
            .withOrderStatus(VALID_ORDER_STATUS_BOOKS)
            .withPrice(VALID_PRICE_BOOKS)
            .withQuantity(VALID_QUANTITY_BOOKS)
            .withDeliveryDate(VALID_DELIVERY_DATE_BOOKS)
            .build();

    public static final Order CHOCOLATES = new OrderBuilder()
            .withOrderInformation(VALID_ORDER_INFORMATION_CHOC)
            .withOrderStatus(VALID_ORDER_STATUS_CHOC)
            .withPrice(VALID_PRICE_CHOC)
            .withQuantity(VALID_QUANTITY_CHOC)
            .withDeliveryDate(VALID_DELIVERY_DATE_CHOC)
            .build();

    public static final Order COMPUTER = new OrderBuilder()
            .withOrderInformation(VALID_ORDER_INFORMATION_COMPUTER)
            .withOrderStatus(VALID_ORDER_STATUS_COMPUTER)
            .withPrice(VALID_PRICE_COMPUTER)
            .withQuantity(VALID_QUANTITY_COMPUTER)
            .withDeliveryDate(VALID_DELIVERY_DATE_COMPUTER)
            .build();

    public static final Order COMICBOOK = new OrderBuilder()
            .withOrderInformation("Comic Book")
            .withOrderStatus("ongoing")
            .withPrice("17.99")
            .withQuantity("1")
            .withDeliveryDate("01-01-2018")
            .build();

    private TypicalOrders() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with one person and all typical orders.
     */
    public static AddressBook getTypicalAddressBookWithOrders() {
        AddressBook ab = new AddressBook();

        try {
            ab.addPerson(ALICE);
        } catch (DuplicatePersonException dpe) {
            throw new AssertionError("not possible");
        }

        for (Order order : getTypicalOrders()) {
            try {
                ab.addOrderToOrderList(order);
            } catch (DuplicateOrderException doe) {
                throw new AssertionError("not possible");
            }
        }
        return ab;
    }

    public static List<Order> getTypicalOrders() {
        return new ArrayList<>(Arrays.asList(SHOES, FACEWASH, BOOKS, CHOCOLATES));
    }
}
```
###### /java/seedu/address/ui/CommandBoxTest.java
``` java
    @Test
    public void handleKeyPress_tab() {
        // add command
        commandBoxHandle.setInput(COMMAND_ADD_INCOMPLETE);
        assertInputHistory(KeyCode.TAB, COMMAND_ADD_COMPLETE);

        // edit command
        commandBoxHandle.setInput(COMMAND_EDIT_INCOMPLETE);
        assertInputHistory(KeyCode.TAB, COMMAND_EDIT_COMPLETE);

        // invalid command
        commandBoxHandle.setInput(COMMAND_THAT_FAILS);
        assertInputHistory(KeyCode.TAB, COMMAND_THAT_FAILS);
    }
```
###### /java/seedu/address/ui/OrderCardTest.java
``` java
package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysOrder;

import org.junit.Test;

import guitests.guihandles.OrderCardHandle;
import seedu.address.model.order.Order;
import seedu.address.testutil.OrderBuilder;

public class OrderCardTest extends GuiUnitTest {

    @Test
    public void display() {
        Order order = new OrderBuilder().build();
        OrderCard orderCard = new OrderCard(order, 1);
        uiPartRule.setUiPart(orderCard);
        assertCardDisplay(orderCard, order, 1);
    }

    @Test
    public void equals() {
        Order order = new OrderBuilder().build();
        OrderCard orderCard = new OrderCard(order, 0);

        // same order, same index -> returns true
        OrderCard orderCardCopy = new OrderCard(order, 0);
        assertTrue(orderCard.equals(orderCardCopy));

        // same object -> returns true
        assertTrue(orderCard.equals(orderCard));

        // null -> returns false
        assertFalse(orderCard.equals(null));

        // different types -> returns false
        assertFalse(orderCard.equals(1));

        // different order, same index -> returns false
        Order differentOrder = new OrderBuilder().withOrderInformation("differentOrderInfo").build();
        assertFalse(orderCard.equals(new OrderCard(differentOrder, 0)));

        // same order, different index -> returns false
        assertFalse(orderCard.equals(new OrderCard(order, 1)));

    }

    /**
     * Asserts that {@code orderCard} displays the details of {@code expectedOrder} correctly and
     * matches {@code expectedId}.
     */
    private void assertCardDisplay(OrderCard orderCard, Order expectedOrder, int expectedId) {
        guiRobot.pauseForHuman();

        OrderCardHandle orderCardHandle = new OrderCardHandle(orderCard.getRoot());

        // verify that id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", orderCardHandle.getId());

        // verify order details are displayed correctly
        assertCardDisplaysOrder(expectedOrder, orderCardHandle);
    }
}
```
###### /java/seedu/address/ui/OrderListPanelTest.java
``` java
package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalOrders.getTypicalOrders;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysOrder;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.OrderCardHandle;
import guitests.guihandles.OrderListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.order.Order;

public class OrderListPanelTest extends GuiUnitTest {
    private static final ObservableList<Order> TYPICAL_ORDERS =
            FXCollections.observableList(getTypicalOrders());

    private OrderListPanelHandle orderListPanelHandle;

    @Before
    public void setUp() {
        OrderListPanel orderListPanel = new OrderListPanel(TYPICAL_ORDERS);
        uiPartRule.setUiPart(orderListPanel);

        orderListPanelHandle = new OrderListPanelHandle(getChildNode(orderListPanel.getRoot(),
                OrderListPanelHandle.ORDER_LIST_VIEW_ID));
    }

    @Test
    public void display() {
        for (int i = 0; i < TYPICAL_ORDERS.size(); i++) {
            orderListPanelHandle.navigateToCard(TYPICAL_ORDERS.get(i));
            Order expectedOrder = TYPICAL_ORDERS.get(i);
            OrderCardHandle actualCard = orderListPanelHandle.getOrderCardHandle(i);

            assertCardDisplaysOrder(expectedOrder, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }
}
```
###### /java/systemtests/AddOrderCommandSystemTest.java
``` java
package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.DELIVERY_DATE_DESC_CHOC;
import static seedu.address.logic.commands.CommandTestUtil.DELIVERY_DATE_DESC_COMPUTER;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DELIVERY_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ORDER_INFORMATION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PRICE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_QUANTITY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.ORDER_INFORMATION_DESC_CHOC;
import static seedu.address.logic.commands.CommandTestUtil.ORDER_INFORMATION_DESC_COMPUTER;
import static seedu.address.logic.commands.CommandTestUtil.PRICE_DESC_CHOC;
import static seedu.address.logic.commands.CommandTestUtil.PRICE_DESC_COMPUTER;
import static seedu.address.logic.commands.CommandTestUtil.QUANTITY_DESC_CHOC;
import static seedu.address.logic.commands.CommandTestUtil.QUANTITY_DESC_COMPUTER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DELIVERY_DATE_CHOC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DELIVERY_DATE_COMPUTER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ORDER_INFORMATION_CHOC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ORDER_INFORMATION_COMPUTER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_CHOC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_COMPUTER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_CHOC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_COMPUTER;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalOrders.COMICBOOK;
import static seedu.address.testutil.TypicalOrders.COMPUTER;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddOrderCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.order.DeliveryDate;
import seedu.address.model.order.Order;
import seedu.address.model.order.OrderInformation;
import seedu.address.model.order.Price;
import seedu.address.model.order.Quantity;
import seedu.address.model.order.exceptions.DuplicateOrderException;
import seedu.address.model.person.Person;
import seedu.address.testutil.OrderBuilder;
import seedu.address.testutil.OrderUtil;

public class AddOrderCommandSystemTest extends AddressBookSystemTest {
    @Test
    public void addOrder() throws Exception {
        Model model = getModel();

        /* --------------------- Perform addOrder operations on the shown unfiltered list -------------------------- */

        Index index = INDEX_FIRST_PERSON;
        Order toAdd = COMPUTER;

        /* Case: add an order to a non-empty address book,
         * command with leading spaces and trailing spaces -> added
         */
        String command = "   " + AddOrderCommand.COMMAND_WORD + " " + index.getOneBased() + " "
                + ORDER_INFORMATION_DESC_COMPUTER + "   " + PRICE_DESC_COMPUTER + "   " + QUANTITY_DESC_COMPUTER + "  "
                + DELIVERY_DATE_DESC_COMPUTER + "   ";
        assertCommandSuccess(command, index, toAdd);

        /* Case: undo adding Books to the list -> Books deleted */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding Books to the list -> Books added again */
        command = RedoCommand.COMMAND_WORD;
        model.addOrderToOrderList(toAdd);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: add order with all fields same as another order in address book except order information -> added */
        toAdd = new OrderBuilder()
                .withOrderInformation(VALID_ORDER_INFORMATION_CHOC)
                .withPrice(VALID_PRICE_COMPUTER)
                .withQuantity(VALID_QUANTITY_COMPUTER)
                .withDeliveryDate(VALID_DELIVERY_DATE_COMPUTER)
                .build();
        command = AddOrderCommand.COMMAND_WORD + " " + index.getOneBased()
                + ORDER_INFORMATION_DESC_CHOC + PRICE_DESC_COMPUTER
                + QUANTITY_DESC_COMPUTER + DELIVERY_DATE_DESC_COMPUTER;
        assertCommandSuccess(command, index, toAdd);

        /* Case: add order with all fields same as another order in address book except price -> added */
        toAdd = new OrderBuilder()
                .withOrderInformation(VALID_ORDER_INFORMATION_COMPUTER)
                .withPrice(VALID_PRICE_CHOC)
                .withQuantity(VALID_QUANTITY_COMPUTER)
                .withDeliveryDate(VALID_DELIVERY_DATE_COMPUTER)
                .build();
        command = AddOrderCommand.COMMAND_WORD + " " + index.getOneBased()
                + ORDER_INFORMATION_DESC_COMPUTER + PRICE_DESC_CHOC
                + QUANTITY_DESC_COMPUTER + DELIVERY_DATE_DESC_COMPUTER;
        assertCommandSuccess(command, index, toAdd);

        /* Case: add order with all fields same as another order in address book except quantity -> added */
        toAdd = new OrderBuilder()
                .withOrderInformation(VALID_ORDER_INFORMATION_COMPUTER)
                .withPrice(VALID_PRICE_COMPUTER)
                .withQuantity(VALID_QUANTITY_CHOC)
                .withDeliveryDate(VALID_DELIVERY_DATE_COMPUTER)
                .build();
        command = AddOrderCommand.COMMAND_WORD + " " + index.getOneBased()
                + ORDER_INFORMATION_DESC_COMPUTER + PRICE_DESC_COMPUTER
                + QUANTITY_DESC_CHOC + DELIVERY_DATE_DESC_COMPUTER;
        assertCommandSuccess(command, index, toAdd);

        /* Case: add order with all fields same as another order in address book except delivery date -> added */
        toAdd = new OrderBuilder()
                .withOrderInformation(VALID_ORDER_INFORMATION_COMPUTER)
                .withPrice(VALID_PRICE_COMPUTER)
                .withQuantity(VALID_QUANTITY_COMPUTER)
                .withDeliveryDate(VALID_DELIVERY_DATE_CHOC)
                .build();
        command = AddOrderCommand.COMMAND_WORD + " " + index.getOneBased()
                + ORDER_INFORMATION_DESC_COMPUTER + PRICE_DESC_COMPUTER
                + QUANTITY_DESC_COMPUTER + DELIVERY_DATE_DESC_CHOC;
        assertCommandSuccess(command, index, toAdd);

        /* Case: add an order command with parameters in random order -> added */
        toAdd = COMICBOOK;
        command = AddOrderCommand.COMMAND_WORD + " " + index.getOneBased()
                + " d/01-01-2018" + " i/Comic Book" + " q/1" + " pr/17.99";
        assertCommandSuccess(command, index, toAdd);

        /* --------------------- Perform addOrder operations on the shown filtered list -------------------------- */

        /* --------------------------------- Perform invalid addOrder operations --------------------------------- */

        /* Case: missing person index -> rejected */
        command = AddOrderCommand.COMMAND_WORD + ORDER_INFORMATION_DESC_COMPUTER
                + PRICE_DESC_COMPUTER + QUANTITY_DESC_COMPUTER
                + DELIVERY_DATE_DESC_COMPUTER;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddOrderCommand.MESSAGE_USAGE));

        /* Case: missing order information -> rejected */
        command = AddOrderCommand.COMMAND_WORD + " " + index.getOneBased()
                + PRICE_DESC_COMPUTER + QUANTITY_DESC_COMPUTER
                + DELIVERY_DATE_DESC_COMPUTER;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddOrderCommand.MESSAGE_USAGE));

        /* Case: missing price -> rejected */
        command = AddOrderCommand.COMMAND_WORD + " " + index.getOneBased() + ORDER_INFORMATION_DESC_COMPUTER
                + QUANTITY_DESC_COMPUTER + DELIVERY_DATE_DESC_COMPUTER;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddOrderCommand.MESSAGE_USAGE));

        /* Case: missing quantity -> rejected */
        command = AddOrderCommand.COMMAND_WORD + " " + index.getOneBased() + ORDER_INFORMATION_DESC_COMPUTER
                + PRICE_DESC_COMPUTER + DELIVERY_DATE_DESC_COMPUTER;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddOrderCommand.MESSAGE_USAGE));

        /* Case: missing delivery date -> rejected */
        command = AddOrderCommand.COMMAND_WORD + " " + index.getOneBased() + ORDER_INFORMATION_DESC_COMPUTER
                + PRICE_DESC_COMPUTER + QUANTITY_DESC_COMPUTER;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddOrderCommand.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        command = "ordersadds " + " " + index.getOneBased() + OrderUtil.getOrderDetails(toAdd);
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid order information -> rejected */
        command = AddOrderCommand.COMMAND_WORD + " " + index.getOneBased()
                + INVALID_ORDER_INFORMATION_DESC + PRICE_DESC_COMPUTER
                + QUANTITY_DESC_COMPUTER + DELIVERY_DATE_DESC_COMPUTER;
        assertCommandFailure(command, OrderInformation.MESSAGE_ORDER_INFORMATION_CONSTRAINTS);

        /* Case: invalid price -> rejected */
        command = AddOrderCommand.COMMAND_WORD + " " + index.getOneBased()
                + ORDER_INFORMATION_DESC_COMPUTER + INVALID_PRICE_DESC
                + QUANTITY_DESC_COMPUTER + DELIVERY_DATE_DESC_COMPUTER;
        assertCommandFailure(command, Price.MESSAGE_PRICE_CONSTRAINTS);

        /* Case: invalid quantity -> rejected */
        command = AddOrderCommand.COMMAND_WORD + " " + index.getOneBased()
                + ORDER_INFORMATION_DESC_COMPUTER + PRICE_DESC_COMPUTER
                + INVALID_QUANTITY_DESC + DELIVERY_DATE_DESC_COMPUTER;
        assertCommandFailure(command, Quantity.MESSAGE_QUANTITY_CONSTRAINTS);

        /* Case: invalid delivery date -> rejected */
        command = AddOrderCommand.COMMAND_WORD + " " + index.getOneBased()
                + ORDER_INFORMATION_DESC_COMPUTER + PRICE_DESC_COMPUTER
                + QUANTITY_DESC_COMPUTER + INVALID_DELIVERY_DATE_DESC;
        assertCommandFailure(command, DeliveryDate.MESSAGE_DELIVERY_DATE_CONSTRAINTS);
    }
```
###### /java/systemtests/AddOrderCommandSystemTest.java
``` java
    /**
     * Performs the same verification as {@code assertCommandSuccess(Index, Order)}. Executes {@code command}
     * instead.
     * @see AddOrderCommandSystemTest#assertCommandSuccess(Index, Order)
     */
    private void assertCommandSuccess(String command, Index index, Order toAdd) {
        Model expectedModel = getModel();
        Person person = expectedModel.getAddressBook().getPersonList().get(index.getZeroBased());

        try {
            expectedModel.addOrderToOrderList(toAdd);
        } catch (DuplicateOrderException dpe) {
            throw new IllegalArgumentException("toAdd already exists in the model.");
        }
        String expectedResultMessage = String.format(
                AddOrderCommand.MESSAGE_ADD_ORDER_SUCCESS, person.getName(), toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }
```
###### /java/systemtests/DeleteOrderCommandSystemTest.java
``` java
package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.DeleteOrderCommand.MESSAGE_DELETE_ORDER_SUCCESS;
import static seedu.address.testutil.TestUtil.getLastOrderIndex;
import static seedu.address.testutil.TestUtil.getMidOrderIndex;
import static seedu.address.testutil.TestUtil.getOrder;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ORDER;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteOrderCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.order.Order;
import seedu.address.model.order.exceptions.OrderNotFoundException;

public class DeleteOrderCommandSystemTest extends AddressBookSystemTest {

    private static final String MESSAGE_INVALID_DELETE_ORDER_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteOrderCommand.MESSAGE_USAGE);

    @Test
    public void delete() {
        /* ----------------- Performing delete operation while an unfiltered list is being shown -------------------- */

        /* Case: delete the first order in the list, command with leading spaces and trailing spaces -> deleted */
        Model expectedModel = getModel();
        String command = "     " + DeleteOrderCommand.COMMAND_WORD + "      "
                + INDEX_FIRST_ORDER.getOneBased() + "       ";
        Order deletedOrder = removeOrder(expectedModel, INDEX_FIRST_ORDER);
        String expectedResultMessage = String.format(MESSAGE_DELETE_ORDER_SUCCESS, deletedOrder);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: delete the last order in the list -> deleted */
        Model modelBeforeDeletingLast = getModel();
        Index lastOrderIndex = getLastOrderIndex(modelBeforeDeletingLast);
        assertCommandSuccess(lastOrderIndex);

        /* Case: undo deleting the last order in the list -> last order restored */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: redo deleting the last order in the list -> last order deleted again */
        command = RedoCommand.COMMAND_WORD;
        removeOrder(modelBeforeDeletingLast, lastOrderIndex);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: delete the middle order in the list -> deleted */
        Index middlePersonIndex = getMidOrderIndex(getModel());
        assertCommandSuccess(middlePersonIndex);

        /* --------------------------------- Performing invalid delete operations ----------------------------------- */

        /* Case: invalid index (0) -> rejected */
        command = DeleteOrderCommand.COMMAND_WORD + " 0";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_ORDER_COMMAND_FORMAT);

        /* Case: invalid index (-1) -> rejected */
        command = DeleteOrderCommand.COMMAND_WORD + " -1";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_ORDER_COMMAND_FORMAT);

        /* Case: invalid index (size + 1) -> rejected */
        Index outOfBoundsIndex = Index.fromOneBased(
                getModel().getAddressBook().getOrderList().size() + 1);
        command = DeleteOrderCommand.COMMAND_WORD + " " + outOfBoundsIndex.getOneBased();
        assertCommandFailure(command, MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(DeleteOrderCommand.COMMAND_WORD + " abc", MESSAGE_INVALID_DELETE_ORDER_COMMAND_FORMAT);

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(DeleteOrderCommand.COMMAND_WORD + " 1 abc", MESSAGE_INVALID_DELETE_ORDER_COMMAND_FORMAT);

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("DelETE 1", MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Removes the {@code Order} at the specified {@code index} in {@code model}'s address book.
     * @return the removed order
     */
    private Order removeOrder(Model model, Index index) {
        Order targetOrder = getOrder(model, index);
        try {
            model.deleteOrder(targetOrder);
        } catch (OrderNotFoundException onfe) {
            throw new AssertionError("targetOrder is retrieved from model.");
        }
        return targetOrder;
    }

    /**
     * Deletes the order at {@code toDelete} by creating a default {@code DeleteOrderCommand} using {@code toDelete}
     * and performs the same verification as {@code assertCommandSuccess(String, Model, String)}.
     * @see DeleteOrderCommandSystemTest#assertCommandSuccess(String, Model, String)
     */
    private void assertCommandSuccess(Index toDelete) {
        Model expectedModel = getModel();
        Order deletedOrder = removeOrder(expectedModel, toDelete);
        String expectedResultMessage = String.format(MESSAGE_DELETE_ORDER_SUCCESS, deletedOrder);

        assertCommandSuccess(
                DeleteOrderCommand.COMMAND_WORD + " "
                        + toDelete.getOneBased(), expectedModel, expectedResultMessage);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to {@code expectedModel}.<br>
     * 4. Asserts that the selected card remains unchanged.<br>
     * 5. Asserts that the status bar's sync status changes.<br>
     * 6. Asserts that the command box has the default style class.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }
```
###### /java/systemtests/EditOrderCommandSystemTest.java
``` java
package systemtests;

import static seedu.address.logic.commands.CommandTestUtil.DELIVERY_DATE_DESC_COMPUTER;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DELIVERY_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ORDER_INFORMATION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PRICE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_QUANTITY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.ORDER_INFORMATION_DESC_BOOKS;
import static seedu.address.logic.commands.CommandTestUtil.ORDER_INFORMATION_DESC_COMPUTER;
import static seedu.address.logic.commands.CommandTestUtil.PRICE_DESC_COMPUTER;
import static seedu.address.logic.commands.CommandTestUtil.QUANTITY_DESC_COMPUTER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DELIVERY_DATE_COMPUTER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ORDER_INFORMATION_COMPUTER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_COMPUTER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_COMPUTER;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ORDERS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ORDER;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_ORDER;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditOrderCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.order.DeliveryDate;
import seedu.address.model.order.Order;
import seedu.address.model.order.OrderInformation;
import seedu.address.model.order.Price;
import seedu.address.model.order.Quantity;
import seedu.address.model.order.exceptions.DuplicateOrderException;
import seedu.address.model.order.exceptions.OrderNotFoundException;
import seedu.address.testutil.OrderBuilder;

public class EditOrderCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void edit() throws Exception {
        Model model = getModel();

        /* ----------------- Performing edit operation while an unfiltered list is being shown ---------------------- */

        /* Case: edit all fields, command with leading spaces, trailing spaces and multiple spaces between each field
         * -> edited
         */
        Index index = INDEX_THIRD_ORDER;
        String command = " " + EditOrderCommand.COMMAND_WORD + "  " + index.getOneBased() + "    "
                + ORDER_INFORMATION_DESC_COMPUTER + " " + PRICE_DESC_COMPUTER + "  " + QUANTITY_DESC_COMPUTER
                + "    " + DELIVERY_DATE_DESC_COMPUTER;

        Order editedOrder = new OrderBuilder().withOrderInformation(VALID_ORDER_INFORMATION_COMPUTER)
                .withPrice(VALID_PRICE_COMPUTER).withQuantity(VALID_QUANTITY_COMPUTER)
                .withDeliveryDate(VALID_DELIVERY_DATE_COMPUTER).build();

        assertCommandSuccess(command, index, editedOrder);

        /* Case: undo editing the order in the list -> order restored */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo editing the last order in the list -> last order edited again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        model.updateOrder(
                getModel().getFilteredOrderList().get(INDEX_THIRD_ORDER.getZeroBased()), editedOrder);
        assertCommandSuccess(command, model, expectedResultMessage);

        /* --------------------------------- Performing invalid edit operation -------------------------------------- */

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(EditOrderCommand.COMMAND_WORD + " 0" + ORDER_INFORMATION_DESC_BOOKS,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditOrderCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(EditOrderCommand.COMMAND_WORD + " -1" + ORDER_INFORMATION_DESC_BOOKS,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditOrderCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected */
        int invalidIndex = getModel().getFilteredOrderList().size() + 1;
        assertCommandFailure(EditOrderCommand.COMMAND_WORD + " " + invalidIndex
                        + ORDER_INFORMATION_DESC_BOOKS,
                Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);

        /* Case: missing index -> rejected */
        assertCommandFailure(EditOrderCommand.COMMAND_WORD + ORDER_INFORMATION_DESC_BOOKS,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditOrderCommand.MESSAGE_USAGE));

        /* Case: missing all fields -> rejected */
        assertCommandFailure(EditOrderCommand.COMMAND_WORD + " " + INDEX_FIRST_ORDER.getOneBased(),
                EditOrderCommand.MESSAGE_NOT_EDITED);

        /* Case: invalid order information -> rejected */
        assertCommandFailure(EditOrderCommand.COMMAND_WORD + " " + INDEX_FIRST_ORDER.getOneBased()
                        + INVALID_ORDER_INFORMATION_DESC,
                OrderInformation.MESSAGE_ORDER_INFORMATION_CONSTRAINTS);

        /* Case: invalid price -> rejected */
        assertCommandFailure(EditOrderCommand.COMMAND_WORD + " " + INDEX_FIRST_ORDER.getOneBased()
                        + INVALID_PRICE_DESC,
                Price.MESSAGE_PRICE_CONSTRAINTS);

        /* Case: invalid quantity -> rejected */
        assertCommandFailure(EditOrderCommand.COMMAND_WORD + " " + INDEX_FIRST_ORDER.getOneBased()
                        + INVALID_QUANTITY_DESC,
                Quantity.MESSAGE_QUANTITY_CONSTRAINTS);

        /* Case: invalid delivery date -> rejected */
        assertCommandFailure(EditOrderCommand.COMMAND_WORD + " " + INDEX_FIRST_ORDER.getOneBased()
                        + INVALID_DELIVERY_DATE_DESC,
                DeliveryDate.MESSAGE_DELIVERY_DATE_CONSTRAINTS);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} except that the
     * selected card remains unchanged.
     * @see EditOrderCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Index, Person, Index)} except that
     * the selected card remains unchanged.
     * @param toEdit the index of the current model's filtered list
     * @see EditOrderCommandSystemTest#assertCommandSuccess(String, Index, Order, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Order editedOrder) {
        assertCommandSuccess(command, toEdit, editedOrder, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} and in addition,<br>
     * 1. Asserts that result display box displays the success message of executing {@code EditOrderCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the order at index {@code toEdit} being
     * updated to values specified {@code editedOrder}.<br>
     * @param toEdit the index of the current model's filtered list.
     * @see EditOrderCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Order editedOrder,
                                      Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        try {
            expectedModel.updateOrder(
                    expectedModel.getFilteredOrderList().get(toEdit.getZeroBased()), editedOrder);
            expectedModel.updateFilteredOrderList(PREDICATE_SHOW_ALL_ORDERS);
        } catch (DuplicateOrderException | OrderNotFoundException e) {
            throw new IllegalArgumentException(
                    "editedOrder is a duplicate in expectedModel, or it isn't found in the model.");
        }

        assertCommandSuccess(command, expectedModel,
                String.format(EditOrderCommand.MESSAGE_EDIT_ORDER_SUCCESS, editedOrder), expectedSelectedCardIndex);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to {@code expectedModel}.<br>
     * 4. Asserts that the selected card updates accordingly depending on the card at
     * {@code expectedSelectedCardIndex}.<br>
     * 5. Asserts that the status bar's sync status changes.<br>
     * 6. Asserts that the command box has the default style class.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see AddressBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
                                      Index expectedSelectedCardIndex) {
        executeCommand(command);
        expectedModel.updateFilteredOrderList(PREDICATE_SHOW_ALL_ORDERS);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }
        assertStatusBarUnchangedExceptSyncStatus();
    }
```
