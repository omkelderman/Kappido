package nl.dare2date.profile;

import java.util.List;

/**
 * Created by Maarten on 05-Oct-15.
 */
public interface ID2DProfileManager {
    public String getTwitchId(int dare2DateUserId);

    public String getSteamId(int dare2DateUserId);

    public List<Integer> getAllUsers();
}
