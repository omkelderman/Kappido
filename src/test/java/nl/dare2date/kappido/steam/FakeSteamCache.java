package nl.dare2date.kappido.steam;

import nl.dare2date.kappido.common.IUserCache;

/**
 * Created by Maarten on 6-10-2015.
 */
public class FakeSteamCache implements IUserCache<ISteamUser> {
    private final ISteamAPIWrapper apiWrapper;

    public FakeSteamCache(ISteamAPIWrapper apiWrapper) {
        this.apiWrapper = apiWrapper;
    }

    @Override
    public ISteamUser getUserById(String id) {
        return new SteamUser(id, apiWrapper);
    }

    @Override
    public void addToCache(ISteamUser steamUser, String id) {

    }
}
