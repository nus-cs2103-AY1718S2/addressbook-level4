# EdwardKSG
###### \java\seedu\progresschecker\logic\commands\EditPersonDescriptorTest.java
``` java
        // different major -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withMajor(VALID_MAJOR_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different year -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withYear(VALID_YEAR_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));
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
