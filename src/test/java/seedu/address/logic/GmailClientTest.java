package seedu.address.logic;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
//@@author KevinCJH
public class GmailClientTest {

    @Test
    public void equals() {
        GmailClient client1 = GmailClient.getInstance();
        GmailClient client2 = GmailClient.getInstance();

        assertTrue(client1.equals(client2));
    }

}
