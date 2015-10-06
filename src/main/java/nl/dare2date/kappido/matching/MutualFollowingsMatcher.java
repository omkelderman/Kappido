package nl.dare2date.kappido.matching;

import nl.dare2date.kappido.common.IUserCache;
import nl.dare2date.kappido.services.MatchEntry;
import nl.dare2date.kappido.twitch.ITwitchUser;
import nl.dare2date.kappido.twitch.TwitchUser;
import nl.dare2date.profile.ID2DProfileManager;

import java.util.*;

/**
 * Created by Maarten on 6-10-2015.
 */
public class MutualFollowingsMatcher extends TwitchMatcher {
    public MutualFollowingsMatcher(ID2DProfileManager profileManager, IUserCache<TwitchUser> twitchUserCache) {
        super(profileManager, twitchUserCache);
    }

    @Override
    protected List<MatchEntry> findMatches(int dare2DateUser, ITwitchUser twitchUser, Map<Integer, ITwitchUser> twitchDare2DateUsers){
        List<MatchEntry> matches = new ArrayList<>();

        Set<ITwitchUser> followingUsers = new HashSet<>(twitchUser.getFollowingUsers());
        for(Map.Entry<Integer, ITwitchUser> otherTwitchUser : twitchDare2DateUsers.entrySet()){
            if(otherTwitchUser.getKey() != dare2DateUser) { //We can't match with ourselves..
                for (ITwitchUser user : otherTwitchUser.getValue().getFollowingUsers()) {
                    if (followingUsers.contains(user)) {
                        MatchEntry entry = new MatchEntry();
                        entry.setUserId(otherTwitchUser.getKey());
                        entry.setProbability(1); //It could be that multiple shared following users, in that case the probabilities will be combined in MatchMaker.
                        matches.add(entry);
                    }
                }
            }
        }

        return matches;
    }
}
