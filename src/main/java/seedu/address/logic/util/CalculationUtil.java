package seedu.address.logic.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

import seedu.address.model.person.Balance;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.Transaction;
import seedu.address.model.transaction.TransactionType;

/**
 * Contains utility methods used for calculating balances of Persons.
 */
public class CalculationUtil {

    private static final int NUMBER_OF_DECIMAL_PLACES = 2;

    //@@author ongkc
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
        //@@author steven-jia
        // This is a deleteTransaction command
        if (!isAddingTransaction) {
            return calculateAmountToAddForPayeeForDeleteTransaction(splitMethodValuesListIndex, transaction);
        }
        //@@author ongkc

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

    //@@author steven-jia
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


