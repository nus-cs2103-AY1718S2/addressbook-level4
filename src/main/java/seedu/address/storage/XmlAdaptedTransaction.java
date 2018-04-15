package seedu.address.storage;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.tag.Tag;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.Description;
import seedu.address.model.transaction.SplitMethod;
import seedu.address.model.transaction.Transaction;
import seedu.address.model.transaction.TransactionType;
//@@author ongkc
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
    //@@author steven-jia
    @XmlElement(required = true)
    private List<XmlAdaptedPerson> payees = new ArrayList<>();
    @XmlElement(required = true)
    private String splitMethod;
    @XmlElement
    private String unitsList;
    @XmlElement
    private String percentagesList;
    //@@author ongkc

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

        //@@author steven-jia
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
    //@@author ongkc
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

        //@@author steven-jia
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

    //@@author ongkc
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

        //@@author steven-jia
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

    //@@author ongkc
    /**
     * Converts this jaxb-friendly adapted transaction's object into the model's Transaction object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted Transaction
     */
    public Transaction toModelType() throws IllegalValueException {

        //@@author steven-jia
        if (this.payer == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Payer"));
        }
        validatePersonFields(this.payer.toModelType());
        final Person payer = this.payer.toModelType();

        //@@author ongkc
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

        //@@author steven-jia
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

    //@@author ongkc
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

    //@@author steven-jia
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
