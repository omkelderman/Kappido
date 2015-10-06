package nl.dare2date.kappido.matching;

import nl.dare2date.kappido.common.IUserCache;
import nl.dare2date.kappido.services.MatchEntry;
import nl.dare2date.kappido.twitch.ITwitchUser;
import nl.dare2date.kappido.twitch.TwitchUser;
import nl.dare2date.profile.ID2DProfileManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Maarten on 6-10-2015.
 */
public class MutualFollowingsMatcher extends TwitchMatcher {
    public MutualFollowingsMatcher(ID2DProfileManager profileManager, IUserCache<TwitchUser> twitchUserCache) {
        super(profileManager, twitchUserCache);
    }

    @Override
    protected List<MatchEntry> findMatches(int dare2DateUser, ITwitchUser twitchUser) {
        List<MatchEntry> matches = new ArrayList<>();

        Set<ITwitchUser> followingUsers = new HashSet<>(twitchUser.getFollowingUsers());
        for(int otherDare2DateUser : profileManager.getAllUsers()) {
            if (otherDare2DateUser != dare2DateUser) {
                String otherTwitchId = profileManager.getTwitchId(otherDare2DateUser);
                if (otherTwitchId != null) {
                    ITwitchUser otherTwitchUser = twitchUserCache.getUserById(otherTwitchId);
                    if (otherTwitchUser != null) {
                        for(ITwitchUser user : otherTwitchUser.getFollowingUsers()){
                            if(followingUsers.contains(user)){
                                MatchEntry entry = new MatchEntry();
                                entry.setUserId(otherDare2DateUser);
                                entry.setProbability(1); //It could be that multiple shared following users, in that case the probabilities will be combined in MatchMaker.
                                matches.add(entry);
                            }
                        }
                    }
                }
            }
        }

        return matches;
    }
}
