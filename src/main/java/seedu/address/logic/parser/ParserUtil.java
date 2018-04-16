package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.JumpToCommand.MESSAGE_USAGE;

import java.time.YearMonth;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.AddPictureCommand;
import seedu.address.logic.commands.ExportCommand;
import seedu.address.model.category.TaskCategory;
import seedu.address.model.event.Datetime;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Picture;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.TaskDescription;
import seedu.address.model.task.TaskDueDate;
import seedu.address.model.task.TaskName;
import seedu.address.model.task.TaskPriority;
import seedu.address.model.task.TaskStatus;

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
    public static final int JUMPTO_VALID_ARGS_LENGTH = 8;

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
     * Checks if {@code String args} is empty or valid.
     */
    public static String parseFilePath(String args) throws IllegalValueException {
        requireNonNull(args);
        String filePath = args.trim();

        if (filePath.isEmpty()) {
            throw new IllegalValueException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));
        }
        return filePath;
    }

    /**
     * Checks if (@code String args) is a valid image filename or directory leading to a image file
     */

    public static String parseImageFilename(String args) throws IllegalValueException {
        requireNonNull(args);
        String file = args.trim();

        if (file.isEmpty() || !Picture.isValidPath(file)) {
            throw new IllegalValueException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddPictureCommand.MESSAGE_USAGE));
        }

        return file;
    }

    //@@author CYX28
    /**
     * Parses a {@code String taskName} into a {@code TaskName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code taskName} is invalid.
     */
    public static TaskName parseTaskName(String taskName) throws IllegalValueException {
        requireNonNull(taskName);
        String trimmedTaskName = taskName.trim();
        if (!TaskName.isValidTaskName(trimmedTaskName)) {
            throw new IllegalValueException(TaskName.MESSAGE_TASK_NAME_CONSTRAINTS);
        }
        return new TaskName(trimmedTaskName);
    }

    /**
     * Parses a {@code Optional<String> taskName} into an {@code Optional<TaskName>} if {@code taskName} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<TaskName> parseTaskName(Optional<String> taskName) throws IllegalValueException {
        requireNonNull(taskName);
        return taskName.isPresent() ? Optional.of(parseTaskName(taskName.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String taskPriority} into a {@code TaskPriority}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code taskPriority} is invalid.
     */
    public static TaskPriority parseTaskPriority(String taskPriority) throws IllegalValueException {
        requireNonNull(taskPriority);
        String trimmedTaskPriority = taskPriority.trim();
        if (!TaskPriority.isValidTaskPriority(trimmedTaskPriority)) {
            throw new IllegalValueException(TaskPriority.MESSAGE_TASK_PRIORITY_CONSTRAINTS);
        }
        return new TaskPriority(trimmedTaskPriority);
    }

    /**
     * Parses a {@code Optional<String> taskPriority} into an {@code Optional<TaskPriority>}
     * if {@code taskPriority} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<TaskPriority> parseTaskPriority(Optional<String> taskPriority) throws IllegalValueException {
        requireNonNull(taskPriority);
        return taskPriority.isPresent() ? Optional.of(parseTaskPriority(taskPriority.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String taskDescription} into a {@code TaskDescription}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code taskDescription} is invalid.
     */
    public static TaskDescription parseTaskDescription(String taskDescription) throws IllegalValueException {
        requireNonNull(taskDescription);
        String trimmedTaskDescription = taskDescription.trim();
        if (!TaskDescription.isValidTaskDescription(trimmedTaskDescription)) {
            throw new IllegalValueException(TaskDescription.MESSAGE_TASK_DESCRIPTION_CONSTRAINTS);
        }
        return new TaskDescription(trimmedTaskDescription);
    }

    /**
     * Parses a {@code Optional<String> taskDescription} into an {@code Optional<TaskDescription>}
     * if {@code taskDescription} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<TaskDescription> parseTaskDescription(Optional<String> taskDescription)
            throws IllegalValueException {
        requireNonNull(taskDescription);
        return taskDescription.isPresent()
                ? Optional.of(parseTaskDescription(taskDescription.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String taskDueDate} into a {@code TaskDueDate}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code taskDueDate} is invalid.
     */
    public static TaskDueDate parseTaskDueDate(String taskDueDate) throws IllegalValueException {
        requireNonNull(taskDueDate);
        String trimmedTaskDueDate = taskDueDate.trim();
        if (!TaskDueDate.isValidTaskDueDate(trimmedTaskDueDate)) {
            throw new IllegalValueException(TaskDueDate.MESSAGE_TASK_DUE_DATE_CONSTRAINTS);
        }
        return new TaskDueDate(trimmedTaskDueDate);
    }

    /**
     * Parses a {@code Optional<String> taskDueDate} into an {@code Optional<TaskDueDate>}
     * if {@code taskDueDate} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<TaskDueDate> parseTaskDueDate(Optional<String> taskDueDate) throws IllegalValueException {
        requireNonNull(taskDueDate);
        return taskDueDate.isPresent() ? Optional.of(parseTaskDueDate(taskDueDate.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String taskStatus} into a {@code TaskStatus}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code taskStatus} is invalid.
     */
    public static TaskStatus parseTaskStatus(String taskStatus) throws IllegalValueException {
        requireNonNull(taskStatus);
        String trimmedTaskStatus = taskStatus.trim();
        if (!TaskStatus.isValidTaskStatus(trimmedTaskStatus)) {
            throw new IllegalValueException(TaskStatus.MESSAGE_TASK_STATUS_CONSTRAINTS);
        }
        return new TaskStatus(trimmedTaskStatus);
    }

    /**
     * Parses a {@code Optional<String> taskStatus} into an {@code Optional<TaskStatus>}
     * if {@code taskStatus} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<TaskStatus> parseTaskStatus(Optional<String> taskStatus) throws IllegalValueException {
        requireNonNull(taskStatus);
        return taskStatus.isPresent() ? Optional.of(parseTaskStatus(taskStatus.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String taskCategory} into a {@code TaskCategory}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code taskCategory} is invalid.
     */
    public static TaskCategory parseTaskCategory(String taskCategory) throws IllegalValueException {
        requireNonNull(taskCategory);
        String trimmedTaskCategory = taskCategory.trim().toLowerCase();
        if (!TaskCategory.isValidTaskCategoryName(trimmedTaskCategory)) {
            throw new IllegalValueException(TaskCategory.MESSAGE_TASK_CATEGORY_CONSTRAINTS);
        }
        return new TaskCategory(trimmedTaskCategory);
    }

    /**
     * Parses {@code Collection<String> taskCategories} into a {@code Set<TaskCategory>}.
     */
    public static Set<TaskCategory> parseTaskCategories(Collection<String> taskCategories)
            throws IllegalValueException {
        requireNonNull(taskCategories);
        final Set<TaskCategory> taskCategorySet = new HashSet<>();
        for (String taskCategoryName : taskCategories) {
            taskCategorySet.add(parseTaskCategory(taskCategoryName));
        }
        return taskCategorySet;
    }

    //@@author x3tsunayh

    /**
     * Parses a {@code Optional<String> datetime} into an {@code Optional<Datetime>} if {@code datetime} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Datetime> parseDatetime(Optional<String> datetime) throws IllegalValueException {
        requireNonNull(datetime);
        return datetime.isPresent() ? Optional.of(new Datetime(datetime.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String yearmonth} into a {@code YearMonth} if {@code yearmonth} is present.
     */
    public static YearMonth parseYearmonth(String yearmonth) throws IllegalValueException {
        requireNonNull(yearmonth);
        if (yearmonth.length() != JUMPTO_VALID_ARGS_LENGTH) {
            throw new IllegalValueException(MESSAGE_USAGE);
        }

        int year = Integer.parseInt(yearmonth.substring(1, 5));
        String separator = yearmonth.substring(5, 6);
        int month = Integer.parseInt(yearmonth.substring(6));

        if (!separator.equals("-") || 1900 > year || year > 2300 || 1 > month || month > 12) {
            throw new IllegalValueException(MESSAGE_USAGE);
        }

        YearMonth yearMonth = YearMonth.of(year, month);
        return yearMonth;
    }
}
