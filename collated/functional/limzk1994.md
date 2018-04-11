# limzk1994
###### /java/seedu/address/commons/util/EncryptionUtil.java
``` java
public class EncryptionUtil {
    /**
     *The standard version of the JRE/JDK are under export restrictions.
     *That also includes that some cryptographic algorithms are not allowed to be shipped in the standard version.
     *Replace files in library with Java Cryptography Extension (JCE) Unlimited Strength Jurisdiction Policy Files 8
     */

    private static final String password = "CS210321CS210321";
    private static final Logger logger = LogsCenter.getLogger(EncryptionUtil.class);

    /**
     * Encrypts XML file
     *
     * @param file path of the file to be encrypted
     * @throws IOException if file could not be found
     */
    public static void encrypt(File file) throws IOException {

        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            SecretKey secretKey = generateKey();
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            fileToBytes(cipher, file);
        } catch (GeneralSecurityException gse) {
            logger.severe("Cipher or Padding might not be supported " + gse.getMessage());
        } catch (UnsupportedEncodingException use) {
            logger.info("Encoding Unsupported " + use.getMessage());
        }

    }

    /**
     * Decrypts XML file
     *
     * @param file path of the file to be decrypted
     * @throws IOException if file could not be found
     */
    public static void decrypt(File file) throws IOException {

        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            SecretKey secretKey = generateKey();
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            fileToBytes(cipher, file);
        } catch (GeneralSecurityException gse) {
            logger.severe("Cipher or Padding might not be supported " + gse.getMessage());
        } catch (UnsupportedEncodingException use) {
            logger.info("Encoding Unsupported " + use.getMessage());
        }
    }

    /**
     * Processes the given file using the given cipher
     *
     * @param cipher cipher used for encryption or decryption
     * @param file path of the file to be encrypted or decrypted
     * @throws IOException if file could not be found
     */

    private static void fileToBytes(Cipher cipher, File file) throws IOException {

        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            byte[] readBytes = new byte[(int) file.length()];
            fileInputStream.read(readBytes);

            byte[] writeBytes = cipher.doFinal(readBytes);
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(writeBytes);

        } catch (BadPaddingException be) {
            logger.info("File might not decoded/encoded properly due to bad padding " + be.getMessage());
        } catch (IllegalBlockSizeException ibe) {
            logger.info("Input length size must be in multiple of 16  " + ibe.getMessage());
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException ioe) {
                logger.info("File streams could not be closed  " + ioe.getMessage());
            }
        }
    }

    /**
     * Method to generate a SecretKey using the password provided
     *
     * @return SecretKey generated using AES encryption
     */
    public static SecretKey generateKey() {

        SecretKeySpec secretKeySpec = null;
        try {
            MessageDigest digester = MessageDigest.getInstance("SHA-256");
            digester.update(password.getBytes("UTF-8"));
            byte[] key = digester.digest();
            secretKeySpec = new SecretKeySpec(key , 0 , 16 ,  "AES");
        } catch (NoSuchAlgorithmException nae) {
            logger.info("Algorithm Unsupported " + nae.getMessage());
        } catch (UnsupportedEncodingException use) {
            logger.info("Encoding Unsupported " + use.getMessage());
        }

        return secretKeySpec;
    }
}
```
###### /java/seedu/address/logic/parser/GroupCommandParser.java
``` java
public class GroupCommandParser implements Parser<GroupCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the GroupCommand
     * and returns an GroupCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public GroupCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, GroupCommand.MESSAGE_USAGE));
        }

        String[]groupKeywords = trimmedArgs.split("\\s+");

        return new GroupCommand(new PersonContainsGroupsPredicate(Arrays.asList(groupKeywords)));
    }

}
```
###### /java/seedu/address/logic/parser/PasswordCommandParser.java
``` java

/**
 * Parses the inputs and create a PasswordCommand object
 */
public class PasswordCommandParser implements Parser<PasswordCommand> {


    @Override
    public PasswordCommand parse(String args) throws ParseException {

        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args, PREFIX_SET, PREFIX_CHANGE, PREFIX_REMOVE);

        if (arePrefixesPresent(argumentMultimap, PREFIX_SET)) {
            return new PasswordCommand(new PasswordCommand.setPassword(argumentMultimap.getValue(PREFIX_SET).get()));
        } else if (arePrefixesPresent(argumentMultimap, PREFIX_REMOVE)) {
            return new PasswordCommand(new PasswordCommand.clearPassword(
                    argumentMultimap.getValue(PREFIX_REMOVE).get()));
        } else if (arePrefixesPresent(argumentMultimap, PREFIX_CHANGE)) {
            final String newPassword = argumentMultimap.getValue(PREFIX_CHANGE).get();
            requireNonNull(newPassword);
            if (newPassword.length() == 0) {
                throw new ParseException("Password cannot be blank!");
            }
            return new PasswordCommand(new PasswordCommand.changePassword(newPassword));

        }

        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, PasswordCommand.MESSAGE_USAGE));
    }
    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
```
###### /java/seedu/address/logic/commands/SortCommand.java
``` java
public class SortCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_SUCCESS = "Sorted all clients!";

    public SortCommand(){
    }
    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        ObservableList<Person> shownList = model.getFilteredPersonList();
        if (shownList.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_PERSON_LIST_EMPTY);
        }
        model.sortFilteredPersonList(shownList);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### /java/seedu/address/logic/commands/PasswordCommand.java
``` java

