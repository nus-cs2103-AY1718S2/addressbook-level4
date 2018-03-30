package seedu.address.model.util;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoField;

public class InterviewDateUtil {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("uuuuMMdd");
    private static final DateTimeFormatter LOWER_DATETIME_FORMATTER =
            new DateTimeFormatterBuilder().append(DATE_FORMATTER)
                    .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                    .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                    .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                    .toFormatter()
                    .withResolverStyle(ResolverStyle.STRICT);
    private static final DateTimeFormatter HIGHER_DATETIME_FORMATTER =
            new DateTimeFormatterBuilder().append(DATE_FORMATTER)
                    .parseDefaulting(ChronoField.HOUR_OF_DAY, 23)
                    .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 59)
                    .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 59)
                    .toFormatter()
                    .withResolverStyle(ResolverStyle.STRICT);
    public static final String MESSAGE_INTERVIEW_DATE_CONSTRAINT =
            "Interview date should be of the format yyyyMMdd. Example: 20170323. Month and day must be valid.";

    public static boolean isValidInterviewDate(String test) {
        requireNonNull(test);
        return test.matches("^[0-9]{8}$");
    }

    public static LocalDateTime formLowerInterviewDateTime(String yyyyMMdd) throws DateTimeParseException {
        return LocalDateTime.parse(yyyyMMdd, LOWER_DATETIME_FORMATTER);
    }
    public static LocalDateTime formHigherInterviewDateTime(String yyyyMMdd) throws DateTimeParseException{
        return LocalDateTime.parse(yyyyMMdd, HIGHER_DATETIME_FORMATTER);
    }
}
