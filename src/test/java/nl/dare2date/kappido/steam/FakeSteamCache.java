package nl.dare2date.kappido.steam;

import nl.dare2date.kappido.common.IUserCache;

/**
 * Created by Maarten on 6-10-2015.
 */
public class FakeSteamCache implements IUserCache<SteamUser> {
    private final SteamAPIWrapper apiWrapper;

    public FakeSteamCache(SteamAPIWrapper apiWrapper) {
        this.apiWrapper = apiWrapper;
    }

    @Override
    public SteamUser getUserById(String id) {
        return new SteamUser(id, apiWrapper);
    }

    @Override
    public void addToCache(SteamUser steamUser, String id) {

    }
}
