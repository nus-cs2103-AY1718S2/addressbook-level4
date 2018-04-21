package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPECTED_GRADUATION_YEAR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADE_POINT_AVERAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IMAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOB_APPLIED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAJOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RESUME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_UNIVERSITY;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Comment;
import seedu.address.model.person.ProfileImage;
import seedu.address.model.person.Resume;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                        PREFIX_UNIVERSITY, PREFIX_EXPECTED_GRADUATION_YEAR, PREFIX_MAJOR, PREFIX_GRADE_POINT_AVERAGE,
                        PREFIX_JOB_APPLIED, PREFIX_RESUME, PREFIX_IMAGE, PREFIX_COMMENT, PREFIX_TAG);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        try {
            ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME)).ifPresent(editPersonDescriptor::setName);
            ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE)).ifPresent(editPersonDescriptor::setPhone);
            ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL)).ifPresent(editPersonDescriptor::setEmail);
            ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS)).ifPresent(editPersonDescriptor::setAddress);
            ParserUtil.parseUniversity(argMultimap.getValue(PREFIX_UNIVERSITY))
                    .ifPresent(editPersonDescriptor::setUniversity);
            ParserUtil.parseExpectedGraduationYear(argMultimap.getValue(PREFIX_EXPECTED_GRADUATION_YEAR))
                    .ifPresent(editPersonDescriptor::setExpectedGraduationYear);
            ParserUtil.parseMajor(argMultimap.getValue(PREFIX_MAJOR)).ifPresent(editPersonDescriptor::setMajor);
            ParserUtil.parseGradePointAverage(argMultimap.getValue(PREFIX_GRADE_POINT_AVERAGE))
                    .ifPresent(editPersonDescriptor::setGradePointAverage);
            ParserUtil.parseJobApplied(argMultimap.getValue(PREFIX_JOB_APPLIED))
                    .ifPresent(editPersonDescriptor::setJobApplied);

            Optional<Resume> parsedResume = parseResumeForEdit(argMultimap.getValue(PREFIX_RESUME));
            if (parsedResume.isPresent()) {
                Resume processedResume = ResumeUtil.process(parsedResume.get());
                editPersonDescriptor.setResume(processedResume);
            }
            Optional<ProfileImage> parsedProfileImage = parseProfileImageForEdit(argMultimap.getValue(PREFIX_IMAGE));
            if (parsedProfileImage.isPresent()) {
                ProfileImage processedProfileImage = ProfileImageUtil.process(parsedProfileImage.get());
                editPersonDescriptor.setProfileImage(processedProfileImage);
            }
            parseCommentForEdit(argMultimap.getValue(PREFIX_COMMENT)).ifPresent(editPersonDescriptor::setComment);
            parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editPersonDescriptor::setTags);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        } catch (IOException ioe) {
            throw new ParseException(ioe.getMessage(), ioe);
        }

        if (!editPersonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editPersonDescriptor);
    }
    //@@author mhq199657
    /**
     * Parses {@code Optional<Resume> resume} into a {@code Optional<Resume>} if {@code resume} is non-empty.
     * If resume is present and equals to empty string, it will be parsed into a
     * {@code Resume} containing null value.
     */
    private Optional<Resume> parseResumeForEdit(Optional<String> resume) throws IllegalValueException {
        assert resume != null;
        if (!resume.isPresent()) {
            return Optional.empty();
        }
        if (resume.get().equals("")) {
            return Optional.of(new Resume(null));
        } else {
            return ParserUtil.parseResume(resume);
        }
    }

    //@@author Ang-YC
    /**
     * Parses {@code Optional<ProfileImage> profileImage} into a {@code Optional<ProfileImage>}
     * if {@code profileImage} is non-empty.
     * If profile image is present and equals to empty string, it will be parsed into a
     * {@code ProfileImage} containing null value.
     */
    private Optional<ProfileImage> parseProfileImageForEdit(Optional<String> profileImage)
            throws IllegalValueException {
        assert profileImage != null;
        if (!profileImage.isPresent()) {
            return Optional.empty();
        }
        if (profileImage.get().equals("")) {
            return Optional.of(new ProfileImage(null));
        } else {
            return ParserUtil.parseProfileImage(profileImage);
        }
    }

    /**
     * Parses {@code Optional<Comment> comment} into a {@code Optional<Comment>} if {@code comment} is non-empty.
     */
    private Optional<Comment> parseCommentForEdit(Optional<String> comment) throws IllegalValueException {
        assert comment != null;
        if (!comment.isPresent()) {
            return Optional.empty();
        }
        if (comment.get().equals("")) {
            return Optional.of(new Comment(null));
        } else {
            return ParserUtil.parseComment(comment);
        }
    }
    //@@author

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws IllegalValueException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

}
