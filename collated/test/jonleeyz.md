# jonleeyz
###### \java\guitests\guihandles\CommandBoxHandle.java
``` java
    /**
     * Sets text in the command box
     */
    public boolean setInput(String text) {
        click();
        guiRobot.interact(() -> getRootNode().setText(text));
        return !getStyleClass().contains(CommandBox.ERROR_STYLE_CLASS);
    }

    /**
     * Clears all text in the command box.
     * @return true if the command succeeded, false otherwise.
     */
    public boolean clear() {
        click();
        guiRobot.interact(() -> getRootNode().clear());
        return getRootNode().getText().equals("");
    }

    /**
     * Gets caret position in the command box
     */
    public int getCaretPosition() {
        return getRootNode().getCaretPosition();
    }

    /**
     * Sets caret position in the command box
     */
    public void setCaretPosition(int index) {
        click();
        guiRobot.interact(() -> getRootNode().positionCaret(index));
    }
```
###### \java\guitests\guihandles\MainMenuHandle.java
``` java
    /**
     * Clicks on {@code menuItems} in order.
     */
    public void clickOnMenuItemsSequentially(String... menuItems) {
        Arrays.stream(menuItems).forEach(guiRobot::clickOn);
    }

    /**
     * Simulates press of given keyboard shortcut
     */
    public void useAccelerator(KeyCode... combination) {
        guiRobot.push(combination);
    }
```
###### \java\seedu\address\logic\commands\ClearCommandTest.java
``` java
    @Test
    public void verifyGetCommandWordWorksCorrectly() {
        assertEquals(new ClearCommand().getCommandWord(), ClearCommand.COMMAND_WORD);
    }
```
###### \java\seedu\address\logic\commands\HistoryCommandTest.java
``` java
    @Test
    public void verifyGetCommandWordWorksCorrectly() {
        assertEquals(new HistoryCommand().getCommandWord(), HistoryCommand.COMMAND_WORD);
    }
```
###### \java\seedu\address\logic\commands\ListCommandTest.java
``` java
    @Test
    public void verifyGetCommandWordWorksCorrectly() {
        assertEquals(new ListCommand().getCommandWord(), ListCommand.COMMAND_WORD);
    }
```
###### \java\seedu\address\logic\commands\RedoCommandTest.java
``` java
    @Test
    public void verifyGetCommandWordWorksCorrectly() {
        assertEquals(new RedoCommand().getCommandWord(), RedoCommand.COMMAND_WORD);
    }
```
###### \java\seedu\address\logic\commands\UndoCommandTest.java
``` java
    @Test
    public void verifyGetCommandWordWorksCorrectly() {
        assertEquals(new UndoCommand().getCommandWord(), UndoCommand.COMMAND_WORD);
    }
```
###### \java\seedu\address\logic\parser\EditCommandParserTest.java
``` java
        assertParseFailure(parser, "1" + INVALID_MONEY_BORROWED_NOT_DOUBLE,
                MoneyBorrowed.MESSAGE_MONEY_BORROWED_DOUBLE_ONLY); // invalid money borrowed: not a double
        assertParseFailure(parser, "1" + INVALID_MONEY_BORROWED_NEGATIVE,
                MoneyBorrowed.MESSAGE_MONEY_BORROWED_NO_NEGATIVE); // invalid money borrowed: negative
        assertParseFailure(parser, "1" + INVALID_STANDARD_INTEREST_NOT_DOUBLE,
                StandardInterest.MESSAGE_STANDARD_INTEREST_DOUBLE_ONLY); // invalid standard interest: not a double
        assertParseFailure(parser, "1" + INVALID_STANDARD_INTEREST_NEGATIVE,
                StandardInterest.MESSAGE_STANDARD_INTEREST_NO_NEGATIVE); // invalid standard interest: negative
```
###### \java\seedu\address\logic\parser\EditCommandParserTest.java
``` java
        // money borrowed
        userInput = targetIndex.getOneBased() + MONEY_BORROWED_314159265;
        descriptor = new EditPersonDescriptorBuilder().withMoneyBorrowed(VALID_MONEY_BORROWED_314159265).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // owe start date
        userInput = targetIndex.getOneBased() + OWE_START_DATE_070518;
        descriptor = new EditPersonDescriptorBuilder().withOweStartDate(VALID_OWE_START_DATE_070518).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // owe due date
        userInput = targetIndex.getOneBased() + OWE_DUE_DATE_121221;
        descriptor = new EditPersonDescriptorBuilder().withOweDueDate(VALID_OWE_DUE_DATE_121221).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // standard interest
        userInput = targetIndex.getOneBased() + STANDARD_INTEREST_971;
        descriptor = new EditPersonDescriptorBuilder().withStandardInterest(VALID_STANDARD_INTEREST_971).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
```
###### \java\seedu\address\logic\parser\EditCommandParserTest.java
``` java
        String userInput = targetIndex.getOneBased() + PHONE_DESC_AMY + ADDRESS_DESC_AMY + EMAIL_DESC_AMY
                + TAG_DESC_FRIEND + MONEY_BORROWED_314159265
                + OWE_START_DATE_070518 + OWE_DUE_DATE_121221 + STANDARD_INTEREST_971
                + PHONE_DESC_AMY + ADDRESS_DESC_AMY + EMAIL_DESC_AMY + TAG_DESC_FRIEND + MONEY_BORROWED_314159265
                + OWE_START_DATE_070518 + OWE_DUE_DATE_121221 + STANDARD_INTEREST_971
                + PHONE_DESC_BOB + ADDRESS_DESC_BOB + EMAIL_DESC_BOB + TAG_DESC_HUSBAND + MONEY_BORROWED_20481028
                + OWE_START_DATE_121221 + OWE_DUE_DATE_070528 + STANDARD_INTEREST_314;
```
###### \java\seedu\address\logic\parser\EditCommandParserTest.java
``` java
        // other valid values specified
        userInput = targetIndex.getOneBased() + EMAIL_DESC_BOB + INVALID_PHONE_DESC + ADDRESS_DESC_BOB
                + PHONE_DESC_BOB + MONEY_BORROWED_20481028 + OWE_START_DATE_070518 + OWE_DUE_DATE_121221
                + STANDARD_INTEREST_314;
        descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).withMoneyBorrowed(VALID_MONEY_BORROWED_20481028)
                .withOweStartDate(OWE_DUE_DATE_070528).withOweDueDate(OWE_DUE_DATE_121221)
                .withStandardInterest(VALID_STANDARD_INTEREST_314).build();
```
###### \java\seedu\address\logic\parser\ParserUtilTest.java
``` java
    @Test
    public void parseMoneyBorrowed_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseMoneyBorrowed((String) null));
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseMoneyBorrowed((Optional<String>) null));
    }

    @Test
    public void parseMoneyBorrowed_invalidValueNotDouble_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () ->
                ParserUtil.parseMoneyBorrowed(INVALID_MONEY_BORROWED_NOT_DOUBLE));
        Assert.assertThrows(IllegalValueException.class, () ->
                ParserUtil.parseMoneyBorrowed(Optional.of(INVALID_MONEY_BORROWED_NOT_DOUBLE)));
    }

    @Test
    public void parseMoneyBorrowed_invalidValueNegative_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () ->
                ParserUtil.parseMoneyBorrowed(INVALID_MONEY_BORROWED_NEGATIVE));
        Assert.assertThrows(IllegalValueException.class, () ->
                ParserUtil.parseMoneyBorrowed(Optional.of(INVALID_MONEY_BORROWED_NEGATIVE)));
    }

    @Test
    public void parseMoneyBorrowed_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseMoneyBorrowed(Optional.empty()).isPresent());
    }

    @Test
    public void parseMoneyBorrowed_validValueWithoutWhitespace_returnsMoneyBorrowed() throws Exception {
        MoneyBorrowed expectedMoneyBorrowed = new MoneyBorrowed(Double.parseDouble(VALID_MONEY_BORROWED));
        assertEquals(expectedMoneyBorrowed, ParserUtil.parseMoneyBorrowed(VALID_MONEY_BORROWED));
        assertEquals(Optional.of(expectedMoneyBorrowed),
                ParserUtil.parseMoneyBorrowed(Optional.of(VALID_MONEY_BORROWED)));
    }

    @Test
    public void parseMoneyBorrowed_validValueWithWhitespace_returnsTrimmedMoneyBorrowed() throws Exception {
        String moneyBorrowedWithWhitespace = WHITESPACE + VALID_MONEY_BORROWED + WHITESPACE;
        MoneyBorrowed expectedMoneyBorrowed = new MoneyBorrowed(Double.parseDouble(moneyBorrowedWithWhitespace));
        assertEquals(expectedMoneyBorrowed, ParserUtil.parseMoneyBorrowed(VALID_MONEY_BORROWED));
        assertEquals(Optional.of(expectedMoneyBorrowed),
                ParserUtil.parseMoneyBorrowed(Optional.of(VALID_MONEY_BORROWED)));
    }

    @Test
    public void parseStandardInterest_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () ->
                ParserUtil.parseStandardInterest((String) null));
        Assert.assertThrows(NullPointerException.class, () ->
                ParserUtil.parseStandardInterest((Optional<String>) null));
    }

    @Test
    public void parseStandardInterest_invalidValueNotDouble_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () ->
                ParserUtil.parseStandardInterest(INVALID_STANDARD_INTEREST_NOT_DOUBLE));
        Assert.assertThrows(IllegalValueException.class, () ->
                ParserUtil.parseStandardInterest(Optional.of(INVALID_STANDARD_INTEREST_NOT_DOUBLE)));
    }

    @Test
    public void parseStandardInterest_invalidValueNegative_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () ->
                ParserUtil.parseStandardInterest(INVALID_STANDARD_INTEREST_NEGATIVE));
        Assert.assertThrows(IllegalValueException.class, () ->
                ParserUtil.parseStandardInterest(Optional.of(INVALID_STANDARD_INTEREST_NEGATIVE)));
    }

    @Test
    public void parseStandardInterest_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseStandardInterest(Optional.empty()).isPresent());
    }

    @Test
    public void parseStandardInterest_validValueWithoutWhitespace_returnsStandardInterest() throws Exception {
        StandardInterest expectedStandardInterest = new StandardInterest(Double.parseDouble(VALID_STANDARD_INTEREST));
        assertEquals(expectedStandardInterest, ParserUtil.parseStandardInterest(VALID_STANDARD_INTEREST));
        assertEquals(Optional.of(expectedStandardInterest),
                ParserUtil.parseStandardInterest(Optional.of(VALID_STANDARD_INTEREST)));
    }

    @Test
    public void parseStandardInterest_validValueWithWhitespace_returnsTrimmedStandardInterest() throws Exception {
        String standardInterestWithWhitespace = WHITESPACE + VALID_STANDARD_INTEREST + WHITESPACE;
        StandardInterest expectedStandardInterest =
                new StandardInterest(Double.parseDouble(standardInterestWithWhitespace));
        assertEquals(expectedStandardInterest, ParserUtil.parseStandardInterest(VALID_STANDARD_INTEREST));
        assertEquals(Optional.of(expectedStandardInterest),
                ParserUtil.parseStandardInterest(Optional.of(VALID_STANDARD_INTEREST)));
    }

    @Test
    public void parseLateInterest_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseLateInterest((String) null));
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseLateInterest((Optional<String>) null));
    }

    @Test
    public void parseLateInterest_invalidValueNotDouble_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () ->
                ParserUtil.parseLateInterest(INVALID_LATE_INTEREST_NOT_DOUBLE));
        Assert.assertThrows(IllegalValueException.class, () ->
                ParserUtil.parseLateInterest(Optional.of(INVALID_LATE_INTEREST_NOT_DOUBLE)));
    }

    @Test
    public void parseLateInterest_invalidValueNegative_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () ->
                ParserUtil.parseLateInterest(INVALID_LATE_INTEREST_NEGATIVE));
        Assert.assertThrows(IllegalValueException.class, () ->
                ParserUtil.parseLateInterest(Optional.of(INVALID_LATE_INTEREST_NEGATIVE)));
    }

    @Test
    public void parseLateInterest_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseLateInterest(Optional.empty()).isPresent());
    }

    @Test
    public void parseLateInterest_validValueWithoutWhitespace_returnsLateInterest() throws Exception {
        LateInterest expectedLateInterest = new LateInterest(Double.parseDouble(VALID_LATE_INTEREST));
        assertEquals(expectedLateInterest, ParserUtil.parseLateInterest(VALID_LATE_INTEREST));
        assertEquals(Optional.of(expectedLateInterest),
                ParserUtil.parseLateInterest(Optional.of(VALID_LATE_INTEREST)));
    }

    @Test
    public void parseLateInterest_validValueWithWhitespace_returnsTrimmedLateInterest() throws Exception {
        String lateInterestWithWhitespace = WHITESPACE + VALID_LATE_INTEREST + WHITESPACE;
        LateInterest expectedLateInterest =
                new LateInterest(Double.parseDouble(lateInterestWithWhitespace));
        assertEquals(expectedLateInterest, ParserUtil.parseLateInterest(VALID_LATE_INTEREST));
        assertEquals(Optional.of(expectedLateInterest),
                ParserUtil.parseLateInterest(Optional.of(VALID_LATE_INTEREST)));
    }
```
###### \java\seedu\address\model\AddressBookTest.java
``` java
    @Test
    public void testHashcode_symmetric() throws DuplicatePersonException {
        AddressBook addressBookA = new AddressBook();
        AddressBook addressBookB = new AddressBook();
        AddressBook addressBookC = new AddressBook();
        AddressBook addressBookD = new AddressBook();

        Person samplePerson = new Person();
        addressBookC.addPerson(samplePerson);
        addressBookD.addPerson(samplePerson);

        assertEquals(addressBookA.hashCode(), addressBookB.hashCode());
        assertEquals(addressBookC.hashCode(), addressBookD.hashCode());
        assertNotEquals(addressBookA.hashCode(), addressBookC.hashCode());
        assertNotEquals(addressBookA.hashCode(), addressBookD.hashCode());
        assertNotEquals(addressBookB.hashCode(), addressBookC.hashCode());
        assertNotEquals(addressBookB.hashCode(), addressBookD.hashCode());
    }
```
###### \java\seedu\address\model\person\AddressTest.java
``` java
    @Test
    public void testHashcode_symmetric() {
        Address addressA = new Address();
        Address addressB = new Address();
        Address addressC = new Address("NUS");
        Address addressD = new Address("NUS");

        assertEquals(addressA.hashCode(), addressB.hashCode());
        assertEquals(addressC.hashCode(), addressD.hashCode());
        assertNotEquals(addressA.hashCode(), addressC.hashCode());
        assertNotEquals(addressA.hashCode(), addressD.hashCode());
        assertNotEquals(addressB.hashCode(), addressC.hashCode());
        assertNotEquals(addressB.hashCode(), addressD.hashCode());
    }
```
###### \java\seedu\address\model\person\customer\LateInterestTest.java
``` java
public class LateInterestTest {
    @Test
    public void testToString_success() {
        assertEquals("9.71", new LateInterest(9.71).toString());
    }

    @Test
    public void testHashcode_symmetric() {
        LateInterest lateInterestA = new LateInterest();
        LateInterest lateInterestB = new LateInterest();
        LateInterest lateInterestC = new LateInterest(9.71);
        LateInterest lateInterestD = new LateInterest(9.71);

        assertEquals(lateInterestA.hashCode(), lateInterestB.hashCode());
        assertEquals(lateInterestC.hashCode(), lateInterestD.hashCode());
        assertNotEquals(lateInterestA.hashCode(), lateInterestC.hashCode());
        assertNotEquals(lateInterestA.hashCode(), lateInterestD.hashCode());
        assertNotEquals(lateInterestB.hashCode(), lateInterestC.hashCode());
        assertNotEquals(lateInterestB.hashCode(), lateInterestD.hashCode());
    }
}
```
###### \java\seedu\address\model\person\customer\MoneyBorrowedTest.java
``` java
public class MoneyBorrowedTest {
    @Test
    public void testToString_success() {
        assertEquals("9.71", new MoneyBorrowed(9.71).toString());
    }

    @Test
    public void testHashcode_symmetric() {
        MoneyBorrowed moneyBorrowedA = new MoneyBorrowed();
        MoneyBorrowed moneyBorrowedB = new MoneyBorrowed();
        MoneyBorrowed moneyBorrowedC = new MoneyBorrowed(9.71);
        MoneyBorrowed moneyBorrowedD = new MoneyBorrowed(9.71);

        assertEquals(moneyBorrowedA.hashCode(), moneyBorrowedB.hashCode());
        assertEquals(moneyBorrowedC.hashCode(), moneyBorrowedD.hashCode());
        assertNotEquals(moneyBorrowedA.hashCode(), moneyBorrowedC.hashCode());
        assertNotEquals(moneyBorrowedA.hashCode(), moneyBorrowedD.hashCode());
        assertNotEquals(moneyBorrowedB.hashCode(), moneyBorrowedC.hashCode());
        assertNotEquals(moneyBorrowedB.hashCode(), moneyBorrowedD.hashCode());
    }
}
```
###### \java\seedu\address\model\person\customer\StandardInterestTest.java
``` java
public class StandardInterestTest {
    @Test
    public void testToString_success() {
        assertEquals("9.71", new StandardInterest(9.71).toString());
    }

    @Test
    public void testHashcode_symmetric() {
        StandardInterest standardInterestA = new StandardInterest();
        StandardInterest standardInterestB = new StandardInterest();
        StandardInterest standardInterestC = new StandardInterest(9.71);
        StandardInterest standardInterestD = new StandardInterest(9.71);

        assertEquals(standardInterestA.hashCode(), standardInterestB.hashCode());
        assertEquals(standardInterestC.hashCode(), standardInterestD.hashCode());
        assertNotEquals(standardInterestA.hashCode(), standardInterestC.hashCode());
        assertNotEquals(standardInterestA.hashCode(), standardInterestD.hashCode());
        assertNotEquals(standardInterestB.hashCode(), standardInterestC.hashCode());
        assertNotEquals(standardInterestB.hashCode(), standardInterestD.hashCode());
    }
}
```
###### \java\seedu\address\model\person\EmailTest.java
``` java
    @Test
    public void testHashcode_symmetric() {
        Email emailA = new Email();
        Email emailB = new Email();
        Email emailC = new Email("test@email.com");
        Email emailD = new Email("test@email.com");

        assertEquals(emailA.hashCode(), emailB.hashCode());
        assertEquals(emailC.hashCode(), emailD.hashCode());
        assertNotEquals(emailA.hashCode(), emailC.hashCode());
        assertNotEquals(emailA.hashCode(), emailD.hashCode());
        assertNotEquals(emailB.hashCode(), emailC.hashCode());
        assertNotEquals(emailB.hashCode(), emailD.hashCode());
    }
```
###### \java\seedu\address\model\person\NameTest.java
``` java
    @Test
    public void testHashcode_symmetric() {
        Name nameA = new Name("Aron");
        Name nameB = new Name("Aron");
        Name nameC = new Name("Aaron");
        Name nameD = new Name("Aaron");

        assertEquals(nameA.hashCode(), nameB.hashCode());
        assertEquals(nameC.hashCode(), nameD.hashCode());
        assertNotEquals(nameA.hashCode(), nameC.hashCode());
        assertNotEquals(nameA.hashCode(), nameD.hashCode());
        assertNotEquals(nameB.hashCode(), nameC.hashCode());
        assertNotEquals(nameB.hashCode(), nameD.hashCode());
    }
```
###### \java\seedu\address\model\person\PhoneTest.java
``` java
    @Test
    public void testHashcode_symmetric() {
        Phone phoneA = new Phone();
        Phone phoneB = new Phone();
        Phone phoneC = new Phone("999");
        Phone phoneD = new Phone("999");

        assertEquals(phoneA.hashCode(), phoneB.hashCode());
        assertEquals(phoneC.hashCode(), phoneD.hashCode());
        assertNotEquals(phoneA.hashCode(), phoneC.hashCode());
        assertNotEquals(phoneA.hashCode(), phoneD.hashCode());
        assertNotEquals(phoneB.hashCode(), phoneC.hashCode());
        assertNotEquals(phoneB.hashCode(), phoneD.hashCode());
    }
```
###### \java\seedu\address\model\person\UniquePersonListTest.java
``` java
    @Test
    public void testHashcode_symmetric() throws DuplicatePersonException {
        UniquePersonList uniquePersonListA = new UniquePersonList();
        UniquePersonList uniquePersonListB = new UniquePersonList();
        UniquePersonList uniquePersonListC = new UniquePersonList();
        UniquePersonList uniquePersonListD = new UniquePersonList();
        Person samplePerson = new Person();
        uniquePersonListC.add(samplePerson);
        uniquePersonListD.add(samplePerson);

        assertEquals(uniquePersonListA.hashCode(), uniquePersonListB.hashCode());
        assertEquals(uniquePersonListC.hashCode(), uniquePersonListD.hashCode());
        assertNotEquals(uniquePersonListA.hashCode(), uniquePersonListC.hashCode());
        assertNotEquals(uniquePersonListA.hashCode(), uniquePersonListD.hashCode());
        assertNotEquals(uniquePersonListB.hashCode(), uniquePersonListC.hashCode());
        assertNotEquals(uniquePersonListB.hashCode(), uniquePersonListD.hashCode());
    }
```
###### \java\seedu\address\ui\CommandBoxTest.java
``` java
    @Test
    public void handleKeyPress_shiftTab_whenNoPrefixesPresent() {
        commandBoxHandle.setInput(COMMAND_THAT_FAILS);
        int expectedCaretPosition = COMMAND_THAT_FAILS.length();
        commandBoxHandle.setCaretPosition(expectedCaretPosition);

        assertShiftTabPressBehaviour(expectedCaretPosition, COMMAND_THAT_FAILS);
    }

    @Test
    public void handleKeyPress_shiftTab_whenPrefixesPresent() {
        // initialisation
        commandBoxHandle.setInput(EXAMPLE_COMMAND_TEMPLATE);
        int expectedCaretPosition = EXAMPLE_COMMAND_TEMPLATE.length();
        commandBoxHandle.setCaretPosition(expectedCaretPosition);

        // test skipping past third prefix's argument and one trailing space
        expectedCaretPosition =
                assertShiftTabPressBehaviour(expectedCaretPosition, EXAMPLE_PHONE + " ");

        // test skipping past second prefix's argument and third prefix, with no trailing spaces
        expectedCaretPosition =
                assertShiftTabPressBehaviour(expectedCaretPosition, EXAMPLE_NAME + " " + PREFIX_PHONE);

        // test skipping past second prefix, with one trailing space following second prefix's argument
        expectedCaretPosition =
                assertShiftTabPressBehaviour(expectedCaretPosition, " " + PREFIX_NAME + " ");

        // test skipping past command word and first prefix, to before the entire CommandBox input
        assertShiftTabPressBehaviour(expectedCaretPosition, AddCommand.COMMAND_WORD + " " + PREFIX_TYPE + " ");
    }

    @Test
    public void handleKeyPress_tab_whenNoPrefixesPresent() {
        commandBoxHandle.setInput(COMMAND_THAT_FAILS);
        int expectedCaretPosition = 0;
        commandBoxHandle.setCaretPosition(expectedCaretPosition);

        assertTabPressBehaviour(expectedCaretPosition, COMMAND_THAT_FAILS);
    }

    @Test
    public void handleKeyPress_tab_whenPrefixesPresent() {
        // initialisation
        commandBoxHandle.setInput(EXAMPLE_COMMAND_TEMPLATE);
        int expectedCaretPosition = 0;
        commandBoxHandle.setCaretPosition(expectedCaretPosition);

        // test skipping past command word and first prefix
        expectedCaretPosition =
                assertTabPressBehaviour(expectedCaretPosition, AddCommand.COMMAND_WORD + " " + PREFIX_TYPE + " ");

        // test skipping past second prefix, with one trailing space following second prefix
        expectedCaretPosition =
                assertTabPressBehaviour(expectedCaretPosition, " " + PREFIX_NAME + " ");

        // test skipping past second prefix's argument and third prefix, without no trailing spaces
        expectedCaretPosition =
                assertTabPressBehaviour(expectedCaretPosition, EXAMPLE_NAME + " " + PREFIX_PHONE);

        // test skipping past third prefix's argument and one trailing space, to after the entire CommandBox input
        assertTabPressBehaviour(expectedCaretPosition, EXAMPLE_PHONE + " ");
    }

    @Test
    public void handleKeyPress_shiftBackspace_whenNoPrefixesPresent() {
        commandBoxHandle.setInput(COMMAND_THAT_FAILS);
        int initialCaretPosition = COMMAND_THAT_FAILS.length();
        commandBoxHandle.setCaretPosition(initialCaretPosition);

        assertShiftBackspacePressBehaviour(COMMAND_THAT_FAILS);
    }

    @Test
    public void handleKeyPress_shiftBackspace_whenPrefixesPresent() {
        // initialisation
        commandBoxHandle.setInput(EXAMPLE_COMMAND_TEMPLATE);
        int initialCaretPosition = EXAMPLE_COMMAND_TEMPLATE.length();
        commandBoxHandle.setCaretPosition(initialCaretPosition);

        // test deleting third prefix's argument and one trailing space
        assertShiftBackspacePressBehaviour(EXAMPLE_PHONE + " ");

        // test deleting second prefix's argument and third prefix, with no trailing spaces
        assertShiftBackspacePressBehaviour(EXAMPLE_NAME + " " + PREFIX_PHONE);

        // test deleting second prefix, with one trailing space following second prefix's argument
        assertShiftBackspacePressBehaviour(" " + PREFIX_NAME + " ");

        // test deleting command word and first prefix
        assertShiftBackspacePressBehaviour(AddCommand.COMMAND_WORD + " " + PREFIX_TYPE + " ");
    }

    /**
     * Presses the keyboard shortcut Shift + Tab, then ensures <br>
     *      - the command box's caret position is expected.
     */
    private int assertShiftTabPressBehaviour(int lastCaretPosition, String stringLiteralSkipped) {
        guiRobot.push(KeyCode.SHIFT, KeyCode.TAB);
        int expectedCaretPosition = lastCaretPosition - stringLiteralSkipped.length();
        assertEquals(expectedCaretPosition, commandBoxHandle.getCaretPosition());
        return expectedCaretPosition;
    }

    /**
     * Presses the keyboard shortcut Tab, then ensures <br>
     *      - the command box's caret position is expected.
     */
    private int assertTabPressBehaviour(int lastCaretPosition, String stringLiteralSkipped) {
        guiRobot.push(KeyCode.TAB);
        int expectedCaretPosition = lastCaretPosition + stringLiteralSkipped.length();
        assertEquals(expectedCaretPosition, commandBoxHandle.getCaretPosition());
        return expectedCaretPosition;
    }

    /**
     * Presses the keyboard shortcut Shift + Backspace, then ensures <br>
     *      - the command box's input is updated as expected.
     */
    private void assertShiftBackspacePressBehaviour(String stringLiteralDeleted) {
        String inputBeforePush = commandBoxHandle.getInput();
        guiRobot.push(KeyCode.SHIFT, KeyCode.BACK_SPACE);
        String inputAfterPush =
                inputBeforePush.substring(0, inputBeforePush.length() - stringLiteralDeleted.length());
        assertEquals(inputAfterPush, commandBoxHandle.getInput());
    }
```
###### \java\systemtests\AddCommandSystemTest.java
``` java
    @Test
    public void focusOnCommandBox_populateAddCommandTemplate_usingAccelerator() {
        getCommandBox().click();
        populateAddCommandUsingAccelerator();
        assertPopulationSuccess();
    }

    @Test
    public void focusOnResultDisplay_populateAddCommandTemplate_usingAccelerator() {
        getResultDisplay().click();
        populateAddCommandUsingAccelerator();
        assertPopulationSuccess();
    }

    @Test
    public void focusOnPersonListPanel_populateAddCommandTemplate_usingAccelerator() {
        getPersonListPanel().click();
        populateAddCommandUsingAccelerator();
        assertPopulationSuccess();
    }

    @Test
    public void focusOnBrowserPanel_populateAddCommandTemplate_usingAccelerator() {
        getBrowserPanel().click();
        populateAddCommandUsingAccelerator();
        assertPopulationSuccess();
    }

    @Test
    public void populateAddCommandTemplate_usingMenuButton() {
        populateAddCommandUsingMenu();
        assertPopulationSuccess();
    }
```
###### \java\systemtests\AddCommandSystemTest.java
``` java
    /**
     * Asserts that population of the {@code CommandBox} with the AddCommand
     * template was successful.
     */
    private void assertPopulationSuccess() {
        assertEquals(AddCommand.COMMAND_TEMPLATE, getCommandBox().getInput());
        assertEquals(AddCommand.MESSAGE_USAGE, getResultDisplay().getText());
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof PopulatePrefixesRequestEvent);
        // assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
        guiRobot.pauseForHuman();

        executeCommand("invalid command");
        assertTrue(getCommandBox().clear());
        assertEquals(MESSAGE_UNKNOWN_COMMAND, getResultDisplay().getText());
        guiRobot.pauseForHuman();
    }

    /**
     * Populates the {@code CommandBox} with the AddCommand template
     * using the associated accelerator in {@code MainWindow}.
     */
    private void populateAddCommandUsingAccelerator() {
        populateUsingAccelerator(KeyCode.CONTROL, KeyCode.I);
    }

    /**
     * Populates the {@code CommandBox} with the AddCommand template
     * using the menu bar in {@code MainWindow}.
     */
    private void populateAddCommandUsingMenu() {
        populateUsingMenu("Actions", "Add a Person...");
    }
```
###### \java\systemtests\AddressBookSystemTest.java
``` java
    /**
     * Executes {@code command} associated with the given keyboard shortcut.
     * Method returns after UI components have been updated.
     */
    protected void executeUsingAccelerator(KeyCode... combination) {
        rememberStates();
        // Injects a fixed clock before executing a command so that the time stamp shown in the status bar
        // after each command is predictable and also different from the previous command.
        clockRule.setInjectedClockToCurrentTime();

        mainWindowHandle.getMainMenu().useAccelerator(combination);

        waitUntilBrowserLoaded(getBrowserPanel());
    }

    /**
     * Populates the appropriate {@code command} template in the application's
     * {@code CommandBox} given a keyboard shortcut.
     */
    protected void populateUsingAccelerator(KeyCode... combination) {
        mainWindowHandle.getMainMenu().useAccelerator(combination);
    }

    /**
     * Executes {@code command} associated with the given menu item.
     * Method returns after UI components have been updated.
     */
    protected void executeUsingMenuItem(String... menuItems) {
        rememberStates();
        // Injects a fixed clock before executing a command so that the time stamp shown in the status bar
        // after each command is predictable and also different from the previous command.
        clockRule.setInjectedClockToCurrentTime();

        mainWindowHandle.getMainMenu().clickOnMenuItemsSequentially(menuItems);

        waitUntilBrowserLoaded(getBrowserPanel());
    }

    /**
     * Populates the appropriate {@code command} template in the application's
     * {@code CommandBox} given the appropriate menu item.
     */
    protected void populateUsingMenu(String... menuItems) {
        mainWindowHandle.getMainMenu().clickOnMenuItemsSequentially(menuItems);
    }
```
###### \java\systemtests\ClearCommandSystemTest.java
``` java
        /* Case: simulate press of Ctrl + Shift + C -> cleared */
        executeCommand(UndoCommand.COMMAND_WORD); // undoes last clear command: address book still will be empty
        executeCommand(UndoCommand.COMMAND_WORD); // restores the original address book
        assertKeyboardShortcutSuccess(ClearCommand.MESSAGE_SUCCESS,
                new ModelManager(),
                KeyCode.CONTROL,
                KeyCode.SHIFT,
                KeyCode.C);
        assertSelectedCardUnchanged();

        /* Case: simulate click of "Clear the Database" menu item -> cleared */
        executeCommand(UndoCommand.COMMAND_WORD); // restores the original address book
        assertMenuItemSuccess(ClearCommand.MESSAGE_SUCCESS,
                new ModelManager(),
                "Edit",
                "Clear the Database");
        assertSelectedCardUnchanged();
```
###### \java\systemtests\ClearCommandSystemTest.java
``` java
    /**
     * Performs the same verification as {@code assertCommandSuccess(String, String, Model)} except that the command
     * is executed using its keyboard shortcut.
     * @see ClearCommandSystemTest#assertCommandSuccess(String, String, Model)
     */
    private void assertKeyboardShortcutSuccess(String expectedResultMessage,
                                               Model expectedModel,
                                               KeyCode... combination) {
        executeUsingAccelerator(combination);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxAndResultDisplayShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, String, Model)} except that the command
     * is executed using its menu item.
     * @see ClearCommandSystemTest#assertCommandSuccess(String, String, Model)
     */
    private void assertMenuItemSuccess(String expectedResultMessage,
                                               Model expectedModel,
                                               String... menuItems) {
        executeUsingMenuItem(menuItems);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxAndResultDisplayShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }
```
###### \java\systemtests\DeleteCommandSystemTest.java
``` java
    @Test
    public void focusOnCommandBox_populateDeleteCommandTemplate_usingAccelerator() {
        getCommandBox().click();
        populateDeleteCommandUsingAccelerator();
        assertPopulationSuccess();
    }

    @Test
    public void focusOnResultDisplay_populateDeleteCommandTemplate_usingAccelerator() {
        getResultDisplay().click();
        populateDeleteCommandUsingAccelerator();
        assertPopulationSuccess();
    }

    @Test
    public void focusOnPersonListPanel_populateDeleteCommandTemplate_usingAccelerator() {
        getPersonListPanel().click();
        populateDeleteCommandUsingAccelerator();
        assertPopulationSuccess();
    }

    @Test
    public void focusOnBrowserPanel_populateDeleteCommandTemplate_usingAccelerator() {
        getBrowserPanel().click();
        populateDeleteCommandUsingAccelerator();
        assertPopulationSuccess();
    }

    @Test
    public void populateDeleteCommandTemplate_usingMenuButton() {
        populateDeleteCommandUsingMenu();
        assertPopulationSuccess();
    }
```
###### \java\systemtests\DeleteCommandSystemTest.java
``` java
    /**
     * Asserts that population of the {@code CommandBox} with the AddCommand
     * template was successful.
     */
    private void assertPopulationSuccess() {
        assertEquals(DeleteCommand.COMMAND_TEMPLATE, getCommandBox().getInput());
        assertEquals(DeleteCommand.MESSAGE_USAGE, getResultDisplay().getText());
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof PopulatePrefixesRequestEvent);
        guiRobot.pauseForHuman();

        executeCommand("invalid command");
        assertTrue(getCommandBox().clear());
        assertEquals(MESSAGE_UNKNOWN_COMMAND, getResultDisplay().getText());
        guiRobot.pauseForHuman();
    }

    /**
     * Populates the {@code CommandBox} with the DeleteCommand template
     * using the associated accelerator in {@code MainWindow}.
     */
    private void populateDeleteCommandUsingAccelerator() {
        populateUsingAccelerator(KeyCode.CONTROL, KeyCode.D);
    }

    /**
     * Populates the {@code CommandBox} with the DeleteCommand template
     * using the menu bar in {@code MainWindow}.
     */
    private void populateDeleteCommandUsingMenu() {
        populateUsingMenu("Actions", "Delete a Person...");
    }
```
###### \java\systemtests\EditCommandSystemTest.java
``` java
    @Test
    public void focusOnCommandBox_populateEditCommandTemplate_usingAccelerator() {
        getCommandBox().click();
        populateEditCommandUsingAccelerator();
        assertPopulationSuccess();
    }

    @Test
    public void focusOnResultDisplay_populateEditCommandTemplate_usingAccelerator() {
        getResultDisplay().click();
        populateEditCommandUsingAccelerator();
        assertPopulationSuccess();
    }

    @Test
    public void focusOnPersonListPanel_populateEditCommandTemplate_usingAccelerator() {
        getPersonListPanel().click();
        populateEditCommandUsingAccelerator();
        assertPopulationSuccess();
    }

    @Test
    public void focusOnBrowserPanel_populateEditCommandTemplate_usingAccelerator() {
        getBrowserPanel().click();
        populateEditCommandUsingAccelerator();
        assertPopulationSuccess();
    }

    @Test
    public void populateEditCommandTemplate_usingMenuButton() {
        populateEditCommandUsingMenu();
        assertPopulationSuccess();
    }
```
###### \java\systemtests\EditCommandSystemTest.java
``` java
    /**
     * Asserts that population of the {@code CommandBox} with the AddCommand
     * template was successful.
     */
    private void assertPopulationSuccess() {
        assertEquals(EditCommand.COMMAND_TEMPLATE, getCommandBox().getInput());
        assertEquals(EditCommand.MESSAGE_USAGE, getResultDisplay().getText());
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof PopulatePrefixesRequestEvent);
        guiRobot.pauseForHuman();

        executeCommand("invalid command");
        assertTrue(getCommandBox().clear());
        assertEquals(MESSAGE_UNKNOWN_COMMAND, getResultDisplay().getText());
        guiRobot.pauseForHuman();
    }

    /**
     * Populates the {@code CommandBox} with the EditCommand template
     * using the associated accelerator in {@code MainWindow}.
     */
    private void populateEditCommandUsingAccelerator() {
        populateUsingAccelerator(KeyCode.CONTROL, KeyCode.E);
    }

    /**
     * Populates the {@code CommandBox} with the EditCommand template
     * using the menu bar in {@code MainWindow}.
     */
    private void populateEditCommandUsingMenu() {
        populateUsingMenu("Actions", "Edit a Person...");
    }
```
###### \java\systemtests\FindCommandSystemTest.java
``` java
    @Test
    public void focusOnCommandBox_populateFindCommandTemplate_usingAccelerator() {
        //use accelerator
        getCommandBox().click();
        populateFindCommandUsingAccelerator();
        assertPopulationSuccess();
    }

    @Test
    public void focusOnResultDisplay_populateFindCommandTemplate_usingAccelerator() {
        getResultDisplay().click();
        populateFindCommandUsingAccelerator();
        assertPopulationSuccess();
    }

    @Test
    public void focusOnPersonListPanel_populateFindCommandTemplate_usingAccelerator() {
        getPersonListPanel().click();
        populateFindCommandUsingAccelerator();
        assertPopulationSuccess();
    }

    @Test
    public void focusOnBrowserPanel_populateFindCommandTemplate_usingAccelerator() {
        getBrowserPanel().click();
        populateFindCommandUsingAccelerator();
        assertPopulationSuccess();
    }

    @Test
    public void populateFindCommandTemplate_usingMenuButton() {
        populateFindCommandUsingMenu();
        assertPopulationSuccess();
    }
```
###### \java\systemtests\FindCommandSystemTest.java
``` java

    /**
     * Asserts that population of the {@code CommandBox} with the AddCommand
     * template was successful.
     */
    private void assertPopulationSuccess() {
        assertEquals(FindCommand.COMMAND_TEMPLATE, getCommandBox().getInput());
        assertEquals(FindCommand.MESSAGE_USAGE, getResultDisplay().getText());
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof PopulatePrefixesRequestEvent);
        guiRobot.pauseForHuman();

        executeCommand("invalid command");
        assertTrue(getCommandBox().clear());
        assertEquals(MESSAGE_UNKNOWN_COMMAND, getResultDisplay().getText());
        guiRobot.pauseForHuman();
    }

    /**
     * Populates the {@code CommandBox} with the FindCommand template
     * using the associated accelerator in {@code MainWindow}.
     */
    private void populateFindCommandUsingAccelerator() {
        populateUsingAccelerator(KeyCode.CONTROL, KeyCode.F);
    }

    /**
     * Populates the {@code CommandBox} with the FindCommand template
     * using the menu bar in {@code MainWindow}.
     */
    private void populateFindCommandUsingMenu() {
        populateUsingMenu("View", "Find...");
    }
```
###### \java\systemtests\HelpCommandSystemTest.java
``` java
    /**
     * Executes the HelpCommand using its accelerator in {@code MainMenu}
     */
    private void executeHelpCommandUsingAccelerator() {
        executeUsingAccelerator(KeyCode.F12);
    }

    /**
     * Executes the HelpCommand using its menu bar item in {@code MainMenu}.
     */
    private void executeHelpCommandUsingMenu() {
        executeUsingMenuItem("Help", "F12");
    }
```
###### \java\systemtests\SelectCommandSystemTest.java
``` java
    @Test
    public void focusOnCommandBox_populateSelectCommandTemplate_usingAccelerator() {
        getCommandBox().click();
        populateSelectCommandUsingAccelerator();
        assertPopulationSuccess();
    }

    @Test
    public void focusOnResultDisplay_populateSelectCommandTemplate_usingAccelerator() {
        getResultDisplay().click();
        populateSelectCommandUsingAccelerator();
        assertPopulationSuccess();
    }

    @Test
    public void focusOnPersonListPanel_populateSelectCommandTemplate_usingAccelerator() {
        getPersonListPanel().click();
        populateSelectCommandUsingAccelerator();
        assertPopulationSuccess();
    }

    @Test
    public void focusOnBrowserPanel_populateSelectCommandTemplate_usingAccelerator() {
        getBrowserPanel().click();
        populateSelectCommandUsingAccelerator();
        assertPopulationSuccess();
    }

    @Test
    public void populateSelectCommandTemplate_usingMenuButton() {
        populateSelectCommandUsingMenu();
        assertPopulationSuccess();
    }
```
###### \java\systemtests\SelectCommandSystemTest.java
``` java
    /**
     * Asserts that population of the {@code CommandBox} with the AddCommand
     * template was successful.
     */
    private void assertPopulationSuccess() {
        assertEquals(SelectCommand.COMMAND_TEMPLATE, getCommandBox().getInput());
        assertEquals(SelectCommand.MESSAGE_USAGE, getResultDisplay().getText());
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof PopulatePrefixesRequestEvent);
        guiRobot.pauseForHuman();

        executeCommand("invalid command");
        assertTrue(getCommandBox().clear());
        assertEquals(MESSAGE_UNKNOWN_COMMAND, getResultDisplay().getText());
        guiRobot.pauseForHuman();
    }

    /**
     * Populates the {@code CommandBox} with the SelectCommand template
     * using the associated accelerator in {@code MainWindow}.
     */
    private void populateSelectCommandUsingAccelerator() {
        populateUsingAccelerator(KeyCode.CONTROL, KeyCode.S);
    }

    /**
     * Populates the {@code CommandBox} with the SelectCommand template
     * using the menu bar in {@code MainWindow}.
     */
    private void populateSelectCommandUsingMenu() {
        populateUsingMenu("Actions", "Select a Person...");
    }
```
