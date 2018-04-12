package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.CommandTarget;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.coin.Amount;
import seedu.address.model.coin.Code;
import seedu.address.model.coin.Coin;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 * {@code ParserUtil} contains methods that take in {@code Optional} as parameters. However, it goes against Java's
 * convention (see https://stackoverflow.com/a/39005452) as {@code Optional} should only be used a return type.
 * Justification: The methods in concern receive {@code Optional} return values from other methods as parameters and
 * return {@code Optional} values based on whether the parameters were present. Therefore, it is redundant to unwrap the
 * initial {@code Optional} before passing to {@code ParserUtil} as a parameter and then re-wrap it into an
 * {@code Optional} return value inside {@code ParserUtil} methods.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_NUMBER = "Argument is not a valid number.";
    public static final String MESSAGE_INSUFFICIENT_PARTS = "Number of parts must be more than 1.";
    public static final String MESSAGE_CONDITION_ARGUMENT_INVALID_SYNTAX = "%s structure of the argument is invalid:"
            + " Expected %s but instead got %s.";

    private static Logger logger = LogsCenter.getLogger(ParserUtil.class);

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws IllegalValueException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws IllegalValueException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new IllegalValueException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code name} is invalid.
     */
    public static Code parseName(String name) throws IllegalValueException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Code.isValidName(trimmedName)) {
            throw new IllegalValueException(Code.MESSAGE_NAME_CONSTRAINTS);
        }
        return new Code(trimmedName);
    }

    /**
     * Parses a {@code Optional<String> name} into an {@code Optional<Name>} if {@code name} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Code> parseName(Optional<String> name) throws IllegalValueException {
        requireNonNull(name);
        return name.isPresent() ? Optional.of(parseName(name.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String target} into a {@code CommandTarget}.
     * Can be any of: code or index.
     *
     * @throws IllegalValueException
     */
    public static CommandTarget parseTarget(String target) throws IllegalValueException {
        requireNonNull(target);
        try {
            return new CommandTarget(parseIndex(target));
        } catch (IllegalValueException ive) {
            return new CommandTarget(parseName(target));
            // may still throw again, handle it at call site
        }

    }

    /**
     * Parses {@code value} into an {@code Amount} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws IllegalValueException if the specified index is invalid (not number value).
     */
    public static Amount parseAmount(String value) throws IllegalValueException {
        String trimmedValue = value.trim();
        if (!StringUtil.isValidAmount(trimmedValue)) {
            throw new IllegalValueException(MESSAGE_INVALID_NUMBER);
        }
        return new Amount(trimmedValue);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws IllegalValueException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new IllegalValueException(Tag.MESSAGE_TAG_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws IllegalValueException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    //@@author Eldon-Chung
    /**
     * Parses a {@code String condition} represented by a {@code TokenStack} into a {@code Predicate<Coin>}.
     * @param argumentTokenStack a {@code TokenStack} representing the tokenized argument.
     * @return a predicate representing the argument
     * @throws IllegalValueException if the given tag names or numbers as parameters are invalid
     *          and if the argument is either syntactically or semantically invalid.
     */
    public static Predicate<Coin> parseCondition(TokenStack argumentTokenStack)
            throws IllegalValueException {
        requireNonNull(argumentTokenStack);
        TokenType expectedTokenType;
        TokenType actualTokenType;

        ConditionSyntaxParser conditionSyntaxParser = new ConditionSyntaxParser(argumentTokenStack);
        if (!conditionSyntaxParser.parse()) {
            expectedTokenType = conditionSyntaxParser.getExpectedType();
            actualTokenType = conditionSyntaxParser.getActualType();
            logger.warning(String.format(MESSAGE_CONDITION_ARGUMENT_INVALID_SYNTAX, "Syntactic",
                    expectedTokenType.description, actualTokenType.description));
            throw new ParseException("command arguments invalid.");
        }

        ConditionSemanticParser conditionSemanticParser = new ConditionSemanticParser(argumentTokenStack);
        if (!conditionSemanticParser.parse()) {
            expectedTokenType = conditionSemanticParser.getExpectedType();
            actualTokenType = conditionSemanticParser.getActualType();
            logger.warning(String.format(MESSAGE_CONDITION_ARGUMENT_INVALID_SYNTAX, "Semantic",
                    expectedTokenType.description, actualTokenType.description));
            throw new ParseException("command arguments invalid.");
        }

        ConditionGenerator conditionGenerator = new ConditionGenerator(argumentTokenStack);
        return conditionGenerator.generate();
    }
    //author@@
}
