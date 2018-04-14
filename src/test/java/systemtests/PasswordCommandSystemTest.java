package systemtests;

import static seedu.address.logic.parser.CliSyntax.PREFIX_SET;

import org.junit.Test;

import seedu.address.logic.commands.PasswordCommand;

public class PasswordCommandSystemTest extends AddressBookSystemTest {

    private static String oldPassword = "old";
    private static String newPassword = "new";

    @Test
    public void pass(){
        // Set Password success
        String command = PasswordCommand.COMMAND_WORD + " " + PREFIX_SET + oldPassword;

        // Set Password fail because password already present
        String command = PasswordCommand.COMMAND_WORD + " " + PREFIX_SET + oldPassword;

        // Change Password success
        String command = PasswordCommand.COMMAND_WORD + " " + PREFIX_SET + newPassword;

        // Change Password fail because no password to change
        String command = PasswordCommand.COMMAND_WORD + " " + PREFIX_SET + newPassword;

        // Remove Password success
        String command = PasswordCommand.COMMAND_WORD + " " + PREFIX_SET + newPassword;

        // Remove Password fail because no password to clear
        String command = PasswordCommand.COMMAND_WORD + " " + PREFIX_SET + newPassword;




    }
}
