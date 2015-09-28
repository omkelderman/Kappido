package nl.dare2date.kappido.twitch;

import java.util.List;

/**
 * Created by Maarten on 28-Sep-15.
 */
public interface ITwitchAPIWrapper {
    public List<ITwitchUser> getFollowingUsers(String twitchId);

    public ITwitchUser getUser(String twitchId);
}
