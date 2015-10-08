package nl.dare2date.kappido.steam;

import nl.dare2date.kappido.common.AbstractUserCache;

/**
 * Created by Maarten on 6-10-2015.
 */
public class SteamUserCache extends AbstractUserCache<ISteamUser> {
    private ISteamAPIWrapper apiWrapper;

    public SteamUserCache(ISteamAPIWrapper apiWrapper) {
        this.apiWrapper = apiWrapper;
    }

    @Override
    protected ISteamUser createNewUser(String id) {
        return new SteamUser(id, apiWrapper);
    }
}
