# mattbuot
###### \java\seedu\address\commons\core\MailerTest.java
``` java
public class MailerTest {

    @Test
    public void sendCustomersEmail() {
        boolean test = Mailer.emailCustomers(Arrays.asList(new Person(
                new Name("John"),
                new Phone("98765432"),
                new Email("pigeonscs2103@gmail.com"),
                new Address("NUS"),
                new DelivDate("2018-03-24"),
                Collections.emptySet())));
        assertTrue(test);
    }

    @Test
    public void sendDriverEmail() {
        boolean test = Mailer.emailDriver(Arrays.asList("NUS"), "45.0 min", "2018-05-04");
        assertTrue(test);
    }

    @Test
    public void sendDriverEmailWithNoAddresses() {
        boolean test = Mailer.emailDriver(Collections.emptyList(), "45.0 min", "2018-05-24");
        assertFalse(test);
    }
}
```
###### \java\seedu\address\logic\AutocompleterTest.java
``` java
public class AutocompleterTest {
    private Model model;
    private Autocompleter autocompleter;

    @Before
    public void setUp() {
        model = new ModelManager();
        autocompleter = new Autocompleter(model.getAddressBook().getPersonList());
    }

    @Test
    public void completeCommand() {
        if (SelectCommand.COMMAND_WORD.length() > 2) {
            String commandPrefix = SelectCommand.COMMAND_WORD.substring(0, SelectCommand.COMMAND_WORD.length() - 1);
            assertEquals(SelectCommand.COMMAND_WORD.substring(SelectCommand.COMMAND_WORD.length() - 1),
                    autocompleter.autocomplete(commandPrefix));
        }
    }

    @Test
    public void completeField() throws DuplicatePersonException {

        model.addPerson(
                new Person(
                        new Name("John"),
                        new Phone("98765432"),
                        new Email("johndoe@test.com"),
                        new Address("NUS"),
                        new DelivDate("2018-03-24"),
                        Collections.emptySet()));

        autocompleter = new Autocompleter(model.getAddressBook().getPersonList());

        String query = "find John";
        String prefix = query.substring(0, query.length() - 3);
        assertEquals(query.substring(query.length() - 3), autocompleter.autocomplete(prefix));
    }

    @Test
    public void completeOptions() throws DuplicatePersonException {
        model.addPerson(
                new Person(
                        new Name("John"),
                        new Phone("98765432"),
                        new Email("johndoe@test.com"),
                        new Address("NUS"),
                        new DelivDate("2018-03-24"),
                        Collections.emptySet()));

        autocompleter = new Autocompleter(model.getAddressBook().getPersonList());

        String query = "add n/";
        query += autocompleter.autocomplete(query);
        query += " p/";
        query += autocompleter.autocomplete(query);
        query += " e/";
        query += autocompleter.autocomplete(query);
        query += " a/";
        query += autocompleter.autocomplete(query);
        query += " d/";
        query += autocompleter.autocomplete(query);
        assertEquals("add n/John p/98765432 e/johndoe@test.com a/NUS d/2018-03-24", query);

    }
}
```
###### \java\seedu\address\logic\commands\EmailCommandTest.java
``` java
public class EmailCommandTest {

    private EmailCommand command;
    private Model model;

    @Before
    public void setup() {
        model = new ModelManager();
        command = new EmailCommand();
        command.setData(model, null, null);
    }

    @Test
    public void executeWithEmptyModel() {

        assertCommandFailure(command, "Empty filtered list!");
    }

    @Test
    public void executeWithInvalidAddress()
        throws DuplicatePersonException {

        model.addPerson(new Person(
                new Name("Alice"),
                new Phone("98765432"),
                new Email("alice@gmail.com"),
                new Address("apskefzjozdked"),
                new DelivDate("2018-03-24"),
                Collections.emptySet()));


        command.setData(model, null, null);
        assertCommandFailure(command, EmailCommand.MESSAGE_ERROR);
    }

    @Test
    public void executeWithoutFilter()
            throws DuplicatePersonException {

        model.addPerson(new Person(
                new Name("Alice"),
                new Phone("98765432"),
                new Email("alice@gmail.com"),
                new Address("NUS"),
                new DelivDate("2018-03-24"),
                Collections.emptySet()));
        model.addPerson(new Person(
                new Name("Bob"),
                new Phone("98765432"),
                new Email("bob@gmail.com"),
                new Address("NTU"),
                new DelivDate("2018-03-25"),
                Collections.emptySet()));
        command.setData(model, null, null);
        assertCommandFailure(command, "The list is not filtered!");
    }

    @Test
    public void execute()
            throws DuplicatePersonException, CommandException {

        model.addPerson(new Person(
                new Name("Alice"),
                new Phone("98765432"),
                new Email("alice@gmail.com"),
                new Address("NUS"),
                new DelivDate("2018-03-24"),
                Collections.emptySet()));
        model.addPerson(new Person(
                new Name("Bob"),
                new Phone("98765432"),
                new Email("bob@gmail.com"),
                new Address("NTU"),
                new DelivDate("2018-03-24"),
                Collections.emptySet()));

        command.setData(model, null, null);
        assertCommandResult(command, EmailCommand.MESSAGE_SUCCESS);
    }

```
