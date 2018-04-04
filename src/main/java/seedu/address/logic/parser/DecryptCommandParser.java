package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_FILE_NOT_FOUND;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.stream.Stream;

import javax.crypto.Cipher;

import seedu.address.commons.util.SecurityUtil;
import seedu.address.logic.commands.DecryptCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.UserPrefs;
//@@author tinyjy94
/**
 * Parses input arguments and creates a new DecryptCommand object
 */
public class DecryptCommandParser implements Parser<DecryptCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DecryptCommand
     * and returns an DecryptCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DecryptCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_PASSWORD);
        String password = args.trim();

        if (!arePrefixesPresent(argMultimap, PREFIX_PASSWORD)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DecryptCommand.MESSAGE_USAGE));
        } else {
            try {
                Key pw = SecurityUtil.generateKey(password);

                UserPrefs pref = new UserPrefs();
                Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
                cipher.init(Cipher.DECRYPT_MODE, pw);
                File inputFile = new File(pref.getEncryptedMoviePlannerFilePath());
                byte[] inputByteArray = new byte[(int) inputFile.length()];
                FileInputStream fis = new FileInputStream(inputFile); //create file input stream
                fis.read(inputByteArray);
                cipher.doFinal(inputByteArray);

            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (GeneralSecurityException e) {
                throw new ParseException(String.format(DecryptCommand.MESSAGE_WRONGPASSWORD,
                        DecryptCommand.MESSAGE_USAGE));
            } catch (IOException e) {
                throw new ParseException(String.format(MESSAGE_FILE_NOT_FOUND, DecryptCommand.MESSAGE_USAGE));
            }
        }

        return new DecryptCommand(password);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
