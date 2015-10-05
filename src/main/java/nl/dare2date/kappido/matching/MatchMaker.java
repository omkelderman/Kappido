package nl.dare2date.kappido.matching;

import nl.dare2date.kappido.services.MatchEntry;
import nl.dare2date.kappido.services.MatchParameter;
import nl.dare2date.kappido.services.MatchType;

import java.util.*;

/**
 * Created by Maarten on 05-Oct-15.
 */
public class MatchMaker {
    private Map<MatchType, IMatchType> matchers;

    public MatchMaker(){
        this(new HashMap<MatchType, IMatchType>());
        //built in matchers can be put here.
    }

    public MatchMaker(Map<MatchType, IMatchType> matchers){
        this.matchers = matchers;
    }

    public List<MatchEntry> findMatch(int dare2DateUser, List<MatchParameter> matchParameters){
        HashMap<Integer, Double> userMatchProbabilities = new HashMap<>();
        for(MatchParameter matchParameter : matchParameters){
            IMatchType matcher = matchers.get(matchParameter.getMatchType());
            if(matcher == null) throw new IllegalStateException("No matcher for match type " + matchParameter.getMatchType());
            List<MatchEntry> matches = matcher.findMatches(dare2DateUser);
            for(MatchEntry match : matches){
                Double lastProbability = userMatchProbabilities.get(match.getUserId());
                userMatchProbabilities.put(match.getUserId(), match.getProbability() * matchParameter.getWeighing() + (lastProbability != null ? lastProbability : 0));
            }
        }
        List<MatchEntry> matches = convertToMatchList(userMatchProbabilities);
        Collections.sort(matches, new MatchEntryComparator()); //Sort the matches from highest probability to lowest.
        return matches;
    }

    private List<MatchEntry> convertToMatchList(Map<Integer, Double> map){
        List<MatchEntry> matches = new ArrayList<>();
        for(Map.Entry<Integer, Double> match : map.entrySet()){
            MatchEntry matchEntry = new MatchEntry();
            matchEntry.setUserId(match.getKey());
            matchEntry.setProbability(match.getValue());
            matches.add(matchEntry);
        }
        return matches;
    }

    private static class MatchEntryComparator implements Comparator<MatchEntry>{

        @Override
        public int compare(MatchEntry entry1, MatchEntry entry2) {
            return Double.compare(entry2.getProbability(), entry1.getProbability());
        }
    }
}
