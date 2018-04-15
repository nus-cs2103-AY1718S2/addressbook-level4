# EdwardKSG
###### \java\guitests\guihandles\BrowserPanelHandle.java
``` java
    /**
     * Returns the {@code String title} of the currently loaded page.
     */
    public String getLoadedTitle() {
        return WebViewUtil.getLoadedTitle(getChildNode(BROWSER_ID));
    }
```
###### \java\guitests\guihandles\WebViewUtil.java
``` java
    /**
     * Returns the {@code String} of the currently loaded page in the {@code webView}.
     */
    public static String getLoadedTitle(WebView webView) {
        return webView.getEngine().getTitle();
    }
```
###### \java\seedu\progresschecker\logic\commands\AddDefaultTasksCommandTest.java
``` java
/**
 * Contains assertion tests for {@code AddDefaultTasksCommand}. This command is not undoable.
 * This test may take a long time to execute, roughly 20s.
 */
public class AddDefaultTasksCommandTest {
    public static final String TEST_TITLE = "testTitle";
    @Test
    public void execute_commandEquals() throws Exception {
        AddDefaultTasksCommand completeTaskCommand = new AddDefaultTasksCommand(DEFAULT_LIST_TITLE);
        AddDefaultTasksCommand completeTaskCommand2 = new AddDefaultTasksCommand("random thing");

        // same object -> execution successful
        assertTrue(completeTaskCommand.equals(completeTaskCommand));

        // different object -> execution failed
        assertFalse(completeTaskCommand.equals(completeTaskCommand2));
    }

    /* Outdated: Decided to remove this test because: 1. this test will add a new task list and the content of the
    list is still being updated while before the final release. Once the list data is updated by us developers, the
    edge condition and expected output for tests of complete/reset task command and view URL command will all must be
    updated which is very tedious. 2. the result of this command is easy to observe and no repetitive tests involved
    3. this test takes a long time, which slows down the process when other developers build the project.
    Current solution: have a special fixed test data file which is small. */
    @Test
    public void execute_success() throws Exception {
        AddDefaultTasksCommand addDefaultTasksCommand = new AddDefaultTasksCommand(TEST_TITLE);

        String expected = String.format(MESSAGE_SUCCESS, DEFAULT_LIST_TITLE);
        String actual = addDefaultTasksCommand.execute().feedbackToUser;
        assertEquals(expected, actual);
    }

}
```
###### \java\seedu\progresschecker\logic\commands\CompleteTaskCommandTest.java
``` java
/**
 * Contains assertion tests for {@code CompleteTaskCommand}. This command is not undoable.
 */
public class CompleteTaskCommandTest {

    @Test
    public void execute_commandEquals() throws Exception {
        CompleteTaskCommand completeTaskCommand = new CompleteTaskCommand(INDEX_FIRST_TASK_INT);
        CompleteTaskCommand completeTaskCommand2 = new CompleteTaskCommand(INDEX_LAST_TASK_INT);

        // same object -> execution successful
        assertTrue(completeTaskCommand.equals(completeTaskCommand));

        // different object -> execution failed
        assertFalse(completeTaskCommand.equals(completeTaskCommand2));
    }

    @Test
    public void execute_validIndexFirst_success() throws Exception {
        CompleteTaskCommand completeFirst = new CompleteTaskCommand(INDEX_FIRST_TASK_INT);

        String expected = String.format(MESSAGE_SUCCESS,
                INDEX_FIRST_TASK_INT + ". LO[W6.5][Submission]");
        String actual = completeFirst.execute().feedbackToUser;
        assertEquals(expected, actual);
    }

    @Test
    public void execute_validIndexLast_success() throws Exception {
        CompleteTaskCommand completeLast = new CompleteTaskCommand(INDEX_LAST_TASK_INT);

        String expected = String.format(MESSAGE_SUCCESS,
                INDEX_LAST_TASK_INT + ". LO[W3.10][Compulsory][Submission]");
        String actual = completeLast.execute().feedbackToUser;
        assertEquals(expected, actual);
    }

    @Test
    public void execute_validIndexLastTwice_success() throws Exception {
        CompleteTaskCommand completeTwice = new CompleteTaskCommand(INDEX_LAST_TASK_INT);

        String expected = String.format(MESSAGE_NO_ACTION,
                INDEX_LAST_TASK_INT + ". LO[W3.10][Compulsory][Submission]");
        String actual = completeTwice.execute().feedbackToUser;
        assertEquals(expected, actual);
    }

    // the case of negative/zero/non-integer are tested in the command parser test.

    @Test
    public void execute_invalidIndexZero_success() throws Exception {
        CompleteTaskCommand completeOutOfBound = new CompleteTaskCommand(OUT_OF_BOUND_TASK_INDEX_INT);

        String expected = String.format(INDEX_OUT_OF_BOUND);
        String actual = completeOutOfBound.execute().feedbackToUser;
        assertEquals(expected, actual);
    }
}

```
###### \java\seedu\progresschecker\logic\commands\EditPersonDescriptorTest.java
``` java
        // different major -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withMajor(VALID_MAJOR_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different year -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withYear(VALID_YEAR_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));
```
###### \java\seedu\progresschecker\logic\commands\GoToTaskUrlCommandTest.java
``` java
/**
 * Contains assertion tests for {@code GoToTaskUrlCommand}. This command is not undoable.
 */
public class GoToTaskUrlCommandTest {

    @Test
    public void execute_commandEquals() throws Exception {
        GoToTaskUrlCommand goToTaskUrlCommand = new GoToTaskUrlCommand(INDEX_FIRST_TASK_INT);
        GoToTaskUrlCommand goToTaskUrlCommand2 = new GoToTaskUrlCommand(INDEX_LAST_TASK_INT);

        // same object -> execution successful
        assertTrue(goToTaskUrlCommand.equals(goToTaskUrlCommand));

        // different object -> execution failed
        assertFalse(goToTaskUrlCommand.equals(goToTaskUrlCommand2));
    }

    @Test
    public void execute_validIndexFirst_success() throws Exception {
        GoToTaskUrlCommand gotoFirst = new GoToTaskUrlCommand(INDEX_FIRST_TASK_INT);

        String expected = String.format(MESSAGE_SUCCESS,
                INDEX_FIRST_TASK_INT + ". LO[W6.5][Submission]");
        String actual = gotoFirst.execute().feedbackToUser;
        assertEquals(expected, actual);
    }

    @Test
    public void execute_validIndexLast_success() throws Exception {
        GoToTaskUrlCommand gotoLast = new GoToTaskUrlCommand(INDEX_LAST_TASK_INT);

        String expected = String.format(MESSAGE_SUCCESS,
                INDEX_LAST_TASK_INT + ". LO[W3.10][Compulsory][Submission]");
        String actual = gotoLast.execute().feedbackToUser;
        assertEquals(expected, actual);
    }

    @Test
    public void execute_validIndexLastTwice_success() throws Exception {
        GoToTaskUrlCommand gotoTwice = new GoToTaskUrlCommand(INDEX_LAST_TASK_INT);

        String expected = String.format(MESSAGE_SUCCESS,
                INDEX_LAST_TASK_INT + ". LO[W3.10][Compulsory][Submission]");
        String actual = gotoTwice.execute().feedbackToUser;
        assertEquals(expected, actual);
    }

    // the case of negative/zero/non-integer are tested in the command parser test.

    @Test
    public void execute_invalidIndexZero_success() throws Exception {
        GoToTaskUrlCommand gotoOutOfBound = new GoToTaskUrlCommand(OUT_OF_BOUND_TASK_INDEX_INT);

        String expected = String.format(INDEX_OUT_OF_BOUND);
        String actual = gotoOutOfBound.execute().feedbackToUser;
        assertEquals(expected, actual);
    }
}
```
###### \java\seedu\progresschecker\logic\commands\ResetTaskCommandTest.java
``` java
/**
 * Contains assertion tests for {@code ResetTaskCommand}. This command is not undoable.
 */
public class ResetTaskCommandTest {

    @Test
    public void execute_commandEquals() throws Exception {
        ResetTaskCommand resetTaskCommand = new ResetTaskCommand(INDEX_FIRST_TASK_INT);
        ResetTaskCommand resetTaskCommand2 = new ResetTaskCommand(INDEX_LAST_TASK_INT);

        // same object -> execution successful
        assertTrue(resetTaskCommand.equals(resetTaskCommand));

        // different object -> execution failed
        assertFalse(resetTaskCommand.equals(resetTaskCommand2));
    }

    @Test
    public void execute_validIndexFirst_success() throws Exception {
        ResetTaskCommand resetFirst = new ResetTaskCommand(INDEX_FIRST_TASK_INT);

        String expected = String.format(MESSAGE_SUCCESS,
                INDEX_FIRST_TASK_INT + ". LO[W6.5][Submission]");
        String actual = resetFirst.execute().feedbackToUser;
        assertEquals(expected, actual);
    }

    @Test
    public void execute_validIndexLast_success() throws Exception {
        ResetTaskCommand resetLast = new ResetTaskCommand(INDEX_LAST_TASK_INT);

        String expected = String.format(MESSAGE_SUCCESS,
                INDEX_LAST_TASK_INT + ". LO[W3.10][Compulsory][Submission]");
        String actual = resetLast.execute().feedbackToUser;
        assertEquals(expected, actual);
    }

    @Test
    public void execute_validIndexLastTwice_success() throws Exception {
        ResetTaskCommand resetTwice = new ResetTaskCommand(INDEX_LAST_TASK_INT);

        String expected = String.format(MESSAGE_NO_ACTION,
                INDEX_LAST_TASK_INT + ". LO[W3.10][Compulsory][Submission]");
        String actual = resetTwice.execute().feedbackToUser;
        assertEquals(expected, actual);
    }

    // the case of negative/zero/non-integer are tested in the command parser test.

    @Test
    public void execute_invalidIndexZero_success() throws Exception {
        ResetTaskCommand resetOutOfBound = new ResetTaskCommand(OUT_OF_BOUND_TASK_INDEX_INT);

        String expected = String.format(INDEX_OUT_OF_BOUND);
        String actual = resetOutOfBound.execute().feedbackToUser;
        assertEquals(expected, actual);
    }
}
```
###### \java\seedu\progresschecker\logic\commands\ViewTaskListCommandTest.java
``` java
/**
 * Contains assertion tests for {@code ViewTaskListCommand}. This command is not undoable.
 */
public class ViewTaskListCommandTest {

    @Test
    public void execute_commandEquals() throws Exception {
        ViewTaskListCommand viewTaskListCommand = new ViewTaskListCommand(ASTERISK_INT);
        ViewTaskListCommand viewTaskListCommand2 = new ViewTaskListCommand(FIRST_WEEK_INT);

        // same object -> execution successful
        assertTrue(viewTaskListCommand.equals(viewTaskListCommand));

        // different object -> execution failed
        assertFalse(viewTaskListCommand.equals(viewTaskListCommand2));
    }

    @Test
    public void execute_validArgUnfilteredList_success() throws Exception {
        ViewTaskListCommand viewAll = new ViewTaskListCommand(ASTERISK_INT);

        String expected = String.format(MESSAGE_SUCCESS, DEFAULT_LIST_TITLE);
        String actual = viewAll.execute().feedbackToUser;
        assertEquals(expected, actual);
    }

    @Test
    public void execute_validArgFirstWeekFilteredList_success() throws Exception {
        ViewTaskListCommand viewFirst = new ViewTaskListCommand(FIRST_WEEK_INT);

        String expected = String.format(MESSAGE_SUCCESS,
                DEFAULT_LIST_TITLE + "  Week: " + FIRST_WEEK_INT);
        String actual = viewFirst.execute().feedbackToUser;
        assertEquals(expected, actual);
    }

    @Test
    public void execute_validArgLastWeekFilteredList_success() throws Exception {
        ViewTaskListCommand viewFirst = new ViewTaskListCommand(LAST_WEEK_INT);

        String expected = String.format(MESSAGE_SUCCESS,
                DEFAULT_LIST_TITLE + "  Week: " + LAST_WEEK_INT);
        String actual = viewFirst.execute().feedbackToUser;
        assertEquals(expected, actual);
    }

    @Test
    public void execute_validArgCompulsoryFilteredList_success() throws Exception {
        ViewTaskListCommand viewFirst = new ViewTaskListCommand(COM_INT);

        String expected = String.format(MESSAGE_SUCCESS,
                DEFAULT_LIST_TITLE + COMPULSORY_STR);
        String actual = viewFirst.execute().feedbackToUser;
        assertEquals(expected, actual);
    }

    @Test
    public void execute_validArgSubmissionFilteredList_success() throws Exception {
        ViewTaskListCommand viewFirst = new ViewTaskListCommand(SUB_INT);

        String expected = String.format(MESSAGE_SUCCESS,
                DEFAULT_LIST_TITLE + SUBMISSION_STR);
        String actual = viewFirst.execute().feedbackToUser;
        assertEquals(expected, actual);
    }

}
```
###### \java\seedu\progresschecker\logic\parser\AddCommandParserTest.java
``` java
        // multiple usernames - last name accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + USERNAME_DESC_AMY
                        + USERNAME_DESC_BOB + MAJOR_DESC_BOB + YEAR_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedPerson));

        // multiple majors - last major accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + USERNAME_DESC_BOB
                + MAJOR_DESC_AMY + MAJOR_DESC_BOB + YEAR_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedPerson));

        // multiple years - last year accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + USERNAME_DESC_BOB
                + MAJOR_DESC_BOB + YEAR_DESC_AMY + YEAR_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedPerson));
```
###### \java\seedu\progresschecker\logic\parser\AddCommandParserTest.java
``` java
        // missing username prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + VALID_USERNAME_BOB
                + MAJOR_DESC_BOB + YEAR_DESC_BOB, expectedMessage);

        // missing major prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + USERNAME_DESC_BOB
                + VALID_MAJOR_BOB + YEAR_DESC_BOB, expectedMessage);

        // missing year prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + USERNAME_DESC_BOB
                + MAJOR_DESC_BOB + VALID_YEAR_BOB, expectedMessage);
```
###### \java\seedu\progresschecker\logic\parser\AddCommandParserTest.java
``` java
        // invalid username
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_USERNAME_DESC
                + MAJOR_DESC_BOB + YEAR_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                GithubUsername.MESSAGE_USERNAME_CONSTRAINTS);

        // invalid major
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + USERNAME_DESC_BOB
                + INVALID_MAJOR_DESC + YEAR_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Major.MESSAGE_MAJOR_CONSTRAINTS);

        // invalid year
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + USERNAME_DESC_BOB
                + MAJOR_DESC_BOB + INVALID_YEAR_DESC + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Year.MESSAGE_YEAR_CONSTRAINTS);
```
###### \java\seedu\progresschecker\logic\parser\CompleteTaskCommandParserTest.java
``` java
public class CompleteTaskCommandParserTest {

    private CompleteTaskCommandParser parser = new CompleteTaskCommandParser();

    @Test
    public void parse_validArgsFirstTask_returnsCompleteTaskCommand() {
        assertParseSuccess(parser, INDEX_FIRST_TASK, new CompleteTaskCommand(INDEX_FIRST_TASK_INT));
    }

    @Test
    public void parse_invalidArgsNegative_throwsParseException() {
        assertParseFailure(parser, INVALID_NEGATIVE, String.format(MESSAGE_INVALID_INDEX_OR_FORMAT,
                CompleteTaskCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgsZero_throwsParseException() {
        assertParseFailure(parser, INVALID_ZERO, String.format(MESSAGE_INVALID_INDEX_OR_FORMAT,
                CompleteTaskCommand.MESSAGE_USAGE));
    }
    @Test
    public void parse_invalidArgsCharset_throwsParseException() {
        assertParseFailure(parser, INVALID_CHARSET, String.format(MESSAGE_INVALID_INDEX_OR_FORMAT,
                CompleteTaskCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\progresschecker\logic\parser\EditCommandParserTest.java
``` java
        assertParseFailure(parser, "1" + INVALID_USERNAME_DESC,
                GithubUsername.MESSAGE_USERNAME_CONSTRAINTS); // invalid username
        assertParseFailure(parser, "1" + INVALID_MAJOR_DESC, Major.MESSAGE_MAJOR_CONSTRAINTS); // invalid major
        assertParseFailure(parser, "1" + INVALID_YEAR_DESC, Year.MESSAGE_YEAR_CONSTRAINTS); // invalid year
