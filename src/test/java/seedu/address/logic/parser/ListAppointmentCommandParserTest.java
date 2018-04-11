package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;


import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.ListAppointmentCommand;

//@@author wynonaK
public class ListAppointmentCommandParserTest {
    private ListAppointmentCommandParser parser = new ListAppointmentCommandParser();

    @Test
    public void parse_fieldsExist_success() {
        assertParseSuccess(parser, " -y 2018 ", new ListAppointmentCommand(1, Year.of(2018)));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        assertParseSuccess(parser, " -m 2018-12 ",
                new ListAppointmentCommand(2, YearMonth.parse("2018-12", formatter)));

        YearMonth date = YearMonth.now().withMonth(12);
        assertParseSuccess(parser, " -m 12 ",
                new ListAppointmentCommand(2, date));

        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        assertParseSuccess(parser, " -w 2018-12-31 ",
                new ListAppointmentCommand(3, LocalDate.parse("2018-12-31", formatter)));


        assertParseSuccess(parser, " -d 2018-12-31 ",
                new ListAppointmentCommand(4, LocalDate.parse("2018-12-31", formatter)));
    }

    @Test
    public void parse_fieldsAbsentButOptionExist_success() {
        assertParseSuccess(parser, " -y ",
                new ListAppointmentCommand(1, Year.now()));

        assertParseSuccess(parser, " -m ",
                new ListAppointmentCommand(2, YearMonth.now()));

        assertParseSuccess(parser, " -w ",
                new ListAppointmentCommand(3, LocalDate.now()));

        assertParseSuccess(parser, " -d ",
                new ListAppointmentCommand(4, LocalDate.now()));
    }

    @Test
    public void parse_optionsAbsent_failure() {
        assertParseFailure(parser, " ",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ListAppointmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_optionsInvalid_failure() {
        assertParseFailure(parser, " -ajsdbiuaeih ",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ListAppointmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_fieldsInvalid_failure() {
        assertParseFailure(parser, " -y jadfoijnoiem ",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ListAppointmentCommand.MESSAGE_USAGE));

        assertParseFailure(parser, " -m jadfoijnoiem ",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ListAppointmentCommand.MESSAGE_USAGE));

        assertParseFailure(parser, " -w jadfoijnoiem ",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ListAppointmentCommand.MESSAGE_USAGE));

        assertParseFailure(parser, " -d jadfoijnoiem ",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ListAppointmentCommand.MESSAGE_USAGE));
    }

}
