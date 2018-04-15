# melvintzw
###### \java\seedu\address\logic\commands\FindCommandTest.java
``` java
    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {

        //test FindCommand object that uses the PersonContainsKeyWordsPredicate
        String arguments = "carl daniel elle";
        String[] splitArguments = arguments.split("\\s+");
        List<String> list = Arrays.asList(splitArguments);
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        FindCommand command = prepareCommand(new PersonContainsKeywordsPredicate(list));
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, DANIEL, ELLE));

        //test FindCommand object that uses the NameContainsKeyWordsPredicate
        arguments = "carl daniel elle";
        splitArguments = arguments.split("\\s+");
        list = Arrays.asList(splitArguments);
        expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        command = prepareCommand(new NameContainsKeywordsPredicate(list));
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, DANIEL, ELLE));

        //test Command object that uses the PhoneContainsKeyWordsPredicate
        arguments = "95352563 87652533 9482224";
        splitArguments = arguments.split("\\s+");
        list = Arrays.asList(splitArguments);
        expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        command = prepareCommand(new PhoneContainsKeywordsPredicate(list));
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, DANIEL, ELLE));

        //test FindCommand object that uses the EmailContainsKeyWordsPredicate
        arguments = "heinz@example.com cornelia@example.com werner@example.com";
        splitArguments = arguments.split("\\s+");
        list = Arrays.asList(splitArguments);
        expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        command = prepareCommand(new EmailContainsKeywordsPredicate(list));
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, DANIEL, ELLE));

        //test FindCommand object that uses the AddressContainsKeyWordsPredicate
        arguments = "wall 10th michegan";
        splitArguments = arguments.split("\\s+");
        list = Arrays.asList(splitArguments);
        expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        command = prepareCommand(new AddressContainsKeywordsPredicate(list));
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, DANIEL, ELLE));
    }
```
###### \java\seedu\address\logic\parser\AssignCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AssignCommand;

public class AssignCommandParserTest {

    private AssignCommandParser parser = new AssignCommandParser();

    @Test
    public void parse_validArgsAndOneCustomer_returnsAssignCommand() {
        assertParseSuccess(parser, "1 c: 2", new AssignCommand(Index.fromOneBased(1), Index.fromOneBased(2)));
    }

    @Test
    public void parse_validArgsAndTwoCustomers_returnsAssignCommand() {
        assertParseSuccess(parser, "1 c: 2 3", new AssignCommand(Index.fromOneBased(1), Index.fromOneBased(2),
                Index.fromOneBased(3)));
    }

