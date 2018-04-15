# Zhu-Jiahui
###### \java\seedu\address\logic\commands\MatchCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code MatchCommand}.
 */
public class MatchCommandTest {
    private Model model;
    private Model expectedStudentModel;
    private Model expectedTutorModel;
    private MatchCommand matchCommandForStudentList;
    private MatchCommand matchCommandForTutorList;

    private final Category studentCategory = new Category("s");
    private final Category tutorCategory = new Category("t");

    @Before
    public void setup() {
        model = new ModelManager(getUnmatchedAddressBook(), new UserPrefs());
        expectedTutorModel = new ModelManager(getMatchedTutorAddressBook(), new UserPrefs());
        expectedStudentModel = new ModelManager(getMatchedStudentAddressBook(), new UserPrefs());

        matchCommandForStudentList = new MatchCommand(INDEX_FIRST_PERSON, studentCategory);
        matchCommandForStudentList.setData(model, new CommandHistory(), new UndoRedoStack());
        matchCommandForTutorList = new MatchCommand(INDEX_FIRST_PERSON, tutorCategory);
        matchCommandForTutorList.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void executeMatchCommand() {
        assertCommandSuccessForStudentList(matchCommandForStudentList,
                MatchCommand.getMessageForClientListShownSummary(1, 2));
        assertCommandSuccessForTutorList(matchCommandForTutorList,
                MatchCommand.getMessageForClientListShownSummary(3, 1));
    }

    /**
     * Asserts that the code is successfully executed.
     * Asserts the command feedback is equal to expectedMessage.
     * Asserts actual FilteredList is equal to the expected FilteredList
     * Asserts the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccessForStudentList(MatchCommand command, String expectedMessage) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        try {
            CommandResult commandResult = command.execute();
            assertEquals(expectedMessage, commandResult.feedbackToUser);
            assertEquals(expectedStudentModel.getFilteredStudentList(), model.getFilteredStudentList());
            assertEquals(expectedAddressBook, model.getAddressBook());
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Asserts that the code is successfully executed.
     * Asserts the command feedback is equal to expectedMessage.
     * Asserts actual FilteredList is equal to the expected FilteredList
     * Asserts the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccessForTutorList(MatchCommand command, String expectedMessage) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        try {
            CommandResult commandResult = command.execute();
            assertEquals(expectedMessage, commandResult.feedbackToUser);
            assertEquals(expectedTutorModel.getFilteredTutorList(), model.getFilteredTutorList());
            assertEquals(expectedAddressBook, model.getAddressBook());
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }
    @Test
    public void equals() {

        MatchCommand matchFirstCommand = new MatchCommand(INDEX_FIRST_PERSON, CATEGORY_FIRST_PERSON);
        MatchCommand matchSecondCommand = new MatchCommand(INDEX_SECOND_PERSON, CATEGORY_SECOND_PERSON);

        // same object -> returns true
        assertTrue(matchFirstCommand.equals(matchFirstCommand));

        // same values -> returns true
        MatchCommand matchFirstCommandCopy = new MatchCommand(INDEX_FIRST_PERSON, CATEGORY_SECOND_PERSON);
        assertTrue(matchFirstCommand.equals(matchFirstCommandCopy));

        // different types -> returns false
        assertFalse(matchFirstCommand.equals(1));

        // null -> returns false
        assertFalse(matchFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(matchFirstCommand.equals(matchSecondCommand));
    }

    /**
     * Parses {@code userInput} into a {@code FindCommand}.
     */
    private MatchCommand prepareCommand(Index index, Category category) {
        MatchCommand command =
                new MatchCommand(index, category);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

}
```
###### \java\seedu\address\logic\parser\MatchCommandParserTest.java
``` java
public class MatchCommandParserTest {

    private MatchCommandParser parser = new MatchCommandParser();

    @Test
    public void parse_validArgs_returnsMatchCommand() {
        assertParseSuccess(parser, "1 c/s",
                new MatchCommand(INDEX_FIRST_PERSON, new Category("s")));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Missing index
        assertParseFailure(parser, "c/s",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MatchCommand.MESSAGE_USAGE));
        // Missing category
        assertParseFailure(parser, "1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MatchCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\model\person\MatchContainsKeywordsPredicateTest.java
``` java

public class MatchContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        Client firstTestClient = BENSON;
        Client secondTestClient = CARL;

        MatchContainsKeywordsPredicate firstPredicate =
                new MatchContainsKeywordsPredicate(firstTestClient);
        MatchContainsKeywordsPredicate secondPredicate =
                new MatchContainsKeywordsPredicate(secondTestClient);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        MatchContainsKeywordsPredicate firstPredicateCopy =
                new MatchContainsKeywordsPredicate(firstTestClient);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_matchContainsKeywords_returnsTrue() {

        //all matches
        MatchContainsKeywordsPredicate predicate =
                new MatchContainsKeywordsPredicate(BENSON);
        assertTrue(predicate.test(new ClientBuilder().withName("Benson Meier")
                .withAddress("311, Clementi Ave 2, #02-25")
                .withEmail("johnd@example.com").withPhone("98765432")
                .withTags("owesMoney", "friends").withLocation("north").withGrade("s2").withSubject("physics")
                .withCategory("s").build()));

        //some matches
        predicate = new MatchContainsKeywordsPredicate(BENSON);
        assertTrue(predicate.test(new ClientBuilder().withName("Benson Meier")
                .withAddress("311, Clementi Ave 2, #02-25")
                .withEmail("johnd@example.com").withPhone("98765431")
                .withTags("owesMoney", "friends").withLocation("south").withGrade("s2").withSubject("physics")
                .withCategory("s").build()));

        //location, grade subject all matches but the rest of the attributes dont match
        predicate = new MatchContainsKeywordsPredicate(BENSON);
        assertTrue(predicate.test(new ClientBuilder().withName("Alice Pauline")
                .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
                .withPhone("85355255")
                .withTags("friends").withLocation("north").withGrade("s2").withSubject("physics")
                .withCategory("s").build()));

        //1 match
        predicate = new MatchContainsKeywordsPredicate(BENSON);
        assertTrue(predicate.test(new ClientBuilder().withName("Benson Meier").build()));

    }

    @Test
    public void test_matchDoesNotContainKeywords_returnsFalse() {

        //grade, subject and location no match
        MatchContainsKeywordsPredicate predicate = new MatchContainsKeywordsPredicate(BENSON);
        assertFalse(predicate.test(new ClientBuilder().withName("Benson")
                .withAddress("311, Clementi Ave 2, #02-25")
                .withEmail("johnd@example.com").withPhone("98765432")
                .withTags("owesMoney", "friends").withLocation("south").withGrade("s1").withSubject("chemistry")
                .withCategory("s").build()));

    }
}

```
###### \java\seedu\address\model\person\MatchContainsPersonsPredicateTest.java
``` java
public class MatchContainsPersonsPredicateTest {

    @Test
    public void equals() {
        Client firstTestClient = BENSON;
        Client secondTestClient = CARL;

        MatchContainsPersonsPredicate firstPredicate =
                new MatchContainsPersonsPredicate(firstTestClient);
        MatchContainsPersonsPredicate secondPredicate =
                new MatchContainsPersonsPredicate(secondTestClient);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        MatchContainsPersonsPredicate firstPredicateCopy =
                new MatchContainsPersonsPredicate(firstTestClient);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_matchContainsPersons_returnsTrue() {

        MatchContainsPersonsPredicate predicate =
                new MatchContainsPersonsPredicate(BENSON);
        assertTrue(predicate.test(new ClientBuilder().withName("Benson Meier")
                .withAddress("311, Clementi Ave 2, #02-25")
                .withEmail("johnd@example.com").withPhone("98765432")
                .withTags("owesMoney", "friends").withLocation("north").withGrade("s2").withSubject("physics")
                .withCategory("s").build()));

    }

    @Test
    public void test_matchDoesNotContainPersons_returnsFalse() {

        MatchContainsPersonsPredicate predicate =
                new MatchContainsPersonsPredicate(BENSON);
        assertFalse(predicate.test(new ClientBuilder().withName("Benson")
                .withAddress("311, Clementi Ave 2, #02-25")
                .withEmail("johnd@example.com").withPhone("98765432")
                .withTags("owesMoney", "friends").withLocation("north").withGrade("s2").withSubject("physics")
                .withCategory("s").build()));
    }
}
```
###### \java\seedu\address\testutil\MatchedClients.java
``` java
/**
 * A utility class containing a list of {@code Clients} objects to be used in tests.
 */
public class MatchedClients {
    //Students
    public static final Client ALICE = new ClientBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("85355255")
            .withTags("friends").withLocation("north").withGrade("k1").withSubject("math").withCategory("s").build();
    public static final Client BENSON = new ClientBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withTags("owesMoney", "friends").withLocation("north").withGrade("s2").withSubject("physics")
            .withCategory("s").build();
    public static final Client CARL = new ClientBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withAddress("wall street").withLocation("south").withGrade("j1")
            .withSubject("physics").withCategory("s").build();
    public static final Client DANIEL = new ClientBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withAddress("10th street").withLocation("east").withGrade("primary6")
            .withSubject("english").withCategory("s").build();
    public static final Client ELLE = new ClientBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").withAddress("michegan ave").withLocation("west").withGrade("p3")
            .withSubject("math").withCategory("s").build();
    public static final Client FIONA = new ClientBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").withAddress("little tokyo").withLocation("north").withGrade("secondary1")
            .withSubject("physics").withCategory("s").build();
    public static final Client GEORGE = new ClientBuilder().withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com").withAddress("4th street").withLocation("west").withGrade("j2")
            .withSubject("chemistry").withCategory("s").build();

    //Tutors
    public static final Client ANDREW = new ClientBuilder().withName("ANDREW LIM").withPhone("5212533")
            .withEmail("andrew@example.com").withAddress("Andrew street").withLocation("east").withGrade("primary2")
            .withSubject("english").withCategory("t").build();
    public static final Client EDISON = new ClientBuilder().withName("EDISON").withPhone("2313224")
            .withEmail("EDISON@example.com").withAddress("EDISON ave").withLocation("west").withGrade("j2")
            .withSubject("math").withCategory("t").build();
    public static final Client FLOWER = new ClientBuilder().withName("Flower").withPhone("2182427")
            .withEmail("flowerislife@example.com").withAddress("little flower").withLocation("central").withGrade("k1")
            .withSubject("physics").withCategory("t").build();
    public static final Client GERRARD = new ClientBuilder().withName("GERRARD").withPhone("8321242")
            .withEmail("liverpool@example.com").withAddress("Anfield").withLocation("west").withGrade("u4")
            .withSubject("chemistry").withCategory("t").build();

    // Manually added
    public static final Client HOON = new ClientBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withAddress("little india").withLocation("north").withGrade("s1")
            .withSubject("chemistry").withCategory("s").build();
    public static final Client IDA = new ClientBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withAddress("chicago ave").withLocation("north").withGrade("k2")
            .withSubject("math").withCategory("s").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Client AMY = new ClientBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND)
            .withLocation(VALID_LOCATION_AMY).withGrade(VALID_GRADE_AMY).withSubject(VALID_SUBJECT_AMY)
            .withCategory(VALID_CATEGORY_AMY).build();
    public static final Client BOB = new ClientBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .withLocation(VALID_LOCATION_BOB).withGrade(VALID_GRADE_BOB).withSubject(VALID_SUBJECT_BOB)
            .withCategory(VALID_CATEGORY_BOB).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private MatchedClients() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical clients sorted by name.
     */
    public static AddressBook getMatchedStudentAddressBook() {
        AddressBook ab = new AddressBook();
        for (Client client : getMatchedStudent()) {
            try {
                ab.addClient(client);
            } catch (DuplicatePersonException e) {
                throw new AssertionError("not possible");
            }
        }
        return ab;
    }

    /**
     * Returns an {@code AddressBook} with all the typical clients sorted by name.
     */
    public static AddressBook getMatchedTutorAddressBook() {
        AddressBook ab = new AddressBook();
        for (Client client : getMatchedTutor()) {
            try {
                ab.addClient(client);
            } catch (DuplicatePersonException e) {
                throw new AssertionError("not possible");
            }
        }
        return ab;
    }

    public static List<Client> getMatchedStudent() {
        return new ArrayList<>(Arrays.asList(ALICE));
    }

    public static List<Client> getMatchedTutor() {
        return new ArrayList<>(Arrays.asList(EDISON));
    }



}
```
###### \java\seedu\address\testutil\TypicalCategories.java
``` java
/**
 * A utility class containing a list of {@code Index} objects to be used in tests.
 */
public class TypicalCategories {
    public static final Category CATEGORY_FIRST_PERSON = TypicalClients.getTypicalStudents().get(0).getCategory();
    public static final Category CATEGORY_SECOND_PERSON = TypicalClients.getTypicalStudents().get(1).getCategory();
    public static final Category CATEGORY_THIRD_PERSON = TypicalClients.getTypicalStudents().get(2).getCategory();
}
```
###### \java\seedu\address\testutil\UnmatchedClients.java
``` java
/**
 * A utility class containing a list of {@code Clients} objects to be used in tests.
 */
public class UnmatchedClients {
    //Students
    public static final Client ALICE = new ClientBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("85355255")
            .withTags("friends").withLocation("north").withGrade("k1").withSubject("math").withCategory("s").build();
    public static final Client BENSON = new ClientBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withTags("owesMoney", "friends").withLocation("north").withGrade("s2").withSubject("physics")
            .withCategory("s").build();
    public static final Client CARL = new ClientBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withAddress("wall street").withLocation("south").withGrade("j1")
            .withSubject("physics").withCategory("s").build();
    public static final Client DANIEL = new ClientBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withAddress("10th street").withLocation("east").withGrade("primary6")
            .withSubject("english").withCategory("s").build();
    public static final Client ELLE = new ClientBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").withAddress("michegan ave").withLocation("west").withGrade("p3")
            .withSubject("math").withCategory("s").build();
    public static final Client FIONA = new ClientBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").withAddress("little tokyo").withLocation("north").withGrade("secondary1")
            .withSubject("physics").withCategory("s").build();
    public static final Client GEORGE = new ClientBuilder().withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com").withAddress("4th street").withLocation("west").withGrade("j2")
            .withSubject("chemistry").withCategory("s").build();

    //Tutors
    public static final Client ANDREW = new ClientBuilder().withName("ANDREW LIM").withPhone("5212533")
            .withEmail("andrew@example.com").withAddress("Andrew street").withLocation("east").withGrade("primary2")
            .withSubject("english").withCategory("t").build();
    public static final Client EDISON = new ClientBuilder().withName("EDISON").withPhone("2313224")
            .withEmail("EDISON@example.com").withAddress("EDISON ave").withLocation("west").withGrade("j2")
            .withSubject("math").withCategory("t").build();
    public static final Client FLOWER = new ClientBuilder().withName("Flower").withPhone("2182427")
            .withEmail("flowerislife@example.com").withAddress("little flower").withLocation("central").withGrade("k1")
            .withSubject("physics").withCategory("t").build();
    public static final Client GERRARD = new ClientBuilder().withName("GERRARD").withPhone("8321242")
            .withEmail("liverpool@example.com").withAddress("Anfield").withLocation("west").withGrade("u4")
            .withSubject("chemistry").withCategory("t").build();

    // Manually added
    public static final Client HOON = new ClientBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withAddress("little india").withLocation("north").withGrade("s1")
            .withSubject("chemistry").withCategory("s").build();
    public static final Client IDA = new ClientBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withAddress("chicago ave").withLocation("north").withGrade("k2")
            .withSubject("math").withCategory("s").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Client AMY = new ClientBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND)
            .withLocation(VALID_LOCATION_AMY).withGrade(VALID_GRADE_AMY).withSubject(VALID_SUBJECT_AMY)
            .withCategory(VALID_CATEGORY_AMY).build();
    public static final Client BOB = new ClientBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .withLocation(VALID_LOCATION_BOB).withGrade(VALID_GRADE_BOB).withSubject(VALID_SUBJECT_BOB)
            .withCategory(VALID_CATEGORY_BOB).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private UnmatchedClients() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical clients.
     */
    public static AddressBook getUnmatchedAddressBook() {
        AddressBook ab = new AddressBook();
        for (Client client : getUnmachedClients()) {
            try {
                ab.addClient(client);
            } catch (DuplicatePersonException e) {
                throw new AssertionError("not possible");
            }
        }
        return ab;
    }

    public static List<Client> getUnmachedClients() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE,
                ANDREW, EDISON, FLOWER, GERRARD));
    }
}

```
