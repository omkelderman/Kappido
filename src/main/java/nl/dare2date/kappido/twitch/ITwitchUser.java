package nl.dare2date.kappido.twitch;

import java.util.List;

/**
 * Created by Maarten on 28-Sep-15.
 */
public interface ITwitchUser {
    String getTwitchId();

    String getLastPlayedGame();

    List<ITwitchUser> getFollowingUsers();
}
