# HEARTOFAL1ON
###### /java/seedu/address/logic/parser/NusCouplesParserTest.java
``` java
    public void parseCommand_addAlias() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(AddCommand.COMMAND_ALIAS + " "
                + PersonUtil.getPersonDetails(person));
        assertEquals(new AddCommand(person), command);
    }

```
###### /java/seedu/address/logic/parser/NusCouplesParserTest.java
``` java
    public void parseCommand_clearAlias() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_ALIAS) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_ALIAS + " 3") instanceof ClearCommand);
    }


```
###### /java/seedu/address/logic/parser/NusCouplesParserTest.java
``` java
    public void parseCommand_deleteAlias() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_ALIAS);
        assertEquals(new DeleteCommand(), command);
    }


```
###### /java/seedu/address/logic/parser/NusCouplesParserTest.java
``` java
    public void parseCommand_editAlias() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_ALIAS + " "
                + PersonUtil.getPersonDetails(person));
        assertEquals(new EditCommand(descriptor), command);
    }

```
###### /java/seedu/address/logic/parser/NusCouplesParserTest.java
``` java
    public void parseCommand_exitAlias() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_ALIAS) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_ALIAS + " 3") instanceof ExitCommand);
    }

```
###### /java/seedu/address/logic/parser/NusCouplesParserTest.java
``` java
    public void parseCommand_helpAlias() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_ALIAS) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_ALIAS + " 3") instanceof HelpCommand);
    }

```
###### /java/seedu/address/logic/parser/NusCouplesParserTest.java
``` java
    public void parseCommand_historyAlias() throws Exception {
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_ALIAS) instanceof HistoryCommand);
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_ALIAS + " 3") instanceof HistoryCommand);

        try {
            parser.parseCommand("histories");
            fail("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
        }
    }

```
###### /java/seedu/address/logic/parser/NusCouplesParserTest.java
``` java
    public void parseCommand_selectAlias() throws Exception {
        ShowTimetableCommand command = (ShowTimetableCommand) parser.parseCommand(
                ShowTimetableCommand.COMMAND_ALIAS);
        assertEquals(new ShowTimetableCommand(), command);
    }

```
###### /java/seedu/address/logic/parser/NusCouplesParserTest.java
``` java
    public void parseCommand_redoCommandWordAlias_returnsRedoCommand() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_ALIAS) instanceof RedoCommand);
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_ALIAS + " 1") instanceof RedoCommand);
    }

```
###### /java/seedu/address/logic/parser/NusCouplesParserTest.java
``` java
    public void parseCommand_undoCommandWordAlias_returnsUndoCommand() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_ALIAS) instanceof UndoCommand);
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_ALIAS + " 3") instanceof UndoCommand);
    }

```