```
###### \java\seedu\progresschecker\logic\parser\EditCommandParserTest.java
``` java
        // username
        userInput = targetIndex.getOneBased() + USERNAME_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withUsername(VALID_USERNAME_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // major
        userInput = targetIndex.getOneBased() + MAJOR_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withMajor(VALID_MAJOR_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
```
###### \java\seedu\progresschecker\logic\parser\GoToTaskUrlCommandParserTest.java
``` java
public class GoToTaskUrlCommandParserTest {

    private GoToTaskUrlCommandParser parser = new GoToTaskUrlCommandParser();

    @Test
    public void parse_validArgsFirstTask_returnsGoToTaskUrlCommand() {
        assertParseSuccess(parser, INDEX_FIRST_TASK, new GoToTaskUrlCommand(INDEX_FIRST_TASK_INT));
    }

    @Test
    public void parse_invalidArgsNegative_throwsParseException() {
        assertParseFailure(parser, INVALID_NEGATIVE, String.format(MESSAGE_INVALID_INDEX_OR_FORMAT,
                GoToTaskUrlCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgsZero_throwsParseException() {
        assertParseFailure(parser, INVALID_ZERO, String.format(MESSAGE_INVALID_INDEX_OR_FORMAT,
                GoToTaskUrlCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgsCharset_throwsParseException() {
        assertParseFailure(parser, INVALID_CHARSET, String.format(MESSAGE_INVALID_INDEX_OR_FORMAT,
                GoToTaskUrlCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\progresschecker\logic\parser\ParserUtilTest.java
``` java
    @Test
    public void parseTaskIndex_invalidInputZero_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseTaskIndex(INVALID_ZERO);
    }

    @Test
    public void parseTaskIndex_invalidInputNegative_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseTaskIndex(INVALID_NEGATIVE);
    }

    @Test
    public void parseTaskIndex_invalidInputNotInteger_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseTaskIndex(INVALID_DOUBLE);
    }

    @Test
    public void parseTaskIndex_invalidInputNotNumber_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseTaskIndex(INVALID_CHARSET);
    }

    @Test
    public void parseTaskIndex_invalidInputMultipleArgs_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseTaskIndex(INVALID_MULTIPLE_ARGS);
    }

    @Test
    public void parseTaskIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_TASK_INT, ParserUtil.parseTaskIndex(INDEX_FIRST_TASK));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_TASK_INT, ParserUtil.parseTaskIndex(" " + INDEX_FIRST_TASK + " "));
    }

    @Test
    public void parseTaskWeek_invalidInputOutOfBound_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseTaskWeek(OUT_OF_BOUND_WEEK);
    }

    @Test
    public void parseTaskWeek_invalidInputZero_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseTaskWeek(INVALID_ZERO);
    }

    @Test
    public void parseTaskWeek_invalidInputNegative_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseTaskWeek(INVALID_NEGATIVE);
    }

    @Test
    public void parseTaskWeek_invalidInputNotInteger_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseTaskWeek(INVALID_DOUBLE);
    }

    @Test
    public void parseTaskWeek_invalidInputNotNumber_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseTaskWeek(INVALID_CHARSET);
    }

    @Test
    public void parseTaskWeek_invalidInputMultipleArgs_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseTaskWeek(INVALID_MULTIPLE_ARGS);
    }

    @Test
    public void parseTaskWeek_validInputFirstWeek_success() throws Exception {
        // No whitespaces
        assertEquals(FIRST_WEEK_INT, ParserUtil.parseTaskWeek(FIRST_WEEK));

        // Leading and trailing whitespaces
        assertEquals(FIRST_WEEK_INT, ParserUtil.parseTaskWeek(" " + FIRST_WEEK + " "));
    }

    @Test
    public void parseTaskWeek_validInputLastWeek_success() throws Exception {
        // No whitespaces
        assertEquals(LAST_WEEK_INT, ParserUtil.parseTaskWeek(LAST_WEEK));

        // Leading and trailing whitespaces
        assertEquals(LAST_WEEK_INT, ParserUtil.parseTaskWeek(" " + LAST_WEEK + " "));
    }

    @Test
    public void parseTaskWeek_validInputAsterisk_success() throws Exception {
        // No whitespaces
        assertEquals(ASTERISK_INT, ParserUtil.parseTaskWeek(ASTERISK));

        // Leading and trailing whitespaces
        assertEquals(ASTERISK_INT, ParserUtil.parseTaskWeek(" " + ASTERISK + " "));
    }

    @Test
    public void parseTaskWeek_validInputCom_success() throws Exception {
        // No whitespaces
        assertEquals(COM_INT, ParserUtil.parseTaskWeek(COMPULSORY));

        // Leading and trailing whitespaces
        assertEquals(COM_INT, ParserUtil.parseTaskWeek(" " + COMPULSORY + " "));

        // No whitespaces-alias
        assertEquals(COM_INT, ParserUtil.parseTaskWeek(COM));

        // Leading and trailing whitespaces-alias
        assertEquals(COM_INT, ParserUtil.parseTaskWeek(" " + COM + " "));
    }

    @Test
    public void parseTaskWeek_validInputSub_success() throws Exception {
        // No whitespaces
        assertEquals(SUB_INT, ParserUtil.parseTaskWeek(SUBMISSION));

        // Leading and trailing whitespaces
        assertEquals(SUB_INT, ParserUtil.parseTaskWeek(" " + SUBMISSION + " "));

        // No whitespaces-alias
        assertEquals(SUB_INT, ParserUtil.parseTaskWeek(SUB));

        // Leading and trailing whitespaces-alias
        assertEquals(SUB_INT, ParserUtil.parseTaskWeek(" " + SUB + " "));
    }

    @Test
    public void parseTaskTitle_invalidInput_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseTaskTitle(INVALID_TITLE);
    }

    @Test
    public void parseTaskTitle_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(DEFAULT_LIST_TITLE, ParserUtil.parseTaskTitle(DEFAULT_LIST_TITLE));

        // Leading and trailing whitespaces
        assertEquals(DEFAULT_LIST_TITLE, ParserUtil.parseTaskTitle(" " + DEFAULT_LIST_TITLE + " "));

        // Valid length without leading and trailing whitespaces, but exceeds limit after having these spaces
        assertEquals(VALID_TITLE_EDGE, ParserUtil.parseTaskTitle(" " + VALID_TITLE_EDGE + " "));
    }
```
###### \java\seedu\progresschecker\logic\parser\ParserUtilTest.java
``` java
    @Test
    public void parseUsername_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseUsername((String) null));
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseUsername((Optional<String>) null));
    }

    @Test
    public void parseUsername_invalidValue_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseUsername(INVALID_USERNAME));
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseUsername(
                Optional.of(INVALID_USERNAME)));
    }

    @Test
    public void parseUsername_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseUsername(Optional.empty()).isPresent());
    }

    @Test
    public void parseUsername_validValueWithoutWhitespace_returnsUsername() throws Exception {
        GithubUsername expectedUsername = new GithubUsername(VALID_USERNAME);
        assertEquals(expectedUsername, ParserUtil.parseUsername(VALID_USERNAME));
        assertEquals(Optional.of(expectedUsername), ParserUtil.parseUsername(Optional.of(VALID_USERNAME)));
    }

    @Test
    public void parseUsername_validValueWithWhitespace_returnsTrimmedUsername() throws Exception {
        String usernameWithWhitespace = WHITESPACE + VALID_USERNAME + WHITESPACE;
        GithubUsername expectedUsername = new GithubUsername(VALID_USERNAME);
        assertEquals(expectedUsername, ParserUtil.parseUsername(usernameWithWhitespace));
        assertEquals(Optional.of(expectedUsername), ParserUtil.parseUsername(Optional.of(usernameWithWhitespace)));
    }


    @Test
    public void parseMajor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseMajor((String) null));
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseMajor((Optional<String>) null));
    }

    @Test
    public void parseMajor_invalidValue_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseMajor(INVALID_MAJOR));
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseMajor(Optional.of(INVALID_MAJOR)));
    }

    @Test
    public void parseMajor_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseMajor(Optional.empty()).isPresent());
    }

    @Test
    public void parseMajor_validValueWithoutWhitespace_returnsMajor() throws Exception {
        Major expectedMajor = new Major(VALID_MAJOR);
        assertEquals(expectedMajor, ParserUtil.parseMajor(VALID_MAJOR));
        assertEquals(Optional.of(expectedMajor), ParserUtil.parseMajor(Optional.of(VALID_MAJOR)));
    }

    @Test
    public void parseMajor_validValueWithWhitespace_returnsTrimmedMajor() throws Exception {
        String majorWithWhitespace = WHITESPACE + VALID_MAJOR + WHITESPACE;
        Major expectedMajor = new Major(VALID_MAJOR);
        assertEquals(expectedMajor, ParserUtil.parseMajor(majorWithWhitespace));
        assertEquals(Optional.of(expectedMajor), ParserUtil.parseMajor(Optional.of(majorWithWhitespace)));
    }
```
###### \java\seedu\progresschecker\logic\parser\ProgressCheckerParserTest.java
``` java
    @Test
    public void parseCommand_addDefaultTasks() throws Exception {
        assertTrue(parser.parseCommand(AddDefaultTasksCommand.COMMAND_WORD) instanceof AddDefaultTasksCommand);
        assertTrue(parser.parseCommand(AddDefaultTasksCommand.COMMAND_WORD
                + " 3") instanceof AddDefaultTasksCommand);
        assertTrue(parser.parseCommand(AddDefaultTasksCommand.COMMAND_ALIAS) instanceof AddDefaultTasksCommand);
        assertTrue(parser.parseCommand(AddDefaultTasksCommand.COMMAND_ALIAS
                + " 3") instanceof AddDefaultTasksCommand);
    }

    @Test
    public void parseCommand_completeTask() throws Exception {
        CompleteTaskCommand command = (CompleteTaskCommand) parser.parseCommand(
                CompleteTaskCommand.COMMAND_WORD + " " + INDEX_FIRST_TASK);
        assertEquals(new CompleteTaskCommand(INDEX_FIRST_TASK_INT), command);
        CompleteTaskCommand command2 = (CompleteTaskCommand) parser.parseCommand(
                CompleteTaskCommand.COMMAND_ALIAS + " " + INDEX_FIRST_TASK);
        assertEquals(new CompleteTaskCommand(INDEX_FIRST_TASK_INT), command2);
    }

    @Test
    public void parseCommand_resetTask() throws Exception {
        ResetTaskCommand command = (ResetTaskCommand) parser.parseCommand(
                ResetTaskCommand.COMMAND_WORD + " " + INDEX_FIRST_TASK);
        assertEquals(new ResetTaskCommand(INDEX_FIRST_TASK_INT), command);
        ResetTaskCommand command2 = (ResetTaskCommand) parser.parseCommand(
                ResetTaskCommand.COMMAND_ALIAS + " " + INDEX_FIRST_TASK);
        assertEquals(new ResetTaskCommand(INDEX_FIRST_TASK_INT), command2);
    }

    @Test
    public void parseCommand_goToTaskUrl() throws Exception {
        GoToTaskUrlCommand command = (GoToTaskUrlCommand) parser.parseCommand(
                GoToTaskUrlCommand.COMMAND_WORD + " " + INDEX_FIRST_TASK);
        assertEquals(new GoToTaskUrlCommand(INDEX_FIRST_TASK_INT), command);
        GoToTaskUrlCommand command2 = (GoToTaskUrlCommand) parser.parseCommand(
                GoToTaskUrlCommand.COMMAND_ALIAS + " " + INDEX_FIRST_TASK);
        assertEquals(new GoToTaskUrlCommand(INDEX_FIRST_TASK_INT), command2);
    }

    @Test
    public void parseCommand_viewTaskList() throws Exception {
        ViewTaskListCommand command = (ViewTaskListCommand) parser.parseCommand(
                ViewTaskListCommand.COMMAND_WORD + " " + FIRST_WEEK);
        assertEquals(new ViewTaskListCommand(FIRST_WEEK_INT), command);
        ViewTaskListCommand command2 = (ViewTaskListCommand) parser.parseCommand(
                ViewTaskListCommand.COMMAND_ALIAS + " " + COMPULSORY);
        assertEquals(new ViewTaskListCommand(COM_INT), command2);
    }
```
###### \java\seedu\progresschecker\logic\parser\ResetTaskCommandParserTest.java
``` java
public class ResetTaskCommandParserTest {

    private ResetTaskCommandParser parser = new ResetTaskCommandParser();

    @Test
    public void parse_validArgsFirstTask_returnsResetTaskCommand() {
        assertParseSuccess(parser, INDEX_FIRST_TASK, new ResetTaskCommand(INDEX_FIRST_TASK_INT));
    }

    @Test
    public void parse_invalidArgsNegative_throwsParseException() {
        assertParseFailure(parser, INVALID_NEGATIVE, String.format(MESSAGE_INVALID_INDEX_OR_FORMAT,
                ResetTaskCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgsZero_throwsParseException() {
        assertParseFailure(parser, INVALID_ZERO, String.format(MESSAGE_INVALID_INDEX_OR_FORMAT,
                ResetTaskCommand.MESSAGE_USAGE));
    }
    @Test
    public void parse_invalidArgsCharset_throwsParseException() {
        assertParseFailure(parser, INVALID_CHARSET, String.format(MESSAGE_INVALID_INDEX_OR_FORMAT,
                ResetTaskCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\progresschecker\logic\parser\ViewTaskListCommandParserTest.java
``` java
public class ViewTaskListCommandParserTest {

    private ViewTaskListCommandParser parser = new ViewTaskListCommandParser();

    @Test
    public void parse_validArgsFirstWeek_returnsViewTaskListCommand() {
        assertParseSuccess(parser, FIRST_WEEK, new ViewTaskListCommand(FIRST_WEEK_INT));
    }

    @Test
    public void parse_validArgsCompulsory_returnsViewTaskListCommand() {
        assertParseSuccess(parser, COMPULSORY, new ViewTaskListCommand(COM_INT));
    }

    @Test
    public void parse_validArgsSubmission_returnsViewTaskListCommand() {
        assertParseSuccess(parser, SUBMISSION, new ViewTaskListCommand(SUB_INT));
    }

    @Test
    public void parse_invalidArgsNegative_throwsParseException() {
        assertParseFailure(parser, INVALID_NEGATIVE, String.format(MESSAGE_INVALID_TASK_FILTER,
                ViewTaskListCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgsZero_throwsParseException() {
        assertParseFailure(parser, INVALID_ZERO, String.format(MESSAGE_INVALID_TASK_FILTER,
                ViewTaskListCommand.MESSAGE_USAGE));
    }
    @Test
    public void parse_invalidArgsCharset_throwsParseException() {
        assertParseFailure(parser, INVALID_CHARSET, String.format(MESSAGE_INVALID_TASK_FILTER,
                ViewTaskListCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\progresschecker\model\person\GithubUsernameTest.java
``` java
public class GithubUsernameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new GithubUsername(null));
    }

    @Test
    public void constructor_invalidUsername_throwsIllegalArgumentException() {
        String invalidUsername = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new GithubUsername(invalidUsername));
    }

    @Test
    public void isValidUsername() {
        // null username
        Assert.assertThrows(NullPointerException.class, () -> GithubUsername.isValidUsername(null));

        // invalid username
        assertFalse(GithubUsername.isValidUsername("")); // empty string
        assertFalse(GithubUsername.isValidUsername(" ")); // spaces only
        assertFalse(GithubUsername.isValidUsername("^")); // only non-alphanumeric characters
        assertFalse(GithubUsername.isValidUsername("peter*")); // contains non-alphanumeric characters

        // valid username
        assertTrue(GithubUsername.isValidUsername("peter jack")); // alphabets only
        assertTrue(GithubUsername.isValidUsername("12345")); // numbers only
        assertTrue(GithubUsername.isValidUsername("peter the 2nd")); // alphanumeric characters
        assertTrue(GithubUsername.isValidUsername("Capital Tan")); // with capital letters
        assertTrue(GithubUsername.isValidUsername("David Roger Jackson Ray Jr 2nd")); // long usernames
    }
}
```
###### \java\seedu\progresschecker\model\person\MajorTest.java
``` java
public class MajorTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Major(null));
    }

    @Test
    public void constructor_invalidMajor_throwsIllegalArgumentException() {
        String invalidMajor = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Major(invalidMajor));
    }

    @Test
    public void isValidMajor() {
        // null major
        Assert.assertThrows(NullPointerException.class, () -> Major.isValidMajor(null));

        // invalid majors
        assertFalse(Major.isValidMajor("")); // empty string
        assertFalse(Major.isValidMajor(" ")); // spaces only

        // valid majors
        assertTrue(Major.isValidMajor("Blk 456, Den Road, #01-355"));
        assertTrue(Major.isValidMajor("-")); // one character
        assertTrue(Major.isValidMajor("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA")); // long major
    }
}
```
###### \java\seedu\progresschecker\testutil\TypicalTaskArgs.java
``` java
/**
 * A utility class containing a list of arguments to be used in tests for tasks commands.
 */
public class TypicalTaskArgs {
    // User input

    // week number arguments
    public static final String FIRST_WEEK = "1";
    public static final int FIRST_WEEK_INT = 1;
    public static final String RANDOM_WEEK = "5";
    public static final String LAST_WEEK = "13";
    public static final int LAST_WEEK_INT = 13;

    public static final String OUT_OF_BOUND_WEEK = "14";

    // task index number arguments
    public static final String INDEX_FIRST_TASK = "1";
    public static final int INDEX_FIRST_TASK_INT = 1;
    public static final int INDEX_LAST_TASK_INT = 4; //specifically for the model being tested
    public static final int OUT_OF_BOUND_TASK_INDEX_INT = 500;

    // valid char arguments
    public static final String COMPULSORY = "compulsory";
    public static final String COM = "com";
    public static final int COM_INT = -13;
    public static final String SUBMISSION = "submission";
    public static final String SUB = "sub";
    public static final int SUB_INT = -20;
    public static final String ASTERISK = "*";
    public static final int ASTERISK_INT = 0;
    public static final String DEFAULT_LIST_TITLE = "CS2103 LOs";
    public static final String VALID_TITLE_EDGE = "1234567891234567891234567891234567891234567891234";

    // general invalid input arguments
    public static final String INVALID_ZERO = "0";
    public static final String INVALID_NEGATIVE = "-3";
    public static final String INVALID_DOUBLE = "3.4";
    public static final String INVALID_CHARSET = "comppp";
    public static final String INVALID_TITLE = "ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss"
            + "ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss"; // exceeds length limit
    public static final String INVALID_MULTIPLE_ARGS = "compulsory 4 2";

    //-----------------------------------------------------------------------------------------------------

    // Parser output (command input)

}
```
###### \java\seedu\progresschecker\ui\Browser2PanelTest.java
``` java
public class Browser2PanelTest extends GuiUnitTest {
    private static final String webpage = "<!DOCTYPE html>\n"
            + "<html>\n"
            + "<head>\n"
            + "    <!-- <link rel=\"stylesheet\" href=\"DarkTheme.css\"> -->\n"
            + "</head>\n"
            + "\n"
            + "<body class=\"background\">\n"
            + "</body>\n"
            + "</html>";

    private Browser2Panel browser2Panel;
    private Browser2PanelHandle browser2PanelHandle;

    private LoadBarEvent loadBarEventStub;

    @Before
    public void setUp() {
        loadBarEventStub = new LoadBarEvent(webpage);

        guiRobot.interact(() -> browser2Panel = new Browser2Panel());
        uiPartRule.setUiPart(browser2Panel);

        browser2PanelHandle = new Browser2PanelHandle(browser2Panel.getRoot());
    }

    @Test
    public void display() throws Exception {
        // default web page
        URL expectedDefaultPageUrl = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        assertEquals(expectedDefaultPageUrl, browser2PanelHandle.getLoadedUrl());

        postNow(loadBarEventStub);
        String expectedTitle = null;

        waitUntilBrowser2Loaded(browser2PanelHandle);
        assertEquals(expectedTitle, browser2PanelHandle.getLoadedTitle());
    }
}
```
###### \java\seedu\progresschecker\ui\BrowserPanelTest.java
``` java
    private static final String webpage = "<!DOCTYPE html>\n"
            + "<html>\n"
            + "<head>\n"
            + "    <!-- <link rel=\"stylesheet\" href=\"DarkTheme.css\"> -->\n"
            + "</head>\n"
            + "\n"
            + "<body class=\"background\">\n"
            + "</body>\n"
            + "</html>";

    private PersonPanelSelectionChangedEvent selectionChangedEventStub;
    private LoadUrlEvent loadUrlEventStub;
    private LoadTaskEvent loadTaskEventStub;

    private BrowserPanel browserPanel;
    private BrowserPanelHandle browserPanelHandle;

    @Before
    public void setUp() {
        selectionChangedEventStub = new PersonPanelSelectionChangedEvent(new PersonCard(ALICE, 0));

        loadTaskEventStub = new LoadTaskEvent(webpage);
        String expectedUrl = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE).toString();

        loadUrlEventStub = new LoadUrlEvent(expectedUrl);

        guiRobot.interact(() -> browserPanel = new BrowserPanel());
        uiPartRule.setUiPart(browserPanel);

        browserPanelHandle = new BrowserPanelHandle(browserPanel.getRoot());
    }

    @Test
    public void display() throws Exception {
        // default web page
        URL expectedDefaultPageUrl = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        assertEquals(expectedDefaultPageUrl, browserPanelHandle.getLoadedUrl());

        // associated web page of a person
        postNow(loadTaskEventStub);
        String expectedTitle = null;

        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedTitle, browserPanelHandle.getLoadedTitle());

        postNow(loadUrlEventStub);
        URL expectedUrl = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);;
    }
}
```
###### \java\systemtests\AddCommandSystemTest.java
``` java
        /* Case: add a person with all fields same as another person in the ProgressChecker except major -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
                .withUsername(VALID_USERNAME_AMY).withMajor(VALID_MAJOR_BOB).withYear(VALID_YEAR_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + USERNAME_DESC_AMY
                + MAJOR_DESC_BOB + YEAR_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);
```
###### \java\systemtests\AddCommandSystemTest.java
``` java
        /* Case: missing username -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + MAJOR_DESC_AMY + YEAR_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing major -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + USERNAME_DESC_AMY
                + YEAR_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
```
###### \java\systemtests\AddCommandSystemTest.java
``` java
        /* Case: invalid username -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + INVALID_USERNAME_DESC
                + MAJOR_DESC_AMY + YEAR_DESC_AMY;
        assertCommandFailure(command, GithubUsername.MESSAGE_USERNAME_CONSTRAINTS);
```
###### \java\systemtests\EditCommandSystemTest.java
``` java
        /* Case: invalid username -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased()
                + INVALID_USERNAME_DESC, GithubUsername.MESSAGE_USERNAME_CONSTRAINTS);
```
