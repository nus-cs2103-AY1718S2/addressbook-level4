package seedu.address.logic;

//@@author lithiumlkid
import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.commands.CommandTrie;

public class AutocompleteTest {

    private CommandTrie commandTrie;

    @Before
    public void setup() {
        commandTrie = new CommandTrie();
    }

    @Test
    public void autocomplete_unique_prefix() {
        assert commandTrie.attemptAutoComplete("l").equals("list");
        assert commandTrie.attemptAutoComplete("he").equals("help");
    }

    @Test
    public void autocomplete_multiple_prefix() {
        assert commandTrie.attemptAutoComplete("e").equals("e");
        assert commandTrie.attemptAutoComplete("H").equals("H");

    }

    @Test
    public void autocomplete_mix_case() {
        assert commandTrie.attemptAutoComplete("setT").equals("setTagColour");
    }
}
