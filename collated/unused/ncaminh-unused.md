# ncaminh-unused
###### \GameCommand.java
``` java
/**
 * Show game on to the display panel
 */
public class GameCommand extends Command {

    public static final String COMMAND_WORD = "game";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Play \"The snake\" game.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_GAME_MESSAGE = "Opened \"The snake\" game.";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new GameEvent());
        return new CommandResult(SHOWING_GAME_MESSAGE);
    }
}
```
###### \GameCommandTest.java
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
###### \games\Snake.html
``` html
<canvas id="gc" width="400" height="400"></canvas>
<script>
gc.style.left = "300px";
gc.style.top = "20px";
gc.style.position = "absolute";

window.onload=function() {
	canv=document.getElementById("gc");
	ctx=canv.getContext("2d");
	document.addEventListener("keydown",keyPush);
	setInterval(game,1000/15);
}
px=py=10;
gs=tc=20;
ax=ay=15;
xv=yv=0;
trail=[];
tail = 5;
function game() {
	px+=xv;
	py+=yv;
	if(px<0) {
		px= tc-1;
	}
	if(px>tc-1) {
		px= 0;
	}
	if(py<0) {
		py= tc-1;
	}
	if(py>tc-1) {
		py= 0;
	}
	ctx.fillStyle="black";
	ctx.fillRect(0,0,canv.width,canv.height);

	ctx.fillStyle="lime";
	for(var i=0;i<trail.length;i++) {
		ctx.fillRect(trail[i].x*gs,trail[i].y*gs,gs-2,gs-2);
		if(trail[i].x==px && trail[i].y==py) {
			tail = 5;
		}
	}
	trail.push({x:px,y:py});
	while(trail.length>tail) {
	trail.shift();
	}

	if(ax==px && ay==py) {
		tail++;
		ax=Math.floor(Math.random()*tc);
		ay=Math.floor(Math.random()*tc);
	}
	ctx.fillStyle="red";
	ctx.fillRect(ax*gs,ay*gs,gs-2,gs-2);
}
function keyPush(evt) {
	switch(evt.keyCode) {
		case 37:
			xv=-1;yv=0;
			break;
		case 38:
			xv=0;yv=-1;
			break;
		case 39:
			xv=1;yv=0;
			break;
		case 40:
			xv=0;yv=1;
			break;
	}
}
</script>
```
