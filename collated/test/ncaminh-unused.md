# ncaminh-unused
###### \java\seedu\address\logic\commands\GameCommandTest.java
``` java
public class GameCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_game_success() {
        CommandResult result = new GameCommand().execute();
        assertEquals(SHOWING_GAME_MESSAGE, result.feedbackToUser);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof GameEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }
}
```
