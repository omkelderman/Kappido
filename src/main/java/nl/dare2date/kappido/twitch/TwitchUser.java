package nl.dare2date.kappido.twitch;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maarten on 28-Sep-15.
 */
public class TwitchUser implements ITwitchUser {

    private final TwitchAPIWrapper apiWrapper;
    private final String twitchId;
    private String lastPlayedGame;
    private List<ITwitchUser> followingUsers;


    TwitchUser(String twitchId, TwitchAPIWrapper apiWrapper){
        this.twitchId = twitchId.toLowerCase();
        this.apiWrapper = apiWrapper;
    }

    TwitchUser(String twitchId, TwitchAPIWrapper apiWrapper, String lastPlayedGame){
        this(twitchId, apiWrapper);
        this.lastPlayedGame = lastPlayedGame;
    }

    @Override
    public String getTwitchId() {
        return twitchId;
    }

    @Override
    public String getLastPlayedGame(){
        if(lastPlayedGame == null){
            return apiWrapper.getUser(twitchId).getLastPlayedGame(); //Retrieve from the API, which will put this new user to the user cache. Therefore this lastPlayedGame doesn't need to be set.
        }
        return lastPlayedGame;
    }

    @Override
    public List<ITwitchUser> getFollowingUsers(){
        if(followingUsers == null){
            followingUsers = apiWrapper.getFollowingUsers(twitchId);
        }
        return followingUsers;
    }

    public String toString(){
        return getTwitchId();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof TwitchUser)) return false;
        TwitchUser user = (TwitchUser) o;
        return twitchId.equals(user.twitchId);
    }

    @Override
    public int hashCode() {
        return twitchId.hashCode();
    }
}
