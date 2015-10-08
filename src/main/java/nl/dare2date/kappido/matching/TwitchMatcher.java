package nl.dare2date.kappido.matching;

import nl.dare2date.kappido.common.IUserCache;
import nl.dare2date.kappido.services.MatchEntry;
import nl.dare2date.kappido.twitch.ITwitchUser;
import nl.dare2date.profile.ID2DProfileManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Maarten on 6-10-2015.
 */
public abstract class TwitchMatcher implements IMatcher {
    protected ID2DProfileManager profileManager;
    protected IUserCache<ITwitchUser> twitchUserCache;

    public TwitchMatcher(ID2DProfileManager profileManager, IUserCache<ITwitchUser> twitchUserCache) {
        this.profileManager = profileManager;
        this.twitchUserCache = twitchUserCache;
    }

    @Override
    public List<MatchEntry> findMatches(int dare2DateUser) {
        String twitchId = profileManager.getTwitchId(dare2DateUser);
        if (twitchId == null) throw new IllegalStateException("No Twitch Id for user " + dare2DateUser);

        ITwitchUser twitchUser = twitchUserCache.getUserById(twitchId);
        if (twitchUser == null) throw new IllegalStateException("No Twitch user for Twitch Id " + twitchId);

        return findMatches(dare2DateUser, twitchUser, getTwitchDare2DateUsers());
    }

    private Map<Integer, ITwitchUser> getTwitchDare2DateUsers() {
        Map<Integer, ITwitchUser> userMap = new HashMap<>();
        for (int dare2DateUser : profileManager.getAllUsers()) {
            String twitchId = profileManager.getTwitchId(dare2DateUser);
            if (twitchId != null) {
                ITwitchUser twitchUser = twitchUserCache.getUserById(twitchId);
                if (twitchUser != null) {
                    userMap.put(dare2DateUser, twitchUser);
                }
            }
        }
        return userMap;
    }

    /**
     * @param dare2DateUser
     * @param twitchUser           Steam user object belonging to the dare2DateUser issuing the match.
     * @param twitchDare2DateUsers Key value map with the keys being dare2date user id's, and their Twitch user object as value.
     * @return
     */
    protected abstract List<MatchEntry> findMatches(int dare2DateUser, ITwitchUser twitchUser, Map<Integer, ITwitchUser> twitchDare2DateUsers);
}
