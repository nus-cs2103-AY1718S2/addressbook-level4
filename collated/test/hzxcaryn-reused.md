# hzxcaryn-reused
###### /java/seedu/ptman/ui/CommandBoxTest.java
``` java
    //Reused from original code base with modifications to test for error style
    /**
     * Runs a command that fails, then verifies that <br>
     *      - the text remains <br>
     *      - the command box's style is the same as {@code errorStyleOfCommandBox}.
     */
    private void assertBehaviorForFailedCommand() {
        commandBoxHandle.run(COMMAND_THAT_FAILS);

        assertTrue(((NewResultAvailableEvent) eventsCollectorRule.eventsCollector.getMostRecent()).isError);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
        eventsCollectorRule.eventsCollector.reset();

        assertEquals(COMMAND_THAT_FAILS, commandBoxHandle.getInput());
        assertEquals(errorStyleOfCommandBox, commandBoxHandleOutput.getStyleClass());
    }

    /**
     * Runs a command that succeeds, then verifies that <br>
     *      - the text is cleared <br>
     *      - the command box's style is the same as {@code defaultStyleOfCommandBox}.
     */
    private void assertBehaviorForSuccessfulCommand() {
        commandBoxHandle.run(COMMAND_THAT_SUCCEEDS);

        assertFalse(((NewResultAvailableEvent) eventsCollectorRule.eventsCollector.getMostRecent()).isError);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
        eventsCollectorRule.eventsCollector.reset();

        assertEquals("", commandBoxHandleOutput.getInput());
        assertEquals(defaultStyleOfCommandBox, commandBoxHandleOutput.getStyleClass());
    }

```