/**
 * Contain methods to modify the password
 */
public class PasswordCommand extends Command {

    public static final String COMMAND_WORD = "password";
    public static final String COMMAND_WORD_ALIAS = "pw";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Set, change or remove password\n"
            + "Set Password Parameters:" + COMMAND_WORD + " set/yourchosenpassword\n"
            + "Change Password Parameters:" + COMMAND_WORD + " change/yournewpassword\n"
            + "Remove Password Parameters: " + COMMAND_WORD + " remove/youroldpassword\n";

    public static final String MESSAGE_SUCCESS = "Password set!";
    public static final String MESSAGE_PASSWORD_CHANGE = "Password successfully changed!";
    public static final String MESSAGE_PASSWORD_EXISTS = "Password already exists!";
    public static final String MESSAGE_PASSWORD_REMOVE = "Password removed!";
    public static final String MESSAGE_NO_PASSWORD_EXISTS = "No password!";
    public static final String MESSAGE_WRONG_PASSWORD = "Wrong password!";

    private final PasswordMode mode;

    /**
     * Creates an PasswordCommand
     */
    public PasswordCommand(PasswordMode mode) {
        this.mode = mode;
    }

    @Override
    public CommandResult execute() throws CommandException {
        try {
            return mode.execute();
        } catch (IOException ioe) {
            throw new CommandException("Password File not found");
        }

    }

    /**
     * Set password if it does not exists
     */
    public static class setPassword extends PasswordMode {
        public setPassword(String password) {
            super(password);
        }

        @Override
        public CommandResult execute() throws IOException, CommandException {
            if (passExists()) {
                throw new CommandException(MESSAGE_PASSWORD_EXISTS);
            } else {
                PasswordManger.savePassword(getPass());
                return new CommandResult(MESSAGE_SUCCESS);
            }
        }
    }

    /**
     * Removes password if it exists
     */
    public static class clearPassword extends PasswordMode {
        public clearPassword(String password) {
            super(password);
        }

        @Override
        public CommandResult execute() throws IOException, CommandException {
            if (passExists()) {
                try {
                    PasswordManger.removePassword(getPass());
                } catch (WrongPasswordException e) {
                    throw new CommandException(MESSAGE_WRONG_PASSWORD);
                }
                return new CommandResult(MESSAGE_PASSWORD_REMOVE);
            } else {
                throw new CommandException(MESSAGE_NO_PASSWORD_EXISTS);
            }
        }
    }

    /**
     * Changes password if it exists
     */
    public static class changePassword extends PasswordMode {

        private String newPass;
        public changePassword(String newPassword) {
            super(newPassword);
            newPass = newPassword;
        }

        @Override
        public CommandResult execute() throws IOException {
            if (passExists()) {
                PasswordManger.savePassword(newPass);
                return new CommandResult(MESSAGE_PASSWORD_CHANGE);
            } else {
                return new CommandResult(MESSAGE_NO_PASSWORD_EXISTS);
            }
        }
    }
}
```
###### /java/seedu/address/logic/commands/GroupCommand.java
``` java
public class GroupCommand extends Command {

    public static final String COMMAND_WORD = "group";
    public static final String COMMAND_WORD_ALIAS = "g";
    public static final String MESSAGE_SUCCESS = "Groups listed!";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Group all persons whose group attribute is"
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " Priority";

    private final PersonContainsGroupsPredicate predicate;

    public GroupCommand(PersonContainsGroupsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GroupCommand // instanceof handles nulls
                && this.predicate.equals(((GroupCommand) other).predicate)); // state check
    }
}
```
###### /java/seedu/address/storage/PasswordManger.java
``` java

/**
 * Accesses the password file stored on the hard disk
 */
public class PasswordManger {

    /**
     *
     * @param password user's password
     * @throws IOException if file could not be found or created
     */
    public static void savePassword(String password) throws IOException {
        requireNonNull(password);
        File file = new File(getFilePath());
        FileUtil.createIfMissing(file);
        FileUtil.writeToFile(file, password);
        EncryptionUtil.encrypt(file);
    }

    /**
     * Check whether to unlock the program
     * @param password
     * @return
     * @throws IOException
     */
    public static boolean verifyPassword(String password) throws IOException {
        boolean unlock = passwordCheck(password);
        if (unlock) {
            EventsCenter.getInstance().post(new PasswordAcceptedEvent());
        }
        return unlock;
    }

