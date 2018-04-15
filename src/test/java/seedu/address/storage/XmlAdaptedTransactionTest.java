package seedu.address.storage;

import static seedu.address.storage.XmlAdaptedTransaction.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.storage.XmlAdaptedTransaction.MISSING_FIELD_MESSAGE_FORMAT_DATE;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.Description;
import seedu.address.model.transaction.SplitMethod;
import seedu.address.model.transaction.TransactionType;
import seedu.address.testutil.Assert;
import seedu.address.testutil.TypicalTransactions;
//@@author ongkc
public class XmlAdaptedTransactionTest extends XmlAdaptedPersonTest {

    private static final String INVALID_AMOUNT = " ";
    private static final String INVALID_DESCRIPTION = " ";
    private static final String INVALID_TRANSACTION_TYPE = " ";
    private static final String INVALID_SPLIT_METHOD = " ";

    private static final Person VALID_PAYER = TypicalTransactions.getTypicalTransactions().get(0).getPayer();
    private static final String VALID_AMOUNT = TypicalTransactions.getTypicalTransactions().get(0)
            .getAmount().toString();
    private static final String VALID_DESCRIPTION = TypicalTransactions.getTypicalTransactions().get(0)
            .getDescription().toString();
    private static final Person VALID_PAYEE = TypicalTransactions.getTypicalTransactions().get(0)
            .getPayees().iterator().next();
    private static UniquePersonList validPayees = new UniquePersonList();
    private static final String VALID_TRANSACTION_TYPE = TypicalTransactions.getTypicalTransactions().get(0)
            .getTransactionType().toString();
    private static final String VALID_SPLIT_METHOD_EVENLY = TypicalTransactions.getTypicalTransactions().get(0)
            .getSplitMethod().toString();
    private static final List<Integer> VALID_SPLIT_BY_UNITS_LIST = TypicalTransactions.getTypicalTransactions().get(1)
            .getUnits();
    private static final List<Integer> VALID_SPLIT_BY_PERCENTAGES_LIST = TypicalTransactions
            .getTypicalTransactions().get(2)
            .getPercentages();
    private static final List<Integer> VALID_EMPTY_LIST = new ArrayList<>();

    @Before
    public void setUp() {
        try {
            validPayees.add(VALID_PAYEE);
        } catch (DuplicatePersonException dpe) {
            dpe.printStackTrace();
        }
    }

