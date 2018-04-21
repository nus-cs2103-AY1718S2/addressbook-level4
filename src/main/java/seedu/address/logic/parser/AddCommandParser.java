package seedu.address.logic.parser;

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
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Comment;
import seedu.address.model.person.Email;
import seedu.address.model.person.ExpectedGraduationYear;
import seedu.address.model.person.GradePointAverage;
import seedu.address.model.person.InterviewDate;
import seedu.address.model.person.JobApplied;
import seedu.address.model.person.Major;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ProfileImage;
import seedu.address.model.person.Rating;
import seedu.address.model.person.Resume;
import seedu.address.model.person.Status;
import seedu.address.model.person.University;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                        PREFIX_UNIVERSITY, PREFIX_EXPECTED_GRADUATION_YEAR, PREFIX_MAJOR, PREFIX_GRADE_POINT_AVERAGE,
                        PREFIX_JOB_APPLIED, PREFIX_RESUME, PREFIX_IMAGE, PREFIX_COMMENT, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap,
                PREFIX_NAME, PREFIX_ADDRESS, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_UNIVERSITY,
                PREFIX_EXPECTED_GRADUATION_YEAR, PREFIX_MAJOR, PREFIX_GRADE_POINT_AVERAGE, PREFIX_JOB_APPLIED)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        try {
            // Compulsory fields
            Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME)).get();
            Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE)).get();
            Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL)).get();
            Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS)).get();
            University university = ParserUtil.parseUniversity(argMultimap.getValue(PREFIX_UNIVERSITY)).get();
            ExpectedGraduationYear expectedGraduationYear = ParserUtil.parseExpectedGraduationYear(argMultimap
                    .getValue(PREFIX_EXPECTED_GRADUATION_YEAR)).get();
            Major major = ParserUtil.parseMajor(argMultimap.getValue(PREFIX_MAJOR)).get();
            GradePointAverage gradePointAverage = ParserUtil.parseGradePointAverage(argMultimap
                    .getValue(PREFIX_GRADE_POINT_AVERAGE)).get();
            JobApplied jobApplied = ParserUtil.parseJobApplied(argMultimap.getValue(PREFIX_JOB_APPLIED)).get();

            // Optional fields
            Optional<Resume> resumeOptional = ParserUtil.parseResume(argMultimap.getValue(PREFIX_RESUME));
            Resume resume = resumeOptional.isPresent() ? resumeOptional.get() : new Resume(null);
            resume = ResumeUtil.process(resume);
            Optional<ProfileImage> profileImageOptional =
                    ParserUtil.parseProfileImage(argMultimap.getValue(PREFIX_IMAGE));
            ProfileImage profileImage = profileImageOptional.isPresent()
                    ? profileImageOptional.get() : new ProfileImage(null);
            profileImage = ProfileImageUtil.process(profileImage);

            Optional<Comment> commentOptional =
                    ParserUtil.parseComment(argMultimap.getValue(PREFIX_COMMENT));
            Comment comment = commentOptional.isPresent()
                    ? commentOptional.get() : new Comment(null);

            // Default-valued fields
            Rating rating = new Rating(Rating.DEFAULT_SCORE, Rating.DEFAULT_SCORE,
                    Rating.DEFAULT_SCORE, Rating.DEFAULT_SCORE);
            InterviewDate interviewDate = new InterviewDate();
            Status status = new Status();

            // Other fields
            Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

            Person person = new Person(name, phone, email, address, university, expectedGraduationYear, major,
                    gradePointAverage, jobApplied, rating, resume, profileImage, comment, interviewDate,
                    status, tagList);
            return new AddCommand(person);

        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        } catch (IOException ioe) {
            throw new ParseException(ioe.getMessage(), ioe);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