    @Test
    public void parse_alphabet_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AssignCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_negativeIndex_throwsParseException() {
        assertParseFailure(parser, "-1 c: 2 3", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AssignCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\logic\parser\FindCommandParserTest.java
``` java
    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new PersonContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedFindCommand);

        //-all specifier
        expectedFindCommand = new FindCommand(new PersonContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "-all Alice Bob", expectedFindCommand);

        //-n specifier
        expectedFindCommand = new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "-n Alice Bob", expectedFindCommand);

        //-p specifier
        expectedFindCommand = new FindCommand(new PhoneContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "-p Alice Bob", expectedFindCommand);

        //-a specifier
        expectedFindCommand = new FindCommand(new AddressContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "-a Alice Bob", expectedFindCommand);

        //-t specifier
        expectedFindCommand = new FindCommand(new TagsContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "-t Alice Bob", expectedFindCommand);

        //-e specifier
        expectedFindCommand = new FindCommand(new EmailContainsKeywordsPredicate(Arrays.asList("alice@example.com",
                "bob@example.com")));
        assertParseSuccess(parser, "-e alice@example.com bob@example.com", expectedFindCommand);
    }
```
###### \java\seedu\address\model\person\PersonContainsKeywordsPredicateTest.java
``` java
public class PersonContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        PersonContainsKeywordsPredicate firstPredicate =
                new PersonContainsKeywordsPredicate(firstPredicateKeywordList);
        PersonContainsKeywordsPredicate secondPredicate =
                new PersonContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PersonContainsKeywordsPredicate firstPredicateCopy =
                new PersonContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_personContainsKeywords_returnsTrue() {
        // One keyword
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
        assertTrue(predicate.test(new PersonBuilder().withAddress("Alice Street").build()));
        assertTrue(predicate.test(new PersonBuilder().withTags("Alice", "Charlie").build()));

        // Multiple keywords
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
        assertTrue(predicate.test(new PersonBuilder().withAddress("Alice Bob Street").build()));
        assertTrue(predicate.test(new PersonBuilder().withTags("Alice", "Bob").build()));

        // Only one matching keyword
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Carol").build()));
        assertTrue(predicate.test(new PersonBuilder().withAddress("Carol Street").build()));
        assertTrue(predicate.test(new PersonBuilder().withTags("Alice", "Bob").build()));

        // Mixed-case keywords
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
        assertTrue(predicate.test(new PersonBuilder().withAddress("Alice Street").build()));
        assertTrue(predicate.test(new PersonBuilder().withTags("Alice", "Charlie").build()));
    }

    @Test
    public void test_personDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));


    }
}
```
###### \java\seedu\address\testutil\PersonBuilder.java
``` java
    //Customer fields
    private MoneyBorrowed moneyBorrowed;
    private Date oweStartDate;
    private Date oweDueDate;
    private StandardInterest standardInterest;
    private LateInterest lateInterest;
    private Person runner;

    //Runner fields:
    private List<Person> customers;

    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        tags = SampleDataUtil.getTagSet(DEFAULT_TAGS);

        //Customer fields
        moneyBorrowed = new MoneyBorrowed();
        oweStartDate = new Date(0);
        oweDueDate = new Date(0);
        standardInterest = new StandardInterest();
        lateInterest = new LateInterest();
        runner = new Runner();

        //Runner fields:
        customers = new ArrayList<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        address = personToCopy.getAddress();
        tags = new HashSet<>(personToCopy.getTags());

        if (personToCopy instanceof Customer) {
            moneyBorrowed = ((Customer) personToCopy).getMoneyBorrowed();
            oweStartDate = ((Customer) personToCopy).getOweStartDate();
            oweDueDate = ((Customer) personToCopy).getOweDueDate();
            standardInterest = ((Customer) personToCopy).getStandardInterest();
            lateInterest = ((Customer) personToCopy).getLateInterest();
            runner = ((Customer) personToCopy).getRunner();
        }

        if (personToCopy instanceof Runner) {
            customers = new ArrayList<>();
        }
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code MoneyBorrowed} of the {@code Person} that we are building.
     */
    public PersonBuilder withMoneyBorrowed(MoneyBorrowed moneyBorrowed) {
        this.moneyBorrowed = moneyBorrowed;
        return this;
    }

    /**
     * Sets the {@code OweStartDate} of the {@code Person} that we are building.
     */
    public PersonBuilder withOweStartDate(Date date) {
        this.oweStartDate = date;
        return this;
    }

    /**
     * Sets the {@code OweDueDate} of the {@code Person} that we are building.
     */
    public PersonBuilder withOweDueDate(Date date) {
        this.oweDueDate = date;
        return this;
    }

    /**
     * Sets the {@code StandardInterest} of the {@code Person} that we are building.
     */
    public PersonBuilder withStandardInterest(StandardInterest interest) {
        this.standardInterest = interest;
        return this;
    }

    /**
     * Sets the {@code LateInterest} of the {@code Person} that we are building.
     */
    public PersonBuilder withLateInterest(LateInterest interest) {
        this.lateInterest = interest;
        return this;
    }

    /**
     * Sets the {@code Runner} of the {@code Person} that we are building.
     */
    public PersonBuilder withRunner(Runner runner) {
        this.runner = runner;
        return this;
    }

    /**
     * Sets the {@code customers} of the {@code Person} that we are building.
     */
    public PersonBuilder withCustomers(List<Person> customers) {
        this.customers = customers;
        return this;
    }

    /**
     * Constructs a Person
     */
    public Person build() {
        return new Person(name, phone, email, address, tags);
    }

    /**
     * Constructs a Customer
     */
    public Customer buildCustomer() {
        return new Customer(name, phone, email, address, tags, moneyBorrowed,
                oweStartDate, oweDueDate, standardInterest, lateInterest, runner);
    }

    public Runner buildRunner() {
        return new Runner(name, phone, email, address, tags, customers);
    }

}

```
###### \java\systemtests\AssignCommandSystemTest.java
``` java
public class AssignCommandSystemTest extends AddressBookSystemTest {
    private static final String MESSAGE_INVALID_ASSIGN_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AssignCommand.MESSAGE_USAGE);


    /* ----------------- Performing assign operation while an unfiltered list is being shown -------------------- */
    /* Case: assign first person in the list to sixth person in the list, command with leading spaces and
        trailing spaces -> sixth person (customer) assigned to first person (runner)*/
    @Test
    public void execute_assignOneCustomerToOneRunnerWithExtraWhiteSpaces_success() throws Exception {
        Model expectedModel = getModel(); //data is from TypicalPersons.java
        String command = "     " + AssignCommand.COMMAND_WORD + "      " + INDEX_SIXTH_PERSON.getOneBased() + " "
                + PREFIX_CUSTOMERS + " " + INDEX_FIRST_PERSON.getOneBased();

        //get runner
        Person runner = expectedModel.getFilteredPersonList().get(INDEX_SIXTH_PERSON.getZeroBased());
        Index[] customerIndexes = {INDEX_FIRST_PERSON};
        //get customers
        List<Person> customers = new ArrayList<>();
        for (Index index : customerIndexes) {
            Person customer = expectedModel.getFilteredPersonList().get(index.getZeroBased());
            customers.add(customer);
        }

        //build editedRunner (assigned with customers)
        Person editedRunner = new PersonBuilder(runner).withCustomers(customers).buildRunner();

        //update expected model
        expectedModel.updatePerson(runner, editedRunner);

        //build editedCustomers (assigned with runner)
        List<Person> editedCustomers = new ArrayList<>();
        for (Person c : customers) {
            Person editedCustomer = new PersonBuilder(c).withRunner((Runner) runner).buildCustomer();
            editedCustomers.add(editedCustomer);
            expectedModel.updatePerson(c, editedCustomer);
        }

        String expectedResultMessage = String.format(MESSAGE_ASSIGN_PERSON_SUCCESS, editedRunner);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }
```
###### \java\systemtests\FindCommandSystemTest.java
``` java
public class FindCommandSystemTest extends AddressBookSystemTest {
    @Test
    public void find() {
        /* Case: find multiple persons in address book, command with leading spaces and trailing spaces
         * -> 2 persons found
         */
        String command = "   " + FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_MEIER + "   ";
        Model expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, BENSON, DANIEL); // first names of Benson and Daniel are "Meier"
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous find command where person list is displaying the persons we are finding
         * -> 2 persons found
         */
        command = FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_MEIER;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person where person list is not displaying the person we are finding -> 1 person found */
        command = FindCommand.COMMAND_WORD + " Carl";
        ModelHelper.setFilteredList(expectedModel, CARL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in address book, 2 keywords -> 2 persons found */
        command = FindCommand.COMMAND_WORD + " Benson Daniel";
        ModelHelper.setFilteredList(expectedModel, BENSON, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in address book, 2 keywords in reversed order -> 2 persons found */
        command = FindCommand.COMMAND_WORD + " Daniel Benson";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in address book, 2 keywords with 1 repeat -> 2 persons found */
        command = FindCommand.COMMAND_WORD + " Daniel Benson Daniel";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in address book, 2 matching keywords and 1 non-matching keyword
         * -> 2 persons found
         */
        command = FindCommand.COMMAND_WORD + " Daniel Benson NonMatchingKeyWord";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        //TODO: implement test cases for specifiers: -n -p -e -a -t
        //ModelHelper.setFilteredList()
        //refer to TypicalPersons.java and test.data.sandbox.sampleData.xml for fields to check

        //-------------INVALID CASES--------------------------------------------------------------------------------->

        /* Case: undo previous find command -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo previous find command -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: find same persons in address book after deleting 1 of them -> 1 person found */
        executeCommand(DeleteCommand.COMMAND_WORD + " 1");
        assertFalse(getModel().getAddressBook().getPersonList().contains(BENSON));
        command = FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_MEIER;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person in address book, keyword is same as name but of different case -> 1 person found */
        command = FindCommand.COMMAND_WORD + " MeIeR";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person in address book, keyword is substring of name -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " Mei";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person in address book, name is substring of keyword -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " Meiers";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person not in address book -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " Mark";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find phone number of person in address book -> 1 persons found */
        command = FindCommand.COMMAND_WORD + " " + DANIEL.getPhone().value;
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find address of person in address book -> 3 persons found */
        command = FindCommand.COMMAND_WORD + " " + DANIEL.getAddress().value;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, CARL, DANIEL, GEORGE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find email of person in address book -> 1 persons found */
        command = FindCommand.COMMAND_WORD + " " + DANIEL.getEmail().value;
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find tags of person in address book -> 6 persons found */
        List<Tag> tags = new ArrayList<>(DANIEL.getTags());
        command = FindCommand.COMMAND_WORD + " " + tags.get(0).tagName;
        ModelHelper.setFilteredList(expectedModel, ALICE, CARL, DANIEL, ELLE, FIONA, GEORGE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find while a person is selected -> selected card deselected */
        showAllPersons();
        selectPerson(Index.fromOneBased(1));
        assertFalse(getPersonListPanel().getHandleToSelectedCard().getName().equals(DANIEL.getName().fullName));
        command = FindCommand.COMMAND_WORD + " Daniel";
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardDeselected();

        /* Case: find person in empty address book -> 0 persons found */
        deleteAllPersons();
        command = FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_MEIER;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: mixed case command word -> rejected */
        command = "FiNd Meier";
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);
    }

```
