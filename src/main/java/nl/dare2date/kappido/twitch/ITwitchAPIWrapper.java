package nl.dare2date.kappido.twitch;

import nl.dare2date.kappido.common.IUserCacheable;

import java.util.List;

/**
 * Created by Maarten on 28-Sep-15.
 */
public interface ITwitchAPIWrapper extends IUserCacheable<ITwitchUser> {
    List<ITwitchUser> getFollowingUsers(String twitchId);

    ITwitchUser getUser(String twitchId);
}
