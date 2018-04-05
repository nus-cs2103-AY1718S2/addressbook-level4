# amad-person
###### \java\guitests\guihandles\OrderCardHandle.java
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
###### \java\guitests\guihandles\OrderListPanelHandle.java
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
###### \java\seedu\address\logic\commands\AddOrderCommandTest.java
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
import seedu.address.model.event.CalendarEntry;
import seedu.address.model.event.exceptions.CalendarEntryNotFoundException;
import seedu.address.model.event.exceptions.DuplicateCalendarEntryException;
import seedu.address.model.order.Order;
import seedu.address.model.order.UniqueOrderList;
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
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class AddOrderCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new CalendarManager(), new UserPrefs());

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
        public void updateOrder(Order target, Order editedOrder) throws UniqueOrderList.DuplicateOrderException {
            fail("This method should not be called.");
        }

        @Override
        public void updateOrderStatus(Order target, String orderStatus)
                throws UniqueOrderList.DuplicateOrderException, OrderNotFoundException {
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
        public ObservableList<CalendarEntry> getFilteredCalendarEventList() {
            return model.getFilteredCalendarEventList();
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
        public void addOrderToOrderList(Order orderToAdd) throws UniqueOrderList.DuplicateOrderException {
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
    }

    /**
     * A Model stub that always throws a DuplicatePersonException when trying to add a person.
     */
    private class ModelStubThrowingDuplicateOrderException extends ModelStub {
        @Override
        public void addOrderToOrderList(Order order) throws UniqueOrderList.DuplicateOrderException {
            throw new UniqueOrderList.DuplicateOrderException();
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
        public void addOrderToOrderList(Order order) throws UniqueOrderList.DuplicateOrderException {
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
###### \java\seedu\address\logic\commands\ChangeThemeCommandTest.java
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
    public ExpectedException thrown = ExpectedException.none();

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

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new CalendarManager(), expectedUserPrefs);
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

        expectedModel = new ModelManager(getTypicalAddressBook(), new CalendarManager(), expectedUserPrefs);
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
###### \java\seedu\address\logic\commands\DeleteOrderCommandTest.java
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

    private Model model = new ModelManager(getTypicalAddressBookWithOrders(), new CalendarManager(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        Order orderToDelete = model.getFilteredOrderList().get(INDEX_FIRST_ORDER.getZeroBased());
        DeleteOrderCommand deleteOrderCommand = prepareCommand(INDEX_FIRST_ORDER);

        String expectedMessage = String.format(DeleteOrderCommand.MESSAGE_DELETE_ORDER_SUCCESS, orderToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new CalendarManager(), new UserPrefs());
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
        Model expectedModel = new ModelManager(model.getAddressBook(), new CalendarManager(), new UserPrefs());

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
###### \java\seedu\address\logic\commands\EditOrderCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_COMICBOOK;
import static seedu.address.logic.commands.CommandTestUtil.DESC_COMPUTER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ORDER_INFORMATION_COMPUTER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_COMPUTER;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.address.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ORDER;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ORDER;
import static seedu.address.testutil.TypicalOrders.getTypicalAddressBookWithOrders;

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

    private Model model = new ModelManager(getTypicalAddressBookWithOrders(), new CalendarManager(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() throws Exception {
        Order editedOrder = new OrderBuilder().build();
        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder(editedOrder).build();
        EditOrderCommand editOrderCommand = prepareCommand(INDEX_FIRST_ORDER, descriptor);

        String expectedMessage = String.format(EditOrderCommand.MESSAGE_EDIT_ORDER_SUCCESS, editedOrder);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new CalendarManager(),
                new UserPrefs());
        expectedModel.updateOrder(model.getFilteredOrderList().get(0), editedOrder);

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

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new CalendarManager(),
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
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new CalendarManager(),
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
###### \java\seedu\address\model\order\DeliveryDateTest.java
``` java
package seedu.address.model.order;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class DeliveryDateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new DeliveryDate(null));
    }

    @Test
    public void constructor_invalidDeliveryDate_throwsIllegalArgumentException() {
        String invalidDeliveryDate = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new DeliveryDate(invalidDeliveryDate));
    }

    @Test
    public void isValidDeliveryDate() {
        // null delivery date
        Assert.assertThrows(NullPointerException.class, () -> DeliveryDate.isValidDeliveryDate(null));

        // invalid delivery date
        assertFalse(DeliveryDate.isValidDeliveryDate("")); // empty string
        assertFalse(DeliveryDate.isValidDeliveryDate(" ")); // spaces only
        assertFalse(DeliveryDate.isValidDeliveryDate("wejo*21")); // invalid string
        assertFalse(DeliveryDate.isValidDeliveryDate("12/12/2012")); // invalid format
        assertFalse(DeliveryDate.isValidDeliveryDate("0-1-98")); // invalid date
        assertFalse(DeliveryDate.isValidDeliveryDate("50-12-1998")); // invalid day
        assertFalse(DeliveryDate.isValidDeliveryDate("10-15-2013")); // invalid month
        assertFalse(DeliveryDate.isValidDeliveryDate("09-08-10000")); // invalid year

        // valid delivery date
        assertTrue(DeliveryDate.isValidDeliveryDate("01-01-2001")); // valid date
        assertTrue(DeliveryDate.isValidDeliveryDate("29-02-2000")); // leap year
    }
}
```
###### \java\seedu\address\model\order\OrderInformationTest.java
``` java
package seedu.address.model.order;

import static org.junit.Assert.assertFalse;
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
    public void isValidOrderInformation() {
        // null order information
        Assert.assertThrows(NullPointerException.class, () -> OrderInformation.isValidOrderInformation(null));

        // invalid order information
        assertFalse(OrderInformation.isValidOrderInformation("")); // empty string
        assertFalse(OrderInformation.isValidOrderInformation(" ")); // spaces only

        // valid order information
        assertTrue(OrderInformation.isValidOrderInformation("Books"));
        assertTrue(OrderInformation.isValidOrderInformation("Facial Cleanser"));
        assertTrue(OrderInformation.isValidOrderInformation("Confectionery Boxes"));
    }
}
```
###### \java\seedu\address\model\order\OrderTest.java
``` java
package seedu.address.model.order;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class OrderTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Order(null, null, null, null));
    }
}
```
###### \java\seedu\address\model\order\PriceTest.java
``` java
package seedu.address.model.order;

import static org.junit.Assert.assertFalse;
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
    public void isValidPrice() {
        // null price
        Assert.assertThrows(NullPointerException.class, () -> Price.isValidPrice(null));

        // invalid price
        assertFalse(Price.isValidPrice("")); // empty string
        assertFalse(Price.isValidPrice(" ")); // spaces only
        assertFalse(Price.isValidPrice("sj)")); // non numeric characters
        assertFalse(Price.isValidPrice("2.3.20")); // more than one decimal place
        assertFalse(Price.isValidPrice("10.1234")); // more than two digits after decimal point
        assertFalse(Price.isValidPrice("10,00,000")); // commas
        assertFalse(Price.isValidPrice("-1.0")); // negative
        assertFalse(Price.isValidPrice("+10.0")); // plus sign

        // valid price
        assertTrue(Price.isValidPrice("10.0")); // one digit after decimal point
        assertTrue(Price.isValidPrice("500.75")); // two digits after decimal point
        assertTrue(Price.isValidPrice("015.50")); // leading zero
    }
}
```
###### \java\seedu\address\model\order\QuantityTest.java
``` java
package seedu.address.model.order;

import static org.junit.Assert.assertFalse;
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
    public void isValidQuantity() {
        // null quantity
        Assert.assertThrows(NullPointerException.class, () -> Price.isValidPrice(null));

        // invalid quantity
        assertFalse(Quantity.isValidQuantity("")); // empty string
        assertFalse(Quantity.isValidQuantity(" ")); // spaces only
        assertFalse(Quantity.isValidQuantity("sj)")); // non numeric characters
        assertFalse(Quantity.isValidQuantity("2.3")); // decimal
        assertFalse(Quantity.isValidQuantity("-1")); // negative integer
        assertFalse(Quantity.isValidQuantity("0")); // zero
        assertFalse(Quantity.isValidQuantity("+9")); // plus sign

        // valid quantity
        assertTrue(Quantity.isValidQuantity("10")); // positive integer
        assertTrue(Quantity.isValidQuantity("0500")); // leading zero
    }
}
```
###### \java\seedu\address\model\theme\ThemeTest.java
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
###### \java\seedu\address\model\UniqueGroupListTest.java
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
###### \java\seedu\address\model\UniqueGroupListTest.java
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
###### \java\seedu\address\model\UniqueOrderListTest.java
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

public class UniqueOrderListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void equals() throws UniqueOrderList.DuplicateOrderException {
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
            throws UniqueOrderList.DuplicateOrderException {
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
            throws UniqueOrderList.DuplicateOrderException {
        UniqueOrderList uniqueOrderList = new UniqueOrderList();
        thrown.expect(UniqueOrderList.DuplicateOrderException.class);
        uniqueOrderList.add(BOOKS);
        uniqueOrderList.add(BOOKS);
    }
}
```
###### \java\seedu\address\model\UniquePreferenceListTest.java
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
###### \java\seedu\address\model\UniquePreferenceListTest.java
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
###### \java\seedu\address\storage\XmlAdaptedOrderTest.java
``` java
package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.XmlAdaptedOrder.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalOrders.BOOKS;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.order.DeliveryDate;
import seedu.address.model.order.OrderInformation;
import seedu.address.model.order.Price;
import seedu.address.model.order.Quantity;
import seedu.address.testutil.Assert;

public class XmlAdaptedOrderTest {
    private static final String INVALID_ORDER_INFORMATION = "Choc0l@t3s";
    private static final String INVALID_PRICE = "25.00.99";
    private static final String INVALID_QUANTITY = "-2";
    private static final String INVALID_DELIVERY_DATE = "50-12-2010";

    private static final String VALID_ORDER_INFORMATION = BOOKS.getOrderInformation().toString();
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
        XmlAdaptedOrder order = new XmlAdaptedOrder(INVALID_ORDER_INFORMATION, VALID_PRICE,
                VALID_QUANTITY, VALID_DELIVERY_DATE);
        String expectedMessage = OrderInformation.MESSAGE_ORDER_INFORMATION_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_nullOrderInformation_throwsIllegalValueException() {
        XmlAdaptedOrder order = new XmlAdaptedOrder(null, VALID_PRICE,
                VALID_QUANTITY, VALID_DELIVERY_DATE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, OrderInformation.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_invalidPrice_throwsIllegalValueException() {
        XmlAdaptedOrder order = new XmlAdaptedOrder(VALID_ORDER_INFORMATION, INVALID_PRICE,
                VALID_QUANTITY, VALID_DELIVERY_DATE);
        String expectedMessage = Price.MESSAGE_PRICE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_nullPrice_throwsIllegalValueException() {
        XmlAdaptedOrder order = new XmlAdaptedOrder(VALID_ORDER_INFORMATION, null,
                VALID_QUANTITY, VALID_DELIVERY_DATE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Price.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_invalidQuantity_throwsIllegalValueException() {
        XmlAdaptedOrder order = new XmlAdaptedOrder(VALID_ORDER_INFORMATION, VALID_PRICE,
                INVALID_QUANTITY, VALID_DELIVERY_DATE);
        String expectedMessage = Quantity.MESSAGE_QUANTITY_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_nullQuantity_throwsIllegalValueException() {
        XmlAdaptedOrder order = new XmlAdaptedOrder(VALID_ORDER_INFORMATION, VALID_PRICE,
                null, VALID_DELIVERY_DATE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Quantity.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_invalidDeliveryDate_throwsIllegalValueException() {
        XmlAdaptedOrder order = new XmlAdaptedOrder(VALID_ORDER_INFORMATION, VALID_PRICE,
                VALID_QUANTITY, INVALID_DELIVERY_DATE);
        String expectedMessage = DeliveryDate.MESSAGE_DELIVERY_DATE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_nullDeliveryDate_throwsIllegalValueException() {
        XmlAdaptedOrder order = new XmlAdaptedOrder(VALID_ORDER_INFORMATION, VALID_PRICE,
                VALID_QUANTITY, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, DeliveryDate.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }
}
```
###### \java\seedu\address\storage\XmlSerializableAddressBookTest.java
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
###### \java\seedu\address\storage\XmlSerializableAddressBookTest.java
``` java
    @Test
    public void toModelType_invalidOrderFile_throwsIllegalValueException() throws Exception {
        XmlSerializableAddressBook dataFromFile = XmlUtil.getDataFromFile(INVALID_ORDER_FILE,
                XmlSerializableAddressBook.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }
```
###### \java\seedu\address\testutil\EditOrderDescriptorBuilder.java
``` java
package seedu.address.testutil;

import seedu.address.logic.commands.EditOrderCommand.EditOrderDescriptor;
import seedu.address.model.order.DeliveryDate;
import seedu.address.model.order.Order;
import seedu.address.model.order.OrderInformation;
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
###### \java\seedu\address\testutil\OrderBuilder.java
``` java
package seedu.address.testutil;

import seedu.address.model.order.DeliveryDate;
import seedu.address.model.order.Order;
import seedu.address.model.order.OrderInformation;
import seedu.address.model.order.Price;
import seedu.address.model.order.Quantity;

/**
 * A utility class to help with building Order objects.
 */
public class OrderBuilder {

    public static final String DEFAULT_ORDER_INFORMATION = "Books";
    public static final String DEFAULT_PRICE = "15.00";
    public static final String DEFAULT_QUANTITY = "5";
    public static final String DEFAULT_DELIVERY_DATE = "10-05-2018";

    private OrderInformation orderInformation;
    private Price price;
    private Quantity quantity;
    private DeliveryDate deliveryDate;

    public OrderBuilder() {
        orderInformation = new OrderInformation(DEFAULT_ORDER_INFORMATION);
        price = new Price(DEFAULT_PRICE);
        quantity = new Quantity(DEFAULT_QUANTITY);
        deliveryDate = new DeliveryDate(DEFAULT_DELIVERY_DATE);
    }

    /**
     * Initializes the OrderBuilder with the data of {@code orderToCopy}.
     */
    public OrderBuilder(Order orderToCopy) {
        orderInformation = orderToCopy.getOrderInformation();
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
        return new Order(orderInformation, price, quantity, deliveryDate);
    }
}
```
###### \java\seedu\address\testutil\OrderUtil.java
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
        sb.append(PREFIX_ORDER_INFORMATION + order.getOrderInformation().toString() + " ");
        sb.append(PREFIX_PRICE + order.getPrice().toString() + " ");
        sb.append(PREFIX_QUANTITY + order.getQuantity().toString() + " ");
        sb.append(PREFIX_DELIVERY_DATE + order.getDeliveryDate().toString());
        return sb.toString();
    }
}
```
###### \java\seedu\address\testutil\TestUtil.java
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
###### \java\seedu\address\testutil\TestUtil.java
``` java

    /**
     * Returns the person in the {@code model}'s person list at {@code index}.
     */
    public static Person getPerson(Model model, Index index) {
        return model.getAddressBook().getPersonList().get(index.getZeroBased());
    }

```
###### \java\seedu\address\testutil\TestUtil.java
``` java
    /**
     * Returns the order in the {@code model}'s order list at {@code index}.
     */
    public static Order getOrder(Model model, Index index) {
        return model.getAddressBook().getOrderList().get(index.getZeroBased());
    }
```
###### \java\seedu\address\testutil\TypicalOrders.java
``` java
package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_DELIVERY_DATE_BOOKS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DELIVERY_DATE_CHOC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DELIVERY_DATE_COMPUTER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ORDER_INFORMATION_BOOKS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ORDER_INFORMATION_CHOC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ORDER_INFORMATION_COMPUTER;
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
import seedu.address.model.order.UniqueOrderList;
import seedu.address.model.person.exceptions.DuplicatePersonException;

/**
 * A utility class containing a list of {@code Order} objects to be used in tests.
 */
public class TypicalOrders {

    public static final Order SHOES = new OrderBuilder()
            .withOrderInformation("Shoes")
            .withPrice("129.99")
            .withQuantity("3")
            .withDeliveryDate("10-09-2018")
            .build();

    public static final Order FACEWASH = new OrderBuilder()
            .withOrderInformation("Face Wash")
            .withPrice("24.75")
            .withQuantity("1")
            .withDeliveryDate("05-11-2018")
            .build();

    public static final Order BOOKS = new OrderBuilder()
            .withOrderInformation(VALID_ORDER_INFORMATION_BOOKS)
            .withPrice(VALID_PRICE_BOOKS)
            .withQuantity(VALID_QUANTITY_BOOKS)
            .withDeliveryDate(VALID_DELIVERY_DATE_BOOKS)
            .build();

    public static final Order CHOCOLATES = new OrderBuilder()
            .withOrderInformation(VALID_ORDER_INFORMATION_CHOC)
            .withPrice(VALID_PRICE_CHOC)
            .withQuantity(VALID_QUANTITY_CHOC)
            .withDeliveryDate(VALID_DELIVERY_DATE_CHOC)
            .build();

    public static final Order COMPUTER = new OrderBuilder()
            .withOrderInformation(VALID_ORDER_INFORMATION_COMPUTER)
            .withPrice(VALID_PRICE_COMPUTER)
            .withQuantity(VALID_QUANTITY_COMPUTER)
            .withDeliveryDate(VALID_DELIVERY_DATE_COMPUTER)
            .build();

    public static final Order COMICBOOK = new OrderBuilder()
            .withOrderInformation("Comic Book")
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
            } catch (UniqueOrderList.DuplicateOrderException doe) {
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
###### \java\seedu\address\ui\CommandBoxTest.java
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
###### \java\seedu\address\ui\OrderCardTest.java
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
###### \java\seedu\address\ui\OrderListPanelTest.java
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
###### \java\systemtests\AddOrderCommandSystemTest.java
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
import seedu.address.model.order.UniqueOrderList;
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

    /**
     * Executes the {@code AddOrderCommand} that adds {@code toAdd} to the model and asserts that the:<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code AddOrderCommand} with details of
     * {@code toAdd}.<br>
     * 4. {@code Model}, {@code Storage} and {@code PersonListPanel} equal to the corresponding components in
     * the current model added with {@code toAdd}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(Index index, Order toAdd) {
        assertCommandSuccess(OrderUtil.getAddOrderCommand(index.getZeroBased(), toAdd), index, toAdd);
    }

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
        } catch (UniqueOrderList.DuplicateOrderException dpe) {
            throw new IllegalArgumentException("toAdd already exists in the model.");
        }
        String expectedResultMessage = String.format(
                AddOrderCommand.MESSAGE_ADD_ORDER_SUCCESS, person.getName(), toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Index, Order)} except asserts that
     * the:<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Model}, {@code Storage} and {@code PersonListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see AddOrderCommandSystemTest#assertCommandSuccess(String, Index, Order)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and asserts that the:<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Model}, {@code Storage} and {@code PersonListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
```
###### \java\systemtests\DeleteOrderCommandSystemTest.java
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
     * 4. Asserts that the browser url and selected card remains unchanged.<br>
     * 5. Asserts that the status bar's sync status changes.<br>
     * 6. Asserts that the command box has the default style class.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String)} except that the browser url
     * and selected card are expected to update accordingly depending on the card at {@code expectedSelectedCardIndex}.
     * @see DeleteOrderCommandSystemTest#assertCommandSuccess(String, Model, String)
     * @see AddressBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
                                      Index expectedSelectedCardIndex) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }

        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to the current model.<br>
     * 4. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 5. Asserts that the command box has the error style.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
```
###### \java\systemtests\EditOrderCommandSystemTest.java
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
import seedu.address.model.order.UniqueOrderList;
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

        /* Case: redo editing the last person in the list -> last person edited again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        model.updateOrder(
                getModel().getFilteredOrderList().get(INDEX_THIRD_ORDER.getZeroBased()), editedOrder);
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: edit some fields -> edited */
        index = INDEX_THIRD_ORDER;
        command = EditOrderCommand.COMMAND_WORD + " " + index.getOneBased() + QUANTITY_DESC_COMPUTER;
        Order orderToEdit = getModel().getFilteredOrderList().get(index.getZeroBased());
        editedOrder = new OrderBuilder(orderToEdit).withQuantity(VALID_QUANTITY_COMPUTER).build();
        assertCommandSuccess(command, index, editedOrder);


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
        } catch (UniqueOrderList.DuplicateOrderException | OrderNotFoundException e) {
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

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to the current model.<br>
     * 4. Asserts that the selected card and status bar remain unchanged.<br>
     * 5. Asserts that the command box has the error style.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
```
