package nl.dare2date.kappido.matching;

import nl.dare2date.kappido.common.IUserCache;
import nl.dare2date.kappido.services.MatchEntry;
import nl.dare2date.kappido.twitch.ITwitchUser;
import nl.dare2date.kappido.twitch.TwitchUser;
import nl.dare2date.kappido.twitch.TwitchUserCache;
import nl.dare2date.profile.ID2DProfileManager;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * Created by Maarten on 6-10-2015.
 */
public class GamesWatchedMatcher implements IMatcher {
    private ID2DProfileManager profileManager;
    private IUserCache<TwitchUser> twitchUserCache;

    public GamesWatchedMatcher(ID2DProfileManager profileManager, IUserCache<TwitchUser> twitchUserCache){
        this.profileManager = profileManager;
        this.twitchUserCache = twitchUserCache;
    }

    @Override
    public List<MatchEntry> findMatches(int dare2DateUser) {
        List<MatchEntry> matches = new ArrayList<>();

        String twitchId = profileManager.getTwitchId(dare2DateUser);
        if(twitchId == null) throw new IllegalStateException("No Twitch Id for user " + dare2DateUser); //TODO handle differently?

        ITwitchUser twitchUser = twitchUserCache.getUserById(twitchId);
        if(twitchUser == null) throw new IllegalStateException("No Twitch user for Twitch Id " + twitchId); //TODO handle differently?

        List<ITwitchUser> followingUsers = twitchUser.getFollowingUsers();

        Set<String> watchedGames = new HashSet<>(); //Fill an HashSet with the watched games, so we can utilize a O(1) lookup speed later.
        for(ITwitchUser followingUser : followingUsers){
            String watchedGame = followingUser.getLastPlayedGame();
            if(!StringUtils.isEmpty(watchedGame)) watchedGames.add(watchedGame);
        }

        for(int otherDare2DateUser : profileManager.getAllUsers()){
            String otherTwitchId = profileManager.getTwitchId(otherDare2DateUser);
            if(otherTwitchId != null){
                ITwitchUser otherTwitchUser = twitchUserCache.getUserById(otherTwitchId);
                if(otherTwitchUser != null) {
                    List<ITwitchUser> otherFollowingUsers = otherTwitchUser.getFollowingUsers();
                    for(ITwitchUser otherFollowingUser : otherFollowingUsers){
                        if(watchedGames.contains(otherFollowingUser.getLastPlayedGame())){
                            MatchEntry entry = new MatchEntry();
                            entry.setUserId(otherDare2DateUser);
                            entry.setProbability(1); //It could be that multiple games watched are shared, in that case the probabilities will be combined in MatchMaker.
                            matches.add(entry);
                        }
                    }
                }
            }
        }

        return matches;
    }
}
