package nl.dare2date.kappido.twitch;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import nl.dare2date.kappido.common.IURLResourceProvider;
import nl.dare2date.kappido.common.JsonAPIWrapper;
import nl.dare2date.kappido.common.URLResourceProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maarten on 28-Sep-15.
 */
public class TwitchAPIWrapper extends JsonAPIWrapper implements ITwitchAPIWrapper {

    private static final String FOLLOWING_USERS_URL = "https://api.twitch.tv/kraken/users/%s/follows/channels?direction=DESC&limit=100&offset=%s&sortby=created_at";
    private static final String GET_CHANNEL_URL = "https://api.twitch.tv/kraken/channels/%s";

    public TwitchAPIWrapper() {
        this(new URLResourceProvider());
    }

    public TwitchAPIWrapper(IURLResourceProvider urlResourceProvider) {
        super(urlResourceProvider);
    }

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
                if(i + 100 >= root.get("_total").getAsInt()) break; //When we've read all users, we're done requesting.
            }
            return followingUsers;
        } catch (IOException e) {
            throw new RuntimeException(e);//TODO handle properly
        }
    }

    public ITwitchUser getUser(String twitchId) {
        try {
            JsonObject root = getJsonForPath(String.format(GET_CHANNEL_URL, twitchId));
            return getUserForChannelObject(root);
        } catch (IOException e) {
            throw new RuntimeException(e);//TODO handle properly
        }
    }

    private ITwitchUser getUserForChannelObject(JsonObject channel){
        String followingTwitchId = channel.get("name").getAsString();
        JsonElement gameElement = channel.get("game");
        String lastPlayedGame = gameElement.isJsonNull() ? null : gameElement.getAsString();
        return new TwitchUser(followingTwitchId, lastPlayedGame);
    }
}
