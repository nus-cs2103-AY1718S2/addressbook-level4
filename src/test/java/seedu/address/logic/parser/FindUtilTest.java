package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOB_APPLIED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAJOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_UNIVERSITY;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.parser.exceptions.ParseException;

//@@author tanhengyeow
public class FindUtilTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    private FindUtil testFindUtil = new FindUtil();

    @Test
    public void parseFindArgs_validInput_success() {
        String testGlobalArgs = "test";
        String testPrefixArgs = "m/";
        ArgumentMultimap testGlobalArgMultiMap =
                ArgumentTokenizer.tokenize(testGlobalArgs, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                        PREFIX_ADDRESS, PREFIX_UNIVERSITY, PREFIX_MAJOR,
                        PREFIX_JOB_APPLIED, PREFIX_COMMENT); // more fields to be added if necessary

        ArgumentMultimap testPrefixArgMultiMap =
                ArgumentTokenizer.tokenize(testPrefixArgs, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                        PREFIX_ADDRESS, PREFIX_UNIVERSITY, PREFIX_MAJOR,
                        PREFIX_JOB_APPLIED, PREFIX_COMMENT); // more fields to be added if necessary

        try {
            testFindUtil.parseFindArgs(testGlobalArgs, testGlobalArgMultiMap);
            testFindUtil.parseFindArgs(testPrefixArgs, testPrefixArgMultiMap);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
    }
}
