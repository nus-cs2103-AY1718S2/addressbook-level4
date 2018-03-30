package seedu.address.logic.parser;

import seedu.address.logic.commands.ExportContactsCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Class to parse the export_contacts command
 */
public class ExportContactsCommandParser implements Parser<ExportContactsCommand> {

    public static final String FAILED_TO_PARSE =
            "Failed to parse import_contacts command";

    /**
     * Parses the given {@code String} of arguments in the context of the ExportContactsCommand
     * and returns an ExportContactsCommand object for execution
     * Can recieve 1 or 0 arguments
     */
    @Override
    public ExportContactsCommand parse(String args) throws ParseException {
        System.out.println("args is:" + args);
        args = args.trim();

        return (args.length() > 1) ? new ExportContactsCommand(args) : new ExportContactsCommand();
    }
}
