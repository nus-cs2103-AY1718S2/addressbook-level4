# ongkc
###### /resources/view/CreditorListCard.fxml
``` fxml

<HBox id="cardPane" fx:id="cardPane" prefHeight="105.0" prefWidth="180.0" xmlns="http://javafx.com/javafx/8.0.141" xmlns:fx="http://javafx.com/fxml/1">
    <GridPane prefHeight="105.0" prefWidth="89.0" HBox.hgrow="ALWAYS">
        <columnConstraints>
            <ColumnConstraints hgrow="SOMETIMES" minWidth="10" prefWidth="150" />
        </columnConstraints>
        <VBox alignment="CENTER_LEFT" minHeight="100.0" prefHeight="105.0" prefWidth="81.0" GridPane.columnIndex="0">
            <padding>
                <Insets bottom="5.0" left="15.0" right="5.0" />
            </padding>
            <HBox alignment="CENTER_LEFT" spacing="5">
                <Label fx:id="id" styleClass="cell_big_label">
                    <minWidth>
                        <!-- Ensures that the label text is never truncated -->
                        <Region fx:constant="USE_PREF_SIZE" />
                    </minWidth>
                </Label>
                <Label fx:id="name" styleClass="cell_big_label" text="\$first" />
            </HBox>
            <FlowPane fx:id="tags" />
            <Label fx:id="phone" styleClass="cell_small_label" text="\$phone" />
            <Label fx:id="email" styleClass="cell_small_label" text="\$email" />
        </VBox>
        <rowConstraints>
            <RowConstraints />
        </rowConstraints>
    </GridPane>
    <VBox prefHeight="105.0" prefWidth="105.0">
        <children>
            <Label text="Is Owed">
                <VBox.margin>
                    <Insets />
                </VBox.margin>
                <padding>
                    <Insets top="25.0" />
                </padding></Label>
            <HBox prefHeight="22.0" prefWidth="105.0">
                <children>
                    <Label fx:id="debt" prefHeight="20.0" prefWidth="105.0" text="\$debt">
                        <graphic>
                            <Label text="\$" />
                        </graphic>
                    </Label>
                </children>
            </HBox>
            <HBox prefHeight="22.0" prefWidth="105.0" />
        </children>
        <padding>
            <Insets top="10.0" />
        </padding>
    </VBox>
</HBox>
```
###### /resources/view/DebtorListPanel.fxml
``` fxml

<VBox xmlns="http://javafx.com/javafx/8.0.141" xmlns:fx="http://javafx.com/fxml/1">
    <Label prefHeight="25.0" prefWidth="180.0" text="Debtor(s)">
        <padding>
            <Insets left="90.0" />
        </padding></Label>
    <ListView fx:id="debtorListView" prefHeight="400.0" prefWidth="200.0" VBox.vgrow="ALWAYS" />

</VBox>
```
###### /resources/view/DebtorListCard.fxml
``` fxml
<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.FlowPane?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.Region?>
<?import javafx.scene.layout.RowConstraints?>
<?import javafx.scene.layout.VBox?>

<VBox xmlns="http://javafx.com/javafx/8.0.141" xmlns:fx="http://javafx.com/fxml/1">
   <children>
      <HBox id="cardPane" fx:id="cardPane" prefHeight="105.0" prefWidth="180.0">
          <GridPane prefHeight="105.0" prefWidth="89.0" HBox.hgrow="ALWAYS">
              <columnConstraints>
                  <ColumnConstraints hgrow="SOMETIMES" minWidth="10" prefWidth="150" />
              </columnConstraints>
              <VBox alignment="CENTER_LEFT" minHeight="100.0" prefHeight="105.0" prefWidth="81.0" GridPane.columnIndex="0">
                  <padding>
                      <Insets bottom="5.0" left="15.0" right="5.0" />
                  </padding>
                  <HBox alignment="CENTER_LEFT" spacing="5">
                      <Label fx:id="id" styleClass="cell_big_label">
                          <minWidth>
                              <!-- Ensures that the label text is never truncated -->
                              <Region fx:constant="USE_PREF_SIZE" />
                          </minWidth>
                      </Label>
                      <Label fx:id="name" styleClass="cell_big_label" text="\$first" />
                  </HBox>
                  <FlowPane fx:id="tags" />
                  <Label fx:id="phone" styleClass="cell_small_label" text="\$phone" />
                  <Label fx:id="email" styleClass="cell_small_label" text="\$email" />
              </VBox>
              <rowConstraints>
                  <RowConstraints />
              </rowConstraints>
          </GridPane>
          <VBox prefHeight="70.0" prefWidth="98.0">
              <children>
                  <Label text="Owes">
                      <VBox.margin>
                          <Insets />
                      </VBox.margin>
                      <padding>
                          <Insets top="25.0" />
                      </padding>
               </Label>
                  <HBox prefHeight="22.0" prefWidth="105.0">
                      <children>
                          <Label fx:id="debt" prefHeight="20.0" prefWidth="105.0" text="\$debt">
                              <graphic>
                                  <Label text="\$" />
                              </graphic>
                          </Label>
                      </children>
                  </HBox>
                  <HBox prefHeight="22.0" prefWidth="105.0" />
              </children>
              <padding>
                  <Insets top="10.0" />
              </padding>
          </VBox>
      </HBox>
   </children>
</VBox>
```
###### /resources/view/CreditorListPanel.fxml
``` fxml
<?import javafx.scene.control.ListView?>
<?import javafx.scene.layout.VBox?>
<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.layout.VBox?>

<VBox xmlns="http://javafx.com/javafx/8.0.141" xmlns:fx="http://javafx.com/fxml/1">
    <Label prefHeight="25.0" prefWidth="180.0" text="Creditor(s)">
        <padding>
            <Insets left="90.0" />
        </padding></Label>
    <ListView fx:id="creditorListView" prefHeight="400.0" prefWidth="200.0" VBox.vgrow="ALWAYS" />
</VBox>
```
###### /java/seedu/address/ui/CreditorListPanel.java
``` java
/**
 * Panel containing the list of creditors.
 */
public class CreditorListPanel extends UiPart<Region>  {


    private static final String FXML = "CreditorListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(CreditorListPanel.class);

    @javafx.fxml.FXML
    private ListView<CreditorCard> creditorListView;

    public CreditorListPanel(ObservableList<Creditor> creditorsList) {
        super(FXML);
        setConnections(creditorsList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Creditor> creditorsList) {
        ObservableList<CreditorCard> mappedList = EasyBind.map(
                creditorsList, (creditor) -> new CreditorCard(creditor, creditorsList.indexOf(creditor) + 1));
        creditorListView.setItems(mappedList);
        creditorListView.setCellFactory(listView -> new CreditorListViewCell());;

    }


    /**
     * Scrolls to the {@code CreditorCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            creditorListView.scrollTo(index);
            creditorListView.getSelectionModel().clearAndSelect(index);
        });
    }


    /**
     * Custom {@code ListCell} that displays the graphics of a {@code CreditorCard}.
     */
    class CreditorListViewCell extends ListCell<CreditorCard> {
        @Override
        protected void updateItem(CreditorCard creditor, boolean empty) {
            super.updateItem(creditor, empty);

            if (empty || creditor == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(creditor.getRoot());
            }
        }
    }
}
```
###### /java/seedu/address/ui/DebtorListPanel.java
``` java
/**
 * Panel containing the list of debtors.
 */
public class DebtorListPanel extends UiPart<Region> {

    private static final String FXML = "DebtorListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(DebtorListPanel.class);

    @javafx.fxml.FXML
    private ListView<DebtorCard> debtorListView;

    public DebtorListPanel(ObservableList<Debtor> debtorsList) {
        super(FXML);
        setConnections(debtorsList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Debtor> debtorsList) {
        ObservableList<DebtorCard> mappedList = EasyBind.map(
                debtorsList, (debtor) -> new DebtorCard(debtor, debtorsList.indexOf(debtor) + 1));
        debtorListView.setItems(mappedList);
        debtorListView.setCellFactory(listView -> new DebtorListViewCell());
    }


    /**
     * Scrolls to the {@code DebtorCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            debtorListView.scrollTo(index);
            debtorListView.getSelectionModel().clearAndSelect(index);
        });
    }


    /**
     * Custom {@code ListCell} that displays the graphics of a {@code DebtorCard}.
     */
    class DebtorListViewCell extends ListCell<DebtorCard> {
        @Override
        protected void updateItem(DebtorCard debtor, boolean empty) {
            super.updateItem(debtor, empty);

            if (empty || debtor == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(debtor.getRoot());
            }
        }
    }

}
```
###### /java/seedu/address/ui/DebtorCard.java
``` java
/**
 * An UI component that displays information of a {@code Debtor}.
 */
public class DebtorCard extends UiPart<Region>  {

    private static final String FXML = "DebtorListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Debtor debtor;

    @javafx.fxml.FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label email;
    @FXML
    private Label debt;
    @FXML
    private FlowPane tags;

    public DebtorCard(Debtor debtor, int displayedIndex) {
        super(FXML);
        this.debtor = debtor;
        id.setText(displayedIndex + ". ");
        name.setText(debtor.getDebtor().getName().fullName);
        phone.setText(debtor.getDebtor().getPhone().value);
        email.setText(debtor.getDebtor().getEmail().value);
        debt.setText(debtor.getDebt().toString());
        debtor.getDebtor().getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DebtorCard)) {
            return false;
        }

        // state check
        DebtorCard card = (DebtorCard) other;
        return id.getText().equals(card.id.getText())
                && debtor.equals(card.debtor);
    }

}
```
###### /java/seedu/address/ui/CreditorCard.java
``` java
/**
 * An UI component that displays information of a {@code Creditor}.
 */
public class CreditorCard extends UiPart<Region> {

    private static final String FXML = "CreditorListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     */
    public final Creditor creditor;

    @javafx.fxml.FXML
    private HBox cardPane;
    @javafx.fxml.FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label email;
    @FXML
    private Label debt;
    @FXML
    private FlowPane tags;

    public CreditorCard(Creditor creditor, int displayedIndex) {
        super(FXML);
        this.creditor = creditor;
        id.setText(displayedIndex + ". ");
        name.setText(creditor.getCreditor().getName().fullName);
        phone.setText(creditor.getCreditor().getPhone().value);
        email.setText(creditor.getCreditor().getEmail().value);
        debt.setText(creditor.getDebt().toString());
        creditor.getCreditor().getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof CreditorCard)) {
            return false;
        }

        // state check
        CreditorCard card = (CreditorCard) other;
        return id.getText().equals(card.id.getText())
                    && creditor.equals(card.creditor); }
}
```
###### /java/seedu/address/logic/util/CalculationUtil.java
``` java
    /**
     * Returns the amount to add to the balance of a payer.
     */
    public static Balance calculateAmountToAddForPayer(Boolean isAddingTransaction,
                                                       Transaction transaction) {
        if (!isAddingTransaction) {
            return calculateAmountToAddForPayerForDeleteTransaction(transaction);
        } else {
            return calculateAmountToAddForPayerForPaymentTransaction(transaction);
        }
    }

    /**
     * Returns the amount to add to the balance or debt of a payee.
     */
    public static Balance calculateAmountToAddForPayee(Boolean isAddingTransaction,
                                                       Integer splitMethodValuesListIndex,
                                                       Transaction transaction) {
```
###### /java/seedu/address/logic/util/CalculationUtil.java
``` java

        switch (transaction.getTransactionType().value.toLowerCase()) {
        case TransactionType.TRANSACTION_TYPE_PAYDEBT:
            return calculateAmountToAddForPayeeForPaydebtTransaction(transaction);
        case TransactionType.TRANSACTION_TYPE_PAYMENT:
        default:
            return calculateAmountToAddForPayeeForPaymentTransaction(splitMethodValuesListIndex, transaction);
        }
    }

    /**
     * Calculates amount to add to the payee's balance after a new paydebt transaction is added.
     * Returned amount will be negative.
     */
    private static Balance calculateAmountToAddForPayeeForPaydebtTransaction(Transaction transaction) {
        Double amountToAdd = Double.valueOf(transaction.getAmount().value);
        return getRoundedFormattedBalance(amountToAdd);
    }

```
###### /java/seedu/address/logic/parser/AddTransactionCommandParser.java
``` java
/**
 * Parses input arguments and creates a new AddTransactionCommand object
 */
public class AddTransactionCommandParser implements Parser<AddTransactionCommand> {
    private TransactionType transactionType;
    private SplitMethod splitMethod;
    private List<Integer> units = Collections.emptyList();
    private List<Integer> percentages = Collections.emptyList();

    /**
     * Parses the given {@code String} of arguments in the context of the AddTransactionCommand
     * and returns an AddTransactionCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddTransactionCommand parse(String args, Model model) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TRANSACTION_TYPE, PREFIX_PAYER, PREFIX_AMOUNT,
                        PREFIX_DESCRIPTION, PREFIX_PAYEE, PREFIX_SPLIT_METHOD, PREFIX_SPLIT_BY_UNITS,
                        PREFIX_SPLIT_BY_PERCENTAGE);
        if (!arePrefixesPresent(argMultimap, PREFIX_TRANSACTION_TYPE, PREFIX_PAYER, PREFIX_AMOUNT,
                PREFIX_DESCRIPTION, PREFIX_PAYEE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddTransactionCommand.MESSAGE_USAGE));
        }

```
###### /java/seedu/address/logic/parser/AddTransactionCommandParser.java
``` java
        try {
            Person payer = model.findPersonByName(ParserUtil.parseName(argMultimap.getValue(PREFIX_PAYER)).get());
            Amount amount = ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT)).get();
            Description description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION)).get();
            UniquePersonList payees = model.getPayeesList(argMultimap, model);
            Date dateTime = Date.from(Instant.now(Clock.system(ZoneId.of("Asia/Singapore"))));
```
###### /java/seedu/address/logic/parser/AddTransactionCommandParser.java
``` java
    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}

```
###### /java/seedu/address/logic/parser/ParserUtil.java
``` java
    /**
     * Parses a {@code String description} into a {@code Description}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code tag} is invalid.
     */
    public static Description parseDescription(String description) throws IllegalValueException {
        requireNonNull(description);
        String trimmedDescription = description.trim();
        if (!Description.isValidDescription(trimmedDescription)) {
            throw new IllegalValueException(Description.MESSAGE_DESCRIPTION_CONSTRAINTS);
        }
        return new Description(trimmedDescription);
    }

    /**
     * Parses {@code Collection<String> Description} into a {@code Set<Description>}.
     */
    public static Optional<Description> parseDescription(Optional<String> description) throws IllegalValueException {
        requireNonNull(description);
        return description.isPresent() ? Optional.of(parseDescription(description.get())) : Optional.empty();
    }

    /**
     * Parses {@code Collection<String> TransactionType} into a {@code Set<TransactionType>}.
     */
    public static TransactionType parseTransactionType(String type) throws IllegalValueException {
        requireNonNull(type);
        String trimmedType = type.trim();
        if (!TransactionType.isValidTransactionType(trimmedType)) {
            throw new IllegalValueException(TransactionType.MESSAGE_TRANSACTION_TYPE_CONSTRAINTS);
        }
        return new TransactionType(trimmedType);
    }

```
###### /java/seedu/address/logic/commands/AddTransactionCommand.java
``` java
/**
 * Adds a new transaction to the SplitSplit application.
 */
public class AddTransactionCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addtransaction";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a new transaction to the address book. \n"
            + "Parameters: "
            + PREFIX_TRANSACTION_TYPE + "TRANSACTION TYPE "
            + PREFIX_PAYER + "PAYER NAME "
            + PREFIX_AMOUNT + "AMOUNT "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + "[" + PREFIX_PAYEE + "PAYEE NAME] "
            + PREFIX_SPLIT_METHOD + "SPLIT METHOD "
            + PREFIX_SPLIT_BY_UNITS + "LIST OF UNITS "
            + PREFIX_SPLIT_BY_PERCENTAGE + "LIST OF PERCENTAGES...\n"
```
###### /java/seedu/address/logic/commands/AddTransactionCommand.java
``` java
    public static final String MESSAGE_SUCCESS = "New transaction added";

    private final Transaction toAdd;

    /**
     * Creates an AddTransactionCommand to add the specified {@code Transaction}
     */
    public AddTransactionCommand(Transaction transaction) {
        requireNonNull(transaction);
        toAdd = transaction;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.addTransaction(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (PersonNotFoundException pnfe) {
            throw new CommandException(MESSAGE_NONEXISTENT_PERSON);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddTransactionCommand// instanceof handles nulls
                && toAdd.equals(((AddTransactionCommand) other).toAdd));
    }
}
```
###### /java/seedu/address/logic/LogicManager.java
``` java
    /**
     * Update the Debtor and Creditor list to an empty list
     */
    @Override
    public void updateDebtorsAndCreditorList() {
        model.updateDebtorList(PREDICATE_SHOW_NO_DEBTORS);
        model.updateCreditorList(PREDICATE_SHOW_NO_CREDITORS);
    }
    /**
     * Update the people in the Debtor and Creditor list
     */
    public void updateDebtorsAndCreditorList(Person person) {
        model.updateCreditorList(PREDICATE_SHOW_ALL_CREDITORS);
        model.updateDebtorList(PREDICATE_SHOW_ALL_DEBTORS);
        DebtsTable debtsTable = model.getAddressBook().getDebtsTable();
        DebtsList debtsList = debtsTable.get(person);
        model.getAddressBook().setDebtors(debtsList);
        model.getAddressBook().setCreditors(debtsList);
    }


}
```
###### /java/seedu/address/storage/XmlAdaptedTransaction.java
``` java
/**
 * JAXB-friendly adapted version of the Transaction.
 */
public class XmlAdaptedTransaction {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Transaction's %s field is missing!";
    public static final String MISSING_FIELD_MESSAGE_FORMAT_DATE =
            "Transaction's %s field is missing, or is in wrong format "
                    + "(date format example: 2018-04-14T17:22:56.218+08:00)";

    @XmlElement(required = true)
    private String transactionType;
    @XmlElement(required = true)
    private XmlAdaptedPerson payer;
    @XmlElement(required = true)
    private String amount;
    @XmlElement(required = true)
    private String description;
    @XmlElement(required = true)
    private Date dateTime;
```
###### /java/seedu/address/storage/XmlAdaptedTransaction.java
``` java

    /**
     * Constructs an XmlAdaptedTransaction.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedTransaction() {}

    /**
     * Constructs an {@code XmlAdaptedTransaction} with the given transactions details.
     */
    public XmlAdaptedTransaction(String transactionType, Person payer, String amount, String description,
                                 UniquePersonList payees, String splitMethod, List<Integer> unitsList,
                                 List<Integer> percentagesList) {
        this.payer = new XmlAdaptedPerson(payer);
        this.transactionType = transactionType;
        this.amount = amount;
        this.description = description;
        this.dateTime = Date.from(Instant.now(Clock.system(ZoneId.of("Asia/Singapore"))));

```
###### /java/seedu/address/storage/XmlAdaptedTransaction.java
``` java
    /**
     * Constructs an {@code XmlAdaptedTransaction} with the given transaction details.
     */
    public XmlAdaptedTransaction(String transactionType, Person payer, String amount, String description,
                                 UniquePersonList payees, Date dateTime, String splitMethod, List<Integer> unitsList,
                                 List<Integer> percentagesList) {
        this.payer = new XmlAdaptedPerson(payer);
        this.transactionType = transactionType;
        this.amount = amount;
        this.description = description;
        this.dateTime = dateTime;

```
###### /java/seedu/address/storage/XmlAdaptedTransaction.java
``` java
    /**
     * Converts a given Transaction into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedTransaction
     */
    public XmlAdaptedTransaction(Transaction source) {
        transactionType = source.getTransactionType().value;
        payer = new XmlAdaptedPerson(source.getPayer());
        amount = source.getAmount().toString();
        description = source.getDescription().value;
        dateTime = source.getDateTime();

```
###### /java/seedu/address/storage/XmlAdaptedTransaction.java
``` java
    /**
     * Converts this jaxb-friendly adapted transaction's object into the model's Transaction object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted Transaction
     */
    public Transaction toModelType() throws IllegalValueException {

```
###### /java/seedu/address/storage/XmlAdaptedTransaction.java
``` java
        if (this.amount == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Amount.class.getSimpleName()));
        }
        if (!this.amount.contains(".")) {
            this.amount += ".00";
        }
        if (!Amount.isValidAmount(this.amount)) {
            throw new IllegalValueException(Amount.MESSAGE_AMOUNT_CONSTRAINTS);
        }
        final Amount amount = new Amount(this.amount);

        if (this.description == null) {
            throw new IllegalValueException(String.format
                    (MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName()));
        }
        if (!Description.isValidDescription(this.description)) {
            throw new IllegalValueException(Description.MESSAGE_DESCRIPTION_CONSTRAINTS);
        }
        final Description description = new Description(this.description);

        if (this.transactionType == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    TransactionType.class.getSimpleName()));
        }
        if (!TransactionType.isValidTransactionType(this.transactionType)) {
            throw new IllegalValueException(TransactionType.MESSAGE_TRANSACTION_TYPE_CONSTRAINTS);
        }
        final TransactionType transactionType = new TransactionType(this.transactionType);

        if (this.dateTime == null) {
            throw new IllegalValueException(String.format
                    (MISSING_FIELD_MESSAGE_FORMAT_DATE, Date.class.getSimpleName()));
        }

        final Date dateTime = this.dateTime;

```
###### /java/seedu/address/storage/XmlAdaptedTransaction.java
``` java
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedTransaction)) {
            return false;
        }

        XmlAdaptedTransaction otherTransaction = (XmlAdaptedTransaction) other;
        return Objects.equals(payer, otherTransaction.payer)
                && Objects.equals(amount, otherTransaction.amount)
                && Objects.equals(description, otherTransaction.description)
                && Objects.equals(payees, otherTransaction.payees)
                && Objects.equals(transactionType, otherTransaction.transactionType)
                && Objects.equals(splitMethod, otherTransaction.splitMethod);
    }

```
###### /java/seedu/address/model/transaction/Amount.java
``` java
/**
 * Represents the amount that a Payer paid in a SmartSplit transaction.
 */
public class Amount {

    public static final String MESSAGE_AMOUNT_CONSTRAINTS =
            "Amount can only take in a positive numerical number up to 2 decimal places, "
                    + "and it should not be blank";

    // The first character of the amount must not be a whitespace,
    // otherwise " " (a blank string) becomes a valid input.
    public static final String AMOUNT_VALIDATION_REGEX = "^\\d+(\\.\\d{1,2})?$";

    public final String value;

    /**
     * Constructs an {@code Amount}.
     *
     * @param amount A valid amount.
     */
    public Amount(String amount) {
        requireNonNull(amount);
        checkArgument(isValidAmount(amount), MESSAGE_AMOUNT_CONSTRAINTS);
        this.value = amount;
    }
    /**
     * Returns true if a given string is a valid amount.
     */
    public static boolean isValidAmount(String test) {
        return test.matches(AMOUNT_VALIDATION_REGEX);
    }

    public double getDoubleValue() {
        return Double.valueOf(value);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Amount // instanceof handles nulls
                && this.value.equals(((Amount) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}

```
###### /java/seedu/address/model/transaction/Description.java
``` java
/**
 * Represents a Transaction's description in the address book.
 */
public class Description {
    public static final String MESSAGE_DESCRIPTION_CONSTRAINTS =
            "Transaction description can take any value and should not be blank";
    /*
     * Description must not be only space or "" (a blank string).
     */
    public static final String DESCRIPTION_VALIDATION_REGEX = "^(\\s|\\S)*(\\S)+(\\s|\\S)*$";

    public final String value;

    /**
     * Constructs an {@code Description}.
     *
     * @param description A valid description.
     */
    public Description(String description) {
        requireNonNull(description);
        checkArgument(isValidDescription(description), MESSAGE_DESCRIPTION_CONSTRAINTS);
        this.value = description;
    }

    /**
     * Returns true if a given string is a valid person email.
     */
    public static boolean isValidDescription(String test) {
        return test.matches(DESCRIPTION_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}

```
###### /java/seedu/address/model/transaction/TransactionList.java
``` java
/**
 * Record all transactions to the list.
 *
 */
public class TransactionList implements Iterable<Transaction> {

    private final ObservableList<Transaction> internalList = FXCollections.observableArrayList();


    /**
     * Adds a transaction to the list.
     *
     */
    public void add(Transaction toAdd) {
        requireNonNull(toAdd);
        internalList.add(toAdd);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Transaction> asObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Transaction> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniquePersonList // instanceof handles nulls
                && this.internalList.equals(((TransactionList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

```
###### /java/seedu/address/model/transaction/TransactionType.java
``` java
/**
 * Types of transaction
 */
public class TransactionType {

    public static final String MESSAGE_TRANSACTION_TYPE_CONSTRAINTS =
            "Transaction type can only be \"paydebt\" or \"payment\" "
                    + "and it should not be blank";
    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String TRANSACTION_TYPE_PAYMENT = "payment";
    public static final String TRANSACTION_TYPE_PAYDEBT = "paydebt";

    public final String value;

    /**
     * Constructs an {@code TransactionType}.
     *
     * @param type a valid transaction type.
     */
    public TransactionType(String type) {
        requireNonNull(type);
        checkArgument(isValidTransactionType(type), MESSAGE_TRANSACTION_TYPE_CONSTRAINTS);
        this.value = type;
    }
    /**
     * Returns true if a given string is a valid transaction type.
     */
    public static boolean isValidTransactionType(String test) {
        if (test.toLowerCase().equals(TRANSACTION_TYPE_PAYMENT) || test.toLowerCase().equals(
                TRANSACTION_TYPE_PAYDEBT)) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TransactionType // instanceof handles nulls
                && this.value.equals(((TransactionType) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### /java/seedu/address/model/person/UniqueDebtorList.java
``` java
import static java.util.Objects.requireNonNull;

