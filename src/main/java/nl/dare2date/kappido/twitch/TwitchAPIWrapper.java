package nl.dare2date.kappido.twitch;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maarten on 28-Sep-15.
 */
public class TwitchAPIWrapper implements ITwitchAPIWrapper {

    private static final String FOLLOWING_USERS_URL = "https://api.twitch.tv/kraken/users/%s/follows/channels?direction=DESC&limit=100&offset=%s&sortby=created_at";
    private static final String GET_CHANNEL_URL = "https://api.twitch.tv/kraken/channels/%s";

    @Override
    public List<ITwitchUser> getFollowingUsers(String twitchId) {
        try {
            List<ITwitchUser> followingUsers = new ArrayList<>();
            for(int i = 0; ; i+= 100){
                JsonObject root = getJsonForPath(String.format(FOLLOWING_USERS_URL, twitchId, i));
                JsonArray followingUserArray = root.get("follows").getAsJsonArray();
                for (JsonElement userElement : followingUserArray) {
                    JsonObject channel = userElement.getAsJsonObject().get("channel").getAsJsonObject();
                    followingUsers.add(getUserForChannelObject(channel));
                }
                if(followingUserArray.size() < 100) break; //When this page contains less entries than the amount we asked for, we're done requesting.
            }
            return followingUsers;
        } catch (IOException e) {
            throw new RuntimeException(e);//TODO handle properly
        }
    }

    public ITwitchUser getUser(String twitchId){
        try {
            JsonObject root = getJsonForPath(String.format(GET_CHANNEL_URL, twitchId));
            return getUserForChannelObject(root);
        } catch (IOException e) {
            throw new RuntimeException(e);//TODO handle properly
        }
    }

    private JsonObject getJsonForPath(String path) throws IOException{
        URL url = new URL(path);
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        JsonObject root =  new JsonParser().parse(reader).getAsJsonObject();
        reader.close(); //TODO close stream properly when exception thrown
        return root;
    }

    private ITwitchUser getUserForChannelObject(JsonObject channel){
        String followingTwitchId = channel.get("name").getAsString();
        JsonElement gameElement = channel.get("game");
        String lastPlayedGame = gameElement.isJsonNull() ? null : gameElement.getAsString();
        return new TwitchUser(followingTwitchId, lastPlayedGame);
    }
}