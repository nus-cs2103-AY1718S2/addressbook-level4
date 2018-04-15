# steven-jia
###### /java/seedu/address/logic/util/CalculationUtil.java
``` java
        // This is a deleteTransaction command
        if (!isAddingTransaction) {
            return calculateAmountToAddForPayeeForDeleteTransaction(splitMethodValuesListIndex, transaction);
        }
```
###### /java/seedu/address/logic/util/CalculationUtil.java
``` java
    /**
     * Calculates amount to add to the payer's balance after a new payment transaction is added.
     * Returned amount will be positive.
     */
    public static Balance calculateAmountToAddForPayerForPaymentTransaction(Transaction transaction) {
        Double amountToAdd;
        switch (transaction.getSplitMethod().method) {
        case UNITS:
            Integer numberOfUnitsForPayer = transaction.getUnits().get(0);
            int totalNumberOfUnits = calculateTotalNumberOfUnits(transaction.getUnits());
            amountToAdd = transaction.getAmount().getDoubleValue()
                    * numberOfUnitsForPayer / totalNumberOfUnits;
            break;
        case PERCENTAGE:
            Integer percentageForPayer = transaction.getPercentages().get(0);
            amountToAdd = transaction.getAmount().getDoubleValue()
                    * percentageForPayer / 100;
            break;
        case EVENLY:
        default:
            amountToAdd = calculateAmountForPayerSplitEvenly(transaction.getAmount(),
                    transaction.getPayees());
            break;
        }
        return getRoundedFormattedBalance(amountToAdd);
    }

    /**
     * Calculates amount to add to the payee's balance after a new payment transaction is added.
     * Returned amount will be negative.
     */
    public static Balance calculateAmountToAddForPayeeForPaymentTransaction(Integer splitMethodValuesListIndex,
                                                                            Transaction transaction) {
        Double amountToAdd;
        switch (transaction.getSplitMethod().method) {
        case UNITS:
            Integer numberOfUnitsForPayee = transaction.getUnits().get(splitMethodValuesListIndex);
            int totalNumberOfUnits = calculateTotalNumberOfUnits(transaction.getUnits());
            amountToAdd = Double.valueOf(transaction.getAmount().value) * numberOfUnitsForPayee
                    / totalNumberOfUnits;
            break;
        case PERCENTAGE:
            Integer percentageForPayee = transaction.getPercentages().get(splitMethodValuesListIndex);
            amountToAdd = Double.valueOf(transaction.getAmount().value) * percentageForPayee / 100;
            break;
        case EVENLY:
        default:
            amountToAdd = calculateAmountForPayeeSplitEvenly(transaction.getAmount(),
                    transaction.getPayees());
            break;
        }
        return getRoundedFormattedBalance(amountToAdd);
    }
    /**
     * Calculates amount to add to the payer's balance after a transaction is deleted.
     * Returned amount will be negative.
     */
    public static Balance calculateAmountToAddForPayerForDeleteTransaction(Transaction transaction) {
        if (transaction.getTransactionType().value.equals(TransactionType.TRANSACTION_TYPE_PAYDEBT)) {
            return getRoundedFormattedBalance(-transaction.getAmount().getDoubleValue());
        }

        Double amountToAdd;
        switch (transaction.getSplitMethod().method) {
        case UNITS:
            Integer numberOfUnitsForPayer = transaction.getUnits().get(0);
            int totalNumberOfUnits = calculateTotalNumberOfUnits(transaction.getUnits());
            amountToAdd = -transaction.getAmount().getDoubleValue()
                    * numberOfUnitsForPayer / totalNumberOfUnits;
            break;
        case PERCENTAGE:
            Integer percentageForPayer = transaction.getPercentages().get(0);
            amountToAdd = -transaction.getAmount().getDoubleValue()
                    * percentageForPayer / 100;
            break;
        case EVENLY:
        default:
            amountToAdd = -calculateAmountForPayerSplitEvenly(transaction.getAmount(),
                    transaction.getPayees());
            break;
        }
        return getRoundedFormattedBalance(amountToAdd);
    }
    /**
     * Calculates amount to add to the payee's balance after a transaction is deleted.
     * Returned amount will be positive.
     */
    public static Balance calculateAmountToAddForPayeeForDeleteTransaction(Integer splitMethodValuesListIndex,
                                                                           Transaction transaction) {
        if (transaction.getTransactionType().value.equals(TransactionType.TRANSACTION_TYPE_PAYDEBT)) {
            return getRoundedFormattedBalance(transaction.getAmount().getDoubleValue()).getInverse();
        }

        Double amountToAdd;
        switch (transaction.getSplitMethod().method) {
        case UNITS:
            Integer numberOfUnitsForPayee = transaction.getUnits().get(splitMethodValuesListIndex);
            int totalNumberOfUnits = calculateTotalNumberOfUnits(transaction.getUnits());
            amountToAdd = -transaction.getAmount().getDoubleValue() * numberOfUnitsForPayee
                    / totalNumberOfUnits;
            break;
        case PERCENTAGE:
            Integer percentageForPayee = transaction.getPercentages().get(splitMethodValuesListIndex);
            amountToAdd = -transaction.getAmount().getDoubleValue() * percentageForPayee / 100;
            break;
        case EVENLY:
        default:
            amountToAdd = -calculateAmountForPayeeSplitEvenly(transaction.getAmount(),
                    transaction.getPayees());
            break;
        }
        return getRoundedFormattedBalance(amountToAdd);
    }

    /**
     * Calculates amount to add to a payer's balance for a transaction that is split evenly.
     * Returned amount will be positive.
     */
    private static Double calculateAmountForPayerSplitEvenly(Amount amount, UniquePersonList payees) {
        int numberOfInvolvedPersons = calculateNumberOfInvolvedPersons(payees);
        return Double.valueOf(amount.value) / numberOfInvolvedPersons;
    }

    /**
     * Calculates amount to add to a payee's balance for a transaction that is split evenly.
     * Returned amount will be positive.
     */
    private static Double calculateAmountForPayeeSplitEvenly(Amount amount, UniquePersonList payees) {
        int numberOfInvolvedPersons = calculateNumberOfInvolvedPersons(payees);
        return Double.valueOf(amount.value) / numberOfInvolvedPersons;
    }

    private static Balance getRoundedFormattedBalance(Double amountToAdd) {
        amountToAdd = round(amountToAdd, NUMBER_OF_DECIMAL_PLACES);
        DecimalFormat formatter = new DecimalFormat("#.00");
        return new Balance(String.valueOf(formatter.format(amountToAdd)));
    }

    /**
     * Calculates the total number of people involved in a transaction
     */
    public static int calculateNumberOfInvolvedPersons(UniquePersonList payees) {
        return payees.asObservableList().size() + 1;
    }

    /**
     * Calculates the total number of units given a list of units
     */
    private static int calculateTotalNumberOfUnits(List<Integer> units) {
        int totalNumberOfUnits = 0;
        for (Integer unit: units) {
            totalNumberOfUnits += unit;
        }
        return totalNumberOfUnits;
    }

    private static double round(double value, int places) {
        BigDecimal amount = new BigDecimal(value);
        amount = amount.setScale(places, RoundingMode.HALF_UP);
        return amount.doubleValue();
    }

}


```
###### /java/seedu/address/logic/parser/AddTransactionCommandParser.java
``` java
        parseTransactionType(argMultimap);

        if (transactionType.value.toLowerCase().equals(TransactionType.TRANSACTION_TYPE_PAYMENT)) {
            parseSplitMethod(argMultimap);
        } else if (transactionType.value.toLowerCase().equals(TransactionType.TRANSACTION_TYPE_PAYDEBT)) {
            if (arePrefixesPresent(argMultimap, PREFIX_SPLIT_METHOD)
                    || arePrefixesPresent(argMultimap, PREFIX_SPLIT_BY_UNITS)
                    || arePrefixesPresent(argMultimap, PREFIX_SPLIT_BY_PERCENTAGE)) {
                throw new ParseException(MESSAGE_TOO_MANY_PREFIXES_FOR_PAYDEBT);
            }
            splitMethod = new SplitMethod(SplitMethod.SPLIT_METHOD_NOT_APPLICABLE);
        }
```
###### /java/seedu/address/logic/parser/AddTransactionCommandParser.java
``` java
            validatePayees(payer, payees);
            validateSplitMethodValues(payees, splitMethod, units, percentages);

            Transaction transaction = new Transaction(transactionType, payer, amount, description, dateTime,
                    payees, splitMethod, units, percentages);
            return new AddTransactionCommand(transaction);
        } catch (PersonNotFoundException pnfe) {
            throw new ParseException(MESSAGE_NONEXISTENT_PERSON, pnfe);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        } catch (CommandException a) {
            throw new ParseException(a.getMessage(), a);
        }
    }
```
###### /java/seedu/address/logic/parser/AddTransactionCommandParser.java
``` java
    /**
     * Attempts to parse the split method
     * @param argMultimap
     * @throws ParseException if the split method is not one of the listed options
     */
    private void parseSplitMethod(ArgumentMultimap argMultimap) throws ParseException {
        try {
            splitMethod = ParserUtil.parseSplitMethod(argMultimap.getValue(PREFIX_SPLIT_METHOD));
            switch (splitMethod.method) {
            case EVENLY:
                break;
            case UNITS:
                units = ParserUtil.parseUnitsList(argMultimap.getValue(PREFIX_SPLIT_BY_UNITS));
                break;
            case PERCENTAGE:
                percentages = ParserUtil.parsePercentagesList(argMultimap.getValue(PREFIX_SPLIT_BY_PERCENTAGE));
                break;
            default:
            }
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Parses the transaction type
     * @param argMultimap
     * @throws ParseException if the transaction type is not one of the listed options
     */
    private void parseTransactionType(ArgumentMultimap argMultimap) throws ParseException {
        try {
            transactionType = ParserUtil.parseTransactionType(argMultimap.getValue(
                    PREFIX_TRANSACTION_TYPE).get());
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Checks list of units and list of percentages for validity
     */
    private void validateSplitMethodValues(UniquePersonList payees, SplitMethod splitMethod,
                                           List<Integer> units, List<Integer> percentages)
            throws IllegalValueException {
        if (splitMethod.method.equals(SplitMethod.Method.UNITS)) {

            if (units.isEmpty()) {
                throw new IllegalValueException(String.format(MESSAGE_MISSING_UNITS_VALUES,
                        splitMethod.toString()));
            }
            if (units.size() != payees.asObservableList().size() + 1) {
                throw new IllegalValueException(
                        String.format(MESSAGE_INVALID_NUMBER_OF_VALUES, splitMethod.toString()));
            }
        } else if (splitMethod.method.equals(SplitMethod.Method.PERCENTAGE)) {
            if (percentages.isEmpty()) {
                throw new ParseException(String.format(MESSAGE_MISSING_PERCENTAGES_VALUES,
                        splitMethod.toString()));
            }
            if (percentages.size() != payees.asObservableList().size() + 1) {
                throw new IllegalValueException(String.format(MESSAGE_INVALID_NUMBER_OF_VALUES,
                        splitMethod.toString()));
            }
            Integer total = 0;
            for (Integer percentage: percentages) {
                total += percentage;
            }
            if (total != 100) {
                throw new IllegalValueException(MESSAGE_INVALID_PERCENTAGE_VALUES);
            }
        }
    }

    private void validatePayees(Person payer, UniquePersonList payees) throws CommandException {
        if (payees.contains(payer)) {
            throw new CommandException(MESSAGE_PAYEE_IS_PAYER);
        }
    }

```
###### /java/seedu/address/logic/parser/ParserUtil.java
``` java
    /**
     * Parses a {@code String amount} into a {@code Amount}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code tag} is invalid.
     */
    public static Amount parseAmount(String amount) throws IllegalValueException {
        requireNonNull(amount);
        String parsedAmount = amount.trim();
        if (!parsedAmount.contains(".")) {
            parsedAmount += ".00";
        }
        if (!Amount.isValidAmount(parsedAmount)) {
            throw new IllegalValueException(Amount.MESSAGE_AMOUNT_CONSTRAINTS);
        }
        return new Amount(parsedAmount);
    }

    /**
     * Parses {@code Collection<String> Amount} into a {@code Set<Amount>}.
     */
    public static Optional<Amount> parseAmount(Optional<String> amount) throws IllegalValueException {
        requireNonNull(amount);
        return amount.isPresent() ? Optional.of(parseAmount(amount.get())) : Optional.empty();
    }
```
###### /java/seedu/address/logic/parser/ParserUtil.java
``` java
    /**
     * Parses a {@code String splitMethod} into a {@code SplitMethod}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code splitMethod} is invalid.
     */
    public static SplitMethod parseSplitMethod(String splitMethod) throws IllegalValueException {
        requireNonNull(splitMethod);
        String trimmedSplitMethod = splitMethod.trim();
        if (!SplitMethod.isValidSplitMethod(trimmedSplitMethod)) {
            throw new IllegalValueException(SplitMethod.MESSAGE_SPLIT_METHOD_CONSTRAINTS);
        }
        return new SplitMethod(trimmedSplitMethod);
    }

    /**
     * Parses {@code String splitMethod} into a {@code SplitMethod}.
     */
    public static SplitMethod parseSplitMethod(Optional<String> splitMethod) throws IllegalValueException {
        requireNonNull(splitMethod);
        return splitMethod.isPresent() ? parseSplitMethod(splitMethod.get())
                : new SplitMethod(SplitMethod.SPLIT_METHOD_EVENLY);
    }

    /**
     * Parses a {@code String unitsList} into a {@code List<Integer>}.
     * Leading and trailing whitespaces between values will be trimmed.
     *
     * @throws IllegalValueException if the given {@code tag} is invalid.
     */
    public static List<Integer> parseUnitsList(String unitsList) throws IllegalValueException {
        requireNonNull(unitsList);
        ArrayList<Integer> trimmedUnitsList = new ArrayList<>();
        if (unitsList.matches("[0-9]+(,( )?[0-9]+)*")) {
            String[] unitsArray = unitsList.split(",");
            for (String unit: unitsArray) {
                trimmedUnitsList.add(Integer.valueOf(unit.trim()));
            }
        } else {
            throw new IllegalValueException("List of units can only take comma-separated integers");
        }
        return trimmedUnitsList;
    }

    /**
     * Parses {@code Collection<String> unitsList} into a {@code List<Integer>}.
     */
    public static List<Integer> parseUnitsList(Optional<String> unitsList) throws IllegalValueException {
        requireNonNull(unitsList);
        return unitsList.isPresent() ? parseUnitsList(unitsList.get()) : Collections.emptyList();
    }

    /**
     * Parses a {@code String percentagesList} into a {@code List<Integer>}.
     * Leading and trailing whitespaces between values will be trimmed.
     *
     * @throws IllegalValueException if the given {@code tag} is invalid.
     */
    public static List<Integer> parsePercentagesList(String percentagesList) throws IllegalValueException {
        requireNonNull(percentagesList);
        ArrayList<Integer> trimmedPercentagesList = new ArrayList<>();
        if (percentagesList.matches("[0-9]+(,( )?[0-9]+)*")) {
            String[] percentagesArray = percentagesList.split(",");
            for (String percentage: percentagesArray) {
                trimmedPercentagesList.add(Integer.valueOf(percentage.trim()));
            }
        } else {
            throw new IllegalValueException("List of percentages can only take comma-separated integers");
        }
        return trimmedPercentagesList;
    }

    /**
     * Parses {@code Collection<String> percentagesList} into a {@code List<Integer>}.
     */
    public static List<Integer> parsePercentagesList(Optional<String> percentagesList) throws IllegalValueException {
        requireNonNull(percentagesList);
        return percentagesList.isPresent() ? parsePercentagesList(percentagesList.get()) : Collections.emptyList();
    }

}
```
###### /java/seedu/address/logic/commands/AddTransactionCommand.java
``` java
            + "The transaction type options are: \"payment\" or \"paydebt\". \n"
            + "The split method options are: \"evenly\", \"units\", or \"percentage\". "
            + "Only add a split method if you are recording a payment (i.e. the transaction type is \"payment\"). \n"
            + "Units and percentages must be entered as a comma-separated list. "
            + "The first number in the list is associated with the payer "
            + "and subsequent numbers are associated with each payee in the order in which they are listed. \n"
            + "Example 1: " + COMMAND_WORD + " "
            + PREFIX_TRANSACTION_TYPE + "payment "
            + PREFIX_PAYER + "John Doe "
            + PREFIX_AMOUNT + "120.00 "
            + PREFIX_DESCRIPTION + "Taxi ride to NUS "
            + PREFIX_PAYEE + "Alex Yeoh "
            + PREFIX_PAYEE + "Bernice Yu "
            + PREFIX_SPLIT_METHOD + "evenly \n"
            + "Example 2: " + COMMAND_WORD + " "
            + PREFIX_TRANSACTION_TYPE + "payment "
            + PREFIX_PAYER + "Alex Yeoh "
            + PREFIX_AMOUNT + "50.00 "
            + PREFIX_DESCRIPTION + "Team dinner "
            + PREFIX_PAYEE + "John Doe "
            + PREFIX_PAYEE + "Bernice Yu "
            + PREFIX_SPLIT_METHOD + "percentage "
            + PREFIX_SPLIT_BY_PERCENTAGE + "40, 40, 20 \n"
            + "Example 3: " + COMMAND_WORD + " "
            + PREFIX_TRANSACTION_TYPE + "payment "
            + PREFIX_PAYER + "Bernice Yu "
            + PREFIX_AMOUNT + "35.00 "
            + PREFIX_DESCRIPTION + "Team apparel items "
            + PREFIX_PAYEE + "Alex Yeoh "
            + PREFIX_PAYEE + "John Doe "
            + PREFIX_SPLIT_METHOD + "units "
            + PREFIX_SPLIT_BY_UNITS + "4, 2, 1 \n"
            + "Example 4: " + COMMAND_WORD + " "
            + PREFIX_TRANSACTION_TYPE + "paydebt "
            + PREFIX_PAYER + "Bernice Yu "
            + PREFIX_AMOUNT + "40.00 "
            + PREFIX_DESCRIPTION + "Amount owed for taxi ride "
            + PREFIX_PAYEE + "John Doe";

    public static final String MESSAGE_INVALID_NUMBER_OF_VALUES = "The number of %1$s values does not match"
            + " the number of persons involved. Remember to include the payer in the count.";
    public static final String MESSAGE_INVALID_PERCENTAGE_VALUES = "The sum of the percentages does not equal 100.";
    public static final String MESSAGE_ONLY_ONE_PAYEE_FOR_PAYDEBT = "Paydebt transactions can only have 1 payee";
    public static final String MESSAGE_PAYEE_NOT_OWED_ANY_DEBT = "Payee is not owed any debt";
    public static final String MESSAGE_PAYEE_IS_BEING_OVERPAID = "Payee is being overpaid. Ensure that the transaction "
            + "amount does not exceed the balance owed.";
    public static final String MESSAGE_TOO_MANY_PREFIXES_FOR_PAYDEBT = "Paydebt transactions do not require "
            + "a split method, a list of units, nor a list of percentages. Ensure that those prefixes are not entered.";
    public static final String MESSAGE_NONEXISTENT_PERSON = "The specified payer or payee(s) do not exist";
    public static final String MESSAGE_PAYEE_IS_PAYER = "A payee cannot be the payer";
    public static final String MESSAGE_MISSING_PERCENTAGES_VALUES = "The percentage value is missing.";
    public static final String MESSAGE_MISSING_UNITS_VALUES = "The unit value is missing.";
```
###### /java/seedu/address/storage/XmlAdaptedPerson.java
``` java
        if (this.balance == null) {
            // Retroactively apply a balance of 0.00 to saved Persons without a balance
            this.balance = "0.00";
        }
        if (!Balance.isValidBalance(this.balance)) {
            throw new IllegalValueException(Balance.MESSAGE_BALANCE_CONSTRAINTS);
        }
        final Balance balance = new Balance(this.balance);
```
###### /java/seedu/address/storage/XmlAdaptedTransaction.java
``` java
    @XmlElement(required = true)
    private List<XmlAdaptedPerson> payees = new ArrayList<>();
    @XmlElement(required = true)
    private String splitMethod;
    @XmlElement
    private String unitsList;
    @XmlElement
    private String percentagesList;
```
###### /java/seedu/address/storage/XmlAdaptedTransaction.java
``` java
        List<XmlAdaptedPerson> payeesToStore = new ArrayList<>();
        payees.asObservableList().forEach(payee -> payeesToStore.add(new XmlAdaptedPerson(payee)));
        this.payees = payeesToStore;
        this.splitMethod = splitMethod;
        if (!unitsList.isEmpty()) {
            this.unitsList = buildIntegerListString(unitsList);
        }
        if (!percentagesList.isEmpty()) {
            this.percentagesList = buildIntegerListString(percentagesList);
        }
    }
```
###### /java/seedu/address/storage/XmlAdaptedTransaction.java
``` java
        List<XmlAdaptedPerson> payeesToStore = new ArrayList<>();
        payees.asObservableList().forEach(payee -> payeesToStore.add(new XmlAdaptedPerson(payee)));
        this.payees = payeesToStore;
        this.splitMethod = splitMethod;
        if (!unitsList.isEmpty()) {
            this.unitsList = buildIntegerListString(unitsList);
        }
        if (!percentagesList.isEmpty()) {
            this.percentagesList = buildIntegerListString(percentagesList);
        }
    }

```
###### /java/seedu/address/storage/XmlAdaptedTransaction.java
``` java
        List<XmlAdaptedPerson> payeesToStore = new ArrayList<>();
        source.getPayees().asObservableList().forEach(payee -> payeesToStore.add(new XmlAdaptedPerson(payee)));
        payees = payeesToStore;
        splitMethod = source.getSplitMethod().toString();
        if (!source.getUnits().isEmpty()) {
            unitsList = buildIntegerListString(source.getUnits());
        }
        if (!source.getPercentages().isEmpty()) {
            percentagesList = buildIntegerListString(source.getPercentages());
        }
    }

```
###### /java/seedu/address/storage/XmlAdaptedTransaction.java
``` java
        if (this.payer == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Payer"));
        }
        validatePersonFields(this.payer.toModelType());
        final Person payer = this.payer.toModelType();

```
###### /java/seedu/address/storage/XmlAdaptedTransaction.java
``` java
        if (this.payees.isEmpty()) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Payees"));
        }

        for (XmlAdaptedPerson payee: this.payees) {
            validatePersonFields(payee.toModelType());
        }
        UniquePersonList convertedPayees = new UniquePersonList();
        for (XmlAdaptedPerson payee: this.payees) {
            convertedPayees.add(payee.toModelType());
        }
        final UniquePersonList payees = convertedPayees;

        if (this.splitMethod == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    SplitMethod.class.getSimpleName()));
        }
        if (!SplitMethod.isValidSplitMethod(this.splitMethod)) {
            throw new IllegalValueException(SplitMethod.MESSAGE_SPLIT_METHOD_CONSTRAINTS);
        }
        final SplitMethod splitMethod = new SplitMethod(this.splitMethod);

        final List<Integer> units = new ArrayList<>();
        if (this.unitsList != null) {
            String[] unitsArray = this.unitsList.split(",");
            for (String unit: unitsArray) {
                units.add(Integer.valueOf(unit.trim()));
            }
        }

        final List<Integer> percentages = new ArrayList<>();
        if (this.percentagesList != null) {
            String[] percentagesArray = this.percentagesList.split(",");
            for (String percentage: percentagesArray) {
                percentages.add(Integer.valueOf(percentage.trim()));
            }
        }

        return new Transaction(transactionType, payer, amount, description, dateTime, payees,
                splitMethod, units, percentages);
    }

    /**
     * Checks each field of the {@code person} for validity
     */
    private void validatePersonFields(Person person) throws IllegalValueException {
        if (person.getName().fullName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(person.getName().fullName)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }

        if (person.getPhone().value == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(person.getPhone().value)) {
            throw new IllegalValueException(Phone.MESSAGE_PHONE_CONSTRAINTS);
        }

        if (person.getEmail().value == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(person.getEmail().value)) {
            throw new IllegalValueException(Email.MESSAGE_EMAIL_CONSTRAINTS);
        }

        for (Tag tag: person.getTags()) {
            if (!Tag.isValidTagName(tag.tagName)) {
                throw new IllegalValueException(Tag.MESSAGE_TAG_CONSTRAINTS);
            }
        }
    }

```
###### /java/seedu/address/storage/XmlAdaptedTransaction.java
``` java
    /**
     * Converts integersList into a comma-separated string for storage
     * @param integersList
     */
    private String buildIntegerListString(List<Integer> integersList) {
        String integersListString = "";
        for (int i = 0; i < integersList.size(); i++) {
            integersListString += String.valueOf(integersList.get(i));
            if (i != integersList.size() - 1) {
                integersListString += ", ";
            }
        }
        return integersListString;
    }

}
```
###### /java/seedu/address/model/transaction/SplitMethod.java
``` java
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Transaction's split method in SmartSplit.
 */
public class SplitMethod {

    /**
     * Split methods that can be used
     */
    public enum Method {
        NOT_APPLICABLE,
        EVENLY,
        UNITS,
        PERCENTAGE
    }

    public static final String MESSAGE_SPLIT_METHOD_CONSTRAINTS =
            "Transaction split method can only be \"evenly\", \"units\", or \"percentage\"";

    public static final String SPLIT_METHOD_NOT_APPLICABLE = "not_applicable";
    public static final String SPLIT_METHOD_EVENLY = "evenly";
    public static final String SPLIT_METHOD_UNITS = "units";
    public static final String SPLIT_METHOD_PERCENTAGE = "percentage";

    public final Method method;

    /**
     * Constructs a {@code splitMethod}.
     *
     * @param splitMethod A valid split method.
     */
    public SplitMethod(String splitMethod) {
        requireNonNull(splitMethod);
        checkArgument(isValidSplitMethod(splitMethod), MESSAGE_SPLIT_METHOD_CONSTRAINTS);
        switch (splitMethod) {
        case SPLIT_METHOD_NOT_APPLICABLE:
            this.method = Method.NOT_APPLICABLE;
            break;
        case SPLIT_METHOD_EVENLY:
            this.method = Method.EVENLY;
            break;
        case SPLIT_METHOD_UNITS:
            this.method = Method.UNITS;
            break;
        case SPLIT_METHOD_PERCENTAGE:
            this.method = Method.PERCENTAGE;
            break;
        default:
            this.method = Method.EVENLY;
        }
    }

    /**
     * Returns true if a given string is a valid split method.
     */
    public static boolean isValidSplitMethod(String test) {
        return test.toLowerCase().matches(SPLIT_METHOD_EVENLY)
            || test.toLowerCase().matches(SPLIT_METHOD_UNITS)
            || test.toLowerCase().matches(SPLIT_METHOD_PERCENTAGE)
            || test.toLowerCase().matches(SPLIT_METHOD_NOT_APPLICABLE);
    }

    @Override
    public String toString() {
        return method.toString().toLowerCase();
    }

    @Override
    public int hashCode() {
        return method.hashCode();
    }

}
```
###### /java/seedu/address/model/transaction/Transaction.java
``` java
package seedu.address.model.transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;

/**
 * Represents a Transaction in SmartSplit.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Transaction {
    private static Integer lastTransactionId = 0;
    private Integer id;
    private Date dateTime;
    private Person payer;
    private final Amount amount;
    private final Description description;
    private UniquePersonList payees;
    private TransactionType transactionType;
    private final SplitMethod splitMethod;
    private ArrayList<Integer> units;
    private ArrayList<Integer> percentages;

    public Transaction(TransactionType transactionType, Person payer, Amount amount, Description description,
                       Date dateTime, UniquePersonList payees, SplitMethod splitMethod, List<Integer> units,
                       List<Integer> percentages) {
        this.transactionType = transactionType;
        this.dateTime = dateTime;
        this.id = lastTransactionId++;
        this.payer = payer;
        this.amount = amount;
        this.description = description;
        this.payees = payees;
        this.splitMethod = splitMethod;
        initializeSplitMethodListValues(units, percentages);
    }

    public Transaction(Transaction transaction) {
        this.transactionType = transaction.getTransactionType();
        this.dateTime = transaction.getDateTime();
        this.id = transaction.getId();
        this.payer = transaction.getPayer();
        this.amount = transaction.getAmount();
        this.description = transaction.getDescription();
        this.payees = transaction.getPayees();
        this.splitMethod = transaction.getSplitMethod();
        this.units = transaction.getUnits();
        this.percentages = transaction.getPercentages();
    }
    /**
     * @param units
     * @param percentages
     * Initializes the split method units list if the split method is by units
     * or initializes the split method percentages list if the split method is by percentage.
     */
    private void initializeSplitMethodListValues(List<Integer> units, List<Integer> percentages) {
        if (this.splitMethod.toString().equals(SplitMethod.SPLIT_METHOD_UNITS)) {
            this.units = new ArrayList<>(units);
            this.percentages = new ArrayList<>();
        } else if (this.splitMethod.toString().equals(SplitMethod.SPLIT_METHOD_PERCENTAGE)) {
            this.units = new ArrayList<>();
            this.percentages = new ArrayList<>(percentages);
        } else {
            this.units = new ArrayList<>();
            this.percentages = new ArrayList<>();
        }
    }
    public void setTransactionType(String transactionType) {
        this.transactionType = new TransactionType(transactionType);
    }
    public Integer getId() {
        return id;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public Person getPayer() {
        return payer;
    }

    public void setPayer(Person payer) {
        this.payer = payer;
    }

    public Amount getAmount() {
        return amount;
    }

    public Description getDescription() {
        return description;
    }

    public UniquePersonList getPayees() {
        return payees;
    }

    public void setPayees(UniquePersonList payees) {
        this.payees = payees;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public SplitMethod getSplitMethod() {
        return splitMethod;
    }

    public ArrayList<Integer> getUnits() {
        return units;
    }

    public ArrayList<Integer> getPercentages() {
        return percentages;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Transaction)) {
            return false;
        }

        Transaction otherTransaction = (Transaction) other;
        return otherTransaction.getId().equals(this.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, transactionType, dateTime, payer, amount,
                description, payees, splitMethod, units, percentages);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Transaction id: ")
                .append(getId())
                .append("\n Transaction Type: ")
                .append(getTransactionType())
                .append("\n Created on: ")
                .append(getDateTime())
                .append("\n Transaction paid by: ")
                .append(getPayer().getName())
                .append("\n Amount: ")
                .append(getAmount().toString())
                .append("\n Description: ")
                .append(getDescription().toString())
                .append("\n Payees: ")
                .append(getPayees().asObservableList().toString())
                .append("\n Split method: ")
                .append(getSplitMethod().toString())
                .append("\n Units list: ")
                .append(getUnits().toString())
                .append("\n Percentages list: ")
                .append(getPercentages().toString());
        return builder.toString();
    }

    /**
     * Tests if a person is implied in this transaction.
     * @param person to check his implication.
     * @return true if the person is the payer or one of the payee;
     * false otherwise.
     */
    public boolean isImplied(Person person) {
        return (payer.equals(person) || payees.contains(person));
    }

}
```
###### /java/seedu/address/model/person/Person.java
``` java
    /**
     * Update the balance of the person
     * @param balanceToAdd
     */
    public void addToBalance(Balance balanceToAdd) {
        Balance newBalance = this.balance.add(balanceToAdd);
        if (newBalance.getDoubleValue() == 0) {
            setBalance(new Balance("0.00"));
        } else {
            setBalance(newBalance);
        }
    }
```
###### /java/seedu/address/model/person/Balance.java
``` java
/**
 * Represents a Person's balance or debt in SmartSplit.
 * Guarantees: immutable; is valid as declared in {@link #isValidBalance(String)}
 */
public class Balance {

    public static final String MESSAGE_BALANCE_CONSTRAINTS =
            "Balance should only contain numeric characters up to 2 digits, and it should not be blank,";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String BALANCE_VALIDATION_REGEX = "^-?\\d*\\.\\d{2}$";

    public final String value;

    /**
     * Constructs a {@code Balance}.
     *
     * @param balance A valid balance.
     */
    public Balance(String balance) {
        requireNonNull(balance);
        checkArgument(isValidBalance(balance), MESSAGE_BALANCE_CONSTRAINTS);
        this.value = balance;
    }

    /**
     * Returns true if a given string is a valid person balance.
     */
    public static boolean isValidBalance(String test) {
        return test.matches(BALANCE_VALIDATION_REGEX);
    }


    public double getDoubleValue() {
        return Double.valueOf(value);
    }

    public Balance getInverse() {
        if (this.value.contains("-")) {
            return new Balance(this.value.substring(1));
        }
        return new Balance("-" + this.value);
    }

    /**
     * Adds the value of two balance
     * @param balance to add.
     * @return Balance with the value of the sum.
     */
    public Balance add(Balance balance) {
        double addition = this.getDoubleValue() + balance.getDoubleValue();
        DecimalFormat formatter = new DecimalFormat("#.00");
        return new Balance(String.valueOf(formatter.format(addition)));
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Balance // instanceof handles nulls
                && this.value.equals(((Balance) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### /java/seedu/address/model/AddressBook.java
``` java
        if (transaction.getTransactionType().toString().toLowerCase()
                .equals(TransactionType.TRANSACTION_TYPE_PAYDEBT)) {
            if (transaction.getPayees().asObservableList().size() > 1) {
                throw new CommandException(MESSAGE_ONLY_ONE_PAYEE_FOR_PAYDEBT);
            }
            Person payeeToFind = transaction.getPayees().asObservableList().get(0);
            if (isNotOwedAnyDebt(transaction, payeeToFind)) {
                throw new CommandException(MESSAGE_PAYEE_NOT_OWED_ANY_DEBT);
            } else if (isBeingOverpaid(transaction, payeeToFind)) {
                throw new CommandException(MESSAGE_PAYEE_IS_BEING_OVERPAID);
            }
        }
