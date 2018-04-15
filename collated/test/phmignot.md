# phmignot
###### /java/seedu/address/ui/TransactionCardTest.java
``` java
package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysTransaction;

import org.junit.Test;

import guitests.guihandles.TransactionCardHandle;
import seedu.address.model.transaction.Transaction;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.testutil.TransactionBuilder;

public class TransactionCardTest extends GuiUnitTest {
    @Test
    public void display() {
        Transaction transaction = new TransactionBuilder().build();
        TransactionCard transactionCard = new TransactionCard(transaction, 1);
        uiPartRule.setUiPart(transactionCard);
        assertCardDisplayTransaction(transactionCard, transaction, 1);
    }

    @Test
    public void equals() {
        Transaction transaction = new TransactionBuilder().build();
        TransactionCard transactionCard = new TransactionCard(transaction, 0);

        // same transaction, same index -> returns true
        TransactionCard copy = new TransactionCard(transaction, 0);
        assertTrue(transactionCard.equals(copy));

        // same object -> returns true
        assertTrue(transactionCard.equals(transactionCard));

        // null -> returns false
        assertFalse(transactionCard.equals(null));

        // different types -> returns false
        assertFalse(transactionCard.equals(0));

        // different transaction, same index -> returns false
        Transaction differentTransaction = new TransactionBuilder()
                .withPayer(SampleDataUtil.getSamplePersons()[1])
                .build();
        assertFalse(transactionCard.equals(new TransactionCard(differentTransaction, 0)));
    }

    /**
     * Asserts that {@code transactionCard} displays the details of {@code expectedTransaction} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplayTransaction(TransactionCard transactionCard, Transaction expectedTransaction,
                                              int expectedId) {
        guiRobot.pauseForHuman();

        TransactionCardHandle transactionCardHandle = new TransactionCardHandle(transactionCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", transactionCardHandle.getId());

        // verify transaction details are displayed correctly
        assertCardDisplaysTransaction(expectedTransaction, transactionCardHandle);
    }
}
```
###### /java/seedu/address/ui/testutil/GuiTestAssert.java
``` java
    /**
     * Asserts that {@code actualTransactionCard} displays the same values as {@code expectedTransactionCard}.
     */
    public static void assertCardEquals(TransactionCardHandle expectedCard, TransactionCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getPayer(), actualCard.getPayer());
        assertEquals(expectedCard.getAmount(), actualCard.getAmount());
        assertEquals(expectedCard.getDescription(), actualCard.getDescription());
        assertEquals(expectedCard.getPayees(), actualCard.getPayees());
    }

```
###### /java/seedu/address/ui/testutil/GuiTestAssert.java
``` java
    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedPerson}.
     */
    public static void assertCardDisplaysPerson(Person expectedPerson, PersonCardHandle actualCard) {
        assertEquals(expectedPerson.getName().fullName, actualCard.getName());
        assertEquals(expectedPerson.getPhone().value, actualCard.getPhone());
        assertEquals(expectedPerson.getEmail().value, actualCard.getEmail());
        assertEquals(expectedPerson.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                actualCard.getTags());
    }
```
###### /java/seedu/address/ui/testutil/GuiTestAssert.java
``` java
    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedPerson}.
     */
    public static void assertCreditorCardDisplays(Creditor expectedCreditor, CreditorCardHandle actualCard) {
        assertEquals(expectedCreditor.getCreditor().getName().fullName, actualCard.getName());
        assertEquals(expectedCreditor.getCreditor().getPhone().value, actualCard.getPhone());
        assertEquals(expectedCreditor.getCreditor().getEmail().value, actualCard.getEmail());
        assertEquals(expectedCreditor.getCreditor().getTags().stream().map(tag -> tag.tagName)
                        .collect(Collectors.toList()),
                actualCard.getTags());
        assertEquals(expectedCreditor.getDebt().value, actualCard.getDebt());
    }
```
###### /java/seedu/address/ui/testutil/GuiTestAssert.java
``` java
    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedPerson}.
     */
    public static void assertDebtorCardDisplays(Debtor expectedDebtor, DebtorCardHandle actualCard) {
        assertEquals(expectedDebtor.getDebtor().getName().fullName, actualCard.getName());
        assertEquals(expectedDebtor.getDebtor().getPhone().value, actualCard.getPhone());
        assertEquals(expectedDebtor.getDebtor().getEmail().value, actualCard.getEmail());
        assertEquals(expectedDebtor.getDebtor().getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                actualCard.getTags());
        assertEquals(expectedDebtor.getDebt().value, actualCard.getDebt());
    }
    /**
     * Asserts that the list in {@code personListPanelHandle} displays the details of {@code persons} correctly and
     * in the correct order.
     */
    public static void assertListMatching(PersonListPanelHandle personListPanelHandle, Person... persons) {
        for (int i = 0; i < persons.length; i++) {
            assertCardDisplaysPerson(persons[i], personListPanelHandle.getPersonCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code personListPanelHandle} displays the details of {@code persons} correctly and
     * in the correct order.
     */
    public static void assertListMatching(PersonListPanelHandle personListPanelHandle, List<Person> persons) {
        assertListMatching(personListPanelHandle, persons.toArray(new Person[0]));
    }

    /**
     * Asserts the size of the list in {@code personListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(PersonListPanelHandle personListPanelHandle, int size) {
        int numberOfPeople = personListPanelHandle.getListSize();
        assertEquals(size, numberOfPeople);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }

```
###### /java/seedu/address/ui/testutil/GuiTestAssert.java
``` java
    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedTransaction}.
     */
    public static void assertCardDisplaysTransaction(Transaction expectedTransaction,
                                                     TransactionCardHandle actualCard) {
        assertEquals(expectedTransaction.getPayer().getName().fullName, actualCard.getPayer());
        assertEquals(expectedTransaction.getAmount().toString(), actualCard.getAmount());
        assertEquals(expectedTransaction.getDescription().value, actualCard.getDescription());

        String expectedPayeesString = "";
        for (int i = 0; i < expectedTransaction.getPayees().asObservableList().size(); i++) {
            Person expectedPayee = expectedTransaction.getPayees().asObservableList().get(i);
            expectedPayeesString += expectedPayee.getName().fullName;
            if (i != expectedTransaction.getPayees().asObservableList().size() - 1) {
                expectedPayeesString += ", ";
            }
        }

        assertEquals(expectedPayeesString, actualCard.getPayees());
    }
}
```
###### /java/seedu/address/testutil/TransactionBuilder.java
``` java
package seedu.address.testutil;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.Description;
import seedu.address.model.transaction.SplitMethod;
import seedu.address.model.transaction.Transaction;
import seedu.address.model.transaction.TransactionType;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Transaction objects.
 */
public class TransactionBuilder {

    public static final String DEFAULT_AMOUNT = "6172.50";
    public static final String DEFAULT_DESCRIPTION = "paying for Cookies";
    private static Integer lastTransactionId = 0;
    private final Integer id;
    private Person payer;
    private Amount amount;
    private Description description;
    private Date dateTime;
    private UniquePersonList payees;
    private TransactionType transactionType;
    private SplitMethod splitMethod;
    private List<Integer> unitsList;
    private List<Integer> percentagesList;

    public TransactionBuilder() {
        transactionType = new TransactionType("payment");
        payer = SampleDataUtil.getSamplePersons()[0];
        amount = new Amount(DEFAULT_AMOUNT);
        description = new Description(DEFAULT_DESCRIPTION);

        UniquePersonList samplePayees = new UniquePersonList();
        try {
            samplePayees.add(SampleDataUtil.getSamplePersons()[1]);
        } catch (DuplicatePersonException dpe) {
            throw new AssertionError("This payee has already been added");
        }
        dateTime = Date.from(Instant.now(Clock.system(ZoneId.of("Asia/Singapore"))));
        this.id = lastTransactionId++;
        payees = samplePayees;
        splitMethod = new SplitMethod(SplitMethod.SPLIT_METHOD_EVENLY);
        unitsList = Collections.emptyList();
        percentagesList = Collections.emptyList();
    }

    /**
     * Initializes the TransactionBuilder with the data of {@code transactionToCopy}.
     */
    public TransactionBuilder(Transaction transactionToCopy) {
        transactionType = transactionToCopy.getTransactionType();
        payer = transactionToCopy.getPayer();
        amount = transactionToCopy.getAmount();
        description = transactionToCopy.getDescription();
        dateTime = transactionToCopy.getDateTime();
        id = transactionToCopy.getId();
        payees = transactionToCopy.getPayees();
        transactionType = transactionToCopy.getTransactionType();
        splitMethod = transactionToCopy.getSplitMethod();
        unitsList = transactionToCopy.getUnits();
        percentagesList = transactionToCopy.getPercentages();
    }

    /**
     * Sets the {@code payer} of the {@code Transaction} that we are building.
     */
    public TransactionBuilder withPayer(Person payer) {
        this.payer = payer;
        return this;
    }
    /**
     * Sets the {@code Amount} of the {@code Transaction} that we are building.
     */
    public TransactionBuilder withAmount(String amount) {
        this.amount = new Amount(amount);
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code Transaction} that we are building.
     */
    public TransactionBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    /**
     * Sets the {@code payees} of the {@code Transaction} that we are building.
     * @param payees
     */
    public TransactionBuilder withPayees(String... payees) throws DuplicatePersonException {
        this.payees = SampleDataUtil.getPayeesSet(payees);
        return this;
    }

    /**
     * Sets the {@code date & time} of the {@code Transaction} that we are building.
     */
    public TransactionBuilder withDate(Date dateTime) {
        this.dateTime = dateTime;
        return this;
    }
```
###### /java/seedu/address/testutil/TransactionBuilder.java
``` java
    /**
     * Builds a new {@code Transaction}.
     */
    public Transaction build() {
        return new Transaction(transactionType, payer, amount, description, dateTime,
                payees, splitMethod, unitsList, percentagesList);
    }
}
```
###### /java/guitests/guihandles/TransactionListPanelHandle.java
``` java
package guitests.guihandles;

import java.util.List;
import java.util.Optional;

import javafx.scene.control.ListView;
import seedu.address.model.transaction.Transaction;
import seedu.address.ui.TransactionCard;



/**
 * Provides a handle for {@code TransactionListPanel} containing the list of {@code TransactionCard}.
 */
public class TransactionListPanelHandle extends NodeHandle<ListView<TransactionCard>> {
    public static final String TRANSACTION_LIST_VIEW_ID = "#transactionListView";

    private Optional<TransactionCard> lastRememberedSelectedTransactionCard;

    public TransactionListPanelHandle(ListView<TransactionCard> transactionListPanelNode) {
        super(transactionListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code TransactionCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     */
    public TransactionCardHandle getHandleToSelectedCard() {
        List<TransactionCard> transactionList = getRootNode().getSelectionModel().getSelectedItems();

        if (transactionList.size() != 1) {
            throw new AssertionError("Transaction list size expected 1.");
        }

        return new TransactionCardHandle(transactionList.get(0).getRoot());
    }

    /**
     * Returns the index of the selected card.
     */
    public int getSelectedCardIndex() {
        return getRootNode().getSelectionModel().getSelectedIndex();
    }

    /**
     * Returns true if a card is currently selected.
     */
    public boolean isAnyCardSelected() {
        List<TransactionCard> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display and select the transaction.
     */
    public void navigateToCard(Transaction transaction) {
        List<TransactionCard> cards = getRootNode().getItems();
        Optional<TransactionCard> matchingCard = cards
                .stream()
                .filter(card -> card.transaction.equals(transaction))
                .findFirst();


        if (!matchingCard.isPresent()) {
            throw new IllegalArgumentException("Transaction does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(matchingCard.get());
            getRootNode().getSelectionModel().select(matchingCard.get());
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Returns the transaction card handle of a transaction associated with the {@code index} in the list.
     */
    public TransactionCardHandle getTransactionCardHandle(int index) {
        return getTransactionCardHandle(getRootNode().getItems().get(index).transaction);
    }

    /**
     * Returns the {@code TransactionCardHandle} of the specified {@code transaction} in the list.
     */
    public TransactionCardHandle getTransactionCardHandle(Transaction transaction) {
        Optional<TransactionCardHandle> handle = getRootNode().getItems().stream()
                .filter(card -> card.transaction.equals(transaction))
                .map(card -> new TransactionCardHandle(card.getRoot()))
                .findFirst();
        return handle.orElseThrow(() -> new IllegalArgumentException("Transaction does not exist."));
    }

    /**
     * Selects the {@code TransactionCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Remembers the selected {@code TransactionCard} in the list.
     */
    public void rememberSelectedTransactionCard() {
        List<TransactionCard> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedTransactionCard = Optional.empty();
        } else {
            lastRememberedSelectedTransactionCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code TransactionCard} is different from the value remembered by the most recent
     * {@code rememberSelectedTransactionCard()} call.
     */
    public boolean isSelectedTransactionCardChanged() {
        List<TransactionCard> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedTransactionCard.isPresent();
        } else {
            return !lastRememberedSelectedTransactionCard.isPresent()
                    || !lastRememberedSelectedTransactionCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}

```
###### /java/guitests/guihandles/TransactionCardHandle.java
``` java
package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * Provides a handle to a transaction card in the transaction list panel.
 */
public class TransactionCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#idDisplay";
    private static final String PAYER_FIELD_ID = "#payerName";
    private static final String AMOUNT_FIELD_ID = "#amount";
    private static final String DESCRIPTION_FIELD_ID = "#description";
    private static final String PAYEES_FIELD_ID = "#payees";

    private final Label idLabel;
    private final Label payerLabel;
    private final Label amountLabel;
    private final Label descriptionLabel;
    private final List<Label> payeesLabel;

    public TransactionCardHandle(Node cardNode) {
        super(cardNode);

        this.idLabel = getChildNode(ID_FIELD_ID);
        this.payerLabel = getChildNode(PAYER_FIELD_ID);
        this.amountLabel = getChildNode(AMOUNT_FIELD_ID);
        this.descriptionLabel = getChildNode(DESCRIPTION_FIELD_ID);
        Region payeesContainer = getChildNode(PAYEES_FIELD_ID);
        this.payeesLabel = payeesContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());

    }

    public String getId() {
        return idLabel.getText();
    }

    public String getPayer() {
        return payerLabel.getText();
    }

    public String getAmount() {
        return amountLabel.getText();
    }

    public String getDescription() {
        return descriptionLabel.getText();
    }

    public String getPayees() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < payeesLabel.size(); i++) {
            builder.append(payeesLabel.get(i).getText());
        }
        return builder.toString();
    }
}

```