import java.util.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.DebtsList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
/**
 * A list of debtors
 */
public class UniqueDebtorList implements Iterable<Debtor> {

    private final ObservableList<Debtor> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent Debtor as the given argument.
     */
    public boolean contains(Debtor toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }
    /**
     * Adds a person to the list.
     *
     * @throws DuplicatePersonException if the person to add is a duplicate of an existing person in the list.
     */
    public void add(Debtor toAdd) {
        requireNonNull(toAdd);
        internalList.add(toAdd);
    }

    public void setDebtor(UniqueDebtorList replacement) {
        this.internalList.setAll(replacement.internalList);
    }
    public void setDebtors(DebtsList debtsList) {

        final UniqueDebtorList replacement = new UniqueDebtorList();
        for (DebtsList.Entry<Person, Balance> entry : debtsList.entrySet()) {
            if (entry.getValue().getDoubleValue() < 0) {
                Person person = entry.getKey();
                Balance debt = entry.getValue().getInverse();
                Debtor debtor = new Debtor(person, debt);
                replacement.add(debtor);
            }
        }
        setDebtor(replacement);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Debtor> asObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }


    @Override
    public Iterator<Debtor> iterator() {
        return internalList.iterator();
    }
}
```
###### /java/seedu/address/model/person/UniqueCreditorList.java
``` java
import static java.util.Objects.requireNonNull;

