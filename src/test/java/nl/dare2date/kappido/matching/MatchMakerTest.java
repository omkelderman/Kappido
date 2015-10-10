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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Class to unit test the {@link MatchMaker} class.
 */
public class MatchMakerTest {

    private MatchMaker matchMaker;

    /**
     * Fill the MatchMaker with a couple fake match parameters.
     */
    @Before
    public void init() {
        Map<MatchType, IMatcher> matchers = new HashMap<>();
        matchers.put(MatchType.MUTUAL_FOLLOWINGS, new TestMatcher());
        matchers.put(MatchType.GAMES_PLAYED, new TestMatcher());
        this.matchMaker = new MatchMaker(matchers);
    }

    /**
     * Make sure the matches that are created are sorted from the user with the highest matching probability to the lowest.
     */
    @Test
    public void checkSortOrder() {
        List<MatchParameter> matchParameters = new ArrayList<>();

        //Set to match only based on 'mutual followings'.
        MatchParameter parameter = new MatchParameter();
        parameter.setMatchType(MatchType.MUTUAL_FOLLOWINGS);
        parameter.setWeighing(1);
        matchParameters.add(parameter);

        List<MatchEntry> matches = matchMaker.findMatch(0, matchParameters);

        //Verify that the first user in the list has a higher/equal match probability as the second user.
        assertTrue(matches.get(0).getProbability() >= matches.get(1).getProbability());
    }

    /**
     * Validate the expected matching probabilities.
     * <p>
     * Two instances of TestMatcher are used which will return a match with:
     * -user id 1 with a probability of 0.5
     * -user id 2 with a probability of 0.3
     * <p>
     * When matching on two matching parameters one with weighing 1 and one with 2, we expect:
     * -user id 1 with a probability of 0.5 * 2 + 0.5 * 1 = 1.5
     * -user id 2 with a probability of 0.3 * 2 + 0.3 * 1 = 0.9
     */
    @Test
    public void checkCombinedProbabilities() {
        List<MatchParameter> matchParameters = new ArrayList<>();

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
    }
}
