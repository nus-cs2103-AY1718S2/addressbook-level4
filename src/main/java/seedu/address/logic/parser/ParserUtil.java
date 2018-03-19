package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.DoubleUtil;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.ExpectedGraduationYear;
import seedu.address.model.person.Major;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Rating;
import seedu.address.model.person.Resume;
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
    public static final String MESSAGE_INSUFFICIENT_PARTS = "Number of parts must be more than 1.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
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
    public static Name parseName(String name) throws IllegalValueException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code Optional<String> name} into an {@code Optional<Name>} if {@code name} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Name> parseName(Optional<String> name) throws IllegalValueException {
        requireNonNull(name);
        return name.isPresent() ? Optional.of(parseName(name.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws IllegalValueException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new IllegalValueException(Phone.MESSAGE_PHONE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code Optional<String> phone} into an {@code Optional<Phone>} if {@code phone} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Phone> parsePhone(Optional<String> phone) throws IllegalValueException {
        requireNonNull(phone);
        return phone.isPresent() ? Optional.of(parsePhone(phone.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws IllegalValueException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new IllegalValueException(Address.MESSAGE_ADDRESS_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code Optional<String> address} into an {@code Optional<Address>} if {@code address} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Address> parseAddress(Optional<String> address) throws IllegalValueException {
        requireNonNull(address);
        return address.isPresent() ? Optional.of(parseAddress(address.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws IllegalValueException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new IllegalValueException(Email.MESSAGE_EMAIL_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code Optional<String> email} into an {@code Optional<Email>} if {@code email} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Email> parseEmail(Optional<String> email) throws IllegalValueException {
        requireNonNull(email);
        return email.isPresent() ? Optional.of(parseEmail(email.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String expectedGraduationYear} into an {@code ExpectedGraduationYear}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if given {@code expectedGraduationYear} is invalid.
     */
    public static ExpectedGraduationYear parseExpectedGraduationYear(String expectedGraduationYear)
            throws IllegalValueException {
        requireNonNull(expectedGraduationYear);
        String trimmedExpectedGraduationYear = expectedGraduationYear.trim();
        if (!ExpectedGraduationYear.isValidExpectedGraduationYear(trimmedExpectedGraduationYear)) {
            throw new IllegalValueException(ExpectedGraduationYear.MESSAGE_EXPECTED_GRADUATION_YEAR_CONSTRAINTS);
        }
        return new ExpectedGraduationYear(trimmedExpectedGraduationYear);
    }
    /**
     * Parses a {@code Optional<String> expectedGraduationYear}
     * into an {@code Optional<ExpectedGraduationYear>} if {@code expectedGraduationYear} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<ExpectedGraduationYear> parseExpectedGraduationYear(Optional<String> expectedGraduationYear)
            throws IllegalValueException {
        requireNonNull(expectedGraduationYear);
        return expectedGraduationYear.isPresent() ? Optional.of(parseExpectedGraduationYear(
                expectedGraduationYear.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String major} into a {@code Major}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code major} is invalid.
     */
    public static Major parseMajor(String major) throws IllegalValueException {
        requireNonNull(major);
        String trimmedMajor = major.trim();
        if (!Major.isValidMajor(trimmedMajor)) {
            throw new IllegalValueException(Major.MESSAGE_MAJOR_CONSTRAINTS);
        }
        return new Major(trimmedMajor);
    }

    /**
     * Parses a {@code Optional<String> major} into an {@code Optional<Major>} if {@code major} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Major> parseMajor(Optional<String> major) throws IllegalValueException {
        requireNonNull(major);
        return major.isPresent() ? Optional.of(parseMajor(major.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String technicalSkillsScore} into an {@code double}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if given {@code technicalSkillsScore} is invalid.
     */
    public static double parseTechnicalSkillsScore(String technicalSkillsScore)
        throws IllegalValueException {
        requireNonNull(technicalSkillsScore);
        String trimmedTechnicalSkillsScore = technicalSkillsScore.trim();
        double technicalSkillsScoreValue = DoubleUtil.roundToTwoDecimalPlaces(trimmedTechnicalSkillsScore);
        if (!Rating.isValidScore(technicalSkillsScoreValue)) {
            throw new IllegalValueException(Rating.MESSAGE_RATING_CONSTRAINTS);
        }
        return technicalSkillsScoreValue;
    }

    /**
     * Parses a {@code Optional<String> technicalSkillsScore} into an {@code Optional<Double>}
     * if {@code technicalSkillsScore} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Double> parseTechnicalSkillsScore(Optional<String> technicalSkillsScore)
            throws IllegalValueException {
        requireNonNull(technicalSkillsScore);
        if (technicalSkillsScore.get().trim().equals("")) {
            throw new IllegalValueException(Rating.MESSAGE_RATING_CONSTRAINTS);
        }
        return technicalSkillsScore.isPresent() ? Optional.of(parseTechnicalSkillsScore(
                technicalSkillsScore.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String communicationSkillsScore} into an {@code double}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if given {@code communicationSkillsScore} is invalid.
     */
    public static double parseCommunicationSkillsScore(String communicationSkillsScore)
            throws IllegalValueException {
        requireNonNull(communicationSkillsScore);
        String trimmedCommunicationSkillsScore = communicationSkillsScore.trim();
        double communicationSkillsScoreValue = DoubleUtil.roundToTwoDecimalPlaces(trimmedCommunicationSkillsScore);
        if (!Rating.isValidScore(communicationSkillsScoreValue)) {
            throw new IllegalValueException(Rating.MESSAGE_RATING_CONSTRAINTS);
        }
        return communicationSkillsScoreValue;
    }

    /**
     * Parses a {@code Optional<String> communicationSkillsScore} into an {@code Optional<Double>}
     * if {@code communicationSkillsScore} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Double> parseCommunicationSkillsScore(Optional<String> communicationSkillsScore)
            throws IllegalValueException {
        requireNonNull(communicationSkillsScore);
        if (communicationSkillsScore.get().trim().equals("")) {
            throw new IllegalValueException(Rating.MESSAGE_RATING_CONSTRAINTS);
        }
        return communicationSkillsScore.isPresent() ? Optional.of(parseCommunicationSkillsScore(
                communicationSkillsScore.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String problemSolvingSkillsScore} into an {@code double}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if given {@code problemSolvingSkillsScore} is invalid.
     */
    public static double parseProblemSolvingSkillsScore(String problemSolvingSkillsScore)
            throws IllegalValueException {
        requireNonNull(problemSolvingSkillsScore);
        String trimmedProblemSolvingSkillsScore = problemSolvingSkillsScore.trim();
        double problemSolvingSkillsScoreValue = DoubleUtil.roundToTwoDecimalPlaces(trimmedProblemSolvingSkillsScore);
        if (!Rating.isValidScore(problemSolvingSkillsScoreValue)) {
            throw new IllegalValueException(Rating.MESSAGE_RATING_CONSTRAINTS);
        }
        return problemSolvingSkillsScoreValue;
    }

    /**
     * Parses a {@code Optional<String> problemSolvingSkillsScore} into an {@code Optional<Double>}
     * if {@code problemSolvingSkillsScore} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Double> parseProblemSolvingSkillsScore(Optional<String> problemSolvingSkillsScore)
            throws IllegalValueException {
        requireNonNull(problemSolvingSkillsScore);
        if (problemSolvingSkillsScore.get().trim().equals("")) {
            throw new IllegalValueException(Rating.MESSAGE_RATING_CONSTRAINTS);
        }
        return problemSolvingSkillsScore.isPresent() ? Optional.of(parseProblemSolvingSkillsScore(
                problemSolvingSkillsScore.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String experienceScore} into an {@code double}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if given {@code experienceScore} is invalid.
     */
    public static double parseExperienceScore(String experienceScore)
            throws IllegalValueException {
        requireNonNull(experienceScore);
        String trimmedExperienceScore = experienceScore.trim();
        double experienceScoreValue = DoubleUtil.roundToTwoDecimalPlaces(trimmedExperienceScore);
        if (!Rating.isValidScore(experienceScoreValue)) {
            throw new IllegalValueException(Rating.MESSAGE_RATING_CONSTRAINTS);
        }
        return experienceScoreValue;
    }

    /**
     * Parses a {@code Optional<String> experienceScore} into an {@code Optional<Double>}
     * if {@code experienceScore} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Double> parseExperienceScore(Optional<String> experienceScore)
            throws IllegalValueException {
        requireNonNull(experienceScore);
        if (experienceScore.get().trim().equals("")) {
            throw new IllegalValueException(Rating.MESSAGE_RATING_CONSTRAINTS);
        }
        return experienceScore.isPresent() ? Optional.of(parseExperienceScore(
                experienceScore.get())) : Optional.empty();
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

    /**
     * Parses a {@code String resume} into an {@code Resume}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code resume} is invalid.
     */
    public static Resume parseResume(String resume) throws IllegalValueException {
        requireNonNull(resume);
        String trimmedResume = resume.trim();
        if (!Resume.isValidResume(trimmedResume)) {
            throw new IllegalValueException(Resume.MESSAGE_RESUME_CONSTRAINTS);
        }
        return new Resume(trimmedResume);
    }

    /**
     * Parses a {@code Optional<String> resume} into an {@code Optional<Resume>} if {@code resume} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Resume> parseResume(Optional<String> resume) throws IllegalValueException {
        requireNonNull(resume);
        return resume.isPresent() ? Optional.of(parseResume(resume.get())) : Optional.empty();
    }
}