    //@@author steven-jia
    @Test(expected = NullPointerException.class)
    public void toModelType_nullPayer_throwsNullPointerException() {
        XmlAdaptedTransaction transaction = new XmlAdaptedTransaction(VALID_TRANSACTION_TYPE, null,
                VALID_AMOUNT, VALID_DESCRIPTION, validPayees,
                VALID_SPLIT_METHOD_EVENLY, VALID_EMPTY_LIST, VALID_EMPTY_LIST);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "Payer");
        Assert.assertThrows(IllegalValueException.class, expectedMessage, transaction::toModelType);

    }

    @Test
    public void toModelType_invalidAmount_throwsIllegalValueException() {
        XmlAdaptedTransaction transaction =
                new XmlAdaptedTransaction(VALID_TRANSACTION_TYPE, VALID_PAYER, INVALID_AMOUNT,
                        VALID_DESCRIPTION, validPayees,
                        VALID_SPLIT_METHOD_EVENLY, VALID_EMPTY_LIST, VALID_EMPTY_LIST);
        String expectedMessage = Amount.MESSAGE_AMOUNT_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, transaction::toModelType);
    }

    @Test
    public void toModelType_nullAmount_throwsIllegalValueException() {
        XmlAdaptedTransaction transaction = new XmlAdaptedTransaction(VALID_TRANSACTION_TYPE,
                VALID_PAYER, null, VALID_DESCRIPTION, validPayees,
                VALID_SPLIT_METHOD_EVENLY, VALID_EMPTY_LIST, VALID_EMPTY_LIST);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Amount.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, transaction::toModelType);
    }
    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        XmlAdaptedTransaction transaction = new XmlAdaptedTransaction(VALID_TRANSACTION_TYPE,
                VALID_PAYER, VALID_AMOUNT, VALID_DESCRIPTION, validPayees, null,
                VALID_SPLIT_METHOD_EVENLY, VALID_EMPTY_LIST, VALID_EMPTY_LIST);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT_DATE, Date.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, transaction::toModelType);
    }

    @Test
    public void toModelType_invalidDescription_throwsIllegalValueException() {
        XmlAdaptedTransaction transaction =
                new XmlAdaptedTransaction(VALID_TRANSACTION_TYPE, VALID_PAYER, VALID_AMOUNT, INVALID_DESCRIPTION,
                        validPayees, VALID_SPLIT_METHOD_EVENLY, VALID_EMPTY_LIST, VALID_EMPTY_LIST);
        String expectedMessage = Description.MESSAGE_DESCRIPTION_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, transaction::toModelType);
    }

    @Test
    public void toModelType_nullDescription_throwsIllegalValueException() {
        XmlAdaptedTransaction transaction = new XmlAdaptedTransaction(VALID_TRANSACTION_TYPE, VALID_PAYER, VALID_AMOUNT,
                null, validPayees,
                VALID_SPLIT_METHOD_EVENLY, VALID_EMPTY_LIST, VALID_EMPTY_LIST);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, transaction::toModelType);
    }

    //@@author steven-jia
    @Test(expected = NullPointerException.class)
    public void toModelType_nullPayee_throwsNullPointerException() {
        XmlAdaptedTransaction transaction = new XmlAdaptedTransaction(VALID_TRANSACTION_TYPE, VALID_PAYER, VALID_AMOUNT,
                VALID_DESCRIPTION, null,
                VALID_SPLIT_METHOD_EVENLY, VALID_EMPTY_LIST, VALID_EMPTY_LIST);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "Payees");
        Assert.assertThrows(IllegalValueException.class, expectedMessage, transaction::toModelType);
    }

    @Test
    public void toModelType_invalidTransactionType_throwsIllegalValueException() {
        XmlAdaptedTransaction transaction =
                new XmlAdaptedTransaction(INVALID_TRANSACTION_TYPE, VALID_PAYER, VALID_AMOUNT, VALID_DESCRIPTION,
                        validPayees, VALID_SPLIT_METHOD_EVENLY, VALID_EMPTY_LIST, VALID_EMPTY_LIST);
        String expectedMessage = TransactionType.MESSAGE_TRANSACTION_TYPE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, transaction::toModelType);
    }

    @Test
    public void toModelType_nullTransactionType_throwsIllegalValueException() {
        XmlAdaptedTransaction transaction = new XmlAdaptedTransaction(null, VALID_PAYER, VALID_AMOUNT,
                VALID_DESCRIPTION, validPayees, VALID_SPLIT_METHOD_EVENLY, VALID_EMPTY_LIST, VALID_EMPTY_LIST);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, TransactionType.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, transaction::toModelType);
    }

    @Test
    public void toModelType_invalidSplitMethod_throwsIllegalValueException() {
        XmlAdaptedTransaction transaction =
                new XmlAdaptedTransaction(VALID_TRANSACTION_TYPE, VALID_PAYER, VALID_AMOUNT, VALID_DESCRIPTION,
                        validPayees, INVALID_SPLIT_METHOD, VALID_EMPTY_LIST, VALID_EMPTY_LIST);
        String expectedMessage = SplitMethod.MESSAGE_SPLIT_METHOD_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, transaction::toModelType);
    }

    @Test
    public void toModelType_nullSplitMethod_throwsIllegalValueException() {
        XmlAdaptedTransaction transaction = new XmlAdaptedTransaction(VALID_TRANSACTION_TYPE, VALID_PAYER, VALID_AMOUNT,
                VALID_DESCRIPTION, validPayees,
                null, VALID_EMPTY_LIST, VALID_EMPTY_LIST);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, SplitMethod.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, transaction::toModelType);
    }


}
