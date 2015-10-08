package nl.dare2date.kappido.matching;

import nl.dare2date.kappido.common.IUserCache;
import nl.dare2date.kappido.services.MatchEntry;
import nl.dare2date.kappido.services.MatchParameter;
import nl.dare2date.kappido.services.MatchType;
import nl.dare2date.kappido.steam.ISteamUser;
import nl.dare2date.kappido.twitch.ITwitchUser;
import nl.dare2date.profile.ID2DProfileManager;

import java.util.*;

/**
 * Created by Maarten on 05-Oct-15.
 */
public class MatchMaker {
    private final Map<MatchType, IMatcher> matchers;

    public MatchMaker(ID2DProfileManager profileManager, IUserCache<ITwitchUser> twitchUserCache, IUserCache<ISteamUser> steamUserCache) {
        this(new HashMap<MatchType, IMatcher>());
        matchers.put(MatchType.GAMES_WATCHED, new GamesWatchedMatcher(profileManager, twitchUserCache));
        matchers.put(MatchType.MUTUAL_FOLLOWINGS, new MutualFollowingsMatcher(profileManager, twitchUserCache));
        matchers.put(MatchType.GAMES_STREAMED, new GamesStreamedMatcher(profileManager, twitchUserCache));
        matchers.put(MatchType.GENRES_PLAYED, new GameGenresPlayedMatcher(profileManager, steamUserCache));
        matchers.put(MatchType.GAMES_PLAYED, new GamesPlayedMatcher(profileManager, steamUserCache));
    }

    public MatchMaker(Map<MatchType, IMatcher> matchers) {
        this.matchers = matchers;
    }

    public List<MatchEntry> findMatch(int dare2DateUser, List<MatchParameter> matchParameters) {
        HashMap<Integer, Double> userMatchProbabilities = new HashMap<>();
        for (MatchParameter matchParameter : matchParameters) {
            IMatcher matcher = matchers.get(matchParameter.getMatchType());
            if (matcher == null)
                throw new IllegalStateException("No matcher for match type " + matchParameter.getMatchType());
            List<MatchEntry> matches = matcher.findMatches(dare2DateUser);
            for (MatchEntry match : matches) {
                Double lastProbability = userMatchProbabilities.get(match.getUserId());
                userMatchProbabilities.put(match.getUserId(), match.getProbability() * matchParameter.getWeighing() + (lastProbability != null ? lastProbability : 0));
            }
        }
        List<MatchEntry> matches = convertToMatchList(userMatchProbabilities);
        Collections.sort(matches, new MatchEntryComparator()); //Sort the matches from highest probability to lowest.
        return matches;
    }

    private List<MatchEntry> convertToMatchList(Map<Integer, Double> map) {
        List<MatchEntry> matches = new ArrayList<>();
        for (Map.Entry<Integer, Double> match : map.entrySet()) {
            MatchEntry matchEntry = new MatchEntry();
            matchEntry.setUserId(match.getKey());
            matchEntry.setProbability(match.getValue());
            matches.add(matchEntry);
        }
        return matches;
    }

    private static class MatchEntryComparator implements Comparator<MatchEntry> {

        @Override
        public int compare(MatchEntry entry1, MatchEntry entry2) {
            return Double.compare(entry2.getProbability(), entry1.getProbability());
        }
    }
}
