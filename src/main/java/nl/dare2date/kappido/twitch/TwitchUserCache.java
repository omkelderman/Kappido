package nl.dare2date.kappido.twitch;

import nl.dare2date.kappido.common.AbstractUserCache;

/**
 * Created by Maarten on 6-10-2015.
 */
public class TwitchUserCache extends AbstractUserCache<ITwitchUser> {

    private ITwitchAPIWrapper apiWrapper;

    public TwitchUserCache(ITwitchAPIWrapper apiWrapper) {
        this.apiWrapper = apiWrapper;
    }

    @Override
    protected ITwitchUser createNewUser(String id) {
        return new TwitchUser(id, apiWrapper);
    }
}
