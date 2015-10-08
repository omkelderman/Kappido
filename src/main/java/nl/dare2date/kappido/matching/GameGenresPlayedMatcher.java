package nl.dare2date.kappido.matching;

import nl.dare2date.kappido.common.IUserCache;
import nl.dare2date.kappido.services.MatchEntry;
import nl.dare2date.kappido.steam.ISteamGame;
import nl.dare2date.kappido.steam.ISteamUser;
import nl.dare2date.kappido.steam.SteamUser;
import nl.dare2date.profile.ID2DProfileManager;

import java.util.*;

/**
 * Created by Maarten on 6-10-2015.
 */
public class GameGenresPlayedMatcher extends SteamMatcher {
    public GameGenresPlayedMatcher(ID2DProfileManager profileManager, IUserCache<SteamUser> steamUserCache) {
        super(profileManager, steamUserCache);
    }

    @Override
    protected List<MatchEntry> findMatches(int dare2DateUser, ISteamUser steamUser, Map<Integer, ISteamUser> steamDare2DateUsers) {
        List<MatchEntry> matches = new ArrayList<>();

        Set<String> gameGenresPlayed = new HashSet<>();
        for (ISteamGame game : steamUser.getOwnedGames()) {
            for (String genre : game.getGenres()) {
                gameGenresPlayed.add(genre);
            }
        }
        for (Map.Entry<Integer, ISteamUser> otherUser : steamDare2DateUsers.entrySet()) {
            if (otherUser.getKey() != dare2DateUser) { //We can't match with ourselves..
                for (ISteamGame otherUserGame : otherUser.getValue().getOwnedGames()) {
                    for (String otherUserGenre : otherUserGame.getGenres()) {
                        if (gameGenresPlayed.contains(otherUserGenre)) {
                            MatchEntry entry = new MatchEntry();
                            entry.setUserId(otherUser.getKey());
                            entry.setProbability(1);
                            matches.add(entry);
                        }
                    }
                }
            }
        }

        return matches;
    }
}
