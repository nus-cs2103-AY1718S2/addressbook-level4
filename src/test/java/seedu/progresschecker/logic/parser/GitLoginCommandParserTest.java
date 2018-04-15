package seedu.progresschecker.logic.parser;

import static seedu.progresschecker.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.progresschecker.logic.commands.CommandTestUtil.GITHUB_DESC_PASSCODE_ONE;
import static seedu.progresschecker.logic.commands.CommandTestUtil.GITHUB_DESC_PASSCODE_TWO;
import static seedu.progresschecker.logic.commands.CommandTestUtil.GITHUB_DESC_REPO_ONE;
import static seedu.progresschecker.logic.commands.CommandTestUtil.GITHUB_DESC_REPO_TWO;
import static seedu.progresschecker.logic.commands.CommandTestUtil.GITHUB_DESC_USERNAME_ONE;
import static seedu.progresschecker.logic.commands.CommandTestUtil.GITHUB_DESC_USERNAME_TWO;
import static seedu.progresschecker.logic.commands.CommandTestUtil.INVALID_GITHUB_PASSCODE_DESC;
import static seedu.progresschecker.logic.commands.CommandTestUtil.INVALID_GITHUB_REPO_DESC;
import static seedu.progresschecker.logic.commands.CommandTestUtil.INVALID_GITHUB_USERNAME_DESC;
import static seedu.progresschecker.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.progresschecker.logic.commands.CommandTestUtil.VALID_GITHUB_PASSCODE_ONE;
import static seedu.progresschecker.logic.commands.CommandTestUtil.VALID_GITHUB_REPO_ONE;
import static seedu.progresschecker.logic.commands.CommandTestUtil.VALID_GITHUB_USERNAME_ONE;
import static seedu.progresschecker.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.progresschecker.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.progresschecker.logic.commands.GitLoginCommand;
import seedu.progresschecker.model.credentials.GitDetails;
import seedu.progresschecker.model.credentials.Passcode;
import seedu.progresschecker.model.credentials.Repository;
import seedu.progresschecker.model.credentials.Username;
import seedu.progresschecker.testutil.GitDetailsBuilder;

public class GitLoginCommandParserTest {
    private GitLoginCommandParser parser = new GitLoginCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        GitDetails expectedGitDetails = new GitDetailsBuilder().withRepository(VALID_GITHUB_REPO_ONE)
                .withUsername(VALID_GITHUB_USERNAME_ONE)
                .withPasscode(VALID_GITHUB_PASSCODE_ONE).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + GITHUB_DESC_REPO_ONE + GITHUB_DESC_USERNAME_ONE
                        + GITHUB_DESC_PASSCODE_ONE,
                new GitLoginCommand(expectedGitDetails));

        // multiple repos - last repo accepted
        assertParseSuccess(parser, GITHUB_DESC_REPO_TWO + GITHUB_DESC_REPO_ONE
                        + GITHUB_DESC_PASSCODE_ONE + GITHUB_DESC_USERNAME_ONE,
                new GitLoginCommand(expectedGitDetails));

        // multiple passcodes - last body accepted
        assertParseSuccess(parser, GITHUB_DESC_REPO_ONE + GITHUB_DESC_PASSCODE_TWO
                        + GITHUB_DESC_PASSCODE_ONE + GITHUB_DESC_USERNAME_ONE,
                new GitLoginCommand(expectedGitDetails));

        // multiple username - last username accepted
        assertParseSuccess(parser, GITHUB_DESC_REPO_ONE + GITHUB_DESC_PASSCODE_ONE
                        + GITHUB_DESC_USERNAME_TWO + GITHUB_DESC_USERNAME_ONE,
                new GitLoginCommand(expectedGitDetails));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, GitLoginCommand.MESSAGE_USAGE);

        //missing repository
        assertParseFailure(parser, GITHUB_DESC_USERNAME_ONE + GITHUB_DESC_PASSCODE_ONE, expectedMessage);

        //missing username
        assertParseFailure(parser, GITHUB_DESC_REPO_ONE + GITHUB_DESC_PASSCODE_ONE, expectedMessage);

        //missing passcode
        assertParseFailure(parser, GITHUB_DESC_USERNAME_ONE + GITHUB_DESC_REPO_ONE, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {

        //invalid repository
        assertParseFailure(parser, INVALID_GITHUB_REPO_DESC + GITHUB_DESC_USERNAME_ONE + GITHUB_DESC_PASSCODE_ONE,
                Repository.MESSAGE_REPOSITORY_CONSTRAINTS);

        //invalid username
        assertParseFailure(parser, GITHUB_DESC_REPO_ONE + INVALID_GITHUB_USERNAME_DESC + GITHUB_DESC_PASSCODE_ONE,
                Username.MESSAGE_GITUSERNAME_CONSTRAINTS);

        //invalid passcode
        assertParseFailure(parser, GITHUB_DESC_REPO_ONE + GITHUB_DESC_USERNAME_ONE + INVALID_GITHUB_PASSCODE_DESC,
                Passcode.MESSAGE_PASSCODE_CONSTRAINTS);

    }
}

