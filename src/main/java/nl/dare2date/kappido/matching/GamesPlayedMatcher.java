package nl.dare2date.kappido.matching;

import nl.dare2date.kappido.common.IUserCache;
import nl.dare2date.kappido.services.MatchEntry;
import nl.dare2date.kappido.steam.ISteamGame;
import nl.dare2date.kappido.steam.ISteamUser;
import nl.dare2date.profile.ID2DProfileManager;

import java.util.*;

/**
 * Created by Maarten on 6-10-2015.
 */
public class GamesPlayedMatcher extends SteamMatcher {
    public GamesPlayedMatcher(ID2DProfileManager profileManager, IUserCache<ISteamUser> steamUserCache) {
        super(profileManager, steamUserCache);
    }

    @Override
    protected List<MatchEntry> findMatches(int dare2DateUser, ISteamUser steamUser, Map<Integer, ISteamUser> steamDare2DateUsers) {
        List<MatchEntry> matches = new ArrayList<>();

        Set<ISteamGame> gamesPlayed = new HashSet<>(steamUser.getOwnedGames());
        for (Map.Entry<Integer, ISteamUser> otherUser : steamDare2DateUsers.entrySet()) {
            if (otherUser.getKey() != dare2DateUser) { //We can't match with ourselves..
                for (ISteamGame otherUserGame : otherUser.getValue().getOwnedGames()) {
                    if (gamesPlayed.contains(otherUserGame)) {
                        MatchEntry entry = new MatchEntry();
                        entry.setUserId(otherUser.getKey());
                        entry.setProbability(1);
                        matches.add(entry);
                    }
                }
            }
        }

        return matches;
    }
}