import java.util.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.DebtsList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
/**
 * A list of creditors
 */

public class UniqueCreditorList implements Iterable<Creditor> {

    private final ObservableList<Creditor> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent Debtor as the given argument.
     */
    public boolean contains(Debtor toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }
    /**
     * Adds a person to the list.
     *
     * @throws DuplicatePersonException if the person to add is a duplicate of an existing person in the list.
     */
    public void add(Creditor toAdd) {
        requireNonNull(toAdd);
        internalList.add(toAdd);
    }

    public void setCreditors(UniqueCreditorList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setCreditors(DebtsList debtsList) {

        final UniqueCreditorList replacement = new UniqueCreditorList();
        for (DebtsList.Entry<Person, Balance> entry : debtsList.entrySet()) {
            if (entry.getValue().getDoubleValue() > 0) {
                Person person = entry.getKey();
                Balance debt = entry.getValue();
                Creditor creditor = new Creditor(person, debt);
                replacement.add(creditor);
            }
        }
        setCreditors(replacement);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Creditor> asObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }


    @Override
    public Iterator<Creditor> iterator() {
        return internalList.iterator();
    }
}
```
###### /java/seedu/address/model/person/Debtor.java
``` java
/**
 * Represents a Debtor in SmartSplit.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Debtor {

    private final Person debtor;
    private Balance debt = new Balance("0.00");

    public Debtor(Person debtor, Balance debt) {
        this.debtor = debtor;
        this.debt = debt;

    }

    public Person getDebtor() {
        return debtor;
    }

    public Balance getDebt() {
        return debt; }

}


```
###### /java/seedu/address/model/person/Creditor.java
``` java
/**
 * Represents a Creditor in SmartSplit.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Creditor {

    private final Person creditor;
    private Balance debt = new Balance("0.00");

    public Creditor(Person creditor, Balance debt) {
        this.creditor = creditor;
        this.debt = debt;

    }

    public Person getCreditor() {
        return creditor;
    }

    public Balance getDebt() {
        return debt; }
}
```
###### /java/seedu/address/model/AddressBook.java
``` java
    public void setDebtors(DebtsList debtsList)  {
        this.debtors.setDebtors(debtsList);
    }

    public void setCreditors(DebtsList debtsList) {
        this.creditors.setCreditors(debtsList); }
    public void setTransactions(List<Transaction> transactions) {
        this.transactions.setTransactions(transactions);
    }
    public void setDebtsTable(DebtsTable debtsTable) {
        final DebtsTable replacement = new DebtsTable();
        for (DebtsTable.Entry<Person, DebtsList> entry : debtsTable.entrySet()) {
            DebtsList debtsList = new DebtsList();
            debtsList.putAll(entry.getValue());
            replacement.put(entry.getKey(), debtsList);
        }
        this.debtsTable = replacement;
    }
    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);
        setTags(new HashSet<>(newData.getTagList()));
        List<Person> syncedPersonList = newData.getPersonList().stream()
                .map(this::syncWithMasterTagList)
                .collect(Collectors.toList());
        List<Transaction> syncedTransactionList = newData.getTransactionList();
        DebtsTable syncedDebtsTable = newData.getDebtsTable();
        try {
            setPersons(syncedPersonList);
            setTransactions(syncedTransactionList);
            setDebtsTable(syncedDebtsTable);
        } catch (DuplicatePersonException e) {
            throw new AssertionError("SmartSplit should not have duplicate persons");
        }
    }

    //// person-level operations

    /**
     * Adds a person to the address book.
     * Also checks the new person's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the person to point to those in {@link #tags}.
     *
     * @throws DuplicatePersonException if an equivalent person already exists.
     */
    public void addPerson(Person p) throws DuplicatePersonException {
        Person person = syncWithMasterTagList(p);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any person
        // in the person list.
        persons.add(person);
        debtsTable.add(person);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code AddressBook}'s tag list will be updated with the tags of {@code editedPerson}.
     *
     * @throws DuplicatePersonException if updating the person's details causes the person to be equivalent to
     *      another existing person in the list.
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     *
     * @see #syncWithMasterTagList(Person)
     */
    public void updatePerson(Person target, Person editedPerson)
            throws DuplicatePersonException, PersonNotFoundException {
        requireNonNull(editedPerson);

        Person syncedEditedPerson = syncWithMasterTagList(editedPerson);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any person
        // in the person list.
        persons.setPerson(target, syncedEditedPerson);
        debtsTable.setPerson(target, editedPerson);
        transactions.setPerson(target, editedPerson);
    }

