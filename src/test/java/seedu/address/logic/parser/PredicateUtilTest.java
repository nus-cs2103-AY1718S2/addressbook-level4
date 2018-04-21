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
public class PredicateUtilTest {
    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    private PredicateUtil testAllPredicate = new PredicateUtil();

    @Test
    public void parseKeywordsArray_validInput_success() {
        String[] globalKeywords = {"Alex", "\"example.com\"", "9012*", "*Science"};
        String[] prefixKeywords = {"n/Alex, Bernice", "e/\"example.com\"",
            "p/9012*, 8121*", "m/*Science, *Engineering"};
        try {
            testAllPredicate.parseAllPredicates(globalKeywords);
            testAllPredicate.parseAllPredicates(prefixKeywords);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
    }

    @Test
    public void parseKeywordsArray_emptyWordParameterForGlobalSearch_throwsParseException() {
        String[] globalKeywords = {};

        thrown.expect(ArrayIndexOutOfBoundsException.class);
        try {
            testAllPredicate.parseAllPredicates(globalKeywords);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
    }

    @Test
    public void parseKeywordsArray_emptyWordParameterForPrefixSearch_throwsParseException() {
        String testArgs = "n/";
        ArgumentMultimap testArgMultiMap = ArgumentTokenizer.tokenize(testArgs,
                PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                PREFIX_ADDRESS, PREFIX_UNIVERSITY, PREFIX_MAJOR,
                PREFIX_JOB_APPLIED, PREFIX_COMMENT);

        try {
            testAllPredicate.parseSelectedPredicates(testArgMultiMap);
        } catch (ParseException pe) {
            thrown.expect(ParseException.class);
        }
    }

    @Test
    public void parseKeywordsArray_invalidWildcardForPrefixSearch_throwsParseException() {
        String testArgs = "n/\"";
        ArgumentMultimap testArgMultiMap = ArgumentTokenizer.tokenize(testArgs,
                PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                PREFIX_ADDRESS, PREFIX_UNIVERSITY, PREFIX_MAJOR,
                PREFIX_JOB_APPLIED, PREFIX_COMMENT);

        try {
            testAllPredicate.parseSelectedPredicates(testArgMultiMap);
        } catch (ParseException pe) {
            thrown.expect(ParseException.class);
        }
    }

    @Test
    public void parseKeywordsArray_emptySubstringParameterForPrefixSearch_throwsParseException() {
        String testArgs = "n/\"\"";
        ArgumentMultimap testArgMultiMap = ArgumentTokenizer.tokenize(testArgs,
                PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                PREFIX_ADDRESS, PREFIX_UNIVERSITY, PREFIX_MAJOR,
                PREFIX_JOB_APPLIED, PREFIX_COMMENT);

        try {
            testAllPredicate.parseSelectedPredicates(testArgMultiMap);
        } catch (ParseException pe) {
            thrown.expect(ParseException.class);
        }
    }
}