```
###### /java/seedu/address/model/AddressBook.java
``` java
            for (int i = 0; i < payees.asObservableList().size(); i++) {
                Person payee = payees.asObservableList().get(i);
                Integer splitMethodValuesListIndex = i + 1;
                updatePayeeBalance(payee, isAddingTransaction, splitMethodValuesListIndex, transaction);
            }
```
###### /java/seedu/address/model/AddressBook.java
``` java
    private boolean isNotOwedAnyDebt(Transaction transaction, Person payeeToFind) {
        return debtsTable.size() != 0
                && (debtsTable.get(transaction.getPayer()).get(payeeToFind) == null
                || debtsTable.get(transaction.getPayer()).get(payeeToFind).getDoubleValue() <= 0);
    }

    private boolean isBeingOverpaid(Transaction transaction, Person payeeToFind) {
        return transaction.getAmount().getDoubleValue()
                > debtsTable.get(transaction.getPayer()).get(payeeToFind).getDoubleValue();
    }

}
```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public Person findPersonByName(Name name) throws PersonNotFoundException {
        Set<Person> matchingPersons = addressBook.getPersonList()
                .stream()
                .filter(person -> person.getName().equals(name))
                .collect(Collectors.toSet());

        if (!matchingPersons.isEmpty()) {
            return matchingPersons.iterator().next();
        } else {
            throw new PersonNotFoundException();
        }
    }

    public UniquePersonList getPayeesList(ArgumentMultimap argMultimap, Model model)
            throws PersonNotFoundException, IllegalValueException {
        UniquePersonList payees = new UniquePersonList();
        List<String> payeeNamesToAdd = argMultimap.getAllValues(PREFIX_PAYEE);

        if (!payeeNamesToAdd.isEmpty()) {
            for (String payeeName: payeeNamesToAdd) {
                payees.add(model.findPersonByName(ParserUtil.parseName(payeeName)));
            }
        }
        return payees;
    }
    public UniquePersonList getPayeesList(UniquePersonList transactionPayees)
            throws PersonNotFoundException {
        UniquePersonList payees = new UniquePersonList();

        for (Person payee: transactionPayees) {
            try {
                payees.add(findPersonByName(payee.getName()));
            } catch (PersonNotFoundException pnfe) {
                throw new PersonNotFoundException();
            } catch (DuplicatePersonException e) {
                e.printStackTrace();
            }
        }

        return payees;
    }
```
###### /java/seedu/address/model/Model.java
``` java
    /** Finds a person by name */
    Person findPersonByName(Name name) throws PersonNotFoundException;

    UniquePersonList getPayeesList(ArgumentMultimap argMultimap, Model model) throws PersonNotFoundException,
            IllegalValueException;

```
###### /java/seedu/address/model/Model.java
``` java
    /** Returns a boolean if a transaction ,that have {@code person} as the payer, has been found. */
    boolean personNotFoundInTransaction(Person person) throws TransactionNotFoundException, PersonFoundException;
```
