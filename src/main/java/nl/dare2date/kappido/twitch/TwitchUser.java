package nl.dare2date.kappido.twitch;

/**
 * Created by Maarten on 28-Sep-15.
 */
public class TwitchUser implements ITwitchUser {

    private final String twitchId;
    private String lastPlayedGame;

    public TwitchUser(String twitchId){
        this.twitchId = twitchId.toLowerCase();
    }

    public TwitchUser(String twitchId, String lastPlayedGame){
        this.twitchId = twitchId;
        this.lastPlayedGame = lastPlayedGame;
    }

    @Override
    public String getTwitchId() {
        return twitchId;
    }

    public String getLastPlayedGame(){
        //TODO request from Twitch API when not initialized.
        return lastPlayedGame;
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
