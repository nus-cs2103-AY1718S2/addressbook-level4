package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.appointment.DateCommand.DATE_VALIDATION_REGEX;
import static seedu.address.logic.commands.appointment.DateCommand.MESSAGE_DATE_CONSTRAINTS;
import static seedu.address.logic.commands.appointment.DateTimeCommand.DATE_TIME_VALIDATION_REGEX;
import static seedu.address.logic.commands.appointment.DateTimeCommand.MESSAGE_DATE_TIME_CONSTRAINTS;
import static seedu.address.logic.commands.appointment.MonthCommand.MESSAGE_YEAR_MONTH_CONSTRAINTS;
import static seedu.address.logic.commands.appointment.MonthCommand.YEAR_MONTH_VALIDATION_REGEX;
import static seedu.address.logic.commands.appointment.WeekCommand.MESSAGE_WEEK_CONSTRAINTS;
import static seedu.address.logic.commands.appointment.WeekCommand.WEEK_VALIDATION_REGEX;
import static seedu.address.logic.commands.appointment.YearCommand.MESSAGE_YEAR_CONSTRAINTS;
import static seedu.address.logic.commands.appointment.YearCommand.YEAR_VALIDATION_REGEX;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.appointment.EndDateTime;
import seedu.address.model.appointment.StartDateTime;
import seedu.address.model.appointment.Title;
import seedu.address.model.job.Location;
import seedu.address.model.job.NumberOfPositions;
import seedu.address.model.job.Position;
import seedu.address.model.job.Team;
import seedu.address.model.person.Address;
import seedu.address.model.person.Company;
import seedu.address.model.person.CurrentPosition;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ProfilePicture;
import seedu.address.model.skill.Skill;

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
    public static final String MESSAGE_INVALID_NUMBER_OF_POSITIONS = "Number of Positions is not a "
            + "non-zero unsigned integer.";
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
     * Parses a {@code String currentPosition} into an {@code CurrentPosition}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code currentPosition} is invalid.
     */
    public static CurrentPosition parseCurrentPosition(String currentPosition) throws IllegalValueException {
        requireNonNull(currentPosition);
        String trimmedCurrentPosition = currentPosition.trim();
        if (!CurrentPosition.isValidCurrentPosition(trimmedCurrentPosition)) {
            throw new IllegalValueException(CurrentPosition.MESSAGE_CURRENT_POSITION_CONSTRAINTS);
        }
        return new CurrentPosition(trimmedCurrentPosition);
    }

    /**
     * Parses a {@code Optional<String> company} into an {@code Optional<Company>} if {@code company} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<CurrentPosition> parseCurrentPosition(Optional<String> currentPosition)
            throws IllegalValueException {
        requireNonNull(currentPosition);
        return currentPosition.isPresent() ? Optional.of(parseCurrentPosition(currentPosition.get()))
                : Optional.empty();
    }

    /**
     * Parses a {@code String company} into an {@code Company}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code company} is invalid.
     */
    public static Company parseCompany(String company) throws IllegalValueException {
        requireNonNull(company);
        String trimmedCompany = company.trim();
        if (!Company.isValidCompany(trimmedCompany)) {
            throw new IllegalValueException(Company.MESSAGE_COMPANY_CONSTRAINTS);
        }
        return new Company(trimmedCompany);
    }

    /**
     * Parses a {@code Optional<String> company} into an {@code Optional<Company>} if {@code company} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Company> parseCompany(Optional<String> company) throws IllegalValueException {
        requireNonNull(company);
        return company.isPresent() ? Optional.of(parseCompany(company.get())) : Optional.empty();
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
     * Parses a {@code String profilePicture} into an {@code ProfilePicture}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code profilePicture} is invalid.
     */
    public static ProfilePicture parseProfilePicture(String profilePicture) throws IllegalValueException {
        requireNonNull(profilePicture);
        String trimmedProfilePicture = profilePicture.trim();
        if (!ProfilePicture.isValidProfilePicture(trimmedProfilePicture)) {
            throw new IllegalValueException(ProfilePicture.MESSAGE_PROFILEPICTURE_CONSTRAINTS);
        }
        return new ProfilePicture(trimmedProfilePicture);
    }

    /**
     * Parses a {@code Optional<String> profilePicture} into an {@code Optional<ProfilePicture>}
     * if {@code profilePicture} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<ProfilePicture> parseProfilePicture(Optional<String> profilePicture)
            throws IllegalValueException {
        return profilePicture.isPresent() ? Optional.of(parseProfilePicture(profilePicture.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String skill} into a {@code Skill}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code skill} is invalid.
     */
    public static Skill parseSkill(String skill) throws IllegalValueException {
        requireNonNull(skill);
        String trimmedSkill = skill.trim();
        if (!Skill.isValidSkillName(trimmedSkill)) {
            throw new IllegalValueException(Skill.MESSAGE_SKILL_CONSTRAINTS);
        }
        return new Skill(trimmedSkill);
    }

    /**
     * Parses {@code Collection<String> skills} into a {@code Set<Skill>}.
     */
    public static Set<Skill> parseSkills(Collection<String> skills) throws IllegalValueException {
        requireNonNull(skills);
        final Set<Skill> skillSet = new HashSet<>();
        for (String skillName : skills) {
            skillSet.add(parseSkill(skillName));
        }
        return skillSet;
    }

    /**
     * Parses a {@code String title} into a {@code Title}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code title} is invalid.
     */
    public static Title parseTitle(String title) throws IllegalValueException {
        requireNonNull(title);
        String trimmedTitle = title.trim();
        if (!Title.isValidTitle(trimmedTitle)) {
            throw new IllegalValueException(Title.MESSAGE_TITLE_CONSTRAINTS);
        }
        return new Title(trimmedTitle);
    }

    /**
     * Parses a {@code Optional<String> title} into an {@code Optional<Title>} if {@code title} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Title> parseTitle(Optional<String> title) throws IllegalValueException {
        requireNonNull(title);
        return title.isPresent() ? Optional.of(parseTitle(title.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String startDateTime} into a {@code StartDateTime}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code startDateTime} is invalid.
     */
    public static StartDateTime parseStartDateTime(String startDateTime) throws IllegalValueException {
        requireNonNull(startDateTime);
        String trimmedStartDateTime = startDateTime.trim();
        if (!StartDateTime.isValidStartDateTime(trimmedStartDateTime)) {
            throw new IllegalValueException(StartDateTime.MESSAGE_START_DATE_TIME_CONSTRAINTS);
        }
        return new StartDateTime(trimmedStartDateTime);
    }

    /**
     * Parses a {@code Optional<String> startDateTime} into an {@code Optional<StartDateTime>}
     * if {@code startDateTime} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<StartDateTime> parseStartDateTime(Optional<String> startDateTime)
            throws IllegalValueException {
        requireNonNull(startDateTime);
        return startDateTime.isPresent() ? Optional.of(parseStartDateTime(startDateTime.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String endDateTime} into a {@code EndDateTime}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code endDateTime} is invalid.
     */
    public static EndDateTime parseEndDateTime(String endDateTime) throws IllegalValueException {
        requireNonNull(endDateTime);
        String trimmedEndDateTime = endDateTime.trim();
        if (!EndDateTime.isValidEndDateTime(trimmedEndDateTime)) {
            throw new IllegalValueException(EndDateTime.MESSAGE_END_DATE_TIME_CONSTRAINTS);
        }
        return new EndDateTime(trimmedEndDateTime);
    }

    /**
     * Parses a {@code Optional<String> endDateTime} into an {@code Optional<EndDateTime>} if {@code name} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<EndDateTime> parseEndDateTime(Optional<String> endDateTime) throws IllegalValueException {
        requireNonNull(endDateTime);
        return endDateTime.isPresent() ? Optional.of(parseEndDateTime(endDateTime.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String position} into a {@code Position}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code position} is invalid.
     */
    public static Position parsePosition(String position) throws IllegalValueException {
        requireNonNull(position);
        String trimmedPosition = position.trim();
        if (!Position.isValidPosition(trimmedPosition)) {
            throw new IllegalValueException(Position.MESSAGE_POSITION_CONSTRAINTS);
        }
        return new Position(trimmedPosition);
    }

    /**
     * Parses a {@code Optional<String> position} into an {@code Optional<Position>} if {@code position} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Position> parsePosition(Optional<String> position) throws IllegalValueException {
        requireNonNull(position);
        return position.isPresent() ? Optional.of(parsePosition(position.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String team} into a {@code Team}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code team} is invalid.
     */
    public static Team parseTeam(String team) throws IllegalValueException {
        requireNonNull(team);
        String trimmedTeam = team.trim();
        if (!Team.isValidTeam(trimmedTeam)) {
            throw new IllegalValueException(Team.MESSAGE_TEAM_CONSTRAINTS);
        }
        return new Team(trimmedTeam);
    }

    /**
     * Parses a {@code Optional<String> team} into an {@code Optional<Team>} if {@code team} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Team> parseTeam(Optional<String> team) throws IllegalValueException {
        requireNonNull(team);
        return team.isPresent() ? Optional.of(parseTeam(team.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String location} into a {@code Location}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code location} is invalid.
     */
    public static Location parseLocation(String location) throws IllegalValueException {
        requireNonNull(location);
        String trimmedLocation = location.trim();
        if (!Location.isValidLocation(trimmedLocation)) {
            throw new IllegalValueException(Location.MESSAGE_LOCATION_CONSTRAINTS);
        }
        return new Location(trimmedLocation);
    }

    /**
     * Parses a {@code Optional<String> location} into an {@code Optional<Location>} if {@code location} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Location> parseLocation(Optional<String> location) throws IllegalValueException {
        requireNonNull(location);
        return location.isPresent() ? Optional.of(parseLocation(location.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String numberOfPositions} into a {@code numberOfPositions}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code numberOfPositions} is invalid.
     */
    public static NumberOfPositions parseNumberOfPositions(String numberOfPositions) throws IllegalValueException {
        requireNonNull(numberOfPositions);
        String trimmedNumberOfPositions = numberOfPositions.trim();
        if (!NumberOfPositions.isValidNumberOfPositions(trimmedNumberOfPositions)) {
            throw new IllegalValueException(NumberOfPositions.MESSAGE_NUMBER_OF_POSITIONS_CONSTRAINTS);
        }
        return new NumberOfPositions(trimmedNumberOfPositions);
    }

    //@@author trafalgarandre
    /**
     * Parses a {@code Optional<String> numberOfPositions} into an {@code Optional<String>}
     * if {@code numberOfPositions} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<NumberOfPositions> parseNumberOfPositions(Optional<String> numberOfPositions)
            throws IllegalValueException {
        requireNonNull(numberOfPositions);
        return numberOfPositions.isPresent() ? Optional.of(parseNumberOfPositions(numberOfPositions.get()))
                : Optional.empty();
    }

    /**
     * Parses a {@code String yearMonth} into a {@code yearMonth}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code yearMonth} is invalid.
     */
    public static YearMonth parseYearMonth(String yearMonth) throws IllegalValueException {
        String trimmedYearMonth = yearMonth.trim();
        if (!trimmedYearMonth.matches(YEAR_MONTH_VALIDATION_REGEX)) {
            throw new IllegalValueException(MESSAGE_YEAR_MONTH_CONSTRAINTS);
        }
        if (trimmedYearMonth.length() == 0) {
            return null;
        } else {
            return YearMonth.parse(trimmedYearMonth);
        }
    }

    /**
     * Parses a {@code String date} into a {@code date}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code date} is invalid.
     */
    public static LocalDate parseDate(String date) throws IllegalValueException {
        String trimmedDate = date.trim();
        if (!trimmedDate.matches(DATE_VALIDATION_REGEX)) {
            throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
        }
        if (trimmedDate.length() == 0) {
            return null;
        } else {
            return LocalDate.parse(trimmedDate);
        }
    }

    /**
     * Parses a {@code String dateTime} into a {@code dateTime}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code dateTime} is invalid.
     */
    public static LocalDateTime parseDateTime(String dateTime) throws IllegalValueException {
        String trimmedDateTime = dateTime.trim();
        if (!trimmedDateTime.matches(DATE_TIME_VALIDATION_REGEX)) {
            throw new IllegalValueException(MESSAGE_DATE_TIME_CONSTRAINTS);
        }
        if (trimmedDateTime.length() == 0) {
            return null;
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            return LocalDateTime.parse(trimmedDateTime, formatter);
        }
    }

    /**
     * Parses a {@code String date} into a {@code year}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code year} is invalid.
     */
    public static Year parseYear(String year) throws IllegalValueException {
        String trimmedYear = year.trim();
        if (!trimmedYear.matches(YEAR_VALIDATION_REGEX)) {
            throw new IllegalValueException(MESSAGE_YEAR_CONSTRAINTS);
        }
        if (trimmedYear.length() == 0) {
            return null;
        } else {
            return Year.parse(trimmedYear);
        }
    }

    /**
     * Parses a {@code String args} into a {@code year}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code args} is invalid.
     */
    public static Year parseYearOfWeek(String args) throws IllegalValueException {
        String trimmedArgs = args.trim();
        if (!trimmedArgs.matches(WEEK_VALIDATION_REGEX)) {
            throw new IllegalValueException(MESSAGE_WEEK_CONSTRAINTS);
        }
        if (trimmedArgs.length() == 0) {
            return null;
        } else {
            return Year.parse(trimmedArgs.substring(0, 4));
        }
    }

    /**
     * Parses a {@code String args} into a {@code week}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code args} is invalid.
     */
    public static int parseWeek(String args) throws IllegalValueException {
        String trimmedArgs = args.trim();
        if (!trimmedArgs.matches(WEEK_VALIDATION_REGEX)) {
            throw new IllegalValueException(MESSAGE_YEAR_CONSTRAINTS);
        }
        if (trimmedArgs.length() == 0) {
            return 0;
        } else {
            return Integer.parseInt(trimmedArgs.substring(5));
        }
    }
}
