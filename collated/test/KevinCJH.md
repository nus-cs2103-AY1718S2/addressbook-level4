# KevinCJH
###### /java/seedu/address/logic/parser/AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_email() throws Exception {
        EmailCommand command = (EmailCommand) parser.parseCommand(
                EmailCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased());
        assertEquals(new EmailCommand(INDEX_FIRST), command);
    }

    @Test
    public void parseCommand_redoCommandWord_returnsRedoCommand() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD) instanceof RedoCommand);
        assertTrue(parser.parseCommand("redo 1") instanceof RedoCommand);
    }

```
