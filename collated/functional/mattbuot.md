# mattbuot
###### \java\seedu\address\commons\core\Mailer.java
``` java
/**
 * Send personalized emails to drivers and customers
 */
public class Mailer {

    private static String pigeonsMail = "pigeonscs2103@gmail.com";
    private static String password = "Pigeons2103";
    private static String host = "smtp.gmail.com";
    //private static String defaultRecipient = "delepine.matthieu@gmail.com";
    //private static String defaultRecipient = "matthieu2301@hotmail.fr";
    private static String defaultRecipient = pigeonsMail;

    /**
     * Send an email to the
     * @param recipients
     */
    public static boolean emailCustomers(List<Person> recipients) {

        Session session = getSession();

        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(pigeonsMail));

            for (Person p: recipients) {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(p.getEmail().toString()));

                String subject = "Your delivery with Pigeons";

                String body = "Dear "
                        + p.getName().toString() + ", your delivery is arriving on "
                        + p.getDate().toString()
                        + " please be at your place some pigeons may visit you :)"
                        + "<img src=\"https://i.imgur.com/Eg6CDss.jpg\" "
                        + "alt=\"Pigeons Logo\" style=\"display: block\"><br>";

                message.setSubject(subject);
                message.setContent(body, "text/html");
            }
            Transport transport = session.getTransport("smtp");
            transport.connect(host, pigeonsMail, password);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();

        } catch (MessagingException ae) {
            return false;
        }
        return true;
    }

    /**
     *
     * Sends an email to the driver with a recap of the itinerary including the following information
     * @param addresses
     * @param duration
     * @param date
     * @return false if an error occured, true otherwise
     */
    public static boolean emailDriver(List<String> addresses, String duration, String date) {
        if (addresses.size() == 0) {
            return false;
        }
        Session session = getSession();

        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(pigeonsMail));
            message.setSubject("Your itinerary on " + date);

            String body = "<h2>The estimated duration for this itinerary is : "
                    + duration
                    + " ("
                    + addresses.size()
                    + " deliveries)"
                    + "</h2>"
                    + "<ol>";

            for (String address: addresses) {

                body += "<li>" + address + "</li>";
            }

            body += "</ol>"
                    + "<img src=\"https://i.imgur.com/Eg6CDss.jpg\" "
                    + "alt=\"Pigeons Logo\" style=\"display: block\">";

            message.setContent(body, "text/html");
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(pigeonsMail));

            Transport transport = session.getTransport("smtp");
            transport.connect(host, pigeonsMail, password);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();

        } catch (MessagingException ae) {
            return false;
        }
        return true;
    }

    private static Session getSession() {
        Properties props = System.getProperties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", pigeonsMail);
        props.put("mail.smtp.password", password);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.trust", host);

        return Session.getDefaultInstance(props);
    }
}
```
###### \java\seedu\address\logic\Autocompleter.java
``` java
/**
 * Implements methods to autocomplete commands and fields in a user query
 */
public class Autocompleter {

    private final Set<String> commandsList = new HashSet<>();
    private Set<String> names;
    private Set<String> phones;
    private Set<String> emails;
    private Set<String> addresses;
    private Set<String> dates;

    public Autocompleter(List<Person> data) {
        updateCommands();
        updateFields(data);
    }

    /**
     * @param input the user query
     * @return the longest possible prefix to append to the last word of the query
     */
    public String autocomplete(String input) {

        String[] words = input.trim().split(" ");
        //List<String>
        Set<String> possibilities = new HashSet<>();

        //trying to complete a command
        if (words.length == 1 && words[0].length() > 0) {
            possibilities = generatePossibleSuffixes(words[0], commandsList);
        } else if (words.length > 1) { //trying to complete a field

            int lastFieldIndex = input.lastIndexOf('/');
            //testing whether the field is an option starting by 'x/'
            if (lastFieldIndex > 0 && input.substring(lastFieldIndex).length() > 0) {
                String field = input.substring(lastFieldIndex + 1);
                Set<String> fieldsList;

                switch (input.charAt(lastFieldIndex - 1)) {
                case 'n':
                    fieldsList = names;
                    break;

                case 'p':
                    fieldsList = phones;
                    break;

                case 'e':
                    fieldsList = emails;
                    break;

                case 'a':
                    fieldsList = addresses;
                    break;

                case 'd':
                    fieldsList = dates;
                    break;

                default:
                    fieldsList = new HashSet<>();
                    break;
                }

                possibilities = generatePossibleSuffixes(field, fieldsList);
            } else {
                //trying to match the last word to a name of the address book
                possibilities = generatePossibleSuffixes(words[words.length - 1], generateNames());
            }
        }
        return getLongestCommonPrefix(possibilities);
    }

    private String getLongestCommonPrefix(Set<String> possibilities) {
        String longestCommonPrefix = "";

        if (possibilities.size() > 0) {
            String p = (String) possibilities.toArray()[0];

            for (int i = 0; i < p.length(); i++) {
                boolean isPrefixOkay = true;

                for (String possibility : possibilities) {
                    if (!possibility.startsWith(longestCommonPrefix + p.charAt(i))) {
                        isPrefixOkay = false;
                    }
                }
                if (!isPrefixOkay) {
                    break;
                }

                longestCommonPrefix += p.charAt(i);
            }
        }
        return longestCommonPrefix;
    }

    /**
     *
     * @param lastWord the last word of the query we're trying to complete
     * @param possibleWords the set of all possible words according to the query type
     * @return the set of all possible suffixes to the last word
     */
    private Set<String> generatePossibleSuffixes(String lastWord, Set<String> possibleWords) {
        Set<String> suffixes = new HashSet<>();

        for (String word : possibleWords) {
            if (word.startsWith(lastWord)) {
                //we only append the end of the command
                suffixes.add(word.substring(lastWord.length()));
            }
        }
        return suffixes;
    }

    /**
     * Update the sets of fields
     */
    public void updateFields(List<Person> data) {
        names = data.stream()
                .map(person -> person.getName().toString()).collect(Collectors.toSet());
        phones = data.stream()
                .map(person -> person.getPhone().toString()).collect(Collectors.toSet());
        emails = data.stream()
                .map(person -> person.getEmail().toString()).collect(Collectors.toSet());
        addresses = data.stream()
                .map(person -> person.getAddress().toString()).collect(Collectors.toSet());
        dates = data.stream()
                .map(person -> person.getDate().toString()).collect(Collectors.toSet());
    }

    /**
     * Updates the set of commands
     */
    private void updateCommands() {
        commandsList.addAll(Arrays.asList(AddCommand.COMMAND_WORD,
                ClearCommand.COMMAND_WORD,
                DeleteCommand.COMMAND_WORD,
                EditCommand.COMMAND_WORD,
                EmailCommand.COMMAND_WORD,
                ExitCommand.COMMAND_WORD,
                FindCommand.COMMAND_WORD,
                FilterCommand.COMMAND_WORD,
                HelpCommand.COMMAND_WORD,
                HistoryCommand.COMMAND_WORD,
                ListCommand.COMMAND_WORD,
                RedoCommand.COMMAND_WORD,
                SelectCommand.COMMAND_WORD,
                UndoCommand.COMMAND_WORD));
    }

    /**
     *
     * @return the set of all the different words included in the set of names
     */
    private Set<String> generateNames() {
        Set<String> names = new HashSet<>();

        for (String fullName : this.names) {
            String[] words = fullName.split(" ");
            names.addAll(Arrays.asList(words));
        }
        return names;
    }
}
```
###### \java\seedu\address\logic\commands\EmailCommand.java
``` java
/**
 * Send an email to the persons listed
 */
public class EmailCommand extends Command {

    public static final String COMMAND_WORD = "email";

    public static final String MESSAGE_USAGE =
            COMMAND_WORD + ": Send an email to list of persons and the optimized itinerary to the driver.";

    public static final String MESSAGE_SUCCESS = "Emails sent successfully.";

    public static final String MESSAGE_ERROR = "An error occurred, emails not sent.";

    @Override
    public CommandResult execute() throws CommandException {

        //we check that the customers listed have their delivery on the same date
        String delivDate = getDate().toString();

        RouteOptimization route = new RouteOptimization();
        List<String> optimizedRoute;

        optimizedRoute = route.getAddresses(model);
        String duration = FilterCommand.getStringDuration();

        boolean result =
                Mailer.emailDriver(optimizedRoute, duration, delivDate)
                        &&  Mailer.emailCustomers(model.getFilteredPersonList());

        String message = result ? MESSAGE_SUCCESS : MESSAGE_ERROR;

        return new CommandResult(message);
    }

    private DelivDate getDate() throws CommandException {
        ObservableList<Person> filteredPersonList = model.getFilteredPersonList();

        if (filteredPersonList.size() > 0) {
            DelivDate date = filteredPersonList.get(0).getDate();
            for (Person p : filteredPersonList) {
                if (!date.equals(p.getDate())) {
                    throw new CommandException("The list is not filtered!");
                }
            }
            return date;
        }
        throw new CommandException("Empty filtered list!");
    }
}
```
