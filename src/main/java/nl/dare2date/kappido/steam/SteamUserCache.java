package nl.dare2date.kappido.steam;

import nl.dare2date.kappido.common.AbstractUserCache;

/**
 * Created by Maarten on 6-10-2015.
 */
public class SteamUserCache extends AbstractUserCache<SteamUser> {
    private SteamAPIWrapper apiWrapper;

    public SteamUserCache(SteamAPIWrapper apiWrapper) {
        this.apiWrapper = apiWrapper;
    }

    @Override
    protected SteamUser createNewUser(String id) {
        return new SteamUser(id, apiWrapper);
    }
}
