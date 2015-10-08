package nl.dare2date.kappido.twitch;

import nl.dare2date.kappido.common.AbstractUserCache;

/**
 * Created by Maarten on 6-10-2015.
 */
public class TwitchUserCache extends AbstractUserCache<TwitchUser> {

    private TwitchAPIWrapper apiWrapper;

    public TwitchUserCache(TwitchAPIWrapper apiWrapper) {
        this.apiWrapper = apiWrapper;
    }

    @Override
    protected TwitchUser createNewUser(String id) {
        return new TwitchUser(id, apiWrapper);
    }
}
