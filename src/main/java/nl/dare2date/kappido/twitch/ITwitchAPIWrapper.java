package nl.dare2date.kappido.twitch;

import java.util.List;

/**
 * Created by Maarten on 28-Sep-15.
 */
public interface ITwitchAPIWrapper {
    List<ITwitchUser> getFollowingUsers(String twitchId);

    ITwitchUser getUser(String twitchId);
}
