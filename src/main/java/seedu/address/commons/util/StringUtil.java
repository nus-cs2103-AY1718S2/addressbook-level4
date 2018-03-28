package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.lang.StringUtils;

/**
 * Helper functions for handling strings.
 */
public class StringUtil {

    /**
     * Returns true if the {@code sentence} contains the {@code word}.
     *   Ignores case, but a full word match is required.
     *   <br>examples:<pre>
     *       containsWordIgnoreCase("ABc def", "abc") == true
     *       containsWordIgnoreCase("ABc def", "DEF") == true
     *       containsWordIgnoreCase("ABc def", "AB") == false //not a full word match
     *       </pre>
     * @param sentence cannot be null
     * @param word cannot be null, cannot be empty, must be a single word
     */
    public static boolean containsWordIgnoreCase(String sentence, String word) {
        requireNonNull(sentence);
        requireNonNull(word);

        String preppedWord = word.trim();
        checkArgument(!preppedWord.isEmpty(), "Word parameter cannot be empty");
        checkArgument(preppedWord.split("\\s+").length == 1, "Word parameter should be a single word");

        String preppedSentence = sentence;
        String[] wordsInPreppedSentence = preppedSentence.split("\\s+");

        for (String wordInSentence: wordsInPreppedSentence) {
            if (wordInSentence.equalsIgnoreCase(preppedWord)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if the {@code sentence} contains the {@code substring}.
     *   Ignores case and matches the substring.
     *   <br>examples:<pre>
     *       containsSubstringIgnoreCase("Tes ting", "e ting") == true
     *       containsSubstringIgnoreCase("TES ting", "e tin") == true
     *       containsSubstringIgnoreCase("TeS Ting", "ef") == false //not a substring
     *       </pre>
     * @param sentence cannot be null
     * @param substring cannot be null, cannot be empty
     */
    public static boolean containsSubstringIgnoreCase(String sentence, String substring) {
        requireNonNull(sentence);
        requireNonNull(substring);

        String preppedSubstring = substring.trim();
        checkArgument(!preppedSubstring.isEmpty(), "Substring parameter cannot be empty");

        return StringUtils.containsIgnoreCase(sentence, substring);
    }

    /**
     * Returns true if the {@code sentence} contains the {@code prefix}.
     *   Ignores case and matches the prefix.
     *   <br>examples:<pre>
     *       containsPrefixIgnoreCase("Tes ting", "tes") == true
     *       containsPrefixIgnoreCase("TES ting", "TeS") == true
     *       containsPrefixIgnoreCase("TeS Ting", "ti") == false
     *       </pre>
     * @param sentence cannot be null
     * @param prefix cannot be null, cannot be empty
     */
    public static boolean containsPrefixIgnoreCase(String sentence, String prefix) {
        requireNonNull(sentence);
        requireNonNull(prefix);

        String preppedPrefix = prefix.trim();
        checkArgument(!preppedPrefix.isEmpty(), "Prefix parameter cannot be empty");

        return StringUtils.startsWithIgnoreCase(sentence, prefix);
    }

    /**
     * Returns true if the {@code sentence} contains the {@code suffix}.
     *   Ignores case and matches the suffix.
     *   <br>examples:<pre>
     *       containsSuffixIgnoreCase("Tes ting", "ing") == true
     *       containsSuffixIgnoreCase("TES ting", "NG") == true
     *       containsSuffixIgnoreCase("TeS Ting", "es") == false
     *       </pre>
     * @param sentence cannot be null
     * @param suffix cannot be null, cannot be empty
     */
    public static boolean containsSuffixIgnoreCase(String sentence, String suffix) {
        requireNonNull(sentence);
        requireNonNull(suffix);

        String preppedSuffix = suffix.trim();
        checkArgument(!preppedSuffix.isEmpty(), "Suffix parameter cannot be empty");

        return StringUtils.endsWithIgnoreCase(sentence, suffix);
    }

    /**
     * Returns a detailed message of the t, including the stack trace.
     */
    public static String getDetails(Throwable t) {
        requireNonNull(t);
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw));
        return t.getMessage() + "\n" + sw.toString();
    }

    /**
     * Returns true if {@code s} represents a non-zero unsigned integer
     * e.g. 1, 2, 3, ..., {@code Integer.MAX_VALUE} <br>
     * Will return false for any other non-null string input
     * e.g. empty string, "-1", "0", "+1", and " 2 " (untrimmed), "3 0" (contains whitespace), "1 a" (contains letters)
     * @throws NullPointerException if {@code s} is null.
     */
    public static boolean isNonZeroUnsignedInteger(String s) {
        requireNonNull(s);

        try {
            int value = Integer.parseInt(s);
            return value > 0 && !s.startsWith("+"); // "+1" is successfully parsed by Integer#parseInt(String)
        } catch (NumberFormatException nfe) {
            return false;
        }
    }
}
