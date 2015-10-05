package nl.dare2date.kappido.matching;

import nl.dare2date.kappido.services.MatchEntry;
import nl.dare2date.kappido.services.MatchParameter;
import nl.dare2date.kappido.services.MatchType;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by Maarten on 05-Oct-15.
 */
public class MatchMakerTest {

    private MatchMaker matchMaker;

    @Before
    public void init(){
        Map<MatchType, IMatchType> matchers = new HashMap<>();
        matchers.put(MatchType.MUTUAL_FOLLOWINGS, new TestMatcher());
        matchers.put(MatchType.GAMES_PLAYED, new TestMatcher());
        this.matchMaker = new MatchMaker(matchers);
    }

    /**
     * Make sure the matches that are created are sorted from the user with the highest matching probability to the lowest.
     */
    @Test
    public void checkSortOrder(){
        List<MatchParameter> matchParameters = new ArrayList<MatchParameter>();

        MatchParameter parameter = new MatchParameter();
        parameter.setMatchType(MatchType.MUTUAL_FOLLOWINGS);
        parameter.setWeighing(1);
        matchParameters.add(parameter);

        List<MatchEntry> matches = matchMaker.findMatch(0, matchParameters);
        assertTrue(matches.get(0).getProbability() >= matches.get(1).getProbability());
    }

    /**
     * Validate the expected matching probabilities.
     */
    @Test
    public void checkCombinedProbabilities(){
        List<MatchParameter> matchParameters = new ArrayList<MatchParameter>();

        MatchParameter parameter = new MatchParameter();
        parameter.setMatchType(MatchType.MUTUAL_FOLLOWINGS);
        parameter.setWeighing(1);
        matchParameters.add(parameter);

        parameter = new MatchParameter();
        parameter.setMatchType(MatchType.GAMES_PLAYED);
        parameter.setWeighing(2);
        matchParameters.add(parameter);

        List<MatchEntry> matches = matchMaker.findMatch(0, matchParameters);
        assertEquals(1.5, matches.get(0).getProbability(), 0.001);
        assertEquals(0.9, matches.get(1).getProbability(), 0.001);
        /*for(MatchEntry entry : matches){
            System.out.println(entry.getUserId() + " , " + entry.getProbability());
        }*/
    }
}
