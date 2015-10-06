package nl.dare2date.kappido.matching;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;
import nl.dare2date.kappido.common.IUserCache;
import nl.dare2date.kappido.services.MatchEntry;
import nl.dare2date.kappido.twitch.FakeTwitchCache;
import nl.dare2date.kappido.twitch.TwitchAPIWrapper;
import nl.dare2date.kappido.twitch.TwitchUser;
import nl.dare2date.kappido.twitch.TwitchUserCache;
import nl.dare2date.profile.FakeD2DProfileManager;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Maarten on 6-10-2015.
 */
public class GameWatchedMatcherTest {

    private GamesWatchedMatcher matcher;

    @Before
    public void init(){
        TwitchAPIWrapper apiWrapper = new TwitchAPIWrapper();
        IUserCache<TwitchUser> userCache = new TwitchUserCache(apiWrapper);
        apiWrapper.setCache(userCache);
        matcher = new GamesWatchedMatcher(new FakeD2DProfileManager(), userCache);
    }

    @Test
    public void checkHasMatch(){
       /* List<MatchEntry> matches = matcher.findMatches(0);
        for(MatchEntry match : matches){
            System.out.println("id: " + match.getUserId() + ", probability: " + match.getProbability());
        }*/
    }

}
