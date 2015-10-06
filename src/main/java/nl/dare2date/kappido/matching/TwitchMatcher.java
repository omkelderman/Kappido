package nl.dare2date.kappido.matching;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;
import nl.dare2date.kappido.common.IUserCache;
import nl.dare2date.kappido.services.MatchEntry;
import nl.dare2date.kappido.twitch.ITwitchUser;
import nl.dare2date.kappido.twitch.TwitchUser;
import nl.dare2date.profile.ID2DProfileManager;

import java.util.List;

/**
 * Created by Maarten on 6-10-2015.
 */
public abstract class TwitchMatcher implements IMatcher {
    protected ID2DProfileManager profileManager;
    protected IUserCache<TwitchUser> twitchUserCache;

    public TwitchMatcher(ID2DProfileManager profileManager, IUserCache<TwitchUser> twitchUserCache){
        this.profileManager = profileManager;
        this.twitchUserCache = twitchUserCache;
    }

    @Override
    public List<MatchEntry> findMatches(int dare2DateUser) {
        String twitchId = profileManager.getTwitchId(dare2DateUser);
        if(twitchId == null) throw new IllegalStateException("No Twitch Id for user " + dare2DateUser); //TODO handle differently?

        ITwitchUser twitchUser = twitchUserCache.getUserById(twitchId);
        if(twitchUser == null) throw new IllegalStateException("No Twitch user for Twitch Id " + twitchId); //TODO handle differently?

        return findMatches(dare2DateUser, twitchUser);
    }

    protected abstract List<MatchEntry> findMatches(int dare2DateUser, ITwitchUser twitchUser);
}
