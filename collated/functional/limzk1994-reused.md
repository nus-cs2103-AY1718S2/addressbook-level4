# limzk1994-reused
###### /java/seedu/address/commons/exceptions/WrongPasswordException.java
``` java
/**
 * Signals that the password provided is wrong.
 */
public class WrongPasswordException extends Exception {

    private static final String MESSAGE_WRONG_PASSWORD = "Wrong Password";

    public WrongPasswordException() {
        super(MESSAGE_WRONG_PASSWORD);
    }

    /**
     * @param cause   of the main exception
     */
    public WrongPasswordException(Throwable cause) {
        super(MESSAGE_WRONG_PASSWORD, cause);
    }
}
```
###### /java/seedu/address/commons/events/ui/PasswordAcceptedEvent.java
``` java
/**
 * Indicates that password is accepted
 */
public class PasswordAcceptedEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### /java/seedu/address/logic/commands/commandmode/PasswordMode.java
``` java

/**
 * Represents the different modes for PasswordCommand
 */
public abstract class PasswordMode {

    private String pass;

    public PasswordMode(String pass) {
        this.pass = pass;
    }

    public String getPass() {
        return pass;
    }

    protected boolean passExists() {
        return PasswordManger.passwordExists();
    }

    public abstract CommandResult execute() throws IOException, CommandException;
}
```