    /**
     *  Updates the master tag list to include tags in {@code person} that are not in the list.
     *  @return a copy of this {@code person} such that every tag in this person points to a Tag object in the master
     *  list.
     */
    private Person syncWithMasterTagList(Person person) {
        final UniqueTagList personTags = new UniqueTagList(person.getTags());
        tags.mergeFrom(personTags);

        // Create map with values = tag object references in the master list
        // used for checking person tag references
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        tags.forEach(tag -> masterTagObjects.put(tag, tag));

        // Rebuild the list of person tags to point to the relevant tags in the master tag list.
        final Set<Tag> correctTagReferences = new HashSet<>();
        personTags.forEach(tag -> correctTagReferences.add(masterTagObjects.get(tag)));
        return new Person(
                person.getName(), person.getPhone(), person.getEmail(), person.getBalance(),
                correctTagReferences);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * @throws PersonNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */
    public boolean removePerson(Person key) throws PersonNotFoundException, CommandException {
```
###### /java/seedu/address/model/AddressBook.java
``` java
        if (debtExists(key)) {
            throw new CommandException(String.format(MESSAGE_DEBT_NOT_PAID, key));
        }
        if (persons.remove(key)) {
            return true;
        } else {
            throw new PersonNotFoundException();
        }
    }
```
###### /java/seedu/address/model/AddressBook.java
``` java
    /**
     * check if the person to be deleted still owed any unpaid debt
     */
    private boolean debtExists(Person key) {
        DebtsList debtsList = debtsTable.get(key);
        if (debtsList != null) {
            for (Balance value : debtsList.values()) {
                if (value.getDoubleValue() != 0) {
                    return true;
                }
            }
        }
        return false;
    }

    //// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

    //// util methods

    @Override
    public String toString() {
        return persons.asObservableList().size() + " persons, "
                + tags.asObservableList().size() +  " tags, "
                + transactions.asObservableList().size() + " transactions";
        // TODO: refine later
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asObservableList();
    }

    @Override
    public ObservableList<Tag> getTagList() {
        return tags.asObservableList();
    }

    @Override
    public DebtsTable getDebtsTable() {
        return debtsTable;
    }

    @Override
    public ObservableList<Transaction> getTransactionList() {
        return transactions.asObservableList();
    }

    public ObservableList<Debtor> getDebtorsList() {
        return debtors.asObservableList();
    }

    public ObservableList<Creditor> getCreditorsList() {
        return creditors.asObservableList();
    }
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && this.persons.equals(((AddressBook) other).persons)
                && this.tags.equalsOrderInsensitive(((AddressBook) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(persons, tags);
    }
```
###### /java/seedu/address/model/AddressBook.java
``` java
    /**
     * Adds a {@code transaction} to the list of transactions.
     */
    public void addTransaction(Transaction transaction) throws CommandException {
```
###### /java/seedu/address/model/AddressBook.java
``` java
        transactions.add(transaction);
        debtsTable.updateDebts(transaction, true);
        debtsTable.display();
    }

    /**
     * Update each payer and payee(s) balance whenever each new transaction is added or deleted
     */
    public void updatePayerAndPayeesBalance(Boolean isAddingTransaction, Transaction transaction, Person payer,
                                            UniquePersonList payees) {
        if (!transaction.getTransactionType().value.toLowerCase().equals(TransactionType.TRANSACTION_TYPE_PAYDEBT)) {
            updatePayerBalance(isAddingTransaction, transaction, payer);
```
###### /java/seedu/address/model/ModelManager.java
``` java
    /**
     * Check if the payer or payee in the transaction indicated exists
     */
    @Override
    public boolean personNotFoundInTransaction(Person person) throws PersonFoundException {
        Set<Transaction> matchingTransactions = addressBook.getTransactionList()
                .stream()
                .filter(transaction -> transaction.getPayer().equals(person))
                .collect(Collectors.toSet());

        if (matchingTransactions.isEmpty()) {
            return true;
        } else {
            throw new PersonFoundException();
        }
    }

    @Override
    public List<Transaction> findTransactionsWithPerson(Person person) {
        List<Transaction> matchingTransactions = addressBook.getTransactionList()
                .filtered(transaction -> transaction.getPayer().equals(person));
        return matchingTransactions;
    }
    /**
     * Returns an unmodifiable view of the list of {@code Transaction}
     */
    @Override
    public ObservableList<Transaction> getFilteredTransactionList() {
        return FXCollections.unmodifiableObservableList(filteredTransactions);
    }

    public ObservableList<Debtor> getFilteredDebtors() {
        return FXCollections.unmodifiableObservableList(filteredDebtors);
    }

    public ObservableList<Creditor> getFilteredCreditors() {
        return FXCollections.unmodifiableObservableList(filteredCreditors);
    }
    @Override
    public void addTransaction(Transaction transaction) throws CommandException, PersonNotFoundException {
        addressBook.addTransaction(transaction);
        addressBook.updatePayerAndPayeesBalance(true, transaction, findPersonByName(
                    transaction.getPayer().getName()), getPayeesList(transaction.getPayees()));
        updateFilteredTransactionList(PREDICATE_SHOW_ALL_TRANSACTIONS);
        updateDebtorList(PREDICATE_SHOW_NO_DEBTORS);
        updateCreditorList(PREDICATE_SHOW_NO_CREDITORS);
        updateFilteredPersonList(PREDICATE_SHOW_NO_PERSON);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }

```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public void updateDebtorList(Predicate<Debtor> predicate) {
        requireNonNull(predicate);
        filteredDebtors.setPredicate(predicate);
    }

    @Override
    public void updateCreditorList(Predicate<Creditor> predicate) {
        requireNonNull(predicate);
        filteredCreditors.setPredicate(predicate);
    }

    @Override
    public void updateFilteredTransactionList(Predicate<Transaction> predicate) {
        requireNonNull(predicate);
        filteredTransactions.setPredicate(predicate);
    }


    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return addressBook.equals(other.addressBook)
                && filteredPersons.equals(other.filteredPersons);
    }
}
```
