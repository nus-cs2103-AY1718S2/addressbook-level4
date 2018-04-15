package seedu.address.commons.core;

import static seedu.address.commons.util.FetchUtil.parseFileToJsonObj;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import seedu.address.MainApp;
import seedu.address.model.coin.Coin;

/**
 * Represents a list of existing coin codes that have a Subreddit.
 */
//@@author Eldon-Chung
//Citation included here because .json files cannot be commented on.
//the list of subreddits CoinCodeToSubreddit.json was partially
//obtained from https://github.com/kendricktan/cryptoshitposting/blob/master/data/subreddits.json
public class CoinSubredditList {

    private static final Map<String, String> COIN_CODE_TO_SUBREDDIT_MAP = new HashMap<>();
    private static final String COIN_CODE_TO_SUBREDDIT_FILEPATH = "/coins/CoinCodeToSubreddit.json";
    private static final String REDDIT_URL = "https://www.reddit.com/r/";
    private static final String CODE_ATTRIBUTE_NAME = "code";
    private static final String SUBREDDIT_ATTRIBUTE_NAME = "subreddit";

    public static boolean isRecognized(Coin coin) {
        return COIN_CODE_TO_SUBREDDIT_MAP.containsKey(coin.getCode().toString());
    }

    /**
     * Obtains the subreddit url associated with the {@code coin}.
     * @param coin
     * @return the subreddit url associated with the {@code coin}
     */
    public static String getRedditUrl(Coin coin) {
        return REDDIT_URL + COIN_CODE_TO_SUBREDDIT_MAP.get(coin.getCode().toString());
    }

    /**
     * Initialises the CoinToSubredditList to store existing Coin subreddits
     * @throws FileNotFoundException if the file missing
     */
    public static void initialize() throws FileNotFoundException, URISyntaxException {
        InputStreamReader fileReader = new InputStreamReader(getCoinCodeToSubredditFilepath());
        JsonArray jsonArray = parseFileToJsonObj(fileReader);
        JsonElement codeString;
        JsonElement subredditName;

        for (JsonElement jsonElement : jsonArray) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            codeString = jsonObject.get(CODE_ATTRIBUTE_NAME);
            subredditName = jsonObject.get(SUBREDDIT_ATTRIBUTE_NAME);
            if (subredditName == null || subredditName.toString().equals("null")) {
                continue;
            }
            COIN_CODE_TO_SUBREDDIT_MAP.put(codeString.getAsString(), subredditName.getAsString());
        }
    }

    private static InputStream getCoinCodeToSubredditFilepath() throws URISyntaxException {
        return MainApp.class.getResourceAsStream(COIN_CODE_TO_SUBREDDIT_FILEPATH);
    }
}
