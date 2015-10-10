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
 * The work horse class of the application. When invoked it will create a match based on the supplied match parameters.
 * It will calculate a list of Dare2Date users with their matching probabilities.
 */
public class MatchMaker {
    /**
     * Map that holds all registered IMatchers, different ways to match users with.
     */
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

    /**
     * When invoked it will analyze the Dare2Date user database on various configurable parameters. It will return
     * a list of users that are a match with the user provided, sorted from highest to lowest match probability.
     *
     * @param dare2DateUser   The user that is used to match with.
     * @param matchParameters A list of parameters to match on and their weighing. With the latter some parameters could
     *                        be made more important than others.
     * @return A list of matches (with other users), sorted from highest matching probability to the lowest.
     */
    public List<MatchEntry> findMatch(int dare2DateUser, List<MatchParameter> matchParameters) {

        //Store all potential matches in a map where the key is the Dare2Date user id, and the value the matching probability.
        HashMap<Integer, Double> userMatchProbabilities = new HashMap<>();

        //Process all the required match parameters.
        for (MatchParameter matchParameter : matchParameters) {
            IMatcher matcher = matchers.get(matchParameter.getMatchType()); //Get the matcher from the registry.
            if (matcher == null)
                throw new IllegalStateException("No matcher for match type " + matchParameter.getMatchType());
            List<MatchEntry> matches = matcher.findMatches(dare2DateUser);
            for (MatchEntry match : matches) {
                Double lastProbability = userMatchProbabilities.get(match.getUserId());

                //When there wasn't a match probability of a stored yet, store the one that was just aqcuired. If there was, store the current one added to the last one.
                userMatchProbabilities.put(match.getUserId(), match.getProbability() * matchParameter.getWeighing() + (lastProbability != null ? lastProbability : 0));
            }
        }
        List<MatchEntry> matches = convertToMatchList(userMatchProbabilities);
        Collections.sort(matches, new MatchEntryComparator()); //Sort the matches from highest probability to lowest.
        return matches;
    }

    /**
     * Simply converts the <Dare2DateUserId, MatchProbability> map to a list containing this info.
     *
     * @param map The map to convert from
     * @return The list that the map has been converted to
     */
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

    /**
     * Sorting class that sorts based on matching probability. Note that the sort is 'inverted', making the highest
     * probability match be the first element when invoking Collections.sort(list, new MatchEntryComparator()).
     */
    private static class MatchEntryComparator implements Comparator<MatchEntry> {

        @Override
        public int compare(MatchEntry entry1, MatchEntry entry2) {
            return Double.compare(entry2.getProbability(), entry1.getProbability());
        }
    }
}
