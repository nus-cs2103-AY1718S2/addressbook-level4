package seedu.address.network;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.address.model.person.TimeTableLink;


public class NetworkManagerTest {

    @Test
    public void getQuery() {
        NetworkManager test = new NetworkManager();
        String actual = test.getQuery(new TimeTableLink("http://modsn.us/MYwiD"));
        assertEquals("CS2101=SEC:3&CS2103T=TUT:T3&CS2105=LEC:1,TUT:7&CS3242=LEC:1,TUT:3&ST2334=LEC:SL1,TUT:T4",
                actual);
    }
}
