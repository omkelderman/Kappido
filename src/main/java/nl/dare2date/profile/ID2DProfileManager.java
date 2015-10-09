package nl.dare2date.profile;

import java.util.Set;

/**
 * Created by Maarten on 05-Oct-15.
 * Dare2Date's profile manager. Used by the application to get required extra information about Dare2Date's users.
 * Would be backed by a database but is simulated with the {@link FakeD2DProfileManager}.
 */
public interface ID2DProfileManager {
    /**
     * Returns a Twitch Id for a certain Dare2Date user id.
     * @param dare2DateUserId
     * @return
     */
    String getTwitchId(int dare2DateUserId);

    /**
     * Returns a Steam Id  for a certain Dare2Date user id.
     * @param dare2DateUserId
     * @return
     */
    String getSteamId(int dare2DateUserId);

    /**
     * Returns a set with all Dare2Date user ids.
     * @return
     */
    Set<Integer> getAllUsers();
}
