package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.io.PrintWriter;
import java.io.StringWriter;

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

    //@@author yeggasd
    /**
     * Returns true if {@code s} represents odd or even
     ** Will return false for any other non-null string input
     * e.g. empty string, "-1", "0", "+1", and " 2 " (untrimmed), "3 0" (contains whitespace), "1 a" (contains letters)
     * @throws NullPointerException if {@code s} is null.
     */
    public static boolean isOddEven(String s) {
        requireNonNull(s);
        return s.equalsIgnoreCase("even") || s.equalsIgnoreCase("odd");
    }

    /**
     * @param s The string to be checked
     * @return 0 is string is even else 1.
     */
    public static int getOddEven(String s) {
        requireNonNull(s);
        if (s.equalsIgnoreCase("even")) {
            return 0;
        } else {
            return 1;
        }
    }

    /**
     * Returns true if (@code text) represents a day in the week
     * e.g. Monday
     * Will return false for any other non-null string input
     * e.g. empty string, "-1", "0", "+1", and " 2 " (untrimmed), "3 0" (contains whitespace), "1 a" (contains letters)
     * @throws NullPointerException if {@code s} is null.
     */
    public static boolean isDay(String s) {
        requireNonNull(s);
        return s.equalsIgnoreCase("Monday") || s.equalsIgnoreCase("Tuesday")
                || s.equalsIgnoreCase("Wednesday") || s.equalsIgnoreCase("Thursday")
                || s.equalsIgnoreCase("Friday") || s.equalsIgnoreCase("Saturday")
                || s.equalsIgnoreCase("Sunday");
    }

    /**
     * Capitalizes the given (@code s)
     * @param s String to be capitalized
     * @return Capitalized String
     * @throws NullPointerException if {@code s} is null.
     */
    public static String capitalize(String s) {
        requireNonNull(s);
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }
    //@@author
}
