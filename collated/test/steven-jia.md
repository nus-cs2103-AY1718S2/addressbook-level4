# steven-jia
###### /java/seedu/address/storage/XmlAdaptedPersonTest.java
``` java
    @Test
    public void toModelType_invalidBalance_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                        INVALID_BALANCE, VALID_TAGS);
        String expectedMessage = Balance.MESSAGE_BALANCE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullBalance_returnsPersonWithZeroBalance() {
        XmlAdaptedPerson person = new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                null, VALID_TAGS);
        Object balance = new Object();
        Balance expectedBalance = new Balance(VALID_ZERO_BALANCE);
        try {
            balance = person.toModelType().getBalance();
        } catch (IllegalValueException e) {
            fail("Could not access balance");
        }
        assertEquals(balance, expectedBalance);
    }
```
###### /java/seedu/address/storage/XmlAdaptedTransactionTest.java
``` java
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

```
###### /java/seedu/address/storage/XmlAdaptedTransactionTest.java
``` java
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
```
###### /java/seedu/address/testutil/TransactionBuilder.java
``` java
    /**
     * Sets the {@code payer} of the {@code Transaction} that we are building.
     */
    public TransactionBuilder withTransactionType(String transactionType) {
        this.transactionType = new TransactionType(transactionType);
        return this;
    }
    /**
     * Sets the {@code SplitMethod} of the {@code Transaction} that we are building.
     */
    public TransactionBuilder withSplitMethod(String splitMethod) {
        this.splitMethod = new SplitMethod(splitMethod);
        return this;
    }

    /**
     * Sets the units {@code List<Integer>} of the {@code Transaction} that we are building.
     */
    public TransactionBuilder withUnits(String unitsList) throws IllegalValueException {
        this.unitsList = ParserUtil.parseUnitsList(unitsList);
        return this;
    }

    /**
     * Sets the percentages {@code List<Integer>} of the {@code Transaction} that we are building.
     */
    public TransactionBuilder withPercentages(String percentagesList) throws IllegalValueException {
        this.percentagesList = ParserUtil.parsePercentagesList(percentagesList);
        return this;
    }

```
###### /java/seedu/address/testutil/PersonBuilder.java
``` java
    /**
     * Sets the {@code Balance} of the {@code Person} that we are building.
     */
    public PersonBuilder withBalance(String balance) {
        this.balance = new Balance(balance);
        return this;
    }
```