    /**
     * Removes existing password if user input the correct password
     * @param password oldpassword to be checked
     * @throws IOException if file does not exists
     */
    public static void removePassword(String password) throws IOException, WrongPasswordException {
        File file = new File(getFilePath());
        if (passwordCheck(password) && FileUtil.isFileExists(file)) {
            file.delete();
        } else {
            throw new WrongPasswordException();
        }
    }
    /**
     * Check if password is correct
     * @param password to be checked against records
     * @return true if password exists, vice-versa
     */
    public static boolean passwordCheck(String password) throws IOException {
        String storedPassword = getPassword();
        return storedPassword.equals(password);
    }
    /**
     * Check if the password exists
     * @return true if password exists, vice-versa
     */
    public static boolean passwordExists() {
        File file = new File(getFilePath());
        return FileUtil.isFileExists(file);
    }
    /**
     * Method to get the password
     * @return password
     * @throws IOException if file could not be found
     */
    public static String getPassword() throws IOException {
        File file = new File(getFilePath());
        EncryptionUtil.decrypt(file);
        String password = FileUtil.readFromFile(file);
        EncryptionUtil.encrypt(file);
        return password;
    }

    /**
     * Method to get the file path of password
     * @return file path
     */
    public static String getFilePath() {
        UserPrefs userPrefs = new UserPrefs();
        String filePath = userPrefs.getPasswordFilePath();

        return filePath;
    }
}
```
###### /java/seedu/address/model/person/PersonContainsGroupsPredicate.java
``` java
public class PersonContainsGroupsPredicate implements Predicate<Person> {

    private final List<String> keywords;

    public PersonContainsGroupsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getGroup().groupName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonContainsGroupsPredicate // instanceof handles nulls
                && this.keywords.equals(((PersonContainsGroupsPredicate) other).keywords)); // state check
    }
}

```
###### /java/seedu/address/model/person/ReadOnlyPersonComparator.java
``` java
public class ReadOnlyPersonComparator implements Comparator<Person> {

    @Override
    public int compare(Person personA, Person personB) {
        return personA.getName().compareTo(personB.getName());
    }
}
```
###### /java/seedu/address/model/person/Group.java
``` java
public class Group {

    public static final String MESSAGE_GROUP_CONSTRAINTS =
            "Person group should only contain alphanumeric characters and spaces, and it should not be blank";

    public static final String GROUP_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String groupName;

    /**
     * Constructs a {@code Name}.
     *
     * @param group A valid group.
     */
    public Group(String group) {
        requireNonNull(group);
        checkArgument(isValidGroup(group), MESSAGE_GROUP_CONSTRAINTS);
        this.groupName = group;
    }

    /**
     * Returns true if a given string is a valid group.
     */
    public static boolean isValidGroup(String test) {
        return test.matches(GROUP_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return groupName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Group // instanceof handles nulls
                && this.groupName.equals(((Group) other).groupName)); // state check
    }

    @Override
    public int hashCode() {
        return groupName.hashCode();
    }

}
```
###### /java/seedu/address/model/person/UniqueGroupList.java
``` java
public class UniqueGroupList implements Iterable<Group> {

    private final ObservableList<Group> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty Group List.
     */
    public UniqueGroupList() {

    }

    /**
     * Creates a UniqueGroupList using given tags.
     * Enforces no nulls.
     */
    public UniqueGroupList(Group groups) {
        requireAllNonNull(groups);
        internalList.addAll(groups);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Replaces the Groups in this list with those in the argument group list.
     */
    public void setGroups(UniqueGroupList groups) {
        requireAllNonNull(groups);
        internalList.setAll(groups.internalList);
        assert CollectionUtil.elementsAreUnique(internalList);
    }

    public void setGroups(List<? extends Group> groups) throws DuplicateGroupException {
        final UniqueGroupList replacement = new UniqueGroupList();
        for (final Group group : groups) {
            replacement.add(group);
        }
        setGroups(replacement);
    }
    /**
     * Ensures every tag in the argument list exists in this object.
     */
    public void mergeFrom(UniqueGroupList from) {
        final UniqueGroupList alreadyInside = this;
        from.internalList.stream()
                .filter(group -> !alreadyInside.contains(group))
                .forEach(internalList::add);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns true if the list contains an equivalent Group as the given argument.
     */
    public boolean contains(Group toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a Tag to the list.
     *
     * @throws DuplicateGroupException if the Group to add is a duplicate of an existing Tag in the list.
     */
    public void add(Group toAdd) throws DuplicateGroupException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateGroupException();
        }
        internalList.add(toAdd);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    @Override
    public Iterator<Group> iterator() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.iterator();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Group> asObservableList() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public boolean equals(Object other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        return other == this // short circuit if same object
                || (other instanceof UniqueGroupList // instanceof handles nulls
                && this.internalList.equals(((UniqueGroupList) other).internalList));
    }

    @Override
    public int hashCode() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.hashCode();
    }

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateGroupException extends DuplicateDataException {
        protected DuplicateGroupException() {
        super("Operation would result in duplicate groups");
        }
    }
}
```
