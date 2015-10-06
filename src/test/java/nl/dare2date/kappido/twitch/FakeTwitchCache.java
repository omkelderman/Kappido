package nl.dare2date.kappido.twitch;

import nl.dare2date.kappido.common.IUserCache;

/**
 * A Twitch User 'cache'. It doesn't cache the values at all, creating a nice stateless object for testing.
 */
public class FakeTwitchCache implements IUserCache<TwitchUser> {
    private TwitchAPIWrapper apiWrapper;

    public FakeTwitchCache(TwitchAPIWrapper apiWrapper){
        this.apiWrapper = apiWrapper;
    }

    @Override
    public TwitchUser getUserById(String id) {
        return new TwitchUser(id, apiWrapper);
    }

    @Override
    public void addToCache(TwitchUser twitchUser, String id) {

    }
}
