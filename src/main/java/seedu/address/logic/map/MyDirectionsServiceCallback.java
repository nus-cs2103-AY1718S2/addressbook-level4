package seedu.address.logic.map;

import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.service.directions.DirectionStatus;
import com.lynden.gmapsfx.service.directions.DirectionsResult;
import com.lynden.gmapsfx.service.directions.DirectionsServiceCallback;

/**
 *  Creates the required DirectionsServiceCallback by passing specified {@GoogleMap map}.
 */

public class MyDirectionsServiceCallback implements DirectionsServiceCallback {
    private GoogleMap map;
    public MyDirectionsServiceCallback(GoogleMap map) {
        this.map = map;
    }
    @Override
    public void directionsReceived(DirectionsResult results, DirectionStatus status) {
    }
}
