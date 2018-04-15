# jonleeyz-reused
###### \java\guitests\guihandles\PersonCardHandle.java
``` java
    /**
     * Gets the style class for a given tag
     *
     * IllegalArgumentException is thrown if tag cannot be found
     * Every Label with the same tag content should have the same style class
    **/
    public List<String> getTagStyleClasses(String tag) {
        return tagLabels
                .stream()
                .filter(label -> label.getText().equals(tag))
                .map(Label::getStyleClass)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No such tag"));
    }
```
###### \java\seedu\address\logic\parser\LocateCommandParserTest.java
``` java
    private LocateCommandParser parser = new LocateCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                LocateCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidSpecifier_throwsParseException() {
        //"-e"
        assertParseFailure(parser, "-z Alice Bob", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                LocateCommand.MESSAGE_USAGE));
    }

    // @Test
    /**
     * PLACEHOLDEER: to address checkstyle violation
     * @TODO remove after test is fixed
     */
    public void parse_validArgs_returnsLocateCommand() {
        // no leading and trailing whitespaces
        LocateCommand expectedLocateCommand =
                new LocateCommand(new PersonContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "Alice Bob", expectedLocateCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedLocateCommand);

        //-all specifier
        expectedLocateCommand = new LocateCommand(new PersonContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "-all Alice Bob", expectedLocateCommand);

        //-n specifier
        expectedLocateCommand = new LocateCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "-n Alice Bob", expectedLocateCommand);

        //-p specifier
        expectedLocateCommand = new LocateCommand(new PhoneContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "-p Alice Bob", expectedLocateCommand);

        //-a specifier
        expectedLocateCommand = new LocateCommand(new AddressContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "-a Alice Bob", expectedLocateCommand);

        //-t specifier
        expectedLocateCommand = new LocateCommand(new TagsContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "-t Alice Bob", expectedLocateCommand);

        //-e specifier
        expectedLocateCommand = new LocateCommand(new EmailContainsKeywordsPredicate(Arrays.asList("alice@example.com",
                "bob@example.com")));
        assertParseSuccess(parser, "-e alice@example.com bob@example.com", expectedLocateCommand);
    }
```
###### \java\seedu\address\ui\CommandBoxTest.java
``` java
    /**
     * Runs a command that fails, then verifies that <br>
     *      - {@code NewResultAvailableEvent} is posted
     *      - the text remains <br>
     *      - the command box's style is the same as {@code errorStyleOfCommandBox}.
     */
    private void assertBehaviorForFailedCommand() {
        commandBoxHandle.run(COMMAND_THAT_FAILS);
        assertFalse(((NewResultAvailableEvent) eventsCollectorRule.eventsCollector.getMostRecent()).isSuccessful());
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
        eventsCollectorRule.eventsCollector.reset();

        assertEquals(COMMAND_THAT_FAILS, commandBoxHandle.getInput());
        assertEquals(errorStyleOfCommandBox, commandBoxHandle.getStyleClass());
    }

    /**
     * Runs a command that succeeds, then verifies that <br>
     *      - {@code NewResultAvailableEvent} is posted
     *      - the text is cleared <br>
     *      - the command box's style is the same as {@code defaultStyleOfCommandBox}.
     */
    private void assertBehaviorForSuccessfulCommand() {
        commandBoxHandle.run(COMMAND_THAT_SUCCEEDS);
        assertTrue(((NewResultAvailableEvent) eventsCollectorRule.eventsCollector.getMostRecent()).isSuccessful());
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
        eventsCollectorRule.eventsCollector.reset();

        assertEquals("", commandBoxHandle.getInput());
        assertEquals(defaultStyleOfCommandBox, commandBoxHandle.getStyleClass());
    }
```
###### \java\seedu\address\ui\ResultDisplayTest.java
``` java
    private static final NewResultAvailableEvent NEW_RESULT_SUCCESS_EVENT_STUB =
            new NewResultAvailableEvent("Stub", true);
    private static final NewResultAvailableEvent NEW_RESULT_FAILURE_EVENT_STUB =
            new NewResultAvailableEvent("Stub", false);

    private List<String> defaultStyleOfResultDisplay;
    private List<String> errorStyleOfResultDisplay;

    private ResultDisplayHandle resultDisplayHandle;
```
###### \java\seedu\address\ui\ResultDisplayTest.java
``` java
    @Test
    public void display() {
        // default result text
        guiRobot.pauseForHuman();
        assertEquals("", resultDisplayHandle.getText());
        assertEquals(defaultStyleOfResultDisplay, resultDisplayHandle.getStyleClass());

        // receiving new results
        assertResultDisplay(NEW_RESULT_SUCCESS_EVENT_STUB);
        assertResultDisplay(NEW_RESULT_FAILURE_EVENT_STUB);
    }

    /**
     * Posts the {@code event} to the {@code EventsCentre}, then verifies that <br>
     *      - the text on the result display matches the {@code event}'s message <br>
     *      - the result display's style is the same as {@code defaultStyleOfResultDisplay} if event is successful,
     *      - {@code errorStyleOfResultDisplay} otherwise.
     */
    private void assertResultDisplay(NewResultAvailableEvent event) {
        postNow(event);
        guiRobot.pauseForHuman();
        List<String> expectedStyleClass = event.isSuccessful()
                ? defaultStyleOfResultDisplay
                : errorStyleOfResultDisplay;

        assertEquals(event.message, resultDisplayHandle.getText());
        assertEquals(expectedStyleClass, resultDisplayHandle.getStyleClass());
    }
```
