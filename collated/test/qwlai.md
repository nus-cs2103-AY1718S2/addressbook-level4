# qwlai
###### \java\seedu\address\logic\commands\AddScreeningCommandTest.java
``` java
public class AddScreeningCommandTest {

    private static final int VALID_THEATER_NUMBER = 1;
    private static final String VALID_DATE_TIME = "11/11/2015 11:10";

    private static final int INVALID_THEATER_NUMBER = 5;

    private static final String DATE_TIME_FORMAT = "dd/MM/uuuu HH:mm";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model;
    private DateTimeFormatter dtf;
    private AddScreeningCommand addScreeningCommand;

    @Before
    public void setUp() throws Exception {

        model = new ModelManager(getTypicalMoviePlanner(), new UserPrefs(), new EmailManager());
        model.addMovie(TypicalMovies.BLACK_PANTHER);
        model.addMovie(TypicalMovies.THOR_RAGNAROK);
        dtf = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT).withResolverStyle(ResolverStyle.STRICT);
    }

    @Test
    public void constructor_nullFields_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddScreeningCommand(null, null, 0, null);
    }

    @Test
    public void execute_invalidIndexCinemaFilteredList_throwsCommandException() throws Exception {
        showCinemaAtIndex(model, INDEX_FIRST_CINEMA);
        Index outOfBoundIndex = INDEX_THIRD_CINEMA;

        // ensures that outOfBoundIndex is still in bounds of movie planner list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getMoviePlanner().getCinemaList().size());
        addScreeningCommand = prepareCommand(INDEX_FIRST_MOVIE, outOfBoundIndex,
                VALID_THEATER_NUMBER, getDateTime(VALID_DATE_TIME));
        assertCommandFailure(addScreeningCommand, model, Messages.MESSAGE_INVALID_CINEMA_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexMovieFilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = INDEX_THIRD_MOVIE;
        addScreeningCommand = prepareCommand(outOfBoundIndex, INDEX_FIRST_CINEMA,
                VALID_THEATER_NUMBER, getDateTime(VALID_DATE_TIME));
        assertCommandFailure(addScreeningCommand, model, Messages.MESSAGE_INVALID_MOVIE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidTheaterNumber_throwsCommandException() throws Exception {
        addScreeningCommand = prepareCommand(INDEX_FIRST_MOVIE, INDEX_FIRST_CINEMA,
                INVALID_THEATER_NUMBER, getDateTime(VALID_DATE_TIME));
        assertCommandFailure(addScreeningCommand, model, Messages.MESSAGE_INVALID_THEATER_NUMBER);
    }

    @Test
    public void execute_screeningDateBeforeMovieReleaseDate_throwsCommandException() throws Exception {
        StartDate firstMovieDate = model.getFilteredMovieList().get(INDEX_FIRST_MOVIE.getZeroBased()).getStartDate();
        LocalDateTime invalidScreenDate = getDateTime(firstMovieDate.toString() + " 10:00").minusDays(1);
        addScreeningCommand = prepareCommand(INDEX_FIRST_MOVIE, INDEX_FIRST_CINEMA,
                VALID_THEATER_NUMBER, invalidScreenDate);
        assertCommandFailure(addScreeningCommand, model, Messages.MESSAGE_INVALID_SCREENING);
    }

    /**
     * Parses datetime and returns a valid LocalDateTime object
     */
    private LocalDateTime getDateTime(String dateTime) {
        return LocalDateTime.parse(dateTime, dtf);
    }

    /**
     * Prepares an addScreeningCommand
     */
    private AddScreeningCommand prepareCommand(Index movieIndex, Index cinemaIndex,
                                               int theaterNumber, LocalDateTime screeningDateTime) {
        AddScreeningCommand command =
                new AddScreeningCommand(movieIndex, cinemaIndex, theaterNumber, screeningDateTime);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
```
###### \java\seedu\address\logic\parser\ParserUtilTest.java
``` java
    @Test
    public void parseTheaterNumber_null_throwsNullPointerException() throws Exception {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseTheaterNumber((String) null));
    }

    @Test
    public void parseTheaterNumber_validValueWithWhitespace_returnsTheaterNumber() throws Exception {
        String theaterNumberWithWhitespace = WHITESPACE + VALID_THEATER + WHITESPACE;
        assertEquals(VALID_THEATER, parseTheaterNumber(theaterNumberWithWhitespace));
    }

    @Test
    public void parseTheaterNumber_invalidValue_throwsIllegalValueException() throws Exception {
        Assert.assertThrows(IllegalValueException.class, () ->
                ParserUtil.parseTheaterNumber(String.valueOf(INVALID_THEATER)));
    }

    @Test
    public void parseScreeningDateTime_null_throwsNullPointerException() throws Exception {
        Assert.assertThrows(NullPointerException.class, () ->
                ParserUtil.parseScreeningDateTime((String) null));
    }

    @Test
    public void parseScreeningDateTime_invalidDateWithoutTime_throwsDateTimeParseException() throws Exception {
        Assert.assertThrows(DateTimeParseException.class, () ->
                ParserUtil.parseScreeningDateTime(INVALID_DATE_WITHOUT_TIME));
    }

    @Test
    public void parseScreeningDateTime_invalidTimeWithoutDate_throwsDateTimeParseException() throws Exception {
        Assert.assertThrows(DateTimeParseException.class, () ->
                ParserUtil.parseScreeningDateTime(INVALID_TIME_WITHOUT_DATE));
    }

    @Test
    public void parseScreeningDateTime_invalidDateTimeFormat_throwsDateTimeParseException() throws Exception {
        Assert.assertThrows(DateTimeParseException.class, () ->
                ParserUtil.parseScreeningDateTime(INVALID_DATE_TIME_FORMAT_1));
        Assert.assertThrows(DateTimeParseException.class, () ->
                ParserUtil.parseScreeningDateTime(INVALID_DATE_TIME_FORMAT_2));
        Assert.assertThrows(DateTimeParseException.class, () ->
                ParserUtil.parseScreeningDateTime(INVALID_DATE_TIME_FORMAT_3));
        Assert.assertThrows(DateTimeParseException.class, () ->
                ParserUtil.parseScreeningDateTime(INVALID_DATE_TIME_FORMAT_4));
        Assert.assertThrows(DateTimeParseException.class, () ->
                ParserUtil.parseScreeningDateTime(INVALID_DATE_TIME_FORMAT_5));
        Assert.assertThrows(DateTimeParseException.class, () ->
                ParserUtil.parseScreeningDateTime(INVALID_DATE_TIME_FORMAT_6));
    }

    @Test
    public void parseScreeningDateTime_invalidTimeValue_throwsIllegalValueException() throws Exception {
        Assert.assertThrows(IllegalValueException.class, () ->
                ParserUtil.parseScreeningDateTime(INVALID_DATE_TIME_MINUTES));
    }

    @Test
    public void parseScreeningDateTime_validDateTime_returnsScreeningDateTime() throws Exception {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT).withResolverStyle(ResolverStyle.STRICT);
        LocalDateTime expectedScreeningDateTime = LocalDateTime.parse(VALID_DATE_TIME, dtf);
        assertEquals(expectedScreeningDateTime, ParserUtil.parseScreeningDateTime(VALID_DATE_TIME));
    }

    @Test
    public void parseScreeningDateTime_validDateTimeWithWhitespace_returnsScreeningDateTime() throws Exception {
        String screeningDateTimeWithWhitespace = WHITESPACE + VALID_DATE_TIME + WHITESPACE;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT).withResolverStyle(ResolverStyle.STRICT);
        LocalDateTime expectedScreeningDateTime = LocalDateTime.parse(VALID_DATE_TIME, dtf);
        assertEquals(expectedScreeningDateTime, ParserUtil.parseScreeningDateTime(screeningDateTimeWithWhitespace));
    }
}
```
###### \java\seedu\address\ui\MovieCardTest.java
``` java
public class MovieCardTest extends GuiUnitTest {

    private Movie movie;
    private MovieCard movieCard;

    @Before
    public void setUp() throws Exception {
        movie = new MovieBuilder().build();
        movieCard = new MovieCard(movie, 1);
    }

    @Test
    public void display_checkDetails_displayedCorrectly() {
        uiPartRule.setUiPart(movieCard);
        assertCardDisplay(movieCard, movie, 1);
    }

    @Test
    public void equals_sameNameSameIndex_returnTrue() {
        MovieCard copy = new MovieCard(movie, 1);
        assertTrue(movieCard.equals(copy));
    }

    @Test
    public void equals_checkNull_returnFalse() {
        assertFalse(movieCard.equals(null));
    }

    @Test
    public void equals_sameMovieCard_returnTrue() {
        assertTrue(movieCard.equals(movieCard));
    }

    @Test
    public void equals_differentMovieSameIndex_returnFalse() {
        Movie differentMovie = new MovieBuilder().withMovieName("differentName").build();
        assertFalse(movieCard.equals((new MovieCard(differentMovie, 1))));
    }

    @Test
    public void equals_sameMovieDifferentIndex_returnFalse() {
        assertFalse(movieCard.equals(new MovieCard(movie, 2)));
    }

    /**
     * Asserts that {@code movieCard} displays the details of {@code expectedMovie} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(MovieCard movieCard, Movie expectedMovie, int expectedId) {
        guiRobot.pauseForHuman();

        MovieCardHandle movieCardHandle = new MovieCardHandle(movieCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", movieCardHandle.getId());

        // verify movie details are displayed correctly
        assertCardDisplaysMovie(expectedMovie, movieCardHandle);
    }
}
```
###### \java\seedu\address\ui\MovieListPanelTest.java
``` java
public class MovieListPanelTest extends GuiUnitTest {
    private static final ObservableList<Movie> TYPICAL_MOVIES =
            FXCollections.observableList(getTypicalMovies());

    private MovieListPanelHandle movieListPanelHandle;

    @Before
    public void setUp() {
        MovieListPanel movieListPanel = new MovieListPanel(TYPICAL_MOVIES);
        uiPartRule.setUiPart(movieListPanel);

        movieListPanelHandle = new MovieListPanelHandle(getChildNode(movieListPanel.getRoot(),
                MovieListPanelHandle.MOVIE_LIST_VIEW_ID));
    }

    @Test
    public void display_cardMatches_returnTrue() {
        for (int i = 0; i < TYPICAL_MOVIES.size(); i++) {
            movieListPanelHandle.navigateToCard(TYPICAL_MOVIES.get(i));
            Movie expectedMovie = TYPICAL_MOVIES.get(i);
            MovieCardHandle actualCard = movieListPanelHandle.getMovieCardHandle(i);

            assertCardDisplaysMovie(expectedMovie, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }
}
```
