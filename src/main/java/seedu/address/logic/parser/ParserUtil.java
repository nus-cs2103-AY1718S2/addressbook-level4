package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import java.util.Set;

import com.google.common.collect.Iterables;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Cca;
import seedu.address.model.person.Email;
import seedu.address.model.person.InjuriesHistory;
import seedu.address.model.person.Name;
import seedu.address.model.person.NameOfKin;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.subject.Subject;
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

    //@@author chuakunhong
    /**
     * Parses a {@code String nric} into a {@code Nric}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code nric} is invalid.
     */
    public static Nric parseNric(String nric) throws IllegalValueException {
        requireNonNull(nric);
        String trimmedNric = nric.trim();
        if (!Nric.isValidNric(trimmedNric)) {
            throw new IllegalValueException(Nric.MESSAGE_NRIC_CONSTRAINTS);
        }
        return new Nric(trimmedNric);
    }

    /**
     * Parses a {@code Optional<String> nric} into an {@code Optional<Nric>} if {@code nric} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Nric> parseNric(Optional<String> nric) throws IllegalValueException {
        requireNonNull(nric);
        return nric.isPresent() ? Optional.of(parseNric(nric.get())) : Optional.empty();
    }

    //@@author
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
     * Parses {@code Collection<String> tags} into a {@code List<Tag>}.
     */
    public static List<Tag> parseTagsForReplacement(Collection<String> tags) throws IllegalValueException {
        requireNonNull(tags);
        final List<Tag> tagSet = new LinkedList<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
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

    //@@author TeyXinHui
    /**
     * Splits a {@code String subjects} into {@code String subjectName} and {@code String subjectGrade}
     * Parses {@code String subjectName} and {@code String subjectGrade}into a {@code Subject}.
     * Add {@code Subject} into a {@code Set<Subject> subjectSet}.
     * @throws IllegalValueException if the given {@code subjects} is invalid
     */
    public static void parseSubject(Collection<String> subjects, Set<Subject> subjectSet) throws IllegalValueException {
        requireNonNull(subjects);
        String subjectsStr = Iterables.get(subjects, 0);
        String[] splitSubjectStr = subjectsStr.trim().split("\\s+");
        for (int i = 0; i < splitSubjectStr.length; i++) {
            String subjectName = splitSubjectStr[i];
            if (!Subject.isValidSubjectName(subjectName)) {
                throw new IllegalValueException(Subject.MESSAGE_SUBJECT_NAME_CONSTRAINTS);
            }
            i += 1;
            String subjectGrade = splitSubjectStr[i];
            if (!Subject.isValidSubjectGrade(subjectGrade)) {
                throw new IllegalValueException(Subject.MESSAGE_SUBJECT_GRADE_CONSTRAINTS);
            }
            subjectSet.add(new Subject(subjectName, subjectGrade));
        }
    }

    /**
     * Parses {@code Collection<String> subjects} into a {@code Set<Subject}.
     */
    public static Set<Subject> parseSubjects(Collection<String> subjects) throws IllegalValueException {
        requireNonNull(subjects);
        final Set<Subject> subjectSet = new HashSet<>();
        if (subjects.size() == 1) {
            parseSubject(subjects, subjectSet);
        }
        return subjectSet;
    }

    //@@author
    /**
     * Parses a {@code String remark} into a {@code Remark}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code name} is invalid.
     */
    public static Remark parseRemark(String remark) {
        requireNonNull(remark);
        String trimmedRemark = remark.trim();
        return new Remark(trimmedRemark);
    }

    /**
     * Parses a {@code Optional<String> remark} into an {@code Optional<Remark>} if {@code remark} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Remark> parseRemark(Optional<String> remark) {
        requireNonNull(remark);
        return remark.isPresent() ? Optional.of(parseRemark(remark.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String info} into a {@code String}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static String parseInfo(String info) {
        requireNonNull(info);
        String trimmedInfo = info.trim();

        return trimmedInfo;
    }

    /**
     * Parses a {@code Optional<String> info} into an {@code Optional<String>} if {@code info} is present.
     */
    public static Optional<String> parseInfo (Optional<String> info) {
        requireNonNull(info);
        return info.isPresent() ? Optional.of(parseInfo(info.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String date} into a {@code String}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code date} is invalid.
     */
    public static String parseDate(String date) throws IllegalValueException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        if (!Appointment.isValidAppointmentDate(trimmedDate)) {
            throw new IllegalValueException(Appointment.MESSAGE_APPOINTMENT_DATE_CONSTRAINTS);
        }
        return new String(trimmedDate);
    }

    /**
     * Parses a {@code Optional<String> date} into an {@code Optional<String>} if {@code date} is present.
     */
    public static Optional<String> parseDate (Optional<String> date) throws IllegalValueException {
        requireNonNull(date);
        return date.isPresent() ? Optional.of(parseDate(date.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String startTime} into a {@code String}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code startTime} is invalid.
     */
    public static String parseStartTime(String startTime) throws IllegalValueException {
        requireNonNull(startTime);
        String trimmedStartTime = startTime.trim();
        if (!Appointment.isValidAppointmentStartTime(trimmedStartTime)) {
            throw new IllegalValueException(Appointment.MESSAGE_APPOINTMENT_START_TIME_CONSTRAINTS);
        }
        return new String(trimmedStartTime);
    }

    /**
     * Parses a {@code Optional<String> startTime} into an {@code Optional<String>} if {@code startTime} is present.
     */
    public static Optional<String> parseStartTime (Optional<String> startTime) throws IllegalValueException {
        requireNonNull(startTime);
        return startTime.isPresent() ? Optional.of(parseStartTime(startTime.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String endTime} into a {@code String}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code endTime} is invalid.
     */
    public static String parseEndTime(String endTime) throws IllegalValueException {
        requireNonNull(endTime);
        String trimmedEndTime = endTime.trim();
        if (!Appointment.isValidAppointmentEndTime(trimmedEndTime)) {
            throw new IllegalValueException(Appointment.MESSAGE_APPOINTMENT_END_TIME_CONSTRAINTS);
        }
        return new String(trimmedEndTime);
    }

    /**
     * Parses a {@code Optional<String> endTime} into an {@code Optional<String>} if {@code endTime} is present.
     */
    public static Optional<String> parseEndTime (Optional<String> endTime) throws IllegalValueException {
        requireNonNull(endTime);
        return endTime.isPresent() ? Optional.of(parseEndTime(endTime.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code name} is invalid.
     */
    public static Cca parseCca(String cca, String pos) {
        requireNonNull(cca);
        String trimmedCca = cca.trim();

        requireNonNull(pos);
        String trimmedPos = pos.trim();
        return new Cca(trimmedCca, trimmedPos);

    }

    /**
     * Parses a {@code Optional<String> name} into an {@code Optional<Name>} if {@code name} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Cca> parseCca(Optional<String> cca, Optional<String> pos) {
        requireNonNull(cca);
        requireNonNull(pos);
        return cca.isPresent() ? Optional.of(parseCca(cca.get(), pos.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code name} is invalid.
     */
    public static InjuriesHistory parseInjuriesHistory(String injuriesHistory) {
        requireNonNull(injuriesHistory);
        String trimmedInjuriesHistory = injuriesHistory.trim();
        return new InjuriesHistory(trimmedInjuriesHistory);
    }

    /**
     * Parses a {@code Optional<String> name} into an {@code Optional<Name>} if {@code name} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<InjuriesHistory> parseInjuriesHistory(Optional<String> injuriesHistory) {
        requireNonNull(injuriesHistory);
        return injuriesHistory.isPresent() ? Optional.of(parseInjuriesHistory(injuriesHistory.get()))
                : Optional.empty();
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code name} is invalid.
     */
    public static NameOfKin parseNameOfKin(String nameOfKin) throws IllegalValueException {
        requireNonNull(nameOfKin);
        String trimmedNameOfKin = nameOfKin.trim();
        if (!NameOfKin.isValidName(trimmedNameOfKin)) {
            throw new IllegalValueException(NameOfKin.MESSAGE_NAME_CONSTRAINTS);
        }
        return new NameOfKin(trimmedNameOfKin);
    }

    /**
     * Parses a {@code Optional<String> name} into an {@code Optional<Name>} if {@code name} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<NameOfKin> parseNameOfKin(Optional<String> nameOfKin) throws IllegalValueException {
        requireNonNull(nameOfKin);
        return nameOfKin.isPresent() ? Optional.of(parseNameOfKin(nameOfKin.get()))
                : Optional.empty();
    }
}
